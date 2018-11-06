/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SignKeyGenerator.java
 * Author:   12073942
 * Date:     2013-11-5 下午2:28:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.sign;

import java.security.SecureRandom;

/**
 * 签名密钥生成器
 * 
 * @author 12073942
 */
public class SignKeyGenerator {

    public static void main(String[] args) {

        // 产生128位密钥
        SecureRandom sr = new SecureRandom();
        byte[] bs = new byte[64];
        sr.nextBytes(bs);
        System.out.println(bytes2Hexstr(bs));

    }

    /**
     * 
     * 字节转十六进制字符串<BR>
     * 输出字符集合为[0-9a-f]<BR>
     * 
     * @param bytes 字节
     * @return 十六进制字符串
     */
    private static String bytes2Hexstr(byte[] bytes) {
        char[] chs = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            chs[2 * i] = "0123456789abcdef".charAt(bytes[i] >>> 4 & 0x0F);
            chs[2 * i + 1] = "0123456789abcdef".charAt(bytes[i] & 0x0F);
        }
        return String.valueOf(chs);
    }
}
