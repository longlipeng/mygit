package com.huateng.univer.hessian.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.constant.Const;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.servlet.SystemInfoBO;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.hessian.ManageHessianInvoke;
import com.huateng.univer.synch.ManageHessianService;

/**
 * 后台webService类,所有前台请求均通过该类与后台进行通信
 * 
 * 该类根据交易代码通过反射调用一个 spring定义的bean的一个方法来完成.
 * 
 * @author lfr
 * 
 */
public class ManageHessianServiceImpl implements ManageHessianService {

	Logger logger = Logger.getLogger(ManageHessianServiceImpl.class);

	private DataSourceTransactionManager transactionManager;

	private SystemInfoBO systemInfoBO;

	private ManageHessianInvoke manageHessianInvoke;

	public OperationResult sendService(String txnCode, Object dto) {
		OperationResult or = new OperationResult();
		Object obj = null;
		try {
			String code = null;
			String[] strs = txnCode.split(ManageHessianInvoke.METHOD_SEPARATOR);
			code = strs[0];

			//记录日志
		    logger.info(code);
			logger.info(ReflectionUtil.toString(dto));
			// 得到当前用户id
			String userId;
			if (strs.length == 1 || "".equals(strs[1]))
				userId = "0";
			else
				userId = strs[1];
			// 添加事务处理
//			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//			TransactionStatus status = transactionManager.getTransaction(def);
			// 获取日志内容
			String txnContent = ReflectionUtil.toString(dto);
			if (txnContent.length() > 4000) {
				txnContent = txnContent.substring(0, 3980);
			}
			try {
				obj = manageHessianInvoke.excuteWebService(code, dto);
				// 添加日志
				systemInfoBO.logSuccess(code, userId, txnContent);

//				transactionManager.commit(status);
				or.setTxnstate(Const.RETURN_SUCC);
			} catch (BizServiceException e) {
//				transactionManager.rollback(status);
				//添加日志
				systemInfoBO.logError(code, userId, txnContent, e
							.getErrorMessage());
				throw e;
			} catch (Exception e) {
//                transactionManager.rollback(status);
                this.logger.error(e.getMessage());
				systemInfoBO.logError(code, userId, txnContent, e
							.getMessage());
                throw e;
			}
		} catch (BizServiceException be) {
			logger.error(be);
			or.setTxncause(be.getErrors());
			or.setErrMessage(be.getErrorMessage());
			or.setTxnstate(Const.RETURN_FAILED);
		} catch (Exception ex) {
			logger.error(ex);
			or.setErrMessage("请求处理异常");
			or.setTxnstate(Const.RETURN_FAILED);
		} finally {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			or.setTxndate(sdf.format(new Date()));
			or.setDetailvo(obj);
		}

		return or;
	}

	public ManageHessianInvoke getManageHessianInvoke() {
		return manageHessianInvoke;
	}

	public void setManageHessianInvoke(ManageHessianInvoke manageHessianInvoke) {
		this.manageHessianInvoke = manageHessianInvoke;
	}

	public SystemInfoBO getSystemInfoBO() {
		return systemInfoBO;
	}

	public void setSystemInfoBO(SystemInfoBO systemInfoBO) {
		this.systemInfoBO = systemInfoBO;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
