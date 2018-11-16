package com.huateng.struts.base.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.huateng.bo.base.T10110BO;
import com.huateng.bo.base.T11502BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.po.base.TblAgentDivide;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T10111ImpAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
//	private T11502BO t11502BO = (T11502BO)ContextUtil.getBean("T11502BO");
	private T10110BO t10110BO = (T10110BO) ContextUtil.getBean("T10110BO");
	public static String ADD_TO_CHECK = "0";
	private List<File> files;
	private List<String> filesFileName;
	
	/**
	 * 导入机构费率信息
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
			int error = 0;
			int k = 1;
			String feeId = "";
			StringBuffer sb = new StringBuffer();
			
			//主键feeId
			String id="00000000";
			String sql="select max(fee_id) from tbl_agency_fee_lub_tmp ";
			List idList = commQueryDAO.findBySQLQuery(sql);
			if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
				id= CommonFunction.fillString(idList.get(0).toString(),'0', 8, false);
			}	
		//	id=CommonFunction.fillString(String.valueOf(Integer.parseInt(id) + 1), '0', 8, false);
			
			
			boolean flag = false;//标志：导入文件中是否重复包含路由表中已存在的数据			
			boolean flag2 = false;//标志：导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：导入文件中分隔符是否正确
			boolean flag4 = false;//标志：导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志：导入文件中是否包含不允许的特殊符号，例如单引号
			boolean flag7 = false;//标志：导入文件 机构费率不是00或01
			boolean flag8 = false;//标志：导入文件机构类型不是01到08
			boolean flag9 = false;//标志：导入文件 机构分润率不是00或01
			/*List<TblRouteInfoTemp> routeInfos = new ArrayList<TblRouteInfoTemp>();*/
			
			List<AgencyFeeLubTmp> list = new ArrayList<AgencyFeeLubTmp>();
			
			while(reader.ready()){
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = tmp.trim().split(",",-1);//去掉前后空格，再按逗号分割
				if(strs.length < 15 || strs.length > 15){//每行被逗号分割后不等于11列的情况不处理
					flag3 = true;
					continue;
				}
				if(strs[0].trim().startsWith("机构代码")){//列名所在标题行不做处理
					flag4 = true;
					continue;
				}
				try{
					
					String agenId = strs[0].trim();  //机构代码
					String agenType = strs[1].trim();//机构类型
					String extenField1 = strs[2].trim();  //交易联接类型
					String mtchNo = strs[3].trim();   //商户号
					String termId = strs[4].trim(); //终端号
					String tradeAcceptReg = strs[5].trim(); //交易受理地区TRADE_ACCEPT_REG
					String mccCode = strs[6].trim(); //MCC码MCC_CODE
					String mchtRateMethod = strs[7].trim(); //机构费率方式MCHT_RATE_METHOD
					String rateParam1 = strs[8].trim(); //费率值RATE_PARAM1
					String mchtPercentLimit = strs[9].trim(); //机构费率下限值MCHT_PERCENT_LIMIT
					String mchtPercentMax = strs[10].trim(); //机构费率上限值MCHT_PERCENT_MAX
					String mchtLubMethod = strs[11].trim();  //机构分润方式
					String mchtLubPercentLimit = strs[12].trim();  //机构分润下限值
					String mchtLubPercentMax = strs[13].trim();   //机构分润上限值
					String lubParam1 =strs[14].trim();         //分润参数1
					
					
					
					if(!"00".equals(mchtRateMethod) & !"01".equals(mchtRateMethod)){
						flag7 = true;
						continue;
					}
					
					if(!"00".equals(mchtLubMethod) & !"01".equals(mchtLubMethod)){
						flag9 = true;
						continue;
					}
					
					if(!Pattern.matches("(01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)", agenType)){
						flag8 = true;
						continue;
					}
					
					
					/*
					 * 判断同一代理商编号的分润方式为按金额的是否存在
					 * by 20141011 shiyiwen
					 */
					/*if("1".equals(divideType)){
						String sql = "select count(*) from tbl_agent_divide where agent_no ='"+agentNo.trim()+"' and divide_type ='1'";
				        String countSql = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
				        if(!"0".equals(countSql)){
				        	flag7 = true;
				        	continue;
				        }
					}	*/
					AgencyFeeLubTmp  agencyFeeLubTmp=new AgencyFeeLubTmp();
					
					feeId=CommonFunction.fillString(String.valueOf(Integer.parseInt(id) + k++ ), '0', 8, false);
					agencyFeeLubTmp.setFEE_ID(feeId);
					agencyFeeLubTmp.setAGEN_ID(agenId);//机构代码
					agencyFeeLubTmp.setAGEN_TYPE(agenType);//机构类型
					agencyFeeLubTmp.setEXTEN_FIELD1(extenField1);//交易联接类型
					agencyFeeLubTmp.setMTCH_NO(mtchNo);//商户号
					agencyFeeLubTmp.setTERM_ID(termId);//终端号
					agencyFeeLubTmp.setTRADE_ACCEPT_REG(tradeAcceptReg);//交易受理地区
					agencyFeeLubTmp.setMCC_CODE(mccCode);//MCC码
					
					agencyFeeLubTmp.setMCHT_RATE_METHOD(mchtRateMethod);//机构费率方式
					agencyFeeLubTmp.setRATE_PARAM1(rateParam1);//费率值
					agencyFeeLubTmp.setMCHT_PERCENT_LIMIT(mchtPercentLimit);//机构费率百分比下限值
					agencyFeeLubTmp.setMCHT_PERCENT_MAX(mchtPercentMax);//机构费率百分比上限值
					
					agencyFeeLubTmp.setMCHT_LUB_METHOD(mchtLubMethod);
					agencyFeeLubTmp.setMCHT_LUB_PERCENT_LIMIT(mchtLubPercentLimit);
					agencyFeeLubTmp.setMCHT_LUB_PERCENT_MAX(mchtLubPercentMax);
					agencyFeeLubTmp.setLUB_PARAM1(lubParam1);
					
					
					//机构费率参数与机构分润参数 要唯一，所以将其值设为主键的值
					agencyFeeLubTmp.setMCHT_RATE_PARAM(agencyFeeLubTmp.getFEE_ID());
					agencyFeeLubTmp.setMCHT_LUB_PARAM(agencyFeeLubTmp.getFEE_ID());
					agencyFeeLubTmp.setCRE_OPR_ID(operator.getOprId());
					agencyFeeLubTmp.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
					agencyFeeLubTmp.setSTATUE(Constants.ADD_TO_CHECK);
				
					if(list!=null && list.size() >0){
						
						for(int i=0;i<list.size();i++){
							AgencyFeeLubTmp agencyFeeLubTmp2 = list.get(i);
							if(agenId.equals(agencyFeeLubTmp2.getAGEN_ID()) && mccCode.equals(agencyFeeLubTmp2.getMCC_CODE())){
								flag = true;
								continue;
							}
						}
					}
					
					
					
					
					//判断集合中是否已包含相同内容的记录20121129
					/*if(list!=null && list.size()>0){
						for(int i=0 ; i<list.size() ; i++){
							TblAgentDivide temp = list.get(i);
							TblAgentDividePK tempPK = temp.getPrimaryKey();
							if("1".equals(tempPK.getDivideType().trim())){  //如果分润方式为按交易金额
								if(agentNo.equals(tempPK.getAgentNo()) && divideType.equals(tempPK.getDivideType()) ){//只要文件中有两条数据重复，即提示失败信息
									flag8 = true;
									continue;
								}
							}else{
								if(agentNo.equals(tempPK.getAgentNo()) && divideType.equals(tempPK.getDivideType()) 
										&& discCd.equals(tempPK.getDiscCd())
										){//只要文件中有两条数据重复，即提示失败信息
									flag5 = true;
									continue;
								}
							}
						}
					}*/
					//将路由信息保存到集合中
					list.add(agencyFeeLubTmp);					
				}catch(Exception e){
					continue;
				}
		//		System.out.println(tmp);
			}
			reader.close();
			
			if(!flag4)//路由导入文件中不包含标题行
				return "导入文件中没有标题行，请核实。";
			if(flag3)//路由导入文件中分隔符不正确
				return "导入文件中分隔符不正确，请核实。";
			if(flag6)//路由导入文件中的字段包含不允许的特殊符号,例如单引号
				return "导入文件中包含不允许的特殊符号:单引号，请核实。";
			if(flag2)//路由导入文件中包含不存在的字段数据
				return "导入文件中包含不存在的字段数据，请核实。";
			if(flag)//路由导入文件中重复包含路由表中已存在的数据
				return "导入文件中存在机构号和mcc码相同的两条记录，请核实。";
			if(flag5)//路由导入文件中包含两条重复数据
				return "导入文件中包含两条重复数据，请核实。";
			if(flag7)
				return "存在机构费率方式填写错误";
			if(flag8)
				return "存在机构类型填写错误。";
			if(flag9)
				return "存在机构分润率方式填写错误";
			else{
				//保存费率信息
				for(int i=0 ; i<list.size() ; i++){
					try{
						String sqlAgenId = "select count(*) from TBL_AGENCY_INFO_TRUE where statue='1'  and agen_id = '" + list.get(i).getAGEN_ID() + "' and tran_type = '"+list.get(i).getEXTEN_FIELD1()+"'";
						String count = commQueryDAO.findCountBySQLQuery(sqlAgenId);
					
						
						if("0".equals(count)){
							error++;
							sb.append( list.get(i).getAGEN_ID()).append(",");
							continue;
						}
						//如果机构号和mcc码相同，就更新记录
						List<AgencyFeeLubTmp> agencyFeeLubTmpList = new ArrayList<AgencyFeeLubTmp>();
						String sql2 = "select fee_id,agen_id , mcc_code from tbl_agency_fee_lub_tmp where agen_id = '"+list.get(i).getAGEN_ID().toString()+"' and mcc_code = '"+list.get(i).getMCC_CODE().toString()+"'";
						List<Object[]> list2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
						if(list2.size()!=0){
							for(int j = 0;j<list2.size();j++){
						//		AgencyFeeLubTmp  agencyFeeLubTmp2=new AgencyFeeLubTmp();
								list.get(i).setFEE_ID(list2.get(j)[0].toString());
								agencyFeeLubTmpList.add(list.get(i));
								t10110BO.updateTmp(agencyFeeLubTmpList);
								succ++;
							}
							
							
						}else{
						
						
						t10110BO.add((list.get(i)));
						succ++;
						}
					}catch(Exception e){
						fail++;
					}
				}
				String sss = sb.toString();
				if(sss.length()>0){
					sss = sss.substring(0, sss.length()-1);
				}else{
					sss = "无";
				}
				return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)
						+"条数据,机构不存在或该机构不存在此交易联接类型:" + String.valueOf(error) + "条数据,分别为:" + sss;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入机构费率信息文件失败";
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
		
	@Override
	protected String subExecute() throws Exception {
		try {
			if("upload".equals(method)) {
				rspCode = upload();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，导入信息文件操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
}
