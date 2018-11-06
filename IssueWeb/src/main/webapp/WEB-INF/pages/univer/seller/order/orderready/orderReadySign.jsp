<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<div class="TitleHref">
			<span>记名卡订单准备</span>
		</div>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		var isDisplayTableBody =false;
	function displayTableBody(table){
		if (isDisplayTableBody) {
			display(table);
			isDisplayTableBody = false;
		} else {
			undisplay(table);
			isDisplayTableBody = true;
		}
	}
		function setOrderContact(){
		var deliveryContactId=document.getElementById("contact").value;
		var optionInnerHTML="<select name='sellOrderDTO.orderContact' id='sellOrderDTO.orderContact'>";
		var deliveryPoint=document.getElementById("deliveryPoint").value;
		
		<s:iterator value="customerDTO.deliveryPointList" status="status" var="map">
			if(deliveryPoint==${map.deliveryId}){
				  <s:iterator  value="%{#attr.map.recipientList}" status="status1" var="map1">
				  		if(deliveryContactId==${map1.deliveryContactId}){
	    					optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}' selected>${map1.deliveryContact}</option>";
				  		}else {
				  			optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}'>${map1.deliveryContact}</option>";
					  	}
				  </s:iterator>
			}
		</s:iterator>
		optionInnerHTML=optionInnerHTML+"</select>";
		document.getElementById("orderContact").innerHTML=optionInnerHTML;
		}
		function initPage(){
		var select=document.getElementById("sellOrderDTO.deliveryMeans");
		deliveryMeansSelect(select);
		setOrderContact();
		}
		function cardReady(){
			//var returnValue =window.showModelessDialog('${ctx}/orderReadyAction!cardReady?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.productId=${sellOrderDTO.productId}&orderReadyDTO.processEntityId=${sellOrderDTO.processEntityId}&orderReadyDTO.orderType=${sellOrderDTO.orderType}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no');
			var returnValue =window.showModalDialog('${ctx}/orderReadyAction!cardReady?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.productId=${sellOrderDTO.productId}&orderReadyDTO.processEntityId=${sellOrderDTO.processEntityId}&orderReadyDTO.orderType=${sellOrderDTO.orderType}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no');
			if(returnValue==1){			 
				  	document.forms[0].action = "orderReadyAction!readySign";
				  	document.forms[0].submit();
			}
		}
		function deleteAllCard(){
				confirm('确定要删除吗?',operationDel);
				return ;
		}
		function operationDel(){		
				document.newForm.action="${ctx}/orderReadyAction!deleteAllCard";
				document.newForm.submit();
		}
		function onBankChange() {
				var chooseBank = document.getElementById("bankNames").value;
				<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
					if(chooseBank==${map.bankId}){
						document.getElementById("bankAccount").value="${map.bankAccount}";
					}			
		       </s:iterator>
			}
		</script>
	</head>
	<body onload="onBankChange()">	
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
			<s:hidden name="orderReadyDTO.orderId" id="orderId"></s:hidden>
			<s:hidden name="orderReadyDTO.processEntityId" id="processEntityId"></s:hidden>

			<s:hidden name="orderReadyDTO.orderCardListId" id="orderCardListId" />
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name="sellOrderDTO.orderId" />
			<s:hidden name="sellOrderDTO.processEntityId" />
			<s:hidden name="errorjsp" />
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();"
										style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
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
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.orderId" />
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.orderDate" />
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.firstEntityId" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.firstEntityName" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productName" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.processEntityId" />
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡片有效期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.validityPeriod" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡面：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.cardLayoutName" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>充值账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.serviceName" />
										</td>
										<s:hidden name="sellOrderDTO.faceValueType" value="1" />
										<td width="15%" align="right">
											<span style="color: red;">*</span>第一次充值金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.faceValue" />
										</td>
									</tr>


									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>包装：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.packageName" />
										</td>
										<td width="15%" align="right">
											<span style="color: red;">*</span>包装费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.packageFee" />
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.cardIssueFee" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.annualFee" />
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
<!-- 											<script type="text/javascript"> -->

<!-- 											</script> -->
<!-- 											<span onmousemove="this.setCapture();" -->
<!-- 												onmouseout="this.releaseCapture();" onfocus="this.blur();"> -->
<%-- 												<dl:dictList displayName="sellOrderDTO.deliveryMeans" --%>
<%-- 													dictType="202" dictValue="${sellOrderDTO.deliveryMeans}" --%>
<%-- 													tagType="2" --%>
<%-- 													props="id='sellOrderDTO.deliveryMeans' onchange=deliveryMeansSelect(this)" /> --%>
<!-- 											</span> -->
										</td>

										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.deliveryFee"/>
										<!-- 	<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10"
												readOnly="true" cssClass="lg_text_gray" /> -->
										</td>


									</tr>

									<tr id="deliveryMeansTr">

										<td width="15%" align="right" nowrap="nowrap">
											收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">										
											<s:label name="sellOrderDTO.deliveryFee"/>
										<!-- <span onmousemove="this.setCapture();"
												onmouseout="this.releaseCapture();" onfocus="this.blur();">
												<s:select name="sellOrderDTO.deliveryPoint"
													id="deliveryPoint" list="customerDTO.deliveryPointList"
													listKey="deliveryId" listValue="deliveryAddress"
													onchange="setOrderContact();">
												</s:select> </span> -->
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
												tagType="1" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.paymentDelay" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>附加费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.additionalFee" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>折扣费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.discountFee" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%">
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
											<s:label name="sellOrderDTO.saleManName" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.totalPrice" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%">
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
										<td align="right" width="15%" nowrap="nowrap">
											张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.cardQuantity" />
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
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5"
												readonly="true"></s:textarea>
										</td>

									</tr>
									<tr  bgcolor="#FFFFFF">
										<td colspan="4" style="padding: 5px;" align="right">
											<button class='btn'
												onclick="newForm.action='orderReadyAction!list';newForm.submit();">
												返 回
											</button>
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
							<span style="color: red;">*</span>支付渠道:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<dl:dictList dictType="901" tagType="1" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							支付明细:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="sellOrderDTO.payDetails" size="20" readonly="true"  cssClass="lg_text_gray"/>
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
										<td align="right" width="15%" nowrap="nowrap">
											开户银行:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.bankId" id="bankNames" list="sellOrderDTO.lstBankDTO" listKey="bankId" listValue="bankName" onchange="onBankChange()"></s:select>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											银行账号:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="bankAccount" id="bankAccount" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
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
							<s:form id="EditForm" name="EditForm"
								action="giftSaleOrderAction!edit" method="post">
								<s:hidden name="sellOrderDTO.orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId" />
								<s:hidden name="actionName" />
								<s:hidden name="message" />
								<s:hidden name="actionMethodName" />
								<ec:table items="orderCardList" var="map" width="100%"
									form="EditForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false"
									tableId="orderCard">
									<ec:row>
										
										<ec:column property="cardNo" title="卡号" width="15%" sortable="false"/>
										<ec:column property="cardholderName" title="持卡人姓名" width="15%" sortable="false"/>
										<ec:column property="externalId" title="持卡人工号" width="15%" sortable="false"/>
									</ec:row>
								</ec:table>
							</s:form>
						</div>
						<!-- div id=TableBody -->
						<br />
						<div style="width: 100%" align=center>

							<div style="width: 100%" align=center>

								<table width="98%" border="0" cellpadding="0" cellspacing="0"
									bgcolor="B5B8BF">
									<tr>
										<td width="98%" height="10" align="center" valign="middle"
											bgcolor="#FFFFFF">

											<s:form id="EditForm1" name="EditForm1"
												action="sellOrderAction!edit">
												<s:hidden name="sellOrderDTO.orderId" value="" id="orderId" />
											</s:form>

											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td>
														<div id="buttonCRUD"
															style="text-align: right; width: 100%; height: 30px;"
															valign="middle">

															<div id="deleteBtn" class="btn"
																style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
																onclick="deleteAllCard();">
																删除全部
															</div>

															<div id="buttonCRUD"
																style="text-align: right; width: 100%">
																<div id="addBtn" class="btn"
																	style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
																	onclick="cardReady();">
																	准备
																</div>
															</div>
															<div style="clear: both"></div>
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<s:hidden name="sellOrderDTO.orderContact" id="contact" />
	</body>
</html>