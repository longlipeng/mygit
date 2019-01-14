<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<base target="_self">
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayQueryBody = false;
			var startCardNo;
			var endCardNo;
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
			
			function submit() {
				if(check()){
					var flag = true;
					for(i = 0; i < document.getElementsByName("cardNoArray").length; i++) {
						if (document.getElementsByName("cardNoArray").item(i).checked) {
							flag=false;
						}
					}
					if (flag) {
						errorDisplay("请选择一条记录操作！");
						return;
					} 
					document.EditForm.action = "creditOrderAction!insertOrderList";
					document.EditForm.submit();
				}
			}


			function check(){
				var priceValue = document.getElementById("priceValue").value;
				var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/;
				if (priceValue == "" || IsSpace(priceValue)) {
					errorDisplay("请输入充值金额！");
					return false;
				}
				if (!doublePatrn.exec(priceValue)) {
					errorDisplay("请输入一个正确的金额！保留两位小数！");
					return false;
				}
				
				if(priceValue>${maxbalance/100}){
					errorDisplay("产品最大金额为${maxbalance/100}");
					return false;
				}
				
				
				 
				if(priceValue>1000000){
					errorDisplay("请输入一个小于1000000的数字");
					return false;
				}
				if(startCardNo==null ||startCardNo==""){
					errorDisplay('没有可以准备的卡');
					return;
				}
				if(endCardNo==null ||endCardNo==""){
					errorDisplay('没有可以准备的卡');
					return;
				}
				if(document.getElementsByName("cardNoArray").length==0){
					errorDisplay('没有可以准备的卡');
					return;
				}	
				return true;	
			 }
			
			function cardSegmentsubmit(){
				if(check()){
					document.EditForm.action="creditOrderAction!insertOrderListByCardSegment";
					document.EditForm.submit();
				}
			}
			function cardNoInit(){
				startCardNo=document.getElementById("startCardNo").value;
				endCardNo=document.getElementById("endCardNo").value;
			}
				function checkey(thekey){
				if((thekey.keyCode>=48&&thekey.keyCode<=57) || thekey.keyCode==8)//通过用户输入键值的ASCII码判断,限制用户只能输入数字而屏蔽其它按键
				return true;
				else
				return false;
				}
			
	</script>
	</head>
	<body onload="cardNoInit()">
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理>添加充值订单明细信息</span>
		</div>
		<div>
		
		<s:form id="EditForm" name="EditForm" action="creditOrderAction!addOrderList"
									method="post">
		<div id="ContainBox">
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
								<s:hidden name="sellOrderDTO.orderId"/>
								<s:hidden name="sellOrderDTO.firstEntityId"/>
								<s:hidden name="sellOrderDTO.processEntityId"/>
								<s:hidden name="sellOrderDTO.productId"/>
								<s:hidden name="maxbalance"/>
					<table width="80%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr height="35">
								<td width="" align="right">
									<span>起始卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderCardListQueryDTO.startCardNo" id="startCardNo" size="24" onkeypress="return checkey(event)"/>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											sellOrderCardListQueryDTO.startCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td width="" align="right">
									<span>结束卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderCardListQueryDTO.endCardNo" id="endCardNo" size="24" onkeypress="return checkey(event)" ></s:textfield>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											sellOrderCardListQueryDTO.endCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td width="" align="right">
									<s:checkbox name="sellOrderCardListQueryDTO.isCurCustomer" id="isCurCustomer"/>
								</td>
								<td width="" align="left">
									非本客户购卡
								</td>
								<td align="right">
									<input type="button" class="bt" style="margin: 5px" onclick="EditForm.submit();" value="查 询" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
						</div>
			<br>
			<br>
			<div style="width: 100%" align=center>
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
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
									<ec:table items="list" var="map" width="100%" form="EditForm"
										action="${ctx}/creditOrderAction!addOrderList"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										<ec:row>
											<ec:column property="null" alias="cardNoArray" title="选择"
												width="10%" sortable="false" headerCell="selectAll"
												viewsAllowed="html">
												<input type="checkbox" name="cardNoArray" value="${map.cardNo}" />
											</ec:column>
											<ec:column property="cardholderId" title="持卡人号" width="15%" >
											<a href="creditOrderAction!checkView?cardholderDTO.cardholderId=${map.cardholderId}&sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}&sellOrderDTO.productId()=${sellOrderDTO.productId}">
											 <s:property value="#attr['map'].cardholderId" />
											 </a>
											 </ec:column>
											<ec:column property="cardholderName" title="持卡人姓名"
												width="15%" >
												</ec:column>
											<ec:column property="externalId" title="持卡人工号" width="15%" />
											<ec:column property="cardNo" title="卡号" width="15%" />
										</ec:row>
									</ec:table>
							</div>
						</td>
					</tr>
				</table>
				<br>
				<script type="text/javascript">
					var isDisplayOrderTable = false;
					function displayOrderTable() {
						if (isDisplayOrderTable) {
							display('orderTable');
							isDisplayOrderTable = false;
						} else {
							undisplay('orderTable');
							isDisplayOrderTable = true;
						}
					}
				</script>

				<div id="order"
					style="border: 1px solid #B9B9B9; margin-top: 5px; width: 98%">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0" bgcolor="B5B8BF">
						<tr>
							<td class="TableTitleFront" onclick="displayOrderTable();"
								style="cursor: pointer;">
								<span class="TableTop">订单明细信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="orderTable"
						style="background-color: #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;width:100%">
						<table width="100%">
							<tr>
								<td width="15%" align="right" nowrap="nowrap">
									充值金额(元)：
								</td>
								<td width="85%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderInputDTO.creditAmount" id="priceValue" ></s:textfield>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			</div>
			</s:form>
			
			<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' style="float: right; margin: 5px"
					onclick="window.close()">
					关 闭
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="this.disabled='disabled';cardSegmentsubmit();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
					全部提交
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="this.disabled='disabled';submit();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
					提 交
				</button>
				<div style="clear: both"></div>
			</div>
	</body>
</html>