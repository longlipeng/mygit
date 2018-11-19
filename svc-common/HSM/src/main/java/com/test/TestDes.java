package com.test;


public class TestDes {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		
		try{
			byte [] BMK = new byte[8];
			CodeUtil.Str2Hex("FEDCBA0987654321".getBytes(), BMK, 16);
			SecretKey key = new SecretKeySpec(BMK, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			//Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			byte [] temp = new byte[8];
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			CodeUtil.Str2Hex("0000000000000000".getBytes(), temp, 16);
			byte[] cc = cipher.doFinal(temp);
			
			String EncMsg = "4718BBCB715F00B4";
			CodeUtil.Str2Hex(EncMsg.getBytes(), temp,16);
			System.arraycopy(temp, 0, cc, 0, 8);
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			cc = cipher.doFinal(cc);
			byte[] MACKey = new byte [16];
			CodeUtil.Hex2Str(cc, MACKey, 8);
			//logger.info("��Կ���ģ� "+EncMsg);
			//logger.info("��Կ���ģ� "+new String(MACKey));
			
			CodeUtil.Str2Hex("0000000000000000".getBytes(), BMK, 16);
			key = new SecretKeySpec(cc, "DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] check=cipher.doFinal(BMK);
			CodeUtil.Hex2Str(check, MACKey, 8);
			logger.info(new String(MACKey));
			
			Mac mac = Mac.getInstance("HmacMD5");
			key = new SecretKeySpec(cc, "DES");
			mac.init(key);
			String aData = "12345678 12345678";
			byte[] macbyte = mac.doFinal(aData.getBytes());
			
			CodeUtil.Hex2Str(macbyte, MACKey, 8);
			logger.info("MAC = " + new String(MACKey));
		}
		catch (Exception ex){
			logger.error(ex.getMessage());	
		}

	}*/

}
