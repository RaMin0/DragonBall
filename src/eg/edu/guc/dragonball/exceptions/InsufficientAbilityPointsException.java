package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class InsufficientAbilityPointsException extends DragonBallException {
	public InsufficientAbilityPointsException(int availableAbilityPoints, int requiredAbilityPoints) {
		super("Insufficient ability points (required: " + requiredAbilityPoints + ", available: "
				+ availableAbilityPoints + ").");
	}
}
