package eg.edu.guc.dragonball.views;

import eg.edu.guc.dragonball.characters.fighters.NonPlayableFighter;

public class FighterCell extends Cell {
	private NonPlayableFighter fighter;

	public FighterCell(NonPlayableFighter fighter) {
		this.fighter = fighter;
	}

	public NonPlayableFighter getFighter() {
		return fighter;
	}

	@Override
	public String toString() {
		return "F(" + getFighter() + ")";
	}
}
