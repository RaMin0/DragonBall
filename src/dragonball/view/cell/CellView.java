package dragonball.view.cell;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class CellView extends JLabel {
	public CellView(String gfx) {
		setIcon(new ImageIcon("gfx" + File.separator + gfx));
		setOpaque(true);
		setBackground(new Color(116, 226, 31));
	}
}
