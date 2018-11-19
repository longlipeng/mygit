package com.allinfinance.prepay.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientImpl implements Client
{

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 服务器名
	private String host;
	// 服务器端口
	private int port;
	
	private String backHost; //HSM备机IP
	private int backPort;//HSM备机端口
	//private Socket socket;
	

	public ClientImpl(String host, int port, String backHost, int backPort) {
		this.host = host;
		this.port = port;
		this.backHost = backHost;
		this.backPort = backPort;
		//this.socket = new Socket();
	}
	
	@Override
	public byte[] request(ByteBuffer data) throws Exception
	{
		// 如果连接资源已释放、则重新构造
		//if(socket==null)
		Socket socket = new Socket();
		socket.setSoTimeout(10000);
		
		String os = System.getProperty("os.name");
		String commandLine;
		int threshold = 3; //ping返回出现ip的最小次数，以此来判断是否ping通
		if(os.contains("Windows"))
			commandLine = "ping -n 1 -w 3000 " + host;
		else
			commandLine = "ping -c 1 " + host;
		Process process;
		int count = 0;
		try
		{
			process = Runtime.getRuntime().exec(commandLine);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			
			while((line = br.readLine()) != null)
			{
				logger.debug(line);
				if(line.contains(host))
					count++;
			}
			logger.debug("count = " + count);
			br.close();
			process.destroy();
		} catch (IOException e)
		{
			logger.warn("ping HSM 失败");
		}
		
		try
		{
			if(count >= threshold)
			{
				logger.info("启用主HSM {}", host);
				socket.connect(new InetSocketAddress(host, port),1000);
			}
						
			else
			{
				logger.warn("主HSM {} 未ping通，启用备用HSM {}", host, backHost);
				socket.connect(new InetSocketAddress(backHost, backPort),1000);
			}
				
			if (!socket.isConnected()) {
				logger.error("加密鸡连失败");
				throw new Exception("加密鸡连失败");
			}
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				byte[] messageBytes = new byte[data.limit()];
				data.get(messageBytes);
				logger.debug("请求数据：" + Hex.encodeHexString(messageBytes));
				out.write(messageBytes);
				byte[] retLen = new byte[2];
				in.read(retLen);
				int length = (retLen[0]<<8&0x0FFFF)+(retLen[1]&0x0FF);
				byte[] result = new byte[length];
				in.read(result);
				ByteBuffer bb = ByteBuffer.allocate(length+2);
				bb.put(retLen).put(result);
				logger.info("收到加密鸡返回，长度:"+length);
				logger.info("返回内容:"+Hex.encodeHexString(result));
				in.close();
				out.close();
				//socket.close();
				return bb.array();
		} 
		catch (IOException e)
		{
			logger.error("加密鸡通信错误", e);
			throw e;
		}
		finally
		{
			try
			{
				if(socket != null)
					socket.close();
			} catch (Exception e2)
			{
				logger.error("加密鸡连接关闭错误", e2);
				e2.printStackTrace();
			}
		}
	}

	



	public static void main(String[] args) throws Exception {
		Client client = new ClientImpl("10.250.3.28", 17888, "",0);
		ByteBuffer b = ByteBuffer.allocate(1024);
		StringBuffer sb = new StringBuffer();
		sb.append("B0C2").append("0000000000000000").append("0041");
		b.put(Hex.decodeHex(sb.toString().toCharArray()));
		//ATC
//		b.put(ConvertProcess.toN("00A1", 4));
		//ARQC
		b.put(Hex.decodeHex("5B7E8FE81285E9D2".toCharArray()));
		//arc
		b.put(Hex.decodeHex(Hex.encodeHex("00".getBytes())));
		//密钥长度指示
		b.put(Hex.decodeHex("02".toCharArray()));
		//分散次数
		b.put(Hex.decodeHex("01".toCharArray()));
		//TDOL长度
		b.put(Hex.decodeHex("00".toCharArray()));
		//CDOL长度
		b.put(Hex.decodeHex(Integer.toHexString(37).toCharArray()));
		sb.delete(0, sb.length());
		String cardno = "6226822016000000908";
		sb.append(cardno.substring(cardno.length()-14, cardno.length())).append("00");
		//卡号末14位+2位卡序列号
		b.put(Hex.decodeHex(sb.toString().toCharArray()));
		//授权金额
//		b.put(ConvertProcess.toN("000000000001", 12));
//		//其他金额
//		b.put(ConvertProcess.toN("000000000000", 12));
//		//终端国家代码
//		b.put(ConvertProcess.toN("0156", 4));
//		//TVR
//		b.put(ConvertProcess.toN("0000000000", 10));
//		//交易货币代码
//		b.put(ConvertProcess.toN("0156", 4));
//		//交易日期
//		b.put(ConvertProcess.toN("140411", 6));
//		//交易类型
//		b.put(ConvertProcess.toN("00", 2));
//		//不可预知数
//		b.put(ConvertProcess.toN("0B25D27B", 8));
//		//AIP
//		b.put(ConvertProcess.toN("7C00", 4));
//		//ATC
//		b.put(ConvertProcess.toN("00A1", 4));
//		//CVR
//		b.put(ConvertProcess.toN("07120103900002010A010000039997EA695E4F".substring(6, 14), 8));
//		//b.put(ConvertProcess.toN("b0c2000000000000000000410052060e7228481fb5f030300201002522013100003692010000000100000000000000000156c40000008001562906226324303e4e7c00005203a02010", 146));
//		//b.put(ConvertProcess.toN("b0c20000000000000000004100A15B7E8FE81285E9D23030020100252201600000090801000000000001000000000000015600000000000156140411000B25D27B7c0000A103900002", 146));

		b.flip();
		byte[] result = client.request(b);
		if(result[0]==(byte)'A')
		{
			System.out.println(Hex.encodeHex(ArrayUtils.subarray(result, 1, 9)));
		}
//		byte a = 0x00;
//		byte b = 0x14;
//		int c = (a<<8)+b;
//		System.out.println(c);
	}
	
}
