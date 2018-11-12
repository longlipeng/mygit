package com.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class RSASignUtils {


    /**
     * 编码格式
     */
    private static final String CHARSET_UTF_8 = "utf-8";

    //SIT
    private static final String PK = "A3CC0143D9A036B70967928D5A1E017DC6065EE1A29096822352367A469DD779F0FBABCA4F4F005A22F30443B34DB443DA7C633536685AEBBD46C68B3D86D7A1D6B958516B9AF38849A180E3FCA69A293C6199631A9CF63A113A54A72937B830A7139F10A1FF1C77C25DFBFA500AE9BBA5EA57B4ED0D11DCE2CA51B3CF0221B4CCB3E8786FBD496CD2305E35473050E31A4BC1C244B85C63159C4F51AD23D55055A2766AB6E4535CE4AF2299380BEE77";

    public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsW7MAryACUkNRgJ882b8zzMedV30vf8IE0AVekX8LAXKHeU4L7/bIbfg9rqMITGlwO7rL7/bgmlRd/S5Nq4KzXJnnYZnh7T5q1wK7FkuAUNRWf2dz+rZSyQO00AsMTCWT1GmkHyRpYuKD11YtX8DCcGZG1gLd80enxDVCGMXfKwIDAQAB";

    public static final String PRIVATEKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALDVfWFjRR5Tes2VGrZjAp3ke9kLVzfmyZ4Xu6ABoeFhnyKmqkFbU9ieqqTxoqXmob16xKTpo0Gb+JTNH0Wq6eg/6gdXYtX+kzghp63uWr2XmRCkUAj3QRf0871cstnAeZmUWrEPuq9BHjI+4ZsTvI/JTUvYd7yjnZaAkEoiZzxHAgMBAAECgYA6aqx6yaKBvC4rUAB9FAQ/SMWpE9gCPZmJQmnXF/L0U52QguJnqNXwD6prxu9mQuRXhgEEQbKO8AmUjpawGyAd7oaQ6arJqRyMA23HnQf1K9auRQ1/qjbkc3FrVzSHJ7hbc5oHBqYbDDdGzSMYy5Yx6vijlxN9NOTws7rnAeb6IQJBAOCrDlzQ1FMgHKxI5dy8ojuXN+1pDL9YtT84CVM0cIDacC7m5gR8/RnMW5W35eNhrkPYw9cdQLG3A4ssV04XDbkCQQDJfrCD/3xKcGvvqVWZJ6WXCWm1mrV2zrHoAy9Pzb/G8mRmpBOvfkjpcz4So3QTQUxyt/iyKfoPb74oQ5aKpZn/AkAOaktQn0GQV9YfI8pD4IKUSj6XaA8eU+XFOaY3hXzWd62NBNmrXg2rUxZpQ9RnQ4Q37DecnM7i2DhM/LpZU1dZAkEAijo2H6eB/ZMYCMaS6UlQiMe1VBc5C7MKSw78QvPz7yhUBjHjK04n+LbNS34EYD04k65vltHDMRnIStjrClxGwwJBAKLOw9GxoPZmhRQWvX6FVoeH8zaNA4NT9frIdOBSWeyep5Gu5wggN3Ilk1k2aMk6PNn1N/v2+OCrtguwDN8cx9g=";

    /**
     * buildRequestPara 方法
     *
     * @param sParaTemp  参数
     * @param privateKey 参数
     * @return
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String privateKey) {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String sign = buildRequestSign(sPara, privateKey);
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", sign);
        sPara.put("sign_type", "RSA");

        return sPara;
    }

    /**
     * buildRequestParaForTenPay 方法
     *
     * @param sParaTemp 参数
     * @return
     */
    public static Map<String, String> buildRequestParaForTenPay(Map<String, String> sParaTemp) {
        // 除去数组中的空值和签名参数
        return paraFilterForTenpay(sParaTemp);
    }

    /**
     * messageDigest 方法
     *
     * @param text      参数
     * @param publicKey 参数
     * @return
     */
    public static String messageDigest(String text, String publicKey) {
        StringBuilder sbText = new StringBuilder();
        sbText.append(text);
        sbText.append(publicKey);
        return DigestUtils.md5Hex(text.getBytes());
    }

    /**
     * buildRequestSign 方法
     *
     * @param sPara      参数
     * @param privateKey 参数
     * @return
     */
    public static String buildRequestSign(Map<String, String> sPara, String privateKey) {
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = createLinkString(sPara);
        return sign(prestr, privateKey, CHARSET_UTF_8);
    }

    /**
     * sign 方法
     *
     * @param content      参数
     * @param privateKey   参数
     * @param inputCharset 参数
     * @return
     */
    public static String sign(String content, String privateKey, String inputCharset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes(inputCharset));

            byte[] signed = signature.sign();

            return Base64.encodeBase64String(signed);
        } catch (InvalidKeyException e) {
            e.getStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        } catch (InvalidKeySpecException e) {
            e.getStackTrace();
        } catch (SignatureException e) {
            e.getStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * createLinkString 方法
     *
     * @param params 参数
     * @return
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuffer sbPreStr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                sbPreStr.append(key);
                sbPreStr.append("=");
                sbPreStr.append(value);
            } else {
                sbPreStr.append(key);
                sbPreStr.append("=");
                sbPreStr.append(value);
                sbPreStr.append("&");
            }
        }

        return String.valueOf(sbPreStr);
    }

    /**
     * paraFilter 方法
     *
     * @param sArray 参数
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new LinkedHashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        Iterator<Entry<String, String>> ite = sArray.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, String> entry = ite.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * paraFilterForTenpay 方法
     *
     * @param sArray 参数
     * @return
     */
    public static Map<String, String> paraFilterForTenpay(Map<String, String> sArray) {
        Map<String, String> result = new LinkedHashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        Iterator<Entry<String, String>> ite = sArray.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, String> entry = ite.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.equals("")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * verifySign 方法
     *
     * @param params    参数
     * @param publicKey 参数
     * @param sign      参数
     * @return
     */
    public static boolean verifySign(Map<String, String> params, String publicKey, String sign) {
        // 过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(params);
        // 获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        // 获得签名验证结果
        return verify(preSignStr, sign, publicKey, CHARSET_UTF_8);
    }

    /**
     * RSA验签名检查
     *
     * @param content      待签名数据
     * @param sign         签名值
     * @param publicKey    公钥
     * @param inputCharset 编码格式
     * @return
     */
    public static boolean verify(String content, String sign, String publicKey, String inputCharset) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

            signature.initVerify(pubKey);
            signature.update(content.getBytes(inputCharset));

            return signature.verify(Base64.decodeBase64(sign));
        } catch (InvalidKeyException e) {
            e.getStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        } catch (InvalidKeySpecException e) {
            e.getStackTrace();
        } catch (SignatureException e) {
            e.getStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        }
        return false;
    }


    /**
     * 公钥加密pin
     *
     * @param pin
     * @return
     */
    public static String pkEncPin(String pin) {
//    	LOGGER.info("encryption pin={},publicKey={}",pin,PK);
        String contentText = "";
        contentText = pin + "FFFFFFFFFF";
        String pass = "";
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            BigInteger n = new BigInteger(PK, 16);
            BigInteger e = new BigInteger("03", 16);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);
            //获取公钥
            PublicKey pubkey = keyFactory.generatePublic(spec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = null;
            plaintext = (Hex.decodeHex(contentText.toCharArray()));
            byte[] enBytes = cipher.doFinal(plaintext);
//				pass = str2HexStr(enBytes);
            pass = new String(Hex.encodeHexString(enBytes));
        } catch (DecoderException de) {
            de.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public static void main(String[] args) {

		/*Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("charset", "utf-8");
		//sPara.put("tran_code", "00003004");
		sPara.put("timestamp", "2018-07-30 11:40:08");
		sPara.put("out_trade_no", "pre_201808020000109479");
		sPara.put("out_trade_list", "车享日常保养服务");
		//sPara.put("out_refund_no", "pre_201808020000109089");
		sPara.put("sign_type", "RSA");
		sPara.put("card_pwd", "11cd2696ee0672fa0881f4718bde537f7a65e65e583b712240274b42d48e455916e980adb480787f6f7025985f9f13093c59cefa9e5d79ee99c88a04b62cb44ebb81af4c869a1a27fb72ef06669fb7dc0d852f0e573b79642fb46b7d7a8367a17de7340743b2ecf2815f8e203cbe924c897a27a372ff282d4235f578eddcfa7592cf1f4d2dad37005b7d1f3ae2e97cdd13a258ca3915de360242af64f5b85a048e61aed241b9f3b8aa44239ec960667");
		sPara.put("notify_url", "https://pmfsit.chexiang.com/giftcardPay/asyncCallBack.htm");
		sPara.put("total_amount", "0.01");
		//9602010100000080416
		//9702021020000005962
		sPara.put("card_no", "9702021020000000062");
		sPara.put("ip_address", "18800206659@864883034691307");
        sPara.put("channel", "01");
        sPara.put("app_id", "897102742140001");
        sPara.put("format", "JSON");
        sPara.put("tran_code",null);
        sPara.put("terminal_sn", null);
		//MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOxbswCvIAJSQ1GAnzzZvzPMx51XfS9/wgTQBV6RfwsBcod5Tgvv9sht+D2uowhMaXA7usvv9uCaVF39Lk2rgrNcmedhmeHtPmrXArsWS4BQ1FZ/Z3P6tlLJA7TQCwxMJZPUaaQfJGli4oPXVi1fwMJwZkbWAt3zR6fENUIYxd8rAgMBAAECgYEAwMfycqZkR0Bto/Zy6yD3cPqf/de0RZ/A0kgReRbXNlTR42mvnND2ynfXuAfO9h/NCDvPmjN5e6ylD5xHvhYcHTordLEEoQaJQQbNnrEB+S/KJt9u4es+ts6cUFprrqkvKbCOPQCxBdDuCPS/YYONV+Gz1rvm3xchaV+IaGQU1bECQQD4KyZItzGm4Z8+0K0aXEUWilHTpS6kbRY5AqlfVIDho/0F1KEQ73jHZnYPC7eXVmmUyPy+NFiM3LIAj7LrekI9AkEA89EjhZfcyhKfcCoiwaqVIYq57HO7TZrmsi8LdysvLqIG1iFRQhLFg8KAxDfsvcKdNqEIT24SeD2dIaZEuOTFhwJAWQOJKN3kxln5N5u760+bTFlyvjpqf3nNVceZnuL9k3GxqOy6lQuAQ+2BfSYTxQmutzpcRZEbDfN/OWpRn9rrdQJBAKGcDp+OeZq3nVcLlt4n45HIElTYoV5fPmr9I+xpg2y1mTJ2AxZ4utMLKa7mItXlGbAMLSX5H+TzciPLweGMQiECQERSQKZCJX4PECaXtDe8cu/ccwMczeKmJ+HJH3EgEBGCPpFnyLJu/Pdnf9iXPNlEsu4br7sUNdc2Vl0NjcQ/iJQ=
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOxbswCvIAJSQ1GAnzzZvzPMx51XfS9/wgTQBV6RfwsBcod5Tgvv9sht+D2uowhMaXA7usvv9uCaVF39Lk2rgrNcmedhmeHtPmrXArsWS4BQ1FZ/Z3P6tlLJA7TQCwxMJZPUaaQfJGli4oPXVi1fwMJwZkbWAt3zR6fENUIYxd8rAgMBAAECgYEAwMfycqZkR0Bto/Zy6yD3cPqf/de0RZ/A0kgReRbXNlTR42mvnND2ynfXuAfO9h/NCDvPmjN5e6ylD5xHvhYcHTordLEEoQaJQQbNnrEB+S/KJt9u4es+ts6cUFprrqkvKbCOPQCxBdDuCPS/YYONV+Gz1rvm3xchaV+IaGQU1bECQQD4KyZItzGm4Z8+0K0aXEUWilHTpS6kbRY5AqlfVIDho/0F1KEQ73jHZnYPC7eXVmmUyPy+NFiM3LIAj7LrekI9AkEA89EjhZfcyhKfcCoiwaqVIYq57HO7TZrmsi8LdysvLqIG1iFRQhLFg8KAxDfsvcKdNqEIT24SeD2dIaZEuOTFhwJAWQOJKN3kxln5N5u760+bTFlyvjpqf3nNVceZnuL9k3GxqOy6lQuAQ+2BfSYTxQmutzpcRZEbDfN/OWpRn9rrdQJBAKGcDp+OeZq3nVcLlt4n45HIElTYoV5fPmr9I+xpg2y1mTJ2AxZ4utMLKa7mItXlGbAMLSX5H+TzciPLweGMQiECQERSQKZCJX4PECaXtDe8cu/ccwMczeKmJ+HJH3EgEBGCPpFnyLJu/Pdnf9iXPNlEsu4br7sUNdc2Vl0NjcQ/iJQ=";
		sPara = RSASignUtils.buildRequestPara(sPara, privateKey);
		System.out.println(sPara.get("sign"));
		System.out.println("添加签名后的请求参数 sPara：" + sPara.toString());
		
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsW7MAryACUkNRgJ882b8zzMedV30vf8IE0AVekX8LAXKHeU4L7/bIbfg9rqMITGlwO7rL7/bgmlRd/S5Nq4KzXJnnYZnh7T5q1wK7FkuAUNRWf2dz+rZSyQO00AsMTCWT1GmkHyRpYuKD11YtX8DCcGZG1gLd80enxDVCGMXfKwIDAQAB";
		boolean result = RSASignUtils.verifySign(sPara, publicKey, sPara.get("sign"));
		
		System.out.println("签名校验结果：" + result);*/
        Map<String, String> sPara = new HashMap<String, String>();
        sPara.put("charset", "utf-8");
        sPara.put("timestamp", "2018-11-07 11:40:08");
        sPara.put("out_trade_no", "pre_201808020000109123");
        sPara.put("sign_type", "RSA");
        sPara.put("refund_amount","1");
        sPara.put("out_refund_no","pre_201808020000109124");
        //sPara.put("total_amount", "1");
        //sPara.put("card_pwd","1234567890ABCDEF");
        sPara.put("card_no", "9088889502102102180");
        sPara.put("ip_address", "18800206659@864883034691307");
        sPara.put("channel", "01");
        sPara.put("app_id", "897310055110001");
        sPara.put("format", "JSON");
        //MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOxbswCvIAJSQ1GAnzzZvzPMx51XfS9/wgTQBV6RfwsBcod5Tgvv9sht+D2uowhMaXA7usvv9uCaVF39Lk2rgrNcmedhmeHtPmrXArsWS4BQ1FZ/Z3P6tlLJA7TQCwxMJZPUaaQfJGli4oPXVi1fwMJwZkbWAt3zR6fENUIYxd8rAgMBAAECgYEAwMfycqZkR0Bto/Zy6yD3cPqf/de0RZ/A0kgReRbXNlTR42mvnND2ynfXuAfO9h/NCDvPmjN5e6ylD5xHvhYcHTordLEEoQaJQQbNnrEB+S/KJt9u4es+ts6cUFprrqkvKbCOPQCxBdDuCPS/YYONV+Gz1rvm3xchaV+IaGQU1bECQQD4KyZItzGm4Z8+0K0aXEUWilHTpS6kbRY5AqlfVIDho/0F1KEQ73jHZnYPC7eXVmmUyPy+NFiM3LIAj7LrekI9AkEA89EjhZfcyhKfcCoiwaqVIYq57HO7TZrmsi8LdysvLqIG1iFRQhLFg8KAxDfsvcKdNqEIT24SeD2dIaZEuOTFhwJAWQOJKN3kxln5N5u760+bTFlyvjpqf3nNVceZnuL9k3GxqOy6lQuAQ+2BfSYTxQmutzpcRZEbDfN/OWpRn9rrdQJBAKGcDp+OeZq3nVcLlt4n45HIElTYoV5fPmr9I+xpg2y1mTJ2AxZ4utMLKa7mItXlGbAMLSX5H+TzciPLweGMQiECQERSQKZCJX4PECaXtDe8cu/ccwMczeKmJ+HJH3EgEBGCPpFnyLJu/Pdnf9iXPNlEsu4br7sUNdc2Vl0NjcQ/iJQ=
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOxbswCvIAJSQ1GAnzzZvzPMx51XfS9/wgTQBV6RfwsBcod5Tgvv9sht+D2uowhMaXA7usvv9uCaVF39Lk2rgrNcmedhmeHtPmrXArsWS4BQ1FZ/Z3P6tlLJA7TQCwxMJZPUaaQfJGli4oPXVi1fwMJwZkbWAt3zR6fENUIYxd8rAgMBAAECgYEAwMfycqZkR0Bto/Zy6yD3cPqf/de0RZ/A0kgReRbXNlTR42mvnND2ynfXuAfO9h/NCDvPmjN5e6ylD5xHvhYcHTordLEEoQaJQQbNnrEB+S/KJt9u4es+ts6cUFprrqkvKbCOPQCxBdDuCPS/YYONV+Gz1rvm3xchaV+IaGQU1bECQQD4KyZItzGm4Z8+0K0aXEUWilHTpS6kbRY5AqlfVIDho/0F1KEQ73jHZnYPC7eXVmmUyPy+NFiM3LIAj7LrekI9AkEA89EjhZfcyhKfcCoiwaqVIYq57HO7TZrmsi8LdysvLqIG1iFRQhLFg8KAxDfsvcKdNqEIT24SeD2dIaZEuOTFhwJAWQOJKN3kxln5N5u760+bTFlyvjpqf3nNVceZnuL9k3GxqOy6lQuAQ+2BfSYTxQmutzpcRZEbDfN/OWpRn9rrdQJBAKGcDp+OeZq3nVcLlt4n45HIElTYoV5fPmr9I+xpg2y1mTJ2AxZ4utMLKa7mItXlGbAMLSX5H+TzciPLweGMQiECQERSQKZCJX4PECaXtDe8cu/ccwMczeKmJ+HJH3EgEBGCPpFnyLJu/Pdnf9iXPNlEsu4br7sUNdc2Vl0NjcQ/iJQ=";
        sPara = RSASignUtils.buildRequestPara(sPara, privateKey);

        System.out.println("添加签名后的请求参数 sPara：" + sPara.toString());

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsW7MAryACUkNRgJ882b8zzMedV30vf8IE0AVekX8LAXKHeU4L7/bIbfg9rqMITGlwO7rL7/bgmlRd/S5Nq4KzXJnnYZnh7T5q1wK7FkuAUNRWf2dz+rZSyQO00AsMTCWT1GmkHyRpYuKD11YtX8DCcGZG1gLd80enxDVCGMXfKwIDAQAB";
        boolean result = RSASignUtils.verifySign(sPara, publicKey, sPara.get("sign"));

        System.out.println("签名校验结果：" + result);

    }

    public static String getCorrelationId() {
        StringBuffer correlationId = new StringBuffer();
        correlationId.append(new Date().getTime());
        correlationId.append(new Date().getTime());
        correlationId.append((Math.random() * 9 + 1) * 100000);
        return correlationId.toString();
    }

}