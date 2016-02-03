package eg.edu.guc.dragonball.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eg.edu.guc.dragonball.exceptions.DragonBallException;
import eg.edu.guc.dragonball.exceptions.InvalidAttackTypeException;
import eg.edu.guc.dragonball.exceptions.InvalidFighterTypeException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.attack.MaximumCharge;
import eg.edu.guc.dragonball.model.attack.SuperAttack;
import eg.edu.guc.dragonball.model.attack.SuperSaiyan;
import eg.edu.guc.dragonball.model.attack.UltimateAttack;
import eg.edu.guc.dragonball.model.battle.Battle;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.character.fighter.Earthling;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;
import eg.edu.guc.dragonball.model.character.fighter.Frieza;
import eg.edu.guc.dragonball.model.character.fighter.Majin;
import eg.edu.guc.dragonball.model.character.fighter.Namekian;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.Saiyan;
import eg.edu.guc.dragonball.model.dragon.Dragon;
import eg.edu.guc.dragonball.model.player.Player;
import eg.edu.guc.dragonball.model.world.World;

public class Game implements World.Listener {
	private Player player;
	private World world;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;
	private Set<Listener> listeners = new HashSet<>();

	public Game() {
		player = new Player("Player");
		world = new World();
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();

		loadData(".");

		world.generateMap(weakFoes, strongFoes);
		world.addListener(this);
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	private String[][] loadCSV(String filePath) {
		ArrayList<String[]> lines = new ArrayList<>();

		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null) {
				lines.add(line.split(","));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lines.toArray(new String[][] {});
	}

	@SuppressWarnings("unchecked")
	private void loadData(String dataDir) {
		try {
			int foesRange = (player.getExploredMaps() - 1) / 10 + 1;

			loadAttacks(dataDir + File.separator + "Database-Attacks.csv");
			loadFoes(dataDir + File.separator + "Database-Foes-Range" + foesRange + ".csv");
			loadDragons(dataDir + File.separator + "Database-Dragons.csv");

			// TODO: Remove this when setup screen is ready
			for (Attack attack : attacks) {
				if (attack instanceof SuperAttack) {
					player.getSuperAttacks().add((SuperAttack) attack);
				} else if (attack instanceof UltimateAttack) {
					player.getUltimateAttacks().add((UltimateAttack) attack);
				}
			}

			Class<? extends PlayableFighter>[] fighterClasses = new Class[] { Earthling.class, Frieza.class,
					Majin.class, Namekian.class, Saiyan.class };
			for (Class<? extends PlayableFighter> fighterClass : fighterClasses) {
				PlayableFighter fighter = addFighter(player, fighterClass, fighterClass.getSimpleName());

				for (int j = 0; j < Fighter.MAX_SUPER_ATTACKS;) {
					int index = new Random().nextInt(player.getSuperAttacks().size());
					Attack attack = player.getSuperAttacks().get(index);
					try {
						fighter.learn(attack, null);
						j++;
					} catch (DragonBallException e) {

					}
				}
				for (int j = 0; j < Fighter.MAX_ULTIMATE_ATTACKS;) {
					int index = new Random().nextInt(player.getUltimateAttacks().size());
					Attack attack = player.getUltimateAttacks().get(index);
					try {
						fighter.learn(attack, null);
						j++;
					} catch (DragonBallException e) {

					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidAttackTypeException e) {
			e.printStackTrace();
		}
	}

	private void loadAttacks(String filePath)
			throws ArrayIndexOutOfBoundsException, NumberFormatException, InvalidAttackTypeException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i++) {
			Attack attack;

			String attackType = lines[i][0];
			String name = lines[i][1];
			int damage = Integer.parseInt(lines[i][2]);

			if (attackType.equalsIgnoreCase("SA")) {
				attack = new SuperAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("UA")) {
				attack = new UltimateAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("MC")) {
				attack = new MaximumCharge();
			} else if (attackType.equalsIgnoreCase("SS")) {
				attack = new SuperSaiyan();
			} else {
				throw new InvalidAttackTypeException(attackType);
			}

			attacks.add(attack);
		}
	}

	private void loadFoes(String filePath) throws ArrayIndexOutOfBoundsException, NumberFormatException {
		String[][] lines = loadCSV(filePath);

		NonPlayableFighter foe = null;
		for (int i = 0; i < lines.length; i++) {
			switch (i % 3) {
			case 0:
				String name = lines[i][0];
				int level = Integer.parseInt(lines[i][1]);
				int maxHealthPoints = Integer.parseInt(lines[i][2]);
				int blastDamage = Integer.parseInt(lines[i][3]);
				int physicalDamage = Integer.parseInt(lines[i][4]);
				int maxKi = Integer.parseInt(lines[i][5]);
				int maxStamina = Integer.parseInt(lines[i][6]);
				boolean strong = Boolean.parseBoolean(lines[i][7]);

				foe = new NonPlayableFighter(name, level, maxHealthPoints, blastDamage, physicalDamage,
						maxKi, maxStamina, strong);
				(strong ? strongFoes : weakFoes).add(foe);
				break;
			case 1:
				for (int j = 0; j < lines[i].length; j++) {
					String attackName = lines[i][j];
					for (Attack attack : attacks) {
						if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
							foe.getSuperAttacks().add((SuperAttack) attack);
							break;
						}
					}
				}
				break;
			case 2:
				for (int j = 0; j < lines[i].length; j++) {
					String attackName = lines[i][j];
					for (Attack attack : attacks) {
						if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
							foe.getUltimateAttacks().add((UltimateAttack) attack);
							break;
						}
					}
				}
				break;
			}
		}
	}

	private void loadDragons(String filePath) throws ArrayIndexOutOfBoundsException, NumberFormatException {
		String[][] lines = loadCSV(filePath);

		Dragon dragon = null;
		for (int i = 0; i < lines.length; i++) {
			switch (i % 3) {
			case 0:
				String name = lines[i][0];
				int senzuBeans = Integer.parseInt(lines[i][1]);
				int dragonsBalls = Integer.parseInt(lines[i][2]);

				dragon = new Dragon(name, senzuBeans, dragonsBalls);
				dragons.add(dragon);
				break;
			case 1:
				for (int j = 0; j < lines[i].length; j++) {
					String attackName = lines[i][j];
					for (Attack attack : attacks) {
						if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
							dragon.getSuperAttacks().add((SuperAttack) attack);
							break;
						}
					}
				}
				break;
			case 2:
				for (int j = 0; j < lines[i].length; j++) {
					String attackName = lines[i][j];
					for (Attack attack : attacks) {
						if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
							dragon.getUltimateAttacks().add((UltimateAttack) attack);
							break;
						}
					}
				}
				break;
			}
		}
	}

	public PlayableFighter addFighter(Player player, String fighterType, String fighterName)
			throws InvalidFighterTypeException {
		Class<? extends PlayableFighter> fighterClass;

		if (fighterType.equalsIgnoreCase("E")) {
			fighterClass = Earthling.class;
		} else if (fighterType.equalsIgnoreCase("S")) {
			fighterClass = Saiyan.class;
		} else if (fighterType.equalsIgnoreCase("N")) {
			fighterClass = Namekian.class;
		} else if (fighterType.equalsIgnoreCase("F")) {
			fighterClass = Frieza.class;
		} else if (fighterType.equalsIgnoreCase("M")) {
			fighterClass = Majin.class;
		} else {
			throw new InvalidFighterTypeException(fighterType);
		}

		return addFighter(player, fighterClass, fighterName);
	}

	private PlayableFighter addFighter(Player player, Class<? extends PlayableFighter> fighterClass,
			String fighterName) {
		PlayableFighter fighter = null;
		try {
			fighter = (PlayableFighter) fighterClass.getConstructors()[0].newInstance(fighterClass.getSimpleName());
			fighter.setLearnableSuperAttacks(player.getSuperAttacks());
			fighter.setLearnableUltimateAttacks(player.getUltimateAttacks());
			player.getFighters().add(fighter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fighter;
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnCellBattle(Battle battle) {
		for (Listener listener : listeners) {
			listener.onCellBattle(battle);
		}
	}

	public void notifyListenersOnCellCollectible(Collectible collectible) {
		for (Listener listener : listeners) {
			listener.onCellCollectible(collectible);
		}
	}

	public void notifyListenersOnDragonMode(Dragon dragon) {
		for (Listener listener : listeners) {
			listener.onDragonMode(dragon);
		}
	}

	@Override
	public void onCellFoe(final NonPlayableFighter foe) {
		Battle battle = new Battle(player.getActiveFighter(), foe);

		notifyListenersOnCellBattle(battle);
	}

	@Override
	public void onCellCollectible(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			player.setSenzuBeans(player.getSenzuBeans() + 1);
			notifyListenersOnCellCollectible(collectible);
			break;
		case DRAGON_BALL:
			player.setDragonBalls(player.getDragonBalls() + 1);
			notifyListenersOnCellCollectible(collectible);

			if (player.getDragonBalls() == 7) {
				player.setDragonBalls(0);

				Dragon dragon = dragons.get(new Random().nextInt(dragons.size()));
				dragon.grantWish(player);
				
				notifyListenersOnDragonMode(dragon);
			}
			break;
		}
	}

	public interface Listener extends EventListener {
		void onCellBattle(Battle battle);

		void onCellCollectible(Collectible collectible);

		void onDragonMode(Dragon dragon);
	}
}
