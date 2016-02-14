package dragonball.exceptions;

import dragonball.model.cell.Collectible;

@SuppressWarnings("serial")
public class NotEnoughCollectiblesException extends DragonBallException {
	public NotEnoughCollectiblesException(Collectible collectible) {
		super("Not enough " + collectible + "s.");
	}
}
