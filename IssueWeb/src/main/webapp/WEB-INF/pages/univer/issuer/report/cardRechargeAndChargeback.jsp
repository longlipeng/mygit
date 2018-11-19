<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>预付卡充值充退报表</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"/>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		function productChange(select) {
				maskDocAll();
				queryForm.action = "queryRechargeAndChargeback.action";
				queryForm.submit();
			}
			function submitForm(){
			var startDateString = document.getElementById('startDate').value;
			if(startDateString == null || startDateString == ""){
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
			//var currentDate = new Date();
			//if(!endDate < currentDate){
			//	errorDisplay("结束日期必须早于当前日期");
			//	return;
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
			<span>预付卡充值充退报表</span>
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
							<!--					todo	-->
							<s:form id="queryForm" name="queryForm"
								action="/ireport/queryRechargeAndChargeback!viewReport.action"
								method="post">
								<s:hidden name="cardRechargeAndChargebackDTO.reportName"></s:hidden>
								<s:hidden name="cardRechargeAndChargebackDTO.reportType"></s:hidden>
								<s:hidden name="cardRechargeAndChargebackDTO.issuerName"></s:hidden>
								<s:hidden name="cardRechargeAndChargebackDTO.reportFileName"></s:hidden>
								<s:hidden name="cardRechargeAndChargebackDTO.fatherEntityId"></s:hidden>
								<s:hidden name="sellerDTO.entityId"></s:hidden>
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="50%" align=left>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 50%; text-align: right;">
														<span style="color: red">*</span>开始时间：
													</td>
													<td align="left">
														<s:textfield name="cardRechargeAndChargebackDTO.startDate"
															id="startDate" size="20" onfocus="dateClick(this)"
															cssClass="Wdate">
														</s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td width="50%" align="left">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 20%; text-align: right;">
														<span style="color: red">*</span>结束时间：
													</td>
													<td align="left">
														<s:textfield name="cardRechargeAndChargebackDTO.endDate"
															id="endDate" size="20" onfocus="dateClick(this)"
															cssClass="Wdate">
														</s:textfield>
														<s:fielderror>
															<s:param>dto.endDate</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<td width="50%" align=left>
										<table style="text-align: right; width: 100%">
											<tr>
												<td style="width: 50%; text-align: right;">
													营销机构：
												</td>
												<td align="left">
													<s:select name="cardRechargeAndChargebackDTO.issuerId"
														id="cardRechargeAndChargebackDTO.issuerId"
														list="sellerDTOs" listKey="entityId"
														listValue="sellerName" headerKey="" headerValue="--请选择--" onchange="productChange(this);"
														>
													</s:select>
												</td>
											</tr>
										</table>
									</td>
									<td width="50%" align="left">
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 20%; text-align: right;">
													收银员：
												</td>
												<td align="left">
													<s:select name="cardRechargeAndChargebackDTO.saleMan"
														list="userDTOs" listKey="userId" listValue="userName"
														headerKey="" headerValue="--请选择--">
													</s:select>
													<s:fielderror>
														<s:param>dto.endDate</s:param>
													</s:fielderror>
												</td>
											</tr>
										</table>
									</td>
									</tr>
									<tr height="35">
										<td>
											&nbsp;
										</td>
										<td></td>
									</tr>
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
									<input type="button" class="bt" style="margin: 5px"
										onclick="submitForm();" value="下 载 报 表" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>