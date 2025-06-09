package dragonball.controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import dragonball.exceptions.DuplicateAttackException;
import dragonball.exceptions.InvalidAttackException;
import dragonball.exceptions.InvalidFighterAttributeException;
import dragonball.exceptions.InvalidFighterException;
import dragonball.exceptions.InvalidFighterRaceException;
import dragonball.exceptions.InvalidMoveException;
import dragonball.exceptions.MaximumAttacksLearnedException;
import dragonball.exceptions.NotEnoughAbilityPointsException;
import dragonball.exceptions.NotEnoughCollectiblesException;
import dragonball.model.attack.Attack;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.view.cell.CellView;
import dragonball.view.cell.EmptyCellView;
import dragonball.view.cell.PlayerCellView;
import dragonball.view.cell.StrongFoeCellView;
import dragonball.view.custom.HudView;
import dragonball.view.custom.MenuView;
import dragonball.view.custom.MenuView.Menu;
import dragonball.view.custom.MenuView.Menu.Item;
import dragonball.view.game.GameView;

public class DragonBallGUI implements GameListener, GameView.Listener {
	private Game game;
	private GameView gameView;
	private Cell[][] map;
	private CellView[][] mapView;
	private boolean battleFirstTurn;

	public DragonBallGUI() {
		game = new Game();
		map = game.getWorld().getMap();
		gameView = new GameView(map.length);
		mapView = gameView.getWorldView().getMap();

		game.setListener(this);
		gameView.addListener(this);

		HudView.getInstance().setTextWithInput("Hey, there ... Umm, What's your name?\n> ",
				new HudView.InputCallback() {
					@Override
					public void onComplete(String input) {
						gameView.requestFocus();
						game.getPlayer().setName(input);
						HudView.getInstance().setText(
								"Ok, " + input
										+ ", nice meeting you! Before you start your journey, you have to create your first fighter.",
								new HudView.Callback() {
									@Override
									public void onComplete() {
										gameView.getWorldView().showMenu();
									}
								});
					}
				});

		for (Class<?> c : PlayableFighter.getPlayableFighters()) {
			if (c != PlayableFighter.class && PlayableFighter.class.isAssignableFrom(c)) {
				final String fighterType = c.getSimpleName();
				gameView.getWorldView().addCreateFighterSubItem(fighterType, new Item.Listener() {
					@Override
					public void onAction(Item item) {
						MenuView.getInstance().removeAllMenus();
						HudView.getInstance().setTextWithInput("Name your new " + fighterType + " fighter:\n> ",
								new HudView.InputCallback() {
									@Override
									public void onComplete(String fighterName) {
										gameView.requestFocus();

										try {
											game.getPlayer().createFighter(fighterType.charAt(0), fighterName);
											final PlayableFighter fighter = game.getPlayer().getFighters()
													.get(game.getPlayer().getFighters().size() - 1);
											syncAttacks();
											HudView.getInstance().setText(fighterName + " is here!");
											gameView.getWorldView().addActiveFighterSubItem(fighter.getName(),
													new Item.Listener() {
														@Override
														public void onAction(Item item) {
															MenuView.getInstance().removeAllMenus();
															try {
																game.getPlayer().setActiveFighter(fighter);
																syncMaps();
																HudView.getInstance().setText(fighter.getName() + " is here!");
															} catch (InvalidFighterException e) {
																HudView.getInstance().setText(e.getMessage());
															}
														}
													});
											syncMaps();
										} catch (InvalidFighterRaceException e) {
											HudView.getInstance().setText(e.getMessage() + ".");
										}
									}
								});
					}
				});
			}
		}

		for (final PlayableFighter fighter : game.getPlayer().getFighters()) {
			gameView.getWorldView().addActiveFighterSubItem(fighter.getName(), new Item.Listener() {
				@Override
				public void onAction(Item item) {
					MenuView.getInstance().removeAllMenus();
					try {
						game.getPlayer().setActiveFighter(fighter);
						HudView.getInstance().setText(fighter.getName() + " is here!");
					} catch (InvalidFighterException e) {
						HudView.getInstance().setText(e.getMessage() + ".");
					}
				}
			});
		}

		String[] fighterAttributes = new String[] { "Health Points", "Blast Damage", "Physical Damage", "Ki", "Stamina" };
		for (String fighterAttribute : fighterAttributes) {
			gameView.getWorldView().addUpgradeFighterSubItem(fighterAttribute, new Item.Listener() {
				@Override
				public void onAction(Item item) {
					MenuView.getInstance().removeAllMenus();
					try {
						PlayableFighter fighter = game.getPlayer().getActiveFighter();
						char fighterAttribute = item.getText().charAt(0);
						game.getPlayer().upgradeFighter(fighter, fighterAttribute);

						int attributeValue = -1;
						switch (fighterAttribute) {
							case 'H':
								attributeValue = fighter.getMaxHealthPoints();
								break;
							case 'B':
								attributeValue = fighter.getBlastDamage();
								break;
							case 'P':
								attributeValue = fighter.getPhysicalDamage();
								break;
							case 'K':
								attributeValue = fighter.getMaxKi();
								break;
							case 'S':
								attributeValue = fighter.getMaxStamina();
								break;
						}

						String isOrAre = item.getText().charAt(item.getText().length() - 1) == 's' ? "are" : "is";
						HudView.getInstance().setText(fighter.getName() + "'s " + item.getText().toLowerCase() + " " + isOrAre
								+ " now " + attributeValue + ".");
					} catch (NotEnoughAbilityPointsException | InvalidFighterException
							| InvalidFighterAttributeException e) {
						HudView.getInstance().setText(e.getMessage());
					}
				}
			});
		}

		syncMaps();

		gameView.setVisible(true);
	}

	public void syncMaps() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell cell = map[i][j];
				if (cell instanceof FoeCell && ((FoeCell) cell).getFoe().isStrong()) {
					mapView[i][j] = new StrongFoeCellView();
				} else if (i == game.getWorld().getPlayerRow() && j == game.getWorld().getPlayerColumn()) {
					if (game.getPlayer().getActiveFighter() != null) {
						String fighterType = game.getPlayer().getActiveFighter().getClass().getSimpleName().toLowerCase();
						mapView[i][j] = new PlayerCellView("player-" + fighterType + ".gif");
					} else {
						mapView[i][j] = new EmptyCellView();
					}
				} else {
					mapView[i][j] = new EmptyCellView();
				}
			}
		}

		gameView.getWorldView().renderMap();
		System.out.println(game.getWorld());
	}

	private void syncFighters(PlayableFighter me, NonPlayableFighter foe) {
		gameView.getBattleView().getMe().setName(me.getName());
		gameView.getBattleView().getMe().setLevel(me.getLevel());
		gameView.getBattleView().getMe().setMaxHealthPoints(me.getMaxHealthPoints());
		gameView.getBattleView().getMe().setHealthPoints(me.getHealthPoints());
		gameView.getBattleView().getMe().setKi(me.getKi());
		gameView.getBattleView().getMe().setMaxKi(me.getMaxKi());
		gameView.getBattleView().getMe().setStamina(me.getStamina());
		gameView.getBattleView().getMe().setMaxStamina(me.getMaxStamina());

		gameView.getBattleView().getFoe().setName(foe.getName());
		gameView.getBattleView().getFoe().setLevel(foe.getLevel());
		gameView.getBattleView().getFoe().setMaxHealthPoints(foe.getMaxHealthPoints());
		gameView.getBattleView().getFoe().setHealthPoints(foe.getHealthPoints());
		gameView.getBattleView().getFoe().setKi(foe.getKi());
		gameView.getBattleView().getFoe().setMaxKi(foe.getMaxKi());
		gameView.getBattleView().getFoe().setStamina(foe.getStamina());
		gameView.getBattleView().getFoe().setMaxStamina(foe.getMaxStamina());
		gameView.getBattleView().getFoe().setBoss(foe.isStrong());
	}

	private void syncAttacks() {
		gameView.getWorldView().clearAssignFighterAttackSubItems();

		final PlayableFighter fighter = game.getPlayer().getActiveFighter();

		gameView.getWorldView().addAssignFighterAttackSubItem("Super Attacks", null);
		for (int i = 0; i < Fighter.MAX_SUPER_ATTACKS; i++) {
			final SuperAttack fighterAttack = fighter.getSuperAttacks().size() > i ? fighter.getSuperAttacks().get(i) : null;
			gameView.getWorldView().addAssignFighterAttackSubItem(
					"\u2022 " + (fighterAttack == null ? "    (EMPTY)" : fighterAttack.getName()),
					new Item.Listener() {
						@Override
						public void onAction(Item item) {
							final Menu superAttacksMenu = MenuView.getInstance().addMenu();
							for (final SuperAttack playerAttack : game.getPlayer().getSuperAttacks()) {
								Item attackSubItem = superAttacksMenu.addItem(playerAttack.getName(), new Item.Listener() {
									@Override
									public void onAction(Item item) {
										try {
											game.getPlayer().assignAttack(fighter, playerAttack, fighterAttack);
											syncAttacks();
											HudView.getInstance().setText(fighter.getName() + " has learned the "
													+ playerAttack.getName() + " attack.");
										} catch (DuplicateAttackException | MaximumAttacksLearnedException e) {
											HudView.getInstance().setText(e.getMessage());
										}

										MenuView.getInstance().removeAllMenus();
									}
								});
								Dimension size = attackSubItem.getPreferredSize();
								attackSubItem.setPreferredSize(new Dimension(300, (int) size.getHeight()));
								superAttacksMenu.notifyListenersOnItemAdded(attackSubItem);
							}
						}
					});
		}

		gameView.getWorldView().addAssignFighterAttackSubItem("Ultimate Attacks", null);
		for (int i = 0; i < Fighter.MAX_ULTIMATE_ATTACKS; i++) {
			final UltimateAttack fighterAttack = fighter.getUltimateAttacks().size() > i
					? fighter.getUltimateAttacks().get(i)
					: null;
			gameView.getWorldView().addAssignFighterAttackSubItem(
					"\u2022 " + (fighterAttack == null ? "    (EMPTY)" : fighterAttack.getName()),
					new Item.Listener() {
						@Override
						public void onAction(Item item) {
							final Menu superAttacksMenu = MenuView.getInstance().addMenu();
							for (final UltimateAttack playerAttack : game.getPlayer().getUltimateAttacks()) {
								Item attackSubItem = superAttacksMenu.addItem(playerAttack.getName(), new Item.Listener() {
									@Override
									public void onAction(Item item) {
										try {
											game.getPlayer().assignAttack(fighter, playerAttack, fighterAttack);
											syncAttacks();
											HudView.getInstance().setText(fighter.getName() + " has learned the "
													+ playerAttack.getName() + " attack.");
										} catch (DuplicateAttackException | MaximumAttacksLearnedException e) {
											HudView.getInstance().setText(e.getMessage());
										}

										MenuView.getInstance().removeAllMenus();
									}
								});
								Dimension size = attackSubItem.getPreferredSize();
								attackSubItem.setPreferredSize(new Dimension(300, (int) size.getHeight()));
								superAttacksMenu.notifyListenersOnItemAdded(attackSubItem);
							}
						}
					});
		}
	}

	@Override
	public void onDragonCalled(final Dragon dragon) {
		HudView.getInstance().setCallback(new HudView.Callback() {
			@Override
			public void onComplete() {
				gameView.getWorldView().leaveWorld();
				gameView.getDragonView().startDragonMode();

				gameView.getDragonView().setName(dragon.getName());

				for (final DragonWish wish : dragon.getWishes()) {
					String wishText = null;
					switch (wish.getType()) {
						case SUPER_ATTACK:
							wishText = wish.getSuperAttack().getName();
							break;
						case ULTIMATE_ATTACK:
							wishText = wish.getUltimateAttack().getName();
							break;
						case SENZU_BEANS:
							wishText = wish.getSenzuBeans() + " Senzu Bean" + (wish.getSenzuBeans() == 1 ? "" : "s");
							break;
						case ABILITY_POINTS:
							wishText = wish.getAbilityPoints() + " Ability Point" + (wish.getAbilityPoints() == 1 ? "" : "s");
							break;
					}

					gameView.getDragonView().addWish(wishText, new Menu.Item.Listener() {
						@Override
						public void onAction(Item item) {
							game.getPlayer().chooseWish(wish);

							HudView.getInstance().setText(
									((Dragon) wish.getSource()).getName() + ": Your wish has been granted... FAREWELL!",
									new HudView.Callback() {
										@Override
										public void onComplete() {
											gameView.switchToWorldView();

											String wishText = null;
											switch (wish.getType()) {
												case SUPER_ATTACK:
													wishText = "You've unlocked the " + wish.getSuperAttack().getName() + " super attack.";
													break;
												case ULTIMATE_ATTACK:
													wishText = "You've unlocked the " + wish.getUltimateAttack().getName() + " ultimate attack.";
													break;
												case SENZU_BEANS:
													int senzuBeans = game.getPlayer().getSenzuBeans();
													wishText = "You now have " + senzuBeans + " Senzu Bean" + (senzuBeans == 1 ? "" : "s") + ".";
													break;
												case ABILITY_POINTS:
													PlayableFighter activeFighter = game.getPlayer().getActiveFighter();
													int abilityPoints = activeFighter.getAbilityPoints();
													wishText = activeFighter.getName() + " now has " + abilityPoints + " Ability Point"
															+ (abilityPoints == 1 ? "" : "s") + ".";
													break;
											}

											HudView.getInstance().setText(wishText);
										}
									});
						}
					});
				}

				HudView.getInstance().setText(
						"Hurray! Now we have all Dragon Balls. Let's call the dragon!!!",
						new HudView.Callback() {
							@Override
							public void onComplete() {
								HudView.getInstance().setText(
										dragon.getName() + "! By your name, I summon you!!! ARISE!!!",
										new HudView.Callback() {
											@Override
											public void onComplete() {
												gameView.switchToDragonView();
												HudView.getInstance()
														.setText(dragon.getName() + ": You who have summoned me, name your wish now.");
											}
										});
							}
						});
			}
		});
	}

	@Override
	public void onBattleEvent(final BattleEvent e) {
		final Battle battle = (Battle) e.getSource();

		final PlayableFighter me = (PlayableFighter) (battle.getAttacker() instanceof PlayableFighter
				? battle.getAttacker()
				: battle.getDefender());
		final NonPlayableFighter foe = (NonPlayableFighter) (battle.getAttacker() instanceof NonPlayableFighter
				? battle.getAttacker()
				: battle.getDefender());

		syncFighters(me, foe);

		switch (e.getType()) {
			case STARTED:
				gameView.getWorldView().leaveWorld();
				gameView.getBattleView().startBattle();

				boolean superAttack = false;
				boolean ultimateAttack = false;
				for (final Attack attack : battle.getAssignedAttacks()) {
					if (!superAttack && attack instanceof SuperAttack) {
						superAttack = true;
						gameView.getBattleView().addAttackSubItem("Super Attacks", null);
					}
					if (!ultimateAttack && attack instanceof UltimateAttack) {
						ultimateAttack = true;
						gameView.getBattleView().addAttackSubItem("Ultimate Attacks", null);
					}
					gameView.getBattleView().addAttackSubItem(
							(attack instanceof PhysicalAttack ? "" : "\u2022 ") + attack.getName(),
							new Item.Listener() {
								@Override
								public void onAction(Item item) {
									MenuView.getInstance().removeAllMenus();
									try {
										battle.attack(attack);
									} catch (InvalidAttackException e) {
										HudView.getInstance().setText(e.getMessage(), new HudView.Callback() {
											@Override
											public void onComplete() {
												gameView.getBattleView().showMenu();
											}
										});
									}
								}
							});
				}

				gameView.getBattleView().setBlockItemListener(new Item.Listener() {
					@Override
					public void onAction(Item item) {
						MenuView.getInstance().removeAllMenus();
						battle.block();
					}
				});

				gameView.getBattleView().addUseSubItem(Collectible.SENZU_BEAN.toString(),
						new Item.Listener() {
							@Override
							public void onAction(Item item) {
								MenuView.getInstance().removeAllMenus();
								try {
									battle.use(game.getPlayer(), Collectible.SENZU_BEAN);
								} catch (NotEnoughCollectiblesException e) {
									HudView.getInstance().setText(e.getMessage(), new HudView.Callback() {
										@Override
										public void onComplete() {
											gameView.getBattleView().showMenu();
										}
									});
								}
							}
						});

				battleFirstTurn = true;
				HudView.getInstance().setText("Watch out!!!");
				break;
			case ENDED:
				String battleEndedMessage = e.getWinner() == me
						? foe.getName() + " was defeated. Your XP is now " + me.getXp() + "/" + me.getTargetXp()
								+ ". Your level is " + me.getLevel() + "."
						: "Oops, you have been defeated!";
				HudView.getInstance().setText(battleEndedMessage, new HudView.Callback() {
					@Override
					public void onComplete() {
						syncMaps();
						syncAttacks();

						if (e.getWinner() == me && foe.isStrong()) {
							int exploredMaps = game.getPlayer().getExploredMaps();
							HudView.getInstance().setText("You've explored " + exploredMaps + " map" + (exploredMaps == 1 ? "" : "s")
									+ ". A new map has been unlocked!");
						} else if (e.getWinner() == foe) {
							HudView.getInstance().setText("I wasn't strong enough. Now I have to start all over again...");
						}

						gameView.switchToWorldView();
					}
				});
				break;
			case NEW_TURN:
				HudView.Callback callback;

				if (battleFirstTurn) {
					battleFirstTurn = false;
					callback = new HudView.Callback() {
						@Override
						public void onComplete() {
							gameView.switchToBattleView();
							HudView.getInstance().setText(foe.getName() + " started to attack you...",
									new HudView.Callback() {
										@Override
										public void onComplete() {
											gameView.getBattleView().showMenu();
										}
									});
						}
					};
				} else {
					callback = new HudView.Callback() {
						@Override
						public void onComplete() {
							if (battle.getAttacker() == me) {
								gameView.getBattleView().showMenu();
							} else {
								battle.play();
							}
						}
					};
				}

				if (HudView.getInstance().isShowing()) {
					HudView.getInstance().setCallback(callback);
				} else {
					callback.onComplete();
				}
				break;
			case ATTACK:
				if (e.getCurrentOpponent() == foe) {
					gameView.getBattleView().animateAttack(true);
				}
				HudView.getInstance().setText(
						(e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used " + e.getAttack().getName() + ".");
				break;
			case BLOCK:
				HudView.getInstance().setText((e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used block.");
				break;
			case USE:
				HudView.getInstance().setText(
						(e.getCurrentOpponent() == me ? me.getName() : foe.getName()) + " used a " + e.getCollectible() + ".");
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

		HudView.getInstance().setText("Look .. A " + collectible + "! You now have " + collectibles + " " + collectible
				+ (collectibles == 1 ? "" : "s") + ".");
	}

	@Override
	public void onKey(int keyCode) {
		if (HudView.getInstance().isShowing()) {
			switch (keyCode) {
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_ENTER:
					HudView.getInstance().nextTextPage();
					break;
			}
		} else {
			switch (game.getState()) {
				case WORLD:
					if (game.getPlayer().getActiveFighter() != null) {
						if (!MenuView.getInstance().isShowing()) {
							try {
								switch (keyCode) {
									case KeyEvent.VK_UP:
										game.getWorld().moveUp();
										break;
									case KeyEvent.VK_DOWN:
										game.getWorld().moveDown();
										break;
									case KeyEvent.VK_LEFT:
										game.getWorld().moveLeft();
										break;
									case KeyEvent.VK_RIGHT:
										game.getWorld().moveRight();
										break;
								}
								syncMaps();
							} catch (InvalidMoveException e) {
								e.printStackTrace();
							}
						}
					}

					switch (keyCode) {
						case KeyEvent.VK_ESCAPE:
							MenuView menuView = MenuView.getInstance();
							if (menuView.isShowing()) {
								menuView.removeAllMenus();
							} else {
								gameView.getWorldView().showMenu();
							}
							break;
					}
					break;
				case BATTLE:
					break;
				case DRAGON:
					break;
			}
		}
	}

	@Override
	public void onWorldViewMenuExit() {
		HudView.getInstance().setText("Good-bye, " + game.getPlayer().getName() + "!",
				new HudView.Callback() {
					@Override
					public void onComplete() {
						gameView.dispatchEvent(new WindowEvent(gameView, WindowEvent.WINDOW_CLOSING));
					}
				});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DragonBallGUI();
			}
		});
	}
}
