<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			var cardno = ${sellOrderOrigCardListDTO.cardNo};
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
	
			function doTest_DeviceKTL656H_Read() {
				var vData = "";
				vData = DeviceKTL656H._CommOpen(1);
				vData = DeviceKTL656H._ReadCard(1);
				if (vData != -1) {
					document.getElementById("cardNo").value = vData;
					alert("读卡完毕");
				} else {
					alert('设备连接有误,串口可能被占用！');
				}
				vData = DeviceKTL656H._CommClose();
			}

			<c:if test="${sellOrderOrigCardListDTO.cardNo==2}">
				setTimeout("readCardNo()",3000);
			</c:if>
			function readCardNo() {
				if (cardno == '2') {
					doTest_DeviceKTL656H_Read();
					var cardNo = document.getElementById("cardNo").value;
					if (cardNo == '') {
						alert("读卡失败!");
						return;
					}
					queryForm.submit();
				}
	
			}
			function BeginCheckCardno() {
				cardno = "2";
				readCardNo();
			}
			function endCheckCardno() {
				document.queryForm.action = 'ransomOrderAction!checkCardOver.action';
				queryForm.submit();
			}
			function returnBack(orderId) {
				document.EditForm.action = '${ctx}/ransomOrderAction!list';
				EditForm.submit();
			}
		</script>
	</head>
	<body>
		<OBJECT ID="ACCOR_ATL"  name="DeviceKTL656H" CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" 
			HEIGHT=0 WIDTH=0 codebase="ACCOR_ATL.dll#version=1,0,0,1" viewastext></OBJECT>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理-->验卡</span>
		</div>
		<br />
		<br />
		<div id="ContainBox">
			<s:form id="queryForm" name="queryForm"
				action="stockOrderAcceptAction!checkCard.action">
				<s:hidden name="sellOrderOrigCardListDTO.cardNo" id="cardNo" />
				<s:hidden name="sellOrderOrigCardListDTO.orderId" id="orderId" />
				<table width="100%" style="table-layout: fixed;" border="0">
					<tr style="text-align: center width :   100%;">
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="text-align: right;">
										<s:if test="errorMessage!=null&&errorMessage!=''">
											<script type="text/javascript">
												alert("验卡失败，请再次验卡!");
											</script>
										</s:if>
										<span>选择验卡机器:</span>
									</td>
									<td>
										<select name="typeName" id="typeName">
											<option value="1">
												磁条卡机
											</option>
										</select>
									</td>
									<td>
										<input type="button" class='bt' value="开始验卡" onclick="BeginCheckCardno();"/>	
									</td>
									<td>
										<input type="button" class='bt' value="结束验卡" onclick="endCheckCardno();"/>
									</td>
								</tr>
							</table>
						</td>
						<td></td>
					</tr>
				</table>
				<br />
				<br />
				<br />
				<br />
				<br />
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
										<span class="TableTop">记录列表</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="orderCardList" var="map" width="100%"
									view="html" retrieveRowsCallback="limit"
									tableId="orderCardList" autoIncludeParameters="false"
									showPagination="false">
									<ec:row>
										<ec:column property="orderCardListId" title="订单明细号" width="6%" />
										<ec:column property="cardNo" title="卡号" width="10%" />

										<ec:column property="cardState" title="卡片状态" width="10%">
											<s:property value="#attr['map'].cardState == 3 ? '有效' : '无效'" />
										</ec:column>
										<ec:column property="cardState" title="制卡状态" width="10%">
											<s:property
												value="#attr['map'].cardState == 1 ? '无卡' : #attr['map'].cardState == 2?'制卡中':#attr['map'].cardState == 3?'制卡成功':#attr['map'].cardState == 4?'制卡失败':''" />
										</ec:column>
									</ec:row>
								</ec:table>
							</div>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<br />
		<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">

						<s:form id="EditForm" name="EditForm" action="" method="post">
							<s:hidden name="sellOrderCardListDTO.orderListId"
								id="orderListId" />
							<s:hidden name="sellOrderOrigCardListDTO.orderId" id="orderId" />
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn'
												style="float: right; margin: 40px 7px 2px"
												onclick="returnBack();">
												返 回
											</button>
										</div>
									</td>
								</tr>
							</table>
						</s:form>
					</td>
				</tr>
			</table>
		</div>
		<br>
		
	</body>
</html>