package print;

import java.text.DecimalFormat;

public class ModelItemSell {

	private double price;

	private int productId;

	private String productName;

	private int qty;

	private double total;

	public ModelItemSell() {
	}

	public ModelItemSell(int productId, String productName, int qty, double price, double total) {
		this.productId = productId;
		this.productName = productName;
		this.qty = qty;
		this.price = price;
		this.total = total;
	}

	public double getPrice() {
		return price;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getQty() {
		return qty;
	}

	public double getTotal() {
		return total;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Object[] toTableRow(int rowNum) {
		DecimalFormat df = new DecimalFormat("##0.0");
		return new Object[] { this, df.format(rowNum), productName, df.format(qty), "$ " + df.format(price),
				"$ " + df.format(total)};
	}
}
