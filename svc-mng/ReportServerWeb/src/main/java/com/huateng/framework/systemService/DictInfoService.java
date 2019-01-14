package com.huateng.framework.systemService;

import java.util.Map;

public interface DictInfoService {

	/**
	 * 
	 * @return Map<机构key,Map<字典key,Map<key,value>>>
	 */
	public Map<String, Map<String, Map<String, String>>> getAllDictInfo();

	public Map<String, Map<String, Map<String, String>>> getDictInfo();
}
