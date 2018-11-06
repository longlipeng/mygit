<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单编辑</title>
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
			}
			function initFaceValue() {
				/*非固定*/
				if(${sellOrderListDTO.faceValueType}==1){
					document.getElementById("sellOrderListDTO.faceValueType").value =1;
					document.getElementById("faceValueId").value=0;
					document.getElementById("sellOrderListDTO.faceValue").value=${sellOrderListDTO.faceValue};
				}
				/*固定面额*/
				if(${sellOrderListDTO.faceValueType}==0){
					document.getElementById("sellOrderListDTO.faceValueType").value =0;
					<s:iterator value="prodFaceValues" var="map">
						if(${map.faceValue}==${sellOrderListDTO.faceValue}){
							document.getElementById("faceValueId").value=${sellOrderListDTO.faceValue};
						}
					</s:iterator>
				}
			}
				function changeCardLayout(){
				cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
			}
			function init(){
			selectInit();
				initFaceValue();					
				changeCardLayout();				
			}
			function submitForm() {
			
				var cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				var faceValueId=document.getElementById("faceValueId").value;
				var faceValue=document.getElementById("sellOrderListDTO.faceValue").value;
				var validityPeriod=document.getElementById("sellOrderListDTO.validityPeriod").value;
				var cardAmount=document.getElementById("sellOrderListDTO.cardAmount").value;
				//var maxBalance=document.getElementById("sellOrderListDTO.getProductDTO().MaxBalance").value;
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
				//if(faceValue > maxBalance/100){
					//errorDisplay("面额值必须为产品最大余额 "+maxBalance/100+" 之内的数字!");
					//return;
				//}			
				//if(IsSpace(validityPeriod)){
				//	errorDisplay("有效期必须输入!");
				//	return;
				//}
				if(!IsInteger(cardAmount)){
					errorDisplay("张数必须为正整数!");
					return;
				}
				if(cardAmount<=0){
					errorDisplay("张数必须为正整数!");
					return;
				}
				document.newForm.action="${ctx}/changeCardOrderAction!updateOrderList";
				document.newForm.submit();
			}
		</script>
	</head>

	<body  onload="init();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑订单明细信息</span>
		</div>
		<div style="width: 100%" align=center>
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
						action="changeCardOrderAction!updateOrderList" method="post">
						<s:hidden name="sellOrderDTO.orderId"/>
						<s:hidden name="sellOrderListDTO.orderListId"/>
						<s:hidden name="sellOrderListDTO.orderId"/>
						<s:hidden name="orderId" />
						<s:hidden name="sellOrderListDTO.faceValueType" id="sellOrderListDTO.faceValueType" />
						<s:hidden id="sellOrderListDTO.currentNowCount" />
						<table width="100%">
							<!-- <tr>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>产品名称：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderListDTO.productName"></s:textfield>
									<input type="hidden" name="sellOrderListDTO.productId"/>
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
										list="prodFaceValues" listKey="serviceId"
										listValue="serviceName"
										onchange="serviceChange();">
									</s:select>
									<s:fielderror>
										<s:param>
											sellOrderListDTO.serviceId
										</s:param>
									</s:fielderror>
								</td>
							</tr> -->
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
								<fmt:formatNumber value="${faceValue/100}" pattern="#" type="number"/> 
								
								<td width="35%" align="left" nowrap="nowrap">
									<s:select name="faceValueId" id="faceValueId"
										list="prodFaceValues" listKey="faceValue"
										listValue="faceValue==0?'非固定':'固定'+(faceValue/100) "
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
			</div>
		</div>
		<div>
			<p align="right">
				<button class='btn' onclick="window.close();" style="float: right; margin: 5px">
					返 回
				</button>
				<button class='btn' onclick="submitForm();" style="float: right; margin: 5px">
					提 交
				</button>
			</p>
		</div>
	</body>
</html>
