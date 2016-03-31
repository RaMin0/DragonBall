package dragonball.view.custom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class JCustomProgressBar extends JProgressBar {
	public JCustomProgressBar() {
		setBorderPainted(false);
	}

	@Override
	public void setValue(int n) {
		setValue(n, true);
	}

	public synchronized void setValue(final int n, boolean animate) {
		if (animate) {
			new Thread(new Runnable() {
				public void run() {
					int sign = n > getValue() ? 1 : -1;
					while (sign * getValue() < sign * n) {
						int dv = (int) (0.01 * (getMaximum() - getMinimum()));
						JCustomProgressBar.super.setValue(getValue() + dv * sign);
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					JCustomProgressBar.super.setValue(n);
				}
			}).start();
		} else {
			super.setValue(n);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 2, 5, 5);

		g2d.setColor(getForeground());
		g2d.fillRoundRect(1, 1, (int) Math.round(getPercentComplete() * (getWidth() - 3)), getHeight() - 2, 5, 5);

		g2d.setColor(getBackground());
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 2, 5, 5);
	}
}
