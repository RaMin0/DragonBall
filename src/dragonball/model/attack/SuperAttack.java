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
	public int getAppliedDamage(BattleOpponent me) {
		return getDamage() + ((Fighter) me).getBlastDamage();
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter meFighter = (Fighter) me;

		// only decrement ki by 1 if fighter is not a transformed saiyan
		if (!(me instanceof Saiyan && ((Saiyan) me).isTransformed())) {
			if (meFighter.getKi() >= 1) {
				meFighter.setKi(meFighter.getKi() - 1);
			} else {
				throw new NotEnoughKiException(1, meFighter.getKi());
			}
		}

		super.onUse(me, foe, foeBlocking);
	}
}
