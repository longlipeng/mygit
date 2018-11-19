package hsmj;
import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

public class ShareHandle {
	private static Logger logger = Logger.getLogger(ShareHandle.class);
	public final int FLAG_NOTUSE = 0;
	public final int FLAG_USED   = 1;
	public final int FLAG_FAULT  = 2;
	private Socket iSocketHandle = null;

	private int iStatus;
    
	private InputStream iInputStream = null;
	private OutputStream iOutputStream = null;
	private String strIP = null;
    
	private int iPort = -1;
	private int iTimeout = -1;
    
	public ShareHandle(String aIP, int aPort, int aTimeout) {
		strIP = aIP;
		iPort = aPort;
		iTimeout = aTimeout;
		connect();
	}

	public void connect() {
		try {
			iSocketHandle = new Socket();
			InetSocketAddress hsmAddr = new InetSocketAddress(strIP, iPort);
 			iSocketHandle.setOOBInline(true);
			iSocketHandle.connect(hsmAddr, iTimeout);
			iSocketHandle.setSoTimeout(iTimeout);
			iSocketHandle.setTcpNoDelay(true);
			iSocketHandle.setReceiveBufferSize(2048);
			iInputStream = iSocketHandle.getInputStream();
			iOutputStream = iSocketHandle.getOutputStream();
            
//			testCmd00();
			testCmdNC();
			setNotused();
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			setFault();
			releaseSocketHandle();
		}
	}

	public void releaseSocketHandle() {
		setFault();
                
		if (iInputStream != null) {
			try {
				iInputStream.close();
			}
			catch (Exception e) {}
				iInputStream = null;
		}
		if (iOutputStream != null) {
			try {
				iOutputStream.close();
			}
			catch (Exception e) {}
				iOutputStream = null;
		}
        
		if (iSocketHandle != null) {
			try {
				iSocketHandle.close();
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
				iSocketHandle = null;
		}
	}

	public String getIP(){
		return strIP;
	}
		
	public void setUsed() {
		iStatus = FLAG_USED;
	}

	public void setNotused() {
		iStatus = FLAG_NOTUSE;
	}

	public void setFault() {
		iStatus = FLAG_FAULT;
	}

	public int getStatus() {
		return iStatus;
	}

	public boolean isUsed() {
		return (iStatus == FLAG_USED);
	}

	public boolean isUsable() {
		return (iStatus == FLAG_NOTUSE);
	}

	public boolean isFault() {
		return (iStatus == FLAG_FAULT);
	}

	public void write(byte[] aByteOut, int aLength) throws IOException {
		logger.info("write");
		iOutputStream.write(aByteOut, 0, aLength);
	}

	public int read(byte[] aByteIn, int aBufferSize) throws IOException {
//		logger.info("read");
		return iInputStream.read(aByteIn);
//		return iInputStream.read(aByteIn, 0, aBufferSize);
		
	}
    
	public void testCmd00() throws IOException{
		byte[] comm_data = new byte[64];
		write(comm_data, 1);
		if(read(comm_data,64)<=0)
			throw new IOException();
		logger.info("test succ" );
	}
	
    //江南科友测试连接
    public void testCmdNC() throws IOException{
        logger.info("testCmdNC");
        byte[] comm_data = new byte[8];
//        byte[] comm_data = EncDec71_Data();
        byte[] nc = "0000NC".getBytes();
        comm_data [0] = 0x00;
        comm_data [1] = 0x06;
       System.arraycopy(nc, 0, comm_data, 2, 6);
       
        write(comm_data, 8);
        byte[] RcvData = new byte[512];
        read(RcvData,8);
       
        String str1 = new String(comm_data); 
//        logger.info(str1);
       
        String str2 = new String(RcvData); 
//        logger.info(str2);

   }

}
