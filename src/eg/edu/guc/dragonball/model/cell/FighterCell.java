package eg.edu.guc.dragonball.model.cell;

import eg.edu.guc.dragonball.model.battle.Battle;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.player.Player;

public class FighterCell extends Cell {
	private NonPlayableFighter fighter;

	public FighterCell(NonPlayableFighter fighter) {
		this.fighter = fighter;
	}

	public NonPlayableFighter getFighter() {
		return fighter;
	}
	
	@Override
	public void handle(Player player) {
		new Battle(player.getActiveCharacter(), fighter);
	}

	@Override
	public String toString() {
		return "F(" + getFighter() + ")";
	}
}
