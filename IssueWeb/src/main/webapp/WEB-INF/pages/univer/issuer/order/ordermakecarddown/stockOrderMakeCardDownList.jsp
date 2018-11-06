<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>客户订单管理-->订单制卡</title>
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
			
			function viewOrder(orderId){
				window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
			}

			function queryOrder(){
				document.getElementById("queryForm").action='${actionName}!list';
				document.getElementById("queryForm").submit();
			}
	    	
		    function submitForm(operation) {
		   		var flag = true;
				var orderId;
				
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						flag = false;
						document.confirmForm.orderId.value=document.getElementsByName("choose").item(i).value;
						document.getElementById("orderId").value= document.getElementsByName("choose").item(i).value;
					}
				}	
				if(flag){
					errorDisplay("请选择一条记录操作！");
					return;
				}
				document.getElementById("operation").value = operation;
				if(operation=='confirm'){
					document.confirmForm.action='${actionName}!operate';
					document.confirmForm.submit();
					return;
					
				}
				if(operation=='reject'){
					confirm( '确定执行退回操作吗？',cancelOperation);
					return;
				}else{
					document.confirmForm.action = "${actionName}!operate";
					document.confirmForm.submit();
				}
			}

		    function cancelOperation(){
		    	document.confirmForm.action = "${actionName}!operate";
		    	document.confirmForm.submit();
			}
		    
		    function down(){
		    	var flag = true;
				var orderId;
				
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
								flag = false;
								document.confirmForm.orderId.value=document.getElementsByName("choose").item(i).value;
								document.getElementById("orderId").value= document.getElementsByName("choose").item(i).value;
						}
					}	
				if(flag){
					errorDisplay("请选择一条记录操作！");
					return;
				}
				document.confirmForm.action = "${actionName}!downMakeCardFile";
				document.confirmForm.submit();
			}
		    function viewDetail(orderId,batchNo,flag){
		      	 window.showModalDialog('${ctx}/batchfile/makeCardView.action?order_id='+orderId+'&batch_id='+batchNo+'&flag='+flag, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		     }
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理-->订单制卡确定</span>
		</div>
		
		<!-- 查询条件 -->
		<div id="queryBox">
			<%@ include file="../orderview/stockOrderQueryInput.jsp" %>
		</div>
		<s:form id="orderMakeCardForm" name="orderMakeCardDownForm" action="" method="post">
		<s:hidden name="sellOrderQueryDTO.orderId" />
		<s:hidden name="sellOrderQueryDTO.createUser" />
		<s:hidden name="sellOrderQueryDTO.firstEntityId" />
		<s:hidden name="sellOrderQueryDTO.firstEntityName" />
		<s:hidden name="companyId" />
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
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="TableBody">
							<ec:table items="sellOrders" var="map" width="100%" form="orderMakeCardDownForm"
							action="${ctx}/${actionName}!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
								<ec:exportXls fileName="SellOrderList.xls" tooltip="导出Excel" />
								<ec:row ondblclick="javaScript:viewOrder(${map.orderId});">
								<ec:column property="null" alias="choose" title="选择" width="5%" sortable="false"  viewsAllowed="html">
									<input type="radio" name="choose" value="${map.orderId}" />
									<input type="hidden" name="ec_chooseOrderType" value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId" title="订单号" width="6%">
									<a href="javascript:viewOrder('${map.orderId}');">${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" width="10%" />
								<ec:column property="processEntityName" title="订单处理者" width="10%" />
								<ec:column property="productName" title="产品名称" width="10%" />
								<ec:column property="orderType" title="订单类型" width="10%" cell="dictInfo" alias="205"/>
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="orderDate" title="订单日期" width="10%" cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUserName" title="创建人" width="10%" />
								<ec:column property="orderState" title="订单状态"  width="10%"  cell="dictInfo" alias="209" />
								<ec:column property="orderState" title="明细" width="6%" viewsDenied="xls">
									<s:if test="#attr['map'].orderState == 34" >
										<a href="javascript:viewDetail(${map.orderId},${map.batchNo}, 2)">
											明细</a>								
									</s:if>
									<s:elseif test="#attr['map'].orderState == 33 || #attr['map'].orderState == 4">
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
		</div>
		</s:form>
		<table width="100%" height="20" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div id="buttonCRUD" style="text-align: right; width: 100%; height: 30px;">
						<display:security urlId="312043">
						<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('cancel');">
							取消订单
						</button>
						</display:security>
						<display:security urlId="312042">
						<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('reject');">
							退回订单
						</button>
						</display:security>
						<display:security urlId="312041">
						<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('confirm');">
							提交订单
						</button>
						</display:security>
					</div>
				</td>
			</tr>
		</table>
        
		<br>
		<form id="confirmForm" name="confirmForm" action="" method="post">
			<s:hidden name="companyId" id="companyId" />
			<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
			<s:hidden name="operation" id="operation" />
			<s:hidden name="actionName" id="actionName" />
		</form>
	</body>
</html>