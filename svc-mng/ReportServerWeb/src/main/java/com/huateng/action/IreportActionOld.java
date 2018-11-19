package com.huateng.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLDecoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * 当全部替换后. 删除此类.
 * @author wpf
 *
 */
@Deprecated
public class IreportActionOld extends BaseAction  implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3732986158120383089L;

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String viewReport() throws Exception {
		try {
			Connection conn = dataSource.getConnection();
			// 获得发布路径
			String[] pars = URLDecoder.decode(getRequest().getParameter("par"), "UTF-8").split(";");


			if (pars == null || pars.length < 5) {
				throw new Exception("报表传参有误！");
			}
			String reportName = pars[0];
			String reportType = pars[1];
			// 指定jasper文件
			String path = this.getClass().getClassLoader().getResource("/")
					.getPath();
			path = path.substring(0, path.lastIndexOf("/") - 16);
			File file = new File(path + "/ireport/" + reportName + ".jasper");
			// 是传参数，其中Pid 要与表中定义的参数一致就是map 中的key 键
			Map parameters = this.parameters(pars);
			print(file, parameters, conn, reportType);
			conn.close();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return NONE;
	}

	public Map parameters(String[] pars) {
		Map parameters = new HashMap();
		if (pars.length > 2) {
			parameters.put("issuerId", pars[2]);
			parameters.put("issuerName", pars[3]);
			parameters.put("reportFileName", pars[4]);
			for (int i = 0; i < pars.length - 5; i++) {
				parameters.put("key" + (i + 1), pars[i + 5]);
			}
		} else {
			parameters = null;
		}
		return parameters;
	}

	public void print(File file, Map parameters, Connection conn, String type)
			throws Exception {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(file
					.getPath(), parameters, conn);
			// 创建输出流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Xls格式的导出器 JRXlsAbstractExport
			JRExporter exporter = new JRXlsExporter();
			// 传入参数将jasperPrint传入导出器
			exporter
					.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			// 输出流为baos
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			// 导出
			exporter.exportReport();
			// 开始解析
			byte[] bytes = baos.toByteArray();
			if (bytes != null && bytes.length > 0) {
				HttpServletResponse response = (HttpServletResponse) ServletActionContext
						.getContext().get(ServletActionContext.HTTP_RESPONSE);
				response.reset();
				response.setCharacterEncoding("UTF-8");
				Date date = new Date();
				if (type.equals("xls")) {

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddhhmmss");

					response.setHeader("Content-Type",
							"application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment;filename="
									+ new String((parameters
											.get("reportFileName")
											+ "_"
											+ parameters.get("issuerName")
											+ "_" + sdf.format(date) + ".xls")
											.getBytes("gb2312"), "ISO8859-1"));
				} else {
					response.setHeader("Content-Type", "application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename="
									+ URLDecoder.decode(parameters.get(
											"reportFileName").toString(),
											"UTF-8") + "_"
									+ parameters.get("issuerId") + ".pdf");
				}
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();

			}
		} catch (JRException jre){
			logger.error("JRException:" + jre.getMessage());
		}
	}

	public String init() throws Exception {
		return INPUT;
	}
}
