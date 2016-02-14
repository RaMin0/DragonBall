package dragonball.model.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import dragonball.exceptions.InvalidMoveException;
import dragonball.model.cell.Cell;
import dragonball.model.cell.CellListener;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.EmptyCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;

public class World {
	public static final int MAP_SIZE = 10;
	public static final int NUM_WEAK_FOES = 15;
	public static final int NUM_SENZU_BEANS = 5;
	public static final int NUM_DRAGON_BALLS = 1;

	private Cell[][] map;
	private int playerRow;
	private int playerColumn;
	private Set<WorldListener> listeners = new HashSet<>();

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

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].addListener(new CellListener() {
					@Override
					public void onFoe(NonPlayableFighter foe) {
						notifyListenersOnCellFoe(foe);
					}

					@Override
					public void onCollectible(Collectible collectible) {
						notifyListenersOnCellCollectible(collectible);
					}
				});
			}
		}

		resetPlayerPosition();
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

	public void moveTo(int row, int column) throws InvalidMoveException {
		if (row >= 0 && row < MAP_SIZE
				&& column >= 0 && column < MAP_SIZE) {
			Cell cell = map[row][column];
			cell.onStep();
			map[row][column] = new EmptyCell();
			playerRow = row;
			playerColumn = column;
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

	public void addListener(WorldListener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnCellFoe(NonPlayableFighter foe) {
		for (WorldListener listener : listeners) {
			listener.onFoeEncountered(foe);
		}
	}

	public void notifyListenersOnCellCollectible(Collectible collectible) {
		for (WorldListener listener : listeners) {
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
