package raven.datetime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PanelDateOptionLabel {

	private final List<Item> listItems = new ArrayList<>();

	public PanelDateOptionLabel() {
	}

	public void add(String label, LabelCallback callback) {
		listItems.add(new Item(label, callback));
	}
	
	public void add(String label) {
		listItems.add(new Item(label));
	}

	public List<Item> getListItems() {
		return listItems;
	}

	@SuppressWarnings("unused")
	private void remove(int index) {
		listItems.remove(index);
	}

	@SuppressWarnings("unused")
	private void clear() {
		listItems.clear();
	}

	public static class Item {

		public String getLabel() {
			return label;
		}

		public LabelCallback getCallback() {
			return callback;
		}

		public Item(String label, LabelCallback callback) {
			this.label = label;
			this.callback = callback;
		}
		
		public Item(String label) {
			this.label = label;
			this.callback = null;
		}

		private final String label;
		private final LabelCallback callback;
	}

	public interface LabelCallback {

		LabelCallback TODAY = () -> new LocalDate[] { LocalDate.now() };
		LabelCallback YESTERDAY = () -> new LocalDate[] { LocalDate.now().minusDays(1) };
		LabelCallback LAST_7_DAYS = () -> new LocalDate[] { LocalDate.now().minusDays(7), LocalDate.now() };
		LabelCallback LAST_30_DAYS = () -> new LocalDate[] { LocalDate.now().minusDays(30), LocalDate.now() };
		LabelCallback LAST_SEASON = () -> new LocalDate[] { LocalDate.now().minusDays(90), LocalDate.now() };
		LabelCallback LAST_YEAR_DATE = () -> new LocalDate[] { LocalDate.now().minusYears(1), LocalDate.now() };
		LabelCallback THIS_MONTH = () -> {
			LocalDate now = LocalDate.now();
			return new LocalDate[] { now.withDayOfMonth(1), now.withDayOfMonth(now.lengthOfMonth()) };
		};
		LabelCallback LAST_MONTH = () -> {
			LocalDate lastMonth = LocalDate.now().minusMonths(1);
			return new LocalDate[] { lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()) };
		};
		LabelCallback LAST_YEAR = () -> {
			LocalDate lastYear = LocalDate.now().minusYears(1).withDayOfYear(1);
			return new LocalDate[] { lastYear, lastYear.withDayOfYear(lastYear.lengthOfYear()) };
		};

		LabelCallback CUSTOM = null;

		LocalDate[] getDate();
	}
}
