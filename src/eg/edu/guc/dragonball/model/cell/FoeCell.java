package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.battle.Battle;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.player.Player;

public class FoeCell extends Cell {
	private NonPlayableFighter foe;

	public FoeCell(NonPlayableFighter foe) {
		this.foe = foe;
	}

	public NonPlayableFighter getFighter() {
		return foe;
	}

	@Override
	public void onStep(Player player) {
		Battle battle = new Battle(player.getActiveFighter(), foe);
		battle.start();
	}

	@Override
	public String toString() {
		return "[" + (getFighter().isStrong() ? 'b' : 'w') + "]";
	}
}
