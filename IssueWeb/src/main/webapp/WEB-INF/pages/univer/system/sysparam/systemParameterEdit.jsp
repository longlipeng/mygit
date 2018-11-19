<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参数编辑</title>
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
			function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
					return true;
				else
					return false;
			}
			
			function sub(){
				 maskDocAll();
				newForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>系统参数信息编辑</span>
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
									<span class="TableTop">系统参数信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="systemParameter/updateSystemParameter.action"
								method="post">
								<s:token></s:token>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														代码：
													</td>
													<td>
														<s:label name="entitySystemParameterDTO.parameterCode"></s:label>
														<s:hidden name="entitySystemParameterDTO.parameterCode" ></s:hidden>
														<s:hidden name="entitySystemParameterDTO.dataState"></s:hidden>
													</td>
												</tr>
											</table>
										</td>
										<s:hidden name="entitySystemParameterDTO.entityId" />
		                                <s:hidden name="entitySystemParameterDTO.fatherEntityId" />
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														名称：
													</td>
													<td>
														<s:label name="entitySystemParameterDTO.parameterName" />
														<s:hidden name="entitySystemParameterDTO.parameterName"/>
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
													<span class="no-empty">*</span>	值：
													</td>
													<td>
														<s:textfield name="entitySystemParameterDTO.parameterValue" onkeypress="return check(event)" maxlength="5"/>														
														<s:fielderror>
															<s:param>
																entitySystemParameterDTO.parameterValue
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
														描述：
													</td>
													<td>				
														<s:label name="entitySystemParameterDTO.parameterComment" />
														<s:hidden name="entitySystemParameterDTO.parameterComment"/>																												
													</td>
												</tr>
										</table>
										<td>
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
				onclick="window.location='systemParameter/listSystemParameter.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
