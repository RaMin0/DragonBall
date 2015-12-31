package eg.edu.guc.dragonball.model.battle;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public class Battle {
	private PlayableCharacter me;
	private NonPlayableCharacter foe;

	public Battle(PlayableCharacter me, NonPlayableCharacter foe) {
		this.me = me;
		this.foe = foe;
	}
}
