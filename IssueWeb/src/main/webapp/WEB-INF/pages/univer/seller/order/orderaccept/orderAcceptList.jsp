<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单接收</title>
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
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBodyAccept');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBodyAccept');
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
						flag=false;
					}
				}
				if(flag){
				errorDisplay("请选择一条记录操作!");
						
				}else{
					document.EditForm.action="orderAcceptAction!"+method;
						document.EditForm.submit();
				}
			}

			function viewOrder(orderId,orderType){
	    		window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	}
			
			function ready(orderId,orderType){
				 EditForm.orderId.value = orderId;
				 EditForm.orderType.value = orderType;
				 document.EditForm.action = "orderAcceptAction!ready";
				 document.EditForm.submit();
			}
			function accept(val){
			var orderId=val.split(',')[0];
            var orderType=val.split(',')[1];	
			   EditForm.orderId.value = orderId;
			   EditForm.orderType.value = orderType;
			   document.EditForm.action = "orderAcceptAction!submitConfirm";
		       document.EditForm.submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单接收</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm"
								action="orderAcceptAction!list">

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
											<span>订单处理者：</span>
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
											<span>创建人：</span>
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
							action="${ctx}/orderAcceptAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="CustomerOrderList.xls" tooltip="导出Excel" />
							<ec:row ondblclick="javaScript:viewOrder(${map.orderId},${map.orderType});">
								<ec:column property="null" alias="ec_choose" title="选择" 
									width="5%" sortable="fasle" 
									viewsAllowed="html">
									<input type="radio" name="ec_choose" value="${map.orderId},${map.productId},${map.orderType},${map.cardLayoutId},${map.faceValueType},${map.faceValue}" />
									<input type="hidden" name="ec_chooseOrderType"
										value="${map.orderType}">
								</ec:column>
								<ec:column property="orderId" title="订单号" width="6%" >
									<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="firstEntityName" title="订单发起者" width="10%" />
								<ec:column property="processEnntityName" title="订单处理者" width="10%" />
								<ec:column property="productName" title="产品名称" width="10%"  />
								<ec:column property="orderType" title="订单类型" width="10%" cell="dictInfo" alias="205" />								
								<ec:column property="cardQuantity" title="卡张数" width="5%" />
								<ec:column property="realCardQuantity" title="配送张数" width="7%" >
								<s:if test="#attr.map.realCardQuantity==''||#attr.map.realCardQuantity==null">
								0
								</s:if>
								</ec:column>
								<ec:column property="origCardQuantity" title="接收张数" width="7%" >
								<s:if test="#attr.map.origCardQuantity==''||#attr.map.origCardQuantity==null">
								0
								</s:if>
								</ec:column>
								<ec:column property="orderDate" title="订单日期" width="10%"  
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="createUser" title="创建人" width="8%" />
								<ec:column property="orderState" title="订单状态"  width="8%"  cell="dictInfo" alias="209" >
								</ec:column>
								<ec:column property="null"  title="操作" width="5%"  sortable="fasle" >
										<s:if test="#attr.map.orderType==30000002">
                                       	 <a style="color:blue" href="javascript:accept('${map.orderId}'+','+'${map.orderType}');"> 接收</a>
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
								action="orderAcceptAction!button">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
								<s:hidden name="sellOrderDTO.orderType" value="" id="orderType"/>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="410131">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="return submitForm('sendBackConfirm');">
													退回
												</div>
											</display:security>
											<%-- <display:security urlId="410132">
												<div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="return submitForm('submitConfirm');">
														提交
												</div>
											</display:security> --%>
											
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