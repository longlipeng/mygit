<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" href="<%= request.getContextPath()%>/ext/resources/images/favicon.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/system/main.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/toolbar.js"></script>
<script type="text/javascript">
toolBarStr = Ext.decode('${tool_bar_str}');
operator[OPR_ID] = '${operator.oprId}';
operator[OPR_NAME] = '${operator.oprName}';
operator[OPR_BRH_NAME] = '${operator.oprBrhName}';
var server='${pageContext.request.contextPath}';
</script>

<title>上汽车享付商户管理平台</title>
<style>
	#load-mask {
		position:absolute;
		left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        background-color:white;
	}
	#load {
		position:absolute;
		left:45%;
        top:45%;
        z-index:20001;
	}
	#load-text {
        position:absolute;
        z-index:20001;
        width: 200px;
        vertical-align: middle;
        text-align: center;
        left:42%;
        top:70%;
	}
	.key {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/key_16.png) !important;
	}
	.lock {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/lock_16.png) !important;
	}
	.quit {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/quit_16.png) !important;
	}
</style>
<script type="text/javascript">
	var txnCode="";
</script>
</head>
<body>
<div id="load-mask">
<div id="load">
<img src="<%= request.getContextPath()%>/ext/resources/images/loading.gif">
</div>
<div id="load-text">
主界面加载中，请稍后......
</div>
</div>
</body>
</html>