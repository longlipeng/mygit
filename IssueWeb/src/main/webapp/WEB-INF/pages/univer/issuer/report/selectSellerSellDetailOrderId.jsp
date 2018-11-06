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
				queryForm['ec_eti'].disabled=true;
				queryForm.submit();
			}
			
			function submitForm(method) {
				if(EditForm['ec_eti']!=null){
		 		 EditForm["ec_eti"].disabled=true;
		   		}
				if(method=="add") {
					EditForm.action = "orderInputAction!add";
					EditForm.submit();
					return;
				}
				var flag = true;
				var orderId;
				var orderType;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						if(method=="submit") {
							document.forms[0].action = "customerOrderAction!"+method;
							document.forms[0].submit();
							return;
						} else if (method=="cancel") {
							confirm( '确定执行取消操作吗？',operation);
							return;
						} else if (method=="edit") {
							if (flag) {
								flag = false;
								orderId = document.getElementsByName("ec_choose").item(i).value;
								orderType = document.getElementsByName("ec_chooseOrderType").item(i).value;
							} else {
								flag = true;
								errorDisplay("请选择一条记录操作！");
								return;
							}
						}
					}
				}
				if (method=="edit" && !flag) {
					hrefOrderId(orderId,orderType,'edit');
					return;
				}
				errorDisplay("请选择一条记录！");
			}
			
			 function viewOrder(orderId,orderType){
	    			window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	  	}
				
			function hrefOrderId(orderId,orderType,method) {
				
				switch (orderType) {
                	case '10000001' : EditForm.action = "sellOrderSignAction!"+method;
                			 break;
                	case '10000002' : EditForm.action = "sellOrderUnsignAction!"+method;
                			 break;
                	case '3' : EditForm.action = "giftStockOrderAction!"+method;
                			 break;
                	case '4' : EditForm.action = "giftSaleOrderAction!"+method;
                			 break;
                	case '5' : EditForm.action = "giftChangeOrderAction!"+method;
                			 break;
                	case '6' : EditForm.action = "reloadableCardChangeOrderAction!"+method;
                			 break;
                	case '7' : EditForm.action = "giftSendBackOrderAction!"+method;;
                			 break;
                	case '8' : EditForm.action = "reloadableCardDelayOrderAction!"+method;;
                			 break;
                	case '9' : EditForm.action = "dispatchOrderAction!"+method;;
                			 break;
                	
                }
                EditForm.orderId.value = orderId;
		        EditForm.submit();
			}
			
			function operation(){
				document.queryForm.action = "orderInputAction!cancel";
				document.queryForm.submit();
			}
			
			function selectOrderId(){
			var id='';
			var n=0;
			var checkbox=document.getElementsByName("ec_choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择一个发行机构！');
				return;
			}
			window.returnValue=id;
			window.close();  
		}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单查询</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm"
								action="orderQueryAction!query">
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
										
										
										<td width="85" align=right>
											<span>订单状态：</span>
										</td>
										<td width="160">
											
											<dl:dictList displayName="sellOrderQueryDTO.orderState"
												dictType="209" dictValue="${sellOrderQueryDTO.orderState}"
												tagType="2" defaultOption="true" valueRange="2" />
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
							action="${ctx}/orderQueryAction!query"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="SellOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}');">
								<ec:column property="null" alias="ec_choose" title="选择"
									width="5%" sortable="false" headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="ec_choose" value="${map.orderId}" />
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId" title="订单号" width="6%">
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" width="10%" />
								<ec:column property="processEnntityName" title="订单处理者" width="10%" />
								<ec:column property="productName" title="产品名称" width="10%" />
								<ec:column property="orderType" title="订单类型" width="10%" cell="dictInfo" alias="205"/>								
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="orderDate" title="订单日期" width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUser" title="创建人" width="10%" />
								<ec:column property="orderState" title="订单状态"  width="10%"  cell="dictInfo" alias="209">
								</ec:column>
							</ec:row>
						</ec:table>
								
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
		                     <tr>
		                        <td align="right">
		                            <table border="0" cellpadding="0" cellspacing="0">
		                                <tr>
		                                        <td>
		                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="selectOrderId();" value="提 交"/>
		                                        </td>
		                                        <td>
		                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="window.close();" value="关 闭"/>
		                                        </td>
		                                </tr>
		                            </table>
		                        </td>
		                    </tr>
		                </table>
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
								action="sellOrderAction!edit">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
							</s:form>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>