<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/error/T100101.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript">
	var instDate = '<%=request.getParameter("instDate") %>';
	var mchtNo='<%=request.getParameter("mchtNo")%>';
	var termId='<%=request.getParameter("termId")%>';
	var pan='<%=request.getParameter("pan")%>';
	var amtTrans='<%=request.getParameter("amtTrans")%>';
	var retrivlRef='<%=request.getParameter("retrivlRef")%>';
	var sysSsn='<%=request.getParameter("sysSsn")%>';
	var createDate='<%=request.getParameter("createDate")%>';
</script>
<style type="text/css">
	.risk {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/risk_16.png) !important;
	}
</style>
</head>
<body>
<!-- -->
</body>
</html>