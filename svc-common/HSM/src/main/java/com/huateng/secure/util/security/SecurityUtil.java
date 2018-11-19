/*
 * @(#)
 * 
 * Project:Sec_hsmj
 * 
 * Modify Information:
 * =============================================================================
 * Author Date Description ------------ ----------
 * --------------------------------------------------- first release
 * 
 * 
 * Copyright Notice:
 * =============================================================================
 * Copyright 2010 Huateng Software, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Shanghai
 * HUATENG Software Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Huateng.
 * 
 * Warning:
 * =============================================================================
 */

package com.huateng.secure.util.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.huateng.secure.util.Base64;
import com.huateng.secure.util.OutputSecResult;
import com.huateng.secure.util.exception.SecException;
import com.huateng.secure.util.log.LogWriter;

/**
 * <p>
 * <strong> This class name was SecurityUtil </strong>
 * </p>
 * 
 * @author Lay
 * @date 2010-5-12 01:00:48
 * @version 1.0
 */
public class SecurityUtil {
	private static  Logger logger = Logger.getLogger(SecurityUtil.class);
	private static final String SIGNATURE_ALGORITHM_RSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";

	private static final String SIGNATURE_ALGORITHM_RSA_MD5 = "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";

	public static final byte[] RSA_512_PublicKey = { 48, 72, 2, 65, 0, -78, 9,
			101, 14, -41, -69, 27, 99, 41, -41, 7, -106, 55, -8, 111, -102, -4,
			-65, 73, -22, 29, -44, 41, 44, -98, -53, 66, 67, -53, -79, -113,
			63, 75, -115, -80, -54, -63, -65, -71, 46, -29, -9, 75, -82, -60,
			31, 43, -85, -45, 66, 103, 100, 5, 54, -42, -66, 46, 37, -125, -8,
			-118, -42, 104, 83, 2, 3, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public static String HsmCACertificate = "";

	public static synchronized boolean initial(String aCupsCACertificate)
			throws SecException {
		if (aCupsCACertificate.equals("") || aCupsCACertificate == null) {
			throw new SecException("证书为空");
		}
		else {
			HsmCACertificate = aCupsCACertificate;
			return true;
		}
	}

	/**
	 * XML签名，报文中不加入证书内容
	 * 
	 * @param aSignedXMLString
	 * @param aKeyIndex
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public static String digitalSignWithoutCert(String aSignedXMLString,
			int aKeyIndex) throws SecException {

		LogWriter.debug("=======================进入签名=======================");
		LogWriter.debug("传入报文： 	" + aSignedXMLString);
		LogWriter.debug("私钥Index： " + aKeyIndex);

		// 待签名XML
//		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 摘要算法URI
		String tDigestURI;
		// 待签名节点
		String tNode;
		// 签名值
		String tSignValue;
		// 摘要值
		String tDigestValue;
		// ReferenceURI
		String tReferenceURI;
		// 规范化结果
		byte[] tC14nValue;

		tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
		tSignatureURI = getSignatureMethodURI(aSignedXMLString);
		tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
		tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);

		LogWriter.debug("待签名节点属性值：  " + tReferenceURI);

		tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);
		LogWriter.debug("待签名部分报文：  " + tNode);

		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

		// 计算并插入摘要值
		tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI, tC14nValue);
		aSignedXMLString = SecurityUtil.setDigestValue(aSignedXMLString,
				tDigestValue);

		// 取得SignedInfo节点报文
		tNode = SecurityUtil.getNodeByName(aSignedXMLString, "SignedInfo");
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

		// 计算并插入签名值
		tSignValue = SecurityUtil.signByHsm(tSignatureURI, tC14nValue, CodeUtil
				.formatIndex(aKeyIndex));
		aSignedXMLString = SecurityUtil.setSignatureValue(aSignedXMLString,
				tSignValue);

		LogWriter.debug("签名结束，报文如下：  " + aSignedXMLString);
		return aSignedXMLString;
	}

	/**
	 * 验证签名，由调用者传入证书（PEM）
	 * 
	 * @param aSignedXMLString
	 * @param aCertCont
	 * @return
	 * @throws SecException
	 */
	public synchronized static boolean validateSignWithCert(
			String aSignedXMLString, String aCertCont) throws SecException {

		LogWriter.debug("=======================进入验证签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);
		LogWriter.debug("证书：  " + aCertCont);

		boolean bool;
		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 待签名节点
		String tNode;
		// 签名值
		byte[] tSignValue;
		// 摘要值
		byte[] tC14nValue;
		// ReferenceURI
		String tReferenceURI;
		// 摘要算法URI
		String tDigestURI = null;
		// 签名算法
		String tSignFlag = null;
		// 证书对象
		X509Certificate tCert;
		// 传入加密机的公钥数据
		byte[] tCertKey;

		// TODO：需要验证
		tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
		tSignatureURI = getSignatureMethodURI(aSignedXMLString);
		tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
		tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);

		// 得到待签名节点
		tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);

		LogWriter.debug("待验证部分报文：  " + tNode);
		LogWriter.debug("待验证节点属性值：  " + tReferenceURI);

		// 得到签名算法
		if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
			tSignFlag = "0";
		}
		else if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
			tSignFlag = "1";
		}

		// 得到规范化运算后的值
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

		// 从报文中取出签名值
		tSignValue = SecurityUtil.getSignatureValue(aSignedXMLString);

		// 构造证书对象
		tCert = getX509Certificate(aCertCont);
		tCertKey = tCert.getPublicKey().getEncoded();

		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);
		HsmUtil tHsm = new HsmUtil();
		bool = tHsm.verifyByPublic(tHsmKey, tSignValue, tC14nValue, tSignFlag);

		LogWriter.debug("验证签名结果：   " + bool);
		return bool;
	}

	/**
	 * 验证签名，由调用者传入证书（PEM）
	 * 
	 * @param aSignedXMLString
	 * @param aCertCont
	 * @return
	 * @throws SecException
	 */
	public synchronized static boolean validateSignWithCert(
			String aSignedXMLString, X509Certificate aCert) throws SecException {

		LogWriter.debug("=======================进入验证签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);
		LogWriter.debug("证书：  ");

		boolean bool;
		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 待签名节点
		String tNode;
		// 签名值
		byte[] tSignValue;
		// 摘要值
		byte[] tC14nValue;
		// ReferenceURI
		String tReferenceURI;
		// 摘要算法URI
		String tDigestURI;
		// 签名算法
		String tSignFlag = null;
		// 证书对象
		X509Certificate tCert;
		// 传入加密机的公钥数据
		byte[] tCertKey;

		tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
		tSignatureURI = getSignatureMethodURI(aSignedXMLString);
		tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
		tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);

		// 得到待签名节点
		tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);
		String tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI,
				tC14nValue);
		String vDigestValue = SecurityUtil.getNodeValueByName(aSignedXMLString,
				"DigestValue");
		if (!tDigestValue.equals(vDigestValue)) {
			return false;
		}

		tNode = SecurityUtil.getNodeByName(aSignedXMLString, "SignedInfo");

		LogWriter.debug("待验证部分报文：  " + tNode);
		LogWriter.debug("待验证节点属性值：  " + tReferenceURI);

		// 得到签名算法
		if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
			tSignFlag = "0";
		}
		else if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
			tSignFlag = "1";
		}

		// 得到规范化运算后的值
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

		// 从报文中取出签名值
		tSignValue = SecurityUtil.getSignatureValue(aSignedXMLString);
		// 构造证书对象
		tCertKey = aCert.getPublicKey().getEncoded();
		LogWriter.debug("公钥是否合法：  "
				+ (SecurityUtil.getPublicKey(Base64.encode(tCertKey)) != null));

		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);
		LogWriter.debug("传入加密机中公钥：  " + Base64.encode(tHsmKey));
		HsmUtil tHsm = new HsmUtil();
		bool = tHsm.verifyByPublic(tHsmKey, tSignValue, tC14nValue, tSignFlag);
		LogWriter.debug("验证签名结果：   " + bool);
		return bool;
	}

	/**
	 * 验证签名，从报文中取证书
	 * 
	 * @param aSignedXMLString
	 * @return
	 * @throws SecException
	 */
	public synchronized static boolean validateSignWithoutCert(
			String aSignedXMLString) throws SecException {

		LogWriter.debug("=======================进入验证签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);

		boolean bool;
		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 摘要算法URI
		String tDigestURI;
		// 待签名节点
		String tNode;
		// 签名值
		byte[] tSignValue;
		// 摘要值
		byte[] tC14nValue;
		// ReferenceURI
		String tReferenceURI;
		// 签名算法
		String tSignFlag = null;
		// 证书对象
		X509Certificate tCert;
		// 证书中取得的公钥数据
		byte[] tCertKey;
		tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
		tSignatureURI = getSignatureMethodURI(aSignedXMLString);
		tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
		tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);
		// 得到待签名节点
		tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);
		String tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI,
				tC14nValue);
		String vDigestValue = SecurityUtil.getNodeValueByName(aSignedXMLString,
				"DigestValue");
		if (!tDigestValue.equals(vDigestValue)) {
			return false;
		}

		LogWriter.debug("待验证部分报文：  " + tNode);
		LogWriter.debug("待验证节点属性值：  " + tReferenceURI);

		// 从报文中取出证书信息并构造证书对象
		String tCertValue = SecurityUtil.getCertValue(aSignedXMLString);
		tCert = getX509Certificate(tCertValue);

		// 验证证书有效性
		// if (HsmCACertificate.equals("") || HsmCACertificate == null) {
		// throw new SecException("CUPSecureCA证书未初始化");
		// }

		// SecurityUtil.CheckCertificate(HsmCACertificate, tCert);
		// LogWriter.debug("证书验证通过");

		// 得到签名算法
		if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
			tSignFlag = "0";
		}
		else if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
			tSignFlag = "1";
		}

		tNode = SecurityUtil.getNodeByName(aSignedXMLString, "SignedInfo");
		// 得到规范化运算后的值
		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);
		// 从报文中取出签名值
		tSignValue = SecurityUtil.getSignatureValue(aSignedXMLString);

		// 从证书中取出公钥数据
		tCertKey = tCert.getPublicKey().getEncoded();
		LogWriter.debug("公钥是否合法：  "
				+ (SecurityUtil.getPublicKey(Base64.encode(tCertKey)) != null));

		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);

		LogWriter.debug("传入加密机中公钥：  " + Base64.encode(tHsmKey));

		HsmUtil tHsm = new HsmUtil();
		bool = tHsm.verifyByPublic(tHsmKey, tSignValue, tC14nValue, tSignFlag);
		LogWriter.debug("验证签名结果：   " + bool);
		return bool; // 正式结果
	}

	/**
	 * API调用的验证签名方法，不调用加密机，传入证书
	 * 
	 * @param aSignedXMLString
	 * @param aCert
	 * @return
	 * @throws SecException
	 */
	public static boolean validateSign4API(String aSignedXMLString, String aCert)
			throws SecException {

		LogWriter.debug("=======================进入验证签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);
		LogWriter.debug("证书： " + aCert);

		boolean bool;
		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 摘要算法URI
		String tDigestURI;
		// 待签名节点
		String tNode;
		// 签名值
		byte[] tSignValue;
		// 摘要值
		String tDigestValue;
		// ReferenceURI
		String tReferenceURI;
		// 签名算法
		String tSignFlag = null;
		// 证书对象
		X509Certificate tCert;
		// 证书中取得的公钥对象
		PublicKey tPublicKey;
		// 规范化后的数据
		byte[] tC14nValue;
		try {
			tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
			tSignatureURI = getSignatureMethodURI(aSignedXMLString);
			tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
			tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);

			// 得到待签名节点
			tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);
			tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

			LogWriter.debug("待验证部分报文:	" + tNode);
			LogWriter.debug("待验证节点属性值:	" + tReferenceURI);

			// 计算摘要值
			tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI,
					tC14nValue);
			// 取SignedInfo中的摘要值
			String vDigestValue = SecurityUtil.getNodeValueByName(
					aSignedXMLString, "DigestValue");
			if (!tDigestValue.equals(vDigestValue)) {
				return false;
			}

			// 得到签名算法
			if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
				tSignFlag = "0";
			}
			else if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
				tSignFlag = "1";
			}

			// 取SignedInfo
			tNode = SecurityUtil.getNodeByName(aSignedXMLString, "SignedInfo");
			// 得到规范化运算后的值
			tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);
			// 从报文中取出签名值
			tSignValue = SecurityUtil.getSignatureValue(aSignedXMLString);

			// 构造证书对象
			tCert = getX509Certificate(aCert);

			// 从证书中取出公钥对象
			tPublicKey = tCert.getPublicKey();

			bool = SecurityUtil
					.verifyBySoft(tPublicKey, tSignValue, tC14nValue);
		} catch (Exception e) {
			LogWriter.debug(new SecurityUtil() + e.getMessage());
			return false;
		}

		LogWriter.debug("验证签名结果:	" + bool);

		return bool; // 正式结果
	}

	/**
	 * API调用的验证签名方法，不调用加密机,不传入证书
	 * 
	 * @param aSignedXMLString
	 * @return
	 * @throws SecException
	 */
	public static boolean validateSign4API(String aSignedXMLString)
			throws SecException {

		LogWriter.debug("=======================进入验证签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);

		boolean bool;
		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 摘要算法URI
		String tDigestURI;
		// 待签名节点
		String tNode;
		// 签名值
		byte[] tSignValue;
		// 摘要值
		String tDigestValue;
		// ReferenceURI
		String tReferenceURI;
		// 签名算法
		String tSignFlag = null;
		// 证书对象
		X509Certificate tCert;
		// 证书中取得的公钥对象
		PublicKey tPublicKey;
		// 规范化后的数据
		byte[] tC14nValue;

		try {

			tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
			tSignatureURI = getSignatureMethodURI(aSignedXMLString);
			tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
			tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);

			// 从报文中取出证书信息并构造证书对象
			String tCertValue = SecurityUtil.getCertValue(aSignedXMLString);
			tCert = getX509Certificate(tCertValue);

			// 得到待签名节点
			tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);
			tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

			LogWriter.debug("待验证部分报文:" + tNode);
			LogWriter.debug("待验证节点属性值：  " + tReferenceURI);

			// 计算摘要值
			tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI,
					tC14nValue);
			// 取SignedInfo中的摘要值
			String vDigestValue = SecurityUtil.getNodeValueByName(
					aSignedXMLString, "DigestValue");
			if (!tDigestValue.equals(vDigestValue)) {
				return false;
			}

			// 得到签名算法
			if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
				tSignFlag = "0";
			}
			else if (tSignatureURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
				tSignFlag = "1";
			}
			// 取SignedInfo
			tNode = SecurityUtil.getNodeByName(aSignedXMLString, "SignedInfo");
			// 得到规范化运算后的值
			tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);
			// 从报文中取出签名值
			tSignValue = SecurityUtil.getSignatureValue(aSignedXMLString);

			// 从证书中取出公钥对象
			tPublicKey = tCert.getPublicKey();

			bool = SecurityUtil
					.verifyBySoft(tPublicKey, tSignValue, tC14nValue);
		} catch (Exception e) {
			LogWriter.debug(new SecurityUtil() + e.getMessage());
			return false;
		}

		LogWriter.debug("验证签名结果：   " + bool);
		return bool; // 正式结果
	}

	/**
	 * API调用的签名方法，不调用加密机
	 * 
	 * @param aStrXML
	 * @param aCertCont
	 * @param aPrivateKeyO
	 * @return
	 * @throws SecException
	 */
	public synchronized static String digitalSign4API(String aSignedXMLString,
			String aCertCont, String aPrivateKey) throws SecException {

		LogWriter.debug("=======================进入签名=======================");
		LogWriter.debug("报文： " + aSignedXMLString);
		LogWriter.debug("私钥：  " + aPrivateKey);
		LogWriter.debug("证书：  " + aCertCont);

		// 待签名XML
		Document tDoc;
		// 规范化算法URI
		String tC14nURI;
		// 签名算法URI
		String tSignatureURI;
		// 摘要算法URI
		String tDigestURI;
		// 待签名节点
		String tNode;
		// 签名值
		String tSignValue;
		// 摘要值
		String tDigestValue;
		// ReferenceURI
		String tReferenceURI;
		// 私钥对象
		PrivateKey tPrivateKey = null;
		// 规范化后的数据
		byte[] tC14nValue;

		tC14nURI = getCanonicalizationMethodURI(aSignedXMLString);
		tSignatureURI = getSignatureMethodURI(aSignedXMLString);
		tDigestURI = getDigestAlgorithmURI(aSignedXMLString);
		tReferenceURI = SecurityUtil.getReferenceURI(aSignedXMLString);
		tNode = SecurityUtil.getSignNode(aSignedXMLString, tReferenceURI);

		LogWriter.debug("待签名部分报文：  " + tNode);
		LogWriter.debug("待签名节点属性值：  " + tReferenceURI);

		tC14nValue = SecurityUtil.c14n(tC14nURI, tNode);

		// 计算并插入摘要值
		tDigestValue = SecurityUtil.messageDigestBase64(tDigestURI, tC14nValue);
		aSignedXMLString = SecurityUtil.setDigestValue(aSignedXMLString,
				tDigestValue);

		// 计算并插入签名值
		tPrivateKey = getPrivateKey(aPrivateKey);
		tSignValue = signBySoft(tPrivateKey, tDigestValue);
		aSignedXMLString = SecurityUtil.setSignatureValue(aSignedXMLString,
				tSignValue);

		// 插入证书到X509Certificate节点
		aSignedXMLString = SecurityUtil.setX509CertificateValue(
				aSignedXMLString, aCertCont);

		// 计算并插入证书DN信息到X509SubjectName节点
		X509Certificate tCert = getX509Certificate(aCertCont);
		aSignedXMLString = SecurityUtil.setX509SubjectNameValue(
				aSignedXMLString, tCert.getIssuerDN().toString());
		return aSignedXMLString;

	}

	/**
	 * 
	 * @param aData
	 * @param keyIndex
	 * @return
	 * @throws SecException
	 */
	public static String digitalSignNotXML(String aData, int keyIndex)
			throws SecException {
		HsmUtil tHsm = new HsmUtil();
		byte[] tByte = tHsm.signByIndexByte(aData.getBytes(), "0", CodeUtil
				.formatIndex(keyIndex));
		return Base64.encode(tByte);
	}

	/**
	 * 
	 * @param aData
	 * @param aPrivateKey
	 * @return
	 * @throws SecException
	 */
	public static String digitalSignNotXML(String aData, String aPrivateKey)
			throws SecException {

		byte[] tPrivate = new byte[Base64.decode(aPrivateKey).length - 26];

		// 从传入参数中取得传入加密机中的私钥数据
		System.arraycopy(Base64.decode(aPrivateKey), 26, tPrivate, 0,
				tPrivate.length);
		HsmUtil tHsm = new HsmUtil();
		byte[] tByte = tHsm.signByPrivate(aData.getBytes(), "0", tPrivate);

		return Base64.encode(tByte);
	}

	/**
	 * 
	 * @param aSignedData
	 * @param aData
	 * @param aCertCont
	 * @return
	 * @throws SecException
	 */

	public static boolean validateSignNotXML(String aSignedData, String aData,
			String aCertCont) throws SecException {

		// 构造证书对象
		byte[] tCertKey;
		X509Certificate tCert = getX509Certificate(aCertCont);
		tCertKey = tCert.getPublicKey().getEncoded();

		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);
		HsmUtil tHsm = new HsmUtil();
		boolean bool = tHsm.verifyByPublic(tHsmKey, Base64.decode(aSignedData),
				Base64.decode(aData), "0");
		return bool;
	}

	public static boolean validateSignNotXML(String aSignedData, String aData,
			X509Certificate aCert) throws SecException {

		// 构造证书对象
		byte[] tCertKey;
		tCertKey = aCert.getPublicKey().getEncoded();

		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);
		HsmUtil tHsm = new HsmUtil();
		boolean bool = tHsm.verifyByPublic(tHsmKey, Base64.decode(aSignedData),
				Base64.decode(aData), "0");
		return bool;
	}

	/**
	 * 非对称加加密
	 * 
	 * @param aPin
	 * @param aKeyLabel
	 * @return
	 * @throws SecException
	 */
	public synchronized static OutputSecResult assymEncrypt(byte[] aPin,
			int aKeyIndex) throws SecException {

		// 软件加密
		byte[] bX509PubKeyHeader = { 48, -127, -97, 48, 13, 6, 9, 42, -122, 72,
				-122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0 };

		OutputSecResult outRslt = new OutputSecResult();

		byte[] tEnData = null;
		byte[] tEnDataLen = null;
		Cipher tCipher = null;
		PublicKey publickey = null;
		KeyFactory keyFact = null;

		try {

			// 构造密钥
			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			byte[] bPubKey = new byte[RSA_512_PublicKey.length
					+ bX509PubKeyHeader.length];
			System.arraycopy(bX509PubKeyHeader, 0, bPubKey, 0,
					bX509PubKeyHeader.length);
			System.arraycopy(RSA_512_PublicKey, 0, bPubKey,
					bX509PubKeyHeader.length, RSA_512_PublicKey.length);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bPubKey);
			keyFact = KeyFactory.getInstance("RSA", "BC");
			publickey = keyFact.generatePublic(keySpec);

			// 加密
			tCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			tCipher.init(Cipher.ENCRYPT_MODE, publickey);
			tEnData = tCipher.doFinal(aPin);
			tEnDataLen = CodeUtil.formatLength(tEnData.length).getBytes();
			outRslt.setData(tEnData);
			outRslt.setDataLen(tEnDataLen);
		} catch (Exception e) {
			LogWriter.debug(new SecurityUtil() + e.getMessage());
			throw new SecException(SecException.ERR_SEC_CODE_8001
					+ SecException.ERR_SEC_MSG_8001);
		}

		// 硬件加密
		/*
		 * LogWriter.debug("进行硬件RSA加密..."); String tIndex =
		 * CodeUtil.formatIndex(aKeyIndex); String tDataLen =
		 * CodeUtil.formatLength(aPin.length); HsmUtil hsm = new HsmUtil();
		 * outRslt = hsm.RSAEncrypt(tIndex, aPin,tDataLen);
		 */
		return outRslt;
	}

	/**
	 * 非对称转PIN操作
	 * 
	 * @param aData
	 * @param aDataLen
	 * @param aAssym512Key
	 * @param aCUPSLable
	 * @param aCardNO
	 * @return
	 * @throws SecException
	 */
	public synchronized static OutputSecResult cupsAssymEncrypt(byte[] aData,
			byte[] aDataLen, int aAssym512KeyIndex, int aHSM2LevelIndex,
			String aCardNO) throws SecException {
		if (aCardNO.length() == 11) {
			aCardNO = "00" + aCardNO;
		}
		else if (aCardNO.length() == 12) {
			aCardNO = "0" + aCardNO;
		}

		HsmUtil hsm = new HsmUtil();

		OutputSecResult outRslt = hsm.UnsymConvertPin(CodeUtil
				.formatIndex(aAssym512KeyIndex), aDataLen, aData, CodeUtil
				.formatIndex(aHSM2LevelIndex), aCardNO);
		return outRslt;
	}

	/**
	 * 对PPP（公共支付平台）非对称转PIN操作
	 * 
	 * @param aData
	 * @param aDataLen
	 * @param RSAKeyIdx
	 * @param BMKKeyIdx
	 * @param aCardNO
	 * @param aPIK
	 * @param PIKLen
	 *            -
	 * @return OutputSecResult - com.huateng.secure.util.OutputSecResult
	 * @throws SecException
	 */
	public synchronized static OutputSecResult pppAssymEncrypt(byte[] aData,
			int aDataLen, int RSAKeyIdx, int BMKKeyIdx, String aCardNO,
			String aPIK, int PIKLen) throws SecException {

		String sPIK = aPIK.substring(0, PIKLen * 2);

		HsmUtil hsm = new HsmUtil();
		String aPIKLen = CodeUtil.formatLength(PIKLen, 2);
		OutputSecResult outRslt = hsm.UnsymConvertPin(CodeUtil
				.formatIndex(RSAKeyIdx), CodeUtil.formatLength(aDataLen, 4)
				.getBytes(), aData, CodeUtil.formatIndex(BMKKeyIdx), aCardNO,
				sPIK.getBytes(), aPIKLen);
		return outRslt;
	}

	/**
	 * 生成数据MAC
	 * 
	 * @param BMKKeyIdx
	 * @param MACKey
	 * @param MACKeyLen
	 * @param aData
	 * @param DataLen
	 * @return OutputSecResult - com.huateng.secure.util.OutputSecResult
	 * @throws SecException
	 */
	public synchronized static OutputSecResult Bank_GenMAC(int BMKKeyIdx,
			String MACKey, int MACKeyLen, String aData, int DataLen)
			throws SecException {

		HsmUtil hsm = new HsmUtil();

		OutputSecResult outRslt = hsm.Bank_GenMac(CodeUtil
				.formatIndex(BMKKeyIdx), CodeUtil.formatLength(MACKeyLen, 2),
				MACKey, CodeUtil.formatLength(DataLen, 3), aData.getBytes());
		return outRslt;
	}

	/* 增加byte方式的mac */
	public synchronized static OutputSecResult Bank_GenMAC(int BMKKeyIdx,
			String MACKey, int MACKeyLen, byte[] bData, int DataLen)
			throws SecException {

		HsmUtil hsm = new HsmUtil();

		OutputSecResult outRslt = hsm.Bank_GenMac(CodeUtil
				.formatIndex(BMKKeyIdx), CodeUtil.formatLength(MACKeyLen, 2),
				MACKey, CodeUtil.formatLength(DataLen, 3), bData);
		return outRslt;
	}

	/**
	 * 生成数据MAC
	 * 
	 * @param BMKKeyIdx
	 * @param MACKey
	 * @param MACKeyLen
	 * @param aData
	 * @param DataLen
	 * @return OutputSecResult - com.huateng.secure.util.OutputSecResult
	 * @throws SecException
	 */
	public synchronized static boolean Bank_VerifyMac(int BMKKeyIdx,
			String MACKey, int MACKeyLen, String MACData, String aData,
			int DataLen) throws SecException {

		HsmUtil hsm = new HsmUtil();

		boolean check = hsm.Bank_VerifyMac(CodeUtil.formatIndex(BMKKeyIdx),
				CodeUtil.formatLength(MACKeyLen, 2), MACKey,
				MACData.getBytes(), aData.getBytes(), CodeUtil.formatLength(
						DataLen, 3));
		return check;
	}

	public synchronized static boolean Bank_VerifyMac(int BMKKeyIdx,
			String MACKey, int MACKeyLen, String MACData, byte[] bData,
			int DataLen) throws SecException {

		HsmUtil hsm = new HsmUtil();

		boolean check = hsm.Bank_VerifyMac(CodeUtil.formatIndex(BMKKeyIdx),
				CodeUtil.formatLength(MACKeyLen, 2), MACKey,
				MACData.getBytes(), bData, CodeUtil.formatLength(DataLen, 3));
		return check;
	}

	/**
	 * 对用RSA 私钥解密
	 * 
	 * @param aData
	 * @param aDataLen
	 * @param RSAKeyIdx
	 * 
	 * @return OutputSecResult - com.huateng.secure.util.OutputSecResult
	 * @throws SecException
	 */
	public synchronized static OutputSecResult RSAPriKeyDecrypt(byte[] aData,
			int aDataLen, int RSAKeyIdx) throws SecException {

		HsmUtil hsm = new HsmUtil();
		OutputSecResult outRslt = hsm.RSAPriKeyDecrypt(CodeUtil
				.formatIndex(RSAKeyIdx), aData, CodeUtil.formatLength(aDataLen,
				4));
		return outRslt;
	}

	/**
	 * 对用RSA 公钥加密
	 * 
	 * @param aData
	 * @param aDataLen
	 * @param RSAKeyIdx
	 * 
	 * @return OutputSecResult - com.huateng.secure.util.OutputSecResult
	 * @throws SecException
	 */
	public synchronized static OutputSecResult PubKeyEncryptWithCert(
			byte[] aData, int aDataLen, X509Certificate aCert)
			throws SecException {

		HsmUtil hsm = new HsmUtil();
		byte[] tCertKey = aCert.getPublicKey().getEncoded();
		// 传入加密机的公钥数据
		byte[] tHsmKey = new byte[tCertKey.length - 22];
		System.arraycopy(tCertKey, 22, tHsmKey, 0, tCertKey.length - 22);
		String sPublicKeyLen = CodeUtil.formatLength(tCertKey.length - 22, 4);
		OutputSecResult outRslt = hsm.PubKeyEncryptWithCert(tHsmKey,
				sPublicKeyLen, aData, CodeUtil.formatLength(aDataLen, 4));
		return outRslt;
	}

	/**
	 * 签名运算，传入私钥index
	 * 
	 * @param SignatureAlgorithmURI
	 * @param aDigestAlgorithmURI
	 * @param aC14nURI
	 * @param aNode
	 * @param aKeyIndex
	 * @return
	 * @throws SecException
	 */
	private static String signByHsm(String SignatureAlgorithmURI,
			byte[] aC14nValue, String aKeyIndex) throws SecException {

		String tSignFlag = null;

		byte[] tSignedBytes = null;
		String tSignedString = null;

		if (SignatureAlgorithmURI.equals(SIGNATURE_ALGORITHM_RSA_SHA1)) {
			tSignFlag = "0";
		}
		else if (SignatureAlgorithmURI.equals(SIGNATURE_ALGORITHM_RSA_MD5)) {
			tSignFlag = "1";
		}

		try {

			HsmUtil tHsm = new HsmUtil();
			tSignedBytes = tHsm.signByIndexByte(aC14nValue, tSignFlag,
					aKeyIndex);
			tSignedString = Base64.encode(tSignedBytes);

			LogWriter.debug("签名运算结果：" + tSignedString);

		} catch (Exception e) {
			throw new SecException(e.getMessage());
		}

		return tSignedString;
	}

	/**
	 * 取出待签名的Element的属性值(Reference的URI属性的值)
	 * 
	 * @param aDoc
	 * @return
	 * @throws SecException
	 */
	private static String getReferenceURI(String aXMLString)
			throws SecException {

		// String tSignatureID =
		// SecurityUtil.getValueByName(aXMLString,"Reference URI=\"");
		int idxRef = aXMLString.indexOf("<Reference");
		String URI = "URI=\"";
		int idxFlag = aXMLString.indexOf(">", idxRef);
		int idxURIBegin = aXMLString.indexOf(URI, idxRef);
		if (idxURIBegin > idxFlag) {
			throw new SecException("取出ReferenceURI失败");
		}
		int idxURIEnd = aXMLString.indexOf("\"", idxURIBegin + URI.length());

		String tSignatureID = aXMLString.substring(idxURIBegin + URI.length(),
				idxURIEnd);
		if (tSignatureID.charAt(0) == '#') {
			tSignatureID = tSignatureID.substring(1, tSignatureID.length());
		}

		return tSignatureID;
	}

	/**
	 * 对需要签名的报文进行规范化和摘要运算
	 * 
	 * @param aDigestAlgorithmURI
	 * @param aC14nURI
	 * @param aNode
	 * @return
	 * @throws SecException
	 */
	private static byte[] messageDigest(String aDigestAlgorithmURI,
			byte[] aC14nValue) throws SecException {

		LogWriter
				.debug("=======================开始规范化和摘要运算=======================");
		LogWriter.debug("aDigestAlgorithmURI:" + aDigestAlgorithmURI);

		String tDigestAlg;
		org.apache.xml.security.Init.init();
		tDigestAlg = JCEMapper.translateURItoJCEID(aDigestAlgorithmURI);

		LogWriter.debug("摘要算法:" + tDigestAlg);
		byte[] tDigestedData = null;

		tDigestedData = hash(tDigestAlg, aC14nValue);

		LogWriter.debug("摘要运算结果：  " + Base64.encode(tDigestedData));

		return tDigestedData;
	}

	/**
	 * 对需要签名的报文进行规范化和摘要运算,返回值经过BASE64编码
	 * 
	 * @param aDigestAlgorithmURI
	 * @param aC14nURI
	 * @param aNode
	 * @return
	 * @throws SecException
	 */
	private static String messageDigestBase64(String aDigestAlgorithmURI,
			byte[] aC14nValue) throws SecException {

		byte[] tByte = SecurityUtil.messageDigest(aDigestAlgorithmURI,
				aC14nValue);

		return Base64.encode(tByte);
	}

	/**
	 * 根据指定的算法，计算传入数据的摘要值
	 * 
	 * @param aAlgorithm
	 *            摘要算法
	 * @param aByteArray
	 *            需要计算摘要的数据
	 * @return 摘要值
	 */
	private static byte[] hash(String aAlgorithm, byte[] aByteArray)
			throws SecException {
		try {
			MessageDigest md = MessageDigest.getInstance(aAlgorithm);
			md.update(aByteArray);
			return md.digest();
		} catch (Exception ex) {
			LogWriter.debug(ex.getMessage());
			throw new SecException("计算摘要失败");
		}
	}

	/**
	 * 取得SignatureMethod节点Algorithm属性的值
	 * 
	 * @param aDoc
	 * @return
	 * @throws SecException
	 */
	private static String getSignatureMethodURI(String aXMLString)
			throws SecException {

		String tSignatureAlgorithmURI = SecurityUtil.getValueByName(aXMLString,
				"SignatureMethod Algorithm=\"");
		if (tSignatureAlgorithmURI == null)
			throw new SecException("取得SignatureMethod节点Algorithm属性失败");
		return tSignatureAlgorithmURI;
	}

	/**
	 * 取得DigestMethod节点Algorithm属性的值
	 * 
	 * @param aDoc
	 * @return
	 * @throws SecException
	 */
	private static String getDigestAlgorithmURI(String aXMLString)
			throws SecException {

		String tDigestAlgorithmURI = SecurityUtil.getValueByName(aXMLString,
				"DigestMethod Algorithm=\"");

		if (tDigestAlgorithmURI == null)
			throw new SecException("取得DigestMethod节点Algorithm属性失败");
		return tDigestAlgorithmURI;
	}

	/**
	 * 取得CanonicalizationMethod节点Algorithm属性的值
	 * 
	 * @param aXMLString
	 * @return
	 * @throws SecException
	 */
	private static String getCanonicalizationMethodURI(String aXMLString)
			throws SecException {
		String tC14nURI = SecurityUtil.getValueByName(aXMLString,
				"CanonicalizationMethod Algorithm=\"");
		if (tC14nURI == null)
			throw new SecException("取得Canonicalization节点Algorithm属性失败");
		return tC14nURI;
	}

	/**
	 * 
	 * @param aXMLString
	 * @param aName
	 * @return
	 * @throws SecException
	 */
	private static String getValueByName(String aXMLString, String aName)
			throws SecException {

		int tNameindex = aXMLString.indexOf(aName);
		if (tNameindex < 10)
			return null;

		int tValueEndIndex = aXMLString.indexOf("\"", tNameindex
				+ aName.length());
		if (tValueEndIndex < tNameindex)
			return null;

		String tValue = aXMLString.substring(tNameindex + aName.length(),
				tValueEndIndex);
		return tValue;
	}

	/**
	 * 
	 * @param aXMLString
	 * @param aName
	 * @param aValue
	 * @return
	 */
	private static String setValueByName(String aXMLString, String aName,
			String aValue) {
		int tNameindex = aXMLString.indexOf(aName);
		if (tNameindex < 0)
			return null;
		String tSub1 = aXMLString.substring(0, tNameindex + aName.length());
		String tSub2 = aXMLString.substring(tNameindex + aName.length());
		StringBuffer tSb = new StringBuffer(tSub1);
		tSb = tSb.append(aValue).append(tSub2);
		return tSb.toString();
	}

	/**
	 * 签名后设置SignatureValue节点的值
	 * 
	 * @param aDoc
	 * @param aValue
	 * @throws SecException
	 */
	private static String setSignatureValue(String aXMLString, String aValue)
			throws SecException {

		String tResult = SecurityUtil.setValueByName(aXMLString,
				"<SignatureValue>", aValue);

		if (tResult == null)
			throw new SecException("设置SignatureValue节点的值失败");
		return tResult;
	}

	/**
	 * 签名后设置DigestValue节点的值
	 * 
	 * @param aDoc
	 * @param aValue
	 * @throws SecException
	 */
	private static String setDigestValue(String aXMLString, String aValue)
			throws SecException {

		String tResult = SecurityUtil.setValueByName(aXMLString,
				"<DigestValue>", aValue);

		if (tResult == null)
			throw new SecException("设置DigestValue节点的值失败");
		return tResult;
	}

	/**
	 * 签名后设置X509SubjectName节点的值
	 * 
	 * @param aDoc
	 * @param aValue
	 * @throws SecException
	 */
	private static String setX509SubjectNameValue(String aXMLString,
			String aValue) throws SecException {

		String tResult = SecurityUtil.setValueByName(aXMLString,
				"<X509SubjectName>", aValue);

		if (tResult == null)
			throw new SecException("设置X509SubjectName节点的值失败");
		return tResult;

	}

	/**
	 * 签名后设置X509Certificate节点的值
	 * 
	 * @param aDoc
	 * @param aValue
	 * @throws SecException
	 */
	private static String setX509CertificateValue(String aXMLString,
			String aValue) throws SecException {

		String tResult = SecurityUtil.setValueByName(aXMLString,
				"<X509Certificate>", aValue);

		if (tResult == null)
			throw new SecException("设置X509SubjectName节点的值失败");
		return tResult;
	}

	/**
	 * 报文中取出SignatureValue的值
	 * 
	 * @param aDoc
	 * @return
	 * @throws SecException
	 */
	public static byte[] getSignatureValue(String aXMLString)
			throws SecException {

		String tBegin = "<SignatureValue>";
		String tEnd = "</SignatureValue>";

		int tNameIndex = aXMLString.indexOf(tBegin);

		if (tNameIndex < 10)
			return null;

		int tEndIndex = aXMLString.indexOf(tEnd, tNameIndex);

		if (tEndIndex < tNameIndex)
			return null;

		String tValue = aXMLString.substring(tNameIndex + tBegin.length(),
				tEndIndex);

		return Base64.decode(tValue);
	}

	/**
	 * 从报文的X509Certificate节点中取出经过Base64编码的证书数据
	 * 
	 * @param aDoc
	 * @return
	 * @throws SecException
	 */
	private static String getCertValue(String aXMLString) throws SecException {

		String tBegin = "<X509Certificate>";
		String tEnd = "</X509Certificate>";

		int tNameIndex = aXMLString.indexOf(tBegin);
		if (tNameIndex < 10)
			return null;

		int tEndIndex = aXMLString.indexOf(tEnd, tNameIndex);
		if (tEndIndex < tNameIndex)
			return null;

		String tValue = aXMLString.substring(tNameIndex + tBegin.length(),
				tEndIndex);
		return tValue;
	}

	/**
	 * Docuement对象转化为字符串
	 * 
	 * @param document
	 * @return
	 * @throws SecException
	 */
	public static String documentToString(Document document)
			throws SecException {

		if (document == null)
			throw new SecException("Docuemnt对象为空");
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t;
		ByteArrayOutputStream bos = null;

		try {
			t = tf.newTransformer();
			bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(document), new StreamResult(bos));
		} catch (Exception e) {
			LogWriter.debug(e.getMessage());
			throw new SecException("Docuemnt对象转字符串失败");
		}
		return bos.toString();
	}

	/**
	 * XML String转化为Document对象
	 * 
	 * @param strXml
	 * @return
	 * @throws SecException
	 * @throws
	 */
	public static Document stringToDocument(String strXml) throws SecException {

		if (strXml == null)
			throw new SecException("字符串为空");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		DocumentBuilder db;
		Document tDoc = null;
		try {
			db = dbf.newDocumentBuilder();
			db.parse(new InputSource(new StringReader(strXml)))
					.getDocumentElement();
			tDoc = db.parse(new InputSource(new StringReader(strXml)));
		} catch (Exception e) {
			LogWriter.debug(e.getMessage());
			throw new SecException("字符串转Docuemnt对象失败");
		}
		return tDoc;

	}

	/**
	 * 根据Reference的URI属性值取出报文待签名节点
	 * 
	 * @param aXMLString
	 *            报文字符串
	 * @param aReferenceURI
	 *            Reference的URI属性值
	 * @return 报文待签名节点
	 * @throws Exception
	 */
	private static String getSignNode(String aXMLString, String aReferenceURI)
			throws SecException {

		// 1、取得 id="aReferenceURI"的起始index
		int tReferenceURIIndex = aXMLString.indexOf("id=\"" + aReferenceURI
				+ "\"");

		if (tReferenceURIIndex < 10)
			return null;

		// 2、取得Node的名称
		int tNodeStartIndex = aXMLString.indexOf("<", tReferenceURIIndex - 10);
		if (tNodeStartIndex < 0)
			return null;

		String tNodeName = aXMLString.substring(tNodeStartIndex + 1,
				tReferenceURIIndex - 1);

		// 3、取得整个节点
		int tNodeEndIndex = aXMLString.indexOf("</" + tNodeName + ">");
		if (tNodeEndIndex < tNodeStartIndex)
			return null;

		String tNode = aXMLString.substring(tNodeStartIndex, tNodeEndIndex + 3
				+ tNodeName.length());

		// Node tResult = stringToDocument(tNode);
		// if(tResult == null){
		// throw new SecException("找不到待签名节点");
		// }

		return tNode;
	}

	/**
	 * 由PEM格式证书内容构造X509Certificate证书对象
	 * 
	 * @param aCer
	 * @return
	 * @throws CERTException
	 */
	private static X509Certificate getX509Certificate(String aCer)
			throws SecException {

		X509Certificate tCer = null;
		CertificateFactory cf;
		try {
			cf = CertificateFactory.getInstance("X.509");

			InputStream tIn = new ByteArrayInputStream(aCer.getBytes());

			tCer = (X509Certificate) cf.generateCertificate(tIn);
		} catch (Exception e) {
			throw new SecException("构造X509证书对象失败");
		}
		return tCer;
	}

	/**
	 * 对需要签名的报文进行规范化
	 * 
	 * 
	 * @param aC14nURI
	 * @param aNode
	 * @return
	 * @throws SecException
	 */
	public static byte[] c14n(String aC14nURI, String aNode)
			throws SecException {

		LogWriter
				.debug("=======================开始规范化运算=======================");
		LogWriter.debug("aC14nURI：  " + aC14nURI);

		Canonicalizer tc14nizer = null;
		byte[] tDigestData;
		try {
			org.apache.xml.security.Init.init();
			tc14nizer = Canonicalizer.getInstance(aC14nURI);
			tDigestData = tc14nizer.canonicalize(aNode.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			LogWriter.debug(e.getMessage());
			throw new SecException("报文规范化失败");
		}

		LogWriter.debug("规范化运算结果：  " + Base64.encode(tDigestData));
		return tDigestData;
	}

	/**
	 * 软件签名
	 * 
	 * @param aKey
	 * @param aData
	 * @return
	 * @throws SecException
	 */
	private static String signBySoft(PrivateKey aKey, String aData)
			throws SecException {

		LogWriter
				.debug("=======================开始进入signBySoft签名=======================");

		String tResult;
		Cipher tCipher;

		try {
			byte[] tByte;
			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			LogWriter
					.debug("=====================addProvider========================"
							+ java.security.Security
									.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()));
			tCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			Provider[] p = null;
			p = java.security.Security.getProviders();
			for (int i = 0; i < p.length; i++) {
				LogWriter
						.debug("==========================Providers=================================");
				LogWriter.debug("====p[i].getName()======" + p[i].getName());
			}
			tCipher.init(Cipher.ENCRYPT_MODE, aKey);
			tByte = tCipher.doFinal(Base64.decode(aData));
			tResult = Base64.encode(tByte);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SecException("私钥签名失败");
		}
		LogWriter
				.debug("=======================signBySoft签名结果======================="
						+ tResult);
		return tResult;
	}

	/**
	 * 由BASE64编码的私钥数据得到PrivateKey对象
	 * 
	 * @param aPrivateKey
	 * @return
	 * @throws SecException
	 */
	private static PrivateKey getPrivateKey(String aPrivateKey)
			throws SecException {
		PrivateKey tPrivateKey = null;
		try {
			byte[] tPrivateKeyBytes = Base64.decode(aPrivateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					tPrivateKeyBytes);
			KeyFactory keyFact = KeyFactory.getInstance("RSA");
			tPrivateKey = keyFact.generatePrivate(keySpec);
		} catch (Exception e) {
			logger.error(e.getMessage());

			throw new SecException("构造私钥对象失败");
		}
		return tPrivateKey;
	}

	/**
	 * 软件实现验证签名，不调用加密机
	 * 
	 * @param aPublicKey
	 * @param tSignValue
	 * @param tDigestValue
	 * @return
	 * @throws SecException
	 */
	public static boolean verifyBySoft(PublicKey aPublicKey, byte[] tSignValue,
			byte[] tC14nValue) throws SecException {

		LogWriter
				.debug("=====================进入verifyBySoft验证签名========================");
		LogWriter.debug("=SignatureValue节点值=" + Base64.encode(tSignValue));
		LogWriter.debug("=SignedInfo节点标准化值=" + Base64.encode(tC14nValue));

		try {
			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			java.security.Signature sign = java.security.Signature.getInstance(
					"SHA1withRSA", "BC");
			sign.initVerify(aPublicKey);

			sign.update(tC14nValue);
			return sign.verify(tSignValue);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SecException("验证签名失败");
		}
	}

	/**
	 * 验证证书有效性
	 * 
	 * @param aParentCert
	 *            用来验证的证书
	 * @param aCert
	 *            被验证证书
	 * @throws SecException
	 */
	private static void CheckCertificate(String aParentCert,
			X509Certificate aCert) throws SecException {

		X509Certificate tCert = SecurityUtil.getX509Certificate(aParentCert);
		PublicKey tKey = tCert.getPublicKey();

		try {
			aCert.checkValidity();
			aCert.verify(tKey);
		} catch (Exception e) {
			throw new SecException("验证签名证书验证失败，可能是无效证书");
		}
	}

	public static PublicKey getPublicKey(String aPublicKey) {

		PublicKey publickey = null;
		KeyFactory keyFact = null;

		try {
			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64
					.decode(aPublicKey));
			keyFact = KeyFactory.getInstance("RSA", "BC");
			publickey = keyFact.generatePublic(keySpec);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return publickey;
	}

	public static String getNodeByName(String aXMLString, String tNodeName) {

		int tNodeStartIndex = aXMLString.indexOf("<" + tNodeName);

		int tNodeEndIndex = aXMLString.indexOf("</" + tNodeName + ">");
		if (tNodeEndIndex < tNodeStartIndex)
			return null;

		String tNode = aXMLString.substring(tNodeStartIndex, tNodeEndIndex + 3
				+ tNodeName.length());

		return tNode;

	}

	public static String getNodeValueByName(String aXMLString, String tNodeName) {

		int tNodeStartIndex1 = aXMLString.indexOf("<" + tNodeName);

		int tNodeStartIndex2 = aXMLString.indexOf(">", tNodeStartIndex1);

		int tNodeEndIndex = aXMLString.indexOf("</" + tNodeName + ">",
				tNodeStartIndex2);
		if (tNodeEndIndex < tNodeStartIndex1)
			return null;

		String tNode = aXMLString
				.substring(tNodeStartIndex2 + 1, tNodeEndIndex);

		return tNode;

	}

	public static String ModifyNodeFormat(String aXMLString, String tNodeName) {

		int tNodeIndex1 = aXMLString.indexOf("<" + tNodeName);

		int tNodeIndex2 = aXMLString.indexOf(tNodeName, tNodeIndex1
				+ tNodeName.length());
		if (tNodeIndex2 > 0) {
			return aXMLString;
		}

		int tNodeEndIndex = aXMLString.indexOf("/>", tNodeIndex1);
		if (tNodeEndIndex < tNodeIndex1)
			return null;

		String tNode = aXMLString.substring(0, tNodeEndIndex) + "></"
				+ tNodeName + ">"
				+ aXMLString.substring(tNodeEndIndex + 2, aXMLString.length());

		return tNode;

	}

	/**
	 * 软件实现验证签名，不调用加密机
	 * 
	 * @param aPublicKey
	 * @param aData
	 * @return String
	 * @throws SecException
	 */
	public static String RSAEncByPub_Soft(PublicKey aPublicKey, String aData)
			throws SecException {

		Cipher tCipher;

		try {
			byte[] tByte;

			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			tCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			tCipher.init(Cipher.ENCRYPT_MODE, aPublicKey);
			tByte = tCipher.doFinal(aData.getBytes());
			return Base64.encode(tByte);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SecException("验证签名失败");
		}
	}
}
