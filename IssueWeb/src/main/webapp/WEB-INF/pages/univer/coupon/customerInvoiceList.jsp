<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
	function checkSelected() {
		var checkboxs = document.getElementsByName('customerInvoiceIds');
		var count = 0;
		for (var i=0;i<checkboxs.length;i++) {
			if (checkboxs[i].checked) {
				count++;
			}
		}
		return count;
	}
	function edit() {
		var count = checkSelected();
		var form = Ext.get('customerInvoiceForm').dom;
		if (form['ec_eti'] != null) {
			form['ec_eti'].disabled = true;
		}
		if (count < 1) {
			errorDisplay('请选择一条记录！');
			return;
		} else if (count > 1) {
			errorDisplay('只能编辑一条记录！');
			return;
		}
		form.action = '${ctx}/customerInvoice/tomodify.action';
		form.submit();
	}
	function view(id) {
		var url = '${ctx}/customerInvoice/view.action?customerInvoiceIds=' + id;
		window.showModalDialog(url, '查看客户发票信息',
				'center:yes;dialogHeight:600px;dialogWidth:800px');
	}
	function select(id) {
		var checkboxs = document.getElementsByName('customerInvoiceIds');
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].value == id){
				checkboxs[i].checked = true;
				return;
			}
		}
	}
</script>
<title>客户发票基本信息管理</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>客户发票基本信息管理</span>
	</div>

	<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="queryTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">查询条件</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="queryTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="queryForm" name="queryForm"
				action="/customerInvoice/inquiry.action" method="post">
				<table width="100%" style="table-layout: fixed;">
					<tr height="35">
						<td width="100" align="right"><span>客户编码：</span></td>
						<td><s:textfield
								name="customerInvoiceInfoQueryDTO.customerCode" /> <s:fielderror>
								<s:param>customerInvoiceInfoQueryDTO.customerCode</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>客户名称：</span></td>
						<td><s:textfield
								name="customerInvoiceInfoQueryDTO.customerName" /> <s:fielderror>
								<s:param>customerInvoiceInfoQueryDTO.customerName</s:param>
							</s:fielderror></td>
					</tr>
					<tr>
						<td width="100" align="right"><span>公司名称：</span></td>
						<td><s:textfield
								name="customerInvoiceInfoQueryDTO.companyName" /> <s:fielderror>
								<s:param>customerInvoiceInfoQueryDTO.companyName</s:param>
							</s:fielderror></td>
						<td align="center" colspan="2">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><input type="button" class="bt" style="margin: 5px"
										onclick="queryForm.submit();" value="查 询" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
	<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
		<div id="listTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">记录列表</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="listTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="customerInvoiceForm" name="customerInvoiceForm"
				action="/customerInvoice/inquiry.action" method="post">
				<s:hidden name="customerInvoiceInfoQueryDTO.customerCode"></s:hidden>
				<s:hidden name="customerInvoiceInfoQueryDTO.customerName"></s:hidden>
				<s:hidden name="customerInvoiceInfoQueryDTO.companyName"></s:hidden>
				<ec:table items="pageDataDTO.data" var="map" width="100%"
					form="customerInvoiceForm" action="${ctx}/customerInvoice/inquiry.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:row onclick="select(${map.id})">
						<ec:column property="null" title="选择" width="3%" sortable="false">
							<input type="radio" name="customerInvoiceIds" value="${map.id}" />
						</ec:column>
						<ec:column property="customerEntityId" title="客户编码" width="6%">
							<a href="javascript:view('${map.id}');">${map.customerEntityId}</a>
						</ec:column>
						<ec:column property="customerName" title="客户名称" width="10%" />
						<ec:column property="companyName" title="公司名称" width="10%" />
						<ec:column property="taxCode" title="税号" width="8%" />
						<ec:column property="invoiceType" title="发票种类" width="6%">
							<c:choose>
								<c:when test="${map.invoiceType=='1'}">增值税专用发票</c:when>
								<c:when test="${map.invoiceType=='2'}">地税票</c:when>
							</c:choose>
						</ec:column>
					</ec:row>
				</ec:table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><display:security urlId="8031010">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="edit();" value="编 辑" />
										</display:security></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</body>
</html>