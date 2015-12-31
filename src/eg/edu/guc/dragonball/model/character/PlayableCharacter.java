package eg.edu.guc.dragonball.model.character;

import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;

public interface PlayableCharacter {
	void onWin(NonPlayableFighter fighter);
}
