package dragonball.exceptions;

@SuppressWarnings("serial")
public class NotEnoughKiException extends InvalidAttackException {
	public NotEnoughKiException(int requiredKi, int availableKi) {
		super("Not enough ki (required: " + requiredKi + ", available: " + availableKi + ").");
	}
}
