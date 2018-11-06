<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<div class="TitleHref">
			<span>不记名卡采购订单详细信息</span>
		</div>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		//function setOrderContact(){
		//var deliveryContactId=document.getElementById("contact").value;
		//var optionInnerHTML="<select name='sellOrderDTO.orderContact' id='sellOrderDTO.orderContact'>";
		//var deliveryPoint=document.getElementById("deliveryPoint").value;
		
		//<s:iterator value="sellerDTO.deliveryPointList" status="status" var="map">
		//	if(deliveryPoint==${map.deliveryId}){
		//		  <s:iterator  value="%{#attr.map.recipientList}" status="status1" var="map1">
		//		  		if(deliveryContactId==${map1.deliveryContactId}){
	    //					optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}' selected>${map1.deliveryContact}</option>";
		//		  		}else {
		//		  			optionInnerHTML=optionInnerHTML+"<option value='${map1.deliveryContactId}'>${map1.deliveryContact}</option>";
		//			  	}
		//		  </s:iterator>
		//	}
		//</s:iterator>
		//optionInnerHTML=optionInnerHTML+"</select>";
		//document.getElementById("orderContact").innerHTML=optionInnerHTML;
		//}
		//function initPage(){
		//var select=document.getElementById("sellOrderDTO.deliveryMeans");
		//deliveryMeansSelect(select);
		//setOrderContact();
		//}
		function accept(){
			if(checkNo()==true){
				viewForm.action = "${actionName}!accept";
				viewForm.submit();
			}else{
		   		return false;
			}   
		}
		
		function acceptAll(){			
			
			viewForm.action = "${actionName}!acceptAll";
			viewForm.submit();
			
		}
		
		
		function acceptBySelect(){			
			var cardNoArray = new Array();
			var count=0;
			for(i = 0; i < document.getElementsByName("orderCardNotReceiveList_checkbox").length; i++) {
				if (document.getElementsByName("orderCardNotReceiveList_checkbox").item(i).checked) {
					var cardNo=document.getElementsByName("orderCardNotReceiveList_checkbox").item(i).value;
					//viewForm.action = "${actionName}!accept?cardNo="+cardNo;
					//viewForm.submit();
					cardNoArray.push(cardNo);
					count++;
					
				}
			}
			if(count==0){
				errorDisplay("请选择至少一条记录操作！");
				return false;
			} 
			document.getElementById("cardNoList").value = cardNoArray;
			viewForm.action = "${actionName}!accept";
			viewForm.submit();
			
		}
		
		
		function delCardList(){
		   acceptForm.action = "${actionName}!deleteAll";
		   acceptForm.submit();
		}
		function deleteOne(){
			var count=0;
			for(i = 0; i < document.getElementsByName("orderListCardPool").length; i++) {
				if (document.getElementsByName("orderListCardPool").item(i).checked) {
					count++;
				}
			}	
			if(count!=1){
				errorDisplay("请选择一条记录操作！");
				return;
			}  
		    acceptForm.action = "${actionName}!delete";
		    acceptForm.submit();
		}
		function checkNo(){
		  var startNo=document.getElementById('startCardNo').value;
		  var endNo=document.getElementById('endCardNo').value;
		  var reg="^[0-9]{13,19}$";
		  if(!startNo.match(reg)||!endNo.match(reg)){
		     errorDisplay("请输入13-19位的数字！");
		     return false;
		  }
		  if(endNo<startNo){
		     errorDisplay("起始卡号不能大于结束卡号！");
		     return false;
		  }
		  return true;
		}
		</script>
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
									<td class="TableTitleFront"  style="cursor: pointer;">
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
												<s:label name="sellOrderDTO.orderId"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.orderDate"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>订单发起者：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.firstEntityId"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
																	订单发起名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.firstEntityName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productName"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>订单处理名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.processEntityName"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>订单类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="sellOrderDTO.orderType"
												dictType="205" dictValue="${sellOrderDTO.orderType}"
												tagType="1"  />
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
										
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											<span style="color: red;">*</span>送货费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.deliveryFee"/>
												<!--<s:textfield name="sellOrderDTO.deliveryFee"
												id="sellOrderDTO.deliveryFee" size="20" maxLength="10" readOnly="true" cssClass="lg_text_gray"/>-->
										</td>
										
										
									</tr>

									<tr id="deliveryMeansTr">
										
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;"></span>收货地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<s:select name="sellOrderDTO.deliveryPoint" 
												id="deliveryPoint" list="sellerDTO.deliveryPointList"
												listKey="deliveryId" listValue="deliveryAddress" onchange="setOrderContact();">
											</s:select>
											</span>  -->
											<s:label name="sellOrderDTO.deliveryPoint"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>收货人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!-- <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<div id="orderContact">
											</div>
											</span> -->
											<s:label name="sellOrderDTO.orderContact"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>发票公司名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.invoiceCompanyName"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>发票地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.invoiceAddresses"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>发票内容名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<edl:entityDictList displayName="sellOrderDTO.invoiceItemId"
												dictType="182" dictValue="${sellOrderDTO.invoiceItemId}"
												tagType="1"  />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>发票日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.invoiceDate"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="sellOrderDTO.paymentTerm"
												dictType="207" dictValue="${sellOrderDTO.paymentTerm}"
												tagType="1"  />
										
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.paymentDelay"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>附加费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.additionalFee"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>折扣费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.discountFee"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.totalPrice"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" >
											<span style="color: red;">*</span>支付状态：
										</td>
										<td>
											<dl:dictList displayName="sellOrderDTO.paymentState"
												dictType="212" dictValue="${sellOrderDTO.paymentState}"
												tagType="1" />
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
									</tr>
									<tr>
									  <td width="15%" align="right" nowrap="nowrap">
									       配送张数：
									  </td>
									  <td width="35%" align="left" nowrap="nowrap">
									        <s:label name="sellOrderDTO.realCardQuantity"/>   
									  </td>
									  <td width="15%" align="right" nowrap="nowrap">
									       接收张数：
									  </td>
									  <td width="35%" align="left" nowrap="nowrap">
									      <s:if test="sellOrderDTO.origCardQuantity==null||sellOrderDTO.origCardQuantity==''">
									      <s:label name="sellOrderDTO.origCardQuantity" value="0"/>
									      </s:if>
									      <s:if test="sellOrderDTO.origCardQuantity!=null">
									        <s:label name="sellOrderDTO.origCardQuantity"/>
									       </s:if>
									  </td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											创建人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.createUser" />
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
											订单状态：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<dl:dictList displayName="sellOrderDTO.orderState"
												dictType="209" dictValue="${sellOrderDTO.orderState}"
												tagType="1" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											订单来源：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
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
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5" readonly="true"></s:textarea>
										</td>
									</tr>
									
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		
		<div style="width: 100%" align=center>
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
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="EditForm" name="EditForm" action="giftSaleOrderAction!edit"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								
									<s:hidden name="sellOrderDTO.firstEntityId"/>
					
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
									<s:hidden  name="buyOrderFlag" />
								<ec:table items="orderlistList" var="map" width="100%" form="EditForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderList">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="cardLayoutName" title="卡面" width="10%" />
										<ec:column property="faceValue" title="面额值" width="10%" />
										<ec:column property="validityPeriod" title="有效期" width="10%" />
										<ec:column property="packageFee" title="包装费" width="10%" />
										<ec:column property="cardAmount" title="张数" />
										<ec:column property="realAmount" title="实际张数" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
			<!-- div id=TableBody -->

			<div style="width: 100%" align=center>
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
										<span class="TableTop">未接收卡信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							
							
							<div id="TableBody">
								<s:form id="viewForm" name="viewForm" action="BuyOrderUnSignAction!edit"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>			
									<s:hidden  name="buyOrderFlag" />
									<s:hidden name="acceptOrderDTO.orderId"></s:hidden>
									<s:hidden name="acceptOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.realCardQuantity"></s:hidden>
									<s:hidden name="sellOrderDTO.origCardQuantity"></s:hidden>
									<s:hidden name="acceptOrderDTO.cardNoList" id="cardNoList"></s:hidden>			
								
								<ec:table items="orderCardNotReceiveList" var="map" width="100%" form="viewForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCardNotReceive">
									<ec:row>
										<ec:column property="null" alias="orderCardNotReceiveList_checkbox" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderCardNotReceiveList_checkbox"
												value="${map.cardNo}"/>
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="cardIssueFee" title="卡费" width="15%" />
									</ec:row>
								</ec:table>
								
								<table width="100%" height="" border="0" cellpadding="0" cellspacing="0"  style="text-align:center">
									<tr height="35">
										<td width="25%" style="border-bottom:1px dashed #BBB;border-top:1px dashed #BBB">
											&nbsp;
										</td>
										<td width="25%" style="border-bottom:1px dashed #BBB;border-top:1px dashed #BBB"">
											<button   class="bt"  onclick="return acceptBySelect();">
								   				接收所选
											</button>
										</td>
										<td width="25%" style="border-bottom:1px dashed #BBB;border-top:1px dashed #BBB"">
											<button    class="bt"  onclick="javascript:acceptAll();">
								   				全部接收
											</button>
										</td>
										<td width="25%" style="border-bottom:1px dashed #BBB;border-top:1px dashed #BBB"">
											&nbsp;
										</td>
									</tr>
									<tr height="35">
										<td width="25%" >
										</td>
										<td width="25%">
											<span>起始卡号：</span>
											<s:textfield name="acceptOrderDTO.startCardNo" id="startCardNo" size="30" maxlength="19"/>
											<s:fielderror>
												<s:param>
													acceptOrderDTO.startCardNo
												</s:param>
											</s:fielderror>
										</td>
										<td width="25%" >
											<span>结束卡号：</span>
											<s:textfield name="acceptOrderDTO.endCardNo" id="endCardNo" size="30" maxlength="19"></s:textfield>
											<s:fielderror>
												<s:param>
													acceptOrderDTO.endCardNo
												</s:param>
											</s:fielderror>
											
										</td>
										
										<td width="25%" align="left">
											<button  class="bt"  onclick="return accept();">
								   				按卡号段接收	
								 			</button>
										</td>
									</tr>
								</table>
								
								<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								
							</table>
								</s:form>
							</div>
							
			</td></tr></table></div>

<div style="width: 100%" align=center>
			<s:form id="acceptForm" name="acceptForm" action="" method="post"> 
			<s:hidden name="acceptOrderDTO.orderId"></s:hidden>
			<s:hidden name="acceptOrderDTO.orderType"></s:hidden>
			<s:hidden name="sellOrderDTO.realCardQuantity"></s:hidden>
			<s:hidden name="sellOrderDTO.origCardQuantity"></s:hidden>
			<s:hidden name="sellOrderDTO.orderId"></s:hidden>
			<s:hidden name="sellOrderDTO.orderType"></s:hidden>
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
										<span class="TableTop">接收卡片明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
								
							
							</table>
							
							<div id="TableBodyAccept">					
								<ec:table items="orderCardReceiveList" var="map" width="100%" form="acceptForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCardReceive">
									<ec:row>
										<ec:column property="null" alias="orderListCardPool" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListCardPool"
												value="${map.startCardNo},${map.endCardNo},${map.cardNum}" />
										</ec:column>
										
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="cardIssueFee" title="卡费" width="15%" />
										
										<ec:column property="null" title="接收状态" width="15%" sortable="false">
										  已接收
										</ec:column>
										
									</ec:row>
								</ec:table>
							</div>
			</td></tr></table>
		<!--  接收后的卡片不允许删除
			<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
											<button class='btn' style="float: right; margin: 7px" 
												onclick="delCardList();">
												全部删除
											</button>
										</div>
									</td>
								</tr>
			</table>
			-->
						
			</s:form>
			</div>


			<!-- div id=TableBody -->
				<%@ include file="orderFlowList.jsp"%>
			<!-- <s:hidden name="sellOrderDTO.orderContact" id="contact"/>-->
</body>
</html>