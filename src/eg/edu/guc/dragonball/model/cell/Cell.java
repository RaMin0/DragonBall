package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.player.Player;

public abstract class Cell {
	public abstract void onStep(Player player);

	@Override
	public abstract String toString();
}