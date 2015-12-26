package eg.edu.guc.dragonball.views;

import eg.edu.guc.dragonball.characters.fighters.NonPlayableFighter;
import eg.edu.guc.dragonball.collectibles.DragonBall;
import eg.edu.guc.dragonball.collectibles.SenzoBean;

public class World {
	public static final int GRID_SIZE = 10;
	public static final int GRID_FIGHTERS = 4;
	public static final int GRID_SENZO_BEANS = 3;
	public static final int GRID_DRAGON_BALLS = 7;

	public static final int CELL_TYPE_FIGHTER = 0;
	public static final int CELL_TYPE_SENZO_BEAN = 1;
	public static final int CELL_TYPE_DRAGON_BALL = 2;

	private Cell[][] grid;
	private int playerRow;
	private int playerColumn;

	public World() {
		grid = new Cell[GRID_SIZE][GRID_SIZE];
		playerRow = GRID_SIZE / 2;
		playerColumn = GRID_SIZE / 2;

		int fighters = GRID_FIGHTERS;
		int senzoBeans = GRID_SENZO_BEANS;
		int dragonBalls = GRID_DRAGON_BALLS;

		// TODO: Enhance to make sure everything is included
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new EmptyCell();

				int cellType = (int) (Math.random() * 10);
				switch (cellType) {
				case CELL_TYPE_FIGHTER:
					if (fighters > 0) {
						grid[i][j] = new FighterCell(
								NonPlayableFighter.getRandomFighter());
						fighters--;
					}
					break;
				case CELL_TYPE_SENZO_BEAN:
					if (senzoBeans > 0) {
						grid[i][j] = new CollectibleCell(new SenzoBean());
						senzoBeans--;
					}
					break;
				case CELL_TYPE_DRAGON_BALL:
					if (dragonBalls > 0) {
						grid[i][j] = new CollectibleCell(new DragonBall());
						dragonBalls--;
					}
					break;
				}
			}
		}
	}

	@Override
	public String toString() {
		String toString = "";

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				toString += grid[i][j] + "    ";
			}
			toString += "\n\n";
		}

		return toString;
	}
}
