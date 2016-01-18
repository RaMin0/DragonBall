package eg.edu.guc.dragonball.model.character.fighter;

import java.util.ArrayList;

import eg.edu.guc.dragonball.exceptions.DuplicateAttackException;
import eg.edu.guc.dragonball.exceptions.MaximumAttacksLearnedException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.Character;

public abstract class Fighter extends Character implements BattleOpponent {

	private int level;
	private int blastDamage;
	private int physicalDamage;
	private int healthPoints;
	private int maxHealthPoints;
	private int ki;
	private int maxKi;
	private int stamina;
	private int maxStamina;
	private ArrayList<Attack> attacks;

	public Fighter(String name, int level, int blastDamage, int physicalDamage, int maxHealthPoints, int maxKi,
			int maxStamina) {
		super(name);
		this.level = level;
		this.blastDamage = blastDamage;
		this.physicalDamage = physicalDamage;
		this.maxHealthPoints = maxHealthPoints;
		this.maxKi = maxKi;
		this.maxStamina = maxStamina;
		this.attacks = new ArrayList<>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBlastDamage() {
		return blastDamage;
	}

	public void setBlastDamage(int blastDamage) {
		this.blastDamage = blastDamage;
	}

	public int getPhysicalDamage() {
		return physicalDamage;
	}

	public void setPhysicalDamage(int physicalDamage) {
		this.physicalDamage = physicalDamage;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;

		if (this.healthPoints < 0) {
			this.healthPoints = 0;
		} else if (this.healthPoints > this.maxHealthPoints) {
			this.healthPoints = this.maxHealthPoints;
		}
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(int maxHealthPoints) {
		this.maxHealthPoints = maxHealthPoints;
	}

	public int getKi() {
		return ki;
	}

	public void setKi(int ki) {
		this.ki = ki;

		if (this.ki < 0) {
			this.ki = 0;
		} else if (this.ki > this.maxKi) {
			this.ki = this.maxKi;
		}
	}

	public int getMaxKi() {
		return maxKi;
	}

	public void setMaxKi(int maxKi) {
		this.maxKi = maxKi;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;

		if (this.stamina < 0) {
			this.stamina = 0;
		} else if (this.stamina > this.maxStamina) {
			this.stamina = this.maxStamina;
		}
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}

	@Override
	public void onTurn() {
	}

	@Override
	public void onMyTurn() {
		onTurn();
	}

	@Override
	public void onFoeTurn() {
		onTurn();
	}
}
