package com.huateng.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;




/**
 * @ClassName FsasService
 * @Description fsassdk接口服务类，接入商户集成请可以直接参考使用本类中的方法
 * @date 2016-7-22 下午2:44:37
 * 声明：以下代码只是为了方便机构测试而提供的样例代码，机构可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class FsasService {

	/**
	 * 请求报文签名(使用配置文件中配置的私钥证书或者对称密钥签名)<br>
	 * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
	 * @param reqData 请求报文map<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return　签名后的map对象<br>
	 */
	public static Map<String, String> sign(Map<String, String> reqData,String encoding) {
		reqData = SDKUtil.filterBlank(reqData);
		SDKUtil.sign(reqData, encoding);
		return reqData;
	}
	
	
	/**
	 * 验证签名(SHA-1摘要算法)<br>
	 * @param resData 返回报文数据<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return true 通过 false 未通过<br>
	 */
	public static boolean validate(Map<String, String> rspData, String encoding) {
		return SDKUtil.validate(SDKUtil.filterBlank(rspData), encoding);
	}
	
	
	/**
	 * 功能：后台交易提交请求报文并接收同步应答报文<br>
	 * @param reqData 请求报文<br>
	 * @param rspData 应答报文<br>
	 * @param reqUrl  请求地址<br>
	 * @param encoding<br>
	 * @return 应答http 200返回true ,其他false<br>
	 */
	public static Map<String,String> doPost(
			Map<String, String> reqData,String reqUrl,String encoding) {
		Map<String, String> rspData = new HashMap<String,String>();
		LogUtil.writeLog("请求银联地址:" + reqUrl);
		//发送后台请求数据
		HttpClient hc = new HttpClient(reqUrl, 30000, 30000);//连接超时时间，读超时时间（可自行判断，修改）
		try {
			int status = hc.sendJsonString(reqData, encoding);
			if (200 == status) {
				String resultString = hc.getResult();
				if (null != resultString && !"".equals(resultString)) {
					// 将返回结果转换为map
					Map<String,String> tmpRspData  = SDKUtil.convertResultJsonStringToMap(resultString);
					rspData.putAll(tmpRspData);
				}
			}else{
				LogUtil.writeLog("返回http状态码["+status+"]，请检查请求报文或者请求地址是否正确");
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return rspData;
	}

	
	/**
	 * 功能：将批量文件内容使用DEFLATE压缩算法压缩，Base64编码生成字符串并返回<br>
	 * 适用到的交易：批量<br>
	 * @param filePath 批量文件-全路径文件名<br>
	 * @return
	 */
	public static String enCodeFileContent(String filePath,String encoding){
		String baseFileContent = "";
		
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				LogUtil.writeErrorLog(e.getMessage(), e);
			}
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			int fl = in.available();
			if (null != in) {
				byte[] s = new byte[fl];
				in.read(s, 0, fl);
				// 压缩编码.
				baseFileContent = new String(SecureUtil.base64Encode(SDKUtil.deflater(s)),encoding);
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				}
			}
		}
		return baseFileContent;
	}
	
	/**
	 * 功能：解析交易返回的fileContent字符串并落地 （ 解base64，解DEFLATE压缩并落地）<br>
	 * 适用到的交易：批量交易状态查询<br>
	 * @param resData 返回报文map<br>
	 * @param fileDirectory 落地的文件目录（绝对路径）
	 * @param encoding 上送请求报文域encoding字段的值<br>	
	 */
	public static String deCodeFileContent(Map<String, String> resData,String fileDirectory,String encoding) {
		// 解析返回文件
		String filePath = null;
		String fileContent = resData.get(SDKConstants.param_fileContent);
		if (null != fileContent && !"".equals(fileContent)) {
			FileOutputStream out = null;
			try {
				byte[] fileArray = SDKUtil.inflater(SecureUtil
						.base64Decode(fileContent.getBytes(encoding)));
				if (SDKUtil.isEmpty(resData.get("fileName"))) {
					filePath = fileDirectory + File.separator + resData.get("merId")
							+ "_" + resData.get("batchNo") + "_"
							+ resData.get("txnTime") + ".txt";
				} else {
					filePath = fileDirectory + File.separator + resData.get("fileName");
				}
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
			    out = new FileOutputStream(file);
				out.write(fileArray, 0, fileArray.length);
				out.flush();
			} catch (UnsupportedEncodingException e) {
				LogUtil.writeErrorLog(e.getMessage(), e);
			} catch (IOException e) {
				LogUtil.writeErrorLog(e.getMessage(), e);
			}finally{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filePath;
	}

	/**
	 * 功能：将结果文件内容 转换成明文字符串：解base64,解压缩<br>
	 * 适用到的交易：批量交易状态查询<br>
	 * @param fileContent 批量交易状态查询返回的文件内容<br>
	 * @return 内容明文<br>
	 */
	public static String getFileContent(String fileContent,String encoding){
		String fc = "";
		try {
			fc = new String(SDKUtil.inflater(SecureUtil.base64Decode(fileContent.getBytes())),encoding);
		} catch (UnsupportedEncodingException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} catch (IOException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return fc;
	}
	

	/**
	 * 对字符串做base64<br>
	 * @param rawStr<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Encode(String rawStr,String encoding) throws IOException{
		byte [] rawByte = rawStr.getBytes(encoding);
		return new String(SecureUtil.base64Encode(rawByte),encoding);
	}
	/**
	 * 对base64的字符串解base64<br>
	 * @param base64Str<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Decode(String base64Str,String encoding) throws IOException{
		byte [] rawByte = base64Str.getBytes(encoding);
		return new String(SecureUtil.base64Decode(rawByte),encoding);	
	}
	
	/**
	 * 获取
	 * @param number
	 * @return
	 */
	public static int genLuhn(String number){
		return SecureUtil.genLuhn(number);
	}
}
