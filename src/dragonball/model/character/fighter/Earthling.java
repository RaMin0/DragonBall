package dragonball.model.character.fighter;

public class Earthling extends PlayableFighter {
	public Earthling(String name) {
		super(name, 1250, 50, 50, 4, 4);
	}

	@Override
	public void onMyTurn() {
		super.onMyTurn();
		setKi(getKi() + 1);
	}
}
