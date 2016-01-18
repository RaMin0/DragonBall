package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;

public class FoeCell extends Cell {
	private NonPlayableFighter foe;

	public FoeCell(NonPlayableFighter foe) {
		this.foe = foe;
	}

	public boolean hasStrongFoe() {
		return foe.isStrong();
	}

	@Override
	public void onStep() {
	}

	@Override
	public String toString() {
		return "[" + (foe.isStrong() ? 'b' : 'w') + "]";
	}
}
