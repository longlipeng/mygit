package com.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

//import hsmj.HsmConst;
//
//import com.hsmj.HsmApp;
//import com.hsmj.HsmSession;
//import com.hsmj.Bank_GenMacIN;
//import com.hsmj.Bank_GenMacOUT;
//
//import com.huateng.secure.util.OutputSecResult;
//import com.huateng.secure.util.exception.SecException;
//import com.huateng.secure.util.security.CodeUtil;
//import com.huateng.secure.util.security.HsmUtil;
//import com.huateng.secure.util.security.SecurityUtil;

public class TestMAC {
	private static  Logger logger = Logger.getLogger(TestMAC.class);
	private static InputStream iInputStream = null;
	private static OutputStream iOutputStream = null;
	/**
	 * @param args
	 */
/*	public static void main(String[] args) {

		try {
			Socket iSocketHandle = null;
			iSocketHandle = new Socket();
			InetSocketAddress hsmAddr = new InetSocketAddress("192.168.40.52", 8);
 			iSocketHandle.setOOBInline(true);
			iSocketHandle.connect(hsmAddr, 30);
			iSocketHandle.setSoTimeout(30);
			iSocketHandle.setTcpNoDelay(true);
			iSocketHandle.setReceiveBufferSize(2048);
			iInputStream = iSocketHandle.getInputStream();
			iOutputStream = iSocketHandle.getOutputStream();
            
			testCmd00();
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}*/

	public static byte[] EncDec71_Data() {
		int iSndLen = 0;
		byte[] iSecBufferIn = new byte[70];
		
		byte[] header = "1234".getBytes();
		System.arraycopy(header, 0, iSecBufferIn, iSndLen, 4);
		iSndLen += 4;
		
		byte[] command = "E0".getBytes();
		System.arraycopy(command, 0, iSecBufferIn, iSndLen, 2);
		iSndLen += 2;
		
		byte[] a = "0010X".getBytes();
		System.arraycopy(a, 0, iSecBufferIn, iSndLen, 5);
		iSndLen += 5;
		
		byte[] work_key = "56A1B60BB3974A6CECE2CDD2B6910711".getBytes();
		System.arraycopy(work_key, 0, iSecBufferIn, iSndLen, 32);
		iSndLen += 32;
		
		byte[] b = "11000000008".getBytes();
		System.arraycopy(b, 0, iSecBufferIn, iSndLen, 11);
		iSndLen += 11;
		
		byte[] date = "D7D67105907787CD".getBytes();
		System.arraycopy(date, 0, iSecBufferIn, iSndLen, 16);
		iSndLen += 16;
		byte[] buf = new byte[72];
	    
		Integer l = new Integer((iSecBufferIn.length)/256);
		buf[0] = l.byteValue();
		l = new Integer((iSecBufferIn.length)%256);
		buf[1] = l.byteValue();
		System.arraycopy(iSecBufferIn, 0, buf, 2, iSndLen);
		
		return buf;

	}
	
	
	public static void testCmd00() throws IOException{
//		byte[] comm_data = new byte[8];
		byte[] comm_data = EncDec71_Data();
		
		write(comm_data, 72);
		byte[] RcvData = new byte[256];
		read(RcvData,256);
		
		String str1 = new String(comm_data);  
		logger.info(str1);
		
		String str3 = byte2hex(comm_data);
		logger.info(str3);
		
		String str2 = new String(RcvData);  
		logger.info(str2);

	}
	
	public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

         for (int n = 0; n < b.length; n++) {
              stmp = (java.lang.Integer. toHexString(b[n] & 0XFF));
               if (stmp.length() == 1) {
                    hs = hs + "0" + stmp;
              } else {
                    hs = hs + stmp;

              }
               if (n < b.length - 1) {
                    hs = hs + " ";
              }
        }

         return hs.toUpperCase();
  }


	
	public static void write(byte[] aByteOut, int aLength) throws IOException {
		iOutputStream.write(aByteOut, 0, aLength);
	}

	public static int read(byte[] aByteIn, int aBufferSize) throws IOException {
		return iInputStream.read(aByteIn, 0, aBufferSize);
	}

}
