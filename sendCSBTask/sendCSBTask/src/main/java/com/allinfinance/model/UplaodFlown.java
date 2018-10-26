package com.allinfinance.model;
/**
 * 上传文件流水对象
 * @author taojiajun
 *
 */
public class UplaodFlown {
private static String flown="";//上传文件的流水号  每天上传文件前 给它置空
public static String getFlown() {
	return flown;
}

public static void setFlown(String flown) {
	UplaodFlown.flown = flown;
}

}
