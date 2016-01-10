package eg.edu.guc.dragonball.model.character.fighter;

public class Namekian extends PlayableFighter {
	public Namekian(String name) {
		super(name, 0, 50, 1350, 3, 5);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
		setHealthPoints(getHealthPoints() + 50);
	}
}
