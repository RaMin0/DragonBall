package dragonball.model.character.fighter;

public class Namekian extends PlayableFighter {
	public Namekian(String name) {
		super(name, 1350, 0, 50, 3, 5);
	}

	@Override
	public void onMyTurn() {
		super.onMyTurn();
		setHealthPoints(getHealthPoints() + 50);
	}

	@Override
	public void onFoeTurn() {
		super.onFoeTurn();
		setHealthPoints(getHealthPoints() + 50);
	}
}
