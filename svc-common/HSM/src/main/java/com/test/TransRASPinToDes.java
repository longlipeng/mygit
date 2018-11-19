package com.test;


public class TransRASPinToDes {

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
			
			File fis=null;
			fis=new File("C://cert//pinRSA1024");
			FileReader fr = new FileReader(fis);
			BufferedReader br = new BufferedReader(fr);
			String line1 = br.readLine();			
			fr.close();
			
			OutputSecResult out = null;
			HsmUtil hsm = new HsmUtil();
			//out = hsm.GenerateWorkKey("040", "08", "08");
			String PIK="4710B3368995C8A9";
			
			byte [] tSecData    = Base64.decode(line1); 
	    	String tSecDataLen = CodeUtil.formatLength(tSecData.length, 4);
	    	
	    	out = hsm.RSAPriKeyDecrypt("010", tSecData, tSecDataLen);
	    	byte [] pinblock = CodeUtil.pin2PinBlockWithCardNO("111111", "6221881882070071517");
	    	
	    	byte [] pinblock2 = new byte[8];
	    	System.arraycopy(out.dataByte, 0, pinblock2, 0, out.dataLenInt);
	    	if (new String(pinblock).equals(new String(pinblock))){
	    		logger.info("pinblock is equals");
	    	}
	    	
	    	byte [] pin = CodeUtil.PinBlock2PinWithCardNO(pinblock, "6221881882070071517");
	    	
	    	
	    	byte [] BMK = new byte[8];
			CodeUtil.Str2Hex("E93268971AC7E5B3".getBytes(), BMK, 16);
			SecretKey key = new SecretKeySpec(BMK, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] des = cipher.doFinal(pin);
			byte[] pindes = new byte[16];
			CodeUtil.Hex2Str(des, pindes, 8);
			logger.info("soft pindes = " + new String(pindes));
	    	//String cc = SecurityUtil.RSAEncByPub_Soft(aPublicKey, "111111");
	    	
			String acct="6221881882070071517";
			// out = hsm.UnsymConvertPin("010",
			// tSecDataLen.getBytes(), tSecData, "040", acct,
			// PIK.getBytes(), "08");
			// String aData = new String(tSecData);
			// out = SecurityUtil.pppAssymEncrypt(aData, tSecData.length, 10, 40, acct, PIK, 8);
         	out = SecurityUtil.pppAssymEncrypt(tSecData, tSecData.length, 10, 40, acct, PIK, 8);
			CodeUtil.Hex2Str(out.dataByte, pindes, 8);
			logger.info("hard pindes = " + new String(pindes));
			
			
		}catch (Exception ex){
			logger.error(ex.getMessage());	
		}
			
	}*/

}
