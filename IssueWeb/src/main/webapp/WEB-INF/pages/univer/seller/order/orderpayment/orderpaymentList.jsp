<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单付款确认</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/jquery.jsp" %>
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
				var count=0;
				var orderId;
				var orderType;
				var ids=new Array();
				var states=new Array();
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						orderId = document.getElementsByName("ec_choose").item(i).value;
						var order_type = orderId.split(",");
						EditForm.orderId.value = order_type[0];
						EditForm.orderType.value = order_type[1];
						EditForm.paymentState.value = order_type[2];
						ids[count]=new Array(order_type[0]);
						states[count]=new Array(order_type[2]);
						count++;
					}
				}
				if(count==1){
					if(method=='pay'&& EditForm.paymentState.value=='0'){
						document.EditForm.action="orderPaymentAction!"+method;
						document.EditForm.submit();
						return;
					}else if(method=='pay'&& EditForm.paymentState.value!='0'){
						alert('订单已确认！');
						return;
					}else if(method=='submitConfirm'&& EditForm.paymentState.value=='2'){
						document.EditForm.action="orderPaymentAction!"+method;
						document.EditForm.submit();
						return;
					}else{
						alert('订单还未确认');
						return;
					}
				}else{
					//if(method='pay'){
					//	for(i=0;i<states.length;i++){
					//		if('0'!=states[i]){
					//			alert('订单已确认！');
					//			return;
					//		}
					//	}
					//	document.EditForm.action="orderPaymentAction!combine.action?sellOrderDTO.orderIds="+ids;
					//	document.EditForm.submit();
					//	return;
					//}else{
						errorDisplay("请选择一条记录!");
					//}
				}
			}
			
			 function viewOrder(orderId,orderType){
	    			window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	 }
			 
			 function change(){
				 var term = $("#paymentTerm").val();
				 if(1==term){
					 $("#orderType").val("10000003");
				 }
			 }
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单付款确认</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm"
								action="orderPaymentAction!list">
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
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														订单号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderId"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														订单类型:
													</td>
													<td>
														<s:select id="orderType" name="sellOrderQueryDTO.orderTypeArray" list="#{'':'------全部------','10000001':'销售记名卡订单(企业)','10000002':'销售不记名卡订单(企业)','10000011':'销售记名卡订单(个人)','10000012':'销售不记名卡订单(个人)','10000003':'充值订单','60000001':'换卡订单','70000001':'赎回订单'}"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														订单状态:
													</td>
													<td>
														<dl:dictList displayName="sellOrderQueryDTO.orderState"
														dictType="209" dictValue="${sellOrderQueryDTO.orderState}"
														tagType="2" defaultOption="true" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														支付节点:
													</td>
													<td>
													<s:select id="paymentTerm" name="sellOrderQueryDTO.paymentTerm" list="#{'':'-全部-','1':'充值前','2':'激活前','3':'配送前','4':'信用支付'}" onchange="change();"></s:select>
													<%--
														<dl:dictList displayName="sellOrderQueryDTO.paymentTerm"
														dictType="207" dictValue="${sellOrderQueryDTO.paymentTerm}"
														tagType="2" defaultOption="true"/>
														--%>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														支付状态:
													</td>
													<td>
													<s:select name="sellOrderQueryDTO.paymentState" list="#{'':'-全部-','2':'已确定','0':'未支付'}"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														付款渠道:
													</td>
													<td>
														<s:select name="sellOrderQueryDTO.paymentType" list="#{'':'-全部-','1':'现金','0':'非现金'}"></s:select>
													</td>
												</tr>
											</table>
										</td>										
									</tr>
									
									<tr height="35">
									<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														销售人员:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.saleManName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														创建人:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.createUserName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														订单发起者:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.entityName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														订单处理者:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.processEntityName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														订单日期From:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderDateStart"
															id="orderId" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														To:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderDateEnd"
															id="orderId" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr >
										<td colspan="3" align="right">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="noneBtn" onclick="queryOrder();">&nbsp;&nbsp;&nbsp;&nbsp;
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
							action="${ctx}/orderPaymentAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="CustomerOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder(${map.orderId},${map.orderType});">
								<ec:column property="null" alias="ec_choose" title="选择"
									width="4%"  sortable="false"
									viewsAllowed="html">
									<input type="checkbox" name="ec_choose" value="${map.orderId},${map.orderType},${map.paymentState}" />
									<input type="hidden" name="ec_chooseOrderType" value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId" title="订单号" width="5%" >
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" width="8%" />
								<ec:column property="processEnntityName" title="订单处理者" width="8%" />
								<ec:column property="productName" title="产品名称" width="7%" />
								<ec:column property="orderType" title="订单类型" width="9%" cell="dictInfo" alias="205" />								
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="orderDate" title="订单日期" width="8%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="salManName" title="销售人" width="6%" />
								<ec:column property="createUser" title="创建人" width="6%" />
								<ec:column property="paymentTerm" title="支付节点" width="6%" >
								<pay:payment orderType="${map.orderType}" paymentTerm="${map.paymentTerm}"></pay:payment>
								<%--
								<s:if test="#attr['map'].orderType==10000003 && #attr['map'].paymentTerm==3 ">
									充值前
								</s:if>
								<s:else>
									<dl:dictList dictType="207" tagType="1" dictValue="${map.paymentTerm}" defaultOption="false"></dl:dictList>
								</s:else>
								--%>
								</ec:column>
								<ec:column property="paymentState" title="支付状态" width="6%" cell="dictInfo" alias="212" >
								</ec:column>
								<ec:column property="paymentTypeDesc" title="付款渠道" width="6%"  > 
								${map.paymentTypeDesc}
										</ec:column>
								<ec:column property="initActStat" title="是否激活" width="6%" cell="dictInfo" alias="213" >
								</ec:column>
								<ec:column property="orderState" title="订单状态"  width="6%"  cell="dictInfo" alias="209" >
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
								<s:hidden name="sellOrderDTO.paymentState" value="" id="paymentState"/>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="410211">
												<div id="addBtn" class="btn"
												style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="return submitForm('submitConfirm');">
														付款审核
												</div>
											</display:security>
											<display:security urlId="410212">
												<div id="addBtn" class="btn"
												style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="return submitForm('pay');">
														付款确认
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