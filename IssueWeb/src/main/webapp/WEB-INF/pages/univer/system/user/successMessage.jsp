<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>

		<title>用户管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
.x-tree-node-icon {
	display: none;
}
</style>
		<script type="text/javascript" charset="UTF-8">
		function sub(){
				document.newForm.action="user/modifyPassword.action";
				document.newForm.submit();
		}
 </script>
	</head>
	<body>
		<center>
		<h1 style="color:red"><s:actionmessage /></h1>
		</center>
	</body>
</html>