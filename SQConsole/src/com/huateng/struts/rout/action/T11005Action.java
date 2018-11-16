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

import com.huateng.bo.rout.T11015BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T11005Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private T11015BO t11015BO = (T11015BO)ContextUtil.getBean("T11015BO");
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
	 * 导入工行终端通道信息 
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
			
			boolean flag = false;//标志：工行终端通道导入文件中是否重复包含终端通道表中已存在的数据			
			boolean flag2 = false;//标志：工行终端通道导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：工行终端通道导入文件中分隔符是否正确
			boolean flag4 = false;//标志：工行终端通道导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：工行终端通道导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志：工行终端通道导入文件中是否包含不允许的特殊符号，例如单引号
			boolean flag7 = false;//标志：工行终端通道导入文件中是否包含不合规范的字段数据
			boolean flag8 = false;//标志：终端通道导入文件中交易金额下限是否大于上限
			boolean flag9 = false;//标志：终端通道导入文件中交易金额区间是否重叠
			String msg="";
			List<TblTermChannelInfTmp> termChanInfos = new ArrayList<TblTermChannelInfTmp>();
			
			while(reader.ready()){
				String tmp = reader.readLine();
				if(StringUtil.isEmpty(tmp))
					continue;
				String[] strs = tmp.trim().split(",");//去掉前后空格，再按逗号分割
				if(strs.length < 9 || strs.length > 9){//每行被逗号分割后不等于9列的情况不处理（新增交易金额上限、下限两个字段）
					flag3 = true;
					continue;
				}
				if(strs[0].trim().startsWith("商户MCC码")){//列名所在标题行不做处理
					flag4 = true;
					continue;
				}
				try{
					/*String termIns = strs[0];//所属机构号
					if( termIns.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					String termInsSql = "select count(*) from  TBL_AGENCY_INFO_TRUE where statue='1' " +
							"and substr(AGEN_ID,1,4) = '0102' and trim(AGEN_ID)='" + termIns.trim() + "'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(termInsSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}*/
					String wheresql = "SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"+AOwner+"' ";
			    	List list =   CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
					String termIns = (String) list.get(0);
					System.out.println("termIns"+termIns);
					//20121127添加对字段数据可用性的检验
					String mchtMcc = strs[0];//商户MCC码
					if( mchtMcc.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if(!mchtMcc.trim().equals("*")){//为*则不需验证
						String mchtMccSql = "select count(*) from tbl_inf_mchnt_tp WHERE trim(mchnt_tp) ='" + mchtMcc.trim() + "'";
						if( "0".equals(commQueryDAO.findCountBySQLQuery(mchtMccSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
							flag2 = true;
							continue;
						}
					}
					
					String mchtNo = strs[1];//商户号
					if( mchtNo.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if(!mchtNo.trim().equals("*")){//为*则不需验证
						String mchtNoSql = "select count(*) from TBL_MCHT_BASE_INF WHERE MCHT_STATUS = '0' and " +
								"MCHT_NO='" + mchtNo.trim() + "'";
						if(!mchtMcc.trim().equals("*")){//为*则不需验证
							mchtNoSql = mchtNoSql + " and MCC='" + mchtMcc.trim() + "'";
						}
						if( "0".equals(commQueryDAO.findCountBySQLQuery(mchtNoSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
							flag2 = true;
							continue;
						}
					}
					
					String mchtTermId = strs[2];//商户终端号
					if( mchtTermId.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if(!mchtTermId.trim().equals("*")){//为*则不需验证
						String mchtTermIdSql = "select count(*) from TBL_TERM_INF WHERE trim(TERM_ID)='" + mchtTermId.trim() + "'";
								
						if(!mchtNo.trim().equals("*")){//为*则不需验证
							mchtTermIdSql = mchtTermIdSql + " and MCHT_CD = '" + mchtNo.trim() + "'";
						}
						if( "0".equals(commQueryDAO.findCountBySQLQuery(mchtTermIdSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
							flag2 = true;
							continue;
						}
					}
					
					String insMcc = strs[3];//机构MCC码
					if( insMcc.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if(!insMcc.trim().equals("*")){//为*则不需验证
						String insMccSql = "select count(*) from tbl_inf_mchnt_tp where trim(mchnt_tp) ='" + insMcc.trim() + "'";
						if( "0".equals(commQueryDAO.findCountBySQLQuery(insMccSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
							flag2 = true;
							continue;
						}
					}
					
					String insMchnt = strs[4];//机构商户号
//					if( insMchnt.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
//						flag6 = true;
//						continue;
//					}
//					if( insMchnt.trim().length() > 15 ||  insMchnt.trim().length() == 0){
//						flag7 = true;
//						continue;
//					}
					
					String insTerm = strs[5];//机构终端号
//					if( insTerm.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
//						flag6 = true;
//						continue;
//					}
//					if( insTerm.trim().length() > 15 ||  insTerm.trim().length() == 0){
//						flag7 = true;
//						continue;
//					}
					
					String reserve = strs[6];//索引值
					if( reserve.trim().indexOf("'") != -1 ){//20121130，记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					/*if(!reserve.matches("(\\d{4})")){	//索引值为4位数字
						flag7 = true;
						continue;
					}*/
					String reserveSql = "select count(*) from CST_SYS_PARAM where OWNER = '"+AOwner+"Index' and KEY = '"+reserve+"'";
					if( "0".equals(commQueryDAO.findCountBySQLQuery(reserveSql)) ){//只要该字段数据没有匹配现有表中记录，即提示失败信息
						flag2 = true;
						continue;
					}
					
//					String channelType = strs[7];
					String minAmt = strs[7];
					String maxAmt = strs[8];
					if( minAmt.trim().indexOf("'") != -1 ){//记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					if( maxAmt.trim().indexOf("'") != -1 ){//记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					double minAmtD = Double.parseDouble(minAmt);
					double maxAmtD = Double.parseDouble(maxAmt);
					long minAmtL = (long) (minAmtD*100);
				    long maxAmtL = (long) (maxAmtD*100);
					if(minAmtL>maxAmtL){
						flag8=true;
						continue;
					}
					//根据工行终端通道信息去终端通道信息中验证数据唯一性
					//状态为已删除的记录不参与重复性判断
//					String sql = "select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='"+termIns+"' and mcht_mcc='"+mchtMcc
//						+"' and mcht_id='"+mchtNo+"' and mcht_term_id='"+mchtTermId+"' ";
//						//+ "and ins_mcc='"+insMcc +"' and ins_mcht='"+insMchnt + "' and ins_term='"+insTerm + "'";
					StringBuffer sqlBuffer=new StringBuffer();
					sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
						.append(termIns).append("' ")
						.append(" and mcht_mcc='").append(mchtMcc).append("' ")
						.append(" and mcht_id='").append(mchtNo).append("' ")
						.append(" and mcht_term_id='").append(mchtTermId).append("' ")
						.append(" and INS_MCC='").append(insMcc).append("' ")
						.append(" and INS_MCHT='").append(insMchnt).append("' ")
						.append(" and INS_TERM='").append(insTerm).append("' ");
					String cnt = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
					if(!"0".equals(cnt)){//只要文件中有一条数据重复，即提示失败信息
						flag = true;
						continue;
					}else{
						sqlBuffer.setLength(0);
						sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
							.append(termIns).append("' ")
							.append(" and mcht_mcc='").append(mchtMcc).append("' ")
							.append(" and mcht_id='").append(mchtNo).append("' ")
							.append(" and mcht_term_id='").append(mchtTermId).append("' ")
							.append(" and ((").append(minAmtL).append("<=min_amt and min_amt<=").append(maxAmtL).append(") or (")
							.append(minAmtL).append("<=max_amt and max_amt<=").append(maxAmtL).append(") or (min_amt<=")
							.append(minAmtL).append(" and ").append(maxAmtL).append("<=max_amt))");
						String count = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
						if(!"0".equals(count)){//只要文件中有一条数据重复，即提示失败信息
							msg="商户MCC码:"+mchtMcc+",商户号:"+mchtNo+",商户终端号:"+mchtTermId+",交易金额下限:"+minAmt+",交易金额上限:"+maxAmtL+"";
							flag9 = true;
							continue;
						}
					}
					
					
					
					TblTermChannelInfTmp termChanTemp = new TblTermChannelInfTmp();
					termChanTemp.setId(StringUtil.getUUID());
			 		termChanTemp.setTermIns(termIns);
			 		termChanTemp.setMchtMcc(mchtMcc);
			 		termChanTemp.setMchtId(mchtNo);
			 		termChanTemp.setMchtTermId(mchtTermId);
			 		termChanTemp.setInsMcc(insMcc);
			 		termChanTemp.setInsMcht(insMchnt);
			 		termChanTemp.setInsTerm(insTerm);
//			 		termChanTemp.setChannelType(channelType);
			 		//termChanTemp.setLmk(lmk);//主密钥
			 		//termChanTemp.setReserve02(lmk);
			 		
			 		//modify by zxc 20130510 转换成16进制存入数据库 start 
			 		termChanTemp.setReserve01(DecToHex(reserve));//索引值
			 		//modify by zxc 20130510 转换成16进制存入数据库 end
			 		
			 		//新增交易金额上限，下限
			 		termChanTemp.setMinAmt(minAmtL);
			 		termChanTemp.setMaxAmt(maxAmtL);
			 		
					termChanTemp.setStat(ADD_TO_CHECK);
					termChanTemp.setTermInsOld(termIns);
			 		termChanTemp.setMchtMccOld(mchtMcc);
			 		termChanTemp.setMchtIdOld(mchtNo);
			 		termChanTemp.setMchtTermIdOld(mchtTermId);
			 		termChanTemp.setInsMccOld(insMcc);
			 		termChanTemp.setInsMchtOld(insMchnt);
			 		termChanTemp.setInsTermOld(insTerm);
			 		termChanTemp.setCreTime(CommonFunction.getCurrentDateTime());
			 		termChanTemp.setCreOprId(operator.getOprId());
			 		termChanTemp.setModiOprId(operator.getOprId());
			 		termChanTemp.setModiTime(CommonFunction.getCurrentDateTime());
					
					//判断集合中是否已包含相同内容的记录20121129
					if(termChanInfos!=null && termChanInfos.size()>0){
						for(int i=0 ; i<termChanInfos.size() ; i++){
							TblTermChannelInfTmp temp = termChanInfos.get(i);
							if(termIns.equals(temp.getTermIns()) && mchtMcc.equals(temp.getMchtMcc()) 
									&& mchtNo.equals(temp.getMchtId()) && mchtTermId.equals(temp.getMchtTermId()) 
									&& insMcc.equals(temp.getInsMcc()) && insMchnt.equals(temp.getInsMcht()) 
									&& insTerm.equals(temp.getInsTerm())){//只要文件中有两条数据重复，即提示失败信息
								flag5 = true;
								continue;
							}
						}
					}
					//将工行终端通道信息保存到集合中
					termChanInfos.add(termChanTemp);					
				}catch(Exception e){
					continue;
				}
				System.out.println(tmp);
			}
			reader.close();
			
			if(!flag4)//工行终端通道导入文件中不包含标题行
				return "终端通道导入文件中没有标题行，请核实。";
			if(flag3)//工行终端通道导入文件中分隔符不正确
				return "终端通道导入文件中分隔符不正确，请核实。";
			if(flag6)//工行终端通道导入文件中的字段包含不允许的特殊符号,例如单引号
				return "终端通道导入文件中包含不允许的特殊符号:单引号，请核实。";
			if(flag2)//工行终端通道导入文件中包含不存在的字段数据
				return "终端通道导入文件中包含不存在的字段数据，请核实。";
			if(flag)//工行终端通道导入文件中重复包含终端通道表中已存在的数据
				return "终端通道导入文件中重复包含终端通道表中已存在的数据，请核实。";
			if(flag5)//工行终端通道导入文件中包含两条重复数据
				return "终端通道导入文件中包含两条重复数据，请核实。";
			if(flag7)//工行终端通道导入文件中是否包含不合规范的字段数据
				return "终端通道导入文件中包含不合规范的字段数据，请核实。";
			if(flag8)//
				return "终端通道导入文件中交易金额下限大于上限，请核实";
			if(flag9)//
				return "终端通道导入文件中交易金额区间和终端通道表中重叠，请核实（"+msg+"）";
			else{
				//保存工行终端通道信息
				for(int i=0 ; i<termChanInfos.size() ; i++){
					try{
						t11015BO.add(termChanInfos.get(i));
						succ++;
					}catch(Exception e){
						fail++;
					}
				}
				return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)+"条数据";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入终端通道信息文件失败";
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
	/**
	 * 10进制索引值转成16进制保存到DB
	 * @param s
	 * @return
	 * 2013-5-10 上午11:51:03
	 * @author zhao_xingcai
	 */
	public String DecToHex(String s) {
		Integer i1 = 0;
	    Integer i2 = 0;
	    String s1;
	    String s2;
	    
		if(s.length() == 1 ) {
			i1 = Integer.valueOf(s);
		}else if(s.length() == 2) {
			i1 = Integer.valueOf(s.substring(0, 2));
		}else if(s.length() == 3) {
			i1 = Integer.valueOf(s.substring(0, 1));
			i2 = Integer.valueOf(s.substring(1, 3));
		}else if(s.length() == 4) {
			i1 = Integer.valueOf(s.substring(0, 2));
			i2 = Integer.valueOf(s.substring(2, 4));
		}
		
		s1 = String.valueOf(Integer.toHexString(i1));
		s2 = String.valueOf(Integer.toHexString(i2));
		
		//补足位数
		if(s1.length() < 2) {
			s1 = "0" + s1;
		}
		if(s2.length() < 2) {
			s2 = "0" + s2;
		}
		return s1+s2;
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
