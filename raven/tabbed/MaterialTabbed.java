package raven.tabbed;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class MaterialTabbed extends JTabbedPane {

	public MaterialTabbed() {
		setUI(new MaterialTabbedUI());
	}

	public class MaterialTabbedUI extends MetalTabbedPaneUI {

		private Animator animator;
		private Rectangle currentRectangle;
		private TimingTarget target;
		private int hoverIndex = -1;

		public MaterialTabbedUI() {

		}

		public void setCurrentRectangle(Rectangle currentRectangle) {
			this.currentRectangle = currentRectangle;
			repaint();
		}

		@Override
		public void installUI(JComponent jc) {
			super.installUI(jc);
			animator = new Animator(500);
			animator.setResolution(0);
			animator.setAcceleration(.5f);
			animator.setDeceleration(.5f);
			tabPane.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					int selected = tabPane.getSelectedIndex();
					if (selected != -1) {
						if (currentRectangle != null) {
							if (animator.isRunning()) {
								animator.stop();
							}
							animator.removeTarget(target);
							target = new PropertySetter(MaterialTabbedUI.this, "currentRectangle", currentRectangle,
									getTabBounds(selected, calcRect));
							animator.addTarget(target);
							animator.start();
						}
					}
				}
			});
			// Add mouse listeners for hover effect
			tabPane.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					int newHoverIndex = tabForCoordinate(tabPane, e.getX(), e.getY());
					if (newHoverIndex != hoverIndex) {
						hoverIndex = newHoverIndex;
						tabPane.repaint();
					}
				}
			});

			tabPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					hoverIndex = -1;
					tabPane.repaint();
				}
			});

		}

		@Override
		protected Insets getTabInsets(int i, int i1) {
			return new Insets(10, 10, 10, 10);
		}

		@Override
		protected void paintTabBorder(Graphics grphcs, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
			Graphics2D g2 = (Graphics2D) grphcs.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(3, 155, 216));
			if (currentRectangle == null || !animator.isRunning()) {
				if (isSelected) {
					currentRectangle = new Rectangle(x, y, w, h);
				}
			}
			if (currentRectangle != null) {
				if (tabPlacement == TOP) {
					g2.fillRect(currentRectangle.x, currentRectangle.y + currentRectangle.height - 3,
							currentRectangle.width, 3);
				} else if (tabPlacement == BOTTOM) {
					g2.fillRect(currentRectangle.x, currentRectangle.y, currentRectangle.width, 3);
				} else if (tabPlacement == LEFT) {
					g2.fillRect(currentRectangle.x + currentRectangle.width - 3, currentRectangle.y, 3,
							currentRectangle.height);
				} else if (tabPlacement == RIGHT) {
					g2.fillRect(currentRectangle.x, currentRectangle.y, 3, currentRectangle.height);
				}
			}
			g2.dispose();
		}

		@Override
		protected void paintContentBorder(Graphics grphcs, int tabPlacement, int selectedIndex) {
			Graphics2D g2 = (Graphics2D) grphcs.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(200, 200, 200));
			Insets insets = getTabAreaInsets(tabPlacement);
			int width = tabPane.getWidth();
			int height = tabPane.getHeight();
			if (tabPlacement == TOP) {
				int tabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
				g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
			} else if (tabPlacement == BOTTOM) {
				int tabHeight = height - calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
				g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
			} else if (tabPlacement == LEFT) {
				int tabWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
				g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
			} else if (tabPlacement == RIGHT) {
				int tabWidth = width - calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - 1;
				g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
			}
			g2.dispose();
		}

		@Override
		protected void paintFocusIndicator(Graphics grphcs, int i, Rectangle[] rctngls, int i1, Rectangle rctngl,
				Rectangle rctngl1, boolean bln) {
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
			Color color = tabPane.getBackgroundAt(tabIndex);
			if (!isSelected)
				if (tabIndex == hoverIndex) {
					color = color.darker();
				}

			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(color);

			if (tabPlacement == TOP) {
				g2.fillRect(x, y, w, h - 3);
			} else if (tabPlacement == BOTTOM) {
				g2.fillRect(x, y + 3, w, h - 3);
			} else {
				g2.fillRect(x, y, w, h);
			}

			g2.dispose();
		}
	}
}
