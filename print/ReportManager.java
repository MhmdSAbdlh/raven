package print;

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

	public void printReportPayment(ParameterReportPayment data) throws JRException {
		Map<String, Object> para = new HashMap<>();
		para.put("staff", data.getStaff());
		para.put("customer", data.getCustomer());
		para.put("total", data.getTotal());
		para.put("totalR", data.getTotalR());
		para.put("hora", data.geHora());
		para.put("fecha", data.getFecha());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
		JasperPrint print = JasperFillManager.fillReport(reportPay, para, dataSource);
		view(print);
	}

	private void view(JasperPrint print) throws JRException {
		JasperViewer.viewReport(print, false);
	}
}
