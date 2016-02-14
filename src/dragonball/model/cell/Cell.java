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

	protected void notifyListenersOnFoe(NonPlayableFighter foe) {
		for (CellListener listener : listeners) {
			listener.onFoe(foe);
		}
	}

	protected void notifyListenersOnCollectible(Collectible collectible) {
		for (CellListener listener : listeners) {
			listener.onCollectible(collectible);
		}
	}

	@Override
	public abstract String toString();
}
