package com.huateng.framework.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * <p>
 * SFTP�����࣬����SFTPЭ��ʹ��
 * </p>
 * <p>
 * һ������ֻ���һ���ϴ������Ŀ�ʹ��
 * {@link #upload(String, int, String, String, String, String, String)} ������Ϊ
 * {@link #download(String, int, String, String, String, String, String)}
 * ������ر����Ӻ��ļ���
 * </p>
 * <p>
 * һ��������Ҫ��ɶ�β����������ʹ��{@link #connect(String, int, String, String)}
 * ���session����в������ϴ�Ϊ{@link #upload(String, String, String, ChannelSftp)},����Ϊ
 * {@link #download(String, String, String, ChannelSftp)},�Ի���ͨ����Ҫ�Լ��رգ��ļ������账��
 * </p>
 * 
 * @author zhangkun
 * 
 */
public class SFtpUtil {

	private static Logger logger = LoggerFactory.getLogger(SFtpUtil.class);

	/**
	 * ����sftp��������������ӻỰ
	 * 
	 * @param host
	 *            ��������
	 * @param port
	 *            �˿ں�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * 
	 * @return SHH�Ի�SESSION
	 * 
	 * @throws JSchException
	 *             ����ʧ��
	 */
	public static Session connect(String host, int port, String username,
			String password) throws JSchException {
		Session session = null;
		try {
			JSch jsch = new JSch();
			// ȡ�öԻ�
			jsch.getSession(username, host, port);
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			// ���ӶԻ�
			session.connect();
			System.out.println("���ӳɹ�");
		} catch (JSchException e) {
			logger.error("[" + host + ":" + port + "]" + "SFTP����ʧ��", e);
			throw e;
		}
		return session;
	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @param remotePath
	 *            Զ���ļ�·��
	 * @param remoteFileName
	 *            Զ���ļ���
	 * @param localFileFullName
	 *            �����ļ����·����
	 * @param channelSftp
	 *            SFTPͨ��
	 * 
	 * @throws SftpException
	 *             SFTPͨ���쳣
	 * @throws IOException
	 *             ����IO�쳣
	 */
	public static void upload(String remotePath, String remoteFileName,
			String localFileFullName, ChannelSftp channelSftp)
			throws SftpException, IOException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(localFileFullName);
			channelSftp.cd(remotePath);
			channelSftp.put(fileInputStream, remoteFileName);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param remotePath
	 *            Զ���ļ�·��
	 * @param remoteFileName
	 *            Զ���ļ���
	 * @param localFileFullName
	 *            �����ļ����·����
	 * @param channelSftp
	 *            SFTPͨ��
	 * 
	 * @throws SftpException
	 *             SFTPͨ���쳣
	 * @throws IOException
	 *             ����IO�쳣
	 */
	public static void download(String remotePath, String remoteFileName,
			String localFileFullName, ChannelSftp channelSftp)
			throws SftpException, IOException {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(localFileFullName);
			channelSftp.cd(remotePath);
			channelSftp.get(remoteFileName, fileOutputStream);
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @param host
	 *            ��������
	 * @param port
	 *            �˿ں�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @param remotePath
	 *            Զ���ļ�·��
	 * @param remoteFileName
	 *            Զ���ļ���
	 * @param localFileFullName
	 *            �����ļ����·����
	 * 
	 * @throws JSchException
	 *             ����ʧ��
	 * @throws SftpException
	 *             SFTPͨ���쳣
	 * @throws IOException
	 *             ����IO�쳣
	 */
	public static void upload(String host, int port, String username,
			String password, String remotePath, String remoteFileName,
			String localFileFullName) throws JSchException, SftpException,
			IOException {
		Session session = null;
		ChannelSftp channel = null;
		try {
			// ��SFTPͨ��
			session = connect(host, port, username, password);
			// ��SFTPͨ��
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			// �ϴ��ļ�
			upload(remotePath, remoteFileName, localFileFullName, channel);
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param host
	 *            ��������
	 * @param port
	 *            �˿ں�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @param remotePath
	 *            Զ���ļ�·��
	 * @param remoteFileName
	 *            Զ���ļ���
	 * @param localFileFullName
	 *            �����ļ����·����
	 * 
	 * @throws JSchException
	 *             ����ʧ��
	 * @throws SftpException
	 *             SFTPͨ���쳣
	 * @throws IOException
	 *             ����IO�쳣
	 */
	public static void download(String host, int port, String username,
			String password, String remotePath, String remoteFileName,
			String localFileFullName) throws JSchException, SftpException,
			IOException {
		Session session = null;
		ChannelSftp channel = null;
		try {
			// ���SESSION
			session = connect(host, port, username, password);
			channel = (ChannelSftp) session.openChannel("sftp");
			// ��SFTPͨ��
			channel.connect();
			// �����ļ�
			download(remotePath, remoteFileName, localFileFullName, channel);
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}
	
	
}
