package dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidFighterTypeException extends DragonBallException {
	public InvalidFighterTypeException(char fighterType) {
		super("Invalid fighter type: " + fighterType + ".");
	}
}
