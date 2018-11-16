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

import com.huateng.bo.base.T11503BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.base.TblAgentDivideTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T11502ImpAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private T11503BO t11503BO = (T11503BO)ContextUtil.getBean("T11503BO");
	public static String ADD_TO_CHECK = "0";
	private List<File> files;
	private List<String> filesFileName;
	
	/**
	 * 导入分润信息
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
			StringBuffer sb = new StringBuffer();
			
			boolean flag = false;//标志：导入文件中是否重复包含路由表中已存在的数据			
			boolean flag2 = false;//标志：导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：导入文件中分隔符是否正确
			boolean flag4 = false;//标志：导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志：导入文件中是否包含不允许的特殊符号，例如单引号
			boolean flag7 = false;//标志：导入文件中分润方式为按交易金额的同一代理商已存在
			boolean flag8 = false;//标志：导入文件中存在多条分润方式为按交易金额的同一代理商
			boolean flag9 = false;//标志：导入文件中分润方式是否填写错误
			/*List<TblRouteInfoTemp> routeInfos = new ArrayList<TblRouteInfoTemp>();*/
			List<TblAgentDivideTmp>  list = new ArrayList<TblAgentDivideTmp>();
			
			while(reader.ready()){
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = tmp.trim().split(",");//去掉前后空格，再按逗号分割
				if(strs.length < 6 || strs.length > 6){//每行被逗号分割后不等于4列的情况不处理
					flag3 = true;
					continue;
				}
				if(strs[0].trim().startsWith("代理商编号")){//列名所在标题行不做处理
					flag4 = true;
					continue;
				}
				try{
					
					String agentNo = strs[0].trim();
					String divideType = strs[1].trim();
					String discCd = strs[2].trim();
					double  minValue = Double.parseDouble(strs[3]);
					double  maxValue = Double.parseDouble(strs[4]);
				    String  profit = strs[5];
					
					
					/*String cardBin = strs[0];//卡BIN编号，不能超过数据库中该字段的长度，如果超过也提示"包含不存在的字段数据"——卡BIN改为发卡机构20130715
					if( cardBin.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if( cardBin.length()>30 ){
						flag2 = true;
						continue;
					}
					String cardBinSql = "select count(*) from  TBL_BANK_BIN_INF where trim(INS_ID_CD)='" + cardBin.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(cardBinSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					//20121127添加对字段数据可用性的检验
					String bussType = strs[1];//业务类型
					if( bussType.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String bussTypeSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'SERVICECODE' and KEY='" + bussType.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(bussTypeSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					String txnNum = strs[2];//交易类型
					if( txnNum.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String txnNumSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'TXNTYPE' and KEY='" + txnNum.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(txnNumSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					String channel = strs[3];//渠道
					if( channel.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String channelSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'CHANNEL' and KEY='" + channel.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(channelSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					String areaNo = strs[4];//地区
					if( areaNo.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String areaNoSql = "select count(*) from TBL_CITY_CODE where CITY_CODE='" + areaNo.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(areaNoSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					String cardType = "*";
					
					String mchntId = strs[5];//受理商户编号
					if( mchntId.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String mchntIdSql = "select count(*) from TBL_MCHT_BASE_INF_TMP where MCHT_NO='" + mchntId.trim() + "'";
					//只要该字段数据没有匹配现有表中记录或者不是*，即提示失败信息20121129modified
					if( "0".equals(commQueryDAO.findCountBySQLQuery(mchntIdSql)) && !"*".equals(mchntId.trim())){
						flag2 = true;
						continue;
					}
					String destInstId = strs[6];//目的机构
					if( destInstId.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String destInstIdSql = "select count(*) from  TBL_AGENCY_INFO_TRUE where statue='1' and AGEN_ID='" + destInstId.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(destInstIdSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}  */
					
					
					
			/*		//根据路由信息去路由信息临时表中验证数据唯一性
					//状态为已删除的记录不参与重复性判断
					String sql = "select count(*) from tbl_agent_divide where AGENT_NO='" + agentNo.trim()
						+ "' and DIVIDE_TYPE='" + divideType.trim()+ "' and DISC_CD='" + discCd.trim() +"' and PROFIT = '"+profit.trim()+"'";
	
					String count = commQueryDAO.findCountBySQLQuery(sql);
					if(!"0".equals(count)){//只要文件中有一条数据重复，即提示失败信息
						flag = true;
						continue;
					}
					*/
					
					/*
					 * 判断同一代理商编号的分润方式为按金额的是否存在
					 * by 20141011 shiyiwen
					 */
					if("1".equals(divideType)){
						String sql = "select count(*) from tbl_agent_divide_tmp where agent_no ='"+agentNo.trim()+"' and divide_type ='1' and state != '1'";
				        String countSql = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
				        if(!"0".equals(countSql)){
				        	flag7 = true;
				        	continue;
				        }
					}	
					
					if(!"1".equals(divideType) & !"2".equals(divideType)){
						flag9 = true;
						continue;
					}
					
				//	TblAgentDividePK PK = new TblAgentDividePK(agentNo,divideType,discCd);
					String uuid = StringUtil.getUUID();
					TblAgentDivideTmp tblAgentDivideTmp = new TblAgentDivideTmp();
					
					tblAgentDivideTmp.setUuid(uuid);
					tblAgentDivideTmp.setAgentNo(agentNo);
					tblAgentDivideTmp.setDivideType(divideType);
					tblAgentDivideTmp.setDiscCd(discCd);
					tblAgentDivideTmp.setMinValue(minValue*10000);
					tblAgentDivideTmp.setMaxValue(maxValue*10000);
					tblAgentDivideTmp.setProfit(profit);
				
					tblAgentDivideTmp.setState("0"); //新增待审核
					tblAgentDivideTmp.setCrtPer(operator.getOprId());
					tblAgentDivideTmp.setCrtDate(CommonFunction.getCurrentDateTime());
					
					
					
					
					
					//判断集合中是否已包含相同内容的记录20121129
					if(list!=null && list.size()>0){
						for(int i=0 ; i<list.size() ; i++){
							TblAgentDivideTmp temp = list.get(i);
						//	String uuid2 = tmp.getUuid();
							if("1".equals(divideType.trim())){  //如果分润方式为按交易金额
								if(agentNo.equals(temp.getAgentNo()) && divideType.equals(temp.getDivideType()) ){//只要文件中有两条数据重复，即提示失败信息
									flag8 = true;
									continue;
								}
							}/*else{
								if(agentNo.equals(tempPK.getAgentNo()) && divideType.equals(tempPK.getDivideType()) 
										&& discCd.equals(tempPK.getDiscCd())
										){//只要文件中有两条数据重复，即提示失败信息
									flag5 = true;
									continue;
								}
							}*/
						}
					}
					//将路由信息保存到集合中
					list.add(tblAgentDivideTmp);					
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
				return "导入文件中重复包含路由表中已存在的数据，请核实。";
			if(flag5)//路由导入文件中包含两条重复数据
				return "导入文件中包含两条重复数据，请核实。";
			if(flag7)//路由导入文件中包含两条重复数据
				return "分润方式为按交易金额的同一代理商已存在。";
			if(flag8)//路由导入文件中包含两条重复数据
				return "文件中存在多条分润方式为按交易金额的同一代理商。";
			if(flag9)
				return "存在分润方式填写错误。请核实";
			else{
				//保存路由信息
				for(int i=0 ; i<list.size() ; i++){
					try{
						String sql = "select count(1) from tbl_agent_info where agent_no = '" + list.get(i).getAgentNo() + "'";
						String str = commQueryDAO.findCountBySQLQuery(sql);
						if("0".equals(str)){
							error++;
							sb.append(list.get(i).getAgentNo()).append(",");
							continue;
						}
						t11503BO.saveOrUpdate((list.get(i)));
						succ++;
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
						+"条数据,代理商不存在:" + String.valueOf(error) + "条数据,分别为:" + sss;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入路由信息文件失败";
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
