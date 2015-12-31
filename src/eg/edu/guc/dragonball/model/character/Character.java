package eg.edu.guc.dragonball.model.character;

public abstract class Character {
	private String name;

	public Character(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
