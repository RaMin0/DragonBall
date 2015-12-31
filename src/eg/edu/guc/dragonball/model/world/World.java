package eg.edu.guc.dragonball.model.world;

import eg.edu.guc.dragonball.controller.Game;
import eg.edu.guc.dragonball.exceptions.DragonBallInvalidMoveException;
import eg.edu.guc.dragonball.model.cell.Cell;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.cell.CollectibleCell;
import eg.edu.guc.dragonball.model.cell.EmptyCell;
import eg.edu.guc.dragonball.model.cell.FighterCell;

public class World {
	public static final int MAP_SIZE = 10;
	public static final int GRID_NORMAL_FIGHTERS = 4;
	public static final int GRID_STRONG_FIGHTERS = 1;
	public static final int GRID_SENZU_BEANS = 3;
	public static final int GRID_DRAGON_BALLS = 7;

	public static final int CELL_TYPE_NORMAL_FIGHTER = 0;
	public static final int CELL_TYPE_STRONG_FIGHTER = 1;
	public static final int CELL_TYPE_SENZU_BEAN = 2;
	public static final int CELL_TYPE_DRAGON_BALL = 3;

	private Game game;
	private Cell[][] map;
	private int playerRow;
	private int playerColumn;

	public World(Game game) {
		this.game = game;
		map = new Cell[MAP_SIZE][MAP_SIZE];
		playerRow = MAP_SIZE / 2;
		playerColumn = MAP_SIZE / 2;

		generateMap();
	}

	private void generateMap() {
		int fighters = GRID_NORMAL_FIGHTERS;
		int senzuBeans = GRID_SENZU_BEANS;
		int dragonBalls = GRID_DRAGON_BALLS;

		// TODO: Enhance to make sure everything is included
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new EmptyCell();

				int cellType = (int) (Math.random() * 10);
				switch (cellType) {
				case CELL_TYPE_NORMAL_FIGHTER:
					if (fighters > 0) {
						map[i][j] = new FighterCell(
								game.getRandomNormalFighter());
						fighters--;
					}
					break;
				case CELL_TYPE_STRONG_FIGHTER:
					if (fighters > 0) {
						map[i][j] = new FighterCell(
								game.getRandomStrongFighter());
						fighters--;
					}
					break;
				case CELL_TYPE_SENZU_BEAN:
					if (senzuBeans > 0) {
						map[i][j] = new CollectibleCell(Collectible.SENZU_BEAN);
						senzuBeans--;
					}
					break;
				case CELL_TYPE_DRAGON_BALL:
					if (dragonBalls > 0) {
						map[i][j] = new CollectibleCell(Collectible.DRAGON_BALL);
						dragonBalls--;
					}
					break;
				}
			}
		}
	}

	public void moveTo(int row, int column) throws DragonBallInvalidMoveException {
		if (row >= 0 && row < MAP_SIZE
				&& column >= 0 && column < MAP_SIZE) {
			Cell cell = map[row][column];
			cell.handle(game.getPlayer());
		} else {
			throw new DragonBallInvalidMoveException(row, column);
		}
	}

	@Override
	public String toString() {
		String toString = "";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				toString += map[i][j] + "    ";
			}
			toString += "\n\n";
		}

		return toString;
	}
}
