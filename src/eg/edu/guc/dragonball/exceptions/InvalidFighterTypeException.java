package eg.edu.guc.dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidFighterTypeException extends DragonBallException {
	public InvalidFighterTypeException(String fighterType) {
		super("Invalid fighter type: " + fighterType + ".");
	}
}
