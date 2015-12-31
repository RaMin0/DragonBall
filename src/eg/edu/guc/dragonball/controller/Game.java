package eg.edu.guc.dragonball.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.player.Player;
import eg.edu.guc.dragonball.model.world.World;

public class Game {
	private Player player;
	private World world;
	private ArrayList<NonPlayableFighter> normalFighters = new ArrayList<>();
	private ArrayList<NonPlayableFighter> strongFighters = new ArrayList<>();
	private ArrayList<Attack> attacks = new ArrayList<>();
	private ArrayList<Attack> dragonAttacks = new ArrayList<>();

	public Game() {
		player = new Player();
		world = new World(this);

		loadData(".");
	}

	public NonPlayableFighter getRandomNormalFighter() {
		int i = (int) (Math.random() * normalFighters.size());
		return normalFighters.get(i);
	}

	public NonPlayableFighter getRandomStrongFighter() {
		int i = (int) (Math.random() * strongFighters.size());
		return strongFighters.get(i);
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
		loadFighters(normalFighters, dataDir + File.separator + "Database-Normal-Fighters.csv");
		loadFighters(strongFighters, dataDir + File.separator + "Database-Strong-Fighters.csv");
		loadAttacks(attacks, dataDir + File.separator + "Database-Attacks.csv");
		loadAttacks(dragonAttacks, dataDir + File.separator + "Database-Dragon-Attacks.csv");
	}

	private void loadFighters(ArrayList<NonPlayableFighter> fighters, String filePath) {
		String[][] lines = loadCSV(filePath);
	}

	private void loadAttacks(ArrayList<Attack> attacks, String filePath) {
		String[][] lines = loadCSV(filePath);
	}

	public static void main(String[] args) {
		Game game = new Game();
		System.out.println(game.getWorld());
	}
}
