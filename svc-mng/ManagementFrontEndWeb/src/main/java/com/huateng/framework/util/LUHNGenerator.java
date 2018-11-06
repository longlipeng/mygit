package com.huateng.framework.util;

public class LUHNGenerator {

    public static String generate(String strCardNo) {
        byte[] bytes = strCardNo.getBytes();
        boolean isOdd = bytes.length % 2 == 1;
        int b = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if ((i % 2 == 1) != isOdd) {
                b += ((bytes[i] - 48) * 2) % 10;
                b += ((bytes[i] - 48) * 2) / 10;
            } else {
                b += (bytes[i] - 48);
            }
        }
        int c = 10 - b % 10;
        if (c == 10) {
            c = 0;
        }
        return strCardNo + c;
    }
    
/*    public static void main(String args[]){
    	logger.info(generate("000030892"));
    }*/
}
