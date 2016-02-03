package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.exceptions.NotEnoughKiException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class SuperAttack extends BlastAttack {
	public SuperAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter meFighter = (Fighter) me;

		if (meFighter.getKi() >= 1) {
			meFighter.setKi(meFighter.getKi() - 1);
			super.onUse(me, foe, foeBlocking);
		} else {
			throw new NotEnoughKiException(1, meFighter.getKi());
		}
	}
}
