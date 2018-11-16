package com.huateng.common.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.common.StringUtil;
import com.huateng.common.XmlObject.DuXMLDoc;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

//这该类作废
/**
 * common xml conver utility
 * 
 * @author   zoujunqing
 * @version Framework 2012.3.12
 */
public class MyServer extends Thread {

@SuppressWarnings("unused")
private Socket s;

//private GenerateNextId GenerateNextId;
private static Logger log = Logger.getLogger(MyServer.class);

private boolean listening=true;
 
@SuppressWarnings("unchecked")
public void run() {
     try {
      //
      String port=SysParamUtil.getParam("MCHTPORT");
      if(StringUtil.isNull(port))port="19010";
      log.info("监听端口："+port);
      ServerSocket ss = new ServerSocket(Integer.parseInt(port)); //服务器的套接字，端口为19020
      while (listening) {
    	//  new ServerThread(socket).start();   

	      Socket s = ss.accept();
	      //ss.setReceiveBufferSize(size)
	      s.setSoTimeout(500000);
	      OutputStream os = s.getOutputStream();
	      
	      InputStream is=s.getInputStream();
	
	      byte[] bu=new byte[1024*1024];
	
	      int len=is.read(bu);//从客户端读取消息
	
	      String outString=new String(bu,0,len);
	      @SuppressWarnings("unused")
		String reqs = CommonFunction.changeCharsetOld(outString,Charset.defaultCharset().toString(),"UTF-8");//从GBK编码转换成UTF-8
	      String  xmlString ="";
	      try{
		     log.info("收到报文字符串："+outString);
		      DuXMLDoc doc = new DuXMLDoc();
		      SelectMcht mchtserch = new SelectMcht();
		      
		      Map map = doc.TradeXml(outString,"splitSearch");
		      
		      String Txn_Num = map.get("Txn_Num").toString();
		      
		      if(Txn_Num.equals("9008")){
		    	  xmlString = mchtserch.SelectMchtAll(outString);
		    	  
		      }else if(Txn_Num.equals("9009")){
		    	  xmlString = mchtserch.SelectMchtDetail(outString);
		      }
		       
		      log.info("返回xml字符串："+xmlString);
		      
		      os.write(xmlString.getBytes("UTF-8"));//
		
		      os.close();
		
		      is.close();
		      s.close();
			} catch (Exception e) {
					e.printStackTrace();
				//	os.write(outString.getBytes("UTF-8"));//
					os.close();
					is.close();
					s.close(); 
				}
      		}
     } catch (Exception e) {
    	 e.printStackTrace();
     }
}

public boolean isListening() {
	return listening;
}

public void setListening(boolean listening) {
	this.listening = listening;
}

public static void main(String[] args) throws UnsupportedEncodingException {
	String out ="中文";
	
	byte outString[] =out.getBytes("UTF-8");
	for(int i=0;i<outString.length;i++){
		System.out.print(outString[i]+256);
	}
}
}
