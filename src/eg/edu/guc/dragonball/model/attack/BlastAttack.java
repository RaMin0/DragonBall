package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.DragonBallInvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public abstract class BlastAttack extends Attack {
	public BlastAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe) throws DragonBallInvalidAttackException {
		PlayableFighter meFighter = (PlayableFighter) me;
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		int damage = getDamage() + meFighter.getBlastDamage();
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
