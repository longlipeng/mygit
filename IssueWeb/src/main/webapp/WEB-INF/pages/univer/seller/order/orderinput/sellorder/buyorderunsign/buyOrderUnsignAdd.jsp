<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加采购匿名订单</title>
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
	function setPaymentTerm() {
		paymentTerm = document.getElementById("sellOrderDTO.paymentTerm").value;
		if (paymentTerm != 4) {
			document.getElementById("sellOrderDTO.paymentDelay").value = "0";
			document.getElementById("sellOrderDTO.paymentDelay").readOnly = true;
		} else {
			document.getElementById("sellOrderDTO.paymentDelay").value = "";
			document.getElementById("sellOrderDTO.paymentDelay").readOnly = false;
		}
	}

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
		<s:iterator value="packages" var="map">	
			if(document.getElementById("sellOrderDTO.packageId").value==${map.packageId}){
				document.getElementById("sellOrderDTO.packageFee").value=${map.packageFee};
			}			
	     </s:iterator>
	}
	
	function setOrderContact(){
		var deliveryContactId = 0;
		<c:if test="${sellOrderDTO.orderContact}!=null&&${sellOrderDTO.orderContact}!=''">
			deliveryContactId =${sellOrderDTO.orderContact};
		</c:if>
	
		var optionInnerHTML="<select name='sellOrderDTO.orderContact' id='sellOrderDTO.orderContact'>";
		var deliveryPoint=document.getElementById("deliveryPoint").value;
		
		<s:iterator value="sellerDTO.deliveryPointList" status="status" var="map">
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
	
	function orderSubmit(){ 	
		var deliveryFee=document.getElementById("sellOrderDTO.deliveryFee").value;	
// 		if(!IsBigdecimal(deliveryFee)){
// 			errorDisplay("送货费用必须为正数!");
// 			return;
// 		}
// 		if(deliveryFee<0){
// 			errorDisplay("送货费用必须为正数!");
// 			return;
// 		}
		var reg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		if(!reg.test(deliveryFee)){
			errorDisplay("送货费用必须为正数且小于100000000(精确到小数点后两位)");
			return;
		}
		newForm.action = "buyOrderUnSignAction!insert";
		newForm.submit();
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
	              confirm("确定要删除吗?",delOpr);
		}


		function delOpr(){
			document.EditForm.action = "sellOrderSignAction!deleteOrderList";
		  	document.EditForm.submit();			
		}
		
		
		function addOrderList(orderId){
			
			var returnValue = window.showModalDialog('${ctx}/buyOrderUnSignAction!addOrderList?sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			if(returnValue==1){			 
			  	document.newForm.action = "buyOrderUnSignAction!edit";
			  	document.newForm.submit();
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
	              confirm("确定要删除吗?",delOpr);
		}


		
		function openProductPage(){
			  var returnValue = window.showModalDialog('${ctx}/buyOrderUnSignAction!loadProductInfoByContract', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:800px;resizable:no'); 
			  if (returnValue == null || returnValue == undefined)
					return;
				var productId_product_name = returnValue.split(",");
				maskDocAll();
				document.getElementById("sellOrderDTO.productId").value=productId_product_name[0];
				document.getElementById("sellOrderDTO.productName").value=productId_product_name[1];
				newForm.action = "buyOrderUnSignAction!addBuyOrder";
				newForm.submit();
		}


		
</script>
	</head>
	<body onload="initPage()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加采购订单</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name ="sellOrderDTO.orderState"/>
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
											<span style="color: red;">*</span>订单发起机构编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId"
												id="sellOrderDTO.firstEntityId" size="20" readonly="true"  cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>订单发起方：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityName"
												size="20" readonly="true"  cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityName
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.productName" size="20" readonly="true" id="sellOrderDTO.productName"  />
											<input type="button" value="..." onclick="javascript:openProductPage();"/>
											<s:hidden name="sellOrderDTO.productId" id="sellOrderDTO.productId"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>订单处理方：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<span><s:select name="sellOrderDTO.processEntityId"
												id="sellOrderDTO.processEntityId" list="entityDTOList" listKey="entityId"
												listValue="entityName" >
											</s:select></span>
											<s:fielderror>
												<s:param>
													sellOrderDTO.processEntityId
												</s:param>
											</s:fielderror>
										</td>
									</tr>
								<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield  name="sellOrderDTO.cardIssueFee"
												id="sellOrderDTO.cardIssueFee" size="20" maxLength="10"  readonly="true" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardIssueFee
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.annualFee"
												id="annualFee" size="20" maxLength="10"  readonly="true" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.annualFee
												</s:param>
											</s:fielderror>
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
														
														document.getElementById("sellOrderDTO.deliveryFee").className = "lg_text_gray";
													} else {
														document.getElementById("sellOrderDTO.deliveryFee").value = "${sellOrderDTO.deliveryFee}";
														document.getElementById("deliveryMeansTr").style.display="";
														document.getElementById("sellOrderDTO.deliveryFee").className="";
													}
												}
											</script>
											<dl:dictList displayName="sellOrderDTO.deliveryMeans"
												dictType="202" dictValue="${sellOrderDTO.deliveryMeans}"
												tagType="2"  props="id='sellOrderDTO.deliveryMeans' onchange=deliveryMeansSelect(this)"/>
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
											<span style="color: red;"></span>收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.deliveryPoint"
												id="deliveryPoint" list="sellerDTO.deliveryPointList"
												listKey="deliveryId" listValue="deliveryAddress" onchange="setOrderContact();">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.deliveryPoint
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>收货人：
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
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>发票公司名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.invoiceCompanyName"
												id="invoiceCompanyName"
												list="sellerDTO.invoiceCompanyList"
												listKey="invoiceCompanyId" listValue="invoiceCompanyName">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.invoiceCompanyName
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>发票地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.invoiceAddresses"
												id="invoiceAddresses" list="sellerDTO.invoiceAddressList"
												listKey="invoiceAddressId" listValue="invoiceAddress">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.invoiceAddresses
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>发票内容名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<edl:entityDictList displayName="sellOrderDTO.invoiceItemId"
												dictType="182" dictValue="${sellOrderDTO.invoiceItemId}"
												tagType="2"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.invoiceItemId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>发票日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.invoiceDate"
												id="invoiceDate" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
												
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.invoiceDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<edl:entityDictList displayName="sellOrderDTO.paymentTerm"
												dictType="207" dictValue="${sellOrderDTO.paymentTerm}"
												tagType="2"  props="onchange=javascript:setPaymentTerm()" />
											
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.paymentDelay"
												id="sellOrderDTO.paymentDelay" size="20" maxLength="5"
												readonly="true" value="0" cssClass="lg_text_gray"/>
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
											<s:textfield name="sellOrderDTO.additionalFee" size="20" maxLength="10" />
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
												 size="20"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.discountFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPriceStr" size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										
										 <td align="right" width="15%" nowrap="nowrap">
											张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.cardQuantity"
											size="10" readonly="true" cssClass="lg_text_gray"/>
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
											<s:textarea onpropertychange="if(value.length>200) value=value.substr(0,200)" name="sellOrderDTO.memo"  cols="70" rows="5"></s:textarea>
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
			<s:form action="buyOrderUnSignAction!list" name="backForm"
				id="backForm">
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
					保存
			</button>
			<div style="clear: both"></div>
		</div>
			<!-- div id=TableBody -->
	</body>
</html>