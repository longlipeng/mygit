package com.huateng.framework.util;

import java.math.BigDecimal;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.huateng.framework.exception.BizServiceException;

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
    public static String strToBigDeByDiv(String srcString){
    	String decString=new BigDecimal(srcString.substring(5, srcString.length())).divide(new BigDecimal(100)).toString();
    	return decString;
    }
    public static String strToBigDeByMuti(String srcString){
    	String decString=new BigDecimal(srcString).multiply(new BigDecimal(100)).toString();
    	return decString;
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

	public static String addZero(String str, int len){
		for (int i = 0; i < len - str.length(); i++){
			str = "0" + str;
		}
		return str;
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
    
	
/*	private static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}*/
   
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
	
	public static String rpad(String org, int length, char plugger) {
		if (org.length() == length)
			return org;

		if (org.length() > length)
			return org.substring(0, length);

		String temp = org;
		for (int i = 0; i < (length - org.length()); i++) {
			temp += plugger;
		}
		return temp;
	}
	public static String rpad(String org, int length, String plugger) {
		if (org.length() == length)
			return org;

		if (org.length() > length)
			return org.substring(0, length);

		String temp = org;
		for (int i = 0; i < (length - org.length()); i++) {
			temp += plugger;
		}
		return temp;
	}
	public static String lpad(String org, int length, char plugger) {
		if (org.length() == length)
			return org;

		if (org.length() > length)
			return org.substring(org.length() - length);

		String temp = org;
		for (int i = 0; i < (length - org.length()); i++) {
			temp = plugger + temp;
		}

		return temp;
	}
	
	//判断字符串是否是数字
	public static boolean Isdouble(String number){
		String num="^[0-9]+(.[0-9]{1,2})?$";
		return number.matches(num);
		
	}
	
	/**
	 * String数组转成字符串
	 * @param args
	 * @return
	 */
	public static String getStr(String[]args){
		String str="";
		for(int i=0;i<args.length;i++){	
				if(i==args.length-1){
					str+=(String)args[i];
				}else{
	              str+=(String)args[i]+",";
				}
		}	
		return str;
	}
/*	public static void main(String[] args) {
		String[] a={"1","2","3"};
		logger.info(StringUtils.getStr(a));
	}*/
	
}
