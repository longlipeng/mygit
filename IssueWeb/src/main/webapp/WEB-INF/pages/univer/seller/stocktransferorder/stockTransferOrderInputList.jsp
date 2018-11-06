<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单录入</title>
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

			function submitForm(method) {
				document.queryForm.ec_eti.value = '';
			    //var contractState = '${sellOrderQueryDTO.contractState}';
				if(method=="add") {
					EditForm.action = "stockTransferOrderInputAction!add";
					EditForm.submit();
					return;
				}
				if(method=="addCreditOrder"){
				if(contractState==2)
					{
					   errorDisplay("该营销机构没有与上级签订合同，不允许添加充值订单！");
					   return;
					}
					if(contractState==0)
					{
					   errorDisplay("该营销机构的合同无效，不允许添加充值订单！");
					   return;
					}
					EditForm.action = "creditOrderAction!addCreditOrder";
					EditForm.submit();
					return;
				}
				
				var flag = true;
				var orderId;
				var orderType;
				var orderPaymentState;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						if(method=="submit") {
							document.queryForm.action = "stockTransferOrderInputAction!submitOrder";
							document.queryForm.submit();
							return;
						} else if (method=="cancel") {
							confirm( '确定执行取消操作吗？',operation);
							return;
						} else if (method=="edit") {
							if (flag) {
								flag = false;
								orderId = document.getElementsByName("ec_choose").item(i).value;
							} else {
								flag = true;
								errorDisplay("请选择一条记录操作！");
								return;
							}
						}
					}
				}
				if (method=="edit" && !flag) {
					EditForm.orderId.value = orderId;
					EditForm.action = "stockTransferOrderInputAction!stockTransferOrderEdit?orderId="+orderId;
		            EditForm.submit();
					return;
				}
				errorDisplay("请选择一条记录！");
			}
			
			 function viewOrder(orderId){
    			window.open("${ctx}/stockTransferOrderInputAction!view.action?sellOrderDTO.orderId="+orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
    	  	}
			function operation(){
				document.queryForm.action = "stockTransferOrderInputAction!cancel";
				document.queryForm.submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>调拨订单管理-->库存调拨订单录入</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm" action="stockTransferOrderInputAction!list">
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
							action="${ctx}/stockTransferOrderInputAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="StockTransferOrderList.xls" tooltip="导出Excel" />
							<ec:row>
								<ec:column property="null" alias="ec_choose" title="选择"
									width="5%"  headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="ec_choose" value="${map.orderId}" />
								</ec:column>
								<ec:column property="orderId" title="订单号" width="6%" >
									<a href="javascript:viewOrder('${map.orderId}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="createEntityName" title="订单发起者" width="10%" />
								<ec:column property="firstEntityName" title="调出机构" width="10%" />
								<ec:column property="processEntityName" title="调入机构" width="10%" />
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
		<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm"
								action="stockTransferOrderInputAction!stockTransferOrderEdit">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
								<s:hidden name="sellOrderDTO.orderType" id="orderType" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="410514">
											<div id="deleteBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('cancel');">
												取消
											</div>
											</display:security>
											<display:security urlId="410513">
											<div id="modifyBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('edit');">
												编辑
											</div>
											</display:security>
											<display:security urlId="410512">
											<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('add');">
												添加
											</div>
											</display:security>
											<display:security urlId="410511">
											<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('submit');">
												提交
											</div>
											</display:security>
											<div style="clear: both"></div>
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