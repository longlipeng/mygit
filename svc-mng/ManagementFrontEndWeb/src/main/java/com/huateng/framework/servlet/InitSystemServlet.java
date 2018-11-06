package com.huateng.framework.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.allinfinance.chargebaltxn.dao.ChargeBalTxnDao;
import com.allinfinance.univer.system.sysparam.dto.TransTypeDTO;
import com.huateng.framework.ibatis.dao.TransTypeDAO;
import com.huateng.framework.ibatis.model.TransType;
import com.huateng.framework.ibatis.model.TransTypeExample;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionPool;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.frameworkUtil.Const;
import com.huateng.hstserver.gatewayService.Java2CCommonServiceImpl;

public class InitSystemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383330395840866359L;
	private Logger logger = Logger.getLogger(InitSystemServlet.class);
@Override
	public void init() throws ServletException {

		try {
			SystemInfoBO systemInfoBO = (SystemInfoBO) WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext())
					.getBean("systemInfoBO");
			systemInfoBO.initSystemInfo();
			
			//交易类型
			TransTypeDAO transTypeDAO =(TransTypeDAO)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("transTypeDAO");
			Map<String,String> map=new HashMap<String, String>();
			TransTypeExample transTypeExample=new TransTypeExample();
			List<String> transCodeList = new ArrayList<String>();
			transCodeList.add("S43");
			transCodeList.add("S45");
			transCodeList.add("S46");
			transCodeList.add("S47");
			transCodeList.add("S51");
			transCodeList.add("S57");
			transCodeList.add("S58");
			transCodeList.add("S71");
			
			transCodeList.add("S42");
			transCodeList.add("S41");
			transCodeList.add("S49");
			transCodeList.add("S53");
			transCodeList.add("S56");
			transCodeList.add("S59");
			transCodeList.add("V62");
			transCodeList.add("G10");
			transCodeList.add("G32");
			transCodeList.add("G22");
			transCodeList.add("G33");	
			transCodeList.add("G34");
			transCodeList.add("S92");
			transCodeList.add("S60");
			transCodeList.add("S32");
			//退货申请
			transCodeList.add("S30");
			//消费撤销冲正
			transCodeList.add("R52");
			//消费冲正
			transCodeList.add("R22");
			
			//支付网关消费登记
			transCodeList.add("G12");
			//卡片信息设置 
			transCodeList.add("G01");
			//支付网关密码修改
			transCodeList.add("G02");
			//单笔管理平台充值
			transCodeList.add("M32");
			//预授权冲正
			transCodeList.add("R10");
			//预授权完成冲正
			transCodeList.add("R20");
			//预授权完成撤销冲正
			transCodeList.add("R50");
			//单笔充值撤销冲正
			transCodeList.add("R62");
			//预授权撤销冲正
			transCodeList.add("R40");
			//转出账户
			transCodeList.add("M34");
			//转入账户
			transCodeList.add("M33");
			//批量激活
			transCodeList.add("M10");
			//POS消费
			transCodeList.add("M22");
			//POS消费冲正
			transCodeList.add("M23");
			//单张卡验卡
			transCodeList.add("M40");
			//单张卡销毁
			transCodeList.add("M41");
			
			transTypeExample.createCriteria().andTransCodeNotIn(transCodeList);
			List<TransType> transTypes=transTypeDAO.selectByExample(transTypeExample);
			for(int i=0;i<transTypes.size();i++){
				TransType transType=transTypes.get(i);
				TransTypeDTO transTypeDTO=new TransTypeDTO();
				ReflectionUtil.copyProperties(transType, transTypeDTO);
				map.put(transTypeDTO.getTransCode(), transTypeDTO.getTransName());
			}
			Const.setMap(map);
			this.getServletContext().setAttribute("transTypeMap", map);
			
			//初始化到C端的通信链路		
			//ConnectionPool.getInstance().initialConnection(true, true, true);	
			ConnectionPool.getInstance().initialConnection(true, true, false);	
			
			// 获取加密机密钥
			boolean isSuccess = this.testHSM();
			if(!isSuccess){
				logger.info("############### 测试加密机连接失败! #####################");
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	/**
	 * 系统启动时获取加密机密钥
	 */
	private boolean initialPinKey(){
		logger.info("#################### CHECK IN ####################");				
		Java2CCommonServiceImpl java2CBusinessService =(Java2CCommonServiceImpl)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("java2CCommonService");
		try {
			return java2CBusinessService.initialPinKey();
		} catch (BizServiceException e) {
			return false;
		}
	}
	
	/*
	 * 测试江南科友加密机连接
	 */
	public boolean testHSM(){
		logger.info("#################### CHECK IN ####################");				
		Java2CCommonServiceImpl java2CBusinessService =(Java2CCommonServiceImpl)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("java2CCommonService");
		try {
			return java2CBusinessService.testHSM();
		} catch (BizServiceException e) {
			return false;
		}
	}
	
}
