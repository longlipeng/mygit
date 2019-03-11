package com;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Test3 {

	private static String version;
	private static String encoding;
	private static String certId;
//	private static String signature;
	private static String signMethod;
	private static String txnType;
//	private static String backUrl;
	private static String txnNo;
	private static String acqInsCode;
	private static String txnDate;
	private static String sndTime;
	private static String insSeq;
	private static String payeeAcctNo;
	private static String payeeAcctName;
//	private static String acctNo;
//	private static String acctName;
	private static String currencyCode;
	private static String txnAmt;
	
	/**
	 * 签名
	 * Map集合存储键值转数组递增排序
	 * HashMap: 无序的
	 * TreeMap: 有序的  两种排序方式: 自然排序和定制排序
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		String[] text = 
//			{
//				"version","encoding","certId","signMethod","txnType","txnNo","acqInsCode",
//				"txnDate","sndTime","insSeq","payeeAcctNo","payeeAcctName","currencyCode","txnAmt"
//			};
		
		Map<String, String> map = new HashMap<String,String>();
//		Map<String, String> map = new TreeMap<String, String>();
		map.put("version", "1");
		map.put("encoding", "2");
		map.put("certId", "3");
		map.put("signMethod", "4");
		map.put("txnType", "5");
		map.put("txnNo", "6");
		map.put("acqInsCode", "7");
		map.put("txnDate", "8");
		map.put("sndTime", "9");
		map.put("insSeq", "10");
		map.put("payeeAcctNo", "11");
		map.put("payeeAcctName", "12");
		map.put("currencyCode", "13");
		map.put("txnAmt", "14");
		//map集合转String数组
		String[] mapKeys = map.keySet().toArray(new String[]{});
		//从小到大排序   递增
		Arrays.sort(mapKeys);
		StringBuffer sb = new StringBuffer();
		for (String key : mapKeys) {
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
//		for (String key : map.keySet()) {
//			sb.append(key).append("=").append(map.get(key)).append("&");
//		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println("ASCII递增排序后的字符串:" + sb.toString());
		String str = getMD5Digest(sb.toString(),"sha-256");
		byte[] asd = str.getBytes();
		System.out.println("sha-256加密后的摘要:" + str);
		System.out.println("sha-256加密后的摘要长度:" + asd.length);
//		System.out.println(mapKeys[0]);
//		System.out.println(text.length);
		
		/*for (int i = 0; i < text.length-1; i++) {
//			System.out.println(text[i]);
			for (int j = 0; j < text.length-i-1; j++) {
				String pre = text[j];//第一个位置
				String next = text[j+1];//第二个位置
				
				char[] preChar = pre.toCharArray();
				char[] nextChar = next.toCharArray();
//				System.out.println(preChar[2]);
//				System.out.println(nextChar[3]);
//				System.out.println(preChar.length);
				
				char preHead = preChar[0];
				char nextTail = nextChar[0];
//				System.out.println(preHead);
//				System.out.println(nextTail);
				String tx;
				if((int)preHead>(int)nextTail){
//					System.out.println((int)preHead);
//					System.out.println((int)nextTail);
					tx = text[j+1];
					text[j+1] = text[j];
					text[j] = tx;
				}else if((int)preHead==(int)nextTail){
					int count = 0;
					while((int)preHead==(int)nextTail){
						preHead = preChar[count+1];
						nextTail = nextChar[count+1];
						if((int)preHead>(int)nextTail){
							tx = text[j+1];
							text[j+1] = text[j];
							text[j] = tx;
						}else{
							count++; 
						}
					}
				}
			}
		}*/
		
		/*for (int i = 0; i < text.length; i++) {
//			System.out.println(text[i]);
			StringBuffer sb = new StringBuffer();
			sb.append(text[i]);
			sb.append("=");
			sb.append("123");
			sb.append("&");
			if(i==text.length-1){
//				System.out.println(sb.length()-1);
				sb.deleteCharAt(sb.length()-1);
			}
			System.out.print(sb.toString());
		}*/
		
//		acqInsCode=123&certId=123&currencyCode=123&encoding=123&insSeq=123&payeeAcctName=123&payeeAcctNo=123&signMethod=123&sndTime=123&txnAmt=123&txnDate=123&txnNo=123&txnType=123&version=123
		
		
		
	}
	
	/**
     * 使用指定哈希算法计算摘要信息
     * @param content 内容
     * @param algorithm 哈希算法 SHA-256
     * @return 内容摘要
     */
    public static String getMD5Digest(String content,String algorithm){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(content.getBytes("utf-8"));
            return bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将字节数组转换成16进制字符串
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
	
	
}
