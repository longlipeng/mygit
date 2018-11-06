package com.huateng.univer.report.action;

import java.net.URLEncoder;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.IreportDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.Config;
import com.huateng.framework.util.base64zip.MsgTransUtil;

/**
 * 报表处理
 */
public class NewIreportAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	// 报表所需要的参数
	private IreportDTO ireportDTO;

	public IreportDTO getIreportDTO() {
		return ireportDTO;
	}

	public void setIreportDTO(IreportDTO ireportDTO) {
		this.ireportDTO = ireportDTO;
	}

	public String viewReport() throws Exception {
		// 得到dto的json值
		JSONObject jo = getJSONOBJect();
		String dtoJson = jo.toString();
		// 对json压缩并encode
		byte[] b = MsgTransUtil.deflateEncode(dtoJson.getBytes("utf-8"));
		dtoJson = URLEncoder.encode(new String(b, "utf-8"), "utf-8");

		// 跳转到下载
		System.out.println("报表地址:" + Config.getIreportURL());
		String url =Config.getIreportURL();
		//String url = "http://127.0.0.1:8080/ReportServerWeb/viewReport.action";
		getResponse().sendRedirect(url + "?dtoJson=" + dtoJson);
		return NONE;
	}

	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(ireportDTO);
	}

}
