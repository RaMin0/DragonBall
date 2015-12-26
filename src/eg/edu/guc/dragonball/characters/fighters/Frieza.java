package eg.edu.guc.dragonball.characters.fighters;

public class Frieza extends PlayableFighter {
	public Frieza() {
		super(1100, 75, 75, 4, 4);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
	}
}
