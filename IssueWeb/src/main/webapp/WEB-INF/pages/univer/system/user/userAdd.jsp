<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>用户添加</title>
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
		function displayTableBody() {
			if (isDisplayTableBody) {
				display('serviceTable');
				isDisplayTableBody = false;
			} else {
				undisplay('serviceTable');
				isDisplayTableBody = true;
			}
		}
		function flag(){
			var v=document.getElementById('issuer').value;
			if(v!=''&&v!=null){
				document.getElementById('groupUserFlag').options[1].selected='selected';
			}else{
				document.getElementById('groupUserFlag').options[0].selected='selected';
			}
		}
		var n=1;
		function pwd(){
			var p1=document.getElementById('password1').value;
			var p2=document.getElementById('password2').value;
			if(p1==p2){
				document.getElementById('pwd').style.display='none';
				n=1;
			}else{
				document.getElementById('pwd').style.display='';
				n=2;
			}
		}
		
		function sub(){
			var p1=document.getElementById('userName').value;
			if(p1==""){
				alert("用户名不能为空！");
				return;
			}
			maskDocAll();
			document.newForm.submit();
		}
 </script>




	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>用户信息新增</span>
		</div>

		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="user/inserUser.action"
				method="post">
				<s:token></s:token>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>用户编号：
													</td>
													<td>
														<s:textfield name="userDTO.userId" readonly="true" cssClass="readonly"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>用户名称：
													</td>
													<td>
														<s:textfield name="userDTO.userName" id="userName" maxlength="128"></s:textfield>
														<br/>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>用户密码：
													</td>
													<td>
														<s:password id="password1" name="password"/>
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
														<span class="no-empty">*</span>确认密码：
													</td>
													<td>														
														<s:password id="password2" name="repassword"/>
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
									
									<tr>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>销售标识：
													</td>
													<td>
														<s:select list="#{1:'是',0:'否'}" name="userDTO.isSaleFlage"></s:select>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														部门名称：
													</td>
													<td>
														<edl:entityDictList dictType="899" tagType="2" displayName="userDTO.departmentId" defaultOption="true"></edl:entityDictList>
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
														<span class="no-empty"></span>用户Email：
													</td>
													<td>
														<s:textfield name="userDTO.email" maxlength="128"></s:textfield>
                                                        <s:fielderror>
															<s:param>
																userDTO.userEmail
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
														<span class="no-empty">*</span>锁定状态：
													</td>
													<td>
														<s:select list="#{0:'正常',6:'锁定'}" name="userDTO.lockedState"></s:select>
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
														用户状态：
													</td>
													<td>
														<s:select list="#{1:'有效',0:'无效'}" name="userDTO.userState"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>&nbsp;</td>
									</tr>
								</table>

							</div>
						</td>

					</tr>
				</table>
			</s:form>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='user/listUser.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="sub()">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>