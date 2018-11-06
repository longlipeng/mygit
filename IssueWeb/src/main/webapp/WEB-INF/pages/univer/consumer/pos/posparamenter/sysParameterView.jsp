<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端系统参数编辑</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>终端系统参数</span>
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
									<span class="TableTop">终端系统参数</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="updateSys" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>版本号：
													</td>
													<td>
														<s:label name="posParameterDTO.prmVersion"/>														
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
														参数标识：
													</td>
													<td>
														<s:label name="posParameterDTO.prmId"/>
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														参数名称：
													</td>
													<td>																	
														<s:label name="check(posParameterDTO.prmName)"/>
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
														参数类型：														
													</td>
													<td>
														<dl:dictList displayName="sysParameterDTO.prmType"
												dictValue="${posParameterDTO.prmType}" dictType="800"
												tagType="1"  />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
															<span class="no-empty">*</span>参数状态：
													</td>
													<td>
														<s:if test="posParameterDTO.prmType==1">
															 无效
														</s:if>
														<s:else>
															有效
														</s:else>														
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
														<span class="no-empty">*</span>参数值：
													</td>
													<td>
														<s:label name="posParameterDTO.prmVal"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>参数长度：
													</td>
													<td>
														<s:label name="posParameterDTO.prmLen"/>																																										
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
													参数描述：
													</td>
													<td>
														<s:label name="posParameterDTO.prmDesc"/>																							
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
				onclick="window.location='terParameter/inqueryPrm.action'">
				返 回
			</button>			
			<div style="clear: both"></div>
		</div>
	</body>
</html>
