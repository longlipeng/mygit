package com.huateng.struts.mchnt.action;

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
import com.huateng.bo.mchnt.T20701BO;
import com.huateng.bo.mchnt.T20706BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.po.mchnt.base.BaseTblHisDiscAlgo;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;
import com.huateng.system.util.SysParamUtil;
import com.lowagie.text.pdf.PRAcroForm;

public class T20708Action extends BaseAction{
	private final static Double _defaultDoutbl = new Double(0);	
	private static final long serialVersionUID = 1L;
	private T20701BO t20701BO = (T20701BO)ContextUtil.getBean("T20701BO");
	private T20706BO t20706BO = (T20706BO) ContextUtil.getBean("T20706BO");
	public static String ADD_TO_CHECK = "0";
	private List<File> files;
	private List<String> filesFileName;
	
	
	/**
	 * 导入 信息 
	 * @return
	 */
	public String upload(){
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
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
			File uploadFile = new File(basePath + fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(uploadFile), "GBK"));
			
			int succ = 0;
			int fail = 0;
			int index = 1;
			int param = 0;
			
			boolean flag1 = false;//标志：导入文件中是否重复包含 表中已存在的数据			
			boolean flag2 = false;//标志：导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：导入文件中分隔符是否正确
			boolean flag4 = false;//标志：导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志： 导入文件中是否包含不允许的特殊符号，例如单引号
			List<TblHisDiscAlgoTmp> routeInfos = new ArrayList<TblHisDiscAlgoTmp>();
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			List<List<TblHisDiscAlgoTmp>> listHis = new ArrayList<List<TblHisDiscAlgoTmp>>();
			List<List<TblHisDiscAlgo>> listHisNorList = new ArrayList<List<TblHisDiscAlgo>>();
			List<TblInfDiscCdTmp> infList = new ArrayList<TblInfDiscCdTmp>();
			List<TblInfDiscCd> infListNor = new ArrayList<TblInfDiscCd>();
//			TblInfDiscCdTmp inf = new TblInfDiscCdTmp();
			
			while(reader.ready()){
				//执行读取操作
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = tmp.trim().split(",");//去掉前后空格，再按逗号分割
				if(strs.length < 7 || strs.length > 7){//每行被逗号分割后不等于7列的情况不处理
					flag3 = true;
					continue;
				}
				if(strs[0].trim().startsWith("计费算法导入")){//列名所在标题行不做处理
					flag4 = true;
					continue;
				}
				try{
					String discNm = strs[1];//计费算法名称，不能超过数据库中该字段的长度，如果超过也提示"包含不存在的字段数据"——卡BIN改为发卡机构20130715
					if( discNm.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if( discNm.length()>40 ){
						flag2 = true;
						continue;
					}

					String floorMount = strs[2];//最低交易金额
					if( floorMount.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}

					String flag = strs[3];//回佣类型
					if( flag.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}

					String feeValue = strs[4];//签约费率
					if( feeValue.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}

					String minFee = strs[5];//按笔最低收费
					if( minFee.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}

					String maxFee = strs[6];//按笔最高收费
					if( maxFee.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}

					String discCd = new String();
					
//					String oprBrhId = getOperator().getOprBrhId();
					String oprBrhId = "0000";
					int max = 1;
					
					//判断是否存在序号为0001的ID
					String sql = "select count(1) from tbl_inf_disc_cd_temp where trim(DISC_CD) = '" + "JF"  + "000001" + "'" ;
					BigDecimal c = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
					if (c.intValue() != 0) {
						sql = "select nvl(MIN(SUBSTR(DISC_CD,3,6) + 1),1) from tbl_inf_disc_cd_temp " +
							"where lpad((SUBSTR(DISC_CD,3,6) + 1),6,0) not in (select (SUBSTR(DISC_CD,3,6)) " +
							"from tbl_inf_disc_cd_temp ) " ;
							/*"from tbl_inf_disc_cd_temp where substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "') " +
							"and substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "'";*/

						BigDecimal bg = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
						max = bg.intValue()+ param++;
						if(max > 999999) {
							return ("该机构的计费算法已满");
						}
					}
			//		System.out.println(max);
					if (c.intValue() == 0) {
						discCd = "JF" /*+ oprBrhId.substring(0,2)*/ + CommonFunction.fillString(String.valueOf(max + param++), '0', 6, false);
					}else{
						discCd = "JF" /*+ oprBrhId.substring(0,2)*/ + CommonFunction.fillString(String.valueOf(max), '0', 6, false);
					}
					
//					System.out.println(discCd);
					TblInfDiscCdTmp inf = new TblInfDiscCdTmp();
					inf.setDiscCd(discCd);
					inf.setDiscOrg(oprBrhId);
					inf.setDiscNm(discNm);
					inf.setLastOperIn("0");
					inf.setCre_id("0000");
					inf.setRecUpdUserId("0000");
					inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
					inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					inf.setSastate(CommonFunction.NORMAL);
					
					infList.add(inf);
					
					TblInfDiscCd infNor = new TblInfDiscCd();
					infNor.setDiscCd(discCd);
					infNor.setDiscOrg(oprBrhId);
					infNor.setDiscNm(discNm);
					infNor.setLastOperIn("0");
					infNor.setCre_id("0000");
					infNor.setRecUpdUserId("0000");
					infNor.setRecCrtTs(CommonFunction.getCurrentDateTime());
					infNor.setRecUpdTs(CommonFunction.getCurrentDateTime());
					infNor.setSastate(CommonFunction.NORMAL);
					
					infListNor.add(infNor);
					
					TblHisDiscAlgoTmp temp = new TblHisDiscAlgoTmp();
					TblHisDiscAlgoPK key = new TblHisDiscAlgoPK();
					//默认插入一条最低交易金额为0的数据
					TblHisDiscAlgoTmp temp2 = new TblHisDiscAlgoTmp();
					TblHisDiscAlgoPK key2 = new TblHisDiscAlgoPK();
					
					key.setDiscId(discCd);
					if(floorMount.equals("0")){
						key.setIndexNum(0);
					}else{
						key.setIndexNum(1);
					}
					
					temp.setId(key);
					temp.setMinFee(new BigDecimal(minFee));
					temp.setMaxFee(new BigDecimal(maxFee));
//					temp.setTxnNum(txnCode);
					temp.setFeeValue(new BigDecimal(feeValue));
					temp.setFloorMount(BigDecimal.valueOf(CommonFunction.getDValue(floorMount, _defaultDoutbl)));
					if("按笔收费".equals(flag)){
						temp.setFlag(1);
					}else{
						temp.setFlag(2);
					}
//					temp.setFlag(CommonFunction.getInt(flag, -1));
					temp.setUpperMount(new BigDecimal("0"));
					temp.setRecCrtTs("0000");
					//temp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					temp.setRecCrtTs(CommonFunction.getCurrentDateTime());
					
					//默认插入一条最低交易金额为0的数据
					key2.setDiscId(discCd);
					key2.setIndexNum(0);
					temp2.setId(key2);
					temp2.setFloorMount(new BigDecimal("0"));//最低交易金额为0
					temp2.setFlag(1);//回佣类型				
					temp2.setFeeValue(new BigDecimal("0"));//回佣值
					temp2.setMinFee(new BigDecimal(0.01));
					temp2.setMaxFee(new BigDecimal(99999));
					List<TblHisDiscAlgoTmp> listHisTmp = new ArrayList<TblHisDiscAlgoTmp>();
					List<TblHisDiscAlgo> listHisNor = new ArrayList<TblHisDiscAlgo>();
					TblInfDiscCd tblInfDiscCd=new TblInfDiscCd(); 
					TblHisDiscAlgo tblHisDiscAlgo=new TblHisDiscAlgo();
					TblHisDiscAlgo tblHisDiscAlgo2=new TblHisDiscAlgo();
					tblHisDiscAlgo.setId(key);
					tblHisDiscAlgo.setMinFee(new BigDecimal(minFee));
					tblHisDiscAlgo.setMaxFee(new BigDecimal(maxFee));
					tblHisDiscAlgo.setFeeValue(new BigDecimal(feeValue));
					tblHisDiscAlgo.setFloorMount(BigDecimal.valueOf(CommonFunction.getDValue(floorMount, _defaultDoutbl)));
					if("按笔收费".equals(flag)){
						tblHisDiscAlgo.setFlag(1);
					}else{
						tblHisDiscAlgo.setFlag(2);
					}
					tblHisDiscAlgo.setUpperMount(new BigDecimal("0"));
					tblHisDiscAlgo.setRecCrtTs("0000");
					tblHisDiscAlgo.setRecCrtTs(CommonFunction.getCurrentDateTime());
					
					
					key2.setDiscId(discCd);
					key2.setIndexNum(0);
					tblHisDiscAlgo2.setId(key2);
					tblHisDiscAlgo2.setFloorMount(new BigDecimal("0"));//最低交易金额为0
					tblHisDiscAlgo2.setFlag(1);//回佣类型				
					tblHisDiscAlgo2.setFeeValue(new BigDecimal("0"));//回佣值
					tblHisDiscAlgo2.setMinFee(new BigDecimal(0.01));
					tblHisDiscAlgo2.setMaxFee(new BigDecimal(99999));
					
//					t20706BO.add(tblHisDiscAlgo);
					BeanUtils.copyProperties(tblInfDiscCd, inf);
//					t20706BO.add(tblInfDiscCd);
				//	List<TblHisDiscAlgoTmp> listHisTmp2 = new ArrayList<TblHisDiscAlgoTmp>();
					listHisTmp.add(temp);
					if(!"0".equals(floorMount)){
					listHisTmp.add(temp2);
					}
					listHis.add(listHisTmp);
					listHisNor.add(tblHisDiscAlgo);
					if(!"0".equals(floorMount)){
						listHisNor.add(tblHisDiscAlgo2);
					}
					listHisNorList.add(listHisNor);
				//	listHis.add(listHisTmp2);	
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
//				System.out.println(tmp);
			}
			reader.close();
			
			if(!flag4)// 导入文件中不包含标题行
				return " 导入文件中没有标题行，请核实。";
			if(flag3)// 导入文件中分隔符不正确
				return " 导入文件中分隔符不正确，请核实。";
			if(flag6)// 导入文件中的字段包含不允许的特殊符号,例如单引号
				return " 导入文件中包含不允许的特殊符号:单引号，请核实。";
//			if(flag2)// 导入文件中包含不存在的字段数据
//				return " 导入文件中包含不存在的字段数据，请核实。";
			if(flag1)// 导入文件中重复包含 表中已存在的数据
				return " 导入文件中重复包含 表中已存在的数据，请核实。";
			if(flag5)// 导入文件中包含两条重复数据
				return " 导入文件中包含两条重复数据，请核实。";
			else{
				//保存 信息
				for(int i=0 ; i<infList.size() ; i++){
					try{
						t20701BO.createArith(list,infList.get(i), listHis.get(i));
						t20706BO.createArith(list,infListNor.get(i), listHisNorList.get(i));
						succ++;
					}catch(Exception e){
						fail++;
					}
				}
				return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)+"条数据";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入 信息文件失败";
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


	
	
	public Operator getOperator() {
		if (null == operator) {
			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		}
		return operator;
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
			log("操作员编号：" + operator.getOprId()+ "，导入 信息文件操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
}
