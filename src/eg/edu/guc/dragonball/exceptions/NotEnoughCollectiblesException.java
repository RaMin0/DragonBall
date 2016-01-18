package eg.edu.guc.dragonball.exceptions;

import eg.edu.guc.dragonball.model.cell.Collectible;

@SuppressWarnings("serial")
public class NotEnoughCollectiblesException extends DragonBallException {
	public NotEnoughCollectiblesException(Collectible collectible) {
		super("Not enough " + collectible + "s.");
	}
}
