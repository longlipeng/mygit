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
<title>记账数据查询</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>记账数据查询</span>
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
				action="/sapInfo/%{actionName}.action" method="post">
				<table width="100%" style="table-layout: fixed;">
					<tr height="35">
						<td width="100" align="right"><span>交易类型：</span></td>
						<td><s:textfield name="sapInfoQueryDTO.transType" /> <s:fielderror>
								<s:param>sapInfoQueryDTO.transType</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>交易日期：</span></td>
						<td><s:textfield name="sapInfoQueryDTO.transDt"
								onfocus="WdatePicker({readOnly:true, dateFmt:'yyyyMMdd', skin:'ext'});"
								cssClass="Wdate" /> <s:fielderror>
								<s:param>sapInfoQueryDTO.transDt</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="100" align="right"><span>交易公司：</span></td>
						<td><s:if test="hasPrivilege">
								<s:select name="sapInfoQueryDTO.transCompany"
									list="{'5101','G500'}" theme="simple" headerKey=""
									headerValue="全部" />
							</s:if> <s:else>
								<s:select name="sapInfoQueryDTO.transCompany"
									list="{user.entityId}" theme="simple" />
							</s:else></td>
						<td width="100" align="right"><span>支付方式：</span></td>
						<td><s:textfield name="sapInfoQueryDTO.payment" /> <s:fielderror>
								<s:param>sapInfoQueryDTO.payment</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="100" align="right"><span>商户编码：</span></td>
						<td><s:textfield name="sapInfoQueryDTO.vendor" /> <s:fielderror>
								<s:param>sapInfoQueryDTO.vendor</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>标志位：</span></td>
						<td><s:select name="sapInfoQueryDTO.flag"
								list="{'0','1','7','8','9'}" theme="simple" headerKey=""
								headerValue="全部" /></td>
					</tr>
					<tr>
						<td width="85" align=right></td>
						<td width="160"></td>
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
			<s:form id="sapInfoForm" name="sapInfoForm"
				action="/sapInfo/%{actionName}.action" method="post">
				<s:hidden name="sapInfoQueryDTO.transType"></s:hidden>
				<s:hidden name="sapInfoQueryDTO.transDt"></s:hidden>
				<s:hidden name="sapInfoQueryDTO.transCompany"></s:hidden>
				<s:hidden name="sapInfoQueryDTO.vendor"></s:hidden>
				<s:hidden name="sapInfoQueryDTO.flag"></s:hidden>
				<ec:table items="pageDataDTO.data" var="map" width="100%"
					form="sapInfoForm" action="${ctx}/sapInfo/${actionName}.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<s:if test="totalRows<10000">
						<ec:exportXls fileName="SapInfo.xls" tooltip="导出为SapInfo.xls文件"></ec:exportXls>
					</s:if>
					<ec:row>
						<ec:column property="transSeq" title="流水号" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="transType" title="交易类型" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="transDt" title="交易日期" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="transCompany" title="交易公司" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="payment" title="支付方式" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="amount" title="金额" width="6%" />
						<ec:column property="rateAmount" title="手续费" width="6%" />
						<ec:column property="reAmount" title="原卡金额" width="6%" />
						<ec:column property="vendor" title="商户编码" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="saleCompany" title="销售机构" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="docNo" title="公文号" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="pubPvtIntFlag" title="公/私/内" width="6%"
							escapeAutoFormat="true" />
						<ec:column property="flag" title="标志位" width="5%"
							escapeAutoFormat="true" />
						<ec:column property="createdTime" title="创建时间" cell="date"
							format="yyyy-MM-dd HH:mm:ss" width="10%" />
					</ec:row>
				</ec:table>
			</s:form>
		</div>
	</div>
</body>
</html>