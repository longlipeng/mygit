package com.huateng.struts.error;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import com.huateng.bo.error.T100101BO;
import com.huateng.bo.error.T100201BO;
import com.huateng.bo.error.T10031BO;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.po.error.ManualReturn;
import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfId;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SysParamUtil;

/**
 * @author zoujunqing
 *
 */
public class T10011Action extends BaseAction {
	 
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T10011Action.class);
	
	private T100101BO t100101BO = (T100101BO) ContextUtil.getBean("T100101BO");
	private T10031BO t10031BO =   (T10031BO)  ContextUtil.getBean("T10031BO");
	private T100201BO t100201BO = (T100201BO) ContextUtil.getBean("T100201BO");
	private TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
	Socket socket = null;
	String sales_ip=SysParamUtil.getParam(SysParamConstants.SALES_IP);
	String sales_port=SysParamUtil.getParam(SysParamConstants.SALES_PORT);
	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	protected String subExecute() throws Exception {
		// 20120911添加审核时的验证
		
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		for (int i = 0; i < len; i++) {
			String idkey = jsonBean.getJSONDataAt(i).getString("ID");
			String sql = "SELECT OPR_ID FROM TBL_MANUAL_RETURN WHERE ID='" + idkey + "'";
			List<String> list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				if (!StringUtil.isNull(list.get(0))) {
					if (list.get(0).equals(operator.getOprId())) {
						return "同一操作员不能审核！";
					}
				}
			}
			String createDate_1 = jsonBean.getJSONDataAt(i).getString("instDate");
			/*String pat1 = "yyyy-MM-dd HH:mm:ss" ;  
	        String pat2 = "yyyyMMdd" ;  
	        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1) ;        // 实例化模板对象  
	        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2) ;        // 实例化模板对象  
	        Date d = null ;  
	        try{  
	            d = sdf1.parse(createDate_1) ;   // 将给定的字符串中的日期提取出来  
	        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理  
	            e.printStackTrace() ;       // 打印异常信息  
	        } 
	        createDate_1 = d.toString();*/ 
			String retrivlRef_1 = jsonBean.getJSONDataAt(i).getString("retrivlRef");
			String sysSsn_1 = jsonBean.getJSONDataAt(i).getString("sysSsn");
			String termId_1 = jsonBean.getJSONDataAt(i).getString("termId");
			ManualReturn manualreturn = t100101BO.get(idkey);
			String state = manualreturn.getSaState();
			if ("accept".equals(method)) {
				String strSql = "select count(*) from tbl_manual_return tmr, TBL_FROZEN_ACC_INF tfai where tfai.MCHT_NO = "
						+ "'" + manualreturn.getMchtNo().trim() + "'" + " and tfai.TERM_ID = " + "'"
						+ manualreturn.getTermId().trim() + "'"
						+ " and tfai.FROZEN_ACCOUNT = tfai.FROZEN_ACCOUNT_FINISH and tfai.FROZEN_ACCOUNT_NO_FINISH = '0' and tfai.FROZEN_ROUTE = '2' and tfai.FROZEN_FINISH_FLAG = '1' and tfai.STATS = '2'";
				String strCount = commQueryDAO.findCountBySQLQuery(strSql);
				if (strCount != "0") {
					String updSql = "UPDATE TBL_FROZEN_ACC_INF tfai SET tfai.SALESSTATS = '1' WHERE tfai.MCHT_NO = "
							+ "'" + manualreturn.getMchtNo().trim() + "'" + "  AND tfai.TERM_ID = " + "'"
							+ manualreturn.getTermId().trim() + "'"
							+ " AND tfai.FROZEN_ACCOUNT = tfai.FROZEN_ACCOUNT_FINISH AND tfai.FROZEN_ACCOUNT_NO_FINISH = '0' AND tfai.FROZEN_ROUTE = '2' AND tfai.FROZEN_FINISH_FLAG = '1' AND tfai.STATS = '2' AND tfai.FROZEN_DATE = (SELECT MAX(t1.FROZEN_DATE) FROM TBL_FROZEN_ACC_INF t1 WHERE t1.MCHT_NO = "
							+ "'" + manualreturn.getMchtNo().trim() + "'" + "  AND t1.TERM_ID = " + "'"
							+ manualreturn.getTermId().trim() + "'"
							+ " AND t1.FROZEN_ACCOUNT = t1.FROZEN_ACCOUNT_FINISH AND t1.FROZEN_ACCOUNT_NO_FINISH = '0' AND t1.FROZEN_ROUTE = '2' AND t1.FROZEN_FINISH_FLAG = '1' AND t1.STATS = '2') ";
					CommonFunction.getCommQueryDAO().excute(updSql);
				}
				
				if (state.equals("3")) {
					log("审核通过删除待审核的信息");
					rspCode = acceptDelete(idkey);
					return rspCode;
				}
				
				// ADD SCOKET
				socket = new Socket(sales_ip, Integer.parseInt(sales_port));
				socket.setSoTimeout(50000);
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				long amtTmp = (long) (Float.parseFloat(manualreturn.getAmtReturn()));
				String messageStr = "51510220";
				String pan = manualreturn.getPan();
				String F003_val = "200000";
				String amt1 = codeAddOne(String.valueOf(amtTmp), 12);
				// String seq_ssnSql = "select seq_ssn.nextval from dual";
				// String seq_ssn =
				// commQueryDAO.findCountBySQLQuery(seq_ssnSql);
				String seq_ssn = manualreturn.getSysSsn();
				seq_ssn = codeAddOne(seq_ssn, 6);
				String retrivlRef = manualreturn.getRetrivlRef();
				String termId = manualreturn.getTermId();
				String mchtNo = manualreturn.getMchtNo();
				String instDate = manualreturn.getInstDate();
				String dateTrans = "";
				if (instDate.trim().length() == 14) {
					dateTrans = instDate.trim().substring(4, 8);
					log("dateTrans结果：" + dateTrans);
				}
				// String dateTransSql = "select DATE_LOCAL_TRANS from
				// TBL_N_TXN_HIS where INST_DATE = '"+instDate+"' and
				// RETRIVL_REF = '"+retrivlRef+"'";
				// String dateTrans =
				// commQueryDAO.findCountBySQLQuery(dateTransSql);
				messageStr = messageStr + pan + F003_val + amt1 + seq_ssn + retrivlRef + termId + mchtNo + dateTrans;
				int lengthStrM = messageStr.length();
				String messageStrLen = codeAddOne(String.valueOf(lengthStrM), 4);
				messageStr = messageStrLen + messageStr;
				InputStream is = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				log("需要發送的報文                =  ---- " + messageStr + "長度             =         " + messageStr.length());
				pw.write(messageStr);
				pw.flush();

				// 接收服务器的相应
				char[] ch = new char[4];
				br.read(ch, 0, 4);
				String string = String.valueOf(ch);
				log("接收的前四个字节" + string);
				int parseInt = Integer.parseInt(string);
				char[] ch1 = new char[parseInt];
				br.read(ch1, 0, parseInt);
				String string1;
				string1 = String.valueOf(ch1);
				log("接收的所有数据" + string1);
				string1 = string1.substring(73, 75);
				System.out.println("应答码：" + string1);
				if (!string1.equals("00")) {
					ManualReturn manualReturn =t100101BO.get(idkey);
//					manualReturn.setTxnInfo(refuseInfo);
					if (manualreturn.getSrcId() == "1901") {
						manualReturn.setPotalSsn(manualReturn.getId() + "%%%");
					}
					manualReturn.setSaState("6");//后台审核通过，前台失败
					manualReturn.setRspCode(string1);
					manualReturn.setUpdId(operator.getOprId());
					manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
					t100101BO.saveOrUpdate(manualReturn);
					return "退货失败错误码" + string1;
				}

				try {
					if (state.equals("0")) {
						log("审核通过新增待审核的信息");
						// 将元转换为分
//						long amt = (long) (Float.parseFloat(manualreturn.getAmtReturn()));
						// 因为流水表中只存7天的数据，为了确保都更新到，在2个表中都执行更新操作
						// 系统流水号+交易日期 可以确定唯一一笔交易流水

						/*
						 * 判断审核通过的时候退货金额是否大于原交易金额
						 * 
						 */
						/*String amtTrans = manualreturn.getAmtTrans();
						String amtReturnSql = "select to_number(amt_return) From tbl_n_txn where  SYS_SEQ_NUM='"
								+ manualreturn.getSysSsn() + "' and INST_DATE='" + manualreturn.getInstDate() + "'";
						String amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
						if (StringUtil.isEmpty(amtReturn)) {
							amtReturnSql = "select to_number(amt_return) From tbl_n_txn_his where  SYS_SEQ_NUM='"
									+ manualreturn.getSysSsn() + "' and INST_DATE='" + manualreturn.getInstDate() + "'";
							amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
							if ("".equals(amtReturn))
								amtReturn = "0";
							if ((amt + Double.valueOf(amtReturn)) > Double.valueOf(amtTrans)) {
								return "退货累计金额不能超过原交易金额";
							}
						} else {
							if ((amt + Double.valueOf(amtReturn)) > Double.valueOf(amtTrans)) {
								return "退货累计金额不能超过原交易金额";
							}
						}*/
						/*// 修改流水表
						String sqlN = "Update tbl_n_txn Set amt_return= lpad(to_number(amt_return)+" + amt
								+ ",12,'0'),trans_state='R' where  SYS_SEQ_NUM='" + manualreturn.getSysSsn()
								+ "' and inst_date='" + manualreturn.getInstDate() + "'";
						// 修改流水历史表
						String sqlH = "Update tbl_n_txn_his Set amt_return= lpad(to_number(amt_return)+" + amt
								+ ",12,'0'),trans_state='R' where  SYS_SEQ_NUM='" + manualreturn.getSysSsn()
								+ "' and inst_date='" + manualreturn.getInstDate() + "'";
						commQueryDAO.excute(sqlN);
						commQueryDAO.excute(sqlH);*/
						rspCode = acceptAdd(idkey);
					}
					
					if (state.equals("2")) {
						log("审核通过修改待审核的信息");
						long amt = (long) (Float.parseFloat(manualreturn.getAmtReturn()));
						System.out.println(amt);
						// 因为流水表中只存7天的数据，为了确保都更新到，在2个表中都执行更新操作
						// 系统流水号+交易日期 可以确定唯一一笔交易流水

						/*
						 * 判断审核通过的时候退货金额是否大于原交易金额 20141009 by shiyiwen
						 */
						/*String amtTrans = manualreturn.getAmtTrans();
						String amtReturnSql = "select to_number(amt_return) From tbl_n_txn where  SYS_SEQ_NUM='"
								+ manualreturn.getSysSsn() + "' and INST_DATE='" + manualreturn.getInstDate() + "'";
						String amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
						if (StringUtil.isEmpty(amtReturn)) {

							amtReturnSql = "select to_number(amt_return) From tbl_n_txn_his where  SYS_SEQ_NUM='"
									+ manualreturn.getSysSsn() + "' and INST_DATE='" + manualreturn.getInstDate() + "'";
							amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
							if ("".equals(amtReturn))
								amtReturn = "0";
							if ((amt + Double.valueOf(amtReturn)) > Double.valueOf(amtTrans)) {
								return "退货累计金额不能超过原交易金额";
							}
						} else {
							if ((amt + Double.valueOf(amtReturn)) > Double.valueOf(amtTrans)) {
								return "退货累计金额不能超过原交易金额";
							}
						}*/
						/*// 修改流水表
						String sqlN = "Update tbl_n_txn Set amt_return= lpad(to_number(amt_return)+" + amt
								+ ",12,'0'),trans_state='R' where  SYS_SEQ_NUM='" + manualreturn.getSysSsn()
								+ "' and inst_date='" + manualreturn.getInstDate() + "'";
						// 修改流水历史表
						String sqlH = "Update tbl_n_txn_his Set amt_return= lpad(to_number(amt_return)+" + amt
								+ ",12,'0'),trans_state='R' where  SYS_SEQ_NUM='" + manualreturn.getSysSsn()
								+ "' and inst_date='" + manualreturn.getInstDate() + "'";
						commQueryDAO.excute(sqlN);
						commQueryDAO.excute(sqlH);*/
						rspCode = acceptModify(idkey);
					}
					String instCode = "" ;
					try {
						System.out.println("==============================================");
						System.out.println("createDate_1.substring(0, 8)："+createDate_1.substring(0, 8));
						System.out.println("retrivlRef_1："+retrivlRef_1);
						System.out.println("sysSsn_1："+sysSsn_1);
						System.out.println("termId_1："+termId_1);
						String instCodeSql = "select RCVG_CODE from TBL_N_TXN where substr(INST_DATE,0,8) = '"+createDate_1.substring(0, 8)+"' and RETRIVL_REF = '"+retrivlRef_1+"' and SYS_SEQ_NUM = '"+sysSsn_1+"' and CARD_ACCP_TERM_ID = '"+termId_1+"'";
						String instCodeSqlHis = "select RCVG_CODE from TBL_N_TXN_HIS where substr(INST_DATE,0,8) = '"+createDate_1.substring(0, 8)+"' and RETRIVL_REF = '"+retrivlRef_1+"' and SYS_SEQ_NUM = '"+sysSsn_1+"' and CARD_ACCP_TERM_ID = '"+termId_1+"'";
						instCode = commQueryDAO.findCountBySQLQuery(instCodeSql);
						System.out.println("==============================================");
						System.out.println("instCode机构号："+instCode);
						if (instCode.equals("")) {
							System.out.println("==============================================");
							instCode = commQueryDAO.findCountBySQLQuery(instCodeSqlHis);
							System.out.println("instCode机构号："+instCode);
						}
					} catch (Exception e) {
						log("获取机构号："+e.getMessage());
					}
					instCode = instCode.trim();
					TblChangeAccInfTmp tblChangeAccInfTmp = new TblChangeAccInfTmp();
					TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
					try {
						tblChangeAccInfTmpId.setMchtNo(mchtNo);
						tblChangeAccInfTmpId.setTermId(termId);
						tblChangeAccInfTmpId.setChangeDate(CommonFunction.getCurrentDateTime());
						tblChangeAccInfTmp.setId(tblChangeAccInfTmpId);
						tblChangeAccInfTmp.setChangeFlag("0");// 调账状态
						tblChangeAccInfTmp.setInstCode(manualreturn.getInstCode());
						tblChangeAccInfTmp.setChangeReason("退货延迟结算完成，调回退款金额");// 调账理由*
						tblChangeAccInfTmp.setChangeAccount(Double.valueOf(manualreturn.getAmtReturn()));
						tblChangeAccInfTmp.setSt("0");// 状态
						tblChangeAccInfTmp.setInsTs(CommonFunction.getCurrentDateTime());
						tblChangeAccInfTmp.setEnterTp("0");// 录入
						tblChangeAccInfTmp.setComfirmAccount(Double.valueOf(0));
						tblChangeAccInfTmp.setInsOpr(operator.getOprId());
						tblChangeAccInfTmp.setUpdTs(CommonFunction.getCurrentDateTime());
						tblChangeAccInfTmp.setUpdOpr(operator.getOprId());
						tblChangeAccInfTmp.setInstCode(instCode);
						t100201BO.addone(tblChangeAccInfTmp);
					} catch (Exception e) {
						log("临时表更新失败："+e.getMessage());
					}
					// 正式表
					try {
						TblChangeAccInf tblChangeAccInf = new TblChangeAccInf();
						TblChangeAccInfId tblChangeAccInfId = new TblChangeAccInfId();
						tblChangeAccInfId.setMchtNo(mchtNo);
						tblChangeAccInfId.setTermId(termId);
						tblChangeAccInfId.setChangeDate(CommonFunction.getCurrentDateTime());
						tblChangeAccInf.setId(tblChangeAccInfId);
						tblChangeAccInf.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
						tblChangeAccInf.setChangeReason(tblChangeAccInfTmp.getChangeReason());
						tblChangeAccInf.setInstCode(tblChangeAccInfTmp.getInstCode());
						tblChangeAccInf.setChangeFlag("0");
						tblChangeAccInf.setAprTs(CommonFunction.getCurrentDateTime());
						tblChangeAccInf.setAprOpr(operator.getOprId());
						tblChangeAccInf.setInsOpr(tblChangeAccInfTmp.getInsOpr());
						tblChangeAccInf.setInsTs(tblChangeAccInfTmp.getInsTs());
						tblChangeAccInf.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
						tblChangeAccInf.setUpdTs(tblChangeAccInfTmp.getUpdTs());
						tblChangeAccInf.setSt("0");
						tblChangeAccInf.setInstCode(instCode);
						tblChangeAccInf.setEnterTp(tblChangeAccInfTmp.getEnterTp());
						t100201BO.saveOrUpdate(tblChangeAccInf);
					} catch (Exception e) {
						log("正式表更新失败："+e.getMessage());
					}
					try {
						Thread.sleep(1010);
					} catch (Exception e) {
						log("休眠失败"+e.getMessage());
					}
				} catch (Exception e) {
					ManualReturn manualReturn =t100101BO.get(idkey);
					if (manualreturn.getSrcId() == "1901") {
						manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
					}
//					manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
					manualreturn.setRspCode("00");
//					manualReturn.setTxnInfo(refuseInfo);
					manualReturn.setSaState("5");//后台审核通过，前台失败
					manualReturn.setUpdId(operator.getOprId());
					manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
					t100101BO.saveOrUpdate(manualReturn);
					log("操作员编号：" + operator.getOprId()+ "，手工退货审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
					e.printStackTrace();
					return "后台退货成功，前端出错！请联系管理员";
				}
			} else if ("refuse".equals(method)) {
				rspCode = refuse(idkey);
			}
		}

		return rspCode;
	}

	private String refuse(String idkey) {
		
			ManualReturn manualReturn =t100101BO.get(idkey);
//			manualReturn.setTxnInfo(refuseInfo);
			manualReturn.setSaState("4");//审核拒绝
			manualReturn.setUpdId(operator.getOprId());
			manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
			t100101BO.saveOrUpdate(manualReturn);

/*		ManualReturn manualReturn =t100101BO.get(ID);
		if(manualReturn==null){
			return "该记录不存在或已处理";
		}
		manualReturn.setTxnInfo(refuseInfo);
		manualReturn.setSaState("4");//审核拒绝
		manualReturn.setUpdId(operator.getOprId());
		manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
		t100101BO.saveOrUpdate(manualReturn);*/
		return "00";
	}
	/**
	 * 审核通过新增待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd(String idkey) throws Exception {

		
			ManualReturn manualreturn = t100101BO.get(idkey);
			manualreturn.setRspCode("00");
			if (manualreturn.getSrcId() == "1901") {
				manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
			}
//			manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
			manualreturn.setOprId(operator.getOprId());
			manualreturn.setUpdId(operator.getOprId());
			manualreturn.setCreateDate(CommonFunction.getCurrentDateTime());
			manualreturn.setUpdDate(CommonFunction.getCurrentDateTime());
			manualreturn.setSaState("1");
			t100101BO.saveOrUpdate(manualreturn);
			
		
		
		return Constants.SUCCESS_CODE;
	
		
	
	}
	/**
	 * 审核通过删除待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete(String idkey) throws Exception {
		
			
			ManualReturn manualreturn = t100101BO.get(idkey);
			t100101BO.delete(manualreturn.getId());
			
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过修改待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify(String idkey) throws Exception {
		
			ManualReturn manualreturn = t100101BO.get(idkey);
			manualreturn.setRspCode("00");
			if (manualreturn.getSrcId() == "1901") {
				manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
			}
//			manualreturn.setPotalSsn(manualreturn.getId() + "%%%");
			manualreturn.setOprId(operator.getOprId());
			manualreturn.setUpdId(operator.getOprId());
			manualreturn.setCreateDate(CommonFunction.getCurrentDateTime());
			manualreturn.setUpdDate(CommonFunction.getCurrentDateTime());
			manualreturn.setSaState("1");
			t100101BO.saveOrUpdate(manualreturn);
			
			
		return Constants.SUCCESS_CODE;
	}
	
	/*private String accept() throws IllegalAccessException, InvocationTargetException {
		ManualReturn manualreturn =t100101BO.get(ID);
		if(manualreturn==null){
			return "该记录不存在或已处理";
		}
		if("0".equals(manualreturn.getSaState())){
			
			//将元转换为分
			long amt=(long)(Float.parseFloat(manualreturn.getAmtReturn()));
			System.out.println(amt);
			//因为流水表中只存7天的数据，为了确保都更新到，在2个表中都执行更新操作
			//系统流水号+交易日期 可以确定唯一一笔交易流水
			
			 判断审核通过的时候退货金额是否大于原交易金额
			 * 20141009 by shiyiwen
			 
			String amtTrans = manualreturn.getAmtTrans();
			String amtReturnSql = "select to_number(amt_return) From tbl_n_txn where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and INST_DATE='"+manualreturn.getInstDate()+"'";
			String amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
			if("0".equals(amtReturn)){
				amtReturnSql = "select to_number(amt_return) From tbl_n_txn_his where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and INST_DATE='"+manualreturn.getInstDate()+"'";
				amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
				if("".equals(amtReturn))
					amtReturn = "0";
				if((amt+Double.valueOf(amtReturn))>Double.valueOf(amtTrans)){
					return "退货累计金额不能超过原交易金额";
				}
			}else{
				if((amt+Double.valueOf(amtReturn))>Double.valueOf(amtTrans)){
					return "退货累计金额不能超过原交易金额";
				}
			}
			
			//修改流水表
			String sqlN="Update tbl_n_txn Set amt_return= lpad(to_number(amt_return)+"+amt+",12,'0'),trans_state='R' where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and inst_date='"+manualreturn.getInstDate()+"'";
			//修改流水历史表
			String sqlH="Update tbl_n_txn_his Set amt_return= lpad(to_number(amt_return)+"+amt+",12,'0'),trans_state='R' where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and inst_date='"+manualreturn.getInstDate()+"'";
			manualreturn.setOprId(operator.getOprId());
			manualreturn.setUpdId(operator.getOprId());
			manualreturn.setCreateDate(CommonFunction.getCurrentDateTime());
			manualreturn.setUpdDate(CommonFunction.getCurrentDateTime());
			manualreturn.setSaState("1");
			t100101BO.saveOrUpdate(manualreturn);
			commQueryDAO.excute(sqlN);
			commQueryDAO.excute(sqlH);
			return Constants.SUCCESS_CODE;
		
			
		}else if ("2".equals(manualreturn.getSaState())){
			long amt=(long)(Float.parseFloat(manualreturn.getAmtReturn()));
			System.out.println(amt);
			//因为流水表中只存7天的数据，为了确保都更新到，在2个表中都执行更新操作
			//系统流水号+交易日期 可以确定唯一一笔交易流水
			
			 判断审核通过的时候退货金额是否大于原交易金额
			 * 20141009 by shiyiwen
			 
			String amtTrans = manualreturn.getAmtTrans();
			String amtReturnSql = "select to_number(amt_return) From tbl_n_txn where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and INST_DATE='"+manualreturn.getInstDate()+"'";
			String amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
			if("0".equals(amtReturn)){
				
				amtReturnSql = "select to_number(amt_return) From tbl_n_txn_his where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and INST_DATE='"+manualreturn.getInstDate()+"'";
				amtReturn = commQueryDAO.findCountBySQLQuery(amtReturnSql);
				if("".equals(amtReturn))
					amtReturn = "0";
				if((amt+Double.valueOf(amtReturn))>Double.valueOf(amtTrans)){
					return "退货累计金额不能超过原交易金额";
				}
			}else{
				if((amt+Double.valueOf(amtReturn))>Double.valueOf(amtTrans)){
					return "退货累计金额不能超过原交易金额";
				}
			}
			
			
			//修改流水表
			String sqlN="Update tbl_n_txn Set amt_return= lpad(to_number(amt_return)+"+amt+",12,'0'),trans_state='R' where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and inst_date='"+manualreturn.getInstDate()+"'";
			//修改流水历史表
			String sqlH="Update tbl_n_txn_his Set amt_return= lpad(to_number(amt_return)+"+amt+",12,'0'),trans_state='R' where  SYS_SEQ_NUM='"+manualreturn.getSysSsn()+"' and inst_date='"+manualreturn.getInstDate()+"'";
			manualreturn.setOprId(operator.getOprId());
			manualreturn.setUpdId(operator.getOprId());
			manualreturn.setCreateDate(CommonFunction.getCurrentDateTime());
			manualreturn.setUpdDate(CommonFunction.getCurrentDateTime());
			manualreturn.setSaState("1");
			t100101BO.saveOrUpdate(manualreturn);
			commQueryDAO.excute(sqlN);
			commQueryDAO.excute(sqlH);
			return Constants.SUCCESS_CODE;
		}else if ("3".equals(manualreturn.getSaState())){
			if(t100101BO.get(ID) == null) {
				return "没有找到要相应的信息";
			}
			
			t100101BO.delete(ID);
			
			return Constants.SUCCESS_CODE;
		}else{
			return Constants.FAILURE_CODE;
		}
	}*/

	private String ID;
	private String refuseInfo;
	private String infList;
	
	
	public String getInfList() {
		return infList;
	}

	public void setInfList(String infList) {
		this.infList = infList;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	 /**
     * 字符左补零
     * @param code
     * @param len
     * @return
     */
    public static String codeAddOne(String code, int len){
        Integer intHao = Integer.parseInt(code);
//        intHao++;
        String strHao = intHao.toString();
        while (strHao.length() < len) {
            strHao = "0" + strHao;
        }
        return strHao;
    }
}
