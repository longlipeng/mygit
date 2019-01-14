package com.huateng.framework.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.LockedException;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.VerificationCodeUtil;
import com.huateng.framework.webservice.service.WebServiceClientService;
import com.huateng.framework.webservice.service.impl.WebServiceClientServiceImpl;

/**
 * Servlet implementation class VerificationCode
 */
public class VerificationCodeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		WebServiceClientService webServiceClientService=new WebServiceClientServiceImpl();
		String msg="短信验证码已发送";
		try {
			String userName=(String) req.getParameter("username");
			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode(ConstCode.USER_SERVICE_FIND_USER);
			OperationRequest operationRequest = new OperationRequest();
			operationRequest.setTxnvo(userName);
			
			OperationResult operationResult = webServiceClientService.process(operationCtx,
						operationRequest);
			if((userName+"userName error!").equals(operationResult.getErrMessage())){
				msg="用户名不存在!";
			}else if((userName+"Invalid User!").equals(operationResult.getErrMessage())){
				msg="无效的用户！";
			}else{
				UserDTO userDTO = (UserDTO) operationResult.getDetailvo();
				//手机号暂存字段 DepartmentId
				if (StringUtils.isEmpty(userDTO.getDepartmentId())){
					msg="用户手机号为空，请联系管理人员!";
				}else{
					VerificationCodeUtil util=new VerificationCodeUtil();
					util.sendMsg(userDTO.getDepartmentId(),userName);
				}
				
			}
		} catch (BizServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="获取短信验证码错误！请联系管理人员";
		}
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter writer = resp.getWriter();
			resp.setHeader("Cache-Control",
					"no-store,private,post-check=0,pre-check=0,max-age=0");
			resp.setHeader("Pragma", "no-cache");
			resp.setDateHeader("Expires", -1);
			writer.write(msg);
			writer.flush();
			writer.close();
		}

}
