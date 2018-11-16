package tools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class test2 {
	
    public static final String KEY_ALGORITHM = "RSA";
	public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 2048;
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	public static final String ENCODE_ALGORITHM  = "SHA-256";
	
	public static void main(String[] args){
		//无序
		Map<String, String> maps = new HashMap<String,String>();
		//自然排序
//		Map<String, String> map = new TreeMap<String, String>();
		maps.put("version", "1");
		maps.put("encoding", "2");
		maps.put("certId", "3");
		maps.put("signMethod", "4");
		maps.put("txnType", "5");
		maps.put("txnNo", "6");
		maps.put("acqInsCode", "7");
		maps.put("txnDate", "8");
		maps.put("sndTime", "9");
		maps.put("insSeq", "10");
		maps.put("payeeAcctNo", "11");
		maps.put("payeeAcctName", "12");
		maps.put("currencyCode", "13");
		maps.put("txnAmt", "14");
		//map集合转String数组
		String[] mapKeys = maps.keySet().toArray(new String[]{});
		//从小到大排序   递增
		Arrays.sort(mapKeys);
		StringBuffer sb = new StringBuffer();
		for (String key : mapKeys) {
			sb.append(key).append("=").append(maps.get(key)).append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		
		Map<String, byte[]> map = generateKeyBytes();
		//获取公钥
		PublicKey publicKey = restorePublicKey(map.get(PUBLIC_KEY));
		//获取私钥
		PrivateKey privateKey = restorePrivateKey(map.get(PRIVATE_KEY));
		//得到数字签名
		byte[] sing_byte = getMD5Digest(sb.toString(),privateKey);
		//进行Base64编码
		String base64 = encodeBase64(sing_byte);
		//使用公钥对密文进行解密,解密后与摘要进行匹配
		verifySign(publicKey,sb.toString(),sing_byte);
		System.out.println("将签名进行Base64编码:" + base64);
	}
	
	/**
	 * 验签
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plain_text
	 *            明文
	 * @param signed
	 *            签名
	 */
	public static boolean verifySign(PublicKey publicKey, String plain_text, byte[] signed) {
 
		MessageDigest messageDigest;
		boolean SignedSuccess = false;
		try {
			messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
			messageDigest.update(plain_text.getBytes());
			byte[] outputDigest_verify = messageDigest.digest();
			String asd = bytesToHexString(outputDigest_verify);
			//System.out.println("SHA-256加密后-----》" +bytesToHexString(outputDigest_verify));
			Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
			verifySign.initVerify(publicKey);
			verifySign.update(asd.getBytes());
			SignedSuccess = verifySign.verify(signed);
			System.out.println("验证成功？---" + SignedSuccess);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SignedSuccess;
	}

	 /**
     * 使用指定哈希算法计算摘要信息
     * @param content 内容
     * @param algorithm 哈希算法
     * @return 内容摘要
     */
    public static byte[] getMD5Digest(String content,PrivateKey privateKey){
        try {
        	//使用SHA-256加密内容成摘要
            MessageDigest messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
            messageDigest.update(content.getBytes("utf-8"));
            byte[] outputDigest_sign = messageDigest.digest();
            String asd = bytesToHexString(outputDigest_sign);
            System.out.println("SHA-256将明文加密后的摘要:" + asd);
            //使用SHA256withRSA将摘要加密成密文,即数字签名
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
			Sign.initSign(privateKey);
			Sign.update(asd.getBytes());
			byte[] signed = Sign.sign();
			System.out.println("SHA256withRSA将摘要加密后的数字签名:" + bytesToHexString(signed));
            return signed;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * bytes[]换成16进制字符串
     * @param bytes 即将转换的数据
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer(bytes.length);
        String temp = null;
        for (int i = 0;i< bytes.length;i++){
            temp = Integer.toHexString(0xFF & bytes[i]);
            if (temp.length() <2){
                sb.append(0);
            }
            sb.append(temp);
        }
        return sb.toString();
    }
    
    /**
     * 生成密钥对
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {
 
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取公钥
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
 
    /**
     * 获取私钥
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
    /**
	 * 二进制数据编码为BASE64字符串 
	 * @param data
	 * @return
	 */
	public static String encodeBase64(byte[] bytes){
		return new String(Base64.encodeBase64(bytes));
	}
	
	/**
	 * BASE64解码
	 * @param bytes
	 * @return
	 */
	public static byte[] decodeBase64(byte[] bytes) { 
		byte[] result = null;
		try {
			result = Base64.decodeBase64(bytes);
		} catch (Exception e) {
			return null;
		}
        return result;  
    }

    
}
