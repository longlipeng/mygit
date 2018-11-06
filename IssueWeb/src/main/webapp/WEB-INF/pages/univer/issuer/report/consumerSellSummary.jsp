<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>收单运营汇总表</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		function  submitForm(){
			var startDateString = document.getElementById('startDate').value;
			if(startDateString == null || startDateString ==""){
				errorDisplay("开始日期必须选择");
				return;
			} 
			var endDateString = document.getElementById('endDate').value;
			if(endDateString == null || endDateString == ""){
				errorDisplay("结束日期必须选择");
				return;
			} 
			var startDate=new Date(startDateString.replace(/-/g, '/'));
		var endDate= new Date(endDateString.replace(/-/g, '/ '));
	//	alert(currentDate);
		//if(!startDate< currentDate){
			//errorDisplay("开始日期必须早于当前日期");
		//}
		if(startDate > endDate){
			errorDisplay("开始日期不能大于结束日期");
			return;
		}
			document.queryForm.submit();
		}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>收单运营汇总表</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;

								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="queryForm" name="queryForm" action="/ireport/queryConsumerSellSummary!viewReport.action"
								method="post">
								<s:hidden name="consumerSellSummaryDTO.reportName"></s:hidden>
								<s:hidden name="consumerSellSummaryDTO.reportType"></s:hidden>
								<s:hidden name="consumerSellSummaryDTO.issuerId"></s:hidden>
								<s:hidden name="consumerSellSummaryDTO.issuerName"></s:hidden>
								<s:hidden name="consumerSellSummaryDTO.reportFileName"></s:hidden>
								<table width="80%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="50%" align=left>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 50%; text-align: right;">
														<span style="color: red">*</span>开始时间：
													</td>
													<td align="left">
														<s:textfield name="consumerSellSummaryDTO.startDate" id="startDate"
															size="20" onfocus="dateClick(this)" cssClass="Wdate">
														</s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td width="50%" align="left">
											<table style="text-align: center; width: 100%">
												<tr>
													<td style="width: 50%; text-align: right;">
														<span style="color: red">*</span>结束时间：
													</td>
													<td align="left">
														<s:textfield name="consumerSellSummaryDTO.endDate" id="endDate"
															size="20" onfocus="dateClick(this)" cssClass="Wdate">
														</s:textfield>
														<s:fielderror>
															<s:param>dto.endDate</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
											</td>
									</tr>
									<tr><td>&nbsp;</td><td></td></tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>

		</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="90%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr height="35">
									
									    <td align="right" colspan="3">
											 <input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="下 载 报 表"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		<s:form name="updatePasswordForm" id="updatePasswordForm">
			<s:hidden id="userDTO.userId" name="userDTO.userId"></s:hidden>
		</s:form>
	</body>
</html>