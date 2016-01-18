package eg.edu.guc.dragonball.model.battle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.exceptions.NotEnoughCollectiblesException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.battle.BattleEvent.Type;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;
import eg.edu.guc.dragonball.model.player.Player;

public class Battle {
	private BattleOpponent me;
	private BattleOpponent foe;
	private BattleOpponent currentOpponent;

	public Battle(BattleOpponent me, BattleOpponent foe) {
		this.me = me;
		this.foe = foe;
		this.currentOpponent = me;
	}

	public BattleOpponent getCurrentOpponent() {
		return currentOpponent;
	}

	public BattleOpponent getOtherOpponent() {
		return currentOpponent == me ? foe : me;
	}

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
