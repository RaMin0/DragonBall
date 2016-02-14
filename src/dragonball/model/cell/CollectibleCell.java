package dragonball.model.cell;

public class CollectibleCell extends Cell {
	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	@Override
	public void onStep() {
		notifyListenersOnCollectible(collectible);
	}

	@Override
	public String toString() {
		return "[" + Character.toLowerCase(collectible.name().charAt(0)) + "]";
	}
}
