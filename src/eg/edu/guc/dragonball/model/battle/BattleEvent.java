package eg.edu.guc.dragonball.model.battle;

import java.util.EventObject;

import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.cell.Collectible;

@SuppressWarnings("serial")
public class BattleEvent extends EventObject {
	private Type type;
	private BattleOpponent currentOpponent;
	private BattleOpponent winner;
	private Attack attack;
	private Collectible collectible;

	public BattleEvent(Battle battle, Type type, Object... data) {
		super(battle);
		this.type = type;
		this.currentOpponent = battle.getCurrentOpponent();

		switch (type) {
		case BATTLE_ENDED:
			winner = (BattleOpponent) data[0];
			break;
		case ATTACK:
			attack = (Attack) data[0];
			break;
		case USE:
			collectible = (Collectible) data[0];
			break;
		default:
			break;
		}
	}

	public Type getType() {
		return type;
	}

	public BattleOpponent getCurrentOpponent() {
		return currentOpponent;
	}

	public BattleOpponent getWinner() {
		return winner;
	}

	public Attack getAttack() {
		return attack;
	}

	public Collectible getCollectible() {
		return collectible;
	}

	public enum Type {
		BATTLE_STARTED, BATTLE_ENDED, ME_TURN, FOE_TURN, ATTACK, BLOCK, USE
	}
}