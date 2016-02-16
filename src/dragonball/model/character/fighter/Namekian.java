package dragonball.model.character.fighter;

import java.util.ArrayList;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;

public class Namekian extends PlayableFighter {
	public Namekian(String name) {
		super(name, 1350, 0, 50, 3, 5);
	}

	public Namekian(String name, int level, int xp, int targetXp, int maxHealthPoints, int blastDamage,
			int physicalDamage, int abilityPoints, int maxKi, int maxStamina, ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks) {
		super(name, level, xp, targetXp, maxHealthPoints, blastDamage, physicalDamage, abilityPoints, maxKi, maxStamina,
				superAttacks, ultimateAttacks);
	}

	@Override
	public void onMyTurn() {
		super.onMyTurn();
		setHealthPoints(getHealthPoints() + 50);
	}

	@Override
	public void onFoeTurn() {
		super.onFoeTurn();
		setHealthPoints(getHealthPoints() + 50);
	}
}
