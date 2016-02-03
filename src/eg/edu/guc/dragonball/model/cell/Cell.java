package eg.edu.guc.dragonball.model.cell;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;

public abstract class Cell {
	private Set<Listener> listeners = new HashSet<>();

	public abstract void onStep();

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	protected void notifyListenersOnFoe(NonPlayableFighter foe) {
		for (Listener listener : listeners) {
			listener.onFoe(foe);
		}
	}

	protected void notifyListenersOnCollectible(Collectible collectible) {
		for (Listener listener : listeners) {
			listener.onCollectible(collectible);
		}
	}

	@Override
	public abstract String toString();

	public interface Listener extends EventListener {
		void onFoe(NonPlayableFighter foe);

		void onCollectible(Collectible collectible);
	}
}
