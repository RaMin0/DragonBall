package eg.edu.guc.dragonball.model.character.fighter;

import java.util.ArrayList;

import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.attack.PhysicalAttack;
import eg.edu.guc.dragonball.model.attack.SuperAttack;
import eg.edu.guc.dragonball.model.attack.UltimateAttack;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.Character;

public abstract class Fighter extends Character implements BattleOpponent {
	public static final int MAX_SUPER_ATTACKS = 4;
	public static final int MAX_ULTIMATE_ATTACKS = 2;

	private int level;
	private int healthPoints;
	private int maxHealthPoints;
	private int blastDamage;
	private int physicalDamage;
	private int ki;
	private int maxKi;
	private int stamina;
	private int maxStamina;
	private ArrayList<SuperAttack> superAttacks;
	private ArrayList<UltimateAttack> ultimateAttacks;

	public Fighter(String name, int level, int maxHealthPoints, int blastDamage, int physicalDamage, int maxKi,
			int maxStamina) {
		super(name);
		this.level = level;
		this.maxHealthPoints = maxHealthPoints;
		this.blastDamage = blastDamage;
		this.physicalDamage = physicalDamage;
		this.maxKi = maxKi;
		this.maxStamina = maxStamina;
		superAttacks = new ArrayList<>();
		ultimateAttacks = new ArrayList<>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public ArrayList<SuperAttack> getSuperAttacks() {
		return superAttacks;
	}

	public void setSuperAttacks(ArrayList<SuperAttack> superAttacks) {
		this.superAttacks = superAttacks;
	}

	public ArrayList<UltimateAttack> getUltimateAttacks() {
		return ultimateAttacks;
	}

	public void setUltimateAttacks(ArrayList<UltimateAttack> ultimateAttacks) {
		this.ultimateAttacks = ultimateAttacks;
	}

	public ArrayList<Attack> getAttacks() {
		ArrayList<Attack> attacks = new ArrayList<>();
		attacks.add(new PhysicalAttack());
		attacks.addAll(superAttacks);
		attacks.addAll(ultimateAttacks);
		return attacks;
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
