package com.huateng.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ReportUtil;
import com.huateng.framework.util.base64zip.MsgTransUtil;
import com.huateng.service.BizService;
import com.huateng.service.ServiceSubject;

public class IreportAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dtoJson;

	private ServiceSubject serviceSubject;
	private static final String XLS = "XLS";
	private static final String PDF = "PDF";
	private static final String HTML = "HTML";
	private static final String WORD = "WORD";
	private static final String JASPER = ".jasper";

	public String viewReport() throws Exception {
		Map<String, String> map;
		try {
			// 取得主要参数
			map = getParamters();
			// 获取jasper文件地址
//			String path = this.getClass().getClassLoader().getResource("/")
//					.getPath();
//			logger.info("path"+path);
//			path = path.substring(0, path.lastIndexOf("/") - 16);
			
			//用于websphere
			String path = this.getClass().getResource("").getPath();
//			logger.info("classPath"+path);
			path = path.substring(0, path.lastIndexOf("/") - 35);
			
			path = path
					+ "/ireport/"
					+ serviceSubject.getReportNameMap().get(
							map.get("reportName")) + JASPER;
			String reportChinessName = map.get("reportFileName");
		    long start = System.currentTimeMillis();
			// 得到填充内容
		    logger.info("--报表查询SQL开始--");
		    logger.info("--报表名称--"+map.get("reportName"));
			List<Object> list = genList(map.get("reportName"));
			long end = System.currentTimeMillis();
			logger.info(reportChinessName + "查询的时间：" + (end - start));
			logger.info("--报表查询SQL结束--");
			// 下载格式
			String reportType = map.get("reportType");
			// 得到补充参数
			map = moreParamters(map.get("reportName"), map);

			// 根据下载格式调用对应报表方法
			if (reportType == null) {
				throw new BizServiceException("错误的报表格式");
			}
			if (XLS.equals(reportType.toUpperCase())) {
			    logger.info("--数据导出到Excel开始--");
			    long start1 = System.currentTimeMillis();
				ReportUtil.reportExcel(path, map, list);
				long end1 = System.currentTimeMillis();
				logger.info(reportChinessName + "数据导出到Excel的时间：" + (end1 - start1));
				logger.info("--数据导出到Excel结束--");
			} else if (PDF.equals(reportType.toUpperCase())) {
				ReportUtil.reportPdf(path, map, list);
			} else if (HTML.equals(reportType.toUpperCase())) {
				ReportUtil.reportHtml(path, map, list);
			} else if (WORD.equals(reportType.toUpperCase())) {
				ReportUtil.reportWord(path, map, list);
			} else {
				throw new BizServiceException("错误的报表格式");
			}
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

		return NONE;
	}

	/**
	 * 调用内容
	 * 
	 * @param reportName
	 *            报表别名
	 * @return
	 */
	public List<Object> genList(String reportName) {
		BizService service = serviceSubject.getServiceMap().get(reportName);
		List<Object> list = service.getList(JSONObject.fromObject(dtoJson));
		return list;
	}

	/**
	 * 加入更多参数. 非可共用方法.
	 * 
	 * @param reportName
	 *            报表别名
	 * @param paramters
	 *            参数集
	 * @return
	 */
	private Map<String, String> moreParamters(String reportName,
			Map<String, String> paramters) {
		BizService service = serviceSubject.getServiceMap().get(reportName);
		Map<String, String> resultParamters = service.setParamters(paramters);
		return resultParamters;
	}

	/**
	 * 获取全部参数. 从serviceSubjct中获取对应的dto如果不存在,则按parValue数组存入
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getParamters() throws Exception {
		JSONObject jo = JSONObject.fromObject(dtoJson);
		Map<String, String> paramters = (Map<String, String>) JSONObject
				.toBean(jo, Map.class);
		return paramters;
	}

	/************************* setter getter ****************************/
	public String getDtoJson() {
		return dtoJson;
	}

	public void setDtoJson(String dtoJson) throws Exception {
		// 字符集转换.
		this.dtoJson = new String(MsgTransUtil.decodeInflate(dtoJson.getBytes("utf-8")), "UTF-8");
	}

	public ServiceSubject getServiceSubject() {
		return serviceSubject;
	}

	public void setServiceSubject(ServiceSubject serviceSubject) {
		this.serviceSubject = serviceSubject;
	}
}
