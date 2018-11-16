<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache,no-store">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/settle/T8050201.js"></script>
<style type="text/css">
	.risk {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/risk_16.png) !important;
	}
	.play {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/play_16.png) !important;
	}
	.monitor {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/monitor_16.gif) !important;
	}
</style>
<script type="text/javascript">
	var date = '<%=request.getParameter("date") %>';
</script>
</head>
<body>
<!-- 银联对账 -->

</body>
</html>