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
			<span>赎回订单入库-->${message}</span>
		</div>
		<script type="text/javascript">
		function submitConfirm(){
			if('${operation}'=='reject'){
				document.confirmForm.action="${actionName}!${operation}";
				document.confirmForm.submit();
			}else{
				confirm("确定需要回收的卡已回收！",submitForm);
			}
			 

		} 
		
				function submitForm() {
					
					
					if('${operation}'=='reject'){
						document.confirmForm.action="${actionName}!${operation}";
						document.confirmForm.submit();
					}else{
						
							var orderId = $('#orderId').val();
							var batchNo = $('#batchNo').val();
							var options = {
								url : '${actionName}!${operation}',
								type : 'POST',
								success : TimeOperation(orderId, 'ransom',
										"${ctx}/batchfile/getOrderState.action",
										"${ctx}/ransomOrderAction!list.action",
										'处理中,请耐心等候...','5s')
							};
							$('#confirmForm').ajaxSubmit(options);
					        
			             
					}	
				}
				function check(){
					
					var flag = true;
					for(i = 0; i < document.getElementsByName("origCardListStr").length; i++) {
				        if (document.getElementsByName("origCardListStr").item(i).checked) {
				                    flag = false;
				                    break;
				            }
				    }   
				    if(flag){
				        errorDisplay("请选择一条记录");
				        return true;
				    }
				    return false;
				}
				function modifyCardState(dataState){
					if(check()){
						return;
					}
					$('#sellOrderOrigCardListDTO\\.callBack').val(dataState);
					document.origCardviewForm.action = "${ctx}/ransomOrderAction!orderCardOperators.action";
				  	document.origCardviewForm.submit();
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
							<s:hidden id="orderId" name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden id="batchNo" name="sellOrderDTO.batchNo"></s:hidden>
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
								onclick="submitConfirm();">
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
		
			<%@ include file="../orderview/ransomViewToCallBack.jsp"%>
	</body>
</html>