package eg.edu.guc.dragonball.model.world;

import eg.edu.guc.dragonball.exceptions.DragonBallInvalidMoveException;
import eg.edu.guc.dragonball.model.cell.Cell;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.cell.CollectibleCell;
import eg.edu.guc.dragonball.model.cell.EmptyCell;
import eg.edu.guc.dragonball.model.cell.FoeCell;
import eg.edu.guc.dragonball.model.game.Game;

public class World {
	public static final int MAP_SIZE = 10;
	public static final int NUM_WEAK_FOES = 15;
	public static final int NUM_SENZU_BEANS = 5;
	public static final int NUM_DRAGON_BALLS = 1;

	private Game game;
	private Cell[][] map;
	private int playerRow;
	private int playerColumn;

	public World(Game game) {
		this.game = game;
		map = new Cell[MAP_SIZE][MAP_SIZE];
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public int getPlayerColumn() {
		return playerColumn;
	}

	public void generateWorld() {
		map[0][0] = new FoeCell(game.getRandomStrongFoe());
		map[MAP_SIZE - 1][MAP_SIZE - 1] = new EmptyCell();

		for (int i = NUM_WEAK_FOES; i > 0;) {
			int row = (int) (Math.random() * MAP_SIZE);
			int column = (int) (Math.random() * MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new FoeCell(game.getRandomWeakFoe());
				i--;
			}
		}

		for (int i = NUM_SENZU_BEANS; i > 0;) {
			int row = (int) (Math.random() * MAP_SIZE);
			int column = (int) (Math.random() * MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new CollectibleCell(Collectible.SENZU_BEAN);
				i--;
			}
		}

		for (int i = NUM_DRAGON_BALLS; i > 0;) {
			int row = (int) (Math.random() * MAP_SIZE);
			int column = (int) (Math.random() * MAP_SIZE);

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
