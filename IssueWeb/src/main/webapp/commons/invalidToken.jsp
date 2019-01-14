<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>

		<title>重复提交</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
.x-tree-node-icon {
	display: none;
}
</style>
	</head>
	<body>
		<center>
		<h1 style="color:red">不能重复提交</h1>
		</center>
	</body>
</html>