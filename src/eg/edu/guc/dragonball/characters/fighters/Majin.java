package eg.edu.guc.dragonball.characters.fighters;

public class Majin extends PlayableFighter {
	public Majin() {
		super(1500, 50, 50, 3, 6);
	}

	@Override
	public void onMyTurn() {
		setStamina(getStamina() + 1);
	}
}
