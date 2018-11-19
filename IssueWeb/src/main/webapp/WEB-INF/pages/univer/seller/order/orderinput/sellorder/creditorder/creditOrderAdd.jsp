<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html >
	<head>
		<title>添加订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			function loadSalesmanList() {
			setPaymentTerm();
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
			
			function openCustomerPage() {
				var returnValue = window.showModalDialog('${ctx}/customer/chooseCustomerByCusType',
						'_blank',
						'center:yes;dialogHeight:500px;dialogWidth:850px;resizable:no')
				if (returnValue == null || returnValue == undefined)
					return;
				maskDocAll();
				document.getElementById("sellOrderDTO.firstEntityId").value=returnValue;
				EditForm.action = "${ctx}/creditOrderAction!addCreditOrderByCustomer.action";
				EditForm.submit();
			}
			
			function productChange(select) {
				maskDocAll();
				document.getElementById("sellOrderDTO.serviceId").value=null;
				EditForm.action = "creditOrderAction!addCreditOrder";
				EditForm.submit();
			}
			function setPaymentTerm(){
			     paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
			     if(paymentTerm!=4){
			     	document.getElementById("sellOrderDTO.paymentDelay").value="0";
			     	document.getElementById("sellOrderDTO.paymentDelay").readOnly=true;
			     }else{
			        returnValue = window.showModalDialog('${ctx}/user/inputAuthPassword.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:700px;resizable:no');
	    		 	document.getElementById("authFlag").value="1";
	    	        if (returnValue == null || returnValue == undefined)
	    	         {  
	    	            alert("没有进行信用支付权限认证！");
	    	            document.getElementById("authFlag").value="0";
	    	             return;
	    	           }	 
				   <s:if test='#sellOrderDTO.paymentDelay!=null'>
			  		   	document.getElementById("sellOrderDTO.paymentDelay").value=${sellOrderDTO.paymentDelay};
			        </s:if>
			     	document.getElementById("sellOrderDTO.paymentDelay").readOnly=false;
			     }
				
			}
			function acctypeChange(select) {
				maskDocAll();
				EditForm.action = "creditOrderAction!add";
				EditForm.submit();
			}
			
			function orderSubmit(){
					//var value = document.getElementById('sellOrderDTO.serviceFee').value;
					//if (!IsBigdecimal(value)) {
					//	errorDisplay("服务费率请输入数字，且最多四位小数！");
					//	return;
					//}	
					 var paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		             var authFlag=document.getElementById("authFlag").value;
		
					 if(paymentTerm==4 && authFlag==0){
			             errorDisplay("信用支付时 必须进行权限认证!");
			             return;
					}	
					EditForm.action = "creditOrderAction!insert";
					EditForm.submit();
			}

			function onCusBankChange() {
				var chooseBank = document.getElementById("cusBankNames").value;
					<s:iterator value="customerDTO.bankList" var="map">
						if(chooseBank==${map.bankId}){
							document.getElementById("cusBankAccount").value="${map.bankAccount}";
							document.getElementById("cusAccountName").value="${map.bankAccountName}";
						}			
				     </s:iterator>
			}

			function onCusIntoBankChange() {
				var chooseBank = document.getElementById("cusIntoBankNames").value;
					<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
						if(chooseBank==${map.bankId}){
							document.getElementById("cusIntoBankAccount").value="${map.bankAccount}";
							document.getElementById("cusIntoAccountName").value="${map.bankAccountName}";
						}			
				     </s:iterator>
			}

			function bankInit(){
	 			onCusBankChange();
	 			onCusIntoBankChange();
	 			onContactChange();
		 	}
		 	function onContactChange(){
		var chooseBank = document.getElementById("contactName").value;
			<s:iterator value="customerDTO.contractList" var="map">
				if(chooseBank==${map.contactId}){
					document.getElementById("contactMobilePhone").value="${map.contactMobilePhone}";
					
					var selectPapersType=document.getElementById("papersType");
					for(var i=0;i<selectPapersType.options.length;i++){
			        	if(selectPapersType.options[i].value=="${map.papersType}"){
			        		selectPapersType.options[i].selected=true;
			        		break;
			        	}
			        }
			        document.getElementById("papersNo").value="${map.papersNo}";
			        document.getElementById("starValidity").value="${map.starValidity}";
			        document.getElementById("endValidity").value="${map.endValidity}";
			        
				}
		     </s:iterator>
		     
	}
	</script>
	</head>
	<body onload="bankInit();<s:if test="sellOrderDTO.saleMan != null">loadSalesmanList();</s:if>">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理-->添加充值订单基本信息</span>
		</div>
		<form id="EditForm" name="EditForm"
								action="creditOrderAction!insert" method="post">
		<s:hidden name="sellOrderDTO.serviceFee" id="sellOrderDTO.serviceFee" value="0"></s:hidden>
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
									<span class="TableTop">订单信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							
								<s:hidden name="sellOrderDTO.orderType"> </s:hidden>
									<s:hidden name="sellOrderDTO.paymentState"/>
									<s:hidden id="authFlag"/>
								<table width="100%">
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" size="20"
												readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											订单日期：
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId" size="20" id="sellOrderDTO.firstEntityId"
												readonly="true"  cssClass="lg_text_gray"/>
											<input type="button" value="选择客户" onclick="openCustomerPage()" />
										
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerName" size="20"
												readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityName
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:if test="productDTOs != null">
												<s:select id="sellOrderDTO.productId" name="sellOrderDTO.productId" list="productDTOs"
															listKey="productId" listValue="productName" onchange="productChange(this);" >
												</s:select>
											</s:if>
											<s:if test="productDTOs == null">
												<s:select list="#{1:''}" id="productsDTOsIsNull" name="productsDTOsIsNull" />
											</s:if>
											<s:fielderror>
												<s:param>
													sellOrderDTO.productId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											订单处理者：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<span>
											<s:textfield name="sellOrderDTO.processEntityName" readonly="true" /> 
											<s:hidden name="sellOrderDTO.processEntityId"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>充值账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:select name="sellOrderDTO.serviceId" list="services"
												listKey="serviceId" listValue="serviceName"
											 id="sellOrderDTO.serviceId" onchange="acctypeChange(this);">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.serviceId
												</s:param>
										    </s:fielderror>
										</td>										
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select list="#{3:'充值前',4:'信用支付'}" id="sellOrderDTO.paymentTerm" name="sellOrderDTO.paymentTerm" onchange="javascript:setPaymentTerm()"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.paymentTerm
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											付款延期天数：
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
											<span style="color: red;">*</span>预计充值日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.forecastCreditDate"
												size="20"
												onfocus="WdatePicker({minDate:'%y-%M-%d',readOnly:true,
															  dateFmt:'yyyy-MM-dd',
															  realDateFmt:'yyyy-MM-dd',
															  isShowWeek:true,
															  isShowClear:true,
															  isShowToday:true,
															  isShowOthers:true,
															  autoPickDate:true,
															  qsEnabled:true,skin:'ext'})"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.forecastCreditDate
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											充值有效期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.validityPeriod"
												size="20" onfocus="dateClick(this)" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.validityPeriod
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
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											总金额(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice" size="20"
												readonly="true"   cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.totalPrice
												</s:param>
											</s:fielderror>
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
											<s:label name="sellOrderDTO.createTime"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单状态：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.orderState"/>
											<s:if test="sellOrderDTO.orderState == 1">
												草稿
											</s:if>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											订单来源：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.orderSource"/>
											<s:if test="sellOrderDTO.orderSource == 1">
												系统录入
											</s:if>
										</td>
									</tr>
									
								</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="ContainBox" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">订单付款信息</span>
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
							<span style="color: red;">*</span>支付渠道:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<dl:dictList dictType="901" tagType="2" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							支付明细:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="sellOrderDTO.payDetails" size="20" />
							<s:fielderror>
								<s:param>
									sellOrderDTO.payDetails
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							开户银行:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.fromBankId" id="cusBankNames"
									list="customerDTO.bankList" listKey="bankId"
									listValue="bankName" onchange="onCusBankChange()"></s:select>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							账户名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusAccountName" id="cusAccountName"
								size="20" readonly="true" cssClass="lg_text_gray" />
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							银行账号:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusBankAccount" id="cusBankAccount"
								size="20" readonly="true" cssClass="lg_text_gray" />
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人信息:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpName" size="20"  readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									customerDTO.corpName
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人联系电话：
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									customerDTO.corpPhone
								</s:param>
							</s:fielderror>
						</td>
					</tr>	
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
						<edl:entityDictList displayName="customerDTO.corpCredType"
												dictValue="${customerDTO.corpCredType}" dictType="140"
												tagType="1" defaultOption="false" />
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件号:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredId" size="20"  readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							法(个)人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredStaValidity" size="20"  readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
							<tr>
						<td align="right" width="15%" nowrap="nowrap">
							<s:textfield name="contactNameText" id="contactNameText" size='11' readonly="true" cssClass="phone" value="经办人名称:"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.contactId" id="contactName" list="customerDTO.contractList" listKey="contactId" listValue="contactName" onchange="onContactChange()"></s:select>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							<s:textfield name="contactMobilePhoneText" id="contactMobilePhoneText" size='13' readonly="true" cssClass="phone" value="经办人联系电话:"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield id="contactMobilePhone" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							<s:textfield name="operCredTypeText" id="operCredTypeText" size='13' readonly="true"  cssClass="phone" value="经办人证件类型:"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="operCredType"
												dictValue="1"
											    dictType="140"
												tagType="2" defaultOption="false" props="id='papersType' disabled='true'"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							<s:textfield name="papersNoText" id="papersNoText" size='13' readonly="true" cssClass="phone" value="经办人证件号码:"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="papersNo" id="papersNo" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							<s:textfield name="starValidityText" id="starValidityText" size='20' readonly="true" cssClass="phone" value="经办人证件有效期:(起)"></s:textfield>
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="starValidity" id="starValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="EndValidity" id="endValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
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
				<div id="ContainBox" style="width: 100%" align="center">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td colspan="4" width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">收款信息</span>
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
												开户银行:
											</td>
											<td width="15%" align="left" nowrap="nowrap">
												<s:select name="sellOrderDTO.intoBankId" id="cusIntoBankNames"
													list="sellOrderDTO.lstBankDTO" listKey="bankId"
													listValue="bankName" onchange="onCusIntoBankChange()"></s:select>
											</td>
											 
													<td align="right" width="15%" nowrap="nowrap">
														账户名称:
													</td>
													<td width="15%" align="left" nowrap="nowrap">
														<s:textfield name="cusIntoAccountName" id="cusIntoAccountName"
															 size="20" readonly="true" cssClass="lg_text_gray" />
													</td>
													<td align="right" width="15%" nowrap="nowrap">
														银行账号:
													</td>
													<td width="15%" align="left" nowrap="nowrap">
														<s:textfield name="cusIntoBankAccount" id="cusIntoBankAccount"
															  size="20" readonly="true" cssClass="lg_text_gray" />
													</td>  
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			</form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="orderInputAction!list" name="backForm"
				id="backForm">
				<s:hidden name="addType" value=""></s:hidden>
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="this.disabled='disabled';orderSubmit();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				下一步
			</button>

			<div style="clear: both"></div>
		</div>
		<s:form action="creditOrderAction!search" name="searchForm"
			id="searchForm">
			<s:hidden name="customerDTO.customerId" id="customerId" value=""></s:hidden>
		</s:form>
	</body>
</html>