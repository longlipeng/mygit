<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>统计信息</title>
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
													持卡人姓名：


												</td>
												<td>
													<s:label name="cardManagementDTO.cardholderName"></s:label>



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
													交易系统参考号：
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
													交易金额：


												</td>
												<td>
													<s:label name="riskDTO.amt"></s:label>

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
													商户编号：
												</td>
												<td>
													<s:label name="riskDTO.maId"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													终端编号：


												</td>
												<td>
													<s:label name="riskDTO.posId"></s:label>

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
													交易日期：
												</td>
												<td>
													<s:label name="riskDTO.txnDate" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 110px; text-align: right;">
													风险错误信息：


												</td>
												<td>
													<s:label name="riskDTO.mon"></s:label>
												</td>
												<td style="text-align: right;">
													交易次数：
												</td>
												<td style="text-align: left;">
													<s:label name="riskDTO.count"></s:label>
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
				onclick="history.back(-1);">
				返回
			</button>

		</div>
	</body>
</html>