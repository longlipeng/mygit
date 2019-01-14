<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String error = request.getParameter("error");
%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>服务管理</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	
	<body>
		<struts:actionerror/>
		<br>
		<input type=button value="返 回" onclick="history.go(-1);">
	</body>
	
</html>