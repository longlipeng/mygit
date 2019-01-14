<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>制卡订单准备</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
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

			function readyList() {
				var resultValue = null;
				resultValue = window.showModalDialog('${ctx}/${actionName}!orderReadyCardList.action?sellOrderDTO.orderId='+${sellOrderDTO.orderId}, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
				if(resultValue == 'success'){
					reload();
				}
			}
			
			function reload(){
				orderListForm.action='${actionName}!orderReadyList';
				orderListForm.submit();
			}

			function delCardList() {
		   		var flag = true;
				var orderCardListId;
				
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						flag = false;
						orderCardListId=document.getElementsByName("choose").item(i).value;
						EditForm.orderCardListId.value = orderCardListId;
					}
				}	
				
				if(flag){
					errorDisplay("请选择一条记录操作！");
					return;
				}
				confirm( '确定执行删除操作吗？',cancelOperation);
			}

			function cancelOperation(){
				EditForm.action='${actionName}!deleteCardList';
		        EditForm.submit();
			}
			
			function queryOrder(){
				document.getElementById("queryForm").action='!list';
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
		<div id="queryBox" align="center">
			
		</div>
		<div width="98%" id="orderListBox"  style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="orderListForm" name="orderListForm" action="">
				<s:hidden name="sellOrderDTO.orderId"/>
					
				<div id="orderListTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront" onclick="displayTableBody();"
								style="cursor: pointer;">
								<span class="TableTop">订单明细</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="orderListTable" style="background-color: #FBFEFF;  border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="orderListDTOs" var="map" width="100%" form="orderListForm"
						action="${ctx}/stockOrderReadyAction!orderReadyList"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" tableId="orderListRow" autoIncludeParameters="false" >
						<ec:row ondblclick="">
							<ec:column property="orderListId" title="订单明细号" width="10%" />
							<ec:column property="realAmount" title="配送张数" width="10%" />
							<ec:column property="cardAmount" title="卡张数" width="10%" />
							<ec:column property="cardLayoutName" title="卡面" width="10%" />
							<ec:column property="faceValueType" title="面额类型" width="10%" >
							<s:property value="#attr['map'].faceValueType==1?'非固定面额':'固定面额'"/>
							</ec:column>
							<ec:column property="faceValue" title="面额值" width="10%" />
						</ec:row>
					</ec:table>
				</div>
			</s:form>
		</div>
		
		<br/>
		<br/>
		
		<div width="98%" id="orderCardListBox" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="orderCardListForm" name="orderCardListForm" action="">
				<s:hidden name="sellOrderDTO.orderId"/>
				<div id="orderCardListTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront" onclick="displayTableBody();"
								style="cursor: pointer;">
								<span class="TableTop">订单卡明细</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="orderCardListTable">
					<ec:table items="orderCardListDTOs" var="map" width="100%" form="orderCardListForm"
						action="${ctx}/stockOrderReadyAction!orderReadyList"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" tableId="orderCardListRow" autoIncludeParameters="false" >
						<ec:row ondblclick="">
							<ec:column property="null" alias="choose" title="选择" width="5%" sortable="false"  viewsAllowed="html">
								<input type="radio" name="choose" value="${map.orderCardListId}" />
							</ec:column>
							<ec:column property="orderCardListId" title="卡明细号" width="5%" />
							<ec:column property="orderListId" title="订单明细号" width="5%" />
							<ec:column property="cardNo" title="卡号" width="10%" />
							<ec:column property="cardState" title="卡片状态"  width="5%"  cell="dictInfo" alias="309" />
						</ec:row>
					</ec:table>
				</div>
			</s:form>
		</div>
			
			
			<s:form id="EditForm" name="EditForm" action="">
				<s:hidden name="sellOrderDTO.orderId"/>
				<s:hidden name="sellOrderCardListDTO.orderCardListId" id="orderCardListId" />												
			</s:form>
			<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<s:form action="stockOrderReadyAction!list" name="backForm" id="backForm">
											<button class='btn' style="float: right; margin: 7px" 
												onclick="backForm.submit();">
												返  回
											</button>
											</s:form>
											<button class='btn' style="float: right; margin: 7px" 
												onclick="delCardList();">
												删  除
											</button>
											<button class='btn' style="float: right; margin: 7px"
												onclick="readyList();">
												添  加
											</button>
										</div>
									</td>
								</tr>
			</table>
			
		
		
	</body>
</html>