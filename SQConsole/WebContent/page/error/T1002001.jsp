<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache,no-store">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/error/T1002001.js"></script>
<!-- <script type="text/javascript" src="<%= request.getContextPath()%>/ext/bootstrap.js"></script> -->
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/HandleOfBatch.js"></script>

<script type="text/javascript">
	var mchtno = '<%=request.getParameter("mchtno") %>';
	var termid = '<%=request.getParameter("termid") %>';
	var txnnum = '<%=request.getParameter("txnnum") %>';
	var transdatestart = '<%=request.getParameter("transdatestart") %>';
	var transdateend = '<%=request.getParameter("transdateend") %>';
	var pan = '<%=request.getParameter("pan") %>';
	var transamtsmall = '<%=request.getParameter("transamtsmall") %>';
	var transamtbig = '<%=request.getParameter("transamtbig") %>';
	var txnssn = '<%=request.getParameter("txnssn") %>';
	var termssn = '<%=request.getParameter("termssn") %>';
</script>

 <style type="text/css">
.btn_2k3 {
 BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #002D96 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #002D96 1px solid
}
</style>

</head>
<body>
<!-- 按交易挂账 -->
</body>
</html>