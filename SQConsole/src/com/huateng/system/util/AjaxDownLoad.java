/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-8       first release
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
package com.huateng.system.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Title: Ajax文件下载类
 * 
 * File: AjaxDownLoad.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class AjaxDownLoad extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("static-access")
	@Override
	public String execute() throws Exception {
		
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getHeader("referer") == null){
			return null;
		}
		
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			
			path = new String(path.getBytes("ISO-8859-1"),"UTF-8");
			
			String fileType = "";
			if (path.indexOf(".") != -1) {
				fileType = path.substring(path.lastIndexOf(".") + 1);
			}
			
			if ("txt".equalsIgnoreCase(fileType)){
				response.setContentType("text/plain");
			} else if ("xls".equalsIgnoreCase(fileType)){
				response.setContentType("application/vnd.ms-excel");
			} else if ("pdf".equalsIgnoreCase(fileType)){
				response.setContentType("application/pdf");
			} else {
				response.setContentType("application/octet-stream");
			}

			dis = new DataInputStream(new FileInputStream(new File(path)));
			dos = new DataOutputStream(response.getOutputStream());
			
			path = path.replace("\\", "/");
			
			if (path.indexOf("RN") != -1) {
				String rn = path.substring(path.indexOf("RN"), path.lastIndexOf("RN") + 2);
				response.setHeader("Content-Disposition", "attachment; filename=" 
						 + URLEncoder.encode(path.substring(path.lastIndexOf("/") + 1).replaceAll(rn, SysParamUtil.getParam(rn)),"UTF-8"));
			} else {
				response.setHeader("Content-Disposition", "attachment; filename=" 
						+ URLEncoder.encode(path.substring(path.lastIndexOf("/") + 1),"UTF-8"));
			}
			
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = dis.read(buffer,0 , 8192)) != -1) {
				dos.write(buffer, 0, bytesRead);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != dis) {
					dis.close();
				}
				if (null != dos) {
					dos.flush();
					dos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.ERROR;
	}
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
