package eg.edu.guc.dragonball.model.character.fighter;

public class Saiyan extends PlayableFighter {
	private boolean transformed;

	public Saiyan(String name) {
		super(name, 150, 100, 1000, 5, 3);
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
	public void onTurn() {
		setStamina(getStamina() + 1);
		if (isTransformed()) {
			setKi(getKi() + 1);
		}
	}
}
