<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单准备</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayQueryBody = false;
			var startCardNo;
			var endCardNo;
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

			function readyList(operation) {
		   		var flag = true;
				var orderId;
				
				for(i = 0; i < document.getElementsByName("cardNoArray").length; i++) {
					if (document.getElementsByName("cardNoArray").item(i).checked) {
						flag = false;
					}
				}	
				if(flag){
					errorDisplay("请选择至少一条记录操作！");
					return;
				}
				stockForm.action='${ctx}/stockTransferOrderReadyAction!cardArrayReady';
				stockForm.submit();
			}

			function cardAllReady(){
				if(startCardNo==null ||startCardNo==""){
					errorDisplay('没有可以准备的卡');
					return;
				}
				if(endCardNo==null ||endCardNo==""){
					errorDisplay('没有可以准备的卡');
					return;
				}
				if(document.getElementsByName("cardNoArray").length==0){
					errorDisplay('没有可以准备的卡');
					return;
				}
				stockForm.action='${ctx}/stockTransferOrderReadyAction!cardAllReady';
				document.getElementById("startCardNo").value=startCardNo;
				document.getElementById("endCardNo").value=endCardNo;
				stockForm.submit();
				
			}
			
			function queryStock(){
				stockForm.action='${ctx}/stockTransferOrderReadyAction!cardReady';
				stockForm.submit();
			}

			function successFlag(){
				alert(${successFlag});
			}
			function cardNoInit(){
				startCardNo=document.getElementById("startCardNo").value;
				endCardNo=document.getElementById("endCardNo").value;
			}
		</script>
	</head>
	<body onload="cardNoInit()">

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨-->调拨订单准备</span>
		</div>
		
		<!-- 查询条件 -->
		<s:form id="stockForm" name="stockForm" action="" method="post">
		<s:hidden name="orderReadyDTO.orderId" />
		<s:hidden name="orderReadyDTO.orderListId"/>
		<div id="queryBox" align="center">
			<%--<s:form name="queryForm" id="queryForm" method="post">
			<s:hidden name="sellOrderDTO.orderId" />
			--%>
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
			<tr>
				<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
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
				<table width="80%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr height="35">
								<td width="" align="right">
									<span>起始卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="orderReadyDTO.startCardNo" id="startCardNo" size="30" maxlength="19"/>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											orderReadyDTO.startCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td width="" align="right">
									<span>结束卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="orderReadyDTO.endCardNo" id="endCardNo" size="30" maxlength="19"></s:textfield>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											orderReadyDTO.endCardNo
										</s:param>
									</s:fielderror>
								</td>
								
								<td align="right">
									<input type="button" class="bt" style="margin: 5px" onclick="queryStock()" value="查 询" />
								</td>
							</tr>
							
						</table>
					</td>
				</tr>
			</table>
			<%--</s:form>
		--%></div>
		<br/>
		<br/>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
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
						<ec:table items="pageDataDTO.data" var="map" width="100%" form="stockForm"
							action="${ctx}/stockTransferOrderReadyAction!cardReady"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="cardStock" autoIncludeParameters="false" >
							<ec:row ondblclick="">
								<ec:column property="null" alias="cardNoArray" title="选择" headerCell="selectAll" width="5%" sortable="false"  viewsAllowed="html">
									<input type="checkbox" name="cardNoArray" value="${map.cardNo}" />
								</ec:column>
								<ec:column property="cardNo" title="卡号" width="10%" />
								<ec:column property="cardLayoutName" title="卡面" width="10%" />
								<ec:column property="faceValueType" title="面额类型" width="10%" />
								<ec:column property="faceValue" title="面额值" width="10%" />
								<ec:column property="stockState" title="库存状态"  width="10%"  cell="dictInfo" alias="300">
								</ec:column>
							</ec:row>
						</ec:table>
						</div>
					</td>
				</tr>
			</table>
			
		</div>
		</s:form>
		<br/>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							

							<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn' style="float: right; margin: 7px" onclick="window.close();">
												返  回
											</button>
											<button class='btn' style="float: right; margin: 7px" onclick="cardAllReady();">
												全部准备
											</button>
											<button class='btn' style="float: right; margin: 7px" onclick="readyList();">
												准  备
											</button>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>