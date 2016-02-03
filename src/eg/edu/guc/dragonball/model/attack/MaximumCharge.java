package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class MaximumCharge extends SuperAttack {
	public MaximumCharge() {
		super("Maximum Charge", 0);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) {
		Fighter meFighter = (Fighter) me;
		meFighter.setKi(meFighter.getKi() + 3);
	}
}
