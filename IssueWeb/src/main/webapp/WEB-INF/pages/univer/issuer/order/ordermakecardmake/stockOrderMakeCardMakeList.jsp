<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>制卡文件下载</title>
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

	function submitForm(operation) {
		var flag = true;
		var orderId;
		
		for(i = 0; i < document.getElementsByName("choose").length; i++) {
			if (document.getElementsByName("choose").item(i).checked) {
				flag = false;
				orderId=document.getElementsByName("choose").item(i).value;
				document.confirmForm.orderId.value = orderId;
			}
		}	
		if(flag){
			errorDisplay("请选择一条记录操作！");
			return;
		}
		document.getElementById("operation").value = operation;
		if(operation=='cancel'){
			confirm( '确定执行取消操作吗？',cancelOperation);
			return;
		}else if(operation=='reject'){
			confirm( '确定执行退回操作吗？',cancelOperation);
			return;
		}else{
			document.confirmForm.action = "${actionName}!operate"
			document.confirmForm.submit();
		}
	}
	
	function cancelOperation(){
		document.confirmForm.action = "${actionName}!operate"
		document.confirmForm.submit();
	}
	
	function viewOrder(orderId){
		window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	}
	
	function down(file) {
		var flag = true;
		var orderId;
		
		for(i = 0; i < document.getElementsByName("choose").length; i++) {
			if (document.getElementsByName("choose").item(i).checked) {
				flag = false;
				orderId=document.getElementsByName("choose").item(i).value;
				document.confirmForm.orderId.value = orderId;
			}
		}

		if(flag){
			errorDisplay("请选择一条记录操作！");
			return;
		}
		
		document.confirmForm.action = "${actionName}!"+file; 
		document.confirmForm.submit();
	}
	
	function queryOrder(){
		document.getElementById("queryForm").action='${actionName}!list';
		document.getElementById("queryForm").submit();
	}
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>订单管理-->制卡文件下载</span>
    </div>
   
	<!-- 查询条件 -->
	<div id="queryBox">
		<%@ include file="../orderview/stockOrderQueryInput.jsp" %>
	</div>
    <s:form id="orderMakeCardForm" name="orderMakeCardForm" action="" method="post">
      	<s:hidden name="sellOrderQueryDTO.orderId" />
		<s:hidden name="sellOrderQueryDTO.createUser" />
		<s:hidden name="sellOrderQueryDTO.firstEntityId" />
		<s:hidden name="sellOrderQueryDTO.firstEntityName" />
		
		
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
							<ec:table items="sellOrders" var="map" width="100%" form="orderMakeCardForm"
								action="${ctx}/${actionName}!list"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
									<ec:exportXls fileName="SellOrderList.xls" tooltip="导出Excel" />
									<ec:row ondblclick="javaScript:viewOrder(${map.orderId});">
									<ec:column property="null" alias="choose" title="选择" width="5%" sortable="false"  viewsAllowed="html">
										<input type="radio" name="choose" value="${map.orderId}" />
										<input type="hidden" name="ec_chooseOrderType" value="${map.orderType}">
									</ec:column>
									<ec:column property="orderId"  title="订单号" width="6%">
										<a href="javascript:viewOrder('${map.orderId}');">${map.orderId}</a>
									</ec:column>
									<ec:column property="firstEntityName"  title="订单发起者" width="10%" />
									<ec:column property="processEntityName"  title="订单处理者" width="10%" />
									<ec:column property="productName"  title="产品名称" width="10%" />
									<ec:column property="orderType"  title="订单类型" width="10%" cell="dictInfo" alias="205"/>
									<ec:column property="cardQuantity"  title="卡张数" width="5%" />
									<ec:column property="orderDate"  title="订单日期" width="10%" cell="date" format="yyyy-MM-dd" />
									<ec:column property="createUserName"  title="创建人" width="10%" />
									<ec:column property="orderState"   title="订单状态"  width="10%"  cell="dictInfo" alias="209" />
									<ec:column property="createCardFileUserName"   title="下载人" width="6%" >
							               	<!--<s:if test="%{#attr.map.isCreateCardFile==''}"> 未生成 </s:if>-->
							                <s:if test="%{#attr.map.isCreateCardFile!=''}"> ${map.createCardFileUserName} </s:if>
							         </ec:column>
								</ec:row>
							</ec:table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
    </s:form>
      <table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div id="buttonCRUD" style="text-align: right; width: 100%; height: 30px;">
						<%--<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('cancel');">
							取消订单
						</button>
						<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('reject');">
							退回订单
						</button>
						--%>
						<display:security urlId="312052">
						<button class='btn' style="float: right; margin: 7px"
							onclick="submitForm('confirm');">
							提交订单
						</button>
						</display:security>
						<display:security urlId="312051">
						<button class='btn' style="float: right; margin: 7px"
							onclick="down('makeCardFile');">
							下载制卡文件
						</button>
						</display:security>
					</div>
				</td>
			</tr>
	</table>
    <form id="confirmForm" name="confirmForm" action="" method="post">
			<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
			<s:hidden name="operation" id="operation" />
			<s:hidden name="actionName" id="actionName" />
	</form>
</body>
</html>