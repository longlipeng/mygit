package com.test;



public class TestEncDecData71 {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {

			String cfgname="D://workspace//hsdata//HSM//resources//hsm.ini";
//			//判断加密机配置文件是否存在
//			boolean sInitialed = HsmUtil.initial(cfgname, "");	   	        
//	        if(	!sInitialed)
//				try {
//					throw new SecException("hsm.ini initial error!");
//				} catch (SecException e2) {
//					logger.error(e2.getMessage());	
//				}
			
			int nRet = 0;
	        OutputSecResult outRslt = null;
	    	HsmSession hSession = null;
	    	HsmStruct hsmStruct = null;
	    	HsmStruct.EncDec71_Data EncDec71_Data=null;
	    	HsmStruct.EncDec71_Data.EncDec71_DataIN   EncDec71_DataIN=null;
	    	HsmStruct.EncDec71_Data.EncDec71_DataOUT  EncDec71_DataOUT=null;
	    	try{
	        	hSession = new HsmSession(cfgname);
	        	HsmApp hApp = new HsmApp();
	        	nRet = hSession.GetLastError();
	        	if (nRet != 0) {
	        		throw new SecException(	SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
	        	}
	        	hsmStruct = new HsmStruct();
	        	EncDec71_Data = hsmStruct.new EncDec71_Data();
	        	EncDec71_DataIN = EncDec71_Data.new EncDec71_DataIN(
	        			                   "062".getBytes(), 
	        	                           "16".getBytes(),	        	                          
	        	                           new byte[]{96,-56,-4,-67,84,127,-43,-94,125,-1,-74,70,77,113,-39,81}, //[60C8FCBD547FD5A27DFFB6464D71D951]
	        	                           "00000000".getBytes(),
	        	                           "1".getBytes(),
	        	                           "0".getBytes(),
	        	                           "8".getBytes(),
	        	                           "12345678".getBytes());

	        	EncDec71_DataOUT = EncDec71_Data.new EncDec71_DataOUT();

	        	nRet = hApp.EncDec71_Data(hSession, EncDec71_DataIN, EncDec71_DataOUT);
	        	           
	    	}catch (Exception e){
	    		logger.error(e.getMessage());
	    		try {
					throw new SecException(	SecException.ERR_SEC_CODE_8002 + 
										SecException.ERR_SEC_MSG_8002 + ":" +
										new String(EncDec71_DataOUT.reply_code));
				} catch (SecException e1) {
					logger.error(e1.getMessage());	
				}
	        	}
	        finally {
	        	if(	hSession != null) {
	        		hSession.HsmSessionEnd();
	        	}
	        }
	        if (nRet != HsmConst.T_SUCCESS) {
	        	try {
					throw new SecException(SecException.ERR_SEC_CODE_8002
							+ SecException.ERR_SEC_MSG_8002
							+ ":" +new String(EncDec71_DataOUT.reply_code));
				} catch (SecException e) {
					logger.error(e.getMessage());
				}           
	       }
	        else{
	        	 outRslt = new OutputSecResult();
	        	 outRslt.setData(EncDec71_DataOUT.data);
	             outRslt.setDataLen(EncDec71_DataOUT.data_len); 
	             logger.info("EncData replyCode:" + HsmApp.byte2hex(EncDec71_DataOUT.reply_code));
	             logger.info("EncData length:" + HsmApp.byte2hex(EncDec71_DataOUT.data_len));
	             logger.info("EncData data:" + HsmApp.byte2hex(EncDec71_DataOUT.data));

	        }      
	}
	*/
	

}
