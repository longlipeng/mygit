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
									<span class="TableTop">流水明细信息</span>
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
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体名称：
												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTNM"></s:label>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体身份证件/证明文件类型：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CITP"></s:label>

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体身份证件/证明文件类型说明：
												</td>
												<td>
													<s:label name="stanStifQueryDTO.CITP_NT"></s:label>

												</td>
											</tr>
										</table>

									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体身份证件/证明文件号码：
												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTID"></s:label>

												</td>
											</tr>
										</table>

									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体的银行账号种类：
												</td>
												<td>
													<s:label name="stanStifQueryDTO.CBAT"></s:label>

												</td>
											</tr>
										</table>

									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体的银行账号：
												</td>
												<td>
													<s:label name="stanStifQueryDTO.CBAC" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体银行账号的开户行名称：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CABM" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体的交易账号种类：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTAT" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体的交易账号：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTAC" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体所在支付机构的名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.CPIN" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体所在支付机构的银行账号：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CPBA" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体所在支付机构的银行账号的开户行名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.CPBN" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体的交易IP地址：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTIP" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易时间：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TSTM" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													货币资金转移方式：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CTTP" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													资金收付标志：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TSDR" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													资金用途：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CRPP" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易币种：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.CRTP" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易金额：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.CRAT" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手姓名/名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCNM" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手特约商户编码：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TSMI" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手证件/证明文件类型：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCIT" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手证件/证明文件类型说明：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCIT_NT" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手证件/证明文件号码：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCID" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手的银行账号种类：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCAT" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手的银行账号：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCBA" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手银行账号的开户行名称：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCBN" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手的交易账号种类：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCTT" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手的交易账号：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCTA" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手所在支付机构的名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCPN" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手所在支付机构的银行账号：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCPA" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手所在支付机构银行账号的开户行名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TPBN" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易对手的交易IP地址：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TCIP" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													交易商品名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.TMNM" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													银行与支付机构之间的业务交易编码：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.BPTC" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													支付机构与商户之间的业务交易编码：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.PMTC" />

												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													业务标识号：


												</td>
												<td>
													<s:label name="stanStifQueryDTO.TICD" />
						
												</td>
											</tr>
										</table>
									</td>
									<td>
										<%-- <table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 150px; text-align: right;font-weight:bold;">
													主体所在支付机构的名称：

												</td>
												<td>
													<s:label name="stanStifQueryDTO.cardholderEmail" />

												</td>
											</tr>
										</table> --%>
									</td>
								</tr>
							
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		 <s:form id="stanStifQueryForm" name="stanStifQueryForm" action="cardManage/stanStifQuery.action" method="post">
		
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				>
				返 回
			</button>

		</div>
		</s:form>
	</body>
</html>