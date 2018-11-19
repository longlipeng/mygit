<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单确定</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单接收--订单确定</span>
		</div>
		<script type="text/javascript">
			function submitForm() {
				confirmForm.action = "${ctx}/stockOrderSendAction!${operation}";
				confirmForm.submit();
			}
		</script>
		<div id="orderTable">
			<table width="100%" border=0>
				<tr>
					<td align="right" width="100" nowrap="nowrap" style="padding: 10px 0 0 0">
						<span>操作说明：</span>
					</td>
					<td width="100%" align="left" nowrap="nowrap" rowspan="2" style="padding: 10px 0 0 0">
						<s:form id="confirmForm" name="confirmForm" method="post">
							<s:hidden name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden name="sellOrderDTO.orderState"></s:hidden>
							<s:hidden name="sellOrderDTO.orderType"></s:hidden>
							<s:hidden name="operation" id="operation" />
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明" rows="3"></s:textarea>
						</s:form>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/stockOrderSendAction!list', '_self')">
								返 回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitForm();">
								提 交
							</button>
							<div style="clear: both"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>

		<!-- 订单信息 -->
		<%@ include file="../orderview/stockOrderView.jsp"%>
	
	</body>
</html>