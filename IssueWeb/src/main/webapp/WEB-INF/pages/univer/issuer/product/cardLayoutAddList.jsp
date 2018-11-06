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
	function sub() {
		var cardFace = document.getElementsByName('cardFaceRadio');
		var n = 0;
		var s = '';
		for (var i = 0; i < cardFace.length; i++) {
			if (cardFace[i].checked) {
				s = cardFace[i].value;
				n = n + 1;
			}
		}
		if (n == 0) {
			errorDisplay('请选择一个卡面');
		} else {
			window.returnValue = s;
			window.close();
		}
	}

	function radio() {

		var cardFace = document.getElementsByName('cardFaceRadio');
		var cardFaceInfo = document.getElementsByName('cardTable');
		for (var i = 0; i < cardFace.length; i++) {
			cardFaceInfo[i].style.display = 'none';
			if (cardFace[i].checked) {
				cardFaceInfo[i].style.display = '';
			}
		}

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
<base target="_self">
</head>
<body>
	<div class="TitleHref">
		<span>添加卡面明细</span>
	</div>
	<div id="query" style="border: 1px solid #B9B9B9; margin-top: 5px;">
		<div id="queryTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<td class="TableTitleFront"><span class="TableTop">卡面查询条件</span>
				</td>
				<td class="TableTitleEnd">&nbsp;</td>
			</table>
		</div>

		<div id="queryCondition"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<form id="queryForm" name="queryForm" action="cardLayoutList.action"
				method="post" style="padding: 0px; margin: 0px;">
				<s:hidden name="cardLayouQueryDTO.productId" />
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<tr height="35">
						<td width="150" align=right><span>卡面名称：</span></td>
						<td width="160"><s:textfield
								name="cardLayouQueryDTO.cardName"></s:textfield> &nbsp;</td>
						<td width="150" align=right><span>状态：</span></td>
						<td width="120">有效</td>
						<td align="center"><img src="${ctx}/images/table/cx.GIF"
							width="50" height="19" class="btn" onclick="queryForm.submit();">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<s:form id="EditForm" name="EditForm">
		<div id="list" style="border: 1px solid #B9B9B9; margin-top: 5px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<td class="TableTitleFront"><span class="TableTop">卡面列表</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</table>

			</div>

			<ec:table items="pageDataDTO.data" var="map" width="100%"
				form="EditForm" action=""
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" retrieveRowsCallback="limit">
				<ec:row onclick="">
					<ec:column property="null" alias="choose" title="选择" width="10%"
						sortable="false">
						<input type="radio" name="cardFaceRadio" onclick="radio();"
							value="${map.cardLayoutId}" />

					</ec:column>
					<ec:column property="cardName" title="卡面名称" width="10%" />
					<ec:column property="validFlag" title="有效标记" width="10%">
				有效
			</ec:column>
				</ec:row>
			</ec:table>

			<div id="card" style="border: 1px solid #B9B9B9; margin-top: 5px;">
				<div id="cardTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<td class="TableTitleFront"><span class="TableTop">卡面信息</span>
						</td>
						<td class="TableTitleEnd">&nbsp;</td>
					</table>
				</div>

				<s:iterator value="pageDataDTO.data" var="cardLayout">
					<div name="cardTable" id="cardTable"
						style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; display: none;">

						<table width="100%" style="table-layout: fixed;">
							<tr>
								<td>

									<table style="text-align: left; width: 100%">
										<tr>
											<td style="width: 70px; text-align: right;">卡面名称：<br></td>
											<td><s:property value="#cardLayout.cardName" /><br></td>
										</tr>
									</table> <br>
								</td>
								<td>

									<table style="text-align: left; width: 100%">
										<tr>
											<td style="width: 70px; text-align: right;">有效状态：<br></td>
											<td>有效<br></td>
										</tr>
									</table> <br>
								</td>
							</tr>

							<tr>
								<td colspan="2">
									<table style="text-align: left; width: 100%">
										<tr>
											<td style="width: 70px; text-align: right;">图片：<br></td>
											<td><img name="img" id="img"
												src="getCardLayoutImg.action?id=${cardLayout.cardLayoutId}"
												height="90px" width="150px" /><br></td>
										</tr>
									</table> <br>
								</td>
							</tr>
						</table>

					</div>
				</s:iterator>
			</div>
			<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' style="float: right; margin: 5px"
					onclick="window.close()">关 闭</button>

				<button class='bt' style="float: right; margin: 5px" onclick="sub()">确
					定</button>
				<div style="clear: both"></div>
			</div>
	</s:form>
</body>
</html>
