package eg.edu.guc.dragonball.model.player;

import java.util.ArrayList;

import eg.edu.guc.dragonball.model.character.PlayableCharacter;

public class Player {
	private ArrayList<PlayableCharacter> characters = new ArrayList<>();
	private PlayableCharacter activeCharacter;
	private int senzuBeans;
	private int dragonBalls;
	private int exploredMaps;

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

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(int senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public int getDragonBalls() {
		return dragonBalls;
	}

	public void setDragonBalls(int dragonBalls) {
		this.dragonBalls = dragonBalls;

		// TODO: Dragon mode
	}

	public int getExploredMaps() {
		return exploredMaps;
	}

	public void setExploredMaps(int exploredMaps) {
		this.exploredMaps = exploredMaps;
	}
}
