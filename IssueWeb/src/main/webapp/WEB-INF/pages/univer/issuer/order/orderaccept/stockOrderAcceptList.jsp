<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>制卡订单接收</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
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
				document.getElementById("queryForm").action='${actionName}!list';
				document.getElementById("queryForm").submit();
			}

			function submitForm(operation) {
		   		var flag = true;
		   		
				var orderId;
				
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						flag = false;
						orderId=document.getElementsByName("choose").item(i).value;
						EditForm.orderId.value = orderId;
					}
				}	
				if(flag){
					errorDisplay("请选择一条记录操作！");
					return;
				}
				document.getElementById("operation").value = operation;
				
				if(operation=='complate'){
				    
					 EditForm.action='${ctx}/${actionName}!makeCardComplate';
					 EditForm.submit();
					 return;
				}
				if(operation=='makeCardList'){
				
					 EditForm.action='${ctx}/${actionName}!makeCardList';
					 EditForm.submit();
					 return;
				}
				if(operation=='checkCard'){
					 EditForm.action='${ctx}/${actionName}!checkCardInit?orderId='+orderId;
					 EditForm.submit();
					 return;
				}
				if(operation=='rejectToCardFileMake'){
					confirm( '确定执行退回操作吗？',cancelToCardFileMake);
					return;
				}
				if(operation=='reject'){
					confirm( '确定执行退回操作吗？',cancelOperation);
					return;
				}else{
					 EditForm.action='${ctx}/${actionName}!operate';
				     EditForm.submit();
				}
			}

			function cancelOperation(){
				EditForm.action='${ctx}/${actionName}!operate';
		        EditForm.submit();
			}

			function cancelToCardFileMake(){
				EditForm.action='${ctx}/${actionName}!rejectToCardFileMake';
		        EditForm.submit();
			}
			function hadCheckCard(){
				var orderId;
				var parameterValue=document.getElementById("parameterValue").value;
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						flag = false;
						orderId=document.getElementsByName("choose").item(i).value;
						
					}
				}	
			 var url='${ctx}/hadCheckCardForOrder.action';
			ajaxRemote(url,{'sellOrderDTO.orderId':orderId,'entitySystemParameterDTO.parameterValue':parameterValue},successFn,'json');
			
			
			}
			
			function successFn(data){
			
			var dis="disabled";
			
			  if(dis == data[0][0]){
			 
			  document.getElementById("complate").disabled=true;
			  document.getElementById("makeCardList").disabled=true;
			  }
			else{
			document.getElementById("complate").disabled=false;
			  document.getElementById("makeCardList").disabled=false;
			}
			}
			function viewOrder(orderId){
				window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
			}
			
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->制卡订单接收</span>
		</div>
		
		<!-- 查询条件 -->
		<div id="queryBox">
			<%@ include file="../orderview/stockOrderQueryInput.jsp" %>
		</div>
		
		<div id="ContainBox">
		<s:form id="listForm" name="listForm" action="stockOrderAcceptAction!list">
			<s:hidden name="sellOrderQueryDTO.orderId" />
			<s:hidden name="sellOrderQueryDTO.createUser" />
			<s:hidden name="sellOrderQueryDTO.firstEntityId" />
			<s:hidden name="sellOrderQueryDTO.firstEntityName" />
			<s:hidden name="entitySystemParameterDTO.parameterValue" id="parameterValue" />
			
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
						<ec:table items="sellOrders" var="map" width="100%" form="listForm"
							action="${ctx}/stockOrderAcceptAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="CustomerOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder(${map.orderId});">
								<ec:column property="null" alias="choose" title="选择"
									width="5%"  sortable="false"
									viewsAllowed="html">
									<c:choose>
									<c:when test="${orderId==map.orderId }">
									    <input type="radio" name="choose" value="${map.orderId}" onclick="hadCheckCard();" checked="checked"/>
									</c:when>
									<c:otherwise>
									    <input type="radio" name="choose" value="${map.orderId}" onclick="hadCheckCard();" />
									</c:otherwise>
									</c:choose>
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
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
								<ec:column property="orderDate"  title="订单日期" width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUserName"  title="创建人" width="10%" />
								<ec:column property="orderState"  title="订单状态"  width="10%"  cell="dictInfo" alias="209">
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
						
							<s:form id="EditForm" name="EditForm" action="" method="post">
								<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
								<s:hidden name="operation" id="operation" />
								<s:hidden name="entitySystemParameterDTO.parameterValue" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
										<!-- <button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('reject');">
												退回制卡生成
											</button>
											 -->	
											 <display:security urlId="312064">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('rejectToCardFileMake');">
												退回
											</button>
											</display:security>
											<display:security urlId="312063">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('confirm');">
												提交订单
											</button>
											</display:security>
											
											<display:security urlId="312062">
											
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('makeCardList');" ${display} id="makeCardList">
												修改制卡状态
											</button>
											</display:security>
											<display:security urlId="312061">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('complate');" ${display} id="complate">
												制卡完成
											</button>
											</display:security>
											
											<display:security urlId="312060">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('checkCard');">
												验卡
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
	</body>
</html>