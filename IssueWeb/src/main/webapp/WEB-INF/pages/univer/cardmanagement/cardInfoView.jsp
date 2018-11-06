<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片信息</title>
		<%@ include file="/commons/meta.jsp"%>

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
			function edit(){
				if(document.getElementById('cardholderId').value==0){
					alert("此卡为礼品卡，没有持卡人信息");
					return;
				}
				document.newForm.action="${ctx}/cardholderManagement/edit.action?cardholderId="+document.getElementById('cardholderId').value;
				
				document.newForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片信息</span>
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
									<span class="TableTop">持卡人信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="" method="post">
								<s:hidden name="cardManagementDTO.cardholderId"
									id="cardholderId" />
								<s:hidden name="cardManagementDTO.cardNo" />
							</s:form>
							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													卡号：
												</td>
												<td>
													<s:label name="cardManagementDTO.cardNo"></s:label>

												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													卡状态：
												</td>
												<td>
													<s:label name="cardManagementDTO.cardstate"></s:label>

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
													卡类型：


												</td>
												<td>
													<dl:dictList displayName="cardType" dictType="102"
														dictValue="${cardManagementDTO.cardType}" tagType="1" />

												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													产品类型：


												</td>
												<td>
													<dl:dictList displayName="prodType" dictType="100"
														dictValue="${cardManagementDTO.prodType}" tagType="1" />
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
													卡有效期：


												</td>
												<td>
													<s:date name="cardManagementDTO.cardValidityPeriod"
														format="yyyy-MM-dd" />
													<input type="hidden"
														name="cardManagementDTO.cardValidityPeriod"
														id="cardValidityPeriod"
														value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													客户名：


												</td>
												<td>
													<s:label name="cardManagementDTO.customerName" />
													<s:fielderror>
														<s:param>
																cardManagementDTO.customerName
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
													特卡人姓名：


												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderName" />
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
												<td style="width: 110px; text-align: right;">
													持卡人工号：


												</td>
												<td>

													<s:label name="cardManagementDTO.externalId" />
													<s:fielderror>
														<s:param>
																cardManagementDTO.externalId
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
													证件类型：


												</td>
												<td>
													<edl:entityDictList displayName="idType" dictType="401"
														dictValue="${cardManagementDTO.idType}" tagType="1" />

												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													证件号：

												</td>
												<td>
													<s:label name="cardManagementDTO.idNo" />
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

							</table>

						</div>
					</td>
				</tr>

			</table>
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
									<span class="TableTop">账户信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm1" name="newForm1"
								action="cardManagement!insertDelay.action?op=8" method="post">

								<table width="100%" style="table-layout: fixed;">
									<s:iterator value="cardManagementDTO.accountDTOs"
										var="accountDTO">
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															账户类型：
														</td>
														<td>
															<s:label name="#accountDTO.accountType"></s:label>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="text-align: right;">
															账户余额：


														</td>
														<td>
															<s:label name="#accountDTO.accBal1" />
															元


														</td>
														<td style="text-align: right;">
															冻结金额：
														</td>
														<td>
															<s:label name="#accountDTO.congeal1" />
															元
														</td>
													</tr>
												</table>
											</td>
											
										</tr>
									</s:iterator>
								</table>
							</s:form>
						</div>
					</td>
				</tr>

			</table>

		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.close();">
				关 闭
			</button>

		</div>
	</body>
</html>