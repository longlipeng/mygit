package com.huateng.framework.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.listener.batch.BatchCheckQuartz;

public class MakeUpServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(MakeUpServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			logger.info("#################### MAKE UP ####################");
			String batchNo = (String)req.getParameter("batchNo");
			String batchType = (String)req.getParameter("batchType");
			logger.info("batchNo:"+batchNo);
			logger.info("batchType:"+batchType);
			BatchCheckQuartz batchCheckQuartz =(BatchCheckQuartz)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("batchCheckQuartz");
			if(null != batchNo && !"".equals(batchNo.trim()) && null != batchType && !"".equals(batchType.trim()) ){
				batchCheckQuartz.addSingleTask(batchNo, batchType);
			}
		} catch (BizServiceException b) {
			// TODO Auto-generated catch block
			logger.error(b.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.logger.error(e.getMessage());
		}
		logger.info("#################### END MAKE UP ####################");
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
