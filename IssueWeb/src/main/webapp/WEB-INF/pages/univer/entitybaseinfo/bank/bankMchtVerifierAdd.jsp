<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增银行账户信息(商户审核)</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"></base>
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
			function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8 || key.keyCode== 45)
					return true;
				else
					return false;
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>商户复核新增银行信息</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">银行信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="">
								<s:hidden name="entityId" />
								<s:hidden name="merchantDTO.entityId"></s:hidden>
								<s:hidden name="bankDTO.bankId"></s:hidden>
								<s:hidden name="bankDTO.entityId" id="bankDTO.entityId"/>
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>银行名称：
													</td>
													<td>
														<s:textfield name="bankDTO.bankName"
															id="bankDTO.bankName"></s:textfield>
														<s:fielderror>
															<s:param>
																bankDTO.bankName
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
														<span class="no-empty">*</span>银行账户：
													</td>
													<td>
														<s:textfield name="bankDTO.bankAccount"
															id="bankDTO.bankAccount"></s:textfield>
														<s:fielderror>
															<s:param>
																bankDTO.bankAccount
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
														<span class="no-empty">*</span>银行账户名称：
													</td>
													<td>
														<s:textfield name="bankDTO.bankAccountName"
															id="bankDTO.bankAccountName"></s:textfield>
														<s:fielderror>
															<s:param>
																bankDTO.bankAccountName
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
														银行账户外部系统号：
													</td>
													<td>
														<s:textfield name="bankDTO.bankAccountCode"
															id="bankDTO.bankAccountCode"></s:textfield>
														<s:fielderror>
															<s:param>
																bankDTO.bankAccountCode
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
														是否设为默认银行账户：
													</td>
													<td>
														<s:radio list="#{'0':'否','1':'是'}" name="bankDTO.accountFlag"></s:radio>
													</td>
												</tr>
										  </table>
										</td>
										<td>
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.close()">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="document.newForm.action='${ctx}/merchantVerifier/insertBank.action';newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>