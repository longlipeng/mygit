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
<title>查看客户发票信息</title>
<script type="text/javascript">
	
</script>
</head>
<body">
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>查看客户发票信息</span>
	</div>

	<div id="baseInfo"
		style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="baseInfoTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">可编辑信息</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="baseInfoTable"
			style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<table width="100%" style="table-layout: fixed;">
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">客户编码：</td>
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
								<td style="width: 120px; text-align: right;">客户名称：</td>
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
								<td style="width: 120px; text-align: right;">公司名称：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.companyName"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">税号：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.taxCode"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">银行账户：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.bankAcctCode"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">地址电话：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.addressTel"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">开票项目：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.invoiceItem"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">发票种类：</td>
								<td><s:select list="#{'1':'增值税专用发票','2':'地税票' }"
										headerKey="" headerValue="-未选择-"
										name="customerInvoiceInfoDTO.invoiceType" disabled="true" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">收票人：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.takerName"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">收票部门：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.takerDept"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">收票电话：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.takerTel"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="customerInfo"
		style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="customerInfoTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">客户信息</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="customerInfoTable"
			style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<table width="100%" style="table-layout: fixed;">
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">客户地址：</td>
								<td><s:textarea
										name="customerInvoiceInfoDTO.customerAddress" rows="3"
										cols="15" disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">客户邮编：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.customerPostcode" disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">客户电话：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.customerTelephone"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">客户传真：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.customerFax"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">工商注册号：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.licenseId"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">注册资本：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.regiCapital"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">营业执照注册日期：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.licenseStaValidity"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">营业执照到期日期：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.licenseEndValidity"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">法人代表姓名：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.corpName"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">法人代表身份证号：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.corpCredId"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="extraInfo"
		style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="extraInfoTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">更多信息</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="extraInfoTable"
			style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<table width="100%" style="table-layout: fixed;">
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">付款方式描述：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.paymentTypeDesc" disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">公司类型：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.coTypeCode"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">城市邮编：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.cityPostCode"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">邮政信箱：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.mailBox"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">税类型：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.taxTypeCode"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">税类型描述：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.taxTypeDesc"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">税号类型：</td>
								<td><s:textfield name="customerInvoiceInfoDTO.taxNoType"
										disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">银行名称：</td>
								<td><s:textfield
										name="customerInvoiceInfoDTO.branchBankName" disabled="true" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">公司注册地址：</td>
								<td><s:textarea name="customerInvoiceInfoDTO.regAddress"
										rows="3" cols="15" disabled="true" /></td>
							</tr>
						</table>
					</td>
					<td>
						<table style="text-align: left; width: 100%">
							<tr>
								<td style="width: 120px; text-align: right;">收票地址：</td>
								<td><s:textarea name="customerInvoiceInfoDTO.takerAddress"
										rows="3" cols="15" disabled="true" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>