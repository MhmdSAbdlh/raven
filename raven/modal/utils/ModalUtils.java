package raven.modal.utils;

import java.awt.Insets;

/**
 * @author Raven
 */
public class ModalUtils {

    public static int maximumInsets(Insets insets) {
        int maxTopBottom = Math.max(insets.top, insets.bottom);
        int maxLeftRight = Math.max(insets.left, insets.right);
        return Math.max(maxTopBottom, maxLeftRight);
    }

    public static int minimumInsets(Insets insets) {
        int maxTopBottom = Math.min(insets.top, insets.bottom);
        int maxLeftRight = Math.min(insets.left, insets.right);
        return Math.min(maxTopBottom, maxLeftRight);
    }
}
