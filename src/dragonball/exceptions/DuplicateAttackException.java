package dragonball.exceptions;

import dragonball.model.attack.Attack;

@SuppressWarnings("serial")
public class DuplicateAttackException extends DragonBallException {
	public DuplicateAttackException(Attack attack) {
		super("Attack already learned: " + attack.getName() + ".");
	}
}
