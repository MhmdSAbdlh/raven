package print;

import java.util.List;

public class ParameterReportPayment {

	private String customer;

	private List<FieldReportPayment> fields;

	private String staff;

	private String total, totalR, fecha, hora;

	public ParameterReportPayment() {
	}

	public ParameterReportPayment(String staff, String customer, String total, String totalR,
			List<FieldReportPayment> fields, String fecha, String hora) {
		this.staff = staff;
		this.customer = customer;
		this.total = total;
		this.totalR = totalR;
		this.fields = fields;
		this.hora = hora;
		this.fecha = fecha;
	}

	public String geHora() {
		return hora;
	}

	public String getCustomer() {
		return customer;
	}

	public String getFecha() {
		return fecha;
	}

	public List<FieldReportPayment> getFields() {
		return fields;
	}

	public String getStaff() {
		return staff;
	}

	public String getTotal() {
		return total;
	}

	public String getTotalR() {
		return totalR;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setFields(List<FieldReportPayment> fields) {
		this.fields = fields;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setTotalR(String totalR) {
		this.totalR = totalR;
	}
}
