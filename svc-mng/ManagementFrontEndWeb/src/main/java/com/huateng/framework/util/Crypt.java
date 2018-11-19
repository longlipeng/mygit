package com.huateng.framework.util;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.Config;
import com.huateng.framework.util.StringUtils;

/**
 * <p>Title: Accor</p>
 *
 * <p>Description:Accor Project 1nd Edition </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author YY
 * @version 1.0
 */

public class Crypt {
	static Logger logger = Logger.getLogger(Crypt.class);
    static String DES = "DES/ECB/NoPadding";   
    static String TriDes = "DESede/ECB/NoPadding";   
	static String tmKey;
	static String wkKeyC;
	static String wkKey;
	static byte[] pinKey;
	static byte[] macKey;
	
	/**
	 * ��ȡpinKey
	 * @return
	 * @throws BizServiceException
	 */
	public static byte[] getPinKey() throws BizServiceException{
		if (pinKey == null || pinKey.length == 0){
			loadKey();
		}
		
		return pinKey;
	}
	
	/**
	 * ��ȡmacKey
	 * @return
	 * @throws BizServiceException
	 */
	public static byte[] getMacKey() throws BizServiceException{
		if (macKey == null || macKey.length == 0){
			loadKey();
		}
		
		return macKey;
	}
	
	/**
	 * ��ȡ��Կ�ļ��������pinKey��macKey
	 * @throws BizServiceException
	 */
	private static void loadKeyC() throws BizServiceException{
		//�������ļ������ȡtmKey(����)��wkKeyC(����)
		String tmKeyTmp = Config.getTmKey();
		String wkKeyCTmp = Config.getWkKeyC();
		byte[] btTmKey = StringUtils.toByteArray(tmKeyTmp);
		byte[] btWkKey = StringUtils.toByteArray(wkKeyCTmp);
		tmKey = new String(StringUtils.toByteArray(tmKeyTmp));
		wkKeyC = new String(StringUtils.toByteArray(wkKeyCTmp));
		logger.debug("tmKey=" + tmKey);
		logger.debug("wkKeyC=" + wkKeyC);
		
		//tmKey������16���ֽ�
		if (tmKeyTmp == null || tmKeyTmp.trim().length() != 32){
			String err = "tmKeyTmp length must be 32 chars";
			logger.error(err);
			throw new BizServiceException(err);
		}
		
		//wkKeyC������40���ֽ�
		if (wkKeyCTmp == null || wkKeyCTmp.trim().length() != 80){
			String err = "tmKey length must be 80 chars";
			logger.error(err);
			throw new BizServiceException(err);
		}
		
		tmKey = tmKey + tmKey.substring(0, 8);
		
		//��3DES����wkKeyC��ǰ16���ֽڣ���Կ��tmKey�����ܳ�4�Ľ�����pinKey
		byte[] btPinKeyC = new byte[16];
		System.arraycopy(btWkKey, 0, btPinKeyC, 0, 16);
		byte[] btPinKey = trides_decrypt(tmKey.getBytes(), btPinKeyC);
		logger.debug("pinKey=" + new String(btPinKey));
		
		//У��pinKey
		byte[] btCheckValuePin = new byte[4];
		System.arraycopy(btWkKey, 16, btCheckValuePin, 0, 4);
		if (!checkKey(btPinKey, btCheckValuePin)){
			String err = "check pinKey error!";
			logger.error(err);
			throw new BizServiceException(err);
		}
		
		//��3DES����wkKeyC�ĵ�20-36���ֽڣ���Կ��tmKey�����ܳ�4�Ľ�����macKey
		byte[] btMacKeyC = new byte[16];
		System.arraycopy(btWkKey, 20, btMacKeyC, 0, 16);
		byte[] btMacKey = trides_decrypt(tmKey.getBytes(), btMacKeyC);
		logger.debug("macKey=" + macKey);
		
		//У��macKey
		byte[] btCheckValueMac = new byte[4];
		System.arraycopy(btWkKey, 36, btCheckValueMac, 0, 4);
		if (!checkKey(btMacKey, btCheckValueMac)){
			String err = "check macKey error!";
			logger.error(err);
			throw new BizServiceException(err);
		}
		
		logger.debug("load keys");
		System.arraycopy(btPinKey, 0, pinKey, 0, 16);
		System.arraycopy(btMacKey, 0, macKey, 0, 16);
		return;
	}
	
	/**
	 * ֱ�Ӷ�ȡpinKey��macKey����
	 * @throws BizServiceException
	 */
	private static void loadKey() throws BizServiceException{
		//�������ļ������ȡpinKey(����)��macKey(����)
		pinKey = new byte[16];
		macKey = new byte[16];
		String pinKeyTmp = Config.getPinKey();
		String macKeyTmp = Config.getMacKey();
		logger.debug("loadKeys");
		logger.debug("pinKey=" + pinKeyTmp);
		logger.debug("macKey=" + macKeyTmp);
		pinKey = StringUtils.toByteArray(pinKeyTmp);
		macKey = StringUtils.toByteArray(macKeyTmp);

		return;
	}
	
	/**
	 * ��3DES�㷨��8�ֽڳ��ȵ�00���ܣ��ü��ܽ���ǰ4���ֽں�checkValue�Ƚ�
	 * @param keyC
	 * @param checkValue
	 * @return
	 */
/*	private static boolean checkKey(String key, String checkValue){
		logger.debug("check value, key=[" + key + "], checkValue=[" + checkValue + "]");
		boolean rst = false;
		char chOrig = 0x00;
		String sOrig = "";
		for (int i = 0; i < 8; i++){
			sOrig += chOrig;
		}
		try{
			String tmp = new String(trides_crypt(key.getBytes(), sOrig.getBytes()));
			
			if (tmp == null || tmp.trim().length() == 0){
				String err = "encrypt 00000000 error! key=[" + key + "]";
				logger.error(err);
				throw new BizServiceException(err);
			}
			logger.debug("checkval" + tmp);
			if (tmp.substring(0, 4).equals(checkValue)){
				rst = true;
			}
		}catch(Exception ex){
			String err = "checkKey exception. " + ex.getMessage();
		}
		return rst;
	}*/

	/**
	 * ��3DES�㷨��8�ֽڳ��ȵ�00���ܣ��ü��ܽ���ǰ4���ֽں�checkValue�Ƚ�
	 * @param keyC
	 * @param checkValue
	 * @return
	 */
	private static boolean checkKey(byte[] key, byte[] checkValue){
		//logger.debug("check value, key=[" + key + "], checkValue=[" + checkValue + "]");
		boolean rst = true;
		char chOrig = 0x00;
		String sOrig = "";
		for (int i = 0; i < 8; i++){
			sOrig += chOrig;
		}
		try{
			byte[] tmp = trides_crypt(key, sOrig.getBytes());
			
			if (tmp == null || tmp.length == 0){
				String err = "encrypt 00000000 error!";
				logger.error(err);
				throw new BizServiceException(err);
			}
			
/*			if (tmp.length != checkValue.length){
				rst = false;
				logger.debug("tmp.length=" + tmp.length + "  checkvalue.length=" + checkValue.length);
			}
			*/
			logger.debug("checkval" + tmp);
			for (int i = 0; i < checkValue.length; i++){
				logger.debug(tmp[i] + " " + checkValue[i]);
				if (tmp[i] != checkValue[i]){
					rst = false;
				}
			}
			
		}catch(Exception ex){
			String err = "checkKey exception. " + ex.getMessage();
		}
		return rst;
	}
	
	/**
	 * 3DES����
	 * @param text ��������(����)
	 * @param key ������Կ(����)
	 * @return ���ܽ��
	 */
	public static String encrypt(String text, String key){
		try{
			byte[] btRst;
			byte[] btText = text.getBytes();
			String algorithm = "DESede";
			//String pinKey = Config.getPinKey();
			byte[] keybyte = key.getBytes();
			SecretKey deskey = new SecretKeySpec(keybyte, algorithm);
			
			Cipher c1 = Cipher.getInstance(algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			btRst = c1.doFinal(btText);
			String rst = StringUtils.bytesToHexString(btRst);
			logger.debug("encrypt result:"+rst);
			
			return rst;
			
		}catch(Exception ex){
			logger.error("encrypt exception." + ex.toString());
			return null;
		}
	}
	
	/**
	 * 3DES����
	 * @param text ��������(����)
	 * @param key ������Կ(����)
	 * @return ���ܽ��
	 */
	public static String decrypt(String text, String key){
		try{
			byte[] btRst;
			byte[] btText = text.getBytes();
			String algorithm = "DESede";
			//String pinKey = Config.getPinKey();
			byte[] keybyte = key.getBytes();
			SecretKey deskey = new SecretKeySpec(keybyte, algorithm);
			
			Cipher c2 = Cipher.getInstance("DESede");
			c2.init(Cipher.DECRYPT_MODE, deskey);
			byte[] rstbyte = StringUtils.hexString2Bytes(text);
			byte[] rstDes = c2.doFinal(rstbyte);

			String rst = new String(rstDes);
			logger.debug("decrypt result:" + rst);
			return rst;
			
		}catch(Exception ex){
			logger.error("decrypt exception." + ex.toString());
			return null;
		}
	}

/*	public static void main(String[] args) {
		String key = "2c073140e9644c577025317fe0dcabb9";
		String text = "12345678";
		try {
			calMAC(text.getBytes(), key.getBytes());
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		
	}*/
	
	/**
	 * ����mac
	 * @param src �������
	 * @param key ������Կ
	 * @return mac
	 */
    public static byte[] calMAC(byte[] btText, byte[] btKey) throws Exception{

    	//Log.printBytes(btKey, "macKey");
    	//Log.printBytes(btText, "8583");
    	byte[] btResult = new byte[8];
    	String rst = "";
    	byte[] tmpKey8 = new byte[8];
    	byte b00 = 0x00;
    	int len = btText.length - btText.length % 8;
    	byte[] btTextF = new byte[btText.length + len];
    	byte[] tmpResult = new byte[8];
    	try{
	    	System.arraycopy(btText, 0, btTextF, 0, btText.length);
	    	
	    	//����MAC ELEMEMENT BLOCK(MAB)�����ԭ�ĳ���(�ֽ���)����8����������� 0x00
	    	for (int i = btText.length; i < btTextF.length; i++){
	    		btTextF[i] = b00;
	    	}
	    	//Log.printBytes(btText, "");
	    	//��MAB������ÿ8���ֽ������
	    	int mLen = btTextF.length / 8;
	    	byte[] xorResult = new byte[8];
	    	int pos = 0;
	    	System.arraycopy(btTextF, pos, xorResult, 0, 8);
	    	pos = 8;
	    	for (int i = 1; i < mLen; i++){
	    		byte[] tmp = new byte[8];
	    		System.arraycopy(btTextF, pos*i, tmp, 0, 8);
	    		xorResult = bytesXOR(xorResult, tmp);
	    	}
	
	    	//������Ľ��ת����16��HEXDECIMAL
	    	String str_1 = StringUtils.toHexString(xorResult);
	    	logger.debug("str_1=" + str_1);

	    	byte[] rst_1 = str_1.substring(0, 8).toUpperCase().getBytes();
	    	byte[] rst_2 = str_1.substring(8).toUpperCase().getBytes();
	    	
	    	//ȡǰ8���ֽ���macKey����
	    	System.arraycopy(btKey, 0, tmpKey8, 0, 8);
	    	byte[] des_1 = des(rst_1, tmpKey8);

	    	//���ܺ�Ľ�����8���ֽ����
	    	byte[] xor_1 = bytesXOR(des_1, rst_2);

	    	//����Ľ���ٽ���һ��DES����
	    	byte[] des_2 = des(xor_1, tmpKey8);

	    	//�����ܽ��ת����16��HEXDECIMAL
	    	String str_2 = StringUtils.toHexString(des_2).toUpperCase();
	    	logger.debug("str_2=" + str_2);
	    	logger.info("str_2=" + str_2);
	    	
	    	//ȡǰ8���ֽ���ΪMAC
	    	tmpResult = str_2.getBytes();
	    	System.arraycopy(tmpResult, 0, btResult, 0, 8);
    	}catch(Exception ex){
    		logger.error(ex.toString());
    		throw new BizServiceException("make mac exception" + ex.getMessage());
    	}
    	
    	return btResult;
    }
    
	/**
	 * ����mac
	 * @param src �������
	 * @param key ������Կ
	 * @return mac
	 */
    public static String calMAC(String src, String key) throws Exception{
    	String rst = "";
    	try{
    		byte[] btResult = calMAC(src.getBytes(), key.getBytes());
    		rst = new String(btResult);
    	}catch(Exception ex){
    		throw ex;
    	}
    	return rst;
    }
    
    /**
     * �������
     * @param a
     * @param b
     * @return �����
     */
    private static byte[] bytesXOR(byte[] a, byte[] b){

/*    	for (int i = 0; i < a.length; i++){
    		logger.info + " ");
    	}
    	logger.info("a[length=" + a.length + "]");
    	logger.info("b:");
    	for (int i = 0; i < b.length; i++){
    		logger.info(b[i] + " ");
    	}*/
		//ByteBuffer bf = ByteBuffer.allocate(a.length);
    	byte[] c = new byte[a.length];
		for (int i = 0; i < a.length; i++){
			c[i] = (byte)(a[i] ^ b[i]);
/*			bf.position(i);
			bf.put(tmp);*/
		}
		//byte[] c = bf.array();
/*    	logger.info();
    	logger.info("c:");
    	for (int i = 0; i < c.length; i++){
    		logger.info(b[i] + " ");
}*/
		return c;
    }
    
	/**
	 * DES����
	 * @param text ��������(����)
	 * @param key ������Կ(����)
	 * @return ���ܽ��
	 */
    public static byte[] des(byte[] text, byte[] key){
    	try{
    		byte[] btRst;
    		byte[] btText = text;
    		String algorithm = "DES/ECB/NoPadding";
    		//String pinKey = Config.getPinKey();
    		byte[] keybyte = key;
    		SecretKey deskey = new SecretKeySpec(keybyte, "DES");
		
    		Cipher c1 = Cipher.getInstance(algorithm);
    		c1.init(Cipher.ENCRYPT_MODE, deskey);
    		btRst = c1.doFinal(btText);
    		return btRst;
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
			return null;
		}
    }
	
    public static byte[] des_crypt(byte key[], byte data[]) {   
    	  
        try {   
            KeySpec ks = new DESKeySpec(key);   
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");   
            SecretKey ky = kf.generateSecret(ks);   
  
            Cipher c = Cipher.getInstance(DES);   
            c.init(Cipher.ENCRYPT_MODE, ky);   
            return c.doFinal(data);   
        } catch (Exception e) {   
            logger.error(e.getMessage());   
            return null;   
        }   
    }   
  
    public static byte[] des_decrypt(byte key[], byte data[]) {   
  
        try {   
            KeySpec ks = new DESKeySpec(key);   
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");   
            SecretKey ky = kf.generateSecret(ks);   
  
            Cipher c = Cipher.getInstance(DES);   
            c.init(Cipher.DECRYPT_MODE, ky);   
            return c.doFinal(data);   
        } catch (Exception e) {   
            logger.error(e.getMessage());   
            return null;   
        }   
    }   
  
    public static byte[] trides_crypt(byte key[], byte data[]) {   
        try {   
            byte[] k = new byte[24];   
  
            int len = data.length;   
            if(data.length % 8 != 0){   
                len = data.length - data.length % 8 + 8;   
            }   
            byte [] needData = null;   
            if(len != 0)   
                needData = new byte[len];   
               
            for(int i = 0 ; i< len ; i++){   
                needData[i] = 0x00;   
            }   
               
            System.arraycopy(data, 0, needData, 0, data.length);   
               
            if (key.length == 16) {   
                System.arraycopy(key, 0, k, 0, key.length);   
                System.arraycopy(key, 0, k, 16, 8);   
            } else {   
                System.arraycopy(key, 0, k, 0, 24);   
            }   
  
            KeySpec ks = new DESedeKeySpec(k);   
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");   
            SecretKey ky = kf.generateSecret(ks);   
  
            Cipher c = Cipher.getInstance(TriDes);   
            c.init(Cipher.ENCRYPT_MODE, ky);   
            return c.doFinal(needData);   
        } catch (Exception e) {   
            logger.error(e.getMessage());   
            return null;   
        }   
  
    }   
  
    public static byte[] trides_decrypt(byte key[], byte data[]) {   
        try {   
            byte[] k = new byte[24];   
  
            int len = data.length;   
            if(data.length % 8 != 0){   
                len = data.length - data.length % 8 + 8;   
            }   
            byte [] needData = null;   
            if(len != 0)   
                needData = new byte[len];   
               
            for(int i = 0 ; i< len ; i++){   
                needData[i] = 0x00;   
            }   
               
            System.arraycopy(data, 0, needData, 0, data.length);   
               
            if (key.length == 16) {   
                System.arraycopy(key, 0, k, 0, key.length);   
                System.arraycopy(key, 0, k, 16, 8);   
            } else {   
                System.arraycopy(key, 0, k, 0, 24);   
            }   
            KeySpec ks = new DESedeKeySpec(k);   
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");   
            SecretKey ky = kf.generateSecret(ks);   
  
            Cipher c = Cipher.getInstance(TriDes);   
            c.init(Cipher.DECRYPT_MODE, ky);   
            return c.doFinal(needData);   
        } catch (Exception e) {   
            logger.error(e.getMessage());   
            return null;   
        }   
  
    }
    
    /**
     * 卡号倒数第二位开始往前取12位，前补四个0组成8个字节字符串
     * 密码前补06，后补八个F，组成8个字节字符串
     * 两个字符串异或
     * 用pinKey对异或的结果进行3DES加密
     * 加密的结果就是密码密文
     * 
     * @param cardNo
     * @param pins
     * @return
     */
    public static String cryptCardPin(String cardNo, String pin) throws BizServiceException{
    	String result = "";
    	try{
    		//判断卡号长度不能少于12位, 卡密长度不能少于6位
    		if (cardNo == null || cardNo.trim().length() < 12 ||
    			pin == null || pin.trim().length() < 6){
    			String err = "卡片长度/密码长度不正确";
    			logger.error(err);
    			throw new BizServiceException(err);
    		}

    		//取卡号倒数第二位开始往前数12位，前补0000
    		String str_card = "0000" + cardNo.substring(cardNo.length() - 13, cardNo.length() - 1);
    		//转换成字节数组
    		byte[] bts_card = StringUtils.hexString2Bytes(str_card);
    		logger.debug("bts_card=" + StringUtils.toHexString(bts_card));
    		
    		//密码前补06，后补FFFFFFFF
    		String str_pin = "06" + pin + "FFFFFFFF";
    		//转换成字节数组
    		byte[] bts_pin = StringUtils.hexString2Bytes(str_pin);
    		logger.debug("bts_pin=" + StringUtils.toHexString(bts_pin));
    		
    		//异或
    		byte[] xor_1 = bytesXOR(bts_card, bts_pin);
    		logger.debug("xor_1=" + StringUtils.toHexString(xor_1));
    		
    		//用pinKey对异或结果进行3DES加密 getPinKey
    		byte[] bts_result = trides_crypt(getPinKey(), xor_1);
    		logger.debug("bts_result=" + StringUtils.toHexString(bts_result));

    		String tmp_result = StringUtils.bytesToHexString(bts_result);
    		char[] tmp_chars = new char[tmp_result.length() / 2];
    		for (int i = 0; i < tmp_result.length() / 2; i++){
    			int tmp_int = Integer.parseInt(tmp_result.substring(i*2, i*2 + 2), 16);
    			tmp_chars[i] = (char)tmp_int;
    		}
    		result = String.valueOf(tmp_chars);
    		logger.info(result);
    		
    		byte[] tmp = result.getBytes();
    		char[] tmp_a = result.toCharArray();
    		//Log.printChars(tmp_a, "aaa");
    		
    	}
    	catch(BizServiceException e){
    		throw e;
    	}
    	catch(Exception e){
    		logger.error(e.getMessage());
    		throw new BizServiceException("密码转换异常!");
    		
    	}
    	
    	return result;
    }

}
