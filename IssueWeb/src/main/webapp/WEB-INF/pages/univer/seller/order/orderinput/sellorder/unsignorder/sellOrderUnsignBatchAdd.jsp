<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加库存订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
   
	function productChange(select) {
		
		maskDocAll();
		newForm.action = "orderQueryAction!unsignInit.action";
		newForm.submit();
	}
	
	
	function openCustomerPage() {
		var returnValue=null;
			returnValue = window.showModalDialog('${ctx}/customer/chooseCustomer.action',
					'_blank',
					'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
			if (returnValue == null || returnValue == undefined)
				return;
			maskDocAll();
			document.getElementById("sellOrderDTO.firstEntityId").value=returnValue;
			document.getElementById("sellOrderDTO.productId").value='';
			newForm.action = "orderQueryAction!unsignInit.action";
			newForm.submit(); 
	 		
			
		}
	
	
	function sub(){
		var flag = true;
			for(i = 0; i < document.getElementsByName("orderListStr").length; i++) {
				var param = document.getElementsByName("orderListStr").length;
			if (document.getElementsByName("orderListStr").item(i).checked) {
					flag = false;
				}
			}	
		if(flag){
			errorDisplay("请选择至少一条记录操作！");
			return;
		}
		var msg=null
		var isTrue=true;
		var count=0;
		$(".orderListStr").each(function(){
			
			if($(this).attr('checked')){
				var valInfo=$(this).parent().parent().find('td:eq(0)').children().val();
				var countRecord=$(this).parent().parent().find('td:eq(4)').text();
				var cardNum=$(this).parent().parent().find('td:eq(5)').children().val();
				var faceValue=$(this).parent().parent().find('td:eq(3)').text();
				var cardName=$(this).parent().parent().find('td:eq(1)').text();
				var r =/^\+?[1-9][0-9]*$/;
				if(!r.test(cardNum)){  
					errorDisplay("请输入非零正整数!");  
			        isTrue=false
			        return;
			    }  
				if(parseInt(cardNum)>parseInt(countRecord)){
					errorDisplay(cardName+"，面额值为："+faceValue+"的卡库存不足！");
					isTrue=false;
					return;
				}
				count=parseInt(count)+parseInt(cardNum);
				if(msg==null){
					msg="面额："+faceValue+","+"售卡张数:"+cardNum+"张。  "
				}else{
					msg+="面额："+faceValue+","+"售卡张数:"+cardNum+"张。  "
				}
				
				
			}
		
		
	})
		if(isTrue){
			
			if(count>500){
					errorDisplay("售卡总数不能超过500张;");
					return;
			}
			confirm("售卡确认："+msg,submit);
		}
	}
	
	function submit(){
		$(".orderListStr").each(function(){
			
			if($(this).attr('checked')){
				var valInfo=$(this).parent().parent().find('td:eq(0)').children().val();
				var cardNum=$(this).parent().parent().find('td:eq(5)').children().val();
				$(this).parent().parent().find('td:eq(0)').children().val(valInfo+cardNum);
				
			}
		
		})
		maskDocAll();
		newForm.action="customer/batchImportUnsignOrder.action";
		document.newForm.submit();
	}
	
	
	
	function cleanInfo(){
$(".orderListStr").each(function(){
	$(this).attr("checked", false); 
	$(this).parent().parent().find('td:eq(5)').children().val("");
	})
	} 
</script>
	</head>
	<body >
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加库存订单基本信息</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post" >
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId"
												id="sellOrderDTO.firstEntityId" size="20" readonly="true" />
											<input type="button" value="选择客户" onclick="openCustomerPage();"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
														客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerName"
												id="customerName" size="20" readonly="true"  cssClass="lg_text_gray" />
										</td>
									</tr>
									<%-- <tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>姓	名：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20"  />
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>电		话：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
														证件类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:select id ="orderType" name="sellOrderQueryDTO.orderTypeArray" list="#{'1':'身份证','2':'护照','3':'其他'}"></s:select>
											<s:fielderror>
												<s:param>customerDTO.corpCredType</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>证件号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20"  />
											<s:fielderror>
											<s:param>
												sellOrderDTO.validityPeriod
											</s:param>
											</s:fielderror>
										</td>
										</td>
									</tr><tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>住址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.processEntityName"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.productId
												</s:param>
											</s:fielderror>
										</td> 
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>邮箱地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.processEntityId"/>
											<span><s:textfield name="sellOrderDTO.processEntityName"
													/>  </span>
										</td>
									</tr><tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>渠道：
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>邮编：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield  name="sellOrderDTO.cardIssueFee"
												id="sellOrderDTO.cardIssueFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardIssueFee
												</s:param>
											</s:fielderror>
										</td>
									</tr><tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>客户合同快递费：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>客户合同失效时间：
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
									</tr> --%>
									
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.productId" id="sellOrderDTO.productId"
												list="productDTOs" listKey="productId"
												listValue="productName" onchange="productChange(this);">
											</s:select>
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
											<s:textfield name="sellOrderDTO.serviceName"
												id="serviceName" size="20" readonly="true"  cssClass="lg_text_gray" />
												<s:hidden  name="sellOrderDTO.serviceId" id="sellOrderDTO.serviceId"></s:hidden>
												<s:hidden  name="sellOrderDTO.defaultEntityId" id="sellOrderDTO.defaultEntityId"></s:hidden>
												<s:hidden  name="sellOrderDTO.packageId" id="sellOrderDTO.packageId"></s:hidden>
												
										</td>
										
									</tr>
									
								<%-- 	<tr>
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
											<span style="color: red;">*</span>包装：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:select name="sellOrderDTO.packageId"
												id="packageId" list="packages" listKey="packageId"
												listValue="packageName">
											</s:select>
											<s:fielderror>
											<s:param>
												sellOrderDTO.packageId
											</s:param>
											</s:fielderror>
										</td>	
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>面额类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
											<s:select name="faceValueId" id="faceValueType" list="prodFaceValues"
												listKey="faceValueId" listValue="faceValueType==1?'非固定':'固定'"
												onchange="faceValueChange();" >	
											</s:select>-->
											<s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
												
												<s:select name="faceValueId" id="faceValueId"
													list="prodFaceValues" listKey="faceValue"
													listValue="faceValue==0?'非固定':'固定  '+(faceValue/100)"
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
										
											<s:textfield name="sellOrderDTO.faceValue"  readonly="true" id="sellOrderDTO.faceValue" size="20" />
												<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
									</tr> --%>
									
								</table>
							</div>
						</td>
					</tr>
				</table>
				<div id="TableBody">
							<ec:table items="list" var="map" width="100%"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								 autoIncludeParameters="false"
								showPagination="false" showStatusBar="false">
								<ec:row>
									<ec:column property="null" title="选择" width="10%"
										sortable="false" viewsAllowed="html">
										<input type="checkbox" name="orderListStr" id="orderListStr" class="orderListStr"
													value="${map.faceValue},${map.cardLayoutId}," />
										<%-- <input type="radio" name="ec_choose" value="${map.orderId}"
											onclick="clickRow('${map.cardLayoutId}','${map.faceValueType}','${map.faceValue}','${map.cardValidDate}','${map.countRecord}');" /> --%>
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
									<ec:column property="countRecord" sortable="false" title="当前库存" />
									<ec:column property="cardAmount" sortable="false" title="售卡张数" width="15%">
										<s:textfield name="cardNum" id="cardNum" size="8" maxlength="5"/>
									</ec:column>
								</ec:row>
							</ec:table>
						</div>
			</div>
			
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' style="float: right; margin: 7px"
					onclick="confirm('确认清空吗？',cleanInfo);">
					清空
				</button>
			<button class='bt' style="float: right; margin: 7px"
				onclick="sub();">
				确 定
			</button>
			<div style="clear: both"></div>
		</div>
		
		
		
	</body>
</html>