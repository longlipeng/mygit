/**
 * Classname SqlCharsetTest.java
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
 *		htd033		2012-11-28
 * =============================================================================
 */

package com.huateng.univer.method.sqlcharsettest;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import com.huateng.framework.ibatis.dao.DictInfoDAO;
import com.huateng.framework.ibatis.model.DictInfo;
import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;

/**
 * @author wpf
 * 
 *         测试UTF-8环境中.字符串长度与数据库字段长度.
 * 
 *         实际结果:
 * 
 *         需要使用getBytes得到实际占用长度
 */
@MakeTestType(testType = { "DatabaseCommonTest" })
public class SqlCharsetTest extends BaseTest {
	private Logger logger = Logger.getLogger(SqlCharsetTest.class);
	@Resource
	private DictInfoDAO dictInfoDAO;

	@Test
	@Rollback(true)
	public void batchFileTest() {
		// try {
		DictInfo dictInfo = new DictInfo();

		dictInfo.setDictCode("1");
		dictInfo.setDictId("90001212");
		dictInfo.setDictName("1");
		dictInfo.setDictShortName("1");
		dictInfo.setDictState("1");
		dictInfo.setDictTypeCode("1");

		dictInfoDAO.insert(dictInfo);
		try {
			String str1 = "中文中文中文中文1212121d";

			logger.info("str1 length : " + str1.length());

			logger.info("str1 byte length : "
					+ str1.getBytes("utf-8").length);
			dictInfo.setDictId("90001213");
			dictInfo.setDictTypeCode(str1);
			dictInfoDAO.insert(dictInfo);
		} catch (Exception e) {

			this.logger.error(e.getMessage());
		}

		try {
			String str1 = "中文中文中文中文1212121d11";

			logger.info("str1 length : " + str1.length());

			logger.info("str1 byte length : "
					+ str1.getBytes("utf-8").length);
			dictInfo.setDictId("90001214");
			dictInfo.setDictTypeCode(str1);
			dictInfoDAO.insert(dictInfo);
		} catch (Exception e) {

			this.logger.error(e.getMessage());

			logger.info("数据越界");
		}

		try {
			String str1 = "1212121d11中文中文中文中文中文";

			byte[] bs = str1.getBytes("utf-8");
			// 当截取的时候最后一位是中文的时候.依然会发生越界问题.为保证安全比最大长度少2.
			byte[] bb = new byte[30];

			System.arraycopy(bs, 0, bb, 0, 30);

			String str = new String(bb);

			logger.info("str length : " + str.length());

			logger.info("str byte length : "
					+ str.getBytes("utf-8").length);
			logger.info("this str =" + str);
			dictInfo.setDictId("90001215");
			dictInfo.setDictTypeCode(str);
			dictInfoDAO.insert(dictInfo);
		} catch (Exception e) {

			this.logger.error(e.getMessage());

			logger.info("数据越界");
		}

		// } catch (BizServiceException e) {
		// 
		// fail(e.getErrorMessage());
		// this.logger.error(e.getMessage());
		// }
	}

}
