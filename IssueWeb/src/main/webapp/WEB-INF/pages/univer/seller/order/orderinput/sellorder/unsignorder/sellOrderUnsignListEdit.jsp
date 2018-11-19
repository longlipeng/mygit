<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单录入</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
		<script type="text/javascript">	
		Date.prototype.format = function(format)
		{
		    var o =
		    {
		        "M+" : this.getMonth()+1, //month
		        "d+" : this.getDate(),    //day
		        "h+" : this.getHours(),   //hour
		        "m+" : this.getMinutes(), //minute
		        "s+" : this.getSeconds(), //second
		        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		        "S" : this.getMilliseconds() //millisecond
		    }
		    if(/(y+)/.test(format))
		    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
		    for(var k in o)
		    if(new RegExp("("+ k +")").test(format))
		    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		    return format;
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
			function packageChange() {
				<s:iterator value="packages" var="map">	
					if(document.getElementById("sellOrderListDTO.packageId").value==${map.packageId}){
						document.getElementById("sellOrderListDTO.packageFee").value=${map.packageFee};
					}			
			     </s:iterator>
			}

			function submitForm() {
				var cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				var faceValueId=document.getElementById("sellOrderListDTO.faceValueType").value;
				var faceValue=document.getElementById("sellOrderListDTO.faceValue").value;
				var validityPeriod=document.getElementById("sellOrderListDTO.validityPeriod").value;
				var packageFee=document.getElementById("sellOrderListDTO.packageFee").value;
				//var cardIssueFee=document.getElementById("sellOrderListDTO.cardIssueFee").value;
				var cardAmount=document.getElementById("sellOrderListDTO.cardAmount").value;
				var maxBalance=document.getElementById("sellOrderListDTO.getProductDTO().MaxBalance").value;
				var faceValueParten=/^([0]|([1-9]{1}[0-9]{0,5}))$/;

				var selectedDate = document.getElementById("sellOrderListDTO.validityPeriod").value;
				var now = new Date(); 
			 	var nowDate = now.format('yyyy-MM-dd'); 
			 	if(selectedDate!=null){
				Month1=parseInt(selectedDate.split("-")[0],10)*12+parseInt(selectedDate.split("-")[1],10);
			    Month2=parseInt(nowDate.split("-")[0],10)*12+parseInt(nowDate.split("-")[1],10);
			    if(Month1-Month2<36){    
			    	errorDisplay("发卡有效期不能少于36个月！");
				    return ;
				}
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
					errorDisplay("请输入格式正确的面额(6位以内正整数)");
					return;
				}
				if(faceValue > maxBalance/100){
					errorDisplay("面额值必须为产品最大余额 "+maxBalance/100+" 之内大于0的数字!");
					return;
				}	
				//if(IsSpace(validityPeriod)){
				//	errorDisplay("有效期必须输入!");
				//	return;
				//}
				//if(IsSpace(cardIssueFee)){
				//	errorDisplay("卡费必须输入!");
				//	return;
				//}
				//if(!IsBigdecimal(cardIssueFee)){
				//	errorDisplay("卡费 必须为大于0的数字!");
				//	return;
				//}
				/* if(IsSpace(packageFee)){
					errorDisplay("包装费必须输入!");
					return;
				}
				
				if(!IsBigdecimal(packageFee)){
					errorDisplay("包装费 必须为大于0的数字!");
					return;
				}
				if(packageFee<0){
					errorDisplay("包装费 必须为大于0的数字!");
					return;
				} */
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
				cardLayoutId=document.getElementById("sellOrderListDTO.cardLayoutId").value;
				document.getElementById("cardLayoutImg").src="${ctx}/product/getCardLayOutImg.action?id="+cardLayoutId;
			}
			function init(){
			selectInit();
				initFaceValue();					
				packageChange();
				changeCardLayout();				
			}
		</script>
	</head>
	<body  onload="init();">

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>编辑订单明细信息</span>
		</div>
			<br>
			<div id="ContainBox">
		
			<br>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" "
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
									action="sellOrderUnsignAction!updateOrderList" method="post">
									<s:hidden name="sellOrderListDTO.orderListId"/>
									<s:hidden name="sellOrderListDTO.orderId"/>
									<s:hidden id="sellOrderListDTO.getProductDTO().MaxBalance" name="sellOrderListDTO.getProductDTO().MaxBalance"/>
									<input type="hidden" name="sellOrderListDTO.productId" value="${sellOrderListDTO.productDTO.productId}"/>
									<table width="100%">
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>卡面：
											</td>
											<td width="35%" align="left" nowrap="nowrap" colspan="3">
												<s:select name="sellOrderListDTO.cardLayoutId" id="sellOrderListDTO.cardLayoutId"
													list="cardLayouts" listKey="cardLayoutId"
													listValue="cardName" onchange="changeCardLayout();">
												</s:select>
												<s:fielderror>
													<s:param>
														sellOrderListDTO.cardLayoutId
													</s:param>
												</s:fielderror>
											</td>
										</tr>
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												面额类型：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
											
											<s:hidden name="sellOrderListDTO.faceValueType" id="sellOrderListDTO.faceValueType"/>
												
												<s:select name="faceValueId" id="faceValueId"
													list="prodFaceValues" listKey="faceValue"
													listValue="faceValue==0?'非固定':'固定'+(faceValue/100)"
													onchange="faceValueChange();">
												</s:select>
										
												
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												面额：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.faceValue"  readonly='%{sellOrderListDTO.faceValueType==1 ? "false" : "true"}' id="sellOrderListDTO.faceValue" size="20" />
												<s:fielderror>
													<s:param>
														sellOrderListDTO.faceValue
													</s:param>
												</s:fielderror>
											</td>
										</tr>
										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>有效期：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.validityPeriod" id="sellOrderListDTO.validityPeriod"
													size="20" onfocus="dateClick(this)" cssClass="Wdate" >
												</s:textfield>
												<s:fielderror>
													<s:param>
														orderListDTO.validityPeriod
													</s:param>
												</s:fielderror>
											</td>
											<!-- <td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>卡发行费(元)：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.cardIssueFee" id="sellOrderListDTO.cardIssueFee"/>
												<s:fielderror>
													<s:param>
														orderListDTO.cardIssueFee
													</s:param>
												</s:fielderror>
											</td> -->
										</tr>
										
										
										 <tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>包装：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:select name="sellOrderListDTO.packageId"
 														list="packages" listKey="packageId" 
 														listValue="packageName" onchange="packageChange();" id="sellOrderListDTO.packageId"> 
												</s:select> 
											</td>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>包装费(元)：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.packageFee" id="sellOrderListDTO.packageFee"/>
												<s:fielderror>
													<s:param>
														sellOrderListDTO.packageFee
													</s:param>
												</s:fielderror>
											</td>
										</tr> 


										<tr>
											<td align="right" width="15%" nowrap="nowrap">
												<span style="color: red;">*</span>张数：
											</td>
											<td width="35%" align="left" nowrap="nowrap">
												<s:textfield name="sellOrderListDTO.cardAmount" id="sellOrderListDTO.cardAmount"/>
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