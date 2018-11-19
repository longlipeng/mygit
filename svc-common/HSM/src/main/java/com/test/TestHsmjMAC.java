package com.test;

import com.huateng.secure.util.OutputSecResult;
import com.huateng.secure.util.security.CodeUtil;
import com.huateng.secure.util.security.HsmUtil;
import com.huateng.secure.util.security.SecurityUtil;

public class TestHsmjMAC {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		
		try{
			String cfgname="c://config//APIServer//hsm.ini";
			
			boolean hsminit=HsmUtil.initial(cfgname, "11111111");
			if (!hsminit) {
				logger.info("Hsm init failure");
				return;
			}
			//String MACKey="94B6872ADB72A6CE";
			String MACKey="4718BBCB715F00B4";
			String aData = "12345678 12345678";
			int DataLen = aData.length();
			OutputSecResult out = null;
			byte [] mackey = new byte[8];
			CodeUtil.Str2Hex(MACKey.getBytes(), mackey, 8*2);
			out = SecurityUtil.Bank_GenMAC(40, MACKey, 8, aData, DataLen);
			
			logger.info("mac = " + out.dataStr);
			boolean chk = SecurityUtil.Bank_VerifyMac(40, MACKey, 8, out.dataStr, aData, DataLen);
			
			logger.info("Check MAC = " + chk);
		}
		catch (Exception ex){
			
		}

	}*/

}
