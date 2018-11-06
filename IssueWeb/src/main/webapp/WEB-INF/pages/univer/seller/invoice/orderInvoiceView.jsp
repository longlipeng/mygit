<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>发票明细</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发票管理界面-->发票明细</span>
		</div>
		<div id="orderInvoiceView"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="orderInvoiceViewTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">发票明细</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="orderInvoiceViewTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;" >
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										开票序号:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceId"  />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>订单号:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.orderId" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>总金额:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.totalPrice" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>客户名称:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.customerName"  />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>发票抬头:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceTitle" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>发票代码:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceCode" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>发票号码:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceNumber" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>开票主体:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.billingSubject" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>发票类型:
									</td>
									<td>
										<dl:dictList displayName="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceType" defaultOption="true"
											dictType="120" tagType="1" dictValue="${orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceType}"></dl:dictList>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>开票项目:
									</td>
									<td>
										<dl:dictList displayName="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceProj" defaultOption="true"
											dictType="120" tagType="1"   dictValue="${orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceProj}"></dl:dictList>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										取票方式:
									</td>
									<td>
										<dl:dictList displayName="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.sendoutWay" defaultOption="true"
											dictType="121" tagType="1" dictValue="${orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.sendoutWay}"></dl:dictList>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										客户地址:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceCustomerAddress" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										客户预计取票时间:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.customerExpectedDate" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										开票人:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.userName"  />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										发票录入日期:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.createTime" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										状态:
									</td>
									<td>
										<dl:dictList dictType="101" tagType="1" defaultOption="true"
											displayName="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceState"
											dictValue="${orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceState}"></dl:dictList>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										<span style="color: red">*</span>发票日期:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.invoiceDate" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										开票日期:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.billingDate" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										备注:
									</td>
									<td>
										<s:label name="orderInvoiceInfoQueryDTO.orderInvoiceInfoDTO.comment" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>

		</div>
	
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
			<%-- 	onclick="window.history.back();">--%>
					onclick="window.close();">
				返回
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>