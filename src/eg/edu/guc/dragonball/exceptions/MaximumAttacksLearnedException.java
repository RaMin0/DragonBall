package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class MaximumAttacksLearnedException extends DragonBallException {
	public MaximumAttacksLearnedException(int numAttacks) {
		super("No more attacks can be learned: Maximum: " + numAttacks + ".");
	}
}
