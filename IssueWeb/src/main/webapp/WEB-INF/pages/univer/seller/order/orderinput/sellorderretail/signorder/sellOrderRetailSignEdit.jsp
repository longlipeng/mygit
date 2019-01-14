<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑销售记名订单（散户）</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		function loadSalesmanList() {
			packageLoad();
        var salesmanList = ${saleUserList};
        var store = new Ext.data.JsonStore({
            data: salesmanList,
            autoLoad: true,
            fields: [{name: 'id', mapping: 'userId'}, {name: 'name', mapping: 'userName'}]
        });
        var combo = new Ext.form.ComboBox({
        	width:120,
            store: store,
            displayField:'name',
            hiddenName: 'sellOrderDTO.saleMan',
            valueField: 'id',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText: '请选择销售人员',
            selectOnFocus: true,
            applyTo: 'saleUserId',
            forceSelection: true
        });
    }
		
   
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
	//function setPaymentTerm() {
	//	paymentTerm = document.getElementById("sellOrderDTO.paymentTerm").value;
	//	if (paymentTerm != 4) {
	//		document.getElementById("sellOrderDTO.paymentDelay").value = "0";
	//		document.getElementById("sellOrderDTO.paymentDelay").readOnly = true;
	//	} else {
	//		document.getElementById("sellOrderDTO.paymentDelay").value = "";
	//		document.getElementById("sellOrderDTO.paymentDelay").readOnly = false;
	//	}
	//}

	function openCustomerPage() {
		var returnValue = window.showModalDialog('${ctx}/customer/chooseCustomer',
				'_blank',
				'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no')
		if (returnValue == null || returnValue == undefined)
			return;
		maskDocAll();
		document.getElementById("sellOrderDTO.firstEntityId").value=returnValue;
		newForm.action = "sellOrderSignAction!add";
		newForm.submit();
	}
	function packageLoad() {
	<s:iterator value="packages" var="map">
		if(document.getElementById("sellOrderDTO.packageId").value==${map.packageId}){
			document.getElementById("sellOrderDTO.packageFee").value=${sellOrderDTO.packageFee};
		}			
     </s:iterator>
	}
	function packageChange() {
		<s:iterator value="packages" var="map">	
			if(document.getElementById("sellOrderDTO.packageId").value==${map.packageId}){
				document.getElementById("sellOrderDTO.packageFee").value=${map.packageFee};
			}			
	     </s:iterator>
	}
	
		function setPaymentTerm(){
		 paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		 oldpaymentTerm=document.getElementById("sellOrderDTO.oldPaymentTerm").value;
		 document.getElementById("authFlag").value="1";	    	        
		 if(paymentTerm!=4){
		  	document.getElementById("sellOrderDTO.paymentDelay").value="0";
		   	document.getElementById("sellOrderDTO.paymentDelay").readOnly=true;
		 }else{
			 if(oldpaymentTerm!=4)
			 {
			     returnValue = window.showModalDialog('${ctx}/user/inputAuthPassword.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:700px;resizable:no');
	    	 	 if (returnValue == null || returnValue == undefined)
	    	     {  
	    	          alert("没有进行信用支付权限认证！");
	    	          document.getElementById("authFlag").value="0";
	    	          return;
	    	       }
			  }			        
			 document.getElementById("sellOrderDTO.paymentDelay").value="";
			 document.getElementById("sellOrderDTO.paymentDelay").readOnly=false;
		}	
	}
	
	function productChange(select) {
		maskDocAll();
		newForm.action = "reloadableCardAction!add";
		newForm.submit();
	}

	
	function orderSubmit(){ 	
	var cruProdMaxBalance=document.getElementById("currentProdMaxBalance").value;
		var printFaceValue=document.getElementById("crutfaceValue").value;
		var doublePatrn = /^(([1-9]{1}[0-9]{0,13})|([0]))$/;
		if((!isNullOrEmpty(printFaceValue))&&(!doublePatrn.exec(printFaceValue))){
			errorDisplay("第一次充值金额输入格式错误,必须是正数(两位小数),无则填写0!");
			return;
		}
		if(parseFloat(printFaceValue)>parseFloat(cruProdMaxBalance)){
			errorDisplay("第一次充值金额不能大于产品的最大余额:"+cruProdMaxBalance);
			return;
		}
		
		var authFlag=document.getElementById("authFlag").value;
		var paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		var oldPaymentTerm=document.getElementById("sellOrderDTO.oldPaymentTerm").value;
		if(oldPaymentTerm!=4 && paymentTerm==4 && authFlag==0){
			errorDisplay("信用支付时  必须进行权限认证!");
			return;
		}
		var additionalFee=document.getElementById("sellOrderDTO.additionalFee").value;
		if(IsSpace(additionalFee)){
			errorDisplay("附加费必须输入，无请填写0!");
			return;
		}
		if(!IsBigdecimal(additionalFee)){
			errorDisplay("附加费必须为正数!");
			return;
		}
		if(additionalFee<0){
			errorDisplay("附加费必须为正数!");
			return;
		}		
		newForm.action = "sellOrderRetailSignAction!update";
		newForm.submit();
	}
	
	 function addOrderList(orderId){
			var returnValue = window.showModalDialog('${ctx}/sellOrderRetailSignAction!addOrderList?sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			//if(returnValue==1){			 
			  	document.forms[0].action = "sellOrderRetailSignAction!edit";
			  	document.forms[0].submit();
			//  }	  
		}
		
		function deleteRecord(){
			var flag = true;
			for(i = 0; i < document.getElementsByName("orderListStr").length; i++) {
	                  if (document.getElementsByName("orderListStr").item(i).checked) {
	                              flag = false;
	                              break;
	                      }
	                  }   
	              if(flag){
	                  errorDisplay("请选择一条记录");
	                  return;
	              }
	              confirm("确定要删除吗?",delOpr);
		}


		function delOpr(){
			document.EditForm.action = "sellOrderRetailSignAction!deleteOrderList";
		  	document.EditForm.submit();			
		}
		
		function importFile(orderId){
			window.open('${ctx}/reloadableCardAction!importFile.action?orderId='+ orderId,'_self');
		}
		function isNullOrEmpty(oValue){
  		return oValue==null||oValue=="";
  	}	
</script>
	</head>
	<body onload="loadSalesmanList();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>编辑销售记名订单(散户)</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name="sellOrderDTO.firstEntityId"/>	
			<s:hidden name="sellOrderDTO.oldPaymentTerm" />		
			<s:hidden name="errorjsp" />		
			<s:hidden id="authFlag" />
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
											<s:textfield name="productName"
												size="20" readonly="true"  cssClass="lg_text_gray" />
											<s:hidden name="sellOrderDTO.productId"/>
											<input type="hidden" id="currentProdMaxBalance" name="currentProdMaxBalance" value="${currentProdMaxBalance}"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<span><s:textfield name="sellOrderDTO.processEntityName"
													readonly="true" cssClass="lg_text_gray" />  </span>
										</td>
									</tr>
									<tr>
										 <s:hidden name="sellOrderDTO.faceValueType" value="1"/>
										<td width="15%" align="right">
											<span style="color: red;">*</span>第一次充值金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.faceValue"
												id="crutfaceValue" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
										<s:hidden name="sellOrderDTO.validityPeriod"></s:hidden>
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
									</tr>
									

									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>包装：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.packageId" list="packages"
												listKey="packageId" listValue="packageName"
												onchange="packageChange();" id="sellOrderDTO.packageId">
											</s:select>
										</td>
										<td width="15%" align="right">
											<span style="color: red;">*</span>包装费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.packageFee"
												id="sellOrderDTO.packageFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.packageFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
									 <td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>充值账户：
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
											<span style="color: red;">*</span>卡费(元)：
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
									</tr>

									<tr>
										
									
								<%--		<s:fielderror>
												<s:param>
										sellOrderDTO.cardIssueFeeStr
									</s:param>
											</s:fielderror> --%>	
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.annualFee"
												id="annualFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.annualFee
												</s:param>
											</s:fielderror>
										</td>										
									</tr>									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<dl:dictList displayName="sellOrderDTO.paymentTerm"
												dictType="207" dictValue="${sellOrderDTO.paymentTerm}"
											tagType="2"  props="onchange=javascript:setPaymentTerm()"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.paymentTerm
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>付款延期天数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.paymentDelay"
												id="sellOrderDTO.paymentDelay" size="20" maxLength="5"
												readonly='%{sellOrderDTO.paymentTerm==4 ? "false" : "true"}' />
											<s:fielderror>
												<s:param>
													sellOrderDTO.paymentDelay
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>附加费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.additionalFee"
												id="sellOrderDTO.additionalFee" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.additionalFee
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>折扣费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.discountFee"
												 size="20" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.discountFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
											<td align="right" width="15%" >
											<span style="color: red;">*</span>销售人员：
										</td>
										<td>
											<s:textfield name="sellOrderDTO.saleMan" id="saleUserId"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.saleMan
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												 size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										
										 <td align="right" width="15%" nowrap="nowrap">
											张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.cardQuantity"
											size="10" readonly="true" cssClass="lg_text_gray"/>
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
											创建日期：</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.createTime"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单状态：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:if test="sellOrderDTO.orderState == 1">
												草稿
											</s:if>
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
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5"></s:textarea>
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
			<s:form action="orderInputAction!list" name="backForm"
				id="backForm">
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
					保存
			</button>
			<div style="clear: both"></div>
		</div>
		
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
								<ec:table items="customerOrderLists" var="map" width="100%" form="EditForm"
									action="${ctx}/sellOrderRetailSignAction!edit"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="customerOrderLists">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderCardId}" />
										</ec:column>
										<ec:column property="cardholderName" title="持卡人姓名" width="15%" />
										<ec:column property="externalId" title="持卡人工号" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												align="center" >
												<div id="buttonCRUD" style="text-align: right; width: 100%">
													<div id="deleteBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="deleteRecord();">
														删除
													</div>					
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addOrderList(${sellOrderDTO.orderId});">
														添加
														
													</div>
												<!-- 	
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="importFile(${sellOrderDTO.orderId});">
														导入
													</div>
													 -->
												</div>
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>

							</div>
							<s:hidden name="sellOrderDTO.orderContact" id="contact"/>
			<!-- div id=TableBody -->
	</body>
</html>