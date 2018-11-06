<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>收单机构网站管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
.x-tree-node-icon {
	display: none;
}
</style>
		<script type="text/javascript" charset="UTF-8">
		function sub(){
				document.newForm.action="consumer/modifWebPassword.action";
				document.newForm.submit();
		}
 </script>




	</head>
	<body>
		<div class="TitleHref">
			<span>收单机构网管密码修改</span>
		</div>


		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="editUser.action"
				method="post">
				
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
										<span class="TableTop">收单网站</span>
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
														<span class="no-empty">*</span>收单机构号：
													</td>
													<td>
														<s:textfield name="consumerDTO.entityId" readonly="true"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>收单机构名称：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerName" readonly="true"></s:textfield>
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
														<span class="no-empty">*</span>新网站密码：
													</td>
													<td>
														<s:password name="password" id="password" maxlength="16"></s:password>
														<s:fielderror>
															<s:param>
																password
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>新网站密码确认：
													</td>
													<td>
														<s:password name="repassword" id="repassword" maxlength="16"></s:password>
														<s:fielderror>
															<s:param>
																repassword
															</s:param>
														</s:fielderror>
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
		<div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='consumer/inquery'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="sub()">
				提 交
			</button>
			<div style="clear: both"></div>
			</div>
		</s:form>
		</div>
	</body>
</html>