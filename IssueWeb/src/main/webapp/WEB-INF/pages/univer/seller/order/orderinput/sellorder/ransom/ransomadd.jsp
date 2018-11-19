<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加赎回订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		function loadSalesmanList() {
		onOperChange();
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

            applyTo: 'saleUserId',
            forceSelection: true
        });
    }
		function openCustomerPage(){

			var returnValue = window.showModalDialog('${ctx}/customer/chooseCustomerByCusType',
				'_blank',
				'center:yes;dialogHeight:500px;dialogWidth:850px;resizable:no');
		 if (returnValue == null || returnValue == undefined)
			 return;
		maskDocAll();
		document.getElementById("sellOrderDTO.firstEntityId").value=returnValue.split(",")[0];
		document.getElementById("customerName").value=returnValue.split(",")[1];
		
		newForm.action = "ransomOrderAction!add";
		newForm.submit();
		}
		
	function onOperChange(){
		var chooseContact = document.getElementById("contactId").value;
			<s:iterator value="customerDTO.contractList" var="map">
				if(chooseContact==${map.contactId}){
					document.getElementById("operPhone").value="${map.contactTelephone}";
					document.getElementById("operCreType").value="${map.papersType}";
					document.getElementById("operCredId").value="${map.papersNo}";
					document.getElementById("operStarValidity").value="${map.starValidity}";
					document.getElementById("operEndValidity").value="${map.endValidity}";
				}			
		     </s:iterator>
	}
		
		</script>
	</head>
	<body onload="loadSalesmanList()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>赎回订单添加</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="ransomOrderAction!insert" method="post">
			<s:hidden name="sellOrderDTO.orderType"/>
			<s:hidden name="sellOrderDTO.cardQuantity" value="0"/>
			<s:hidden name="sellOrderDTO.paymentState" value="0"/>
			<s:hidden name="sellOrderDTO.serviceFee" id="sellOrderDTO.serviceFee" value="0"></s:hidden>
			<s:hidden name="sellOrderDTO.additionalFee" id="sellOrderDTO.additionalFee" value="0"></s:hidden>
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
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId" size="20" id="sellOrderDTO.firstEntityId"
												readonly="true"  cssClass="lg_text_gray"/>
											<input type="button" value="..." onclick="openCustomerPage()" />
										
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
												id="customerName" readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityName
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
											<span style="color: red;">*</span>营销机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.processEntityId"/>
											<span><s:textfield name="sellOrderDTO.processEntityName"
													readonly="true" cssClass="lg_text_gray" />  </span>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡余额总计（元）：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.origCardTotalAmt" size="20" maxLength="10" cssClass="lg_text_gray" readonly="true"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.origCardTotalAmt
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPriceStr" size="10" readonly="true" cssClass="lg_text_gray"/>
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
											<s:if test="sellOrderDTO.orderSource == 2">
												订单合并
											</s:if>
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
								</table>
							</div>
						</td>
					</tr>
				</table>
				
				
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront"  style="cursor: pointer;">
										<span class="TableTop">订单付款信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable2">
							
							


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
						<s:select list="customerDTO.contractList" name="sellOrderDTO.contactId" onchange="onOperChange()" id="contactId" listKey="contactId" listValue="contactName"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人联系电话:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operPhone"  id="operPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList props="id='operCreType' disabled='true'" displayName="customerDTO.operCredType"
												dictValue="${customerDTO.operCredType}" dictType="140"
												tagType="2" defaultOption="false" />
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件号码:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredId" size="20" id="operCredId" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredStaValidity" id="operStarValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredEndValidity" id="operEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
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
			<s:form id="combackForm" name="combackForm" action="orderInputAction!list.action" 
				 method="post">
				<button class='bt' style="float: right; margin: 7px 5px" type="button"
					onclick="combackForm.submit();">
					取 消
				</button>
			</s:form>
			
			<button class='bt' style="float: right; margin: 7px 5px" type="button"
				onclick="newForm.submit();">
					下一步
			</button>
			<div style="clear: both"></div>
		</div>
			<!-- div id=TableBody -->
	</body>
</html>