package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class DragonBallInvalidMoveException extends DragonBallException {
	public DragonBallInvalidMoveException(int row, int column) {
		super("Invalid move to (row: " + row + ", col: " + column);
	}
}
