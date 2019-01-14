package com.huateng.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.Ignore;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.cardmanagement.dto.CardBalanceDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.ReDTOs;
import com.huateng.framework.webservice.ManageServiceTestImpl;
import com.huateng.test.BaseTest;
import com.huateng.univer.system.user.UserAction;
import com.opensymphony.xwork2.ActionProxy;

/**
 * 管理平台前台部分JUnit测试相关解释:
 * 
 * 默认BaseTest中会提供测试相关API和挡板service, 具体: @see BaseTest
 * 
 * 测试方法: @see {@link #actionTest()} 测试中如同web访问一样.
 * 
 * 对于spring的配置文件.spring的test-plugin 默认会去加载applicationContext.xml
 * 而web.xml中.引入方法是/spring/*.xml 又因为前台访问后台需要调用WebService 部分配置不适合JUnit
 * 所以部分配置文件被剔除.并把applicationContext.xml内容清空了. @see BaseTest
 * 
 * 由于上述原因.当修改过spring配置文件的时候.需要同时修改MAIN和TEST下的spring配置文件.
 * 
 * Struts2配置文件.是由 @see {@link BaseTest#getContextLocations()} 这个方法提供spring配置的
 * 对struts.xml文件没有影响.
 * 
 * 一般测试类建立. 针对前台测试.可以以包为单位.
 * 
 * 
 * @author wpf
 */
// 注解Ignore表示忽略这个测试
@Ignore
public class readme extends BaseTest {
	private Logger logger = Logger.getLogger(readme.class);
	/**
	 * 这是一个struts2测试的样例
	 * 
	 * 可以查看:
	 * 
	 * @see ReDTOs#getDTO(Class)
	 * @see ReDTOs#getObj(Object)
	 * 
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
	public void actionTest() {
		// 表单属性用request方式传入
		request.setParameter("intpuName:obj.attribute", "1234567890");
		// 或者使用
		// dispatcherInitParams.put("cardManagementDTO.transferOutCard",
		// "1234567890123456789");

		// 返回一个Action代理. url为访问地址.以卡片查询为例:
		// ActionProxy proxy = getActionProxy("/cardManage/cardQuery")
		ActionProxy proxy = getActionProxy("url");
		// 提供action中使用的Session的Atrribute, 当getActionProxy执行后,才可以拿到当前Context.
		Map<String, Object> session = new HashMap<String, Object>();
		session.put("balanceDtoList", new ArrayList<CardBalanceDTO>());
		ServletActionContext.getContext().setSession(session);

		// 需要给挡板service提供返回DTO对象,
		// 可根据情况给返回DTO对象加入相关的属性完成action后续流程.
		BaseDTO dto = (BaseDTO) ReDTOs.getDTO(BaseDTO.class);
		dto.setLoginUserId("10000");

		// 需要给挡板service提供返回OperationResult 可以根据情况加入返回状态
		OperationResult obj = ReDTOs.getObj(dto);
		// 如故意失败:
		// obj.setTxnstate("0");
		// obj.setErrMessage("故意失败");

		// 根据action中所带有的sendService("key",obj)方法 对应加入key和返回DTO
		// 以卡片查询为例:
		/**
		 * cardManagementDTO = (CardManagementDTO) sendService(
		 * ConstCode.CARDMANAGEMENT_QUERY, cardManagementDTO).getDetailvo();
		 * 
		 * Map<String, Object> map = ManageServiceTestImpl.getMap();
		 * map.put(ConstCode.CARDMANAGEMENT_QUERY, obj);
		 */
		Map<String, Object> map = ManageServiceTestImpl.getMap();
		map.put("key", obj);

		// 返回一个action实例
		BaseAction baseAction = (BaseAction) proxy.getAction();
		String result = null;
		try {
			// 执行action方法. 此时. 会先进入方法对应的validation.xml
			// 之后再进入validate方法最后进入action方法
			result = proxy.execute();
		} catch (Exception e) {
			// 当proxy执行出错时. 说明测试本身存在错误.则判定测试失败
			fail("readme测试类. 失败说明:xxx  ,  失败原因:" + e.getMessage());
			this.logger.error(e.getMessage());
		}

		// 返回当前Action的字段错误. 主要产生原因是validation.xml和validate方法
		// 一般不会手工加入fieldErrors
		Map<String, List<String>> filedErrors = baseAction.getFieldErrors();

		// 返回action中的严重错误.一般是baseAction中的sendService方法抛出
		// 个别Action中也会加入这个错误
		Collection<String> actionErrors = baseAction.getActionErrors();

		// 返回action的提示信息. 例如: 操作成功
		Collection<String> actionmessage = baseAction.getActionMessages();

		// 可以做如下断言 (其中最少要求需要断言result):
		// 正常情况下.字段应该全部OK不可以有字段错误
		assertTrue("", filedErrors.size() < 1);

		// 正常情况下. 不应该有action方法错误.
		assertTrue("", actionErrors.size() < 1);

		// 个别Action会有操作成功信息.
		assertEquals("这里写JUnit日志信息.", actionmessage.remove("成功信息"), true);

		// action返回 默认是正常success错误是input
		assertEquals("这里写JUnit日志信息.", result, "success");

		// response ,request ,servletContext ,pageContext 与在JSP页面上使用基本一样.

	}
}
