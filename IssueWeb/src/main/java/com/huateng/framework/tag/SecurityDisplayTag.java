package com.huateng.framework.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.huateng.framework.security.model.User;

/**
 * 根据授权情况判断是否显示jsp控件的标签
 * 
 * @author liming.feng
 *
 */
public class SecurityDisplayTag extends BodyTagSupport {
	
	private Logger logger = Logger.getLogger(SecurityDisplayTag.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4483361655253585796L;
	
	/**
	 * 控件中使用的url的id
	 */
	private String urlId;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		try {
			// 如果url置空，允许显示
			if (urlId == null) return EVAL_BODY_INCLUDE;
			
			String [] ids=urlId.split(",");
			// 从用户信息中取出全部已授权的url
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			for (ResourceDTO resource : user.getResourceDTOList()) {
				for(int i=0;i<ids.length;i++){
				if (ids[i].equals(resource.getResourceId())) {
					logger.info(resource.getResourceId());
					return EVAL_BODY_INCLUDE;
				}
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new JspException(e.toString());
		}
		return SKIP_BODY;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

}
