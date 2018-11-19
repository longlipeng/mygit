<%@page contentType="text/html; charset=UTF-8"%>
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
								充值账户：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.serviceName" />
							</td>

						</tr>

						<tr>
							<td width="15%" align="right" nowrap="nowrap">
								卡面：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.cardLayoutName" /> 
							</td>
							<td width="15%" align="right" nowrap="nowrap">
								卡片有效期：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.validityPeriod" /> 
							</td>
						</tr>

						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								面额值(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								 <s:label name="sellOrderDTO.faceValue" /> 
							</td>

							<td align="right" width="15%" nowrap="nowrap">
								张数：
							</td>
							<td>
								<s:label name="sellOrderDTO.cardQuantity" />
							</td>
						</tr>
						<s:if test="sellOrderDTO.cardIssueFee !=null && sellOrderDTO.annualFee !=null">
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
						</s:if>
						<tr>
							<td width="15%" align="right" nowrap="nowrap">
								送货方式：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<dl:dictList displayName="sellOrderDTO.deliveryMeans"
									dictType="202" dictValue="${sellOrderDTO.deliveryMeans}"
									tagType="1" />  
							</td>
							<td align="right" width="15%" nowrap="nowrap" id="deliveryFeeTd1">
								送货费用(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap" id="deliveryFeeTd2">
								<s:label name="sellOrderDTO.deliveryFee" />
							</td>

						</tr>
						<tr id="deliveryMeansTr">
							<td width="15%" align="right" nowrap="nowrap">
								收货地址：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.deliveryPoint" />
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								收货人：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<div id="orderContact">
								</div>
								<s:label name="sellOrderDTO.orderContact" />
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								发票公司名称：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								 <s:label name="sellOrderDTO.invoiceCompanyName" />
							</td>
							<td width="15%" align="right" nowrap="nowrap">
								发票地址：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								 <s:label name="sellOrderDTO.invoiceAddresses" /> 
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								发票内容名称：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<edl:entityDictList displayName="sellOrderDTO.invoiceItemId"
									dictType="182" dictValue="${sellOrderDTO.invoiceItemId}"
									tagType="1" />  
							</td>
							<td width="15%" align="right" nowrap="nowrap">
								发票日期：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.invoiceDate" />
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								附加费(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:label name="sellOrderDTO.additionalFee" />
							</td>
							<td width="15%" align="right" nowrap="nowrap">
								折扣费(元)：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
							 <s:label name="sellOrderDTO.discountFee" />
							</td>
						</tr>
						<tr>
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
<br/>
