<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp" %>
		<script type="text/javascript">
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
				display('QueryBody');
				isDisplayQueryBody = false;
			} else {
				undisplay('QueryBody');
				isDisplayQueryBody = true;
			}
		}
		function edit(){
			var n=0;
			var userId;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					userId=checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='user/loadUser.action?id='+userId;
				document.EditForm.submit();
			}
		}
		function changePassword(){
			var n=0;
			var userId;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					userId=checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择要修改密码的对象');
			}
			if(n>1){
				errorDisplay('修改密码的对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='user/loadName.action?id='+userId;
				document.EditForm.submit();
			}
		}
		function changeAuthPassword(){
			var n=0;
			var userId;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					userId=checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择要修改授权密码的对象');
			}
			if(n>1){
				errorDisplay('修改授权密码的对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='user/loadAuthUser.action?id='+userId;
				document.EditForm.submit();
			}
		}
		
		var n=0;
		function loadissuerGroup(){
			issuerGroupSelect();
			var issuerOption=document.getElementById('issuer').options;
			
			n=0;
		}
	
		function del(){
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要注销的对象');
			}
			if(n>0){
				confirm("确定注销吗？",operation)
				
				
			}
		}
		function operation(){
				EditForm.action='user/delUser.action';
				EditForm.submit();
		}
	</script>
		<style type="text/css">
			#wrap{white-space:normal; width:200px; }
			body,table,td,p,font,select {
				font-size: 9pt;
			}
			
			.bt {
				background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0
					0;
				border: 1px solid #7DE7FD;
				font-size: 9pt;
				height: 22px;
				cursor: pointer;
			}
			
			.bt:HOVER {
				background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll
					0 0;
				border: 1px solid #C3A336;
			}
			
			.btn {
				cursor: pointer;
				border: #FFFFFF 1px solid;
			}
			
			.btn:HOVER {
				background-color: F0FFE1;
				border: #93B3CA 1px solid;
			}
		</style>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>用户管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="queryForm" name="queryForm" action="user/listUser.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>用户编号：</span>
										</td>
										<td width="160">
											<s:textfield name="userQueryDTO.userId"></s:textfield>
											<s:fielderror>
														<s:param>
																userQueryDTO.userId
														</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>用户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="userQueryDTO.userName" />
											<s:fielderror>
														<s:param>
																userQueryDTO.userName
														</s:param>
											</s:fielderror>
										</td>
									
										<td align="center">
											<input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查询"/>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<br>
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
								action="user/listUser.action" method="post">
								<s:hidden name="userQueryDTO.userId"></s:hidden>
								<s:hidden name="userQueryDTO.userName"></s:hidden>
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="user/listUser.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row ondblclick="view('user/viewUser.action',{'id':'${map.userId}'},'');">
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.userId}" />
										</ec:column>
										<ec:column property="userId"   title="用户编号" width="10%">
											<a href="user/viewUser.action?id=${map.userId}">${map.userId}</a>
										</ec:column>								
										<ec:column property="userName"   title="用户名称" width="15%" />									
										<ec:column property="entityId"   title="所属实体" width="15%" />
										<ec:column property="departmentId"   title="部门名称" width="10%">
											<edl:entityDictList dictType="901" tagType="1" dictValue="${map.departmentId}"></edl:entityDictList>
										</ec:column>
										<ec:column property="modifyUser"   title="操作人" width="10%"/>
										<ec:column property="modifyTime"   title="操作日期" cell="date" format="yyyy-MM-dd"  width="10%">					
										</ec:column>
										
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
											 
												<display:security urlId="60104">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="del()">
													注销
												</div>
												</display:security>
												<display:security urlId="60103">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>
												<display:security urlId="60102">
												<div id="modifyBtn" class="btn"
													style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="changePassword();">
													密码重置
												</div>
												</display:security>
												<display:security urlId="60105">
												<div id="modifyBtn" class="btn"
													style="width: 105px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="changeAuthPassword();">
													修改授权密码
												</div>
												</display:security>
												<display:security urlId="60101">
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="EditForm.action='user/addUser.action';EditForm.submit();">
														添加
													</div>												
													<div style="clear: both"></div>
												</div>
												</display:security>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>