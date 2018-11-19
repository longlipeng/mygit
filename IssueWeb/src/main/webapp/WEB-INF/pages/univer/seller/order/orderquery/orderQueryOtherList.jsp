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
				document.queryForm.action = "orderQueryAction!queryOther";
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
								action="orderQueryAction!queryOther.action">
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
														卡号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.cardNo"
															id="orderId" required="true"></s:textfield>
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
														证件类型：
													</td>
													<td>
														<s:select id="paymentTerm" name="sellOrderQueryDTO.idType" list="#{'':'---全部---','1':'身份证','2':'护照','3':'其他'}" ></s:select>
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														证件号:
													</td>
													<td>
													<s:textfield name="sellOrderQueryDTO.idNo"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														手机号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.phoneNum"
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
														持卡人姓名:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.entityName"
															id="cardNO" required="true"></s:textfield>
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
													<s:textfield name="sellOrderQueryDTO.channel"
															id="cardNO" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														门店号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.branchId"
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
													
												 </tr>
											</table>
										</td>
									</tr>
								</table>
						</s:form>
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
						<s:form id="queryOtherForm" name="queryOtherForm" action="orderQueryAction!queryOther.action" method="post">
			                <s:hidden name="sellOrderQueryDTO.orderId"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.orderState"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.cardNo"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.idType"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.idNo"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.phoneNum"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.entityName"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.channel"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.branchId"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.orderDateStart"></s:hidden>
			                <s:hidden name="sellOrderQueryDTO.orderDateEnd"></s:hidden>
						<ec:table items="sellOrders" var="map" width="100%" form="queryOtherForm"
							action="${ctx}/orderQueryAction!queryOther.action"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="sellOrder" autoIncludeParameters="false">
							<ec:exportXls fileName="IposOrderList.xls" tooltip="导出Excel" />
							
							<ec:row>
								
								<ec:column property="orderId"  title="订单号" width="5%">
								</ec:column>
								<ec:column property="entityName"  title="持卡人姓名" width="7%" />
								<ec:column property="idType"  title="持卡人证件类型" width="7%" />
								<ec:column property="idNo" escapeAutoFormat="true"  title="持卡人证件号" width="7%" />
								<ec:column property="orderType"  title="订单类型" width="8%" cell="dictInfo" alias="205"/>								
								<ec:column property="cardNo" escapeAutoFormat="true"  title="卡号" width="5%" />
								<ec:column property="phoneNum"  title="手机号" width="5%" />
								<ec:column property="orderDate" escapeAutoFormat="true" title="订单创建日期" width="9%"  cell="date" format="yyyy-MM-dd HH:mm:ss"
									/>
								<ec:column property="channel" escapeAutoFormat="true"  title="渠道号" width="6%" />
								<ec:column property="branchId" escapeAutoFormat="true"   title="门店号" width="6%" />
								<ec:column property="orderState"  title="订单状态"  width="6%"  cell="dictInfo" alias="209">
								</ec:column>
								
							</ec:row>
						</ec:table>
						
						 	</s:form>	
						
						</div>
					</td>
				</tr>
			</table>
			
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