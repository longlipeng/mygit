<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<title>卡交易调整查询</title>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
	function query() {
		var queryForm = Ext.get("queryForm").dom;
		if (queryForm['ec_eti'] != null) {
			queryForm['ec_eti'].disabled = true;
		}
		queryForm.action = '${ctx}/cardManage/queryAdjustAcount.action';
		queryForm.submit();
	}
</script>
</head>
<body>

	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>卡交易调整查询</span>
	</div>

	<s:form id="queryForm" name="queryForm"
			action="/cardManage/queryAdjustAcount.action" method="post">
		<div id="query" style="border: 1px solid #B9B9B9;">
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					align="center">
					<tr>
						<td class="TableTitleFront"><span class="TableTop">查询条件</span>
						</td>
						<td class="TableTitleEnd">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div id="queryTable"
				style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">

				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td style="width: 110px; text-align: right;">卡号：</td>
						<td><s:textfield name="chargeTxnDTO.cardNo" id="cardNo"
								size="23"></s:textfield> <s:fielderror>
								<s:param>
																chargeTxnDTO.cardNo
															</s:param>
							</s:fielderror></td>

						<td style="width: 110px; text-align: right;">上财流水号：</td>
						<td><s:textfield name="chargeTxnDTO.transSeqNo2"
								id="transSeqNo2" size="23"></s:textfield> <s:fielderror>
								<s:param>
																chargeTxnDTO.transSeqNo2
															</s:param>
							</s:fielderror></td>
						<td style="width: 110px; text-align: right;">上汽流水号：</td>
						<td><s:textfield name="chargeTxnDTO.transSeqNo1"
								id="transSeqNo1" size="23"></s:textfield> <s:fielderror>
								<s:param>
																chargeTxnDTO.transSeqNo1
															</s:param>
							</s:fielderror> </td>

					</tr>

					<tr>
						<td style="width: 110px; text-align: right;">开始时间：</td>
						<td><input type="text" name="chargeTxnDTO.startDate"
							id="startDate" onclick="dateClick(this)" class="Wdate"
							value="${chargeTxnDTO.startDate}" /> 
							<s:fielderror>
								<s:param>
								chargeTxnDTO.startDate
								</s:param>
							</s:fielderror></td>

						<td style="width: 110px; text-align: right;"></span>结束时间：</td>
						<td><input type="text" name="chargeTxnDTO.endDate"
							id="endDate" onclick="dateClick(this)" class="Wdate"
							value="${chargeTxnDTO.endDate}" /> <s:fielderror>
								<s:param>
									cardchargeTxnDTO.endDate
									</s:param>
							</s:fielderror></td>
						<td align="center"><img src="${ctx}/images/table/cx.GIF"
							width="50" height="19" class="noneBtn" onclick="query();"></td>
					</tr>
					<tr></tr>
				</table>
				</div>
		</div>
		<div id="list" style="border: 1px solid #B9B9B9;">
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
				style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<ec:table items="pageDataDTO.data" var="map" width="100%"
					form="queryForm"
					action="${ctx}/cardManage/queryAdjustAcount.action"
					imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
					retrieveRowsCallback="limit" autoIncludeParameters="false">
					<ec:exportXls fileName="cardOperateList.xls" tooltip="导出Excel" />
					<ec:row>
						<ec:column property="DATE_SETTLE" title="清算日期" width="10%" >
						</ec:column>
						<ec:column property="SEQ_NO" title="交易参考号"  />
						<ec:column property="SYS_NO" title="交易系统号"  escapeAutoFormat="true"/>
						<ec:column property="DATE_TXN" title="调账日期" >
						</ec:column>
						<ec:column property="TIME_TXN" title="调账时间"  />
						<ec:column property="CARD_NO" title="卡号"  escapeAutoFormat="true"/>

						<ec:column property="CHARGE_AMT" title="调账金额(元)"  />

						<ec:column property="CHARGE_FEE" title="调账手续费(元)"  />

						<ec:column property="CHARGE_MISC" escapeAutoFormat="true"
							title="调账原因"  />
							<ec:column property="TXN_STAT" escapeAutoFormat="true"
							title="调账状态"  />
						<ec:column property="TRANS_SEQ_NO1" title="上汽流水号" /> 
						<ec:column property="TRANS_SEQ_NO2" title="上财流水号"  />
					</ec:row>
				</ec:table>
			</div>
		</div>
	</s:form>

</body>
</html>