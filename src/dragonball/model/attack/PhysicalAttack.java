package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super("Physical Attack", 50);
	}

	@Override
	public int getAppliedDamage(BattleOpponent attacker) {
		return getDamage() + ((Fighter) attacker).getPhysicalDamage();
	}

	@Override
	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking)
			throws InvalidAttackException {
		super.onUse(attacker, defender, defenderBlocking);

		// increment ki by 1
		Fighter attackerFighter = (Fighter) attacker;
		attackerFighter.setKi(attackerFighter.getKi() + 1);
	}
}
