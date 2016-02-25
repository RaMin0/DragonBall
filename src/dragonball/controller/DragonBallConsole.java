package dragonball.controller;

import java.util.ArrayList;
import java.util.Scanner;

import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.InvalidFighterException;
import dragonball.exceptions.InvalidFighterTypeException;
import dragonball.exceptions.InvalidMoveException;
import dragonball.exceptions.NotEnoughCollectiblesException;
import dragonball.model.attack.Attack;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;

public class DragonBallConsole implements GameListener {
	private Game game;
	String[] fighterTypes;
	private Scanner in;

	public DragonBallConsole() {
		game = new Game();
		game.setListener(this);
		fighterTypes = new String[] { "Earthling", "Frieza", "Majin", "Namekian", "Saiyan" };

		in = new Scanner(System.in);

		System.out.print("Hey, there ... Umm, What's your name?\n> ");
		game.getPlayer().setName(in.next());
		System.out.println("Ok, " + game.getPlayer().getName() + ", nice meeting you! Before you start your journey, "
				+ "you have to create your first fighter.");

		menuWorldCreateFighter();
	}

	private void printMap() {
		System.out.println(game.getWorld());
		System.out.println("What do you want to do?");
		System.out.println("- w: Move up");
		System.out.println("- s: Move down");
		System.out.println("- a: Move left");
		System.out.println("- d: Move right");
		System.out.println("- m: Show menu");
		System.out.print("> ");
		char choice = in.next().charAt(0);

		try {
			switch (choice) {
			case 'w':
				game.getWorld().moveUp();
				break;
			case 's':
				game.getWorld().moveDown();
				break;
			case 'a':
				game.getWorld().moveLeft();
				break;
			case 'd':
				game.getWorld().moveRight();
				break;
			case 'm':
				menuWorld();
			}
		} catch (InvalidMoveException e) {
			System.err.println(e.getMessage());
		}

		printMap();
	}

	private void printFighters(PlayableFighter me, NonPlayableFighter foe) {
		System.out.println("Me:");
		System.out.println("- Name           : " + me.getName());
		System.out.println("- Level          : " + me.getLevel());
		System.out.println("- HealthPoints   : " + me.getHealthPoints() + "/" + me.getMaxHealthPoints());
		System.out.println("- Ki             : " + me.getKi() + "/" + me.getMaxKi());
		System.out.println("- Stamina        : " + me.getStamina() + "/" + me.getMaxStamina());

		System.out.println("Foe:");
		System.out.println("- Name           : " + foe.getName());
		System.out.println("- Level          : " + foe.getLevel());
		System.out.println("- Boss           : " + foe.isStrong());
		System.out.println("- HealthPoints   : " + foe.getHealthPoints() + "/" + foe.getMaxHealthPoints());
		System.out.println("- Ki             : " + foe.getKi() + "/" + foe.getMaxKi());
		System.out.println("- Stamina        : " + foe.getStamina() + "/" + foe.getMaxStamina());
	}

	private void menuWorld() {
		System.out.println("What do you want to do?");
		System.out.println("1. Create Fighter");
		System.out.println("2. Active Fighter");
//		System.out.println("3. Upgrade Fighter");
//		System.out.println("4. Assign Attack");
//		System.out.println("5. Load/Save");
		System.out.println("6. Exit");
		System.out.print("> ");
		switch (in.nextInt()) {
		case 1:
			menuWorldCreateFighter();
			break;
		case 2:
			menuWorldActiveFighter();
			break;
		case 6:
			System.out.println("Good-bye, " + game.getPlayer().getName() + "!");
			System.exit(0);
		}
	}

	private void menuWorldCreateFighter() {
		System.out.println("Choose a race:");
		for (String fighterType : fighterTypes) {
			System.out.println("- " + fighterType);
		}
		System.out.print("> ");
		String fighterType = in.next();
		System.out.print("Name your new " + fighterType + " fighter:\n> ");
		try {
			PlayableFighter fighter = game.getPlayer().createFighter(fighterType.charAt(0), in.next());
			System.out.println(fighter.getName() + " is here!");
		} catch (InvalidFighterTypeException e) {
			System.err.println(e.getMessage() + ".");
		}

		printMap();
	}

	private void menuWorldActiveFighter() {
		System.out.println("Choose a fighter:");
		ArrayList<PlayableFighter> fighters = game.getPlayer().getFighters();
		for (int i = 0; i < fighters.size(); i++) {
			System.out.println((i + 1) + ". " + fighters.get(i).getName());
		}
		System.out.print("> ");
		PlayableFighter fighter = game.getPlayer().getFighters().get(in.nextInt() - 1);
		try {
			game.getPlayer().setActiveFighter(fighter);
			System.out.println(fighter.getName() + " is here!");
		} catch (InvalidFighterException e) {
			System.err.println(e.getMessage() + ".");
		}

		printMap();
	}

	private void menuBattle(Battle battle) {
		System.out.println("What do you want to do?");
		System.out.println("1. Attack");
		System.out.println("2. Block");
		System.out.println("3. Use");
		System.out.print("> ");
		boolean ok = true;
		switch (in.nextInt()) {
		case 1:
			ok = menuBattleAttack(battle);
			break;
		case 2:
			menuBattleBlock(battle);
			break;
		case 3:
			ok = menuBattleUse(battle);
			break;
		}

		if (!ok) {
			menuBattle(battle);
		}
	}

	private boolean menuBattleAttack(Battle battle) {
		System.out.println("Choose an attack:");
		boolean superAttack = false;
		boolean ultimateAttack = false;
		ArrayList<Attack> currentOpponentAttacks = battle.getCurrentOpponentAttacks();
		for (int i = 0; i < currentOpponentAttacks.size(); i++) {
			Attack attack = currentOpponentAttacks.get(i);
			if (!superAttack && attack instanceof SuperAttack) {
				superAttack = true;
				System.out.println("- Super Attacks");
			}
			if (!ultimateAttack && attack instanceof UltimateAttack) {
				ultimateAttack = true;
				System.out.println("- Ultimate Attacks");
			}
			System.out.println(
					(attack instanceof PhysicalAttack ? (i + 1) + ". " : "  " + (i + 1) + ".") + attack.getName());
		}
		System.out.print("> ");
		Attack attack = currentOpponentAttacks.get(in.nextInt() - 1);
		try {
			battle.attack(attack);
			return true;
		} catch (InvalidAttackException e) {
			System.err.println(e.getMessage() + ".");
			return false;
		}
	}

	private void menuBattleBlock(Battle battle) {
		battle.block();
	}

	private boolean menuBattleUse(Battle battle) {
		System.out.println("Choose an item:");
		System.out.println("1. " + Collectible.SENZU_BEAN);

		System.out.print("> ");
		Collectible collectible = null;
		switch (in.nextInt()) {
		case 1:
			collectible = Collectible.SENZU_BEAN;
			break;
		}
		try {
			battle.use(game.getPlayer(), collectible);
			return true;
		} catch (NotEnoughCollectiblesException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void onDragonCalled(final Dragon dragon) {
		System.out.println("Hurray! Now we have all Dragon Balls. Let's call the dragon!!!");
		System.out.println(dragon.getName() + "! By your name, I summon you!!! ARISE!!!");
		System.out.println(dragon.getName() + ": You who have summoned me, name your wish now.");
		System.out.println("Choose a wish:");
		DragonWish[] wishes = dragon.getWishes();
		for (int i = 0; i < wishes.length; i++) {
			final DragonWish wish = wishes[i];
			String wishText = null;
			switch (wish.getType()) {
			case SUPER_ATTACK:
				wishText = wish.getSuperAttack().getName();
				break;
			case ULTIMATE_ATTACK:
				wishText = wish.getUltimateAttack().getName();
				break;
			case SENZU_BEANS:
				wishText = wish.getSenzuBeans() + " Senzu Bean"
						+ (wish.getSenzuBeans() == 1 ? "" : "s");
				break;
			case ABILITY_POINTS:
				wishText = wish.getAbilityPoints() + " Ability Point"
						+ (wish.getAbilityPoints() == 1 ? "" : "s");
				break;
			}

			System.out.println((i + 1) + ". " + wishText);
		}

		System.out.print("> ");
		game.getPlayer().chooseWish(wishes[in.nextInt() - 1]);
	}

	@Override
	public void onDragonWishGranted(final DragonWish wish) {
		Dragon dragon = ((Dragon) wish.getSource());
		System.out.println(dragon.getName() + ": Your wish has been granted... FAREWELL!");

		String wishText = null;
		switch (wish.getType()) {
		case SUPER_ATTACK:
			wishText = "You've unlocked the " + wish.getSuperAttack().getName()
					+ " super attack.";
			break;
		case ULTIMATE_ATTACK:
			wishText = "You've unlocked the " + wish.getUltimateAttack().getName()
					+ " ultimate attack.";
			break;
		case SENZU_BEANS:
			int senzuBeans = game.getPlayer().getSenzuBeans();
			wishText = "You now have " + senzuBeans + " Senzu Bean"
					+ (senzuBeans == 1 ? "" : "s")
					+ ".";
			break;
		case ABILITY_POINTS:
			PlayableFighter activeFighter = game.getPlayer().getActiveFighter();
			int abilityPoints = activeFighter.getAbilityPoints();
			wishText = activeFighter.getName() + " now has " + abilityPoints + " Ability Point"
					+ (abilityPoints == 1 ? "" : "s") + ".";
			break;
		}

		System.out.println(wishText);
	}

	@Override
	public void onBattle(final Battle battle) {
		battle.start();
	}

	@Override
	public void onBattleEvent(final BattleEvent e) {
		final Battle battle = (Battle) e.getSource();

		final PlayableFighter me = (PlayableFighter) (battle.getCurrentOpponent() instanceof PlayableFighter
				? battle.getCurrentOpponent() : battle.getOtherOpponent());
		final NonPlayableFighter foe = (NonPlayableFighter) (battle.getCurrentOpponent() instanceof NonPlayableFighter
				? battle.getCurrentOpponent() : battle.getOtherOpponent());

		switch (e.getType()) {
		case STARTED:
			System.out.println("Watch out!!! " + foe.getName() + " started to attack you...");
			break;
		case ENDED:
			if (e.getWinner() == me) {
				System.out.println(foe.getName() + " was defeated. Your XP is now " + me.getXp() + "/"
						+ me.getTargetXp() + ". Your level is " + me.getLevel() + ".");
			} else {
				System.out.println("Oops, you have been defeated!");
				System.out.println("I wasn't strong enough. Now I have to start all over again...");
			}
			break;
		case NEW_TURN:
			if (battle.getCurrentOpponent() == me) {
				printFighters(me, foe);
				menuBattle(battle);
			} else {
				battle.play();
			}
			break;
		case ATTACK:
			System.out.println((e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used "
					+ e.getAttack().getName() + ".");
			break;
		case BLOCK:
			System.out.println((e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used block.");
			break;
		case USE:
			System.out.println((e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used a "
					+ e.getCollectible() + ".");
			break;
		}
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		int collectibles = 0;
		switch (collectible) {
		case SENZU_BEAN:
			collectibles = game.getPlayer().getSenzuBeans();
			break;
		case DRAGON_BALL:
			collectibles = game.getPlayer().getDragonBalls();
			break;
		}

		System.out.println("Look .. A " + collectible + "! You now have " + collectibles + " " + collectible
				+ (collectibles == 1 ? "" : "s") + ".");
	}

	public static void main(String[] args) {
		new DragonBallConsole();
	}
}
