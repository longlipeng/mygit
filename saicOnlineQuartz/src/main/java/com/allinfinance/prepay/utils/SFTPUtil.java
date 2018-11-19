package com.allinfinance.prepay.utils;
/**
 * sftp 连接工具类
 * @author taojiajun
 *
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	private transient Logger log = LoggerFactory.getLogger(this.getClass());

	private ChannelSftp sftp;

	private Session session;

	private String userName;// 用户名

	private String passWord;// 密码

	private String privateKey;// 私钥

	private String host;// 服务器ip

	private int port;// 端口号

	public SFTPUtil() {
	}

	/**
	 * 基于密码认证的sftp对象
	 * 
	
	 * @param userName
	 * @param passWord
	 * @param host
	 * @param port
	 */
	public SFTPUtil(String userName, String passWord, String host, int port) {
		this.userName = userName;
		this.passWord = passWord;
		this.host = host;
		this.port = port;
	}

	/**
	 * 构造基于秘钥认证的sftp对象
	 * 
	 * @param userName
	 * @param host
	 * @param port
	 * @param privateKey
	 */
	public SFTPUtil(String userName, String host, int port, String privateKey) {
		this.userName = userName;
		this.host = host;
		this.port = port;
		this.privateKey = privateKey;
	}
/**
 * 连接sftp服务器
 */
	public void login() {
		try {
			JSch jsch = new JSch();
			if (privateKey != null) {
				jsch.addIdentity(privateKey);
				log.info("sftp connect,path of private key file：{}" , privateKey);  
			}
			log.info("sftp connect by host:{} username:{}",host,userName); 
			
			session=jsch.getSession(userName, host, port);
			log.info("session is build");
			if(passWord!=null){
				session.setPassword(passWord);
			}
			Properties config=new Properties();
			config.setProperty("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			log.info("Session is connected");
			Channel channel=session.openChannel("sftp");
			channel.connect();
			log.info("channel is connected");
			sftp=(ChannelSftp)channel;
			log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));  
		} catch (JSchException e) {
			log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});  
		}		
	}
	/**
	 * 关闭连接 server
	 */
	public void logout(){
		if(sftp!=null){
			if(sftp.isConnected()){
				sftp.disconnect();
				 log.info("sftp is closed already");  
			}		
		}
		 if (session != null) {  
	            if (session.isConnected()) {  
	                session.disconnect();  
	                log.info("sshSession is closed already");  
	            }  
	        } 
	}
	/**
	 * 将输入流的数据上传到sftp作为文件
	 * @param directory
	 * @param sftpFileName
	 * @param input
	 * @throws SftpException
	 */
	
	 public void upload(String directory, String sftpFileName, InputStream input) throws SftpException{  
		 try {    
	            sftp.cd(directory);  
	        } catch (SftpException e) {  
	            log.warn("directory is not exist");  
	            sftp.mkdir(directory);  
	            sftp.cd(directory);  
	        } 
		 sftp.put(input, sftpFileName);  
	     log.info("file:{} is upload successful" , sftpFileName);
	 }
	 /**
	  * 上传单个文件
	  * @param directory  上传到sftp目录  目标服务器上的
	  * @param uploadFile  要上传的文件,包括路径    本地的
	  * @throws FileNotFoundException
	  * @throws SftpException
	  */
	 public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException{
		  File file = new File(uploadFile);  
	      upload(directory, file.getName(), new FileInputStream(file));
	 }
	 /** 
	     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。 
	     *  
	     * @param directory 
	     *            上传到sftp目录 
	     * @param sftpFileName 
	     *            文件在sftp端的命名 
	     * @param byteArr 
	     *            要上传的字节数组 
	     * @throws SftpException 
	     * @throws Exception 
	     */  
	    public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException{  
	        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));  
	    }  
	    /**  
	     * 将字符串按照指定的字符编码上传到sftp 
	     *   
	     * @param directory 
	     *            上传到sftp目录 
	     * @param sftpFileName 
	     *            文件在sftp端的命名 
	     * @param dataStr 
	     *            待上传的数据 
	     * @param charsetName 
	     *            sftp上的文件，按该字符编码保存 
	     * @throws UnsupportedEncodingException 
	     * @throws SftpException 
	     * @throws Exception 
	     */  
	    public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException{    
	        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));    
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
	     * @throws SftpException 
	     * @throws FileNotFoundException 
	     * @throws Exception 
	     */    
	    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{  
	        if (directory != null && !"".equals(directory)) {  
	            sftp.cd(directory);  
	        }  
	        File file = new File(saveFile);  
	        sftp.get(downloadFile, new FileOutputStream(file));  
	        log.info("file:{} is download successful" , downloadFile);  
	    }  
	    /**  
	     * 下载文件 
	     * @param directory 下载目录 
	     * @param downloadFile 下载的文件名 
	     * @return 字节数组 
	     * @throws SftpException 
	     * @throws IOException 
	     * @throws Exception 
	     */  
	    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{  
	        if (directory != null && !"".equals(directory)) {  
	            sftp.cd(directory);  
	        }  
	        InputStream is = sftp.get(downloadFile);  
	          
	        byte[] fileData = IOUtils.toByteArray(is);  
	          
	        log.info("file:{} is download successful" , downloadFile);  
	        return fileData;  
	    }  
	    
	    /** 
	     * 删除文件 
	     *   
	     * @param directory 
	     *            要删除文件所在目录 
	     * @param deleteFile 
	     *            要删除的文件 
	     * @throws SftpException 
	     * @throws Exception 
	     */  
	    public void delete(String directory, String deleteFile) throws SftpException{  
	        sftp.cd(directory);  
	        sftp.rm(deleteFile);  
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
	    public Vector<?> listFiles(String directory) throws SftpException {  
	        return sftp.ls(directory);  
	    }  
	      
	    /**
	     * 判断文件夹是否存在
	     * @param directory
	     * @return
	     */
	    public boolean  dirIsExist(String directory){
	    	try {
				sftp.cd(directory);
			} catch (SftpException e) {
				log.warn("directory is not exist");
				return false;
			}
	    	return true;
	    }
	    public static void main(String[] args) throws SftpException, IOException {  
	    	String logPF="log4j.properties";	
	    	PropertyConfigurator.configureAndWatch(logPF, 6000);// log4j 加载配置文件
	        SFTPUtil sftp = new SFTPUtil("cxpay", "cxpay", "10.250.3.103", 22);  
	        sftp.login();  
	        // 
	        //byte[] buff = sftp.download("/opt", "start.sh");  
	        //System.out.println(Arrays.toString(buff));  
	        File file = new File("F:/csb20180606.txt");  
	        InputStream is = new FileInputStream(file);  
	          
	       // sftp.upload("/home/cxpay/card_system/20180626", "test_sftp_upload.txt", is);  
	        System.out.println(sftp.dirIsExist("/home/cxpay/card_system/20180629"));
	        sftp.logout();  
	    }  
	    
}
