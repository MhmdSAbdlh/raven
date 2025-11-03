package abed;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ParameterReportPayment {

	private List<FieldReportPayment> fields;
	private double cambio;
	private int invoiceN;
	private String fecha, hora;
	private String client_name, client_id;
	private int totalS, gastos, agregado, totalC, totalSP, gastosP, agregadoP, totalCP, pix;
	private static final byte[] TRANSPARENT_1X1_PNG = Base64.getDecoder()
			.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");

	public ParameterReportPayment() {
	}

	public ParameterReportPayment(String client_name, String client_id, List<FieldReportPayment> fields, String fecha,
			String hora, double cambio, int invoiceN) {
		this.client_name = client_name;
		this.client_id = client_id;
		this.fields = fields;
		this.fecha = fecha;
		this.cambio = cambio;
		this.hora = hora;
		this.invoiceN = invoiceN;
	}

	public ParameterReportPayment(int totalS, int gastos, int agregado, int totalC, int totalSP, int gastosP,
			int agregadoP, int totalCP, int pix) {
		this.totalS = totalS;
		this.totalC = totalC;
		this.gastos = gastos;
		this.agregado = agregado;
		this.totalSP = totalSP;
		this.totalCP = totalCP;
		this.gastosP = gastosP;
		this.agregadoP = agregadoP;
		this.pix = pix;
	}

	public String getClientName() {
		return client_name;
	}

	public String getClienntID() {
		return client_id;
	}

	public double getCambio() {
		return cambio;
	}

	public String getInvoiceNumber() {
		return "A " + invoiceN;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public List<FieldReportPayment> getFields() {
		return fields;
	}

	public InputStream getQrcode(boolean type) {
		try {
			return generateQrcode(type);
		} catch (Exception e) {
			e.printStackTrace();
			return new ByteArrayInputStream(TRANSPARENT_1X1_PNG); // Safe fallback
		}
	}

	public String getTotal() {
		double tot = 0;
		if (fields != null)
			for (FieldReportPayment report : fields)
				tot += report.getTotal();
		return String.format("%.2f", tot);
	}

	public String getTotalP() {
		double tot = 0;
		if (fields != null)
			for (FieldReportPayment report : fields)
				tot += report.getTotal();
		return String.format("%.0f", tot * cambio);
	}

	public String getTotalD() {
		double tot = 0;
		if (fields != null)
			for (FieldReportPayment report : fields)
				tot += report.getTotal();
		return String.format("%.2f", tot / 5);
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setFields(List<FieldReportPayment> fields) {
		this.fields = fields;
	}

	public void setClientID(String client_id) {
		this.client_id = client_id;
	}

	public void setClientName(String client_name) {
		this.client_name = client_name;
	}

	private String buildDgiUrl() {
		return "https://www.efactura.dgi.gub.uy/consultaQR/"
				+ "cfe?150985690014,101,A,134,280.00,22/09/2025,FaxvukHDUBeRVyHMmVOBfvKRVPDyLbisU9Oh5N2X5xY%3d";

	}

	private InputStream generateQrcode(boolean type) throws WriterException, IOException {
		String invoice = type ? buildDgiUrl() : "https://www.instagram.com/haditech.lb";
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.MARGIN, 1);

		// Generate matrix (adjust size if needed)
		BitMatrix bitMatrix = new MultiFormatWriter().encode(invoice, BarcodeFormat.QR_CODE, 100, 100, hints);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream); // Direct ZXing method
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	// EXPORT DAY SUMMARY
	public int getTotalSale() {
		return totalS;
	}

	public int getTotalC() {
		return totalC;
	}

	public int getGastos() {
		return gastos;
	}

	public int getAgregados() {
		return agregado;
	}

	public int getTotalSaleP() {
		return totalSP;
	}

	public int getTotalCP() {
		return totalCP;
	}

	public int getGastosP() {
		return gastosP;
	}

	public int getAgregadosP() {
		return agregadoP;
	}

	public int getPix() {
		return pix;
	}

}
