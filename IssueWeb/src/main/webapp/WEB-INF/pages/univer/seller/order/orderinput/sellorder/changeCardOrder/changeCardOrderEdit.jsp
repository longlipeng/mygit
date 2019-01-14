<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>编辑换卡订单</title>
    <%@ include file="/commons/meta.jsp"%>
	<%@ include file="/commons/ajax.jsp"%>
	<script type="text/javascript">
	
		function loadSalesmanList() {
			setOrderContact();
			onBankChange();
			onOperChange();
			onCusBankChange();
			var select=document.getElementById("sellOrderDTO.deliveryMeans");
			deliveryMeansSelect(select);
        var salesmanList = ${saleUserList};
        var store = new Ext.data.JsonStore({
            data: salesmanList,
            autoLoad: true,
            fields: [{name: 'id', mapping: 'userId'}, {name: 'name', mapping: 'userName'}]
        });
        var combo = new Ext.form.ComboBox({
        	width:120,
            store: store,
            displayField:'name',
            hiddenName: 'sellOrderDTO.saleMan',
            valueField: 'id',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText: '请选择销售人员',

            applyTo: 'saleUserId',
            forceSelection: true
        });
    }
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
	
	function setOrderContact(){
		var optionInnerHTML="<select name='sellOrderDTO.orderContact' id='sellOrderDTO.orderContact'>";
		var deliveryPoint=document.getElementById("deliveryPoint").value;
		
		<s:iterator value="customerDTO.deliveryPointList" status="status" var="map">
			if(deliveryPoint==${map.deliveryId}){
				  <s:iterator  value="%{#attr.map.recipientList}" status="status1" var="map1">
	    				optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}'>${map1.deliveryContact}</option>";
				  </s:iterator>
			}
		</s:iterator>
		optionInnerHTML=optionInnerHTML+"</select>";
		document.getElementById("orderContact").innerHTML=optionInnerHTML;
	}
	
	function orderSubmit(){ 	
		var paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		var authFlag=document.getElementById("authFlag").value;
		var oldPaymentTerm=document.getElementById("sellOrderDTO.oldPaymentTerm").value;
		if(oldPaymentTerm!=4 && paymentTerm==4 && authFlag==0){
			errorDisplay("信用支付时  必须进行权限认证!");
			return;
		}
		editForm.action = "changeCardOrderAction!update";
		editForm.submit();
	}
	function onBankChange() {
		var chooseBank = document.getElementById("bankNames").value;
			<s:iterator value="lstBankDTOs" var="map">
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
	function deleteOrigCard(){
		var flag = true;
		for(i = 0; i < document.getElementsByName("origCardListStr").length; i++) {
            if (document.getElementsByName("origCardListStr").item(i).checked) {
                        flag = false;
                        break;
                }
	    }   
        if(flag){
            errorDisplay("请选择一条记录");
            return;
        }
	    confirm("确定要删除吗?",delOpr);
	}
	function delOpr(){
		document.origCardListForm.action = "${ctx}/changeCardOrderAction!deleteOrigCard?sellOrderOrigCardListQueryDTO.orderId=${sellOrderDTO.orderId}";
	  	document.origCardListForm.submit();			
	}
	function addOrigCard(orderId){
		var returnValue = window.showModalDialog('${ctx}/changeCardOrderAction!addOrigCard?sellOrderOrigCardListQueryDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}&sellOrderDTO.productId=${sellOrderDTO.productId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		if(returnValue==1){			 
		  	document.editForm.action = "${ctx}/changeCardOrderAction!edit";
		  	document.editForm.submit();
		}	  
	}
	function addOrderList(){
		var returnValue = window.showModalDialog('${ctx}/changeCardOrderAction!addOrderList?sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}', '_blank',  'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			if(returnValue==1){			 
			  	document.editForm.action = "${ctx}/changeCardOrderAction!edit";
			  	document.editForm.submit();
			 }	  
	}
	function deleteRecord(){
			var flag = true;
			for(i = 0; i < document.getElementsByName("orderListStr").length; i++) {
	                  if (document.getElementsByName("orderListStr").item(i).checked) {
	                              flag = false;
	                              break;
	                      }
	                  }   
	              if(flag){
	                  errorDisplay("请选择一条记录");
	                  return;
	              }
	              confirm("确定要删除吗?",delOrderList);
		}
	function delOrderList(){
		document.orderListForm.action = "${ctx}/changeCardOrderAction!deleteOrderList?sellOrderDTO.orderId=${sellOrderDTO.orderId}";
	  	document.orderListForm.submit();			
	}
	function editRecord(orderListId){
            var returnValue = window.showModalDialog('${ctx}/changeCardOrderAction!editOrderList?sellOrderListDTO.productId=${sellOrderDTO.productId}&sellOrderListDTO.orderListId='+orderListId, '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');           
			if(returnValue==1){			 
			  	document.editForm.action = "${ctx}/changeCardOrderAction!edit";
			  	document.editForm.submit();
			  }	  
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
  
 <body onload="loadSalesmanList();">
 	<%@ include file="/commons/messages.jsp"%>
 	
	<div class="TitleHref">
		<span>订单管理>编辑换卡订单基本信息</span>
	</div>
	<s:form id="editForm" name="editForm" action="changeCardOrderAction!update" method="post">
	<s:hidden name="sellOrderDTO.orderType" />
	<s:hidden name="sellOrderDTO.oldPaymentTerm" id="sellOrderDTO.oldPaymentTerm" />
	<s:hidden id="sellOrderDTO.productId" name="sellOrderDTO.productId"></s:hidden>
	<s:hidden name="errorjsp" />		
	<s:hidden  id="authFlag"/>
	<s:hidden id="sellOrderDTO.paymentTerm" name="sellOrderDTO.paymentTerm"></s:hidden>
	<s:hidden id="sellOrderDTO.paymentDelay" name="sellOrderDTO.paymentDelay"></s:hidden>
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
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20" readonly="true" required="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderDate"
												id="sellOrderDTO.orderDate" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId"
												id="sellOrderDTO.firstEntityId" size="20" readonly="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
														客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerName"
												id="customerName" size="20" readonly="true"  cssClass="lg_text_gray" />
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.productName"
												id="productName" size="20" readonly="true"  cssClass="lg_text_gray" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.processEntityId"/>
											<span><s:textfield name="sellOrderDTO.processEntityName"
													readonly="true" cssClass="lg_text_gray" />  </span>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>送货方式：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<script type="text/javascript">
												var value;
												function deliveryMeansSelect(select) {
													if (select.value == 2) {
														document.getElementById("deliveryMeansTr").style.display="none";
														value = document.getElementById("sellOrderDTO.deliveryFee").value;
														document.getElementById("sellOrderDTO.deliveryFee").value = "0";
														document.getElementById("sellOrderDTO.deliveryFee").readOnly = true;
														document.getElementById("sellOrderDTO.deliveryFee").className = "lg_text_gray";
													} else {
														document.getElementById("sellOrderDTO.deliveryFee").value = "${sellOrderDTO.deliveryFee}";
														document.getElementById("deliveryMeansTr").style.display="";
														document.getElementById("sellOrderDTO.deliveryFee").readOnly  = false;
														document.getElementById("sellOrderDTO.deliveryFee").className="";
													}
												}
											</script>
											<dl:dictList displayName="sellOrderDTO.deliveryMeans"
												dictType="202" dictValue="${sellOrderDTO.deliveryMeans}"
												tagType="2"  props="id='sellOrderDTO.deliveryMeans'   onchange='deliveryMeansSelect(this)'"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.deliveryMeans
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap"
											id="deliveryFeeTd2">
											<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.deliveryFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr id="deliveryMeansTr">
										
										<td width="15%" align="right" nowrap="nowrap">
											收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.deliveryPoint"
												id="deliveryPoint" list="customerDTO.deliveryPointList"
												listKey="deliveryId" listValue="deliveryAddress" onchange="setOrderContact();">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.deliveryPoint
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											收货人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<div id="orderContact">
											</div>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderContact
													</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
											<td align="right" width="15%" >
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
											<s:textfield name="sellOrderDTO.saleMan" id="saleUserId"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.saleMan
												</s:param>
											</s:fielderror>
										</td>
											<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>包装费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:textfield name="sellOrderDTO.packageFee"
												id="sellOrderDTO.packageFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.packageFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
											<td align="right" width="15%" >
											<span style="color: red;">*</span>原卡总金额(元)：
										</td>
										<td>
											<s:textfield name="sellOrderDTO.origCardTotalAmt" id="origCardTotalAmount" cssClass="lg_text_gray" readonly="true"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.saleMan
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											新卡总金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.newCardTotalAmt"
												id="newCardTotalAmount" size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>服务费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.additionalFee"
												id="sellOrderDTO.additionalFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.additionalFee
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPriceStr" size="10" readonly="true" cssClass="lg_text_gray"/>
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
											<s:date name="sellOrderDTO.createTime"
												format="yyyy-MM-dd" />
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
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="70" rows="5"></s:textarea>
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
							<dl:dictList dictType="901" tagType="2" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							支付明细:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="sellOrderDTO.payDetails" maxlength="40" size="20" />
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							开户银行:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.fromBankId" id="cusBankNames" list="customerDTO.bankList" listKey="bankId" listValue="bankName" onchange="onCusBankChange()"></s:select>
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
						<s:select list="customerDTO.contractList" name="sellOrderDTO.contactId" onchange="onOperChange()" id="contactId" listKey="contactId" listValue="contactName"/>
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
	</s:form>
	<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="orderInputAction!list" name="backForm"
				id="backForm">
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<!-- onclick="this.disabled='disabled';orderSubmit();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');" -->
			<button class='bt' style="float: right; margin: 7px"
				 onclick="orderSubmit();">
					保存
			</button>
			<div style="clear: both"></div>
	</div>
	<!-- 原有卡列表 -->
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
						<s:form id="origCardListForm" name="origCardListForm" action=""
									method="post">
									<input type="hidden" name="sellOrderOrigCardListDTO.orderId" value="${sellOrderDTO.orderId}"/>
						<ec:table items="origCardLists" var="map" width="100%" form="origCardListForm"
									action="${ctx}/changeCardOrderAction!edit"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="origCardLists"
									showPagination="false" showStatusBar="false">
							<ec:row ondblclick="">
								<ec:column property="null" alias="origCardListStr" title="选择"
										width="10%" sortable="false" headerCell="selectAll"
										viewsAllowed="html">
									<input type="checkbox" name="origCardListStr"
										value="${map.origCardListId}" />
								</ec:column>
								<ec:column property="cardNo" sortable="false" title="卡号" width="20%" />
								<ec:column property="productName" sortable="false" title="产品名称" width="20%" />
								<ec:column property="amount" sortable="false" title="余额" width="15%" />
								<ec:column property="firstName" sortable="false" title="持卡人姓名" width="15%" />
								<ec:column property="validityPeriod" sortable="false" title="有效期" width="20%" />
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
														onclick="deleteOrigCard();">
														删除
													</div>					
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addOrigCard(${sellOrderDTO.orderId});">
														添加
													</div>
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
	<!-- 新卡明细列表 -->
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
								<span class="TableTop">新卡明细信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="TableBody">
						<s:form id="orderListForm" name="orderListForm" action=""
									method="post">
						<ec:table items="customerOrderLists" var="map" width="100%" form="orderListForm"
									action="${ctx}/changeCardOrderAction!edit"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="customerOrderLists"
									showPagination="false" showStatusBar="false">
							<ec:row ondblclick="">
								<ec:column property="null" alias="orderListStr" title="选择"
										width="10%" sortable="false" headerCell="selectAll"
										viewsAllowed="html">
									<input type="checkbox" name="orderListStr"
										value="${map.orderListId}" />
								</ec:column>
								<ec:column property="productName" sortable="false" title="产品名称" width="15%" />
								<ec:column property="serviceName" sortable="false" title="充值账户" width="15%" />
								<ec:column property="cardLayoutName" sortable="false" title="卡面" width="10%" />
								<ec:column property="faceValueType" sortable="false" title="面额类型"  width="10%" />
								<ec:column property="faceValue" sortable="false" title="面额值" width="10%" />
								<ec:column property="validityPeriod" sortable="false" title="有效期" width="13%" />
								<ec:column property="cardAmount" sortable="false" title="张数" width="7%" />
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
														onclick="deleteRecord();">
														删除
													</div>					
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addOrderList(${sellOrderDTO.orderId});">
														添加
													</div>
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
  </body>
</html>
