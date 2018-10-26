package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpParameters;

import basic.BASE64Util;
import net.sf.json.JSONObject;

/**
 * ����csb����
 * 
 * @author dc
 */
@SuppressWarnings("all")
public class CSBUtil {
	private Logger logger=LoggerFactory.getLogger(CSBUtil.class);
	/**
	 * �Դ���õ��ļ����ݽ��м��ܣ�������CSB����
	 */
	
	public String sendCSB(String ak, String sk, String url, String privateKey, String jsonData,
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
		//��CSB�������ݽ������ñ���Ϊ��UTF-8��
		//result = HttpCaller.changeCharset(result);
		logger.info("���ļ��ӿ�csb���ؽ��Ϊ:-------" + result);
		//System.out.println("�鿴csb���:----" + result);
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
		//�����ļ��ϴ���ˮ�� �¸��ļ�ȥ����
	}
}
