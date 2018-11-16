package com.huateng.struts.settle.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.huateng.bo.settle.T80601BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblSettleRedempTionInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T80601Action extends BaseSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T80601Action.class);
	T80601BO t80601BO = (T80601BO) ContextUtil.getBean("T80601BO");
	
	private String redempTionId;  //客户编号
	private String redempTionAccountName; //名称
	private String redempTionAccount; //账户
	private String redempTionMoney; //金额
	private String redempTionBankCard; //客户开户行行号
	
	private String infList; //
	

	/**
	 * 添加赎回信息
	 * @return
	 */
	public String redempAdd(){
		
		try {
			String sql = "select trim(REDEMPTION_ACCOUNT) from TBL_SETTLE_REDEMPTION_INF_TMP where REDEMPTION_ACCOUNT = '" + redempTionAccount + "'";
			String redempAccountTmp = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
			if(redempAccountTmp.equals(redempTionAccount)){
				return returnService("客户账户已存在");
			}
			
			TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = new TblSettleRedempTionInfTmp();
			
			String sql1 = "select MAX(REDEMPTION_ID) from TBL_SETTLE_REDEMPTION_INF_TMP ";
			String redempMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
			String redempNo;
			if (redempMax == "") {
				redempNo = "000001";
			}else {
				int i = Integer.parseInt(redempMax);
				i = i + 1;
				//如5在前面补0  直到凑够6位数  000005
				redempNo = String.format("%06d", i);
			}
			tblSettleRedempTionInfTmp.setRedempTionId(redempNo);
			tblSettleRedempTionInfTmp.setRedempTionAccountName(redempTionAccountName);
			tblSettleRedempTionInfTmp.setRedempTionAccount(redempTionAccount);
			tblSettleRedempTionInfTmp.setRedempTionMoney(redempTionMoney);
			tblSettleRedempTionInfTmp.setRedempTionBankCard(redempTionBankCard);
			//0为正常，1为新增审核状态
			tblSettleRedempTionInfTmp.setRedempTionStatus("1");
			//0为已入账，1未入账，2为赎回中
			tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			tblSettleRedempTionInfTmp.setRedempTionEnTry("1");
			//入账日期
			tblSettleRedempTionInfTmp.setRedempTionAddTime(sdf.format(date));
			//入账人员
			tblSettleRedempTionInfTmp.setRedempTionAddName(getOperator().getOprId());
			
			rspCode = t80601BO.redempAdd(tblSettleRedempTionInfTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("新增后台处理失败："+e);
			return returnService("新增后台处理失败！请联系管理员！");
		}
		log("客户新增信息成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 删除客户赎回信息
	 * @return
	 */
	public String redempDel(){
		
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");
			TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
			try {
				tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
				if(tblSettleRedempTionInfTmp==null){
					returnService("该条信息已删除");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.error("删除后台处理失败："+e);
				return returnService("删除后台处理失败！请联系管理员！");
			}
			
			try {
				//删除审核    改变其状态   在审核过后真正的删除
				tblSettleRedempTionInfTmp.setRedempTionStatus("6");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				//发起日期
				tblSettleRedempTionInfTmp.setRedempTionAddTime(sdf.format(date));
				//发起人员
				tblSettleRedempTionInfTmp.setRedempTionAddName(getOperator().getOprId());
				
				rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.error("删除后台处理失败："+e);
				return returnService("删除后台处理失败！请联系管理员！");
			}
		}
		log("删除客户赎回信息成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 客户赎回
	 * @return
	 */
	public String redemp(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");
			String redempTionAccountName = jsonBean.getJSONDataAt(i).getString("redempTionAccountName");
			String redempTionAccount = jsonBean.getJSONDataAt(i).getString("redempTionAccount");
			String redempTionMoney = jsonBean.getJSONDataAt(i).getString("redempTionMoney");
			String redempTionBankCard = jsonBean.getJSONDataAt(i).getString("redempTionBankCard");
			String redempTionStatus = jsonBean.getJSONDataAt(i).getString("redempTionStatus");
			String redempTionAccountStatus = jsonBean.getJSONDataAt(i).getString("redempTionAccountStatus");
			String redempTionPayStatus = jsonBean.getJSONDataAt(i).getString("redempTionPayStatus");
			String redempTionAddTime = jsonBean.getJSONDataAt(i).getString("redempTionAddTime");
			String redempTionAddName = jsonBean.getJSONDataAt(i).getString("redempTionAddName");
			String redempTionAuditDate = jsonBean.getJSONDataAt(i).getString("redempTionAuditDate");
			String redempTionAuditName = jsonBean.getJSONDataAt(i).getString("redempTionAuditName");
			String redempTionEnTry = jsonBean.getJSONDataAt(i).getString("redempTionEnTry");
			
			try {
				tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
				//审核状态
				tblSettleRedempTionInfTmp.setRedempTionStatus("3");
				//入账状态
				tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				//入账日期
				tblSettleRedempTionInfTmp.setRedempTionAddTime(sdf.format(date));
				//入账人员
				tblSettleRedempTionInfTmp.setRedempTionAddName(getOperator().getOprId());
				
				rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.error("赎回后台处理失败："+e);
				return returnService("赎回后台处理失败！请联系管理员！");
			}
			
		}
		log("客户赎回信息成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 客户赎回审核     通过
	 * @return
	 */
	public String redempAccept(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod = SysParamUtil.getParam(SysParamConstants.BUSCOD);//银企直业务类别
		String nusage = SysParamUtil.getParam(SysParamConstants.NUSAGE);//用途
		String crtsqn = SysParamUtil.getParam(SysParamConstants.CRTSQN);//收方编号
		String dbtacc = SysParamUtil.getParam(SysParamConstants.DBTACC);//付方帐号
		String dbtbnk = SysParamUtil.getParam(SysParamConstants.DBTBNK);//付方开户地区
		String c_dbtbbk = SysParamUtil.getParam(SysParamConstants.C_DBTBBK);//付方开户地区
		String dbtnam = SysParamUtil.getParam(SysParamConstants.DBTNAM);//付方帐户名
		String dbtrel = SysParamUtil.getParam(SysParamConstants.DBTREL);//付方客户关系号
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		//临时
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		//正式
		TblSettleRedempTionInf tblSettleRedempTionInf = null;
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			String redempTionAccountName = jsonBean.getJSONDataAt(i).getString("redempTionAccountName");//客户账户名称
			String redempTionAccount = jsonBean.getJSONDataAt(i).getString("redempTionAccount");//客户账户
			String redempTionMoney = jsonBean.getJSONDataAt(i).getString("redempTionMoney");//赎回金额
			String redempTionBankCard = jsonBean.getJSONDataAt(i).getString("redempTionBankCard");//客户开户行行号
			String redempTionStatus = jsonBean.getJSONDataAt(i).getString("redempTionStatus");//审核状态
			String redempTionAccountStatus = jsonBean.getJSONDataAt(i).getString("redempTionAccountStatus");//是否打款
			String redempTionPayStatus = jsonBean.getJSONDataAt(i).getString("redempTionPayStatus");//支付状态
			String redempTionAddTime = jsonBean.getJSONDataAt(i).getString("redempTionAddTime");//发起入账日期
			String redempTionAddName = jsonBean.getJSONDataAt(i).getString("redempTionAddName");//发起入账人
			String redempTionAuditDate = jsonBean.getJSONDataAt(i).getString("redempTionAuditDate");//审核日期
			String redempTionAuditName = jsonBean.getJSONDataAt(i).getString("redempTionAuditName");//审核人
			String redempTionEnTry = jsonBean.getJSONDataAt(i).getString("redempTionEnTry");//录入方式
			
			System.out.println("发起人:"+redempTionAddName +"		发起时间:"+redempTionAddTime);
			log.info("发起人:"+redempTionAddName +"		发起时间:"+redempTionAddTime);
			try{
				if (getOperator().getOprId().equals(redempTionAddName)) {
					l++ ;
					log.info("发起人与审核人同一用户！");
					return returnService("发起人与审核人同一用户！");
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
			//1为新增审核      3为赎回审核     6为删除审核
			if(redempTionStatus.equals("1")){
				//临时表
				tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
				
				if(tblSettleRedempTionInfTmp==null){
					return returnService("没找到客户赎回的临时信息，请重试");
				}
				
				//正式表
				tblSettleRedempTionInf = t80601BO.getRedempInf(tblSettleRedempTionInfTmp.getRedempTionId());
				
				//为空创建对象
				if(tblSettleRedempTionInf==null){
					new TblSettleRedempTionInf();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				
				String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
				commQueryDAO.excute(redempDelInf);
				
				String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
						+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
						+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
						+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','2','1',null,'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
						+ ",'" + sdf.format(date) + "','" + getOperator().getOprId() + "','1')";
				commQueryDAO.excute(redempDel);
				
				
				//更新临时表审核时间
				tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(date));
				//更新临时表审核人
				tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
				//0 正常
				tblSettleRedempTionInfTmp.setRedempTionStatus("2");
				//1失败
				tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
				try {
					//更新到数据库
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(redempTionStatus.equals("3")){
				try {
					XmlPacket xmlPkt = new XmlPacket("Payment", lgnnam);//登陆用户名
					Map mpPodInfo = new Properties();
					mpPodInfo.put("BUSCOD", buscod);//业务类别
					xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
					Map mpPayInfo = new Properties();
					//用于标识该笔业务的编号，企业银行编号+业务类型+业务参考号必须唯一。企业可以自定义业务参考号，也可使用银行缺省值（单笔支付），批量支付须由企业提供。直联必须用企业提供
					String yurref;
					
					mpPayInfo.put("YURREF", redempTionId);//业务参考号
					mpPayInfo.put("DBTACC", dbtacc);//付方帐号
					mpPayInfo.put("C_DBTBBK", c_dbtbbk);//付方开户地区
					mpPayInfo.put("DBTBNK", dbtbnk);//付方开户行
					mpPayInfo.put("DBTNAM", dbtnam);//付方帐户名
					mpPayInfo.put("DBTREL", dbtrel);//付方客户关系号
					mpPayInfo.put("TRSAMT", redempTionMoney);//赎回金额
					mpPayInfo.put("CCYNBR", "10");//币种代码
					mpPayInfo.put("STLCHN", "N");//结算方式代码
					mpPayInfo.put("NUSAGE", nusage);//用途
					mpPayInfo.put("CRTACC", redempTionAccount);//收方帐号
					mpPayInfo.put("CRTNAM", redempTionAccountName);//收方帐户名
					mpPayInfo.put("CRTSQN", crtsqn);//收方编号
					
					xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);//支付输入明细接口
					// 生成请求报文
					String date = xmlPkt.toXmlString();
					log.info("生成请求报文:"+date);
					// 连接前置机，发送请求报文，获得返回报文
					String result = T80601Action.sendRequest(date);
					result = result.replace("GBK", "UTF-8");
					// 处理返回的结果
					String processResult = T80601Action.processResult(result);
					if(processResult.toUpperCase().equals("NTE")){
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");//赎回中
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("终审完毕");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if (processResult.toUpperCase().equals("AUT")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("等待审批");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if(processResult.toUpperCase().equals("WCF")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("订单待确认");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if(processResult.toUpperCase().equals("BNK")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("银行处理中");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if(processResult.toUpperCase().equals("ACK")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("等待确认");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if(processResult.toUpperCase().equals("APD")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("待银行确认");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else if(processResult.toUpperCase().equals("OPR")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("数据接收中");
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
									+ ",'" + sdf.format(dates) + "','" + getOperator().getOprId() + "','1')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}else{
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date dates = new Date();
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionStatus("5");
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus(processResult);
							//更新临时表审核时间
							tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(dates));
							//更新临时表审核人
							tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
							
							//思路是先删除正式表中对应的数据，在临时表添加相应的数据至正式表
							String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
							commQueryDAO.excute(redempDelInf);
							
							String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
									+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
									+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
									+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','" + tblSettleRedempTionInfTmp.getRedempTionStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "','" + sdf.format(dates) + "','" + getOperator().getOprId() + "'"
									+ ",'" + tblSettleRedempTionInfTmp.getRedempTionEnTry() + "')";
							commQueryDAO.excute(redempDel);
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("赎回后台处理失败："+e);
							return returnService("赎回后台处理失败！请联系管理员！");
						}
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					return returnService(rspCode,e);
				}
			}else if(redempTionStatus.equals("6")){
				try {
					String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf);
					//删除审核通过，删除外键表数据
					String redempDelInf1 = "delete from TBL_MCHT_SETTLE_RESERVE where REDEMPTION_MONEY = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf1);
					
					String redempDelInf2 = "delete from TBL_MCHT_SETTLE_RESERVE_TMP where REDEMPTION_MONEY = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf2);
					
					rspCode = t80601BO.redempDel(redempTionId);
				} catch (Exception e) {
					// TODO: handle exceptionSystem.out.println(e.getMessage());
					return returnService(rspCode,e);
				}
			}
			
		}
		log("客户审核成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 客户赎回审核	    拒绝
	 * @return
	 */
	public String redempRefuse(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		//临时表
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			String redempTionAccountName = jsonBean.getJSONDataAt(i).getString("redempTionAccountName");//客户账户名称
			String redempTionAccount = jsonBean.getJSONDataAt(i).getString("redempTionAccount");//客户账户
			String redempTionMoney = jsonBean.getJSONDataAt(i).getString("redempTionMoney");//赎回金额
			String redempTionBankCard = jsonBean.getJSONDataAt(i).getString("redempTionBankCard");//客户开户行行号
			String redempTionStatus = jsonBean.getJSONDataAt(i).getString("redempTionStatus");//审核状态
			String redempTionAccountStatus = jsonBean.getJSONDataAt(i).getString("redempTionAccountStatus");//是否打款
			String redempTionPayStatus = jsonBean.getJSONDataAt(i).getString("redempTionPayStatus");//支付状态
			String redempTionAddTime = jsonBean.getJSONDataAt(i).getString("redempTionAddTime");//发起入账日期
			String redempTionAddName = jsonBean.getJSONDataAt(i).getString("redempTionAddName");//发起入账人
			String redempTionAuditDate = jsonBean.getJSONDataAt(i).getString("redempTionAuditDate");//审核日期
			String redempTionAuditName = jsonBean.getJSONDataAt(i).getString("redempTionAuditName");//审核人
			String redempTionEnTry = jsonBean.getJSONDataAt(i).getString("redempTionEnTry");//录入方式
			
			System.out.println("发起人:"+redempTionAddName +"		发起时间:"+redempTionAddTime);
			log.info("发起人:"+redempTionAddName +"		发起时间:"+redempTionAddTime);
			try{
				if (getOperator().getOprId().equals(redempTionAddName)) {
					l++ ;
					log.info("发起人与审核人同一用户！");
					return returnService("发起人与审核人同一用户！");
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
			//1为新增审核     3为赎回审核     6为删除审核
			if(redempTionStatus.equals("1")){
				try {
					/*tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionStatus("2");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新临时表审核时间
					tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(date));
					//更新临时表审核人
					tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);*/
					
					rspCode = t80601BO.redempDel(redempTionId);
					
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(redempTionStatus.equals("3")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionStatus("4");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新临时表审核时间
					tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(date));
					//更新临时表审核人
					tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(redempTionStatus.equals("6")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					//8删除审核拒绝
					tblSettleRedempTionInfTmp.setRedempTionStatus("7");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新临时表审核时间
					tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(date));
					//更新临时表审核人
					tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exceptionSystem.out.println(e.getMessage());
					return returnService(rspCode,e);
				}
			}
		}
		log("客户赎回审核拒绝成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 客户回填
	 * @return
	 */
	public String redempBackFill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		//临时表
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			String redempTionAuditDate = jsonBean.getJSONDataAt(i).getString("redempTionAuditDate");//审核日期
			
			XmlPacket xmlPkt = new XmlPacket("GetPaymentInfo", lgnnam);//函数名     登陆用户名
			Map mpPodInfo = new Properties();
			mpPodInfo.put("BUSCOD", buscod01);//业务类别
			mpPodInfo.put("BGNDAT", redempTionAuditDate);//起始时间
			mpPodInfo.put("ENDDAT", redempTionAuditDate);//结束时间
			mpPodInfo.put("DATFLG", "B");//日期类型  A：经办日期；B：期望日期
			mpPodInfo.put("YURREF", redempTionId);//业务参考号
			xmlPkt.putProperty("SDKPAYQYX", mpPodInfo);
			String date = xmlPkt.toXmlString();
			log.info("生成查询报文:"+date);
			// 连接前置机，发送请求报文，获得返回报文
			String result = T80601Action.sendRequest(date);
			result = result.replace("GBK", "UTF-8");
			// 处理返回的结果
			String processResult = T80601Action.processResultTS(result);
			if (processResult.toUpperCase().equals("FIN")) {
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("0");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("完成");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}				
			}else if(processResult.toUpperCase().equals("AUT")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("等待审批");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("NTE")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("终审完毕");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("WCF")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("订单待确认");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("BNK")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("银行处理中");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("ACK")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("等待确认");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("APD")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("待银行确认");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.toUpperCase().equals("OPR")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("数据接收中");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.equals("F")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("银行支付失败");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else if(processResult.equals("B")){
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus(processResult);
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}else{
				try {
					tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
					tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
					tblSettleRedempTionInfTmp.setRedempTionPayStatus("银行支付被退票");
					
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("回填后台处理失败："+e);
					return returnService("回填处理失败！请联系管理员！");
				}
			}
		}
		
		log("客户回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 手动回填失败
	 * @return
	 */
	public String redempBackFillSDFail(){
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		//临时表
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			try {
				tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
				tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
				tblSettleRedempTionInfTmp.setRedempTionPayStatus("客户手动回填失败");
				
				String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
				commQueryDAO.excute(redempDelInf);
				
				String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
						+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
						+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
						+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','" + tblSettleRedempTionInfTmp.getRedempTionStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "','" + tblSettleRedempTionInfTmp.getRedempTionAuditDate() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAuditName() + "','" + tblSettleRedempTionInfTmp.getRedempTionEnTry() + "')";
				commQueryDAO.excute(redempDel);
				
				rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("手动回填失败后台处理失败："+e);
				return returnService("手动回填失败处理失败！请联系管理员！");
			}
		}
		log("客户回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 手动回填成功
	 * @return
	 */
	public String redempBackFillSDSuccess(){
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		//临时表
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			try {
				tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
				tblSettleRedempTionInfTmp.setRedempTionAccountStatus("0");
				tblSettleRedempTionInfTmp.setRedempTionPayStatus("客户手动回填成功");
				
				String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
				commQueryDAO.excute(redempDelInf);
				
				String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
						+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
						+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
						+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','" + tblSettleRedempTionInfTmp.getRedempTionStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "','" + tblSettleRedempTionInfTmp.getRedempTionAuditDate() + "'"
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAuditName() + "','" + tblSettleRedempTionInfTmp.getRedempTionEnTry() + "')";
				commQueryDAO.excute(redempDel);
				
				rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("手动回填成功后台处理失败："+e);
				return returnService("手动回填成功处理失败！请联系管理员！");
			}
		}
		log("客户回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 */
	public static String sendRequest(String data) {
		String result = "";
		try {
			URL url;
			String yqhtpp = SysParamUtil.getParam(SysParamConstants.YQHTPP);
			url = new URL(yqhtpp);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "GBK");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			System.out.println("\n");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("\n");
			os.write(data.toString().getBytes("GBK"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"gbk"));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("\n");
			System.out.println("======================================================================================================");
			log.info("返回数据result："+"\n"+result);
			System.out.println("======================================================================================================");
			System.out.println("\n");
			br.close();
			
		} catch (MalformedURLException e) {
			log.error("MalformedURLException:"+e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException:"+e);
			e.printStackTrace();
		} catch (ProtocolException e) {
			log.error("ProtocolException:"+e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException:"+e);
			e.printStackTrace();
		}
		try {
			return new String(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException:"+e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 处理返回的结果
	 * @param result
	 * @return
	 */
	private static String processResultTS(String result) {
		String str="";
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYQYZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						String object = (String) propPayResult.get("ERRTXT");
						str = "支付失败："+ object;
						log.error("支付失败："+ object);
					} else {
						if(sREQSTS.equals("S")){
							return "FIN";
						}
						str = sREQSTS;
						log.error("支付已被银行受理（支付状态：" + sREQSTS + "）");
					}
				} else if (sRetCod.equals("-9")) {
					str = "支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG();
					log.error("支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
				} else {
					str = "支付失败：" + pktRsp.getERRMSG() +" REQSTS:"+sRetCod;
					log.error("支付失败：" + pktRsp.getERRMSG());
				}
			} else {
				str = "响应报文解析失败" ;
				log.error("响应报文解析失败");
			}
		}
		return str;
	}
	
	/**
	 * 处理返回的结果
	 * 
	 * @param result
	 */
	public static String processResult(String result) {
		String str="";
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						String object = (String) propPayResult.get("ERRTXT");
						str = "支付失败："+ object;
						log.error("支付失败："+ object);
					} else {
						if(sREQSTS.equals("S")){
							return "FIN";
						}
						str = sREQSTS;
						log.error("支付已被银行受理（支付状态：" + sREQSTS + "）");
					}
				} else if (sRetCod.equals("-9")) {
					str = "支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG();
					log.error("支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
				} else {
					str = "支付失败：" + pktRsp.getERRMSG() +" REQSTS:"+sRetCod;
					log.error("支付失败：" + pktRsp.getERRMSG());
				}
			} else {
				str = "响应报文解析失败" ;
				log.error("响应报文解析失败");
			}
		}
		return str;
	}
	
	
	
	
	
	public String getRedempTionBankCard() {
		return redempTionBankCard;
	}
	public void setRedempTionBankCard(String redempTionBankCard) {
		this.redempTionBankCard = redempTionBankCard;
	}
	public String getInfList() {
		return infList;
	}
	public void setInfList(String infList) {
		this.infList = infList;
	}
	public String getRedempTionId() {
		return redempTionId;
	}
	public void setRedempTionId(String redempTionId) {
		this.redempTionId = redempTionId;
	}
	public String getRedempTionAccountName() {
		return redempTionAccountName;
	}
	public void setRedempTionAccountName(String redempTionAccountName) {
		this.redempTionAccountName = redempTionAccountName;
	}
	public String getRedempTionAccount() {
		return redempTionAccount;
	}
	public void setRedempTionAccount(String redempTionAccount) {
		this.redempTionAccount = redempTionAccount;
	}
	public String getRedempTionMoney() {
		return redempTionMoney;
	}
	public void setRedempTionMoney(String redempTionMoney) {
		this.redempTionMoney = redempTionMoney;
	}
	
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}
	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}
	
}
