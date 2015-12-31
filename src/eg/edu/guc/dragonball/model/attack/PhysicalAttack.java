package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super(50);
	}

	@Override
	public void onUse(PlayableCharacter me, NonPlayableCharacter foe) {
		PlayableFighter meFighter = (PlayableFighter) me;
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		int damage = getDamage() + meFighter.getPhysicalDamage();
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
