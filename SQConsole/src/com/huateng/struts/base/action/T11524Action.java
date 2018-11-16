package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import oracle.net.ns.Communication;

import com.huateng.bo.base.T10206BO;
import com.huateng.bo.base.T11501BO;
import com.huateng.bo.base.T11521BO;
import com.huateng.bo.rout.T11015BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.TblBankBinInf;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SysParamUtil;

public class T11524Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private T11521BO t11521BO = (T11521BO) ContextUtil.getBean("T11521BO");
	public static String ADD_TO_CHECK = "0";
	private List<File> files;
	private List<String> filesFileName;
	private String AOwner;
	
	

	public String getAOwner() {
		return AOwner;
	}

	public void setAOwner(String aOwner) {
		AOwner = aOwner;
	}

	/**
	 * 导入信息 
	 * @return
	 */
	public String upload(){
//		List<TblAgentFeeTmp> tblAgentFeeTmp = new ArrayList<TblAgentFeeTmp>();
		try {
			int success = 0;
			int fail = 0;
			TblAgentFeeTmp tblAgentFeeTmp = null;
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						String[] data = tmp.split(",");
						if(data[0].trim().startsWith("代理商编号")){
							continue;
						}
						if(data.length==7){
							tblAgentFeeTmp = new TblAgentFeeTmp();
							tblAgentFeeTmp.setUuId(StringUtil.getUUID());
							tblAgentFeeTmp.setAgentNo(data[0]);
							tblAgentFeeTmp.setFeeMax(BigDecimal.valueOf(Double.valueOf(data[1])));
							tblAgentFeeTmp.setFeeMin(BigDecimal.valueOf(Double.valueOf(data[2])));
							tblAgentFeeTmp.setFeeValue(BigDecimal.valueOf(Double.valueOf(data[3])));
							if(data[4].endsWith("00")||data[4].endsWith("01")){
								tblAgentFeeTmp.setFeeType(data[4]);
							}else{
								return "费率方式设置有误，请检查导入文件";
							}
							
							if(data[5].length()==4){
								tblAgentFeeTmp.setMccCode(data[5]);
							}else{
								return "MCC设置有误，请检查导入文件";
							}
							
							if(data[6].endsWith("1702")||data[6].endsWith("1708")){
								tblAgentFeeTmp.setExtend1(data[6]);
							}else{
								return "通道设置有误，请检查导入文件";
							}
							
							tblAgentFeeTmp.setState("0");
							tblAgentFeeTmp.setCrtDate(CommonFunction.getCurrentDateTime());
							System.out.println("date ="+CommonFunction.getCurrentTime());
							tblAgentFeeTmp.setCrtPer(operator.getOprId());
							String sql = "select count(1) from tbl_agent_fee_tmp where agent_no = '"+ data[0] +"' and mcc_code = '"+ data[5]+"' and extend1 ='"+data[6]+"' ";
						//	BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
							String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
							
							if(!"0".equals(count)){
								String wheresql = "select uuid from tbl_agent_fee_tmp where agent_no = '"+ data[0] +"' and mcc_code = '"+ data[5]+"' and extend1 ='"+data[6]+"' ";
								List list = CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
								if(list.size()!=0){
									for(int i=0;i<list.size();i++){
										tblAgentFeeTmp.setUuId(list.get(0).toString());
										tblAgentFeeTmp.setState("3");
										t11521BO.update(tblAgentFeeTmp);
										fail++;
									}
								}
								
								continue;
							}
							
							t11521BO.add(tblAgentFeeTmp);
							success++;
							
						}
//						
					}
				}
				reader.close();
			}
			
			

			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",更新条目：" + String.valueOf(fail)  ;
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

	
	
	
	public T11521BO getT11521BO() {
		return t11521BO;
	}

	public void setT11521BO(T11521BO t11521bo) {
		t11521BO = t11521bo;
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
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，导入终端通道信息文件操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
}