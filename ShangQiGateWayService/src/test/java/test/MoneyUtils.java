/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MoneyUtils.java
 * Author:   秦伟
 * Date:     2013-12-9 下午7:27:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MoneyUtils {
    /**
     * 分转换为元.
     * 
     * @param fen 分
     * @return 元
     */
    public static String fromFenToYuan(final String fen) {
        String yuan = "";
        final int MULTIPLIER = 100;
        Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
        Matcher matcher = pattern.matcher(fen);
        if (matcher.matches()) {
            yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
        } else {
            System.out.println("参数格式不正确!");
        }
        return yuan;
    }

    /**
     * 元转换为分.
     * 
     * @param yuan 元
     * @return 分
     * @throws  PatternSyntaxException ConversionException
     */
    public static String fromYuanToFen(final String yuan) {
        String fen = "";
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?{1}");
        Matcher matcher = pattern.matcher(yuan);
        if (matcher.matches()) {
            try {
                NumberFormat format = NumberFormat.getInstance();
                Number number = format.parse(yuan);
                double temp = number.doubleValue() * 100.0;
                // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
                format.setGroupingUsed(false);
                // 设置返回数的小数部分所允许的最大位数
                format.setMaximumFractionDigits(0);
                fen = format.format(temp);
            } catch (ParseException e) {
                //e.printStackTrace();
                throw new ConversionException(e);
            }
        } else {
            System.out.println("参数格式不正确!");
            throw new ConversionException("参数格式不正确: [" + yuan + "]" );
        }
        return fen;

    }
}
