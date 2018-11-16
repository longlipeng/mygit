package tools;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.huateng.system.util.CommonFunction;

public class TEST1 {

	public static String formatToYuan(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.trim().equals("") || input.length() != 12){
			return "";
		}else if(input.trim().equals("000000000000") || input.trim().equals("-00000000000")){
			return "0";
		}else if(input.startsWith("-")){
			StringBuffer formatTo= new StringBuffer("-");
			int temp = Integer.valueOf(input.substring(1, 10));
			formatTo.append(String.valueOf(temp));
			formatTo.append(".").append(input.subSequence(10, 12));
			return formatTo.toString();
		}else{
			StringBuffer formatTo = new StringBuffer("");
			int temp = Integer.valueOf(input.substring(0, 10));
			formatTo.append(String.valueOf(temp));
			formatTo.append(".").append(input.subSequence(10, 12));
			return formatTo.toString();
		}
	}
	public static void main(String[] args) {
		try{
//			//指定目录(“.”表示当前目录)
//			File dir = new File("E:\\ips--words");
//			String[] names;
//			//获得所有java文件
//			DirFilter filter=new DirFilter(".pdf");
//			names = dir.list(filter);
//			//打印所有txt文件名称
//			for(int i=0;i<names.length;i++){
//				System.out.println("i:"+i+" name:"+names[i]);
//			}
//			String rsp = "831208109900040000004321310650172300006";
//			System.out.println(rsp.subSequence(14, 16));
			
//			String transamtbig1 = "123456.56";
//			if(transamtbig1.indexOf(".") == -1){
//				transamtbig1 += "00";
//			}else{
//				transamtbig1 = transamtbig1.replace(".","");
//			}
//			String transamtbig = CommonFunction.fillString(transamtbig2, '0',12, false);
//			System.out.println(transamtbig1);
						
			System.out.println(formatToYuan("-00000002000"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
