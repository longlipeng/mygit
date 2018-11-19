<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>赎回管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			function getMoney(){
				var redemptionFee=document.getElementById("redemptionFee").value;
				var costPrice=document.getElementById("costPrice").value;
				var balance=document.getElementById("balance").value;
				var money=document.getElementById("money").value;
				var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/;
				if(redemptionFee==null || costPrice==null){
					redemptionFee=0;
					costPrice=0;
					errorDisplay("赎回手续费和相关成本费不能为空!");
					return;
				}
				if(!doublePatrn.exec(redemptionFee)){
				document.getElementById("redemptionFee").value=0;
					errorDisplay("赎回手续费输入格式错误，必须是0-99999999.99的数字");
					return;
				}
				if(!doublePatrn.exec(costPrice)){
				document.getElementById("costPrice").value=0;
					errorDisplay("相关成本费输入格式错误，必须是0-99999999.99的数字");
					return;
				}
				money=parseFloat(balance)-parseFloat(redemptionFee)+parseFloat(costPrice);
				document.getElementById("money").value=money;
		
			}
			function redemption(){
				
	        	document.newForm.action='cardManage/redemptionCard.action';
	        	document.newForm.submit(); 
			
			}
			function enter(){
				newForm.action='${ctx}/cardManage/enter.action';
				newForm.submit();
			}
			function a(){
			document.getElementById("money").value=${cardManagementDTO.balance};
				var n='${n}';
				if(n!=''&&n!=null){
					document.getElementById("cardNo").readOnly='true';
					document.getElementById("CVV2").readOnly='true';
					document.getElementById("password").readOnly='true';
				}
			}
			
		</script>
		
	</head>
	<body onload="a()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>赎回管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">赎回操作</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>						
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManage/cardQuery.action" method="post">
								<s:token></s:token>
								           <s:hidden name="cardManagementDTO.agentrName" ></s:hidden>
		<s:hidden name="cardManagementDTO.agentrCertType" ></s:hidden>
		<s:hidden name="cardManagementDTO.agentrCertTypeNo" ></s:hidden>
		<s:hidden name="cardManagementDTO.startDate" ></s:hidden>
		<s:hidden name="cardManagementDTO.endDate" ></s:hidden>
		 <s:hidden name="cardManagementDTO.owner" />	
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" id="cardNo" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferOutCard
															</s:param>
														</s:fielderror>
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>
										<td>
										</td>
									
									</tr>
									<tr>										
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>余额：
														

													</td>
													<td>
													<s:textfield name="cardManagementDTO.balance" id="balance" readonly="true"></s:textfield> 元
														<s:fielderror>
															<s:param>
																cardManagementDTO.balance
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									
									</tr>
									<tr>
									<td colspan="3" align="right">									
									<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													</td>
													<td>
													
													</td>
												</tr>
											</table>
											</td>
									</tr>
									<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														<h3>原销售订单信息</h3>
														</td>
														<td>
															
															
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
									
									<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														产品类型：

														</td>
														<td>
														<s:label name="cardManagementDTO.prodType"></s:label>
														<s:hidden name="cardManagementDTO.prodType"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.prodType
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														产品：

														</td>
														<td>
														<s:label name="cardManagementDTO.prodName"></s:label>
														<s:hidden name="cardManagementDTO.prodName"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.prodName
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														原销售订单号：

														</td>
														<td>
														<s:label name="cardManagementDTO.oldOrder"></s:label>
														<s:hidden name="cardManagementDTO.oldOrder"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.oldOrder
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
										<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														客户：

														</td>
														<td>
														<s:label name="cardManagementDTO.customerName"></s:label>
														<s:hidden name="cardManagementDTO.customerName"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.customerName
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														客户类型：

														</td>
														<td>
														<s:label name="cardManagementDTO.customerType"></s:label>
														<s:hidden name="cardManagementDTO.customerType"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.customerType
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														订单类型：

														</td>
														<td>
														<s:label name="cardManagementDTO.orderType"></s:label>
														<s:hidden name="cardManagementDTO.orderType"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.orderType
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
									 	<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														经办人：

														</td>
														<td>
														<s:label name="cardManagementDTO.operator"></s:label>
														<s:hidden name="cardManagementDTO.operator"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.operator
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														经办人电话：

														</td>
														<td>
														<s:label name="cardManagementDTO.operatorTel"></s:label>
														<s:hidden name="cardManagementDTO.operatorTel"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.operatorTel
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														售卡日期：

														</td>
														<td>
														<s:label name="cardManagementDTO.modifyTime"></s:label>
														<s:hidden name="cardManagementDTO.modifyTime"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.modifyTime
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
										<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														支付渠道：

														</td>
														<td>
														<s:label name="cardManagementDTO.payChannel"></s:label>
														<s:hidden name="cardManagementDTO.payChannel"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.payChannel
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														转入账号：

														</td>
														<td>
														<s:label name="cardManagementDTO.intoBankId"></s:label>
														<s:hidden name="cardManagementDTO.intoBankId"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.intoBankId
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														转出账号：

														</td>
														<td>
														<s:label name="cardManagementDTO.fromBinkId"></s:label>
														<s:hidden name="cardManagementDTO.fromBinkId"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.fromBankId
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
									</tr>
									<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														<h3>持卡人信息</h3>

														</td>
														<td>
															
															
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
									
									<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														持卡人姓名：

														</td>
														<td>
														<s:label name="cardManagementDTO.cardholderName"></s:label>
														<s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.cardholderName
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														手机号码：

														</td>
														<td>
														<s:label name="cardManagementDTO.mobile"></s:label>
														<s:hidden name="cardManagementDTO.mobile"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.mobile
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														证件类型：

														</td>
														<td>
														<s:label name="cardManagementDTO.idType"></s:label>
														<s:hidden name="cardManagementDTO.idType"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.idType
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
														证件号码：

														</td>
														<td>
														<s:label name="cardManagementDTO.idNo"></s:label>
														<s:hidden name="cardManagementDTO.idNo"></s:hidden>
															<s:fielderror>
															<s:param>
																cardManagementDTO.idNo
															</s:param>
														</s:fielderror>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>赎回手续费：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" id="redemptionFee" size="23" value="0" onchange="getMoney();"></s:textfield> 元
														<s:fielderror>
															<s:param>
																cardManagementDTO.serviceFee
															</s:param>
														</s:fielderror>
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>退相关成本费：
														

													</td>
													<td>
													<s:textfield name="cardManagementDTO.costPrice" value="0" id="costPrice" onchange="getMoney();"></s:textfield> 元
														<s:fielderror>
															<s:param>
																cardManagementDTO.costPrice
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>赎回金额：
														

													</td>
													<td>
													<s:textfield name="cardManagementDTO.redemptionMoney" id="money" readonly="true"></s:textfield> 元
														<s:fielderror>
															<s:param>
																cardManagementDTO.redemptionMoney
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡是否回收：
														

													</td>
													<td>
													<s:select
																	list="#{'0':'不回收','1':'回收'}"
																	name="cardManagementDTO.callBack"
																	id="callBack"></s:select>
														<s:fielderror>
															<s:param>
																cardManagementDTO.callBack
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
		
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="redemption();">
				确认
			</button>		
			<div style="clear: both"></div>
		</div>
	</body>
</html>
