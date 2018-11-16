package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.system.util.BlackListAndRiskGradeConfig;

public class Test2 {
	private static Logger logger = Logger.getLogger(Test2.class);
	private static String blockListURL;
	private static String riskGradeURL;
	private static String blackResult;
	private static String riskResult;
	
	public static void main(String[] args) throws Exception {
		blockListURL=BlackListAndRiskGradeConfig.getBlackListRUL();
        riskGradeURL = BlackListAndRiskGradeConfig.getRiskGradeURL();
		System.out.println(blockListURL);
		System.out.println(riskGradeURL);
		
		// 黑名单校验----只做黑名单
        Map<String, String> parameters = new HashMap<String,String>();
//        parameters.put("CName",dto.getCName());   //中文或者英文名
//        parameters.put("CertID", dto.getCertID());   //证件号码
//        parameters.put("Country", dto.getCountry());  //国籍
//        parameters.put("Birthday", dto.getBirthday());  //出生日期/注册日期
//        parameters.put("Gender", dto.getGender());  //性别
        
//        返回状态	Result	Y	Int	
//        						1匹配上黑名单
//						        2匹配上灰名单
//						        3匹配上白名单
//						        0匹配无结果
//						        -1匹配校验或计算出错
        
        parameters.put("CName", "姓名");//CName 中文-英文姓名 (中英文名称至少填一个)      
		blackResult =sendGet(blockListURL,parameters);
		
		// 风险等级
//        Map<String, String> parameters2 = null;
//		  parameters.put("CNum", dto.getCNum());
//		  parameters.put("CName", dto.getCName());
        
//	    返回状态	Result	Y	String(10)	
//	    								H:高级风险
//								        L：低级风险
//								        M：中级风险
//								        P：极高风险
//								        0尚未确认评级
//								        -1无此客户或客户号姓名不匹配
//        parameters2.put("CNum", "37068600000");//客户号             
//        parameters2.put("CName", "姓名");//中文姓名
//        riskResult = sendGet(riskGradeURL, parameters2);
	}
	
	   /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) { 
                        if(parameters.get(name)!=null) {
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8")).append("&");  
                        }
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            String full_url = url + "?" + params;  
            logger.info("请求地址&参数："+full_url);  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error(e.getMessage());
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
                logger.error(ex.getMessage());
            }  
        }
        logger.info("返回&参数："+result);
        return result; 
    }  
}
