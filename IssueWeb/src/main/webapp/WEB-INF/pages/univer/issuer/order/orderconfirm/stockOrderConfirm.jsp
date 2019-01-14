<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单审核</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理-->订单确认/退回</span>
		</div>
		<script type="text/javascript">
			function submitForm(type) {
			if(type==30000002){
			var realCardQuantity=document.getElementById('realCardQuantityValue').value;
			var cardQuantity=document.getElementById('cardQuantity').value;
			 var reg = /^\d+$/;
			if(!realCardQuantity.match(reg)){
			    errorDisplay("请输入数字！");
			    return;
			}
			if(parseInt(realCardQuantity.trim())==0){
			    errorDisplay("请输入一个正整数！");
			    return;  
			}
			if(parseInt(realCardQuantity.trim())>parseInt(cardQuantity.trim())){
			    errorDisplay("配送张数不能大于订单张数！");
			    return;
			}
			    document.getElementById('realCardQuantity').value=document.getElementById('realCardQuantityValue').value;
			    confirmForm.action = "${ctx}/${actionName}!${operation}";
				confirmForm.submit();
			 }else{
				 maskDocAll();
			  confirmForm.action = "${ctx}/${actionName}!${operation}";
				confirmForm.submit();
			 }
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
							<s:hidden name="sellOrderDTO.cardQuantity" id="cardQuantity"></s:hidden>
							<s:hidden name="sellOrderDTO.realCardQuantity" id="realCardQuantity"></s:hidden>
							<s:hidden name="operation" id="operation" />
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明" rows="3" maxlength="128"></s:textarea>
						</s:form>
					</td>
					<!-- 个性化卡不参与 -->
					<s:if test="getSellOrderDTO().getOrderType()==30000002">
					<td align="right" width="100" nowrap="nowrap" style="padding: 10px 0 0 0">
						<span>配送张数：</span>
					</td>
					<td align="right" width="100" nowrap="nowrap" style="padding: 10px 0 0 0">
						<s:textfield name="sellOrderDTO.cardQuantity" id="realCardQuantityValue" size="20" maxLength="10"></s:textfield>
					</td>
					</s:if>
					<td>
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/${actionName}!list', '_self')">
								返 回
							</button>
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="submitForm('${sellOrderDTO.orderType}');">
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

		<!-- 订单信息 -->
		<%@ include file="../orderview/stockOrderDetail.jsp"%>
	</body>
</html>