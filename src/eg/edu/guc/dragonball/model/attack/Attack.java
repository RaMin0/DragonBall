package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.DragonBallInvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;

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

	public abstract void onUse(BattleOpponent me, BattleOpponent foe) throws DragonBallInvalidAttackException;
}
