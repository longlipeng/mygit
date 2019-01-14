<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html >
	<head>
		<title>编辑充值订单</title>
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
			
			
	function setPaymentTerm() {
		paymentTerm = document.getElementById("sellOrderDTO.paymentTerm").value;
		if (paymentTerm != 4) {  	
		    document.getElementById("sellOrderDTO.paymentDelay").value = "0";
			document.getElementById("sellOrderDTO.paymentDelay").readOnly = true;
		} else {
			document.getElementById("sellOrderDTO.paymentDelay").readOnly = true;		
		}
	}
		
	function updatePaymentTerm(){
		 paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		 oldpaymentTerm=document.getElementById("sellOrderDTO.oldPaymentTerm").value;
		 document.getElementById("authFlag").value="1";	    	        
		 if(paymentTerm!=4){
		  	document.getElementById("sellOrderDTO.paymentDelay").value="0";
		   	document.getElementById("sellOrderDTO.paymentDelay").readOnly=true;
		 }else{			 
			     returnValue = window.showModalDialog('${ctx}/user/inputAuthPassword.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:700px;resizable:no');
	    	 	 if (returnValue == null || returnValue == undefined)
	    	     {  
	    	          alert("没有进行信用支付权限认证！");
	    	          document.getElementById("authFlag").value="0";
	    	          return;
	    	       }
	    	  	document.getElementById("sellOrderDTO.paymentDelay").value="";
			   	document.getElementById("sellOrderDTO.paymentDelay").readOnly=false;			 
		}	
	}
			
			function productChange(select) {
				maskDocAll();
				document.getElementById("customerOrderDTO.accTypeId").value=null;
				document.forms[0].action  = "creditOrderAction!edit";
				document.forms[0].submit();
			}
			function acctypeChange(select) {
				maskDocAll();
				document.forms[0].action  = "creditOrderAction!edit";
				document.forms[0].submit();
			}
			
			function nextBtn() {
					//var value = document.getElementById('sellOrderDTO.serviceFee').value;
					//if (!IsBigdecimal(value)) {
					//	errorDisplay("服务费率请输入数字，且最多四位小数！");
					//	return;
					//}
					var paymentTerm=document.getElementById("sellOrderDTO.paymentTerm").value;
		            var authFlag=document.getElementById("authFlag").value;
					var oldPaymentTerm=document.getElementById("sellOrderDTO.oldPaymentTerm").value;
					if(oldPaymentTerm!=4 && paymentTerm==4 && authFlag==0){
						errorDisplay("信用支付时  必须进行权限认证!");
						return;
					}	
					document.EditForm.action = "creditOrderAction!update";
					document.EditForm.submit();
				}
				
			function formBack(){
				document.EditForm.action = "${ctx}/orderInputAction!list";
				document.EditForm.submit();
			}
			
			function addOrderList(orderId){
				var returnValue = window.showModalDialog('${ctx}/creditOrderAction!addOrderList?sellOrderDTO.productId=${sellOrderDTO.productId}&sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}&sellOrderDTO.processEntityId=${sellOrderDTO.processEntityId}&maxbalance=${maxbalance}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1000px;resizable:no');
				if(returnValue==1){			 
				  	document.forms[0].action = "creditOrderAction!edit";
				  	document.forms[0].submit();
				  }	  
			}
			
			function importFile(){
				var returnValue=window.showModalDialog('${ctx}/creditOrderAction!importFile?sellOrderDTO.orderId=${sellOrderDTO.orderId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1000px;resizable:no');
				if(returnValue==1){			 
				  	document.forms[0].action = "creditOrderAction!edit";
				  	document.forms[0].submit();
				  }	 
			}
			
			function deleteRecord(){
				var flag = true;
				for(i = 0; i < document.getElementsByName("orderCardIdStr").length; i++) {
                    if (document.getElementsByName("orderCardIdStr").item(i).checked) {
                                flag = false;
                        }
                    }   
                if(flag){
                    errorDisplay("请选择一条记录操作！");
                    return;
                }
					document.forms[1].action = "creditOrderAction!deleteOrderList";
				  	document.forms[1].submit();
			}
			function onBankChange() {
				var chooseBank = document.getElementById("bankNames").value;
				<s:iterator value="sellOrderDTO.lstBankDTO" var="map">
					if(chooseBank==${map.bankId}){
						document.getElementById("bankAccount").value="${map.bankAccount}";
					}			
		        </s:iterator>
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
	 			loadSalesmanList();
		 	}
	</script>
	</head>
	<body onload="bankInit();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理-->修改充值订单信息</span>
		</div>
		<form id="EditForm" name="EditForm"
								action="creditOrderAction!insert" method="post">
		<s:hidden name="sellOrderDTO.serviceFee" id="sellOrderDTO.serviceFee"></s:hidden>
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
								<s:hidden id="sellOrderDTO.oldPaymentTerm" name="sellOrderDTO.oldPaymentTerm" />
								<s:hidden id="authFlag"/>
								<table width="100%">
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" size="20"
												readonly="true" cssClass="lg_text_gray"/>
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
												readonly="true" cssClass="lg_text_gray"/>
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
												readonly="true" cssClass="lg_text_gray"/>
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
											<s:textfield name="productName"
												size="20" readonly="true"  cssClass="lg_text_gray" />
											<s:hidden name="sellOrderDTO.productId"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											订单处理者：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<span>
											<s:textfield name="sellOrderDTO.processEntityName" readonly="true" cssClass="lg_text_gray"/> 
											<s:hidden name="sellOrderDTO.processEntityId" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.serviceId" list="services"
												listKey="serviceId" listValue="serviceName"
											 id="sellOrderDTO.serviceId" onchange="acctypeChange(this);" disabled="true">
											</s:select>
											<s:hidden name="sellOrderDTO.serviceId"/>
										</td>										
									</tr>								
								
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											支付节点：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select list="#{3:'充值前',4:'信用支付'}" id="sellOrderDTO.paymentTerm" name="sellOrderDTO.paymentTerm" onchange="javascript:updatePaymentTerm()"/>
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
											<s:label name="sellOrderDTO.totalPrice"/>
										</td>
										
										
										<td width="15%" align="right" nowrap="nowrap">
											总张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
										
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="70" rows="5"></s:textarea>
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
							<s:textfield name="sellOrderDTO.payDetails" maxlength="50" size="20" />
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
							经(代)办人名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operName" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人联系电话:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="customerDTO.operCredType"
												dictValue="${customerDTO.operCredType}" dictType="140"
												tagType="1" defaultOption="false" />
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件号码:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredId" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredStaValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
							<s:fielderror>
								<s:param>
									
								</s:param>
							</s:fielderror>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
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
			<button class='bt' style="float: right; margin: 7px"
				onclick="formBack();">
				返回
			</button>
			<button class='bt' style="float: right; margin: 7px"
				onclick="nextBtn();">
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
								<s:form id="EditListForm" name="EditListForm" action="creditOrderAction!edit"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
								<ec:table items="customerOrderLists" var="map" width="100%" form="EditListForm"
									action="${ctx}/creditOrderAction!edit"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="customerOrderLists">
									<ec:row>
										<ec:column property="null" alias="orderCardIdStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderCardIdStr"
												value="${map.orderCardId}" />
										</ec:column>
										<ec:column property="cardholderName" title="持卡人姓名" width="15%" />
										<ec:column property="externalId" title="持卡人工号" width="15%" />
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="creditAmount" title="充值金额(元)" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<div id="buttonCRUD" style="text-align: right; width: 100%">
													<div id="deleteBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="deleteRecord();">
														删除
													</div>					
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addOrderList();">
														添加
													</div>
													<div id="importBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="importFile();">
														导入
													</div>  
													
												</div>
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>

							</div>
							<!-- div id=TableBody -->
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>