package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidMoveException extends DragonBallException {
	public InvalidMoveException(int row, int column) {
		super("Invalid move to (row: " + row + ", col: " + column + ").");
	}
}
