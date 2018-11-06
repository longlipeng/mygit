package com.huateng.framework.webservice;

import java.io.UnsupportedEncodingException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceRef;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.constant.Const;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.servlet.SystemInfoBO;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.XstreamDateConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * 后台webService�?所有前台请求均通过该类与后台进行通信,该类根据交易代码通过反射调用一�?
 * spring定义的bean的一个方法来完成.
 * 
 * @author jianji.dai
 * 
 */
@WebService(endpointInterface = "com.huateng.framework.webservice.ManageService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ManageServiceImpl implements ManageService {
	@WebServiceRef(wsdlLocation = "http://localhost:7001/ManagementFrontEndWeb/manageService?wsdl")
	Logger logger = Logger.getLogger(ManageServiceImpl.class);
	/**
	 * 
	 */

	protected String needPwdCheck;
	protected String needRecord;

	/**
	 * 完成后台请求的方法，该方法通过接前台传递过来的两个参数，参数类型为xml字符�?
	 * 首先通过将xml字符串转换为java对象OperationCtx和OperationRequest 然后通过获得的交易代�?
	 */
	public String sendServece(String ctx, String req) {
		/**
		 * 设置xml转换格式
		 */
		logger.info("ctx="+ctx);
		logger.info("req="+req);
		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.allinfinance.");
		OperationCtx opCtx = null;
		OperationRequest opReq = null;

		OperationResult oprRst = new OperationResult();
		XStream smRsp = new XStream();
		smRsp.registerConverter(new XstreamDateConverter());
		smRsp.alias("OperationResult", OperationResult.class);
		smRsp.aliasPackage("RSP", "com.allinfinance.");
		String rspStr = "";

		try {
			// 将xml转换为对�?
			try {
				opCtx = (OperationCtx) smReq.fromXML(ctx);
				opReq = (OperationRequest) smReq.fromXML(req);
			} catch (XStreamException xe) {
				this.logger.error(xe.getMessage());
				logger.error("xml字符转换字符出错");
				throw new BizServiceException("请求数据格式转换异常!");
			}

			String txnCode = "";
			try {
				txnCode = opCtx.getTxncode();
			} catch (Exception e) {
				logger.error("获取交易代码异常");
				throw new BizServiceException("获取交易代码异常");
			}
			String userId = "";
			if (opCtx.getOprId() == null || "".equals(opCtx.getOprId()))
				userId = "0";
			else
				userId = opCtx.getOprId();

			// 添加事务处理
			// DefaultTransactionDefinition def = new
			// DefaultTransactionDefinition();
			// def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			// TransactionStatus status =
			// transactionManager.getTransaction(def);
			String txnContent = ReflectionUtil.toString(opReq.getTxnvo());
			if (txnContent.getBytes("utf-8").length > 4000) {
				txnContent = formatStr(txnContent, 3980);
			}
			try {
				oprRst = webServiceInvoke.excuteWebService(txnCode, opReq
						.getTxnvo());
				
				systemInfoBO.logSuccess(txnCode, userId, txnContent);
				// transactionManager.commit(status);
				oprRst.setTxnstate(Const.RETURN_SUCC);
			} catch (BizServiceException e) {
				// transactionManager.rollback(status);
				systemInfoBO.logError(txnCode, userId, txnContent, e
						.getErrorMessage());
				throw e;
			}
		} catch (BizServiceException be) {
			this.logger.error(be.getMessage());
			// 如果出现异常,将异常信息设置在对象oprRst�?
			oprRst.setErrMessage((be.getErrorMessage()));
			oprRst.setTxncause(be.getErrors());
			// 设置oprRst的状态为请求失败
			oprRst.setTxnstate(Const.RETURN_FAILED);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			oprRst.setErrMessage("请求处理异常");
			oprRst.setTxnstate(Const.RETURN_FAILED);
		}
		try {
			rspStr = smRsp.toXML(oprRst);
			logger.info("rspStr=" + rspStr);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			OperationResult oprRstErr = new OperationResult();
			oprRstErr.setTxnstate(Const.RETURN_FAILED);
			oprRstErr.setErrMessage("请求处理异常");
			rspStr = smRsp.toXML(oprRstErr);
		}
		return rspStr;
	}

	private String formatStr(String source, int len) {
		try {
			byte[] bs = source.getBytes("UTF-8");
			byte[] tag = new byte[len];
			System.arraycopy(bs, 0, tag, 0, len);
			return new String(tag, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}

	}

	/**
	 * 完成后台请求的方法，该方法通过接前台传递过来的两个参数，参数类型为xml字符�?
	 * 首先通过将xml字符串转换为java对象OperationCtx和OperationRequest 然后通过获得的交易代�?
	 */
	@WebMethod
	public String sendMisService(@WebParam(name = "ctx") String ctx,
			@WebParam(name = "req") String req) {
		/**
		 * 设置xml转换格式
		 */
		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.allinfinance.");
		OperationCtx opCtx = null;
		OperationRequest opReq = null;

		OperationResult oprRst = new OperationResult();
		XStream smRsp = new XStream();
		smRsp.registerConverter(new XstreamDateConverter());
		smRsp.alias("OperationResult", OperationResult.class);
		smRsp.aliasPackage("RSP", "com.allinfinance.");
		String rspStr = "";

		try {
			// 将xml转换为对�?
			try {
				opCtx = (OperationCtx) smReq.fromXML(ctx);
				opReq = (OperationRequest) smReq.fromXML(req);
			} catch (XStreamException xe) {
				this.logger.error(xe.getMessage());
				logger.error("xml字符转换字符出错");
				throw new BizServiceException("请求数据格式转换异常!");
			}

			String txnCode = "";
			try {
				txnCode = opCtx.getTxncode();
			} catch (Exception e) {
				logger.error("获取交易代码异常");
				throw new BizServiceException("获取交易代码异常");
			}
			String userId;
			if (opCtx.getOprId() == null)
				userId = "0";
			else
				userId = opCtx.getOprId();

			// 添加事务处理
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def
					.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionManager.getTransaction(def);
			String txnContent = ReflectionUtil.toString(opReq.getTxnvo());
			if (txnContent.getBytes("utf-8").length > 4000) {
				txnContent = formatStr(txnContent, 3980);
			}
			try {
				oprRst = webServiceInvoke.excuteWebService(txnCode, opReq
						.getTxnvo());
				systemInfoBO.logSuccess(txnCode, userId, txnContent);
				transactionManager.commit(status);
				oprRst.setTxnstate(Const.RETURN_SUCC);
			} catch (BizServiceException e) {
				transactionManager.rollback(status);
				systemInfoBO.logError(txnCode, userId, txnContent, e
						.getErrorMessage());
				throw e;
			}
		} catch (BizServiceException be) {
			// 如果出现异常,将异常信息设置在对象oprRst�?
			oprRst.setErrMessage((be.getErrorMessage()));
			oprRst.setTxncause(be.getErrors());
			// 设置oprRst的状态为请求失败
			oprRst.setTxnstate(Const.RETURN_FAILED);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			oprRst.setErrMessage(ex.getMessage());
			oprRst.setTxnstate(Const.RETURN_FAILED);
		}
		try {
			rspStr = smRsp.toXML(oprRst);
			logger.debug("rspStr=" + rspStr);
		} catch (Exception e) {
			OperationResult oprRstErr = new OperationResult();
			oprRstErr.setTxnstate(Const.RETURN_FAILED);
			oprRstErr.setErrMessage(e.getMessage());
			rspStr = smRsp.toXML(oprRstErr);
		}
		return rspStr;
	}

	private DataSourceTransactionManager transactionManager;
	/**
	 * 
	 */
	private WebServiceInvoke webServiceInvoke;

	private SystemInfoBO systemInfoBO;

	public SystemInfoBO getSystemInfoBO() {
		return systemInfoBO;
	}

	public void setSystemInfoBO(SystemInfoBO systemInfoBO) {
		this.systemInfoBO = systemInfoBO;
	}

	public WebServiceInvoke getWebServiceInvoke() {
		return webServiceInvoke;
	}

	public void setWebServiceInvoke(WebServiceInvoke webServiceInvoke) {
		this.webServiceInvoke = webServiceInvoke;
	}
}
