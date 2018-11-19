<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			var isDisplayTableBody1 = false;
			function displayTableBody1() {
				if (isDisplayTableBody1) {
					display('TableBody1');
					isDisplayTableBody1 = false;
				} else {
					undisplay('TableBody1');
					isDisplayTableBody1 = true;
				}
			}
			var isDisplayTableBody2 = false;
			function displayTableBody2() {
				if (isDisplayTableBody2) {
					display('TableBody2');
					isDisplayTableBody2 = false;
				} else {
					undisplay('TableBody2');
					isDisplayTableBody2 = true;
				}
			}
			var isDisplayTableBody3 = false;
			function displayTableBody3() {
				if (isDisplayTableBody3) {
					display('TableBody3');
					isDisplayTableBody3 = false;
				} else {
					undisplay('TableBody3');
					isDisplayTableBody3 = true;
				}
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>错误信息</span>
		</div>
			
			<div style="width: 100%" align=center>
				<span style="widht: 98%" style="color: red; align:left;" >以下持卡人没有订单产品的卡</span>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody1();"
										style="cursor: pointer;" nowrap="nowrap" >
										<span class="TableTop">错误信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody1">
								<ec:table items="errList1" var="map" width="100%"
									action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									showPagination="false" showStatusBar="false" sortable="false"
									tableId="errList1">
									<ec:row>
										<ec:column property="cardholderName" title="持卡人姓名" width="15%" />
										<ec:column property="externalId" title="持卡人工号" width="15%" />
										<ec:column property="creditPriceStr" title="充值金额" width="15%" />
									</ec:row>
								</ec:table>
							</div>
						</td>
					</tr>
				</table>
			</div>

		
			<br>
			<div style="width: 100%" align=center>
				<span style="widht: 98%" style="color: red; align:left;" >以下持卡人已经在列表中了</span>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody2();"
										style="cursor: pointer;" nowrap="nowrap">
										<span class="TableTop">错误信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody2">
								<ec:table items="errList2" var="map"
									width="100%" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									showPagination="false" showStatusBar="false" sortable="false"
									tableId="errList2">
									<ec:row>
										<ec:column property="cardholderName" title="持卡人姓名" width="15%" />
										<ec:column property="externalId" title="持卡人工号" width="15%" />
										<ec:column property="creditPriceStr" title="充值金额" width="15%" />
									</ec:row>
								</ec:table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='btn' style="float: right; margin: 8px"
				onclick="window.open('${ctx}/creditOrderAction!importFile?orderId=${orderId}', '_self');">
				返 回
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>