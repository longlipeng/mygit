<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
<title>编辑客户发票基本要素</title>
<script type="text/javascript">
	function pickInvoiceItem() {
		var obj = document.getElementById('invoiceItem');
		var url = '${ctx}/customerInvoice/pickItem.action?invoiceItem='
				+ obj.value;
		var sels = window.showModalDialog(url, '_blank',
				'center:yes;dialogHeight:600px;dialogWidth:800px');
		if (sels != null) {
			if (sels.length > 0) {
				obj.value = convertArray(sels);
			} else {
				obj.value = '';
			}
		}
	}
	function sub() {
		var form = Ext.get('customerInvoiceForm').dom;
		form.submit();
	}
	function convertArray(array) {
		var ret = '';
		for ( var i = 0; i < array.length; i++) {
			ret = ret + array[i] + ',';
		}
		return ret.substring(0, ret.length - 1);
	}
</script>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>编辑客户发票基本要素</span>
	</div>

	<s:form id="customerInvoiceForm" name="customerInvoiceForm"
		action="/customerInvoice/modify.action" method="post">
		<s:hidden name="customerInvoiceInfoDTO.id" />
		<s:hidden name="customerInvoiceInfoDTO.customerEntityId" />
		<s:hidden name="customerInvoiceInfoDTO.customerName" />
		<div id="customerInvoiceBase"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="customerInvoiceBaseTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront"><span class="TableTop">发票基本要素</span>
						</td>
						<td class="TableTitleEnd">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div id="customerInvoiceBaseTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">客户编码：</td>
									<td><s:label>
											<s:label name="customerInvoiceInfoDTO.customerEntityId" />
										</s:label></td>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">客户名称：</td>
									<td><s:label>
											<s:label name="customerInvoiceInfoDTO.customerName" />
										</s:label></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">公司名称：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.companyName" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.companyName</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">税号：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.taxCode" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.taxCode</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">银行账号：</td>
									<td><s:textfield
											name="customerInvoiceInfoDTO.bankAcctCode" /> <s:fielderror>
											<s:param>customerInvoiceInfoDTO.bankAcctCode</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">地址电话：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.addressTel" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.addressTel</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">开票项目：</td>
									<td><s:textfield id="invoiceItem"
											name="customerInvoiceInfoDTO.invoiceItem" cssClass="watch"
											readonly="true" onclick="pickInvoiceItem()" /> <s:fielderror>
											<s:param>customerInvoiceInfoDTO.invoiceItem</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">发票种类：</td>
									<td><s:select list="#{'1':'增值税专用发票','2':'地税票' }"
											headerKey="" headerValue="-请选择-"
											name="customerInvoiceInfoDTO.invoiceType" /> <s:fielderror>
											<s:param>customerInvoiceInfoDTO.invoiceType</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">收票人：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.takerName" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.takerName</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">收票部门：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.takerDept" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.takerDept</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">收票电话：</td>
									<td><s:textfield name="customerInvoiceInfoDTO.takerTel" />
										<s:fielderror>
											<s:param>customerInvoiceInfoDTO.takerTel</s:param>
										</s:fielderror></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="buttonDiv" style="margin: 5px 8px 0px;">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><input type="button" class="bt" style="margin: 5px"
									onclick="sub();" value="保 存" /></td>
								<td><input type="button" class="bt" style="margin: 5px"
									onclick="window.location = '${ctx}/customerInvoice/inquiry.action'"
									value="返 回" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</s:form>
</body>
</html>