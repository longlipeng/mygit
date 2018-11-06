/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: RandomUtils.java
 * Author:   unknown
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 
 * 随机相关工具
 * 
 * @author unknown
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RandomUtils {

    /**
     * 10个数字+26个小写字母+26个大写字母
     */
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 26个小写字母+26个大写字母
     */
    public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 10个数字
     */
    public static final String NUMBER_CHAR = "0123456789";

    /**
     * UUID长度：48
     */
    private static final int UUID_LENGTH_48 = 48;

    /**
     * 
     * 生成UUID（大写32位）
     * 
     * @return 32位大写UUID
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 
     * 生成UUID（小写48位）
     * 
     * @return 48位小写UUID
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generate48UUID() {
        // 拼接2个UUID
        String str = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        // 去除连接符后截取48位
        return str.replace("-", "").substring(0, UUID_LENGTH_48);
    }

    /**
     * 
     * 返回一个定长的随机字符串(只包含数字)
     * 
     * @param length 随机字符串长度
     * @return 随机字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     * 
     * @param length 随机字符串长度
     * @return 随机字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     * 
     * @param length 随机字符串长度
     * @return 随机字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     * 
     * @param length 随机字符串长度
     * @return 随机字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 
     * 生成一个定长的纯0字符串
     * 
     * @param length 字符串长度
     * @return 纯0字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 
     * 根据数字生成一个定长的字符串，长度不够前面补0
     * 
     * @param num 数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 
     * 根据数字生成一个定长的字符串，长度不够前面补0
     * 
     * @param num 数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    // public static void main(String[] args) {
    //
    // System.out.println(generateUUID());
    // System.out.println(generate48UUID());
    //
    // System.out.println(generateString(6));
    // System.out.println(generateMixString(15));
    // System.out.println(generateLowerString(15));
    // System.out.println(generateUpperString(15));
    // System.out.println(generateZeroString(15));
    // System.out.println(toFixdLengthString(123, 15));
    // System.out.println(toFixdLengthString(123L, 15));
    // }

}