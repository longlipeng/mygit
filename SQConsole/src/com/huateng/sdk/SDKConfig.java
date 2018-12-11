/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       MPI基本参数工具类
 * =============================================================================
 */
package com.huateng.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;



/**
 * 
 * @ClassName SDKConfig
 * @Description fsas_sdk配置文件fsas_sdk.properties配置信息类
 * @date 2016-7-22 下午4:04:55
 * 声明：以下代码只是为了方便机构测试而提供的样例代码，机构可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class SDKConfig {
	public static final String FILE_NAME = "fsas_sdk.properties";

	/** frontUrl  */
	private String frontUrl;
	/** backUrl  */
	private String backUrl;
	
	/** 后台请求URL. */
	private String backRequestUrl;
	/** 签名证书路径. */
	private String signCertPath;
	/** 签名证书密码. */
	private String signCertPwd;
	/** 签名证书类型. */
	private String signCertType;
	/** Rsa中级证书路径  */
	private String rsaMiddleCertPath;
	/** Rsa根证书路径  */
	private String rsaRootCertPath;
	/** Sm2中级证书路径  */
	private String sm2MiddleCertPath;
	/** Sm2根证书路径  */
	private String sm2RootCertPath;
	
	/** 安全密钥(SHA256和SM3计算时使用) */
	private String secureKey;
	/** 验证签名公钥证书目录. */
	private String validateCertDir;
	/** 是否验证验签证书CN，除了false都验  */
	private boolean ifValidateCNName = true;
	/** 是否验证https证书，默认都不验  */
	private boolean ifValidateRemoteCert = false;
	/** signMethod，没配按01吧  */
	private String signMethod = "01";
	/** version */
	private String version = "1.0.0";
	
	
	/** 配置文件中的后台URL常量. */
	public static final String SDK_BACK_URL = "fsassdk.backTransUrl";
	/** 配置文件中签名证书路径常量. */
	public static final String SDK_SIGNCERT_PATH = "fsassdk.signCert.path";
	/** 配置文件中签名证书密码常量. */
	public static final String SDK_SIGNCERT_PWD = "fsassdk.signCert.pwd";
	/** 配置文件中签名证书类型常量. */
	public static final String SDK_SIGNCERT_TYPE = "fsassdk.signCert.type";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_RSA_ROOTCERT_PATH = "fsassdk.rsa.rootCert.path";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_RSA_MIDDLECERT_PATH = "fsassdk.rsa.middleCert.path";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_SM2_ROOTCERT_PATH = "fsassdk.sm2.rootCert.path";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_SM2_MIDDLECERT_PATH = "fsassdk.sm2.middleCert.path";
	/** 配置是否需要验证验签证书CN，除了false之外的值都当true处理 */
	public static final String SDK_IF_VALIDATE_CN_NAME = "fsassdk.ifValidateCNName";
	/** 配置是否需要验证https证书，除了true之外的值都当false处理 */
	public static final String SDK_IF_VALIDATE_REMOTE_CERT = "fsassdk.ifValidateRemoteCert";
	/** signmethod */
	public static final String SDK_SIGN_METHOD ="fsassdk.signMethod";
	/** version */
	public static final String SDK_VERSION = "fsassdk.version";
	/** 配置文件中验证签名证书目录常量. */
	public static final String SDK_VALIDATECERT_DIR = "fsassdk.validateCert.dir";
	/** 配置文件中安全密钥 */
	public static final String SDK_SECURITYKEY = "fsassdk.secureKey";
	/** 后台通知地址  */
	public static final String SDK_BACKURL = "fsassdk.backUrl";
	/** 前台通知地址  */
	public static final String SDK_FRONTURL = "fsassdk.frontUrl";
	
	/** 操作对象. */
	private static SDKConfig config = new SDKConfig();
	/** 属性文件对象. */
	private Properties properties;

	private SDKConfig() {
		super();
	}

	/**
	 * 获取config对象.
	 * @return
	 */
	public static SDKConfig getConfig() {
		return config;
	}

	/**
	 * 从properties文件加载
	 * 
	 * @param rootPath
	 *            不包含文件名的目录.
	 */
	public void loadPropertiesFromPath(String rootPath) {
		if (StringUtils.isNotBlank(rootPath)) {
			LogUtil.writeLog("从路径读取配置文件: " + rootPath+File.separator+FILE_NAME);
			File file = new File(rootPath + File.separator + FILE_NAME);
			InputStream in = null;
			if (file.exists()) {
				try {
					in = new FileInputStream(file);
					properties = new Properties();
					properties.load(in);
					loadProperties(properties);
				} catch (FileNotFoundException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				} finally {
					if (null != in) {
						try {
							in.close();
						} catch (IOException e) {
							LogUtil.writeErrorLog(e.getMessage(), e);
						}
					}
				}
			} else {
				// 由于此时可能还没有完成LOG的加载，因此采用标准输出来打印日志信息
				LogUtil.writeErrorLog(rootPath + FILE_NAME + "不存在,加载参数失败");
			}
		} else {
			loadPropertiesFromSrc();
		}

	}

	/**
	 * 从classpath路径下加载配置参数
	 */
	public void loadPropertiesFromSrc() {
		InputStream in = null;
		try {
			LogUtil.writeLog("从classpath: " +SDKConfig.class.getClassLoader().getResource("").getPath()+" 获取属性文件"+FILE_NAME);
			in = SDKConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
			if (null != in) {
				properties = new Properties();
				try {
					properties.load(in);
				} catch (IOException e) {
					throw e;
				}
			} else {
				LogUtil.writeErrorLog(FILE_NAME + "属性文件未能在classpath指定的目录下 "+SDKConfig.class.getClassLoader().getResource("").getPath()+" 找到!");
				return;
			}
			loadProperties(properties);
		} catch (IOException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 根据传入的 {@link #load(java.util.Properties)}对象设置配置参数
	 * 
	 * @param pro
	 */
	public void loadProperties(Properties pro) {
		LogUtil.writeLog("开始从属性文件中加载配置项");
		String value = null;
		
		value = pro.getProperty(SDK_SIGNCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertPath = value.trim();
			LogUtil.writeLog("配置项：私钥签名证书路径==>"+SDK_SIGNCERT_PATH +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_SIGNCERT_PWD);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertPwd = value.trim();
			LogUtil.writeLog("配置项：私钥签名证书密码==>"+SDK_SIGNCERT_PWD +" 已加载");
		}
		value = pro.getProperty(SDK_SIGNCERT_TYPE);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertType = value.trim();
			LogUtil.writeLog("配置项：私钥签名证书类型==>"+SDK_SIGNCERT_TYPE +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_VALIDATECERT_DIR);
		if (!SDKUtil.isEmpty(value)) {
			this.validateCertDir = value.trim();
			LogUtil.writeLog("配置项：验证签名证书路径(这里配置的是目录，不要指定到公钥文件)==>"+SDK_VALIDATECERT_DIR +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_SECURITYKEY);
		if (!SDKUtil.isEmpty(value)) {
			this.secureKey = value.trim();
		}

		value = pro.getProperty(SDK_BACK_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.backRequestUrl = value.trim();
		}

		value = pro.getProperty(SDK_RSA_ROOTCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.rsaRootCertPath = value.trim();
		}
		value = pro.getProperty(SDK_RSA_MIDDLECERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.rsaMiddleCertPath = value.trim();
		}
		
		value = pro.getProperty(SDK_SM2_ROOTCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.sm2RootCertPath = value.trim();
		}
		value = pro.getProperty(SDK_SM2_MIDDLECERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.sm2MiddleCertPath = value.trim();
		}

		value = pro.getProperty(SDK_IF_VALIDATE_CN_NAME);
		if (!SDKUtil.isEmpty(value)) {
			if( SDKConstants.FALSE_STRING.equals(value.trim()))
					this.ifValidateCNName = false;
		}
		
		value = pro.getProperty(SDK_IF_VALIDATE_REMOTE_CERT);
		if (!SDKUtil.isEmpty(value)) {
			if( SDKConstants.TRUE_STRING.equals(value.trim()))
					this.ifValidateRemoteCert = true;
		}
		
		value = pro.getProperty(SDK_SIGN_METHOD);
		if (!SDKUtil.isEmpty(value)) {
			this.signMethod = value.trim();
		}
		
		value = pro.getProperty(SDK_SIGN_METHOD);
		if (!SDKUtil.isEmpty(value)) {
			this.signMethod = value.trim();
		}
		value = pro.getProperty(SDK_VERSION);
		if (!SDKUtil.isEmpty(value)) {
			this.version = value.trim();
		}
		value = pro.getProperty(SDK_FRONTURL);
		if (!SDKUtil.isEmpty(value)) {
			this.frontUrl = value.trim();
		}
		value = pro.getProperty(SDK_BACKURL);
		if (!SDKUtil.isEmpty(value)) {
			this.backUrl = value.trim();
		}

	}
   
	public String getBackRequestUrl() {
		return backRequestUrl;
	}
	public void setBackRequestUrl(String backRequestUrl) {
		this.backRequestUrl = backRequestUrl;
	}
  
	public String getSignCertPath() {
		return signCertPath;
	}
	public void setSignCertPath(String signCertPath) {
		this.signCertPath = signCertPath;
	}

	public String getSignCertPwd() {
		return signCertPwd;
	}

	public void setSignCertPwd(String signCertPwd) {
		this.signCertPwd = signCertPwd;
	}

	public String getSignCertType() {
		return signCertType;
	}
	public void setSignCertType(String signCertType) {
		this.signCertType = signCertType;
	}

	public boolean isIfValidateCNName() {
		return ifValidateCNName;
	}
	public void setIfValidateCNName(boolean ifValidateCNName) {
		this.ifValidateCNName = ifValidateCNName;
	}

	public boolean isIfValidateRemoteCert() {
		return ifValidateRemoteCert;
	}
	public void setIfValidateRemoteCert(boolean ifValidateRemoteCert) {
		this.ifValidateRemoteCert = ifValidateRemoteCert;
	}
//
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
//
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	//
	public String getRsaRootCertPath() {
		return rsaRootCertPath;
	}
	public void setRsaRootCertPath(String rsaRootCertPath) {
		this.rsaRootCertPath = rsaRootCertPath;
	}
//	
	public String getRsaMiddleCertPath() {
		return rsaMiddleCertPath;
	}
	public void setRsaMiddleCertPath(String rsaMiddleCertPath) {
		this.rsaMiddleCertPath = rsaMiddleCertPath;
	}
	
	public String getSm2RootCertPath() {
		return sm2RootCertPath;
	}
	public void setSm2RootCertPath(String sm2RootCertPath) {
		this.sm2RootCertPath = sm2RootCertPath;
	}
//	
	public String getSm2MiddleCertPath() {
		return sm2MiddleCertPath;
	}
	public void setSm2MiddleCertPath(String sm2MiddleCertPath) {
		this.sm2MiddleCertPath = sm2MiddleCertPath;
	}
	//
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public String getValidateCertDir() {
		return validateCertDir;
	}

	public void setValidateCertDir(String validateCertDir) {
		this.validateCertDir = validateCertDir;
	}
	
	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String securityKey) {
		this.secureKey = securityKey;
	}
	
	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
}
