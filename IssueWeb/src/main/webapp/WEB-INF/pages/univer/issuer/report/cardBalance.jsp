<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

		<title>卡余额报表</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		function submitForm(){
			var dataDateString = document.getElementById('dataDate').value;
			if(dataDateString == null || dataDateString == ""){
				errorDisplay("查询时间必须选择");
				return;
			} 
		document.queryForm.submit();
		document.getElementById('dowBt').value="下 载 中...";
        document.getElementById('dowBt').disabled=true;
		setTimeout(function (){
			document.getElementById('dowBt').disabled=false;
			document.getElementById('dowBt').value="下 载 报 表";
        },10000);
		
	}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>卡余额报表</span>
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
								action="/ireport/queryCardBalance!downLoad.action" method="post">
								<s:hidden name="cardBalanceDTO.reportName"></s:hidden>
								<s:hidden name="cardBalanceDTO.reportType"></s:hidden>
								<s:hidden name="cardBalanceDTO.issuerId"></s:hidden>
								<s:hidden name="cardBalanceDTO.issuerName"></s:hidden>
								<s:hidden name="cardBalanceDTO.reportFileName"></s:hidden>
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="50%" align=left>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 50%; text-align: right;">
														<span style="color: red">*</span>查询时间：
													</td>
													<td align="left">
														<s:textfield name="cardBalanceDTO.dataDate" id="dataDate"
															size="20" onfocus="dateClick(this)" cssClass="Wdate">
														</s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td width="50%" align="left">
											<table style="text-align: left; width: 100%">
												<tr>
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
											 <input type="button" id="dowBt" class="bt" style="margin: 5px" onclick="submitForm();" value="下 载 报 表"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>