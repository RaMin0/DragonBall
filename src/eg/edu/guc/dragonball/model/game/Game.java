package eg.edu.guc.dragonball.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eg.edu.guc.dragonball.exceptions.InvalidAttackTypeException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.attack.MaximumCharge;
import eg.edu.guc.dragonball.model.attack.PhysicalAttack;
import eg.edu.guc.dragonball.model.attack.SuperAttack;
import eg.edu.guc.dragonball.model.attack.SuperSaiyan;
import eg.edu.guc.dragonball.model.attack.UltimateAttack;
import eg.edu.guc.dragonball.model.battle.Battle;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.character.fighter.Earthling;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.player.Player;
import eg.edu.guc.dragonball.model.world.World;

	private Player player;
	private World world;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Attack> dragonAttacks;

	public Game() {
		player = new Player("Player");
		world = new World();
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragonAttacks = new ArrayList<>();

		loadData(".");

		world.generateMap(weakFoes, strongFoes);
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

	private void loadData(String dataDir) {
		try {
			loadFoes(weakFoes, dataDir + File.separator + "Database-Weak-Foes.csv");

			loadFoes(strongFoes, dataDir + File.separator + "Database-Strong-Foes.csv");
			for (NonPlayableFighter foe : strongFoes) {
				foe.setStrong(true);
			}

			loadAttacks(attacks, dataDir + File.separator + "Database-Attacks.csv");

			loadAttacks(dragonAttacks, dataDir + File.separator + "Database-Dragon-Attacks.csv");
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidAttackTypeException e) {
			e.printStackTrace();
		}
	}

	private void loadFoes(ArrayList<NonPlayableFighter> foes, String filePath)
			throws ArrayIndexOutOfBoundsException, NumberFormatException {
		String[][] lines = loadCSV(filePath);

		for (int i = 1; i < lines.length; i++) {
			String name = lines[i][0];
			int level = Integer.parseInt(lines[i][1]);
			int blastDamage = Integer.parseInt(lines[i][2]);
			int physicalDamage = Integer.parseInt(lines[i][3]);
			int maxHealthPoints = Integer.parseInt(lines[i][4]);
			int maxKi = Integer.parseInt(lines[i][5]);
			int maxStamina = Integer.parseInt(lines[i][6]);

			NonPlayableFighter foe = new NonPlayableFighter(name, level, blastDamage, physicalDamage, maxHealthPoints,
					maxKi, maxStamina, false);
			foes.add(foe);
		}
	}

	private void loadAttacks(ArrayList<Attack> attacks, String filePath)
			throws ArrayIndexOutOfBoundsException, NumberFormatException, InvalidAttackTypeException {
		String[][] lines = loadCSV(filePath);

		for (int i = 1; i < lines.length; i++) {
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

	}
}
