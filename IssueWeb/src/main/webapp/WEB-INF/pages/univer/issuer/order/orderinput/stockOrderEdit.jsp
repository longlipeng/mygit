<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑库存订单</title>
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
	
	function productChange(select) {
		maskDocAll();
		newForm.action = "stockOrderInputAction!editInput";
		newForm.submit();
	}

	//function faceValueChange() {
		
	//	<s:iterator value="prodFaceValues" var="map">
	//		<s:if test="#map.faceValueType eq 1">
	//			if(document.forms[0].getAttribute("faceValueId").value==${map.faceValueId}){
	//			document.forms[0].getAttribute("sellOrderDTO.faceValue").value=0;
	//			document.getElementById("sellOrderDTO.faceValue").readOnly = false;
	//			document.forms[0].getAttribute("sellOrderDTO.faceValueType").value=1;
	//			}	
	//		</s:if>
	//		<s:if test="#map.faceValueType eq 0">
	//			if(document.forms[0].getAttribute("faceValueId").value==${map.faceValueId}){
	//			document.forms[0].getAttribute("sellOrderDTO.faceValue").value=${map.faceValue/100};
	//			document.forms[0].getAttribute("sellOrderDTO.faceValueType").value=0;
	//			}	
	//		</s:if>
	//	</s:iterator>					
	//}
	function selectInit(){
		var select = document.getElementById("sellOrderDTO.faceValueId");
		for(var i=0;i<select.length;i++){
			var text = select.options[i].text;
			if(text.search(/固定/) != -1){
				var index = text.indexOf(".");
				if(index != -1){
					text = text.slice(0,index);
					select.options[i].text = text;
				}
			}
		}
	}
	function faceValueChange() {
			<s:iterator value="prodFaceValues" var="map">
				<s:if test="#map.faceValueType  eq 1">
					if(document.getElementById("sellOrderDTO.faceValueId").value==${map.faceValue}){
						document.getElementById("sellOrderDTO.faceValue").value=0;
						document.getElementById("sellOrderDTO.faceValueType").value =1;
					}	
				</s:if>
				<s:if test="#map.faceValueType eq 0">
					if(document.getElementById("sellOrderDTO.faceValueId").value==${map.faceValue}){
						document.getElementById("sellOrderDTO.faceValue").value=${map.faceValue/100};
						document.getElementById("sellOrderDTO.faceValueType").value =0;
					}	
				</s:if>
			</s:iterator>
			selectInit();					
		}
	function orderSubmit(){
		maskDocAll();
		newForm.action = "stockOrderInputAction!update";
		newForm.submit();
	}
	function onProductChange(){
		var curProductId=document.getElementById("sellOrderDTO.productId").value;
		<s:iterator value="productDTOs" var="map">
			if(curProductId==${map.productId}){
				if(${map.onymousStat}==3){
					document.getElementById("cardValidityPeriod").value='2999-12-31';
					document.getElementById("validPeriod").disabled=false;
					document.getElementById("validPeriod").value='2999-12-31';
					document.getElementById("cardValidityPeriod").disabled=true;
				}else{
					document.getElementById("cardValidityPeriod").disabled=false;
					document.getElementById("validPeriod").value="";
					document.getElementById("validPeriod").disabled=true;
				}
			}
		</s:iterator>
	}
</script>
	</head>
	<body onload="faceValueChange();onProductChange();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>编辑库存订单基本信息</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
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
											<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<s:select name="sellOrderDTO.productId" id="sellOrderDTO.productId"
												list="productDTOs" listKey="productId"
												listValue="productName" onchange="productChange(this);" readonly="true"  cssClass="lg_text_gray">
											</s:select>
											</span>
											<s:fielderror>
												<s:param>
													sellOrderDTO.productId
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>账户：
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
										
									</tr>
									
									<tr>
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡片有效期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.validityPeriod"  id="validPeriod"></s:hidden>
											<s:textfield name="sellOrderDTO.validityPeriod" 
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
											<s:param>
												sellOrderDTO.validityPeriod
											</s:param>
											</s:fielderror>
										</td>	
									</tr>
									<s:if test="sellOrderDTO.cardIssueFee !=null && sellOrderDTO.annualFee !=null">
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield  name="sellOrderDTO.cardIssueFee"
												id="sellOrderDTO.cardIssueFee" size="20" maxLength="10"   readonly="true"  cssClass="lg_text_gray"/>
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
												id="annualFee" size="20" maxLength="10"  readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.annualFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									</s:if>
									<s:if test="sellOrderDTO.deliveryFee !=null ">
									<tr>
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap"
											id="deliveryFeeTd2">
											<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10"   readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.deliveryFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									</s:if>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>面额类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
											<s:select name="faceValueId" id="faceValueType" list="prodFaceValues"
												listKey="faceValueId" listValue="faceValueType==1?'非固定':固定"
												onchange="faceValueChange();" headerKey="" headerValue="--请选择--" >
										    </s:select>-->
										    <s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
											
												<s:select name="sellOrderDTO.faceValueId" id="sellOrderDTO.faceValueId"
													list="prodFaceValues" listKey="faceValue"
													listValue="faceValue==0?'非固定':'固定'+faceValue/100"
													onchange="faceValueChange();">
												</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValueType
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											面额值：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<s:textfield name="sellOrderDTO.faceValue"
												id="faceValue" size="20" maxLength="10" readonly="true" />
											<s:fielderror>-->
											<s:textfield name="sellOrderDTO.faceValue"  readonly="true" id="sellOrderDTO.faceValue" size="20" />
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
										<%--<td align="right" width="15%" nowrap="nowrap">
											面额值：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.faceValue" id="faceValue" list="prodFaceValues"
												listKey="faceValue" listValue="faceValue/100" />	
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
										
										--%>
										
									</tr>
								
									<tr>
										
										<td width="15%" align="right" nowrap="nowrap">
											总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPrice" size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>张数：
										</td>
										<td>
										<s:if test="productCardBinDTO.binType != 1">
											<s:textfield name="sellOrderDTO.cardQuantity"
														id="cardQuantity" size="20" maxLength="10" />
												<s:fielderror>
													<s:param>
														sellOrderDTO.cardQuantity
													</s:param>
												</s:fielderror>
											</td>
										</s:if>
										<s:if test="productCardBinDTO.binType == 1">
											<s:textfield name="sellOrderDTO.cardQuantity"
														id="cardQuantity" size="20" maxLength="10" readonly="true" cssClass="lg_text_gray"/>
												<s:fielderror>
													<s:param>
														sellOrderDTO.cardQuantity
													</s:param>
												</s:fielderror>
											</td>
										</s:if>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.memo" id="memo" size="20"
												maxLength="400" />
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
			<s:form action="stockOrderInputAction!list" name="backForm"
				id="backForm">
				<s:hidden name="addType" value=""></s:hidden>
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		
		
	</body>
</html>