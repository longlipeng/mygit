<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单准备</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			function load(){
				onCusIntoBankChange();
			}
			
		function onCusIntoBankChange() {
				<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
					if('${sellOrderDTO.intoBankId}'=='${map.bankId}'){
						document.getElementById("IntoBankAccount").value="${map.bankAccount}";
						document.getElementById("IntoAccountName").value="${map.bankAccountName}";
					}			
			     </s:iterator>
		}
			function onContactChange(){
			var chooseBank = document.getElementById("contactName").value;
			<s:iterator value="customerDTO.contractList" var="map">
				if(chooseBank=='${map.contactId}'){
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
			function deleteRecord(orderCardId){
				newForm.action="${ctx}/orderReadyAction!deleteRecord?orderReadyDTO.orderId=${sellOrderDTO.orderId} & orderReadyDTO.processEntityId=${sellOrderDTO.processEntityId}";
				newForm.orderCardListId.value=orderCardId;
			//	newForm.orderId.value=${sellOrderDTO.orderId};
				//newForm.processEntityId.value=${sellOrderDTO.processEntityId};
				newForm.submit();
				return ;
			}

			function cardReady(){
			    var count=0;
			    var orderListId="";
				for(i = 0; i < document.getElementsByName("orderListIdStr").length; i++) {
					if (document.getElementsByName("orderListIdStr").item(i).checked) {
						orderListId=document.getElementsByName("orderListIdStr").item(i).value;
						count++;
					}
				}
				if(count==0){
					errorDisplay("请选择一条订单明细信息!");
					return;
				}
				if(count!=1){
					errorDisplay("只能选择一条订单明细信息!");
					return;
				}
				var returnValue =window.showModalDialog('${ctx}/orderReadyAction!cardReady?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.productId=${sellOrderDTO.productId}&orderReadyDTO.processEntityId=${sellOrderDTO.processEntityId}&orderReadyDTO.orderListId='+orderListId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no');
				//避免returnValue在各个浏览器上的差异，不需要判断返回值，直接刷新页面;
				//经过后续测试,暂时不改
				if(returnValue==1){			 
				  	document.forms[0].action = "orderReadyAction!ready";
				  	document.forms[0].submit();
				}	  
			}
	

			function operationDel(){		
				document.newForm.action="${ctx}/orderReadyAction!deleteAllCard";
				document.newForm.submit();
			}

			function deleteAllCard(){
				confirm('确定要删除吗?',operationDel);
				return ;
			}
			function orderListAllReady(){
				document.newForm.action='${ctx}/orderReadyAction!orderListAllReady?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.productId=${sellOrderDTO.productId}&orderReadyDTO.processEntityId=${sellOrderDTO.processEntityId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no';
				document.newForm.submit();
			}
		</script>
	</head>
	
	<body onload="load();">	
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单准备</span>
		</div>
	<s:form id="newForm" name="newForm"
			action="" method="post">
			<!--<s:hidden name="orderReadyDTO.orderId"   id="orderId"></s:hidden>
			<s:hidden name="orderReadyDTO.processEntityId"   id="processEntityId"></s:hidden>
			-->
			<s:hidden name="orderReadyDTO.orderCardListId"  id="orderCardListId"/>
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name="sellOrderDTO.orderId"/>
			<s:hidden name="sellOrderDTO.processEntityId"/>
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
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
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
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.memo"/>
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
							<s:textfield name="contactNameText" size='11' readonly="true" cssClass="phone" value="经(代)办人名称:"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.contactId" id="contactName" list="customerDTO.contractList" listKey="contactId" listValue="contactName" onchange="onContactChange()"></s:select>
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
						<span style="color: red;">*
							<s:textfield name="operCredTypeText" size='13' readonly="true"  cssClass="phone" value="经(代)办人证件类型:"></s:textfield>
						</span>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="operCredType"
												dictValue="1"
											    dictType="140"
												tagType="2" defaultOption="false" props="id='papersType' disabled='true'"/>
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
											<s:textfield id="IntoBankAccount"  size="20" readonly="true" cssClass="lg_text_gray" />
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
								<s:form id="orderlistListForm" name="orderlistListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
								<ec:table items="orderlistList" var="map" width="100%" form="orderlistListForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderList">
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListIdStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListIdStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="cardLayoutName" title="卡面" width="15%" />
										<ec:column property="faceValue" title="面额值" width="15%" />
										<ec:column property="validityPeriod" title="有效期" width="10%" />
										<ec:column property="faceValueType" title="面额类型" width="10%" />
										<ec:column property="cardAmount" title="张数" />
										<ec:column property="realAmount" title="实际张数" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
			<!-- div id=TableBody -->
			
			
			
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
							<div id="orderCardListBody">
								<s:form id="orderCardListForm" name="orderCardListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>								
								<ec:table items="orderCardList" var="map" width="100%" form="orderCardListForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCard" >
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderCardId}" />
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="null" sortable="false" title="操作" width="15%" >
												  <a href="javascript:deleteRecord('${map.orderCardId }');"> 删除 </a>
										</ec:column>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table>
	
		<div style="width: 100%" align=center>
		
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

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
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
												
												<div id="buttonCRUD" style="text-align: right; width: 100%">
													  <div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="cardReady();">
														准备
													</div>
													
													<!--
													<div id="addBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="orderListAllReady();">
														全部准备
													</div>
													-->
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
		<br>
	</body>
</html>