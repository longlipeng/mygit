<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参数管理</title>
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
			var paramCode;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					paramCode=checkbox[i].value;
				}
			}
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				document.queryForm.action='systemParameter/editSystemParameter.action?entitySystemParameterDTO.parameterCode='+paramCode;
				document.queryForm.submit();
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
			<span>系统参数管理</span>
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
								action="systemParameter/listSystemParameter.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>参数代码：</span>
										</td>
										<td width="120">
											<s:textfield name="entitySystemParameterQueryDTO.parameterCode"></s:textfield>
										</td>
										<td width="150" align=right>
											<span>参数名称：</span>
										</td>
										<td width="120">
											<s:textfield name="entitySystemParameterQueryDTO.parameterName" />
										</td>
										</tr>
										<tr>
										<td width="150" align=right>
											<span>参数值：</span>
										</td>
										<td width="120">
											<s:textfield name="entitySystemParameterQueryDTO.parameterValue" />
										</td>
										<td width="150" align=right>
											<span>描述：</span>
										</td>
										<td width="120">
											<s:textfield name="entitySystemParameterQueryDTO.parameterComment" />
										</td>
										<td align="center">
											<input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查询"/>
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
									<span class="TableTop">参数列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">						
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="queryForm" action="systemParameter/listSystemParameter.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									 retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.parameterCode}" />
										</ec:column>
										<ec:column property="parameterCode"  title="参数代码" width="25%">
											<a href="systemParameter/viewSystemParameter.action?parameterCode=${map.parameterCode}">${map.parameterCode}</a>
										</ec:column>
										<ec:column property="parameterName"  title="参数名称" width="20%" />
										<ec:column property="parameterValue"  title="参数值" width="20%"/>
										<ec:column property="parameterComment"  title="描述" width="20%"/>
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
													<display:security urlId="60301">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>
												<div style="clear: both"></div>
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