package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public abstract class BlastAttack extends Attack {
	public BlastAttack(int damage) {
		super(damage);
	}

	@Override
	public void onUse(PlayableCharacter me, NonPlayableCharacter foe) {
		PlayableFighter meFighter = (PlayableFighter) me;
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		int damage = getDamage() + meFighter.getBlastDamage();
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
