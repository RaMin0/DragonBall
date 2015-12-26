package eg.edu.guc.dragonball;

import java.util.ArrayList;

import eg.edu.guc.dragonball.characters.PlayableCharacter;
import eg.edu.guc.dragonball.collectibles.Collectible;

public class Player {
	private ArrayList<PlayableCharacter> characters;
	private PlayableCharacter activeCharacter;
	private ArrayList<Collectible> collectibles;

	public ArrayList<PlayableCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<PlayableCharacter> characters) {
		this.characters = characters;
	}

	public PlayableCharacter getActiveCharacter() {
		return activeCharacter;
	}

	public void setActiveCharacter(PlayableCharacter activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	public ArrayList<Collectible> getCollectibles() {
		return collectibles;
	}

	public void setCollectibles(ArrayList<Collectible> collectibles) {
		this.collectibles = collectibles;
	}
}
