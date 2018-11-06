package com.allinfinance.prepay.encryption;

public interface EncryptionService
{
	boolean validateArqc(Object txnData, String mdk) throws Exception;
	
	String validateArqcAndGenerateArpc(Object txnData, String mdk) throws Exception;
	
	String generateArpc(Object txnData, String mdk) throws Exception;
	
	String calculateMac(Object txnData, String data, String mdk) throws Exception;
	
	boolean verifyTc(Object txnData, String mdk) throws Exception;
	
	/**
	 * �ù�Կ���ܵ�pin����pinoffset
	 * @param a ��Կ���ܵ�pin����
	 * @param b	 ˽Կ
	 * @param c zpk
	 * @param d �˺ź�12λ������У��λ��
	 * @return  pinoffset
	 * @throws Exception
	 */
	String pk2zpk(String a, String b, String c, String d) throws Exception;
	
	/**
	 * ��TPK���ܵ�pin����pinoffset
	 * @param a TPK���ܵ�pin����
	 * @param b zpk
	 * @param c	 tpk
	 * @param d �˺ź�12λ������У��λ��
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
	 * @param a ��Կ���� 0-�㹫Կ  1-DER����
	 * @param b ��Կ
	 * @param c ��Կָ��
	 * @param d DES��Կ���� 00-ZMK 01-ZPK 02-PVK/TPK/TMK 03-TAK 04-ZAK 05-ZEK
	 * @param e DES��Կ���� 0-������ 1-˫���� 2-������
	 * @param f DES��Կ
	 * @param g DES��ԿУ��ֵ
	 * @return	 ��Կ���ܵ�DES��Կ
	 * @throws Exception
	 */
	String desKeyLMK2PK(String a, String b, String c, String d, String e, String f, String g) throws Exception;
	
	String skDecryption(String a, String b) throws Exception;
	
	String[] termKeyZmk2Lmk(String a, String b, String c) throws Exception;

	String generateLmkPinoffset(String pinData, String pvk, String acctNo)
			throws Exception;
	
}
	
	
	
	
	
	
	
	
	
	
	
