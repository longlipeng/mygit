<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加订单付款方式成功</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<base target="_self">
		<script type="text/javascript">
				alert("${sucessMessage}");
				window.returnValue=1;
				window.close();	
		</script>
	</head>
</html>