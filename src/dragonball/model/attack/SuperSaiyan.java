package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.NotEnoughKiException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Saiyan;

public class SuperSaiyan extends UltimateAttack {
	public SuperSaiyan() {
		super("Super Saiyan", 0);
	}

	@Override
	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) throws InvalidAttackException {
		if (attacker instanceof Saiyan) {
			Saiyan attackerSaiyan = (Saiyan) attacker;

			// only requires 3 ki, without consuming them (override superclass behavior)
			if (attackerSaiyan.getKi() >= 3) {
				attackerSaiyan.setTransformed(true);
			} else {
				throw new NotEnoughKiException(3, attackerSaiyan.getKi());
			}
		} else {
			throw new InvalidAttackException(
					"Only Saiyans can use the Super Saiyan attack: " + attacker.getClass().getSimpleName() + ".");
		}
	}
}
