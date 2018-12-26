package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;

import com.huateng.system.util.SysParamUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPClientProvider {

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param name  
	 * 			      要上传的文件名
	 * @param sftp
	 */
	public boolean uploadNm(String directory, File uploadFile,String name, ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.cd(directory);
//			File file = new File(uploadFile);
			sftp.put(new FileInputStream(uploadFile), name);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public boolean upload(String directory, File uploadFile, ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.cd(directory);
//			File file = new File(uploadFile);
			sftp.put(new FileInputStream(uploadFile), uploadFile.getName());
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public boolean download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public boolean delete(String directory, String deleteFile, ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 更改文件名
	 * 
	 * @param directory当前文件目录
	 * @param oldNameFile旧文件
	 * @param newNameFile新文件
	 * @param sftp
	 * @throws SftpException
	 */
	public boolean rename(String directory, String oldNameFile, String newNameFile, ChannelSftp sftp) throws SftpException {
		boolean flag = false;
		try {
			sftp.cd(directory);
			try {
				sftp.rm(directory+newNameFile);
			} catch (Exception e) {
				System.out.println("不存在相同文件！");
			}
			sftp.rename(oldNameFile, newNameFile);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public Vector<LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 关闭连接
	 * 
	 * @param sftp
	 */
	public boolean disconnect(ChannelSftp sftp) {
		boolean flag = false;
		try {
			sftp.disconnect();
			sftp.getSession().disconnect();
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public Map<String, String> FilePathForamtData(Map<String, String> dataObject, String FilePath) {
		String baseName = FilenameUtils.getBaseName(FilePath);
		String lastname = FilenameUtils.getExtension(FilePath);
		dataObject.put(baseName, lastname);
		return dataObject;
	}
	
	public static void main(String[] args) {
		String host = "192.168.1.111";
		int port = 9622;
		String username = "wangshuang";
		String password = "123456";
		String directory = "/home/posp/online/file/";
		String uploadFile = "D:\\work.txt";
		String downloadFile = "upload.txt";
		String saveFile = "D:\\tmp\\download.txt";
		String deleteFile = "work.txt";
		String oldName = "work.txt";
		String newName = "ws.txt";
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
		boolean flag = provider.upload( directory, uploadFile, sftp);
		System.out.println("flag:"+ flag);
		provider.disconnect(sftp);
		/*
		 * ChannelSftp sftp = connect(host, port, username, password);
		 * download(directory, downloadFile, saveFile, sftp);
		 */
		// upload(directory, uploadFile, sftp);
		// delete(directory, deleteFile,sftp);

		/*
		 * Vector<LsEntry> vector; try { vector = listFiles(directory, sftp);
		 * for(LsEntry obj :vector){ System.out.println(obj.getFilename()); } }
		 * catch (SftpException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */

		/*
		 * try { rename(directory, newName, oldName, sftp); } catch
		 * (SftpException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		/*
		 * try { disconnect(sftp); System.out.println("finished"); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}
}