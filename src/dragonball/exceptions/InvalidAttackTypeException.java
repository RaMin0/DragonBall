package dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidAttackTypeException extends DragonBallException {
	public InvalidAttackTypeException(String attackType) {
		super("Invalid attack type: " + attackType + ".");
	}
}
