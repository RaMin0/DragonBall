package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;

public class NonPlayableFighter extends Fighter implements
		NonPlayableCharacter {
	private boolean strong;

	public NonPlayableFighter(String name, int healthPoints, int blastDamage, int physicalDamage, int ki, int stamina,
			boolean strong) {
		super(name, healthPoints, blastDamage, physicalDamage, ki, stamina);
		this.strong = strong;
	}

	public boolean isStrong() {
		return strong;
	}

	public void setStrong(boolean strong) {
		this.strong = strong;
	}

	@Override
	public String toString() {
		return getName().substring(0, 1);
	}
}
