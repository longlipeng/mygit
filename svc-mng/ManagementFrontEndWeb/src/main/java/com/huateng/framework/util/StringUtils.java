package com.huateng.framework.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.huateng.framework.exception.BizServiceException;

public class StringUtils {
	static final String STR_ZERO = "0";
	static final String DEFAULT_NUM = STR_ZERO;
	private static Logger logger = Logger.getLogger(StringUtils.class);
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
	
	//按length填充String  不足后补空格
	public static String blankFillString(String value,int length){
		String a="";
		int len=value.length();
		if(len<length){
			for(int i=0;i<length-len;i++){
				a+=" ";
			}
		}
		return value+a;
	}
/*	public static void main(String[] args) {
		String[] a={"1","2","3"};
		logger.info(StringUtils.getStr(a));
	}*/

    private static final char[] ESCAPES = createEscapes();

    /*
     *  For better performance most methods are folded down. Don't you scream... :)
     */

    /**
     *  HTML encoding (does not convert line breaks).
     *  Replaces all '&gt;' '&lt;' '&amp;' and '"' with entity reference
     */
    public static String HTMLEnc(String s) {
        return XMLEncNA(s);
    }

    /**
     *  XML Encoding.
     *  Replaces all '&gt;' '&lt;' '&amp;', "'" and '"' with entity reference
     */
    public static String XMLEnc(String s) {
        return XMLOrXHTMLEnc(s, "&apos;");
    }

    /**
     *  XHTML Encoding.
     *  Replaces all '&gt;' '&lt;' '&amp;', "'" and '"' with entity reference
     *  suitable for XHTML decoding in common user agents (including legacy
     *  user agents, which do not decode "&apos;" to "'", so "&#39;" is used
     *  instead [see http://www.w3.org/TR/xhtml1/#C_16])
     */
    public static String XHTMLEnc(String s) {
        return XMLOrXHTMLEnc(s, "&#39;");
    }
    
    private static String XMLOrXHTMLEnc(String s, String aposReplacement) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '<' || c == '>' || c == '&' || c == '"' || c == '\'') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '<': b.append("&lt;"); break;
                    case '>': b.append("&gt;"); break;
                    case '&': b.append("&amp;"); break;
                    case '"': b.append("&quot;"); break;
                    case '\'': b.append(aposReplacement); break;
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '<' || c == '>' || c == '&' || c == '"' || c == '\'') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '<': b.append("&lt;"); break;
                            case '>': b.append("&gt;"); break;
                            case '&': b.append("&amp;"); break;
                            case '"': b.append("&quot;"); break;
                            case '\'': b.append(aposReplacement); break;
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) b.append(s.substring(next));
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }
    
    /**
     *  XML encoding without replacing apostrophes.
     *  @see #XMLEnc(String)
     */
    public static String XMLEncNA(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '<' || c == '>' || c == '&' || c == '"') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '<': b.append("&lt;"); break;
                    case '>': b.append("&gt;"); break;
                    case '&': b.append("&amp;"); break;
                    case '"': b.append("&quot;"); break;
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '<' || c == '>' || c == '&' || c == '"') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '<': b.append("&lt;"); break;
                            case '>': b.append("&gt;"); break;
                            case '&': b.append("&amp;"); break;
                            case '"': b.append("&quot;"); break;
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) b.append(s.substring(next));
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }

    /**
     *  XML encoding for attributes valies quoted with <tt>"</tt> (not with <tt>'</tt>!).
     *  Also can be used for HTML attributes that are quoted with <tt>"</tt>.
     *  @see #XMLEnc(String)
     */
    public static String XMLEncQAttr(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '<' || c == '&' || c == '"') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '<': b.append("&lt;"); break;
                    case '&': b.append("&amp;"); break;
                    case '"': b.append("&quot;"); break;
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '<' || c == '&' || c == '"') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '<': b.append("&lt;"); break;
                            case '&': b.append("&amp;"); break;
                            case '"': b.append("&quot;"); break;
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) {
                    b.append(s.substring(next));
                }
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }

    /**
     *  XML encoding without replacing apostrophes and quotation marks and
     *  greater-thans (except in {@code ]]>}).
     *  @see #XMLEnc(String)
     */
    public static String XMLEncNQG(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '<'
                    || (c == '>' && i > 1
                            && s.charAt(i - 1) == ']'
                            && s.charAt(i - 2) == ']')
                    || c == '&') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '<': b.append("&lt;"); break;
                    case '>': b.append("&gt;"); break;
                    case '&': b.append("&amp;"); break;
                    default: throw new RuntimeException("Bug: unexpected char");
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '<'
                            || (c == '>' && i > 1
                                    && s.charAt(i - 1) == ']'
                                    && s.charAt(i - 2) == ']')
                            || c == '&') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '<': b.append("&lt;"); break;
                            case '>': b.append("&gt;"); break;
                            case '&': b.append("&amp;"); break;
                            default: throw new RuntimeException("Bug: unexpected char");
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) {
                    b.append(s.substring(next));
                }
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }
    
    /**
     *  Rich Text Format encoding (does not replace line breaks).
     *  Escapes all '\' '{' '}' and '"'
     */
    public static String RTFEnc(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '\\' || c == '{' || c == '}') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '\\': b.append("\\\\"); break;
                    case '{': b.append("\\{"); break;
                    case '}': b.append("\\}"); break;
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '\\' || c == '{' || c == '}') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '\\': b.append("\\\\"); break;
                            case '{': b.append("\\{"); break;
                            case '}': b.append("\\}"); break;
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) b.append(s.substring(next));
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }


    private static char[] createEscapes()
    {
        char[] escapes = new char['\\' + 1];
        for(int i = 0; i < 32; ++i)
        {
            escapes[i] = 1;
        }
        escapes['\\'] = '\\';
        escapes['\''] = '\'';
        escapes['"'] = '"';
        escapes['<'] = 'l';
        escapes['>'] = 'g';
        escapes['&'] = 'a';
        escapes['\b'] = 'b';
        escapes['\t'] = 't';
        escapes['\n'] = 'n';
        escapes['\f'] = 'f';
        escapes['\r'] = 'r';
        escapes['$'] = '$';
        return escapes;
    }

    public static String FTLStringLiteralEnc(String s)
    {
        StringBuffer buf = null;
        int l = s.length();
        int el = ESCAPES.length;
        for(int i = 0; i < l; i++)
        {
            char c = s.charAt(i);
            if(c < el)
            {
                char escape = ESCAPES[c];
                switch(escape)
                {
                    case 0:
                    {
                        if (buf != null) {
                            buf.append(c);
                        }
                        break;
                    }
                    case 1:
                    {
                        if (buf == null) {
                            buf = new StringBuffer(s.length() + 3);
                            buf.append(s.substring(0, i));
                        }
                        // hex encoding for characters below 0x20
                        // that have no other escape representation
                        buf.append("\\x00");
                        int c2 = (c >> 4) & 0x0F;
                        c = (char) (c & 0x0F);
                        buf.append((char) (c2 < 10 ? c2 + '0' : c2 - 10 + 'A'));
                        buf.append((char) (c < 10 ? c + '0' : c - 10 + 'A'));
                        break;
                    }
                    default:
                    {
                        if (buf == null) {
                            buf = new StringBuffer(s.length() + 2);
                            buf.append(s.substring(0, i));
                        }
                        buf.append('\\');
                        buf.append(escape);
                    }
                }
            } else {
                if (buf != null) {
                    buf.append(c);
                }
            }
        }
        return buf == null ? s : buf.toString();
    }

  

    public static String capitalize(String s) {
        StringTokenizer st = new StringTokenizer(s, " \t\r\n", true);
        StringBuffer buf = new StringBuffer(s.length());
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            buf.append(tok.substring(0, 1).toUpperCase());
            buf.append(tok.substring(1).toLowerCase());
        }
        return buf.toString();
    }

    public static boolean getYesNo(String s) {
        if (s.startsWith("\"")) {
            s = s.substring(1, s.length() -1);

        }
        if (s.equalsIgnoreCase("n")
                || s.equalsIgnoreCase("no")
                || s.equalsIgnoreCase("f")
                || s.equalsIgnoreCase("false")) {
            return false;
        }
        else if  (s.equalsIgnoreCase("y")
                || s.equalsIgnoreCase("yes")
                || s.equalsIgnoreCase("t")
                || s.equalsIgnoreCase("true")) {
            return true;
        }
        throw new IllegalArgumentException("Illegal boolean value: " + s);
    }

    /**
     * Splits a string at the specified character.
     */
    public static String[] split(String s, char c) {
        int i, b, e;
        int cnt;
        String res[];
        int ln = s.length();

        i = 0;
        cnt = 1;
        while ((i = s.indexOf(c, i)) != -1) {
            cnt++;
            i++;
        }
        res = new String[cnt];

        i = 0;
        b = 0;
        while (b <= ln) {
            e = s.indexOf(c, b);
            if (e == -1) e = ln;
            res[i++] = s.substring(b, e);
            b = e + 1;
        }
        return res;
    }

    /**
     * Splits a string at the specified string.
     */
    public static String[] split(String s, String sep, boolean caseInsensitive) {
        String splitString = caseInsensitive ? sep.toLowerCase() : sep;
        String input = caseInsensitive ? s.toLowerCase() : s;
        int i, b, e;
        int cnt;
        String res[];
        int ln = s.length();
        int sln = sep.length();

        if (sln == 0) throw new IllegalArgumentException(
                "The separator string has 0 length");

        i = 0;
        cnt = 1;
        while ((i = input.indexOf(splitString, i)) != -1) {
            cnt++;
            i += sln;
        }
        res = new String[cnt];

        i = 0;
        b = 0;
        while (b <= ln) {
            e = input.indexOf(splitString, b);
            if (e == -1) e = ln;
            res[i++] = s.substring(b, e);
            b = e + sln;
        }
        return res;
    }

    /**
     * Replaces all occurrences of a sub-string in a string.
     * @param text The string where it will replace <code>oldsub</code> with
     *     <code>newsub</code>.
     * @return String The string after the replacements.
     */
    public static String replace(String text, 
                                  String oldsub, 
                                  String newsub, 
                                  boolean caseInsensitive,
                                  boolean firstOnly) 
    {
        StringBuffer buf;
        int tln;
        int oln = oldsub.length();
        
        if (oln == 0) {
            int nln = newsub.length();
            if (nln == 0) {
                return text;
            } else {
                if (firstOnly) {
                    return newsub + text;
                } else {
                    tln = text.length();
                    buf = new StringBuffer(tln + (tln + 1) * nln);
                    buf.append(newsub);
                    for (int i = 0; i < tln; i++) {
                        buf.append(text.charAt(i));
                        buf.append(newsub);
                    }
                    return buf.toString();
                }
            }
        } else {
            oldsub = caseInsensitive ? oldsub.toLowerCase() : oldsub;
            String input = caseInsensitive ? text.toLowerCase() : text;
            int e = input.indexOf(oldsub);
            if (e == -1) {
                return text;
            }
            int b = 0;
            tln = text.length();
            buf = new StringBuffer(
                    tln + Math.max(newsub.length() - oln, 0) * 3);
            do {
                buf.append(text.substring(b, e));
                buf.append(newsub);
                b = e + oln;
                e = input.indexOf(oldsub, b);
            } while (e != -1 && !firstOnly);
            buf.append(text.substring(b));
            return buf.toString();
        }
    }

    /**
     * Removes the line-break from the end of the string.
     */
    public static String chomp(String s) {
        if (s.endsWith("\r\n")) return s.substring(0, s.length() - 2);
        if (s.endsWith("\r") || s.endsWith("\n"))
                return s.substring(0, s.length() - 1);
        return s;
    }

    /**
     * Converts the parameter with <code>toString</code> (if not
     * <code>null</code>)and passes it to {@link #jQuote(String)}. 
     */
    public static String jQuote(Object obj) {
        return jQuote(obj != null ? obj.toString() : null);
    }
    
    /**
     * Quotes string as Java Language string literal.
     * Returns string <code>"null"</code> if <code>s</code>
     * is <code>null</code>.
     */
    public static String jQuote(String s) {
        if (s == null) {
            return "null";
        }
        int ln = s.length();
        StringBuffer b = new StringBuffer(ln + 4);
        b.append('"');
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '"') {
                b.append("\\\"");
            } else if (c == '\\') {
                b.append("\\\\");
            } else if (c < 0x20) {
                if (c == '\n') {
                    b.append("\\n");
                } else if (c == '\r') {
                    b.append("\\r");
                } else if (c == '\f') {
                    b.append("\\f");
                } else if (c == '\b') {
                    b.append("\\b");
                } else if (c == '\t') {
                    b.append("\\t");
                } else {
                    b.append("\\u00");
                    int x = c / 0x10;
                    b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
                    x = c & 0xF;
                    b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
                }
            } else {
                b.append(c);
            }
        } // for each characters
        b.append('"');
        return b.toString();
    }

    /**
     * Converts the parameter with <code>toString</code> (if not
     * <code>null</code>)and passes it to {@link #jQuoteNoXSS(String)}. 
     */
    public static String jQuoteNoXSS(Object obj) {
        return jQuoteNoXSS(obj != null ? obj.toString() : null);
    }
    
    /**
     * Same as {@link #jQuoteNoXSS(String)} but also escapes <code>'&lt;'</code>
     * as <code>\u003C</code>. This is used for log messages to prevent XSS
     * on poorly written Web-based log viewers. 
     */
    public static String jQuoteNoXSS(String s) {
        if (s == null) {
            return "null";
        }
        int ln = s.length();
        StringBuffer b = new StringBuffer(ln + 4);
        b.append('"');
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '"') {
                b.append("\\\"");
            } else if (c == '\\') {
                b.append("\\\\");
            } else if (c == '<') {
                b.append("\\u003C");
            } else if (c < 0x20) {
                if (c == '\n') {
                    b.append("\\n");
                } else if (c == '\r') {
                    b.append("\\r");
                } else if (c == '\f') {
                    b.append("\\f");
                } else if (c == '\b') {
                    b.append("\\b");
                } else if (c == '\t') {
                    b.append("\\t");
                } else {
                    b.append("\\u00");
                    int x = c / 0x10;
                    b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
                    x = c & 0xF;
                    b.append((char) (x < 0xA ? x + '0' : x - 0xA + 'A'));
                }
            } else {
                b.append(c);
            }
        } // for each characters
        b.append('"');
        return b.toString();
    }
    
    /**
     * Escapes the <code>String</code> with the escaping rules of Java language
     * string literals, so it is safe to insert the value into a string literal.
     * The resulting string will not be quoted.
     * 
     * <p>All characters under UCS code point 0x20 will be escaped.
     * Where they have no dedicated escape sequence in Java, they will
     * be replaced with hexadecimal escape (<tt>\</tt><tt>u<i>XXXX</i></tt>). 
     * 
     * @see #jQuote(String)
     */ 
    public static String javaStringEnc(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '"' || c == '\\' || c < 0x20) {
                StringBuffer b = new StringBuffer(ln + 4);
                b.append(s.substring(0, i));
                while (true) {
                    if (c == '"') {
                        b.append("\\\"");
                    } else if (c == '\\') {
                        b.append("\\\\");
                    } else if (c < 0x20) {
                        if (c == '\n') {
                            b.append("\\n");
                        } else if (c == '\r') {
                            b.append("\\r");
                        } else if (c == '\f') {
                            b.append("\\f");
                        } else if (c == '\b') {
                            b.append("\\b");
                        } else if (c == '\t') {
                            b.append("\\t");
                        } else {
                            b.append("\\u00");
                            int x = c / 0x10;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'a'));
                            x = c & 0xF;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'a'));
                        }
                    } else {
                        b.append(c);
                    }
                    i++;
                    if (i >= ln) {
                        return b.toString();
                    }
                    c = s.charAt(i);
                }
            } // if has to be escaped
        } // for each characters
        return s;
    }
    
    /**
     * Escapes a <code>String</code> according the JavaScript string literal
     * escaping rules. The resulting string will not be quoted.
     * 
     * <p>It escapes both <tt>'</tt> and <tt>"</tt>.
     * In additional it escapes <tt>></tt> as <tt>\></tt> (to avoid
     * <tt>&lt;/script></tt>).
     * 
     * <p>All characters under UCS code point 0x20 will be escaped.
     * Where they have no dedicated escape sequence in JavaScript, they will
     * be replaced with hexadecimal escape (<tt>\</tt><tt>u<i>XXXX</i></tt>). 
     */ 
    public static String javaScriptStringEnc(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '"' || c == '\'' || c == '\\' || c == '>' || c < 0x20) {
                StringBuffer b = new StringBuffer(ln + 4);
                b.append(s.substring(0, i));
                while (true) {
                    if (c == '"') {
                        b.append("\\\"");
                    } else if (c == '\'') {
                        b.append("\\'");
                    } else if (c == '\\') {
                        b.append("\\\\");
                    } else if (c == '>') {
                        b.append("\\>");
                    } else if (c < 0x20) {
                        if (c == '\n') {
                            b.append("\\n");
                        } else if (c == '\r') {
                            b.append("\\r");
                        } else if (c == '\f') {
                            b.append("\\f");
                        } else if (c == '\b') {
                            b.append("\\b");
                        } else if (c == '\t') {
                            b.append("\\t");
                        } else {
                            b.append("\\x");
                            int x = c / 0x10;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'A'));
                            x = c & 0xF;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'A'));
                        }
                    } else {
                        b.append(c);
                    }
                    i++;
                    if (i >= ln) {
                        return b.toString();
                    }
                    c = s.charAt(i);
                }
            } // if has to be escaped
        } // for each characters
        return s;
    }

    /**
     * Escapes a <code>String</code> according the JSON string literal
     * escaping rules. The resulting string will <em>not</em> be quoted;
     * the caller have to ensure that they are there in the final output.
     * 
     * <p>Beware, it doesn't escape <tt>'</tt>, as JSON string must be delimited with
     * <tt>"</tt>, and JSON has no <tt>\'</tt> escape either!
     * 
     * <p>It will escape <tt>/</tt> as <tt>\/</tt> if it's after <tt>&lt;</tt>,
     * to avoid <tt>&lt;/script></tt>.
     * 
     * <p>It will escape <tt>></tt> as <tt>\</tt><tt>u003E</tt> if it's after
     * <tt>]]</tt>, to avoid closing a CDATA section.
     * 
     * <p>All characters under UCS code point 0x20 will be escaped.
     * Where they have no dedicated escape sequence in JSON, they will
     * be replaced with hexadecimal escape (<tt>\</tt><tt>u<i>XXXX</i></tt>). 
     */
    public static String jsonStringEnc(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '"' || c == '\\' || c < 0x20
                    || (c == '/' && i > 0 && s.charAt(i -1) == '<')
                    || (c == '>' && i > 1
                        && s.charAt(i - 1) == ']' && s.charAt(i - 2) == ']')) {
                StringBuffer b = new StringBuffer(ln + 4);
                b.append(s.substring(0, i));
                while (true) {
                    if (c == '"') {
                        b.append("\\\"");
                    } else if (c == '\\') {
                        b.append("\\\\");
                    } else if (c == '/' && i > 0 && s.charAt(i -1) == '<') {
                        b.append("\\/");
                    } else if (c == '>' && i > 1
                            && s.charAt(i - 1) == ']' && s.charAt(i - 2) == ']') {
                        b.append("\\u003E");
                    } else if (c < 0x20) {
                        if (c == '\n') {
                            b.append("\\n");
                        } else if (c == '\r') {
                            b.append("\\r");
                        } else if (c == '\f') {
                            b.append("\\f");
                        } else if (c == '\b') {
                            b.append("\\b");
                        } else if (c == '\t') {
                            b.append("\\t");
                        } else {
                            b.append("\\u00");
                            int x = c / 0x10;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'A'));
                            x = c & 0xF;
                            b.append((char)
                                    (x < 0xA ? x + '0' : x - 0xA + 'A'));
                        }
                    } else {
                        b.append(c);
                    }
                    i++;
                    if (i >= ln) {
                        return b.toString();
                    }
                    c = s.charAt(i);
                }
            } // if has to be escaped
        } // for each characters
        return s;
    }

    /**
     * Parses a name-value pair list, where the pairs are separated with comma,
     * and the name and value is separated with colon.
     * The keys and values can contain only letters, digits and <tt>_</tt>. They
     * can't be quoted. White-space around the keys and values are ignored. The
     * value can be omitted if <code>defaultValue</code> is not null. When a
     * value is omitted, then the colon after the key must be omitted as well.
     * The same key can't be used for multiple times.
     * 
     * @param s the string to parse.
     *     For example: <code>"strong:100, soft:900"</code>.
     * @param defaultValue the value used when the value is omitted in a
     *     key-value pair.
     * 
     * @return the map that contains the name-value pairs.
     * 
     * @throws java.text.ParseException if the string is not a valid name-value
     *     pair list.
     */
    public static Map parseNameValuePairList(String s, String defaultValue)
    throws java.text.ParseException {
        Map map = new HashMap();
        
        char c = ' ';
        int ln = s.length();
        int p = 0;
        int keyStart;
        int valueStart;
        String key;
        String value;
        
        fetchLoop: while (true) {
            // skip ws
            while (p < ln) {
                c = s.charAt(p);
                if (!Character.isWhitespace(c)) {
                    break;
                }
                p++;
            }
            if (p == ln) {
                break fetchLoop;
            }
            keyStart = p;

            // seek key end
            while (p < ln) {
                c = s.charAt(p);
                if (!(Character.isLetterOrDigit(c) || c == '_')) {
                    break;
                }
                p++;
            }
            if (keyStart == p) {
                throw new java.text.ParseException(
                       "Expecting letter, digit or \"_\" "
                        + "here, (the first character of the key) but found "
                        + jQuote(String.valueOf(c))
                        + " at position " + p + ".",
                        p);
            }
            key = s.substring(keyStart, p);

            // skip ws
            while (p < ln) {
                c = s.charAt(p);
                if (!Character.isWhitespace(c)) {
                    break;
                }
                p++;
            }
            if (p == ln) {
                if (defaultValue == null) {
                    throw new java.text.ParseException(
                            "Expecting \":\", but reached "
                            + "the end of the string "
                            + " at position " + p + ".",
                            p);
                }
                value = defaultValue;
            } else if (c != ':') {
                if (defaultValue == null || c != ',') {
                    throw new java.text.ParseException(
                            "Expecting \":\" here, but found "
                            + jQuote(String.valueOf(c))
                            + " at position " + p + ".",
                            p);
                }

                // skip ","
                p++;
                
                value = defaultValue;
            } else {
                // skip ":"
                p++;
    
                // skip ws
                while (p < ln) {
                    c = s.charAt(p);
                    if (!Character.isWhitespace(c)) {
                        break;
                    }
                    p++;
                }
                if (p == ln) {
                    throw new java.text.ParseException(
                            "Expecting the value of the key "
                            + "here, but reached the end of the string "
                            + " at position " + p + ".",
                            p);
                }
                valueStart = p;
    
                // seek value end
                while (p < ln) {
                    c = s.charAt(p);
                    if (!(Character.isLetterOrDigit(c) || c == '_')) {
                        break;
                    }
                    p++;
                }
                if (valueStart == p) {
                    throw new java.text.ParseException(
                            "Expecting letter, digit or \"_\" "
                            + "here, (the first character of the value) "
                            + "but found "
                            + jQuote(String.valueOf(c))
                            + " at position " + p + ".",
                            p);
                }
                value = s.substring(valueStart, p);

                // skip ws
                while (p < ln) {
                    c = s.charAt(p);
                    if (!Character.isWhitespace(c)) {
                        break;
                    }
                    p++;
                }
                
                // skip ","
                if (p < ln) {
                    if (c != ',') {
                        throw new java.text.ParseException(
                                "Excpecting \",\" or the end "
                                + "of the string here, but found "
                                + jQuote(String.valueOf(c))
                                + " at position " + p + ".",
                                p);
                    } else {
                        p++;
                    }
                }
            }
            
            // store the key-value pair
            if (map.put(key, value) != null) {
                throw new java.text.ParseException(
                        "Dublicated key: "
                        + jQuote(key), keyStart);
            }
        }
        
        return map;
    }
    
    /**
     * @return whether the name is a valid XML tagname.
     * (This routine might only be 99% accurate. Should maybe REVISIT) 
     */
    static public boolean isXMLID(String name) {
        for (int i=0; i<name.length(); i++) {
            char c = name.charAt(i);
            if (i==0) {
                if (c== '-' || c=='.' || Character.isDigit(c))
                    return false;
            }
            if (!Character.isLetterOrDigit(c) && c != ':' && c != '_' && c != '-' && c!='.') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Pads the string at the left with spaces until it reaches the desired
     * length. If the string is longer than this length, then it returns the
     * unchanged string. 
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     */
    public static String leftPad(String s, int minLength) {
        return leftPad(s, minLength, ' ');
    }
    
    /**
     * Pads the string at the left with the specified character until it reaches
     * the desired length. If the string is longer than this length, then it
     * returns the unchanged string.
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     * @param filling the filling pattern.
     */
    public static String leftPad(String s, int minLength, char filling) {
        int ln = s.length();
        if (minLength <= ln) {
            return s;
        }
        
        StringBuffer res = new StringBuffer(minLength);
        
        int dif = minLength - ln;
        for (int i = 0; i < dif; i++) {
            res.append(filling);
        }
        
        res.append(s);
        
        return res.toString();
    }

    /**
     * Pads the string at the left with a filling pattern until it reaches the
     * desired length. If the string is longer than this length, then it returns
     * the unchanged string. For example: <code>leftPad('ABC', 9, '1234')</code>
     * returns <code>"123412ABC"</code>.
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     * @param filling the filling pattern. Must be at least 1 characters long.
     *     Can't be <code>null</code>.
     */
    public static String leftPad(String s, int minLength, String filling) {
        int ln = s.length();
        if (minLength <= ln) {
            return s;
        }
        
        StringBuffer res = new StringBuffer(minLength);

        int dif = minLength - ln;
        int fln = filling.length();
        if (fln == 0) {
            throw new IllegalArgumentException(
                    "The \"filling\" argument can't be 0 length string.");
        }
        int cnt = dif / fln;
        for (int i = 0; i < cnt; i++) {
            res.append(filling);
        }
        cnt = dif % fln;
        for (int i = 0; i < cnt; i++) {
            res.append(filling.charAt(i));
        }
        
        res.append(s);
        
        return res.toString();
    }
    
    /**
     * Pads the string at the right with spaces until it reaches the desired
     * length. If the string is longer than this length, then it returns the
     * unchanged string. 
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     */
    public static String rightPad(String s, int minLength) {
        return rightPad(s, minLength, ' ');
    }
    
    /**
     * Pads the string at the right with the specified character until it
     * reaches the desired length. If the string is longer than this length,
     * then it returns the unchanged string.
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     * @param filling the filling pattern.
     */
    public static String rightPad(String s, int minLength, char filling) {
        int ln = s.length();
        if (minLength <= ln) {
            return s;
        }
        
        StringBuffer res = new StringBuffer(minLength);

        res.append(s);
        
        int dif = minLength - ln;
        for (int i = 0; i < dif; i++) {
            res.append(filling);
        }
        
        return res.toString();
    }

    /**
     * Pads the string at the right with a filling pattern until it reaches the
     * desired length. If the string is longer than this length, then it returns
     * the unchanged string. For example: <code>rightPad('ABC', 9, '1234')</code>
     * returns <code>"ABC412341"</code>. Note that the filling pattern is
     * started as if you overlay <code>"123412341"</code> with the left-aligned
     * <code>"ABC"</code>, so it starts with <code>"4"</code>.
     * 
     * @param s the string that will be padded.
     * @param minLength the length to reach.
     * @param filling the filling pattern. Must be at least 1 characters long.
     *     Can't be <code>null</code>.
     */
    public static String rightPad(String s, int minLength, String filling) {
        int ln = s.length();
        if (minLength <= ln) {
            return s;
        }
        
        StringBuffer res = new StringBuffer(minLength);

        res.append(s);

        int dif = minLength - ln;
        int fln = filling.length();
        if (fln == 0) {
            throw new IllegalArgumentException(
                    "The \"filling\" argument can't be 0 length string.");
        }
        int start = ln % fln;
        int end = fln - start <= dif
                ? fln
                : start + dif;
        for (int i = start; i < end; i++) {
            res.append(filling.charAt(i));
        }
        dif -= end - start;
        int cnt = dif / fln;
        for (int i = 0; i < cnt; i++) {
            res.append(filling);
        }
        cnt = dif % fln;
        for (int i = 0; i < cnt; i++) {
            res.append(filling.charAt(i));
        }
        
        return res.toString();
    }
    
    /**
     * Converts a version number string to an integer for easy comparison.
     * The version number must start with numbers separated with
     * dots. There can be any number of such dot-separated numbers, but only
     * the first three will be considered. After the numbers arbitrary text can
     * follow, and will be ignored.
     * 
     * The string will be trimmed before interpretation.
     * 
     * @return major * 1000000 + minor * 1000 + micro
     */
    public static int versionStringToInt(String version) {
        version = version.trim();
        
        int[] parts = new int[3];
        int partIdx = 0;
        boolean valid = false;
        for (int i = 0; i < version.length(); i++) {
            char c = version.charAt(i);
            if (c >= '0' && c <= '9') {
                parts[partIdx] = parts[partIdx] * 10 + (c - '0');
                valid = true;
            } else {
                if (c == '.') {
                    if (partIdx == 2) break; else partIdx++;
                } else {
                    break;
                }
            }
        }
        if (!valid) throw new IllegalArgumentException(
                "A version number string " + jQuote(version)
                + " must start with a number.");
        return parts[0] * 1000000 + parts[1] * 1000 + parts[2];
    }
    
}

