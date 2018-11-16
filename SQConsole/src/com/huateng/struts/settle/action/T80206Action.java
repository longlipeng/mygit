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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.huateng.bo.base.T10202BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FtpUtil;
import com.huateng.system.util.SysParamUtil;
import com.huateng.system.util.TarBuilder;
import com.jcraft.jsch.ChannelSftp;
import tools.SFTPClientProvider;

/**
 * Title: 清算报表
 * 
 * File: T80206Action.java
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
public class T80206Action extends BaseSupport{

	private static final long serialVersionUID = 1L;
	T10202BO t10202BO = (T10202BO) ContextUtil.getBean("T10202BO");
	
	public boolean download3;
	public String downloadYQ() {
		System.out.println("reportName:"+reportName);
		System.out.println("date"+date);
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD);
		basePath += date.substring(0, 6);
		basePath += "/";
//		basePath += "\\";
		SFTPClientProvider provider = new SFTPClientProvider();
		ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("YINQI_NAME"), SysParamUtil.getParam("YINQI_PWD"));
		if (reportName.equals("1")) {
			String str = "1支付机构客户备付金信息统计报表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
						} else {
							log("文件		:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							System.out.println("replaceAll："+replaceAll);
							System.out.println("=====================================================");
							System.out.println("replaceAll.substring(0, str.length())"+replaceAll.substring(0, str.length()));
							System.out.println("replaceAll.substring(0, str.length()) == str："+replaceAll.substring(0, str.length()) == str);
							System.out.println("=====================================================");
							System.out.println("replaceAll.substring(0, str.length()).equals(str)："+replaceAll.substring(0, str.length()).equals(str));
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("2")) {
			String str = "2支付机构客户备付金出金业务明细表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
							
						} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("3")) {
			String str = "3支付机构客户备付金业务实际出金明细表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("4")) {
			String str = "4支付机构客户资金账户转账业务统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("5")) {
			String str = "5支付机构客户资金账户余额统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("6")) {
			String str = "6支付机构招商银行特殊业务明细表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return "文件不存在";
			}
		}else if (reportName.equals("7")) {
			String str = "7支付机构现金购卡业务统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("8")) {
			String str = "8支付机构预付卡现金赎回业务统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("9")) {
			String str = "9支付机构招商银行客户备付金业务未达账项统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("10")) {
			String str = "10支付机构招商银行客户备付金业务未达账项分析表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("11")) {
			String str = "11支付机构客户资金账户余额变动调节表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("12")) {
			String str = "12支付机构客户资金账户余额试算表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("13")) {
			String str = "13预付卡发行企业备付金账户中售卡押金统计表";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {

							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录："+ basePath);
									System.out.println("下载的文件："+ absolutePath);
									System.out.println("存在本地的路径："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("14")) {
			//14招商银行汇明商务服务有限公司支付机构备付金
			String str = "14招商银行汇明商务服务有限公司支付机构备付金";
			File file = new File(basePath);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
					return returnService("文件夹是空的!");
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {} else {
							log("文件:" + file2.getAbsolutePath());
							String absolutePath = file2.getAbsolutePath();
							String replaceAll = absolutePath.replaceAll(basePath, "");
							System.out.println("=======================");
							System.out.println("下载目录："+ basePath);
							System.out.println("下载的文件："+ absolutePath);
							System.out.println("存在本地的路径：" + replaceAll);
							System.out.println("=======================");
							System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
							System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
							if(replaceAll.substring(0, str.length()).equals(str)){
								try {
									System.out.println("==========================");
									System.out.println("下载目录1："+ basePath);
									System.out.println("下载的文件1："+ absolutePath);
									System.out.println("存在本地的路径1："+ replaceAll);
									download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
								} catch (Exception e) {
									log(e.getMessage());
									return returnService("SFTP 到报表服务器异常");	
								}
								if (download3) {
									return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
								}else {
									return returnService("您所请求的报表文件不存在!");
								}
							}
						
						}
					}
				}
			} else {
				log("文件不存在!");
				return returnService("文件不存在");
			}
		}else if (reportName.equals("15")) {
			//15银行客户备付金入金业务调节表
			String str = "15银行客户备付金入金业务调节表";
			File file = new File(basePath);
			if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return returnService("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						System.out.println("=======================");
						System.out.println("下载目录："+ basePath);
						System.out.println("下载的文件："+ absolutePath);
						System.out.println("存在本地的路径：" + replaceAll);
						System.out.println("=======================");
						System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
						System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
						if(replaceAll.substring(0, str.length()).equals(str)){
							try {
								System.out.println("==========================");
								System.out.println("下载目录1："+ basePath);
								System.out.println("下载的文件1："+ absolutePath);
								System.out.println("存在本地的路径1："+ replaceAll);
								download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
							} catch (Exception e) {
								log(e.getMessage());
								return returnService("SFTP 到报表服务器异常");	
							}
							if (download3) {
								return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
							}else {
								return returnService("您所请求的报表文件不存在!");
							}
						}
					
					}
				}
			}
			} else {
			log("文件不存在!");
			return returnService("文件不存在");
			}
		}else if (reportName.equals("16")) {
			//16备付金新增分银行账户的表1-2
			String str = "16备付金新增分银行账户的表1-2";
			File file = new File(basePath);
			if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return returnService("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						System.out.println("=======================");
						System.out.println("下载目录："+ basePath);
						System.out.println("下载的文件："+ absolutePath);
						System.out.println("存在本地的路径：" + replaceAll);
						System.out.println("=======================");
						System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
						System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
						if(replaceAll.substring(0, str.length()).equals(str)){
							try {
								System.out.println("==========================");
								System.out.println("下载目录1："+ basePath);
								System.out.println("下载的文件1："+ absolutePath);
								System.out.println("存在本地的路径1："+ replaceAll);
								download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
							} catch (Exception e) {
								log(e.getMessage());
								return returnService("SFTP 到报表服务器异常");	
							}
							if (download3) {
								return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
							}else {
								return returnService("您所请求的报表文件不存在!");
							}
						}
					
					}
				}
			}
			} else {
			log("文件不存在!");
			return returnService("文件不存在");
			}
		}else if (reportName.equals("17")) {
			//  17备付金新增支付机构管理账户情况统计_201702
			String str = "17备付金新增支付机构管理账户情况统计";
			File file = new File(basePath);
			if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return returnService("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						System.out.println("=======================");
						System.out.println("下载目录："+ basePath);
						System.out.println("下载的文件："+ absolutePath);
						System.out.println("存在本地的路径：" + replaceAll);
						System.out.println("=======================");
						System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
						System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
						if(replaceAll.substring(0, str.length()).equals(str)){
							try {
								System.out.println("==========================");
								System.out.println("下载目录1："+ basePath);
								System.out.println("下载的文件1："+ absolutePath);
								System.out.println("存在本地的路径1："+ replaceAll);
								download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
							} catch (Exception e) {
								log(e.getMessage());
								return returnService("SFTP 到报表服务器异常");	
							}
							if (download3) {
								return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
							}else {
								return returnService("您所请求的报表文件不存在!");
							}
						}
					
					}
				}
			}
			} else {
			log("文件不存在!");
			return returnService("文件不存在");
			}
		}else if (reportName.equals("18")) {
			//18备付金新增预付卡商户白名单表
			String str = "18备付金新增预付卡商户白名单表_"+date;
			File file = new File(basePath);
			if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return returnService("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						System.out.println("=======================");
						System.out.println("下载目录："+ basePath);
						System.out.println("下载的文件："+ absolutePath);
						System.out.println("存在本地的路径：" + replaceAll);
						System.out.println("=======================");
						System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
						System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
						if(replaceAll.substring(0, str.length()).equals(str)){
							try {
								System.out.println("==========================");
								System.out.println("下载目录1："+ basePath);
								System.out.println("下载的文件1："+ absolutePath);
								System.out.println("存在本地的路径1："+ replaceAll);
								download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
							} catch (Exception e) {
								log(e.getMessage());
								return returnService("SFTP 到报表服务器异常");	
							}
							if (download3) {
								return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
							}else {
								return returnService("您所请求的报表文件不存在!");
							}
						}
					
					}
				}
			}
			} else {
			log("文件不存在!");
			return returnService("文件不存在");
			}
		}else if (reportName.equals("19")) {
			//19支付机构合作行每日账户余额列表
			String str = "19支付机构合作行每日账户余额列表";
			File file = new File(basePath);
			if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return returnService("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {} else {
						log("文件:" + file2.getAbsolutePath());
						String absolutePath = file2.getAbsolutePath();
						String replaceAll = absolutePath.replaceAll(basePath, "");
						System.out.println("=======================");
						System.out.println("下载目录："+ basePath);
						System.out.println("下载的文件："+ absolutePath);
						System.out.println("存在本地的路径：" + replaceAll);
						System.out.println("=======================");
						System.out.println("可能下载的文件："+replaceAll.substring(0, str.length()));
						System.out.println("replaceAll.substring(0, str.length()).equals(str)============="+replaceAll.substring(0, str.length()).equals(str));
						if(replaceAll.substring(0, str.length()).equals(str)){
							try {
								System.out.println("==========================");
								System.out.println("下载目录1："+ basePath);
								System.out.println("下载的文件1："+ absolutePath);
								System.out.println("存在本地的路径1："+ replaceAll);
								download3 = provider.download(basePath, absolutePath, replaceAll, sftp);
							} catch (Exception e) {
								log(e.getMessage());
								return returnService("SFTP 到报表服务器异常");	
							}
							if (download3) {
								return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + replaceAll );
							}else {
								return returnService("您所请求的报表文件不存在!");
							}
						}
					
					}
				}
			}
			} else {
			log("文件不存在!");
			return returnService("文件不存在");
			}
		}else if (reportName.equals("20")) {
			String basePath1 = SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1);//存放加密文件的目录
			String basePath2 = basePath1 + date + "/";
			File basePath2File = new File(basePath2);
			//如果文件夹不存在则创建  
			if  (!basePath2File.exists()  && !basePath2File.isDirectory()) {     
				return returnService("该包未上传！");
			}
			System.out.println("要压缩的目录："+basePath2 + "	输出目录：" + SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1));
			
			String tarName = "YQ_"+date+".tar";
			try {
				TarBuilder tarBuilder = new TarBuilder(basePath2, SysParamUtil.getParam(SysParamConstants.FILE_PATH_YINQI_UPLOAD1), tarName, false);
				tarBuilder.build();
			} catch (Exception e) {
				e.printStackTrace();
				return returnService("打包失败");
			}
			try {
				download3 = provider.download(basePath1, basePath1 + tarName, tarName, sftp);
				if (download3) {
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + tarName );
				}else {
					return returnService("您所请求的报表文件不存在!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return returnService("下载失败！");
			}
			
		}
		
		return returnService("没有该文件！");
	}
	
	
	/**
	 * 下载报表
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String download(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += date;
			path += "/";
			path += brhId;
			path += "/";
			path += reportName + brhId + "_" + date;
			path += ".txt";
			
			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
			
			File down = new File(path);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	/**
	 * 下载所有报表
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String downloadreport(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey(reportName);
			cstSysParamPK.setOwner("REPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);
			//报表名称
			String filename1=cstSysParam.getDescr();
			//路径
			String path1=cstSysParam.getReserve();
			String birId=getOperator().getOprBrhId();
			if(path1.contains("xxxx")){   
			   path1=path1.replace("xxxx", birId);
			}
			//拼写路径 /home/posp/report/riskafterYYYYMMDD.txt
			filename1=filename1.replace("YYYYMMDD", date);
			filename1=filename1.replace("$$$$", operator.getOprBrhId());
			String path2=path1+date+"/";
			String path =path2+filename1;
			
			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"),
					SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
			try{
				ftp.connectServer();
				/*if(!ftp.ftpFileIsExist(path)){
					ftp.closeServer();
					return returnService("未找到该报表文件");					
				}*/
				ftp.downloadFile(path, SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
				ftp.closeServer();
				return returnService("FTP 到报表服务器异常");
			}
			File down = new File(SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
	 * 下载所有报表
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String downloadchannelreport(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey(reportName);
			cstSysParamPK.setOwner("CHANNELREPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);
			//报表名称
			String filename1=cstSysParam.getDescr();
			//路径
//			String path1=cstSysParam.getReserve();
			String path1 = SysParamUtil.getParam("FILE_PATH_SETTLE_REPORT")+date+"/";
			String birId=getOperator().getOprBrhId();
			if(path1.contains("xxxx")){   
			   path1=path1.replace("xxxx", birId);
			}
			if(path1.contains("YYYYMMDD")){   
				   path1=path1.replace("YYYYMMDD", date);
			}
			//拼写路径 /home/posp/online/file/YYYYMMDD/riskResultReport_YYYYMMDD.txt
			String date1 = date.substring(0, 4) + "-"+date.substring(4, 6)+"-"+date.substring(6, 8);
			
			filename1=filename1.replace("YYYYMMDD", date);
			filename1=filename1.replace("$$$$", operator.getOprBrhId());
			/*String path2=path1+date+"/";
			String path =path2+filename1;*/
			String path = path1+filename1;
			
			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
//			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"), SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			
			try{
				download2 = provider.download(path1, path1+filename1, "C:\\"+filename1, sftp);

//				ftp.connectServer();
				/*if(!ftp.ftpFileIsExist(path)){
					ftp.closeServer();
					return returnService("未找到该报表文件");					
				}*/
//				ftp.downloadFile(path, SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
//				ftp.closeServer();
				return returnService("SFTP 到报表服务器异常");
			}
			if (download2) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
//			File down = new File(SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			if (down.exists()) {
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			} else {
//				return returnService("您所请求的报表文件不存在!");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
	 * 商户反洗钱黑名单结果文件下载
	 * @return
	 */
	public String uploadTxt(){
		try {
			if (StringUtil.isNull(date)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			
			//文件名
			String filename1 = "backlist_mcht_YYYYMMDD.txt";
			filename1 = filename1.replace("YYYYMMDD", date);
			
			//文件路径
			String path = "/home/posp/online/file/backlist/backlist_mcht_YYYYMMDD.txt";
			path=path.replace("YYYYMMDD", date);
//			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
//			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"), SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			
			try{//String directory, String downloadFile, String saveFile, ChannelSftp sftp ---- 下载目录，下载文件 存放本地路径
				download2 = provider.download(path, filename1, "C:\\"+filename1, sftp);

//				ftp.connectServer();
				/*if(!ftp.ftpFileIsExist(path)){
					ftp.closeServer();
					return returnService("未找到该报表文件");					
				}*/
//				ftp.downloadFile(path, SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
//				ftp.closeServer();
				return returnService("SFTP 到报表服务器异常");
			}
			if (download2) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
//			File down = new File(SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			if (down.exists()) {
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			} else {
//				return returnService("您所请求的报表文件不存在!");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
     * 字符左补零
     * @param code
     * @param len
     * @return
     */
    public static String codeAddOne(String code, int len){
        Integer intHao = Integer.parseInt(code);
//        intHao++;
        String strHao = intHao.toString();
        while (strHao.length() < len) {
            strHao = "0" + strHao;
        }
        return strHao;
    }
    
	/**
	 * 中石油对账文件下载
	 */
	public String downloadPetroReport(){
		try {
			/*if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}*/
			/*CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey("dataSource_id");
			cstSysParamPK.setOwner("CHANNELREPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);*/
			System.out.println("日期："+date);
			//报表名称
			String filename1=SysParamUtil.getParam(SysParamConstants.ZSYTXNNAME);
			//路径
//			String path1=cstSysParam.getReserve();
			String path1 = SysParamUtil.getParam("FILE_PATH_REPORT_FTP_SERVER");
			String birId=getOperator().getOprBrhId();
			if(path1.contains("xxxx")){   
			   path1=path1.replace("xxxx", birId);
			}
			if(path1.contains("YYYYMMDD")){   
				   path1=path1.replace("YYYYMMDD", date);
			}
			//拼写路径 /home/posp/online/file/YYYYMMDD/riskResultReport_YYYYMMDD.txt
			int ri = Integer.parseInt(date.substring(6, 8))-1;
//			int length = String.valueOf(ri).length();
			String codeAddOne = codeAddOne(ri+"", 2);
			
//			String date1 = date.substring(0, 4) + "_"+date.substring(4, 6)+"_"+ codeAddOne;
			
			filename1=filename1.replace("YYYYMMDD", date);
			filename1=filename1.replace("$$$$", operator.getOprBrhId());
			String path = path1+filename1;
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy_MM_dd");
	        Date dt=sdf.parse(date.toString().trim());
	        Calendar rightNow = Calendar.getInstance();
	        rightNow.setTime(dt);
	        rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
	        Date dt1=rightNow.getTime();
	        String date1 = sdf1.format(dt1);

			filename1 = filename1+date1+".csv";
			path = path.replace("\\", "/");
			log("GET FILE:" + path);
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			try{
				log("中石油报表:" + "path1:"+path1+"------"+filename1);
				download2 = provider.download(path1, filename1, "C:\\"+filename1, sftp);
			}catch(Exception e){
				e.printStackTrace();
				return returnService("SFTP 到报表服务器异常");
			}
			if (download2) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	
	/**
	 * 下载所有报表
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String downloadreportDYF(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey(reportName);
			cstSysParamPK.setOwner("REPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);
			//报表名称
			String filename1=cstSysParam.getDescr();
			//路径
//			String path1=cstSysParam.getReserve();
			String path1 = SysParamUtil.getParam("FILE_PATH_REPORT_FTP_SERVER");
			String birId=getOperator().getOprBrhId();
			if(path1.contains("xxxx")){   
			   path1=path1.replace("xxxx", birId);
			}
			if(path1.contains("YYYYMMDD")){   
				   path1=path1.replace("YYYYMMDD", date);
				}
			//拼写路径 /home/posp/online/file/YYYYMMDD/report/riskResultReport_YYYYMMDD.csv
			filename1=filename1.replace("YYYYMMDD", date);
			filename1=filename1.replace("$$$$", operator.getOprBrhId());
			/*String path2=path1+date+"/";
			String path =path2+filename1;*/
			String path = path1+filename1;
			
			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
//			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"),
//					SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			
			try{
				download = provider.download(path1, path1+filename1, "C:\\"+filename1, sftp);
				
//				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
//				ftp.closeServer();
				return returnService("SFTP 到报表服务器异常");
			}
//			File down = new File(SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			File down = new File(path1+filename1);
			if (download) {
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path1+filename1);
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
	 * 打包下载
	 * 
	 * @return
	 */
	public String downloadAll(){
		
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(brhId)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			reportName = "settle";
			
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_REPORT);
			path += date;
			path += "/";
			path += brhId;
			path += "/";
			path += date + "_" + brhId + "_" + reportName;
			path += ".tar";
			
			path = path.replace("\\", "/");

			log("GET FILE:" + path);
			
			File down = new File(path);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	/**
	 * 下载黑名单报表
	 * 
	 * @return
	 * 2018-1-26下午02:23:26
	 */
	public String downloadBlackListFile(){
		
		System.out.print(reportName);
		try {
			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			/*CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey(reportName);
			cstSysParamPK.setOwner("REPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);*/
			//报表名称
			String filename1="blacklist_mcht_YYYYMMDD.txt";
			//路径
//			String path1=cstSysParam.getReserve();
//			String path1 = SysParamUtil.getParam("FILE_PATH_REPORT_FTP_SERVER");
			String path1 = SysParamUtil.getParam("FILE_PATH_REPORT_BLACKLIST_FTP_SERVER");
			String birId=getOperator().getOprBrhId();
			
			if(path1.contains("YYYYMMDD")){   
				   path1=path1.replace("YYYYMMDD", date);
				}
			//拼写路径 /home/posp/online/file/YYYYMMDD/report/riskResultReport_YYYYMMDD.csv
			filename1=filename1.replace("YYYYMMDD", date);
		
			/*String path2=path1+date+"/";
			String path =path2+filename1;*/
			String path = path1+filename1;
			
			path = path.replace("\\", "/");
			
			log("GET FILE:" + path);
//			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),SysParamUtil.getParam("FTPPORT"),
//					SysParamUtil.getParam("FTPUSERNAME"),SysParamUtil.getParam("FTPPASSWORD"));
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			
			try{
				//filename1=filename1.replace("blacklist_mcht", "商户反洗钱黑名单报表");
				download = provider.download(path1, path1+filename1, "C:\\"+filename1, sftp);
				
//				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
//				ftp.closeServer();
				return returnService("SFTP 到报表服务器异常");
			}
//			File down = new File(SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
//			File down = new File(path1+filename1);
			if (download) {
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path1+filename1);
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
//				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("FILE_PATH_REPORT_FTP_DOWNLOAD")+date+"/"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	private String brhId;
	
	private String date;
	
	private String reportType;
	
	private String reportName;
	private boolean download;
	private boolean download2;
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}
	
	
	
	

}
