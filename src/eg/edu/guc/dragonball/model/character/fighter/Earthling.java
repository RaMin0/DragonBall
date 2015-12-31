package eg.edu.guc.dragonball.model.character.fighter;

public class Earthling extends PlayableFighter {
	public Earthling() {
		super("Earthling", 1250, 50, 50, 4, 4);
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
	}

	@Override
	public void onMyTurn() {
		super.onMyTurn();
		setKi(getKi() + 1);
	}
}
