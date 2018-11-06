package com.webservice.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.huateng.framework.util.ConfigPosp;

public class SocketConnect {
	private static Logger log = Logger.getLogger(SocketConnect.class);
	private String msg = null;
	private Socket socket = null;
	private String rsp;
	private OutputStream outputStream = null;
	private InputStream inputstream = null;
	public boolean isOver = false;
	
	public SocketConnect() {
		try {
			this.msg = "Hello World";
			// 发送地址IP
			String ip =  ConfigPosp.getIp() ;
			// 发送地址端口
			int port = Integer.parseInt(ConfigPosp.getPort() );
			socket = new Socket(ip, port);
			// 设置超时
			socket.setSoTimeout(Integer.parseInt( ConfigPosp.getTimeout()));
			
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch (ConnectException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * 发送报文
	 * 
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketConnect(String msg,String sendPort) throws UnknownHostException, IOException {
		try {
			this.msg = msg;
			// 发送地址IP
			String ip =  ConfigPosp.getIp() ;
			// 发送地址端口
			int port = Integer.parseInt(sendPort);
			log.info("往posp发送的IP："+ip+"往posp发送的端口："+sendPort);
			socket = new Socket(ip, port);
			// 设置超时
			socket.setSoTimeout(Integer.parseInt( ConfigPosp.getTimeout() ));
			//socket.setReceiveBufferSize(4096);
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch (ConnectException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 发送报文
	 * 
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketConnect(String msg,String sendPort,String ip) throws UnknownHostException, IOException {
		try {
			this.msg = msg;
			// 发送地址端口
			int port = Integer.parseInt(sendPort);
			log.info("往核心发送的IP："+ip+"往核心发送的端口："+sendPort);
			socket = new Socket(ip, port);
			// 设置超时
			socket.setSoTimeout(Integer.parseInt( ConfigPosp.getTimeout() ));
			//socket.setReceiveBufferSize(4096);
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch (ConnectException e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * 无编码发送
	 */
	public void run() {
		try {
			sendMessage(msg);
			this.rsp = getMessage().trim();
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	/*
	 * 带编码发送
	 */
	public void run(String encoding) {
		try {
			sendMessage(msg,encoding);
			this.rsp = getMessageByLen(encoding).trim();
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/*
	 * 带编码发送
	 */
	public void run(String encoding,int len,int rspLen) {
		try {
			sendMessage(msg,encoding);
			this.rsp = getMessageByLen(encoding,len,rspLen).trim();
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	
	
	
	/**
	 * 发送报文
	 * 
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req) throws IOException {
		byte[] reqByte = req.getBytes();
		outputStream.write(reqByte);
		outputStream.flush();
	}

	
	/**
	 * 发送报文 指定编码
	 * 
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req,String encoding) throws IOException {
		byte[] reqByte = req.getBytes(encoding);
		outputStream.write(reqByte);
		outputStream.flush();
	}
	
	
	/**
	 * 获取报文
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getMessage() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputstream));
		String str = null;
		while (!isOver) {
			str = bf.readLine();
			if (str != null)
				isOver = true;
		}
		return str;
	}

	private String getMessageByLen(String encoding) throws IOException{
		byte[] lenBytes = new byte[4];
		
		inputstream.read(lenBytes, 0, 4);
		String len = new String(lenBytes,encoding.trim());
		byte[] rspBytes = new byte[Integer.parseInt(len)];
		inputstream.read(rspBytes, 0, Integer.parseInt(len));
		return len+new String(rspBytes,encoding.trim());
	}
	
	private String getMessageByLen(String encoding,int lenth,int rspLen) throws IOException{
		byte[] lenBytes = new byte[lenth];
		inputstream.read(lenBytes, 0, 5);
		
//		byte[] wangr = new byte[2000];
//		inputstream.read(wangr);
//		System.out.println("wangr1:" + new String(Hex.encodeHex(wangr)));
//		System.out.println("wangr2:" + new String(wangr,encoding.trim()));
		
		
		
		String len = new String(lenBytes,encoding.trim());
		int bodyLen=Integer.parseInt(len);
		System.out.println(bodyLen);
		byte[] rspBytes = new byte[bodyLen];
		for (int i =0; i < bodyLen; i++) {
			inputstream.read(rspBytes, i, 1);
		}
		/*System.out.print("resBytes:" + new String(Hex.encodeHex(rspBytes)));
		for (int i = 0; i < rspBytes.length; i++) {
			System.out.println(i+":" +rspBytes[i]);
		}*/
		return len+new String(rspBytes,encoding.trim());
	}
	
	@SuppressWarnings("unused")
	private String getMessage(String encoding) throws IOException{
		byte[] rspBytes = new byte[1024];
		int offsize = 0;
		int len = 0;
		while((len = inputstream.read()) != -1) {
			rspBytes[offsize]=(byte) len;
			inputstream.read(rspBytes, offsize+1, len);
			offsize += len+1;
		}
		return new String(rspBytes,encoding.trim());
	}
	/**
	 * @return the rsp
	 */
	public String getRsp() {
		return rsp;
	}
	/**
	 * @param rsp
	 *            the rsp to set
	 */
	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public void close() throws IOException {
		if (!socket.isClosed()) {
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
}
