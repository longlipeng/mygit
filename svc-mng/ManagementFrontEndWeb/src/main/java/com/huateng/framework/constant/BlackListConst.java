package com.huateng.framework.constant;

/**
 * 黑名单返回匹配
 * 
 * @author jason
 *
 */
public class BlackListConst {
	// 匹配上黑名单
	public static int RESULT_BlACK = 1;
	// 匹配上灰名单
	public static int RESULT_GRAY = 2;
	// 匹配上白名单
	public static int RESULT_WHITE = 2;
	// 无匹配结果
	public static int RESULT_NO = 0;
	// 匹配出错
	public static int RESULT_ERROR = -1;
	// 匹配成功
	public static int CONTENT_OK = 1;
	// 未匹配上
	public static int CONTENT_FAIL = 1;
	// 未传递参数，或者名单实体无此属性值
	public static int CONTENT_ERROR = 1;

}
