<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>参考数据管理</title>
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
			var dictId;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					dictId=checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='dictInfo/loadDictInfo.action?dictId='+dictId;
				document.EditForm.submit();
			}
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
				errorDisplay('请选择要删除的对象');
			}
			
			if(n>0){
				confirm("确定删除吗？",operation);
			}
		}
		
		function operation(){
			EditForm.action='dictInfo/delDictInfo.action';
			EditForm.submit();
		}
	
		function query(){
			EditForm.action='inqueryDictInfo.action';
			EditForm.submit();
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
			<span>参考数据管理</span>
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
							<s:form id="EditForm" name="EditForm" action="inqueryDictInfo.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>数据编号：</span>
										</td>
										<td width="120">
											<s:textfield name="dictInfoQueryDTO.dictCode"></s:textfield>
										</td>
										<td width="150" align=right>
											<span>数据类型编号：</span>
										</td>
										<td width="120">
											<s:textfield name="dictInfoQueryDTO.dictTypeCode" />
										</td>
										<td width="150" align=right>
											<span>数据类型名称：</span>
										</td>
										<td width="120">
											<s:textfield name="dictInfoQueryDTO.dictTypeName" />
										</td>
										</tr>
										<tr>
										<td width="150" align=right>
											<span>数据名称：</span>
										</td>
										<td width="120">
											<s:textfield name="dictInfoQueryDTO.dictName" />
										</td>
										<td width="150" align=right>
											<span>数据英文名称：</span>
										</td>
										<td width="120">
											<s:textfield name="dictInfoQueryDTO.dictEnglishName" />
										</td>
										<td width="150" align=right>
											<span>是否可编辑：</span>
										</td>
										<td width="120">
											<s:select id="issuerGroupId"
												list="#{'':'-请选择-',0:'不可编辑',1:'可以编辑'}"
												name="dictInfoQueryDTO.updateFlag"></s:select>
										</td>
										<td align="center">
											<input type="button" class="bt" style="margin: 5px" onclick="query();" value="查询"/>
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
									<span class="TableTop">参考数据列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
								<s:hidden name="dictInfoQueryDTO.dictId"></s:hidden>		
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="inqueryDictInfo.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									 retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<s:if test="#attr['map'].flag == 1">
											<input type="checkbox" name="choose" value="${map.dictId}" />
											</s:if>
										</ec:column>
										<ec:column property="dictId"   title="数据编号" width="10%" >
											<%--<a href="dictInfo/viewDictInfo.action?key=${map.dictId},${map.dictTypeCode}">${map.dictCode}</a>--%>
											<a href="dictInfo/viewDictInfo.action?dictId=${map.dictId}">${map.dictCode}</a>
										</ec:column>
										<ec:column property="dictTypeCode"   title="数据类型编号" width="12%" />
										<ec:column property="dictTypeName"   title="数据类型名称" width="20%" />
										<ec:column property="dictName"   title="数据名称" width="20%"/>
										<ec:column property="dictEnglishName"   title="数据英文名称" width="15%"/>
										<ec:column property="updateFlag"   title="是否可以编辑" width="10%"/>
										<%--<ec:column property="dictState" title="是否有效" width="15%"/>
									--%>
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
													<display:security urlId="60403">												
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="del()">
													删除
												</div>
												</display:security>
													<display:security urlId="60402">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<display:security urlId="60401">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="EditForm.action='dictInfo/redirectAddDictInfo.action';EditForm.submit();">
														添加
													</div>
													</display:security>
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