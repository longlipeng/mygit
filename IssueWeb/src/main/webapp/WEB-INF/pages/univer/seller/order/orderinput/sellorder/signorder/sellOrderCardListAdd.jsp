<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<base target="_self">
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
			
			function submit() {		
				var flag = true;
				for(i = 0; i < document.getElementsByName("orderListStr").length; i++) {
					if (document.getElementsByName("orderListStr").item(i).checked) {
						flag=false;
					}
				}
				if (flag) {
					errorDisplay("请选择一条记录操作！");
					return;
				} 
				document.queryForm.action = "sellOrderSignAction!insertOrderList";
				document.queryForm.submit();
			}
			
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
				
		<div class="TitleHref">
			<span>订单管理>添加记名订单明细信息</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
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
								action="sellOrderSignAction!addOrderList" method="post">
								<s:hidden name="sellOrderDTO.orderId" id="orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId"/>
								<table>
									<tr>
										<td align="right" width="80" nowrap>
											持卡人工号：
										</td>
										<td width="120">
											<s:textfield name="cardholderQueryDTO.externalId" size="14" />
											<s:fielderror>
												<s:param>
													cardholderQueryDTO.externalId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td align="right" width="80" nowrap>
											持卡人姓名：
										</td>
										<td width="120">
											<s:textfield name="cardholderQueryDTO.firstName"
												size="14" />
											<s:fielderror>
												<s:param>
													cardholderQueryDTO.firstName
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td align="right" width="100" nowrap>
											持卡人手机号：
										</td>
										<td width="120">
											<s:textfield name="cardholderQueryDTO.cardholderMobile"
												size="14" />
											<s:fielderror>
												<s:param>
													cardholderQueryDTO.cardholderMobile
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<!-- <td align="right" width="80" nowrap>
											&nbsp;
										</td>
										<td width="120">
											&nbsp;
										</td> -->
									</tr>
									<tr>
									<td align="right" width="100" nowrap>
											持卡人证件号：
										</td>
										<td width="120">
											<s:textfield name="cardholderQueryDTO.idNo"
												size="14" />
											<s:fielderror>
												<s:param>
													cardholderQueryDTO.idNo
												</s:param>
											</s:fielderror>
											
										</td>
										<td align="right" width="80" nowrap>
											部门：
										</td>
										<td width="120">
											<s:select name="cardholderQueryDTO.departmentId" list="departments"
														headerKey="0" headerValue="--请选择--"	listKey="departmentId" listValue="departmentName" >
											</s:select>
											&nbsp;
										</td>
										<td>&nbsp;</td>
										<td width="80">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class='bt' onclick="queryForm.submit();">
												查 询
											</button>
										</td>
									</tr>

								</table>

						</div>
					</td>
				</tr>
			</table>
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
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
									<ec:table items="list" var="map" width="100%" form="queryForm"
										action="${ctx}/sellOrderSignAction!addOrderList"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										<ec:row>
											<ec:column property="null" alias="orderListStr" title="选择"
												width="10%" sortable="false" headerCell="selectAll"
												viewsAllowed="html">
												<input type="checkbox" name="orderListStr" value="${map.cardholderId}" />
											</ec:column>
											<ec:column property="cardholderId" title="持卡人编号" width="15%" />
											<ec:column property="cardholderName" title="持卡人名称" width="15%" />
											<ec:column property="type" title="持卡人类别" width="10%" />
											<ec:column property="idNo" title="证件号" width="15%" />
											<ec:column property="externalId" title="持卡人工号" width="10%" />
											<ec:column property="cardholderMobile" title="联系方式" width="15%" />
											<ec:column property="departmentName" title="部门" width="10%" />
										</ec:row>
									</ec:table>
								
							</div>
							<!-- div id=TableBody -->
						</td>
					</tr>
				</table>
				<br>
				<script type="text/javascript">
					var isDisplayOrderTable = false;
					function displayOrderTable() {
						if (isDisplayOrderTable) {
							display('orderTable');
							isDisplayOrderTable = false;
						} else {
							undisplay('orderTable');
							isDisplayOrderTable = true;
						}
					}
				</script>
			</div>
			
			</s:form>
			
			<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' style="float: right; margin: 5px"
					onclick="window.close()">
					关 闭
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="return submit();">
					提 交
				</button>
				<div style="clear: both"></div>
			</div>
	</body>
</html>