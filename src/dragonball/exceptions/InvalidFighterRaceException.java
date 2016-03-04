package dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidFighterRaceException extends DragonBallException {
	public InvalidFighterRaceException(char fighterType) {
		super("Invalid fighter race: " + fighterType + ".");
	}
}
