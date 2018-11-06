<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看调拨订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨>查看调拨订单</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="stockTransferOrderInputAction!update" method="post">
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%">
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.orderId"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.orderDate"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调出机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.firstEntityName"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调入机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.processEntityName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡片总数：
										</td>
										<td>
										<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
									   </tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<div style="clear: both"></div>
		</div>
		
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
								<s:form id="EditForm" name="EditForm" action="stockTransferOrderInputAction!view.action"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
								<ec:table items="stockTransferOrderList" var="map" width="100%" form="EditForm"
									action="${ctx}/stockTransferOrderInputAction!view.action" showPagination="false"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html" sortable="false"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderList">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="productName" title="产品" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="cardAmount" title="卡片数量" width="15%" />
										<ec:column property="realAmount" title="实际张数" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												align="center" >
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<s:if test="sellOrderDTO.orderState == 11 or sellOrderDTO.orderState == 10">
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
												<span class="TableTop">未接收卡片</span>
											</td>
											<td class="TableTitleEnd">
												&nbsp;
											</td>
										</tr>
									</table>
									
									<div id="orderCardListBody">
										<s:form id="orderOriCardListForm" name="orderOriCardListForm" action="stockTransferOrderInputAction!view.action"
											method="post">
											<s:hidden name="sellOrderDTO.orderId"></s:hidden>
											<s:hidden name="sellOrderDTO.firstEntityId"/>
											<s:hidden name="sellOrderDTO.orderType" />
										<ec:table items="stockTransferOrderOriCardListDTOs" var="map" width="100%" form="orderOriCardListForm"
											action="${ctx}/stockTransferOrderInputAction!view.action"
											imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
											retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderOriCardListRow" >
											<ec:row ondblclick="">
												<ec:column property="productName" title="产品名称" width="15%" />
												<ec:column property="cardNo" title="卡号" width="15%" />
												<ec:column property="faceValue" title="面额" width="15%" />
												<ec:column property="faceValueType" title="面额类型" width="15%" />
											</ec:row>
										</ec:table>
										</s:form>
									</div>
									
							</td>
						</tr>
					</table>
				</div>
				<div>
				    &nbsp;
				</div>
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
											<span class="TableTop">已接收卡片</span>
										</td>
										<td class="TableTitleEnd">
											&nbsp;
										</td>
									</tr>
								</table>
								<div id="orderCardListBody">
									<s:form id="orderReceiveCardListForm" name="orderReceiveCardListForm" action="stockTransferOrderInputAction!view.action"
										method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<ec:table items="stockTransferOrderRaceCardListDTOs" var="map" width="100%" form="orderReceiveCardListForm"
										action="${ctx}/stockTransferOrderInputAction!view.action"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderRaceCardListRow" >
										<ec:row ondblclick="">
											<ec:column property="productName" title="产品名称" width="15%" />
											<ec:column property="cardNo" title="卡号" width="15%" />
											<ec:column property="faceValue" title="面额" width="15%" />
											<ec:column property="faceValueType" title="面额类型" width="15%" />
										</ec:row>
									</ec:table>
									</s:form>
								</div>
								
						</td>
					</tr>
				</table>
			</div>
			</s:if>	
		<%@ include file="orderFlowList.jsp"%>
	</body>
</html>