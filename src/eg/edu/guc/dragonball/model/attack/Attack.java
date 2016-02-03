package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Fighter;
import eg.edu.guc.dragonball.model.character.fighter.Saiyan;

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

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public abstract int getAppliedDamage(BattleOpponent me, BattleOpponent foe);

	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		Fighter foeFighter = (Fighter) foe;

		int damage = getAppliedDamage(me, foe);

		if (me instanceof Saiyan && ((Saiyan) me).isTransformed()) {
			damage = (int) (damage * 1.25);
		}

		if (foeBlocking) {
			while (damage > 0 && foeFighter.getStamina() > 0) {
				foeFighter.setStamina(foeFighter.getStamina() - 1);
				damage -= damage >= 100 ? 100 : damage;
			}
		}
		foeFighter.setHealthPoints(foeFighter.getHealthPoints() - damage);
	}
}
