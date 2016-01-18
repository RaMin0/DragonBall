package eg.edu.guc.dragonball.exceptions;

import eg.edu.guc.dragonball.model.attack.Attack;

@SuppressWarnings("serial")
public class DuplicateAttackException extends DragonBallException {
	public DuplicateAttackException(Attack attack) {
		super("Attack already learned: " + attack.getName() + ".");
	}
}
