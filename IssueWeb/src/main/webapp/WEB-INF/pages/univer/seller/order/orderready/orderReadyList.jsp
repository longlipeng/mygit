<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单准备</title>
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
				var flag=true;
				var orderId;
				var inputStr;
				var inputArr;
				var orderType;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						inputStr = document.getElementsByName("ec_choose").item(i).value;
						inputArr = inputStr.split(",");
						orderId = inputArr[0];
						EditForm.orderId.value = orderId;
						EditForm.orderType.value = inputArr[2];
						EditForm.onymousStat.value=inputArr[7];
						flag=false;
					}
				}
				if(flag){
				        errorDisplay("请选择一条记录操作!");
						
				}else{
					    document.EditForm.action="orderReadyAction!"+method;
						document.EditForm.submit();
				}
			}

			var globalProductId = null;
			var globalOrderType = null;
			var globalCardLayoutId = null;
			var globalFaceValueType = null;
			var globalFaceValue = null;
			var globalServiceId=null;
			function addBuyOrder() {
				var selectCount=0;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
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
				var serviceId;
				var onymousStat;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						inputStr = document.getElementsByName("ec_choose").item(i).value;
						inputArr = inputStr.split(",");
						orderId = inputArr[0];
						productId = inputArr[1];
						orderType = inputArr[2];
						cardLayoutId = inputArr[3];
						faceValueType = inputArr[4];
						faceValue = inputArr[5];
						serviceId=inputArr[6];
						onymousStat=inputArr[7];
						if(count==0){
							globalProductId = inputArr[1];
							globalOrderType = inputArr[2];
							globalCardLayoutId = inputArr[3];
							globalFaceValueType = inputArr[4];
							globalFaceValue = inputArr[5];
							globalServiceId = inputArr[6];
							globalonyMousStat=inputArr[7];
							queryForm.productId.value = inputArr[1];
							queryForm.orderType.value = inputArr[2];
							queryForm.cardLayoutId.value = inputArr[3];
							queryForm.faceValueType.value = inputArr[4];
							queryForm.faceValue.value = inputArr[5];
						}else{
							if(productId != globalProductId){
								errorDisplay("所选订单产品类型必须相同！");
								return;
							}
							if(serviceId != globalServiceId){
								errorDisplay("所选订单产品的充值账户必须相同！");
								return;
							}
							if(orderType != globalOrderType){
								switch (orderType+globalOrderType) {
					            	case '1000000120000001' : execBuyOrder();break;
					            	case '2000000110000001' : execBuyOrder();break;
					            	case '1000000220000002' : execBuyOrder();break;						 
					            	case '2000000210000002' : execBuyOrder();break;
				            	}
								errorDisplay("所选订单类型必须相同！");
								return;
							}
							//记名
							if(orderType=='10000001'||orderType=='20000001'){
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
						if(orderType=='10000001'||orderType=='10000011'||orderType=='20000001'||orderType=='10000005'){
							
								if(onymousStat=='3'){
									errorDisplay("产品为库存卡销售订单不能生成采购单！");
									return;
								}
						}
						if(orderType=='10000002' || orderType == '10000012' || orderType=='20000002'||orderType=='10000006'){
							errorDisplay("不记名订单不能生成采购单!");
							return;
						}
						if(orderType=='60000001'){
							errorDisplay("换卡订单不能生成采购单!");
							return;
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
				document.queryForm.action="orderReadyAction!addBuyOrder";
				document.queryForm.submit();
			}
			
			 function viewOrder(orderId,orderType){
	    			window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	  }
				
			
			function ready(orderId,orderType){
				 EditForm.orderId.value = orderId;
				 EditForm.orderType.value = orderType;
				 document.EditForm.action = "orderReadyAction!ready";
				 document.EditForm.submit();
			}
			function readySign(orderId,orderType){
				EditForm.orderId.value = orderId;
				 EditForm.orderType.value = orderType;
				 document.EditForm.action = "orderReadyAction!readySign";
				 document.EditForm.submit();
			}
			function readyChangeCard(orderId,orderType){
				EditForm.orderId.value = orderId;
				EditForm.orderType.value = orderType;
				document.EditForm.action = "orderReadyAction!readyChangeCard";
				document.EditForm.submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单准备</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm"
								action="orderReadyAction!list">
			<s:hidden name="sellBuyOrderDTO.productId"  id="productId"/>
			<s:hidden name="sellBuyOrderDTO.orderType" id="orderType"/>
			<s:hidden name="sellBuyOrderDTO.cardLayoutId" id="cardLayoutId" />
			<s:hidden name="sellBuyOrderDTO.faceValueType" id="faceValueType" />
			<s:hidden name="sellBuyOrderDTO.faceValue" id="faceValue" />
			<s:hidden name="sellBuyOrderDTO.onymousStat" id="onymousStat" />
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
											<span>订单发起者编号：</span>
										</td>
										<td width="160">
											<s:textfield name="sellOrderQueryDTO.entityId"
												id="customerId" size="20" />
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
							action="${ctx}/orderReadyAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="CustomerOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}','${map.orderType}');">
								<ec:column property="null" alias="ec_choose" title="选择"
									width="5%"  sortable="false"
									viewsAllowed="html">
									<input type="radio" name="ec_choose" value="${map.orderId},${map.productId},${map.orderType},${map.cardLayoutId},${map.faceValueType},${map.faceValue},${map.serviceId},${map.onymousStat}" />
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId"  title="订单号" width="6%">
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="entityId"  title="订单发起编号" width="10%" />
								<ec:column property="firstEntityName"  title="订单发起者" width="10%" />
								<ec:column property="processEnntityName"  title="订单处理者" width="10%" />
								<ec:column property="productName"  title="产品名称" width="10%" />
								<ec:column property="orderType"  title="订单类型" width="10%" cell="dictInfo" alias="205"/>								
								<ec:column property="cardQuantity"  title="卡张数" width="5%" />
								<ec:column property="orderDate"  title="订单日期" width="10%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUser"  title="创建人" width="10%" />
								<ec:column property="orderState"  title="订单状态"  width="10%"  cell="dictInfo" alias="209">
								</ec:column>
								
								<ec:column property="null"  title="操作"   viewsAllowed="html">
										<s:if test="#attr.map.orderType==10000002">
                                       	 <a href="javascript:ready('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==10000012">
                                       	 <a href="javascript:ready('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==20000002">
                                       	 <a href="javascript:ready('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==10000001">
                                        <s:if test="#attr.map.onymousStat!=1">
                                       	 <a href="javascript:readySign('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==10000011">
                                        <s:if test="#attr.map.onymousStat!=1">
                                       	 <a href="javascript:readySign('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==20000001">
                                        <s:if test="#attr.map.onymousStat!=1">
                                       	 <a href="javascript:readySign('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        </s:if>
                                         <s:if test="#attr.map.orderType==10000005">
                                        <s:if test="#attr.map.onymousStat!=1">
                                       	 <a href="javascript:readySign('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        </s:if>
                                         <s:if test="#attr.map.orderType==10000006">
                                       	 <a href="javascript:ready('${map.orderId}','${map.orderType}');"> 准备 </a>
                                        </s:if>
                                        <s:if test="#attr.map.orderType==60000001">
                                       	 <a href="javascript:readyChangeCard('${map.orderId}','${map.orderType}');"> 准备 </a>
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
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm"
								action="orderReadyAction!button">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
								<s:hidden name="sellOrderDTO.orderType" value="" id="orderType"/>
								<s:hidden name="sellOrderDTO.onymousStat" id="onymousStat"></s:hidden>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="410052">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="return submitForm('sendBackConfirm');">
													退回
												</div>
											</display:security>
											<display:security urlId="410051">
												<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="return submitForm('submitConfirm');">
														提交
												</div>
											</display:security>
											<display:security urlId="410053">
												<div id="addBtn" class="btn"
												style="width: 110px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addBuyOrder();">
														生成采购订单
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