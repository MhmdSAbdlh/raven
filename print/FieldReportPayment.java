package print;

public class FieldReportPayment {

	private String name;

	private double price;

	private int qty;

	private double total;

	public FieldReportPayment() {
	}

	public FieldReportPayment(String name, int qty, double price, double total) {
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.total = total;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQty() {
		return qty;
	}

	public double getTotal() {
		return total;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
