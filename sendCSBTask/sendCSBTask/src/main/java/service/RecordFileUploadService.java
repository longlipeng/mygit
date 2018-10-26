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
 * 1 ���ܴ�������
 * 2 ��װ����ͷ
 * 3 ��������
 */
@Service
public class RecordFileUploadService {
	/**
	 * ����ļ��ϴ�״̬
	 */
	private static Logger logger=LoggerFactory.getLogger(RecordFileUploadService.class);
	public String recordFileUploadState(String uploadFlowNo) throws Exception{
		//CSBUtil st = new CSBUtil();
		String uniqueNo="";		
		String sendJsonData = "{\"dataMap\":{\"uploadFlowNo\":\"" + uploadFlowNo+"\"}}";
		HashMap map = (HashMap) new InitCorpMsg().initCopInfo(uniqueNo).get(0);
		// ��ȡ�������
		String result= sendCSB((String) map.get("ak"), (String) map.get("sk"), (String) map.get("url"),
					(String) map.get("privateKey"), (String) sendJsonData, (String) map.get("uniqueNo"),
					"recordFileUploadService");
		return result;
	
	}
	
	public static String sendCSB(String ak, String sk, String url, String privateKey, String jsonData,
			String uniqueNo, String api_name) throws Exception {
		// ��ȡ�������
		String symkey = BASE64Util.getRandomkey();
		// ������������json��
		String jsonDataEncrypt = BASE64Util.encryptAES("" + symkey, jsonData);
		// ���������Կ
		String subPasswordEncrpt = BASE64Util.encryptRSA("" + symkey, BASE64Util.getPrivateKey(privateKey));
		String result = "";
		// ����csb�еķ���
		HttpParameters.Builder builder = new HttpParameters.Builder();
		// builderƴ���ַ������ڷ�������
		builder.api(api_name).version("1.0.0").requestURL(url).method("POST").accessKey(ak).secretKey(sk)
				.putParamsMap("dataMap", "{\"dataMap\":{\"uniqueNo\":\"" + uniqueNo + "\",\"symmetricKeyEncrpt\":\""
						+ subPasswordEncrpt + "\",\"jsonDataEncrypt\":\"" + jsonDataEncrypt + "\"}}");
		// ���ڲ鿴��csb�ԱȺ���
		result = HttpCaller.invoke(builder.build());
		result = HttpCaller.changeCharset(result);//��CSB�������ݽ������ñ���Ϊ��UTF-8��
		System.out.println("�鿴csb���:------" + result);
		return result;
	}
/*	public static void main(String[] args) {
		String logPF="src/main/resources/log4j.properties";	
		PropertyConfigurator.configureAndWatch(logPF, 6000);
		CopProPertiesUtil.init();
		try {
			String result=new RecordFileUploadService().recordFileUploadState("201806240519581000001472");// 201806051212181000009078
			logger.info("�ϴ��ļ�����ˮ��Ϊ��>>>>>"+"201806120915381000011357"+"�ϴ��Ľ��Ϊ��>>>>>>>"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
