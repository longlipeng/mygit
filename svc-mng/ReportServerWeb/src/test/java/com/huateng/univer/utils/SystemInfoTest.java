/**
 * Classname SystemInfoTest.java
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
 *		htd033		2012-11-6
 * =============================================================================
 */

package com.huateng.univer.utils;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.systemService.SystemInfoService;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.SystemInfo;
import com.huateng.test.BaseTest;

/**
 * @author htd033
 * 
 */
@MakeTestType(testType={"ServiceTest"})
public class SystemInfoTest extends BaseTest {
	private static Logger logger = Logger.getLogger(SystemInfoTest.class);
	@Resource(name = "systemInfoService")
	private SystemInfoService systemInfoService;

	@Test
	public void loadTest() {
		try {
			systemInfoService.initSystemInfo();
		} catch (BizServiceException e) {
			fail("init reload failed  :" + e.getErrorMessage());
			logger.error(e.getMessage());
		}
		if (SystemInfo.getEntityDictInfo() == null)
			fail("init load failed");
	}

	@Test
	public void formatMapTest() {
		try {
			List<Map<String, String>> list = forgeMapData();
			// system dict test
			SystemInfo.dictCodeformat("10000001", list, "105", "test");
			Map<String, String> map = list.get(0);
			if (map.get("test") == null || map.get("test").equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(map.get("test"), "缺省");
			}

			// entity dict test
			list = forgeMapData();
			SystemInfo.dictCodeformat(list, "141", "test");
			map = list.get(0);
			if (map.get("test") == null || map.get("test").equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(map.get("test"), "未审核");
			}

			// system parameters
			list = forgeMapData();
			SystemInfo.parametersformat(list, "test");
			map = list.get(list.size() - 1);
			if (map.get("test") == null
					|| map.get("test").equals("EXPORT_DATA_MAXIMUM")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(map.get("test"), "1000");
			}
			// entity parameters
			list = forgeMapData();
			SystemInfo.parametersformat("10000005", list, "test");
			map = list.get(list.size() - 1);
			if (map.get("test") == null || map.get("test").equals("1")) {
				logger.info(map.get("test"));
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(map.get("test"), "1000");
			}
		} catch (Exception e){ 
				logger.error(e.getMessage());
			fail("formatTest failed : " + e.getMessage());
		}

	}

	@Test
	public void formatObjTest() {
		try {
			List<TO> list = forgeObjData();
			// system dict test
			SystemInfo.dictCodeformat("10000001", list, "105", "test");
			TO to = list.get(0);
			if (to.getTest() == null || to.getTest().equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(to.getTest(), "缺省");
			}

			// entity dict test
			list = forgeObjData();
			SystemInfo.dictCodeformat(list, "141", "test");
			to = list.get(0);
			if (to.getTest() == null || to.getTest().equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(to.getTest(), "未审核");
			}

			// system parameters
			list = forgeObjData();
			SystemInfo.parametersformat(list, "test");
			to = list.get(list.size() - 1);
			if (to.getTest() == null || to.getTest().equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(to.getTest(), "1000");
			}

			// entity parameters
			list = forgeObjData();
			SystemInfo.parametersformat("10000005", list, "test");
			to = list.get(list.size() - 1);
			if (to.getTest() == null || to.getTest().equals("1")) {
				fail("codeFormatTest failed :   ");
			} else {
				assertEquals(to.getTest(), "1000");
			}

		} catch (Exception e){
				logger.error(e.getMessage());
			fail("formatTest failed : " + e.getMessage());
		}

	}

	public List<Map<String, String>> forgeMapData() {
		// 假造数据list
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 1; i < 7; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("test", i + "");
			list.add(map);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "EXPORT_DATA_MAXIMUM");
		list.add(map);
		return list;
	}

	public List<TO> forgeObjData() {
		// 假造数据list
		List<TO> list = new ArrayList<TO>();
		for (int i = 1; i < 7; i++) {
			TO t = new TO();
			t.setTest("" + i);
			list.add(t);
		}
		TO t = new TO();
		t.setTest("EXPORT_DATA_MAXIMUM");
		list.add(t);
		return list;
	}

	// private void printObject(List<TO> list) {
	// for (TO to : list) {
	// logger.info("+++++++    " + to.getTest());
	// }
	// }

}
