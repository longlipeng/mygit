package com.huateng.struts.reserve.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.huateng.bo.reserve.T9130101BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFictitiousQuery;
import com.huateng.po.reserve.TblHistoryQuery;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.SDKConfig;
import com.huateng.sdk.FsasService;
import com.huateng.sdk.LogUtil;

public class T91401Action extends BaseSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T91401Action.class);
	T9130101BO t9130101BO = (T9130101BO) ContextUtil.getBean("T9130101BO");
	
	private String date;
	
	/**
	 * 人行备付金余额查询交易
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String init(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
//		String PAYEEACCTNO = SysParamUtil.getParam(SysParamConstants.PAYEEACCTNO);//收款方账号
		String BANKNO = SysParamUtil.getParam(SysParamConstants.BANKNO);//ACS备付金存管账号
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmmss");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
		
		Map<String, String> contentData = new HashMap<String, String>();

		/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "43");                                   //交易类型 43-备付金余额查询 
	    contentData.put("bankNo", BANKNO);                                    //ACS 备付金存管账号	    
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
		contentData.put("txnNo", sdf.format(new Date()));                       //交易流水号
	    contentData.put("txnDate",sdfs.format(new Date()));                               //交易日期
		contentData.put("sndTime", DemoBase.getSendTime());      //发送时间 格式HHmmss
		
		
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
				String respCode = rspData.get("respCode");  //
				String respMsg = rspData.get("respMsg");  //
				if(("00").equals(respCode)){
					//成功
					//TODO
					try {
						String str1 = "select BALANCE_NO,BALANCE_DATE,BALANCE_ACS_BANK_NO,BALANCE_ACCT_BAL,BALANCE_AVLB_BAL,BALANCE_ACS_ACCT_BAL,BALANCE_ACS_ACCT_NAME,BALANCE_AVLB_QUOTA_AMT from TBL_BALANCE_RESERVE_QUERY";
						List<Object[]> balanceList = CommonFunction.getCommQueryDAO().findBySQLQuery(str1);
						
						if(balanceList!=null && !balanceList.isEmpty()){
							for (Object[] objects : balanceList) {
								String str2 = "delete from TBL_BALANCE_RESERVE_QUERY where BALANCE_NO = '" + String.valueOf(objects[0]) + "'";
								CommonFunction.getCommQueryDAO().excute(str2);
								
								String str3 = "insert into TBL_BALANCE_RESERVE_QUERY(BALANCE_NO,BALANCE_DATE,BALANCE_ACS_BANK_NO,BALANCE_ACCT_BAL,BALANCE_AVLB_BAL,BALANCE_ACS_ACCT_BAL,BALANCE_AVLB_QUOTA_AMT) "
										+ "VALUES('" + rspData.get("txnNo") + "','" + rspData.get("txnDate") + "','" + rspData.get("bankNo") + "','" + rspData.get("proAcctBal") + "',"
										+ "'" + rspData.get("proAvlbBal") + "','" + rspData.get("acsAcctBal") + "','" + rspData.get("avlbQuotaAmt") + "')";
								CommonFunction.getCommQueryDAO().excute(str3);
								
								return returnService("00");
							}
						}
						
						TblBalanceReserveQuery tblBalanceReserveQuery = new TblBalanceReserveQuery();
						tblBalanceReserveQuery.setBalanceNo(rspData.get("txnNo"));
						tblBalanceReserveQuery.setBalanceDate(rspData.get("txnDate"));
						tblBalanceReserveQuery.setBalanceAcsBankNo(rspData.get("bankNo"));
						tblBalanceReserveQuery.setBalanceAcctBal(rspData.get("proAcctBal"));
						tblBalanceReserveQuery.setBalanceAvlbBal(rspData.get("proAvlbBal"));
						tblBalanceReserveQuery.setBalanceAcsAcctBal(rspData.get("acsAcctBal"));
//						tblBalanceReserveQuery.setBalanceAcsAcctName(rspData.get("acsAcctName"));
						tblBalanceReserveQuery.setBalanceAvlbQuotaAmt(rspData.get("avlbQuotaAmt"));
						
						rspCode = t9130101BO.addBalance(tblBalanceReserveQuery);
					} catch (Exception e) {
						// TODO: handle exception
						log.error("备付金余额查询处理失败："+e);
						return returnService("备付金余额查询处理失败！请联系管理员！");
					}
				}else{
					//其他应答码为失败请排查原因
					//TODO
//					LogUtil.writeErrorLog("备付金余额查询失败:" + respMsg);
					return returnService("备付金余额查询失败:" + respMsg);
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
		log("人行备付金余额查询成功，操作员编号："+getOperator().getOprId());		
		return returnService(rspCode);
	}
	
	/**
	 * 历史余额查询
	 * @return
	 */
	public String inits(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String ACCTNO = SysParamUtil.getParam(SysParamConstants.ACCTNO);//虚拟账号
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");

		
		/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "42");                                   //交易类型 42-历史余额查询 
		
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
		contentData.put("txnNo", sdf.format(new Date()));                                    //交易流水号
	    contentData.put("txnDate",sdfs.format(new Date()));                               //交易日期
		contentData.put("sndTime", DemoBase.getSendTime());      //发送时间 格式HHmmss
		
	    contentData.put("acctDate", date);                                    //银联记账日期	 
	    contentData.put("acctNo", ACCTNO);                                    //虚拟帐号	    
	    contentData.put("currencyCode", "156");                                    //币种
	    

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
				if(("00").equals(respCode)){
					//成功 
					//TODO
					try {
						String str = "select HISTORY_NO, HISTORY_DATE, HISTORY_ACCTNO, HISTORY_ACCTNAME, HISTORY_OPENBAL, HISTORY_ENCBAL, HISTORY_TOTALDBTRQTY, HISTORY_TOTALCDTRQTY, HISTORY_TOTALDBTRAMT, HISTORY_TOTALCDTRAMT from TBL_HISTORY_QUERY";
						List<Object[]> balanceList = CommonFunction.getCommQueryDAO().findBySQLQuery(str);
						
						if(balanceList!=null && !balanceList.isEmpty()){
							for (Object[] objects : balanceList) {
								String str2 = "delete from TBL_HISTORY_QUERY where HISTORY_NO = '" + String.valueOf(objects[0]) + "'";
								CommonFunction.getCommQueryDAO().excute(str2);
								
								String str3 = "insert into TBL_HISTORY_QUERY(HISTORY_NO, HISTORY_DATE, HISTORY_ACCTNO, HISTORY_ACCTNAME, HISTORY_OPENBAL, HISTORY_ENCBAL, HISTORY_TOTALDBTRQTY, HISTORY_TOTALCDTRQTY, HISTORY_TOTALDBTRAMT, HISTORY_TOTALCDTRAMT) "
										+ "VALUES('" + rspData.get("txnNo") + "','" + rspData.get("txnDate") + "','" + rspData.get("acctNo") + "','" + rspData.get("acctName") + "',"
										+ "'" + rspData.get("openBal") + "','" + rspData.get("encBal") + "','" + rspData.get("totalDbtrQty") + "','" + rspData.get("totalCdtrQty") + "',"
										+ "'" + rspData.get("totalDbtrAmt") + "','" + rspData.get("totalCdtrAmt") + "')";
								CommonFunction.getCommQueryDAO().excute(str3);
								
								return returnService("00");
							}
						}
						
						TblHistoryQuery tblHistoryQuery = new TblHistoryQuery();
						tblHistoryQuery.setHistoryNo(rspData.get("txnNo"));
						tblHistoryQuery.setHistoryDate(rspData.get("txnDate"));
						tblHistoryQuery.setHistoryAcctNo(rspData.get("acctNo"));
						tblHistoryQuery.setHistoryAcctName(rspData.get("acctName"));
						tblHistoryQuery.setHistoryOpenBal(rspData.get("openBal"));
						tblHistoryQuery.setHistoryEncBal(rspData.get("encBal"));
						tblHistoryQuery.setHistoryTotalDbtrQty(rspData.get("totalDbtrQty"));
						tblHistoryQuery.setHistoryTotalCdtrQty(rspData.get("totalCdtrQty"));
						tblHistoryQuery.setHistoryTotalDbtrAmt(rspData.get("totalDbtrAmt"));
						tblHistoryQuery.setHistoryTotalCdtrAmt(rspData.get("totalCdtrAmt"));
						
						rspCode = t9130101BO.saveHistory(tblHistoryQuery);
					} catch (Exception e) {
						// TODO: handle exception
						log.error("历史余额查询处理失败："+e);
						return returnService("历史余额查询处理失败！请联系管理员！");
					}
				}else{
					//其他应答码为失败请排查原因
					//TODO
					log("历史余额查询失败。应答码respCode=" + respCode + "失败原因:" + rspData.get("respMsg"));
					return returnService("历史余额查询失败。应答码respCode=" + respCode + "失败原因:" + rspData.get("respMsg"));
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
		LogUtil.writeLog("历史余额查询交易请求报文:"+reqData+" + 应答报文:"+rspData);
		return returnService(rspCode);
	}
	
	/**
	 * 虚拟记账余额查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initss(){
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String ACCTNO = SysParamUtil.getParam(SysParamConstants.ACCTNO);//机构代码
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载fsas_sdk.properties文件
		
		//流水号
		SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fictitiousNo = sdfQuery.format(new Date());
		SimpleDateFormat sdfQuery1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfQuery2 = new SimpleDateFormat("HHmmss");
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		if(date == null){
			date = sdfQuery1.format(new Date());
		}

		/***银联资金结算接入系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "41");                                   //交易类型 41-实时余额查询 
	    contentData.put("acctNo", ACCTNO);                                    //虚拟帐号	    
	    contentData.put("currencyCode", "156");                                    //币种
	    contentData.put("acqInsCode", ACQINSCODE);                        //机构代码
		contentData.put("txnNo", fictitiousNo);                                    //交易流水号
	    contentData.put("txnDate", date);                 //交易日期  sdfQuery1.format(new Date())
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
					try {
						String str = "select FICTITIOUS_NO,FICTITIOUS_DATE,FICTITIOUS_ACCTNO,FICTITIOUS_ACCTNAME,FICTITIOUS_ACCTBAL,FICTITIOUS_AVLBBAL from TBL_FICTITIOUS_QUERY";
						List<Object[]> fictitiousList = CommonFunction.getCommQueryDAO().findBySQLQuery(str);
						
						if(fictitiousList!=null && !fictitiousList.isEmpty()){
							for (Object[] objects : fictitiousList) {
								String str1 = "delete from TBL_FICTITIOUS_QUERY where FICTITIOUS_NO = '" + String.valueOf(objects[0]) + "'";
								CommonFunction.getCommQueryDAO().excute(str1);
								
								String str2 = "insert into TBL_FICTITIOUS_QUERY(FICTITIOUS_NO,FICTITIOUS_DATE,FICTITIOUS_ACCTNO,FICTITIOUS_ACCTNAME,FICTITIOUS_ACCTBAL,FICTITIOUS_AVLBBAL) "
										+ "VALUES('" + rspData.get("txnNo") + "','" + rspData.get("txnDate") + "','" + rspData.get("acctNo") + "','" + rspData.get("acctName") + "',"
										+ "'" + rspData.get("acctBal") + "','" + rspData.get("avlbBal") + "')";
								CommonFunction.getCommQueryDAO().excute(str2);
								
								return returnService("00");
							}
						}
						TblFictitiousQuery tblFictitiousQuery = new TblFictitiousQuery();
						
						tblFictitiousQuery.setFictitiousNo(rspData.get("txnNo"));
						tblFictitiousQuery.setFictitiousDate(rspData.get("txnDate"));
						tblFictitiousQuery.setFictitiousAcctNo(rspData.get("acctNo"));
						tblFictitiousQuery.setFictitiousAcctName(rspData.get("acctName"));
						tblFictitiousQuery.setFictitiousAcctBal(rspData.get("acctBal"));
						tblFictitiousQuery.setFictitiousAvlbBal(rspData.get("avlbBal"));
						
						rspCode = t9130101BO.saveFictitious(tblFictitiousQuery);	
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						return returnService("虚拟记账余额查询后台处理失败");
					}
				}else{
					//其他应答码为失败请排查原因
					//TODO
//					LogUtil.writeErrorLog("虚拟记账余额查询失败" + rspData.get("respMsg"));
					return returnService("虚拟记账余额查询失败" + rspData.get("respMsg"));
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
		LogUtil.writeLog("银联虚拟记账余额查询交易请求报文:"+reqData+" + 应答报文:"+rspData+parseStr);
		return returnService(rspCode);
	}
	
	
	/**
	 * 处理返回的结果
	 * @param result
	 * @return
	 */
	/*private static JsonPacket processResultTS(String result) {
		if (result != null && result.length() > 0) {
			//json字符串转java对象
			JSONObject obj = JSONObject.fromObject(result);
			JsonPacket jsonPacket = (JsonPacket) obj.toBean(obj, JsonPacket.class);
			if (jsonPacket != null) {
				String sResCode = jsonPacket.getRespCode();
				if (sResCode.equals("00")) {
					return jsonPacket;//返回对象
				}else {
					log.error("备款失败：" + jsonPacket.getRespMsg());
				}
			} else {
				log.error("响应报文解析失败");
			}
		}
		return null;
	}*/
	
	
	
	
	
	
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
