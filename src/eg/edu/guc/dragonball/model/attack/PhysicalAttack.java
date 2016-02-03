package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super("Physical Attack", 50);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me, BattleOpponent foe) {
		return getDamage() + ((Fighter) me).getPhysicalDamage();
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		super.onUse(me, foe, foeBlocking);

		Fighter meFighter = (Fighter) me;
		meFighter.setKi(meFighter.getKi() + 1);
	}
}
