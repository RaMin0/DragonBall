package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.Character;

public abstract class Fighter extends Character implements BattleOpponent {
	private int level;
	private int xp;
	private int targetXp;
	private int abilityPoints;
	private int healthPoints;
	private int blastDamage;
	private int physicalDamage;
	private int ki;
	private int stamina;

	public Fighter(String name, int healthPoints, int blastDamage, int physicalDamage, int ki, int stamina) {
		this(name, 1, 0, 10, 0, healthPoints, blastDamage, physicalDamage, ki, stamina);
	}

	public Fighter(String name, int level, int xp, int targetXp, int abilityPoints, int healthPoints, int blastDamage,
			int physicalDamage, int ki, int stamina) {
		super(name);
		this.level = level;
		this.xp = xp;
		this.targetXp = targetXp;
		this.abilityPoints = abilityPoints;
		this.healthPoints = healthPoints;
		this.blastDamage = blastDamage;
		this.physicalDamage = physicalDamage;
		this.ki = ki;
		this.stamina = stamina;
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
	}

	public int getTargetXp() {
		return targetXp;
	}

	public void setTargetXp(int targetXp) {
		this.targetXp = targetXp;
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
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
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
