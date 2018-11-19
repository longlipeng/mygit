<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

		<title>卡实时库存查询报表</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		function submitForm(){
		document.queryForm.submit();
	}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>卡实时库存查询报表</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<div id="QueryBody">
							<s:form id="queryForm" name="queryForm"
								action="/ireport/currentTimeStock!viewReport.action" method="post">
								<s:hidden name="currentTimeStockDTO.functionRoleId"></s:hidden>
								<s:hidden name="currentTimeStockDTO.reportName"></s:hidden>
								<s:hidden name="currentTimeStockDTO.reportType"></s:hidden>
								<s:hidden name="currentTimeStockDTO.issuerId"></s:hidden>
								<s:hidden name="currentTimeStockDTO.issuerName"></s:hidden>
								<s:hidden name="currentTimeStockDTO.reportFileName"></s:hidden>
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