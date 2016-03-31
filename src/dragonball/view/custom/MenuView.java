package dragonball.view.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dragonball.view.game.GameView;

@SuppressWarnings("serial")
public class MenuView extends JLayeredPane {
	private static MenuView instance;

	private ArrayList<Menu> menus;
	private Point origin;

	public static MenuView getInstance() {
		if (instance == null) {
			instance = new MenuView();
		}
		return instance;
	}

	private MenuView() {
		menus = new ArrayList<>();
		origin = new Point(10, 10);

		setVisible(false);
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(int x, int y) {
		setOrigin(new Point(x, y));
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Menu addMenu() {
		final Menu menu = new Menu();
		menu.addListener(new Menu.Listener() {
			@Override
			public void onItemAdded(Menu.Item item) {
				layoutMenus();
			}
		});
		add(menu);
		menus.add(menu);

		return menu;
	}

	public void removeMenu(Menu menu) {
		removeMenu(menus.indexOf(menu));
	}

	public void removeMenusAfter(Menu menu) {
		removeMenu(menus.indexOf(menu) + 1);
	}

	public void removeMenu(int index) {
		for (int i = index; i >= 0 && i < menus.size();) {
			remove(menus.remove(i));
		}
		layoutMenus();
	}

	public void removeAllMenus() {
		menus.clear();
		removeAll();
		layoutMenus();
		setVisible(false);
	}

	private void layoutMenus() {
		int menuX = (int) (origin.getX() + 2);
		int menuY = (int) (origin.getY() - 2);

		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			setLayer(menu, i);

			Dimension menuSize = menu.getPreferredSize();
			if (menuSize.getWidth() + 20 > getSize().getWidth()) {
				menuSize.setSize(getSize().getWidth() - 20, menuSize.getHeight());
			}
			if (menuSize.getHeight() + 20 > getSize().getHeight()) {
				menuSize.setSize(menuSize.getWidth(), getSize().getHeight() - 20);
			}
			menu.setSize(menuSize);

			int newMenuX = (int) Math.min(menuX, getSize().getWidth() - 10 - menu.getSize().getWidth());
			int newMenuY = (int) Math.max(menuY - menu.getSize().getHeight(), 10);
			menu.setLocation(newMenuX, newMenuY);

			menuX += menu.getSize().getWidth() * 0.25;
			menuY -= 10;
		}

		repaint();
		validate();

		setVisible(true);
	}

	public static class Menu extends JPanel {
		private Set<Listener> listeners = new HashSet<>();
		private ArrayList<Item> items;
		private JPanel contentPane;

		public Menu() {
			items = new ArrayList<>();
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));
			setOpaque(false);

			contentPane = new JPanel(new GridLayout(0, 1));
			contentPane.setBorder(null);
			JScrollPane scrollPane = new JScrollPane(contentPane);
			scrollPane.setBorder(null);
			scrollPane.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL) {
				@Override
				public boolean isVisible() {
					return true;
				}
			});
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			add(scrollPane, BorderLayout.CENTER);
		}

		public Item addItem(String text) {
			return addItem(text, null);
		}

		public Item addItem(String text, Item.Listener listener) {
			final Item item = new Item(this, text);
			if (listener != null) {
				item.addListener(listener);
			}
			contentPane.add(item);
			items.add(item);

			notifyListenersOnItemAdded(item);

			return item;
		}

		public void resetAllItems() {
			for (Item item : items) {
				item.setSelected(false);
			}
		}

		public void addListener(Listener listener) {
			listeners.add(listener);
		}

		public void notifyListenersOnItemAdded(Item item) {
			for (Listener listener : listeners) {
				listener.onItemAdded(item);
			}
		}

		@Override
		public Dimension getPreferredSize() {
			int preferredWidth = 0;
			int preferredHeight = 0;

			for (Item item : items) {
				Dimension size = item.getPreferredSize();
				int height = (int) size.getHeight();
				int width = (int) size.getWidth();
				if (width > preferredWidth) {
					preferredWidth = width;
				}
				if (height > preferredHeight) {
					preferredHeight = height;
				}
			}
			return new Dimension(preferredWidth, preferredHeight * items.size() + 14);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			graphics.setColor(Color.BLACK);
			graphics.fillRoundRect(0, 0, width, height, 15, 15);
			graphics.setColor(Color.WHITE);
			graphics.fillRoundRect(5, 5, width - 10, height - 10, 10, 10);
		}

		public interface Listener extends EventListener {
			void onItemAdded(Item item);
		}

		public static class Item extends JButton {
			private Menu menu;
			private Set<Listener> listeners = new HashSet<>();
			private Object defaultButtonSelectColor;

			public Item(Menu menu, String text) {
				super(text);
				setFocusable(false);
				setPreferredSize(new Dimension(120, 40));
				setOpaque(true);
				setBackground(getBackgroundColor());
				setForeground(getForegroundColor());
				setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				setRolloverEnabled(true);
				setFont(GameView.DEFAULT_FONT.deriveFont(14f));
				setHorizontalAlignment(LEFT);

				this.menu = menu;
				defaultButtonSelectColor = UIManager.getColor("Button.select");

				getModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						UIManager.put("Button.select", defaultButtonSelectColor);
						ButtonModel model = (ButtonModel) e.getSource();
						if (!listeners.isEmpty() && (model.isRollover() || model.isSelected())) {
							UIManager.put("Button.select", getForegroundColor());
							setBackground(getForegroundColor());
							setForeground(getBackgroundColor());
						} else {
							setBackground(getBackgroundColor());
							setForeground(getForegroundColor());
						}
					}
				});
			}

			public Menu getMenu() {
				return menu;
			}

			public Color getForegroundColor() {
				return Color.BLACK;
			}

			public Color getBackgroundColor() {
				return Color.WHITE;
			}

			public void addListener(Listener listener) {
				listeners.add(listener);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (menu != null) {
							MenuView menuView = MenuView.getInstance();
							menuView.removeMenusAfter(menu);
						}

						if (isSelected()) {
							setSelected(false);
							return;
						} else {
							if (menu != null) {
								menu.resetAllItems();
							}
							setSelected(true);
						}

						notifyListenersOnAction();
					}
				});
			}

			public void notifyListenersOnAction() {
				for (Listener listener : listeners) {
					listener.onAction(this);
				}
			}

			public interface Listener extends EventListener {
				void onAction(Item item);
			}
		}
	}
}
