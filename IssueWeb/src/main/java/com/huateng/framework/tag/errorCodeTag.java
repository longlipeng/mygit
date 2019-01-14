package com.huateng.framework.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.huateng.framework.constant.RspCodeMap;


public class errorCodeTag extends BodyTagSupport{
	
	private Logger logger = Logger.getLogger(errorCodeTag.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//错误码
	private String respCode;	
	

	@Override
	public int doStartTag() throws JspException {
		String strHtml="";
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			strHtml = rspCodeMap.get(respCode);
			JspWriter writer = pageContext.getOut();

			try {
				writer.print(strHtml);
			} catch (IOException e) {
				this.logger.error(e.getMessage());
				throw new JspException(e.toString());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new JspException(e.toString());
		}
		return BodyTagSupport.EVAL_BODY_TAG;
	}
	
	

	@Override
	public int doEndTag() throws JspException {
		return BodyTagSupport.EVAL_BODY_TAG;
	}



	public String getRespCode() {
		return respCode;
	}



	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	
	
	
}
