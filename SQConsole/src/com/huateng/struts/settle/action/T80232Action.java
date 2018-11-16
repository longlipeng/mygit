package com.huateng.struts.settle.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.impl.settle.TblSettleService;
import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import tools.SFTPClientProvider;

/**
 * 银企直连文件上传
 * 
 * @author Li
 *
 */
public class T80232Action extends BaseSupport {

	private static final long serialVersionUID = 1L;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private String date;
	
	protected TblSettleService service = (TblSettleService) ContextUtil.getBean("TblSettleService");
	
	/**
	 * 对账文件上传 由于swf没有上传文件的本地路径，故先将本地文件上传的web服务器，然后在从web服务器ftp到 对应的服务器
	 * 
	 * @return
	 */
	public String upload() {
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("YINQI_NAME"), SysParamUtil.getParam("YINQI_PWD"));
		try {
			String fileName = "";
			int fileNameIndex = 0;
			String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD);
			basePath += date.substring(0, 6);
			basePath += "/";
			basePath = basePath.replace("\\", "/");
			try{
				sftp.cd(basePath);
			}catch(SftpException sException){
			    if(sftp.SSH_FX_NO_SUCH_FILE == sException.id){
			    	sftp.mkdir(basePath);
			    }
			}
			
			for (File file : files) {
				fileName = filesFileName.get(fileNameIndex);
				log("WRITE FILE:" + basePath + fileName);
				fileName = filesFileName.get(fileNameIndex);
				log("WRITE FILE:" + basePath + fileName);
				System.out.println("WRITE FILE:" + basePath + fileName);
				System.out.println("file----:" + file);
				System.out.println("WRITE files:" + files.toString());
				boolean flag = provider.upload(basePath, file, sftp);
				if (!flag) {
					return "上传失败！";
				}
				String oldNameFile = file.toString();
				int indexOf = oldNameFile.indexOf("upload_");
				oldNameFile = oldNameFile.substring(indexOf, oldNameFile.length());
				System.out.println("当前目录："+basePath);
				System.out.println("旧文件名："+oldNameFile);
				System.out.println("新文件名："+fileName);
				provider.rename(basePath, oldNameFile, fileName, sftp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(e.getMessage());
		} finally {
			provider.disconnect(sftp);

		}
		return returnService(Constants.SUCCESS_CODE);
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
	 * @param brhId
	 *            the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	private static Logger log = Logger.getLogger(T80232Action.class);

}
