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
			<span>风险统计信息</span>
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
									<span class="TableTop">风险统计信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">

							<s:form action="setRiskSvcCtrl.action" method="post" name="editForm" id="editForm">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														单笔金额超限次数：
													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCount">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCount
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
														单笔超限金额：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntPerover">
														</s:textfield>元<br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntPerover
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio  name="riskSvcCtrlDTO.maxCntPeroverStat" list="#{1:'显示',0:'不显示'}"/>
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
														总失败交易数：
													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntFail">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntFail
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntFailStat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														异常时间交易次数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEtime">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEtime
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEtimeStat"  list="#{1:'显示',0:'不显示'}"/>
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
														卡制作状态错误次数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEcteate">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEcteate
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEcteateStat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>

										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡激活状态错误次数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEact">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEact
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEactStat" list="#{1:'显示',0:'不显示'}"/>
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
														卡锁定状态错误次数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntElock">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntElock
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntElockStat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡挂失状态错误次数：

													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntElost">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntElost
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntElostStat" list="#{1:'显示',0:'不显示'}"/>
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
														当天最大限额：
													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxAmt">
														</s:textfield>元<br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxAmt
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxAmtStat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														CVV错误次数：

													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEcvv">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEcvv
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEcvvStat" list="#{1:'显示',0:'不显示'}"/>
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
														CVV2错误次数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEcvv2">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEcvv2
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEcvv2Stat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														PIN错误次数：

													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntEpin">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntEpin
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntEpinStat" list="#{1:'显示',0:'不显示'}"/>
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
														最大日正常交易数：


													</td>
													<td>
														<s:textfield name="riskSvcCtrlDTO.maxCntUtime">
														</s:textfield><br/>
														<s:fielderror>
															<s:param>
																riskSvcCtrlDTO.maxCntUtime
															</s:param>
														</s:fielderror>
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.maxCntUtimeStat" list="#{1:'显示',0:'不显示'}"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														是否显示两小时内跨地区交易
													</td>
													<td align="right">
														<s:radio name="riskSvcCtrlDTO.txnEctiyStat" list="#{1:'显示',0:'不显示'}"/>
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
			<button class='bt' style="float: right; margin: 5px 10px" onclick="editForm.submit();">
				保存
			</button>

		</div>
	</body>
</html>