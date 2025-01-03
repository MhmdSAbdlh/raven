package raven.datetime.component.date;

import java.awt.Component;
import java.time.LocalDate;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.PanelDateOptionLabel.Item;

public class PanelDateOption extends JPanel {

    private final DatePicker datePicker;
    private boolean disableChange;

    public PanelDateOption(DatePicker datePicker) {
        this.datePicker = datePicker;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        buttonGroup = new ButtonGroup();
    }

    public void installDateOptionLabel() {
        removeAll();
        PanelDateOptionLabel panelDateOptionLabel = datePicker.getPanelDateOptionLabel();
        if (panelDateOptionLabel == null) {
            panelDateOptionLabel = createDefaultPanelDateOptionLabel();
        }
        String layoutRowConstraints = "";
        List<PanelDateOptionLabel.Item> items = panelDateOptionLabel.getListItems();
        for (Item item : items) {
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
            if (callback == null) {
                datePicker.clearSelectedDate();
            } else {
                LocalDate[] dates = callback.getDate();
                if (dates.length == 0) {
                    throw new IllegalArgumentException("Date option is empty so can't be select");
                }
                boolean singleDate = dates.length == 1 || datePicker.getDateSelectionMode() == DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED;
                if (singleDate) {
                    datePicker.setSelectedDate(dates[0]);
                } else {
                    datePicker.setSelectedDateRange(dates[0], dates[1]);
                }
            }
        });
        button.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:10;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "margin:4,10,4,10;" +
                "background:null");
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

    private PanelDateOptionLabel createDefaultPanelDateOptionLabel() {
        PanelDateOptionLabel defaultPanelDateOptionLabel = new PanelDateOptionLabel();
        defaultPanelDateOptionLabel.add("Today", PanelDateOptionLabel.LabelCallback.TODAY);
        defaultPanelDateOptionLabel.add("Yesterday", PanelDateOptionLabel.LabelCallback.YESTERDAY);
        defaultPanelDateOptionLabel.add("Last 7 Days", PanelDateOptionLabel.LabelCallback.LAST_7_DAYS);
        defaultPanelDateOptionLabel.add("Last 30 Days", PanelDateOptionLabel.LabelCallback.LAST_30_DAYS);
        defaultPanelDateOptionLabel.add("This Month", PanelDateOptionLabel.LabelCallback.THIS_MONTH);
        defaultPanelDateOptionLabel.add("Last Month", PanelDateOptionLabel.LabelCallback.LAST_MONTH);
        defaultPanelDateOptionLabel.add("Last YEAR", PanelDateOptionLabel.LabelCallback.LAST_YEAR);
        defaultPanelDateOptionLabel.add("Custom", PanelDateOptionLabel.LabelCallback.CUSTOM);

        return defaultPanelDateOptionLabel;
    }

    private ButtonGroup buttonGroup;
}
