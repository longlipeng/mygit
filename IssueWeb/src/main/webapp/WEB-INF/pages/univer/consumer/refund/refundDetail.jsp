<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>退货申请信息详情</title>
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
			
			function submit()
			{
				newForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>退货申请信息详情</span>
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
									<span class="TableTop">退货申请信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="refund/refundVerify.action"
								method="post">
								<s:hidden name="refundDTO.transChnlFlag"></s:hidden>
								<table width="200%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														收单机构号：
													</td>
													<td>
														<s:label name="refundDTO.acqInsCd"></s:label>													
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														商户号：
													</td>
													<td>
														<s:label name="refundDTO.mchntCd"/>
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
														退货申请时间：
													</td>
													<td >
														<s:label name="refundDTO.reqDate"/>
													</td>
												</tr>
										</table>
										</td>
										<td>
										<table style="text-align: left; width: 100%">
													<td style="width: 110px; text-align: right;">
														中心流水号：
													</td>
													<td>
														<s:label name="refundDTO.orderId"/>
													</td>
										</table>
										</td>
									</tr>
									<tr>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														交易时间：
													</td>
													<td>
														<s:label name="refundDTO.transDate"/>
													
													</td>
												</tr>
										</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														退货金额(元)：
													</td>
													<td>
														<s:label name="refundDTO.strTransAt"/>
													</td>
												</tr>
											</table>
										<td>
									</tr>
									<tr>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														退货原因：
													</td>
													<td>
														<s:label name="refundDTO.refundRsn"/>
													
													</td>
												</tr>
										</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														原交易金额(元)：
													</td>
													<td>
														<s:label name="refundDTO.strOrigTransAt"/>
													</td>
												</tr>
											</table>
										<td>
									</tr>
									<tr>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														累计退货金额(元)：
													</td>
													<td>
														<s:label name="refundDTO.strRefundAt"/>
													
													</td>
												</tr>
										</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														审核状态：
													</td>
													<td>
														<s:label name="refundDTO.authRlt"/>
													</td>
												</tr>
											</table>
										<td>
									</tr>
									<tr>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														审核意见：
													</td>
													<td>
														<s:select id="authAction"
												list="#{0:'同意',1:'拒绝'}"
												name="authAction"></s:select>
													
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
														拒绝原因：
													</td>
													<td>
														<s:textarea style="width:100%;" name="refundDTO.authDesc"></s:textarea>
													</td>
												</tr>
											</table>
										<td>
									</tr>

								</table>
							</s:form>

					</td>
				</tr>	
			</table>																																																							
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="submit()">	
				确 认
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
