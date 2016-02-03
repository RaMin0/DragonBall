package eg.edu.guc.dragonball.model.world;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eg.edu.guc.dragonball.exceptions.DuplicateAttackException;
import eg.edu.guc.dragonball.exceptions.InvalidAttackException;
import eg.edu.guc.dragonball.exceptions.InvalidMoveException;
import eg.edu.guc.dragonball.exceptions.MaximumAttacksLearnedException;
import eg.edu.guc.dragonball.exceptions.NotEnoughAbilityPointsException;
import eg.edu.guc.dragonball.model.attack.Attack;
import eg.edu.guc.dragonball.model.cell.Cell;
import eg.edu.guc.dragonball.model.cell.Collectible;
import eg.edu.guc.dragonball.model.cell.CollectibleCell;
import eg.edu.guc.dragonball.model.cell.EmptyCell;
import eg.edu.guc.dragonball.model.cell.FoeCell;
import eg.edu.guc.dragonball.model.character.fighter.NonPlayableFighter;
import eg.edu.guc.dragonball.model.character.fighter.PlayableFighter;

public class World {
	public static final int MAP_SIZE = 10;
	public static final int NUM_WEAK_FOES = 15;
	public static final int NUM_SENZU_BEANS = 5;
	public static final int NUM_DRAGON_BALLS = 1;

	private Cell[][] map;
	private int playerRow;
	private int playerColumn;
	private Set<Listener> listeners = new HashSet<>();

	public World() {
		map = new Cell[MAP_SIZE][MAP_SIZE];
	}

	public Cell[][] getMap() {
		return map;
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public int getPlayerColumn() {
		return playerColumn;
	}

	private NonPlayableFighter getRandomFoe(ArrayList<NonPlayableFighter> foes) {
		int i = new Random().nextInt(foes.size());
		return foes.get(i);
	}

	public void generateMap(ArrayList<NonPlayableFighter> weakFoes, ArrayList<NonPlayableFighter> strongFoes) {
		map[0][0] = new FoeCell(getRandomFoe(strongFoes));
		map[MAP_SIZE - 1][MAP_SIZE - 1] = new EmptyCell();

		for (int i = NUM_WEAK_FOES; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new FoeCell(getRandomFoe(weakFoes));
				i--;
			}
		}

		for (int i = NUM_SENZU_BEANS; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new CollectibleCell(Collectible.SENZU_BEAN);
				i--;
			}
		}

		for (int i = NUM_DRAGON_BALLS; i > 0;) {
			int row = new Random().nextInt(MAP_SIZE);
			int column = new Random().nextInt(MAP_SIZE);

			if (map[row][column] == null) {
				map[row][column] = new CollectibleCell(Collectible.DRAGON_BALL);
				i--;
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == null) {
					map[i][j] = new EmptyCell();
				}
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].addListener(new Cell.Listener() {
					@Override
					public void onFoe(NonPlayableFighter foe) {
						notifyListenersOnCellFoe(foe);
					}

					@Override
					public void onCollectible(Collectible collectible) {
						notifyListenersOnCellCollectible(collectible);
					}
				});
			}
		}

		try {
			moveTo(MAP_SIZE - 1, MAP_SIZE - 1);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public void moveTo(int row, int column) throws InvalidMoveException {
		if (row >= 0 && row < MAP_SIZE
				&& column >= 0 && column < MAP_SIZE) {
			Cell cell = map[row][column];
			cell.onStep();
			map[row][column] = new EmptyCell();
			playerRow = row;
			playerColumn = column;
		} else {
			throw new InvalidMoveException(row, column);
		}
	}

	public void upgradeFighter(PlayableFighter fighter, int abilityPoints, PlayableFighter.Attribute fighterAttribute)
			throws NotEnoughAbilityPointsException {
		if (abilityPoints <= fighter.getAbilityPoints()) {
			switch (fighterAttribute) {
			case HEALTH_POINTS:
				fighter.setMaxHealthPoints(fighter.getMaxHealthPoints() + abilityPoints * 50);
				break;
			case BLAST_DAMAGE:
				fighter.setBlastDamage(fighter.getBlastDamage() + abilityPoints * 50);
				break;
			case PHYSICAL_DAMAGE:
				fighter.setPhysicalDamage(fighter.getPhysicalDamage() + abilityPoints * 50);
				break;
			case KI:
				fighter.setMaxKi(fighter.getMaxKi() + abilityPoints);
				break;
			case STAMINA:
				fighter.setMaxStamina(fighter.getMaxStamina() + abilityPoints);
				break;
			}

			fighter.setAbilityPoints(fighter.getAbilityPoints() - abilityPoints);
		} else {
			throw new NotEnoughAbilityPointsException(abilityPoints, fighter.getAbilityPoints());
		}
	}

	public void learnAttack(PlayableFighter fighter, Attack newAttack, Attack oldAttack)
			throws InvalidAttackException, DuplicateAttackException, MaximumAttacksLearnedException {
		fighter.learn(newAttack, oldAttack);
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnCellFoe(NonPlayableFighter foe) {
		for (Listener listener : listeners) {
			listener.onCellFoe(foe);
		}
	}

	public void notifyListenersOnCellCollectible(Collectible collectible) {
		for (Listener listener : listeners) {
			listener.onCellCollectible(collectible);
		}
	}

	@Override
	public String toString() {
		String toString = "";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == playerRow && j == playerColumn) {
					toString += "[x]";
				} else {
					toString += map[i][j];
				}
			}
			toString += "\n";
		}

		return toString.substring(0, toString.length() - 1);
	}

	public interface Listener extends EventListener {
		void onCellFoe(NonPlayableFighter foe);

		void onCellCollectible(Collectible collectible);
	}
}
