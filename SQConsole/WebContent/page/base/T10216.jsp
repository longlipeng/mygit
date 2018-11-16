<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日终监控</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/base/T10216.js"></script>
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
</head>
<body>
<!--日终监控
insert into TBL_FUNC_INF (FUNC_ID, FUNC_PARENT_ID, FUNC_TYPE, FUNC_NAME, PAGE_NAME, PAGE_URL, PAGE_TARGET, MISC1, MISC2, DESCRIPTION, REC_UPD_OPR, REC_CRT_TS, REC_UPD_TS, ICON_PATH) values (10216, 506, '0', '日终监控', '日终监控', '/page/base/T10216.jsp', null, ' ', ' ', ' ', ' ', ' ', ' ', 'T10216');
insert into TBL_ROLE_FUNC_MAP (KEY_ID, VALUE_ID, REC_UPD_OPR, REC_CRT_TS, REC_UPD_TS) values (1, 10216, '0000', '20131226000000', '20131226000000');
insert into TBL_ROLE_FUNC_MAP (KEY_ID, VALUE_ID, REC_UPD_OPR, REC_CRT_TS, REC_UPD_TS) values (2, 10216, '0000', '20131226000000', '20131226000000');
-->
</body>
</html>