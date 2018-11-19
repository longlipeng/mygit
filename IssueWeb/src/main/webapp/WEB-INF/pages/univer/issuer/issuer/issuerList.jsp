<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

		<title>发行机构管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
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
		function noReport(){
			if (document.forms.EditForm['ec_eti'] != null) {
				document.forms.EditForm['ec_eti'].disabled=true;
	        }
		}
		function add(){
			noReport();
			document.EditForm.action='${ctx}/issuerReAdd.action';
			document.EditForm.submit();
		}
		
		function edit(){
			noReport();
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='issuerEdit.action';
				document.EditForm.submit();
			}
		}
		function modifyPassword(){
			noReport();
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要修改用户密码的对象');
			}
			if(n>1){
				errorDisplay('修改用户密码对象只能是一个');
			}
			if(n==1){
				document.updatePasswordForm.action='issuerModifyPassword.action';
				document.updatePasswordForm.submit();
			}
		}
		function del(){
			noReport();
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
				confirm("确定注销吗？",operation);
			}
		}
		
		function operation(){
			noReport();
			EditForm.action='issuerDelete.action';
			EditForm.submit();
		}
		
		function modifyUserState(){
			noReport();
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要修改用户密码的对象');
			}
			if(n>1){
				errorDisplay('修改用户密码对象只能是一个');
			}
			if(n==1){
				document.updatePasswordForm.action='issuerModifyUserState.action';
				document.updatePasswordForm.submit();
			}
		}
		
		function query(){
				noReport();
				document.EditForm.action='listIssuer.action';
				document.EditForm.submit();
			}
	</script>
<style type="text/css">
	body, table, td, p, font, select {
		font-size: 9pt;
	}
	
	.bt {
		background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0 0;
		border: 1px solid #7DE7FD;
		font-size: 9pt;
		height: 22px;
		cursor: pointer;
	}
	
	.bt:HOVER {
		background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll 0 0;
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
			<span>发行机构管理</span>
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
							<s:form id="EditForm" name="EditForm"
								action="listIssuer" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>发行机构标识：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.entityId"></s:textfield>
											<s:fielderror>
												<s:param>
													issuerQueryDTO.entityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>发行机构名称：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.issuerName" />
											<s:fielderror>
												<s:param>
													issuerQueryDTO.issuerName
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr height="35">
									  <td width="150" align=right>
											<span>发行机构英文名称：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.issuerEnglishName"></s:textfield>
											<s:fielderror>
												<s:param>
													issuerQueryDTO.issuerEnglishName
												</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>发行机构状态：</span>
										</td>
										<td width="160">
									        <s:select list="#{'':'-请选择-',1:'有效',0:'无效'}" name="issuerQueryDTO.dataState"/>
										</td>	
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="query();">
										</td>
									</tr>
								</table>
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
								<ec:table items="pageDataDTO.data" var="map" width="100%" form="EditForm"
									action="${ctx}/listIssuer.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="issuerList.xls" tooltip="导出Excel"/>
									<ec:row>
										<ec:column property="null" alias="choose" title="选择" 
											width="10%"  headerCell="selectAll"  >
											<input type="checkbox" name="choose" value="${map.entityId}" onclick="document.getElementById('userDTO.userId').value=${map.userId};"/>
										</ec:column>
										<ec:column property="entityId" title="发行机构标识" width="20%"  />
										<ec:column property="issuerName" title="发行机构名称" width="20%"  />
										<ec:column property="issuerEnglishName" title="发行机构英文名称" width="20%"  />
										<ec:column property="dataState" title="发行机构状态" width="30%"  />
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
											<display:security urlId="30504">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="del()">
													注销
												</div>
											</display:security>
											<display:security urlId="30503">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>
												<div id="btnDiv" style="text-align: right; width: 100%">
												<display:security urlId="30502">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="add();">
													添加
												</div>
												</display:security>
                                                 <div id="ppBtn" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="modifyPassword();">
													修改用户密码
												 </div>
												 <div id="usBtn" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="modifyUserState();">
													编辑机构用户
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
		 <s:form name="updatePasswordForm" id="updatePasswordForm">
            <s:hidden id="userDTO.userId" name="userDTO.userId"></s:hidden>
        </s:form>
	</body>
</html>