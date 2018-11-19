<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单录入</title>
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
					
		    function clickRow(cardLayoutId,faceValueType,faceValue,cardValidDate,countRecord){
			    
		    	document.getElementById("sellOrderListDTO.validityPeriod").value=cardValidDate;
		    	document.getElementById("sellOrderListDTO.cardLayoutId").value=cardLayoutId;
		    	document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
		    	document.getElementById("sellOrderListDTO.faceValueType").value=faceValueType;
		    	document.getElementById("sellOrderListDTO.currentNowCount").value=countRecord;
		    	document.getElementById("faceValueId").value=faceValue*100;
		    	document.getElementById("sellOrderListDTO.faceValue").value=faceValue;
		    }
			function faceValueChange() {
				<s:iterator value="prodFaceValues" var="map">
					<s:if test="#map.faceValueType  eq 1">
						if(document.getElementById("faceValueId").value==${map.faceValue}){
							document.getElementById("sellOrderListDTO.faceValue").value=0;
							document.getElementById("sellOrderListDTO.faceValue").readOnly=false;
							document.getElementById("sellOrderListDTO.faceValueType").value =1;
						}	
					</s:if>
					<s:if test="#map.faceValueType eq 0">
						if(document.getElementById("faceValueId").value==${map.faceValue}){
							document.getElementById("sellOrderListDTO.faceValue").value=${map.faceValue/100};
							document.getElementById("sellOrderListDTO.faceValue").readOnly=true;
							document.getElementById("sellOrderListDTO.faceValueType").value =0;
						}	
					</s:if>
				</s:iterator>	
				selectInit();				
			}
			function selectInit(){
		var select = document.getElementById("faceValueId");
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
			//function packageChange() {
				//<s:iterator value="packages" var="map">	
					//if(document.getElementById("sellOrderListDTO.packageId").value==${map.packageId}){
						//document.getElementById("sellOrderListDTO.packageFee").value=${map.packageFee};
					//}			
			     //</s:iterator>
			//}
			
			function submitForm() {
			
				var cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				var faceValueId=document.getElementById("faceValueId").value;
				var faceValue=document.getElementById("sellOrderListDTO.faceValue").value;
				var validityPeriod=document.getElementById("sellOrderListDTO.validityPeriod").value;
				//var packageFee=document.getElementById("sellOrderListDTO.packageFee").value;
				//var cardIssueFee=document.getElementById("sellOrderListDTO.cardIssueFee").value;
				var cardAmount=document.getElementById("sellOrderListDTO.cardAmount").value;
				var maxBalance=document.getElementById("sellOrderListDTO.getProductDTO().MaxBalance").value;
				var faceValueParten=/^([0]|([1-9]{1}[0-9]{0,5}))$/;
				if(IsSpace(cardLayoutId)){
					errorDisplay("卡面 必须输入!");
					return;
				}
				if(IsSpace(faceValueId)){
					errorDisplay("面额类型 必须输入!");
					return;
				}
				if(IsSpace(faceValue)){
					errorDisplay("面额必须输入!");
					return;
				}
				if(!faceValueParten.exec(faceValue)){
					errorDisplay("请输入格式正确的面额(6位以内正整数)");
					return;
				}
				if(faceValue > maxBalance/100){
					errorDisplay("面额值必须为产品最大余额 "+maxBalance/100+" 之内的数字!");
					return;
				}			
				
				//if(IsSpace(validityPeriod)){
				//	errorDisplay("有效期必须输入!");
				//	return;
				//}
			//	if(IsSpace(packageFee)){
			//	errorDisplay("包装费必须输入!");
				//	return;
			//	}
				
				//if(!IsBigdecimal(packageFee)){
				//	errorDisplay("包装费 必须为大于0的数字!");
					//return;
				//}
			//	if(packageFee<0){
				//	errorDisplay("包装费 必须为大于0的数字!");
			//		return;
				//}
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
				faceValueChange();					
				//packageChange();
				changeCardLayout();				
			}
		</script>
	</head>
	<body onload="init();">

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>添加订单明细信息</span>
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
										<input type="radio" name="ec_choose" value="${map.orderId}"
											onclick="clickRow('${map.cardLayoutId}','${map.faceValueType}','${map.faceValue}','${map.cardValidDate}','${map.countRecord}');" />
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
									action="sellOrderRetailUnsignAction!insertOrderList" method="post">
									<s:hidden name="sellOrderDTO.orderId">
									</s:hidden>
									<s:hidden name="orderId" />
									<s:hidden name="sellOrderListDTO.faceValueType" id="sellOrderListDTO.faceValueType" />
									<s:hidden id="sellOrderListDTO.currentNowCount" />
									<s:hidden id="sellOrderListDTO.getProductDTO().MaxBalance" name="sellOrderListDTO.getProductDTO().MaxBalance"/>							
									<table width="100%">
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>卡面：
											</td>
											<td width="35%" align="left" nowrap="nowrap" colspan="3">
												<s:select name="sellOrderListDTO.cardLayoutId"
													id="sellOrderListDTO.cardLayoutId" list="cardLayouts"
													listKey="cardLayoutId" listValue="cardName" onchange="changeCardLayout();">
												</s:select>
												<s:fielderror>
													<s:param>
														orderListDTO.cardLayoutId
													</s:param>
												</s:fielderror>
											</td>
										</tr>
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>面额类型：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:select name="faceValueId" id="faceValueId"
													list="prodFaceValues" listKey="faceValue"
													listValue="faceValue==0?'非固定':'固定'+faceValue/100"
													onchange="faceValueChange();">
												</s:select>
												
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>面额：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.faceValue"
													id="sellOrderListDTO.faceValue" size="20"/>
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
											<!--<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>卡发行费(元)：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.cardIssueFee"
													id="sellOrderListDTO.cardIssueFee" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.cardIssueFee
													</s:param>
												</s:fielderror>
											</td>  -->
										</tr>
									<!-- 	<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>包装：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:select name="sellOrderListDTO.packageId"
													list="packages" listKey="packageId" listValue="packageName"
													onchange="packageChange();"
													id="sellOrderListDTO.packageId">
												</s:select>
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>包装费(元)：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.packageFee"
													id="sellOrderListDTO.packageFee" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.packageFee
													</s:param>
												</s:fielderror>
											</td>
										</tr> -->
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
												<img name="cardLayoutImg" id="cardLayoutImg" src="${ctx}/product/getCardLayOutImg.action?id=${sellOrderListDTO.cardLayoutId}" height="90px"
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