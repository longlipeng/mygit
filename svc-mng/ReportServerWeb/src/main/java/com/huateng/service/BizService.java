/**
 * Classname BizService.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2012-10-17
 * =============================================================================
 */

package com.huateng.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author wpf
 * 
 */
public interface BizService {
	/**
	 * 内容提供者
	 * 
	 * @param jsonDto
	 *            jsonDto可以通过JSONObject.toBean(jsonDto,class)
	 *            得到对应的dto对象或map对象.
	 * @return
	 */
	public List<Object> getList(JSONObject jsonDto);

	/**
	 * 
	 * @param map
	 *            参数map 如果需要额外的静态字符,或者类似加和操作等特殊计算结果等
	 * @return 固定返回当前参数map
	 */
	public Map<String, String> setParamters(Map<String, String> map);
}
