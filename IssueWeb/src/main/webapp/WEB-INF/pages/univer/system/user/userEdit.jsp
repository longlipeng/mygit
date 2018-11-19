<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>用户管理</title>
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
			var isDisplayQueryBody = false;
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBody');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBody');
					isDisplayTableBody = true;
				}
			}
			function displayQueryBody() {
				if (isDisplayQueryBody) {
					display('serviceTable');
					isDisplayQueryBody = false;
				} else {
					undisplay('serviceTable');
					isDisplayQueryBody = true;
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
				if(n==1){
					maskDocAll();
					document.newForm.submit();
				}
			}
			function add(){
				var roleId=window.showModalDialog('${ctx}/roleResource/roleListn.action?id=${userDTO.userId}', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(roleId!=null){
					document.getElementById('id').value=roleId;
					newForm.action="user/addRole.action";
					newForm.submit();
				}
			}
			
			function deleteCheck(){debugger;
				var roleBox=document.getElementsByName("rolebox");
				for(var i=0;i<roleBox.length;i++){
				  if(roleBox[i].checked == true){
				         if(roleBox[i].value == 9) {
				         	alert("默认角色不能被删除!");
				         	break;
				        }
				    }
				 }
			    if(i == roleBox.length){
			         	confirm("确定删除吗?",operation)
			      }
			}  
			
			function operation(){
				          EditForm.action='user/delRole.action';
			                EditForm.submit();
		}
	 </script>
	</head>
	
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>用户信息编辑</span>
		</div>

		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="user/editUser.action" method="post">
				<s:token></s:token>
				<input type="hidden" id="id" name="id"/>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayQueryBody();"
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
														<s:textfield name="userDTO.userName" readonly="true" cssClass="readonly"></s:textfield>
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
														<edl:entityDictList dictType="899" tagType="2" dictValue="${userDTO.departmentId}" displayName="userDTO.departmentId" defaultOption="true"></edl:entityDictList>
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
														<s:textfield name="userDTO.email" maxlength="30"></s:textfield>
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
													 <s:select list="#{0:'正常',6:'锁定'}" name="userDTO.lockedState" ></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
										
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
		<div style="width: 100%" align=center>
		
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTableBody();"
									style="cursor: pointer;">
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
						<s:form id="EditForm" name="EditForm"
								action="user/delRole.action" method="post">
								
							<s:hidden id="userDTO.userId" name="userDTO.userId"></s:hidden>
							<ec:table tableId="role" items="userDTO.roleDTOs" var="map" width="100%" form="EditForm"
									action="${ctx}/roleResource/roleListn.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false" sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="rolebox" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="rolebox" value="${map.roleId}" />
										</ec:column>
										<ec:column property="roleId" title="角色号" width="10%" />
										
										<ec:column property="roleName" title="角色名称" width="20%" />
									</ec:row>
								</ec:table>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">

											<div id="deleteBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="deleteCheck();">
												删除
											</div>
											<div id="btnDiv" style="text-align: right; width: 100%">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="add()">
													添加
												</div>
												<div style="clear: both"></div>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</s:form>
							<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		
		</div>
	</body>
</html>