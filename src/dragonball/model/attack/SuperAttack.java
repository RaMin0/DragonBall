package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.NotEnoughKiException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public class SuperAttack extends Attack {
	public SuperAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent attacker) {
		return getDamage() + ((Fighter) attacker).getBlastDamage();
	}

	@Override
	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) throws InvalidAttackException {
		Fighter attackerFighter = (Fighter) attacker;

		// only decrement ki by 1 if fighter is not a transformed saiyan
		if (!(attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed())) {
			if (attackerFighter.getKi() >= 1) {
				attackerFighter.setKi(attackerFighter.getKi() - 1);
			} else {
				throw new NotEnoughKiException(1, attackerFighter.getKi());
			}
		}

		super.onUse(attacker, defender, defenderBlocking);
	}
}
