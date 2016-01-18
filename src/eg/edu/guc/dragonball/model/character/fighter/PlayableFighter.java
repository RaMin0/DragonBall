package eg.edu.guc.dragonball.model.character.fighter;

import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements PlayableCharacter {
	public static final int INITIAL_LEVEL = 0;
	public static final int INITIAL_XP = 0;
	public static final int INITIAL_TARGET_XP = 10;
	public static final int INITIAL_ABILITY_POINTS = 0;

	private int xp;
	private int targetXp;
	private int abilityPoints;

	public PlayableFighter(String name, int blastDamage, int physicalDamage, int maxHealthPoints, int maxKi,
			int maxStamina) {
		this(name, INITIAL_LEVEL, INITIAL_XP, INITIAL_TARGET_XP, blastDamage, physicalDamage, INITIAL_ABILITY_POINTS,
				maxHealthPoints, maxKi, maxStamina);
	}

	public PlayableFighter(String name, int level, int xp, int targetXp, int blastDamage, int physicalDamage,
			int abilityPoints, int maxHealthPoints, int maxKi, int maxStamina) {
		super(name, level, blastDamage, physicalDamage, maxHealthPoints, maxKi, maxStamina);
		this.xp = xp;
		this.targetXp = targetXp;
		this.abilityPoints = abilityPoints;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;

		if (this.xp > this.targetXp) {
			this.xp -= this.targetXp;
			this.targetXp *= 2;
			setLevel(getLevel() + 1);
		}
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

	@Override
	public void onWin(BattleOpponent foe) {
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		// TODO: Gain foe's skills
		if (foeFighter.isStrong()) {
			// TODO: Unlock new map
		}
	}
}
