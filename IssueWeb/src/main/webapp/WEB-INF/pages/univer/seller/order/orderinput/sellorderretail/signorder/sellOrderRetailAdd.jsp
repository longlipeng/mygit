<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加销售订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		function loadSalesmanList() {			
			packageChange();
			setPaymentTerm();
			//var select=document.getElementById("sellOrderDTO.deliveryMeans");
			//deliveryMeansSelect(select);
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
	//function setPaymentTerm() {
	//	paymentTerm = document.getElementById("sellOrderDTO.paymentTerm").value;
	//	if (paymentTerm != 4) {
	//		document.getElementById("sellOrderDTO.paymentDelay").value = "0";
	//		document.getElementById("sellOrderDTO.paymentDelay").readOnly = true;
	//	} else {
	//		document.getElementById("sellOrderDTO.paymentDelay").value = "${sellOrderDTO.paymentDelay}";
	//		document.getElementById("sellOrderDTO.paymentDelay").readOnly = false;
	//	}
	//}

	function openCustomerPage() {
		var returnValue = window.showModalDialog('${ctx}/customer/chooseCustomer',
				'_blank',
				'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no')
		if (returnValue == null || returnValue == undefined)
			return;
		maskDocAll();
		document.getElementById("sellOrderDTO.firstEntityId").value=returnValue;
		newForm.action = "sellOrderSignAction!add";
		newForm.submit();
	}

	function packageChange() {

	//	var packagefee = document.getElementById("sellOrderDTO.packageFee").value;

			<s:iterator value="packages" var="map">	
				if(document.getElementById("sellOrderDTO.packageId").value==${map.packageId}){
					document.getElementById("sellOrderDTO.packageFee").value=${map.packageFee};
				}			
		     </s:iterator>
	}
	
	function setPaymentTerm(){
	     paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
	     if(paymentTerm!=4){
	     	document.getElementById("sellOrderDTO.paymentDelay").value="0";
	     	document.getElementById("sellOrderDTO.paymentDelay").readOnly=true;
	     }else{
	        returnValue = window.showModalDialog('${ctx}/user/inputAuthPassword.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:700px;resizable:no');
	    	document.getElementById("authFlag").value="1";
	    	if (returnValue == null || returnValue == undefined)
	    	{  
	    	   alert("没有进行信用支付权限认证！");
	    	   document.getElementById("authFlag").value="0";
	    	   return;
	    	}	 
	     	document.getElementById("sellOrderDTO.paymentDelay").value="${sellOrderDTO.paymentDelay}";
	     	document.getElementById("sellOrderDTO.paymentDelay").readOnly=false;
	     }
	}
	//function setOrderContact(){
	//	var optionInnerHTML="<select name='sellOrderDTO.orderContact' id='sellOrderDTO.orderContact'>";
	//	var deliveryPoint=document.getElementById("deliveryPoint").value;
		
	//	<s:iterator value="customerDTO.deliveryPointList" status="status" var="map">
	//		if(deliveryPoint==${map.deliveryId}){
	//			  <s:iterator  value="%{#attr.map.recipientList}" status="status1" var="map1">
	 //   				optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}'>${map1.deliveryContact}</option>";
	//			  </s:iterator>
	//		}
	//	</s:iterator>
	//	optionInnerHTML=optionInnerHTML+"</select>";
	//	document.getElementById("orderContact").innerHTML=optionInnerHTML;
	//}
	function productChange(select) {
		maskDocAll();
		newForm.action = "${ctx}/sellOrderRetailSignAction!add";
		newForm.submit();
	}

	function isNullOrEmpty(oValue){
  		return oValue==null||oValue=="";
  	}
  	
	function orderSubmit(){ 	
		var cruProdMaxBalance=document.getElementById("currentProdMaxBalance").value;
		var printFaceValue=document.getElementById("crutfaceValue").value;
		var doublePatrn = /^(([1-9]{1}[0-9]{0,13})|([0]))$/;
		if((!isNullOrEmpty(printFaceValue))&&(!doublePatrn.exec(printFaceValue))){
			errorDisplay("第一次充值金额输入格式错误,必须是正整数,无则填写0!");
			return;
		}
		if(parseFloat(printFaceValue)>parseFloat(cruProdMaxBalance)){
			errorDisplay("第一次充值金额不能大于产品的最大余额:"+cruProdMaxBalance);
			return;
		}
		var authFlag=document.getElementById("authFlag").value;
		var paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		
		if(paymentTerm==4 && authFlag==0){
			errorDisplay("信用支付时 必须进行权限认证!");
			return;
		}
		
		
		var additionalFee=document.getElementById("sellOrderDTO.additionalFee").value;		
		if(IsSpace(additionalFee)){
			errorDisplay("附加费必须输入，无请填写0!");
			return;
		}
		if(!IsBigdecimal(additionalFee)){
			errorDisplay("附加费必须为正数!");
			return;
		}
		if(additionalFee<0){
			errorDisplay("附加费必须为正数!");
			return;
		}
		newForm.action = "sellOrderRetailSignAction!insert";
		newForm.submit();
	}
	function onProductChange(){
		var curProductId=document.getElementById("sellOrderDTO.productId").value;
		<s:iterator value="productDTOs" var="map">
			if(curProductId==${map.productId}){
				if(${map.onymousStat}==3){
					document.getElementById("cardValidityPeriod").value='2999-12-31';
					document.getElementById("validityPeriod").disabled=false;
					document.getElementById("validityPeriod").value='2999-12-31';
					document.getElementById("cardValidityPeriod").disabled=true;
				}else{
					document.getElementById("cardValidityPeriod").disabled=false;
					document.getElementById("validityPeriod").value="";
					document.getElementById("validityPeriod").disabled=true;
				}
			}
		</s:iterator>
	}
</script>
	</head>
	<body onload="loadSalesmanList();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加销售记名卡订单基本信息</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
			<s:hidden name="sellOrderDTO.orderType" />			
			<s:hidden id="authFlag" />
			<s:hidden name="sellOrderDTO.cardQuantity"/>
			<s:hidden name="sellOrderDTO.paymentState"/>
			<s:hidden name="sellOrderDTO.firstEntityId"/>
			<s:hidden name="customer.customerName"/>
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
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.productId" id="sellOrderDTO.productId"
												list="productDTOs" listKey="productId"
												listValue="productName" onchange="productChange(this);">
											</s:select>
											<input type="hidden" id="currentProdMaxBalance" name="currentProdMaxBalance" value="${currentProdMaxBalance}"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.productId
												</s:param>
											</s:fielderror>
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
										<s:hidden name="sellOrderDTO.faceValueType" value="1"/>
										<td width="15%" align="right">
											<span style="color: red;">*</span>第一次充值金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.faceValue"
												id="crutfaceValue" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
										<s:hidden name="sellOrderDTO.validityPeriod" id="validityPeriod" value="2999-12-31"></s:hidden>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡面：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.cardLayoutId"
												id="cardLayoutId" list="cardLayouts" listKey="cardLayoutId"
												listValue="cardName">
											</s:select>
											<s:fielderror>
												<s:param>
											sellOrderDTO.cardLayoutId
												</s:param>
											</s:fielderror>
										</td>
									</tr>

									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>包装：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.packageId" list="packages"
												listKey="packageId" listValue="packageName"
												onchange="packageChange();" id="sellOrderDTO.packageId">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.packageId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right">
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
										
										 <td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>充值账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.serviceId" list="services"
												listKey="serviceId" listValue="serviceName"
											 id="sellOrderDTO.serviceId">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.serviceId
												</s:param>
											</s:fielderror>
										</td> 
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield  name="sellOrderDTO.cardIssueFee"
												id="sellOrderDTO.cardIssueFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardIssueFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										
									
								<%--		<s:fielderror>
												<s:param>
										sellOrderDTO.cardIssueFeeStr
									</s:param>
											</s:fielderror> --%>	
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.annualFee"
												id="annualFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.annualFee
												</s:param>
											</s:fielderror>
										</td>										
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<dl:dictList displayName="sellOrderDTO.paymentTerm"
												dictType="207" dictValue="${sellOrderDTO.paymentTerm}"
											 	tagType="2" props="id='sellOrderDTO.paymentTerm'    onchange=javascript:setPaymentTerm()"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.paymentTerm
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.paymentDelay"
												id="sellOrderDTO.paymentDelay" size="20" maxLength="5"
												readonly='%{sellOrderDTO.paymentTerm==4 ? "false" : "true"}' />
											<s:fielderror>
												<s:param>
													sellOrderDTO.paymentDelay
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>附加费(元)：
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
											<span style="color: red;">*</span>折扣费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.discountFee"
												 size="20" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.discountFee
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
										<td width="35%" align="left" nowrap="nowrap">va
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
			</div>			
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="orderInputAction!list" name="backForm"
				id="backForm">
				<s:hidden name="addType" value=""></s:hidden>
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
				下一步
			</button>
			<div style="clear: both"></div>
		</div>
		
		
	</body>
</html>