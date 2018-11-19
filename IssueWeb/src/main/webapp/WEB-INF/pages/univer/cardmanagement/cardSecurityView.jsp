<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>限额信息</title>
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
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
	
		<div class="TitleHref">
			<span>限额信息</span>
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
									<span class="TableTop">限额信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="insertSecurity.action?op=6" method="post">
								<s:hidden name="cardManagementDTO.cardNo"></s:hidden>
								<s:hidden name="cardManagementDTO.accountId"></s:hidden>
								<input type="hidden" name="cardManagementDTO.cardValidityPeriod"
									id="cardValidityPeriod" value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>"/>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>POS支付单笔限额：


													</td>
													<td>
														<s:label name="cardManagementDTO.posSingleAmount" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.posSingleAmount
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
														<span class="no-empty">*</span>POS支付日限额：


													</td>
													<td>
														<s:label name="cardManagementDTO.posDailyAmount" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.posDailyAmount
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
														<span class="no-empty">*</span>网上支付单笔限额：


													</td>
													<td>
														<s:label name="cardManagementDTO.webSingleAmount" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.webSingleAmount
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
														<span class="no-empty">*</span>网上支付日限额：


													</td>
													<td>
														<s:label name="cardManagementDTO.webDailyAmount" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.webDailyAmount
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
														<span class="no-empty">*</span>无需密码限额：


													</td>
													<td>
														<s:label name="cardManagementDTO.withoutPinAmount" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.withoutPinAmount
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
			<button class='bt'type="button" style="float: right; margin: 5px" onclick="window.history.go(-1);">
				返 回
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>

