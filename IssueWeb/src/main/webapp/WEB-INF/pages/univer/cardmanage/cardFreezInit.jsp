<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片冻结</title>
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
			function add(){

					newForm.submit();
		
			}
			function a(){
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
			<span>卡片冻结</span>
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
									<span class="TableTop">卡片冻结</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManagea/cardFreez.action" method="post">
                                 <s:hidden name="cardManagementDTO.password"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cvv2"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
                                  <s:hidden name="cardManagementDTO.cardValidityPeriod" />
							     <s:hidden name="cardManagementDTO.cardstate"></s:hidden>
							    	<!--      <s:hidden name="cardManagementDTO.cardBalanceDTOs"></s:hidden>	-->							
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardNo" id="cardNo" readonly="true" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.cardNo
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
														<span class="no-empty">*</span>服务费用：
														

													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" />分
														<s:fielderror>
															<s:param>
																cardManagementDTO.serviceFee
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
		<s:hidden name="cardManagementDTO.agentrName" />
		<s:hidden name="cardManagementDTO.agentrCertType" />
		<s:hidden name="cardManagementDTO.agentrCertTypeNo" />
		<s:hidden name="cardManagementDTO.startDate" />
		<s:hidden name="cardManagementDTO.endDate" />
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="add();">
				冻 结
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
