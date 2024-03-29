package dragonball.model.cell;

import dragonball.model.character.fighter.NonPlayableFighter;

public abstract class Cell {
	private CellListener listener;

	public abstract void onStep();

	public void setListener(CellListener listener) {
		this.listener = listener;
	}

	protected void notifyOnFoeEncountered(NonPlayableFighter foe) {
		if (listener != null) {
			listener.onFoeEncountered(foe);
		}
	}

	protected void notifyOnCollectibleFound(Collectible collectible) {
		if (listener != null) {
			listener.onCollectibleFound(collectible);
		}
	}

	@Override
	public abstract String toString();
}
