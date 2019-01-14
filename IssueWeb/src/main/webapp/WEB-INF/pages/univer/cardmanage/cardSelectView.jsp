<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>交易明细</title>
		<%@ include file="/commons/meta.jsp"%>
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
			<span>交易明细</span>
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
													卡类型：


												</td>
												<td>
													<dl:dictList displayName="cardType" dictType="102"
														dictValue="${cardManagementDTO.cardType}" tagType="1" />

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
													持卡人编号：
												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderId"></s:label>

												</td>
											</tr>
										</table>

									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													特卡人姓名：
												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderName"></s:label>

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
													客户编号：
												</td>
												<td>
													<s:label name="cardManagementDTO.customerId"></s:label>

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
														dictValue="${cardManagementDTO.cardholderDTO.idType}" tagType="1" />

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
													<s:label name="cardManagementDTO.cardholderDTO.cardholderidNo" />

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
													手机号码：


												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderDTO.cardholderMobile" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													Email：

												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderDTO.cardholderEmail" />

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
									<span class="TableTop">商户信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">


							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													商户名：
												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.merchantName"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													商户地址：
												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.merchantAddress"></s:label>

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
													联系电话：


												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.telephone"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													网站：
												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.website"></s:label>
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
													传真：


												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.fax"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													发票名称：
												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.invoiceName"></s:label>
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
													门店名：
												</td>
												<td>
													<s:label name="cardManagementDTO.shopDTO.shopName"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													英文名称：
												</td>
												<td>
													<s:label name="cardManagementDTO.merchantDTO.englishName"></s:label>
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
													门店地址：
												</td>
												<td>
													<s:label name="cardManagementDTO.shopDTO.shopAddress"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													门店英文地址：
												</td>
												<td>
													<s:label name="cardManagementDTO.shopDTO.englishAddress"></s:label>
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
													联系电话：
												</td>
												<td>
													<s:label name="cardManagementDTO.shopDTO.telephone"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													传真：
												</td>
												<td>
													<s:label name="cardManagementDTO.shopDTO.fax"></s:label>
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
									<span class="TableTop">交易明细</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">

							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													交易流水：
												</td>
												<td>
													<s:label name="cardManagementDTO.sysSeqNum"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													交易类型：


												</td>
												<td>
													<s:label name="cardManagementDTO.txnType"></s:label>

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
													交易金额：


												</td>
												<td>
													<s:label name="cardManagementDTO.txnAmt"></s:label>
													元
												</td>
											</tr>
										</table>

									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													交易时间：


												</td>
												<td>
													<s:label name="cardManagementDTO.txnDate"></s:label>
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

		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.close();">
				返 回
			</button>

		</div>
	</body>
</html>