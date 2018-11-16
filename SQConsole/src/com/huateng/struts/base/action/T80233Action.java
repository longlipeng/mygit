package com.huateng.struts.base.action;

import java.io.File;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.SysParamUtil;
import com.huateng.system.util.TarBuilder;
import com.huateng.system.util.TestDES;
import com.jcraft.jsch.ChannelSftp;

import tools.Read_excel;
import tools.SFTPClientProvider;

/**
 * 银企直连文件上传至文件服务器
 * 
 * @author Li
 *
 */
public class T80233Action extends BaseAction {
	private static Logger log = Logger.getLogger(T80233Action.class);

	private static final long serialVersionUID = 1L;

	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if ("uploadfile".equals(getMethod())) {
				rspCode = uploadfile();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId() + "，上传数据至文件服务器" + getMethod() + "失败，失败原因为：" + e.getMessage());
		}
		return rspCode;
	}

	private String uploadfile() {
		
		System.out.println("上传文件的日期：" + date );
		String fileDate = date.substring(0, 6);//存放上传文件的的日期文件夹
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD);//存放上传文件的总目录
		String basePath1 = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1);//需要上传的TXT文件存放位置
		
//		Read_excel read_excel = new Read_excel();
		basePath += fileDate;//存放上传文件的位置
		basePath1 += date;//存放上传TXT文件的路径
		System.out.println("存放上传文件的位置：" + basePath);
		File file = new File(basePath);
		File file1 = new File(basePath1);
		
		String str1 = "1支付机构客户备付金信息统计报表";
		//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号  + 交易日期
		String data1 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE);
		String name1 = SysParamUtil.getParam(SysParamConstants.PMS_101_CMB100);
		String fixed = SysParamUtil.getParam(SysParamConstants.FIXED);
		
		String str2 = "2支付机构客户备付金出金业务明细表";
		//由银行提供支付机构代码  + 交易日期
		String data2 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|" + date + "|";
		String name2 = SysParamUtil.getParam(SysParamConstants.PMS_102_CMB100);
		
		String str3 = "3支付机构客户备付金业务实际出金明细表";
		//由银行提供支付机构代码  + 交易日期 + 银行名称  + 账户户名 + 账户账号
		String data3 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|" + date + "|";
		String name3 = SysParamUtil.getParam(SysParamConstants.PMS_103_CMB100);
		
		String str4 = "4支付机构客户资金账户转账业务统计表";
		//由银行提供支付机构代码  + 交易日期
		String data4 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|" + date + "|";
		String name4 = SysParamUtil.getParam(SysParamConstants.PMS_104_CMB100);
		
		String str5 = "5支付机构客户资金账户余额统计表";
		//由银行提供支付机构代码  + 交易日期
		String data5 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|" + date + "|";
		String name5 = SysParamUtil.getParam(SysParamConstants.PMS_105_CMB100);
		
		String str6 = "6支付机构招商银行特殊业务明细表";
		//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
		String data6 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name6 = SysParamUtil.getParam(SysParamConstants.PMS_106_CMB100);
		
		String str7 = "7支付机构现金购卡业务统计表";
		//由银行提供支付机构代码 + 交易日期
		String data7 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name7 = SysParamUtil.getParam(SysParamConstants.PMS_107_CMB100);
		
		String str8 = "8支付机构预付卡现金赎回业务统计表";
		//由银行提供支付机构代码 + 交易日期
		String data8 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name8 = SysParamUtil.getParam(SysParamConstants.PMS_108_CMB100);
		
		String str9 = "9支付机构招商银行客户备付金业务未达账项统计表";
		//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
		String data9 =  SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name9 = SysParamUtil.getParam(SysParamConstants.PMS_109_CMB100);
		
		String str10 = "10支付机构招商银行客户备付金业务未达账项分析表";
		//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
		String data10 =  SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name10 = SysParamUtil.getParam(SysParamConstants.PMS_110_CMB100);
		
		String str11 = "11支付机构客户资金账户余额变动调节表";
		//由银行提供支付机构代码 + 交易日期
		String data11 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name11 = SysParamUtil.getParam(SysParamConstants.PMS_111_CMB100);
		
		String str12 = "12支付机构客户资金账户余额试算表";
		//由银行提供支付机构代码 + 交易日期
		String data12 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name12 = SysParamUtil.getParam(SysParamConstants.PMS_112_CMB100);
		
		String str13 = "13预付卡发行企业备付金账户中售卡押金统计表";
		String data13 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name13 = SysParamUtil.getParam(SysParamConstants.PMS_113_CMB100);
		
		String str14 = "15银行客户备付金入金业务调节表";
		String data14 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name14 = SysParamUtil.getParam(SysParamConstants.PMS_114_CMB100);
		
		String str15 = "16备付金新增分银行账户的表1-2";
		String data15 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name15 = SysParamUtil.getParam(SysParamConstants.PMS_115_CMB100);
		
		String str16 = "17备付金新增支付机构管理账户情况统计";
		String data16 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name16 = SysParamUtil.getParam(SysParamConstants.PMS_116_CMB100);
		
		String str17 = "18备付金新增预付卡商户白名单表_"+ date.substring(0, 8);
		String data17 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name17 = SysParamUtil.getParam(SysParamConstants.PMS_117_CMB100);
		
		String str18 = "19支付机构合作行每日账户余额列表";
		String data18 = SysParamUtil.getParam(SysParamConstants.COMPANY_CODE) + "|";
		String name18 = SysParamUtil.getParam(SysParamConstants.PMS_118_CMB100);
		
		System.out.println("====================================="+"生成TXT文件"+"=====================================");
		String namePath = basePath1 + "/";
		File namePathFile = new File(namePath);
		if (file.exists()) {//判断目录是否存在
			File[] files = file.listFiles();
			if (files.length == 0) {
				return "目录中没有文件！";
			}else{
				deleteDir(namePathFile);
			}
			
			for (File file2 : files) {//创建TXT文件
				String name = file2.getName();//获取到的文件的文件名
				String absolutePath = file2.getAbsolutePath();
				if (name.substring(0, str1.length()).equals(str1)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_1 = Read_excel.readExcel_1_1(data1, absolutePath, namePath, name1, date, fixed);
					if (readExcel_1_1) {
//						System.out.println("生成第一份TXT文件");
						log.info("生成第一份TXT文件");
					}
				}else if (name.substring(0, str2.length()).equals(str2)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_2 = Read_excel.readExcel_1_2(data2, absolutePath, namePath, name2, date, fixed);
					if (readExcel_1_2) {
						log.info("生成第二份TXT文件");
					}
				}else if (name.substring(0, str3.length()).equals(str3)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_3 = Read_excel.readExcel_1_3(data3, absolutePath, namePath, name3, date, fixed);
					if (readExcel_1_3) {
						log.info("生成第三份TXT文件");
					}
				}else if (name.substring(0, str4.length()).equals(str4)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_4 = Read_excel.readExcel_1_4(data4, absolutePath, namePath, name4, date, fixed);
					if (readExcel_1_4) {
						log.info("生成第四份TXT文件");
					}
				}else if (name.substring(0, str5.length()).equals(str5)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_5 = Read_excel.readExcel_1_5(data5, absolutePath, namePath, name5, date, fixed);
					if (readExcel_1_5) {
						log.info("生成第五份TXT文件");
					}
				}else if (name.substring(0, str6.length()).equals(str6)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_6 = Read_excel.readExcel_1_6(data6, absolutePath, namePath, name6, date, fixed);
					if (readExcel_1_6) {
						log.info("生成第六份TXT文件");
					}
				}else if (name.substring(0, str7.length()).equals(str7)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_7 = Read_excel.readExcel_1_7(data7, absolutePath, namePath, name7, date, fixed);
					if (readExcel_1_7) {
						log.info("生成第七份TXT文件");
					}
				}else if (name.substring(0, str8.length()).equals(str8)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_8 = Read_excel.readExcel_1_8(data8, absolutePath, namePath, name8, date, fixed);
					if (readExcel_1_8) {
						log.info("生成第八份TXT文件");
					}
				}else if (name.substring(0, str9.length()).equals(str9)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_9 = Read_excel.readExcel_1_9(data9, absolutePath, namePath, name9, date, fixed);
					if (readExcel_1_9) {
						log.info("生成第九份TXT文件");
					}
				}else if (name.substring(0, str10.length()).equals(str10)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_10 = Read_excel.readExcel_1_10(data10, absolutePath, namePath, name10, date, fixed);
					if (readExcel_1_10) {
						log.info("生成第十份TXT文件");
					}
				}else if (name.substring(0, str11.length()).equals(str11)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_11 = Read_excel.readExcel_1_11(data11, absolutePath, namePath, name11, date, fixed);
					if (readExcel_1_11) {
						log.info("生成第十一份TXT文件");
					}
				}else if (name.substring(0, str12.length()).equals(str12)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_12 = Read_excel.readExcel_1_12(data12, absolutePath, namePath, name12, date, fixed);
					if (readExcel_1_12) {
						log.info("生成第十二份TXT文件");
					}
				}else if (name.substring(0, str13.length()).equals(str13)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_13 = Read_excel.readExcel_1_13(data13, absolutePath, namePath, name13, date, fixed);
					if (readExcel_1_13) {
						log.info("生成第十三份TXT文件");
					}
				}else if (name.substring(0, str14.length()).equals(str14)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_14 = Read_excel.readExcel_1_14(data14, absolutePath, namePath, name14, date, fixed);
					if (readExcel_1_14) {
						log.info("生成第十四份TXT文件");
					}
				}else if (name.substring(0, str15.length()).equals(str15)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_15 = Read_excel.readExcel_1_15(data15, absolutePath, namePath, name15, date, fixed);
					if (readExcel_1_15) {
						log.info("生成第十五份TXT文件");
					}
				}else if (name.substring(0, str16.length()).equals(str16)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_16 = Read_excel.readExcel_1_16(data16, absolutePath, namePath, name16, date, fixed);
					if (readExcel_1_16) {
						log.info("生成第十六份TXT文件");
					}
				}else if (name.substring(0, str17.length()).equals(str17)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_17 = Read_excel.readExcel_1_17(data17, absolutePath, namePath, name17, date, fixed);
					if (readExcel_1_17) {
						log.info("生成第十七份TXT文件");
					}
				}else if (name.substring(0, str18.length()).equals(str18)) {
					if (!namePathFile.isDirectory()) {
						namePathFile.mkdirs();
					}
					boolean readExcel_1_18 = Read_excel.readExcel_1_18(data18, absolutePath, namePath, name18, date, fixed);
					if (readExcel_1_18) {
						log.info("生成第十八份TXT文件");
					}
				}
			}
		} else {
			return "目录不存在！";
		}
		System.out.println("====================================="+"进行数据加密"+"=====================================");
		String deskey = SysParamUtil.getParam("DESKEY");
		TestDES des = new TestDES(deskey);
		String basePath2 = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD2);//存放加密文件的目录
		basePath2 += date + "/";
		File basePath2File = new File(basePath2);
		if (file1.exists()) {
			File[] files = file1.listFiles();
//			if (files.length == 13) {
				for (File file2 : files) {
					try {
//						String absolutePath = file2.getAbsolutePath();
						String replaceAll = file2.getName();
						String destFile = basePath2 + replaceAll;
						System.out.println("==========================");
						System.out.println("destFile" + destFile);
						System.out.println("==========================");
						File destFile1 =new File(basePath1);  
						//如果文件夹不存在则创建  
						if  (!basePath2File.exists()  && ! basePath2File.isDirectory()) {     
						    System.out.println("//不存在    "+basePath2File);
						    basePath2File.mkdirs();  
						}
						System.out.println("加密的文件："+ destFile);
						des.encrypt(file2.getAbsolutePath(), destFile);
						System.out.println("==================");
						System.out.println("！文件加密成功！");
					} catch (Exception e) {
						log("加密失败" + e.getMessage());
						e.printStackTrace();
					}
//				}
//			}else {
					if (files.length < 18) {
						System.out.println("上传备付金文件不足13份，一共"+files.length);
						return "上传文件不足18份，请检查后再上传！";
					}
			}
		}else {
			return "上传文件不存在请联系管理员！";
		}
		System.out.println("====================================="+"进行数据上传"+"=====================================");
		String yqftpserverip = SysParamUtil.getParam(SysParamConstants.YQFTPSERVERIP);
		String yqftpname = SysParamUtil.getParam(SysParamConstants.YQFTPNAME);
		String yqftppwd = SysParamUtil.getParam(SysParamConstants.YQFTPPWD);
		try {
			System.out.println("==================");
			System.out.println("要压缩的目录："+basePath2 + "	输出目录：" + SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD2));
			System.out.println("tar包的名称："+ "YQ_"+date+".tar");
			TarBuilder tarBuilder = new TarBuilder(basePath2, SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD2), "YQ_"+date+".tar", false);
			tarBuilder.build();
			System.out.println("打包成功！");
		} catch (Exception e) {
			log("加密文件打包失败：" + e.getMessage());
			return "加密文件打包失败!";
		}
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = null;
		try {
			/*FtpUtil ftpUtil = new FtpUtil(yqftpserverip, yqftpname, yqftppwd);
			ftpUtil.connectServer();
			ftpUtil.upload(SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD2)+"YQ_"+date+".tar", SysParamUtil.getParam(SysParamConstants.YQFTPPATH)+"YQ_"+date+".tar");
			ftpUtil.closeServer();
			System.out.println("连接的IP："+yqftpserverip);
			System.out.println("连接的端口："+Integer.parseInt(SysParamUtil.getParam("FTPPORT")));
			System.out.println("连接的用户名："+yqftpname);
			System.out.println("连接的密码："+yqftppwd);*/
			sftp = provider.connect(yqftpserverip, Integer.parseInt(SysParamUtil.getParam("FTPPORT")), yqftpname, yqftppwd);
			String yqftppath = SysParamUtil.getParam(SysParamConstants.YQFTPPATH);
			String upFile = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD2)+"YQ_"+date+".tar";
			try {
				boolean delete = provider.delete(yqftppath, "YQ_"+date+".tar", sftp);
				if (delete) {
					System.out.println("删除文件服务器文件失败！");
				}
				System.out.println("删除文件服务器文件成功！");
			} catch (Exception e) {
				log("文件服务器文件删除："+e.getMessage());
			}
			System.out.println("上传的目录: "+ yqftppath);
			System.out.println(" 要上传的文件: " + upFile);
			boolean flag = provider.upload( yqftppath, upFile, sftp);
			System.out.println("上传是否："+flag);
			if (!flag) {
				return "上传失败！";
			}
		} catch (Exception e) {
			log(e.getMessage());
			return "上传文件失败！";
		} finally {
			provider.disconnect(sftp);
		}
		return Constants.SUCCESS_CODE;
		/*System.out.println("日期："+date);
		String deskey = SysParamUtil.getParam("DESKEY");
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD);
		String basePath1 = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1);
		basePath += date.substring(0, 6);
		basePath += "/";
		basePath = basePath.replace("\\", "/");
		basePath1 += date.substring(0, 6);
		basePath1 += "/";
		basePath1 = basePath1.replace("\\", "/");
		TestDES des = new TestDES(deskey);
		File file = new File(basePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return "文件夹是空的!";
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						
					} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						try {
							System.out.println("需要加密的文件："+file2.getAbsolutePath());
							String destFile = basePath1 + replaceAll;
							File destFile1 =new File(basePath1);  
							//如果文件夹不存在则创建  
							if  (!destFile1.exists()  && ! destFile1.isDirectory()) {     
							    System.out.println("//不存在    "+destFile);
							    destFile1.mkdir();  
							}
							System.out.println("加密的文件："+ destFile);
							des.encrypt(file2.getAbsolutePath(), destFile);
							System.out.println("==================");
							System.out.println("！文件加密成功！");
						} catch (Exception e) {
							e.printStackTrace();
							log("文件加密失败！"+e.getMessage());
							return "文件加密失败！";
						}
					}
				}
			}
		} else {
			log("文件不存在!");
			return "文件不存在";
		}
		String yqftpserverip = SysParamUtil.getParam(SysParamConstants.YQFTPSERVERIP);
		String yqftpname = SysParamUtil.getParam(SysParamConstants.YQFTPNAME);
		String yqftppwd = SysParamUtil.getParam(SysParamConstants.YQFTPPWD);
		try {
			System.out.println("==================");
			System.out.println("要压缩的目录："+basePath1 + "	输出目录：" + SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1));
			System.out.println("tar包的名称："+ "YQ_"+date+".tar");
			TarBuilder tarBuilder = new TarBuilder(basePath1, SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1), "YQ_"+date+".tar", false);
			tarBuilder.build();
			System.out.println("打包成功！");
		} catch (Exception e) {
			log("加密文件打包失败：" + e.getMessage());
			return "加密文件打包失败!";
		}
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = null;
		try {
			FtpUtil ftpUtil = new FtpUtil(yqftpserverip, yqftpname, yqftppwd);
			ftpUtil.connectServer();
			ftpUtil.upload(SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1)+"YQ_"+date+".tar", SysParamUtil.getParam(SysParamConstants.YQFTPPATH)+"YQ_"+date+".tar");
			ftpUtil.closeServer();
			System.out.println("连接的IP："+yqftpserverip);
			System.out.println("连接的端口："+Integer.parseInt(SysParamUtil.getParam("FTPPORT")));
			System.out.println("连接的用户名："+yqftpname);
			System.out.println("连接的密码："+yqftppwd);
			sftp = provider.connect(yqftpserverip, Integer.parseInt(SysParamUtil.getParam("FTPPORT")), yqftpname, yqftppwd);
			String yqftppath = SysParamUtil.getParam(SysParamConstants.YQFTPPATH);
			String upFile = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1)+"YQ_"+date+".tar";
			try {
				boolean delete = provider.delete(yqftppath, "YQ_"+date+".tar", sftp);
				if (delete) {
					System.out.println("删除文件服务器文件失败！");
				}
				System.out.println("删除文件服务器文件成功！");
			} catch (Exception e) {
				log("文件服务器文件删除："+e.getMessage());
			}
			System.out.println("上传的目录: "+ yqftppath);
			System.out.println(" 要上传的文件: " + upFile);
			boolean flag = provider.upload( yqftppath, upFile, sftp);
			System.out.println("上传是否："+flag);
			if (!flag) {
				return "上传失败！";
			}
		} catch (Exception e) {
			log(e.getMessage());
			return "上传文件失败！";
		} finally {
			provider.disconnect(sftp);
		}
		return Constants.SUCCESS_CODE;*/
	}

}
