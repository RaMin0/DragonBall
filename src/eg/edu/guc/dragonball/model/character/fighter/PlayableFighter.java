package eg.edu.guc.dragonball.model.character.fighter;

import java.util.ArrayList;

import eg.edu.guc.dragonball.exceptions.DuplicateAttackException;
import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.exceptions.MaximumAttacksLearnedException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.attack.SuperAttack;
import eg.edu.guc.dragonball.model.attack.UltimateAttack;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements PlayableCharacter {
	public static final int INITIAL_LEVEL = 1;
	public static final int INITIAL_XP = 0;
	public static final int INITIAL_TARGET_XP = 10;
	public static final int INITIAL_ABILITY_POINTS = 0;

	private int xp;
	private int targetXp;
	private int abilityPoints;
	private ArrayList<SuperAttack> learnableSuperAttacks;
	private ArrayList<UltimateAttack> learnableUltimateAttacks;

	public PlayableFighter(String name, int maxHealthPoints, int blastDamage, int physicalDamage, int maxKi,
			int maxStamina) {
		this(name, INITIAL_LEVEL, INITIAL_XP, INITIAL_TARGET_XP, maxHealthPoints, blastDamage, physicalDamage,
				INITIAL_ABILITY_POINTS,
				maxKi, maxStamina);
	}

	public PlayableFighter(String name, int level, int xp, int targetXp, int maxHealthPoints, int blastDamage,
			int physicalDamage, int abilityPoints, int maxKi, int maxStamina) {
		super(name, level, maxHealthPoints, blastDamage, physicalDamage, maxKi, maxStamina);
		this.xp = xp;
		this.targetXp = targetXp;
		this.abilityPoints = abilityPoints;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;

		while (this.xp >= this.targetXp) {
			this.xp -= this.targetXp;
			this.targetXp += 20;
			setLevel(getLevel() + 1);
			abilityPoints += 2;
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

	public ArrayList<SuperAttack> getLearnableSuperAttacks() {
		return learnableSuperAttacks;
	}

	public void setLearnableSuperAttacks(ArrayList<SuperAttack> learnableSuperAttacks) {
		this.learnableSuperAttacks = learnableSuperAttacks;
	}

	public void addLearnableSuperAttack(SuperAttack superAttack) {
		if (!learnableSuperAttacks.contains(superAttack)) {
			learnableSuperAttacks.add(superAttack);
		}
	}

	public ArrayList<UltimateAttack> getLearnableUltimateAttacks() {
		return learnableUltimateAttacks;
	}

	public void setLearnableUltimateAttacks(ArrayList<UltimateAttack> learnableUltimateAttacks) {
		this.learnableUltimateAttacks = learnableUltimateAttacks;
	}

	public void addLearnableUltimateAttack(UltimateAttack ultimateAttack) {
		if (!learnableUltimateAttacks.contains(ultimateAttack)) {
			learnableUltimateAttacks.add(ultimateAttack);
		}
	}

	public void learn(Attack newAttack, Attack oldAttack)
			throws InvalidAttackException, DuplicateAttackException, MaximumAttacksLearnedException {
		if (newAttack instanceof SuperAttack) {
			learn(getSuperAttacks(), newAttack, oldAttack, MAX_SUPER_ATTACKS);
		} else if (newAttack instanceof UltimateAttack) {
			learn(getUltimateAttacks(), newAttack, oldAttack, MAX_ULTIMATE_ATTACKS);
		} else {
			throw new InvalidAttackException(
					"You can only learn super and ultimate attacks: " + newAttack.getClass().getSimpleName() + ".");
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void learn(ArrayList<T> attacks, Attack newAttack, Attack oldAttack, int max)
			throws DuplicateAttackException, MaximumAttacksLearnedException {
		if (attacks.contains(newAttack)) {
			throw new DuplicateAttackException(newAttack);
		} else if (attacks.size() == max && oldAttack == null) {
			throw new MaximumAttacksLearnedException(max);
		} else {
			int index = attacks.size();
			if (oldAttack != null) {
				index = attacks.indexOf(oldAttack);
				attacks.remove(oldAttack);
			}
			attacks.add(index, (T) newAttack);
		}
	}

	@Override
	public void onWin(BattleOpponent foe) {
		NonPlayableFighter foeFighter = (NonPlayableFighter) foe;

		setXp(xp + foeFighter.getLevel() * 5);
		
		for (SuperAttack superAttack : foeFighter.getSuperAttacks()) {
			addLearnableSuperAttack(superAttack);
		}
		for (UltimateAttack ultimateAttack : foeFighter.getUltimateAttacks()) {
			addLearnableUltimateAttack(ultimateAttack);
		}

		if (foeFighter.isStrong()) {
			// TODO: Unlock new map
		}
	}

	public enum Attribute {
		HEALTH_POINTS, BLAST_DAMAGE, PHYSICAL_DAMAGE, KI, STAMINA
	}
}
