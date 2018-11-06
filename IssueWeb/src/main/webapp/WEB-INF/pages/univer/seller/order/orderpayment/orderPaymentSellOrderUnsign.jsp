<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<script type="text/javascript">
		function onBankChange() {
		var chooseBank = document.getElementById("bankNames").value;
			<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
				if(chooseBank==${map.bankId}){
					document.getElementById("bankAccount").value="${map.bankAccount}";
					document.getElementById("accountName").value="${map.bankAccountName}";
				}			
		     </s:iterator>
	}
	function onCusBankChange() {
		var chooseBank = document.getElementById("cusBankNames").value;
			<s:iterator value="customerDTO.bankList" var="map">
				if(chooseBank==${map.bankId}){
					document.getElementById("cusBankAccount").value="${map.bankAccount}";
					document.getElementById("cusAccountName").value="${map.bankAccountName}";
				}			
		     </s:iterator>
	}
	function addPayment(orderId){
	
		if(document.getElementsByName("orderPaymentListStr").length >= 1){
			alert("只能有一种支付渠道，请先删除再添加");
			return;
		}
		var returnValue = window.showModalDialog('${ctx}/${actionName}!addOrderPayment?sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}&sellOrderDTO.orderType=${sellOrderDTO.orderType}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		if(returnValue==1){
			if("${sellOrderDTO.paymentState}"== "0"){
				confirmForm.action = "orderPaymentAction!pay";
			}
			if("${sellOrderDTO.paymentState}"== "2"){
				confirmForm.action = "orderPaymentAction!submitConfirm";
			}			 
		  	confirmForm.submit();
		  }	  
	}
	function deletePayment(){
		var flag = true;
		var orderPaymentListStr="";
			for(i = 0; i < document.getElementsByName("orderPaymentListStr").length; i++) {
	                  if (document.getElementsByName("orderPaymentListStr").item(i).checked) {
	                              flag =false;
	                              orderPaymentListStr = orderPaymentListStr+","+document.getElementsByName("orderPaymentListStr").item(i).value;
	                             // break;
	                      }
	                  }  
	                  orderPaymentListStr = orderPaymentListStr.substr(1,orderPaymentListStr.length);
	                  document.getElementsByName("orderPaymentDTO.paymentId").value = orderPaymentListStr;
	              if(flag){
	                  errorDisplay("请选择一条记录操作");
	                  return;
	              }
	              confirm("确定要删除吗?",delOpr);
	}
	function delOpr(){
			confirmForm.action = "orderPaymentAction!deletePay?orderPaymentDTO.orderId=${sellOrderDTO.orderId}&orderPaymentDTO.paymentId="+document.getElementsByName("orderPaymentDTO.paymentId").value;
		  	confirmForm.submit();			
		}
	function submitCheck(){
		var paymentAmount="${sellOrderDTO.paymentAmount}";
		var totalPrice = "${sellOrderDTO.totalPrice}";
		if(paymentAmount ==null || paymentAmount == ""){
		    alert("付款信息不能为空,不能提交订单！");
		    return;
		    //paymentAmount = 0;
		}
		if(parseFloat(paymentAmount)< parseFloat(totalPrice)){
			alert("付款总额小于订单总金额,不能提交订单！");
		    return;
		}else if (parseFloat(paymentAmount) > parseFloat(totalPrice)){
			alert("付款总额大于订单总金额,不能提交订单！");
		    return;	
		}else if(parseFloat(paymentAmount) == parseFloat(totalPrice)) {
			submitForm();
		}
	}
	</script>
</head>
<body onload="onBankChange();onCusBankChange()">
<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody('orderTable')" style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="orderTable">
								<table width="100%">
									
											
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.orderId"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.orderDate"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.firstEntityId"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
												客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:label name="sellOrderDTO.firstEntityName"/>
										
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productName"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.processEntityId"/>
										</td>
									</tr>
									
									<tr>
									<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>默认充值账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.serviceName"/>
										</td> 
									
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>订单类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="sellOrderDTO.orderType"
												dictType="205" dictValue="${sellOrderDTO.orderType}"
												tagType="1"  />
										</td>
									
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>包装：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.packageName"/>
										</td>
										<td width="15%" align="right">
											<span style="color: red;">*</span>包装费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.packageFee"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.cardIssueFee"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.annualFee"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>送货方式：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<dl:dictList displayName="sellOrderDTO.deliveryMeans"
												dictType="202" dictValue="${sellOrderDTO.deliveryMeans}"
												tagType="1" />
										</td>
										
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.deliveryFee"/>
										</td>
										
										
									</tr>

									<tr id="deliveryMeansTr">
										<td width="15%" align="right" nowrap="nowrap">
											收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.deliveryPoint"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											收货人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.orderContact"/>
										</td>
									</tr>
									
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="sellOrderDTO.paymentTerm"
												dictType="207" dictValue="${sellOrderDTO.paymentTerm}"
												tagType="1"  />
										
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.paymentDelay"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>附加费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.additionalFee"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>折扣费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.discountFee"/>
										</td>
									</tr>
									<tr>
											<td align="right" width="15%" >
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
											<s:label name="sellOrderDTO.saleManName"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.totalPrice"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" >
											<span style="color: red;">*</span>支付状态：
										</td>
										<td>
											<s:if test="sellOrderDTO.paymentState==1">
												已支付
											</s:if>
											<s:else>
												未支付				
											</s:else>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
										
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											创建人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.createUser" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											创建日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.createTime" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单状态：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<dl:dictList displayName="sellOrderDTO.orderState"
												dictType="209" dictValue="${sellOrderDTO.orderState}"
												tagType="1" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											订单来源：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:if test="sellOrderDTO.orderSource == 1">
												系统录入
											</s:if>
										</td>										
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5" readonly="true"></s:textarea>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="ContainBox" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">订单付款信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
						<div id="serviceTable">
						<table width="100%"> 
						<tr>
						<td align="right" width="15%" nowrap="nowrap">
							支付渠道:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<dl:dictList dictType="901" tagType="2" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							付款明细:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="sellOrderDTO.payDetails" size="20"/>
							<s:fielderror>
								<s:param>
									sellOrderDTO.payDetails
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							开户银行:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.fromBankId" id="cusBankNames" list="customerDTO.bankList" listKey="bankId" listValue="bankName" onchange="onCusBankChange()"></s:select>
							<s:fielderror>
								<s:param>
									sellOrderDTO.fromBankId
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							账户名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusAccountName" id="cusAccountName" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							银行账号:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusBankAccount" id="cusBankAccount" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人信息:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpName" size="20"  readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人联系电话：
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>	
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
						<edl:entityDictList displayName="customerDTO.corpCredType"
												dictValue="${customerDTO.corpCredType}" dictType="140"
												tagType="1" defaultOption="false" />
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件号:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredId" size="20"  readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredStaValidity" size="20"  readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operName" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人联系电话:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="customerDTO.operCredType"
												dictValue="${customerDTO.operCredType}" dictType="140"
												tagType="1" defaultOption="false" />
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件号码:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredId" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredStaValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					</table>
					</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="ContainBox" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">收款信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%"> 
									<tr>
										<td align="right" width="13%" nowrap="nowrap">
											开户银行:
										</td>
										<td width="20%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.intoBankId" id="bankNames" list="sellOrderDTO.lstBankDTO" listKey="bankId" listValue="bankName" onchange="onBankChange()"></s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.intoBankId
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="13%" nowrap="nowrap">
											账户名称:
										</td>
										<td width="20%" align="left" nowrap="nowrap">
											<s:textfield name="accountName" id="accountName" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
										<td align="right" width="13%" nowrap="nowrap">
											银行账号:
										</td>
										<td width="20%" align="left" nowrap="nowrap">
											<s:textfield name="bankAccount" id="bankAccount" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="orderTable">
			<table width="100%" border="0">
				<tr>
					<td align="right" width="100" nowrap="nowrap"
						style="padding: 10px 0 0 0">
						<span>操作说明：</span>
					</td>
					<td width="100%" align="left" nowrap="nowrap" rowspan="2"
						style="padding: 10px 0 0 0">
							<s:hidden name="operation" id="operation"></s:hidden>
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明"
								rows="3"></s:textarea>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/${actionName}!list', '_self')">
									返回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitCheck();">
									提交
							</button>
							<div style="clear: both"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%" align=center>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody('TableBody');"
										style="cursor: pointer;">
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="editForm" name="editForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
								<ec:table items="orderlistList" var="map" width="100%" form="editForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderList">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="cardLayoutName" title="卡面" width="20%" sortable="false"/>
										<ec:column property="faceValue" title="面额值" width="10%" sortable="false"/>
										<ec:column property="validityPeriod" title="有效期" width="15%" sortable="false"/>
										<ec:column property="cardAmount" title="张数" sortable="false"/>
										<ec:column property="realAmount" title="实际张数" sortable="false"/>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
	<c:if test="${orderCard_totalRows>0}">
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
										<span class="TableTop">卡明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="viewForm" name="viewForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>									
								<ec:table items="orderCardList" var="map" width="100%" form="viewForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCard">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderCardId}" />
										</ec:column>
											<ec:column property="cardNo" title="卡号" width="15%" sortable="false"/>
										<ec:column property="faceValue" title="面额" width="15%" sortable="false"/>
										<ec:column property="faceValueType" title="面额类型" width="15%" sortable="false"/>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
</c:if>
<!-- 订单付款信息     开始 -->
							<div id="ContainBox" style="width: 100%" align="center">
								<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
									<tr>
										<td width="98%" height="10" align="center" valign="middle"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
											cellspacing="0">
												<tr>
													<td class="TableTitleFront" onclick="displayTableBody();"
														style="cursor: pointer;">
															<span class="TableTop">付款信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<div id="TableBody" style="width: 98%">
									<s:form id="paymentForm" name="paymentForm" action="orderPaymentAction!submitConfirm"
									method="post">
									
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
										<s:hidden name="orderPaymentDTO.paymentId"/>
										<input type="hidden" name="orderPaymentDTO.orderId" value="${sellOrderDTO.orderId }"/>
										<ec:table items="orderPaymentList" var="map" width="100%" form="paymentForm"
											action="${ctx}/${actionName}!${actionMethodName}"
											imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
											retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderPayment">
											<ec:row>
												<ec:column property="null" alias="orderPaymentListStr" title="选择"
													width="10%" sortable="false" headerCell="selectAll"
													viewsAllowed="html">
													<input type="checkbox" name="orderPaymentListStr"
														value="${map.paymentId}" />
												</ec:column>
												<ec:column property="paymentType" title="支付渠道" width="10%" sortable="false">
													<dl:dictList dictType="901" tagType="1" dictValue="${map.paymentType}"></dl:dictList>
												</ec:column>
												<ec:column property="paymentAmount" title="金额" width="15%" sortable="false"/>
												<ec:column property="remark" title="备注信息" sortable="false"/>
											</ec:row>
										</ec:table>
									</s:form>
									<table width="100%" height="20" border="0" cellpadding="0"
										cellspacing="0" style="border-top: 1px solid silver;">
										<tr>
											<td>
												<div id="buttonCRUD"
													style="text-align: right; width: 100%; height: 30px;"
													align="center" >
													<div id="buttonCRUD" style="text-align: right; width: 100%">
														<div id="deleteBtn" class="btn"
															style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
															onclick="deletePayment();">
															删除
														</div>					
														<div id="addBtn" class="btn"
															style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
															onclick="addPayment(${sellOrderDTO.orderId});">
															添加
														</div>
														<b>总计:${sellOrderDTO.paymentAmount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
													</div>
													<div style="clear: both"></div>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<!-- 订单付款信息     结束 -->
			<!-- div id=TableBody -->
				<%@ include file="../orderview/orderFlowList.jsp"%>
</body>
</html>
			