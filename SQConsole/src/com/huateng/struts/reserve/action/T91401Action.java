package com.huateng.struts.reserve.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.huateng.bo.reserve.T9130101BO;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T91401Action extends BaseSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T91401Action.class);
	T9130101BO t9130101BO = (T9130101BO) ContextUtil.getBean("T9130101BO");
	
	/**
	 * 人行备付金余额查询交易
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String init(){
		String CERTID = SysParamUtil.getParam(SysParamConstants.CERTID);//证书ID
		String SIGNATURE = SysParamUtil.getParam(SysParamConstants.SIGNATURE);//签名
		String SIGNMETHOD = SysParamUtil.getParam(SysParamConstants.SIGNMETHOD);//签名方法
		String ACQINSCODE = SysParamUtil.getParam(SysParamConstants.ACQINSCODE);//机构代码
		String BANKNO = SysParamUtil.getParam(SysParamConstants.BANKNO);//ACS备付金存管账号
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmmss");
		Date date1 = new Date();
		
		Random random = new Random();
		String balanceNo = sdf.format(date) + ACQINSCODE + random.nextInt(99999999);//交易流水号
		
		JsonPacket jsonPacket = new JsonPacket();
		jsonPacket.setVersion("1.0.0");
		jsonPacket.setEncoding("UTF-8");
		jsonPacket.setCertId(CERTID);
		jsonPacket.setSignature(SIGNATURE);
		jsonPacket.setSignMethod(SIGNMETHOD);
		jsonPacket.setTxnType("43");//交易类型  43 备付金余额查询
		jsonPacket.setTxnNo(balanceNo);
		jsonPacket.setAcqInsCode(ACQINSCODE);
		jsonPacket.setTxnDate(sdf.format(date));
		jsonPacket.setSndTime(sdf1.format(date1));
		jsonPacket.setBankNo(BANKNO);
		//对象转json字符串
		String json = JSON.toJSONString(jsonPacket);
		log.info("生成请求报文:"+json);
		//发送请求报文，获得返回报文
		String result = T91301Action.sendRequest(json);
		result = result.replace("GBK", "UTF-8");
		//处理返回的结果  得到返回对象
		JsonPacket processResult = T91401Action.processResultTS(result);
		if(processResult!=null){
			try {
				TblBalanceReserveQuery tblBalanceReserveQuery = new TblBalanceReserveQuery();
				tblBalanceReserveQuery.setBalanceNo(processResult.getTxnNo());
				tblBalanceReserveQuery.setBalanceDate(processResult.getTxnDate());
				tblBalanceReserveQuery.setBalanceAcsBankNo(processResult.getBankNo());
				tblBalanceReserveQuery.setBalanceAcctBal(processResult.getProAcctBal());
				tblBalanceReserveQuery.setBalanceAvlbBal(processResult.getProAvlbBal());
				tblBalanceReserveQuery.setBalanceAcsAcctBal(processResult.getAcsAcctBal());
				tblBalanceReserveQuery.setBalanceAcsAcctName(processResult.getAcsAcctName());
				tblBalanceReserveQuery.setBalanceAvlbQuotaAmt(processResult.getAvlbQuotaAmt());
				
				rspCode = t9130101BO.addBalance(tblBalanceReserveQuery);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("余额查询处理失败："+e);
				return returnService("余额查询处理失败！请联系管理员！");
			}
		}else{
			log.error("余额查询处理失败！请联系管理员");
			rspCode = Constants.SUCCESS_CODE;
			return returnService(rspCode);
		}
		log("人行备付金余额查询成功，操作员编号："+getOperator().getOprId());		
		return returnService(rspCode);
	}
	
	/**
	 * 处理返回的结果
	 * @param result
	 * @return
	 */
	private static JsonPacket processResultTS(String result) {
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
