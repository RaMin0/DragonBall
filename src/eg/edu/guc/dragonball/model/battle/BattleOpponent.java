package eg.edu.guc.dragonball.model.battle;

public interface BattleOpponent {
	void onTurn();

	void onMyTurn();

	void onFoeTurn();

	void onWin(BattleOpponent foe);
}
