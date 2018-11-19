/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SignUtil.java
 * Author:   12073942
 * Date:     2013-10-18 下午3:11:30
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名工具类
 * 
 * @author LEO
 */
public class SignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 
     * 使用指定密钥对指定的参数签名<BR>
     * "参数名=参数值"作为一组<BR>
     * 按参数名a-z顺序用'&'连接<BR>
     * 所得结果作为待签名串
     * 
     * @param params 参数键值对
     * @param key 拼接的密钥
     * @return 签名结果
     */
    public static String sign(Map<String, String> params, String key) {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<String>(params.size());
        paramNames.addAll(params.keySet());
        Collections.sort(paramNames);
        for (String paramName : paramNames) {
            sb.append('&').append(paramName).append('=').append(params.get(paramName));
        }
        if (sb.length() < 1) {
            return sign("", key);
        }
        return sign(sb.substring(1), key);
    }

    /**
     * 
     * 使用指定密钥对指定的串签名<BR>
     * 密钥拼接在串尾计算MD5摘要<BR>
     * 字符集为UTF-8
     * 
     * @param str 待签名串
     * @param key 拼接的密钥
     * @return 签名结果
     */
    public static String sign(String str, String key) {
        LOGGER.trace("待签名串为{}", str);
        String union = str + key;
        byte[] digest = getMD5Digest(union.getBytes(Charset.forName("UTF-8")));
        String md = bytes2Hexstr(digest);
        LOGGER.trace("签名结果为{}", md);
        return md;
    }

    /**
     * 
     * 获取MD5摘要
     * 
     * @param data 源字节
     * @return MD5摘要字节
     */
    private static byte[] getMD5Digest(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("不应发生的错误：" + e);
            throw new RuntimeException(e);
        }
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
