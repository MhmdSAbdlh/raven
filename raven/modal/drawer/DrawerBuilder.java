package raven.modal.drawer;

import java.awt.Component;

import raven.modal.option.Option;

/**
 * @author Raven
 */
public interface DrawerBuilder {

    void build(DrawerPanel drawerPanel);

    Component getHeader();

    Component getHeaderSeparator();

    Component getMenu();

    Component getFooter();

    Option getOption();

    int getDrawerWidth();

    int getDrawerCompactWidth();

    int getOpenDrawerAt();

    boolean openDrawerAtScale();

    void drawerOpenChanged(boolean isOpen);
}
