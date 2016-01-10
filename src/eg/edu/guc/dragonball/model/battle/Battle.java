package eg.edu.guc.dragonball.model.battle;

import eg.edu.guc.dragonball.model.character.fighter.Fighter;

public class Battle {
	private BattleOpponent me;
	private BattleOpponent foe;

	public Battle(BattleOpponent me, BattleOpponent foe) {
		this.me = me;
		this.foe = foe;

		Fighter meFighter = (Fighter) me;
		meFighter.setHealthPoints(meFighter.getMaxHealthPoints());
		meFighter.setStamina(meFighter.getMaxStamina());
		meFighter.setKi(0);
		
		Fighter foeFighter = (Fighter) foe;
		foeFighter.setHealthPoints(foeFighter.getMaxHealthPoints());
		foeFighter.setStamina(foeFighter.getMaxStamina());
		foeFighter.setKi(0);
	}
}
