<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>包装管理</title>
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
		var package = document.getElementsByName('packageRadio');
		var n = 0;
		var s = '';
		for (var i = 0; i < package.length; i++) {
			if (package[i].checked) {
				s = package[i].value;
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
		<span>添加包装明细</span>
	</div>
	<form id="queryForm" name="queryForm"
		action="${ctx}/productPackageList.action?id=${productDTO.productId}"
		method="post" style="padding: 0px; margin: 0px;">

		<div id="query" style="border: 1px solid #B9B9B9; margin-top: 5px;">
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront"><span class="TableTop">包装查询条件</span>
						</td>
						<td class="TableTitleEnd">&nbsp;</td>
					</tr>
				</table>
			</div>

			<div id="queryCondition"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<input type="hidden" name="id"
						value="${ packageQueryDTO.productId }" />
					<s:hidden name="packageQueryDTO.productId"></s:hidden>
					<tr height="35">
						<td width="150" align=right><span>包装号：</span></td>
						<td width="160"><s:textfield name="packageQueryDTO.packageId"></s:textfield>
							&nbsp;</td>
						<td width="150" align=right><span>包装名称：</span></td>
						<td width="120"><s:textfield
								name="packageQueryDTO.packageName"></s:textfield></td>
						<td align="center"><img src="${ctx}/images/table/cx.GIF"
							width="50" height="19" class="btn" onclick="queryForm.submit();">
						</td>
					</tr>
				</table>


			</div>
		</div>
		<div id="list" style="border: 1px solid #B9B9B9; margin-top: 5px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront"><span class="TableTop">包装列表</span>
						</td>
						<td class="TableTitleEnd">&nbsp;</td>
					</tr>
				</table>
			</div>

			<ec:table items="pageDataDTO.data" var="map" width="100%"
				retrieveRowsCallback="limit" form="queryForm"
				action="${ctx}/productPackageList.action"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false">
				<ec:row>
					<ec:column property="null" alias="choose" title="选择" width="10%"
						sortable="false">
						<input type="radio" name="packageRadio" id="packageRadio"
							value='${map.packageId}' />

					</ec:column>
					<ec:column property="packageId" title="包装号" width="30%" />
					<ec:column property="packageName" title="包装名称" width="30%" />
					<ec:column property="packageFee" title="包装费  (单位:元)" width="40%" />
				</ec:row>
			</ec:table>

		</div>

		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px" onclick="window.close()">关 闭</button>

			<button class='bt' style="float: right; margin: 5px" onclick="sub()">确定</button>
			<div style="clear: both"></div>
		</div>
	</form>
</body>
</html>
