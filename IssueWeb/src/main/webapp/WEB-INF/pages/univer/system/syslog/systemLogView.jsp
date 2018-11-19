<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统日志信息详情</title>
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
			<span>系统日志信息详情</span>
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
									<span class="TableTop">系统日志信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="udpateSysParameter.action"
								method="post">
								<table width="200%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														日志编号：
													</td>
													<td>
														<s:label name="systemLogDTO.logId"></s:label>													
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														编码：
													</td>
													<td>
														<s:label name="systemLogDTO.txnCode"/>
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
														操作描述：
													</td>
													<td style="color:red">
														<s:label name="systemLogDTO.operationMemo"/>
													</td>
												</tr>
										</table>
										<td>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														是否成功：
													</td>
													<td>
														<s:select list="#{1:'成功',0:'失败'}"
															name="systemLogDTO.successFlag"  disabled="true"/>
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
														操作时间：
													</td>
													<td>
														<s:label name="systemLogDTO.operationTime"/>
													
													</td>
												</tr>
										</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														操作人：
													</td>
													<td>
														<s:label name="systemLogDTO.operationUserName"/>
													</td>
												</tr>
											</table>
										<td>
									</tr>
									<tr>									
									<td>
									<div style="display: none">
										<table style="text-align: left; width: 200%">
											<tr>
											<td style="width: 110px; text-align: right;">
											操作说明：
											</td>
											
												<td>			
													<s:textarea name="systemLogDTO.txnContent" cols="48" rows="10"/>	
												</td>
											</tr>
										</table>
									</div>	
							</s:form>

					</td>
				</tr>	
			</table>																																																							
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.location='systemLog/inquerySysLog.action'">
				返 回
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
