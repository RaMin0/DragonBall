package dragonball.model.attack;

import dragonball.exceptions.InvalidAttackException;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public abstract class Attack {
	private String name;
	private int damage;

	public Attack(String name, int damage) {
		this.name = name;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public abstract int getAppliedDamage(BattleOpponent me);

	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter foeFighter = (Fighter) foe;

		// get the applied damage of the attack, taking into consideration
		// the fighter's attributes
		int damage = getAppliedDamage(me);

		// if the fighter is a transformed saiyan, increase damage by 25%
		if (me instanceof Saiyan && ((Saiyan) me).isTransformed()) {
			damage = (int) (damage * 1.25);
		}

		// if opponent is in block mode, consume stamina first before applying
		// remaining damage
		if (foeBlocking) {
			while (damage > 0 && foeFighter.getStamina() > 0) {
				foeFighter.setStamina(foeFighter.getStamina() - 1);
				damage -= damage >= 100 ? 100 : damage;
			}
		}
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
