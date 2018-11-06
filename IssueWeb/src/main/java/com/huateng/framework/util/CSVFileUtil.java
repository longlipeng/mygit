package com.huateng.framework.util;  
  
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;  



import java.util.Calendar;
import java.util.Date;

import com.allinfinance.univer.manager.transactionQuery.dto.TransactionQueryDTO;
import com.allinfinance.univer.report.dto.CardBalanceDTO;
import com.huateng.framework.constant.Const;

import antlr.collections.List;
  
public class CSVFileUtil {  
    // CSV文件编码  
    public static final String ENCODE = "gbk";  
  
    private FileInputStream fis = null;  
    private InputStreamReader isw = null;  
    private BufferedReader br = null;  
    static String currentDate;
	private static TransactionQueryDTO transactionQueryDTO=new TransactionQueryDTO();

    public CSVFileUtil(String filename) throws Exception {  
        fis = new FileInputStream(filename);  
        isw = new InputStreamReader(fis, ENCODE);  
        br = new BufferedReader(isw);  
    }  
  
    // ==========以下是公开方法=============================  
    /** 
     * 从CSV文件流中读取一个CSV行。 
     * 
     * @throws Exception 
     */  
    public String readLine() throws Exception {  
  
        StringBuffer readLine = new StringBuffer();  
        boolean bReadNext = true;  
  
        while (bReadNext) {  
            //  
            if (readLine.length() > 0) {  
                readLine.append("\r\n");  
            }  
            // 一行  
            String strReadLine = br.readLine();  
  
            // readLine is Null  
            if (strReadLine == null) {  
                return null;  
            }  
            readLine.append(strReadLine);  
  
            // 如果双引号是奇数的时候继续读取。考虑有换行的是情况。  
            if (countChar(readLine.toString(), '"', 0) % 2 == 1) {  
                bReadNext = true;  
            } else {  
                bReadNext = false;  
            }  
        }  
        return readLine.toString();  
    }  
  
    /** 
     *把CSV文件的一行转换成字符串数组。指定数组长度，不够长度的部分设置为null。 
     */  
    public static String[] fromCSVLine(String source, int size) {  
        ArrayList tmpArray = fromCSVLinetoArray(source);  
        if (size < tmpArray.size()) {  
            size = tmpArray.size();  
        }  
        String[] rtnArray = new String[size];  
        tmpArray.toArray(rtnArray);  
        return rtnArray;  
    }  
  
    /** 
     * 把CSV文件的一行转换成字符串数组。不指定数组长度。 
     */  
    public static ArrayList fromCSVLinetoArray(String source) {  
        if (source == null || source.length() == 0) {  
            return new ArrayList();  
        }  
        int currentPosition = 0;  
        int maxPosition = source.length();  
        int nextComma = 0;  
        ArrayList rtnArray = new ArrayList();  
        while (currentPosition < maxPosition) {  
            nextComma = nextComma(source, currentPosition);  
            rtnArray.add(nextToken(source, currentPosition, nextComma));  
            currentPosition = nextComma + 1;  
            if (currentPosition == maxPosition) {  
                rtnArray.add("");  
            }  
        }  
        return rtnArray;  
    }  
  
  
    /** 
     * 把字符串类型的数组转换成一个CSV行。（输出CSV文件的时候用） 
     */  
    public static String toCSVLine(String[] strArray) {  
        if (strArray == null) {  
            return "";  
        }  
        StringBuffer cvsLine = new StringBuffer();  
        for (int idx = 0; idx < strArray.length; idx++) {  
            String item = addQuote(strArray[idx]);  
            cvsLine.append(item);  
            if (strArray.length - 1 != idx) {  
                cvsLine.append(',');  
            }  
        }  
        return cvsLine.toString();  
    }  
  
    /** 
     * 字符串类型的List转换成一个CSV行。（输出CSV文件的时候用） 
     */  
    public static String toCSVLine(ArrayList strArrList) {  
        if (strArrList == null) {  
            return "";  
        }  
        String[] strArray = new String[strArrList.size()];  
        for (int idx = 0; idx < strArrList.size(); idx++) {  
            strArray[idx] = (String) strArrList.get(idx);  
        }  
        return toCSVLine(strArray);  
    }  
  
    // ==========以下是内部使用的方法=============================  
    /** 
     *计算指定文字的个数。 
     * 
     * @param str 文字列 
     * @param c 文字 
     * @param start  开始位置 
     * @return 个数 
     */  
    private int countChar(String str, char c, int start) {  
        int i = 0;  
        int index = str.indexOf(c, start);  
        return index == -1 ? i : countChar(str, c, index + 1) + 1;  
    }  
  
    /** 
     * 查询下一个逗号的位置。 
     * 
     * @param source 文字列 
     * @param st  检索开始位置 
     * @return 下一个逗号的位置。 
     */  
    private static int nextComma(String source, int st) {  
        int maxPosition = source.length();  
        boolean inquote = false;  
        while (st < maxPosition) {  
            char ch = source.charAt(st);  
            if (!inquote && ch == ',') {  
                break;  
            } else if ('"' == ch) {  
                inquote = !inquote;  
            }  
            st++;  
        }  
        return st;  
    }  
  
    /** 
     * 取得下一个字符串 
     */  
    private static String nextToken(String source, int st, int nextComma) {  
        StringBuffer strb = new StringBuffer();  
        int next = st;  
        while (next < nextComma) {  
            char ch = source.charAt(next++);  
            if (ch == '"') {  
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {  
                    strb.append(ch);  
                    next++;  
                }  
            } else {  
                strb.append(ch);  
            }  
        }  
        return strb.toString();  
    }  
  
    /** 
     * 在字符串的外侧加双引号。如果该字符串的内部有双引号的话，把"转换成""。 
     * 
     * @param item  字符串 
     * @return 处理过的字符串 
     */  
    private static String addQuote(String item) {  
        if (item == null || item.length() == 0) {  
            return "\"\"";  
        }  
        StringBuffer sb = new StringBuffer();  
        sb.append('"');  
        for (int idx = 0; idx < item.length(); idx++) {  
            char ch = item.charAt(idx);  
            if ('"' == ch) {  
                sb.append("\"\"");  
            } else {  
                sb.append(ch);  
            }  
        }  
        sb.append('"');  
        return sb.toString();  
    }  
    
    public static void main(String[] args) throws Exception {
     String fileToBeRead = "C:\\Users\\hujian\\Downloads\\卡余额报表_测试_20160712.csv";

       /* CardBalanceDTO cardBalance = new  CardBalanceDTO();
    	CSVFileUtil util = new CSVFileUtil(fileToBeRead);
    	for(int i=0;i<4;i++){
    		
    	  String fileLine =	util.readLine();
    	  
    	  if(i!=3){
    		  
    		  continue;
    	  }
    	
    	  String[] strs =  fileLine.split(",");
    	  String first = strs[0];
    	  String date = first.split(":")[1];
    	  System.out.println(date);
    	  String second = strs[1];
    	  
    	  String balance = second.split(":")[1];
    	  System.out.println(balance);
    	}*/
     
 	Date startDate =null;
	Date endDate =null;
    try{
		
	transactionQueryDTO.setStartDate("2015-01-01");
	transactionQueryDTO.setEndDate("2016-01-01");
		
		if(null!=transactionQueryDTO.getEndDate()&&null!=transactionQueryDTO.getStartDate()){
			if(!transactionQueryDTO.getEndDate().equals("")&&!transactionQueryDTO.getStartDate().equals("")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				startDate = sdf.parse(transactionQueryDTO.getStartDate());
				endDate  = sdf.parse(transactionQueryDTO.getEndDate());
        	if(startDate.getTime()>endDate.getTime()){
        		
        	}
			 }
		
		}
}catch(Exception e){
	   
	   e.printStackTrace();
}	
		
	Calendar dd = Calendar.getInstance();//定义日期实例
	dd.setTime(startDate);//设置日期起始时间

	while(dd.getTime().before(endDate)){//判断是否到结束日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(dd.getTime());
		currentDate= str;
	    String fileName = getName();
	    com.allinfinance.univer.report.dto.CardBalanceDTO cardBalance = new  com.allinfinance.univer.report.dto.CardBalanceDTO();
    	CSVFileUtil util;
		try {
			util = new CSVFileUtil(fileName);
			for(int i=0;i<4;i++){
	    		
		    	  String fileLine =	util.readLine();
		    	  
		    	  if(i!=3){
		    		  
		    		  continue;
		    	  }
		    	
		    	  String[] strs =  fileLine.split(",");
		    	  String first = strs[0];
		    	  String date = first.split(":")[1];
		    	  System.out.println(date);
		    	  cardBalance.setDataDate(date);
		    	  String second = strs[1];
		    	  
		    	  String balance = second.split(":")[1];
		    	  BigDecimal b = new BigDecimal(balance);
		    	  cardBalance.setAccBal(b);
		    	  //cardBalanceList.add(cardBalance);
		    	  System.out.println(balance);
		    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    
		dd.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
	}
    
	}
    
    
    public static String getName() {
		return (Const.CARD_BALANCE + "_"+"测试" + "_"
				+ currentDate.replace("-", "") + Const.CSV);
	}
}  