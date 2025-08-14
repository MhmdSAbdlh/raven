package raven.datetime.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArcLegendItem extends JPanel {
    public ArcLegendItem(LabelDate labelDate, String name) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        add(new ArcIcon(labelDate));
        add(new JLabel(name));
    }

    private static class ArcIcon extends JComponent {
        private final LabelDate labelDate;

        public ArcIcon(LabelDate labelDate) {
            this.labelDate = labelDate;
            setPreferredSize(new Dimension(30,30)); // adjust size if needed
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawArc(g2, labelDate.getColor(), labelDate.getValue(), getWidth());
            g2.dispose();
        }
    }
    private static void drawArc(Graphics2D g2, Color color, float value, int size) {
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        Rectangle2D.Float rec = new Rectangle2D.Float(1, 1, size - 2, size - 2);
        g2.draw(new Arc2D.Float(rec, 90, 360 * value, Arc2D.OPEN));
    }

}

