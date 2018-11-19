<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单配送确定</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript" src="${ctx}/widgets/js/jquery.timers.js"></script>

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
				document.queryForm['ec_eti'].disabled=true;
				document.queryForm.submit();
			}
			
			function submitForm(method) {
				var count=0;
				var orderId;
				var orderType;
				var batchNo;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						orderId = document.getElementsByName("ec_choose").item(i).value;
						var order_type = orderId.split(",");
						EditForm.orderId.value = order_type[0];
						EditForm.orderType.value = order_type[1];
						EditForm.batchNo.value= order_type[2];
						count++;
					}
				}
				if(count==1){
						document.EditForm.action="orderSendConfirmAction!"+method;
						document.EditForm.submit();
				}else{
					errorDisplay("请选择一条记录!");
				}
			}
			
			 function viewOrder(orderId,orderType){
	    			window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	 }
	    	 
	    	 function viewDetail(orderId,batchNo,flag){
	    	 window.showModalDialog('${ctx}/batchfile/rechargeActView.action?order_id='+orderId+'&batch_id='+batchNo+'&flag='+flag, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
	    	 }
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单待配送确定</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm" action="orderSendConfirmAction!list">
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
											<span>订单发起者编号：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.entityId"
												id="customerId" size="20" />
											&nbsp;
										</td>
										<td width="120" align=right>
											<span>订单发起者名称：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.entityName"
												id="entityName" size="20" />
											&nbsp;
										</td>
										
										<td align="center">
											&nbsp;
										</td>
									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>创建人名称：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.createUserName"
												id="createUser" size="14"></s:textfield>
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
						<ec:table items="sellOrders" var="map" width="100%" form="queryForm"
							action="${ctx}/orderSendConfirmAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="CustomerOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}');">
								<ec:column property="null" alias="ec_choose" title="选择"
									width="5%" sortable="false" 
									viewsAllowed="html">
									<input type="radio" name="ec_choose" value="${map.orderId},${map.orderType},${map.batchNo}" />
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId"   title="订单号" width="6%">
										
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName"   title="订单发起者" width="10%" />
								<ec:column property="processEnntityName"   title="订单处理者" width="10%" />
								<ec:column property="productName"   title="产品名称" width="10%" />
								<ec:column property="orderType"   title="订单类型" width="10%" cell="dictInfo" alias="205"/>								
								<ec:column property="cardQuantity"   title="卡张数" width="5%" />
								<ec:column property="orderDate"   title="订单日期" width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUser" title="创建人"   width="10%" />
								<ec:column property="orderState" title="订单状态"    width="10%"  cell="dictInfo" alias="209">
								</ec:column>	
								<ec:column property="orderState" title="明细"   width="6%">
									<s:if test="#attr['map'].orderState == 34" >
										<a href="javascript:viewDetail(${map.orderId},${map.batchNo}, 2)">
											明细</a>								
									</s:if>
									<s:elseif test="#attr['map'].orderState == 33">
										<a href="javascript:viewDetail(${map.orderId},${map.batchNo}, 1)">
											明细</a>		
									</s:elseif> 
									<s:else>
									&nbsp;
									</s:else>
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
								action="orderHandOutAction!button">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
								<s:hidden name="sellOrderDTO.orderType" value="" id="orderType"/>
								<s:hidden name="sellOrderDTO.batchNo" value="" id="batchNo"/>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="410072">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="return submitForm('sendBackConfirm');">
													退回
												</div>
											</display:security>
											<display:security urlId="410071">
												<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="return submitForm('submitConfirm');">
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