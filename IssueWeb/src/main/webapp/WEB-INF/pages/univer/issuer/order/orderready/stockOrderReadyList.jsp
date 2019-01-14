<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>制卡订单准备</title>
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

			var globalProductId = null;
			var globalOrderType = null;
			var globalCardLayoutId = null;
			var globalFaceValueType = null;
			var globalFaceValue = null;
			function addBuyOrder() {
				var selectCount=0;
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						selectCount++;
					}
				}
				if(selectCount==0){
					errorDisplay("请选择一条记录!");
					return;
				}
				if(selectCount>1){
					errorDisplay("只能选择一条记录操作!");
					return;
				}
				var count=0;
				var orderId;
				var productId;
				var inputStr;
				var inputArr;
				var orderType;
				var cardLayoutId;
				var faceValueType;
				var faceValue;
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						inputStr = document.getElementsByName("choose").item(i).value;
						inputArr = inputStr.split(",");
						orderId = inputArr[0];
						productId = inputArr[1];
						orderType = inputArr[2];
						cardLayoutId = inputArr[3];
						faceValueType = inputArr[4];
						faceValue = inputArr[5];
						if(count==0){
							globalProductId = inputArr[1];
							globalOrderType = inputArr[2];
							globalCardLayoutId = inputArr[3];
							globalFaceValueType = inputArr[4];
							globalFaceValue = inputArr[5];
							
							readyForm.productId.value = inputArr[1];
							readyForm.orderType.value = inputArr[2];
							readyForm.cardLayoutId.value = inputArr[3];
							readyForm.faceValueType.value = inputArr[4];
							readyForm.faceValue.value = inputArr[5];
						}else{
							if(productId != globalProductId){
								errorDisplay("所选订单产品类型必须相同！");
								return;
							}
							if(orderType != globalOrderType){
								switch (orderType+globalOrderType) {
					            	case '3000000140000001' : execBuyOrder();break;
					            	case '4000000130000001' : execBuyOrder();break;
					            	case '3000000240000002' : execBuyOrder();break;						 
					            	case '4000000230000002' : execBuyOrder();break;
				            	}
								errorDisplay("所选订单类型必须相同！");
								return;
							}
							//记名
							if(orderType=='30000001'||orderType=='40000001'){
								if(cardLayoutId != globalCardLayoutId){
									errorDisplay("记名订单卡面必须相同！");
									return;
								}
								if(faceValueType!=globalFaceValueType){
									errorDisplay("记名订单面额类型必须相同！");
									return;
								}
								//固定面额面额值必须相同
								if(faceValueType='0'){
									if(faceValue!=globalFaceValue){
										errorDisplay("记名订单面额值必须相同！");
										return;
									}
								}
							}
						}
						count++;
					}
				}
				if(count<1){
					errorDisplay("请选择要生成的订单记录!");
				}else{
					execBuyOrder();
				}
				
			}

			function execBuyOrder(){
				document.readyForm.action="stockOrderReadyAction!makeLoyaltyOrder";
				document.readyForm.submit();
			}
			
			function submitForm(operation) {
		   		var flag = true;
		   		var inputStr;
		   		var inputArr;
				var orderId;
				
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						flag = false;
						inputStr=document.getElementsByName("choose").item(i).value;
						inputArr = inputStr.split(",");
						orderId=inputArr[0];
						EditForm.orderId.value = orderId;
					}
				}	
				if(flag){
					errorDisplay("请选择一条记录操作！");
					return;
				}
				if(operation=='ready'){
					EditForm.action='${actionName}!orderReadyList';
					EditForm.submit();
					return;
				}
				document.getElementById("operation").value = operation;
				if(operation=='reject'){
					confirm( '确定执行退回操作吗？',cancelOperation);
					return;
				}else{
				       EditForm.submit();
				}
			}

			function ready(orderId){
				 EditForm.orderId.value = orderId;
				 document.EditForm.action = "${actionName}!orderReadyList";
				 document.EditForm.submit();
			}

			function cancelOperation(){
		        EditForm.submit();
			}
			
			function viewOrder(orderId){
				window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
			}

			function queryOrder(){
		    	document.getElementById("queryForm").action='${actionName}!list.action';
				document.getElementById("queryForm").submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->制卡订单准备</span>
		</div>
		
		<!-- 查询条件 -->
		<div id="queryBox">
			<%@ include file="../orderview/stockOrderQueryInput.jsp" %>
		</div>
		
		<div id="ContainBox">
		<s:form id="readyForm" name="readyForm" action="stockOrderReadyAction!list">
			<s:hidden name="sellOrderQueryDTO.orderId" />
			<s:hidden name="sellOrderQueryDTO.createUserName" />
			<s:hidden name="sellOrderQueryDTO.firstEntityId" />
			<s:hidden name="sellOrderQueryDTO.firstEntityName" />
			<s:hidden name="sellBuyOrderDTO.productId"  id="productId"/>
			<s:hidden name="sellBuyOrderDTO.orderType" id="orderType"/>
			<s:hidden name="sellBuyOrderDTO.cardLayoutId" id="cardLayoutId" />
			<s:hidden name="sellBuyOrderDTO.faceValueType" id="faceValueType" />
			<s:hidden name="sellBuyOrderDTO.faceValue" id="faceValue" />
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
						<ec:table items="sellOrders" var="map" width="100%" form="readyForm"
							action="${ctx}/${actionName}!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="OrderReadyList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder(${map.orderId});">
								<ec:column property="null" alias="choose" title="选择"
									width="5%"  sortable="false"
									viewsAllowed="html">	
									<input type="radio" name="choose" value="${map.orderId},${map.productId},${map.orderType},${map.cardLayoutId},${map.faceValueType},${map.faceValue}"  />
									<input type="hidden" name="ec_chooseOrderType" value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId"  title="订单号" width="6%">
									<a href="javascript:viewOrder('${map.orderId}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName"  title="订单发起者" width="10%" />
								<ec:column property="processEntityName"  title="订单处理者" width="10%" />
								<ec:column property="productName"  title="产品名称" width="10%" />
								<ec:column property="orderType"  title="订单类型" width="10%" cell="dictInfo" alias="205"/>
								<ec:column property="cardQuantity"  title="卡张数" width="5%" />
								<ec:column property="realCardQuantity"  title="配送张数" width="8%" />
								<ec:column property="orderDate"  title="订单日期" width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUserName"  title="创建人" width="7%" />
								<ec:column property="orderState"  title="订单状态"  width="10%"  cell="dictInfo" alias="209">
								</ec:column>
								<ec:column property="null"  title="操作" width="5%">
										<s:if test="#attr.map.orderType==30000002">
                                       	 <a href="javascript:ready('${map.orderId}');"> 准备 </a>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==40000002">
                                       	 <a href="javascript:ready('${map.orderId}');"> 准备 </a>
                                        </s:if>
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
					<td width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<s:form id="EditForm" name="EditForm" action="stockOrderReadyAction!operateReady">
								<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
								<s:hidden name="operation" id="operation" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<display:security urlId="312073">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('reject');">
												退回订单
											</button>
											</display:security>
											<display:security urlId="312072">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('confirm');">
												提交订单
											</button>
											</display:security>
<%--											<button class='btn' style="float: right; margin: 7px"--%>
<%--												onclick="submitForm('ready');">--%>
<%--												订单准备--%>
<%--											</button>--%>
											<display:security urlId="312071">
											<button class='btn' style="float: right; margin: 7px"
												onclick="addBuyOrder();">
												生成采购订单
											</button>
											</display:security>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<form id="confirmForm" name="confirmForm" action="" method="post">
			<s:hidden name="operation" id="operation" />
			<s:hidden name="actionName" id="actionName" />
		</form>
	</body>
</html>