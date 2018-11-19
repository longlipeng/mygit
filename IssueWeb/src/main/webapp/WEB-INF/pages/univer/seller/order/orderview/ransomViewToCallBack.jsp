<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>查看赎回订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
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
  
  <body onload="onOperChange()">
  	
	<div class="TitleHref">
		<span>订单管理>赎回订单查看</span>
	</div>
	<s:form id="newForm" name="newForm"
			action="" method="post">
		<s:hidden name="sellOrderDTO.orderType" />
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
									<td align="right" width="15%">
										<span style="color: red;">*</span>销售人员：
									</td>
									<td>
										<s:label name="sellOrderDTO.saleManName"/>
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
									<td align="right" width="15%" nowrap="nowrap">
										<span style="color: red;">*</span>卡余额总计（元）：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:label name="sellOrderDTO.origCardTotalAmt"/>
									</td>
								</tr>
								<tr>
									<td width="15%" align="right" nowrap="nowrap">
										张数：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:label name="sellOrderDTO.cardQuantity"/>
									</td>
									<td width="15%" align="right" nowrap="nowrap">
										<span style="color: red;">*</span>总费用(元)：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:label name="sellOrderDTO.totalPrice"/>
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
										<s:label name="sellOrderDTO.createTime"/>
									</td>
								</tr>
								<tr>
									<td align="right" width="15%" nowrap="nowrap">
										订单状态：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
									<s:hidden name="sellOrderDTO.orderState"/>
										<s:if test="sellOrderDTO.orderState == 1">
											草稿
										</s:if>
									</td>
									<td align="right" width="15%" nowrap="nowrap">
										订单来源：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:hidden name="sellOrderDTO.orderSource"/>
										<s:if test="sellOrderDTO.orderSource == 1">
											系统录入
										</s:if>
										<s:if test="sellOrderDTO.orderSource == 2">
											订单合并
										</s:if>
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
							</table>
						</div>						
						
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront"  style="cursor: pointer;">
										<span class="TableTop">订单付款信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable2">
							
							


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
						
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人信息:
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
								法(个)人联系电话：
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
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件类型:
							</td>
							<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="customerDTO.corpCredType"
													dictValue="${customerDTO.corpCredType}" dictType="140"
													tagType="1" defaultOption="false" />
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件号:
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredId" size="20"  readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件有效期:(起)
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredStaValidity" size="20"  readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								(至):
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
						<s:select disabled="true" list="customerDTO.contractList" name="sellOrderDTO.contactId" onchange="onOperChange()" id="contactId" listKey="contactId" listValue="contactName"/>
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
										<span class="TableTop">原有卡信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="origCardviewForm" name="origCardviewForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId" ></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>	
									<input type="hidden" name="sellOrderOrigCardListDTO.callBack" id="sellOrderOrigCardListDTO.callBack" value=""/>								
								<ec:table items="origCardList" var="map" width="100%" form="origCardviewForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="origCardList">
									<ec:row ondblclick="">
									<ec:column property="null" alias="origCardListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<s:if test="#attr['map'].cardState== 1 || #attr['map'].cardState== 3">
										<input type="checkbox" name="origCardListStr"
											value="${map.origCardListId}" />
											</s:if>
									</ec:column>
									<ec:column property="cardNo" title="卡号" width="15%" sortable="false"/>
									<ec:column property="productName" title="产品名称" width="20%" sortable="false"/>
									<ec:column property="amount" title="余额" width="10%" sortable="false"/>
									<ec:column property="firstName" title="持卡人姓名" width="10%" sortable="false"/>
									<ec:column property="validityPeriod" title="有效期" width="15%" sortable="false"/>
									<ec:column property="cardState" title="卡状态" width="10%" sortable="false">
										<s:if test="#attr['map'].cardState== 0">待验收</s:if>
										<s:if test="#attr['map'].cardState== 1">已验收</s:if>
										<s:if test="#attr['map'].cardState== 2">入库</s:if>
										<s:if test="#attr['map'].cardState== 3">销户</s:if>
									</ec:column>
									<ec:column property="callBack" title="是否回收" width="20%" sortable="false">
									<s:if test="#attr['map'].cardState== 1 ||#attr['map'].cardState== 3">
									    <s:if test="#attr['map'].callBack==1">是</s:if>
										 <s:if test="#attr['map'].callBack==0">否</s:if>
									</s:if>
									<!-- <s:if test="#attr['map'].cardState== 3">
									    <s:if test="#attr['map'].callBack==1">是</s:if>
										 <s:if test="#attr['map'].callBack==0">否</s:if>
									</s:if> -->
									</ec:column>
								</ec:row>
								</ec:table>
								<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="modifyCardState('0');">
									取消回收
								</button>
								<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="modifyCardState('1');">
									回收卡片
								</button>
								
								</s:form>
							</div>
			</td></tr></table></div>
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
															<span class="TableTop">支付节点信息</span>
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
							</div>
							<!-- 订单付款信息     结束 -->
			<%@ include file="orderFlowList.jsp"%>
  </body>
</html>
