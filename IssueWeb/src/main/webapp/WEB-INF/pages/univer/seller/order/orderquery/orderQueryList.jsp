<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单查询</title>
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
				//由于去掉导出按钮，也需要把ec_eti注释掉
				//queryForm['ec_eti'].disabled=true;
				document.queryForm.action = "orderQueryAction!query";
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
			function downloadAll(){
				var orderDateStart = document.getElementById("orderDateStart").value;
				var orderDateEnd = document.getElementById("orderDateEnd").value;
				if (orderDateStart=='' || orderDateEnd==''){
					errorDisplay('请选择订单日期！');
					return;
				}
				document.queryForm.action = "orderQueryAction!downloadInfo.action?downloadFlg=all";
				document.queryForm.submit();
			}
			function downloadSingle(){
				var inputStr;
				var n=0;
				var checkbox=document.getElementsByName("ec_choose");
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
				if (inputArr[4]!= 0 || inputArr[5] != 32 || inputArr[6] != 1) {
					errorDisplay('只有送货上门的，未激活的并且配送成功的订单才能下载邮寄信息！');
					return;
				}
				document.queryForm.action = "orderQueryAction!downloadInfo.action?sellOrderDTO.orderId="+inputArr[0]+"&sellOrderDTO.orderType="+inputArr[1];
				document.queryForm.submit();
		}
			
			function selectOrderId(){
				var inputStr;
				var n=0;
				var checkbox=document.getElementsByName("ec_choose");
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
				document.queryForm.action = "orderQueryAction!queryForCertPrint.action?sellOrderDTO.orderId="+inputArr[0]+"&sellOrderDTO.orderType="+inputArr[1];
				document.queryForm.submit();
		}
		function printPayment(){
		 		var inputStr;
				var n=0;
				var checkbox=document.getElementsByName("ec_choose");
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
				document.queryForm.action = "orderQueryAction!queryForPaymentPrint.action?sellOrderDTO.orderId="+inputArr[0]+"&sellOrderDTO.orderType="+inputArr[1];
				document.queryForm.submit();
		 	}
		 function printStock(){
		 	var inputStr;
				var n=0;
				var checkbox=document.getElementsByName("ec_choose");
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
				document.queryForm.action = "orderQueryAction!queryForOrderStockPrint.action?sellOrderDTO.orderId="+inputArr[0]+"&sellOrderDTO.orderType="+inputArr[1];
				document.queryForm.submit();
		 }
		 
        function addInvoice(position){
		    var inputStr;
			var n=0;
			var checkbox=document.getElementsByName("ec_choose");
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
			var queryForm = document.getElementById("queryForm");
			if('basic' == position) {
			queryForm.action = "orderQueryAction!addOrderInvoiceBasicPositionInit.action?sellOrderDTO.orderId="+inputArr[0]+
				"&sellOrderDTO.orderType="+inputArr[1]+
				"&sellOrderDTO.modifyTime="+inputArr[2]+
				"&sellOrderDTO.paymentState="+inputArr[3];
			}
			if('management' == position) {
			queryForm.action = "orderQueryAction!addOrderInvoiceManagemetPositionInit.action?sellOrderDTO.orderId="+inputArr[0]+
				"&sellOrderDTO.orderType="+inputArr[1]+
				"&sellOrderDTO.paymentState="+inputArr[3];
			}
			queryForm.submit();
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
														<s:select id ="orderType" name="sellOrderQueryDTO.orderTypeArray" list="#{'':'------全部------','10000001,10000005,10000011':'销售记名订单','10000002,10000006,10000012':'销售不记名订单','20000001,30000001':'采购记名订单','20000002,30000002':'采购订单','10000003':'充值订单','60000001':'换卡订单','70000001':'赎回订单'}"></s:select>
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
														<s:select id="paymentTerm" name="sellOrderQueryDTO.paymentTerm" list="#{'':'---全部---','1':'充值前','2':'激活前','3':'配送前','4':'信用支付'}" onchange="change();"></s:select>
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
													<s:select name="sellOrderQueryDTO.paymentState" list="#{'':'-全部-','1':'已支付','0':'未支付','2':'已确认'}"></s:select>
													</td>
												</tr>
											</table>
										</td>
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
									</tr>
									<tr height="35">
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
									</tr>
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														订单日期From:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderDateStart"
															id="orderDateStart" required="true"  onfocus="dateClick(this)"
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
														<%-- <s:textfield name="sellOrderQueryDTO.orderDateEnd"
															id="orderDateEnd" required="true"  onfocus="WdatePicker({maxDate:'%y-%M-%d'})"
												cssClass="Wdate"></s:textfield> --%>
														<s:textfield name="sellOrderQueryDTO.orderDateEnd"
															id="orderDateEnd" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														卡号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.cardNo"
															id="cardNO" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr >
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														发票状态:
													</td>
													<td>
														<s:select id="invoiceState" name="sellOrderQueryDTO.invoiceState" 
															list="#{'':'---全部---','1':'已录入','0':'未录入'}" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														发卡渠道:
													</td>
													<td>
														<s:select id="invoiceState" name="sellOrderQueryDTO.channel" 
															list="#{'':'---全部---','console':'控制台发卡','70000023':'IPOS发卡'}" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														网点号(IPOS):
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.branchId"
															id="cardNO" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
															class="noneBtn" onclick="queryOrder();">
															
													</td>
													<td> 
														<input type="button" class="btn" style="width: 100px; height: 20px;font-weight:bolder; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right;" onclick="downloadAll();" value="下载邮寄信息"/>
														</button>
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
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}');">
								<ec:column property="null" alias="ec_choose" title="选择"
										width="5%"  sortable="false"
										viewsAllowed="html">
										<input type="radio" name="ec_choose" value="${map.orderId},
										${map.orderType},${map.modifyTime},${map.paymentState},${map.initActStat},${map.orderState},${map.deliveryMeans}" />
								</ec:column>
								<ec:column property="orderId"  title="订单号" width="5%">
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										
										<s:if test="#attr['map'].downloadflag!=null && #attr['map'].downloadflag==1 ">
											<font color="#32CD32">${map.orderId}</font>
										</s:if>
										<s:else>
											${map.orderId}
										</s:else>
									</a>
								</ec:column>
								<ec:column property="firstEntityName"  title="订单发起者" width="8%" />
								<ec:column property="processEnntityName"  title="订单处理者" width="8%" />
								<ec:column property="productName"  title="产品名称" width="7%" />
								<ec:column property="orderType"  title="订单类型" width="8%" cell="dictInfo" alias="205"/>								
								<ec:column property="cardQuantity"  title="卡张数" width="5%" />
								<ec:column property="totalPrice"  title="订单金额" width="5%" cell="currency" format="###,###,##0.00" />
								<ec:column property="orderDate"  title="订单日期" width="7%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="salManName"  title="销售人" width="6%" />
								<ec:column property="createUser"  title="创建人" width="6%" />
								<ec:column property="paymentTerm"  title="支付节点" width="6%">
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
								<ec:column property="paymentState"  title="支付状态" width="6%" cell="dictInfo" alias="212">
								</ec:column>	
								<ec:column property="initActStat"  title="是否激活" width="6%" cell="dictInfo" alias="213">	
								</ec:column>
								<ec:column property="orderState"  title="订单状态"  width="6%"  cell="dictInfo" alias="209">
								</ec:column>
								<ec:column property="invoiceState"  title="发票状态" />
							</ec:row>
						</ec:table>
						
						 		
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
		                     <tr>
		                        <td align="right">
		                            <table border="0" cellpadding="0" cellspacing="0">
		                            	<tr>
		                            		<td><font color="red">(*订单号标绿的表示被下载过邮寄信息。)</font></td>
		                            	    <td>
		                            			<display:security urlId="4100041">
		                                            <input type="button" class="btn" 
		                                            	style="width: 150px; height: 20px; margin: 2px 5px; 
		                                            		background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" 
		                                            	onclick="addInvoice('basic');" 
		                                            	value="发票信息录入(基础岗)"/>
	                                         	</display:security>
	                                            
	                                        </td>
		                            	    <td>
		                            	    	<display:security urlId="4100042">
		                                            <input type="button" class="btn"
			                                            style="width: 150px; height: 20px; margin: 2px 5px; 
			                                            	background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
			                                            onclick="addInvoice('management');" 
			                                            value="发票信息录入(管理岗)"/>
		                                        </display:security>
	                                        </td>
	                                        
		                            		<td>
	                                            <input type="button" class="btn" style="width: 120px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="printStock();" value="打印出入库凭证"/>
	                                        </td>
		                            		<td>
	                                            <input type="button" class="btn" style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="printPayment();" value="打印付款明细"/>
	                                        </td>
		                            		<td>
	                                            <input type="button" class="btn" style="width: 80px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="selectOrderId();" value="打印凭证"/>
	                                        </td>
	                                      	<td>
	                                      		<input type="button" class="btn" style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right;" onclick="downloadSingle();" value="下载邮寄信息"/>
														</button>
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