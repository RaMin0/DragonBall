package eg.edu.guc.dragonball.model.cell;

public enum Collectible {
	SENZU_BEAN, DRAGON_BALL;

	@Override
	public String toString() {
		switch (this) {
		case SENZU_BEAN:
			return "Senzu Bean";
		case DRAGON_BALL:
			return "Dragon Ball";
		}
		return this.name().replace('_', ' ');
	}
}
