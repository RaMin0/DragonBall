package dragonball.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import dragonball.exceptions.InvalidAttackTypeException;
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
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class Game {
	private Player player;
	private World world;
	private GameState state;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;
	private Set<GameListener> listeners = new HashSet<>();

	public Game() {
		player = new Player("Player");
		world = new World();
		state = GameState.WORLD;
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();

		loadData(".");

		world.generateMap(weakFoes, strongFoes);

		player.addListener(new PlayerListener() {
			@Override
			public void onDragonCalled() {
				state = GameState.DRAGON;

				Dragon dragon = dragons.get(new Random().nextInt(dragons.size()));

				notifyListenersOnDragonMode(dragon);
			}

			@Override
			public void onWishChosen(DragonWish wish) {
				state = GameState.WORLD;

				notifyListenersOnDragonWishGranted(wish);
			}
		});

		world.addListener(new WorldListener() {
			@Override
			public void onFoeEncountered(final NonPlayableFighter foe) {
				state = GameState.BATTLE;

				Battle battle = new Battle(player.getActiveFighter(), foe);
				// handle winning and losing in a battle
				battle.addListener(new BattleListener() {
					@Override
					public void onEvent(BattleEvent e) {
						if (e.getType() == BattleEventType.BATTLE_ENDED) {
							state = GameState.WORLD;

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
									loadFoes("." + File.separator + "Database-Foes-Range" + foesRange + ".csv");

									// regenerate map
									world.generateMap(weakFoes, strongFoes);
								}
							// if my opponent won
							} else if (e.getWinner() == foe) {
								world.resetPlayerPosition();
							}
						}
					}
				});

				notifyListenersOnBattleMode(battle);
			}

			@Override
			public void onCollectibleFound(Collectible collectible) {
				switch (collectible) {
				case SENZU_BEAN:
					player.setSenzuBeans(player.getSenzuBeans() + 1);
					notifyListenersOnCollectibleFound(collectible);
					break;
				case DRAGON_BALL:
					player.setDragonBalls(player.getDragonBalls() + 1);
					notifyListenersOnCollectibleFound(collectible);

					try {
						// try to call the dragon. it will fail if not enough dragon balls
						// are collected
						player.callDragon();
					} catch (NotEnoughCollectiblesException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		});
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

	private void loadData(String dataDir) {
		try {
			int foesRange = (player.getMaxFighterLevel() - 1) / 10 + 1;

			loadAttacks(dataDir + File.separator + "Database-Attacks.csv");
			loadFoes(dataDir + File.separator + "Database-Foes-Range" + foesRange + ".csv");
			loadDragons(dataDir + File.separator + "Database-Dragons.csv");
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
						maxKi, maxStamina, strong, new ArrayList<SuperAttack>(), new ArrayList<UltimateAttack>());
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

				dragon = new Dragon(name, new ArrayList<SuperAttack>(), new ArrayList<UltimateAttack>(), senzuBeans,
						dragonsBalls);
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

	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnCollectibleFound(Collectible collectible) {
		for (GameListener listener : listeners) {
			listener.onCollectibleFound(collectible);
		}
	}

	public void notifyListenersOnBattleMode(Battle battle) {
		for (GameListener listener : listeners) {
			listener.onBattle(battle);
		}
	}

	public void notifyListenersOnDragonMode(Dragon dragon) {
		for (GameListener listener : listeners) {
			listener.onDragonCalled(dragon);
		}
	}

	public void notifyListenersOnDragonWishGranted(DragonWish wish) {
		for (GameListener listener : listeners) {
			listener.onDragonWishGranted(wish);
		}
	}
}
