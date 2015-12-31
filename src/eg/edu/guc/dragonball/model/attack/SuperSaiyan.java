package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.model.character.NonPlayableCharacter;
import eg.edu.guc.dragonball.model.character.PlayableCharacter;
import eg.edu.guc.dragonball.model.character.fighter.Saiyan;

public class SuperSaiyan extends SuperAttack {
	public SuperSaiyan() {
		super(0);
	}

	@Override
	public void onUse(PlayableCharacter me, NonPlayableCharacter foe) {
		Saiyan meSaiyan = (Saiyan) me;
		meSaiyan.setTransformed(true);
	}
}
