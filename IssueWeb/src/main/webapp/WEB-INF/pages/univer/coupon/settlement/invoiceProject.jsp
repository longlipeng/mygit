<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择开票项目</title>
<%@ include file="/commons/meta.jsp"%>
<base target="_self">
<script type="text/javascript">
	function doCommonInvoice() {
		var project = document.getElementById('project').value;
		window.returnValue = project;
		window.close();
	}
</script>
</head>
<body>

	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>选择开票项目</span>
	</div>
	<div id="queryTable"
		style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		<s:form id="commonInvoiceForm" name="commonInvoiceForm"
			action="settlement/addCommonInvoice.action" method="post">
			<s:hidden name="settleMentId"></s:hidden>
			<table width="100%" style="table-layout: fixed;">
				<tr height="35">
					<td  align="right"><span>开票项目：</span>
					</td>
					<td align="left"><s:select id="project" list="listProject" listKey="dictCode" listValue="dictName" headerKey="" headerValue="--请选择--"/></td>
				</tr>
				
			</table>
		</s:form>
	</div>
	<div id="btnDiv" style="text-align: right; width: 100%">
		<button class='bt' style="float: right; margin: 5px"
			onclick="window.close()">关 闭</button>
		<button class='bt' style="float: right; margin: 5px"
			onclick="doCommonInvoice();">提 交</button>
		<div style="clear: both"></div>
	</div>
</body>
</html>