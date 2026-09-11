package raven.card;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CardItem extends JPanel {

	private final String redB = "arc:8;" + "border:2,5,2,5;" + "background:fade(#f43f5e,10%);"
			+ "foreground:#f43f5e;",
			greenB = "arc:8;" + "border:2,5,2,5;" + "background:fade(#1f9669,10%);"
					+ "foreground:#1f9669;";

	public CardItem(Icon icon, String title) {
		init(icon, title);
	}

	private void init(Icon icon, String title) {
		setLayout(new MigLayout("hidemode 3,wrap", "15 push[] 15 push"));
		putClientProperty(FlatClientProperties.STYLE, "" + "background:null;");

		lbTitle = new JLabel(title, icon, JLabel.LEADING);
		lbValue = new JLabel("0");
		lbDescription = new JLabel("description");
		lbTags = new JLabel("0%");

		lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:$Label.disabledForeground;");
		lbValue.putClientProperty(FlatClientProperties.STYLE, "" + "font:+8;");

		lbDescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:$Label.disabledForeground;");
		
		lbTags.putClientProperty(FlatClientProperties.STYLE, greenB);

		add(lbTitle);
		add(lbValue);
		add(lbDescription, "split 2");
		add(lbTags);
	}

	public void setValue(String value, String description, String tags, boolean up) {
		lbValue.setText(value);
		lbDescription.setText(description);
		lbTags.setText(tags);
		lbTags.putClientProperty(FlatClientProperties.STYLE, up ? greenB : redB);
		lbDescription.setVisible(description != null);
		lbTags.setVisible(tags != null);
	}

	public void setCardIconColor(Color color) {
		if (lbTitle.getIcon() != null && lbTitle.getIcon() instanceof FlatSVGIcon) {
			FlatSVGIcon icon = (FlatSVGIcon) lbTitle.getIcon();
			icon.getColorFilter().setMapper(_ -> color);
			repaint();
		}
	}

	private JLabel lbTitle;
	private JLabel lbValue;
	private JLabel lbDescription;
	private JLabel lbTags;
}
