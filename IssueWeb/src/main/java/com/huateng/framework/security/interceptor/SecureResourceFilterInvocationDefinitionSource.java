package com.huateng.framework.security.interceptor;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import com.allinfinance.univer.system.role.dto.ResourceDTO;

/**
 * 资源访问拦截器
 * 
 * @author liming.feng
 * 
 */
public class SecureResourceFilterInvocationDefinitionSource implements
		FilterInvocationDefinitionSource, InitializingBean {

	private UrlMatcher urlMatcher;

	private boolean useAntPath = true;

	private boolean lowercaseComparisons = true;

	/**
	 * @param useAntPath
	 *            the useAntPath to set
	 */
	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	/**
	 * @param lowercaseComparisons
	 */
	public void setLowercaseComparisons(boolean lowercaseComparisons) {
		this.lowercaseComparisons = lowercaseComparisons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		// default url matcher will be RegexUrlPathMatcher
		this.urlMatcher = new RegexUrlPathMatcher();

		if (useAntPath) { // change the implementation if required
			this.urlMatcher = new AntUrlPathMatcher();
		}

		// Only change from the defaults if the attribute has been set
		if (lowercaseComparisons) {
			if (!useAntPath) {
				((RegexUrlPathMatcher) this.urlMatcher)
						.setRequiresLowerCaseUrl(true);
			}
		} else {
			if (useAntPath) {
				((AntUrlPathMatcher) this.urlMatcher)
						.setRequiresLowerCaseUrl(false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getAttributes(java.lang.Object)
	 */
	public ConfigAttributeDefinition getAttributes(Object filter)
			throws IllegalArgumentException {
		FilterInvocation filterInvocation = (FilterInvocation) filter;
		String requestURI = filterInvocation.getRequestUrl();
		
		String requestURISimple = requestURI.replace(".action", "");
		
		//得到所有受到保护的资源
		List<ResourceDTO> urlAuthorities = this
				.getUrlAuthorities(filterInvocation);
		
		if (urlAuthorities == null) return null;
		
		//把保护的资源跟requestURI匹配，看是否存在其中
		String grantedAuthorities = null;
		
		for (ResourceDTO r : urlAuthorities) {
			
			String url = r.getResourceUrl();
			if (url == null)
				continue;
			
			url = url.replace(".action", "");
			if (urlMatcher.pathMatchesUrl(url, requestURISimple)) {
				
				String str = requestURI;
				if (r.getResourceUrl().indexOf(".action") != -1
						&& requestURI.indexOf(".action") == -1) {
					if (requestURI.indexOf("?") != -1) {
						str = requestURI.substring(requestURI.indexOf(requestURI.indexOf("?"))) + ".action"
								+ requestURI.substring(requestURI.indexOf("?"),requestURI.length());
					} else {
						str = requestURI + ".action";
					}
				}
				grantedAuthorities = str;
				break;
			}
		}
		
		//如果RequestURI受到保护，返回RequestURI
		if (grantedAuthorities != null) {
			ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
			configAttrEditor.setAsText(grantedAuthorities);
			ConfigAttributeDefinition cd = (ConfigAttributeDefinition) configAttrEditor
					.getValue();
			return cd;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return true;
	}

	/**
	 * 得到urlAuthorities, ServletContextLoaderListener初始化系统的时候放在ServletContext中
	 * 
	 * @param filterInvocation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ResourceDTO> getUrlAuthorities(
			FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest()
				.getSession().getServletContext();
		return (List<ResourceDTO>) servletContext
				.getAttribute("urlAuthorities");
	}

}
