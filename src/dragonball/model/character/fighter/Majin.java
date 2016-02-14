package dragonball.model.character.fighter;

public class Majin extends PlayableFighter {
	public Majin(String name) {
		super(name, 1500, 50, 50, 3, 6);
	}

	@Override
	public void onMyTurn() {
		// Override to do nothing
	}
}