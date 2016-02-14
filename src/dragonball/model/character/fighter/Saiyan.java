package dragonball.model.character.fighter;

public class Saiyan extends PlayableFighter {
	private boolean transformed;

	public Saiyan(String name) {
		super(name, 1000, 150, 100, 5, 3);
	}

	public Saiyan(String name, boolean transformed) {
		this(name);
		this.transformed = transformed;
	}

	public boolean isTransformed() {
		return transformed;
	}

	public void setTransformed(boolean transformed) {
		this.transformed = transformed;
	}

	@Override
	public void onMyTurn() {
		super.onMyTurn();

		if (transformed) {
			setKi(getKi() - 1);
			
			if (getKi() == 0) {
				setStamina(0);
				transformed = false;
			}
		}
	}

	@Override
	public void onFoeTurn() {
		super.onFoeTurn();

		if (transformed) {
			setKi(getKi() - 1);
			
			if (getKi() == 0) {
				setStamina(0);
				transformed = false;
			}
		}
	}
}
