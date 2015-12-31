package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.player.Player;

public class CollectibleCell extends Cell {
	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	public Collectible getCollectible() {
		return collectible;
	}

	@Override
	public void handle(Player player) {
		switch (getCollectible()) {
		case SENZU_BEAN:
			player.setSenzuBeans(player.getSenzuBeans() + 1);
			break;
		case DRAGON_BALL:
			player.setDragonBalls(player.getDragonBalls() + 1);
			break;
		}
	}

	@Override
	public String toString() {
		return "C(" + getCollectible().name().substring(0, 1) + ")";
	}
}
