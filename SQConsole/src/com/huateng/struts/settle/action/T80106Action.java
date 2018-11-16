/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-31       first release
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.impl.settle.TblSettleService;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.po.settle.TblInfileOpt;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;
import com.jcraft.jsch.ChannelSftp;

import tools.SFTPClientProvider;

/**
 * Title: 商户入账文件下载
 * 
 * File: T80205.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80106Action extends BaseSupport{
	
	private static final long serialVersionUID = 1L;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private String date;
	private String channel;

	protected TblSettleService service = (TblSettleService) ContextUtil.getBean("TblSettleService");
		

	
	/**
	 * 对账文件上传	
	 * 由于swf没有上传文件的本地路径，故先将本地文件上传的web服务器，然后在从web服务器ftp到 对应的服务器
	 * @return
	 */
	public String upload(){
		FileInputStream is = null;
		DataOutputStream out = null;
		DataInputStream dis = null;
		String errflag = "";
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
		try {
			String fileName = "";
			int fileNameIndex = 0;
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_UPLOADJIGOU);
//			String basePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
			String ftpPath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_UPLOADACCOUNT);
			/*basePath += agenid;
			basePath += "/";*/
			
//			basePath += channel;
//			basePath += "/";
			basePath += date;
			basePath += "/";
			
			basePath = basePath.replace("\\", "/");
//			ftpPath += channel+"/";
			ftpPath += date+"/";
			ftpPath = ftpPath.replace("\\", "/");
			//创建FTP连接
//			SFTPClientProvider provider = new SFTPClientProvider();
//			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
//			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"),SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
//			ftp.connectServer();
			for(File file : files) {
				fileName = filesFileName.get(fileNameIndex);
				log("WRITE FILE:" + basePath + fileName);
//				FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"),SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
//				ftp.connectServer();
					fileName = filesFileName.get(fileNameIndex);
					log("WRITE FILE:" + basePath + fileName);
					System.out.println("WRITE FILE:" + basePath + fileName);
					System.out.println("file----:" + file);
					System.out.println("WRITE files:" +  files.toString());
					boolean flag = provider.upload( basePath, file, sftp);
					System.out.println(flag);
				/*is = new FileInputStream(file);
				fileName = filesFileName.get(fileNameIndex);
				//由于银联的对账文件名称中的日期年份为2位数，故截取yyyyMMdd日期为yyMMdd再做比较-modify at 20131031
				String newDate = "";
				if (StringUtils.length(date) >=8) {
					newDate = date.substring(3);
				} else {
					newDate = date;
				}
				if(fileName.indexOf(newDate) < 0)
				//if(fileName.indexOf(date) < 0) 
					return returnService("上传的对账文件与选择的日期不符，请重新上传！");
				log("WRITE FILE:" + basePath + fileName);
				File writeFile = new File(basePath + fileName);
				
				System.out.println("**********上传进入的本机临时文件目录是否存在：" + writeFile.getParentFile().exists());//20121105
				if (!writeFile.getParentFile().exists()) {
					writeFile.getParentFile().mkdirs();
				}
				System.out.println("**********上传进入的临时文件是否存在：" + writeFile.exists());//20121105
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
				try {
					Runtime.getRuntime().exec("chmod 777 " + basePath + fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ftp.upload(writeFile.getPath(),ftpPath+fileName);
			}
			ftp.closeServer();
			}	
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return returnService("写入文件失败(没有权限将文件写入指定目录)！", e);*/
		}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(e.getMessage());
		} finally {
			provider.disconnect(sftp);
			
		}
		return returnService(Constants.SUCCESS_CODE);
//			try {
//				if (null != out) {
//					out.close();
//				}
//				if (null != is) {
//					is.close();
//				}
//				if (null != dis) {
//					dis.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if (!StringUtil.isNull(errflag)) {
//			return returnService(errflag);
//		} else {
//			return returnService(Constants.SUCCESS_CODE);
//		}
	}

	public String exec(){
		try {
			
			TblInfileOpt inf = service.getInf(getOperator().getOprBrhId(), CommonFunction.getCurrentDate());
			if (null != inf) {
				inf.setOprId2(getOperator().getOprId());
				inf.setRecUpdTime(CommonFunction.getCurrentDateTime());
				service.update(inf);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
//			交易码
			StringBuffer req = new StringBuffer("8221062");
			req.append(CommonFunction.fillString(CommonFunction.getCurrentDate() + getOperator().getOprBrhId(), ' ', 62, true));
			String len = Integer.toHexString(req.toString().length());
			len = new String(CommonFunction.hexStringToByte(len));
			len = new String(CommonFunction.hexStringToByte("00"))+len;
			
			SocketConnect socket = null;
			try {
				log.info("报文:[" + len + req.toString() + "]");
				socket = new SocketConnect(len + req.toString());
				socket.run(Charset.defaultCharset().toString());
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals("00"))
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "解析代发文件结果处理成功，请稍后查看状态！");
				else
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "解析代发文件结果处理失败，返回码:" + resp.substring(4,6));
			} catch (UnknownHostException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			} finally {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnService("解析代发文件结果处理失败，请稍后重试！");
	}
	
	/**
	 * 重置商户入账
	 * 
	 * @return
	 * 2011-11-16上午10:45:24
	 */
	public String reset(){
		try {
			
			//判断柜员是否为总行柜员
			String oprBrh = getOperator().getOprBrhId();
			if (!"9900".equals(oprBrh)) {
				return returnService("重置商户入账只能由总行柜员操作！");
			}
			
			//判断是否有该机构的代发记录
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			TblSettleService service = (TblSettleService) ContextUtil
				.getBean("TblSettleService");
			TblInfileOpt inf = service.getInf(brhId, today);
			
			if (null == inf) {
				return returnService("未找到该机构当天的代发记录,请刷新后再试！");
			}
			
			rspCode = service.reset(inf, brhId, today);

			return returnService(rspCode);
			
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("重置状态发生异常，请稍后再试。", e);
		}
	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
	private List<File> files;
	
	private List<String> filesFileName;
	
	private String brhId;

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
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	private static Logger log = Logger.getLogger(T80106Action.class);
	
}
