package eg.edu.guc.dragonball.model.battle;

import java.util.ArrayList;
import java.util.EventListener;
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
	private Set<Listener> listeners = new HashSet<>();
	private boolean meBlocking;
	private boolean foeBlocking;

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

	private void switchTurn() {
		currentOpponent = getOtherOpponent();
	}

	private void endTurn() {
		if (currentOpponent == me && foeBlocking) {
			foeBlocking = false;
		} else if (currentOpponent == foe && meBlocking) {
			meBlocking = false;
		}

		if (((Fighter) me).getHealthPoints() == 0) {
			foe.onWin(me);

			notifyListeners(new BattleEvent(this, Type.BATTLE_ENDED, foe));
		} else if (((Fighter) foe).getHealthPoints() == 0) {
			me.onWin(foe);

			notifyListeners(new BattleEvent(this, Type.BATTLE_ENDED, me));
		} else {
			getCurrentOpponent().onMyTurn();
			getOtherOpponent().onFoeTurn();

			switchTurn();

			notifyListeners(new BattleEvent(this, currentOpponent == me ? Type.ME_TURN : Type.FOE_TURN));
		}
	}

	public void start() {
		Fighter meFighter = (Fighter) me;
		meFighter.setHealthPoints(meFighter.getMaxHealthPoints());
		meFighter.setKi(0);
		meFighter.setStamina(meFighter.getMaxStamina());

		Fighter foeFighter = (Fighter) foe;
		foeFighter.setHealthPoints(foeFighter.getMaxHealthPoints());
		foeFighter.setKi(0);
		foeFighter.setStamina(foeFighter.getMaxStamina());

		notifyListeners(new BattleEvent(this, Type.BATTLE_STARTED));
		notifyListeners(new BattleEvent(this, Type.ME_TURN));
	}

	public void play() {
		int randomAction = new Random().nextInt(2);
		switch (randomAction) {
		case 0:
			ArrayList<Attack> attacks = ((Fighter) currentOpponent).getAttacks();
			Attack randomAttack = attacks.get(new Random().nextInt(attacks.size()));
			do {
				try {
					attack(randomAttack);
					break;
				} catch (InvalidAttackException e) {

				}
			} while (true);
			break;
		case 1:
			block();
			break;
		}
	}

	public void attack(Attack attack) throws InvalidAttackException {
		attack.onUse(currentOpponent, getOtherOpponent(),
				(currentOpponent == me && foeBlocking) || (currentOpponent == foe && meBlocking));

		notifyListeners(new BattleEvent(this, Type.ATTACK, attack));

		endTurn();
	}

	public void block() {
		if (currentOpponent == me) {
			meBlocking = true;
		} else if (currentOpponent == foe) {
			foeBlocking = true;
		}

		notifyListeners(new BattleEvent(this, Type.BLOCK));

		endTurn();
	}

	public void use(Player player, Collectible collectible) throws NotEnoughCollectiblesException {
		switch (collectible) {
		case SENZU_BEAN:
			if (player.getSenzuBeans() > 0) {
				PlayableFighter activeFighter = player.getActiveFighter();
				activeFighter.setHealthPoints(activeFighter.getMaxHealthPoints());
				activeFighter.setStamina(activeFighter.getMaxStamina());

				player.setSenzuBeans(player.getSenzuBeans() - 1);

				notifyListeners(new BattleEvent(this, Type.USE, collectible));
			} else {
				throw new NotEnoughCollectiblesException(Collectible.SENZU_BEAN);
			}
			break;
		default:
			break;
		}

		endTurn();
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void notifyListeners(BattleEvent e) {
		for (Listener listener : listeners) {
			listener.onEvent(e);
		}
	}

	public interface Listener extends EventListener {
		void onEvent(BattleEvent e);
	}
}
