package eg.edu.guc.dragonball.characters.fighters;

import eg.edu.guc.dragonball.characters.NonPlayableCharacter;

public abstract class NonPlayableFighter extends Fighter implements
		NonPlayableCharacter {
	private boolean strong;

	public static NonPlayableFighter getRandomFighter() {
		int fighterType = (int) (Math.random() * 2);
		return new NonPlayableFighter[] {
				// TODO: Add instances of NonPlayableFighters
				new Ramy(), new Sherif() }[fighterType];
	}

	public boolean isStrong() {
		return strong;
	}

	public void setStrong(boolean strong) {
		this.strong = strong;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().substring(0, 1);
	}
}
