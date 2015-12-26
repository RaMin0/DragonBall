package eg.edu.guc.dragonball;

import eg.edu.guc.dragonball.views.World;

public class Game {
	private World world;

	public Game() {
		this.world = new World();
	}

	public World getWorld() {
		return world;
	}

	public static void main(String[] args) {
		Game game = new Game();
		System.out.println(game.getWorld());
	}
}
