package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super("Physical Attack", 50);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me) {
		return getDamage() + ((Fighter) me).getPhysicalDamage();
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		super.onUse(me, foe, foeBlocking);

		// increment ki by 1
		Fighter meFighter = (Fighter) me;
		meFighter.setKi(meFighter.getKi() + 1);
	}
}
