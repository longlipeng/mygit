package com.huateng.system.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

import com.huateng.sdk.SDKConfig;
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.FsasService;
import com.huateng.sdk.LogUtil;

public class BackRcvServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载fsas_sdk.properties文件

		LogUtil.writeLog("Form_5_17_QuotaSettlmentNotice接收通知开始");

		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParamStream(req);
		LogUtil.printRequestLog(reqParam);

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!FsasService.validate(reqParam, DemoBase.encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			
			String respCode = reqParam.get("respCode"); //获取后台通知的数据，其他字段也可用类似方式获取
			LogUtil.writeLog("respCode=" + respCode);
		}
		LogUtil.writeLog("Form_5_17_QuotaSettlmentNotice接收通知结束");
		//返回给银联服务器http 200状态码
		resp.getWriter().print("ok");
		String str = "{'txnType':'01'}";
		System.out.println(str);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	/**
	  * 获取请求参数中所有的信息。
	  * 非struts可以改用此方法获取。
	  * struts可能对某些content-type会提前读取参数导致从inputstream读不到信息，所以可能用不了这个方法。理论应该可以调整struts配置使不影响，但请自己去研究。
	  * 调用本方法之前不能调用req.getParameter("key");这种方法，否则会导致request取不到输入流。
	  * @param request
	  * @return
	  */
	public static Map<String, String> getAllRequestParamStream(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		try {
			String notifyStr = new String(IOUtils.toByteArray(request.getInputStream()), DemoBase.encoding);
			LogUtil.writeLog("收到通知报文：" + notifyStr);
			JSONObject jObj = JSONObject.fromObject(notifyStr);
			for(Iterator<String> iter = jObj.keys(); iter.hasNext(); ){
				String key = iter.next();
				String value = jObj.getString(key);
				res.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.writeLog("getAllRequestParamStream.UnsupportedEncodingException error: " + e.getClass() + ":"
					+ e.getMessage());
		} catch (IOException e) {
			LogUtil.writeLog("getAllRequestParamStream.IOException error: " + e.getClass() + ":" + e.getMessage());
		}
		return res;
	}
	

}
