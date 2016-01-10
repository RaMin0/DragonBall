package eg.edu.guc.dragonball.model.character.fighter;

public class Majin extends PlayableFighter {
	public Majin(String name) {
		super(name, 50, 50, 1500, 3, 6);
	}

	@Override
	public void onMyTurn() {
		setStamina(getStamina() + 1);
	}
}
