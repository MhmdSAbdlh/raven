package raven.modal.drawer.menu;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import raven.modal.drawer.renderer.AbstractDrawerLineStyleRenderer;
import raven.modal.drawer.renderer.DrawerCurvedLineStyle;

/**
 * @author Raven
 */
public class MenuStyle {

    public AbstractDrawerLineStyleRenderer getDrawerLineStyleRenderer() {
        return drawerLineStyleRenderer;
    }

    public void setDrawerLineStyleRenderer(AbstractDrawerLineStyleRenderer drawerLineStyleRenderer) {
        this.drawerLineStyleRenderer = drawerLineStyleRenderer;
    }

    private AbstractDrawerLineStyleRenderer drawerLineStyleRenderer = new DrawerCurvedLineStyle();

    public void styleMenu(JComponent component) {
    }

    public void styleMenuPanel(JPanel panel, int[] index) {
    }

    public void styleMenuItem(JButton menu, int[] index, boolean isMainItem) {
    }

    public void styleLabel(JLabel label) {
    }

    public void styleSeparator(JSeparator separator) {
    }
}
