package com.webservice.util;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ConfigPosp;




public class HttpPostData  {


	

	    //

	    public static String postData(String soapRequestData) { 
	    	
	    	
	    	 try {
//				 String soapRequestData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//				 		+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
//				 		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
//				 		+ "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"	
//                         + "<soap12:Body>"
//                         + "<GetCardList xmlns=\"http://tempuri.org/\">"
//                         + " <User_Id>"+""+"</User_Id>"
//                         + "<Method_ID>001A</Method_ID>"
//                         + "</GetCardList>" + "</soap12:Body>" + "</soap12:Envelope>";
					System.out.println(soapRequestData);
					PostMethod postMethod = new PostMethod(
							ConfigPosp.getWebserviceIp());
					byte[] b = soapRequestData.getBytes("utf-8");
					InputStream is = new ByteArrayInputStream(b, 0, b.length);
					RequestEntity re = new InputStreamRequestEntity(is, b.length,
					    "application/soap+xml; charset=utf-8");
					postMethod.setRequestEntity(re);
					HttpClient httpClient = new HttpClient();
					int statusCode = httpClient.executeMethod(postMethod);
					if(statusCode==200){
					    soapRequestData = postMethod.getResponseBodyAsString();
					    System.out.println(soapRequestData);
					    return soapRequestData;
					}
			     } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      return "";

	    } 
	    
	    public static String getRemoteInfo(String data, String method) throws BizServiceException {
	    	String result="";
	        // 命名空间  
	    	String nameSpace = "http://tempuri.org/";
	        //String nameSpace = "http://WebXml.com.cn/";  
	        // 调用的方法名称  
	        //String methodName = "GetCardList";  
	        String methodName = method;  
	        // EndPoint  
	       // String endPoint = "http://114.251.186.61:80/CoreToIntervention.asmx";
	        String endPoint = ConfigPosp.getWebserviceIp();
	        //String endPoint = "http://10.120.132.210:8081/CoreToAlipay.asmx";
	        //String endPoint = "http://114.251.186.60:8081/CoreToAlipay.asmx";  
	        // SOAP Action  
	        String soapAction = "http://tempuri.org/"+method;  
	        //String soapAction = "http://tempuri.org/GetCardList";
	  
	        // 指定WebService的命名空间和调用的方法名  
	        SoapObject rpc = new SoapObject(nameSpace, methodName);
	        // 设置需调用WebService接口需要传入的参数
//	        rpc.addProperty("userid", userid);
//	        rpc.addProperty("methodId", methodId);
//	        Iterator iter = dataMap.entrySet().iterator(); 
//	    	while (iter.hasNext()) { 
//	    	Map.Entry entry = (Map.Entry) iter.next(); 
//	    	String key = (String) entry.getKey(); 
//	    	System.out.println(key);
//	    	String val = (String) entry.getValue(); 
//	    	System.out.println(val);
//	    	rpc.addProperty(key, val);
//	    	}
	        rpc.addProperty("data", data);
//	        rpc.addProperty("DataSource_id", "CNPC707717");
//	        rpc.addProperty("Method_ID", "001A");
//	        rpc.addProperty("User_Name", "Test-1");
//	        rpc.addProperty("Id_Type", "1");
//	        rpc.addProperty("Id_Num", "321654987789451223");
//	        rpc.addProperty("Card_Num", "9030400000002001");
//	        rpc.addProperty("Phone", "15956416155");
//	        rpc.addProperty("Email", "1085873310@qq.com");
//	        rpc.addProperty("Gender", "1");
//	        rpc.addProperty("Province", "上海");
//	        rpc.addProperty("City", "上海");
//	        rpc.addProperty("Fied1", "05555");
//	        rpc.addProperty("Fied2", "05555");
	         
	        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);  
	  
	        envelope.bodyOut = rpc;  
	        // 设置是否调用的是dotNet开发的WebService  
	        envelope.dotNet = true;  
	        // 等价于envelope.bodyOut = rpc;  
	        envelope.setOutputSoapObject(rpc);  
	  
	        HttpTransportSE transport = new HttpTransportSE(endPoint); 
	        try {
				transport.call(soapAction, envelope);
				 // （6）使用getResponse方法获得WebService方法的返回结果
			      if (envelope.getResponse() != null) {
			        result = String.valueOf(envelope.getResponse());
			      }
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new BizServiceException("中石油通讯异常！");
			} catch (XmlPullParserException e1) {
				e1.printStackTrace();
				throw new BizServiceException("中石油通讯异常！");
				
			}
	        return result;
	    }  

}




/*package com.webservice.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class HttpPostData{
	public static void main(String[] args) {
		String a = new HttpPostData().getRemoteInfo("708076","78123");
		System.out.println(a);
	}

    public String getRemoteInfo(String userid, String methodId) {
    	String result="";
        // 命名空间  
    	String nameSpace = "http://tempuri.org/";
        //String nameSpace = "http://WebXml.com.cn/";  
        // 调用的方法名称  
        //String methodName = "GetCardList";  
        String methodName = "BindingCard";  
        // EndPoint  
        String endPoint = "http://114.251.186.61:80/CoreToIntervention.asmx";
        //String endPoint = "http://10.120.132.210:8081/CoreToAlipay.asmx";
        //String endPoint = "http://114.251.186.60:8081/CoreToAlipay.asmx";  
        // SOAP Action  
        String soapAction = "http://tempuri.org/BindingCard";  
        //String soapAction = "http://tempuri.org/GetCardList";
  
        // 指定WebService的命名空间和调用的方法名  
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的参数
//        rpc.addProperty("userid", userid);
//        rpc.addProperty("methodId", methodId);
        rpc.addProperty("UserId", "");
        rpc.addProperty("DataSource_id", "CNPC707717");
        rpc.addProperty("Method_ID", "001A");
        rpc.addProperty("User_Name", "Test-1");
        rpc.addProperty("Id_Type", "1");
        rpc.addProperty("Id_Num", "321654987789451223");
        rpc.addProperty("Card_Num", "9030400000002001");
        rpc.addProperty("Phone", "15956416155");
        rpc.addProperty("Email", "1085873310@qq.com");
        rpc.addProperty("Gender", "1");
        rpc.addProperty("Province", "上海");
        rpc.addProperty("City", "上海");
        rpc.addProperty("Fied1", "05555");
        rpc.addProperty("Fied2", "05555");
         
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);  
  
        envelope.bodyOut = rpc;  
        // 设置是否调用的是dotNet开发的WebService  
        envelope.dotNet = true;  
        // 等价于envelope.bodyOut = rpc;  
        envelope.setOutputSoapObject(rpc);  
  
        HttpTransportSE transport = new HttpTransportSE(endPoint); 
        try {
			transport.call(soapAction, envelope);
			 // （6）使用getResponse方法获得WebService方法的返回结果
		      if (envelope.getResponse() != null) {
		        result = String.valueOf(envelope.getResponse());
		      }
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
		}
        return result;
    }  
}  */

