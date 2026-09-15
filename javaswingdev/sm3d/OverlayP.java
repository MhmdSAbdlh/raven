package javaswingdev.sm3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

// Create a custom overlay panel
public class OverlayP extends JPanel {
	private float alpha = 0f; // Starting alpha (0% transparency)
	private Color overlayColor = new Color(255, 255, 255); // Default color: white

	public OverlayP() {
		setOpaque(false);
		setDoubleBuffered(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int alphaVal = Math.max(0, Math.min(255, (int) (alpha * 255)));
		g2d.setColor(new Color(overlayColor.getRed(), overlayColor.getGreen(), overlayColor.getBlue(), alphaVal));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.dispose();
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint(); // Repaint the panel with updated alpha
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setOverlayColor(Color color) {
		this.overlayColor = color; // Update the overlay color
		repaint(); // Repaint the panel with the new color
	}

	public Color getOverlayColor() {
		return this.overlayColor;
	}

	@Override
	public boolean isShowing() {
		return alpha > 0 && super.isShowing();
	}
}
