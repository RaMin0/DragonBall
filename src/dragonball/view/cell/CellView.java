package dragonball.view.cell;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class CellView extends JLabel {
	public CellView(String gfx) {
		setIcon(new ImageIcon(getClass().getResource("/gfx/" + gfx)));
		setOpaque(true);
		setBackground(new Color(116, 226, 31));
	}
}
