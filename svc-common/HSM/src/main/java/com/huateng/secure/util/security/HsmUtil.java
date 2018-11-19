/*
 * @(#)HsmUtil.java V0.1
 * 
 * Project: CUPSecure
 * 
 * Modify Information:
 * =============================================================================
 * Author       Date                  Description 
 * --------------------------------------------------------------- ---------- 
 * Jack      2005-11-11              ֱ������ܻ�,�ӽ���,ǩ��,��֤ǩ��,�Լ���Կ�����һЩ������
 * Jack      2006-02-10              �޸ķǶԳ�תPIN�������ü��ܻ� PIK���ȵĲ���Ϊ00
 * 
 * Copyright Notice:
 * =============================================================================
 * Copyright (c) 2001-2005 Beijing HiTRUST Technology Co., Ltd. 1808 Room,
 * Science & Technology Building, No. 9 South Street, Zhong Guan Cun, Haidian
 * District, Beijing ,100081, China All rights reserved.
 * 
 * This software is the confidential and proprietary information of Beijing
 * HiTRUST Technology Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HiTRUST.
 * 
 * Warning:
 * =============================================================================
 * 
 */
package com.huateng.secure.util.security;



import hsmj.HsmApp;
import hsmj.HsmConst;
import hsmj.HsmSession;
import hsmj.HsmStruct;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.huateng.secure.util.Base64;
import com.huateng.secure.util.OutputSecResult;
import com.huateng.secure.util.exception.SecException;
import com.huateng.secure.util.log.LogWriter;
/**
 * 
 * <p>
 * <strong>HsmUtil.</strong>
 * </p>
 * @author ZERO
 * @date 2010-6-1 12:58:51
 * @version 1.0
 */
public class HsmUtil {
	private static  Logger logger = Logger.getLogger(HsmUtil.class);
	private static boolean sInitialed     = false;
    private static String  sHSMPassword   = "";
    private static String  sHsmConfigFile = "";

    public static boolean initial(String aHsmConfigFile, String aPassword) {
        File tHsmConfigFile = new File(aHsmConfigFile);
        sInitialed = tHsmConfigFile.exists();
        if (sInitialed)
            sHsmConfigFile = aHsmConfigFile;
        sHSMPassword = aPassword;
        return sInitialed;
    }

    /**
     * 
     * 
     * @param aIndex
     * @param aData
     * @param aDataLen
     * @return
     */
    public OutputSecResult RSAEncrypt(String aIndex, byte[] aData, String aDataLen) throws SecException {
    	
    	int nRet;
        
    	if(	!sInitialed)
            throw new SecException("加密机配置文件不存在，初始化失败");        
        OutputSecResult outRslt = null;
        HsmStruct hsmStruct = null;
        HsmApp hApp 		= null;
        HsmSession hSession = null;
        HsmStruct.PublicKeyEncrypt.PublicKeyEncryptOut publicKeyEncryptOut = null;
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            hApp = new HsmApp();
            nRet = hSession.GetLastError();            
            if (nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            hsmStruct = new HsmStruct();
            HsmStruct.PublicKeyEncrypt publicKeyEncrypt = hsmStruct.new PublicKeyEncrypt();
            logger.info(""+aIndex.getBytes().length);
            logger.info(""+"0000".getBytes().length);
            logger.info(""+aDataLen.getBytes().length);
            logger.info(""+"01".getBytes().length);
            logger.info(""+"0".getBytes().length);
            logger.info(""+sHSMPassword.getBytes().length);
            logger.info(""+"0".getBytes().length);
            logger.info(""+aData.length);
            
            HsmStruct.PublicKeyEncrypt.PublicKeyEncryptIn publicKeyEncryptIn = publicKeyEncrypt.new PublicKeyEncryptIn(
                    aIndex.getBytes(), 
                    "0000".getBytes(), 
                    aDataLen.getBytes(),
                    "01".getBytes(),
                    "0".getBytes(),
                    sHSMPassword.getBytes(),
                    "0".getBytes(), 		
                    aData);
            
            publicKeyEncryptOut = publicKeyEncrypt.new PublicKeyEncryptOut();
            
            nRet = hApp.PublicKeyEncrypt(	hSession, 
            								publicKeyEncryptIn,
            								publicKeyEncryptOut);
            
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
        	throw new SecException(	SecException.ERR_SEC_CODE_8001
                    				+ SecException.ERR_SEC_MSG_8001);
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.PublicKeyEncrypt = ["+nRet+"]");
        if (nRet != HsmConst.T_SUCCESS) {
            throw new SecException(	SecException.ERR_SEC_CODE_8001
                    				+ SecException.ERR_SEC_MSG_8001
                    				+ publicKeyEncryptOut.reply_code[0]
                    				+ publicKeyEncryptOut.reply_code[1]);
        }
        else {
            outRslt = new OutputSecResult();
            
            int tLen = CodeUtil.formatDataLen(publicKeyEncryptOut.dataLen);
            byte[] tByte = new byte[tLen];
            System.arraycopy(publicKeyEncryptOut.data, 0, tByte, 0, tLen);
            
            outRslt.setData(tByte);            
            outRslt.setDataLen(publicKeyEncryptOut.dataLen);
        }

        return outRslt;

    }

    /**
     * �ǶԳ�תPIN
     * 
     * @param aKey1Index
     * @param aData1Len
     * @param aData
     * @param aKey2Index
     * @return
     * @throws CUPSException
     */
    public OutputSecResult UnsymConvertPin(	String aKey1Index, byte[] aDataLen,byte[] aData, 
    										String aKey2Index, String aCardNO) throws SecException {
    	int nRet;
    	
    	if(	!sInitialed)
            throw new SecException("加密机配置文件不存在，初始化失败");
        String tFlag = "02";
        
        if(	aCardNO.equals("")	|| 
        	aCardNO == null		) 	{
            
        	tFlag = "01";
            aCardNO = "0000";
        }
        
        OutputSecResult outRslt = null;
        HsmSession hSession = null;
        HsmStruct hsmStruct = null;
        HsmStruct.UnsymConvertPin unsymCvtPin = null;
        HsmStruct.UnsymConvertPin.UnsymConvertPinIn  unsymCvtPinIn = null;
        HsmStruct.UnsymConvertPin.UnsymConvertPinOut unsymCvtPinOut = null;
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if (nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8002
                        				+ SecException.ERR_SEC_MSG_8002);
            }

            hsmStruct = new HsmStruct();
            unsymCvtPin = hsmStruct.new UnsymConvertPin();
            unsymCvtPinIn = unsymCvtPin.new UnsymConvertPinIn(
            		aKey1Index.getBytes(), 	// rsa ID
                    "0000".getBytes(), 		// pri key len
                    aDataLen, 				// data len
                    "01".getBytes(), 		// padFlag
                    "1".getBytes(), 		// athFlag
                    sHSMPassword.getBytes(),// password
                    "0".getBytes(), 		// pri key
                    aData, 					// dec data
                    tFlag.getBytes(), 		// flagIn
                    aCardNO.getBytes(), 	// acco in
                    aKey2Index.getBytes(), 	// bmk ID
                    "00".getBytes(), 		// pik len
                    "0000".getBytes(), 		// pik
                    tFlag.getBytes(), 		// flag out
                    aCardNO.getBytes() 		// acco 2
            );
            
            unsymCvtPinOut = unsymCvtPin.new UnsymConvertPinOut();

            nRet = hApp.UnsymConvertPin(hSession, unsymCvtPinIn, unsymCvtPinOut);
        }
        catch (Exception e) {
            throw new SecException(	SecException.ERR_SEC_CODE_8002
                    				+ SecException.ERR_SEC_MSG_8002);
        }
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.UnsymConvertPin = ["+nRet+"]");
        if (nRet != HsmConst.T_SUCCESS) {
            throw new SecException(SecException.ERR_SEC_CODE_8002
                    				+ SecException.ERR_SEC_MSG_8002
                    				+ unsymCvtPinOut.reply_code[0]
                    				+ unsymCvtPinOut.reply_code[1]);

        }
        else {
            outRslt = new OutputSecResult();
            if(	tFlag.equals("01")) {
                byte[] tByte = CodeUtil.Byte2byte(unsymCvtPinOut.pinblock);
                outRslt.setData(tByte);
                outRslt.setDataLen(unsymCvtPinOut.pinLen);
            }
            else if( tFlag.equals("02")) {
                byte[] tByte = CodeUtil.Byte2byteWithCardNO(unsymCvtPinOut.pinblock);
                outRslt.setData(tByte);
                outRslt.setDataLen(unsymCvtPinOut.pinLen);
            }
            else {
                throw new SecException(	SecException.ERR_SEC_CODE_8003
                        				+ SecException.ERR_SEC_MSG_8003);
            }
        }
        return outRslt;
    }

    /**
     * 
     * 
     * @param aByte
     * @param aSignFlag
     * @param aPrivateKey
     * @return
     * @throws CUPSException
     */
    public byte[] signByPrivate(byte[] aByte, String aSignFlag, byte[] aPrivateKey) throws SecException {
        
    	int nRet;
        if(	!sInitialed)
            throw new SecException("加密机配置文件不存在，初始化失败");
        HsmSession hSession = null;

        HsmStruct.Sign.SignOut signOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.Sign sign = hsmStruct.new Sign();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            nRet = hSession.GetLastError();
            if (nRet != 0) {
            	LogWriter.debug("hApp.GetLastError = ["+nRet+"]");
                throw new SecException(SecException.ERR_SEC_CODE_8004
                        				+ SecException.ERR_SEC_MSG_8004);
            }
            
            HsmApp hApp = new HsmApp();
            HsmStruct.Sign.SignIn signIn = sign.new SignIn(	
            					"FFF".getBytes(),
                    			CodeUtil.formatLength(aPrivateKey.length).getBytes(),
                    			CodeUtil.formatLength(aByte.length).getBytes(), 
                    			aSignFlag.getBytes(), 
                    			"1".getBytes(), 
                    			sHSMPassword.getBytes(),
                    			aPrivateKey, aByte);

            signOut = sign.new SignOut();
            
            nRet = hApp.Sign(hSession, signIn, signOut);
        }
        catch (Exception e) {
            throw new SecException("ǩ������ʧ��");
        }
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.Sign = ["+nRet+"]");
        if(	nRet != HsmConst.T_SUCCESS) {
            throw new SecException("ǩ������ʧ��");
        }
        else {
            int tLen = CodeUtil.formatDataLen(signOut.dataLen);
            byte[] tByte = new byte[tLen];
            System.arraycopy(signOut.data, 0, tByte, 0, tLen);
            return tByte;
        }
    }

    /**
     * ǩ�����㣬����index
     * 
     * @param aByte
     * @return
     * @throws SecException
     */
    public OutputSecResult signByIndex(byte[] aByte, String aSignFlag, String aKeyIndex) throws SecException {

        int nRet;
        OutputSecResult outRslt = null;
        
        if(	!sInitialed)
            throw new SecException("δ��ʼ��HsmUtil����ȷ���Ѿ�ʹ��HsmUtil.initial(���ܻ����������ļ���)������ʼ����");
                
        HsmSession hSession = null;
        
        HsmStruct.Sign.SignOut signOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.Sign sign = hsmStruct.new Sign();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            
            HsmStruct.Sign.SignIn signIn = sign.new SignIn(	
            					aKeyIndex.getBytes(),
            					"0000".getBytes(), 
            					CodeUtil.formatLength(aByte.length).getBytes(), 
            					aSignFlag.getBytes(), 
            					"1".getBytes(),
            					sHSMPassword.getBytes(), 
            					"0000".getBytes(), 
            					aByte);
            
            signOut = sign.new SignOut();

            nRet = hApp.Sign(hSession, signIn, signOut);
            
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
            throw new SecException("ǩ������ʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.Sign = ["+nRet+"]");
        if(nRet != HsmConst.T_SUCCESS) {
            throw new SecException("ǩ������ʧ��");
        }
        else {
            
        	outRslt = new OutputSecResult();
        	
        	int tLen = CodeUtil.formatDataLen(signOut.dataLen);
            byte[] tByte = new byte[tLen];
            System.arraycopy(signOut.data, 0, tByte, 0, tLen);
           
        	outRslt.setData(tByte);            
            outRslt.setDataLen(signOut.dataLen);
            
            return outRslt;
        }
    }
    
    /**
     * ǩ�����㣬����index,����byte[]
     * 
     * @param aByte
     * @return
     * @throws SecException
     */
    public byte[] signByIndexByte(byte[] aByte, String aSignFlag, String aKeyIndex) throws SecException {

        int nRet;
        
        if(	!sInitialed)
            throw new SecException("δ��ʼ��HsmUtil����ȷ���Ѿ�ʹ��HsmUtil.initial(���ܻ����������ļ���)������ʼ����");        
        HsmSession hSession = null;
        
        HsmStruct.Sign.SignOut signOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.Sign sign = hsmStruct.new Sign();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            
            HsmStruct.Sign.SignIn signIn = sign.new SignIn(	
            					aKeyIndex.getBytes(),
            					"0000".getBytes(), 
            					CodeUtil.formatLength(aByte.length).getBytes(), 
            					aSignFlag.getBytes(), 
            					"1".getBytes(),
            					sHSMPassword.getBytes(), 
            					"0000".getBytes(), 
            					aByte);
            
            signOut = sign.new SignOut();

            nRet = hApp.Sign(hSession, signIn, signOut);
            
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
            throw new SecException("ǩ������ʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.Sign = ["+nRet+"]");
        if(nRet != HsmConst.T_SUCCESS) {
            throw new SecException("ǩ������ʧ��");
        }
        else {
            
        	int tLen = CodeUtil.formatDataLen(signOut.dataLen);
            byte[] tByte = new byte[tLen];
            System.arraycopy(signOut.data, 0, tByte, 0, tLen);
           
            return tByte;
        }
    }

    /**
     * ���빫Կ��ݣ���֤ǩ��
     * 
     * @param aPublicKey
     * @param aSignatureData
     * @param aDigestValue
     * @param aSignFlag
     * @return
     * @throws SecException
     */
    public boolean verifyByPublic(	byte[] aPublicKey, byte[] aSignatureData,
            						byte[] aDigestValue, String aSignFlag) throws SecException {
        int nRet;
        if(	!sInitialed)
            throw new SecException("HsmUtil.initial错误");
        
        /**{
        	byte[] a=new byte[2000];
        	CodeUtil.Hex2Str(aPublicKey, a, aPublicKey.length);
        	logger.info("aPublicKey="+new String(a).trim());
        	CodeUtil.Hex2Str(aSignatureData, a, aSignatureData.length);
        	logger.info("aSignatureData="+new String(a).trim());
        	CodeUtil.Hex2Str(aDigestValue, a, aDigestValue.length);
        	logger.info("aDigestValue="+new String(a).trim());
        	logger.info("aSignFlag="+aSignFlag);
         
        }**/
        HsmSession hSession = null;

        HsmStruct.VerifySign.VerifySignOut verifySignOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.VerifySign verifySign = hsmStruct.new VerifySign();
        
        try	{
            HsmApp hApp = new HsmApp();
            hSession = new HsmSession(sHsmConfigFile);
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            HsmStruct.VerifySign.VerifySignIn verifySignIn = verifySign.new VerifySignIn(
                    			"fff".getBytes(), 
                    			CodeUtil.formatLength(aPublicKey.length).getBytes(), 
                    			CodeUtil.formatLength(aDigestValue.length).getBytes(), 
                    			CodeUtil.formatLength(aSignatureData.length).getBytes(),
                    			aSignFlag.getBytes(), 
                    			"1".getBytes(),
                    			sHSMPassword.getBytes(), 
                    			aPublicKey, 
                    			aDigestValue,// CodeUtil.formatData(aDigestValue),
                    			CodeUtil.formatData(aSignatureData));
            
            verifySignOut = verifySign.new VerifySignOut();

            nRet = hApp.VerifySign(hSession, verifySignIn, verifySignOut);
            //LogWriter.debug("验证签名返回的nRet=["+nRet+"]");
        }
        catch (Exception e) {
//            LogWriter.error(this, e);
            logger.error(e.getMessage());
            throw new SecException("��֤ǩ������ʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.VerifySign = ["+nRet+"]");
       if(	nRet != HsmConst.T_SUCCESS) {
        	throw new SecException("��֤ǩ������ʧ��");
        }
        else {
        	
            if(	verifySignOut.reply_code[0] == 48	&&
            	verifySignOut.reply_code[1] == 48) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    /**
     * ʹ���ڲ������RSA��Կ������ţ���֤ǩ��
     * 
     * @param aPublicKey
     * @param aSignatureData
     * @param aDigestValue
     * @param aSignFlag
     * @return
     * @throws SecException
     */
    public boolean verifyByIndex(	String aKeyIndex, byte[] aSignatureData,
            						byte[] aDigestValue, String aSignFlag) throws SecException {
        int nRet;
        if(	!sInitialed)
            throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        HsmSession hSession = null;

        HsmStruct.VerifySign.VerifySignOut verifySignOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.VerifySign verifySign = hsmStruct.new VerifySign();
        
        try	{
            HsmApp hApp = new HsmApp();
            hSession = new HsmSession(sHsmConfigFile);
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            HsmStruct.VerifySign.VerifySignIn verifySignIn = verifySign.new VerifySignIn(
            					aKeyIndex.getBytes(), 
                    			"0000".getBytes(), 
                    			CodeUtil.formatLength(aDigestValue.length).getBytes(), 
                    			CodeUtil.formatLength(aSignatureData.length).getBytes(),
                    			aSignFlag.getBytes(), 
                    			"1".getBytes(),
                    			sHSMPassword.getBytes(), 
                    			"0".getBytes(), 
                    			aDigestValue,// CodeUtil.formatData(aDigestValue),
                    			CodeUtil.formatData(aSignatureData));
            
            verifySignOut = verifySign.new VerifySignOut();

            nRet = hApp.VerifySign(hSession, verifySignIn, verifySignOut);
            
        }
        catch (Exception e) {
//            LogWriter.error(this, e);
            logger.error(e.getMessage());
            throw new SecException("��֤ǩ������ʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.VerifySign = ["+nRet+"]");
        if(	nRet != HsmConst.T_SUCCESS) {
        	throw new SecException("��֤ǩ������ʧ��");
//        	return false;  //��Ҫ����ܻ���ѯ��
        }
        else {
        	
            if(	verifySignOut.reply_code[0] == 48	&&
            	verifySignOut.reply_code[1] == 48) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * ��ɷǶԳ�RSA��Կ��
     * 
     * @param aKeyIndex
     * @param aKeyLen
     * @return
     * @throws SecException
     */
    public ArrayList GenerateRSAKey(String aKeyIndex, String aKeyLen) throws SecException {
    	
    	int nRet;
    	
    	if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        ArrayList tList = new ArrayList();
        
        HsmSession hSession = null;
        HsmStruct.GenerateRsaKey.GenerateRsaKeyOut generateRsaKeyOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.GenerateRsaKey generateRsaKey = hsmStruct.new GenerateRsaKey();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
            	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            HsmStruct.GenerateRsaKey.GenerateRsaKeyIn generateRsaKeyIn = generateRsaKey.new GenerateRsaKeyIn(
                    			aKeyLen.getBytes(), 
                    			aKeyIndex.getBytes(), 
                    			"3".getBytes(),
                    			sHSMPassword.getBytes());
            
            generateRsaKeyOut = generateRsaKey.new GenerateRsaKeyOut();

            nRet = hApp.GenerateRsaKey(hSession, generateRsaKeyIn,generateRsaKeyOut);
            
        }
        catch (Exception e) {
//            LogWriter.error(e.getMessage());
            logger.error(e.getMessage());
            throw new SecException("���RSA��Կʧ��");
        }
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        LogWriter.debug("hApp.GenerateRsaKey = ["+nRet+"]");
        if(	nRet != HsmConst.T_SUCCESS) {
        	
        	logger.info(""+nRet);
        	logger.info(""+HsmConst.T_SUCCESS);
        	throw new SecException("���RSA��Կʧ��");
        }
        else {
//            LogWriter.debug("==========================���ܻ����Կ================================="+Base64.encode(generateRsaKeyOut.publicKey));
//            LogWriter.debug("==========================���ܻ��˽Կ================================="+Base64.encode(generateRsaKeyOut.privateKey));
            
            String tProviderName;
            KeyFactory keyFactPub = null;
            KeyFactory keyFactPri = null;
//            int tPubLen = Integer.parseInt(new String(generateRsaKeyOut.publicKeyLen));
//            int tPriLen = Integer.parseInt(new String(generateRsaKeyOut.privateKeyLen));
            
            byte[] bX509PubKeyHeader = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, 
            							-122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0 };
            byte[] bX509PriKeyHeader = {48, -126, 2, 117, 2, 1, 0, 48, 13, 6, 9,42, -122, 
            							72, -122, -9, 13, 1, 1, 1, 5, 0, 4, -126, 2,95 };

            byte[] tPub = new byte[bX509PubKeyHeader.length + generateRsaKeyOut.publicKey.length];
            byte[] tPri = new byte[bX509PriKeyHeader.length + generateRsaKeyOut.privateKey.length];
            
            System.arraycopy(bX509PubKeyHeader, 0, tPub, 0, bX509PubKeyHeader.length);
            System.arraycopy(generateRsaKeyOut.publicKey, 0, tPub, bX509PubKeyHeader.length, generateRsaKeyOut.publicKey.length);
            System.arraycopy(bX509PriKeyHeader, 0, tPri, 0,bX509PriKeyHeader.length);
            System.arraycopy(generateRsaKeyOut.privateKey, 0, tPri,bX509PriKeyHeader.length, generateRsaKeyOut.privateKey.length);

            // ��ɹ�Կ
            X509EncodedKeySpec keySpecPub = new X509EncodedKeySpec(tPub);
            PKCS8EncodedKeySpec keySpecPri = new PKCS8EncodedKeySpec(tPri);
            
            try {
                
            	java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider());
 //               java.security.Security.addProvider(	new org.bouncycastle.jce.provider.BouncyCastleProvider());
                
                Provider [] p = java.security.Security.getProviders();
                
                for(int i= 0;i<p.length;i++){
//                    LogWriter.debug("==========================Providers=================================");
//                    LogWriter.debug("====p["+i+"].getName()======" + p[i].getName());
                }
                
                if(aKeyLen.equals("0512")){
                    tProviderName="BC";
                }else{
                    tProviderName="SunJSSE";
                }
                
                keyFactPub = KeyFactory.getInstance("RSA",tProviderName);                
                keyFactPri = KeyFactory.getInstance("RSA",tProviderName);
                
                PublicKey 	tPubKey  = keyFactPub.generatePublic(keySpecPub);
                PrivateKey 	tPrivKey = keyFactPri.generatePrivate(keySpecPri);
                
                tList.add(0, tPubKey);
                tList.add(1, tPrivKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage());
//                LogWriter.debug(e.getMessage());
                throw new SecException("���ܻ����Կ���ת��Ϊ��Ӧ��Key����ʱ����");
            }
        }
        return tList;

    }

    /**
     * ��ݴ����indexֵɾ����ܻ��е�һ��RSA��Կ��
     * 
     * @param aIndex
     * @return
     * @throws SecException
     */
    public boolean delRSAKeyByIndex(String aIndex) throws SecException {
    	
    	int nRet;
    	
    	if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        HsmStruct.DelRSAKey.DelRSAKeyOut delRSAKeyOut = null;
        HsmStruct hsmStruct = new HsmStruct();

        HsmSession hSession = null;

        HsmStruct.DelRSAKey delRSAKey = hsmStruct.new DelRSAKey();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            
            if(	nRet != 0) {
//                LogWriter.error(SecException.CS_EXC_MSG_8001
//                        		+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            HsmStruct.DelRSAKey.DelRSAKeyIn delRSAKeyIn = delRSAKey.new DelRSAKeyIn(
                    			aIndex.getBytes(), 
                    			sHSMPassword.getBytes());
            delRSAKeyOut = delRSAKey.new DelRSAKeyOut();
            
            nRet = hApp.DelRSAKey(hSession, delRSAKeyIn, delRSAKeyOut);
            
            hSession.HsmSessionEnd();
        }
        catch (Exception e) {
            hSession.HsmSessionEnd();
//            LogWriter.error(SecException.CS_EXC_MSG_8003 + e.getMessage());
            throw new SecException("ɾ����ܻ��е�RSA��Կʧ��");
        }
        
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        if (nRet != HsmConst.T_SUCCESS) {
            throw new SecException("ɾ����ܻ��е�RSA��Կʧ��");
        }
        else {
            return true;
        }
    }

    /**
     * ��˽Կ��ݵ�����ܻ�
     * 
     * @param aPrivate
     * @param aKeyIndex
     * @param aPassWord
     * @throws SecException
     */
    public boolean loadPrivateKey(	String aPrivate, int aKeyIndex,
            						String aPassWord) throws SecException {
        int nRet;
        if(	!sInitialed)
        	throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        HsmSession hSession = null;

        byte[] tPrivate = new byte[Base64.decode(aPrivate).length - 26];
        
        System.arraycopy(Base64.decode(aPrivate), 26, tPrivate, 0, Base64.decode(aPrivate).length - 26);

        HsmStruct.ImportPrivateKey.ImportPrivateKeyOut importPrivateKeyOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.ImportPrivateKey importPrivateKey = hsmStruct.new ImportPrivateKey();
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if(	nRet != 0) {
//                LogWriter.error(SecException.CS_EXC_MSG_8001
//                        		+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }
            
            HsmStruct.ImportPrivateKey.ImportPrivateKeyIn importPrivateKeyIn = importPrivateKey.new ImportPrivateKeyIn(
                    			CodeUtil.formatLength(aKeyIndex).getBytes(), 
                    			CodeUtil.formatLength(tPrivate.length).getBytes(), 
                    			"1".getBytes(), 
                    			sHSMPassword.getBytes(), 
                    			tPrivate);
            
            importPrivateKeyOut = importPrivateKey.new ImportPrivateKeyOut();

            nRet = hApp.ImportPrivateKey(hSession, importPrivateKeyIn,importPrivateKeyOut);
        }
        catch (Exception e) {
//            LogWriter.error(SecException.CS_EXC_MSG_8003 + e.getMessage());
            throw new SecException("˽Կ������ܻ�ʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        
        if (nRet != HsmConst.T_SUCCESS) {
            throw new SecException("˽Կ������ܻ�ʧ��");
        }
        else {
            return true;
        }
    }

    /**
     * ��ѯ�����ڼ��ܻ��е�RSA��Կ��index
     * 
     * @return ǰ4λΪ���ܻ��е���Կ����������λΪ��Կindexֵ��
     * @throws SecException
     */
    public byte[] queryAllRSAKeyIndex() throws SecException {
        
    	int nRet;
        if(	!sInitialed)
        	throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        byte[] tByte = null;
        HsmSession hSession = null;

        HsmStruct.QueryRSAKey.QueryRSAKeyOut queryRSAKeyOut;
        HsmStruct hsmStruct = new HsmStruct();
        HsmStruct.QueryRSAKey queryRSAKey = hsmStruct.new QueryRSAKey();
        HsmStruct.QueryRSAKey.QueryRSAKeyIn queryRSAKeyIn = queryRSAKey.new QueryRSAKeyIn();
        queryRSAKeyOut = queryRSAKey.new QueryRSAKeyOut();
        
        try	{
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            
            if (nRet != 0) {
//                LogWriter.error(SecException.CS_EXC_MSG_8001
//                        		+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }

            nRet = hApp.QueryRSAKey(hSession, queryRSAKeyIn, queryRSAKeyOut);
        }
        catch (Exception e) {
//            LogWriter.error(SecException.CS_EXC_MSG_8003 + e.getMessage());
            throw new SecException("��ѯ�����ڼ��ܻ��е�RSA��Կindexʧ��");
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        if (nRet != HsmConst.T_SUCCESS) {
            throw new SecException("��ѯ�����ڼ��ܻ��е�RSA��Կindexʧ��");
        }
        else {
            tByte = new byte[queryRSAKeyOut.indexList.length + queryRSAKeyOut.keyNum.length];
            System.arraycopy(queryRSAKeyOut.keyNum, 0, tByte, 0, 4);
            System.arraycopy(queryRSAKeyOut.indexList, 0, tByte, 4, queryRSAKeyOut.indexList.length);
            return tByte;
        }
        
    }
    
    
    /**
     * �ǶԳ�תPIN
     * 
     * @param aKey1Index
     * @param aData1Len
     * @param aData
     * @param aKey2Index
     * @return
     * @throws CUPSException
     */
    public OutputSecResult UnsymConvertPin(	String aRSAIndex, byte[] aDataLen, byte[] aData, 
    										String aBMKIndex, String aCardNO, byte[] PIK, String PIKLen)
    	throws SecException {
    	int nRet = 0;
    	
    	if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");
        
        String tFlag = "02";
        
        if(	aCardNO == null ||aCardNO.equals("") ) 	{            
        	tFlag = "01";
            aCardNO = "0000";
        }
        
        OutputSecResult outRslt = null;
        HsmSession hSession = null;
        HsmStruct hsmStruct = null;
        HsmStruct.UnsymConvertPin unsymCvtPin = null;
        HsmStruct.UnsymConvertPin.UnsymConvertPinIn  unsymCvtPinIn = null;
        HsmStruct.UnsymConvertPin.UnsymConvertPinOut unsymCvtPinOut = null;
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if (nRet != 0) {
//                LogWriter.error(CUPSException.CS_EXC_MSG_8001
//                				+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8002
                        				+ SecException.ERR_SEC_MSG_8002);
            }
            hsmStruct = new HsmStruct();
            unsymCvtPin = hsmStruct.new UnsymConvertPin();
            unsymCvtPinIn = unsymCvtPin.new UnsymConvertPinIn(
            		aRSAIndex.getBytes(), 	// rsa ID
                    "0000".getBytes(), 		// pri key len
                    aDataLen, 				// data len
                    "01".getBytes(), 		// padFlag
                    "1".getBytes(), 		// athFlag
                    sHSMPassword.getBytes(),// password
                    "0".getBytes(), 		// pri key
                    aData, 					// dec data
                    tFlag.getBytes(), 		// flagIn
                    aCardNO.getBytes(), 	// acco in
                    aBMKIndex.getBytes(), 	// bmk ID
                    PIKLen.getBytes(), 		// pik len
                    PIK, 		            // pik
                    "01".getBytes(), 		// flag out
                    "0000".getBytes() 		// acco 2
            );
            
            unsymCvtPinOut = unsymCvtPin.new UnsymConvertPinOut();

            nRet = hApp.UnsymConvertPin(hSession, unsymCvtPinIn, unsymCvtPinOut);           
        }
        catch (Exception e) {
//            LogWriter.error(CUPSException.CS_EXC_MSG_8004 + e.getMessage());
            logger.error(e.getMessage());
            throw new SecException(	SecException.ERR_SEC_CODE_8002
                    				+ SecException.ERR_SEC_MSG_8002);
        }
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        if (nRet != HsmConst.T_SUCCESS) {
//            LogWriter.error(CUPSException.CS_EXC_MSG_8004
//            				+ unsymCvtPinOut.reply_code[0]+unsymCvtPinOut.reply_code[1]);
            throw new SecException(SecException.ERR_SEC_CODE_8002
                    				+ SecException.ERR_SEC_MSG_8002 + ":"
                    				+ new String(unsymCvtPinOut.reply_code));

        }
        else {
            outRslt = new OutputSecResult();
            if(	tFlag.equals("01")) {
                byte[] tByte = CodeUtil.Byte2byte(unsymCvtPinOut.pinblock);
                outRslt.setData(tByte);
                outRslt.setDataLen(unsymCvtPinOut.pinLen);
            }
            else if( tFlag.equals("02")) {
                byte[] tByte = CodeUtil.Byte2byteWithCardNO(unsymCvtPinOut.pinblock);
                outRslt.setData(tByte);
                outRslt.setDataLen(unsymCvtPinOut.pinLen);
            }
            else {
                throw new SecException(	SecException.ERR_SEC_CODE_8003
                        				+ SecException.ERR_SEC_MSG_8003);
            }
        }
        return outRslt;
    }
    
    public OutputSecResult GenerateWorkKey(	String BKMIndex, String BKMLen,	String PIKLen)
    	throws SecException {
    	
    	int nRet;
    	
    	if(	!sInitialed)
            throw new SecException("HsmUtil.initial错误");
    	
    	OutputSecResult outRslt = null;
    	HsmSession hSession = null;
        HsmStruct hsmStruct = null;
        
        HsmStruct.GenerateWorkKey GenerateWorkKey=null;
        HsmStruct.GenerateWorkKey.GenerateWorkKeyIn GenerateWorkKeyIn= null;
        HsmStruct.GenerateWorkKey.GenerateWorkKeyOut GenerateWorkKeyOut= null;
        try{
        	hSession = new HsmSession(sHsmConfigFile);
            HsmApp hApp = new HsmApp();
            nRet = hSession.GetLastError();
            if (nRet != 0) {
//                LogWriter.error(CUPSException.CS_EXC_MSG_8001
//                				+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8002
                        				+ SecException.ERR_SEC_MSG_8002);
            }

            hsmStruct = new HsmStruct();
            GenerateWorkKey    = hsmStruct.new GenerateWorkKey();
            GenerateWorkKeyIn  = GenerateWorkKey.new GenerateWorkKeyIn(
            		            BKMLen.getBytes(),
            					BKMIndex.getBytes());
            GenerateWorkKeyOut = GenerateWorkKey.new GenerateWorkKeyOut();
            
            nRet = hApp.GenerateWorkKey(hSession, GenerateWorkKeyIn, GenerateWorkKeyOut);
        	
        }
        catch (Exception e){
        	 throw new SecException(	SecException.ERR_SEC_CODE_8002
     				+ SecException.ERR_SEC_MSG_8002);
        }
        finally {
            if(	hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        if (nRet != HsmConst.T_SUCCESS) {
//            LogWriter.error(CUPSException.CS_EXC_MSG_8004
//            				+ unsymCvtPinOut.reply_code[0]+unsymCvtPinOut.reply_code[1]);
            throw new SecException(SecException.ERR_SEC_CODE_8002
                    				+ SecException.ERR_SEC_MSG_8002 + ":"
                    				+ new String(GenerateWorkKeyOut.reply_code));

        }
        else {
            outRslt = new OutputSecResult();
            outRslt.setData(GenerateWorkKeyOut.workKey);
            outRslt.setDataLen(GenerateWorkKeyOut.keyLen); 
            outRslt.signData = new byte[GenerateWorkKeyOut.checkvalue.length];
            System.arraycopy(GenerateWorkKeyOut.checkvalue, 0, outRslt.signData, 0, GenerateWorkKeyOut.checkvalue.length);           
        }
        return outRslt;       
    }
    
    public OutputSecResult Bank_GenMac(	String BKMIndex, String MACKeyLen,	String MACKey,String aDataLen, byte [] aData )
		throws SecException {
	
	int nRet;
	
	if(	!sInitialed)
        throw new SecException("HsmUtil.initial错误");
	
	OutputSecResult outRslt = null;
	HsmSession hSession = null;
    HsmStruct hsmStruct = null;
    
    HsmStruct.Bank_GenMac Bank_GenMac=null;
    HsmStruct.Bank_GenMac.Bank_GenMacIN Bank_GenMacIN= null;
    HsmStruct.Bank_GenMac.Bank_GenMacOUT Bank_GenMacOUT= null;
    try{
    	hSession = new HsmSession(sHsmConfigFile);
        HsmApp hApp = new HsmApp();
        nRet = hSession.GetLastError();
        
        if (nRet != 0) {
        	LogWriter.debug("hSession.GetLastError() = ["+nRet+"]");
            throw new SecException(	SecException.ERR_SEC_CODE_9000
                    				+ SecException.ERR_SEC_MSG_9000);
        }

        hsmStruct = new HsmStruct();
        Bank_GenMac        = hsmStruct.new Bank_GenMac();
        Bank_GenMacIN      = Bank_GenMac.new Bank_GenMacIN(
        		           			BKMIndex.getBytes(),
        		           			MACKeyLen.getBytes(),
        		           			MACKey.getBytes(),
        		           			aDataLen.getBytes(),
        		           			aData );
        LogWriter.debug("BKMIndex.getBytes() = ["+new String(BKMIndex.getBytes())+"]");
        LogWriter.debug("MACKeyLen.getBytes() = ["+new String(MACKeyLen.getBytes())+"]");
        LogWriter.debug("MACKey.getBytes() = ["+new String(MACKey.getBytes())+"]");
        LogWriter.debug("aDataLen.getBytes() = ["+new String(aDataLen.getBytes())+"]");
        LogWriter.debug("aData = ["+new String(aData)+"]");
        Bank_GenMacOUT     = Bank_GenMac.new Bank_GenMacOUT();
        
        nRet = hApp.Bank_GenMac(hSession, Bank_GenMacIN, Bank_GenMacOUT);
        LogWriter.debug("hApp.Bank_GenMac = ["+nRet+"]");
    }
    catch (Exception e){
    	logger.error(e.getMessage());
    	 throw new SecException(	SecException.ERR_SEC_CODE_9000
 				+ SecException.ERR_SEC_MSG_9000);
    }
    finally {
        if(	hSession != null) {
            hSession.HsmSessionEnd();
        }
    }
    if (nRet != HsmConst.T_SUCCESS) {
    	LogWriter.debug("throw SecException nRet = ["+nRet+"]");
        throw new SecException(SecException.ERR_SEC_CODE_9000
                				+ SecException.ERR_SEC_MSG_9000
                				+ ":" +new String(Bank_GenMacOUT.reply_code));

    }
    else {
        outRslt = new OutputSecResult();
        outRslt.setData(Bank_GenMacOUT.mac);        
    }
  	return outRslt;       
}
    
    public boolean Bank_VerifyMac(	String BKMIndex, String MACKeyLen,	String MACKey, 
    		byte[] MACData, byte [] aData, String aDataLen)
	throws SecException {
    	
    	boolean check = false;
    	int nRet;

    	if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");

    	HsmSession hSession = null;
    	HsmStruct hsmStruct = null;

    	HsmStruct.Bank_VerifyMac Bank_VerifyMac=null;
    	HsmStruct.Bank_VerifyMac.Bank_VerifyMacIN  Bank_VerifyMacIN= null;
    	HsmStruct.Bank_VerifyMac.Bank_VerifyMacOUT Bank_VerifyMacOUT= null;
    	try{
    		hSession = new HsmSession(sHsmConfigFile);
    		HsmApp hApp = new HsmApp();
    		nRet = hSession.GetLastError();
    		if (nRet != 0) {
    			//        LogWriter.error(CUPSException.CS_EXC_MSG_8001
    			//        				+ hSession.ParseErrCode(nRet));
    			throw new SecException(	SecException.ERR_SEC_CODE_8002
                				+ SecException.ERR_SEC_MSG_8002);
    		}

    		hsmStruct = new HsmStruct();
    		Bank_VerifyMac        = hsmStruct.new Bank_VerifyMac();
    		Bank_VerifyMacIN      = Bank_VerifyMac.new Bank_VerifyMacIN(
    		           			BKMIndex.getBytes(),
    		           			MACKeyLen.getBytes(),
    		           			MACKey.getBytes(),    		           			
    		           			MACData,
    		           			aDataLen.getBytes() ,
    		           			aData	);
    		Bank_VerifyMacOUT     = Bank_VerifyMac.new Bank_VerifyMacOUT();
    
    		nRet = hApp.Bank_VerifyMac(hSession, Bank_VerifyMacIN, Bank_VerifyMacOUT);
	
    	}
    	catch (Exception e){
    		throw new SecException(	SecException.ERR_SEC_CODE_9001
				+ SecException.ERR_SEC_MSG_9001);
    	}
    	finally {
    		if(	hSession != null) {
    			hSession.HsmSessionEnd();
    		}
    	}
    	if (nRet != HsmConst.T_SUCCESS) {
       		LogWriter.debug(SecException.ERR_SEC_CODE_9001
            				+ SecException.ERR_SEC_MSG_9001 + ":"
            				+ new String(Bank_VerifyMacOUT.reply_code));
       		return false;

    	}
    	else {
    		check = true;      
    	}
    	return check;       
    }
    /**
     * ˽Կ�������
     * 
     * @param aPrivate
     * @param aKeyIndex
     * @param aPassWord
     * @throws SecException
     */
    public OutputSecResult RSAPriKeyDecrypt(String RSAKeyIdx, byte[] aData, String aDataLen)
    	throws SecException{
        int nRet;
       
        if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");
        
        OutputSecResult outRslt = null;
    	HsmSession hSession = null;
    	HsmStruct hsmStruct = null;
    	HsmStruct.PrivateKeyEncrypt privateKeyDecrypt=null;
    	HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptIn privateKeyDecryptIn=null;
        HsmStruct.PrivateKeyEncrypt.PrivateKeyEncryptOut privateKeyDecryptOut=null;
    	try{
        	hSession = new HsmSession(sHsmConfigFile);
        	HsmApp hApp = new HsmApp();
        	nRet = hSession.GetLastError();
        	if (nRet != 0) {
        		throw new SecException(	SecException.ERR_SEC_CODE_8002
                   				+ SecException.ERR_SEC_MSG_8002);
        	}
        	hsmStruct = new HsmStruct();
        	privateKeyDecrypt = hsmStruct.new PrivateKeyEncrypt();
        	privateKeyDecryptIn = privateKeyDecrypt.new PrivateKeyEncryptIn(
        							   RSAKeyIdx.getBytes(), 
        	                           "0000".getBytes(), 
        	                           aDataLen.getBytes(), 
        	                           "01".getBytes(),
        	                           "1".getBytes(), 
        	                           sHSMPassword.getBytes(), 
        	                           "0".getBytes(),
        	                           aData);

        	privateKeyDecryptOut = privateKeyDecrypt.new PrivateKeyEncryptOut();

        	nRet = hApp.PrivateKeyDecrypt(hSession, privateKeyDecryptIn, privateKeyDecryptOut);
        	           
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		throw new SecException(	SecException.ERR_SEC_CODE_8002 + 
    							SecException.ERR_SEC_MSG_8002 + ":" +
    							new String(privateKeyDecryptOut.reply_code));
        	}
        finally {
        	if(	hSession != null) {
        		hSession.HsmSessionEnd();
        	}
        }
        if (nRet != HsmConst.T_SUCCESS) {
        	throw new SecException(SecException.ERR_SEC_CODE_8002
    				+ SecException.ERR_SEC_MSG_8002
    				+ ":" +new String(privateKeyDecryptOut.reply_code));           
       }
        else{
        	 outRslt = new OutputSecResult();
        	 outRslt.setData(privateKeyDecryptOut.data);
             outRslt.setDataLen(privateKeyDecryptOut.dataLen); 
        }           
        
        return outRslt;   
       
    }
    /**
     * �ǶԳ�֤�鹫Կ����
     * 
     * @param sPublicKey
     * @param sPublicKeyLen
     * @param aData
     * @param aDataLen
     * @return
     */
    public OutputSecResult PubKeyEncryptWithCert(byte[] sPublicKey, String aPublicKeyLen, 
    		byte[] aData, String aDataLen) throws SecException {
    	
    	int nRet;
        
    	if(	!sInitialed)
            throw new SecException("HsmUtil.initial错误");
        
//        LogWriter.debug("=================sHsmConfigFile================"
//                		+ sHsmConfigFile);
        
        OutputSecResult outRslt = null;
        HsmStruct hsmStruct = null;
        HsmApp hApp 		= null;
        HsmSession hSession = null;
        HsmStruct.PublicKeyEncrypt.PublicKeyEncryptOut publicKeyEncryptOut = null;
        
        try {
            hSession = new HsmSession(sHsmConfigFile);
            
            hApp = new HsmApp();
            
            nRet = hSession.GetLastError();            
            if (nRet != 0) {
//                LogWriter.error(CUPSException.CS_EXC_MSG_8001
//                        		+ hSession.ParseErrCode(nRet));
                throw new SecException(	SecException.ERR_SEC_CODE_8000
                        				+ SecException.ERR_SEC_MSG_8000);
            }

            hsmStruct = new HsmStruct();
            HsmStruct.PublicKeyEncrypt publicKeyEncrypt = hsmStruct.new PublicKeyEncrypt();
            
            HsmStruct.PublicKeyEncrypt.PublicKeyEncryptIn publicKeyEncryptIn = publicKeyEncrypt.new PublicKeyEncryptIn(
                    "FFF".getBytes(), 
                    aPublicKeyLen.getBytes(), 
                    aDataLen.getBytes(),
                    "01".getBytes(), 			// ��䷽ʽ
                    "1".getBytes(), 			// �㷨��־
                    sHSMPassword.getBytes(), 	// ������ʿ���
                    sPublicKey, 			    // ��Կ���
                    aData);
            
            publicKeyEncryptOut = publicKeyEncrypt.new PublicKeyEncryptOut();
            
            nRet = hApp.PublicKeyEncrypt(	hSession, 
            								publicKeyEncryptIn,
            								publicKeyEncryptOut);
            
        }
        catch (Exception e) {
//            LogWriter.error(CUPSException.CS_EXC_MSG_8002 + e.getMessage());
        	logger.error(e.getMessage());
        	throw new SecException(	SecException.ERR_SEC_CODE_8005
                    				+ SecException.ERR_SEC_MSG_8005);
        }
        finally {
            if (hSession != null) {
                hSession.HsmSessionEnd();
            }
        }
        
        if (nRet != HsmConst.T_SUCCESS) {
//            LogWriter.error(CUPSException.CS_EXC_MSG_8002
//                    		+ publicKeyEncryptOut.reply_code[1]
//                    		+ publicKeyEncryptOut.reply_code[1]);
            throw new SecException(	SecException.ERR_SEC_CODE_8005
                    				+ SecException.ERR_SEC_MSG_8005
                    				+ publicKeyEncryptOut.reply_code[0]
                    				+ publicKeyEncryptOut.reply_code[1]);
        }
        else {
            outRslt = new OutputSecResult();
            
            int tLen = CodeUtil.formatDataLen(publicKeyEncryptOut.dataLen);
            byte[] tByte = new byte[tLen];
            System.arraycopy(publicKeyEncryptOut.data, 0, tByte, 0, tLen);
            
            outRslt.setData(tByte);            
            outRslt.setDataLen(publicKeyEncryptOut.dataLen);
        }

        return outRslt;

    }

    /**
     * ˽Կ�������
     * 
     * @param aPrivate
     * @param aKeyIndex
     * @param aPassWord
     * @throws SecException
     */
    public static OutputSecResult EncDec_Data(String BMKKeyIdx, String WorkKey, String WorkKeyLen,
    		 byte[] aData, String aDataLen)
    	throws SecException{
        int nRet;
       
        if(	!sInitialed)
    		throw new SecException("HsmUtil.initial错误");
        
        OutputSecResult outRslt = null;
    	HsmSession hSession = null;
    	HsmStruct hsmStruct = null;
    	HsmStruct.EncDec_Data EncDec_Data=null;
    	HsmStruct.EncDec_Data.EncDec_DataIN   EncDec_DataIN=null;
    	HsmStruct.EncDec_Data.EncDec_DataOUT  EncDec_DataOUT=null;
    	try{
        	hSession = new HsmSession(sHsmConfigFile);
        	HsmApp hApp = new HsmApp();
        	nRet = hSession.GetLastError();
        	if (nRet != 0) {
        		throw new SecException(	SecException.ERR_SEC_CODE_8002
                   				+ SecException.ERR_SEC_MSG_8002);
        	}
        	hsmStruct = new HsmStruct();
        	EncDec_Data = hsmStruct.new EncDec_Data();
        	EncDec_DataIN = EncDec_Data.new EncDec_DataIN(
        			                   BMKKeyIdx.getBytes(), 
        	                           "16".getBytes(), 
        	                           WorkKey.getBytes(), 
        	                           "1".getBytes(),
        	                           aDataLen.getBytes(),
        	                           aData);

        	EncDec_DataOUT = EncDec_Data.new EncDec_DataOUT();

        	nRet = hApp.EncDec_Data(hSession, EncDec_DataIN, EncDec_DataOUT);
        	           
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		throw new SecException(	SecException.ERR_SEC_CODE_8002 + 
    							SecException.ERR_SEC_MSG_8002 + ":" +
    							new String(EncDec_DataOUT.reply_code));
        	}
        finally {
        	if(	hSession != null) {
        		hSession.HsmSessionEnd();
        	}
        }
        if (nRet != HsmConst.T_SUCCESS) {
        	throw new SecException(SecException.ERR_SEC_CODE_8002
    				+ SecException.ERR_SEC_MSG_8002
    				+ ":" +new String(EncDec_DataOUT.reply_code));           
       }
        else{
        	 outRslt = new OutputSecResult();
        	 outRslt.setData(EncDec_DataOUT.data);
             outRslt.setDataLen(EncDec_DataOUT.data_len); 
        }           
        
        return outRslt;   
       
    }
/*    *//**
     * main���������ڵ�Ԫ����
     *//*
    public static void main(String args[]) {

    	
    }*/

}
