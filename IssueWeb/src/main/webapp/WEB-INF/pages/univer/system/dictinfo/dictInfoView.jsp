<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参照信息查看</title>
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
			<span>系统参照信息查看</span>
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
									<span class="TableTop">系统参照信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="dictInfo/addDictInfo"
								method="post">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														数据编号：
													</td>
													<td>
														<s:label name="dictInfoDTO.dictCode" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														数据类型名称：
													</td>
													<td>
													<s:label name="dictInfoDTO.dictTypeName"/>																											
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
														名称：
													</td>
													<td>
														<s:label name="dictInfoDTO.dictName"/>																																								
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
														简称：
													</td>
													<td>
														<s:label name="dictInfoDTO.dictShortName"/>																																					
													</td>
												</tr>
										</table>
										<td>
										
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														英文名称：
													</td>
													<td>
														<s:label name="dictInfoDTO.dictEnglishName"/>																						
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
														父字典代码：
													</td>
													<td>
														<s:label name="dictInfoDTO.fatherDictName"/>													
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
														是否可以更新：
													</td>
													<td>
														<s:if test="dictInfoDTO.updateFlag==0">
															不可更新
														</s:if>
														<s:else>
															可以更新
														</s:else>												
													</td>
												</tr>
										</table>
										<td>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														是否有效：
													</td>
													<td>
														<s:if test="dictInfoDTO.dictState==0">
															无效
														</s:if>
														<s:else>
															有效
														</s:else>												
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
				onclick="window.location='dictInfo/inqueryDictInfo.action'">
				返 回
			</button>			
			<div style="clear: both"></div>
		</div>
	</body>
</html>
