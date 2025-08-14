package raven.datetime.renderer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.formdev.flatlaf.util.UIScale;

import raven.datetime.DatePicker;
import raven.datetime.component.date.ButtonDate;
import raven.datetime.component.date.DefaultDateCellRenderer;
import raven.datetime.component.date.SingleDate;

public class CustomDateCellRenderer extends DefaultDateCellRenderer {

	private List<DateStatus> list = new ArrayList<DateStatus>();
	private Map<LocalDate, String> tooltips;

	public CustomDateCellRenderer(Map<LocalDate, LabelDate> dates) {
		for (Entry<LocalDate, LabelDate> dateM : dates.entrySet()) {
			LocalDate date = dateM.getKey();
			LabelDate label = dateM.getValue();
			DateStatus dateS = new DateStatus(date, label);
			list.add(dateS);
		}
	}

	public CustomDateCellRenderer(Map<LocalDate, LabelDate> dates, Map<LocalDate, String> tooltips) {
		this.tooltips = tooltips;
		for (Entry<LocalDate, LabelDate> dateM : dates.entrySet()) {
			LocalDate date = dateM.getKey();
			LabelDate label = dateM.getValue();
			DateStatus dateS = new DateStatus(date, label);
			list.add(dateS);
		}
	}

	@Override
	public void paint(Graphics2D g2, DatePicker datePicker, ButtonDate component, SingleDate date, float width,
			float height) {
		super.paint(g2, datePicker, component, date, width, height);
		LabelDate labelDate = getLabelDate(date.toLocalDate());
		if (labelDate != null) {
			g2.setColor(labelDate.getColor());

			// use rendering hint to control draw strokes line
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2.setStroke(new BasicStroke(UIScale.scale(getSelectedFocusWidth()), BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_MITER));
			Rectangle2D.Float rec = getRectangle(width, height);
			g2.draw(new Arc2D.Float(rec, 90, 360 * labelDate.getValue(), Arc2D.OPEN));
		}
		// Set tooltip text if exists
		if (tooltips != null) {
			String tooltipText = tooltips.get(date.toLocalDate());
			component.setToolTipText(tooltipText);
		}
	}

	private LabelDate getLabelDate(LocalDate date) {
		for (DateStatus d : list) {
			if (d.getDate().equals(date)) {
				return d.getLabel();
			}
		}
		return null;
	}

	private class DateStatus {

		public LocalDate getDate() {
			return date;
		}

		@SuppressWarnings("unused")
		public void setDate(LocalDate date) {
			this.date = date;
		}

		public LabelDate getLabel() {
			return label;
		}

		@SuppressWarnings("unused")
		public void setLabel(LabelDate label) {
			this.label = label;
		}

		public DateStatus(LocalDate date, LabelDate label) {
			this.date = date;
			this.label = label;
		}

		private LocalDate date;
		private LabelDate label;
	}
}