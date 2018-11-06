<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单录入</title>
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
				document.getElementById("queryForm").action="${actionName}!list.action";
				document.getElementById("queryForm").submit();
			}
			
			function submitForm(method) {
				if(method=="add") {
					EditForm.action = "stockOrderInputAction!add";
					EditForm.submit();
					return;
				}
				var flag = true;
				var orderId;
				var orderType;
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						if(method=="batchSubmit") {
							listForm.action = "stockOrderInputAction!"+method;
							listForm.submit();
							return;
						} else if (method=="batchCancel") {
							confirm( '确定执行取消操作吗？',operation);
							return;
						} else if (method=="edit") {
							if (flag) {
								flag = false;
								orderId = document.getElementsByName("choose").item(i).value;
								
								//orderType = document.getElementsByName("ec_chooseOrderType").item(i).value;
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
					EditForm.orderId.action="stockOrderInputAction!edit";
			        EditForm.submit();
					return;
				}
				errorDisplay("请选择一条记录！");
			}

			function viewOrder(orderId){
				window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
			}
			
			function operation(){
				listForm.action = "stockOrderInputAction!batchCancel";
				listForm.submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单录入</span>
		</div>
		
		<!-- 查询条件 -->
		<div id="queryBox">
			<%@ include file="../orderview/stockOrderQueryInput.jsp" %>
		</div>
		
		<div id="ContainBox">
		<s:form id="listForm" name="listForm" action="orderInputAction!list" method="post">
			<s:hidden name="sellOrderQueryDTO.orderId" />
			<s:hidden name="sellOrderQueryDTO.createUser" />
			<s:hidden name="sellOrderQueryDTO.firstEntityId" />
			<s:hidden name="sellOrderQueryDTO.firstEntityName" />
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
							action="${ctx}/stockOrderInputAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:row ondblclick="javaScript:viewOrder(${map.orderId});">
								<ec:column property="null" alias="choose" title="选择"
									width="5%"  headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="choose" value="${map.orderId}" />
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
						
							<s:form id="EditForm" name="EditForm" action="stockOrderInputAction!edit">
								<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="312024">
											<div id="deleteBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('batchCancel');">
												取消
											</div>
											</display:security >
											<display:security urlId="312023">
											<div id="modifyBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('edit');">
												编辑
											</div>
											</display:security >
											<display:security urlId="312022">
											<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('add');">
												添加
											</div>
											</display:security >
											 <display:security urlId="312021">
											<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="return submitForm('batchSubmit');">
												提交
											</div>
											  </display:security >
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