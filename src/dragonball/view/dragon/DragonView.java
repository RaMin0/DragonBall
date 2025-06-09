package dragonball.view.dragon;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.view.custom.JImagePanel;
import dragonball.view.custom.MenuView.Menu;
import dragonball.view.game.GameView;

@SuppressWarnings("serial")
public class DragonView extends JImagePanel {
	private Clip clip;
	private JLabel lblName;
	private JPanel pnlWishes;

	public DragonView() {
		setLayout(null);
		setImage("/gfx/dragon.gif");
	}

	public void initComponents() {
		lblName = new JLabel("");
		lblName.setBounds(10, 10, getWidth() - 20, 40);
		lblName.setHorizontalAlignment(JLabel.CENTER);
		lblName.setFont(GameView.DEFAULT_FONT);
		lblName.setForeground(Color.WHITE);
		add(lblName);

		pnlWishes = new JPanel(new GridLayout(4, 0));
		pnlWishes.setOpaque(false);
		pnlWishes.setBounds(10, 60, 340, 160);
		add(pnlWishes);
	}

	public void startDragonMode() {
		pnlWishes.removeAll();

		try {
			clip = AudioSystem.getClip();
			InputStream audioStream = getClass().getClassLoader().getResourceAsStream("sfx/dragon.wav");
			if (audioStream == null) {
				throw new Exception("Resource not found: sfx/dragon.wav");
			}
			clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream)));
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.START) {
						clip.setLoopPoints(365283, -1);
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

	public void endDragonMode() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void setName(String name) {
		lblName.setText(name);
	}

	public void addWish(final String wish, Menu.Item.Listener listener) {
		Menu.Item lblWish = new Menu.Item(null, "\u2022 " + wish) {
			@Override
			public Color getForegroundColor() {
				return Color.WHITE;
			}

			@Override
			public Color getBackgroundColor() {
				return Color.BLACK;
			}
		};
		lblWish.addListener(listener);
		pnlWishes.add(lblWish);
	}
}
