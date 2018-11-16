<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计费算法导入</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/T20708.js"></script>
<style type="text/css">
	.mchnt {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/mchnt_16.png) !important;
	}
	.download {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/download_16.png) !important;
	}
</style>
<script type="text/javascript"></script>
</head>
<body>
<div id="reportPanel" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>