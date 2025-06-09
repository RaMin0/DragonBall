package dragonball.view.custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import dragonball.view.game.GameView;

@SuppressWarnings("serial")
public class HudView extends JTextPane {
	public static final int LINE_WIDTH = 23;
	public static final int NUM_LINES = 3;

	private ArrayList<String> textPages;
	private boolean animating;
	private boolean withAnimation;
	private boolean skipAnimation;
	private Callback callback;
	private static HudView instance;

	public static HudView getInstance() {
		if (instance == null) {
			instance = new HudView();
		}
		return instance;
	}

	private HudView() {
		textPages = new ArrayList<>();

		setMargin(new Insets(18, 18, 18, 18));
		setEditable(false);
		setFocusable(false);
		setDragEnabled(false);
		setOpaque(false);
		setBackground(null);
		setVisible(false);
		setFont(GameView.DEFAULT_FONT);

		DefaultCaret caret = new DefaultCaret() {
			@Override
			protected synchronized void damage(Rectangle r) {
				if (r == null) {
					return;
				}

				x = r.x;
				y = r.y;
				height = r.height;

				if (width <= 0) {
					width = getComponent().getWidth();
				}

				repaint();
			}

			public void paint(Graphics g) {
				JTextComponent component = getComponent();
				if (component == null) {
					return;
				}

				int dot = getDot();
				Rectangle r = null;
				char dotChar = ' ';
				try {
					Rectangle2D r2 = component.modelToView2D(dot);
					if (r2 == null) {
						return;
					}
					r = r2.getBounds();
					dotChar = component.getText(dot, 1).charAt(0);
				} catch (BadLocationException e) {
					return;
				}

				if ((x != r.x) || (y != r.y)) {
					repaint();
					x = r.x;
					y = r.y;
					height = r.height;
				}

				g.setColor(component.getCaretColor());
				g.setXORMode(component.getBackground());

				if (isVisible()) {
					width = g.getFontMetrics().charWidth(' ');
					g.fillRect(r.x, r.y, width / (dotChar == '\n' ? 2 : 1), r.height);
				}
			}
		};
		caret.setBlinkRate(500);
		setCaret(caret);
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private void prepareTextPages(String text) {
		String[] textLines = text.split("\n");
		ArrayList<String> lines = new ArrayList<>();

		for (int i = 0; i < textLines.length; i++) {
			if (textLines[i].length() > LINE_WIDTH) {
				int trimLength = Math.min(textLines[i].length(), LINE_WIDTH);
				if ((lines.size() + 1) % NUM_LINES == 0) {
					lines.add(textLines[i].substring(0, trimLength - 3) + "...");
					textLines[i] = "..." + textLines[i].substring(trimLength - 3);
				} else {
					lines.add(textLines[i].substring(0, trimLength));
					textLines[i] = textLines[i].substring(trimLength);
				}
				i--;
			} else {
				lines.add(textLines[i]);
			}
		}

		while (!lines.isEmpty()) {
			String textPage = "";
			for (int i = 0; i < NUM_LINES && !lines.isEmpty(); i++) {
				textPage += lines.remove(0);
				if (!lines.isEmpty()) {
					textPage += "\n";
				}
			}
			textPages.add(textPage);
		}
	}

	public void nextTextPage() {
		setVisible(true);

		if (animating) {
			skipAnimation = true;
			return;
		}

		if (textPages.isEmpty()) {
			setVisible(false);
			if (callback != null) {
				Callback thisCallback = callback;
				callback = null;
				thisCallback.onComplete();
			}
		} else {
			if (withAnimation) {
				animating = true;
				new Thread(new Runnable() {
					@Override
					public void run() {
						String textPage = textPages.remove(0);
						for (int i = 0; i <= textPage.length(); i++) {
							if (skipAnimation) {
								i = textPage.length();
								skipAnimation = false;
							}
							HudView.super.setText(textPage.substring(0, i));
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						animating = false;
						if (textPages.isEmpty() && callback != null) {
							callback.onLastPage();
						}
					}
				}).start();
			} else {
				super.setText(textPages.remove(0));
				if (textPages.isEmpty() && callback != null) {
					callback.onLastPage();
				}
			}
		}
	}

	@Override
	public void setText(String text) {
		setText(text, null);
	}

	public void setText(String text, Callback callback) {
		setText(text, true, callback);
	}

	public void setText(String text, boolean animate, Callback callback) {
		prepareTextPages(text);
		withAnimation = animate;
		if (callback != null) {
			this.callback = callback;
		}

		nextTextPage();
	}

	public void setTextWithInput(String text, final InputCallback callback) {
		setText(text, new Callback() {
			@Override
			public void onLastPage() {
				HudView.getInstance().nextTextPage();

				final String lastText = getText();
				setVisible(true);
				setEditable(true);
				setFocusable(true);

				for (CaretListener caretListener : getCaretListeners()) {
					removeCaretListener(caretListener);
				}
				setCaretPosition(lastText.length());
				addCaretListener(new CaretListener() {
					@Override
					public void caretUpdate(CaretEvent e) {
						if (e.getDot() < lastText.length()) {
							setCaretPosition(lastText.length());
						}
					}
				});
				requestFocus();

				final AbstractDocument abstractDocument = (AbstractDocument) getDocument();
				abstractDocument.setDocumentFilter(new DocumentFilter() {
					@Override
					public void remove(DocumentFilter.FilterBypass filterBypass, int offset, int length)
							throws BadLocationException {
						replace(filterBypass, offset, length, "", null);
					}

					@Override
					public void replace(DocumentFilter.FilterBypass filterBypass, int offset, int length, String text,
							AttributeSet attrs) throws BadLocationException {
						Document document = filterBypass.getDocument();
						Element root = document.getDefaultRootElement();
						int count = root.getElementCount();
						int index = root.getElementIndex(offset);
						int promptPosition = lastText.length();

						if (index == count - 1 && offset - promptPosition >= 0) {
							if (text.equals("\n")) {
								setVisible(false);
								setEditable(false);
								setFocusable(false);
								abstractDocument.setDocumentFilter(null);

								if (callback != null) {
									String input = document.getText(promptPosition, offset - promptPosition);
									callback.onComplete(input);
								}
							} else if (text.equals("") || text.toUpperCase().matches("[A-Z0-9 ]+")) {
								filterBypass.replace(offset, length, text, attrs);
							}
						}
					}
				});
			}

			@Override
			public void onComplete() {

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(Color.BLACK);
		graphics.fillRoundRect(0, 0, width, height, 15, 15);
		graphics.setColor(Color.WHITE);
		graphics.fillRoundRect(5, 5, width - 10, height - 10, 10, 10);

		super.paintComponent(g);
	}

	public abstract static class Callback {
		public void onLastPage() {

		}

		public abstract void onComplete();
	}

	public interface InputCallback {
		void onComplete(String input);
	}
}
