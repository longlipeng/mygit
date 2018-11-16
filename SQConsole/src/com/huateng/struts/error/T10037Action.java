package com.huateng.struts.error;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.huateng.bo.error.T10031BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SysParamUtil;

public class T10037Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private T10031BO t10031BO =   (T10031BO)  ContextUtil.getBean("T10031BO");
	private List<File> files;
	private List<String> filesFileName;
	/**
	 * 导入差错文件 
	 * 差错必须都是短款的差错
	 * @return
	 */
	public String upload(){
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		String errflag = "";
		try {
			String fileName = "";
			int fileNameIndex = 0;
			String basePath=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
			basePath += "/";
			basePath = basePath.replace("\\", "/");
			for(File file : files) {
				is = new FileInputStream(file);
				fileName = filesFileName.get(fileNameIndex);
				log("WRITE FILE:" + basePath + fileName);
				File writeFile = new File(basePath + fileName);
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				if (writeFile.exists()) {
					writeFile.delete();
				}
				dis = new DataInputStream(is);
				out = new DataOutputStream(new FileOutputStream(writeFile));

				int temp;
				while((temp = dis.read()) != -1){
					out.write(temp);
				}
				out.flush();
			}
			File uploadFile=new File(basePath + fileName);
			BufferedReader reader = 
				new BufferedReader(new InputStreamReader(new FileInputStream(uploadFile), "GBK"));
			String txnSsn=GenerateNextId.getSSn();
			boolean nextFlag=false;
			int succ=0;
			int fail=0;
			while(reader.ready()){
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = trimSpace(tmp.trim()).split(" ");//去掉前后空格
				System.out.println("----------------"+strs.length);
				System.out.println(">>>>>>>>>>>>>>>>"+trimSpace(tmp.trim()));			
				if(strs.length < 10)
					continue;
				try{
					Long.parseLong(strs[0]);//如果第一列不为数字 则继续下一循环
					
					String termId=strs[0];//终端号
					String pan=strs[2];//交易卡号
					String transDate=strs[3];//交易日期
					String transTime=strs[4];//交易时间
					String retrivlRef = strs[12];//参考号
					
					//根据差错信息去对账不平明细表中查询相关数据
					String errGcSql="select acq_txn_num,IPS_NO,ACQ_MCHT_CD,MCHNT_TYPE,RCVG_CODE,ISSUER_CODE,MCHT_ACC_IN_TYPE,ACQ_MCHT_FEE,POS_ENTRY_MODE,ACQ_TXN_SSN,TERM_SSN " +
							" from  bth_dtl_err_gc where trim(CARD_ACCP_TERM_ID)='"+termId+"' and trim(acq_pan)='" + pan +
							"' and ACQ_TXN_TIME='"+transTime+"' and ACQ_TXN_DATE='"+transDate+"' and RETRIVL_REF='" + retrivlRef + "'";
					List<Object[]> list = commQueryDAO.findBySQLQuery(errGcSql);
					if(list==null || list.size()==0){
						fail++;
						continue;
					}
					String batchNo = strs[1];//批号
					String amtTrans=strs[5];//交易金额
					String fee=strs[6];//手续费
					String amtSettle=strs[7];//结算金额
					String authorRsp = strs[8];//授权码
					String tranCode = strs[9];//交易码
					String staging = strs[10];//分期
					String cardType = strs[11];//卡别
					
					BthCupErrTxn errTxn=new BthCupErrTxn();
					BthCupErrTxnPK pk=new BthCupErrTxnPK();
					//待定
					txnSsn=this.getNextId(txnSsn, nextFlag);
					nextFlag=true;
					pk.setDateSettlmt(CommonFunction.getCurrentDate());
					pk.setTxnNum((String) list.get(0)[0]);
					pk.setTxnSsn(txnSsn);//差错流水号
					errTxn.setId(pk);
					errTxn.setAuthorRspCd(authorRsp);//授权码
					errTxn.setMsgType(staging);//分期
					errTxn.setProccessingCode(tranCode);//交易码
					errTxn.setXferFee(batchNo + cardType);//批号+卡别
					errTxn.setErrTrigFlag("00");//差错发起标识
					errTxn.setStlmFlg("0");//清分状态
//					errTxn.setTransDateTime(transDate);
					errTxn.setTransDateTime(transTime);//交易时间
					errTxn.setErrFlag("E22");//E22－请款
					errTxn.setPan(pan);//交易卡号
					//交易金额,20121126修改为添加负号，20121205修改为不要添加负号
//					errTxn.setAmtTrans("-" + CommonFunction.fillString(String.valueOf((long)(Float.parseFloat(amtTrans)*100)), '0', 11, false));
					errTxn.setAmtTrans(CommonFunction.fillString(String.valueOf((long)(Float.parseFloat(amtTrans)*100)), '0', 12, false));
					errTxn.setTxnFeeC(CommonFunction.fillString("", '0', 12, false));
					errTxn.setTxnFeeD(CommonFunction.fillString("", '0', 12, false));
					errTxn.setAcqInsIdCd(CommonFunction.fillString("30000000",' ',11,true));//代理机构代码
					errTxn.setRcvInsIdCd(String.valueOf(list.get(0)[4]));//接受机构代码
					errTxn.setIssInsIdCd(String.valueOf(list.get(0)[5]));//发卡行
					errTxn.setPosEntryMode(String.valueOf(list.get(0)[8]));//服务点输入方式
					errTxn.setMchtId(String.valueOf(list.get(0)[2]));//商户号
					errTxn.setIpsNo(String.valueOf(list.get(0)[1]));//ips商户号
					errTxn.setTermId(termId);//终端号
					errTxn.setMchtTp(String.valueOf(list.get(0)[3]));//商户类型MCC码
					errTxn.setMchtAccInType(CommonFunction.fillString(String.valueOf(list.get(0)[6]), '0', 2, false));//商户收支2条线标识
					errTxn.setRespCode("  ");
					errTxn.setFeeDebit(CommonFunction.fillString(String.valueOf((long)(Float.parseFloat(fee)*100)), '0', 12, false));//应付手续费
					errTxn.setFeeCredit(CommonFunction.fillString(String.valueOf(list.get(0)[7]), '0', 12, false));//应收手续费
					errTxn.setAmtTransFee(CommonFunction.fillString("", '0', 12, false));
					errTxn.setFwdInsIdCd("30000000   ");//发送机构标识码
					errTxn.setTermCap("0");//终端读取能力
					errTxn.setOrgTransAmt(CommonFunction.fillString(String.valueOf((long)(Float.parseFloat(amtTrans)*100)),'0',12,false));//原始交易金额
					errTxn.setOrgRetrivlRef(retrivlRef);//原系统交易参考号
					errTxn.setOrgTxnSsn(String.valueOf(list.get(0)[9]));//原系统流水号
					errTxn.setOrgTermSsn(String.valueOf(list.get(0)[10]));//原终端流水号
					errTxn.setOrgDateTime(transDate);//原交易日期
					errTxn.setLstUpdTlr(operator.getOprId());
					errTxn.setCreateTime(CommonFunction.getCurrentDateTime());
					errTxn.setLstUpdTime(CommonFunction.getCurrentDateTime());
					//保存差错流水信息
					t10031BO.saveOrUpdate(errTxn);
					//删除对账不平明细表中的相关数据
					String del="delete from bth_dtl_err_gc where trim(CARD_ACCP_TERM_ID)='"+termId+"' and trim(acq_pan)='"+pan+"' and ACQ_TXN_TIME='"+transTime+"' and ACQ_TXN_DATE='"+transDate+"' and RETRIVL_REF='"+retrivlRef+"'";
					commQueryDAO.excute(del);
					succ++;
				}catch(Exception e){
					continue;
				}
				System.out.println(tmp);
			}
			reader.close();
			return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)+"条数据";
		} catch (Exception e) {
			e.printStackTrace();
			return "导入差错文件失败";
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != dis) {
					dis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	/**
	 * 将不定空格分隔符 替换为单空格分隔符
	 * @param str
	 * @return
	 */
	public String trimSpace(String str){
		do{
			str=str.replace("  ", " ");
			str=str.replace("	", " ");//将文件中空格替换为空格
		}while(!str.equals(str.replaceAll("  ", " ")));
		return str;
	}
	
	public String getNextId(String id,boolean isNext){
		if(isNext){
			int s=1000001;
			s+=Integer.valueOf(id);
			id=String.valueOf(s).substring(1);
		}
		return id;
	}
	@Override
	protected String subExecute() throws Exception {
		try {
			if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，导入差错文件操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
}
