package hsmj;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <strong> This class name was HsmSession </strong>
 * </p>
 * 
 * @author Lay
 * @date 2010-5-11 03:44:06
 * @version 1.0
 */
public class HsmSession {
	private static Logger logger = Logger.getLogger(HsmSession.class);
	private static final SessionMonitor sSessionMonitor = new SessionMonitor();
	private static final int ERR_CONFIG_FILE = 0x90;
	private static final int ERR_CONNECT_HSM = 0x91;
	private static final int ERR_SENDTO_HSM = 0x92;
	private static final int ERR_RECVFORM_HSM = 0x93;
	private static final int ERR_SESSION_END = 0x94;
	private static final int ERR_HANDLE_FAULT = 0x95;

	private static ShareHandle[] sHandles;
	private static String[] sIPs;
	private static int sHsmNumber;
	private static int sBalance;
	private static int sPort;
	private static int sTimeOut;
	private static int sPreIndex = -1;

	private int iCurIdx = -1;
	private int iLastErr = -1;

	/**
	 * Create HsmSession Object.
	 */
	public HsmSession(String profile) {
		iLastErr = 0;
		iCurIdx = -1;

		/** (1) Initialize the HSM **/
		try {
			initial(profile);
		} catch (Throwable t) {
			iLastErr = ERR_CONFIG_FILE;
			logger.error(
					(new StringBuffer("Failed to initialize the HSM with profile ["))
							.append(profile).append("]").toString(), t);
			return;
		}

		/** (2) get one available HSM connection **/
		for (int loop = 0; loop < (sTimeOut / 20); loop++) {
			if ((iCurIdx = getSession(-1)) != -1)
				break;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
		}

		/** (3) set the error code if failed **/
		if (iCurIdx == -1)
			iLastErr = ERR_HANDLE_FAULT;
	}

	/**
	 * 
	 * @param aProfileFile
	 * @throws Exception
	 *             void
	 */
	public static synchronized void initial(String profile) throws Exception {
		int i, j, nError;
		String str, sDigit;
		nError = 0;
		/** Determine if initialized **/
		if (sHandles != null) {
			logger.info("Hsm has already bean initialized");
			return;
		}
		logger.info("####### Begin to initialize HSM ######");
		
		/*add by youyong 0206 for test*/
		logger.info("####### Initialize HSM successfully ######");
		if(1==1){
			return;
		}
		/*by youyong end*/
/*		FileInputStream raf = new FileInputStream(profile);
		Properties pr = new Properties();
		pr.load(raf);*/
		
		InputStream input = HsmSession.class.getResourceAsStream(profile);
		Properties pr = new Properties();
		pr.load(input);

		sDigit = pr.getProperty("NUMBER");
		sHsmNumber = Integer.parseInt(sDigit);
		sDigit = pr.getProperty("BALANCE");
		sBalance = Integer.parseInt(sDigit);
		sDigit = pr.getProperty("PORT");
		sPort = Integer.parseInt(sDigit);
		sDigit = pr.getProperty("TIMEOUT");
		sTimeOut = Integer.parseInt(sDigit);

		sIPs = new String[sHsmNumber];
		for (i = 0; i < sHsmNumber; i++) {
			str = Integer.toString(i + 1);
			sIPs[i] = pr.getProperty(str);
		}

		/** Initialize the HSM connection pool with the style of load balance **/
		ShareHandle[] tHandle = new ShareHandle[sHsmNumber * sBalance];
		for (i = 0; i < sBalance; i++) {
			for (j = 0; j < sHsmNumber; j++) {
				tHandle[i * sHsmNumber + j] = new ShareHandle(sIPs[j], sPort, sTimeOut);
				if (tHandle[i * sHsmNumber + j].isFault())
					nError++;
			}
		}

		if (nError == sHsmNumber * sBalance) {
			throw new Exception("Failed to create connection with HSM");
		}

		/** Create and start the Monitor thread **/
		sHandles = tHandle;
		sSessionMonitor.addHandle(sHandles);
		sSessionMonitor.start();
		logger.info("####### Initialize HSM successfully ######");
	}

	private static synchronized int getSession(int idx) {
		int i = 0;
		boolean tStatus = false;
		int tNumOfSession = sHsmNumber * sBalance;

		for (i = sPreIndex + 1; i < tNumOfSession; i++) {
			ShareHandle tHandle = sHandles[i];

			/** If is a retry request, switch to next balance HSM **/
			if (idx != -1 && ((idx % sHsmNumber) == (i % sHsmNumber) || tHandle.isUsed())) {
				if ((idx % sHsmNumber) == (i % sHsmNumber)) {
					continue;
				} else {
					i = i + sHsmNumber;
				}
			}

			if (tHandle.isUsable()) {
				tHandle.setUsed();
				sPreIndex = i;
				tStatus = true;
				break;
			}
		}

		if (!tStatus) {
			for (i = 0; i < sPreIndex; i++) {
				ShareHandle tHandle = sHandles[i];
				if (idx != -1 && ((idx % sHsmNumber) == (i % sHsmNumber) || tHandle.isUsed())) {
					if ((idx % sHsmNumber) == (i % sHsmNumber)) {
						continue;
					} else {
						i = i + sHsmNumber;
					}
				}

				if (tHandle.isUsable()) {
					tHandle.setUsed();
					sPreIndex = i;
					tStatus = true;
					break;
				}
			}
		}

		if (!tStatus) {
			i = -1;
			logger.error("HsmSession.getSession() fail to get a TCP Connection !"
					+ " E1".getBytes() + "  1");
		}

		return i;
	}

	public int GetPortConf() {
		return sPort;
	}

	public int GetLastError() {
		return iLastErr;
	}

	public void SetErrCode(int nErrCode) {
		iLastErr = nErrCode;
		return;
	}

	/**
	 * 
	 * @param nErrCode
	 * @return String
	 */
	public String ParseErrCode(int nErrCode) {
		String sMeaning;
		switch (nErrCode) {
			case 0 :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":操作正确,状态正常";
				break;
			case ERR_CONFIG_FILE :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":配置文件异常";
				break;
			case ERR_CONNECT_HSM :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接密码机失败";
				break;
			case ERR_SENDTO_HSM :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":发送数据至密码机失败";
				break;
			case ERR_RECVFORM_HSM :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":接收密码机数据失败";
				break;
			case ERR_SESSION_END :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接已关闭";
				break;
			case ERR_HANDLE_FAULT :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接句柄状态异常";
				break;
			default :
				sMeaning = "0x" + Integer.toHexString(nErrCode) + ":异常操作,检查密码机日志";
				break;
		}
		return sMeaning;
	}

	/**
	 * Send data to HSM
	 */
	private int SendData(byte[] byteOut, int nLength) {
		logger.debug((new StringBuffer("HandleID[")).append(iCurIdx)
				.append("] Send data to HSM......").toString());
		ShareHandle tHandle = sHandles[iCurIdx];
		if (tHandle.isFault()) {
			iLastErr = ERR_HANDLE_FAULT;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.SendData() failed - ").append(ParseErrCode(iLastErr))
					.toString());
			return iLastErr;
		}

		try {
			tHandle.write(byteOut, nLength);
			iLastErr = 0;
		} catch (Throwable t) {
			tHandle.setFault();
			iLastErr = ERR_SENDTO_HSM;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.SendData() failed - ").append(ParseErrCode(iLastErr))
					.append(":").append(t.getMessage()).toString());
		}

		logger.info((new StringBuffer("HandleID[")).append(iCurIdx).append("]Data send to [")
				.append(tHandle.getIP()).append("];").append("byteOut = [").append(byteOut)
				.append("];").append("nLength = [").append(nLength)
				.append("];Return Result iLastErr = [").append(iLastErr).append("], description=[")
				.append(ParseErrCode(iLastErr)).append("]").toString());
		return iLastErr;
	}

	/**
	 * Receive data from HSM
	 * **/
	private int RecvData(byte[] byteIn) {
		logger.debug((new StringBuffer("HandleID[")).append(iCurIdx)
				.append("] Receive data from HSM......").toString());
		ShareHandle tHandle = sHandles[iCurIdx];
		int nRcvLen;

		iLastErr = 0;
		if (tHandle.isFault()) {
			iLastErr = ERR_HANDLE_FAULT;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.RecvData() failed - ").append(ParseErrCode(iLastErr))
					.toString());
			return -1;
		}

		try {
			nRcvLen = tHandle.read(byteIn, 256);
		} catch (Throwable t) {
			logger.error(t.getMessage());	
			nRcvLen = -1;
			iLastErr = ERR_RECVFORM_HSM;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.RecvData() failed - ").append(ParseErrCode(iLastErr))
					.append(":").append(t.getMessage()).toString());
		}

		/** In case of receive timeout **/
		if (nRcvLen <= 0) {
			tHandle.setFault();
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.RecvData() failed - received timeout").toString());
		}

		logger.info((new StringBuffer("HandleID[")).append(iCurIdx).append("]Data received from [")
				.append(tHandle.getIP()).append("];").append("byteIn = [").append(byteIn)
				.append("];").append("nRcvLen = [").append(nRcvLen)
				.append("];Return Result iLastErr = [").append(iLastErr).append("], description=[")
				.append(ParseErrCode(iLastErr)).append("]").toString());
		return nRcvLen;
	}

	public int SndAndRcvData(byte[] SndData, int nLength, byte[] RcvData) {
		int ret;
		long start = System.currentTimeMillis();

		ret = SendData(SndData, nLength);
		if (ret != 0) { // fail to send data, get another connection to send the data
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] send data failed and for 1st time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret != 0)
				return ret;
		}

		ret = RecvData(RcvData);
		if (ret <= 0) {// Receive timeout or error, get another connection to do the job
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] rcv data failed and for 2nd time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret == 0) {
				if ((ret = RecvData(RcvData)) <= 0)
					return ERR_RECVFORM_HSM;
			} else {
				return ERR_SENDTO_HSM;
			}
		}
		// HsmApp.OutputDataHex("Receive Data",RcvData,ret);
		if (RcvData[0] != 'A') {
			iLastErr = RcvData[1] & 0xff;
		} else
			iLastErr = 0;

		logger.info((new StringBuffer("Hsm SndAndRcvData cost ["))
				.append(System.currentTimeMillis() - start).append("] msec").toString());
		return iLastErr;
	}
	
	public int SndAndRcvDataJNKY(byte[] SndData, int nLength, byte[] RcvData) {
		int ret;
		long start = System.currentTimeMillis();

		ret = SendData(SndData, nLength);
		if (ret != 0) { // fail to send data, get another connection to send the data
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] send data failed and for 1st time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret != 0)
				return ret;
		}

		ret = RecvData(RcvData);
		if (ret <= 0) {// Receive timeout or error, get another connection to do the job
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] rcv data failed and for 2nd time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret == 0) {
				if ((ret = RecvData(RcvData)) <= 0)
					return ERR_RECVFORM_HSM;
			} else {
				return ERR_SENDTO_HSM;
			}
		}

		logger.info((new StringBuffer("Hsm SndAndRcvData cost ["))
				.append(System.currentTimeMillis() - start).append("] msec").toString());
		return iLastErr;
	}
	

	public int HsmSessionEnd() {
		if (iCurIdx != -1) {
			ShareHandle tHandle = sHandles[iCurIdx];
			if (tHandle.isUsed()) {
				logger.info((new StringBuffer("HandleID[")).append(iCurIdx)
						.append("] connection is released to connection pool").toString());
				tHandle.setNotused();
				iLastErr = 0;
			}
		}
		return iLastErr;
	}

	public static synchronized void destroy() {
		logger.info("HsmSession start to destory and release connections");
		if (sHandles != null) {
			for (int i = 0; i < sHandles.length; i++) {
				ShareHandle tHandle = sHandles[i];
				if (!tHandle.isFault()) {
					tHandle.releaseSocketHandle();
				}
			}
			sHandles = null;
		}
		logger.info("HsmSession start to destory completed");
	}

}
