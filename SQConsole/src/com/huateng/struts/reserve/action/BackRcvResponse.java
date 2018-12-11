package com.huateng.struts.reserve.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.huateng.bo.reserve.T9130101BO;
import com.huateng.bo.settle.T80224BO;
import com.huateng.bo.settle.T80601BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;
import com.huateng.po.settle.TblMchtSumrzInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.opensymphony.xwork2.ActionContext;
import com.huateng.sdk.LogUtil;
import com.huateng.sdk.SDKConfig;
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.FsasService;

@SuppressWarnings("serial")
public class BackRcvResponse extends BaseAction {

	T9130101BO t9130101BO = (T9130101BO) ContextUtil.getBean("T9130101BO");
	T80224BO t80224BO = (T80224BO) ContextUtil.getBean("T80224BO");
	T80601BO t80601BO = (T80601BO) ContextUtil.getBean("T80601BO");
	
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		
		//获取通知返回json字符串
		String jsonData = getStrResponse();
		
//		HttpServletResponse response = ServletActionContext.getResponse();
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		
		log("收到通知报文:" + jsonData);
		 
		Map<String,String> map = new HashMap<String, String>();
		
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		
		for(@SuppressWarnings("unchecked")
		Iterator<String> it = jsonObject.keys(); it.hasNext();)
		{
			String key = it.next();
			map.put(key, jsonObject.getString(key));
		}
		
		//从classpath加载fsas_sdk.properties文件
		SDKConfig.getConfig().loadPropertiesFromSrc();
		//初始化所有证书.
//		CertUtil.init();
	
		//重要！验证签名前不要修改map中的键值对的内容，否则会验签不过
		if (!FsasService.validate(map, DemoBase.encoding)) {
			log("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			log("验证签名结果[成功].");
			String respCode = map.get("respCode"); //获取交易应答码
			String txnType = map.get("txnType");  //获取交易类型
			String reqReserved = map.get("reqReserved");  //用来区分备款不同途径的同一类型交易或其它用途
			String txnNo = map.get("txnNo");  //交易流水号
//			String settleMethod = map.get("settleMethod");  //商户资金结算方式
			log("应答码respCode=" + respCode + "交易类型txnType=" + txnType + "请求方保留域reqReserved=" + reqReserved);
			//临式表
			TblMchtSettleReserveTmp tblMchtSettleReserveTmp = new TblMchtSettleReserveTmp();
			TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = new TblSettleRedempTionInfTmp();
			
			if(txnType.equals("31")){    //31：来账
				log("来账通知报文:" + jsonData);
				
				
				
				LogUtil.writeLog("接收来账通知结束");
				//返回给银联服务器http 200状态码
				response.getWriter().print("ok");
				rspCode = "00";
			}else if(txnType.equals("32")){    //32：退汇
				log("退汇通知报文:" + jsonData);
				
				
				
				LogUtil.writeLog("接收退汇通知结束");
				//返回给银联服务器http 200状态码
				response.getWriter().print("ok");
				rspCode = "00";
			}
			
			if(respCode.equals("00")){
				//成功          
				if(txnType.equals("01")){ //01: 备款
					if(reqReserved.equals("1")){  //1商户结算备款
						log("商户结算备款通知报文:" + jsonData);
						try {
							String sql = "select RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, RESERVE_AUDIT_NAME, RESERVE_BATCH from TBL_MCHT_SETTLE_RESERVE_TMP where RESERVE_BATCH = '" + txnNo + "'";
							List<Object[]> tmsrt = commQueryDAO.findBySQLQuery(sql);
							
							for (Object[] objects : tmsrt) {
								tblMchtSettleReserveTmp.setReserveId(String.valueOf(objects[0]));
								tblMchtSettleReserveTmp.setReserveTime(String.valueOf(objects[1]));
								tblMchtSettleReserveTmp.setRedemptionMoney(String.valueOf(objects[2]));
								tblMchtSettleReserveTmp.setReserveSettleMoney(String.valueOf(objects[3]));
								tblMchtSettleReserveTmp.setReserveMoney(String.valueOf(objects[4]));
								tblMchtSettleReserveTmp.setReserveStatus(String.valueOf(objects[5]));
								tblMchtSettleReserveTmp.setReserveSettleStatus("0");//0备款成功
								tblMchtSettleReserveTmp.setReservePayStatus("成功");
								tblMchtSettleReserveTmp.setReserveLaunchTime(String.valueOf(objects[8]));
								tblMchtSettleReserveTmp.setReserveLaunchName(String.valueOf(objects[9]));
								tblMchtSettleReserveTmp.setReserveAuditTime(String.valueOf(objects[10]));
								tblMchtSettleReserveTmp.setReserveAuditName(String.valueOf(objects[11]));
								tblMchtSettleReserveTmp.setReserveBatch(String.valueOf(objects[12]));
							}
							
							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_BATCH = '" + txnNo + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
									+ ",'0','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "','" + txnNo + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log("备款后台处理失败："+e);
						}
					}else if(reqReserved.equals("2")){  //2人行集中缴存备款
						log("人行集中缴存备款通知报文:" + jsonData);
						try {
							TblFocusReserve tblFocusReserve = null;
							tblFocusReserve = t9130101BO.getFocus(txnNo);
							//0备款成功
							tblFocusReserve.setFocusStatus("0");
							
							t9130101BO.upFocus(tblFocusReserve);
							
							//人行集中缴存备款成功后，自动发起银联虚拟记账余额查询交易
							Map<String, String> rspData = T91301Action.focusQuery();
							//主动发起回款交易
							huikuan(rspData.get("acctNo"),rspData.get("acctName"),rspData.get("avlbBal"));
						} catch (Exception e) {
							// TODO: handle exception
							log("备款后台处理失败："+e);
						}
					}
				}else if(txnType.equals("04")){  //04: 回款（模式二）06：回款（模式一） 
					log("回款通知报文:" + jsonData);
					try {
						TblPaymentReserve tblPaymentReserve = null;
						tblPaymentReserve = t9130101BO.getPayment(txnNo);
						//0出款成功
						tblPaymentReserve.setPaymentStatus("0");//回款状态
						
						rspCode = t9130101BO.upPayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.writeErrorLog("回款后台处理失败："+e);
					}
				}else if(txnType.equals("02")){   //02: 客户资金结算（单笔）
					log("客户资金结算（单笔）通知报文:" + jsonData);
					if(reqReserved.equals("00")){  //00: T+0
						TblMchtSumrzInf tblMchtSumrzInf = new TblMchtSumrzInf();
						try{
							//TODO
							tblMchtSumrzInf = t80224BO.get(new Integer(txnNo));
							tblMchtSumrzInf.setSaStatus("1");
							tblMchtSumrzInf.setCauseStat("划款成功");
							
							rspCode = t80224BO.update(tblMchtSumrzInf);
						} catch (Exception e) {
							log("划款后台处理失败："+e);
						}
					}else if(reqReserved.equals("03")){  //03: 客户赎回
						log("客户赎回通知报文:" + jsonData);
						try {
							String sql = "select REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY, REDEMPTION_BATCH from TBL_SETTLE_REDEMPTION_INF_TMP where REDEMPTION_BATCH = '" + txnNo + "'";
							List<Object[]> tsrtit = commQueryDAO.findBySQLQuery(sql);
							
							for (Object[] objects : tsrtit) {
								tblSettleRedempTionInfTmp.setRedempTionId(String.valueOf(objects[0]));
								tblSettleRedempTionInfTmp.setRedempTionAccountName(String.valueOf(objects[1]));
								tblSettleRedempTionInfTmp.setRedempTionAccount(String.valueOf(objects[2]));
								tblSettleRedempTionInfTmp.setRedempTionMoney(String.valueOf(objects[3]));
								tblSettleRedempTionInfTmp.setRedempTionBankCard(String.valueOf(objects[4]));
								tblSettleRedempTionInfTmp.setRedempTionStatus(String.valueOf(objects[5]));
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("0");//赎回成功
								tblSettleRedempTionInfTmp.setRedempTionPayStatus("成功");
								tblSettleRedempTionInfTmp.setRedempTionAddTime(String.valueOf(objects[8]));
								tblSettleRedempTionInfTmp.setRedempTionAddName(String.valueOf(objects[9]));
								tblSettleRedempTionInfTmp.setRedempTionAuditDate(String.valueOf(objects[10]));
								tblSettleRedempTionInfTmp.setRedempTionAuditName(String.valueOf(objects[11]));
								tblSettleRedempTionInfTmp.setRedempTionEnTry(String.valueOf(objects[12]));
								tblSettleRedempTionInfTmp.setRedemptionBatch(String.valueOf(objects[13]));
							}
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','" + tblSettleRedempTionInfTmp.getRedempTionStatus() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAuditDate() + "','" + tblSettleRedempTionInfTmp.getRedempTionAuditName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionEnTry() + "')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log("赎回后台处理失败："+e);
						}
					}
				}
			}else{    //其他应答码为失败请排查原因
				
				if(txnType.equals("01")){ //01: 备款
					if(reqReserved.equals("1")){  //1商户结算备款
						log("商户结算备款通知报文:" + jsonData);
						try {
							String sql = "select RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, RESERVE_AUDIT_NAME, RESERVE_BATCH from TBL_MCHT_SETTLE_RESERVE_TMP where RESERVE_BATCH = '" + txnNo + "'";
							List<Object[]> tmsrt = commQueryDAO.findBySQLQuery(sql);
							
							for (Object[] objects : tmsrt) {
								tblMchtSettleReserveTmp.setReserveId(String.valueOf(objects[0]));
								tblMchtSettleReserveTmp.setReserveTime(String.valueOf(objects[1]));
								tblMchtSettleReserveTmp.setRedemptionMoney(String.valueOf(objects[2]));
								tblMchtSettleReserveTmp.setReserveSettleMoney(String.valueOf(objects[3]));
								tblMchtSettleReserveTmp.setReserveMoney(String.valueOf(objects[4]));
								tblMchtSettleReserveTmp.setReserveStatus(String.valueOf(objects[5]));
								tblMchtSettleReserveTmp.setReserveSettleStatus("1");//1备款失败
								tblMchtSettleReserveTmp.setReservePayStatus(String.valueOf(objects[7]));
								tblMchtSettleReserveTmp.setReserveLaunchTime(String.valueOf(objects[8]));
								tblMchtSettleReserveTmp.setReserveLaunchName(String.valueOf(objects[9]));
								tblMchtSettleReserveTmp.setReserveAuditTime(String.valueOf(objects[10]));
								tblMchtSettleReserveTmp.setReserveAuditName(String.valueOf(objects[11]));
								tblMchtSettleReserveTmp.setReserveBatch(String.valueOf(objects[12]));
							}
							
							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_BATCH = '" + txnNo + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
									+ ",'1','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "','" + txnNo + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log("备款后台处理失败："+e);
						}
					}else if(reqReserved.equals("2")){  //2人行集中缴存备款
						log("人行集中缴存备款通知报文:" + jsonData);
						try {
							TblFocusReserve tblFocusReserve = null;
							tblFocusReserve = t9130101BO.getFocus(txnNo);
							//1备款失败
							tblFocusReserve.setFocusStatus("1");
							
							rspCode = t9130101BO.upFocus(tblFocusReserve);
						} catch (Exception e) {
							// TODO: handle exception
							log("备款后台处理失败："+e);
						}
					}
				}else if(txnType.equals("04")){  //04: 回款（模式二）06：回款（模式一） 
					log("回款通知报文:" + jsonData);
					try {
						TblPaymentReserve tblPaymentReserve = null;
						tblPaymentReserve = t9130101BO.getPayment(txnNo);
						//1出款失败
						tblPaymentReserve.setPaymentStatus("1");//回款状态
						
						rspCode = t9130101BO.upPayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.writeErrorLog("回款后台处理失败："+e);
					}
				}else if(txnType.equals("02")){   //02: 客户资金结算（单笔）
					if(reqReserved.equals("00")){  //00: T+0
						log("客户资金结算（单笔）通知报文:" + jsonData);
						TblMchtSumrzInf tblMchtSumrzInf = new TblMchtSumrzInf();
						try{
							//TODO
							tblMchtSumrzInf = t80224BO.get(new Integer(txnNo));
							tblMchtSumrzInf.setSaStatus("0");
							tblMchtSumrzInf.setCauseStat("划款失败");
							
							rspCode = t80224BO.update(tblMchtSumrzInf);
						} catch (Exception e) {
							log("划款后台处理失败："+e);
						}
					}else if(reqReserved.equals("03")){  //03: 客户赎回
						log("客户赎回通知报文:" + jsonData);
						try {
							String sql = "select REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY, REDEMPTION_BATCH from TBL_SETTLE_REDEMPTION_INF_TMP where REDEMPTION_BATCH = '" + txnNo + "'";
							List<Object[]> tsrtit = commQueryDAO.findBySQLQuery(sql);
							
							for (Object[] objects : tsrtit) {
								tblSettleRedempTionInfTmp.setRedempTionId(String.valueOf(objects[0]));
								tblSettleRedempTionInfTmp.setRedempTionAccountName(String.valueOf(objects[1]));
								tblSettleRedempTionInfTmp.setRedempTionAccount(String.valueOf(objects[2]));
								tblSettleRedempTionInfTmp.setRedempTionMoney(String.valueOf(objects[3]));
								tblSettleRedempTionInfTmp.setRedempTionBankCard(String.valueOf(objects[4]));
								tblSettleRedempTionInfTmp.setRedempTionStatus(String.valueOf(objects[5]));
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");//赎回失败
								tblSettleRedempTionInfTmp.setRedempTionPayStatus("失败");
								tblSettleRedempTionInfTmp.setRedempTionAddTime(String.valueOf(objects[8]));
								tblSettleRedempTionInfTmp.setRedempTionAddName(String.valueOf(objects[9]));
								tblSettleRedempTionInfTmp.setRedempTionAuditDate(String.valueOf(objects[10]));
								tblSettleRedempTionInfTmp.setRedempTionAuditName(String.valueOf(objects[11]));
								tblSettleRedempTionInfTmp.setRedempTionEnTry(String.valueOf(objects[12]));
								tblSettleRedempTionInfTmp.setRedemptionBatch(String.valueOf(objects[13]));
							}
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','" + tblSettleRedempTionInfTmp.getRedempTionStatus() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAuditDate() + "','" + tblSettleRedempTionInfTmp.getRedempTionAuditName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionEnTry() + "')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log("赎回后台处理失败："+e);
						}
					}
					
				}
				LogUtil.writeErrorLog("交易失败。原因:" + map.get("respMsg"));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 回款
	 * @param acctBal
	 * @return
	 */
	public void huikuan(String acctNo,String acctName,String acctBal){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
//		String PAYERACCTNO = SysParamUtil.getParam(SysParamConstants.PAYERACCTNO);//付款方账号
//		String PAYERACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYERACCTNAME);//付款方账户名称
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件

		SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String txnNo = sdfQuery.format(new Date());
		SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
		String txnDate = sdfQuery1.format(new Date());
//		SimpleDateFormat sdfQuery2 = new SimpleDateFormat("HHmmss");
		
		/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "04");  ////交易类型04：回款（模式二）06：回款（模式一） 	
		contentData.put("backUrl", DemoBase.backUrl);            //后台通知地址
	    contentData.put("txnNo", txnNo);                                    //交易流水号
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
	    contentData.put("txnDate",txnDate);                               //交易日期
	    contentData.put("sndTime", DemoBase.getSendTime());      //发送时间 格式HHmmss
	    contentData.put("insSeq", "01");   //头寸序号   以机构代码 +头寸序号在银联系统内对应的银行账户为准
	    contentData.put("payerAcctNo", acctNo);                //收款方账号
	    contentData.put("payerAcctName", acctName);        //收款方账户名称
	    contentData.put("currencyCode", "156");                          //币种
	    contentData.put("txnAmt",acctBal);                                 //金额

		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = FsasService.sign(contentData,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			//交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的 fsassdk.backTransUrl
		Map<String, String> rspData = FsasService.doPost(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
//		StringBuffer parseStr = new StringBuffer("");
		if(!rspData.isEmpty()){
			if(FsasService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
//				String respMsg = rspData.get("respMsg");
				if(("00").equals(respCode)){
					//成功 
					//TODO
					try {
						TblPaymentReserve tblPaymentReserve = new TblPaymentReserve();
						tblPaymentReserve.setPaymentId(txnNo);//交易流水号
						tblPaymentReserve.setPaymentAccount(acctNo);//回款账户
						tblPaymentReserve.setPaymentAccountName(acctName);//回款账户名称
						tblPaymentReserve.setPaymentMoney(acctBal);//回款金额
						tblPaymentReserve.setPaymentDate(txnDate);//回款日期
						//2出款受理中
						tblPaymentReserve.setPaymentStatus("2");//回款状态
						
						rspCode = t9130101BO.savePayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.writeErrorLog("回款后台处理失败："+e);
					}
				}else{
					//其他应答码为失败请排查原因
					//TODO
					try {
						TblPaymentReserve tblPaymentReserve = new TblPaymentReserve();
						tblPaymentReserve.setPaymentId(txnNo);//交易流水号
						tblPaymentReserve.setPaymentAccount(acctNo);//回款账户
						tblPaymentReserve.setPaymentAccountName(acctName);//回款账户名称
						tblPaymentReserve.setPaymentMoney(acctBal);//回款金额
						tblPaymentReserve.setPaymentDate(txnDate);//回款日期
						//1出款失败
						tblPaymentReserve.setPaymentStatus("1");//回款状态失败
						
						rspCode = t9130101BO.savePayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.writeErrorLog("回款后台处理失败："+e);
					}
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
	}
	
	/**
	 * 获取银联通知     json数据
	 * @return
	 */
	public static String getStrResponse(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);  
		InputStream inputStream;
		String strResponse = "";
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			while ((strMessage = reader.readLine()) != null) {
				strResponse += strMessage;
			}
			reader.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strResponse;
	}
	
	
	
}
