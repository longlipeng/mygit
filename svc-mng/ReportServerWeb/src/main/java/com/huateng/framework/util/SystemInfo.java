package com.huateng.framework.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.framework.systemService.DictInfoService;

public class SystemInfo {

	private static final String NULL_KEY = "";
	private static final String FALSE = "false";
	private static Logger logger = Logger.getLogger(SystemInfo.class);
	/***
	 * 实体数据字典
	 */
	private static Map<String, Map<String, Map<String, String>>> entityDictInfo;

	/***
	 * 实体系统参数
	 */
	private static Map<String, Map<String, String>> entityParameters;

	public static Map<String, Map<String, Map<String, String>>> getEntityDictInfo() {
		return entityDictInfo;
	}

	public static void setEntityDictInfo(
			Map<String, Map<String, Map<String, String>>> entityDictInfo) {
		SystemInfo.entityDictInfo = entityDictInfo;
	}

	public static Map<String, Map<String, String>> getEntityParameters() {
		return entityParameters;
	}

	public static void setEntityParameters(
			Map<String, Map<String, String>> entityParameters) {
		SystemInfo.entityParameters = entityParameters;
	}
	@Autowired
	private DictInfoService dictInfoService;


	public DictInfoService getDictInfoService() {
		return dictInfoService;
	}

	public void setDictInfoService(DictInfoService dictInfoService) {
		this.dictInfoService = dictInfoService;
	}
	
	private static SystemInfo systemInfo;
	@PostConstruct
	 public void init()

	    {

		    systemInfo = this;

		    systemInfo.dictInfoService = this.dictInfoService;

	    }

	/**
	 * 格式化list
	 * 
	 * @param entityId
	 * @param list
	 *            参数集合的泛型必须为map或者dto类型.对于替换属性必需为String类型
	 * @param fields
	 *            需要替换的属性名,如卡片类型, cardType; fields为不定项参数. 必需为偶数个
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<?> dictCodeformat(String entityId, List<?> list,
			String... fields) {
		try {
			if (fields.length % 2 != 0) {
				return list;
			}
			Object o = list.get(0);
			if (o instanceof Map) {
				for (Object obj : list) {
					dictFieldMapFormat(obj, entityId, fields);
				}
			} else {
				for (Object obj : list) {
					dictFieldFormat(obj, entityId, fields);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	/**
	 * 该方法用来替换对象内对应属性的值
	 * 
	 * @param obj
	 * @param entityId
	 * @param fields
	 *            需要替换的属性名,如卡片类型, cardType; fields为不定项参数. 必需为偶数个
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	private static Object dictFieldFormat(Object obj, String entityId,
			String... fields) throws Exception {
		Class clazz = obj.getClass();

		for (int i = 0; i < fields.length; i = i + 2) {
			String Name = StringUtils.abc2Abc(fields[i + 1]);
			String setter = "set" + Name;
			String getter = "get" + Name;
			Method methodGet = clazz.getDeclaredMethod(getter);
			Method methodSet = clazz.getDeclaredMethod(setter, String.class);
			String value = dictCodeInitDictInfo((String) methodGet.invoke(obj), fields[i],
					entityId);

			methodSet.invoke(obj, value);
		}

		return obj;
	}

	/**
	 * 该方法用来替换对象内对应属性的值
	 * 
	 * @param obj
	 *            为Map
	 * @param entityId
	 * @param fields
	 *            需要替换的属性名,如卡片类型, cardType; fields为不定项参数. 必需为偶数个
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Object dictFieldMapFormat(Object obj, String entityId,
			String... fields) throws Exception {
		Map<String, String> map = (Map<String, String>) obj;
		for (int i = 0; i < fields.length; i = i + 2) {
			String value = dictCodeInitDictInfo(map.get(fields[i + 1]), fields[i], entityId);
			map.put(fields[1], value);
		}
		return obj;
	}

	/**
	 * 系统字典使用
	 * 
	 * @param type
	 * @param list
	 * @param fields
	 * @return
	 */
	public static List<?> dictCodeformat(List<?> list, String... fields) {
		return dictCodeformat(NULL_KEY, list, fields);
	}

	/**
	 * 
	 * 替换机构单一字典对象
	 * 如果替换失败则更新机构字典信息
	 * 
	 * @param key
	 * @param type
	 * @param entityId
	 * @return
	 */
	public static String dictCodeInitDictInfo(String key, String type,
			String entityId) {
		String dict = dictCode(key, type, entityId);
		// 更新机构字典信息
		if (FALSE.equals(dict)) {
			DictInfoService dictInfoServiceStatic = systemInfo.getDictInfoService();;		
			Map<String, Map<String, Map<String, String>>> entityDictInfo = dictInfoServiceStatic
					.getAllDictInfo();
			SystemInfo.setEntityDictInfo(entityDictInfo);
			dict = dictCode(key, type, entityId);
			if (FALSE.equals(dict)) {
				return key;
			}else{
				return dict;
			}
		}
		return dict;
	}
	
	
	/**
	 * 替换机构单一字典对象
	 * 
	 * @param key
	 * @param type
	 * @param entityId
	 * @return
	 */
	public static String dictCode(String key, String type, String entityId) {
		Map<String, Map<String, String>> typeMap = entityDictInfo.get(entityId);
		if (typeMap == null) {
			return FALSE;
		}
		Map<String, String> codeMap = typeMap.get(type);
		if (codeMap == null) {
			return FALSE;
		}
		String dict = codeMap.get(key);
		if (dict == null) {
			return FALSE;
		} else {
			return dict;
		}
	}

	/**
	 * 替换系统单一字典对象
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public static String dictCode(String key, String type) {
		return dictCode(key, type, NULL_KEY);
	}

	/**
	 * 替换系统参数
	 * 
	 * @param entityId
	 * @param list
	 *            参数集合的泛型必须为map或者dto类型.对于替换属性必需为String类型
	 * @param fields
	 *            需要替换的属性名,如卡片类型, cardType; fields为不定项参数.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<?> parametersformat(String entityId, List<?> list,
			String... fields) {
		try {
			Object o = list.get(0);
			if (o instanceof Map) {
				for (Object obj : list) {
					parametersFieldMapFormat(obj, entityId, fields);
				}
			} else {
				for (Object obj : list) {
					parametersFieldFormat(obj, entityId, fields);
				}
			}
		} catch (Exception e){
				logger.error(e.getMessage());
		}

		return list;
	}

	/**
	 * 该方法用来替换对象内对应属性的值
	 * 
	 * @param obj
	 * @param entityId
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	private static Object parametersFieldFormat(Object obj, String entityId,
			String... fields) throws Exception {
		Class clazz = obj.getClass();
		for (String field : fields) {
			String Name = StringUtils.abc2Abc(field);
			String setter = "set" + Name;
			String getter = "get" + Name;
			Method methodGet = clazz.getDeclaredMethod(getter);
			Method methodSet = clazz.getDeclaredMethod(setter, String.class);
			String value = parametersCode((String) methodGet.invoke(obj),
					entityId);
			methodSet.invoke(obj, value);
		}
		return obj;
	}

	/**
	 * 该方法用来替换对象内对应属性的值
	 * 
	 * @param obj
	 * @param entityId
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Object parametersFieldMapFormat(Object obj, String entityId,
			String... fields) throws Exception {
		Map<String, String> map = (Map<String, String>) obj;
		for (String field : fields) {
			String value = parametersCode(map.get(field), entityId);
			map.put(field, value);
		}
		return obj;
	}

	/**
	 * 
	 * @param list
	 *            参数集合的泛型必须为map或者dto类型.对于替换属性必需为String类型
	 * @param fields
	 *            需要替换的属性名,如卡片类型, cardType; fields为不定项参数.
	 * @return
	 */
	public static List<?> parametersformat(List<?> list, String... fields) {
		return parametersformat(NULL_KEY, list, fields);
	}

	public static String parametersCode(String type, String entityId) {
		Map<String, String> parametersMap = entityParameters.get(entityId);
		if (parametersMap == null) {
			return type;
		}
		String parameter = parametersMap.get(type);
		if (parameter == null) {
			return type;
		} else {
			return parameter;
		}
	}

	public static String parametersCode(String type) {
		return parametersCode(type, NULL_KEY);
	}
	
}
