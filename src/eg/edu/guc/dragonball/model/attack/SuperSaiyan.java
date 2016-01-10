package eg.edu.guc.dragonball.model.attack;

import eg.edu.guc.dragonball.exceptions.DragonBallInvalidAttackException;
import eg.edu.guc.dragonball.model.battle.BattleOpponent;
import eg.edu.guc.dragonball.model.character.fighter.Saiyan;

public class SuperSaiyan extends SuperAttack {
	public SuperSaiyan() {
		super("Super Saiyan", 0);
	}

	@Override
	public void onUse(BattleOpponent me, BattleOpponent foe) throws DragonBallInvalidAttackException {
		if (me instanceof Saiyan) {
			Saiyan meSaiyan = (Saiyan) me;
			meSaiyan.setTransformed(true);
		} else {
			throw new DragonBallInvalidAttackException(
					"Only Saiyans can use the Super Saiyan attack: " + me.getClass().getSimpleName());
		}
	}
}
