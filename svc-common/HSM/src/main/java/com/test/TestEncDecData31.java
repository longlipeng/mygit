package com.test;

public class TestEncDecData31 {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {

			String cfgname="D://workspace//hsdata_tag//1.0.8//java-pom-parent//common-pom-parent//HSM//src//main//resources//hsm1.ini";
			int nRet = 0;
	    	HsmSession hSession = null;
	    	HsmStruct hsmStruct = null;
	    	
	    	
	    	HsmStruct.EncDec31_Data EncDec31_Data=null;
	    	HsmStruct.EncDec31_Data.EncDec31_DataIN   EncDec31_DataIN=null;
	    	HsmStruct.EncDec31_Data.EncDec31_DataOUT  EncDec31_DataOUT=null;
	    	try{
	        	hSession = new HsmSession(cfgname);
	        	HsmApp hApp = new HsmApp();
	        	nRet = hSession.GetLastError();
	        	if (nRet != 0) {
	        		throw new SecException(	SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
	        	}
	        	hsmStruct = new HsmStruct();
	        	EncDec31_Data = hsmStruct.new EncDec31_Data();
	        	EncDec31_DataIN = EncDec31_Data.new EncDec31_DataIN("062".getBytes());

	        	EncDec31_DataOUT = EncDec31_Data.new EncDec31_DataOUT();

	        	nRet = hApp.EncDec31_Data(hSession, EncDec31_DataIN, EncDec31_DataOUT);
	        	           
	    	}catch (Exception e){
	    		logger.error(e.getMessage());
	    		try {
					throw new SecException(	SecException.ERR_SEC_CODE_8002 + 
										SecException.ERR_SEC_MSG_8002 + ":" +
										new String(EncDec31_DataOUT.reply_code));
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
							+ ":" +new String(EncDec31_DataOUT.reply_code));
				} catch (SecException e) {
					logger.error(e.getMessage());
				}           
	       }
	       else{
	             logger.info("EncData replyCode:" + HsmApp.byte2hex(EncDec31_DataOUT.reply_code));
	             logger.info("EncData length:" + HsmApp.byte2hex(EncDec31_DataOUT.pk_encrypted_bylmk));
	             logger.info("EncData data:" + HsmApp.byte2hex(EncDec31_DataOUT.check_value));
	       } 
	        
	        

	    	HsmStruct.EncDec0552_Data EncDec0552_Data=null;
	    	HsmStruct.EncDec0552_Data.EncDec0552_DataIN   EncDec0552_DataIN=null;
	    	HsmStruct.EncDec0552_Data.EncDec0552_DataOUT  EncDec0552_DataOUT=null;
	    	try{
	        	hSession = new HsmSession(cfgname);
	        	HsmApp hApp = new HsmApp();
	        	nRet = hSession.GetLastError();
	        	if (nRet != 0) {
	        		throw new SecException(	SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
	        	}
	        	hsmStruct = new HsmStruct();
	        	EncDec0552_Data = hsmStruct.new EncDec0552_Data();
	        	EncDec0552_DataIN = EncDec0552_Data.new EncDec0552_DataIN("062".getBytes(),EncDec31_DataOUT.pk_encrypted_bylmk);

	        	EncDec0552_DataOUT = EncDec0552_Data.new EncDec0552_DataOUT();

	        	nRet = hApp.EncDec0552_Data(hSession, EncDec0552_DataIN, EncDec0552_DataOUT);
	        	           
	    	}catch (Exception e){
	    		logger.error(e.getMessage());
	    		try {
					throw new SecException(	SecException.ERR_SEC_CODE_8002 + 
										SecException.ERR_SEC_MSG_8002 + ":" +
										new String(EncDec31_DataOUT.reply_code));
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
							+ ":" +new String(EncDec31_DataOUT.reply_code));
				} catch (SecException e) {
					logger.error(e.getMessage());
				}           
	       }
	       else{
	             logger.info("EncData replyCode:" + HsmApp.byte2hex(EncDec0552_DataOUT.reply_code));
	             logger.info("EncData length:" + HsmApp.byte2hex(EncDec0552_DataOUT.pk_encrypted_byzmk));
	       } 	        
	}*/
	
	

}
