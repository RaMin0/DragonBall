package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class NotEnoughAbilityPointsException extends DragonBallException {
	public NotEnoughAbilityPointsException(int requiredAbilityPoints, int availableAbilityPoints) {
		super("Not enough ability points (required: " + requiredAbilityPoints + ", available: "
				+ availableAbilityPoints + ").");
	}
}
