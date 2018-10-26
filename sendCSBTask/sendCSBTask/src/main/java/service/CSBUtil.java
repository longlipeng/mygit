package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpParameters;

import basic.BASE64Util;
import net.sf.json.JSONObject;

/**
 * 调用csb服务
 * 
 * @author dc
 */
@SuppressWarnings("all")
public class CSBUtil {
	private Logger logger=LoggerFactory.getLogger(CSBUtil.class);
	/**
	 * 对处理好的文件数据进行加密，并发送CSB请求
	 */
	
	public String sendCSB(String ak, String sk, String url, String privateKey, String jsonData,
			String uniqueNo, String api_name) throws Exception {
		// 获取随机密码
		String symkey = BASE64Util.getRandomkey();
		// 用随机密码加密json串
		String jsonDataEncrypt = BASE64Util.encryptAES("" + symkey, jsonData);
		// 加密随机密钥
		String subPasswordEncrpt = BASE64Util.encryptRSA("" + symkey, BASE64Util.getPrivateKey(privateKey));
		String result = "";
		// 请求csb中的服务
		HttpParameters.Builder builder = new HttpParameters.Builder();
		// builder拼接字符串用于发送请求
		builder.api(api_name).version("1.0.0").requestURL(url).method("POST").accessKey(ak).secretKey(sk)
				.putParamsMap("dataMap", "{\"dataMap\":{\"uniqueNo\":\"" + uniqueNo + "\",\"symmetricKeyEncrpt\":\""
						+ subPasswordEncrpt + "\",\"jsonDataEncrypt\":\"" + jsonDataEncrypt + "\"}}");
		// 用于查看和csb对比后结果
		result = HttpCaller.invoke(builder.build());
		//对CSB返回数据进行设置编码为“UTF-8”
		//result = HttpCaller.changeCharset(result);
		logger.info("传文件接口csb返回结果为:-------" + result);
		//System.out.println("查看csb结果:----" + result);
		int flwnoPos = result.indexOf("uploadFlowNo");
		String flwno = result.substring(flwnoPos + 3 + "uploadFlowNo".length(),
				flwnoPos + 3 + "uploadFlowNo".length() + 24);
		JSONObject resultbody=JSONObject.fromObject(result);
		JSONObject body=(JSONObject)resultbody.get("body");
		String errorCode=(String)body.get("errorCode");
		if(errorCode.equals("0")){
		return flwno;
		}else{}
		return "";
		//返回文件上传流水号 下个文件去发送
	}
}
