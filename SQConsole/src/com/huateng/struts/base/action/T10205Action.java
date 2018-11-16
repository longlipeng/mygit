package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jaxen.function.SubstringFunction;

import com.huateng.bo.base.T10205BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.TblBankBinInfDAO;

import com.huateng.po.TblBankBinInf;

import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title:银联卡表维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10205Action extends BaseAction {
	/**
	 * 1-导入
	 */
	private static String AUTO = "1";
	/**
	 * 0-手动
	 */
	private static String MANUAL = "0";
	
	
	T10205BO t10205BO = (T10205BO) ContextUtil.getBean("T10205BO");

	private String ind;
	private String CardTp;
	
	private String insIdCd;
	//一磁偏移量
	private String acc1Offset;	
    //一磁长度
	private String acc1Len;
	//一磁所在磁道号
	private String acc1Tnum;	
	//二磁偏移量
	private String acc2Offset;	
	//二磁长度
	private String acc2Len;	
	//二磁所在磁道号
	private String acc2Tnum;
	//卡bin偏移量
	private String binOffSet;
	//卡bin长度
	private String binLen;
	//卡bin起始值
	private String binStaNo;	
	//卡bin结束值
	private String binEndNo;	
	//卡bin所在磁道号
	private String binTnum;	
	//描述
	private String cardDis;	
	
	private String tblBankBinInfList;
	
	private String infList;
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {					
				 rspCode = add();				
			} else if("delete".equals(method)) {				
				rspCode = deleteall();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，银联卡表维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add() throws IllegalAccessException, InvocationTargetException{
		
		TblBankBinInf tblBankBinInf = new TblBankBinInf();
		String bankBinId = GenerateNextId.getBankBinId();
		BeanUtils.copyProperties(tblBankBinInf, this);	
		tblBankBinInf.setId(Integer.parseInt(bankBinId));
		tblBankBinInf.setInsIdCd(StringUtil.trim(insIdCd));
		tblBankBinInf.setCardDis(StringUtil.trim(cardDis));
		tblBankBinInf.setCardTp(StringUtil.trim(CardTp));
		tblBankBinInf.setAcc1Offset(StringUtil.trim(acc1Offset));
		tblBankBinInf.setAcc1Len(StringUtil.trim(acc1Len));
		tblBankBinInf.setAcc1Tnum(StringUtil.trim(acc1Tnum));
		tblBankBinInf.setBinOffSet(StringUtil.trim(binOffSet));
		tblBankBinInf.setBinLen(StringUtil.trim(binLen));
		tblBankBinInf.setBinStaNo(StringUtil.trim(binStaNo));
		tblBankBinInf.setBinEndNo(StringUtil.trim(binEndNo));
		tblBankBinInf.setBinTnum(StringUtil.trim(binTnum));
//		tblBankBinInf.setRecOprId(operator.getOprId());
//		tblBankBinInf.setRecUpdOpr(operator.getOprId());
		tblBankBinInf.setRecCrtTs(CommonFunction.getCurrentDateTime());		
		tblBankBinInf.setRecUpdTs(CommonFunction.getCurrentDateTime());	
		tblBankBinInf.setAcc2Tnum(MANUAL);
		
		String sql = "select count(*) from tbl_bank_bin_inf where bin_sta_no = '"+binStaNo+"' AND ACC1_LEN = '" + acc1Len + "' ";
//		String wheresql = "select count(*) from tbl_bank_bin_inf where  bin_end_no = '"+binEndNo+"'";
		
		String count = commQueryDAO.findCountBySQLQuery(sql);
//		String count1 = commQueryDAO.findCountBySQLQuery(wheresql);
		if(!"0".equals(count)){
			return "所添加的卡bin已存在";
		}
//		if(!"0".equals(count1)){
//			return "所添加的卡bin已存在";
//		}
		t10205BO.createTblBankBinInf(tblBankBinInf);
		log("新增新联卡表信息成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String deleteall() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
//		TblBankBinInf tblBankBinInf = null;
//		List<TblBankBinInf> tblBankBinInfList = new ArrayList<TblBankBinInf>(len);
		List<Integer> list = new ArrayList<Integer>(len);
		try {
			for (int i = 0; i < len; i++) {
//				jsonBean.setObject(jsonBean.getJSONDataAt(i));
//				tblBankBinInf = TblBankBinInfDAO.get(jsonBean.getJSONDataAt(i).getString("ind"));
//				BeanUtils.setObjectWithPropertiesValue(tblBankBinInf, jsonBean,false);
//				tblBankBinInfList.add(tblBankBinInf);
				list.add(new Integer(jsonBean.getJSONDataAt(i).getString("ind")));
			}
			t10205BO.deleteall(list);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}

/*			t10205BO.delete(Integer.parseInt(ind));
			log("删除新联卡表信息成功。操作员编号：" + operator.getOprId());
				return Constants.SUCCESS_CODE;*/

	}
	
	/**
	 * 
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ASException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getTblBankBinInfList());
		
		int len = jsonBean.getArray().size();
		
		TblBankBinInf tblBankBinInf = null;
		List<TblBankBinInf> tblBankBinInfList = new ArrayList<TblBankBinInf>(len);
		try {
			for (int i = 0; i < len; i++) {
				tblBankBinInf = t10205BO.get(new Integer(jsonBean
						.getJSONDataAt(i).getString("ind")));
				BeanUtils.setObjectWithPropertiesValue(tblBankBinInf, jsonBean,
						false);
				tblBankBinInf.setCardDis(tblBankBinInf.getCardDis().trim());
				tblBankBinInfList.add(tblBankBinInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t10205BO.update(tblBankBinInfList);

		log("更新银联卡表信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	
	private String upload(){
		List<TblBankBinInf> tblBankBinInfList = new ArrayList<TblBankBinInf>();
//		Map<String, String> map = new HashMap<String, String>();
		try {
			TblBankBinInf tblBankBinInf = null;
			for(File file : files) {
				BufferedReader reader = 
					new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						String[] data = tmp.split(",");
						if(data.length>=14){
							tblBankBinInf = new TblBankBinInf();
							tblBankBinInf.setInsIdCd(StringUtil.trim(data[0]));
	
							tblBankBinInf.setAcc1Offset(StringUtil.trim(data[4]));
							tblBankBinInf.setAcc1Len(StringUtil.trim(data[5]));
							if("2 3".equals(data[3])){
								tblBankBinInf.setAcc1Tnum("2");
							}else{
								tblBankBinInf.setAcc1Tnum(StringUtil.trim(data[3]));
//								tblBankBinInf.setAcc1Tnum(data[3].trim());
								
							}
							
							
							tblBankBinInf.setBinOffSet(StringUtil.trim(data[8]));
							tblBankBinInf.setBinLen(StringUtil.trim(data[9]));
							if(StringUtil.trim(data[6]).length()== 6){
								tblBankBinInf.setBinStaNo(StringUtil.trim(data[6]));
								tblBankBinInf.setBinEndNo(StringUtil.trim(data[6]));
							}else{
	//							tblBankBinInf.setBinStaNo(StringUtil.trim(data[6].substring(0,6)));
								tblBankBinInf.setBinStaNo(StringUtil.trim(data[6]));
								tblBankBinInf.setBinEndNo(StringUtil.trim(data[6]));
	//							tblBankBinInf.setBinEndNo(StringUtil.trim(data[6].substring(6,StringUtil.trim(data[6]).length()-1)));
							
							}
							if("2 3".equals(data[7])||"2&3".equals(data[7])){
								tblBankBinInf.setBinTnum("2");
							}else{
								tblBankBinInf.setBinTnum(StringUtil.trim(data[7]));
							}
							if("1".equals(StringUtil.trim(data[14]))){
								tblBankBinInf.setCardTp("00");
							}else if("2".equals(StringUtil.trim(data[14]))){
								tblBankBinInf.setCardTp("01");
							}else if("3".equals(StringUtil.trim(data[14]))){
								tblBankBinInf.setCardTp("02");
							}else if("4".equals(StringUtil.trim(data[14]))){
								tblBankBinInf.setCardTp("03");
							}
	//						tblBankBinInf.setCardTp(StringUtil.trim(data[14]));
							/*if (!StringUtil.isNull(StringUtil.trim(data[14]))) {
								if ("借记卡".equals(StringUtil.trim(data[14]))) {
									tblBankBinInf.setCardTp("01");
								} else if ("1".equals(StringUtil.trim(data[14]))){
									tblBankBinInf.setCardTp("00");
								} else if ("2".equals(StringUtil.trim(data[14]))){
									tblBankBinInf.setCardTp("02");
								} else if ("3".equals(StringUtil.trim(data[14]))){
									tblBankBinInf.setCardTp("03");
								} else{
									tblBankBinInf.setCardTp(StringUtil.trim(data[14]));
								}
							}*/
							tblBankBinInf.setCardDis(StringUtil.trim(data[1]));
							
							tblBankBinInf.setRecCrtTs(CommonFunction.getCurrentDateTime());		
							tblBankBinInf.setRecUpdTs(CommonFunction.getCurrentDateTime());	
							
							tblBankBinInfList.add(tblBankBinInf);
						}
//						map.put(data[15] + data[17], tmp);
					}
				}
				reader.close();
			}
			
			int success = 0;
			int fail = 0;
			
			StringBuffer sbNo = new StringBuffer();
			StringBuffer sbNum = new StringBuffer();
			
			for(TblBankBinInf inf:tblBankBinInfList){
				String sql = "select count(1) from TBL_BANK_BIN_INF where " +
						"BIN_STA_NO = '" + inf.getBinStaNo() + "' " + "and ACC1_LEN = '" + inf.getAcc1Len() + "'" + 
						" and acc2_tnum = '"+AUTO+"'"; 
				//拼接BinStaNo
				sbNo.append("'").append(inf.getBinStaNo()).append("',");
				//拼接BinTnum
				sbNum.append("'").append(inf.getBinTnum()).append("',");
				
				BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
				String bankBinId = GenerateNextId.getBankBinId();
				inf.setId(Integer.parseInt(bankBinId));
				inf.setAcc2Tnum(AUTO);
				if (count.intValue() != 0) {
					//TODO 是否更新
					String wheresql = "select ind from TBL_BANK_BIN_INF where " +
					"BIN_STA_NO = '" + inf.getBinStaNo() + "' " + "and ACC1_LEN = '" + inf.getAcc1Len() + "'" + 
					" and acc2_tnum = '"+AUTO+"'"; 
			    	List list =  CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
			    	inf.setId(Integer.parseInt(list.get(0).toString()));
			    	System.out.println(Integer.parseInt(list.get(0).toString()));
					t10205BO.update(inf);
					System.out.println("BIN_STA_NO aa ="+ inf.getBinStaNo() + "and BIN_LEN bb = " + inf.getBinLen());
					fail++;
					continue;
				}
				
				t10205BO.createTblBankBinInf(inf);
				success++;
			}
			
			int delNum = 0;
			//TODO 待删除标志确定后修改
			
/*			if(sbNo!=null&&sbNo.toString().length()>0&&sbNum!=null&&sbNum.toString().length()>0){
				String delSql = "select ind from tbl_bank_bin_inf where ind not in (" +
							" select ind from tbl_bank_bin_inf where "+
							" bin_sta_no  in ( "+ sbNo.toString().substring(0,sbNo.toString().length()-1)+ ") " +
							" and BIN_TNUM  in ( " + sbNum.toString().substring(0, sbNum.toString().length()-1) + ") " +	
							" and acc2_tnum = '"+AUTO+"') " +
							" and acc2_tnum = '"+AUTO+"'";
			
				List inds = CommonFunction.getCommQueryDAO().findBySQLQuery(delSql);
				delNum = inds.size();
				List<Integer> dellist = new ArrayList<Integer>();
				for (Object object : inds) {
					int tmp = Integer.parseInt(object.toString());
					dellist.add(tmp);
				}
				
				t10205BO.deleteall(dellist);
				
				
			}*/

			
			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",更新条目：" + String.valueOf(fail) +",删除条目:" + delNum ;
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

	public String getInfList() {
		return infList;
	}


	public void setInfList(String infList) {
		this.infList = infList;
	}
	
	public T10205BO getT10205BO() {
		return t10205BO;
	}

	public void setT10205BO(T10205BO t10205bo) {
		t10205BO = t10205bo;
	}

	public String getCardTp() {
		return CardTp;
	}

	public void setCardTp(String cardTp) {
		CardTp = cardTp;
	}

	public String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public String getAcc1Offset() {
		return acc1Offset;
	}

	public void setAcc1Offset(String acc1Offset) {
		this.acc1Offset = acc1Offset;
	}

	public String getAcc1Len() {
		return acc1Len;
	}

	public void setAcc1Len(String acc1Len) {
		this.acc1Len = acc1Len;
	}

	public String getAcc1Tnum() {
		return acc1Tnum;
	}

	public void setAcc1Tnum(String acc1Tnum) {
		this.acc1Tnum = acc1Tnum;
	}

	public String getAcc2Offset() {
		return acc2Offset;
	}

	public void setAcc2Offset(String acc2Offset) {
		this.acc2Offset = acc2Offset;
	}

	public String getAcc2Len() {
		return acc2Len;
	}

	public void setAcc2Len(String acc2Len) {
		this.acc2Len = acc2Len;
	}

	public String getAcc2Tnum() {
		return acc2Tnum;
	}

	public void setAcc2Tnum(String acc2Tnum) {
		this.acc2Tnum = acc2Tnum;
	}
	
	public String getBinStaNo() {
		return binStaNo;
	}

	public void setBinStaNo(String binStaNo) {
		this.binStaNo = binStaNo;
	}

	public String getBinEndNo() {
		return binEndNo;
	}

	public void setBinEndNo(String binEndNo) {
		this.binEndNo = binEndNo;
	}

	public String getBinTnum() {
		return binTnum;
	}

	public void setBinTnum(String binTnum) {
		this.binTnum = binTnum;
	}

	public String getCardDis() {
		return cardDis;
	}

	public void setCardDis(String cardDis) {
		this.cardDis = cardDis;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getTblBankBinInfList() {
		return tblBankBinInfList;
	}

	public void setTblBankBinInfList(String tblBankBinInfList) {
		this.tblBankBinInfList = tblBankBinInfList;
	}

	public String getBinOffSet() {
		return binOffSet;
	}

	public void setBinOffSet(String binOffSet) {
		this.binOffSet = binOffSet;
	}

	public String getBinLen() {
		return binLen;
	}

	public void setBinLen(String binLen) {
		this.binLen = binLen;
	}
	

	// 文件集合
	private List<File> files;
	// 文件名称集合
	private List<String> filesFileName;


	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}

	/**
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	
}
