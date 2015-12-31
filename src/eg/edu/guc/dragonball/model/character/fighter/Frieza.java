package eg.edu.guc.dragonball.model.character.fighter;

public class Frieza extends PlayableFighter {
	public Frieza() {
		super("Frieza", 1100, 75, 75, 4, 4);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
	}
}
