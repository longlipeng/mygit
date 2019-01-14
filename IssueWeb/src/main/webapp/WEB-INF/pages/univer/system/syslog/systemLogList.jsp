<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统日志查看</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp" %>
		<script type="text/javascript">
		var isDisplayTableBody = false;
		var isDisplayQueryBody = false;
		function displayTableBody() {
			if (isDisplayTableBody) {
				display('TableBody');
				isDisplayTableBody = false;
			} else {
				undisplay('TableBody');
				isDisplayTableBody = true;
			}
		}
		function displayQueryBody() {
			if (isDisplayQueryBody) {
				display('QueryBody');
				isDisplayQueryBody = false;
			} else {
				undisplay('QueryBody');
				isDisplayQueryBody = true;
			}
		}
	
	</script>
		<style type="text/css">
body,table,td,p,font,select {
	font-size: 9pt;
}

.bt {
	background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0
		0;
	border: 1px solid #7DE7FD;
	font-size: 9pt;
	height: 22px;
	cursor: pointer;
}

.bt:HOVER {
	background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll
		0 0;
	border: 1px solid #C3A336;
}

.btn {
	cursor: pointer;
	border: #FFFFFF 1px solid;
}

.btn:HOVER {
	background-color: F0FFE1;
	border: #93B3CA 1px solid;
}
</style>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>系统日志查看</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="EditForm" name="EditForm"
								action="systemLog/inquerySysLog.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>日志编号：</span>
										</td>
										<td width="120">
											<s:textfield name="systemLogQueryDTO.logId"></s:textfield>
											<s:fielderror>
												<s:param>
													systemLogQueryDTO.logId
												</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>编码：</span>
										</td>
										<td width="120">
											<s:textfield name="systemLogQueryDTO.txnCode" />
										</td>
										<td width="150" align=right>
											<span>编码名称：</span>
										</td>
										<td width="120">
											<s:textfield name="systemLogQueryDTO.txnName" />
										</td>																										
										</tr>
										<tr>
										<td width="150" align=right>
											<span>操作者：</span>
										</td>
										<td width="120">
											<s:textfield name="systemLogQueryDTO.operationUser" />
										</td>
										<td width="150" align=right>
											<span>操作时间：</span>
										</td>
										<td width="120">
											<input type="text" name="systemLogQueryDTO.operationTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="systemLogQueryDTO.operationTime" />"/>
										</td>
										<td width="150" align=right>
											<span>成功标识：</span>
										</td>
										<td width="120">
											<s:select id="issuerGroupId"
												list="#{'':'-请选择-',1:'成功',0:'失败'}"
												name="systemLogQueryDTO.successFlag"></s:select>
										</td>
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="EditForm.submit();">
										</td>
									</tr>
								</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTableBody();"
									style="cursor: pointer;">
									<span class="TableTop">日志列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">									
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="systemLog/inquerySysLog.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									 retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="logId" title="日志编号" width="10%" >
										<a href="systemLog/loadSysLog.action?systemLogDTO.logId=${map.logId}">${map.logId}</a>
										</ec:column>
										<ec:column property="txnCode" title="编码" width="15%"  />
											<ec:column property="txnName" title="编码名称" width="30%" />
										<ec:column property="operationUser" title="操作人" width="10%" />
										<ec:column property="successFlag" title="状态" width="10%" >
										</ec:column>
										<ec:column property="operationTime" title="操作时间" width="20%" />
									</ec:row>
								</ec:table>				
							</s:form>
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>