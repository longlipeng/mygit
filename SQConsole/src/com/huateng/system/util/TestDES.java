package com.huateng.system.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

public class TestDES {
	public static Key key;

	public TestDES(String str) {
		getKey(str);// 生成密匙
	}

	/**
	 * 根据参数生成KEY
	 */
	public static void getKey(String strKey) {
		try {
			//1.构造密钥生成器，指定为DES算法,不区分大小写
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			//2.根据ecnodeRules规则初始化密钥生成器
            //生成一个随机源,根据传入的字节数组
			_generator.init(new SecureRandom(strKey.getBytes()));
			//3.产生原始对称密钥
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 */
	public static void encrypt(String file, String destFile) throws Exception {
		//1.根据指定算法AES自成密码器
		Cipher cipher = Cipher.getInstance("DES");
		// cipher.init(Cipher.ENCRYPT_MODE, getKey());
		//2.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
		cipher.init(Cipher.ENCRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(destFile);
		CipherInputStream cis = new CipherInputStream(is, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = cis.read(buffer)) > 0) {
			out.write(buffer, 0, r);
		}
		cis.close();
		is.close();
		out.close();
	}

	/**
	 * 文件采用DES算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如c:/加密后文件.txt 
	 * @param destFile 
     *            解密后存放的文件名 如c:/test/解密后文件.txt
	 */
	public static void decrypt(String file, String dest) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(dest);
		CipherOutputStream cos = new CipherOutputStream(out, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = is.read(buffer)) >= 0) {
			System.out.println();
			cos.write(buffer, 0, r);
		}
		cos.close();
		out.close();
		is.close();
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		TestDES td = new TestDES("yq123b");
//		 td.encrypt("C:\\Users\\Li\\Desktop\\123456.xls", "C:\\Users\\Li\\Desktop\\123456_加密.xls"); //加密
//		td.decrypt("C:\\Users\\Li\\Desktop\\123456_加密.xls", "C:\\Users\\Li\\Desktop\\123456_解密.xls"); // 解密

		td.encrypt("E:\\加解密文件\\待加密文件.txt", "E:\\加解密文件\\已加密文件.txt");
		System.out.println("文件加密成功!");
		
		td.decrypt("E:\\加解密文件\\已加密文件.txt", "E:\\加解密文件\\已解密文件.txt");
		System.out.println("文件解密成功!");
	}
	
	
	
	
	
	
	
	
	
	
	
}