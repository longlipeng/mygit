<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
	</head>
<body >
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
						<td class="TableTitleFront" onclick="displayTableBody('basicMsgTable');"
							style="cursor: pointer;">
							<span class="TableTop">订单信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
				<div id="basicMsgTable">
					<table width="100%">

						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								订单号：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.orderId" />
							</td>

							<td align="right" width="15%" nowrap="nowrap">
								订单日期：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.orderDate" />
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								订单发起方：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.firstEntityName" />
							</td>

							<td align="right" width="15%" nowrap="nowrap">
								订单处理方：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.processEntityName" />
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
							<td align="right" width="15%" nowrap="nowrap"
								id="deliveryFeeTd1">
								订单类型：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
									<dl:dictList displayName="sellOrderDTO.orderType"
									dictType="205" dictValue="${sellOrderDTO.orderType}"
									tagType="1"  />
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								产品：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.productName" />
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								张数：
							</td>
							<td>
								<s:label name="sellOrderDTO.cardQuantity" />
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap"
									id="deliveryFeeTd1">
									<span style="color: red;">*</span>配送张数：
								</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.realCardQuantity"/>
							</td>
							<td align="right" width="15%" nowrap="nowrap"
									id="deliveryFeeTd1">
									<span style="color: red;">*</span>接收张数：
								</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.origCardQuantity"/>
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
												tagType="2"  props="id='sellOrderDTO.deliveryMeans' onchange=deliveryMeansSelect(this)"/>
											</span>-->
										</td>
										
										</tr>
										<tr id="deliveryMeansTr">
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.deliveryFee"/>
											<!--<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10" readOnly="true" cssClass="lg_text_gray"/>-->
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--  <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<s:select name="sellOrderDTO.deliveryPoint" 
												id="deliveryPoint" list="sellerDTO.deliveryPointList"
												listKey="deliveryId" listValue="deliveryAddress" onchange="setOrderContact();">
											</s:select>
											</span>-->
											<s:label name="sellOrderDTO.deliveryPoint"/>
										</td>
										</tr>
										<tr>
										<td align="right" width="15%" nowrap="nowrap">
											收货人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!-- <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<div id="orderContact">
											</div>
											</span>-->
											<s:label name="sellOrderDTO.orderContact"/>
										</td>
							
							<td align="right" width="15%" nowrap="nowrap">
								发票公司名称：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.invoiceCompanyName" />
							</td>
							</tr>
							<tr>
							<td width="15%" align="right" nowrap="nowrap">
								发票地址：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.invoiceAddresses" />
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								发票内容名称：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<edl:entityDictList displayName="sellOrderDTO.invoiceItemId"
									dictType="182" dictValue="${sellOrderDTO.invoiceItemId}"
									tagType="1" />
							</td>
							</tr>
							<tr>
							<td width="15%" align="right" nowrap="nowrap">
								发票日期：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.invoiceDate" />
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								附加费(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.additionalFee" />
							</td>
							</tr>
							<tr>
							<td width="15%" align="right" nowrap="nowrap">
								折扣费(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.discountFee" />
							</td>

							<td width="15%" align="right" nowrap="nowrap">
								总费用(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.totalPrice" id="totalPriceStr" />
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
								备注：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textarea name="sellOrderDTO.memo" cols="50" rows="5"
									readonly="true"></s:textarea>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
</s:form>
<br/>
</body>
</html>