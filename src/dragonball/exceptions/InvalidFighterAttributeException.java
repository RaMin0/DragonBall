package dragonball.exceptions;

@SuppressWarnings("serial")
public class InvalidFighterAttributeException extends DragonBallException {
	public InvalidFighterAttributeException(char fighterAttribute) {
		super("Invalid fighter attribute: " + fighterAttribute + ".");
	}
}
