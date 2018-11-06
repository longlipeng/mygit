<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.extremecomponents.org" prefix="ec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.huateng.com.cn/tags-dl" prefix="dl" %>
<%@ taglib uri="http://www.huateng.com.cn/tags-edl" prefix="edl" %>
<%@ taglib uri="http://www.huateng.com.cn/tags-securityDisplay" prefix="display" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://www.huateng.com.cn/tags-pay" prefix="pay" %>
<%@ taglib uri="http://www.huateng.com.cn/tags-errorMsg" prefix="errorMsg" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>