<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>

		<style>
		 #inhalt {  
		            position: absolute;  
		            height: 200px;  
		            width: 400px;  
		            margin: -100px 0px 0px -200px;  
		            top: 50%;  
		            left: 50%;  
		            text-align: left;  
		            padding: 0px;  
		            border: 1px dotted #000000;  
		            overflow: auto;  
		            background-color: #FFFFFF;
		        }  

        </style>
        
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>

<title>未开发票需求汇总</title>
</head>
<body>
<%@ include file="/commons/messages.jsp"%>
<div class="TitleHref"><span>未开发票需求汇总</span></div>
<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
<div id="queryTitle"
	style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableTitleFront"><span class="TableTop">查询条件</span></td>
		<td class="TableTitleEnd">&nbsp;</td>
	</tr>
</table>
</div>

<div id="queryTable"
	style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
<s:form id="queryForm" name="queryForm"
	action="invoiceRequirement/mchtSumList.action" method="post">
	<s:hidden name="type"></s:hidden>
	<table width="100%" style="table-layout: fixed;">
		<tr height="35">
			<td width="85" align=right><span>客户号：</span></td>
			<td width="160"><s:textfield
				name="invoiceRequirementsDTO.customerEntityId" /> <s:fielderror>
				<s:param>invoiceRequirementsDTO.customerEntityId</s:param>
			</s:fielderror></td>
		</tr>
		<tr>
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
</s:form></div>
</div>

<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
<div id="listTitle"
	style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableTitleFront"><span class="TableTop">记录列表</span></td>
		<td class="TableTitleEnd">&nbsp;</td>
	</tr>
</table>
</div>
<div id="listTable"
	style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
<s:form id="invoiceRequirement.Form" name="orderInvoiceForm"
	action="invoiceRequirement/mchtSumList.action" method="post">
    <s:hidden name="invoiceRequirementsDTO.customerEntityId"></s:hidden>
	<ec:table items="invoiceList" var="map" width="100%"
		tableId="invoiceList" form="orderInvoiceForm"
		action="invoiceRequirement/mchtSumList.action"
		imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
		retrieveRowsCallback="limit" autoIncludeParameters="false">
		<ec:row>
			<ec:column property="mchtId" title="客户号" width="6%" />
			<ec:column property="mchtName" title="客户名称" width="6%" />
			<ec:column property="sumWaitAmount" title="未开票汇总金额" width="6%" />
		</ec:row>
	</ec:table>
</s:form></div>

</div>
</body>
</html>