package eg.edu.guc.dragonball.model.character.fighter;

public class Frieza extends PlayableFighter {
	public Frieza(String name) {
		super(name, 75, 75, 1100, 4, 4);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
	}
}
