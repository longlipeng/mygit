
package com.huateng.struts.pos.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T30109Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T30109Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
	 	String[] titlelist = InformationUtil.createTitles("V_", 19);   //+2
		title = InformationUtil.createTitles("V_", 31);
		
		Object[][] datalist = reportCreator.process(genSql(), titlelist);
		data = new Object[datalist.length][31];	
		

		if(datalist.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
	
		
		for (int i = 0; i < datalist.length; i++) {
			data[i][0]=datalist[i][0];
			data[i][1]=datalist[i][1];
			data[i][2]=datalist[i][2];
			data[i][3]=datalist[i][3];
			
			data[i][4]=datalist[i][4];	//代理商信息
			data[i][5]=datalist[i][5];	//客户经理信息
					        //商户简称			
			data[i][7]=datalist[i][6];
			data[i][8]=datalist[i][7];
			data[i][9]=datalist[i][8];
			data[i][10]=datalist[i][9];
			data[i][11]=datalist[i][10];
			data[i][12]=datalist[i][11];
			data[i][13]=datalist[i][12];
			data[i][14]=datalist[i][13];
			data[i][15]=datalist[i][14];
			data[i][16]=datalist[i][15];
			data[i][17]=datalist[i][16];
			data[i][18]=datalist[i][17];
//			data[i][19]=datalist[i][18];
//			data[i][20]=datalist[i][19];
			
			String para = (String)datalist[i][18];     //字符串截取
			
			
			//终端所属省
			String sql4 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,3,2)='00'";
			List<Object[]> province =CommonFunction.getCommQueryDAO().findBySQLQuery(sql4);
			
			//终端所属市
			String sql5 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  ((substr(CITY_CODE,3,1)<>'0' and substr(CITY_CODE,4,1)='0') or (CITY_CODE IN (1000,1100,2900,6900)))";
			List<Object[]> city =CommonFunction.getCommQueryDAO().findBySQLQuery(sql5);
			
			//终端所属县
			String sql6 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,4,1)<>'0' ";
			List<Object[]> area =CommonFunction.getCommQueryDAO().findBySQLQuery(sql6);
			
			//终端所属省
			if(data[i][15] != null && !"".equals(data[i][15].toString().trim())){
				if(province !=null && province.size() >0){
					for(int j = 0; j < province.size(); j++){
						if(data[i][15].toString().trim().equals(province.get(j)[0].toString().trim())){
							data[i][15] = province.get(j)[1].toString();
						}
					}
				}else{
					data[i][15] = data[i][15].toString().trim();
				}
			}else{
				data[i][15] = "";
			}
			
			//终端所属市
			if(data[i][16] != null && !"".equals(data[i][18].toString().trim())){
				if(city !=null && city.size() >0){
					for(int j = 0; j < city.size(); j++){
						if(data[i][16].toString().trim().equals(city.get(j)[0].toString().trim())){
							data[i][16] = city.get(j)[1].toString();
						}
					}
				}else{
					data[i][16] = data[i][16].toString().trim();
				}
			}else{
				data[i][16] = "";
			}
			
			//终端所属县
			if(data[i][17] != null && !"".equals(data[i][17].toString().trim())){
				if(area !=null && area.size() >0){
					for(int j = 0; j < area.size(); j++){
						if(data[i][17].toString().trim().equals(area.get(j)[0].toString().trim())){
							data[i][17] = area.get(j)[1].toString();
						}
					}
				}else{
					data[i][17] = data[i][17].toString().trim();
				}
			}else{
				data[i][17] = "";
			}
			
			
			
			//商户简称 90-130
			if(para!=null && !para.equals("") && para.length()>=92){
		//		System.out.println(para.indexOf("dyf"));
			//	System.out.println(para.indexOf("23"));
				String chName = para.substring(90,130).trim();
			//	System.out.println(chName);
				
				data[i][4] = chName;
			}else{
				data[i][4] = "";
			}
			
			//打印联数152-153 
			if(para!=null && !para.equals("") && para.length()>=153){
				String txn27 = para.substring(152,153);
				data[i][19] = txn27;
			}else{
				data[i][19] = "";
			}
			//是否显示LOGO 158-159
			if(para!=null && !para.equals("") && para.length()>=159){
				String txn29 = para.substring(158,159);
				if(txn29.equals("1")){
					data[i][20] = "是";
				}else if(txn29.equals("0")){
					data[i][20] = "否";
				}else{
					data[i][20] = "";
				}
			}else{
				data[i][20] = "";
			}
			//是否屏蔽卡号 155-156
			if(para!=null && !para.equals("") && para.length()>=156){
				String txn28 = para.substring(155,156);
				if(txn28.equals("1")){
					data[i][29] = "是";
				}else if(txn28.equals("0")){
					data[i][29] = "否";
				}else{
					data[i][29] = "";
				}
			}else{
				data[i][29] = "";
			}
			//将终端号和142-144
			StringBuffer sb = new StringBuffer();
			if(para!=null && !para.equals("") && para.length()>=144){
				for (char c : para.substring(142, 144).toCharArray()){
					//排除空格
					if(c != (char)32){
						sb.append(CommonFunction.fillString(Integer.toBinaryString(Integer.parseInt(String.valueOf(c),16)), '0', 4, false));
					}else{
						sb.append("    ");
					}
				}
			}else{
				sb.append("        ");
			}
			
			String binary = sb.toString();
			
			if(binary !=null && !binary.equals("") && binary.length()>=8){
				if(para!=null && !para.equals("") && para.length()>=144){
					char[] cs = binary.toCharArray();
					for(int k = 0 ; k < cs.length ; k++ ){
						if(String.valueOf(cs[k]).equals("1")){
							data[i][21+k] = "是";
						}else if(String.valueOf(cs[k]).equals("0")){
							data[i][21+k] = "否";
						}else{
							data[i][21+k] = "";
						}
					}
				}else{
					for(int l = 0 ; l < binary.length() ; l++ ){
						data[i][21+l] = "";
					}
				}
			}else{
				for(int h = 0 ; h < binary.length() ; h++ ){
					data[i][21+h] = "";
				}
			}
		}
		reportModel.setData(data);
		reportModel.setTitle(title);
//		parameters.put("betdate", startDate + " - " + endDate);
		reportCreator.initReportData(getJasperInputSteam("T30109.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		if(Constants.REPORT_EXCELMODE.equals(reportType)){
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN30109RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		}else if(Constants.REPORT_PDFMODE.equals(reportType)){
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN30109RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		}
		outputStream = new FileOutputStream(fileName);
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN30109RN"));
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		//ADD BY张骏恺20140820 end
		StringBuffer whereSql = new StringBuffer(" where t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		// .append("  and  t1.TERM_STA<>'8' ");
		if (startTime != null && !startTime.trim().equals("")) {
			if (startTime.length() == 8)
				whereSql.append(" AND t1.REC_CRT_TS>='")
						.append(CommonFunction.fillString(startTime, '0', 14,
								true)).append("'");
		}
		if (endTime != null && !endTime.trim().equals("")) {
			if (endTime.length() == 8)
				whereSql.append(" AND t1.REC_CRT_TS<='").append(endTime)
						.append("235959'");
		}
		if (province != null && !province.trim().equals("")) {
			whereSql.append(" AND t1.PROVINCE='").append(province).append("'");
		}
		if (city != null && !city.trim().equals("")) {
			whereSql.append(" AND t1.CITY='").append(city).append("'");
		}
		if (area != null && !area.trim().equals("")) {
			whereSql.append(" AND t1.AREA='").append(area).append("'");
		}
		if (cityCode != null && !cityCode.trim().equals("")) {
			whereSql.append(" AND t1.CITYCODE='").append(cityCode).append("'");
		}
		//ADD BY张骏恺20140820 begin
		if (termTpQ != null && !termTpQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_TP='").append(termTpQ)
					.append("'");
		}
		if (termFactoryQ != null && !termFactoryQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_FACTORY ='").append(termFactoryQ)
			.append("'");
		}
		if (connectModeQ != null && !connectModeQ.trim().equals("")) {
			whereSql.append(" AND t1.CONNECT_MODE='").append(connectModeQ)
			.append("'");
		}
		if (propTpQ != null && !propTpQ.trim().equals("")) {
			whereSql.append(" AND t1.PROP_TP='").append(propTpQ)
			.append("'");
		}
		if (CompQ != null && !CompQ.trim().equals("")) {
			whereSql.append(" AND t1.term_id in (" +
					"select term_id from tbl_term_inf_tmp " +
					"where MCHT_CD IN (" +
					"select MCHT_NO " +
					"FROM tbl_mcht_base_inf where ACQ_inst_id = '").append(CompQ).append("'))");
		}
		if (tradeTp != null && !tradeTp.trim().equals("")) {
			//获取所有term和term_para
			String str = "select term_id , term_para from TBL_TERM_INF_TMP";
			List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(str);
			List<String[]> tp = new ArrayList<String[]>();
			Iterator<Object[]> it = list.iterator();
			//遍历集合
			while(it.hasNext()){
				Object[] obj = it.next();
				String[] st = new String[2];
				//将终端号和142-144
				st[0] = (String)obj[0];
				StringBuffer result = new StringBuffer();
				if((String)obj[1]!=null && !((String)obj[1]).equals("")){
					for (char c : ((String)obj[1]).substring(142, 144).toCharArray()){
						//排除空格
						if(c != (char)32){
							result.append(CommonFunction.fillString(Integer.toBinaryString(Integer.parseInt(String.valueOf(c),16)), '0', 4, false));
						}else{
							result.append("0000");
						}
					}
				}else{
					result.append("00000000");
				}
				st[1] = result.toString();
				tp.add(st);
			}
			//拼接终端号
			StringBuffer sb = new StringBuffer();
			if(tradeTp.trim().equals("0")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(0, 1).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("1")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(1, 2).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("2")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(2, 3).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("3")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(3, 4).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("4")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(4, 5).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("5")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(5, 6).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if(tradeTp.trim().equals("6")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(6, 7).equals("1")){
						sb.append("'").append(tp.get(m)[0]).append("',");
					}
				}
			}
			if(tradeTp.trim().equals("7")){
				for(int m = 0 ; m < tp.size() ; m ++){
					if(tp.get(m)[1].substring(7, 8).equals("1")){
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			//拼接查询语句
			String query = sb.toString();
			if(query !=null ){
				query = query.substring(0,query.length()-1);
				whereSql.append("and t1.term_id in (").append(query).append(")");
			}else{
				whereSql.append("and t1.term_id in (null)");
			}
			
		}
		if (mchnNo != null && !mchnNo.trim().equals("")) {
			whereSql.append(" AND MCHT_CD='").append(mchnNo)
			.append("'");
		}
		if (termMccQ != null && !termMccQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_MCC='").append(termMccQ)
			.append("'");
		}
		if (UpdstartTime != null && !UpdstartTime.trim().equals("")) {
			if (UpdstartTime.length() == 8)
				whereSql.append(" AND t1.REC_DEL_TS>='")
						.append(CommonFunction.fillString(UpdstartTime, '0', 14,
								true)).append("'");
		}
		if (UpdendTime != null && !UpdendTime.trim().equals("")) {
			if (UpdendTime.length() == 8)
				whereSql.append(" AND t1.REC_DEL_TS<='").append(UpdendTime)
						.append("235959'");
		}
		//ADD BY张骏恺20140820 end
		if (termId != null && !termId.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termId, ' ', 12, true))
					.append("'");
		}
		//
		if (chk_sta != null && !chk_sta.trim().equals("")) {
			whereSql.append(" AND t1.chk_sta='")
			.append(chk_sta).append("'");
		}
		
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.TERM_STA='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.TERM_STA in ('").append(termSta)
						.append("')");
			}
		}
		String sql = "SELECT t1.TERM_ID ," +  //1 终端号
				"t1.MCHT_CD||'-'||t2.MCHT_NM," +	//2 商户号
				"decode(t1.TERM_STA,'0','新增未审核','1','启用','2','修改未审核'," +
						"'3','停用未审核','4','停用','5','恢复未审核','6','注销未审核'," +
						"'7','注销','8','新增审核拒绝','9','修改审核拒绝','A','停用审核拒绝','B'," +
						"'恢复审核拒绝','C','注销审核拒绝',t1.TERM_STA)," +	//3 终端状态
			//	"tp.descr," +		//终端类型
			    "tp.VALUE," +        //4 终端类型
			
			                         //商户简称
			    /*"agent.AGENT_NO||'-'||agent.AGENT_NM, " +     //5代理商信息
			    "t2.oper_nm," +                     //6客户经理信息
*/			                         
				"t1.REC_CRT_TS," +  // 7 添加日期
				"t1.REC_DEL_TS," + 	//8 修改日期
	 //			"t1.TERM_FACTORY||'-'|| cst.value," +	//终端厂商
	            "cst.VALUE," +     //9 终端厂商
	 //			"decode(t1.CONNECT_MODE,'1','电话线连接','2','移动','3','网络',t1.Connect_mode)," +	//终端通讯方式（连接类型）
	            "cst2.VALUE," +     //10 终端型号
	   //        "t1.PROP_TP," + 	//产权属性
	            "cst3.VALUE," + 	//11 产权属性
				"t1.PROP_INS_NM," + //12 产权所属机构名称
				"t1.TERM_ADDR," + //13 终端安装地址
				"t1.CONT_TEL," + //14 联系电话
	//			"t1.MISC_2," + //安装维护员
	            "t1.TERM_PARA_2," +        //135联系人
				"ssss.ACQ_inst_id||'-'||brh.branch_name," + //16 终端所属分公司
		//		"t1.PROVINCE||'-'||s.VALUE," +	//17 省
		       "trim(t1.PROVINCE)," +	//17 省
		//		"t1.CITY||'-'||ss.VALUE," +		//18 市
		        "trim(t1.CITY)," +		//18 市
		//		"t1.AREA||'-'||sss.VALUE," +	//19区
		        "trim(t1.AREA)," +	//19区
				//"t1.CITYCODE||'-'||c.CITY_DES," +
				"t1.TERM_MCC||'-'||mchtp.descr," +	//20 MCC

				"t1.Term_para, "+  //21字符串截取
				
				" DECODE(t1.chk_sta,'0','未下发','1','未下发','2','已下发',t1.chk_sta) "
				
				+ "FROM TBL_TERM_INF_TMP t1 left join TBL_MCHT_BASE_INF t2 on t1.MCHT_CD = t2.MCHT_NO "
				+ "left join TBL_CITY_CODE c on t1.CITYCODE=c.CITY_CODE "
				+ "left join CST_SYS_PARAM s on t1.PROVINCE=s.KEY  and s.OWNER='PROVINCE' "
				+ "left join CST_SYS_PARAM ss on t1.CITY=ss.KEY and ss.OWNER='CITY' "
				+ "left join CST_SYS_PARAM sss on t1.AREA=sss.KEY and sss.OWNER='AREA' "
				+ "left join tbl_mcht_base_inf ssss on t1.MCHT_CD=ssss.MCHT_NO "
			//	+ "left join tbl_term_tp tp on trim(t1.term_tp) = trim(tp.term_tp) "
				+ "left join cst_sys_param tp on trim(t1.term_tp) = trim(tp.key) and tp.OWNER = 'TERM_TYPE'"
				+ "left join tbl_branch_manage brh on ssss.ACQ_inst_id = brh.branch_no "
			//	+ "left join cst_sys_param cst on t1.term_factory = cst.key and cst.owner = 'FACTORY' "
				+ "left join cst_sys_param cst on trim(t1.TERM_FACTORY)=trim(cst.key) and cst.owner ='FACTORY' "  //终端厂商
				+ "left join cst_sys_param cst2 on trim(t1.TERM_MACH_TP)=trim(cst2.KEY) and cst2.owner ='TERMMACHTP' "   //8 终端型号
				+ "left join cst_sys_param cst3 on trim(t1.PROP_TP)=trim(cst3.key) and cst3.owner ='PROP_TP'"//9 产权所属
				+ "left join tbl_inf_mchnt_tp mchtp on t1.term_mcc = mchtp.mchnt_tp "
				+ "left join tbl_agent_info  agent on t2.agent_no = agent.agent_no "
				+ whereSql.toString() + " ORDER BY t1.REC_CRT_TS desc ";
		return sql;
	}
	


	private T3010BO t3010BO;

	public T3010BO getT3010BO() {
		return t3010BO;
	}

	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}
	
	private String termId;
	private String termSta;
	private String startTime;
	private String endTime;
	private String province;
	private String city;
	private String area;
	private String cityCode;
	private String termTpQ;
	private String termFactoryQ;
	private String connectModeQ;
	private String propTpQ;
	private String CompQ;
	private String tradeTp;
	private String termMccQ;
	private String UpdstartTime;
	private String UpdendTime;
	private String mchnNo;
	private String txnId;
	private String subTxnId;
	private String chk_sta;

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermSta() {
		return termSta;
	}

	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTermTpQ() {
		return termTpQ;
	}

	public void setTermTpQ(String termTpQ) {
		this.termTpQ = termTpQ;
	}

	public String getTermFactoryQ() {
		return termFactoryQ;
	}

	public void setTermFactoryQ(String termFactoryQ) {
		this.termFactoryQ = termFactoryQ;
	}

	public String getConnectModeQ() {
		return connectModeQ;
	}

	public void setConnectModeQ(String connectModeQ) {
		this.connectModeQ = connectModeQ;
	}

	public String getPropTpQ() {
		return propTpQ;
	}

	public void setPropTpQ(String propTpQ) {
		this.propTpQ = propTpQ;
	}

	public String getCompQ() {
		return CompQ;
	}

	public void setCompQ(String compQ) {
		CompQ = compQ;
	}

	public String getTradeTp() {
		return tradeTp;
	}

	public void setTradeTp(String tradeTp) {
		this.tradeTp = tradeTp;
	}

	public String getTermMccQ() {
		return termMccQ;
	}

	public void setTermMccQ(String termMccQ) {
		this.termMccQ = termMccQ;
	}

	public String getUpdstartTime() {
		return UpdstartTime;
	}

	public void setUpdstartTime(String updstartTime) {
		UpdstartTime = updstartTime;
	}

	public String getUpdendTime() {
		return UpdendTime;
	}

	public void setUpdendTime(String updendTime) {
		UpdendTime = updendTime;
	}

	public String getMchnNo() {
		return mchnNo;
	}

	public void setMchnNo(String mchnNo) {
		this.mchnNo = mchnNo;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getSubTxnId() {
		return subTxnId;
	}

	public void setSubTxnId(String subTxnId) {
		this.subTxnId = subTxnId;
	}

	public String getChk_sta() {
		return chk_sta;
	}

	public void setChk_sta(String chk_sta) {
		this.chk_sta = chk_sta;
	}
	
}
