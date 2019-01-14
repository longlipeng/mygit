<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单准备成功</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<base target="_self">
		<script type="text/javascript">
				alert("${sucessMessage}");
				window.returnValue=${orderId};
				window.close();	
		</script>
				<s:hidden name="stockAllocateDTO.allocateId"></s:hidden>
	</head>
</html>