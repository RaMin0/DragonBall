package dragonball.model.character.fighter;

import dragonball.model.character.NonPlayableCharacter;

public class NonPlayableFighter extends Fighter implements NonPlayableCharacter {
	private boolean strong;

	public NonPlayableFighter(String name, int level, int maxHealthPoints, int blastDamage, int physicalDamage,
			int maxKi, int maxStamina, boolean strong) {
		super(name, level, maxHealthPoints, blastDamage, physicalDamage, maxKi, maxStamina);
		this.strong = strong;
	}

	public boolean isStrong() {
		return strong;
	}

	public void setStrong(boolean strong) {
		this.strong = strong;
	}
}