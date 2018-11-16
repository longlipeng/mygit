package com.huateng.common.Thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import org.apache.log4j.Logger;
import com.huateng.common.XmlObject.DuXMLDoc;
import com.huateng.common.server.SelectMcht;

public class ServerThread extends Thread {
	private Server server = null;
	private Socket socket = null;
	private static Logger log = Logger.getLogger(ServerThread.class);

	public ServerThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		OutputStream os = null;
		InputStream is = null;
		try {
			//System.out.println(socket.getSendBufferSize());
			//System.out.println(socket.getReceiveBufferSize());
			socket.setSendBufferSize(1024);
			//socket.setReceiveBufferSize(1024);
			//System.out.println(socket.getSendBufferSize());
			//System.out.println(socket.getReceiveBufferSize());
			is = socket.getInputStream();
			os = socket.getOutputStream();
			 byte[] bu=new byte[1024*1024];
		      int len=is.read(bu);//从客户端读取消息
		      if(len<4)return;//当报文长度小于4时，直接跳过。负载均衡会每个10秒发送一个长度小于4的报文过来
		      String outString=new String(bu,0,len);
		      
		      
		     // String reqs = CommonFunction.changeCharsetOld(outString,Charset.defaultCharset().toString(),"UTF-8");//从GBK编码转换成UTF-8
		      String  xmlString ="";
			     log.info("收到报文字符串："+outString);
			     outString= outString.replace("#END", "");
			      DuXMLDoc doc = new DuXMLDoc();
			      SelectMcht mchtserch = new SelectMcht();
			      Map map = doc.TradeXml(outString,"splitSearch");
			      String Txn_Num = map.get("Txn_Num").toString();
			      if(Txn_Num.equals("9008")){
			    	  xmlString = mchtserch.SelectMchtAll(outString);
			      }else if(Txn_Num.equals("9009")){
			    	  xmlString = mchtserch.SelectMchtDetail(outString);
			      }
			      xmlString+="#END";	
			      log.info("返回xml字符串："+xmlString);
			      os.write(xmlString.getBytes("UTF-8"));//
			      os.flush();
		} catch (SocketTimeoutException ex) {
			ex.printStackTrace();
			log.info("连接超时" + ex.toString());
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("SOCket服务器端异常" + ex.toString());
		} finally {
			try {
				os.close();
			} catch (IOException e) {	
				e.printStackTrace();
				os = null;
				log.info("SOCket服务器端请求socket关闭OutputStream流异常");
			}
			try {
				is.close();				
			} catch (IOException e) {
				is = null;
				e.printStackTrace();
				log.info("SOCket服务器端请求socket关闭InputStream流异常");
			}
			Close();			
		}
	}

	private void Close() {
		try {
			server.removeClient(socket);
		} catch (Exception ex) {
			ex.printStackTrace();
		 
			log.info("SOCKET服务端移除socket请求异常" + ex.toString());
		}
		try {
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("关闭SOCKET服务端异常" + ex.toString());
		}
	}

}
