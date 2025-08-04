package raven.datetime;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelDateMoh extends JPanel {

	private final DatePicker datePicker;
	private boolean disableChange;

	public PanelDateMoh(DatePicker datePicker) {
		this.datePicker = datePicker;
		init();
	}

	private void init() {
		putClientProperty(FlatClientProperties.STYLE, "" + "background:null");
		buttonGroup = new ButtonGroup();
	}

	public void installDateOptionLabel(String today, String yesterday, String last_week, String last_month,
			String last_season, String last_year) {
		removeAll();
		PanelDateOptionLabel panelDateOptionLabel = datePicker.getPanelDateOptionLabel();
		if (panelDateOptionLabel == null) {
			panelDateOptionLabel = createDefaultPanelDateOptionLabel(today, yesterday, last_week, last_month,
					last_season, last_year);
		}
		String layoutRowConstraints = "";
		List<PanelDateOptionLabel.Item> items = panelDateOptionLabel.getListItems();
		for (int i = 0; i < items.size(); i++) {
			PanelDateOptionLabel.Item item = items.get(i);
			add(createButton(item.getLabel(), item.getCallback()));
			if (item.getCallback() == null) {
				layoutRowConstraints += "push";
			} else {
				layoutRowConstraints += "[]";
			}
		}
		layoutRowConstraints += "[]";
		setLayout(new MigLayout("wrap,insets 5,fillx", "[fill]", layoutRowConstraints));
		add(new JSeparator(SwingConstants.VERTICAL), "dock west,height 100%", 0);
		repaint();
		revalidate();
	}

	public void installDateOptionLabel() {
		removeAll();
		PanelDateOptionLabel panelDateOptionLabel = datePicker.getPanelDateOptionLabel();
		if (panelDateOptionLabel == null) {
			panelDateOptionLabel = createDefaultPanelDateOptionLabel(DatePicker.getDefaultPanel()[0],
					DatePicker.getDefaultPanel()[1], DatePicker.getDefaultPanel()[2], DatePicker.getDefaultPanel()[3],
					DatePicker.getDefaultPanel()[4], DatePicker.getDefaultPanel()[5]);
		}
		String layoutRowConstraints = "";
		List<PanelDateOptionLabel.Item> items = panelDateOptionLabel.getListItems();
		for (int i = 0; i < items.size(); i++) {
			PanelDateOptionLabel.Item item = items.get(i);
			add(createButton(item.getLabel(), item.getCallback()));
			if (item.getCallback() == null) {
				layoutRowConstraints += "push";
			} else {
				layoutRowConstraints += "[]";
			}
		}
		layoutRowConstraints += "[]";
		setLayout(new MigLayout("wrap,insets 5,fillx", "[fill]", layoutRowConstraints));
		add(new JSeparator(SwingConstants.VERTICAL), "dock west,height 100%", 0);
		repaint();
		revalidate();
	}

	private JToggleButton createButton(String name, PanelDateOptionLabel.LabelCallback callback) {
		JToggleButton button = new JToggleButton(name);
		button.setHorizontalAlignment(SwingConstants.LEADING);
		if (callback == null) {
			button.setName("custom");
		}
		button.addActionListener(e -> {
			disableChange = true;
			boolean isEnable = datePicker.isEnabled();
			if (callback == null) {
				if (isEnable) {
					datePicker.clearSelectedDate();
				}
			} else {
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
		button.putClientProperty(FlatClientProperties.STYLE, "" + "arc:10;" + "borderWidth:0;" + "focusWidth:0;"
				+ "innerFocusWidth:0;" + "margin:4,10,4,10;" + "background:null");
		buttonGroup.add(button);
		return button;
	}

	public void setSelectedCustom() {
		if (!disableChange) {
			JToggleButton customButton = null;
			for (Component com : getComponents()) {
				String name = com.getName();
				if (name != null && name.equals("custom")) {
					customButton = (JToggleButton) com;
					break;
				}
			}
			if (customButton != null) {
				customButton.setSelected(true);
			}
		}
		disableChange = false;
	}

	private PanelDateOptionLabel createDefaultPanelDateOptionLabel(String today, String yesterday, String last_week,
			String last_month, String last_season, String last_year) {
		PanelDateOptionLabel defaultPanelDateOptionLabel = new PanelDateOptionLabel();
		defaultPanelDateOptionLabel.add(today, PanelDateOptionLabel.LabelCallback.TODAY);
		defaultPanelDateOptionLabel.add(yesterday, PanelDateOptionLabel.LabelCallback.YESTERDAY);
		defaultPanelDateOptionLabel.add(last_week, PanelDateOptionLabel.LabelCallback.LAST_7_DAYS);
		defaultPanelDateOptionLabel.add(last_month, PanelDateOptionLabel.LabelCallback.LAST_30_DAYS);
		defaultPanelDateOptionLabel.add(last_season, PanelDateOptionLabel.LabelCallback.LAST_SEASON);
		defaultPanelDateOptionLabel.add(last_year, PanelDateOptionLabel.LabelCallback.LAST_YEAR_DATE);

		return defaultPanelDateOptionLabel;
	}

	private ButtonGroup buttonGroup;
}
