<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>备付金余额查询报表</title>
	<%@ include file="/commons/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	<script type="text/javascript">
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
		if(startDate > endDate){
			errorDisplay("开始日期不能大于结束日期");
			return;
		}
		var dd = new Date();
    	var y = dd.getFullYear();
    	var m = dd.getMonth()+1;
    	var d = dd.getDate();
    	if(m < 10) m = "0" + m;
    	if(d < 10) d = "0" + d;
    	var today = y + "-" + m + "-" + d;
    	if(startDate >= new Date(y,m-1,d)) {
			errorDisplay("开始日期不能大于或等于当前日期");
			return;
		}
		if(endDate >= new Date(y,m-1,d)) {
			errorDisplay("结束日期不能大于或等于当前日期");
			return;
		}
		if((startDate.getYear() != endDate.getYear()) || (startDate.getMonth() != endDate.getMonth())) {
			errorDisplay("只能查一个月以内的数据且不能跨月查询");
			return;
		}
		document.queryForm.submit();
	}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>备付金余额查询报表</span>
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
							<s:form id="queryForm" name="queryForm"
								action="/ireport/queryProvisionsBanlance!viewReport.action" method="post">
								<s:hidden name="provisionsBanlanceDTO.reportName"></s:hidden>
								<s:hidden name="provisionsBanlanceDTO.reportType"></s:hidden>
								<s:hidden name="provisionsBanlanceDTO.issuerId"></s:hidden>
								<s:hidden name="provisionsBanlanceDTO.issuerName"></s:hidden>
								<s:hidden name="provisionsBanlanceDTO.reportFileName"></s:hidden>
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
														<s:textfield name="provisionsBanlanceDTO.startDate" id="startDate"
															size="20" onfocus="dateClick(this)" cssClass="Wdate">
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
														<s:textfield name="provisionsBanlanceDTO.endDate" id="endDate"
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
									<tr height="35">
										<td>&nbsp;</td><td></td>
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
										 <input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="下 载 报 表"/></td>
								</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>