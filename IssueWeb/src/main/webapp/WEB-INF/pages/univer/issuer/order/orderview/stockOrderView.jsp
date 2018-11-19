<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
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
		</script>
		
	</head>
	
	<body onload="">&nbsp; 
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
			<s:if test="getSellOrderDTO().getOrderType()==50000001">
				<%@ include file="orderFlowList.jsp"%>
				<s:hidden name="sellOrderDTO.orderContact" id="contact"/>
		</s:if>
			<%-- <c:if test="${orderListList_totalRows > 0}">
				<%@ include file="orderListList.jsp"%>
			</c:if> --%>
			
			<%-- <!-- 订单卡明细信息 -->
			<c:if test="${orderCardList_totalRows > 0}">
				<%@ include file="orderCardList.jsp"%>
			</c:if>
			<!-- 卡片接收明细 -->
			<c:if test="${orderCardReceiveList_totalRows > 0}">
				<%@ include file="orderCardReceiveList.jsp"%>
			</c:if>
			<!-- 订单流程信息 -->
			<c:if test="${sellOrderFlow_totalRows > 0}">
				<%@ include file="orderFlowList.jsp"%>
			</c:if>  --%>
			
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="stockOrderInputAction!list" name="backForm"
				id="backForm">
				<s:hidden name="addType" value=""></s:hidden>
				<button class='bt' style="float: right; margin: 7px"
					onclick="window.close();">
					关  闭
				</button>
			</s:form>
			<div style="clear: both"></div>
		</div>
		
	
	</body>
</html>