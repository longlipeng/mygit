package com.huateng.startup.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.huateng.bo.settle.T80224BO;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblMchtSumrzInf;
import com.huateng.struts.settle.action.T80224Action;
import com.huateng.struts.settle.action.XmlPacket;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class Study {
	private static Logger log = Logger.getLogger(Study.class);

	public void doStudy01() {
		T80224BO t80224BO = (T80224BO) ContextUtil.getBean("T80224BO");
		log.info(new Date() + "执行自动回填！");
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd ").format(cal.getTime());
		String sql = "SELECT distinct a.SEQ_NUM FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a LEFT JOIN TBL_MCHT_BASE_INF b ON a.MCHT_NO = b.MCHT_NO WHERE 1=1 AND c.MCHT_NO = a.MCHT_NO AND a.SUM_AMT<>0 AND a.INST_DATE = '"
				+ yesterday + "' AND a.SA_STATUS = '2' ";
		String lgnnam = SysParamUtil.getParam(SysParamConstants.LGNNAM);// 银企直连登陆用户名
		String buscod01 = SysParamUtil.getParam(SysParamConstants.BUSCOD01);// 业务类别
		try {
			List seqNoList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			TblMchtSumrzInf tblMchtSumrzInf = null;
			if (seqNoList.size() > 0) {
				List<TblMchtSumrzInf> tblMchtSumrzInfList = new ArrayList<TblMchtSumrzInf>(seqNoList.size());
				String sumrzDate = yesterday;// 日期
				for (Object object : seqNoList) {
					String seqNo = (String) object;
					XmlPacket xmlPkt = new XmlPacket("GetPaymentInfo", lgnnam);// 函数名  // 登陆用户名
					Map mpPodInfo = new Properties();
					mpPodInfo.put("BUSCOD", buscod01);// 业务类别
					mpPodInfo.put("BGNDAT", sumrzDate);// 起始时间
					mpPodInfo.put("ENDDAT", sumrzDate);// 结束时间
					mpPodInfo.put("DATFLG", "B");// 日期类型 A：经办日期；B：期望日期
					mpPodInfo.put("YURREF", seqNo);// 业务参考号
					// mpPodInfo.put("RTNFLG", "B");//业务处理结果
					xmlPkt.putProperty("SDKPAYQYX", mpPodInfo);
					String date = xmlPkt.toXmlString();
					log.info("生成查询报文:" + date);
					// 连接前置机，发送请求报文，获得返回报文
					String result = T80224Action.sendRequest(date);
					result = result.replace("GBK", "UTF-8");
					// 处理返回的结果
					String processResult = T80224Action.processResult(result);
					if (processResult.equals("成功")) {
						try {
							tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
							tblMchtSumrzInf.setSaStatus("1");
							tblMchtSumrzInf.setCauseStat(processResult);
							tblMchtSumrzInf.setRecUpdOpr("0000");
							tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
							tblMchtSumrzInfList.add(tblMchtSumrzInf);
						} catch (Exception e) {
							log.error("查询后台处理失败：" + e);
						}
					} else if (processResult.equals("F")) {
						try {
							tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
							tblMchtSumrzInf.setSaStatus("0");
							tblMchtSumrzInf.setCauseStat("银行支付失败");
							tblMchtSumrzInf.setRecUpdOpr("0000");
							tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
							tblMchtSumrzInfList.add(tblMchtSumrzInf);
						} catch (Exception e) {
							log.error("查询后台处理失败：" + e);
						}
					} else if (processResult.equals("B")) {
						try {
							tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
							tblMchtSumrzInf.setSaStatus("0");
							tblMchtSumrzInf.setCauseStat(processResult);
							tblMchtSumrzInf.setRecUpdOpr("0000");
							tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
							tblMchtSumrzInfList.add(tblMchtSumrzInf);
						} catch (Exception e) {
							log.error("查询后台处理失败：" + e);
						}
					} else {
						try {
							tblMchtSumrzInf = t80224BO.get(new Integer(seqNo));
							tblMchtSumrzInf.setSaStatus("0");
							tblMchtSumrzInf.setCauseStat("银行支付被退票");
							tblMchtSumrzInf.setRecUpdOpr("0000");
							tblMchtSumrzInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
							tblMchtSumrzInfList.add(tblMchtSumrzInf);
						} catch (Exception e) {
							log.error("查询后台处理失败：" + e);
						}
					}
					try {
						t80224BO.update(tblMchtSumrzInfList);
					} catch (Exception e) {
						log.error("自动回填失败：" + e.getMessage());
					}
				}
			}*/
//		} catch (Exception e) {
//			log.error("查询数据失败" + e.getMessage());
//		}
		log.info(new Date() + "执行自动结束！");
	}
}
