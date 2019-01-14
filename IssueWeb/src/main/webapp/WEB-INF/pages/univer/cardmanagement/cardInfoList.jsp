<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>卡片信息查询</title>
		<%@ include file="/commons/meta.jsp"%>

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
		function check(key){
			if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
				return true;
			else
				return false;
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
			<span>卡片信息查询</span>
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
								action="inqueryCardInfo.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>卡号：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.cardNo" size="23" maxLength="19" onkeypress="return check(event);"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.cardNo</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>客户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.customerName"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.customerName</s:param>
											</s:fielderror>
										</td>



									</tr>
									<tr height="35">
										<td width="150" align=right>
											<span>持卡人证件ID：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.idNo"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.idNo</s:param>
											</s:fielderror>
										</td>

										<td width="150" align=right>
											<span>持卡人名：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.cardholderName"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.cardholderName</s:param>
											</s:fielderror>
										</td>
									<tr height="35">
										<td width="150" align=right>
											<span>卡片类型：</span>
										</td>
										<td width="160">
											<dl:dictList displayName="cardManagementQueryDTO.cardType"
												dictType="102"
												dictValue="${cardManagementQueryDTO.cardType}" tagType="2"
												defaultOption="true" />
											
										</td>
										<td width="150" align=right>
											<span>产品类型：</span>
										</td>
										<td width="160">
											<dl:dictList displayName="cardManagementQueryDTO.prodType"
												dictType="100"
												dictValue="${cardManagementQueryDTO.prodType}"
												tagType="2" defaultOption="true" />
											
										</td>



									
									<td align="center">
										<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
											class="btn" onclick="queryForm.submit();">
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
							<s:form action="inqueryCardInfo.action" name="EditForm"
								method="post">
								<div id="list"
									style="border: 1px solid #B9B9B9; margin-top: 5px;">
									<div id="listTitle"
										style="font-weight: bold; height: 19px; background-color: #DFE8F6;">

										<span>卡号信息</span>

									</div>
									<s:hidden name="cardManagementQueryDTO.cardType"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.prodType"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.cardNo"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.customerName"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.idNo"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.cardholderName"></s:hidden>
									<ec:table items="pageDataDTO.data" var="map" width="100%"
										form="EditForm" action="inqueryCardInfo.action"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										<ec:row ondblclick="view('viewCardInfo.action',{'cardManagementDTO.cardNo':'${map.cardNo}'},'');">

											<ec:column property="cardNo" title="卡号">
												<a href="viewCardInfo.action?cardManagementDTO.cardNo=${map.cardNo}">${map.cardNo}</a>
											</ec:column>
											<ec:column property="cardType" title="卡片类型" />
											<ec:column property="prodType" title="产品类型"/>
											<ec:column property="prodName" title="产品名称" />
											<ec:column property="cardholderName" title="持卡人" />
											<ec:column property="customerName" title="客户" />
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