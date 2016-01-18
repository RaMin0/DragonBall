package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public abstract class BlastAttack extends Attack {
	public BlastAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me, BattleOpponent foe) {
		return getDamage() + ((Fighter) me).getBlastDamage();
	}
}
