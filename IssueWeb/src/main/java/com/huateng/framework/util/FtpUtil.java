package com.huateng.framework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.huateng.framework.exception.BizServiceException;

/**
 * FTP 上传下载文件工具类
 * 
 * @author xxl
 * 
 */

public class FtpUtil {
	
	private static Logger logger = Logger.getLogger(FtpUtil.class);
	
	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param inputStream
	 *            输入流
	 */
	public static boolean uploadFile(String ip, int port, String userName,
			String password, String path, String fileName,
			InputStream inputStream) throws Exception {
		boolean flag = false;
		FTPClient ftp = null;
		try {
			ftp = new FTPClient();
			int reply;
			// 连接FTP服务器
			ftp.connect(ip, port);

			ftp.login(userName, password);
			// 返回值230:登陆成功
			reply = ftp.getReplyCode();
			// 连接失败
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return flag;
			}
			// 转到指定上传目录
			ftp.changeWorkingDirectory(path);
			// 将上传文件存储到指定目录
			ftp.storeFile(fileName, inputStream);
			ftp.logout();
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage());
				}
			}
		}
		return flag;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		boolean flag = false;
		FTPClient ftp = null;
		try {
			ftp = new FTPClient();
			int reply;
			ftp.connect(url, port);
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return flag;
			} // 转到指定下载目录
			ftp.changeWorkingDirectory(remotePath);
			// 列出该目录下所有文件
			FTPFile[] fs = ftp.listFiles();
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					// 根据绝对路径初始化文件
					File localFile = new File(localPath + "/" + ff.getName());
					// 输出流
					OutputStream os = new FileOutputStream(localFile);
					// 下载文件
					ftp.retrieveFile(ff.getName(), os);

					if (null != os) {
						os.close();
					}
					break;
				}
			}
			ftp.logout();
			flag = true;
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				    logger.error(ioe.getMessage());
				}
			}
		}
		return flag;
	}

	/**
	 * ftp读取文件返回字节数组列表
	 */
	public static List<byte[]> downFileStream111(String url, int port,
			String username, String password, String remotePath, String fileName)
			throws Exception {
		long currentTime = System.currentTimeMillis();
		logger.info("ftp start:");
		List<byte[]> byteList = null;
		InputStream is = null;
		FTPClient ftp = null;
		try {
			byteList = new ArrayList<byte[]>();
			ftp = new FTPClient();
			int reply;
			ftp.connect(url, port);
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
			// 转到指定下载目录
			ftp.changeWorkingDirectory(remotePath);
			// 列出该目录下所有文件
			FTPFile[] fs = ftp.listFiles();
			// 遍历所有文件，找到指定的文件
			currentTime = System.currentTimeMillis();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					// 读取文件返回输入流
					is = ftp.retrieveFileStream(ff.getName());

					if (null != is) {
						byte[] temp = new byte[256];
						while (is.read(temp) != -1) {
							byteList.add(temp);
							temp = new byte[256];
						}
					}
					break;
				}
			}
			currentTime = System.currentTimeMillis();

			// ftp.logout();
			logger.info("ftp end:");
			currentTime = System.currentTimeMillis();
			return byteList;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				    logger.error(ioe.getMessage());
				}
			}
			if (null != is) {
				is.close();
			}
		}

	}

	public static List<byte[]> downFileStream(String url, int port,
			String username, String password, String remotePath, String fileName) {
		List<byte[]> byteList = null;
		FTPClient ftp = null;
		try {
			byteList = new ArrayList<byte[]>();
			ftp = new FTPClient();
			int reply;
			ftp.connect(url, port);
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			} // 转到指定下载目录
			ftp.changeWorkingDirectory(remotePath);
			// 列出该目录下所有文件
			FTPFile[] fs = ftp.listFiles();
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					// 根据绝对路径初始化文件
					File tempResFile = null;
					// 输出流
					OutputStream os = null;
					FileInputStream fis = null;
					try {
						tempResFile = File
								.createTempFile("tempResFile", ".txt");

						os = new FileOutputStream(tempResFile);
						// 下载文件
						ftp.retrieveFile(ff.getName(), os);
						fis = new FileInputStream(tempResFile);
						if (null != fis) {
							byte[] temp = new byte[256];
							while (fis.read(temp) != -1) {
								byteList.add(temp);
								temp = new byte[256];
							}
						}
					} finally {
						if (null != os) {
							os.close();
						}
						if (null != fis) {
							fis.close();
						}
						if (null != tempResFile) {
							logger.info("删除下载文件结果：" + tempResFile.delete());
						}
					}

					break;
				}
			}
			ftp.logout();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				    logger.error(ioe.getMessage());
				}
			}
		}
		return byteList;
	}

	public static boolean ftpPutFile(String text, String fileName, String ip,
			Integer port, String userName, String password,
			String uploadFilePath) throws Exception {
		boolean connectFlag = false;
		// 生成临时文件
		File tempFile = null;
		BufferedWriter bw = null;
		try {
			tempFile = File.createTempFile("tempFile", ".txt");
			// tempFile.createNewFile();

			// 写入制卡内容
			bw = new BufferedWriter(new FileWriter(tempFile));
			bw.write(text);
			bw.flush();
			// java虚拟机正常终止后会删除该临时文件 （若虚拟机非正常终止则临时文件无法删除）
			// tempFile.deleteOnExit();

			FileInputStream fis = new FileInputStream(tempFile);
			connectFlag = FtpUtil.uploadFile(ip, port, userName, password,
					uploadFilePath, fileName, fis);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("写入制卡文件失败！");
		} finally {
			if (null != bw) {
				bw.close();
			}
			if (null != tempFile) {
				logger.info("删除文件结果：" + tempFile.delete());
			}
		}
		return connectFlag;
	}

}
