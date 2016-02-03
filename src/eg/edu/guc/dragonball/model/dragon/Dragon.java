package eg.edu.guc.dragonball.model.dragon;

import java.util.ArrayList;
import java.util.Random;

import eg.edu.guc.dragonball.model.attack.SuperAttack;
import eg.edu.guc.dragonball.model.attack.UltimateAttack;
import eg.edu.guc.dragonball.model.player.Player;

public class Dragon {
	private String name;
	private ArrayList<SuperAttack> superAttacks;
	private ArrayList<UltimateAttack> ultimateAttacks;
	private int senzuBeans;
	private int abilityPoints;

	public Dragon(String name, int senzuBeans, int abilityPoints) {
		this.name = name;
		this.senzuBeans = senzuBeans;
		this.abilityPoints = abilityPoints;
		this.superAttacks = new ArrayList<>();
		this.ultimateAttacks = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<SuperAttack> getSuperAttacks() {
		return superAttacks;
	}

	public ArrayList<UltimateAttack> getUltimateAttacks() {
		return ultimateAttacks;
	}

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public int getAbilityPoints() {
		return abilityPoints;
	}

	public void grantWish(Player player) {
		player.setSenzuBeans(player.getSenzuBeans() + senzuBeans);
		player.getActiveFighter().setAbilityPoints(player.getActiveFighter().getAbilityPoints() + abilityPoints);
		player.getSuperAttacks().add(superAttacks.get(new Random().nextInt(superAttacks.size())));
		player.getUltimateAttacks().add(ultimateAttacks.get(new Random().nextInt(ultimateAttacks.size())));
	}
}
