package raven.datetime;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class PanelDateRenderer extends JPanel {

	private final DatePicker datePicker;

	public PanelDateRenderer(DatePicker datePicker) {
		this.datePicker = datePicker;
		init();
	}

	private void init() {
		putClientProperty(FlatClientProperties.STYLE, "background:null");
	}

	public void installDateOptionLabel(String half_day, String full_day, String absc, String licen, String holiday,
			String weekend) {
		removeAll();
		PanelDateOptionLabel panelDateOptionLabel = datePicker.getPanelDateOptionLabel();
		if (panelDateOptionLabel == null) {
			panelDateOptionLabel = createDefaultPanelDateOptionLabel(half_day, full_day, absc, licen, holiday, weekend);
		}
		addLabels(panelDateOptionLabel);
	}

	public void installDateOptionLabel() {
		removeAll();
		PanelDateOptionLabel panelDateOptionLabel = datePicker.getPanelDateOptionLabel();
		if (panelDateOptionLabel == null) {
			panelDateOptionLabel = createDefaultPanelDateOptionLabel(DatePicker.getDefaultPanel()[0],
					DatePicker.getDefaultPanel()[1], DatePicker.getDefaultPanel()[2], DatePicker.getDefaultPanel()[3],
					DatePicker.getDefaultPanel()[4], DatePicker.getDefaultPanel()[5]);
		}
		addLabels(panelDateOptionLabel);
	}

	private void addLabels(PanelDateOptionLabel panelDateOptionLabel) {
		List<PanelDateOptionLabel.Item> items = panelDateOptionLabel.getListItems();
		for (PanelDateOptionLabel.Item item : items) {
			add(createClickableLabel(item.getLabel(), item.getCallback()));
		}
		setLayout(new MigLayout("wrap,insets 5,fillx", "[fill]"));
		add(new JSeparator(SwingConstants.VERTICAL), "dock west,height 100%", 0);
		repaint();
		revalidate();
	}

	private JLabel createClickableLabel(String name, PanelDateOptionLabel.LabelCallback callback) {
		JLabel label = new JLabel(name);
		label.putClientProperty(FlatClientProperties.STYLE, "font: +1; background:null");
		label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10)); // padding effect

		if (callback != null) {
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					boolean isEnable = datePicker.isEnabled();
					LocalDate[] dates = callback.getDate();
					if (dates.length == 0) {
						throw new IllegalArgumentException("Date option is empty so can't be select");
					}
					boolean singleDate = dates.length == 1
							|| datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED;
					if (isEnable) {
						if (singleDate) {
							datePicker.setSelectedDate(dates[0]);
						} else {
							datePicker.setSelectedDateRange(dates[0], dates[1]);
						}
					} else {
						datePicker.slideTo(dates[0]);
					}
				}
			});
		}
		return label;
	}

	public void setSelectedCustom() {
		// Labels do not have a "selected" state, so this can be ignored or used to
		// style a label if needed
	}

	private PanelDateOptionLabel createDefaultPanelDateOptionLabel(String half_day, String full_day, String absc,
			String licen, String holiday, String weekend) {
		PanelDateOptionLabel defaultPanelDateOptionLabel = new PanelDateOptionLabel();
		defaultPanelDateOptionLabel.add(half_day);
		defaultPanelDateOptionLabel.add(full_day);
		defaultPanelDateOptionLabel.add(absc);
		defaultPanelDateOptionLabel.add(licen);
		defaultPanelDateOptionLabel.add(holiday);
		defaultPanelDateOptionLabel.add(weekend);
		return defaultPanelDateOptionLabel;
	}
}
