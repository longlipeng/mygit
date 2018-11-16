package com.huateng.struts.rout.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.rout.T11011BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T11018Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private T11011BO t11011BO = (T11011BO)ContextUtil.getBean("T11011BO");
	public static String ADD_TO_CHECK = "0";
	private List<File> files;
	private List<String> filesFileName;
	
	/**
	 * 导入路由信息 
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
			
			boolean flag = false;//标志：路由导入文件中是否重复包含路由表中已存在的数据			
			boolean flag2 = false;//标志：路由导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：路由导入文件中分隔符是否正确
			boolean flag4 = false;//标志：路由导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：路由导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志：路由导入文件中是否包含不允许的特殊符号，例如单引号
			List<TblRouteInfoTemp> routeInfos = new ArrayList<TblRouteInfoTemp>();
			
			while(reader.ready()){
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = tmp.trim().split(",");//去掉前后空格，再按逗号分割
				if(strs.length < 11 || strs.length > 11){//每行被逗号分割后不等于7列的情况不处理
					flag3 = true;
					continue;
				}
				if(strs[0].trim().startsWith("发卡机构")){//列名所在标题行不做处理
					flag4 = true;
					continue;
				}
				try{
					String cardBin = strs[0].trim();//卡BIN编号，不能超过数据库中该字段的长度，如果超过也提示"包含不存在的字段数据"——卡BIN改为发卡机构20130715
					
				/*	String cardBinSql = "select count(*) from  TBL_BANK_BIN_INF where trim(INS_ID_CD)='" + cardBin.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(cardBinSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					//20121127添加对字段数据可用性的检验
					String bussType = strs[1].trim();//业务类型
				
				//	String bussTypeSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'SERVICECODE' and KEY='" + bussType.trim() + "'";
					/*if( "0".equals(commQueryDAO.findCountBySQLQuery(bussTypeSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					String txnNum = strs[2].trim();//交易类型
				
				//	String txnNumSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'TXNTYPE' and KEY='" + txnNum.trim() + "'";
				/*	if( "0".equals(commQueryDAO.findCountBySQLQuery(txnNumSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					String channel = strs[3].trim();//渠道
				
			//		String channelSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'CHANNEL' and KEY='" + channel.trim() + "'";
			/*		if( "0".equals(commQueryDAO.findCountBySQLQuery(channelSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					String areaNo = strs[4].trim();//地区
			
				//	String areaNoSql = "select count(*) from TBL_CITY_CODE where CITY_CODE='" + areaNo.trim() + "'";
				/*	if( "0".equals(commQueryDAO.findCountBySQLQuery(areaNoSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
				//	String cardType = "*";
					String cardType = strs[5].trim();//卡类型
				/*
                	String cardTypeSql = "select count(*) from CST_SYS_PARAM WHERE OWNER = 'CARDTYPE' and KEY='" + channel.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(cardTypeSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					String mchntId = strs[6].trim();//受理商户编号
				
				//	String mchntIdSql = "select count(*) from TBL_MCHT_BASE_INF_TMP where MCHT_NO='" + mchntId.trim() + "'";
					//只要该字段数据没有匹配现有表中记录或者不是*，即提示失败信息20121129modified
					/*if( "0".equals(commQueryDAO.findCountBySQLQuery(mchntIdSql)) && !"*".equals(mchntId.trim())){
						flag2 = true;
						continue;
					}*/
					String destInstId = strs[7].trim();//目的机构
				
				//	String destInstIdSql = "select count(*) from  TBL_AGENCY_INFO_TRUE where statue='1' and AGEN_ID='" + destInstId.trim() + "'";
				/*	if( "0".equals(commQueryDAO.findCountBySQLQuery(destInstIdSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					
					//根据路由信息去路由信息临时表中验证数据唯一性
					//状态为已删除的记录不参与重复性判断
					String sql = "select count(*) from TBL_TXN_ROUTE_INF_TEMP where sa_state<>'1' and card_bin='" + cardBin.trim()
						+ "' and buss_type='" + bussType + "' and txn_num='" + txnNum.trim() + "' and channel='" + channel.trim() 
						+ "' and area_no='" + areaNo + "' and merch_id='" + mchntId +"' and card_type='"+cardType.trim()+"'";
						//+ "' and dest_inst_id='" + destInstId.trim() +"'";
					String count = commQueryDAO.findCountBySQLQuery(sql);
					if(!"0".equals(count)){//只要文件中有一条数据重复，即提示失败信息
						flag = true;
						continue;
					}
					
					long maxAmt = (long) (Double.valueOf(strs[8].trim())*100);  //最高金额
					long minAmt = (long) (Double.valueOf(strs[9].trim())*100);   //最低金额
					
					String mchtMcc = strs[10].trim();  //Mcc码
					
					TblRouteInfoTemp routeTemp = new TblRouteInfoTemp();
					routeTemp.setReserved(StringUtil.getUUID());
					routeTemp.setCardBin(cardBin);
					routeTemp.setBussType(bussType);
					routeTemp.setTxnNum(txnNum);
					routeTemp.setChannel(channel);
					routeTemp.setAreaNo(areaNo);
					routeTemp.setMchntId(mchntId);
					routeTemp.setCardType(cardType);
					routeTemp.setCreOprId("0000");
					routeTemp.setCreTime(CommonFunction.getCurrentDateTime());
					routeTemp.setCreatorId("0000");//修改人
					routeTemp.setUpdateTime(CommonFunction.getCurrentDateTime());
					routeTemp.setSaState(ADD_TO_CHECK);
					routeTemp.setDestInstId(destInstId);
					routeTemp.setMaxAmt(maxAmt);
					routeTemp.setMinAmt(minAmt);
					routeTemp.setMchtMcc(mchtMcc);
					
					
					//判断集合中是否已包含相同内容的记录20121129
					if(routeInfos!=null && routeInfos.size()>0){
						for(int i=0 ; i<routeInfos.size() ; i++){
							TblRouteInfoTemp temp = routeInfos.get(i);
							if(cardBin.equals(temp.getCardBin()) && bussType.equals(temp.getBussType()) 
									&& txnNum.equals(temp.getTxnNum()) && channel.equals(temp.getChannel()) 
									&& areaNo.equals(temp.getAreaNo()) && mchntId.equals(temp.getMchntId()) 
									&& cardType.equals(temp.getCardType().trim())
									/*&& destInstId.equals(temp.getDestInstId())*/){//只要文件中有两条数据重复，即提示失败信息
								flag5 = true;
								continue;
							}
						}
					}
					//将路由信息保存到集合中
					routeInfos.add(routeTemp);					
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
				System.out.println(tmp);
			}
			reader.close();
			
			if(!flag4)//路由导入文件中不包含标题行
				return "路由导入文件中没有标题行，请核实。";
			if(flag3)//路由导入文件中分隔符不正确
				return "路由导入文件中分隔符不正确，请核实。";
			if(flag6)//路由导入文件中的字段包含不允许的特殊符号,例如单引号
				return "路由导入文件中包含不允许的特殊符号:单引号，请核实。";
			if(flag2)//路由导入文件中包含不存在的字段数据
				return "路由导入文件中包含不存在的字段数据，请核实。";
			if(flag)//路由导入文件中重复包含路由表中已存在的数据
				return "路由导入文件中重复包含路由表中已存在的数据，请核实。";
			if(flag5)//路由导入文件中包含两条重复数据
				return "路由导入文件中包含两条重复数据，请核实。";
			else{
				//保存路由信息
				for(int i=0 ; i<routeInfos.size() ; i++){
					try{
						t11011BO.add(routeInfos.get(i));
						succ++;
					}catch(Exception e){
						fail++;
					}
				}
				return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)+"条数据";
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
			log("操作员编号：" + operator.getOprId()+ "，导入路由信息文件操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
}
