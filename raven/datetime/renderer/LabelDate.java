package raven.datetime.renderer;

import java.awt.*;

public enum LabelDate {

    WEEKEND("Weekend", new Color(44, 69, 213), 0.9f), // blue light
    WEEKLY_FULL("Weekly Full", new Color(59, 155, 60), 1f), // green light
    WEEKLY_HALF("Weekly Half", new Color(213, 104, 41), 0.5f), // orange
    HOLIDAY("Vacation", new Color(231, 219, 140), 1f), //yellow
    LICENSE("Mostly Free", new Color(198, 140, 231), 0.8f), //purple
    ABSCENT("Abscent", new Color(239, 138, 138), 0.35f), // red
    BIRTHDAY("BIRTHDAY", new Color(188, 186, 107), 0.65f), // YELLOW
    BILLS("BILLS", new Color(188, 107, 107), 0.75f); // red

    private final String name;
    private final Color color;
    private final float value;

    LabelDate(String name, Color color, float value) {
        this.name = name;
        this.color = color;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public float getValue() {
        return value;
    }
}
