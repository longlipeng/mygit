<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
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
	
			
			function onOperChange(){
				var chooseContact = document.getElementById("contactId").value;
					<s:iterator value="customerDTO.contractList" var="map">
						if(chooseContact==${map.contactId}){
							document.getElementById("operPhone").value="${map.contactTelephone}";
							document.getElementById("operCreType").value="${map.papersType}";
							document.getElementById("operCredId").value="${map.papersNo}";
							document.getElementById("operStarValidity").value="${map.starValidity}";
							document.getElementById("operEndValidity").value="${map.endValidity}";
						}			
				     </s:iterator>
			}
		</script>
	</head>
	<body onload="setOrderContact();onBankChange();onCusBankChange();onOperChange()">
		<div class="TitleHref">
			<span>换卡订单信息</span>
		</div>
		
		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="reloadableCardAction!insert" method="post">	
			
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<div id="orderInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
							
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td class="TableTitleFront"  style="cursor: pointer;">
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productName"></s:label>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.processEntityId"/>
											<s:label name="sellOrderDTO.processEntityId"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>订单类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="sellOrderDTO.orderType"
												dictType="205" dictValue="${sellOrderDTO.orderType}"
												tagType="1"  />
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
											<s:label name="sellOrderDTO.orderContact" id="orderContact"/>
										</td>
										
									</tr>
									<tr>
										<td align="right" width="15%" >
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
											<s:label name="sellOrderDTO.saleManName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" >
											<span style="color: red;">*</span>原卡总金额(元)：
										</td>
										<td>
											<s:label name="sellOrderDTO.origCardTotalAmt"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											新卡总金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.newCardTotalAmt"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>服务费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.additionalFee"/>
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
											<s:label name="sellOrderDTO.createUserName" />
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
						<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
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
											<dl:dictList dictType="901" tagType="1" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											支付明细:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.payDetails" size="20" readonly="true"/>
										</td>
										</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											开户银行:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select disabled="true"  name="sellOrderDTO.fromBankId" id="cusBankNames" list="customerDTO.bankList" listKey="bankId" listValue="bankName" onchange="onCusBankChange()"></s:select>
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
										<s:select  disabled="true" list="customerDTO.contractList" name="sellOrderDTO.contactId" onchange="onOperChange()" id="contactId" listKey="contactId" listValue="contactName"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											经(代)办人联系电话:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.operPhone"  id="operPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											经(代)办人证件类型:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<edl:entityDictList props="id='operCreType' disabled='true'" displayName="customerDTO.operCredType"
																dictValue="${customerDTO.operCredType}" dictType="140"
																tagType="2" defaultOption="false" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											经(代)办人证件号码:
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.operCredId" size="20" id="operCredId" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											经(代)办人证件有效期:(起)
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.corpCredStaValidity" id="operStarValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											(至):
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.corpCredEndValidity" id="operEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
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
										<td align="right" width="13%" nowrap="nowrap">
											开户银行:
										</td>
										<td width="20%" align="left" nowrap="nowrap">
											<s:select disabled="true"  name="sellOrderDTO.intoBankId" id="bankNames" list="sellOrderDTO.lstBankDTO" listKey="bankId" listValue="bankName" onchange="onBankChange()"></s:select>
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
		<!-- 订单付款信息     开始 -->
		<div id="paymentInfo" style="width: 100%" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<div id="paymentInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
						</div>
					
						<div id="paymentInfoTable" style="width: 100%">
							<s:form id="paymentForm" name="paymentForm" action="orderPaymentAction!submitConfirm"
							method="post">
								<s:hidden name="sellOrderDTO.orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId"/>
								<input type="hidden"  name="${sellOrderDTO.firstEntityId}"/>
								<input type="hidden" name="orderPaymentDTO.orderId" value="${sellOrderDTO.orderId}"/>
								<input type="hidden"  name="orderPaymentDTO.paymentState" value="${sellOrderDTO.paymentState}"/>
								<input type="hidden"  name="orderPaymentDTO.orderType" value="${sellOrderDTO.orderType}"/>
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
										<ec:column property="paymentType" title="付款渠道" width="10%" sortable="false">
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
											align="center">
											<div id="buttonCRUD" style="text-align: right; width: 100%">									
												<b>总计:<s:label name="sellOrderDTO.paymentAmount"></s:label></b>
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
		<!-- 订单付款信息     结束 -->
		<div id="origCardInfo" style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<div id="origCardInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">原有卡信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div id="origCardInfoTable">
							<s:form id="origCardviewForm" name="origCardviewForm" action=""
									method="post">
								<s:hidden name="sellOrderDTO.orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId"/>
								<s:hidden name="actionName"/>
								<s:hidden name="actionMethodName"/>
								<s:hidden name= "message"/>
								<s:hidden name= "operation"/>									
								<ec:table items="origCardList" var="map" width="100%" form="origCardviewForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="origCardList"
									showPagination="false" showStatusBar="false">
									<ec:row ondblclick="">
										<ec:column property="null" alias="origCardListStr" title="选择"
												width="10%" sortable="false" headerCell="selectAll"
												viewsAllowed="html">
											<input type="checkbox" name="origCardListStr"
												value="${map.origCardListId}" />
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" sortable="false"/>
										<ec:column property="productName" title="产品名称" width="20%" sortable="false"/>
										<ec:column property="amount" title="余额" width="15%" sortable="false"/>
										<ec:column property="firstName" title="持卡人姓名" width="15%" sortable="false"/>
										<ec:column property="validityPeriod" title="有效期" width="15%" sortable="false"/>
										<ec:column property="cardState" title="卡状态" width="20%" sortable="false">
											<s:if test="#attr['map'].cardState== 0">待验收</s:if>
											<s:if test="#attr['map'].cardState== 1">已验收</s:if>
											<s:if test="#attr['map'].cardState== 2">入库</s:if>
											<s:if test="#attr['map'].cardState== 3">销户</s:if>
										</ec:column>
									</ec:row>
								</ec:table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="newCardInfo" style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<div id="newCardInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" 
										style="cursor: pointer;">
										<span class="TableTop">新卡明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							</div>
							<div id="newCardInfoTable">
								<s:form id="orderListForm" name="orderListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>									
								<ec:table items="orderlistList" var="map" width="100%" form="orderListForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderList"
									showPagination="false" showStatusBar="false">
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="productName" title="产品名称" width="15%" sortable="false"/>
										<ec:column property="serviceName" title="充值账户" width="15%" sortable="false"/>
										<ec:column property="cardLayoutName" title="卡面" width="10%" sortable="false"/>
										<ec:column property="faceValueType" title="面额类型"  width="10%" sortable="false"/>
										<ec:column property="faceValue" title="面额值" width="10%" sortable="false"/>
										<ec:column property="validityPeriod" title="有效期" width="13%" sortable="false"/>
										<ec:column property="cardAmount" title="张数" width="7%" sortable="false"/>
									</ec:row>
								</ec:table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<c:if test="${orderCard_totalRows>0}">
			<div id="cardInfo" style="width: 100%" align=center>
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
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCard"
									showPagination="false" showStatusBar="false">
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
				<%@ include file="orderFlowList.jsp"%>
				<s:hidden name="sellOrderDTO.orderContact" id="contact"/>
				<s:hidden name="sellOrderDTO.deliveryPoint" id="deliveryPoint"/>
	</body>	
</html>