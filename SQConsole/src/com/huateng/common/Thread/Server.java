package com.huateng.common.Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.huateng.common.StringUtil;
import com.huateng.system.util.SysParamUtil;

public class Server extends Thread {// extends Thread{
	private ServerSocket serverSocket = null;
	private static Logger log = Logger.getLogger(Server.class);
	 
	private boolean listening = true;
	
	@SuppressWarnings("unchecked")
	Vector clientSockets = new Vector(100);

	public Server() {
	}

	public void run() {
		try {
			this.Server1();
		} catch (Exception e) {
			log.error("SOCKET服务器端异常" + e.toString());
		}
	}

	public void Server1() throws Exception {
		String port=SysParamUtil.getParam("MCHTPORT");
	    if(StringUtil.isNull(port))port="19030";
	    log.info("监听端口："+port);
		try {
			serverSocket = new ServerSocket(Integer.parseInt(port)); //服务器的套接字，端口为19030
		} catch (Exception ex) {
			log.error("服务器启动失败" + port);
		}
		 
		log.info("SOCKET服务器端启动成功" + port);
		while (listening)
			addClient(serverSocket.accept());
		if(serverSocket!=null&&!serverSocket.isClosed())
			serverSocket.close();
		log.info("服务器关闭" + port);
	}

	// add client
	@SuppressWarnings("unchecked")
	public void addClient(Socket socket) throws IOException {
		String timeOut=SysParamUtil.getParam("TIMEOUT");
		if(StringUtil.isEmpty(timeOut))timeOut="60000";//默认超时60s
		 socket.setSoTimeout(Integer.parseInt(timeOut));
		 new ServerThread(socket, this).start();
		 clientSockets.add(socket);
	}

	// close client socket
	public void removeClient(Socket socket) throws IOException {
		clientSockets.remove(socket);
	}
  

	public boolean isListening() {
		return listening;
	}

	public void setListening(boolean listening) {
		this.listening = listening;
	}
	
	public void serverStop(){
		this.listening=false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new Server().start();
	}

}