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
			<span>订单付款审核-->${message}</span>
		</div>
		<script type="text/javascript">
				function submitForm() {
					document.getElementById("initActStat").value=0;
					
					confirmForm.action = "${actionName}!${operation}";
					confirmForm.submit();
				}
		</script>
		<s:form id="confirmForm" name="confirmForm" method="post">
		<s:hidden name="sellOrderDTO.orderId"></s:hidden>
		<s:hidden name="sellOrderDTO.orderState"></s:hidden>
		<s:hidden name="sellOrderDTO.orderType"></s:hidden>
		<s:hidden name="sellOrderDTO.paymentState"></s:hidden>
		<s:hidden name="sellOrderDTO.paymentTerm"></s:hidden>
		<s:hidden name="operation" id="operation"></s:hidden>
		<s:hidden name="sellOrderDTO.initActStat" id="initActStat"></s:hidden>
		<s:if test="getSellOrderDTO().getOrderType()==20000001 || getSellOrderDTO().getOrderType()==20000002 || getSellOrderDTO().getOrderType()==10000005 || getSellOrderDTO().getOrderType()==10000006">
		<div id="orderTable">
							
			<table width="100%" border=0>
				<tr>
					<td align="right" width="100" nowrap="nowrap"
						style="padding: 10px 0 0 0">
						<span>操作说明：</span>
					</td>
					<td width="100%" align="left" nowrap="nowrap" rowspan="2"
						style="padding: 10px 0 0 0">
						
							
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明"
								rows="3"></s:textarea>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/${actionName}!list', '_self')">
									返回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitForm();">
									提交
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
		</s:if>
		</s:form>
		<s:if test="getSellOrderDTO().getOrderType()==10000001">
			<%@ include file="orderPaymentSubmitSellOrderSign.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000002">
			<%@ include file="orderPaymentSubmitSellOrderUnsign.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000011">
			<%@ include file="orderPaymentSubmitSellOrderSign.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==10000012">
			<%@ include file="orderPaymentSubmitSellOrderUnsign.jsp"%>
		</s:if>
		
			
		<s:if test="getSellOrderDTO().getOrderType()==10000003">
			<%@ include file="orderPaymentSubmitCreditOrder.jsp"%>
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
		<s:if test="getSellOrderDTO().getOrderType()==60000001">
			<%@ include file="orderPaymentChangeCardSubmit.jsp"%>
		</s:if>
		<s:if test="getSellOrderDTO().getOrderType()==70000001">
			<%@ include file="ransomOrderPaymentSubmit.jsp"%>
		</s:if>
	</body>
</html>