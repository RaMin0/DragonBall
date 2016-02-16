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
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		if (me instanceof Saiyan) {
			Saiyan meSaiyan = (Saiyan) me;

			// only requires 3 ki, without consuming them (override superclass behavior)
			if (meSaiyan.getKi() >= 3) {
				meSaiyan.setTransformed(true);
			} else {
				throw new NotEnoughKiException(3, meSaiyan.getKi());
			}
		} else {
			throw new InvalidAttackException(
					"Only Saiyans can use the Super Saiyan attack: " + me.getClass().getSimpleName() + ".");
		}
	}
}
