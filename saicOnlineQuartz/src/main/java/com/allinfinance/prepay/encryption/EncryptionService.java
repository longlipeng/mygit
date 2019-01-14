package com.allinfinance.prepay.encryption;

public interface EncryptionService
{
	boolean validateArqc(Object txnData, String mdk) throws Exception;
	
	String validateArqcAndGenerateArpc(Object txnData, String mdk) throws Exception;
	
	String generateArpc(Object txnData, String mdk) throws Exception;
	
	String calculateMac(Object txnData, String data, String mdk) throws Exception;
	
	boolean verifyTc(Object txnData, String mdk) throws Exception;
	
	/**
	 * 用公钥加密的pin生成pinoffset
	 * @param a 公钥加密的pin密文
	 * @param b	 私钥
	 * @param c zpk
	 * @param d 账号后12位（不算校验位）
	 * @return  pinoffset
	 * @throws Exception
	 */
	String pk2zpk(String a, String b, String c, String d) throws Exception;
	
	/**
	 * 用TPK加密的pin生成pinoffset
	 * @param a TPK加密的pin密文
	 * @param b zpk
	 * @param c	 tpk
	 * @param d 账号后12位（不算校验位）
	 * @return pinoffset
	 * @throws Exception
	 */
	String tpk2zpk(String a, String b, String c, String d) throws Exception;
	
	String genMac(String a, String b, String c, String d) throws Exception;
	
	String encdec(String a, String b, String c, String d, String e) throws Exception;
	
	String convertEncryption(String a, String b, String c) throws Exception;
	
	String pkEncrypt(String a, String b) throws Exception;
	
	String generateCVV(String a, String b, String c, String d) throws Exception;
	
	String generatePinoffset(String a, String b, String c, String d) throws Exception;
	
	String[] generateTerminalKey(String a, String b, String c, String d) throws Exception;
	
	String genMac4pos(String a, String b) throws Exception;
	
	String[] genRSAKeyPair(String a, String b) throws Exception;
	
	
	/**
	 * @param a 公钥类型 0-裸公钥  1-DER编码
	 * @param b 公钥
	 * @param c 公钥指数
	 * @param d DES密钥类型 00-ZMK 01-ZPK 02-PVK/TPK/TMK 03-TAK 04-ZAK 05-ZEK
	 * @param e DES密钥长度 0-单倍长 1-双倍长 2-三倍长
	 * @param f DES密钥
	 * @param g DES密钥校验值
	 * @return	 公钥加密的DES密钥
	 * @throws Exception
	 */
	String desKeyLMK2PK(String a, String b, String c, String d, String e, String f, String g) throws Exception;
	
	String skDecryption(String a, String b) throws Exception;
	
	String[] termKeyZmk2Lmk(String a, String b, String c) throws Exception;

	String generateLmkPinoffset(String pinData, String pvk, String acctNo)
			throws Exception;
	
}
	
	
	
	
	
	
	
	
	
	
	
