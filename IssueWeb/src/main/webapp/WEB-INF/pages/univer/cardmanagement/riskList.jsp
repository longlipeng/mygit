<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	

	<head>
		<title>风险控制信息查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		//setTimeout('myrefresh()',${time});
		
		function myrefresh(){
			EditForm.submit();
		}
		
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
			<span>风险统计信息</span>
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
							<s:form id="queryForm" name="queryForm"
								action="inqueryRisk.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="110" align=right>
											<span>卡号：</span>
										</td>
										<td width="80">
											
											<s:textfield name="cardManagementQueryDTO.cardNo" size="23" id="cardNo"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.cardNo</s:param>
											</s:fielderror>
										</td>
										<td width="80" align=right>
											<span>开始日期：</span>
										</td>
										<td width="80">
											<input type="text" name="cardManagementQueryDTO.txnDate" id="txnDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='cardManagementQueryDTO.txnDate' format='yyyy-MM-dd'/>"/>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.txnDate</s:param>
											</s:fielderror>
										</td>
										<td width="80" align=right>
											<span>结束日期：</span>
										</td>
										<td width="80">
											<input type="text" name="cardManagementQueryDTO.enDate" id="enDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='cardManagementQueryDTO.enDate' format='yyyy-MM-dd'/>"/>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.enDate</s:param>
											</s:fielderror>
										</td>
										<td width="80" align=right>
											<span>等级：</span>
										</td>
										<td width="80">
											<s:select name="cardManagementQueryDTO.monLev" id="monLev" list="#{'':'请选择',1:'通知',2:'警告',3:'严重警告'}"/>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.monLev</s:param>
											</s:fielderror>
										</td>
										<!-- <td width="120" align=right>
											刷新页面：<s:select list="#{'10000':'10','20000':'20','30000':'30'}" name="time" id="time"/>秒
										</td> -->
										<td align="center">
											<button class='bt' type="button" style="float: left; margin: 5px 20px"
														onclick="queryForm.submit();">
														查询
											</button>
										</td>
									</tr>
								</table>
							</s:form>
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
									<span class="TableTop">卡片列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
							<s:form action="inqueryRisk.action" name="EditForm" method="post">
								<div id="list"
									style="border: 1px solid #B9B9B9; margin-top: 5px;">
									<input type="hidden" name="cardManagementQueryDTO.txnDate" value="<s:date name='cardManagementQueryDTO.txnDate' format='yyyy-MM-dd'/>"/>
									<input type="hidden" name="cardManagementQueryDTO.enDate" value="<s:date name='cardManagementQueryDTO.enDate' format='yyyy-MM-dd'/>"/>
									<s:hidden name="cardManagementQueryDTO.cardNo"></s:hidden>
									<s:hidden name="time" id="time1"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.monLev"></s:hidden>
									<script type="text/javascript">
										document.getElementById("time1").value=document.getElementById("time").value 
									</script>
									
									<ec:table items="pageDataDTO.data" var="map" width="100%"
										form="EditForm" action="inqueryRisk.action"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										
										<ec:row onclick="EditForm.action='viewRisk.action?sysSeqNum=${map.sysSeq}&cardNo=${map.cardNo}&rsvd1=${map.rsvd1}';EditForm.submit();">
											<ec:column property="cardNo" title="卡号" width="20%" escapeAutoFormat="true">
												
											</ec:column>
											<ec:column property="txnDate" title="日期" width="20%"/>
											<ec:column property="txnTime" title="时间" width="20%"/>
											<ec:column property="txnType" title="交易类型" width="10%"/>
											<ec:column property="sysSeq" title="交易系统参考号" width="20%"/>
											<ec:column property="monLev" title="异常等级" width="10%"/>
										</ec:row>
										
									</ec:table>
								</div>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>