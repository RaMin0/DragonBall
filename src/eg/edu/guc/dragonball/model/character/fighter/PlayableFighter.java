package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements
		PlayableCharacter {
	public PlayableFighter(String name, int healthPoints, int blastDamage, int physicalDamage, int ki, int stamina) {
		super(name, healthPoints, blastDamage, physicalDamage, ki, stamina);
	}

	@Override
	public void onWin(NonPlayableFighter fighter) {
		// TODO: Gain foe's skills
		if (fighter.isStrong()) {
			// TODO: Unlock new map
		}
	}
}
