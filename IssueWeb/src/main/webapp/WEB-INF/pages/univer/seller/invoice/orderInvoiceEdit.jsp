<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>编辑发票</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
			.x-tree-node-icon {
				display: none;
			}
		</style>
		<script type="text/javascript" charset="UTF-8">
		var isDisplayTableBody = false;
		function displayTableBody() {
			if (isDisplayTableBody) {
				display('serviceTable');
				isDisplayTableBody = false;
			} else {
				undisplay('serviceTable');
				isDisplayTableBody = true;
			}
		}
		
		function sub(){
			//var invoiceState = $("#invoiceState").attr("value");
			//alert(invoiceState);
			var editForm = document.getElementById("newForm");
	        editForm.action='${ctx}/orderInvoice/editInvoice.action';
        	editForm.submit();
		}
 </script>




	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发票管理界面-->编辑</span>
		</div>

		<div id="ContainBox">
<!--	TODO	-->
			<s:form id="newForm" name="newForm" action="orderQueryAction!insertOrderInvoice.action"
				method="post">
				<s:hidden name="orderInvoiceInfoDTO.userId"/> 
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">发票信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														开票序号：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.invoiceId" readonly="true" cssClass="readonly"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>订单号：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.orderId" readonly="true" cssClass="readonly"></s:textfield>
														<br/>
														<s:fielderror>
															<s:param>
																orderInvoiceInfoDTO.orderId
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>总金额：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.totalPrice" readonly="true" cssClass="readonly"></s:textfield>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.totalPrice
														</s:param>
														</s:fielderror>														
													</td>
													
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>客户名称：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.customerName" readonly="true" cssClass="readonly"></s:textfield>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.customerName
														</s:param>
														</s:fielderror>														
													</td>
													
												</tr>
											</table>
										</td>
									</tr>
									
									<tr>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>发票抬头：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.invoiceTitle" maxlength="80"></s:textfield>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.invoiceTitle
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>发票类型：
													</td>
													<td>
														<dl:dictList dictType="182" tagType="2" defaultOption="true"
															displayName="orderInvoiceInfoDTO.invoiceType"
															dictValue="${orderInvoiceInfoDTO.invoiceType}"></dl:dictList>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.invoiceType
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>		
									</tr>
									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>开票项目：
													</td>
													<td>
														<dl:dictList displayName="orderInvoiceInfoDTO.invoiceProj" defaultOption="true"
														dictType="120" tagType="2" dictValue="${orderInvoiceInfoDTO.invoiceProj}"></dl:dictList>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.invoiceProj
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>	
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														客户预计取票日期：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.customerExpectedDate"
															id="orderInvoiceInfoDTO.customerExpectedDate" size="20" onfocus="dateClick(this)"
															cssClass="Wdate"/>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.customerExpectedDate
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>						
									</tr>
									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														取票方式：
													</td>
													<td>
														<dl:dictList displayName="orderInvoiceInfoDTO.sendoutWay" defaultOption="true"
														dictType="121" tagType="2" dictValue="${orderInvoiceInfoDTO.sendoutWay}"></dl:dictList>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.sendoutWay
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>	
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														客户地址：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.invoiceCustomerAddress" maxlength="80"></s:textfield>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.invoiceCustomerAddress
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>						
									</tr>
									
									<tr>
										<td style="text-align: left; width: 100%">
										</td>	
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														开票人：
													</td>
													<td>
														<s:textfield name="orderInvoiceInfoDTO.saleManName" readonly="true" cssClass="readonly"></s:textfield>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.saleManName
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>						
									</tr>
									<tr>
										<td>
											<table style="text-align:left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														备注：
													</td>
													<td>
													    <s:textarea name="orderInvoiceInfoDTO.comment" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="70" rows="5" ></s:textarea>
														<s:fielderror>
														<s:param>
															orderInvoiceInfoDTO.comment
														</s:param>
														</s:fielderror>														
													</td>
												</tr>
											</table>
										</td>	
										<td>
										</td>						
									</tr>
									
			     	</table>
			     	</div>
			     </td>
				</tr>
			</table>
			</s:form>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='${ctx}/orderInvoice/list.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="sub()">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>