package com;

public class Test6 {

	public static void main(String[] args){
		//如何打印出字符串中最长的数字段(即12987)   0-9  48-57
		String str = "a33btsd657sd8asd80oas12987hd587nm4";
		
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArr.length; i++) {
			if((int)charArr[i] >= 48 && (int)charArr[i] <= 57){
				sb.append(charArr[i]);
			}else{
				sb = new StringBuffer();
			}
		}
		System.out.println(sb.toString());
		
		
		
		
	}
}
