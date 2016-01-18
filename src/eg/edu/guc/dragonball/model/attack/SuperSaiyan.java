package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Saiyan;

public class SuperSaiyan extends SuperAttack {
	public SuperSaiyan() {
		super("Super Saiyan", 0);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe, boolean foeBlocking) throws InvalidAttackException {
		if (me instanceof Saiyan) {
			Saiyan meSaiyan = (Saiyan) me;
			meSaiyan.setTransformed(true);
		} else {
			throw new InvalidAttackException(
					"Only Saiyans can use the Super Saiyan attack: " + me.getClass().getSimpleName() + ".");
		}
	}
}
