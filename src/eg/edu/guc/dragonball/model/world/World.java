package eg.edu.guc.dragonball.model.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eg.edu.guc.dragonball.exceptions.DuplicateAttackException;
import eg.edu.guc.dragonball.exceptions.InsufficientAbilityPointsException;
import eg.edu.guc.dragonball.exceptions.InvalidMoveException;
import eg.edu.guc.dragonball.exceptions.MaximumAttacksLearnedException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.cell.Cell;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.cell.CollectibleCell;
import eg.edu.guc.dragonball.model.cell.EmptyCell;
import eg.edu.guc.dragonball.model.cell.FoeCell;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class World {
	public static final int MAP_SIZE = 10;
	public static final int NUM_WEAK_FOES = 15;
	public static final int NUM_SENZU_BEANS = 5;
	public static final int NUM_DRAGON_BALLS = 1;

	private Cell[][] map;
	private int playerRow;
	private int playerColumn;

	public World() {
		map = new Cell[MAP_SIZE][MAP_SIZE];
	}

	public Cell[][] getMap() {
		return map;
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public int getPlayerColumn() {
		return playerColumn;
	}

	private NonPlayableFighter getRandomFoe(ArrayList<NonPlayableFighter> foes) {
		int i = new Random().nextInt(foes.size());
		return foes.get(i);
	}

	public void generateMap(ArrayList<NonPlayableFighter> weakFoes, ArrayList<NonPlayableFighter> strongFoes) {
		map[0][0] = new FoeCell(getRandomFoe(strongFoes));
		map[MAP_SIZE - 1][MAP_SIZE - 1] = new EmptyCell();

		for (int i = NUM_WEAK_FOES; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new FoeCell(getRandomFoe(weakFoes));
				i--;
			}
		}

		for (int i = NUM_SENZU_BEANS; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new CollectibleCell(Collectible.SENZU_BEAN);
				i--;
			}
		}

		for (int i = NUM_DRAGON_BALLS; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new CollectibleCell(Collectible.DRAGON_BALL);
				i--;
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == null) {
					map[i][j] = new EmptyCell();
				}
			}
		}

		try {
			moveTo(MAP_SIZE - 1, MAP_SIZE - 1);
		} catch (DragonBallInvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public void moveTo(int row, int column) throws DragonBallInvalidMoveException {
		if (row >= 0 && row < MAP_SIZE
				&& column >= 0 && column < MAP_SIZE) {
			Cell cell = map[row][column];
			cell.onStep(game.getPlayer());
			playerRow = row;
			playerColumn = column;
		} else {
			throw new DragonBallInvalidMoveException(row, column);
		}
	}

	@Override
	public String toString() {
		String toString = "";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == playerRow && j == playerColumn) {
					toString += "[x]";
				} else {
					toString += map[i][j];
				}
			}
			toString += "\n";
		}

		return toString.substring(0, toString.length() - 1);
	}
}
