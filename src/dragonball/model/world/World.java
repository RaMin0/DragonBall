package dragonball.model.world;

import java.util.ArrayList;
import java.util.Random;

import dragonball.exceptions.InvalidMoveException;
import dragonball.model.cell.Cell;
import dragonball.model.cell.CellListener;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.EmptyCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;

public class World implements CellListener {
	public static final int MAP_SIZE = 10;
	public static final int NUM_WEAK_FOES = 15;
	public static final int NUM_MIN_SENZU_BEANS = 3;
	public static final int NUM_MAX_SENZU_BEANS = 5;
	public static final int NUM_DRAGON_BALLS = 1;

	private Cell[][] map;
	private int playerRow;
	private int playerColumn;
	private WorldListener listener;

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
		clearMap();

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

		for (int i = NUM_MIN_SENZU_BEANS
				+ new Random().nextInt(NUM_MAX_SENZU_BEANS - NUM_MIN_SENZU_BEANS + 1); i > 0;) {
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

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].setListener(this);
			}
		}

		resetPlayerPosition();
	}

	private void clearMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = null;
			}
		}
	}

	public void moveUp() throws InvalidMoveException {
		moveTo(playerRow - 1, playerColumn);
	}

	public void moveDown() throws InvalidMoveException {
		moveTo(playerRow + 1, playerColumn);
	}

	public void moveLeft() throws InvalidMoveException {
		moveTo(playerRow, playerColumn - 1);
	}

	public void moveRight() throws InvalidMoveException {
		moveTo(playerRow, playerColumn + 1);
	}

	private void moveTo(int row, int column) throws InvalidMoveException {
		if (row >= 0 && row < MAP_SIZE
				&& column >= 0 && column < MAP_SIZE) {
			Cell cell = map[row][column];
			map[row][column] = new EmptyCell();
			playerRow = row;
			playerColumn = column;
			cell.onStep();
		} else {
			throw new InvalidMoveException(row, column);
		}
	}

	public void resetPlayerPosition() {
		try {
			moveTo(MAP_SIZE - 1, MAP_SIZE - 1);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFoeEncountered(NonPlayableFighter foe) {
		notifyListenersOnFoeEncountered(foe);
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		notifyListenersOnCollectibleFound(collectible);
	}

	public void setListener(WorldListener listener) {
		this.listener = listener;
	}

	public void notifyListenersOnFoeEncountered(NonPlayableFighter foe) {
		if (listener != null) {
			listener.onFoeEncountered(foe);
		}
	}

	public void notifyListenersOnCollectibleFound(Collectible collectible) {
		if (listener != null) {
			listener.onCollectibleFound(collectible);
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
