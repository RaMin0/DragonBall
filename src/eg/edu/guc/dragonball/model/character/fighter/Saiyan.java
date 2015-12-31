package eg.edu.guc.dragonball.model.character.fighter;

public class Saiyan extends PlayableFighter {
	private boolean transformed;

	public Saiyan() {
		super("Saiyan", 1000, 150, 100, 5, 3);
	}

	public boolean isTransformed() {
		return transformed;
	}

	public void setTransformed(boolean transformed) {
		this.transformed = transformed;
	}

	@Override
	public void onTurn() {
		setStamina(getStamina() + 1);
		if (isTransformed()) {
			setKi(getKi() + 1);
		}
	}
}
