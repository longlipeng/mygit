package com.huateng.framework.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.framework.util.DateUtil;

/**
 * 这个类用来做jasperReport报表的导出.
 */
@SuppressWarnings("unchecked")
public class ReportUtil {
	
	private static Logger logger = Logger.getLogger(ReportUtil.class);

	/**
	 * 导出报表excel.
	 * 
	 * @param path
	 *            jasper文件路径
	 * @param parameters
	 *            jasper文件参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */

	public static void reportExcel(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = new FileInputStream(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((parameters.get("reportFileName") + "_"
						+ parameters.get("issuerName") + "_"
						+ DateUtil.getCurrentTime() + ".xls").getBytes("GBK"),
						"ISO8859-1"));
		OutputStream out = response.getOutputStream();
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,
				Boolean.FALSE);
		exporter.exportReport();
		out.flush();
		out.close();
	}

	/**
	 * 导出报表 pdf.
	 * 
	 * @param path
	 *            jasper文件路径
	 * @param parameters
	 *            jasper文件需要的参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportPdf(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = new FileInputStream(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.reset();
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		response
				.addHeader("Content-Disposition", "inline; filename=report.pdf");
		OutputStream out = response.getOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				"UniGB-UCS2-H");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
	}

	/**
	 * 导出Word
	 * 
	 * @param path
	 *            the jasper文件路径
	 * @param parameters
	 *            jasper参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportWord(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = new FileInputStream(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.reset();
		response.setContentType("application/rtf");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition",
				"attachment; filename=report.rtf");
		OutputStream out = response.getOutputStream();
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				"UniGB-UCS2-H");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
	}

	/**
	 * 导出HTML
	 * 
	 * @param path
	 *            the jasper文件路径
	 * @param parameters
	 *            jasper参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportHtml(String path, Map parameters, List list)
			throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = response.getWriter();
		InputStream ins = new FileInputStream(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				"<HR>");
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.TRUE);

		request.getSession().setAttribute(
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
				jasperPrint);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				"./imageForJasper?image=");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				"<HR>");
		exporter.setParameter(
				JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);

		exporter.exportReport();
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 导出html,去掉一些自动去空格的参数
	 * 
	 * @param path
	 *            the path
	 * @param parameters
	 *            the parameters
	 * @param list
	 *            the list
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportNewHtml(String path, Map parameters, List list)
			throws Exception {
		HttpServletResponse response = (HttpServletResponse) ServletActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = response.getWriter();
		InputStream ins = new FileInputStream(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		JRHtmlExporter exporter = new JRHtmlExporter();

		request.getSession().setAttribute(
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
				jasperPrint);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				"./imageForJasper?image=");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "pt");

		exporter.exportReport();
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Exception e){
				logger.error(e.getMessage());
			}
		}
		if (ins != null) {
			ins.close();
		}
	}

	// public static void main(String[] args) {
	//
	// logger.info(ReportUtil.class.getClassLoader());
	// logger.info(new
	// ReportUtil().getClass().getClassLoader());
	// logger.info(ReportUtil.class.getClassLoader().equals(
	// new ReportUtil().getClass().getClassLoader()));
	// }
}
