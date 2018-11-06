package com.huateng.framework.msg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ConfigSocket;
import com.huateng.framework.util.StringUtils;

/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class SendMessage {
	static final String VISABLE = "1";
	static final String NOT_VISABLE = "0";
	static Logger logger = Logger.getLogger(SendMessage.class);

	/**
	 * 调用socket发送报文, 并获取应答
	 * 
	 * @param sendData
	 * @param sendLenVisable
	 * @param sendLen
	 * @param recvLenVisable
	 * @param recvLen
	 * @return
	 */
	public static byte[] send(byte[] sendData, String sendLenVisable,
			int sendLen, String recvLenVisable, int recvLen, String ip,
			String port) throws BizServiceException {
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		byte[] rspData = null;
		logger.debug("recvLen=" + recvLen);
		logger.debug("recvLenVisable=" + recvLenVisable);
		try {
			socket = new Socket(ip, Integer.parseInt(port));

			// 设置socket超时时间, 从配置文件中读取
			socket.setSoTimeout(Integer.parseInt(ConfigSocket
					.getSocketTimeOut()));
			out = socket.getOutputStream();
			in = socket.getInputStream();

			// 向socket写数据
			write(out, sendData, sendLen);

			int recvDataLen = recvLen;
			int recvPosStart = 0;
			// 如果接收报文带有长度域, 先读取长度域, 把长度域的值做为值域的总长度, recvLen存放长度域的长度
			if (recvLenVisable.equals(VISABLE)) {
				recvDataLen = recvLen / 2;
				byte[] len = read(in, recvPosStart, recvDataLen);
				// 测试用打印
				/*
				 * StringBuffer temp = new StringBuffer(len.length * 2);
				 * 
				 * for(int i=0;i<len.length;i++){ temp.append((len[i]&
				 * 0xf0)>>>4); temp.append((len[i]& 0x0f)); }
				 */
				String slen = StringUtils.bytesToHexString(len);
				logger.debug("len=" + slen);
				// 测试用打印结束

				if (len.length == 0) {
					String err = "receive pack is null";
					logger.error(err);
					throw new BizServiceException(err);
				}

				recvPosStart = recvDataLen;
				recvDataLen = Integer.parseInt(slen, 16);

			}
			// 读取应答报文
			rspData = read(in, recvPosStart, recvDataLen);

		} catch (Exception ex) {
			logger.error(ex.toString());

		} finally {
			// 调用结束关闭socket
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {

			}
		}

		return rspData;
	}

	public static List multSend(List dataList, String ip, String port)
			throws BizServiceException {
		// 如果批量发送参数为空, 抛出异常
		if (dataList == null || dataList.size() == 0) {
			String err = "multSend dataList is empty";
			logger.error(err);
			throw new BizServiceException(err);
		}

		List rst = new ArrayList();
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;

		try {

			socket = new Socket(ip, Integer.parseInt(port));

			// 设置socket超时时间, 从配置文件中读取
			socket.setSoTimeout(Integer.parseInt(ConfigSocket
					.getSocketTimeOut()));
			out = socket.getOutputStream();
			in = socket.getInputStream();

			// 循环处理
			for (int i = 0; i < dataList.size(); i++) {

				// dataList中每一个元素是一个ArrayList对象, 这个对象中包含了将要发送的byte数组以及接收报文长度等信息
				ArrayList childList;
				try {
					childList = (ArrayList) dataList.get(i);
				} catch (ClassCastException e) {
					String err = "multSend request must be list of ArrayList.";
					logger.error(err);
					throw new BizServiceException(err + e.toString());
				}

				if (childList == null || childList.size() == 0) {
					String err = "send child can not be empty";
					logger.error(err);
					throw new BizServiceException(err);
				}

				// 报文byte数组
				byte[] tmp = (byte[]) childList.get(0);

				// 发送报文最前端是否带有长度域
				String sendLenVisable = (String) childList.get(1);

				// 如果发送报文最前端带有长度域, sendLen表示长度域的长度;
				// 如果发送报文最前端不带长度域, sendLen表示数据域的总长度
				int sendLen = ((Integer) childList.get(2)).intValue();

				// 接收报文最前端是否带有长度域
				String recvLenVisable = (String) childList.get(3);

				// 如果接收报文最前端带有长度域, recvLen表示长度域的长度;
				// 如果接收报文最前端不带长度域, recvLen表示数据域的总长度
				int recvLen = ((Integer) childList.get(4)).intValue();

				// 向socket写数据
				write(out, tmp, sendLen);

				int recvDataLen = recvLen;
				int recvPosStart = 0;
				// 如果接收报文带有长度域, 先读取长度域, 把长度域的值做为值域的总长度, recvLen存放长度域的长度
				if (recvLenVisable.equals(VISABLE)) {
					byte[] len = read(in, recvPosStart, recvLen);
					recvDataLen = Integer.parseInt(new String(len));
					recvPosStart = recvLen / 2;
				}
				// 读取应答报文
				byte[] rspData = read(in, recvPosStart, recvDataLen);
				rst.add(rspData);
			}
		} catch (Exception ex) {
			String err = "multSend exception. " + ex.getMessage();
			logger.error(err);
			throw new BizServiceException(err);
		} finally {
			// 调用结束关闭socket
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {

			}
		}
		return rst;
	}

	protected static void write(OutputStream out, byte[] msg, int sendLen)
			throws IOException {

		// log send data to send file
		logger.debug("********Send Message***********");
		logger.debug(msg);
		for (byte tp : msg) {
			 logger.info("" + tp + " ");
		}
		logger.debug("*******************************");
		logger.debug(" ");

		if (sendLen != 0)
			out.write(msg, 0, sendLen);
		else
			out.write(msg);
		out.flush();
	}

	protected static byte[] read(InputStream in, int offset, int recvLen)
			throws IOException {
		logger.debug("reading " + recvLen + "bytes form " + offset);
		byte[] data = new byte[recvLen + offset];
		byte[] dest = new byte[recvLen];
		try {
			int tmpLen = in.read(data, offset, recvLen);
			if (tmpLen == -1) {
				logger.debug("tmpLen == -1");
			}
			System.arraycopy(data, offset, dest, 0, recvLen);
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		logger.debug("read end");
		logger.debug("********Recv Message***********");
		logger.debug(dest);
		logger.debug("*******************************");
		logger.debug(" ");
		return dest;
	}

}
