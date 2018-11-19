<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>用户授权密码验证</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
.x-tree-node-icon {
	display: none;
}
</style>
		<script type="text/javascript" charset="UTF-8">
		var isDisplayTableBody = false;
		function displayServiceTable() {
			if (isDisplayTableBody) {
				display('serviceTable');
				isDisplayTableBody = false;
			} else {
				undisplay('serviceTable');
				isDisplayTableBody = true;
			}
		}
		
		function sub(){
		        var authPassward = document.getElementById("authPassword").value;
		        if( authPassward==null||authPassward=="")
		        {
		           errorDisplay("授权密码不能为空!");
			       return;
		        }
				document.newForm.action="user/inspectAuthPassword.action";
				document.newForm.submit();
		      
		}
 </script>




	</head>
	<body>
	<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>用户授权密码验证</span>
		</div>


		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="editUser.action"
				method="post">
				<s:hidden name="userDTO.userId" />
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
										<span class="TableTop">用户信息</span>
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>用户名称：
													</td>
													<td>
														<s:textfield name="userDTO.userName" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																userDTO.userName
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>授权密码：
													</td>
													<td>
														<s:password name="authPassword" id="authPassword"></s:password>
														<s:fielderror>
															<s:param>
																authPassword
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
			<!-- 
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.close()">
				关闭
			</button>
			 -->
			<button class='bt' style="float: right; margin: 5px" onclick="sub()">
				提 交
			</button>
			<div style="clear: both"></div>
			</div>
		</s:form>
		</div>
	</body>
</html>