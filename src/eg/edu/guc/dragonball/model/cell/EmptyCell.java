package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.player.Player;

public class EmptyCell extends Cell {
	@Override
	public void onStep(Player player) {

	}

	@Override
	public String toString() {
		return "[ ]";
	}
}
