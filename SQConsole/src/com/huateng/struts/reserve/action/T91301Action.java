package com.huateng.struts.reserve.action;

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
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.huateng.bo.reserve.T9130101BO;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T91301Action extends BaseSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T91301Action.class);
	T9130101BO t9130101BO = (T9130101BO) ContextUtil.getBean("T9130101BO");
	
	private String reserveMoney;  //备款金额
	private String reserveId;  //id
	
	private String infList;
	
	private String focusDate;  //人行集中缴存备款时间
	private String focusMoney;  //人行集中缴存备款金额
	
	private String startdate;//当前时间
	private String preDate;//当前时间的前一天时间
	
//	public static int count = 0; //定义全局变量标识
	
	@SuppressWarnings("unchecked")
	public String init(){
		/*if(count==0){
			//查询所有未入账客户的赎回金额     1未入账
			String sql1 = "select sum(REDEMPTION_MONEY) from TBL_SETTLE_REDEMPTION_INF_TMP where REDEMPTION_ACCOUNT_STATUS = '1'";
			String accountMoney = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
			//查询前一日的商户结算金额
			String sql2 = "select sum(RESERVE_SETTLE_MONEY) from TBL_MCHT_SETTLE_RESERVE  where RESERVE_TIME = '" + preDate + "'";
			String accountSettleMoney = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql2);
			double sum;
			if(accountSettleMoney.equals("")){
				sum = Double.parseDouble(accountMoney);
			}else{
				sum = Double.parseDouble(accountMoney) + Double.parseDouble(accountSettleMoney);
			}
			//查询结算备款表
			String sql3 = "select RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, RESERVE_AUDIT_NAME from TBL_MCHT_SETTLE_RESERVE_TMP";
			List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql3);
			for (Object[] objects : list) {
				String sql4 = "update TBL_MCHT_SETTLE_RESERVE_TMP set RESERVE_MONEY = '" + String.valueOf(sum) + "' where RESERVE_ID = '" + objects[0] + "'";
				CommonFunction.getCommQueryDAO().excute(sql4);
			}
			count++;
		}*/
		rspCode = Constants.SUCCESS_CODE;
		return returnService(rspCode);
	}
	
	/**
	 * 商户备款审核     通过
	 * @return
	 */
	public String redempAccept(){
		String BACKURL = SysParamUtil.getParam(SysParamConstants.BACKURL);//后台通知地址
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String INSSEQ = SysParamUtil.getParam(SysParamConstants.INSSEQ);//头寸序号
		String CERTID = SysParamUtil.getParam(SysParamConstants.CERTID);//证书ID
		String SIGNATURE = SysParamUtil.getParam(SysParamConstants.SIGNATURE);//签名
		String SIGNMETHOD = SysParamUtil.getParam(SysParamConstants.SIGNMETHOD);//签名方法
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		TblMchtSettleReserve tblMchtSettleReserve = null;
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp = null;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");
			String reserveTime = jsonBean.getJSONDataAt(i).getString("reserveTime");
			String redemptionMoney = jsonBean.getJSONDataAt(i).getString("redemptionMoney");
			String reserveSettleMoney = jsonBean.getJSONDataAt(i).getString("reserveSettleMoney");
			String reserveMoney = jsonBean.getJSONDataAt(i).getString("reserveMoney");
			String reserveStatus = jsonBean.getJSONDataAt(i).getString("reserveStatus");
			String reserveSettleStatus = jsonBean.getJSONDataAt(i).getString("reserveSettleStatus");
			String reservePayStatus = jsonBean.getJSONDataAt(i).getString("reservePayStatus");
			String reserveLaunchTime = jsonBean.getJSONDataAt(i).getString("reserveLaunchTime");
			String reserveLaunchName = jsonBean.getJSONDataAt(i).getString("reserveLaunchName");
			String reserveAuditTime = jsonBean.getJSONDataAt(i).getString("reserveAuditTime");
			String reserveAuditName = jsonBean.getJSONDataAt(i).getString("reserveAuditName");
			String redemptionAccount = jsonBean.getJSONDataAt(i).getString("redemptionAccount");
			String redemptionAccountName = jsonBean.getJSONDataAt(i).getString("redemptionAccountName");
			
			System.out.println("发起人:"+reserveLaunchName +"		发起时间:"+reserveLaunchTime);
			log.info("发起人:"+reserveLaunchName +"		发起时间:"+reserveLaunchTime);
			try{
				if (getOperator().getOprId().equals(reserveLaunchName)) {
					l++ ;
					log.info("发起人与审核人同一用户！");
					return returnService("发起人与审核人同一用户！");
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
			//1为修改待审核    2为备款待审核
			if(reserveStatus.equals("1")){
				//临式表
				tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
				
				if(tblMchtSettleReserveTmp==null){
					return returnService("没找到商户备款的信息，请重试");
				}
				//正时表
				tblMchtSettleReserve = t9130101BO.getReserve(reserveId);
				
				if(tblMchtSettleReserve==null){
					new TblMchtSettleReserve();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				
				/*String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
				commQueryDAO.excute(reserveDel);		
				
				String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
						+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
						+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveSettleStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + sdf.format(date) + "'"
						+ ",'" + getOperator().getOprId() + "')";
				commQueryDAO.excute(reserveDelInf);*/
				
				//改为正常0
				tblMchtSettleReserveTmp.setReserveStatus("0");
				
				//更新审核时间
				tblMchtSettleReserveTmp.setReserveAuditTime(sdf.format(date));
				//更新审核人
				tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
				try {
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(reserveStatus.equals("2")){
				try {
					SimpleDateFormat sdfs = new SimpleDateFormat("HHmmss");
					Date date = new Date();
					
					JsonPacket jsonPacket = new JsonPacket();
					jsonPacket.setVersion("1.0.0");//版本号
					jsonPacket.setEncoding("UTF-8");//编码方式
					jsonPacket.setCertId(CERTID);//证书ID
					jsonPacket.setSignature(SIGNATURE);//签名
					jsonPacket.setSignMethod(SIGNMETHOD);//签名方法
					jsonPacket.setTxnType("01");//交易类型     01备款
					jsonPacket.setBackUrl(BACKURL);//后台通知地址
					jsonPacket.setTxnNo(reserveId);//交易流水号
					jsonPacket.setAcqInsCode(ACQINSCODE);//机构代码
					jsonPacket.setTxnDate(focusDate);//交易日期
					jsonPacket.setSndTime(sdfs.format(date));//发送时间
					jsonPacket.setInsSeq(INSSEQ);//头寸序号
					jsonPacket.setPayeeAcctNo(PAYEEACCTNO);//收款方账号
					jsonPacket.setPayeeAcctName(PAYEEACCTNAME);//收款方账户名称
					jsonPacket.setCurrencyCode("156");//币种
					jsonPacket.setTxnAmt(reserveMoney);//金额
					
					//对象转换成json字符串
					String json = JSON.toJSONString(jsonPacket);
					log.info("生成请求报文:"+json);
					//发送请求报文，获得返回报文
					String result = T91301Action.sendRequest(json);
					result = result.replace("GBK", "UTF-8");
					//处理返回的结果
					String processResult = T91301Action.processResult(result);
					if(processResult.toUpperCase().equals("FIN")){
						try {
							//临式表
							tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date date1 = new Date();
							tblMchtSettleReserveTmp.setReserveAuditTime(sdf.format(date1));
							tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
							//0备款成功
							tblMchtSettleReserveTmp.setReserveSettleStatus("0");
							//0成功   审核状态
							tblMchtSettleReserveTmp.setReserveStatus("0");
			//				tblMchtSettleReserveTmp.setReservePayStatus("终审完毕");
							
							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
									+ ",'0','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + sdf.format(date1) + "'"
									+ ",'" + getOperator().getOprId() + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("备款后台处理失败："+e);
							return returnService("备款后台处理失败！请联系管理员！");
						}
					}else{
						try {
							//临式表
							tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date date1 = new Date();
							tblMchtSettleReserveTmp.setReserveAuditTime(sdf.format(date1));
							tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
							//2备款中   备款状态
							tblMchtSettleReserveTmp.setReserveSettleStatus("1");
							//0成功   审核状态
							tblMchtSettleReserveTmp.setReserveStatus("0");
			//				tblMchtSettleReserveTmp.setReservePayStatus(processResult);
							
							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
									+ ",'1','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + sdf.format(date1) + "'"
									+ ",'" + getOperator().getOprId() + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("备款后台处理失败："+e);
							return returnService("备款后台处理失败！请联系管理员！");
						}	
					}
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}
		}
		log("商户备款审核通过成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 商户备款审核     拒绝
	 * @return
	 */
	public String redempRefuse(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		
		TblMchtSettleReserve tblMchtSettleReserve;
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");
			String reserveLaunchTime = jsonBean.getJSONDataAt(i).getString("reserveLaunchTime");
			String reserveLaunchName = jsonBean.getJSONDataAt(i).getString("reserveLaunchName");
			String reserveStatus = jsonBean.getJSONDataAt(i).getString("reserveStatus");
			
			System.out.println("发起人:"+reserveLaunchName +"		发起时间:"+reserveLaunchTime);
			log.info("发起人:"+reserveLaunchName +"		发起时间:"+reserveLaunchTime);
			try{
				if (getOperator().getOprId().equals(reserveLaunchName)) {
					l++ ;
					log.info("发起人与审核人同一用户！");
					return returnService("发起人与审核人同一用户！");
				}
			}catch (Exception e) {
				log.error("审核判断数据出错"+e.getMessage());
			}
			
			//1位修改待审核    2位备款待审核
			if(reserveStatus.equals("1")){
				try {
					tblMchtSettleReserve = t9130101BO.getReserve(reserveId);
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					
					if(tblMchtSettleReserve==null){
						
						tblMchtSettleReserve = new TblMchtSettleReserve();
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						Date date = new Date();
						
						tblMchtSettleReserve.setReserveId(tblMchtSettleReserveTmp.getReserveId());
						tblMchtSettleReserve.setReserveTime(tblMchtSettleReserveTmp.getReserveTime());
						tblMchtSettleReserve.setRedemptionMoney(tblMchtSettleReserveTmp.getRedemptionMoney());
						tblMchtSettleReserve.setReserveSettleMoney(tblMchtSettleReserveTmp.getReserveSettleMoney());
						tblMchtSettleReserve.setReserveMoney(tblMchtSettleReserveTmp.getReserveMoney());
						//正常
						tblMchtSettleReserve.setReserveStatus("0");
						tblMchtSettleReserve.setReserveSettleStatus(tblMchtSettleReserveTmp.getReserveSettleStatus());
						tblMchtSettleReserve.setReserveLaunchTime(tblMchtSettleReserveTmp.getReserveLaunchTime());
						tblMchtSettleReserve.setReserveLaunchName(tblMchtSettleReserveTmp.getReserveLaunchName());
						tblMchtSettleReserve.setReserveAuditTime(sdf.format(date));
						tblMchtSettleReserve.setReserveAuditName(getOperator().getOprId());
						
						t9130101BO.savaReserve(tblMchtSettleReserve);
					}
					
					//正常
					tblMchtSettleReserveTmp.setReserveStatus("0");
					//返回备款金额
					tblMchtSettleReserveTmp.setReserveMoney(tblMchtSettleReserve.getReserveMoney());
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					tblMchtSettleReserveTmp.setReserveAuditTime(sdf.format(date));
					tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(reserveStatus.equals("2")){
				
				try {
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					
					tblMchtSettleReserveTmp.setReserveStatus("0");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新审核时间
					tblMchtSettleReserveTmp.setReserveAuditTime(sdf.format(date));
					//更新审核人
					tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				} catch (Exception e) {
					// TODO: handle exception
					returnService(rspCode,e);
				}
			}
		}
		log("商户备款审核拒绝成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 修改备款金额
	 * @return
	 */
	public String addRedemp(){
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
		
		tblMchtSettleReserveTmp.setReserveMoney(reserveMoney);
		//改变状态 1.修改待审核
		tblMchtSettleReserveTmp.setReserveStatus("1");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		//更新发起时间
		tblMchtSettleReserveTmp.setReserveLaunchTime(sdf.format(date));
		//更新发起人
		tblMchtSettleReserveTmp.setReserveLaunchName(getOperator().getOprId());
		try {
			rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("修改后台处理失败：" + e);
			return returnService("修改后台处理失败！请联系管理员！");
		}
		log("修改备款金额成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 备款
	 * @return
	 */
	public String redemp(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");
			
			try {
				tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
				//2备款待审核
				tblMchtSettleReserveTmp.setReserveStatus("2");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				//更新发起时间
				tblMchtSettleReserveTmp.setReserveLaunchTime(sdf.format(date));
				//更新发起人
				tblMchtSettleReserveTmp.setReserveLaunchName(getOperator().getOprId());
				
				rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
			} catch (Exception e) {
				// TODO: handle exception
				returnService(rspCode,e);
			}
		}
		log("商户备款成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 商户回填
	 * @return
	 */
	/*public String redempBackFill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");//id
			String reserveAuditTime = jsonBean.getJSONDataAt(i).getString("reserveAuditTime"); //审核时间
			
			try {
				JsonPacket jsonPacket = new JsonPacket();
				
				Map map1 = new Properties();
				map1.put("Version", "1.0.0");//版本号
				map1.put("Encoding", "UTF-8");//编码方式 
				map1.put("TxnType", "");//业务类别
				map1.put("BiginDate", reserveAuditTime); //起始时间
				map1.put("EndDate", reserveAuditTime);//结束时间
				map1.put("DateFlg", "B");//日期类型  A：经办日期；B：期望日期
				map1.put("Yurref", reserveId);//业务参考号
				
				Map map = new Properties();
				map.put("SDKPAYQYX", map1);
				jsonPacket.putProperty("CMBSDKPGK", map);
				//生成请求报文
				String json = jsonPacket.toJsonString();
				log.info("生成请求报文:"+json);
				//连接前置机，发送请求报文，获得返回报文
				String result = T91301Action.sendRequest(json);
				result = result.replace("GBK", "UTF-8");
				String processResult = T91301Action.processResult(result);
				if (processResult.toUpperCase().equals("FIN")) {
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("0");
					tblMchtSettleReserveTmp.setReservePayStatus("完成");

					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);		
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'0','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("AUT")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("等待审批");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("NTE")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("终审完毕");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("WCF")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("订单待确认");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("BNK")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("银行处理中");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("ACK")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("等待确认");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("APD")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("待银行确认");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.toUpperCase().equals("OPR")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("2");
					tblMchtSettleReserveTmp.setReservePayStatus("数据接收中");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.equals("F")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("1");
					tblMchtSettleReserveTmp.setReservePayStatus("银行支付失败");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else if(processResult.equals("B")){
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("1");
					tblMchtSettleReserveTmp.setReservePayStatus(processResult);
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}else{
					//临式表
					tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
					//2备款中   备款状态
					tblMchtSettleReserveTmp.setReserveSettleStatus("1");
					tblMchtSettleReserveTmp.setReservePayStatus("银行支付被退票");
					
					String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
					commQueryDAO.excute(reserveDel);
					
					String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
							+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
							+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
							+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
							+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
					commQueryDAO.excute(reserveDelInf);
					
					rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		log("商户回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}*/
	
	/**
	 * 手动回填失败
	 * @return
	 */
	public String redempBackFillSDFail(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");//客户编号
			
			try {
				tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
				tblMchtSettleReserveTmp.setReserveSettleStatus("1");
	//			tblMchtSettleReserveTmp.setReservePayStatus("客户手动回填失败");
				
				String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
				commQueryDAO.excute(reserveDel);		
				
				String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
						+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
						+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveSettleStatus() + "','" + tblMchtSettleReserveTmp.getReservePayStatus() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "','" + tblMchtSettleReserveTmp.getReserveLaunchName() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveAuditTime() + "','" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
				commQueryDAO.excute(reserveDelInf);
				
				rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("手动回填失败后台处理失败："+e);
				return returnService("手动回填失败处理失败！请联系管理员！");
			}
		}
		log("商户回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 手动回填成功
	 * @return
	 */
	public String redempBackFillSDSuccess(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");//客户编号
			
			try {
				tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
				tblMchtSettleReserveTmp.setReserveSettleStatus("0");
	//			tblMchtSettleReserveTmp.setReservePayStatus("客户手动回填成功");
				
				String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
				commQueryDAO.excute(reserveDel);		
				
				String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
						+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
						+ "RESERVE_AUDIT_NAME) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveSettleStatus() + "','" + tblMchtSettleReserveTmp.getReservePayStatus() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "','" + tblMchtSettleReserveTmp.getReserveLaunchName() + "'"
						+ ",'" + tblMchtSettleReserveTmp.getReserveAuditTime() + "','" + tblMchtSettleReserveTmp.getReserveAuditName() + "')";
				commQueryDAO.excute(reserveDelInf);
				
				rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("手动回填成功后台处理失败："+e);
				return returnService("手动回填成功处理失败！请联系管理员！");
			}
		}
		log("商户回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 人行集中缴存备款
	 * @return
	 */
	public String focusReserve(){
		String BACKURL = SysParamUtil.getParam(SysParamConstants.BACKURL);//后台通知地址
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String INSSEQ = SysParamUtil.getParam(SysParamConstants.INSSEQ);//头寸序号
		String CERTID = SysParamUtil.getParam(SysParamConstants.CERTID);//证书ID
		String SIGNATURE = SysParamUtil.getParam(SysParamConstants.SIGNATURE);//签名
		String SIGNMETHOD = SysParamUtil.getParam(SysParamConstants.SIGNMETHOD);//签名方法
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收付款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收付款方账户名称
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		
	//	String sql = "select MAX(trim(FOCUS_ID)) from TBL_FOCUS_RESERVE";
	//	String focusMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		String focusNo;
		
		//生成交易流水号
		Random random = new Random();
		focusNo = nowdate + ACQINSCODE + random.nextInt(99999999);
		 
		/*if (focusMax == "") {
			focusNo = "400000001";
			focusNo = nowdate + ACQINSCODE + focusNo;
		}else {//2018101912345400000001
			int i = Integer.parseInt(focusMax);
			i = i + 1;
			//如5在前面补0  直到凑够6位数  000005   大于6直接输出i
			focusNo = String.format("%06d", i);
		}*/
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String dates = sdf2.format(date);
		
		String sql = "select ACCOUNT_NAME, BANK_ACCOUNT from TBL_BANKNO_INFO_TMP";
		List<Object[]> bankList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object bankAccountName = null;
		Object bankAccount = null;
		for (Object[] objects : bankList) {
			bankAccountName = objects[0];
			bankAccount = objects[1];
		}
		
		//人行入账备款交易
		JsonPacket jsonPacket = new JsonPacket();
		jsonPacket.setVersion("1.0.0");//版本号
		jsonPacket.setEncoding("UTF-8");//编码方式
		jsonPacket.setCertId(CERTID);//证书ID
		jsonPacket.setSignature(SIGNATURE);//签名
		jsonPacket.setSignMethod(SIGNMETHOD);//签名方法
		jsonPacket.setTxnType("01");//交易类型     01备款
		jsonPacket.setBackUrl(BACKURL);//后台通知地址
		jsonPacket.setTxnNo(focusNo);//交易流水号
		jsonPacket.setAcqInsCode(ACQINSCODE);//机构代码
		jsonPacket.setTxnDate(focusDate);//交易日期
		jsonPacket.setSndTime(dates);//发送时间
		jsonPacket.setInsSeq(INSSEQ);//头寸序号
		jsonPacket.setPayeeAcctNo(String.valueOf(bankAccount));//收款方账号
		jsonPacket.setPayeeAcctName(String.valueOf(bankAccountName));//收款方账户名称
		jsonPacket.setCurrencyCode("156");//币种
		jsonPacket.setTxnAmt(focusMoney);//金额
		
		//对象转换成json字符串
		String json = JSON.toJSONString(jsonPacket);
		log.info("生成请求报文:"+json);
		//发送请求报文，获得返回报文
		String result = T91301Action.sendRequest(json);
		result = result.replace("GBK", "UTF-8");
		//处理返回的结果
		String processResult = T91301Action.processResult(result);
		//FIN成功
		if(processResult.toUpperCase().equals("FIN")){
			try {
				TblFocusReserve tblFocusReserve = new TblFocusReserve();
				tblFocusReserve.setFocusId(focusNo);
				tblFocusReserve.setFocusAccount(String.valueOf(bankAccount));
				tblFocusReserve.setFocusAccountName(String.valueOf(bankAccountName));
				tblFocusReserve.setFocusMoney(focusMoney);
				tblFocusReserve.setFocusDate(focusDate);
				//0备款成功
				tblFocusReserve.setFocusStatus("0");
				
				t9130101BO.saveFocus(tblFocusReserve);
				//虚拟记账余额查询交易流水号
				String focusNo1 = nowdate + ACQINSCODE + random.nextInt(99999999);
				//人行集中缴存备款成功后，自动发起银联虚拟记账余额查询交易,获得对象
				JsonPacket focusQuery = T91301Action.focusQuery(focusNo1,focusDate,dates);
				
				//人行集中缴存回款交易流水号
				String focusNo2 = nowdate + ACQINSCODE + random.nextInt(99999999);
				//主动发起人行集中缴存回款交易
				JsonPacket jsonPacket1 = new JsonPacket();
				jsonPacket1.setVersion("1.0.0");//版本号
				jsonPacket1.setEncoding("UTF-8");//编码方式
				jsonPacket1.setCertId(CERTID);//证书ID
				jsonPacket1.setSignature(SIGNATURE);//签名
				jsonPacket1.setSignMethod(SIGNMETHOD);//签名方法
				jsonPacket1.setTxnType("04");//交易类型     04回款
				jsonPacket1.setBackUrl(BACKURL);//后台通知地址
				jsonPacket1.setTxnNo(focusNo2);//交易流水号
				jsonPacket1.setAcqInsCode(ACQINSCODE);//机构代码
				jsonPacket1.setTxnDate(focusDate);//交易日期
				jsonPacket1.setSndTime(dates);//发送时间
				jsonPacket1.setInsSeq(INSSEQ);//头寸序号
				jsonPacket1.setPayeeAcctNo(focusQuery.getAcctNo());//付款方账号
				jsonPacket1.setPayeeAcctName(focusQuery.getAcctName());//付款方账户名称
				jsonPacket1.setCurrencyCode("156");//币种
				jsonPacket1.setTxnAmt(focusQuery.getAcctBal());//回款金额
				
				//对象转换成json字符串
				String json1 = JSON.toJSONString(jsonPacket1);
				log.info("生成请求报文:"+json1);
				//发送请求报文，获得返回报文
				String result1 = T91301Action.sendRequest(json1);
				result1 = result1.replace("GBK", "UTF-8");
				//处理返回的结果
				String processResult1 = T91301Action.processResult(result1);
				//FIN成功
				if(processResult1.toUpperCase().equals("FIN")){
					try {
						TblPaymentReserve tblPaymentReserve = new TblPaymentReserve();
						tblPaymentReserve.setPaymentId(focusNo2);//交易流水号
						tblPaymentReserve.setPaymentAccount(focusQuery.getAcctNo());//回款账户
						tblPaymentReserve.setPaymentAccountName(focusQuery.getAcctName());//回款账户名称
						tblPaymentReserve.setPaymentMoney(focusQuery.getAcctBal());//回款金额
						tblPaymentReserve.setPaymentDate(focusDate);//回款日期
						//0成功
						tblPaymentReserve.setPaymentStatus("0");//回款状态
						
						rspCode = t9130101BO.savePayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						log.error("回款后台处理失败："+e);
						return returnService("回款后台处理失败！请联系管理员！");
					}
				}else{
					try {
						TblPaymentReserve tblPaymentReserve = new TblPaymentReserve();
						tblPaymentReserve.setPaymentId(focusNo2);//交易流水号
						tblPaymentReserve.setPaymentAccount(focusQuery.getAcctNo());//回款账户
						tblPaymentReserve.setPaymentAccountName(focusQuery.getAcctName());//回款账户名称
						tblPaymentReserve.setPaymentMoney(focusQuery.getAcctBal());//回款金额
						tblPaymentReserve.setPaymentDate(focusDate);//回款日期
						//1失败
						tblPaymentReserve.setPaymentStatus("1");//回款状态
						
						rspCode = t9130101BO.savePayment(tblPaymentReserve);
					} catch (Exception e) {
						// TODO: handle exception
						log.error("回款后台处理失败："+e);
						return returnService("回款后台处理失败！请联系管理员！");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error("备款后台处理失败："+e);
				return returnService("备款后台处理失败！请联系管理员！");
			}
		}else{
			try {
				TblFocusReserve tblFocusReserve = new TblFocusReserve();
				tblFocusReserve.setFocusId(focusNo);
				tblFocusReserve.setFocusAccount(String.valueOf(bankAccount));
				tblFocusReserve.setFocusAccountName(String.valueOf(bankAccountName));
				tblFocusReserve.setFocusMoney(focusMoney);
				//1备款失败
				tblFocusReserve.setFocusStatus("1");
				tblFocusReserve.setFocusDate(focusDate);
				
				rspCode = t9130101BO.saveFocus(tblFocusReserve);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("备款后台处理失败："+e);
				return returnService("备款后台处理失败！请联系管理员！");
			}
		}
		log("人行集中缴存备款成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 银联虚拟记账余额查询
	 * @return
	 */
	public static JsonPacket focusQuery(String focusNo1,String focusDate1,String dates){
		String BACKURL = SysParamUtil.getParam(SysParamConstants.BACKURL);//后台通知地址
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String INSSEQ = SysParamUtil.getParam(SysParamConstants.INSSEQ);//头寸序号
		String CERTID = SysParamUtil.getParam(SysParamConstants.CERTID);//证书ID
		String SIGNATURE = SysParamUtil.getParam(SysParamConstants.SIGNATURE);//签名
		String SIGNMETHOD = SysParamUtil.getParam(SysParamConstants.SIGNMETHOD);//签名方法
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		String ACCTNO = SysParamUtil.getParam(SysParamConstants.ACCTNO);//虚拟账号
		String ACCTNAME = SysParamUtil.getParam(SysParamConstants.ACCTNAME);//虚拟账户名称
		
		JsonPacket jsonPacket = new JsonPacket();
		jsonPacket.setVersion("1.0.0");//版本号
		jsonPacket.setEncoding("UTF-8");//编码方式
		jsonPacket.setCertId(CERTID);//证书ID
		jsonPacket.setSignature(SIGNATURE);//签名
		jsonPacket.setSignMethod(SIGNMETHOD);//签名方法
		jsonPacket.setTxnType("41");//交易类型     41实时余额查询
		jsonPacket.setTxnNo(focusNo1);//交易流水号
		jsonPacket.setAcqInsCode(ACQINSCODE);//机构代码
		jsonPacket.setTxnDate(focusDate1);//交易日期
		jsonPacket.setSndTime(dates);//发送时间
		jsonPacket.setAcctNo(ACCTNO);//虚拟账号
		jsonPacket.setCurrencyCode("156");//币种
		
		//对象转换成json字符串
		String json = JSON.toJSONString(jsonPacket);
		log.info("生成请求报文:"+json);
		//发送请求报文，获得返回报文
		String result = T91301Action.sendRequest(json);
		result = result.replace("GBK", "UTF-8");
		//处理返回的结果  得到返回对象
		JsonPacket processResult = T91301Action.processResultTS(result);
		
		return processResult;
	}
	
	/**
	 * 人行集中缴存回填失败
	 * @return
	 */
	public String focusFail(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblFocusReserve tblFocusReserve;
		for (int i = 0; i < len; i++) {
			String focusId = jsonBean.getJSONDataAt(i).getString("focusId");//主键
			String focusAccount = jsonBean.getJSONDataAt(i).getString("focusAccount");//备款账户
			String focusAccountName = jsonBean.getJSONDataAt(i).getString("focusAccountName");//备款账户名称
			String focusMoney = jsonBean.getJSONDataAt(i).getString("focusMoney");//备款金额
			String focusStatus = jsonBean.getJSONDataAt(i).getString("focusStatus");//备款状态
			String focusDate = jsonBean.getJSONDataAt(i).getString("focusDate");//备款日期
			
			try {
				tblFocusReserve = new TblFocusReserve();
				
				tblFocusReserve.setFocusId(focusId);
				tblFocusReserve.setFocusAccount(focusAccount);
				tblFocusReserve.setFocusAccountName(focusAccountName);
				tblFocusReserve.setFocusMoney(focusMoney);
				//1失败
				tblFocusReserve.setFocusStatus("1");
				tblFocusReserve.setFocusDate(focusDate);
				
				rspCode = t9130101BO.upFocus(tblFocusReserve);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		log("人行集中缴存回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 人行集中缴存回填成功
	 * @return
	 */
	public String focusSuccess(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblFocusReserve tblFocusReserve;
		for (int i = 0; i < len; i++) {
			String focusId = jsonBean.getJSONDataAt(i).getString("focusId");//主键
			String focusAccount = jsonBean.getJSONDataAt(i).getString("focusAccount");//备款账户
			String focusAccountName = jsonBean.getJSONDataAt(i).getString("focusAccountName");//备款账户名称
			String focusMoney = jsonBean.getJSONDataAt(i).getString("focusMoney");//备款金额
			String focusStatus = jsonBean.getJSONDataAt(i).getString("focusStatus");//备款状态
			String focusDate = jsonBean.getJSONDataAt(i).getString("focusDate");//备款日期
			
			try {
				tblFocusReserve = new TblFocusReserve();
				
				tblFocusReserve.setFocusId(focusId);
				tblFocusReserve.setFocusAccount(focusAccount);
				tblFocusReserve.setFocusAccountName(focusAccountName);
				tblFocusReserve.setFocusMoney(focusMoney);
				//0成功
				tblFocusReserve.setFocusStatus("0");
				tblFocusReserve.setFocusDate(focusDate);
				
				rspCode = t9130101BO.upFocus(tblFocusReserve);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		log("人行集中缴存回填成功成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 手动回款回填失败
	 * @return
	 */
	public String paymentFail(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblPaymentReserve tblPaymentReserve;
		for (int i = 0; i < len; i++) {
			String paymentId = jsonBean.getJSONDataAt(i).getString("paymentId");//主键
			String paymentAccount = jsonBean.getJSONDataAt(i).getString("paymentAccount");//回款金额
			String paymentAccountName = jsonBean.getJSONDataAt(i).getString("paymentAccountName");//回款金额
			String paymentMoney = jsonBean.getJSONDataAt(i).getString("paymentMoney");//回款金额
			String paymentStatus = jsonBean.getJSONDataAt(i).getString("paymentStatus");//回款状态
			String paymentDate = jsonBean.getJSONDataAt(i).getString("paymentDate");//回款日期
			
			try {
				tblPaymentReserve = new TblPaymentReserve();
				
				tblPaymentReserve.setPaymentId(paymentId);
				tblPaymentReserve.setPaymentAccount(paymentAccount);
				tblPaymentReserve.setPaymentAccountName(paymentAccountName);
				tblPaymentReserve.setPaymentMoney(paymentMoney);
				//1失败
				tblPaymentReserve.setPaymentStatus("1");
				tblPaymentReserve.setPaymentDate(paymentDate);
				
				rspCode = t9130101BO.upPayment(tblPaymentReserve);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		log("人行集中缴存回款回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 手动回款回填成功
	 * @return
	 */
	public String paymentSuccess(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblPaymentReserve tblPaymentReserve;
		for (int i = 0; i < len; i++) {
			String paymentId = jsonBean.getJSONDataAt(i).getString("paymentId");//主键
			String paymentAccount = jsonBean.getJSONDataAt(i).getString("paymentAccount");//回款金额
			String paymentAccountName = jsonBean.getJSONDataAt(i).getString("paymentAccountName");//回款金额
			String paymentMoney = jsonBean.getJSONDataAt(i).getString("paymentMoney");//回款金额
			String paymentStatus = jsonBean.getJSONDataAt(i).getString("paymentStatus");//回款状态
			String paymentDate = jsonBean.getJSONDataAt(i).getString("paymentDate");//回款日期
			
			try {
				tblPaymentReserve = new TblPaymentReserve();
				
				tblPaymentReserve.setPaymentId(paymentId);
				tblPaymentReserve.setPaymentAccount(paymentAccount);
				tblPaymentReserve.setPaymentAccountName(paymentAccountName);
				tblPaymentReserve.setPaymentMoney(paymentMoney);
				//0成功
				tblPaymentReserve.setPaymentStatus("0");
				tblPaymentReserve.setPaymentDate(paymentDate);
				
				rspCode = t9130101BO.upPayment(tblPaymentReserve);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		log("人行集中缴存回款回填成功成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	
	/**
	 * 发送请求报文，获得返回报文
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
			//转换成byte类型UTF-8编码格式   方便计算长度
			byte[] dates = data.getBytes("UTF-8");			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", "" + dates.length);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			System.out.println("\n");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("\n");
			os.write(data.toString().getBytes("UTF-8"));
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
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
	private static String processResult(String result) {
		String str="";
		if (result != null && result.length() > 0) {
			//json字符串转java对象
			JSONObject obj = JSONObject.fromObject(result);
			JsonPacket jsonPacket = (JsonPacket) obj.toBean(obj, JsonPacket.class);
			if (jsonPacket != null) {
				String sResCode = jsonPacket.getRespCode();
				if (sResCode.equals("00")) {
					return "FIN";
				}else {
					str = "备款失败：" + jsonPacket.getRespMsg();
					log.error("备款失败：" + jsonPacket.getRespMsg());
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
	 * @param result
	 * @return
	 */
	private static JsonPacket processResultTS(String result) {
	//	String str="";
		if (result != null && result.length() > 0) {
			//json字符串转java对象
			JSONObject obj = JSONObject.fromObject(result);
			JsonPacket jsonPacket = (JsonPacket) obj.toBean(obj, JsonPacket.class);
			if (jsonPacket != null) {
				String sResCode = jsonPacket.getRespCode();
				if (sResCode.equals("00")) {
					return jsonPacket;//返回对象
				}else {
		//			str = "备款失败：" + jsonPacket.getRespMsg();
					log.error("备款失败：" + jsonPacket.getRespMsg());
				}
			} else {
		//		str = "响应报文解析失败" ;
				log.error("响应报文解析失败");
			}
		}
		return null;
	}
	
	
	
	
	
	
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getPreDate() {
		return preDate;
	}
	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}
	public String getFocusDate() {
		return focusDate;
	}
	public void setFocusDate(String focusDate) {
		this.focusDate = focusDate;
	}
	public String getFocusMoney() {
		return focusMoney;
	}
	public void setFocusMoney(String focusMoney) {
		this.focusMoney = focusMoney;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getReserveMoney() {
		return reserveMoney;
	}
	public void setReserveMoney(String reserveMoney) {
		this.reserveMoney = reserveMoney;
	}
	public String getInfList() {
		return infList;
	}
	public void setInfList(String infList) {
		this.infList = infList;
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
