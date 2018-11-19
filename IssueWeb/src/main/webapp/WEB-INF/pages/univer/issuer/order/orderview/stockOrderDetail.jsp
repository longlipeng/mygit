<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>订单查看</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<head>
		<title>订单查看</title>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			var isDisplay = false;
			function displayTableBody(tableName) {
				if (isDisplay) {
					display(tableName);
					isDisplay = false;
				} else {
					undisplay(tableName);
					isDisplay = true;
				}
			}
			
			function orderSubmit(){
				newForm.action = "stockOrderInputAction!insert";
				newForm.submit();
			}
			function downLoad(){
				cardListForm.action = "stockOrderConfirmAction!downLoad";
				cardListForm.submit();
			}
			
			
		</script>
		
	</head>
	
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单查看</span>
		</div>
			<!-- 订单基本信息 -->
			<s:if test="getSellOrderDTO().getOrderType()==30000001">
			<%@ include file="orderBasicBySeller.jsp"%>
			</s:if>
			<s:if test="getSellOrderDTO().getOrderType()==30000002">
			<%@ include file="orderBasicBySellerUnsign.jsp"%>
			</s:if>
			<s:if test="getSellOrderDTO().getOrderType()!=30000001 && getSellOrderDTO().getOrderType()!=30000002">
			<%@ include file="orderBasic.jsp"%>
			</s:if>
			<!-- 订单明细信息 -->
			<c:if test="${orderListList_totalRows > 0}">
				<%@ include file="orderListList.jsp"%>
			</c:if>
			<!-- 订单卡明细信息 -->
			<c:if test="${orderInputCardList_totalRows > 0}">
				<%@ include file="orderInputCardList.jsp"%>
			</c:if>
			
			<!-- 订单卡明细信息 -->
			<c:if test="${orderCardList_totalRows > 0}">
				<%@ include file="orderCardList.jsp"%>
			</c:if>
			
			<!-- 订单流程信息 -->
			<c:if test="${sellOrderFlow_totalRows > 0}">
				<%@ include file="orderFlowList.jsp"%>
			</c:if>
		
	</body>
</html>