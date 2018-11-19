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
<title>正式数据</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>正式数据</span>
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
				action="/tradeItemDeale/inquery.action" method="post">
				<table width="100%" style="table-layout: fixed;">
					<tr height="35">
						<td width="100" align="right"><span>商户号：</span></td>
						<td><s:textfield
								name="tradeItemDealedQueryDto.mchtCode" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.mchtCode</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>券号：</span></td>
						<td><s:textfield
								name="tradeItemDealedQueryDto.couponNo" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.couponNo</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="100" align="right"><span>消费行项：</span></td>
						<td><s:textfield
								name="tradeItemDealedQueryDto.itemOrderNo" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.itemOrderNo</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>状态：</span></td>
						<td>
							<s:textfield
								name="tradeItemDealedQueryDto.status" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.status</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="100" align="right"><span>交易类型：</span></td>
						<td><s:select name="tradeItemDealedQueryDto.tradeType"
								list="{'0007','0008','0010'}" theme="simple" headerKey=""
								headerValue="-请选择-" /></td>
						<td width="100" align="right"><span>交易方向：</span></td>
						<td><s:select name="tradeItemDealedQueryDto.direction"
								list="#{'1':'正向','-1':'逆向'}" theme="simple" headerKey=""
								headerValue="-请选择-" /></td>
					</tr>
					<tr height="35">
						<td width="100" align="right"><span>关联单据ID：</span></td>
						<td><s:textfield
								name="tradeItemDealedQueryDto.refOrderId" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.refOrderId</s:param>
							</s:fielderror></td>
						<td width="100" align="right"><span>关联单据类型：</span></td>
						<td><s:textfield
								name="tradeItemDealedQueryDto.refOrderType" /> <s:fielderror>
								<s:param>tradeItemDealedQueryDto.refOrderType</s:param>
							</s:fielderror>
							</td>
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
			<s:form id="tradeItemDealeForm" name="tradeItemDealeForm"
				action="/tradeItemDeale/inquery.action" method="post">
				<s:hidden name="tradeItemDealedQueryDto.mchtCode"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.couponNo"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.itemOrderNo"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.status"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.tradeType"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.direction"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.refOrderId"></s:hidden>
				<s:hidden name="tradeItemDealedQueryDto.refOrderType"></s:hidden>
				<ec:table items="tradeItemDealedList" var="map" width="100%"
					form="tradeItemDealeForm" action="${ctx}/tradeItemDeale/inquery.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:row>
						<ec:column property="id" title="流水号" width="6%" />
						<ec:column property="refOrderId" title="关联单据" width="6%" />
						<ec:column property="refOrderType" title="关联单据类型" width="6%" />
						<ec:column property="direction" title="交易方向" />
						<ec:column property="partner" title="合作伙伴" width="6%" />
						<ec:column property="mchtCode" title="商户号" width="6%" />
						<ec:column property="couponNo" title="券号" width="6%" />
						<ec:column property="itemOrderNo" title="消费行项" width="6%" />
						<ec:column property="tradeTime" title="交易时间" width="6%" />
						<ec:column property="tradeType" title="交易类型" width="6%" />
						<ec:column property="amount" title="金额" width="6%" />
						<ec:column property="orderNo" title="交易单号" width="6%" />
						<ec:column property="status" title="状态" width="6%" />
						<ec:column property="counter" title="处理次数" width="6%" />
						<ec:column property="batchId" title="处理批次" width="6%" />
						<ec:column property="createdTime" cell="date"
							format="yyyy-MM-dd HH:mm:ss.SSS" title="创建时间" width="12%" />
					</ec:row>
				</ec:table>
			</s:form>
		</div>
	</div>
</body>
</html>