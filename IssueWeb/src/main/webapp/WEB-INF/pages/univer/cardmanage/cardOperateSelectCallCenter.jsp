<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/jquery.jsp"%>
		<title>卡业务审计</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
	function query() {

		var queryForm = Ext.get("queryForm").dom;
		if (queryForm['ec_eti'] != null) {
			queryForm['ec_eti'].disabled = true;
		}
		startDate = $("#startDate").val()
		endDate = $("#endDate").val()
		if (startDate != "" && startDate != null && endDate != null
				&& endDate != "") {
			if (startDate > endDate) {
				errorDisplay("开始时间不能大于结束时间!");
				return false;
			}
		}
		queryForm.action = '${ctx}/cardManageExt/selectCardOperation.action';
		queryForm.submit();
	}
</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡业务审计</span>
		</div>

		<s:form id="queryForm" name="queryForm"
					action="/cardManageExt/selectCardOperation.action" method="post">
		<div id="query" style="border: 1px solid #B9B9B9;">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">查询条件</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="queryTable" style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											卡号：
										</td>
										<td>
											<s:textfield name="cardManagementQueryDTO.cardNo" size="23"/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											业务员：
										</td>
										<td>&nbsp;
											<s:textfield name="cardManagementQueryDTO.userName" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											开始时间：
										</td>
										<td>

											<input type="text" id="startDate"  name="cardManagementQueryDTO.starDate"
												onclick="dateClick(this)" class="Wdate"
												value="${cardManagementQueryDTO.starDate}" />

										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											结束时间：
										</td>
										<td>
											<input type="text" id="endDate" name="cardManagementQueryDTO.enDate"
												onclick="dateClick(this)" class="Wdate"
												value="${cardManagementQueryDTO.enDate}" />
										</td>
									</tr>
								</table>
							</td>

							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="query();" value="查 询" />
										<button class='bt' type="button" style=" margin: 5px 10px"
											onclick="queryForm.action='cardManageExt/comeback.action';queryForm.submit();">
											返 回
										</button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				
			</div>
		</div>
		<div id="list"
			style="border: 1px solid #B9B9B9; ">
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
				style="background-color: #FBFEFF;border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="queryForm"
						action="${ctx}/cardManageExt/selectCardOperation.action"
						imagePath="${ctx}/images/extremecomponents/*.gif"  view="html"
							retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="cardOperateList.xls" tooltip="导出Excel" />
						<ec:row>

							<ec:column property="cardNo" title="卡号" width="10%"  escapeAutoFormat="true" >

							</ec:column>

							<ec:column property="operationType" title="操作类型" width="10%" >
								
							</ec:column>
							<ec:column property="serviceFee" title="服务费(元)" />
							<ec:column property="adjustAmount" title="调整金额" />
							
							<ec:column property="modifyTime" title="操作日期"  />


							<ec:column property="userName" title="业务员"  />
						</ec:row>
					</ec:table>
			</div>
			</div>
			</s:form>
		
	</body>
</html>