package print;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
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

	private String client_name, client_id, seller;

	private String fecha, hora;
	private static final byte[] TRANSPARENT_1X1_PNG = Base64.getDecoder()
			.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");

	public ParameterReportPayment() {
	}

	public ParameterReportPayment(String client_name, String client_id, List<FieldReportPayment> fields, String fecha,
			String hora, String seller) {
		this.client_name = client_name;
		this.client_id = client_id;
		this.seller = seller;
		this.fields = fields;
		this.hora = hora;
		this.fecha = fecha;
	}

	public String geHora() {
		return hora;
	}

	public String getClienntID() {
		return client_id;
	}

	public String getFecha() {
		return fecha;
	}

	public List<FieldReportPayment> getFields() {
		return fields;
	}

	public String getClientName() {
		return client_name;
	}

	public String getSellerName() {
		return seller;
	}

	public InputStream getQrcode() {
		try {
			return generateQrcode();
		} catch (Exception e) {
			e.printStackTrace();
			return new ByteArrayInputStream(TRANSPARENT_1X1_PNG); // Safe fallback
		}
	}

	public String getTotal() {
		double tot = 0;
		for (FieldReportPayment report : fields)
			tot += report.getTotal();
		return String.format("%.2f", tot);
	}

	public String getTotalR() {
		double tot = 0;
		for (FieldReportPayment report : fields)
			tot += report.getTotal();
		return "R$" + String.format("%.2f", tot * 5);
	}

	public void setClientID(String client_id) {
		this.client_id = client_id;
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

	public void setClientName(String client_name) {
		this.client_name = client_name;
	}

	public void setSellerName(String seller) {
		this.seller = seller;
	}

	private String buildDgiUrl() {
		String baseUrl = "https://efactura.dgi.gub.uy/consultaQR";

		// Get your company's RUT (must be registered with DGI)
		String rutEmpresa = "216547290011"; // Replace with your company's RUT

		// Generate unique document ID (follow DGI specifications)
		String serie = "A"; // Your document series
		String numero = getClienntID(); // 10-digit document number

		// Format amounts according to DGI requirements
		String montoTotal = getTotal();

		// Get current date in DGI format (YYYYMMDD)
		String fecha = new SimpleDateFormat("yyyyMMdd").format(new Date());

		// Build the URL with required parameters
		return String.format("%s?rut=%s&serie=%s&numero=%s&fechaEmision=%s&montoTotal=%s", baseUrl, rutEmpresa, serie,
				numero, fecha, montoTotal);
	}

	private InputStream generateQrcode() throws WriterException, IOException {
		String invoice = buildDgiUrl();
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.MARGIN, 1);

		// Generate matrix (adjust size if needed)
		BitMatrix bitMatrix = new MultiFormatWriter().encode(invoice, BarcodeFormat.QR_CODE, 100, 100, hints);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream); // Direct ZXing method
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
}
