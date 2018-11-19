<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/jquery.jsp"%>
		<title>结算页面</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
	function doSettle() {

		var queryForm = Ext.get("queryForm").dom;
		if (queryForm['ec_eti'] != null) {
			queryForm['ec_eti'].disabled = true;
		}
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		if(startDate==""||endDate==""){
			errorDisplay("日期不能为空");
			return false;
		}
		if (startDate != "" && startDate != null && endDate != null
				&& endDate != "") {
			if (startDate > endDate) {
				errorDisplay("开始时间不能大于结束时间!");
				return false;
			}
		}
		queryForm.action = '${ctx}/settlement/doSettlement.action';
		queryForm.submit();
	}
</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算页面</span>
		</div>

		<s:form id="queryForm" name="queryForm"
					action="/cardManage/selectCardOperation.action" method="post">
		<div id="query" style="border: 1px solid #B9B9B9;">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">结算起止日期</span>
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
											开始时间：
										</td>
										<td>

											<input type="text" id="startDate"  name="doSettleDto.startDateStr"
												onclick="dateClick(this)" class="Wdate" />
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
											<input type="text" id="endDate" name="doSettleDto.endDateStr"
												onclick="dateClick(this)" class="Wdate"/>
										</td>
									</tr>
								</table>
							</td>

							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="doSettle();" value="结算" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				
			</div>
		</div>
		
			</s:form>
		
	</body>
</html>