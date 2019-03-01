package com.huateng.struts.risk.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.risk.T40202BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.ExcelReader;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 商户黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40202Action extends BaseAction {
	
	private static Logger log = Logger.getLogger(T40202Action.class);

	
	T40202BO t40202BO = (T40202BO) ContextUtil.getBean("T40202BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
//		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户黑名单信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户黑名单信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户黑名单信息");
			rspCode = delete();
		} else if("uploadFile".equals(method)) {
			log("批量上传商户黑名单信息");
			rspCode = uploadFile();
		}else if("addNew".equals(method)) {
			rspCode = addNew();
		}else if("deleteNew".equals(method)) {
			rspCode = deleteNew();
		}else if("acceptNew".equals(method)) {
			rspCode = acceptNew();
		}else if("refuseNew".equals(method)) {
			rspCode = refuseNew();
		}else if("deleteRegion".equals(method)) {
			rspCode = deleteRegion();
		}else if("addRegion".equals(method)) {
			rspCode = addRegion();
		}else if("batchCardImportIssue".equals(method)){
			rspCode = batchCardImportIssue();
		}else if("batchImportFile".equals(method)){
			rspCode = batchImportFile();//导入信息到黑名单表数据
		}else if("cleanUpBlackList".equals(method)){
			rspCode = cleanUpBlackList();//清空2个黑名单表数据
		}else if("downloadBlackListFile".equals(method)){
			rspCode = downloadBlackListFile();//导出黑名单表数据为excel文件
		}
		return rspCode;
	}
	
	private String addRegion() {
		/*System.out.println("rgtp:" + rgtp);
		System.out.println("rgcd:" + rgcd);
		System.out.println("rgnm:" + rgnm);
		System.out.println("status:" + status);*/
		
		String chNameSql = "select count(*) from t_lst_region where region_name = '"+rgnm.trim() +"'";
		String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(chNameSql);
		if(!("0").equals(result)){
			return "该商户已在新增列表中";
		}
		String maxSql = "select max(to_number(id)) from t_lst_region ";
		String maxId= CommonFunction.getCommQueryDAO().findCountBySQLQuery(maxSql);
		int i = Integer.parseInt(maxId)+1;
		String insrtSql = "insert into t_lst_region(id,lskd,region_type,region_code,region_name,status,create_time,creator,update_time,updator) values("
		+ "'"+i+"', '" + lstp +"', '"+rgtp+"','"+rgcd+"', '"+rgnm+"', '"+status +"', '"+CommonFunction.getCurrentDateTime()+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"', '"+operator.getOprId()+"') ";
		try {
			log("addRegion SQL:"+insrtSql);
			CommonFunction.getCommQueryDAO().excute(insrtSql);
		} catch (Exception e) {
			e.printStackTrace();
			log("addRegion请求失败T40202Action:"+e.getMessage());
			return "添加地区黑名单失败！";
		}
		return Constants.SUCCESS_CODE;
	}

	private String deleteRegion() {
		System.out.println("id"+id);
		log("deleteRegion删除 ID："+id);
		String deSql = "delete  from t_lst_region where 1=1 and id = '" + id + "'";
		log("deleteRegion删除 SQL："+deSql);
		try {
			CommonFunction.getCommQueryDAO().excute(deSql);
		} catch (Exception e) {
			e.printStackTrace();
			log("删除请求失败deleteRegion:"+e.getMessage());
			return "删除黑名单失败";
		}
		return Constants.SUCCESS_CODE;
	}

	private String refuseNew() {
		log("T40202Action审核拒绝："+id);
		if (creator.equals((operator.getOprId()))) {
			return "操作人与审核人不能是同一人!";
		}
		try {
			String upSql = "";
			String upSql1 = "";
			if (ckstatus.equals("0")) {
				upSql = "update t_lst_entity_tmp set  ckstatus = '5' where id = '"+id+"'";
			}else if(ckstatus.equals("7")) {
				upSql = "update t_lst_entity_tmp set  ckstatus = '3' where id = '"+id+"'";
			}else if(ckstatus.equals("6")) {
				upSql = "update t_lst_entity_tmp set  ckstatus = '4' where id = '"+id+"'";
				upSql1 = "update t_lst_entity set  ckstatus = '4' where id = '"+id+"'";
			}
			CommonFunction.getCommQueryDAO().excute(upSql);
			CommonFunction.getCommQueryDAO().excute(upSql1);
			
		} catch (Exception e) {
			e.printStackTrace();
			log("审核拒绝请求失败T40202Action:"+e.getMessage());
			return "审核拒绝请求失败";
		}
		return Constants.SUCCESS_CODE;
	}

	private String acceptNew() {
		log("T40202Action审核通过："+id);
		try {
			if (creator.equals((operator.getOprId()))) {
				return "操作人与审核人不能是同一人!";
			}
			try {
				String upsql = "update t_lst_entity_tmp set ckstatus ='2' where id = '"+id+"' ";
				CommonFunction.getCommQueryDAO().excute(upsql);
				String delSql = "delete from t_lst_entity where id = '"+id+"'";
				CommonFunction.getCommQueryDAO().excute(delSql);
				try {
					String intSql = "insert into t_lst_entity select * from t_lst_entity_tmp where id = '"+id+"'";
					CommonFunction.getCommQueryDAO().excute(intSql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log("审核通过请求失败T40202Action:"+e.getMessage());
			return "审核通过请求失败";
		}
		return Constants.SUCCESS_CODE;
	}

	private String deleteNew() {
		log("T40202Action删除 ID："+id);
		String deSql = "update t_lst_entity_tmp set ckstatus = '8', create_time = '"+ CommonFunction.getCurrentDateTime() +"', creator = '"+operator.getOprId()+"' where 1=1 and id = '" + id + "'";
		log("T40202Action删除 SQL："+deSql);
		try {
			CommonFunction.getCommQueryDAO().excute(deSql);
		} catch (Exception e) {
			e.printStackTrace();
			log("删除请求失败T40202Action:"+e.getMessage());
			return "删除黑名单失败";
		}
		return Constants.SUCCESS_CODE;
	}

	private String addNew() {
		String chNameSql = "select count(*) from t_lst_entity where ctnm = '"+ctnm.trim() +"'";
		String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(chNameSql);
		if(!("0").equals(result)){
			return "该商户已在新增列表中";
		}
		String maxSql = "select max(to_number(id)) from t_lst_entity_tmp";
		String maxId= CommonFunction.getCommQueryDAO().findCountBySQLQuery(maxSql);
		int i = Integer.parseInt(maxId)+1;
		if (cttp.equals(null)) {
			cttp="1";
		}
		if (lstp.equals(null)) {
			lstp="0";
		}
		String insrtSql = "insert into t_lst_entity_tmp(id,cttp,ctcr,ctnm,ciid,cleg,clid,care,lstp,create_time,creator,update_time,updator,ckstatus,citp,citp_nt,ceml,cbir,crag,cltp_nt,cltp,cpcd,cadd,cpho,cmob,lgtp) values("
				+ "'"+i+"', '"+cttp+"','"+ctcr+"', '"+ctnm+"', '"+ciid+"', '"+cleg+"', '"+clid+"', '"+care+"','"+lstp+"', '"+CommonFunction.getCurrentDateTime()+"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"', '"+operator.getOprId()+"', '0' , '"+citp+"', '"+citp_nt+"', '"+ceml+"', '"+cbir+"', '"+crag+"', '"+cltp_nt+"', '"+cltp+"','"+cpcd+"','"+cadd+"','"+cpho+"','"+cmob+"','"+lgtp+"') ";
		try {
			log("T40202Action新增SQL："+insrtSql);
			CommonFunction.getCommQueryDAO().excute(insrtSql);
		} catch (Exception e) {
			e.printStackTrace();
			log("新增失败T40202Action:"+e.getMessage());
			return "新增黑名单失败";
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 添加商户黑名单信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		/*if(t40202BO.get(saMerNo) != null ) {
			TblCtlMchtInf tblCtlMchtInf = t40202BO.get(saMerNo);
			if(tblCtlMchtInf.getSaState().equals(DELETE)){
				tblCtlMchtInf.setSaState(ADD_TO_CHECK);
				tblCtlMchtInf.setSaInitOprId(operator.getOprId());//操作人
				tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());//操作时间
				
				rspCode = t40202BO.update(tblCtlMchtInf);
				return rspCode;
			}else{
				return "该商户已在黑名单中";
			}
		}*/
		String chNameSql = "select count(*) from tbl_ctl_mcht_inf where SA_MER_CH_NAME = '"+saMerChName.trim()+"' and SA_STATE <> '1'";
		String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(chNameSql);
		if(!("0").equals(result)){
			return "该商户已在新增列表中";
		}
		
		TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
	//	BeanUtils.copyProperties(tblCtlMchtInf, this);
		tblCtlMchtInf.setDatePk(CommonFunction.getCurrentDateTime());
		tblCtlMchtInf.setSaMerChName(saMerChName);
		tblCtlMchtInf.setLicenceNo(licenceNo);
		tblCtlMchtInf.setIdentityNo(identityNo);
		tblCtlMchtInf.setManagerTel(managerTel);
		tblCtlMchtInf.setAppRemark(appRemark.trim());
		tblCtlMchtInf.setSaState(ADD_TO_CHECK);
		tblCtlMchtInf.setAddType("0");
		tblCtlMchtInf.setBankLicenceNo(bankLicenceNo);
		tblCtlMchtInf.setSaArea(saArea);
		tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
		tblCtlMchtInf.setSaInitOprId(operator.getOprId());
		tblCtlMchtInf.setSaModiOprId("");
		
		
		
		rspCode = t40202BO.add(tblCtlMchtInf);
		return rspCode;
	}
	
	/**
	 * 更新商户黑名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getMchtInfList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
			BeanUtils.setObjectWithPropertiesValue(tblCtlMchtInf,jsonBean,true);
			tblCtlMchtInf.setSaInitZoneNo(operator.getOprBrhId());
			tblCtlMchtInf.setSaInitOprId(operator.getOprId());
			tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
			if(StringUtil.isNull(tblCtlMchtInf.getSaMerEnName())) 
				tblCtlMchtInf.setSaMerEnName(" ");
			t40202BO.update(tblCtlMchtInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户黑名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		//t40202BO.delete(saMerNo);
		if(t40202BO.get(datePk).getSaState().equals("1")) {
			return "该商户黑名单已是删除状态，请勿重复删除";
		}
		TblCtlMchtInf tblCtlMchtInf = t40202BO.get(datePk);
		if(ADD_TO_CHECK.equals(tblCtlMchtInf.getSaState())){
			t40202BO.delete(datePk);
		}else{
			tblCtlMchtInf.setSaState(DELETE_TO_CHECK);
			tblCtlMchtInf.setSaInitOprId(operator.getOprId());
			tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
			tblCtlMchtInf.setAppRemark(appRemark.trim());
			tblCtlMchtInf.setSaModiOprId("");
			t40202BO.update(tblCtlMchtInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	private List<File> files;

	private List<String> filesFileName;
	
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
	 * 批量上传文件 每次只有一个文件，调用多次
	 * @return
	 * @throws Exception
	 */
	private String batchImportFile() throws Exception{	
		
		String sql = "select count(1) from TBL_BLACKLIST_OBSERVE";
		String sql1 = "select count(1) from TBL_BLACKLIST_REGION";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		String count1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		//两个表数据不为空时清空所有数据
		if(!count.equals("0") && !count1.equals("0")){
			//导表之前清空观察表地区表所有数据
			truncate1();
			truncate2();
		}
		
		batchCardImportIssue2(files.get(0), filesFileName.get(0));
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 下载反洗钱黑名单数据
	 * 生成文件在临时目录，返回文件名称
	 * 文件下载通过其他方法下载
	 * @return
	 */
	private String downloadBlackListFile() throws Exception{
		//获取文件保存路径和文件名，生成完整文件路径
		String path = SysParamUtil.getParam(SysParamConstants.BLACKLIST_RESULT_FILE_PATH);
		String fileName = SysParamUtil.getParam(SysParamConstants.BLACKLIST_RESULT_FILE_NAME);
		String dateStr = DateFormatUtils.format(new Date(), "yyyyMMdd");
		
		String fullPath = path + fileName  + "_"+dateStr + ".xls";
		log.info("反洗钱黑名单结果文件保存路径: " + fullPath);
		
		//获取数据库数据(地区黑名单和个人黑名单)
		String sql = "select BLACK_REGION_TYPE, BLACK_REGION_NO, BLACK_REGION_NAME, BLACK_REGION_HOME, UPDATETIME from TBL_BLACKLIST_REGION";
		List<Object[]> regionList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		/*for(Object[] arr : regionList){
			for(Object str : arr){
				System.out.print(str + " ");
			}
			System.out.printf(" ");
		}*/
		
		sql = "select BLACK_OBSERVE_NAME, BLACK_OBSERVE_TYPE, BLACK_OBSERVE_HOME, BLACK_OBSERVE_ENTITY, BLACK_OBSERVE_CON, BLACK_OBSERVE_UPDATETIME from TBL_BLACKLIST_OBSERVE";
		List<Object[]> observeList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		
		//生成EXCEL文件
		HSSFWorkbook wkb = new HSSFWorkbook();
		//建立新的sheet对象（excel的表单）  
		HSSFSheet regoinSheet = wkb.createSheet("地区");
		
		//创建标题行
		int rowIndex = 0;
		HSSFRow rowTitle = regoinSheet.createRow(rowIndex++);
		Object[] title = {"地区类型", "地区代码", "地区名称", "名单来源", "更新时间"};
		writeExcelRow(title, rowTitle);
		for(Object[] arr : regionList){
			writeExcelRow(arr, regoinSheet.createRow(rowIndex++));
		}
		
		//建立新的sheet对象（excel的表单）  
		HSSFSheet observeSheet = wkb.createSheet("个人");
		//创建标题行
		rowIndex = 0;
		HSSFRow rowTitle2 = observeSheet.createRow(rowIndex++);
		Object[] title2 = {"名称", "名单类别", "名单来源", "实体类别", "名单种类", "更新时间"};
		writeExcelRow(title2, rowTitle2);
		for(Object[] arr : observeList){
			writeExcelRow(arr, observeSheet.createRow(rowIndex++));
		}
		
		File file = new File(fullPath);
		file.createNewFile();
		OutputStream os = new FileOutputStream(file);
		wkb.write(os);
		os.close();
		
		//API参考 http://hutool.mydoc.io/?t=255688
		/*ExcelWriter writer = ExcelUtil.getWriter(fullPath, "黑名单地区");
		writer.write(regionList);
		writer.close();
		
		writer = ExcelUtil.getWriter(fullPath, "黑名单个人");
		writer.write(observeList);
		writer.close();
		*/
		//返回文件名
		writeUsefullMsg(fullPath);
		
		return null;
	}
	
	private void writeExcelRow(Object[] data, HSSFRow row){
		writeExcelRow(Arrays.asList(data), row);
	}
	
	private void writeExcelRow(List<Object> data, HSSFRow row){
		int index = 0;
		for(Object val : data){
			row.createCell(index++).setCellValue(val == null ? "" : val.toString());
		}
	}
	
	
	/**
	 * 向客户端输出有效信息
	 * @param response
	 * @param msg
	 * @throws IOException
	 * 2010-8-30下午07:45:40
	 */
	protected void writeUsefullMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 *先上传文件到服务器，再从服务器下载 导入数据到数据库 
	 * @throws Exception 
	 */
	private String batchCardImportIssue() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");   
		System.out.println(df.format(System.currentTimeMillis()));   
		String date= df.format(System.currentTimeMillis());
		System.out.println("上传文件的日期：" + date );
		String fileDate = date;//存放上传文件的的日期文件夹
		String basePath = SysParamUtil.getParam(SysParamConstants.AML_EXCEL);//存放上传文件的总目录
		
		basePath += fileDate;//存放上传文件的位置
		System.out.println("存放上传文件的位置：" + basePath);
		File file = new File(basePath);
		System.out.println("====================================="+"循环导入数据"+"=====================================");
		String namePath = basePath + "/";
//		File namePathFile = new File(namePath);
		if (file.exists()) {//判断目录是否存在
			File[] files = file.listFiles();
			if (files.length == 0) {
				log("目录中没有文件！");
				return "目录中没有文件！";
			}else{
				//deleteDir(namePathFile);
			}
			
			int num = 0;
			for (File file2 : files) {
				String name = file2.getName();//获取到的文件的文件名
//				String absolutePath = file2.getAbsolutePath();
				if(num == 0) {
					truncate1();
					truncate2();
				}
				num++;
				try {
					String pp = batchCardImportIssue2(file2, null);
					if(msg!=null) {
						return msg;
					}
				} catch (Exception e) {
					log(name+"文件数据导入失败！");
					return name+"文件数据导入失败！";
				}
			}
		} else {
			log("目录不存在！");
			return "目录不存在！";
		}
		return Constants.SUCCESS_CODE;
	}

	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 *批量导入反洗钱数据 
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	public String batchCardImportIssue2(File file, String fileName) throws Exception{
		msg=null;
		InputStream  is=null;
		if(file==null||file.length()<=0){
			log("文件不能为空文件！");
			msg = "文件不能为空文件！";
			return msg;
		}
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ExcelReader reader = new ExcelReader();
		Map<Integer, String> readExcelContent2=null;
		try {
			readExcelContent2 = reader.readExcelContent2(is);
			//readExcelContent2 = reader.readExcelContent2(fileName,is);
		} catch (Exception e) {
			log("无法解析更高版本的EXCEL!");
			msg = "无法解析更高版本的EXCEL!";
			return msg;
		}
		//根据excel有多少列判断观察地区=13还是观察个人=14
		//根据excel的序列号判断观察地区=1还是观察个人=2
		int flag = 0;
		for(Integer key : readExcelContent2.keySet()){
			String cMessageString = readExcelContent2.get(key);
			String[] cMessage = cMessageString.split("--");
			String time = CommonFunction.getCurrentDateTime().trim();
			String insrtSql = null;
			//地区
			if(cMessage[0].equals("1.0")){
				flag = 13;
				insrtSql ="insert into TBL_BLACKLIST_REGION(BLACK_REGION_ID,BLACK_REGION_TYPE,BLACK_REGION_NO,BLACK_REGION_NAME,BLACK_REGION_HOME,UPDATETIME) "
						+ "values('"+ 1 +"','"+cMessage[1]+"','"+ cMessage[2] +"','"+ cMessage[3] +"',' ','"+ time +"') ";
			}
//			if (cMessage.length == 13) {
//				flag = 13;
//				insrtSql ="insert into TBL_BLACKLIST_REGION(BLACK_REGION_ID,BLACK_REGION_TYPE,BLACK_REGION_NO,BLACK_REGION_NAME,BLACK_REGION_HOME,UPDATETIME) "
//						+ "values('"+ 1 +"','"+cMessage[3]+"','"+ cMessage[4] +"','"+ cMessage[5] +"','"+ cMessage[1] +"','"+ time +"') ";
//			}
			
			//观察
			if(cMessage[0].equals("2.0")){
				flag = 14;
				String da = cMessage[1].replace('\'','*');//将单引号转换为*，在sql中将*转换为单引号
				
				if(cMessage.length == 9){
					insrtSql ="INSERT INTO TBL_BLACKLIST_OBSERVE (BLACK_OBSERVE_ID, BLACK_OBSERVE_NAME, BLACK_OBSERVE_SEX, BLACK_OBSERVE_BIR, BLACK_OBSERVE_COUNTRY, BLACK_OBSERVE_NO, BLACK_OBSERVE_TYPE, BLACK_OBSERVE_HOME, BLACK_OBSERVE_ENTITY, BLACK_OBSERVE_ADDRESS, BLACK_OBSERVE_CON, BLACK_OBSERVE_UPDATETIME) "  
							+ "values('"+ 2 +"',REPLACE('"+da+"','*',''''),'"+ cMessage[2] +"','"+ cMessage[3] +"','"+ cMessage[4] +"','"
							+ cMessage[5] +"','"+cMessage[6]+"','"+ cMessage[7] +"','"+ cMessage[8] +"',' ',' ','"+ time +"') ";
				}else{
					insrtSql ="INSERT INTO TBL_BLACKLIST_OBSERVE (BLACK_OBSERVE_ID, BLACK_OBSERVE_NAME, BLACK_OBSERVE_SEX, BLACK_OBSERVE_BIR, BLACK_OBSERVE_COUNTRY, BLACK_OBSERVE_NO, BLACK_OBSERVE_TYPE, BLACK_OBSERVE_HOME, BLACK_OBSERVE_ENTITY, BLACK_OBSERVE_ADDRESS, BLACK_OBSERVE_CON, BLACK_OBSERVE_UPDATETIME) "  
							+ "values('"+ 2 +"',REPLACE('"+da+"','*',''''),'"+ cMessage[2] +"','"+ cMessage[3] +"','"+ cMessage[4] +"','"
							+ cMessage[5] +"','"+cMessage[6]+"','"+ cMessage[7] +"','"+ cMessage[8] +"','"+ cMessage[9] +"','"+ cMessage[10] +"','"+ time +"') ";
				}
				
			}
//			if (cMessage.length == 14) {
//				flag = 14;
//				String da = cMessage[3].replace('\'','*');//将单引号转换为*，在sql中将*转换为单引号
//				insrtSql ="INSERT INTO TBL_BLACKLIST_OBSERVE (BLACK_OBSERVE_ID, BLACK_OBSERVE_NAME, BLACK_OBSERVE_SEX, BLACK_OBSERVE_BIR, BLACK_OBSERVE_COUNTRY, BLACK_OBSERVE_NO, BLACK_OBSERVE_TYPE, BLACK_OBSERVE_HOME, BLACK_OBSERVE_ENTITY, BLACK_OBSERVE_ADDRESS, BLACK_OBSERVE_CON, BLACK_OBSERVE_UPDATETIME) "  
//						+ "values('"+ 1 +"',REPLACE('"+da+"','*',''''),'"+ " " +"','"+ " " +"','"+ " " +"','"
//						+ " " +"','"+cMessage[4]+"','"+ cMessage[5] +"','"+ cMessage[2] +"','"+ " " +"','"+ cMessage[6] +"','"+ time +"') ";
//			}
			try {
				CommonFunction.getCommQueryDAO().excute(insrtSql);
			} catch (Exception e) {
				truncate1();
				truncate2();
				log("新增反洗钱数据失败！");
				msg = "新增反洗钱数据失败！";
				return msg;
			}
		}
		msg = fileName + "导入黑名单" + (flag == 13 ? "地区" : "个人") + "共计" + readExcelContent2.size() + "条";
		
		//关闭流，删除临时文件
		IOUtils.closeQuietly(is);
		file.delete();
		
		return msg;
	}
	
	
	/**
	 * 清空反洗钱黑名单数据(删除地区表和个人表内容)
	 * @return
	 */
	public String cleanUpBlackList(){
		String result = "反洗钱黑名单数据已清除";
		try{
			truncate1();
			truncate2();
			result += "成功！";
		}catch(Exception e){
			e.printStackTrace();
			result += "失败！";
		}
		return result;
	}
	
	/**
	 * 地区
	 * @return
	 */
	public String truncate1() {
		String insrtSqlr2 ="truncate table TBL_BLACKLIST_REGION";
		log("反洗钱批量导入数据，清空地区表数据   SQL:"+insrtSqlr2);
		CommonFunction.getCommQueryDAO().excute(insrtSqlr2);
		return "数据库旧数据清除成功！";
	}
	
	/**
	 * 反洗钱批量导入前清空数据库
	 * 观察
	 * @return 
	 */
	public String truncate2() {
		String insrtSqlr ="truncate table TBL_BLACKLIST_OBSERVE";
		log("反洗钱批量导入数据，清空观察表数据   SQL:"+insrtSqlr);
		CommonFunction.getCommQueryDAO().excute(insrtSqlr);
		return "数据库旧数据清除成功！";
	}
	
	/**
	 * 批量新增商户黑名单信息
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	private String uploadFile() throws Exception {
		rspCode = t40202BO.importFile(xlsFile, xlsFileFileName, operator);
		return rspCode;
	}
	// 受控商户号
	private String saMerNo;
	
	// 商户英文名称
	private String saMerEnName;
	// 商户受控金额
	private String saLimitAmt;
	// 商户受控动作
	private String saAction="2";
	
	// 联系人
	private String saConnOr;
	// 联系电话
	private String saConnTel;
	// 分行号
	private String saZoneNo;
	// 主管分行号
	private String saAdmiBranNo;
	
	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	// 黑名单商户修改集
	private String mchtInfList;
	
	
	
	private String saMerChName;
	private String licenceNo;
	private String identityNo;
	private String managerTel;
	private String appRemark;
	private String bankLicenceNo;
	private String datePk;
	private String saArea;
	
	//new
	private String id;
	private String ctcr;
	private String cttp;
	private String ctnm;
	private String ciid;
	private String cbad;
	private String cleg;
	private String clid;
	private String care;
	private String lstp;
	private String ckstatus;
	private String creator;
	
	private String rgtp;
	private String rgcd;
	private String rgnm;
	private String status;
	
	private String citp; 
	private String citp_nt; 
	private String ceml; 
	private String cbir; 
	private String crag; 
	private String cltp_nt; 
	private String cltp; 
//	private String care; 
	private String cpcd; 
	private String cadd; 
	private String cpho; 
	private String cmob; 
	private String lgtp;
	
	private String msg;
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRgtp() {
		return rgtp;
	}

	public void setRgtp(String rgtp) {
		this.rgtp = rgtp;
	}

	public String getRgcd() {
		return rgcd;
	}

	public void setRgcd(String rgcd) {
		this.rgcd = rgcd;
	}

	public String getRgnm() {
		return rgnm;
	}

	public void setRgnm(String rgnm) {
		this.rgnm = rgnm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCkstatus() {
		return ckstatus;
	}

	public void setCkstatus(String ckstatus) {
		this.ckstatus = ckstatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtcr() {
		return ctcr;
	}

	public void setCtcr(String ctcr) {
		this.ctcr = ctcr;
	}

	public String getCttp() {
		return cttp;
	}

	public void setCttp(String cttp) {
		this.cttp = cttp;
	}

	public String getCtnm() {
		return ctnm;
	}

	public void setCtnm(String ctnm) {
		this.ctnm = ctnm;
	}

	public String getCiid() {
		return ciid;
	}

	public void setCiid(String ciid) {
		this.ciid = ciid;
	}

	public String getCbad() {
		return cbad;
	}

	public void setCbad(String cbad) {
		this.cbad = cbad;
	}

	public String getCleg() {
		return cleg;
	}

	public void setCleg(String cleg) {
		this.cleg = cleg;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public String getCare() {
		return care;
	}

	public void setCare(String care) {
		this.care = care;
	}

	public String getLstp() {
		return lstp;
	}

	public void setLstp(String lstp) {
		this.lstp = lstp;
	}

	public String getSaArea() {
		return saArea;
	}

	public void setSaArea(String saArea) {
		this.saArea = saArea;
	}

	/**
	 * @return the saMerNo
	 */
	public String getSaMerNo() {
		return saMerNo;
	}

	/**
	 * @param saMerNo the saMerNo to set
	 */
	public void setSaMerNo(String saMerNo) {
		this.saMerNo = saMerNo;
	}

	/**
	 * @return the saMerChName
	 */
	public String getSaMerChName() {
		return saMerChName;
	}

	/**
	 * @param saMerChName the saMerChName to set
	 */
	public void setSaMerChName(String saMerChName) {
		this.saMerChName = saMerChName;
	}

	/**
	 * @return the saMerEnName
	 */
	public String getSaMerEnName() {
		return saMerEnName;
	}

	/**
	 * @param saMerEnName the saMerEnName to set
	 */
	public void setSaMerEnName(String saMerEnName) {
		this.saMerEnName = saMerEnName;
	}

	/**
	 * @return the saLimitAmt
	 */
	public String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * @param saLimitAmt the saLimitAmt to set
	 */
	public void setSaLimitAmt(String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * @return the saAction
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * @param saAction the saAction to set
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the xlsFile
	 */
	public List<File> getXlsFile() {
		return xlsFile;
	}

	/**
	 * @param xlsFile the xlsFile to set
	 */
	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	/**
	 * @return the xlsFileFileName
	 */
	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	/**
	 * @param xlsFileFileName the xlsFileFileName to set
	 */
	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	/**
	 * @return the mchtInfList
	 */
	public String getMchtInfList() {
		return mchtInfList;
	}

	/**
	 * @param mchtInfList the mchtInfList to set
	 */
	public void setMchtInfList(String mchtInfList) {
		this.mchtInfList = mchtInfList;
	}

	/**
	 * @return the saConnOr
	 */
	public String getSaConnOr() {
		return saConnOr;
	}

	/**
	 * @param saConnOr the saConnOr to set
	 */
	public void setSaConnOr(String saConnOr) {
		this.saConnOr = saConnOr;
	}

	/**
	 * @return the saConnTel
	 */
	public String getSaConnTel() {
		return saConnTel;
	}

	/**
	 * @param saConnTel the saConnTel to set
	 */
	public void setSaConnTel(String saConnTel) {
		this.saConnTel = saConnTel;
	}

	/**
	 * @return the saZoneNo
	 */
	public String getSaZoneNo() {
		return saZoneNo;
	}

	/**
	 * @param saZoneNo the saZoneNo to set
	 */
	public void setSaZoneNo(String saZoneNo) {
		this.saZoneNo = saZoneNo;
	}

	/**
	 * @return the saAdmiBranNo
	 */
	public String getSaAdmiBranNo() {
		return saAdmiBranNo;
	}

	/**
	 * @param saAdmiBranNo the saAdmiBranNo to set
	 */
	public void setSaAdmiBranNo(String saAdmiBranNo) {
		this.saAdmiBranNo = saAdmiBranNo;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getAppRemark() {
		return appRemark;
	}

	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}

	public String getBankLicenceNo() {
		return bankLicenceNo;
	}

	public void setBankLicenceNo(String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	public String getDatePk() {
		return datePk;
	}

	public void setDatePk(String datePk) {
		this.datePk = datePk;
	}
	
}
