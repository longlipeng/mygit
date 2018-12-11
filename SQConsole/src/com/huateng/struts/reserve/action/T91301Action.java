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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.FsasService;
import com.huateng.sdk.SDKConfig;
import com.huateng.sdk.LogUtil;

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
	
//	@SuppressWarnings("unchecked")
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
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
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
			String reserveBatch = jsonBean.getJSONDataAt(i).getString("reserveBatch");
			
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
			}else if(reserveStatus.equals("2")){//备款
				
				SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String txnNo = sdf.format(new Date());
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
				String txnDate = sdf1.format(new Date());
				SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
				
				Map<String,String> contentData = new HashMap<String, String>();
				//***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***//
				contentData.put("version", DemoBase.version);                  //版本号
				contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
				contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
				contentData.put("txnType", "01");                                   //交易类型 01-备款
			    contentData.put("txnNo", txnNo);                  //交易流水号  必须是yyyyMMddHHmmssSSS格式
			    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
			    contentData.put("txnDate", txnDate);                               //交易日期
			    contentData.put("sndTime", sdf2.format(new Date()));      //发送时间 格式HHmmss
			    contentData.put("insSeq", "01");                                  //头寸序号   以机构代码 +头寸序号在银联系统内对应的银行账户为准
			    contentData.put("payeeAcctNo", PAYEEACCTNO);                //收款方账号
			    contentData.put("payeeAcctName", PAYEEACCTNAME);        //收款方账户名称
			    contentData.put("currencyCode", "156");                          //币种
			    contentData.put("txnAmt",reserveMoney);                                 //金额    不能带小数点    
			    contentData.put("backUrl", DemoBase.backUrl);
			    contentData.put("remark", "备款");                    //附言
			    contentData.put("reqReserved", "1");     //请求方保留域         1表示商户结算备款
			    contentData.put("reserved", "");                  //保留域
			    
			    /**对请求参数进行签名并发送http post请求，接收同步应答报文**/
				Map<String, String> reqData = FsasService.sign(contentData,DemoBase.encoding);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
				String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			//交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的 fsassdk.backTransUrl
				Map<String, String> rspData = FsasService.doPost(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

				StringBuffer parseStr = new StringBuffer("");
				if(!rspData.isEmpty()){
					if(FsasService.validate(rspData, DemoBase.encoding)){
						LogUtil.writeLog("验证签名成功");
						String respCode = rspData.get("respCode") ;
						if(("00").equals(respCode)){
							//成功 
							//TODO
							try {
								//临式表
								tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
								
								tblMchtSettleReserveTmp.setReserveAuditTime(txnDate);
								tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
								//2备款受理中
								tblMchtSettleReserveTmp.setReserveSettleStatus("2");
								//0成功   审核状态
								tblMchtSettleReserveTmp.setReserveStatus("0");
								//支付状态
								tblMchtSettleReserveTmp.setReservePayStatus("备款已受理");
								//交易流水号
								tblMchtSettleReserveTmp.setReserveBatch(txnNo);
								
								String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
								commQueryDAO.excute(reserveDel);		
								
								String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
										+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
										+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
										+ ",'2','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + txnDate + "'"
										+ ",'" + getOperator().getOprId() + "','" + txnNo + "')";
								commQueryDAO.excute(reserveDelInf);
								
								rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("备款后台处理失败："+e);
								return returnService("备款后台处理失败！请联系管理员！");
							}
						}else{
							//其他应答码为失败请排查原因
							//TODO
							try {
								//临式表
								tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
								
								SimpleDateFormat sdfs1 = new SimpleDateFormat("yyyyMMdd");
								Date date1 = new Date();
								tblMchtSettleReserveTmp.setReserveAuditTime(txnDate);
								tblMchtSettleReserveTmp.setReserveAuditName(getOperator().getOprId());
								//1备款失败   备款状态
								tblMchtSettleReserveTmp.setReserveSettleStatus("1");
								//0成功   审核状态
								tblMchtSettleReserveTmp.setReserveStatus("0");
								//支付状态
								if(("39").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("交易不在受理时间范围内");
								}else if(("10").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("报文格式错误");
								}else if(("38").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("银联风险受限");
								}else if(("90").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("虚拟记账余额不足");
								}else if(("91").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("划付失败");
								}else if(("12").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("重复交易");
								}else if(("11").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("验证签名失败");
								}else if(("02").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("系统未开放或暂时关闭，请稍后再试");
								}else if(("05").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("交易已受理，请稍后查询交易结果");
								}else if(("13").equals(respCode)){
									tblMchtSettleReserveTmp.setReservePayStatus("报文交易要素缺失");
								}
								//交易流水号
								tblMchtSettleReserveTmp.setReserveBatch(txnNo);
								
								String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
								commQueryDAO.excute(reserveDel);		
								
								String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
										+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
										+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','0'"
										+ ",'1','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
										+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + txnDate + "'"
										+ ",'" + getOperator().getOprId() + "','" + txnNo + "')";
								commQueryDAO.excute(reserveDelInf);
								
								rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("备款后台处理失败："+e);
								return returnService("备款后台处理失败！请联系管理员！");
							}
						}
					}else{
//						LogUtil.writeErrorLog("验证签名失败");
						//TODO 检查验证签名失败的原因
						return returnService("验证签名失败");
					}
				}else{
					//未返回正确的http状态
//					LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
					return returnService("未获取到返回报文或返回http状态码非200");
				}
//				String reqMessage = DemoBase.genHtmlResult(reqData);
//				String rspMessage = DemoBase.genHtmlResult(rspData);
				LogUtil.writeLog("备款交易请求报文:"+reqData+" + 应答报文:"+rspData+parseStr);
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
	 * 商户回填备款状态查询
	 * @return
	 */
	public String redempBackFill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		TblMchtSettleReserveTmp tblMchtSettleReserveTmp;
		for (int i = 0; i < len; i++) {
			String reserveId = jsonBean.getJSONDataAt(i).getString("reserveId");//id
			String reserveAuditTime = jsonBean.getJSONDataAt(i).getString("reserveAuditTime"); //审核时间
			String reserveBatch = jsonBean.getJSONDataAt(i).getString("reserveBatch"); //业务流水号
			
			
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
			
			Map<String, String> contentData = new HashMap<String, String>();
			
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
			String txnDate = sdfQuery1.format(new Date());

			/*** 银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", DemoBase.version); // 版本号
			contentData.put("encoding", DemoBase.encoding); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
			contentData.put("txnType", "05"); // 交易类型 05-交易状态查询（单笔）
			contentData.put("sndTime", DemoBase.getSendTime()); // 发送时间 格式HHmmss

			contentData.put("txnNo", sdfs.format(new Date())); // 交易流水号
			contentData.put("acqInsCode", ACQINSCODE); // 机构代码
			contentData.put("txnDate", txnDate); // 交易日期
			contentData.put("origTxnType", "01"); // 原交易类型01：备款 02：商户资金结算
															// 04：回款
			contentData.put("origTxnNo", reserveBatch); // 原交易流水号
			contentData.put("origTxnDate", reserveAuditTime); // 原交易日期

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> reqData = FsasService.sign(contentData,
					DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的
																				// fsassdk.backTransUrl
			Map<String, String> rspData = FsasService.doPost(reqData,
					requestBackUrl, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			
			
			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			StringBuffer parseStr = new StringBuffer("");
			if (!rspData.isEmpty()) {
				if (FsasService.validate(rspData, DemoBase.encoding)) {
					LogUtil.writeLog("验证签名成功");
					String respCode = rspData.get("respCode");
					String respMsg = rspData.get("respMsg");
					String origRespCode = rspData.get("origRespCode");
					if (("00").equals(respCode)) {
						// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
						// TODO
						if (("00").equals(origRespCode)) {
							// 代表原交易成功 如不成功根据原交易应答码做相应处理
							// TODO
							//临式表
							tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
							//0备款成功   备款状态
							tblMchtSettleReserveTmp.setReserveSettleStatus("0");

							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
									+ ",'0','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "','" + tblMchtSettleReserveTmp.getReserveBatch() + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						} else if (("03").equals(origRespCode)
								|| ("05").equals(origRespCode)) {
							// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
							// TODO
							return returnService("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
						} else {
							 //其它根据需要作处理 
							// TODO
							//临式表
							tblMchtSettleReserveTmp = t9130101BO.getReserveTmp(reserveId);
							//1备款失败   备款状态
							tblMchtSettleReserveTmp.setReserveSettleStatus("1");

							String reserveDel = "delete from TBL_MCHT_SETTLE_RESERVE where RESERVE_ID = '" + tblMchtSettleReserveTmp.getReserveId() + "'";
							commQueryDAO.excute(reserveDel);		
							
							String reserveDelInf = "insert into TBL_MCHT_SETTLE_RESERVE(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, "
									+ "RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, "
									+ "RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('" + tblMchtSettleReserveTmp.getReserveId() + "','" + tblMchtSettleReserveTmp.getReserveTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getRedemptionMoney() + "','" + tblMchtSettleReserveTmp.getReserveSettleMoney() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveMoney() + "','" + tblMchtSettleReserveTmp.getReserveStatus() + "'"
									+ ",'1','" + tblMchtSettleReserveTmp.getReservePayStatus() + "','" + tblMchtSettleReserveTmp.getReserveLaunchTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveLaunchName() + "','" + tblMchtSettleReserveTmp.getReserveAuditTime() + "'"
									+ ",'" + tblMchtSettleReserveTmp.getReserveAuditName() + "','" + tblMchtSettleReserveTmp.getReserveBatch() + "')";
							commQueryDAO.excute(reserveDelInf);
							
							rspCode = t9130101BO.addRedempTmp(tblMchtSettleReserveTmp);
						}
					} else {
						// 其他应答码为失败请排查原因
						// TODO
						LogUtil.writeErrorLog("查询失败:" + respCode + "失败信息:" + respMsg);
						return returnService("查询失败:" + respCode + "失败信息:" + respMsg);
					}
				} else {
//					LogUtil.writeErrorLog("验证签名失败");
					// TODO 检查验证签名失败的原因
					return returnService("验证签名失败");
				}
			} else {
				// 未返回正确的http状态
//				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
				return returnService("未获取到返回报文或返回http状态码非200");
			}
		}
		log("商户回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 人行集中缴存备款查询备款状态
	 * @return
	 */
	public String focusBackFill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		
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
			
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
			
			Map<String, String> contentData = new HashMap<String, String>();
			
			SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
			String txnDate = sdfQuery1.format(new Date());

			/*** 银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", DemoBase.version); // 版本号
			contentData.put("encoding", DemoBase.encoding); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
			contentData.put("txnType", "05"); // 交易类型 05-交易状态查询（单笔）
			contentData.put("sndTime", DemoBase.getSendTime()); // 发送时间 格式HHmmss

			contentData.put("txnNo", sdfQuery.format(new Date())); // 交易流水号
			contentData.put("acqInsCode", ACQINSCODE); // 机构代码
			contentData.put("txnDate", txnDate); // 交易日期
			contentData.put("origTxnType", "01"); // 原交易类型01：备款 02：商户资金结算
															// 04：回款
			contentData.put("origTxnNo", focusId); // 原交易流水号
			contentData.put("origTxnDate", focusDate); // 原交易日期

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> reqData = FsasService.sign(contentData,
					DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的
																				// fsassdk.backTransUrl
			Map<String, String> rspData = FsasService.doPost(reqData,
					requestBackUrl, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			StringBuffer parseStr = new StringBuffer("");
			if (!rspData.isEmpty()) {
				if (FsasService.validate(rspData, DemoBase.encoding)) {
					LogUtil.writeLog("验证签名成功");
					String respCode = rspData.get("respCode");
					String respMsg = rspData.get("respMsg");
					String origRespCode = rspData.get("origRespCode");
					if (("00").equals(respCode)) {
						// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
						// TODO
						if (("00").equals(origRespCode)) {
							// 代表原交易成功 如不成功根据原交易应答码做相应处理
							// TODO
							try {
								tblFocusReserve = t9130101BO.getFocus(focusId);
								//0成功
								tblFocusReserve.setFocusStatus("0");
								
								rspCode = t9130101BO.upFocus(tblFocusReserve);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else if (("03").equals(origRespCode)
								|| ("05").equals(origRespCode)) {
							// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
							// TODO
							return returnService("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
						} else {
							 //其它根据需要作处理 
							// TODO
							try {
								tblFocusReserve = t9130101BO.getFocus(focusId);
								//1失败
								tblFocusReserve.setFocusStatus("1");
								
								rspCode = t9130101BO.upFocus(tblFocusReserve);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					} else if (("03").equals(respCode)
							|| ("05").equals(respCode)) {
						// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
						// TODO
						return returnService("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
					} else {
						// 其他应答码为失败请排查原因
						// TODO
						LogUtil.writeErrorLog("查询失败:" + respCode + "失败信息:" + respMsg);
						return returnService("查询失败:" + respCode + "失败信息:" + respMsg);
					}
				} else {
//					LogUtil.writeErrorLog("验证签名失败");
					// TODO 检查验证签名失败的原因
					return returnService("验证签名失败");
				}
			} else {
				// 未返回正确的http状态
//				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
				return returnService("未获取到返回报文或返回http状态码非200");
			}
		}
		log("商户回填成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 人行集中缴存回款查询回款状态
	 * @return
	 */
	public String paymentBackFail(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		
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
			
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
			
			Map<String, String> contentData = new HashMap<String, String>();
			
			SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
			String txnDate = sdfQuery1.format(new Date());

			/*** 银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", DemoBase.version); // 版本号
			contentData.put("encoding", DemoBase.encoding); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); // 签名方法
			contentData.put("txnType", "05"); // 交易类型 05-交易状态查询（单笔）
			contentData.put("sndTime", DemoBase.getSendTime()); // 发送时间 格式HHmmss

			contentData.put("txnNo", sdfQuery.format(new Date())); // 交易流水号
			contentData.put("acqInsCode", ACQINSCODE); // 机构代码
			contentData.put("txnDate", txnDate); // 交易日期
			contentData.put("origTxnType", "04"); // 原交易类型01：备款 02：商户资金结算
															// 04：回款
			contentData.put("origTxnNo", paymentId); // 原交易流水号
			contentData.put("origTxnDate", paymentDate); // 原交易日期

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> reqData = FsasService.sign(contentData,
					DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的
																				// fsassdk.backTransUrl
			Map<String, String> rspData = FsasService.doPost(reqData,
					requestBackUrl, DemoBase.encoding); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			StringBuffer parseStr = new StringBuffer("");
			if (!rspData.isEmpty()) {
				if (FsasService.validate(rspData, DemoBase.encoding)) {
					LogUtil.writeLog("验证签名成功");
					String respCode = rspData.get("respCode");
					String respMsg = rspData.get("respMsg");
					String origRespCode = rspData.get("origRespCode");
					if (("00").equals(respCode)) {
						// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
						// TODO
						if (("00").equals(origRespCode)) {
							// 代表原交易成功 如不成功根据原交易应答码做相应处理
							// TODO
							try {
								tblPaymentReserve = t9130101BO.getPayment(paymentId);
								//0成功
								tblPaymentReserve.setPaymentStatus("0");
								
								rspCode = t9130101BO.upPayment(tblPaymentReserve);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else if (("03").equals(origRespCode)
								|| ("05").equals(origRespCode)) {
							// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
							// TODO
							return returnService("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
						} else {
							 //其它根据需要作处理 
							// TODO
							try {
								tblPaymentReserve = t9130101BO.getPayment(paymentId);
								//1失败
								tblPaymentReserve.setPaymentStatus("1");
								
								rspCode = t9130101BO.upPayment(tblPaymentReserve);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					} else if (("03").equals(respCode)
							|| ("05").equals(respCode)) {
						// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
						// TODO
						return returnService("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
					} else {
						// 其他应答码为失败请排查原因
						// TODO
						LogUtil.writeErrorLog("查询失败:" + respCode + "失败信息:" + respMsg);
						return returnService("查询失败:" + respCode + "失败信息:" + respMsg);
					}
				} else {
//					LogUtil.writeErrorLog("验证签名失败");
					// TODO 检查验证签名失败的原因
					return returnService("验证签名失败");
				}
			} else {
				// 未返回正确的http状态
//				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
				return returnService("未获取到返回报文或返回http状态码非200");
			}
		}
		log("人行集中缴存回款回填成功成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	public String paymentBackFails(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		
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
		contentData.put("txnType", "04");  //交易类型04：回款（模式二）06：回款（模式一） 	
		contentData.put("backUrl", DemoBase.backUrl);            //后台通知地址
	    contentData.put("txnNo", txnNo);                                    //交易流水号
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
	    contentData.put("txnDate",txnDate);                               //交易日期
	    contentData.put("sndTime", DemoBase.getSendTime());      //发送时间 格式HHmmss
	    contentData.put("insSeq", "01");   //头寸序号   以机构代码 +头寸序号在银联系统内对应的银行账户为准
	    contentData.put("payerAcctNo", PAYEEACCTNO);                //收款方账号
	    contentData.put("payerAcctName", PAYEEACCTNAME);        //收款方账户名称
	    contentData.put("currencyCode", "156");                          //币种
	    contentData.put("txnAmt","112000010");                                 //金额

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
				String respMsg = rspData.get("respMsg");
				if(("00").equals(respCode)){
					//成功 
					//TODO
					try {
						TblPaymentReserve tblPaymentReserve = new TblPaymentReserve();
						tblPaymentReserve.setPaymentId(txnNo);//交易流水号
						tblPaymentReserve.setPaymentAccount(PAYEEACCTNO);//回款账户
						tblPaymentReserve.setPaymentAccountName(PAYEEACCTNAME);//回款账户名称
						tblPaymentReserve.setPaymentMoney("1");//回款金额
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
						tblPaymentReserve.setPaymentAccount(PAYEEACCTNO);//回款账户
						tblPaymentReserve.setPaymentAccountName(PAYEEACCTNAME);//回款账户名称
						tblPaymentReserve.setPaymentMoney("1");//回款金额
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
//				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
				return returnService("验证签名失败");
			}
		}else{
			//未返回正确的http状态
//			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			return returnService("未获取到返回报文或返回http状态码非200");
		}
		log("人行集中缴存回款回填成功成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 商户回填手动回填成功
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
	 * 商户回填手动回填失败
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
		log("商户回填失败成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 人行集中缴存备款
	 * @return
	 */
	public String focusReserve(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String focusNo = sdf.format(new Date());
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String focusDate = sdf1.format(new Date());
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
		
		String sql = "select ACCOUNT_NAME, BANK_ACCOUNT from TBL_BANKNO_INFO_TMP";
		List<Object[]> bankList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object bankAccountName = null;
		Object bankAccount = null;
		for (Object[] objects : bankList) {
			bankAccountName = objects[0];
			bankAccount = objects[1];
		}
		
		Map<String,String> contentData = new HashMap<String, String>();
		//***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***//
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "01");                                   //交易类型 01-备款
	    contentData.put("txnNo", focusNo);                  //交易流水号
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
	    contentData.put("txnDate", focusDate);                               //交易日期
	    contentData.put("sndTime", sdf2.format(new Date()));      //发送时间 格式HHmmss
	    contentData.put("insSeq", "01");                                  //头寸序号   以机构代码 +头寸序号在银联系统内对应的银行账户为准
	    contentData.put("payeeAcctNo", PAYEEACCTNO);                //收款方账号
	    contentData.put("payeeAcctName", PAYEEACCTNAME);        //收款方账户名称
	    contentData.put("currencyCode", "156");                          //币种
	    contentData.put("txnAmt", focusMoney);                                 //金额
	    contentData.put("backUrl", DemoBase.backUrl);      //通知地址
	    contentData.put("remark", "备款");
	    contentData.put("reqReserved", "2");      //请求方保留域         2表示人行集中缴存备款
	    contentData.put("reserved", "");
	    
	    /**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = FsasService.sign(contentData,DemoBase.encoding);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			//交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的 fsassdk.backTransUrl
		Map<String, String> rspData = FsasService.doPost(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		StringBuffer parseStr = new StringBuffer("");
		if(!rspData.isEmpty()){
			if(FsasService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//成功 
					//TODO
					try {
						TblFocusReserve tblFocusReserve = new TblFocusReserve();
						tblFocusReserve.setFocusId(focusNo);
						tblFocusReserve.setFocusAccount(PAYEEACCTNO);
						tblFocusReserve.setFocusAccountName(PAYEEACCTNAME);
						tblFocusReserve.setFocusMoney(focusMoney);
						tblFocusReserve.setFocusDate(focusDate);
						//2备款已受理
						tblFocusReserve.setFocusStatus("2");
						
						rspCode = t9130101BO.saveFocus(tblFocusReserve);
						
						//人行集中缴存备款成功后，自动发起银联虚拟记账余额查询交易,获得对象
//						JsonPacket focusQuery = T91301Action.focusQuery();
						
					} catch (Exception e) {
						// TODO: handle exception
						log.error("备款后台处理失败："+e);
						return returnService("备款后台处理失败！请联系管理员！");
					}
				}else{
					//其他应答码为失败请排查原因
					//TODO
					try {
						TblFocusReserve tblFocusReserve = new TblFocusReserve();
						tblFocusReserve.setFocusId(focusNo);
						tblFocusReserve.setFocusAccount(PAYEEACCTNO);
						tblFocusReserve.setFocusAccountName(PAYEEACCTNAME);
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
			}else{
//				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
				return returnService("验证签名失败");
			}
		}else{
			//未返回正确的http状态
//			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			return returnService("未获取到返回报文或返回http状态码非200");
		}
		LogUtil.writeLog("备款交易请求报文:"+reqData+" + 应答报文:"+rspData+parseStr);
		
		
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		
	//	String sql = "select MAX(trim(FOCUS_ID)) from TBL_FOCUS_RESERVE";
	//	String focusMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		String focusNo;
		
		//生成交易流水号
		Random random = new Random();
		focusNo = nowdate + ACQINSCODE + random.nextInt(99999999);
		 
		if (focusMax == "") {
			focusNo = "400000001";
			focusNo = nowdate + ACQINSCODE + focusNo;
		}else {//2018101912345400000001
			int i = Integer.parseInt(focusMax);
			i = i + 1;
			//如5在前面补0  直到凑够6位数  000005   大于6直接输出i
			focusNo = String.format("%06d", i);
		}
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String dates = sdf2.format(date);
		
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
		jsonPacket.setPayeeAcctNo(PAYEEACCTNO);//收款方账号
		jsonPacket.setPayeeAcctName(PAYEEACCTNAME);//收款方账户名称
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
				tblFocusReserve.setFocusAccount(PAYEEACCTNO);
				tblFocusReserve.setFocusAccountName(PAYEEACCTNAME);
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
				
				String sql = "select ACCOUNT_NAME, BANK_ACCOUNT from TBL_BANKNO_INFO_TMP";
				List<Object[]> bankList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				Object bankAccountName = null;
				Object bankAccount = null;
				for (Object[] objects : bankList) {
					bankAccountName = objects[0];
					bankAccount = objects[1];
				}
				
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
				jsonPacket1.setPayeeAcctNo(String.valueOf(bankAccount));//付款方账号
				jsonPacket1.setPayeeAcctName(String.valueOf(bankAccountName));//付款方账户名称
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
				tblFocusReserve.setFocusAccount(PAYEEACCTNO);
				tblFocusReserve.setFocusAccountName(PAYEEACCTNAME);
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
		}*/
		log("人行集中缴存备款成功，操作员编号："+getOperator().getOprId());
		return returnService(rspCode);
	}
	
	/**
	 * 银联虚拟记账余额查询
	 * @return
	 */
	public static Map<String, String> focusQuery(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
		
		//流水号
		SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String focusNo = sdfQuery.format(new Date());
		SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfQuery2 = new SimpleDateFormat("HHmmss");
		
		Map<String, String> contentData = new HashMap<String, String>();

		/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "41");                                   //交易类型 41-实时余额查询 
	    contentData.put("acctNo", PAYEEACCTNO);                                    //虚拟帐号	    
	    contentData.put("currencyCode", "156");                                    //币种
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
		contentData.put("txnNo", focusNo);                                    //交易流水号
	    contentData.put("txnDate", sdfQuery1.format(new Date()));                               //交易日期
		contentData.put("sndTime", sdfQuery2.format(new Date()));      //发送时间 格式HHmmss
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = FsasService.sign(contentData,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			//交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的 fsassdk.backTransUrl
		Map<String, String> rspData = FsasService.doPost(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		StringBuffer parseStr = new StringBuffer("");
		if(!rspData.isEmpty()){
			if(FsasService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if(("00").equals(respCode)){
					//成功 
					//TODO
					LogUtil.writeLog("银联虚拟记账余额查询交易请求报文:"+reqData+" + 应答报文:"+rspData+parseStr);
					return rspData;
				}else{
					//其他应答码为失败请排查原因
					//TODO
					LogUtil.writeErrorLog("备款失败" + rspData.get("respMsg"));
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		LogUtil.writeLog("银联虚拟记账余额查询交易请求报文:"+reqData+" + 应答报文:"+rspData+parseStr);
		return null;
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
			conn.setRequestProperty("Content-Length", String.valueOf(dates.length));
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
