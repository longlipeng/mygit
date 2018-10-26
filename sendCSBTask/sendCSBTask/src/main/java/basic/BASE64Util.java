package basic;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class BASE64Util {
	/**
	 * 生成公私钥对
	 * @return
	 * @throws Exception
	 */
	public static KeyPair pkAndPub() throws Exception{
		KeyPairGenerator gen=KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024, new SecureRandom());
		KeyPair pair=gen.generateKeyPair();
		PrivateKey pk=pair.getPrivate();
		PublicKey pub=pair.getPublic();
        KeyPair kp=new KeyPair(pub, pk);
        return kp;
	}
	
	/**
	 * 报文加密
	 * @param aesKey
	 * @param plainData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String encryptAES(String aesKey,String plainData) throws Exception{
		BASE64Encoder encoder=new BASE64Encoder();
		KeyGenerator aesGen = KeyGenerator.getInstance("AES");
		SecureRandom secureRadmon= new SecureRandom().getInstance("SHA1PRNG");
		secureRadmon.setSeed(aesKey.getBytes());
		aesGen.init(128,secureRadmon);
		SecretKey secretKey =aesGen.generateKey();
		Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);
		byte[] aesData =aesCipher.doFinal(plainData.getBytes());
		String lineSeparator = System.getProperty("line.separator"); 
		String aesBase64 =encoder.encode(aesData).replaceAll("[\r\n]","");
		//String aesBase64 =encoder.encode(aesData).replaceAll("["+lineSeparator+"]","");
		return aesBase64;
	}
	
	/**
	 * 报文解密
	 * @param aesBase64
	 * @param aesKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(String aesBase64,String aesKey) throws Exception{
		BASE64Decoder decoder=new BASE64Decoder();
		byte[] aesData =decoder.decodeBuffer(aesBase64);
		KeyGenerator aesGen = KeyGenerator.getInstance("AES");
		aesGen.init(128, new SecureRandom(aesKey.getBytes()));
		SecretKey secretKey = aesGen.generateKey();
		Cipher aesCipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
		aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plain =aesCipher.doFinal(aesData);
		String  decrypt=new String(plain,Charset.forName("utf-8"));
		return decrypt;
	}
	

	/**	
	 * 生成随机密钥
	 * @return
	 */
	public static String getRandomkey(){
		SecureRandom  symkey =  new SecureRandom();
		long sym_l=symkey.nextLong();
		return ""+sym_l;
	}

	/**
	 * 密钥加密
	 * @param aesKey
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public static  String encryptRSA(String aesKey,PrivateKey pk) throws Exception{
		BASE64Encoder encoder=new BASE64Encoder();
		Cipher cipher =Cipher.getInstance("RSA/ECB/PKCs1padding");
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		cipher.update(aesKey.getBytes());
		byte[] encryptData =cipher.doFinal();
		String keyBase64=encoder.encode(encryptData).replaceAll("[\r\n]","");
		return keyBase64;
	}
	
	/**
	 * 密钥解密
	 * @param keyBase64
	 * @param pub
	 * @return
	 * @throws Exception
	 */
	public static String decryptRSA(String keyBase64,PublicKey pub) throws Exception{
		BASE64Decoder decoder=new BASE64Decoder();
		//解密时，先使用我们的公钥解密出aes密钥
		byte[] encryptData = decoder.decodeBuffer(keyBase64);
		Cipher cipher =Cipher.getInstance("RSA/ECB/PKCs1padding");
		cipher.init(Cipher.DECRYPT_MODE,pub);
		cipher.update(encryptData);
		byte[] data=cipher.doFinal();
		String decrypt=new String(data,Charset.forName("utf-8"));
		return decrypt;
	}
	/**
	 * 转换为public Key类型
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * 转换为PrivateKey类型
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	 public static void main(String[] args) throws Exception {
	    	
//	    	KeyPair p = pkAndPub();
//	    	System.out.println(byte2Base64StringFun(p.getPrivate().getEncoded()));
	    	//MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJti8uFM3yI38q0xKV0PN2+I1wpUznNABIuHnIe4EqUqELfUAIHU+0CHoFyobdOEiTMuz8Q9J54Fi1HkX2FxobAKYzOGsEV9XTTPbFltkWxcIaWjHtLOiKzCuSr+QOTdQvUVPr9CYpUHQH/VVAC0yMNxXkaEhR3Lvb0bH0yFKxzDAgMBAAECgYEAhK1lg7jWoeoxvW5Hyl0O0Z5/NyQYr9HOz7D4l+41hW+4nK5ZzIImEwB7jgKG3F/Dor9qyj4JkOVdA4zDYHndOxTwoZmziWZAssmu+u62nvMJ+09ZjPw+KuVjKYQZOclncey2HBlYdEcRaLmk1DOaZVMFVkUD1W6/7Z8vtdiBd7kCQQDWkOnUWWNzzbTzmmdn6u3UjmuLGPigKT0Ap01D4YM9msHTxwtJQsp7JY3EjbrqT8Z6wfrrp/8EbQKprYkbSg2/AkEAuWR9oMRci9mKw2DMlot2o8Bg3j56MtwmIQq62501wf+zU2/pvQlZXD2kQejfU9+SwzSOaiskGde6cU9pQxQ5/QJAN61W3+FqKyyf3e6dEpwlpthmE6whJ/5LdBX1uL2V/rBfx4KI7TPFsvrltLFKZW7aBBS99dScGhvnavw67bfjpQJBALEWTW4owyIPDZ5tM1/GHfBzDdScp0s3wPAk+LJYrAYAgsH7j7CzYnlapDEVCWZrlBwEctj1Fonc6EneCfU6N90CQDIRSkqUkOe4n70j0sSAAPdVpSXS3vpxpRjXfM/MUNFJ3juPUhHR3SP3+1t/oOfyvkUkBp59BkAZ8ubGen//2CM=
	    	//MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbYvLhTN8iN/KtMSldDzdviNcKVM5zQASLh5yHuBKlKhC31ACB1PtAh6BcqG3ThIkzLs/EPSeeBYtR5F9hcaGwCmMzhrBFfV00z2xZbZFsXCGlox7Szoiswrkq/kDk3UL1FT6/QmKVB0B/1VQAtMjDcV5GhIUdy729Gx9MhSscwwIDAQAB
//	    	System.out.println(byte2Base64StringFun(p.getPublic().getEncoded()));
	    	String json = "{\"dataMap\":{\"isOpenAcc\":\"0\",\"serialNo\":\"1008420170913153958021008420170913153958024232\",\"cardID\":\"10084201709131539884\",\"cardNo\":\"1008420170913153957935\",\"cardMon\":\"500\",\"chargeTime\":\"20170928\",\"chargeMon\":\"20\",\"chargeBalance\":\"480\",\"cardBalance\":\"500\",\"chargeWay\":\"0\",\"chargeWayDes\":\"线上\"}}";
	    	
	    	
	    	String randomKey = BASE64Util.getRandomkey();
	    	System.out.println("randomKey:" + randomKey);
	    	//私钥
	    	String sys = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJti8uFM3yI38q0xKV0PN2+I1wpUznNABIuHnIe4EqUqELfUAIHU+0CHoFyobdOEiTMuz8Q9J54Fi1HkX2FxobAKYzOGsEV9XTTPbFltkWxcIaWjHtLOiKzCuSr+QOTdQvUVPr9CYpUHQH/VVAC0yMNxXkaEhR3Lvb0bH0yFKxzDAgMBAAECgYEAhK1lg7jWoeoxvW5Hyl0O0Z5/NyQYr9HOz7D4l+41hW+4nK5ZzIImEwB7jgKG3F/Dor9qyj4JkOVdA4zDYHndOxTwoZmziWZAssmu+u62nvMJ+09ZjPw+KuVjKYQZOclncey2HBlYdEcRaLmk1DOaZVMFVkUD1W6/7Z8vtdiBd7kCQQDWkOnUWWNzzbTzmmdn6u3UjmuLGPigKT0Ap01D4YM9msHTxwtJQsp7JY3EjbrqT8Z6wfrrp/8EbQKprYkbSg2/AkEAuWR9oMRci9mKw2DMlot2o8Bg3j56MtwmIQq62501wf+zU2/pvQlZXD2kQejfU9+SwzSOaiskGde6cU9pQxQ5/QJAN61W3+FqKyyf3e6dEpwlpthmE6whJ/5LdBX1uL2V/rBfx4KI7TPFsvrltLFKZW7aBBS99dScGhvnavw67bfjpQJBALEWTW4owyIPDZ5tM1/GHfBzDdScp0s3wPAk+LJYrAYAgsH7j7CzYnlapDEVCWZrlBwEctj1Fonc6EneCfU6N90CQDIRSkqUkOe4n70j0sSAAPdVpSXS3vpxpRjXfM/MUNFJ3juPUhHR3SP3+1t/oOfyvkUkBp59BkAZ8ubGen//2CM=";
			//公钥
	    	String gys ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbYvLhTN8iN/KtMSldDzdviNcKVM5zQASLh5yHuBKlKhC31ACB1PtAh6BcqG3ThIkzLs/EPSeeBYtR5F9hcaGwCmMzhrBFfV00z2xZbZFsXCGlox7Szoiswrkq/kDk3UL1FT6/QmKVB0B/1VQAtMjDcV5GhIUdy729Gx9MhSscwwIDAQAB";
	    	// 私钥用随机秘钥加密
	    	String symmetricKeyEncrpt = 
					BASE64Util.encryptRSA(randomKey, BASE64Util.getPrivateKey(sys));
			System.out.println("symmetricKeyEncrpt" + symmetricKeyEncrpt);
			// 用随机秘钥加密报文
			String jsonDataEncrypt = BASE64Util.encryptAES(randomKey, json);
			System.out.println("jsonDataEncrypt" + jsonDataEncrypt);
			// 服务端用公钥解密随机秘钥
			String randomKeyJm = BASE64Util.decryptRSA(symmetricKeyEncrpt, BASE64Util.getPublicKey(gys));
			System.out.println("服务端---------------randomKeyJm:" + randomKeyJm);
			// 服务端用随机秘钥解密报文
			String jsonJM = BASE64Util.decryptAES(jsonDataEncrypt, randomKeyJm);
			System.out.println("服务端---------------jsonJM:" + jsonJM);
			
	    }

}
