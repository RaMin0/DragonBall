package dragonball.model.cell;

import dragonball.model.character.fighter.NonPlayableFighter;

public class FoeCell extends Cell {
	private NonPlayableFighter foe;

	public FoeCell(NonPlayableFighter foe) {
		this.foe = foe;
	}

	public NonPlayableFighter getFoe() {
		return foe;
	}

	@Override
	public void onStep() {
		notifyListenersOnFoeEncountered(foe);
	}

	@Override
	public String toString() {
		return "[" + (foe.isStrong() ? 'b' : 'w') + "]";
	}
}
