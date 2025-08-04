package print;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager {

	private static ReportManager instance;

	public static ReportManager getInstance() {
		if (instance == null) {
			instance = new ReportManager();
		}
		return instance;
	}

	private JasperReport reportPay;

	private ReportManager() {
	}

	public void compileReport() throws JRException {
		reportPay = JasperCompileManager.compileReport(getClass().getResourceAsStream("/print/report_pay.jrxml"));
	}

	public JasperReport getReport() {
		return reportPay;
	}

	public JasperPrint generateReportPayment(ParameterReportPayment data) throws JRException {
		Map<String, Object> para = new HashMap<>();
		para.put("clientName", data.getClientName());
		para.put("clientID", data.getClienntID());
		para.put("sellerName", data.getSellerName());
		para.put("total", data.getTotal());
		para.put("totalR", data.getTotalR());
		para.put("hora", data.geHora());
		para.put("fecha", data.getFecha());
		para.put("qrcode", data.getQrcode());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date parsed;
		String vencimiento = "";
		try {
			parsed = sdf.parse(data.getFecha());
			Calendar cal = Calendar.getInstance();
			cal.setTime(parsed);
			cal.add(Calendar.YEAR, 1);
			vencimiento = sdf.format(cal.getTime());
		} catch (ParseException e) {
		}

		para.put("vencimiento", vencimiento);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
		return JasperFillManager.fillReport(reportPay, para, dataSource);
	}

	public void printReportPayment(ParameterReportPayment data) throws JRException {
		JasperPrint print = generateReportPayment(data);
		view(print); // Keep existing behavior (optional)
	}

	private void view(JasperPrint print) throws JRException {
		JasperViewer.viewReport(print, false);
	}
}
