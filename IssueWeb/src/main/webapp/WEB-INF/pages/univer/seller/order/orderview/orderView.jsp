<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单管理--查看订单信息</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<script type="text/javascript">
	var isDisplayTableBody =false;
	function displayTableBody(table){
		if (isDisplayTableBody) {
			display(table);
			isDisplayTableBody = false;
		} else {
			undisplay(table);
			isDisplayTableBody = true;
		}
	}
	
	</script>

	
	<body>
		<%@ include file="/commons/messages.jsp"%>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000001">
			<%@ include file="sellOrderSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000011">
			<%@ include file="sellOrderSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000002">
			<%@ include file="sellOrderUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000012">
			<%@ include file="sellOrderUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000005">
			<%@ include file="sellOrderRetailSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000006">
			<%@ include file="sellOrderRetailUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==10000003">
			<%@ include file="creditOrderView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==20000001">
			<%@ include file="../orderview/buyOrderSignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==20000002">
			<%@ include file="../orderview/buyOrderUnsignView.jsp"%>
		</s:if>
		
		<s:if test="getSellOrderDTO().getOrderType()==30000001">
			<%@ include file="../orderview/buyOrderUnsignView.jsp"%>
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
	
		
		<div id="btnDiv" style="text-align: right;">
			<c:if test="${closeFlag==0}">
			<button class='btn' style="float: right; margin: 7px"
				onclick="window.open('${ctx}/${actionName}!list', '_self')">
						返回
			</button>
			</c:if>
			<c:if test="${closeFlag==1}">
			<button class='btn' style="float: right; margin: 7px"
				onclick="window.close();">
					关闭
			</button>
			</c:if>
		</div>
	</body>
</html>