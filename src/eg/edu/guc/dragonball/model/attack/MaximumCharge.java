package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class MaximumCharge extends SuperAttack {
	public MaximumCharge() {
		super(0);
	}

	@Override
	public void onUse(PlayableCharacter me, NonPlayableCharacter foe) {
		PlayableFighter meFighter = (PlayableFighter) me;
		meFighter.setKi(meFighter.getKi() + 2);
	}
}
