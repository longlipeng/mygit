package com.huateng.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import com.huateng.common.StringUtil;

public class SocketConnect {
	private static Logger log = Logger.getLogger(SocketConnect.class);
	private String msg = null;
	private Socket socket = null;
	private String rsp;
	private OutputStream outputStream = null;
	private InputStream inputstream = null;
	public boolean isOver = false;
		
	public SocketConnect(){
		try{
			this.msg = "Hello World";
			//发送地址IP
			String ip = SysParamUtil.getParam("IP");
			//发送地址端口
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			socket = new Socket(ip,port);
			//设置超时
			socket.setSoTimeout(Integer.parseInt(SysParamUtil.getParam("TIMEOUT")));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		}
		catch(ConnectException e) {
			log.error(e.getMessage(),e);
		}
		catch(IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 发送向机构签到报文
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @return 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public  SocketConnect(String msg,String str)throws UnknownHostException, IOException {
		try{
			this.msg = msg;
			//发送地址IP
			String ip = SysParamUtil.getParam("SERVERIP");
			//发送地址端口
			int port = Integer.parseInt(SysParamUtil.getParam("SERVERPORT"));
			socket = new Socket(ip,port);
			//设置超时
			socket.setSoTimeout(Integer.parseInt(SysParamUtil.getParam("TIMEOUT")));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch(ConnectException e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 发送商户验证报文	
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketConnect(String msg)throws UnknownHostException, IOException {
		try{
			this.msg = msg;
			//发送地址IP
			String ip = SysParamUtil.getParam("IP");
			//发送地址端口
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			socket = new Socket(ip,port);
			//设置超时
			socket.setSoTimeout(Integer.parseInt(SysParamUtil.getParam("TIMEOUT")));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch(ConnectException e) {
			log.error(e.getMessage(),e);
		}
	}
			
	public void run() {
		try {
			sendMessage(msg);
			this.rsp = getMessage();
//			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
			
	public void run(String encoding) {
		try {
			sendMessage(msg);
			this.rsp = getMessage(encoding);
//			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 发送报文
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req) throws IOException {
		//req+="#END";
		byte[] reqByte = req.getBytes();
		outputStream.write(reqByte);
		outputStream.flush();
	}
			
	/**
	 * 获取报文
	 * @return
	 * @throws IOException 
	 */
	private String getMessage() throws IOException{
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputstream));
		String str = null;
		while(!isOver){
			str = bf.readLine();
			if(str != null)
				isOver = true;
		}
		return str;
	}	
	
	private String getMessage(String encoding) throws IOException{
		byte[] rspBytes = new byte[1024];
		int offsize = 0;
		int len = 0;
		while((len = inputstream.read()) != -1) {
			inputstream.read(rspBytes, offsize, len);
			offsize += len;
		}
		return new String(rspBytes,encoding);
	}
	
	/**环讯6.0接口xml报文增加了#END作为结束标志，取报文时已经将该结束标志去掉了
	 * @return the rsp
	 */
	public String getRsp() {
		if(StringUtil.isEmpty(rsp))return rsp;
		return rsp.replace("#END", "");
	}
	/**
	 * @param rsp the rsp to set
	 */
	public void setRsp(String rsp) {
		this.rsp = rsp;
	}
	
	public void close() throws IOException{
		if(!socket.isClosed())	{
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
	
	/**
	 * 向中行/农行发送签到报文
	 * @param message 2012.07.10
	 * @return
	 */
	public static String sendMessage1(String message){//20121026修改为静态static
		if (StringUtil.isNull(message)) {
			return "没有报文消息";
		}
		
		String ip = SysParamUtil.getParam("SERVERIP");
		String port =SysParamUtil.getParam("SERVERPORT");
		String timeOut = SysParamUtil.getParam("TIMEOUT");
		
//		String sendMessage = CommonFunction.fillString(String.valueOf(message.toString().length()), '0', 4, false);
//		sendMessage += message.toString();
		System.out.println("发送签到报文字符串:" + message);
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		String ret = "";
		
		try {
			//发送
			socket = new Socket(ip,Integer.valueOf(port));
			socket.setSoTimeout(Integer.valueOf(timeOut));
			outputStream = socket.getOutputStream();
			outputStream.write(message.getBytes()); 
			outputStream.flush();
						
			InputStream is = socket.getInputStream();
			
			byte[] serverSay = new byte[1024*1024];
			int len = is.read(serverSay);//接受服务器消息
			ret = new String(serverSay, 0, len);
			System.out.println("服务器返回签到响应："+ret);

			if ( !StringUtil.isNull(ret) ) 
				return ret;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "失败";
	}
	
	/**银联管理类交易
	 * @param message
	 * @return
	 */
	public static String sendMessageCup(String message,String encoding){
		if (StringUtil.isNull(message)) {
			return "没有报文消息";
		}
		
		String ip = SysParamUtil.getParam("SETTLEIP");
		String port =SysParamUtil.getParam("SETTLEPORT");
		String timeOut = SysParamUtil.getParam("TIMEOUT");
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		String ret = "";
		
		try {
			//发送
			socket = new Socket(ip,Integer.valueOf(port));
			socket.setSoTimeout(Integer.valueOf(timeOut));
			outputStream = socket.getOutputStream();
			outputStream.write(message.getBytes()); 
			outputStream.flush();
						
			InputStream is = socket.getInputStream();
			
			byte[] serverSay = new byte[1024];
			int len = is.read(serverSay);//接受服务器消息
			ret = new String(serverSay, 0, len,encoding);

			if ( !StringUtil.isNull(ret) ) 
				return ret;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "失败";
	}
}
