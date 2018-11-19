package com.allinfinance.prepay.utils;

import java.math.BigDecimal;

public class AmountTransformUtil
{
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	public static String toString(BigDecimal amt)
	{
		return amt.multiply(HUNDRED).setScale(0, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static BigDecimal toBigDecimal(String amt)
	{
		return new BigDecimal(amt).divide(HUNDRED).setScale(2, BigDecimal.ROUND_FLOOR);
	}
	
	public static void main(String[] args)
	{
		System.out.println(toString(new BigDecimal(123.45)));
		System.out.println(toBigDecimal("12345632"));
	}
}
