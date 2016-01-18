package eg.edu.guc.dragonball.model.cell;

public class CollectibleCell extends Cell {
	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	@Override
	public void onStep() {
	}

	@Override
	public String toString() {
		return "[" + Character.toLowerCase(collectible.name().charAt(0)) + "]";
	}
}
