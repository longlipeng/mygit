<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">
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
	var chooseBank = document.getElementById("cusIntoBankNames").value;
		<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
			if(chooseBank==${map.bankId}){
				document.getElementById("cusIntoBankAccount").value="${map.bankAccount}";
				document.getElementById("cusIntoAccountName").value="${map.bankAccountName}";
			}			
	     </s:iterator>
}

function init(){
	onCusBankChange();
	onCusIntoBankChange();
};
</script>
</head>
<body onload="init();">
		<div class="TitleHref">
			<span>订单管理-->充值订单信息</span>
		</div>
		<div id="orderInfo" style="width: 100%" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<div id="orderInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
											订单日期：
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
											<span style="color: red;">*</span>客户名称：
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
											订单处理者：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
									
												<s:label name="sellOrderDTO.processEntityId"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>充值账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.serviceName"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
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
											支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select list="#{3:'充值前',4:'信用支付'}"  name="sellOrderDTO.paymentTerm" disabled="true"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.paymentDelay"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>预计充值日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.forecastCreditDate"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											充值有效期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.validityPeriod"/>
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
											<td align="right" width="15%" >
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
												<s:label name="sellOrderDTO.saleManName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											总金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.totalPrice"/>
										</td>
										
										
										<td width="15%" align="right" nowrap="nowrap">
											总张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
										
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5"></s:textarea>
											<s:fielderror>
												<s:param>
													sellOrderDTO.memo
												</s:param>
											</s:fielderror>
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
											<s:label name="sellOrderDTO.createTime"/>
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
											<s:hidden name="sellOrderDTO.orderSource"/>
											<s:if test="sellOrderDTO.orderSource == 1">
												系统录入
											</s:if>
										</td>
									</tr>
									
								</table>
							
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div style="clear: both"></div>
		<div id="orderPayInfo" style="width: 100%" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td colspan="4" width="100%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
						<div id="orderPayInfoTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
										<s:textfield name="sellOrderDTO.payDetails" size="20" readonly="true"  cssClass="lg_text_gray"/>
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
		<div id="intoBankInfo" style="width: 100%" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
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
									<td width="15%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.intoBankId" id="cusIntoBankNames"
												list="sellOrderDTO.lstBankDTO" listKey="bankId"
												listValue="bankName" onchange="onCusIntoBankChange()"></s:select>
									</td>
											 
									<td align="right" width="15%" nowrap="nowrap">
											账户名称:
									</td>
									<td width="15%" align="left" nowrap="nowrap">
											<s:textfield name="cusIntoAccountName" id="cusIntoAccountName"
												size="20" readonly="true" cssClass="lg_text_gray" />
									</td>
									<td align="right" width="15%" nowrap="nowrap">
											银行账号:
									</td>
									<td width="15%" align="left" nowrap="nowrap">
											<s:textfield name="cusIntoBankAccount" id="cusIntoBankAccount"
												size="20" readonly="true" cssClass="lg_text_gray" />
									</td>
								</tr>
							</table>
						</div>
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
						<div id="orderDetailTitle" style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
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
						</div>
						<div id="orderDetailTable">
							<s:form id="EditListForm" name="EditListForm" action="creditOrderAction!edit"
								method="post">
								<s:hidden name="sellOrderDTO.orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId"/>
								<s:hidden name="actionName"/>
								<s:hidden name= "message"/>
								<s:hidden name="actionMethodName"/>
								<s:hidden name="operation"/>
										
								<ec:table items="orderCardList" var="map" width="100%" form="EditListForm"
										action="${ctx}/${actionName}!${actionMethodName}"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCard">
										<ec:row>
											<ec:column property="null" alias="orderCardIdStr" title="选择"
												width="10%" sortable="false" headerCell="selectAll"
												viewsAllowed="html">
												<input type="checkbox" name="orderCardIdStr"
													value="${map.orderCardId}" />
											</ec:column>
											<ec:column property="cardholderName" title="持卡人姓名" width="15%" sortable="false"/>
											<ec:column property="externalId" title="持卡人工号" width="15%" sortable="false"/>
											<ec:column property="cardNo" title="卡号" width="15%" sortable="false"/>
											<ec:column property="creditAmount" title="充值金额(元)" width="15%" sortable="false"/>
										</ec:row>
								</ec:table>
							</s:form>
						</div>
						<!-- div id=Table    end -->
					</td>
				</tr>
			</table>
		</div>
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
		<%@ include file="orderFlowList.jsp"%>
	</body>
	</html>