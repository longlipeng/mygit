<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
		<script type="text/javascript">		
		
			function submitForm() {
				var paymentAmount = document.getElementById("paymentAmount").value;				
				var amountParten=/^\d{1,9}(\.\d{1,2})?/
				if(IsSpace(paymentAmount)){
					errorDisplay("付款金额必须输入!");
					return;
				}
				if(!amountParten.exec(paymentAmount)){
					errorDisplay("请输入格式正确的付款金额(9位以内正整数,两位小数)");
					return;
				}
				var paymentTypeSelectedId = document.getElementById('paymentType').value;

				// 选中银行转账时，需要设置开户银行
				//if (paymentTypeSelectedId == 9) {				  
					//var bankNames=document.getElementById("bankNames");
					//alert("bankNames:"+bankNames);
					//if(null!=bankNames && ""!=bankNames){
						//errorDisplay("选中银行转账时，需要设置开户银行!");
						////return;	
					//}
				//}
				if(paymentTypeSelectedId == 1){
					if('70000001' == '${sellOrderDTO.orderType}'){
						var maxRansomFee='${sellOrderDTO.maxRansomFee}'
						if(eval(paymentAmount) > eval(maxRansomFee)){
							confirm("已经超过最大现金付款金额确定继续操作吗?",operator);
							return ;
						}
					}
				}
				operator();
			}

			function operator(){
				var id=document.getElementById("paymentId").value;
				if(null!=id && ""!=id){
					newForm.action="orderPaymentAction!combinePay";
				}
				newForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">订单明细信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="orderPaymentAction!insertOrderPayment" method="post">
								<s:hidden name="sellOrderDTO.orderId"></s:hidden>
								<s:hidden name="sellOrderDTO.paymentId" id="paymentId"></s:hidden>
								<s:hidden name="sellOrderDTO.totalPrice"></s:hidden>
								<s:hidden name="sellOrderDTO.firstEntityId"></s:hidden>

								<input type="hidden" name="orderPaymentDTO.orderId" value="${sellOrderDTO.orderId}"/>
								<input type="hidden" name="orderPaymentDTO.paymentId" value="${sellOrderDTO.paymentId}"/>
								<table width="100%">
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>支付渠道：
										</td>
										<td width="35%" align="left" nowrap="nowrap" colspan="3">
											<dl:dictList dictType="901" tagType="2" dictValue="${orderPaymentDTO.paymentType}" displayName="orderPaymentDTO.paymentType" 
													props="id=\"paymentType\""></dl:dictList>
											<s:fielderror>
												<s:param>
													orderPaymentDTO.paymentType
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>付款金额：
										</td>
										<td width="35%" align="left" nowrap="nowrap" colspan="3">
											<s:textfield name="orderPaymentDTO.paymentAmount" maxlength="10"
													id="paymentAmount" size="20"/>
											<s:fielderror>
												<s:param>
													orderPaymentDTO.paymentAmount
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注信息：
										</td>
										<td width="35%" align="left" nowrap="nowrap" colspan="3">
											<s:textfield name="orderPaymentDTO.remark" maxlength="100"
													id="remark" size="20"/>
											<s:fielderror>
												<s:param>
													orderPaymentDTO.remark
												</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<p align="right">
				<button class='btn' onclick="window.close();"
					style="float: right; margin: 5px">
					返 回
				</button>
				<button class='btn' onclick="this.disabled='disabled';submitForm();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');"
					style="float: right; margin: 5px">
					添加
				</button>
			</p>
		</div>
	</body>
</html>
