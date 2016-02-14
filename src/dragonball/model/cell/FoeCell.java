package dragonball.model.cell;

import dragonball.model.character.fighter.NonPlayableFighter;

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
		notifyListenersOnFoe(foe);
	}

	@Override
	public String toString() {
		return "[" + (foe.isStrong() ? 'b' : 'w') + "]";
	}
}
