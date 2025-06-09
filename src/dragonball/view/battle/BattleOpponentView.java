package dragonball.view.battle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.view.custom.JCustomProgressBar;
import dragonball.view.game.GameView;

@SuppressWarnings("serial")
public class BattleOpponentView extends JPanel {
	private boolean foe;
	private String image;

	private JLabel lblAttack;
	private JLabel lblName;
	private JLabel lblImage;
	private JLabel lblLevel;
	private JCustomProgressBar prgHealthPoints;
	private JCustomProgressBar[] prgStaminas;
	private JCustomProgressBar[] prgKis;

	public BattleOpponentView() {
		this(false);
	}

	public BattleOpponentView(boolean foe) {
		this.foe = foe;
		image = "/gfx/" + (foe ? "foe" : "me") + "-thumb.gif";
		prgStaminas = new JCustomProgressBar[20];
		prgKis = new JCustomProgressBar[20];

		setLayout(null);
		setOpaque(false);
	}

	public void initComponents() {
		JPanel pnlImageLevel = new JPanel(null);
		pnlImageLevel.setOpaque(false);
		setChildBounds(pnlImageLevel, 5, getHeight() - (foe ? 229 : 117), 96, 112);
		add(pnlImageLevel);

		lblAttack = new JLabel(new ImageIcon(getClass().getResource("/gfx/attack.gif"))) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;

				if (BattleOpponentView.this.foe) {
					g2d.scale(-1, 1);
					g2d.translate(-getWidth(), 0);
				}

				super.paintComponent(g2d);
			}
		};
		lblAttack.setVisible(false);
		setChildBounds(lblAttack, 5, getHeight() - (foe ? 261 : 149), 128, 128);
		add(lblAttack);

		lblImage = new JLabel(new ImageIcon(getClass().getResource(image))) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;

				if (BattleOpponentView.this.foe) {
					g2d.scale(-1, 1);
					g2d.translate(-getWidth(), 0);
				}

				super.paintComponent(g2d);
			}
		};
		lblImage.setBounds(0, 0, 96, 96);
		pnlImageLevel.add(lblImage);

		lblLevel = new JLabel();
		lblLevel.setFont(GameView.DEFAULT_FONT.deriveFont(12f));
		lblLevel.setHorizontalAlignment(JLabel.CENTER);
		lblLevel.setBounds(lblImage.getX(), lblImage.getHeight() + 1, lblImage.getWidth(), 15);
		pnlImageLevel.add(lblLevel);

		JPanel pnlNameInfo = new JPanel(null);
		pnlNameInfo.setOpaque(false);
		setChildBounds(pnlNameInfo, 5, 5, getWidth() - 8, pnlImageLevel.getHeight());
		add(pnlNameInfo);

		lblName = new JLabel();
		lblName.setFont(GameView.DEFAULT_FONT);
		lblName.setBounds(0, 0, pnlNameInfo.getWidth(), 30);
		pnlNameInfo.add(lblName);

		JLabel lblHealthPointsLabel = new JLabel("HP:");
		lblHealthPointsLabel.setFont(lblLevel.getFont());
		lblHealthPointsLabel.setBounds(lblName.getX(), lblName.getHeight() + 3, 40, 25);
		pnlNameInfo.add(lblHealthPointsLabel);

		prgHealthPoints = new JCustomProgressBar();
		prgHealthPoints.setForeground(Color.RED);
		prgHealthPoints.setBackground(Color.BLACK);
		prgHealthPoints.setBounds(lblHealthPointsLabel.getWidth(), lblHealthPointsLabel.getY(),
				pnlNameInfo.getWidth() - lblHealthPointsLabel.getWidth(), lblHealthPointsLabel.getHeight());
		pnlNameInfo.add(prgHealthPoints);

		JLabel lblKiLabel = new JLabel("KI:");
		lblKiLabel.setFont(lblLevel.getFont());
		lblKiLabel.setBounds(lblHealthPointsLabel.getX(),
				lblHealthPointsLabel.getY() + lblHealthPointsLabel.getHeight() + 2, lblHealthPointsLabel.getWidth(),
				lblHealthPointsLabel.getHeight());
		pnlNameInfo.add(lblKiLabel);

		JPanel pnlKi = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1));
		pnlKi.setOpaque(false);
		pnlKi.setBounds(lblKiLabel.getWidth(), lblKiLabel.getY(),
				pnlNameInfo.getWidth() - lblKiLabel.getWidth(), lblKiLabel.getHeight());
		pnlNameInfo.add(pnlKi);

		JLabel lblStaminaLabel = new JLabel("ST:");
		lblStaminaLabel.setFont(lblLevel.getFont());
		lblStaminaLabel.setBounds(lblKiLabel.getX(), lblKiLabel.getY() + lblKiLabel.getHeight() + 2, lblKiLabel.getWidth(),
				lblKiLabel.getHeight());
		pnlNameInfo.add(lblStaminaLabel);

		JPanel pnlStamina = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1));
		pnlStamina.setOpaque(false);
		pnlStamina.setBounds(lblStaminaLabel.getWidth(), lblStaminaLabel.getY(),
				pnlNameInfo.getWidth() - lblStaminaLabel.getWidth(), lblStaminaLabel.getHeight());
		pnlNameInfo.add(pnlStamina);

		for (int i = 0; i < 20; i++) {
			JCustomProgressBar prgKi = new JCustomProgressBar();
			prgKi.setMaximum(1);
			prgKi.setForeground(Color.YELLOW);
			prgKi.setBackground(Color.BLACK);
			prgKi.setPreferredSize(new Dimension((int) ((pnlNameInfo.getWidth() - 40) / 10), 11));
			pnlKi.add(prgKi);
			prgKis[i] = prgKi;

			JCustomProgressBar prgStamina = new JCustomProgressBar();
			prgStamina.setMaximum(1);
			prgStamina.setForeground(Color.GREEN);
			prgStamina.setBackground(Color.BLACK);
			prgStamina.setPreferredSize(new Dimension((int) ((pnlNameInfo.getWidth() - 40) / 10), 11));
			pnlStamina.add(prgStamina);
			prgStaminas[i] = prgStamina;
		}
	}

	public void setName(String name) {
		lblName.setText(name);
	}

	public void setLevel(int level) {
		lblLevel.setText("lvl " + level);
	}

	public void setHealthPoints(int healthPoints) {
		prgHealthPoints.setValue(healthPoints);
	}

	public void setMaxHealthPoints(int maxHealthPoints) {
		prgHealthPoints.setMaximum(maxHealthPoints);
	}

	public void setKi(int ki) {
		for (int i = 0; i < prgKis.length; i++) {
			prgKis[i].setValue(i < ki ? 1 : 0, false);
		}
	}

	public void setMaxKi(int maxKi) {
		for (int i = 0; i < prgKis.length; i++) {
			prgKis[i].setVisible(i < maxKi);
		}
	}

	public void setStamina(int stamina) {
		for (int i = 0; i < prgStaminas.length; i++) {
			prgStaminas[i].setValue(i < stamina ? 1 : 0, false);
		}
	}

	public void setMaxStamina(int maxStamina) {
		for (int i = 0; i < prgStaminas.length; i++) {
			prgStaminas[i].setVisible(i < maxStamina);
		}
	}

	public void setBoss(boolean boss) {
		((ImageIcon) lblImage.getIcon()).setImage(
				new ImageIcon(getClass().getResource(image = "/gfx/" + (boss ? "boss" : "foe") + "-thumb.gif")).getImage());
	}

	private void setChildBounds(Component c, int x, int y, double width, double height) {
		c.setBounds(foe ? (int) (getWidth() - x - width) : x, y, (int) width, (int) height);
	}

	public void animateAttack() {
		lblAttack.setVisible(true);
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
					lblAttack.setVisible(false);
				} catch (InterruptedException e) {

				}
			}
		}).start();
	}
}
