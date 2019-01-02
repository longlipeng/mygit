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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import com.huateng.bo.settle.T80601BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblSettleRedempTionInf;
import com.huateng.po.settle.TblSettleRedempTionInfTmp;
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.FsasService;
import com.huateng.sdk.SDKConfig;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.huateng.sdk.LogUtil;
import com.thoughtworks.xstream.io.binary.Token.Value;

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
			
//			String sql1 = "select MAX(REDEMPTION_ID) from TBL_SETTLE_REDEMPTION_INF_TMP ";
//			String redempMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
//			String redempNo;
//			if (redempMax == "") {
//				redempNo = "000001";
//			}else {
//				int i = Integer.parseInt(redempMax);
//				i = i + 1;
//				//如5在前面补0  直到凑够6位数  000005
//				redempNo = String.format("%06d", i);
//			}
			
			Random random = new Random();
			StringBuffer redempNo = new StringBuffer();
			for (int i = 0; i < 15; i++) {
				int a = random.nextInt(10);
				redempNo.append(String.valueOf(a));
			}
			
			tblSettleRedempTionInfTmp.setRedempTionId(redempNo.toString());
			tblSettleRedempTionInfTmp.setRedempTionAccountName(redempTionAccountName);
			tblSettleRedempTionInfTmp.setRedempTionAccount(redempTionAccount);
			tblSettleRedempTionInfTmp.setRedempTionMoney(redempTionMoney);
			tblSettleRedempTionInfTmp.setRedempTionBankCard(redempTionBankCard);
			//0为正常，1为新增审核状态
			tblSettleRedempTionInfTmp.setRedempTionStatus("1");
			//0为已入账，1未入账，2为赎回中
//			tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
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
//				tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
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
		
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String PAYEEACCTNAME = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNAME);//收款方账户名称
		
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
						+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','2',null,null,'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
						+ ",'" + sdf.format(date) + "','" + getOperator().getOprId() + "','1')";
				commQueryDAO.excute(redempDel);
				
				//更新临时表审核时间
				tblSettleRedempTionInfTmp.setRedempTionAuditDate(sdf.format(date));
				//更新临时表审核人
				tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
				//0 正常
				tblSettleRedempTionInfTmp.setRedempTionStatus("2");
				try {
					//更新到数据库
					rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					return returnService(rspCode,e);
				}
			}else if(redempTionStatus.equals("3")){   //3为赎回审核
				
				SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
				
				Map<String, String> contentData = new HashMap<String, String>();
				
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String txnNo = sdfs.format(new Date());
				SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
				String txnDate = sdfQuery1.format(new Date());

				/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
				contentData.put("version", DemoBase.version);                  //版本号
				contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
				contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
				contentData.put("txnType", "02");                                   //交易类型 02-商户资金结算（单笔）
				contentData.put("backUrl", DemoBase.backUrl);                   //后台通知地址
			    contentData.put("currencyCode", "156");                          //币种
			    contentData.put("settleMethod", "03");                           //结算方式00:T+0  01:T+N(N>0) 02:收入费用结转 03:客户赎回
			    
			    contentData.put("txnNo", txnNo);                                    //交易流水号
			    contentData.put("acqInsCode", ACQINSCODE);                    //机构代码
			    contentData.put("txnDate", txnDate);                               //交易日期
			    contentData.put("sndTime", DemoBase.getSendTime());      //发送时间 格式HHmmss
			    contentData.put("merId", redempTionId);                                    //商户号
			    contentData.put("merName", redempTionAccountName);                           //商户名称redempTionAccountName车享付商户
			    contentData.put("payeeBankNo", redempTionBankCard);                // 403100000004  收款方开户行行 号当payeeAccType=01时，可以为空；其他情况必填
			    contentData.put("payeeAcctNo", redempTionAccount);                //收款方账号
//		        contentData.put("payeeAccType", payeeAccType);              //选填字段 收款方账号类型01：银联卡99：其他
			    contentData.put("payeeAcctName", redempTionAccountName);        //收款方账户名称
			    
			    //元转分
			    String Amt = yuanToFen(redempTionMoney);
			    
			    contentData.put("txnAmt", Amt);                                  //金额
			    contentData.put("reqReserved", "03");                           //请求方保留域

//			    contentData.put("payerAcctNo", payerAcctNo);   //付款方账号    可选字段 
//			    contentData.put("payerAcctName", payerAcctName);//付款方账号名称  可选字段 
			    
//			    contentData.put("regionCd", "") ;                                        //地区代码 （4位数字代码）
				
			    /**对请求参数进行签名并发送http post请求，接收同步应答报文**/
				Map<String, String> reqData = FsasService.sign(contentData,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
				String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			                //交易请求url从配置文件读取对应属性文件fsas_sdk.properties中的 fsassdk.backTransUrl
				Map<String, String> rspData = FsasService.doPost(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
				
				
				/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
				StringBuffer parseStr = new StringBuffer("");
				if(!rspData.isEmpty()){
					if(FsasService.validate(rspData, DemoBase.encoding)){
						LogUtil.writeLog("验证签名成功");
						String respCode = rspData.get("respCode");
						String respMsg = rspData.get("respMsg");
						if(("00").equals(respCode)){
							//成功 
							//TODO
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								Date dates = new Date();
								tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
								tblSettleRedempTionInfTmp.setRedempTionStatus("5");
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("2");//赎回中
								tblSettleRedempTionInfTmp.setRedempTionPayStatus("成功");
								//更新临时表审核时间
								tblSettleRedempTionInfTmp.setRedempTionAuditDate(txnDate);
								//更新临时表审核人
								tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
								//交易流水号
								tblSettleRedempTionInfTmp.setRedemptionBatch(txnNo);
								
								String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
								commQueryDAO.excute(redempDelInf);
								
								String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
										+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
										+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY, REDEMPTION_BATCH) "
										+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','2','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
										+ ",'" + txnDate + "','" + getOperator().getOprId() + "','1','" + txnNo + "')";
								commQueryDAO.excute(redempDel);
								
								rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("赎回后台处理失败："+e);
								return returnService("赎回后台处理失败！请联系管理员！");
							}
						}else{
							//其他应答码为失败请排查原因
							//TODO
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								Date dates = new Date();
								tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
								tblSettleRedempTionInfTmp.setRedempTionStatus("5");
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");//赎回失败
								tblSettleRedempTionInfTmp.setRedempTionPayStatus(respMsg);
								//更新临时表审核时间
								tblSettleRedempTionInfTmp.setRedempTionAuditDate(txnDate);
								//更新临时表审核人
								tblSettleRedempTionInfTmp.setRedempTionAuditName(getOperator().getOprId());
								//交易流水号
								tblSettleRedempTionInfTmp.setRedemptionBatch(txnNo);
								
								String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
								commQueryDAO.excute(redempDelInf);
								
								String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
										+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
										+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY, REDEMPTION_BATCH) "
										+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','1','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
										+ ",'" + txnDate + "','" + getOperator().getOprId() + "','1','" + txnNo + "')";
								commQueryDAO.excute(redempDel);
								
								rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("赎回后台处理失败："+e);
								return returnService("赎回后台处理失败！请联系管理员！");
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
			}else if(redempTionStatus.equals("6")){
				try {
					String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf);
					/*//删除审核通过，删除外键表数据
					String redempDelInf1 = "delete from TBL_MCHT_SETTLE_RESERVE where REDEMPTION_MONEY = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf1);
					
					String redempDelInf2 = "delete from TBL_MCHT_SETTLE_RESERVE_TMP where REDEMPTION_MONEY = '" + redempTionId + "'";
					commQueryDAO.excute(redempDelInf2);*/
					
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
					
					String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
					commQueryDAO.excute(redempDelInf);
					
					String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
							+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
							+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
							+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
							+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
							+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','4',null,null,'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
							+ ",'" + sdf.format(date) + "','" + getOperator().getOprId() + "','1')";
					commQueryDAO.excute(redempDel);
					
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
					
					String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
					commQueryDAO.excute(redempDelInf);
					String redempDel = "";
					if(tblSettleRedempTionInfTmp.getRedempTionAccountStatus()!=null){
						redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
								+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
								+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
								+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
								+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
								+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','7','" + tblSettleRedempTionInfTmp.getRedempTionAccountStatus() + "',null,'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
								+ ",'" + sdf.format(date) + "','" + getOperator().getOprId() + "','1')";
					}else{
						redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
								+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
								+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY) "
								+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
								+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
								+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','7',null,null,'" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
								+ ",'" + sdf.format(date) + "','" + getOperator().getOprId() + "','1')";
					}
					commQueryDAO.excute(redempDel);
					
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
	 * 客户赎回状态查询
	 * @return
	 */
	public String redempBackFill(){
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);//银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);//业务类别
		
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		//临时表
		TblSettleRedempTionInfTmp tblSettleRedempTionInfTmp = null;
		for (int i = 0; i < len; i++) {
			String redempTionId = jsonBean.getJSONDataAt(i).getString("redempTionId");//客户编号
			String redempTionAuditDate = jsonBean.getJSONDataAt(i).getString("redempTionAuditDate");//审核日期
			String redemptionBatch = jsonBean.getJSONDataAt(i).getString("redemptionBatch");//交易流水号
			
			
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
			contentData.put("origTxnType", "02"); // 原交易类型01：备款 02：商户资金结算
															// 04：回款
			contentData.put("origTxnNo", redemptionBatch); // 原交易流水号
			contentData.put("origTxnDate", redempTionAuditDate); // 原交易日期

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
					String origRespCode = rspData.get("origRespCode");
					if (("00").equals(respCode)) {
						// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
						// TODO
						if (("00").equals(origRespCode)) {
							// 代表原交易成功 如不成功根据原交易应答码做相应处理
							// TODO
							try {
								tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("0");
								tblSettleRedempTionInfTmp.setRedempTionPayStatus("完成");
								
								String redempDelInf = "delete from TBL_SETTLE_REDEMPTION_INF where REDEMPTION_ID = '" + tblSettleRedempTionInfTmp.getRedempTionId() + "'";
								commQueryDAO.excute(redempDelInf);
								
								String redempDel = "insert into TBL_SETTLE_REDEMPTION_INF(REDEMPTION_ID, REDEMPTION_ACCOUNT_NAME, REDEMPTION_ACCOUNT, "
										+ "REDEMPTION_MONEY, REDEMPTION_BANK_CARD, REDEMPTION_STATUS, REDEMPTION_ACCOUNT_STATUS, REDEMPTION_PAY_STATUS, REDEMPTION_ADD_TIME, "
										+ "REDEMPTION_ADD_NAME, REDEMPTION_AUDIT_DATE, REDEMPTION_AUDIT_NAME, REDEMPTION_ENTRY, REDEMPTION_BATCH) "
										+ "VALUES ('" + tblSettleRedempTionInfTmp.getRedempTionId() + "','" + tblSettleRedempTionInfTmp.getRedempTionAccountName() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAccount() + "','" + tblSettleRedempTionInfTmp.getRedempTionMoney() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionBankCard() + "','5','0','" + tblSettleRedempTionInfTmp.getRedempTionPayStatus() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddTime() + "','" + tblSettleRedempTionInfTmp.getRedempTionAddName() + "'"
										+ ",'" + tblSettleRedempTionInfTmp.getRedempTionAuditDate() + "','" + tblSettleRedempTionInfTmp.getRedempTionAuditName() + "','1','" + tblSettleRedempTionInfTmp.getRedemptionBatch() + "')";
								commQueryDAO.excute(redempDel);
								
								rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("回填后台处理失败："+e);
								return returnService("回填处理失败！请联系管理员！");
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
								tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
								tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
								tblSettleRedempTionInfTmp.setRedempTionPayStatus("失败");
								
								rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("回填后台处理失败："+e);
								return returnService("回填处理失败！请联系管理员！");
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
						try {
							tblSettleRedempTionInfTmp = t80601BO.getRedemp(redempTionId);
							tblSettleRedempTionInfTmp.setRedempTionAccountStatus("1");
							tblSettleRedempTionInfTmp.setRedempTionPayStatus("失败");
							
							rspCode = t80601BO.redempUp(tblSettleRedempTionInfTmp);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("回填后台处理失败："+e);
							return returnService("回填处理失败！请联系管理员！");
						}
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
	 * 
	 * 功能描述：金额字符串转换：单位分转成单元
	  
	 * @param str 传入需要转换的金额字符串
	 * @return 转换后的金额字符串
	 */
	public static String yuanToFen(Object o) {
		if(o == null)
			return "0";
		String s = o.toString();
		int posIndex = -1;
		String str = "";
		StringBuilder sb = new StringBuilder();
		if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
			posIndex = s.indexOf(".");
			if(posIndex>0){
				int len = s.length();
			    if(len == posIndex+1){
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
				    sb.append(str).append("00");
				}else if(len == posIndex+2){
				    str = s.substring(0,posIndex);
				    if(str == "0"){
				    	str = "";
				    }
				    sb.append(str).append(s.substring(posIndex+1,posIndex+2)).append("0");
				}else if(len == posIndex+3){
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
					sb.append(str).append(s.substring(posIndex+1,posIndex+3));
				}else{
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
					sb.append(str).append(s.substring(posIndex+1,posIndex+3));
				}
			}else{
				sb.append(s).append("00");
			}
		}else{
			sb.append("0");
		}
		str = removeZero(sb.toString());
		if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
			return str;
		}else{
			return "0";
		}
	}
	
	
	
	/**
	 * 
	 * 功能描述：去除字符串首部为"0"字符
	  
	 * @param str 传入需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String removeZero(String str){   
	   	char  ch;  
	   	String result = "";
	   	if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){				
	   		try{			
				for(int i=0;i<str.length();i++){
					ch = str.charAt(i);
					if(ch != '0'){						
						result = str.substring(i);
						break;
					}
				}
			}catch(Exception e){
				result = "";
			}	
		}else{
			result = "";
		}
	   	return result;
			
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
