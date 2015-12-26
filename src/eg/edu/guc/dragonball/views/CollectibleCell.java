package eg.edu.guc.dragonball.views;

import eg.edu.guc.dragonball.collectibles.Collectible;

public class CollectibleCell extends Cell {
	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	public Collectible getCollectible() {
		return collectible;
	}

	@Override
	public String toString() {
		return "C(" + getCollectible() + ")";
	}
}
