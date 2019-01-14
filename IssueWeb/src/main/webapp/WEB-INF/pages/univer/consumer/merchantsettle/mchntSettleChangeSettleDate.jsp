<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改结算日期</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}

			function check(nextSetDate){
				var yestoday = new Date(new Date() - 24*60*60*1000);
			   	var day = yestoday.getDate();    
			    var month = yestoday.getMonth() + 1; 
			    var year = yestoday.getYear();
			    var yesDate = year + "-" + month + "-" + day;
				if (nextSetDate.value >= yesDate){
					nextSetDate.value = yesDate;
				}

			}
			function su(){
					newForm.action='${ctx}/merchantSettle/changeSettleDate.action';
					newForm.submit();
			}
			function checkDate(){
			var end="";
			    var enddate = document.getElementById('settleEndDate').value;
			   var enddates= enddate.split('-');
			   for(var i=0;i<enddates.length;i++){
			   		end+=enddates[i];
			   }
			    var startdate = document.getElementById('startDate').value;
		    if (end < startdate) {
			   	alert("结算截止日期不能小于结算起始日期!");
			   	document.getElementById('settleEndDate').value = startdate;
			   }
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>修改结算日期</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">结算日期修改</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="merchantSettle/changeSettleDate">
								<s:hidden name="settleQueryDTO.settleObject2"/>
								<s:hidden name="settleDTO.contractId"></s:hidden>
								<table width="100%" style="table-layout: fixed;" border="0">
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 140px; text-align: right;">
															商户号：
														</td>
														<td>
															<s:hidden  name="settleDTO.settleObject2"  id="merchantId"></s:hidden>
																<s:textfield name="settleDTO.merchantCode" 
																readonly="true"></s:textfield>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 140px; text-align: right;">
															商户名称：
														</td>
														<td>
															<s:textfield name="settleDTO.settleObject2Name"
																id="merchantName" readonly="true"></s:textfield>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 140px; text-align: right;">
															结算金额：
														</td>
														<td>
															<s:text name="settleDTO.settleMoney" />
															<s:hidden name="settleDTO.settleMoney"></s:hidden>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
										<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 140px; text-align: right;">
															结算开始日期：
														</td>
														<td>
														<s:text name="settleDTO.settleStartDate" />

															<s:hidden name="settleDTO.settleStartDate" id="startDate"></s:hidden>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 140px; text-align: right;">
															结算截止日期：
														</td>
														<td>
														<s:text name="settleDTO.settleDate" />
														<s:hidden name="settleDTO.settleDate" id="startDate"></s:hidden>
														</td>
													</tr>
												</table>
											</td>
											<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 140px; text-align: right;">
														<span class="no-empty">*</span>修改结算截止日期为：
													</td>
													<td>
														<input type="text" name="settleEndDate" id="settleEndDate"
															onclick="dateClick(this)" class="Wdate"
															value="${settleEndDate}" />
														<s:fielderror>
															<s:param>
															settleQueryDTO.settleEndDate
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
													<td valign="top" style="width: 140px; text-align: right;">
														<span class="no-empty">*</span>修改原因：
													</td>
													<td>
														<s:textarea name="settleDTO.memo" cols="30" rows="8" id="memo"/>
														<s:fielderror>
															<s:param>
															settleDTO.memo
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<input type="hidden" name="settleQueryDTO.contractId"
									value="${mchntSettleDTO.merchantContractId}" />
								<!--<s:hidden name="mchntSettleDTO.oldSellteDate"
									value="%{mchntSettleDTO.settleEndDate}" />
							-->
							
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='merchantSettle/settlePreview';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="su();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>