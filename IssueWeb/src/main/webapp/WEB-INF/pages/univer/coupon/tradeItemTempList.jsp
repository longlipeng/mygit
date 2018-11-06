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
	function pickStartTime() {
		window.WdatePicker({
			el : document.getElementById('startTime'),
			readOnly : true,
			startDate : '%y-%M-%d 00:00:00',
			maxDate : '#F{$dp.$D(\'stopTime\')}',
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			isShowWeek : false,
			isShowClear : true,
			isShowToday : true,
			isShowOthers : true,
			autoPickDate : false,
			qsEnabled : true,
			skin : 'ext'
		});
	}
	function pickStopTime() {
		window.WdatePicker({
			el : document.getElementById('stopTime'),
			readOnly : true,
			startDate : '%y-%M-%d 00:00:00',
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			isShowWeek : false,
			isShowClear : true,
			isShowToday : true,
			isShowOthers : true,
			autoPickDate : false,
			qsEnabled : true,
			skin : 'ext'
		});
	}
</script>
<title>接收数据临时表查询</title>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>接收数据临时表查询</span>
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
				action="/tradeItemTemp/inquiry.action" method="post">
				<table width="100%" style="table-layout: fixed;">
					<tr height="35">
						<td width="120" align="right"><span>数据来源：</span></td>
						<td><s:select list="{'SAC','SAPFM'}" headerKey=""
								headerValue="全部" name="tradeItemTempQueryDTO.partner" /></td>
						<td width="120" align="right"><span>状态：</span></td>
						<td><s:select list="{'0','1','2'}" headerKey=""
								headerValue="全部" name="tradeItemTempQueryDTO.status" /></td>
					</tr>
					<tr height="35">
						<td width="120" align="right"><span>交易类型：</span></td>
						<td><s:select name="tradeItemTempQueryDTO.tradeType"
								list="{'0007','0008','0010'}" theme="simple" headerKey=""
								headerValue="全部" /></td>
						<td width="120" align="right"><span>交易方向：</span></td>
						<td><s:select name="tradeItemTempQueryDTO.direction"
								list="#{'1':'正向','-1':'逆向'}" theme="simple" headerKey=""
								headerValue="全部" /></td>
					</tr>
					<tr height="35">
						<td width="120" align="right"><span>流水号：</span></td>
						<td><s:textfield name="tradeItemTempQueryDTO.id" /> <s:fielderror>
								<s:param>tradeItemTempQueryDTO.id</s:param>
							</s:fielderror></td>
						<td width="120" align="right"><span>商户编码：</span></td>
						<td><s:textfield name="tradeItemTempQueryDTO.mchtCode" /> <s:fielderror>
								<s:param>tradeItemTempQueryDTO.mchtCode</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="120" align="right"><span>券号：</span></td>
						<td><s:textfield name="tradeItemTempQueryDTO.couponNo" /> <s:fielderror>
								<s:param>tradeItemTempQueryDTO.couponNo</s:param>
							</s:fielderror></td>
						<td width="120" align="right"><span>B2C行项号：</span></td>
						<td><s:textfield name="tradeItemTempQueryDTO.itemOrderNo" />
							<s:fielderror>
								<s:param>tradeItemTempQueryDTO.itemOrderNo</s:param>
							</s:fielderror></td>
					</tr>
					<tr height="35">
						<td width="120" align="right"><span>接收时间开始于：</span></td>
						<td><s:textfield id="startTime"
								name="tradeItemTempQueryDTO.startTime"
								onfocus="pickStartTime();" cssClass="Wdate" /> <s:fielderror>
								<s:param>tradeItemTempQueryDTO.startTime</s:param>
							</s:fielderror></td>
						<td width="120" align="right"><span>接收时间结束于：</span></td>
						<td><s:textfield id="stopTime"
								name="tradeItemTempQueryDTO.stopTime" onfocus="pickStopTime();"
								cssClass="Wdate" /> <s:fielderror>
								<s:param>tradeItemTempQueryDTO.stopTime</s:param>
							</s:fielderror></td>
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
			<s:form id="tradeItemTempForm" name="tradeItemTempForm"
				action="/tradeItemTemp/inquiry.action" method="post">
				<s:hidden name="tradeItemTempQueryDTO.id"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.partner"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.status"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.mchtCode"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.tradeType"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.direction"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.couponNo"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.itemOrderNo"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.startTime"></s:hidden>
				<s:hidden name="tradeItemTempQueryDTO.stopTime"></s:hidden>
				<ec:table items="pageDataDTO.data" var="map" width="100%"
					form="tradeItemTempForm"
					action="${ctx}/tradeItemTemp/inquiry.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<s:if test="totalRows<10000">
						<ec:exportXls fileName="TradeItemTemp.xls" tooltip="导出为TradeItemTemp.xls文件"></ec:exportXls>
					</s:if>
					<ec:row>
						<ec:column property="id" title="流水号" width="6%" />
						<ec:column property="partner" title="数据来源" width="6%" />
						<ec:column property="mchtCode" title="商户编码" width="6%" escapeAutoFormat="true" />
						<ec:column property="tradeType" title="交易类型" width="6%" escapeAutoFormat="true" />
						<ec:column property="direction" title="交易方向" width="6%" escapeAutoFormat="true" />
						<ec:column property="couponNo" title="券号" width="6%" escapeAutoFormat="true" />
						<ec:column property="itemOrderNo" title="B2C行项号" width="6%" escapeAutoFormat="true" />
						<ec:column property="tradeTime" title="交易时间" width="6%" escapeAutoFormat="true" />
						<ec:column property="amount" title="金额"
							value="${map.amount/100}" width="6%" />
						<ec:column property="status" title="状态" width="3%" />
						<ec:column property="counter" title="处理次数" width="6%" />
						<ec:column property="errMsg" title="错误信息" width="6%" />
						<ec:column property="createdTime" title="接收时间" cell="date"
							format="yyyy-MM-dd HH:mm:ss.SSS" width="12%" />
						<ec:column property="null" title="操作" width="3%" viewsDenied="xls">
							<input type="button" class="bt" style="margin: 0px auto;"
								onclick="confirm('警告：删除后不可恢复！！！',function(){location.href='tradeItemTemp/delete.action?id=${map.id}';});"
								value="删除" />
						</ec:column>
					</ec:row>
				</ec:table>
			</s:form>
		</div>
	</div>
</body>
</html>