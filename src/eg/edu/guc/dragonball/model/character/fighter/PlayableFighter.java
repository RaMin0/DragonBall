package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements PlayableCharacter {
	public PlayableFighter(String name, int blastDamage, int physicalDamage, int maxHealthPoints, int maxKi,
			int maxStamina) {
		super(name, blastDamage, physicalDamage, maxHealthPoints, maxKi, maxStamina);
	}

	public PlayableFighter(String name, int level, int xp, int targetXp, int blastDamage, int physicalDamage,
			int abilityPoints, int healthPoints, int maxHealthPoints, int ki, int maxKi, int stamina, int maxStamina) {
		super(name, level, xp, targetXp, blastDamage, physicalDamage, abilityPoints, healthPoints, maxHealthPoints, ki,
				maxKi, stamina, maxStamina);
	}

	@Override
	public void onWin(BattleOpponent foe) {
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		// TODO: Gain foe's skills
		if (foeFighter.isStrong()) {
			// TODO: Unlock new map
		}
	}
}
