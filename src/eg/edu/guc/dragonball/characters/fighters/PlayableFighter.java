package eg.edu.guc.dragonball.characters.fighters;

import eg.edu.guc.dragonball.characters.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements
		PlayableCharacter {
	public PlayableFighter(int healthPoints, int blastDamage,
			int physicalDamage, int ki, int stamina) {
		setHealthPoints(healthPoints);
		setBlastDamage(blastDamage);
		setPhysicalDamage(physicalDamage);
		setKi(ki);
		setStamina(stamina);
	}

	@Override
	public void onWin(NonPlayableFighter fighter) {
		// TODO: Gain foe's skills
		if (fighter.isStrong()) {
			// TODO: Unlock new map
		}
	}
}
