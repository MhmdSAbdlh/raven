package abed;

import java.io.InputStream;
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

	public void compileReport(String url) throws JRException {
		reportPay = JasperCompileManager.compileReport(getClass().getResourceAsStream(url));
	}

	public JasperReport getReport() {
		return reportPay;
	}

	public JasperPrint generateReportPayment(ParameterReportPayment data) throws JRException {
		Map<String, Object> para = new HashMap<>();
		para.put("total", data.getTotal());
		para.put("totalP", data.getTotalP());
		para.put("totalD", data.getTotalD());
		para.put("fecha", data.getFecha());
		para.put("hora", data.getHora());
		para.put("invoiceN", data.getInvoiceNumber());
		InputStream logoStream = getClass().getResourceAsStream("/abed/manhattan.png");
		if (logoStream == null) {
			throw new IllegalStateException("Logo not found in resources: /abed/manhattan.png");
		}
		para.put("logo", logoStream);
		para.put("qrcodeC", data.getQrcode(true));
		para.put("qrcodeH", data.getQrcode(false));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date parsed;
		String vencimiento = "", garentia = "";
		try {
			parsed = sdf.parse(data.getFecha());
			Calendar cal = Calendar.getInstance();
			cal.setTime(parsed);
			cal.add(Calendar.YEAR, 2);
			vencimiento = sdf.format(cal.getTime());
			cal = Calendar.getInstance();
			cal.setTime(parsed);
			cal.add(Calendar.MONTH, 1);
			garentia = sdf.format(cal.getTime());
		} catch (ParseException e) {
		}
		para.put("vencimiento", vencimiento);
		para.put("garentia", garentia);
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
