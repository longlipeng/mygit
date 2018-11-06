<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端厂商添加</title>
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
			
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>终端厂商添加</span>
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
									<span class="TableTop">终端厂商添加</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="addPosBrandInf" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>厂商名称：
													</td>
													<td>
														<s:textfield name="posBrandInfDTO.brandName" />
														<s:fielderror>
															<s:param>
																posBrandInfDTO.brandName
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
														<span class="no-empty">*</span>终端厂商主密钥：														
													</td>
													<td>
														<s:textfield name="posBrandInfDTO.tmkKey" maxlength="32" size="45%"/>
														<s:fielderror>
															<s:param>
																posBrandInfDTO.tmkKey
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
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="window.location='inqueryPosBrand.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="this.disabled='disabled';newForm.submit();setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
