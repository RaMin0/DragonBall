package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class MaximumCharge extends SuperAttack {
	public MaximumCharge() {
		super("Maximum Charge", 0);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe) {
		PlayableFighter meFighter = (PlayableFighter) me;
		meFighter.setKi(meFighter.getKi() + 2);
	}
}
