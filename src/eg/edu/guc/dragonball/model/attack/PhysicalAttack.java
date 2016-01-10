package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class PhysicalAttack extends Attack {
	public PhysicalAttack() {
		super("Physical Attack", 50);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe) {
		PlayableFighter meFighter = (PlayableFighter) me;
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		int damage = getDamage() + meFighter.getPhysicalDamage();
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
