<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>账户编缉</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			window.onload=function(){
				var flag=document.getElementById('flag').value;
				if(flag=='true'){
					window.returnValue="flag";
					window.close();
				}
			}		
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
		 function sub(){
					var doublePatrn = /^[1-9]{1}[0-9]+$/;
					var TxnAmt=document.getElementById('maxTxnAmt').value;
					var DayTxnAmt=document.getElementById('maxDayTxnAmt').value;
					var webMaxTxnAmt=document.getElementById('webMaxTxnAmt').value;
					var webMaxDayTxnAmt=document.getElementById('webMaxDayTxnAmt').value;
					var maxBalance='${productDTO.maxBalance}';
					if(!doublePatrn.exec(DayTxnAmt)){
		    			errorDisplay("当日交易额上限必须为正整数！(非零开头)");
						return ;
		    		}
					if(!doublePatrn.exec(TxnAmt)){
		    			errorDisplay("单笔交易额上限必须为正整数！(非零开头)");
						return ;
		    		}
					
					if(parseFloat(TxnAmt)>parseFloat(DayTxnAmt)){
					
					      errorDisplay('单笔交易额上限不能大于当日交易额上限');
						  return;
					}
					if(parseFloat(DayTxnAmt)>parseFloat(maxBalance)){
						
					      errorDisplay('当日交易额上限不能大于产品最大余额');
						  return;
					}
					if(!doublePatrn.exec(webMaxTxnAmt)){
		    			errorDisplay("网上单笔交易额上限必须为正整数！(非零开头)");
						return ;
		    		}
					if(!doublePatrn.exec(TxnAmt)){
		    			errorDisplay("网上当日交易额上限必须为正整数！(非零开头)");
						return ;
		    		}
					if(parseFloat(webMaxTxnAmt)>parseFloat(webMaxDayTxnAmt)){
					      errorDisplay('网上单笔交易额上限不能大于网上当日交易额上限');
						  return;
					}
					if(parseFloat(webMaxDayTxnAmt)>parseFloat(maxBalance)){
						
					      errorDisplay('网上当日交易额上限不能大于产品最大余额');
						  return;
					}
					document.newForm.submit();
			}

				
	</script>
		<base target="_self"/>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">			
			<span>修改账户明细</span>
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
									<span class="TableTop">明细信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">		
							<s:form id="newForm" name="newForm" action="prodAccounteEdit.action"
								method="post">
								<s:hidden name="flag" id="flag"></s:hidden>
								<s:hidden name="prodAcctypeDTO.productId" id="productId"></s:hidden>
								<s:hidden name="prodAcctypeDTO.relId" id="relId"></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>账户号：
													</td>
													<td>
														<s:label name="prodAcctypeDTO.serviceId" />
														<s:hidden name="prodAcctypeDTO.serviceId" id="accTypeId"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														账户名：
													</td>
													<td width="120">
														<s:label name="prodAcctypeDTO.serviceName"/>
														<s:hidden name="prodAcctypeDTO.serviceName"/>
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
														单笔交易额上限：
													</td>
													<td>
														<s:textfield name="prodAcctypeDTO.maxTxnAmt" id="maxTxnAmt"/>
														元
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.maxTxnAmt
															</s:param>
														 </s:fielderror>						
													</td>		
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														当日交易额上限：
													</td>
													<td>
														<s:textfield name="prodAcctypeDTO.maxDayTxnAmt" id="maxDayTxnAmt"/>元
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.maxDayTxnAmt
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
														网上单笔交易额上限：
													</td>
													<td>
														<s:textfield name="prodAcctypeDTO.webMaxTxnAmt" id="webMaxTxnAmt"/>
														元	
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.webMaxTxnAmt
															</s:param>
														</s:fielderror>															
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														网上当日交易额上限：
													</td>
													<td>
														<s:textfield name="prodAcctypeDTO.webMaxDayTxnAmt" id="webMaxDayTxnAmt"/>元	
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.webMaxDayTxnAmt
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
														默认账户费（费率）：
													</td>
													<td>
														<s:hidden name="modifyRate"/>
														<s:textfield name="prodAcctypeDTO.serviceFee" id="serviceFee" ></s:textfield>
														%
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.serviceFee
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
														设置为默认账户：
													</td>
													<td>
													 <s:hidden id="defaultFlag" name="prodAcctypeDTO.defaultFlag" value="%{prodAcctypeDTO.defaultFlag == null ? 0 : 1}"></s:hidden>
                                                     <input type="checkbox" <s:property value="prodAcctypeDTO.defaultFlag == 1 ? 'checked=checked' : ''"/> name="rodAcctypeDTO.defaultFlag" id="defaultFlag"  onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
                                             
														<s:fielderror>
															<s:param>
																prodAcctypeDTO.defaultFlag
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.close()">
				关闭
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub()">
				确定
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
