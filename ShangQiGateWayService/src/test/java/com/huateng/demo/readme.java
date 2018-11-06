package com.huateng.demo;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.cardmanage.biz.service.CardManageService;

/**
 * 管理平台后台部分JUnit测试相关解释:
 * 
 * BaseTest中我们将测试的事务.默认为启动,并把级别设置为回滚.方便单一方法测试.对于流程测试方法.
 * 可以使用@Rollback(false)注解 进行提交.对于不使用事务的方法 .可以使用 @NotTransactional 注解.
 * 
 * @Resource spring的IOC注解写法
 * 
 *           后台测试.应分为包为单位 , 以流程为单位两种.
 * 
 * @author wpf
 */
// 注解Ignore表示忽略这个测试
@Ignore
// MakeTestType表示没有使用当前测试例子
// @MakeTestType()
// 如已经加入某个测试则使用:  @MakeTestType(testType={"test1","test2"})
// 为了不让FindNotMakeTestClass找到(除Test框架代码.请不要这样做)写成如下:
@MakeTestType(testType={"MakeTestType"})
public class readme extends BaseTest {
	private Logger logger = Logger.getLogger(readme.class);
	@Resource
	private CardManageService cardManageService;

	/**
	 * 可以查看:
	 * 
	 * @see ReDTOs#getDTO(Class)
	 * @see ReDTOs#getObj(Object)
	 * 
	 *      断言:
	 * @see Assert#assertTrue()
	 * @see Assert#assertEquals()
	 * @see Assert#assertNull()
	 * @see Assert#assertNotNull()
	 * @see Assert#assertSame()
	 * @see Assert#assertNotSame()
	 * @see Assert#fail()
	 * @see Assert#failSame()
	 * @see Assert#failNotSame()
	 * @see Assert#failNotEquals()
	 */
	@Test
	// 这个注解可以省略
	@Rollback(true)
	public void serviceTest() {
		// 以卡片操作 插入为例.
		CardManagementDTO dto = (CardManagementDTO) ReDTOs
				.getDTO(CardManagementDTO.class);
		// 修改其中一部分数据.
		dto.setCardValidityPeriod("2014-10-10");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setLoginUserId("10000");
		dto.setFlag("1");

		try {
			// 执行service方法.
			cardManageService.insertCardManagement(dto);
			// 如果是查询方法
			// DTO dto = service.method(xx);
			// 测试dto中的属性.是否正常.
		} catch (BizServiceException e) {
			// 发生异常.则认为测试失败.
			fail(e.getErrorMessage());
			this.logger.error(e.getMessage());
		}
	}
}
