package dragonball.model.battle;

import java.util.EventListener;

public interface BattleListener extends EventListener {
	void onEvent(BattleEvent e);
}
