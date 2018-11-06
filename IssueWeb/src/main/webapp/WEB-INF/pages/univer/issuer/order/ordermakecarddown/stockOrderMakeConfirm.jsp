<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单确定</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单制卡生成-->订单确定</span>
		</div>
		<script type="text/javascript">
			function submitForm() {
				if('${operation}' == 'reject'){
					document.confirmForm.action="${actionName}!${operation}";
					document.confirmForm.submit();
				}else{
					var orderId = $('#orderId').val();
			        var batchNo = $('#batchNo').val();
			        var options= {
			        	url:'${actionName}!openAccount',
			        	type:'POST',
			        	success:TimeOperation(
			        			orderId,
			        			'makeCard',
			        			"${ctx}/batchfile/getOrderState.action",
			        			"${ctx}/stockOrderMakeCardDownAction!choiceCardCompany",
			        			'制卡中,请耐心等候...','5s')};
			        $('#confirmForm').ajaxSubmit(options);
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
						    <s:hidden id="orderId" name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden id="batchNo" name="sellOrderDTO.batchNo"></s:hidden>
							<s:hidden name="sellOrderDTO.orderState"></s:hidden>
							<s:hidden name="sellOrderDTO.orderType"></s:hidden>
							<s:hidden name="operation" id="operation" />
							<s:hidden name="companyId" id="companyId" />
							<s:textarea name="sellOrderDTO.operationMemo" cols="50" label="操作说明" tooltip="操作说明" rows="3"></s:textarea>
						</s:form>
					</td>
					<td rowspan="2">
						<div id="btnDiv" style="text-align: right; width: 300px;">
							<button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="window.open('${ctx}/${actionName}!list', '_self')">
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
		<%@ include file="../orderview/stockOrderDetail.jsp"%>
	
	</body>
</html>