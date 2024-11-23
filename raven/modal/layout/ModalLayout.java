package raven.modal.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;

import raven.modal.option.LayoutOption;

/**
 * @author Raven
 */
public class ModalLayout implements LayoutManager {

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    private Component component;
    private final LayoutOption layoutOption;
    private float animate;

    public ModalLayout(Component component, LayoutOption layoutOption) {
        this.component = component;
        this.layoutOption = layoutOption;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return new Dimension(0, 0);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return new Dimension(0, 0);
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            if (component != null && component.isVisible()) {
                Rectangle rec = OptionLayoutUtils.getLayoutLocation(parent, component, animate, layoutOption);
                component.setBounds(rec.x, rec.y, rec.width, rec.height);
            }
        }
    }

    public Dimension getComponentSize(Container parent, Component component, Insets margin) {
        Insets insets = FlatUIUtils.addInsets(parent.getInsets(), UIScale.scale(layoutOption.getMargin()));
        int width = parent.getWidth() - (insets.left + insets.right + margin.left + margin.right);
        int height = parent.getHeight() - (insets.top + insets.bottom + margin.top + margin.bottom);
        return OptionLayoutUtils.getComponentSize(component, width, height, 1f, layoutOption);
    }
}
