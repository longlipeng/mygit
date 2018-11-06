<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单接收</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			function submitForm(){
			    document.newForm.action = "stockTransferOrderAcceptAction!sendBackAtAcceptSubmit";
			    document.newForm.submit();
			}
		</script>
		
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>调拨入库>退回订单</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="" method="post">
			<div id="orderTable">
			<table width="100%" border=0>
				<tr>
					<td align="right" width="100" nowrap="nowrap" style="padding: 10px 0 0 0">
						<span>操作说明：</span>
					</td>
					<td width="100%" align="left" nowrap="nowrap" rowspan="2" style="padding: 10px 0 0 0">
							<s:hidden name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden name="sellOrderDTO.orderState"></s:hidden>
							<s:hidden name="sellOrderDTO.orderType"></s:hidden>
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明" rows="3"></s:textarea>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/stockTransferOrderAcceptAction!list', '_self')">
								返 回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitForm();">
								确认
							</button>
							<div style="clear: both"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		</s:form>
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
									
									<!--<tr>
									    <td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>配送日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:hidden name="sellOrderDTO.validityPeriod"  id="validPeriod"></s:hidden>
											<s:textfield name="sellOrderDTO.validityPeriod"
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
											<s:param>
												sellOrderDTO.validityPeriod
											</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>配送人员：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20"  required="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>
											
									</tr>
									--><tr>
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
			<s:hidden name="sellOrderDTO.orderId"  id="sellOrderDTO.orderId"/>
			<s:hidden name="sellOrderDTO.firstEntityId"/>
			<s:hidden name="orderReadyDTO.orderCardListId"  id="orderCardListId"/>
		
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
										<span class="TableTop">出库明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="orderlistListForm" name="orderlistListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
								<ec:table items="stockTransferOrderList" var="map" width="100%" form="orderlistListForm"
									action="${ctx}/stockTransferOrderAcceptAction!sendBackAtAccept"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderList">
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListIdStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListIdStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="productName" title="产品名称" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="10%" />
										<ec:column property="faceValue" title="面额值" width="15%" />
										<ec:column property="validityPeriod" title="有效期" width="10%" 
											parse="yyyyMMdd" format="yyyy-MM-dd"/>
										<ec:column property="cardAmount" title="张数" />
										<ec:column property="realAmount" title="实际张数" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
			<!-- div id=TableBody -->
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
								<s:form id="orderCardListForm" name="orderCardListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>								
								<ec:table items="stockTransferOrderOriCardListDTOs" var="map" width="100%" form="orderCardListForm"
									action="${ctx}/stockTransferOrderAcceptAction!sendBackAtAccept"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderOriCardListRow" >
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderCardListId}" />
										</ec:column>
										<ec:column property="productName" title="产品名称" width="15%" />
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
	</body>
</html>