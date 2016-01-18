package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;

public class NonPlayableFighter extends Fighter implements NonPlayableCharacter {
	private boolean strong;

	public NonPlayableFighter(String name, int level, int blastDamage, int physicalDamage, int maxHealthPoints,
			int maxKi, int maxStamina, boolean strong) {
		super(name, level, blastDamage, physicalDamage, maxHealthPoints, maxKi, maxStamina);
		this.strong = strong;
	}

	public boolean isStrong() {
		return strong;
	}

	public void setStrong(boolean strong) {
		this.strong = strong;
	}

	@Override
	public void onWin(BattleOpponent foe) {

	}
}
