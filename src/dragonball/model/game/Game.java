package dragonball.model.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import dragonball.exceptions.NotEnoughCollectiblesException;
import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class Game implements PlayerListener, WorldListener, BattleListener {
	private Player player;
	private World world;
	private GameState state;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;
	private GameListener listener;

	public Game() {
		player = new Player("Player");
		world = new World();
		state = GameState.WORLD;
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();

		loadAttacks("Database-Attacks.csv");
		loadFoes("Database-Foes-Range1.csv");
		loadDragons("Database-Dragons.csv");

		world.generateMap(weakFoes, strongFoes);

		player.setListener(this);
		world.setListener(this);
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public GameState getState() {
		return state;
	}

	public ArrayList<NonPlayableFighter> getWeakFoes() {
		return weakFoes;
	}

	public ArrayList<NonPlayableFighter> getStrongFoes() {
		return strongFoes;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public ArrayList<Dragon> getDragons() {
		return dragons;
	}

	private String[][] loadCSV(String filePath) {
		ArrayList<String[]> lines = new ArrayList<>();

		BufferedReader reader = null;
		String line = null;
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
			if (is == null) {
				throw new IOException("Resource not found: " + filePath);
			}
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				lines.add(line.split(","));
			}
		} catch (IOException e) {
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

	private void loadAttacks(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i++) {
			Attack attack = null;

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
			}

			if (attack != null) {
				attacks.add(attack);
			}
		}
	}

	private void loadFoes(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
			String name = lines[i][0];
			int level = Integer.parseInt(lines[i][1]);
			int maxHealthPoints = Integer.parseInt(lines[i][2]);
			int blastDamage = Integer.parseInt(lines[i][3]);
			int physicalDamage = Integer.parseInt(lines[i][4]);
			int maxKi = Integer.parseInt(lines[i][5]);
			int maxStamina = Integer.parseInt(lines[i][6]);
			boolean strong = Boolean.parseBoolean(lines[i][7]);
			ArrayList<SuperAttack> superAttacks = new ArrayList<>();
			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<>();

			for (int j = 0; j < lines[i + 1].length; j++) {
				String attackName = lines[i + 1][j];
				for (Attack attack : attacks) {
					if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
						superAttacks.add((SuperAttack) attack);
						break;
					}
				}
			}

			for (int j = 0; j < lines[i + 2].length; j++) {
				String attackName = lines[i + 2][j];
				for (Attack attack : attacks) {
					if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
						ultimateAttacks.add((UltimateAttack) attack);
						break;
					}
				}
			}

			NonPlayableFighter foe = new NonPlayableFighter(name, level, maxHealthPoints, blastDamage, physicalDamage, maxKi,
					maxStamina, strong, superAttacks, ultimateAttacks);
			if (strong) {
				strongFoes.add(foe);
			} else {
				weakFoes.add(foe);
			}
		}
	}

	private void loadDragons(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
			String name = lines[i][0];
			int senzuBeans = Integer.parseInt(lines[i][1]);
			int dragonsBalls = Integer.parseInt(lines[i][2]);
			ArrayList<SuperAttack> superAttacks = new ArrayList<>();
			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<>();

			for (int j = 0; j < lines[i + 1].length; j++) {
				String attackName = lines[i + 1][j];
				for (Attack attack : attacks) {
					if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
						superAttacks.add((SuperAttack) attack);
						break;
					}
				}
			}

			for (int j = 0; j < lines[i + 2].length; j++) {
				String attackName = lines[i + 2][j];
				for (Attack attack : attacks) {
					if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
						ultimateAttacks.add((UltimateAttack) attack);
						break;
					}
				}
			}

			Dragon dragon = new Dragon(name, superAttacks, ultimateAttacks, senzuBeans, dragonsBalls);
			dragons.add(dragon);
		}
	}

	@Override
	public void onDragonCalled() {
		state = GameState.DRAGON;

		Dragon dragon = dragons.get(new Random().nextInt(dragons.size()));

		notifyOnDragonCalled(dragon);
	}

	@Override
	public void onWishChosen(DragonWish wish) {
		state = GameState.WORLD;
	}

	@Override
	public void onFoeEncountered(final NonPlayableFighter foe) {
		state = GameState.BATTLE;

		Battle battle = new Battle(player.getActiveFighter(), foe);
		// handle winning and losing in a battle
		battle.setListener(this);
		// start the battle
		battle.start();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
			case SENZU_BEAN:
				player.setSenzuBeans(player.getSenzuBeans() + 1);
				notifyOnCollectibleFound(collectible);
				break;
			case DRAGON_BALL:
				player.setDragonBalls(player.getDragonBalls() + 1);
				notifyOnCollectibleFound(collectible);

				try {
					// try to call the dragon. it will fail if not enough dragon balls are collected
					player.callDragon();
				} catch (NotEnoughCollectiblesException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();

		if (e.getType() == BattleEventType.ENDED) {
			PlayableFighter me = player.getActiveFighter();
			// if i won
			if (e.getWinner() == me) {
				// gain xp
				me.setXp(me.getXp() + foe.getLevel() * 5);

				// learn opponents super and ultimate attacks
				for (SuperAttack superAttack : foe.getSuperAttacks()) {
					if (!player.getSuperAttacks().contains(superAttack)) {
						player.getSuperAttacks().add(superAttack);
					}
				}
				for (UltimateAttack ultimateAttack : foe.getUltimateAttacks()) {
					if (!player.getUltimateAttacks().contains(ultimateAttack)) {
						player.getUltimateAttacks().add(ultimateAttack);
					}
				}

				// if opponent is boss
				if (foe.isStrong()) {
					// increment explored maps by 1
					player.setExploredMaps(player.getExploredMaps() + 1);

					// reload foes in case range changed
					int foesRange = (player.getMaxFighterLevel() - 1) / 10 + 1;
					loadFoes("Database-Foes-Range" + foesRange + ".csv");

					// regenerate map
					world.generateMap(weakFoes, strongFoes);
				}
				// if my opponent won
			} else if (e.getWinner() == foe) {
				// undo removing the foe from the cell
				Cell foeCell = new FoeCell(foe);
				foeCell.setListener(world);
				world.getMap()[world.getPlayerRow()][world.getPlayerColumn()] = foeCell;

				// reset player position to starting cell
				world.resetPlayerPosition();
			}

			state = GameState.WORLD;
		}

		notifyOnBattleEvent(e);
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void notifyOnCollectibleFound(Collectible collectible) {
		if (listener != null) {
			listener.onCollectibleFound(collectible);
		}
	}

	public void notifyOnBattleEvent(BattleEvent e) {
		if (listener != null) {
			listener.onBattleEvent(e);
		}
	}

	public void notifyOnDragonCalled(Dragon dragon) {
		if (listener != null) {
			listener.onDragonCalled(dragon);
		}
	}
}
