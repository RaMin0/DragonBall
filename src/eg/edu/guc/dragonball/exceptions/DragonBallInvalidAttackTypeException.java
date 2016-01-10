package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class DragonBallInvalidAttackTypeException extends DragonBallException {
	public DragonBallInvalidAttackTypeException(String attackType) {
		super("Invalid attack type: " + attackType);
	}
}
