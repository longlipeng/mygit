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

</script>
<title>结算单匹配发票</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>结算单匹配发票</span>
	</div>

	<div id="queryTable"
		style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		<s:form id="queryForm" name="queryForm"
			action="settlement/toMachingInvoice.action" method="post">
			<s:hidden name="settleMentId"></s:hidden>
			<table width="100%" style="table-layout: fixed;">
			   <tr height="35">
					<td width="85" align=right><span>结算单号：</span></td>
					<td width="160">
						<s:label name="settlementInfoDto.settleNo" />
					<td width="90" align=right><span>商户号：</span></td>
					<td width="160">
						<s:label name="settlementInfoDto.mchtEntityId" />
					</td>
				</tr>
				<tr height="35">
					<td width="85" align=right><span>结算单金额：</span></td>
					<td width="160">
						<s:label name="settlementInfoDto.totalAmount" />
					<td width="90" align=right><span>可开发票金额：</span></td>
					<td width="160">
						<s:label name="settlementInfoDto.waitIvcAmount" />
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	
	<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">记录列表</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="listTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="invoiceViewForm" name="invoiceViewForm"
					action="settlement/settleInvoiceView.action" method="post">
					<s:hidden name="settleMentId"></s:hidden>
					<ec:table items="invoiceList" var="map" width="100%"
						tableId="invoice" form="invoiceViewForm"
						action="settlement/settleInvoiceView.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="invoiceSettleView.xls" tooltip="导出Excel" />
						<ec:row>
							<ec:column property="name" title="发票名称" width="6%"/>
							<ec:column property="amount" title="发票金额"  width="6%"/>
							
							<ec:column property="type" title="发票类型" width="6%">
							<c:choose>
								<c:when test="${map.type=='1'}">增值税专用发票</c:when>
								<c:when test="${map.type=='2'}">地税票</c:when>
							</c:choose>
							</ec:column>
							<ec:column property="createdTime" title="发票录入日期" cell="date"
								format="yyyy-MM-dd"  width="6%" />
							<ec:column property="status" title="发票状态"   
								cell="dictInfo" alias="456" width="6%" />
							<ec:column property="assigner" title="匹配人" width="6%"/>			
						</ec:row>
					</ec:table>
				</s:form>
			</div>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
		   <button class='bt' style="float: right; margin: 5px"
			onclick="window.close()">关 闭</button>
	    </div>
	</body>	
</html>
