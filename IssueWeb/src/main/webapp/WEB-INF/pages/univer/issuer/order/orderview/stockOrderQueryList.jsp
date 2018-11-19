<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单查询</title>
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
				var listForm = Ext.get("listForm").dom;
        		if (listForm['ec_eti'] != null) {
           		 	listForm['ec_eti'].disabled=true;
       			 }
				listForm.action="stockOrderQueryAction!list";
				listForm.submit();
			}
			

			function viewOrder(orderId,orderType){
				window.open("${ctx}/${actionName}!view.action?sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
			}
			function printStock(){
			if (listForm['ec_eti'] != null) {
				listForm['ec_eti'].disabled=true;
		    }
		 	var inputStr;
				var n=0;
				var checkbox=document.getElementsByName("choose");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						inputStr=checkbox[i].value;
						n++;
					}
				}
				if(n==0){
					errorDisplay('请选择一个订单！');
					return;
				}
				if(n!=1){
					errorDisplay('只能选择一个订单！');
					return;
				}
				var inputArr=inputStr.split(',');
				document.listForm.action = "stockOrderQueryAction!queryForOrderStockPrint.action?sellOrderDTO.orderId="+inputArr[0]+"&sellOrderDTO.orderType="+inputArr[1];
				document.listForm.submit();
		 }
			
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单查询</span>
		</div>
		
		
		
		<div id="ContainBox">
		<s:form id="listForm" name="listForm" action="stockOrderQueryAction!list">
		<!-- 查询条件 -->
		<div id="queryBox">
			<%@ include file="../orderview/stockOrderQueryInputForQuery.jsp" %>
		</div>
		<!--<s:hidden name="sellOrderQueryDTO.orderId" />
		<s:hidden name="sellOrderQueryDTO.createUser" />
		<s:hidden name="sellOrderQueryDTO.firstEntityId" />
		<s:hidden name="sellOrderQueryDTO.firstEntityName" />-->
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
							action="${ctx}/stockOrderQueryAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="StockOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}' );">
								<ec:column property="null" alias="choose" title="选择"
									width="5%"  headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="choose" value="${map.orderId},${map.orderType}"/>
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId" 
								 title="订单号" width="6%">
									<a href="javaScript:viewOrder('${map.orderId}','${map.orderType}' );">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" 
								 width="10%" />
								<ec:column property="processEntityName" title="订单处理者" 
								 width="10%" />
								<ec:column property="productName" title="产品名称" 
								 width="10%" />
								<ec:column property="orderType" title="订单类型" 
								 width="10%" cell="dictInfo" alias="205"/>
								<ec:column property="cardQuantity" title="卡张数" 
								 width="5%" />
								<ec:column property="totalPrice" title="订单金额" 
								 width="7%" cell="currency" format="###,###,##0.00"/>
								<ec:column property="orderDate" title="订单日期" 
								 width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUserName" 
								 title="创建人" width="8%" />
								<ec:column property="orderState"  title="订单状态"  width="10%"  cell="dictInfo" alias="209">
								</ec:column>
							</ec:row>
						</ec:table>
						<!-- 
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
		                     <tr>
		                        <td align="right">
		                            <table border="0" cellpadding="0" cellspacing="0">
		                            	<tr>
		                            		<td>
	                                            <input type="button" class="btn" style="width: 120px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="printStock();" value="打印出入库凭证"/>
	                                        </td>
		                            	</tr>
		                             </table>
		                          </td>
		                     </tr>
		                </table>  
		                 -->
						</div>
					</td>
				</tr>
			</table>
			</s:form>
		</div>
		<br/>
		
		<br>
	</body>
</html>