<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单查询</title>
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
			
			function queryOrder(){
				queryForm['ec_eti'].disabled=true;
				queryForm.submit();
			}

			
			 function viewOrder(orderId){
    			window.open("${ctx}/stockTransferOrderInputAction!view.action?sellOrderDTO.orderId="+orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
    	  	}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>调拨订单管理-->库存调拨订单查询</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm" action="stockTransferOrderQueryAction!query">
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
							
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="85" align=right>
											<span>订单号：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.orderId"
												id="orderId" required="true"></s:textfield>
											&nbsp;
										</td>
										<td width="120" align=right>
											<span>调出机构：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.firstEntityName"
												id="customerId" size="20" />
											&nbsp;
										</td>
										<td width="120" align=right>
											<span>调入机构：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.processEntityName"
												id="entityName" size="20" />
											&nbsp;
										</td>
										
										<td align="center">
											&nbsp;
										</td>
									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>订单起始日期：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.orderDateStart"
															id="orderId" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
											&nbsp;
										</td>
										
										<td width="85" align=right>
											<span>订单结束日期：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.orderDateEnd"
															id="orderId" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
											&nbsp;
										</td>
										<td width="102" align=right>
											<span>&nbsp;</span>
										</td>
										<td width="120">
										</td>
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="noneBtn" onclick="queryOrder();">
										</td>
									</tr>
								</table>
						
						</div>
					</td>
				</tr>
			</table>
			
			<br/>
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
						<ec:table items="stockTransferOrders" var="map" width="100%" form="queryForm"
							action="${ctx}/stockTransferOrderQueryAction!query"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="StockTransferOrderList.xls" tooltip="导出Excel" />
							<ec:row>
								<ec:column property="null" alias="ec_choose" title="选择"
									width="5%"  headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="ec_choose" value="${map.orderId}" />
								</ec:column>
								<ec:column property="orderId" title="订单号" width="6%"  >
									<a href="javascript:viewOrder('${map.orderId}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="createEntityName" title="订单发起者" width="10%" />
								<ec:column property="firstEntityName" title="调出机构" width="10%"  />
								<ec:column property="processEntityName" title="调入机构" width="10%"  />
								<ec:column property="orderType" title="订单类型" width="10%" cell="dictInfo" alias="205" />								
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="orderDate" title="订单日期" width="10%" parse="yyyyMMdd"
									cell="date" format="yyyy-MM-dd" />	
								<ec:column property="createUser" title="创建人" width="10%" />
								<ec:column property="orderState" title="订单状态"  width="10%"  cell="dictInfo" alias="313" >
								</ec:column>
							</ec:row>
						</ec:table>
						</div>
					</td>
				</tr>
			</table>
			</s:form>
		</div>
		<br/>
	</body>
</html>