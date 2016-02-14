package dragonball.model.cell;

import java.util.EventListener;

import dragonball.model.character.fighter.NonPlayableFighter;

public interface CellListener extends EventListener {
	void onFoe(NonPlayableFighter foe);

	void onCollectible(Collectible collectible);
}
