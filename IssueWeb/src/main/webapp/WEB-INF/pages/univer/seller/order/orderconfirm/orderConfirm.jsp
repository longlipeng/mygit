<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title><s:text name="xxl.order.orderConfirm"/></title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单待审核-->${message}</span>
		</div>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
				function submitForm() {
					confirmForm.action = "${actionName}!${operation}";
					confirmForm.submit();
				}
		</script>
		<div id="orderTable">
			<table width="100%" border=0>
				<tr>
					<td align="right" width="100" nowrap="nowrap"
						style="padding: 10px 0 0 0">
						<span>操作说明：</span>
					</td>
					<td width="100%" align="left" nowrap="nowrap" rowspan="2"
						style="padding: 10px 0 0 0">
						<s:form id="confirmForm" name="confirmForm" method="post">
							<s:hidden name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden name="sellOrderDTO.orderState"></s:hidden>
							<s:hidden name="sellOrderDTO.orderType"></s:hidden>
							<s:hidden name="operation" id="operation"></s:hidden>
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明"
								rows="3"></s:textarea>
						</s:form>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/${actionName}!list', '_self')">
									返回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitForm();">
									确认
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
		<s:if test="getSellOrderDTO().getOrderType()==10000001">
			<%@ include file="../orderview/sellOrderSignView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000002">
			<%@ include file="../orderview/sellOrderUnsignView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000011">
			<%@ include file="../orderview/sellOrderSignView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000012">
			<%@ include file="../orderview/sellOrderUnsignView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000003">
			<%@ include file="../orderview/creditOrderView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000005">
			<%@ include file="../orderview/sellOrderRetailSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000006">
			<%@ include file="../orderview/sellOrderRetailUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==20000001">
			<%@ include file="../orderview/buyOrderSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==20000002">
			<%@ include file="../orderview/buyOrderUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==30000001">
			<%@ include file="../orderview/buyOrderSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==30000002">
			<%@ include file="../orderview/buyOrderUnsignView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==60000001">
			<%@ include file="../orderview/changeCardOrderView.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==70000001">
			<%@ include file="../orderview/ransomView.jsp"%>
		</s:if>
		
	</body>
</html>