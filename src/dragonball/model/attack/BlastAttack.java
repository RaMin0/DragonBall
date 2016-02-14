package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;

public abstract class BlastAttack extends Attack {
	public BlastAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me, BattleOpponent foe) {
		return getDamage() + ((Fighter) me).getBlastDamage();
	}
}
