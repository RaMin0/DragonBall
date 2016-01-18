package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super("Physical Attack", 50);
	}

	@Override
	public int getAppliedDamage(BattleOpponent me, BattleOpponent foe) {
		return getDamage() + ((Fighter) me).getPhysicalDamage();
	}
}
