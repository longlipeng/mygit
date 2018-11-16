<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>

<%@page import="java.util.List"%>
<%@page import="com.huateng.po.TblDivMchntTmp"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户详细信息</title>
</head>
<body>
<script type="text/javascript">
	var agenInfo = new Array();
	var agenDivInfoArr = new Array();
	alert("aaaaa");
	<%
	Map<String,String> agenInfo = (Map)session.getAttribute("agenInfo");
	List<TblDivMchntTmp> agenDivInfoArr = (List)session.getAttribute("agenDivInfoArr");
	Iterator<String> iter = agenInfo.keySet().iterator();
	String key = null;
	String value = null;
	while(iter.hasNext()) {
		key = iter.next();
		value = agenInfo.get(key);
	%>
	agenInfo['<%= key%>'] = '<%= value%>';
	<%	
	}
	Iterator<TblDivMchntTmp> divIter = agenDivInfoArr.iterator();
	TblDivMchntTmp tblDivMchntTmp = null;
	while(divIter.hasNext()) {
		tblDivMchntTmp = divIter.next();
	%>
	agenDivInfoArr['<%= tblDivMchntTmp.getId().getDivNo()%>'] = '<%= tblDivMchntTmp.getId().getDivNo()%>';
	<%
	}
	%>
	// 设置为同步，使下拉列表信息能够回显
	dwr.engine.setAsync(false);
</script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/base/AgencyDetailS.js"></script>
<!-- 商户信息维护 -->
</body>
</html>