package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T10209BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblHolidays;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10209Action  extends BaseAction {
	private static final long serialVersionUID = -3119552449953945813L;
	
	T10209BO t10209BO = (T10209BO) ContextUtil.getBean("T10209BO");
	private String id;
	private String name;
	private String createOprId;
	private String createTime;
	private String updOprId;
	private String updTime;
	private String saState;
	private String holidayStart;
	private String holidayEnd;
	private String tblHolidayList;
	private String unionFlag;
	
	
	public String getUnionFlag() {
		return unionFlag;
	}
	public void setUnionFlag(String unionFlag) {
		this.unionFlag = unionFlag;
	}
	// 文件集合
	private List<File> files;
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
	// 文件名称集合
	private List<String> filesFileName;
	/**
	 * @return the tblHolidayList
	 */
	public String getTblHolidayList() {
		return tblHolidayList;
	}
	/**
	 * @param tblHolidayList the tblHolidayList to set
	 */
	public void setTblHolidayList(String tblHolidayList) {
		this.tblHolidayList = tblHolidayList;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateOprId() {
		return createOprId;
	}
	public void setCreateOprId(String createOprId) {
		this.createOprId = createOprId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdOprId() {
		return updOprId;
	}
	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getSaState() {
		return saState;
	}
	public void setSaState(String saState) {
		this.saState = saState;
	}
	public String getHolidayStart() {
		return holidayStart;
	}
	public void setHolidayStart(String holidayStart) {
		this.holidayStart = holidayStart;
	}
	public String getHolidayEnd() {
		return holidayEnd;
	}
	public void setHolidayEnd(String holidayEnd) {
		this.holidayEnd = holidayEnd;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {					
				 rspCode = add();				
			} else if("delete".equals(method)) {				
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，节假日维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() {
		String sql="select count(*) from tbl_holidays where holiday_start='"+this.holidayStart+"' and holiday_end='"+this.holidayEnd+"' and SA_STATE <> '1'";
		String result = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		if(!result.equals("0")){
			return "该节假日信息已存在！";
		}
		String sql2="select count(*) from tbl_holidays where holiday_start>='"+this.holidayStart+"' and holiday_start<='"+this.holidayEnd+"' and SA_STATE <> '1'";
		String result2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql2);
		if(!result2.equals("0")){
			return "节假日信息时间段重复！";
		}
		String sql3="select count(*) from tbl_holidays where holiday_end>='"+this.holidayStart+"' and holiday_end<='"+this.holidayEnd+"' and SA_STATE <> '1'";
		String result3 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql3);
		if(!result3.equals("0")){
			return "节假日信息时间段重复！";
		}
		String sql4="select count(*) from tbl_holidays where holiday_start<='"+this.holidayStart+"' and holiday_end>='"+this.holidayEnd+"' and SA_STATE <> '1'";
		String result4 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql4);
		if(!result4.equals("0")){
			return "节假日信息时间段重复！";
		}
		
		TblHolidays tblHolidays = new TblHolidays();
		tblHolidays.setHolidayStart(this.holidayStart);
		tblHolidays.setHolidayEnd(this.holidayEnd);
		tblHolidays.setName(name);
		tblHolidays.setCreateOprId(operator.getOprId());
		tblHolidays.setCreateTime(CommonFunction.getCurrentDateTime());
		tblHolidays.setHolidayStartOld(this.holidayStart);
		tblHolidays.setHolidayEndOld(this.holidayEnd);
		tblHolidays.setNameOld(this.name);
		tblHolidays.setUnionFlag(this.unionFlag);
		tblHolidays.setSaState(CommonFunction.NORMAL);
				
		t10209BO.add(tblHolidays);
		log("添加节假日信息成功，等待审核。");
		return Constants.SUCCESS_CODE;
	}
	
	private String update() {
        jsonBean.parseJSONArrayData(getTblHolidayList());
		int len = jsonBean.getArray().size();
		
		TblHolidays tblHolidays = null;	
		List<TblHolidays> tblholidaysList = new ArrayList<TblHolidays>(len);
		try {
			for (int i = 0; i < len; i++) {
				tblHolidays = t10209BO.get(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(tblHolidays, jsonBean,false);
				/*String state = tblHolidays.getSaState();
				
				if(("2").equals(state)){//正常状态的节假日修改后状态为：修改待审核,否则还是原状态，如新增未审核或修改未审核
					tblHolidays.setSaState(CommonFunction.MODIFY_TO_CHECK);
				}*/
				tblHolidays.setHolidayStart(tblHolidays.getHolidayStart().split("T")[0].replace("-",""));
				tblHolidays.setHolidayEnd(tblHolidays.getHolidayEnd().split("T")[0].replace("-",""));
				tblHolidays.setCreateOprId(operator.getOprId());
				tblHolidays.setCreateTime(CommonFunction.getCurrentDateTime());
				tblHolidays.setUpdOprId(operator.getOprId());
				tblHolidays.setUpdTime(CommonFunction.getCurrentDateTime());
				String sql="select count(*) from tbl_holidays where holiday_start='"+tblHolidays.getHolidayStart()
					+"' and holiday_end='"+tblHolidays.getHolidayEnd()+"' and SA_STATE <> '1' and id != '"+tblHolidays.getId()+"'";
				String result = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
				if(!result.equals("0")){
					return "该节假日信息已存在！";
				}
				String sql2="select count(*) from tbl_holidays where holiday_start>='"+tblHolidays.getHolidayStart()+"' and holiday_start<='"+tblHolidays.getHolidayEnd()+"' and SA_STATE <> '1' and id != '"+tblHolidays.getId()+"'";
				String result2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql2);
				if(!result2.equals("0")){
					return "节假日信息时间段重复！";
				}
				String sql3="select count(*) from tbl_holidays where holiday_end>='"+tblHolidays.getHolidayStart()+"' and holiday_end<='"+tblHolidays.getHolidayEnd()+"' and SA_STATE <> '1' and id != '"+tblHolidays.getId()+"'";
				String result3 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql3);
				if(!result3.equals("0")){
					return "节假日信息时间段重复！";
				}
				String sql4="select count(*) from tbl_holidays where holiday_start<='"+tblHolidays.getHolidayStart()+"' and holiday_end>='"+tblHolidays.getHolidayEnd()+"' and SA_STATE <> '1' and id != '"+tblHolidays.getId()+"'";
				String result4 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql4);
				if(!result4.equals("0")){
					return "节假日信息时间段重复！";
				}
				//判断集合中是否已包含相同内容的记录20121129
				if(tblholidaysList!=null && tblholidaysList.size()>0){
					for(int j=0 ; j<tblholidaysList.size() ; j++){
						TblHolidays temp = tblholidaysList.get(j);
						if(tblHolidays.getHolidayStart().equals(temp.getHolidayStart()) 
								&& tblHolidays.getHolidayEnd().equals(temp.getHolidayEnd()) ) {
							return "提交的节假日信息有重复！";
						}
						if(Double.parseDouble(tblHolidays.getHolidayStart()) >= Double.parseDouble(temp.getHolidayStart()) &&
								Double.parseDouble(tblHolidays.getHolidayStart()) <= Double.parseDouble(temp.getHolidayEnd())){
							return "提交的节假日信息时间段重复！";
						}
						if(Double.parseDouble(tblHolidays.getHolidayEnd()) >= Double.parseDouble(temp.getHolidayStart()) &&
								Double.parseDouble(tblHolidays.getHolidayEnd()) <= Double.parseDouble(temp.getHolidayEnd())){
							return "提交的节假日信息时间段重复！";
						}
						if(Double.parseDouble(tblHolidays.getHolidayStart()) <= Double.parseDouble(temp.getHolidayStart()) &&
								Double.parseDouble(tblHolidays.getHolidayEnd()) >= Double.parseDouble(temp.getHolidayEnd())){
							return "提交的节假日信息时间段重复！";
						}
					}
				}
				tblholidaysList.add(tblHolidays);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t10209BO.update(tblholidaysList);

		log("更新节假日信息成功");
		return Constants.SUCCESS_CODE;
	}
	
	private String delete() {
		TblHolidays tblHolidays = t10209BO.get(id);
		/*String state = tblHolidays.getSaState();
		if("0".equals(state)){//新增未审核状态的节假日可直接删除
			t10209BO.delete(id);
		}
		tblHolidays.setCreateOprId(operator.getOprId());
		tblHolidays.setCreateTime(CommonFunction.getCurrentDateTime());
		tblHolidays.setSaState(CommonFunction.DELETE_TO_CHECK);
		
		t10209BO.update(tblHolidays);*/
		if(tblHolidays==null){
			return "节假日信息不存在！";
		}
		t10209BO.delete(id);
		log("删除节假日信息成功");
		return Constants.SUCCESS_CODE;
	}
	
	private String upload() {
		List<TblHolidays> tblholidaysList = new ArrayList<TblHolidays>();
		try {
			TblHolidays tblholidays = null;
			for(File file : files) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				while(reader.ready()){
					String tmp = reader.readLine();
					if (!StringUtil.isNull(tmp)) {
						String[] data = tmp.split(",");
//						System.out.println(tmp);
						tblholidays = new TblHolidays();	
						tblholidays.setHolidayStart(StringUtil.trim(data[0]));
						tblholidays.setName(StringUtil.trim(data[1]));
						tblholidays.setCreateOprId(operator.getOprId());	
						tblholidays.setCreateTime(CommonFunction.getCurrentDateTime());	
						tblholidays.setSaState(CommonFunction.ADD_TO_CHECK);
						tblholidays.setHolidayStartOld(StringUtil.trim(data[0]));
						tblholidays.setNameOld(StringUtil.trim(data[1]));
						tblholidaysList.add(tblholidays);
					}
				}
				reader.close();
			}
			int success = 0;
			int fail = 0;
			
			for(TblHolidays inf:tblholidaysList){
				String sql = "select count(1) from TBL_HOLIDAYS where HOLIDAY = '" + inf.getHolidayStart() + "' ";
				BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
				if (count.intValue() != 0) {
					fail++;
					continue;
				}		
				t10209BO.add(inf);
				success++;
			}
			return Constants.SUCCESS_CODE_CUSTOMIZE + 
				"成功录入条目：" + String.valueOf(success) + ",已存在的条目：" + String.valueOf(fail);
		} catch (Exception e) {
			e.printStackTrace();
			return "解析文件失败";
		}
	}

}
