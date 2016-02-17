package dragonball.model.game;

import java.util.EventListener;

import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.cell.Collectible;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;

public interface GameListener extends EventListener {
	void onDragonCalled(Dragon dragon);

	void onDragonWishGranted(DragonWish wish);

	void onCollectibleFound(Collectible collectible);

	void onBattle(Battle battle);

	void onBattleEvent(BattleEvent e);
}
