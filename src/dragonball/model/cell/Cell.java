package dragonball.model.cell;

import java.util.HashSet;
import java.util.Set;

import dragonball.model.character.fighter.NonPlayableFighter;

public abstract class Cell {
	private Set<CellListener> listeners = new HashSet<>();

	public abstract void onStep();

	public void addListener(CellListener listener) {
		listeners.add(listener);
	}

	protected void notifyListenersOnFoeEncountered(NonPlayableFighter foe) {
		for (CellListener listener : listeners) {
			listener.onFoeEncountered(foe);
		}
	}

	protected void notifyListenersOnCollectibleFound(Collectible collectible) {
		for (CellListener listener : listeners) {
			listener.onCollectibleFound(collectible);
		}
	}

	@Override
	public abstract String toString();
}
