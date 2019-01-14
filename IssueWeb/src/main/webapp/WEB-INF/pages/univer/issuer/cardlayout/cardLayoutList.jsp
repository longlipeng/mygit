<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>卡面管理</title>
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
	function edit() {
		var n = 0;
		var checkbox = document.getElementsByName("choose");
		for (i = 0; i < checkbox.length; i++) {
			if (checkbox[i].checked == true) {
				n++;
			}
		}
		if (n == 0) {
			errorDisplay('请选择要编辑的对象');
		}
		if (n > 1) {
			errorDisplay('编辑对象只能是一个');
		}
		if (n == 1) {
			document.EditForm.action = '${ctx}/product/cardLayoutLoad.action';
			document.EditForm.submit();
		}
	}
	function del() {
		var n = 0;
		var checkbox = document.getElementsByName("choose");
		for (i = 0; i < checkbox.length; i++) {
			if (checkbox[i].checked == true) {
				n++;
			}
		}
		if (n == 0) {
			errorDisplay('请选择要删除的对象');
		}

		if (n > 0) {
			confirm("确定删除吗？", operation)
		}
	}
	function operation() {
		EditForm.action = '${ctx}/product/cardLayoutDelete.action';
		EditForm.submit();
	}
</script>
<style type="text/css">
body, table, td, p, font, select {
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
	<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>

	<div class="TitleHref">
		<span>卡面信息管理</span>
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
								style="cursor: pointer;"><span class="TableTop">查询条件</span>
							</td>
							<td class="TableTitleEnd">&nbsp;</td>
						</tr>
					</table>
					<div id="QueryBody">
						<s:form id="queryForm" name="queryForm" action="cardLayoutInquery"
							method="post">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="0">
								<tr height="35">
									<td width="150" align=right><span>卡面号：</span></td>
									<td width="160"><s:textfield
											name="cardLayoutQueryDTO.cardLayoutId"></s:textfield> <s:fielderror>
											<s:param>
																cardLayoutQueryDTO.cardLayoutId
															</s:param>
										</s:fielderror></td>
									<td width="150" align=right><span>卡面名称：</span></td>
									<td width="160"><s:textfield
											name="cardLayoutQueryDTO.cardName" /> <s:fielderror>
											<s:param>
																cardLayoutQueryDTO.cardName
															</s:param>
										</s:fielderror></td>
									<td align="center"><img src="${ctx}/images/table/cx.GIF"
										width="50" height="19" class="btn"
										onclick="queryForm.submit();"></td>
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
								style="cursor: pointer;"><span class="TableTop">记录列表</span>
							</td>
							<td class="TableTitleEnd">&nbsp;</td>
						</tr>
					</table>
					<div id="TableBody">
						<s:form id="EditForm" name="EditForm"
							action="/product/cardLayoutInquery.action" method="post">
							<s:token></s:token>
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="EditForm" action="${ctx}/product/cardLayoutInquery.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row>
									<ec:column property="null" alias="choose" title="选择"
										width="10%" headerCell="selectAll">
										<input type="checkbox" name="choose"
											value="${map.cardLayoutId}" />
									</ec:column>
									<ec:column property="cardLayoutId" title="卡面号" width="20%">
										<a
											href="${ctx}/product/cardLayoutView.action?id=${map.cardLayoutId}">
											<s:property value="#attr['map'].cardLayoutId" />
										</a>
									</ec:column>
									<ec:column property="cardName" title="卡面名称" width="30%" />
									<ec:column property="validFlag" title="有效标记" width="30%">
										<c:if test="${map.validFlag eq 1}">有效</c:if>
										<c:if test="${map.validFlag eq 0}">无效</c:if>
									</ec:column>



								</ec:row>
							</ec:table>


							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td align="right">
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>

												<td><display:security urlId="30102">
														<input type="button" class="btn"
															style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
															onclick="submitForm('EditForm', '${ctx}/product/cardLayoutInsert.action', 'add')"
															value="添加" />
													</display:security></td>


												<td><display:security urlId="30103">
														<input type="button" class="btn"
															style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
															onclick="edit();" value="编辑" />
													</display:security></td>


												<td><display:security urlId="30104">
														<input type="button" class="btn"
															style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
															onclick="del();" value="删除" />
													</display:security></td>



											</tr>
										</table>
									</td>
								</tr>
							</table>
						</s:form>
					</div> <!-- div id=TableBody -->
				</td>
			</tr>
		</table>
	</div>
	<br>
</body>
</html>
