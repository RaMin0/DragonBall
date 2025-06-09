package dragonball.view.battle;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import dragonball.view.custom.JImagePanel;
import dragonball.view.custom.MenuView;
import dragonball.view.custom.MenuView.Menu;
import dragonball.view.custom.MenuView.Menu.Item;
import dragonball.view.game.GameView;

@SuppressWarnings("serial")
public class BattleView extends JImagePanel {
	private Clip clip;
	private BattleOpponentView me;
	private BattleOpponentView foe;
	private ArrayList<SimpleEntry<String, Item.Listener>> attackSubItems;
	private Item.Listener blockItemListener;
	private ArrayList<SimpleEntry<String, Item.Listener>> useSubItems;

	public BattleView() {
		setLayout(null);
		setImage("/gfx/battle.gif");

		attackSubItems = new ArrayList<>();
		useSubItems = new ArrayList<>();
	}

	public void initComponents() {
		me = new BattleOpponentView();
		me.setSize(new Dimension(getWidth() / 2, getHeight()));
		me.initComponents();
		add(me);

		foe = new BattleOpponentView(true);
		foe.setLocation(getWidth() / 2, 0);
		foe.setSize(new Dimension(getWidth() / 2, getHeight()));
		foe.initComponents();
		add(foe);
	}

	public BattleOpponentView getMe() {
		return me;
	}

	public BattleOpponentView getFoe() {
		return foe;
	}

	public void setBlockItemListener(Item.Listener blockItemListener) {
		this.blockItemListener = blockItemListener;
	}

	public void startBattle() {
		attackSubItems.clear();
		blockItemListener = null;
		useSubItems.clear();

		try {
			clip = AudioSystem.getClip();
			InputStream audioStream = getClass().getClassLoader().getResourceAsStream("sfx/battle.wav");
			if (audioStream == null) {
				throw new Exception("Resource not found: sfx/battle.wav");
			}
			clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream)));
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.START) {
						clip.setLoopPoints(353596, -1);
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					}
				}
			});

			if (GameView.PLAY_SOUND) {
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endBattle() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void addAttackSubItem(String attackName, Item.Listener listener) {
		attackSubItems.add(new SimpleEntry<>(attackName, listener));
	}

	public void addUseSubItem(String attackName, Item.Listener listener) {
		useSubItems.add(new SimpleEntry<>(attackName, listener));
	}

	public void showMenu() {
		final MenuView menuView = MenuView.getInstance();
		menuView.setOrigin(106, getHeight());

		final Menu menu = menuView.addMenu();

		final Item attackItem = menu.addItem("Attack");
		attackItem.addListener(new Item.Listener() {
			@Override
			public void onAction(Item item) {
				final Menu attackMenu = menuView.addMenu();
				for (SimpleEntry<String, Item.Listener> entry : attackSubItems) {
					String attackName = entry.getKey();
					Item.Listener listener = entry.getValue();
					Item attackSubItem = attackMenu.addItem(attackName, listener);
					Dimension size = attackSubItem.getPreferredSize();
					attackSubItem.setPreferredSize(new Dimension(300, (int) size.getHeight()));
					attackMenu.notifyListenersOnItemAdded(attackSubItem);
				}
			}
		});

		if (blockItemListener != null) {
			menu.addItem("Block", blockItemListener);
		}

		final Item useItem = menu.addItem("Use");
		useItem.addListener(new Item.Listener() {
			@Override
			public void onAction(Item item) {
				final Menu useMenu = menuView.addMenu();
				for (SimpleEntry<String, Item.Listener> entry : useSubItems) {
					String useName = entry.getKey();
					Item.Listener listener = entry.getValue();
					Item useSubItem = useMenu.addItem(useName, listener);
					Dimension size = useSubItem.getPreferredSize();
					useSubItem.setPreferredSize(new Dimension(200, (int) size.getHeight()));
					useMenu.notifyListenersOnItemAdded(useSubItem);
				}
			}
		});
	}

	public void animateAttack(boolean foe) {
		if (foe) {
			this.foe.animateAttack();
		} else {
			this.me.animateAttack();
		}
	}
}
