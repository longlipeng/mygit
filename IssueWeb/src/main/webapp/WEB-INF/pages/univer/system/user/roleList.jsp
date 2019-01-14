<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户角色管理</title>
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
		function sub(){
			var n=0;
			var roleId='';
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					roleId=roleId+','+checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择对象');
			}
			if(n>0){
				window.returnValue=roleId;
				window.close();
			}
		}
		
	
	</script>
		<style type="text/css">
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
			<span>角色管理</span>
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
							<s:form id="queryForm" name="queryForm"
								action="roleResource/roleListn.action" method="post">
								<s:hidden name="roleQueryDTO.userId"></s:hidden>
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
										<td width="150" align=right>
											<span>角色号：</span>
										</td>
										<td width="160">
											<s:textfield name="roleQueryDTO.roleId"></s:textfield>
											<s:fielderror>
														<s:param>
																roleQueryDTO.roleId
														</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>角色名称：</span>
										</td>
										<td width="160">
											<s:textfield name="roleQueryDTO.roleName" />
											<s:fielderror>
														<s:param>
																roleQueryDTO.roleName
														</s:param>
											</s:fielderror>
										</td>
										
										<td align="center">
											<input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查询"/>
										</td>
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
								action="roleResource/roleListn.action" method="post">
								<s:hidden name="roleQueryDTO.roleId"></s:hidden>
								<s:hidden name="roleQueryDTO.roleName"></s:hidden>
								<s:hidden name="roleQueryDTO.userId"></s:hidden>
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="roleResource/roleListn.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.roleId}" />
										</ec:column>
										<ec:column property="roleId" title="角色号" width="15%">
										</ec:column>
										<ec:column property="roleName" title="角色名称" width="25%" />
										<ec:column property="userName" title="操作人" width="20%"/>
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
													onclick="window.close();">
													关闭
												</div>
												
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="sub();">
														确定
													</div>
													<div style="clear: both"></div>
												</div>
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