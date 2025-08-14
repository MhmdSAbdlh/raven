package raven.datetime;

import javax.swing.*;
import java.awt.*;

public class LabelWithCirce extends JLabel {
    private Color circleColor;
    private float arcValue; // from 0.0 to 1.0 for how much circle arc to draw

    public LabelWithCirce(String text, Color circleColor, float arcValue) {
        super(text);
        this.circleColor = circleColor;
        this.arcValue = arcValue;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4)); // left padding for circle space
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (circleColor != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int diameter = 12; // circle diameter in pixels
            int y = (getHeight() - diameter) / 2;
            int x = 4; // left padding

            g2.setColor(circleColor);
            g2.setStroke(new BasicStroke(2f));
            // Draw partial arc circle based on arcValue
            g2.drawArc(x, y, diameter, diameter, 90, (int)(360 * arcValue));

            g2.dispose();
        }
    }
}
