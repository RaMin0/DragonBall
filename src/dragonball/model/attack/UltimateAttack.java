package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.NotEnoughKiException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public class UltimateAttack extends Attack {
	public UltimateAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me) {
		return getDamage() + ((Fighter) me).getBlastDamage();
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter meFighter = (Fighter) me;

		// only decrement ki by 3 if fighter is not a transformed saiyan
		if (!(me instanceof Saiyan && ((Saiyan) me).isTransformed())) {
			if (meFighter.getKi() >= 3) {
				meFighter.setKi(meFighter.getKi() - 3);
			} else {
				throw new NotEnoughKiException(3, meFighter.getKi());
			}
		}

		super.onUse(me, foe, foeBlocking);
	}
}
