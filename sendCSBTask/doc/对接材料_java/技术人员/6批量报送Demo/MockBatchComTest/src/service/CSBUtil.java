package service;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpParameters;

import basic.BASE64Util;

/**
 * 调用csb服务
 * 
 * @author dc
 */
public class CSBUtil {
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
		System.out.println("查看csb结果:----" + result);
		int flwnoPos = result.indexOf("uploadFlowNo");
		String flwno = result.substring(flwnoPos + 3 + "uploadFlowNo".length(),
				flwnoPos + 3 + "uploadFlowNo".length() + 24);
		return flwno;
	}
}
