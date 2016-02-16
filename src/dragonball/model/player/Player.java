package dragonball.model.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dragonball.exceptions.DuplicateAttackException;
import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.InvalidFighterException;
import dragonball.exceptions.InvalidFighterTypeException;
import dragonball.exceptions.MaximumAttacksLearnedException;
import dragonball.exceptions.NotEnoughAbilityPointsException;
import dragonball.exceptions.NotEnoughCollectiblesException;
import dragonball.model.attack.Attack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.DragonWish;

public class Player {
	public static final int NUM_DRAGON_BALLS = 7;

	private String name;
	private ArrayList<PlayableFighter> fighters;
	private ArrayList<SuperAttack> superAttacks;
	private ArrayList<UltimateAttack> ultimateAttacks;
	private int senzuBeans;
	private int dragonBalls;
	private PlayableFighter activeFighter;
	private int exploredMaps;
	private Set<PlayerListener> listeners = new HashSet<>();

	public Player(String name) {
		this(name, new ArrayList<PlayableFighter>(), new ArrayList<SuperAttack>(), new ArrayList<UltimateAttack>(), 0,
				0, null, 0);
	}

	public Player(String name, ArrayList<PlayableFighter> fighters, ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks, int senzuBeans, int dragonBalls,
			PlayableFighter activeFighter, int exploredMaps) {
		this.name = name;
		this.fighters = fighters;
		this.superAttacks = superAttacks;
		this.ultimateAttacks = ultimateAttacks;
		this.senzuBeans = senzuBeans;
		this.dragonBalls = dragonBalls;
		this.activeFighter = activeFighter;
		this.exploredMaps = exploredMaps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PlayableFighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<PlayableFighter> fighters) {
		this.fighters = fighters;
	}

	public ArrayList<SuperAttack> getSuperAttacks() {
		return superAttacks;
	}

	public void setSuperAttacks(ArrayList<SuperAttack> superAttacks) {
		this.superAttacks = superAttacks;
	}

	public ArrayList<UltimateAttack> getUltimateAttacks() {
		return ultimateAttacks;
	}

	public void setUltimateAttacks(ArrayList<UltimateAttack> ultimateAttacks) {
		this.ultimateAttacks = ultimateAttacks;
	}

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(int senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public int getDragonBalls() {
		return dragonBalls;
	}

	public void setDragonBalls(int dragonBalls) {
		this.dragonBalls = dragonBalls;
	}

	public PlayableFighter getActiveFighter() {
		// if active fighter is not yet set, set the first fighter to be the active one
		if (activeFighter == null && fighters.size() > 0) {
			activeFighter = fighters.get(0);
		}
		return activeFighter;
	}

	public void setActiveFighter(PlayableFighter activeFighter) throws InvalidFighterException {
		if (fighters.contains(activeFighter)) {
			this.activeFighter = activeFighter;
		} else {
			throw new InvalidFighterException(activeFighter.getName() + " is not yours");
		}
	}

	public int getExploredMaps() {
		return exploredMaps;
	}

	public void setExploredMaps(int exploredMaps) {
		this.exploredMaps = exploredMaps;
	}

	// used to decide which map range to use
	public int getMaxFighterLevel() {
		int maxLevel = PlayableFighter.INITIAL_LEVEL;
		for (PlayableFighter fighter : fighters) {
			if (fighter.getLevel() > maxLevel) {
				maxLevel = fighter.getLevel();
			}
		}
		return maxLevel;
	}

	public PlayableFighter createFighter(String fighterType, String fighterName)
			throws InvalidFighterTypeException {
		Class<? extends PlayableFighter> fighterClass;

		if (fighterType.equalsIgnoreCase("E")) {
			fighterClass = Earthling.class;
		} else if (fighterType.equalsIgnoreCase("S")) {
			fighterClass = Saiyan.class;
		} else if (fighterType.equalsIgnoreCase("N")) {
			fighterClass = Namekian.class;
		} else if (fighterType.equalsIgnoreCase("F")) {
			fighterClass = Frieza.class;
		} else if (fighterType.equalsIgnoreCase("M")) {
			fighterClass = Majin.class;
		} else {
			throw new InvalidFighterTypeException(fighterType);
		}

		return createFighter(fighterClass, fighterName);
	}

	private PlayableFighter createFighter(Class<? extends PlayableFighter> fighterClass,
			String fighterName) {
		PlayableFighter fighter = null;
		try {
			fighter = (PlayableFighter) fighterClass.getConstructors()[0].newInstance(fighterName);
			fighters.add(fighter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fighter;
	}

	public void upgradeFighter(PlayableFighter fighter, String fighterAttribute)
			throws NotEnoughAbilityPointsException, InvalidFighterException {
		if (!fighters.contains(fighter)) {
			throw new InvalidFighterException(fighter.getName() + " is not yours");
		}

		if (fighter.getAbilityPoints() > 0) {
			if (fighterAttribute.equals("H")) {
				fighter.setMaxHealthPoints(fighter.getMaxHealthPoints() + 50);
			} else if (fighterAttribute.equals("B")) {
				fighter.setBlastDamage(fighter.getBlastDamage() + 50);
			} else if (fighterAttribute.equals("P")) {
				fighter.setPhysicalDamage(fighter.getPhysicalDamage() + 50);
			} else if (fighterAttribute.equals("K")) {
				fighter.setMaxKi(fighter.getMaxKi() + 1);
			} else if (fighterAttribute.equals("S")) {
				fighter.setMaxStamina(fighter.getMaxStamina() + 1);
			}

			fighter.setAbilityPoints(fighter.getAbilityPoints() - 1);
		} else {
			throw new NotEnoughAbilityPointsException();
		}
	}

	public void assignAttackToFighter(PlayableFighter fighter, Attack newAttack, Attack oldAttack)
			throws InvalidAttackException, DuplicateAttackException, MaximumAttacksLearnedException {
		if (newAttack instanceof SuperAttack) {
			assignAttackToFighter(fighter, getSuperAttacks(), newAttack, oldAttack, PlayableFighter.MAX_SUPER_ATTACKS);
		} else if (newAttack instanceof UltimateAttack) {
			assignAttackToFighter(fighter, getUltimateAttacks(), newAttack, oldAttack,
					PlayableFighter.MAX_ULTIMATE_ATTACKS);
		} else {
			throw new InvalidAttackException(
					"You can only learn super and ultimate attacks: " + newAttack.getClass().getSimpleName() + ".");
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void assignAttackToFighter(PlayableFighter fighter, ArrayList<T> attacks, Attack newAttack,
			Attack oldAttack, int max) throws DuplicateAttackException, MaximumAttacksLearnedException {
		// if attack already assigned
		if (attacks.contains(newAttack)) {
			throw new DuplicateAttackException(newAttack);
		// if attack list is full and new attack won't replace an assigned one
		} else if (attacks.size() == max && oldAttack == null) {
			throw new MaximumAttacksLearnedException(max);
		// otherwise
		} else {
			int index = attacks.size();
			// if replacing an already assigned attack
			if (oldAttack != null) {
				index = attacks.indexOf(oldAttack);
				attacks.remove(oldAttack);
			}
			attacks.add(index, (T) newAttack);
		}
	}

	public void callDragon() throws NotEnoughCollectiblesException {
		if (dragonBalls >= NUM_DRAGON_BALLS) {
			dragonBalls -= NUM_DRAGON_BALLS;

			notifyListenersOnDragonCalled();
		} else {
			throw new NotEnoughCollectiblesException(Collectible.DRAGON_BALL);
		}
	}

	public void chooseWish(DragonWish wish) {
		switch (wish.getType()) {
		case SENZU_BEANS:
			senzuBeans += wish.getSenzuBeans();
			break;
		case ABILITY_POINTS:
			activeFighter.setAbilityPoints(activeFighter.getAbilityPoints() + wish.getAbilityPoints());
			break;
		case SUPER_ATTACK:
			superAttacks.add(wish.getSuperAttack());
			break;
		case ULTIMATE_ATTACK:
			ultimateAttacks.add(wish.getUltimateAttack());
			break;
		default:
			break;
		}

		notifyListenersOnWishChosen(wish);
	}

	public void addListener(PlayerListener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnDragonCalled() {
		for (PlayerListener listener : listeners) {
			listener.onDragonCalled();
		}
	}

	public void notifyListenersOnWishChosen(DragonWish wish) {
		for (PlayerListener listener : listeners) {
			listener.onWishChosen(wish);
		}
	}
}
