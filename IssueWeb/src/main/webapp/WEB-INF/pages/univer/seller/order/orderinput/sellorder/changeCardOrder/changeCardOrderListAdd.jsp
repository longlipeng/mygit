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

			function onFaceValueChange(){
				var faceValueId=document.getElementById("faceValueId").value;
				var productType=document.getElementById("productType").value;
				document.getElementById("faceValue").value=faceValueId;
				if(productType=="2"){
					document.getElementById("faceValue").readOnly=false;
					document.getElementById("faceValueType").value="0";
				}else{
					if(faceValueId=="0"){
						document.getElementById("faceValue").readOnly=false;
						document.getElementById("faceValueType").value="1";
					}
				}
				if(faceValueId!="0"){
					document.getElementById("faceValue").readOnly=true;
					document.getElementById("faceValueType").value="0";
				}
				
				
			}
			function submitForm() {
				var productType=document.getElementById("productType").value;
				var cardLayoutId=document.getElementById("cardLayoutId").value;
				var faceValueId=document.getElementById("faceValueId").value;
				var faceValue=document.getElementById("faceValue").value;
				var validityPeriod=document.getElementById("sellOrderListDTO.validityPeriod").value;
				var cardAmount=document.getElementById("sellOrderListDTO.cardAmount").value;
				//var maxBalance=document.getElementById("sellOrderListDTO.getProductDTO().MaxBalance").value;
				//var faceValueParten=/^([0]|([1-9]{1}[0-9]{0,5}))$/;
				var faceValueParten=/^([0]|([1-9]{1}[0-9]{0,9})|([1-9]{1}[0-9]{0,9}\.\d{1,2}))$/;
				var serviceId=document.getElementById("serviceId").value;				
				if(IsSpace(serviceId)){
					errorDisplay("充值账户 必须输入!");
					return;
				}
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
					errorDisplay("请输入格式正确的面额(整数不超过10位，小数不超过2位！)");
					return;
				} 
				//if(faceValue > maxBalance/100){
					//errorDisplay("面额值必须为产品最大余额 "+maxBalance/100+" 之内的数字!");
					//return;
				//}			
				//if(IsSpace(validityPeriod)){
				//	errorDisplay("有效期必须输入!");
				//	return;
				//}
				 if(productType=="2"&&faceValueId!="0"){
						errorDisplay("不记名卡，换卡面额类型只能为0面额！");
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
				document.newForm.action="${ctx}/changeCardOrderAction!insertOrderList";
				document.newForm.submit();
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
			
			function init(){
				selectInit();
				var faceValueId=document.getElementById("faceValueId");
				var products=${jsonString};
				//生成面额下拉列表
						faceValueId.options.length=0;
						var prodFaceValueDTO=products[0].prodFaceValueDTO;
						for(var l=0;l<prodFaceValueDTO.length;l++){
							var opt = document.createElement('option');
							 opt.value = prodFaceValueDTO[l]['faceValue']/100;
							
							 if(prodFaceValueDTO[l]['faceValueType']=="0"){
								opt.innerHTML="固定"+opt.value;
							}
							if(prodFaceValueDTO[l]['faceValueType']=="1"){
								opt.innerHTML="非固定";
							} 
							faceValueId.appendChild(opt);
						}
					onFaceValueChange();
			}
			function changeCardLayout(){
				var cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				
				document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
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
									<ec:column property="productName" sortable="false" title="产品名称"></ec:column>
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
						</td>
					</tr>
				</table>
				<div id="serviceTable">
					<s:form id="newForm" name="newForm"
						action="/changeCardOrderAction!insertOrderList" method="post">
						<s:hidden name="sellOrderDTO.orderId">
						</s:hidden>
						<s:hidden name="orderId" />
						<s:hidden name="sellOrderListDTO.faceValueType" id="faceValueType" />
						<s:hidden name="sellOrderListDTO.callBack" id="productType" />
						<s:hidden id="sellOrderListDTO.currentNowCount" />
						<s:hidden id="sellOrderListDTO.cardAmount" name="sellOrderListDTO.cardAmount" value="1" />
						<s:hidden id="sellOrderListDTO.productId" name="sellOrderListDTO.productId"></s:hidden>
						<table width="100%">
							<tr>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>产品名称：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:label name="sellOrderListDTO.productName" id="productName"></s:label>									
									<s:fielderror>
										<s:param>
											sellOrderListDTO.productId
										</s:param>
									</s:fielderror>
								</td>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>充值账户：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:select name="sellOrderListDTO.serviceId" id="serviceId"
										list="services" listKey="serviceId"
										listValue="serviceName">
									</s:select>
									<s:fielderror>
										<s:param>
											sellOrderListDTO.serviceId
										</s:param>
									</s:fielderror>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>卡面：
								</td>
								<td width="35%" align="left" nowrap="nowrap" colspan="3">
									<s:select name="sellOrderListDTO.cardLayoutId"
										id="cardLayoutId" list="cardLayouts"
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
													onchange="onFaceValueChange();">
												</s:select>

											</td>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>面额：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderListDTO.faceValue" 
										id="faceValue" size="20"/>
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
										id="sellOrderListDTO.validityPeriod" size="20" readonly="true">
									</s:textfield>
									<s:fielderror>
										<s:param>
											sellOrderListDTO.validityPeriod
										</s:param>
									</s:fielderror>
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
