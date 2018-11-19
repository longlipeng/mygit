<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>结算单开普通发票</title>
<%@ include file="/commons/meta.jsp"%>
<base target="_self">
<script type="text/javascript">
	function doCommonInvoice() {
		var commonAmount = document.getElementById('txtCommonAmount').value;
		var r = /^[0-9]*[1-9][0-9]*$/;
		if (!r.test(commonAmount)) {
			alert("请输入正整数");
			return false;
		}
		;
		var waitIvcAmount = ${settlementInfoDto.waitIvcAmount};
		if (commonAmount > waitIvcAmount) {
			alert("超出结算单可开票金额");
			return false;
		}
		var commonInvoiceForm = Ext.get("commonInvoiceForm").dom;
		commonInvoiceForm.submit();
		window.returnValue = 1;
		window.close();
	}
</script>
</head>
<body>

	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>结算单开普通发票</span>
	</div>
	<div id="queryTable"
		style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		<s:form id="commonInvoiceForm" name="commonInvoiceForm"
			action="settlement/addCommonInvoice.action" method="post">
			<s:hidden name="settleMentId"></s:hidden>
			<table width="100%" style="table-layout: fixed;">
				<tr height="35">
					<td  align="right"><span>可开发票金额：</span>
					</td>
					<td align="left"><s:label
							name="settlementInfoDto.waitIvcAmount" /></td>
				</tr>
				<tr height="35">
					<td  align="right"><span>开票项目：</span>
					</td>
					<td align="left"><s:select id="project" name="invoiceRequireMentQueryDto.invoiceProject" list="listProject" listKey="dictCode" 
					listValue="dictName" headerKey="" headerValue="--请选择--"/></td>
				</tr>
				<tr height="35">
					<td  align=right><span>发票金额：</span>
					</td>
					<td align="left"><input type="text" name="commonAmount"
						id="txtCommonAmount" value="${settlementInfoDto.waitIvcAmount}" />
						<s:fielderror>
							<s:param>commonAmount</s:param>
						</s:fielderror></td>
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