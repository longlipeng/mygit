<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加调拨订单明细</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayServiceTable = false;
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBody');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBody');
					isDisplayTableBody = true;
				}
			}
			function displayServiceTable() {
				if (isDisplayServiceTable) {
					display('serviceTable');
					isDisplayServiceTable = false;
				} else {
					undisplay('serviceTable');
					isDisplayServiceTable = true;
				}
			}
					
		    function clickRow(orderId,cardName,cardLayoutId,faceValueType,faceValue,cardValidDate,faceValueType2,productId,firstEntityId){
		    	document.getElementById("sellOrderListDTO.orderId").value = orderId;
		    	document.getElementById("firstEntityId").value = firstEntityId;	
		    	document.getElementById("sellOrderListDTO.productId").value = productId;	
		        document.getElementById("cardName").value = cardName;//
		    	document.getElementById("sellOrderListDTO.validityPeriod").value=cardValidDate;//
		    	document.getElementById("sellOrderListDTO.cardLayoutId").value=cardLayoutId;//
		    	document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
		    	document.getElementById("faceValueType").value=faceValueType;
		    	//document.getElementById("sellOrderListDTO.currentNowCount").value=countRecord;
		    	//document.getElementById("faceValueId").value=faceValue*100;
		    	document.getElementById("sellOrderListDTO.faceValue").value=faceValue;
		    	document.getElementById("sellOrderListDTO.faceValueType").value=faceValueType2;
		    }
			function submitForm() {
				var cardAmount=document.getElementById("sellOrderListDTO.cardAmount").value;
				var cardName = document.getElementById("cardName").value;
				var faceValueType = document.getElementById("faceValueType").value;
				var faceValue = document.getElementById("sellOrderListDTO.faceValue").value;
				if(cardName == null || cardName == "" || faceValueType == null || 
					faceValueType == "" || faceValue == null || faceValue == ""){
				    errorDisplay("请选择一条订单明细");
					return;
				}
				if(!IsInteger(cardAmount)){
					errorDisplay("张数必须为正整数!");
					return;
				}
				if(cardAmount<=0){
					errorDisplay("张数必须为正整数!");
					return;
				}
				newForm.submit();
			}
			
			function changeCardLayout(){
				var cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				
				document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
			}

			function init(){					
				changeCardLayout();				
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>添加调拨订单明细信息</span>
		</div>
		<div style="width: 100%" align=center>
			<span style="widht: 98%" style="color: red; align:left;">请注意卡库存数量，如果库存不够请及时下库存订单。</span>
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
									<span class="TableTop">库存信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="TableBody">
							<ec:table items="list" var="map" width="100%"
								action="${ctx}/giftSaleOrderAction!addOrderList"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								showPagination="false" showStatusBar="false">
								<ec:row>
									<ec:column property="null" title="选择" width="10%"
										sortable="false" viewsAllowed="html">
										<input type="radio" name="ec_choose" 
											onclick="clickRow('${orderId}','${map.cardName}','${map.cardLayoutId}','${map.faceValueType == 0 ? "固定":"非固定"}','${map.faceValue}','${map.cardValidDate}','${map.faceValueType}','${productId}','${firstEntityId}');" />
									</ec:column>
									<ec:column property="cardName" sortable="false" title="卡面" width="15%" />
									<ec:column property="faceValueType" sortable="false" title="面额" width="18%">
										<c:choose>
											<c:when test="${map.faceValueType eq 0}">固定</c:when>
											<c:otherwise>非固定</c:otherwise>
										</c:choose>
									</ec:column>
									<ec:column property="faceValue" sortable="false" title="面额值" width="18%">
									</ec:column>
									<ec:column property="cardValidDate" sortable="false" title="有效期" width="20%"
										cell="date" format="yyyy-MM-dd" />
									<ec:column property="countRecord" sortable="false" title="当前库存" />
								</ec:row>
							</ec:table>
						</div>
					</td>
				</tr>
			</table>
			<br>
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();"
										style="cursor: pointer;">
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<s:form id="newForm" name="newForm"
									action="stockTransferOrderInputAction!insertOrderList"   method="post">
									<s:hidden name="firstEntityId" id="firstEntityId"/>
									<s:hidden name="sellOrderListDTO.orderId" id="sellOrderListDTO.orderId"/>
									<s:hidden name="sellOrderListDTO.productId" id="sellOrderListDTO.productId"/>
									<s:hidden name="sellOrderListDTO.cardLayoutId" id="sellOrderListDTO.cardLayoutId"/>
									<s:hidden name="sellOrderListDTO.faceValueType" id="sellOrderListDTO.faceValueType"/>
									
									<table width="100%">
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>卡面：
											</td>
											<td width="35%" align="left" nowrap="nowrap" colspan="3">
												<s:textfield id="cardName" size="20" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												面额类型：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield 
													id="faceValueType" size="20" readonly="true" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.faceValueType
													</s:param>
												</s:fielderror>
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												面额：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.faceValue"
													id="sellOrderListDTO.faceValue" size="20" readonly="true" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.faceValue
													</s:param>
												</s:fielderror>
											</td>
										</tr>
									 <tr>
									 <td align="right" width="15%" nowrap="nowrap">
												有效期：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.validityPeriod"
													id="sellOrderListDTO.validityPeriod" size="20"
													onfocus="dateClick(this)" cssClass="Wdate">
												</s:textfield>
												<s:fielderror>
													<s:param>
														sellOrderListDTO.validityPeriod
													</s:param>
												</s:fielderror>
											</td>
										</tr>
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>张数：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.cardAmount"
													id="sellOrderListDTO.cardAmount" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.cardAmount
													</s:param>
												</s:fielderror>
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												&nbsp;
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td style="width: 110px; text-align: right;">
												卡面图片：
											</td>
											<td colspan="3">
												<img name="cardLayoutImg" id="cardLayoutImg" <s:if test="sellOrderListDTO.cardLayoutId != null">src="${ctx}/product/getCardLayOutImg.action?id=${sellOrderListDTO.cardLayoutId}"</s:if> height="90px"
													width="150px"/>
											</td>
										</tr>
									</table>
									
								</s:form>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div>
			<p align="right">
				<button class='btn' onclick="window.close();"
					style="float: right; margin: 5px">
					返 回
				</button>
				<button class='btn' onclick="this.disabled='disabled';submitForm();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');"
					style="float: right; margin: 5px">
					提 交
				</button>
			</p>
		</div>
	</body>
</html>