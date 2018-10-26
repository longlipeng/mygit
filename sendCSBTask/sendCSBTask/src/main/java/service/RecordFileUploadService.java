package service;

import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpParameters;

import basic.BASE64Util;
import basic.InitCorpMsg;

/**
 * 
 * @author taojiajun
 * 1 加密传送数据
 * 2 组装请求头
 * 3 发送数据
 */
@Service
public class RecordFileUploadService {
	/**
	 * 检查文件上传状态
	 */
	private static Logger logger=LoggerFactory.getLogger(RecordFileUploadService.class);
	public String recordFileUploadState(String uploadFlowNo) throws Exception{
		//CSBUtil st = new CSBUtil();
		String uniqueNo="";		
		String sendJsonData = "{\"dataMap\":{\"uploadFlowNo\":\"" + uploadFlowNo+"\"}}";
		HashMap map = (HashMap) new InitCorpMsg().initCopInfo(uniqueNo).get(0);
		// 获取随机密码
		String result= sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
					(String) map.get("privateKey"), (String) sendJsonData, (String) map.get("uniqueNo"),
					"recordFileUploadService");
		return result;
	
	}
	
	public static String sendCSB(String ak, String sk, String url, String privateKey, String jsonData,
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
		result = HttpCaller.changeCharset(result);//对CSB返回数据进行设置编码为“UTF-8”
		System.out.println("查看csb结果:------" + result);
		return result;
	}
/*	public static void main(String[] args) {
		String logPF="src/main/resources/log4j.properties";	
		PropertyConfigurator.configureAndWatch(logPF, 6000);
		CopProPertiesUtil.init();
		try {
			String result=new RecordFileUploadService().recordFileUploadState("201806240519581000001472");// 201806051212181000009078
			logger.info("上传文件的流水号为：>>>>>"+"201806120915381000011357"+"上传的结果为：>>>>>>>"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
