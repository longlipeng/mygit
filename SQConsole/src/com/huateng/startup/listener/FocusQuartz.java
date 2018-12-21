package com.huateng.startup.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.bo.reserve.T9130101BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.sdk.DemoBase;
import com.huateng.sdk.FsasService;
import com.huateng.sdk.LogUtil;
import com.huateng.sdk.SDKConfig;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * 
 * @author 
 *
 */
public class FocusQuartz {
	private static Logger log = Logger.getLogger(FocusQuartz.class);
//	T9130101BO t9130101BO = (T9130101BO) ContextUtil.getBean("T9130101BO");
	
	public void focus(){
		log.info(new Date() + "执行人行集中缴存备款回填自动查询备款状态");
		
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		
		String sql1 = "select FOCUS_ID, FOCUS_ACCOUNT, FOCUS_ACCOUNT_NAME, FOCUS_MONEY, FOCUS_STATUS, FOCUS_DATE from TBL_FOCUS_RESERVE";
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql1);
		TblFocusReserve tblFocusReserve;
		for (Object[] objects : list) {
			
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
			contentData.put("origTxnNo", String.valueOf(objects[0])); // 原交易流水号
			contentData.put("origTxnDate", String.valueOf(objects[5])); // 原交易日期

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
//								tblFocusReserve = t9130101BO.getFocus(String.valueOf(objects[0]));
//								//0成功
//								tblFocusReserve.setFocusStatus("0");
//								
//								t9130101BO.upFocus(tblFocusReserve);
								
								String sql2 = "update TBL_FOCUS_RESERVE set FOCUS_STATUS = '0' where FOCUS_ID = '" + String.valueOf(objects[0]) + "'";
								CommonFunction.getCommQueryDAO().equals(sql2);
								
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else if (("03").equals(origRespCode)
								|| ("05").equals(origRespCode)) {
							// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
							// TODO
							log.error("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
						} else {
							 //其它根据需要作处理 
							// TODO
							try {
//								tblFocusReserve = t9130101BO.getFocus(String.valueOf(objects[0]));
//								//1失败
//								tblFocusReserve.setFocusStatus("1");
//								
//								t9130101BO.upFocus(tblFocusReserve);
								
								String sql2 = "update TBL_FOCUS_RESERVE set FOCUS_STATUS = '1' where FOCUS_ID = '" + String.valueOf(objects[0]) + "'";
								CommonFunction.getCommQueryDAO().equals(sql2);
								
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					} else if (("03").equals(respCode)
							|| ("05").equals(respCode)) {
						// 订单处理中或交易状态未明，需稍后发起交易状态查询交易
						// TODO
						log.error("订单处理中或交易状态未明，需稍后发起交易状态查询交易");
					} else {
						// 其他应答码为失败请排查原因
						// TODO
						log.error("查询失败:" + respCode + "失败信息:" + respMsg);
					}
				} else {
//					LogUtil.writeErrorLog("验证签名失败");
					// TODO 检查验证签名失败的原因
					log.error("验证签名失败");
				}
			} else {
				// 未返回正确的http状态
				log.error("未获取到返回报文或返回http状态码非200");
			}
			
		}
		log.info(new Date() + "执行自动结束");
	}
	
}
