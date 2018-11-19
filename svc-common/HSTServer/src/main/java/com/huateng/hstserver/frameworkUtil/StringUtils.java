package com.huateng.hstserver.frameworkUtil;

import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.huateng.hstserver.exception.BizServiceException;


public class StringUtils {
	private static Logger logger = Logger.getLogger(StringUtils.class);
	static final String STR_ZERO = "0";
	static final String DEFAULT_NUM = STR_ZERO;

    public static String getFormattedNum(String num, int len) {
        int numLen = num == null ? 0 : num.length();
        StringBuffer buf = new StringBuffer();
        len = len - numLen;
        for (int i = 0; i < len; i++) {
            buf.append(DEFAULT_NUM);
        }

        return numLen > 0 ? buf.append(num).toString() : buf.toString();
    }

    public static String getRepeatSpace(int len) {
        return getRepeatString(" ", len);
    }
    

    public static String getRepeatZero(int len) {
        return getRepeatString("0", len);
    }
    

    public static String getRepeatString(String s, int len){
        StringBuffer buf = new StringBuffer(len);
        for (int i=0; i<len; i++){
            buf.append(s);            
        }
        return buf.toString(); 
    }
    

    public static byte[] toByteArray(String hexStr) {
        byte[] bytes = null;

        if (hexStr != null) {
            if (hexStr.startsWith("0x") || hexStr.startsWith("0X")) {
                hexStr = hexStr.substring(2);
            }
            int len = hexStr.length();

            if (len % 2 != 0) {
                throw new IllegalStateException("invalid hex string: " + hexStr);
            }

            len = len / 2;
            bytes = new byte[len];
            int idx = 0;

            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(
                        idx, idx + 2), 16));
                idx += 2;
            }
        }

        return bytes == null ? new byte[0] : bytes;
    }
    

    public static String toHexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        int len = bytes == null ? 0 : bytes.length;

        String str = null;

        for (int i = 0; i < len; i++) {
            str = Integer.toHexString(0xff & bytes[i]);
            buf.append(str.length() < 2 ? STR_ZERO.concat(str) : str);
        }

        return buf.toString();
    }

    public static String toBinString (byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        for (int j=0; j< bytes.length; j++){
	        for (int i=7; i>=0;i--){
	            if (((1 << i) & bytes[j]) != 0){
	                buf.append("1");
	            }else{
	                buf.append("0");
	            }
	        } 
        }
        return buf.toString();
    }
    

    public static String[] split(String source, String delim) {
        int i = 0;
        int l = 0;
        if (source == null || delim == null)
            return new String[0];
        if (source.equals("") || delim.equals(""))
            return new String[0];

        StringTokenizer st = new StringTokenizer(source, delim);
        l = st.countTokens();
        String[] s = new String[l];
        while (st.hasMoreTokens()) {
            s[i++] = st.nextToken();
        }
        return s;
    }	   

    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
        	sTemp = Integer.toHexString(0xFF & bArray[i]);
        	if (sTemp.length() < 2)
        		sb.append(0);
        	sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    
	
	private static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	public static byte[] hexString2Bytes(String hexStr){
		byte[] baKeyword = new byte[hexStr.length()/2]; 
		for(int i = 0; i < baKeyword.length; i++) { 
			try { 
				baKeyword[i] = (byte)(0xff & Integer.parseInt(hexStr.substring(i*2, i*2+2),16));
			}catch(Exception e){ 
				logger.error(e.getMessage()); 
			} 
		}
		return baKeyword;
	}
	
	public static String stringXor(String str1, String str2) throws BizServiceException{
		StringBuffer sb = new StringBuffer();
		if (str1.length() != str2.length()){
			throw new BizServiceException("xor args must be have the same length");
		}
		
		byte[] bt1 = Str2Hex(str1);
		byte[] bt2 = Str2Hex(str2);
		
		for (int i = 0; i < bt1.length; i++){
			sb.append(addZero(Integer.toHexString((bt1[i] ^ bt2[i]) & 0xFF), 2));
		}
		
		return sb.toString();
	}
	
	public static String addZero(String str, int len){
		for (int i = 0; i < len - str.length(); i++){
			str = "0" + str;
		}
		return str;
	}

	/** 字符串转字节数组 * */
	public static byte[] Str2Hex(String str) {
		char[] ch = str.toCharArray();
		byte[] b = new byte[ch.length / 2];
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] == 0)
				break;
			if (ch[i] >= '0' && ch[i] <= '9') {
				ch[i] = (char) (ch[i] - '0');
			} else if (ch[i] >= 'A' && ch[i] <= 'F') {
				ch[i] = (char) (ch[i] - 'A' + 10);
			}
		}
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (((ch[2 * i] << 4) & 0xf0) + (ch[2 * i + 1] & 0x0f));
		}
		return b;
	}

	/**
	 *转成16进制
	 * @param b
	 * @return
	 */
	public static String Hex2Str(byte[] b) {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i=0; i<b.length; i++) {
            char hi = Character.forDigit ((b[i] >> 4) & 0x0F, 16);
            char lo = Character.forDigit (b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }
	
	
	public static String SetToString(Set value){
		String b="";
		if(null!=value && value.size()>0){
			String a=value.toString();
			int len=a.length();
			b=a.substring(1,len-1);			
		}
		return b;
	}
	
	public static String CollectionToString(Collection value){
		Object[] a=value.toArray();
		String b="";
		if(null!=a && a.length>0){			
			for(int i=0;i<a.length;i++){
				b+=a[i]+"|";
			}
		}
		return b;
	}
	
	//按length填充String  不足前补零
	public static String fillString(String value,int length){
		String a="";
		int len=value.length();
		if(len<length){
			for(int i=0;i<length-len;i++){
				a+="0";
			}
		}
		return a+value;
	}
	
	//按length填充String  不足前补空格
	public static String blankFillString(String value,int length){
		String a="";
		int len=value.length();
		if(len<length){
			for(int i=0;i<length-len;i++){
				a+=" ";
			}
		}
		return a+value;
	}
	
	public static byte[] fillByteArray(byte[] a,int length){
		byte[] msg=new byte[length];
		if(a.length<length){
			int len=length-a.length;
			byte [] b=new byte[len];
			for(int i=0;i<len;i++){
				b[i]=0;
			}
			System.arraycopy(a, 0, msg, 0, a.length);
			System.arraycopy(b, 0, msg, a.length, b.length);
		}
		return msg;
	}
	
	/**
	 * 10进制转2进制
	 * @param value
	 * @return
	 */
	public static byte[] StringTobyte(int sizeItr){
		int lenFieldLen=8;
		byte[] body=new byte[lenFieldLen];
		byte byteLen = 0;
		for (int i = lenFieldLen-1; i >=0 ; i --)
		{
			byteLen =  (byte)(sizeItr % 256);
			body[lenFieldLen - i - 1] = byteLen;
			sizeItr = sizeItr / 256;
		}
		return body;
	}
//	public static void main(String[] args) throws Exception{
///*		String a = "AA";
//		String b = "BB";
//		byte[] aa = Str2Hex(a);
//		byte[] bb = Str2Hex(b);
//		
//		int cc = aa[0] ^ bb[0];
//		
//		logger.info(String.valueOf(Integer.toHexString(cc)));*/
//		//logger.info(stringXor("06674031FFFFFFFF", "0000000000000030"));
//		
//		Map map = new HashMap();
//
//		
//		logger.info(StringUtils.SetToString(map.keySet()));
//		logger.info(StringUtils.CollectionToString(map.values()));
//		
//		logger.info(StringUtils.fillString(String.valueOf(317), 5));
//		logger.info(StringUtils.blankFillString(String.valueOf(317), 10));
//		logger.info(StringUtils.hexString2Bytes("00000000"));
//		logger.info(StringUtils.Hex2Str("00000000".getBytes()));
//		int[] a={1,2,3};
//		int[]b={4,5,6};
//		int[]c=new int[6];
//		System.arraycopy(a, 0,c, 0, 3);
//		System.arraycopy(b, 0,c, 3, 3);
//		logger.info("a"+Arrays.toString(c));
//		
//		byte[] msgTop=new byte[]{0,0,0,0,0,0,0,0,-43,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,17,0,0,0,-1,-1,-1,-1,0,0,-5,30,97,99,113,100,111,109,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,118,67,97,114,100,113,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,32,32,32,32,32,32};
//		logger.info(StringUtils.bytes2HexString(msgTop));
//		//String s="acqdom";
//		String s="       317";
//		logger.info(StringUtils.bytes2HexString(s.getBytes()));
//		String k="vCardBatInq";
//		logger.info(StringUtils.bytes2HexString(k.getBytes()));
//		
////		Function function=new Function();
////
////		function.sendMessage("vCardBatInq", map);
//		
//		
//		logger.info(StringUtils.bytes2HexString(StringUtils.fillByteArray(k.getBytes(), 32)));
//	}
}
