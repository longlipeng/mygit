/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-27       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.settle.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseSupport;

/**
 * Title: 
 * 
 * File: T80102.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80101Action extends BaseSupport{

	private static final long serialVersionUID = 1L;

	private String startdate;
	private String enddate;
	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@SuppressWarnings("unchecked")
	public String init() throws ParseException{//对账清分
//		String sql="select distinct date_settlmt from tbl_algo_dtl where stlm_flg='0' and date_settlmt<'"
//				+this.getStartdate()+"'";
		/*String sql = "select to_char(to_date(max(date_settlmt),'yyyymmdd')+1,'yyyymmdd') from tbl_bat_main_ctl_dtl " +
				" where bat_id='B0007' and bat_state='2' ";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		if(list!=null && list.size()>0){
			StringBuffer dateString = new StringBuffer();
//			for(int i=0;i<list.size();i++){
				if( !this.getStartdate().equals(list.get(0)) ){
					Date date1 = sdf.parse(list.get(0));//表中查得的最大日期
					Date date2 = sdf.parse(this.getStartdate());//所选的对账日期
					
					if(date1.after(date2)){//表中查得的最大日期大于所选的对账日期时
						msg = this.getStartdate()+"已对账清分，请勿重复操作";
				        return returnService(msg);
					}
			        
					if(date1.before(date2)){//表中查得的最大日期小于所选的对账日期时，之间相差的日期用分号连接起来，供提示
						Date[] strArray = getDateArrays( date1, date2, Calendar.DAY_OF_YEAR);   
				        for(int j=0;j<strArray.length-1;j++){
				        	dateString.append(sdf.format(strArray[j])).append(";");
				        }
				        msg = this.getStartdate()+"之前有未对账清分:" + dateString.toString();
				        return returnService(msg);
					}
				}else{
					rspCode = Constants.SUCCESS_CODE;
				}
//			}
//			msg = this.getStartdate()+"之前有未清算:" + dateString.toString();
//			return returnService(msg);
		}else*/
			rspCode = Constants.SUCCESS_CODE;
		log(this.getStartdate()+"-"+this.getEnddate()+"对账开始");
		return returnService(rspCode);
	}
	
	@SuppressWarnings("unchecked")
	public String init2() throws Exception {//清算
		String sql = "select to_char(to_date(max(date_settlmt),'yyyymmdd')+1,'yyyymmdd') from tbl_bat_main_ctl_dtl" +
				" where bat_id='B0018' and bat_state='2' ";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		if(list!=null && list.size()>0){
			StringBuffer dateString = new StringBuffer();
			if( !this.getStartdate().split(",")[0].trim().equals(list.get(0)) ){
				Date date1 = sdf.parse(list.get(0));//表中查得的最大日期
				Date date2 = sdf.parse(this.getStartdate().split(",")[0].trim());//所选的对账日期
				
				if(date1.after(date2)){//表中查得的最大日期大于所选的对账日期时
					msg = this.getStartdate().split(",")[0].trim()+"已清算，请勿重复操作";
			        return returnService(msg);
				}
		        
				if(date1.before(date2)){//表中查得的最大日期小于所选的对账日期时，之间相差的日期用分号连接起来，供提示
					Date[] strArray = getDateArrays( date1, date2, Calendar.DAY_OF_YEAR);   
			        for(int j=0;j<strArray.length-1;j++){
			        	dateString.append(sdf.format(strArray[j])).append(";");
			        }
			        msg = this.getStartdate().split(",")[0].trim()+"之前有未清算:" + dateString.toString();
			        return returnService(msg);
				}
			}//20121022 添加新的判断条件：如果开始日期和结束日期不是同一天再继续判断，防止部分清算的任务无法继续进行并完成
			else if(!this.getStartdate().split(",")[0].trim().equals(this.getEnddate().split(",")[0].trim())){
				//20121018 modify,继续往下判断
				String result = init3();
				if(result.equals(Constants.SUCCESS_CODE)){
					rspCode = Constants.SUCCESS_CODE;
				}else{
					return result;
				}
			}else{
				rspCode = Constants.SUCCESS_CODE;
			}
		}//20121022 添加新的判断条件：如果开始日期和结束日期不是同一天再继续判断，防止部分清算的任务无法继续进行并完成
		else if(!this.getStartdate().split(",")[0].equals(this.getEnddate().split(",")[0])){
			//20121018 modify,继续往下判断
			String result = init3();
			if(result.equals(Constants.SUCCESS_CODE)){
				rspCode = Constants.SUCCESS_CODE;
			}else{
				return result;
			}
		}else{
			rspCode = Constants.SUCCESS_CODE;
		}
		log(this.getStartdate().split(",")[0].trim() + "-"+this.getEnddate().split(",")[0].trim() + "清算开始");
		return returnService(rspCode);
	}
	
	@SuppressWarnings("unchecked")
	public String init3() throws Exception {//20121018 add
		String result = "";
		String sql = "select bat_state from tbl_bat_main_ctl_dtl where bat_id='B0007' and date_settlmt='"
			+ this.getEnddate().split(",")[0].trim() + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		
		if(list!=null && list.size()>0){
			if( "2".equals(list.get(0).toString()) ){//查询结果是：执行成功
//				String result2 = init4();
//				if(result2.equals(Constants.SUCCESS_CODE)){
					result = Constants.SUCCESS_CODE;
//				}else{
//					return result2;
				}
//			}else 
				if("3".equals(list.get(0).toString())){//查询结果是：执行失败
				msg = this.getEnddate().split(",")[0].trim() + "前有未对账清分的数据";
		        return returnService(msg);
			}
		}else{//查询结果为空
			msg = this.getEnddate().split(",")[0].trim() + "前有未对账清分的数据";
	        return returnService(msg);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
//	public String init4() throws Exception {//20121018 add
//		String result = "";
//		String sql = "select bat_state from tbl_bat_main_ctl_dtl where bat_id='B0008' and date_settlmt='"
//			+ this.getStartdate().split(",")[0].trim() + "'";
//		List<String> list = commQueryDAO.findBySQLQuery(sql);
//		
//		if(list!=null && list.size()>0){
//			if( "2".equals(list.get(0).toString()) ){//查询结果是：执行成功
//				msg = this.getStartdate().split(",")[0].trim() + "前已清算或部分清算";
//		        return returnService(msg);
//			}else if("3".equals(list.get(0).toString())){//查询结果是：执行失败
//				result = Constants.SUCCESS_CODE;
//			}
//		}else{//查询结果为空
//			result = Constants.SUCCESS_CODE;
//		}
//		return result;
//	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
	/**  
     * 获得日期字符串数组  
     * @param calendarType 日期跨度的类型，  
     * */  
    public static Date[] getDateArrays(Date start,Date end ,int calendarType){ 
        ArrayList<Date> ret = new ArrayList<Date>();   
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(start);   
        Date tmpDate = calendar.getTime();   
        long endTime = end.getTime();   
        while(tmpDate.before(end)||tmpDate.getTime() == endTime){   
            ret.add(calendar.getTime());   
            calendar.add(calendarType, 1);   
            tmpDate = calendar.getTime();   
        }          
        Date[] dates = new Date[ret.size()];   
        return ret.toArray(dates);      
    }   

}
