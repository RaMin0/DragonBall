package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.Character;

public abstract class Fighter extends Character implements BattleOpponent {
	public static final int INITIAL_LEVEL = 1;
	public static final int INITIAL_XP = 0;
	public static final int INITIAL_TARGET_XP = 10;
	public static final int INITIAL_ABILITY_POINTS = 0;
	public static final int INITIAL_HEALTH_POINTS = 0;
	public static final int INITIAL_KI = 0;
	public static final int INITIAL_STAMINA = 0;

	private int level;
	private int xp;
	private int targetXp;
	private int blastDamage;
	private int physicalDamage;
	private int abilityPoints;
	private int healthPoints;
	private int maxHealthPoints;
	private int ki;
	private int maxKi;
	private int stamina;
	private int maxStamina;

	public Fighter(String name, int blastDamage, int physicalDamage, int maxHealthPoints, int maxKi,
			int maxStamina) {
		this(name, INITIAL_LEVEL, INITIAL_XP, INITIAL_TARGET_XP, blastDamage, physicalDamage, INITIAL_ABILITY_POINTS,
				INITIAL_HEALTH_POINTS, maxHealthPoints, INITIAL_KI, maxKi, INITIAL_STAMINA, maxStamina);
	}

	public Fighter(String name, int level, int xp, int targetXp, int blastDamage, int physicalDamage, int abilityPoints,
			int healthPoints, int maxHealthPoints, int ki, int maxKi, int stamina, int maxStamina) {
		super(name);
		this.level = level;
		this.xp = xp;
		this.targetXp = targetXp;
		this.blastDamage = blastDamage;
		this.physicalDamage = physicalDamage;
		this.abilityPoints = abilityPoints;
		this.healthPoints = healthPoints;
		this.maxHealthPoints = maxHealthPoints;
		this.ki = ki;
		this.maxKi = maxKi;
		this.stamina = stamina;
		this.maxStamina = maxStamina;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;

		if (this.xp > this.targetXp) {
			this.xp -= this.targetXp;
			this.targetXp *= 2;
			this.level++;
		}
	}

	public int getTargetXp() {
		return targetXp;
	}

	public void setTargetXp(int targetXp) {
		this.targetXp = targetXp;
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

	public int getAbilityPoints() {
		return abilityPoints;
	}

	public void setAbilityPoints(int abilityPoints) {
		this.abilityPoints = abilityPoints;
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
