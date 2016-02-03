package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.exceptions.NotEnoughKiException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class UltimateAttack extends BlastAttack {
	public UltimateAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter meFighter = (Fighter) me;

		if (meFighter.getKi() >= 3) {
			meFighter.setKi(meFighter.getKi() - 3);
			super.onUse(me, foe, foeBlocking);
		} else {
			throw new NotEnoughKiException(3, meFighter.getKi());
		}
	}
}
