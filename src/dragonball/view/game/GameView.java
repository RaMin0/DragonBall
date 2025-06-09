package dragonball.view.game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import dragonball.view.battle.BattleView;
import dragonball.view.custom.HudView;
import dragonball.view.custom.MenuView;
import dragonball.view.dragon.DragonView;
import dragonball.view.world.WorldView;

@SuppressWarnings("serial")
public class GameView extends JFrame {
	public static final Font DEFAULT_FONT;
	public static final boolean PLAY_SOUND = true;

	private JLayeredPane contentPane;
	private WorldView worldView;
	private BattleView battleView;
	private DragonView dragonView;
	private Set<Listener> listeners = new HashSet<>();

	static {
		Font font;
		try {
			InputStream is = GameView.class.getClassLoader().getResourceAsStream("res/8-bit.ttf");
			if (is == null) {
				throw new Exception("Resource not found: res/8-bit.ttf");
			}
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			GraphicsEnvironment gc = GraphicsEnvironment.getLocalGraphicsEnvironment();
			gc.registerFont(font);
		} catch (Exception e) {
			font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
			e.printStackTrace();
		}
		DEFAULT_FONT = font.deriveFont(Font.BOLD, 18f);
	}

	public GameView(int worldSize) {
		setTitle("Dragon Ball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setResizable(false);
		getContentPane().setLayout(null);

		contentPane = new JLayeredPane();

		worldView = new WorldView(worldSize);
		worldView.setLocation(0, 0);
		worldView.setSize(worldView.getPreferredSize());
		worldView.addListener(new WorldView.Listener() {
			@Override
			public void onMenuExit() {
				notifyListenersOnWorldViewMenuExit();
			}
		});
		contentPane.setPreferredSize(worldView.getPreferredSize());
		contentPane.setLayer(worldView, 0);
		contentPane.add(worldView);

		battleView = new BattleView();
		battleView.setLocation(0, 0);
		battleView.setSize(contentPane.getPreferredSize());
		battleView.initComponents();
		contentPane.add(battleView);

		dragonView = new DragonView();
		dragonView.setLocation(0, 0);
		dragonView.setSize(contentPane.getPreferredSize());
		dragonView.initComponents();
		contentPane.add(dragonView);

		MenuView menuView = MenuView.getInstance();
		menuView.setLocation(0, 0);
		menuView.setSize(contentPane.getPreferredSize());
		menuView.setOrigin(0, (int) (contentPane.getPreferredSize().getHeight()));
		contentPane.setLayer(menuView, 3);
		contentPane.add(menuView);

		HudView hudView = HudView.getInstance();
		hudView.setLocation(2, (int) contentPane.getPreferredSize().getHeight() - 98);
		hudView.setSize((int) contentPane.getPreferredSize().getWidth() - 4, 96);
		contentPane.setLayer(hudView, 4);
		contentPane.add(hudView);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				notifyListenersOnKey(e.getKeyCode());
			}
		});

		setContentPane(contentPane);
		pack();
	}

	public WorldView getWorldView() {
		return worldView;
	}

	public BattleView getBattleView() {
		return battleView;
	}

	public DragonView getDragonView() {
		return dragonView;
	}

	public void switchToWorldView() {
		MenuView.getInstance().removeAllMenus();
		contentPane.setLayer(battleView, -1);
		contentPane.setLayer(dragonView, -1);

		battleView.endBattle();
		dragonView.endDragonMode();
		worldView.enterWorld();
	}

	public void switchToBattleView() {
		MenuView.getInstance().removeAllMenus();
		contentPane.setLayer(battleView, 1);
	}

	public void switchToDragonView() {
		MenuView.getInstance().removeAllMenus();
		contentPane.setLayer(dragonView, 2);
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void notifyListenersOnKey(int keyCode) {
		for (Listener listener : listeners) {
			listener.onKey(keyCode);
		}
	}

	public void notifyListenersOnWorldViewMenuExit() {
		for (Listener listener : listeners) {
			listener.onWorldViewMenuExit();
		}
	}

	public interface Listener extends EventListener {
		void onKey(int keyCode);

		void onWorldViewMenuExit();
	}
}
