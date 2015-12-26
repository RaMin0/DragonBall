package eg.edu.guc.dragonball.characters.fighters;

public class Namekian extends PlayableFighter {
	public Namekian() {
		super(1350, 0, 50, 3, 5);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
		setHealthPoints(getHealthPoints() + 50);
	}
}
