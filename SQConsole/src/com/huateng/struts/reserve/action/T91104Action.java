package com.huateng.struts.reserve.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.huateng.bo.reserve.T91101BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T91104Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	T91101BO t91101BO = (T91101BO) ContextUtil.getBean("T91101BO");
	
	private List<File> files;
	private List<String> filesFileName;

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
	
	/**
	 * 账户白名单文件导入
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
			//用于读取数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(uploadFile), "GBK"));
			
			int succ = 0;
			int fail = 0;
			
			boolean flag1 = false;//标志：导入文件中是否重复包含 表中已存在的数据			
	//		boolean flag2 = false;//标志：导入文件中是否包含不存在的字段数据			
			boolean flag3 = false;//标志：导入文件中分隔符是否正确
			boolean flag4 = false;//标志：导入文件中第一行是否是标题内容，目前无法判断是否为第一行，只能判断是否包含标题行
			boolean flag5 = false;//标志：导入文件中是否包含相同内容的记录
			boolean flag6 = false;//标志： 导入文件中是否包含不允许的特殊符号，例如单引号
			
			List<TblSettleRosterInfTmp> rosterInfTmp = new ArrayList<TblSettleRosterInfTmp>();
			List<TblSettleRosterInf> rosterInf = new ArrayList<TblSettleRosterInf>();
			
			while (reader.ready()){
				
				try {
					//执行读取操作
					String tmp = reader.readLine();
					
					if(StringUtil.isEmpty(tmp))
						continue;
					
					String[] strs = tmp.trim().split(",");//去掉前后空格，再按逗号分割
					
					String rosterBankCard = strs[0];//收款方开户行行号
					if(strs[0].trim().startsWith("账户白名单导入")){//列名所在标题行不做处理
						flag4 = true;
						continue;
					}
					
					if(strs.length < 3 || strs.length > 3){//每行被逗号分割后不等于3列的情况不处理
						flag3 = true;
						continue;
					}
					
					String rosterAccount = strs[1];//收款方账号
					if(rosterAccount.trim().indexOf("'")!=-1){//记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					
					String rosterAccountName = strs[2];//收款方账户名称
					if(rosterAccountName.trim().indexOf("'")!=-1){//记录的字段中如果包含单引号,则提示失败信息
						flag6 = true;
						continue;
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					
					Random random = new Random();
					String rosterNo = sdf.format(date) + random.nextInt(9999);
					
					
					//临
					TblSettleRosterInfTmp tblSettleRosterInfTmp = new TblSettleRosterInfTmp();
					tblSettleRosterInfTmp.setRosterId(rosterNo);
					tblSettleRosterInfTmp.setRosterBankCard(rosterBankCard);
					tblSettleRosterInfTmp.setRosterAccount(rosterAccount);
					tblSettleRosterInfTmp.setRosterAccountName(rosterAccountName);
					tblSettleRosterInfTmp.setRosterStatus("0");//0正常
					
					rosterInfTmp.add(tblSettleRosterInfTmp);
					//正
					TblSettleRosterInf tblSettleRosterInf = new TblSettleRosterInf();
					tblSettleRosterInf.setRosterId(rosterNo);
					tblSettleRosterInf.setRosterBankCard(rosterBankCard);
					tblSettleRosterInf.setRosterAccount(rosterAccount);
					tblSettleRosterInf.setRosterAccountName(rosterAccountName);
					tblSettleRosterInf.setRosterStatus("0");//0正常
					
					rosterInf.add(tblSettleRosterInf);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					continue;
				}
			}
			reader.close();
			
			if(!flag4)// 导入文件中不包含标题行
				return " 导入文件中没有标题行，请核实。";
			if(flag3)// 导入文件中分隔符不正确
				return " 导入文件中分隔符不正确，请核实。";
			if(flag6)// 导入文件中的字段包含不允许的特殊符号,例如单引号
				return " 导入文件中包含不允许的特殊符号:单引号，请核实。";
			if(flag1)// 导入文件中重复包含 表中已存在的数据
				return " 导入文件中重复包含 表中已存在的数据，请核实。";
			if(flag5)// 导入文件中包含两条重复数据
				return " 导入文件中包含两条重复数据，请核实。";
			else{
				//保存信息
				for (int i = 0; i < rosterInfTmp.size(); i++) {
					try {
						t91101BO.addRoster(rosterInfTmp.get(i));
						t91101BO.addRosterInf(rosterInf.get(i));
						succ++;
					} catch (Exception e) {
						// TODO: handle exception
						fail++;
					}
				}
				return Constants.SUCCESS_CODE_CUSTOMIZE + "成功导入：" + String.valueOf(succ) + "条数据,失败：" + String.valueOf(fail)+"条数据";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
	
	
	
}
