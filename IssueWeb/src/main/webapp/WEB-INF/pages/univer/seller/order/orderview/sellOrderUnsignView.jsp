<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			 
		function load(){
			resetContactTxt();
			onCusBankChange();
			onCusIntoBankChange();
        	onContactChange();
		}

		function resetContactTxt(){
			if(${sellOrderDTO.orderType}=='10000011' || ${sellOrderDTO.orderType}=='10000012'){
	        	document.getElementById("div1").style.display='none';
	        	document.getElementById("corpCredType").value="个人证件类型:";
	        	document.getElementById("corpCredId").value="个人证件号:";
	        	document.getElementById("corpCredStaValidity").value="个人证件有效期:(起)";
	        	document.getElementById("contactNameText").value="代办人名称:";
	        	document.getElementById("contactMobilePhoneText").value="代办人联系电话:";
	        	document.getElementById("operCredTypeText").value="代办人证件类型:";
	        	document.getElementById("papersNoText").value="代办人证件号码:";
	        	document.getElementById("starValidityText").value="代办人证件有效期:(起)";
	        }else{
	        	document.getElementById("div2").style.display='none';
	        }
		}
		
		function onContactChange(){
			var chooseBank = document.getElementById("contactName").value;
			<s:iterator value="customerDTO.contractList" var="map">
				if(chooseBank==${map.contactId}){
					document.getElementById("contactMobilePhone").value="${map.contactMobilePhone}";
					
					var selectPapersType=document.getElementById("papersType");
					for(var i=0;i<selectPapersType.options.length;i++){
			        	if(selectPapersType.options[i].value=='${map.papersType}'){
			        		selectPapersType.options[i].selected=true;
			        		break;
			        	}
			        }
			        document.getElementById("papersNo").value="${map.papersNo}";
			        document.getElementById("starValidity").value="${map.starValidity}";
			        document.getElementById("endValidity").value="${map.endValidity}";
			        
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

		function onCusIntoBankChange() {
				<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
					if(${sellOrderDTO.intoBankId}==${map.bankId}){
						document.getElementById("IntoBankAccount").value="${map.bankAccount}";
						document.getElementById("IntoAccountName").value="${map.bankAccountName}";
						//$("#IntoBankAccount").val("${map.bankAccount}");
						//$("#IntoAccountName").val("${map.bankAccountName}");
					}			
			     </s:iterator>
		}
		</script>
	</head>
	<body onload="load();">		
		<s:form id="newForm" name="newForm"
			action="" method="post">
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name="errorjsp" />
			<s:hidden name="sellOrderDTO.perFlag" id="perFlag"></s:hidden> 
			<div id="orderInfo" width="100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<div id="orderInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td class="TableTitleFront" style="cursor: pointer;">
											<span class="TableTop">订单信息</span>
										</td>
										<td class="TableTitleEnd">
											&nbsp;
										</td>
									</tr>
								</table>
							</div>
							<div id="orderInfoTable">
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
												<!--<script type="text/javascript">
												var value;
												function deliveryMeansSelect(select) {
													if (select.value == 2) {
														document.getElementById("deliveryMeansTr").style.display="none";
														value = document.getElementById("sellOrderDTO.deliveryFee").value;
														document.getElementById("sellOrderDTO.deliveryFee").value = "0";
														
														document.getElementById("sellOrderDTO.deliveryFee").className = "lg_text_gray";
													} else {
														document.getElementById("sellOrderDTO.deliveryFee").value = "${sellOrderDTO.deliveryFee}";
														document.getElementById("deliveryMeansTr").style.display="";
														document.getElementById("sellOrderDTO.deliveryFee").className="";
													}
												}
											</script>
											<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<dl:dictList displayName="sellOrderDTO.deliveryMeans"  
												dictType="202" dictValue="${sellOrderDTO.deliveryMeans}"  
												tagType="1"  props="id='sellOrderDTO.deliveryMeans' onchange=deliveryMeansSelect(this)"/>
											</span> -->
										</td>
										
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<s:label name="sellOrderDTO.deliveryFee"/>
											<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10" readOnly="true" cssClass="lg_text_gray"/>
											-->
											<s:label name="sellOrderDTO.deliveryFee"/>
										</td>
										
										
									</tr>

									<tr id="deliveryMeansTr">
										<td width="15%" align="right" nowrap="nowrap">
											收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<s:select name="sellOrderDTO.deliveryPoint" 
												id="deliveryPoint" list="customerDTO.deliveryPointList"
												listKey="deliveryId" listValue="deliveryAddress" onchange="setOrderContact();">
											</s:select>
											</span>  -->
											<s:label name="sellOrderDTO.deliveryPoint"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											收货人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<div id="orderContact">
											</div>
											</span>  -->
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
											<dl:dictList displayName="sellOrderDTO.paymentState"
												dictType="212" dictValue="${sellOrderDTO.paymentState}"
												tagType="1" />
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
			</s:form>
		</div>
		<div id="orderPayInfo" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="100%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<div id="orderPayInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td class="TableTitleFront" 
											style="cursor: pointer;">
											<span class="TableTop">订单付款信息</span>
										</td>
										<td class="TableTitleEnd">
											&nbsp;
										</td>
									</tr>
								</table>
							</div>
							<div id="orderPayInfoTable">
								<table width="100%"> 
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付渠道:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<dl:dictList dictType="901" tagType="2" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
											<s:fielderror>
												<s:param>
													
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											支付明细:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.payDetails" size="20" />
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
											<s:select name="sellOrderDTO.fromBankId" id="cusBankNames"
													list="customerDTO.bankList" listKey="bankId"
													listValue="bankName" onchange="onCusBankChange()"></s:select>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											账户名称:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="cusAccountName" id="cusAccountName"
												size="20" readonly="true" cssClass="lg_text_gray" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											银行账号:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="cusBankAccount" id="cusBankAccount"
												size="20" readonly="true" cssClass="lg_text_gray" />
										</td>
									</tr>
									<tr id="div1">
										<td align="right" width="15%" nowrap="nowrap">
											法人信息:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.corpName" size="20"  readonly="true" cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													customerDTO.corpName
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											法人联系电话:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.corpPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													customerDTO.corpPhone
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									
									<tr id="div2">
										<td align="right" width="15%" nowrap="nowrap">
											个人信息:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerName" size="20"  readonly="true" cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													customerDTO.corpName
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											个人联系电话:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerTelephone" size="20" readonly="true" cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													customerDTO.corpPhone
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="corpCredType" size='11' readonly="true" cssClass="phone" value="法人证件类型:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<edl:entityDictList displayName="customerDTO.corpCredType"
																dictValue="${customerDTO.corpCredType}" dictType="140"
																tagType="1" defaultOption="false" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="corpCredId" size='11' readonly="true" cssClass="phone" value="法人证件号:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.corpCredId" size="20"  readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="corpCredStaValidity" size='18' readonly="true" cssClass="phone" value="法人证件有效期:(起)"></s:textfield>
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
											<s:textfield name="contactNameText" size='11' readonly="true" cssClass="phone" value="经办人名称:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.contactId" id="contactName" list="customerDTO.contractList" listKey="contactId" listValue="contactName" onchange="onContactChange()"></s:select>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="contactMobilePhoneText" size='13' readonly="true" cssClass="phone" value="经办人联系电话:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield id="contactMobilePhone" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="operCredTypeText" size='13' readonly="true"  cssClass="phone" value="经办人证件类型:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<edl:entityDictList displayName="operCredType"
																dictValue="1"
															    dictType="140"
																tagType="2" defaultOption="false" props="id='papersType' disabled='true'"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="papersNoText" size='13' readonly="true" cssClass="phone" value="经办人证件号码:"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="papersNo" id="papersNo" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<s:textfield name="starValidityText" size='20' readonly="true" cssClass="phone" value="经办人证件有效期:(起)"></s:textfield>
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="starValidity" id="starValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											(至):
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="EndValidity" id="endValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
		</div>
		<div id="intoBankInfo" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="100%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<div id="intoBankInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
							</div>
							<div id="intoBankInfoTable">
								<table width="100%"> 
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											开户银行:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.intoBankId" id="C"
												list="sellOrderDTO.lstBankDTO" listKey="bankId"
												listValue="bankName" onchange="onCusIntoBankChange()"></s:select>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											账户名称:
										</td>
										<td width="15%" align="left" nowrap="nowrap">
											<s:textfield id="IntoAccountName"
												 size="20" readonly="true" cssClass="lg_text_gray" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											银行账号:
										</td>
										<td width="15%" align="left" nowrap="nowrap">
											<s:textfield id="IntoBankAccount"
												  size="20" readonly="true" cssClass="lg_text_gray" />
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
		</div>
		
		<div id="orderDetail" style="width: 100%" align=center>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<div id="orderDetailTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
							</div>
							
							<div id="orderDetailTable">
								<s:form id="EditForm" name="EditForm" action="giftSaleOrderAction!edit"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
					
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
									<ec:table items="orderlistList" var="map" width="100%" form="EditForm"
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
						</td>
					</tr>
				</table>
		</div>
		<!-- div id=TableBody -->
<c:if test="${orderCard_totalRows>0}">
		<div style="width: 100%" align=center>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<div id="cardInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
							</div>
							<div id="cardInfoTable">
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
										<ec:exportXls fileName="SellOrderList.xls" tooltip="导出Excel" />
										<ec:row>
											<ec:column property="null" alias="orderListStr" title="选择"
												width="10%" sortable="false" headerCell="selectAll"
												viewsAllowed="html">
												<input type="checkbox" name="orderListStr"
													value="${map.orderCardId}" />
											</ec:column>
												<ec:column property="cardNo" title="卡号"  escapeAutoFormat="true" width="15%" sortable="false"/>
											<ec:column property="faceValue" title="面额" width="15%" sortable="false"/>
											<ec:column property="faceValueType" title="面额类型" width="15%" sortable="false"/>
										</ec:row>
									</ec:table>
								</s:form>
							</div>
						</td>
					</tr>
				</table>
		</div>
</c:if>
	<!-- 支付节点信息     开始 -->
		<div id="paymentInfo" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<div id="paymentInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
								<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
									<tr>
										<td class="TableTitleFront" onclick="displayTableBody();"
											style="cursor: pointer;">
												<span class="TableTop">支付节点信息</span>
										</td>
										<td class="TableTitleEnd">
											&nbsp;
										</td>
									</tr>
								</table>
							</div>
						
							<div id="paymentInfoTable" style="width: 100%">
								<s:form id="paymentForm" name="paymentForm" action="orderPaymentAction!submitConfirm"
								method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name= "operation"/>
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
											<ec:column property="paymentId" title="支付节点编号" width="20%" sortable="false"/>
											<ec:column property="paymentType" title="支付渠道" width="10%" sortable="false">
												<dl:dictList dictType="901" tagType="1" dictValue="${map.paymentType}"></dl:dictList>
											</ec:column>
											<ec:column property="paymentAmount" title="支付金额" width="15%" sortable="false"/>
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
													<b>总计:${sellOrderDTO.paymentAmount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
												</div>
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
		</div>
			
		<!-- 支付节点信息     结束 -->
		<!-- div id=TableBody -->
		<%@ include file="orderFlowList.jsp"%>
			
	</body>
</html>