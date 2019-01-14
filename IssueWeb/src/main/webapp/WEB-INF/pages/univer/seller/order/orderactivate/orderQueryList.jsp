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
				queryForm.submit();
			}
			
			function submitForm(method) {
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
								orderType ="";
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
                    case '10000011' : EditForm.action = "sellOrderSignAction!"+method;
                			 break;
                	case '10000012' : EditForm.action = "sellOrderUnsignAction!"+method;
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
				errorDisplay('请选择一个订单！');
				return;
			}
			window.returnValue=id;
			window.close();  
		}
			
			function orderActivate(orderId,orderType){
				EditForm.orderId.value=orderId;
				EditForm.orderType.value=orderType;
				document.EditForm.action="orderConfirmAction!submitConfirm?buyOrderFlag=2&exFlag=1";
				document.EditForm.submit();
			}

			 function viewDetail(orderId,batchNo,flag){
		    	 window.showModalDialog('${ctx}/batchfile/actView.action?order_id='+orderId+'&batch_id='+batchNo+'&flag='+flag, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
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
								action="orderQueryAction!queryActivate?initFlag=1">
			<s:hidden name="sellOrderQueryDTO.validFlag" value="valid"></s:hidden>
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
														<s:select name="sellOrderQueryDTO.orderTypeArray" list="#{'':'------全部------','10000001,10000005,10000011':'销售记名订单','10000002,10000006,10000012':'销售不记名订单','60000001':'换卡订单'}"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														支付节点:
													</td>
													<td>
														<dl:dictList displayName="sellOrderQueryDTO.paymentTerm"
														dictType="207" dictValue="${sellOrderQueryDTO.paymentTerm}"
														tagType="2" defaultOption="true"/>
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
							action="orderQueryAction!queryActivate?initFlag=1"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}');">
								<ec:column property="orderId" title="订单号" width="5%" >
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" width="8%" />
								<ec:column property="processEnntityName" title="订单处理者" width="8%" />
								<ec:column property="productName" title="产品名称" width="7%" />
								<ec:column property="orderType" title="订单类型" width="8%" cell="dictInfo" alias="205" />								
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="orderDate" title="订单日期" width="7%"
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
								<ec:column property="orderState" title="订单状态"  width="7%"  cell="dictInfo" alias="209" >
								</ec:column>
								<ec:column title="操作" property="initActStat"  width="7%" viewsAllowed="html">									
									<s:if test="#attr['map'].initActStat==1">
										     &nbsp;
									</s:if>
									<s:elseif test="#attr['map'].initActStat==2">
										<a href="javascript:viewDetail(${map.orderId},${map.batchNo}, 1)">激活中</a>		
									</s:elseif>
									<s:elseif test="#attr['map'].initActStat==3">
										<a href="javascript:viewDetail(${map.orderId},${map.batchNo}, 2)">激活失败</a>										
									</s:elseif>
									<s:else>
										<a href="javascript:orderActivate(${map.orderId},${map.orderType})">激活</a>
									</s:else>
								</ec:column>
							</ec:row>
						</ec:table>
						
						 		
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
		                     <tr>
		                        <td align="right">
		                            <table border="0" cellpadding="0" cellspacing="0">
		                              <!--    <tr>
		                                        <td>
		                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="selectOrderId();" value="提 交"/>
		                                        </td>
		                                        <td>
		                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="window.close();" value="关 闭"/>
		                                        </td>
		                                </tr> --> 
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
								action="orderConfirmAction!button">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
								<s:hidden name="sellOrderDTO.orderType" value="" id="orderType"/>
							</s:form>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>