package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public abstract class Attack {
	private int damage;

	public Attack() {

	}

	public Attack(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public abstract void onUse(PlayableCharacter me, NonPlayableCharacter foe);
}
