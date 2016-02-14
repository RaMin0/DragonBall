package dragonball.exceptions;

@SuppressWarnings("serial")
public class NotEnoughAbilityPointsException extends DragonBallException {
	public NotEnoughAbilityPointsException() {
		super("Not enough ability points.");
	}
}
