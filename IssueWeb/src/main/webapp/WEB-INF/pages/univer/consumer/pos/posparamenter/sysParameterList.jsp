<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参数管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		
		<script type="text/javascript">
			function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
					return true;
				else
					return false;
			}
			function query(){
				document.EditForm.action='${ctx}/terParameter/inqueryPrm.action';
				document.EditForm.submit();
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
			<span>终端管理</span>
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
								action="terParameter/inqueryPrm.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>参数标识：</span>
										</td>
										<td width="160">
											<s:textfield name="posParameterQueryDTO.prmId" onkeypress="return check(event);"></s:textfield>
											<s:fielderror>
												<s:param>
													posParameterQueryDTO.prmId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>参数名称：</span>
										</td>
										<td width="160">
											<s:textfield name="posParameterQueryDTO.prmName" />
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>参数版本号：</span>
										</td>
										<td width="160">
											<s:textfield name="posParameterQueryDTO.prmVersion" onkeypress="return check(event);" />
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="150" align=right>
											<span>参数状态：</span>
										</td>
										<td width="120">
											<s:select id="issuerGroupId"
												list="#{'':'-请选择-',1:'无效',0:'有效'}"
												name="posParameterQueryDTO.prmStat"></s:select>
										</td>
										<td width="150" align=right>
											<span>参数类型：</span>
										</td>
										<td width="120">
											<dl:dictList displayName="posParameterQueryDTO.prmType" dictValue="${posParameterQueryDTO.prmType}" dictType="800" tagType="2" defaultOption="true" />
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
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/terParameter/inqueryPrm.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="key"
												value="${map.prmId},${map.prmType},${map.prmVersion}" />
										</ec:column>
										<ec:column property="prmId" title="参数标识" width="10%" >
											<a
												href="terParameter/viewPrm.action?key=${map.prmId},${map.prmType},${map.prmVersion}">${map.prmId}</a>
										</ec:column>
										<ec:column property="prmName" title="参数名称" width="20%" cell="sysParameter" />
										<ec:column property="prmValue" title="参数值" width="15%" >
										</ec:column>
										<ec:column property="prmType" title="参数类型" width="15%"
											cell="prmType" >
										</ec:column>
										<ec:column property="prmState" title="参数状态" width="15%" />
										<ec:column property="prmVersion" title="版本号" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td align="right">
											<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<display:security urlId="500051">
													<input type="button" class="btn"
														style="width: 90px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
														onclick="submitForm('EditForm', '${ctx}/terParameter/redirectAddPrm.action', 'add')"
														value="添加公共参数" />
												</display:security>
											</td>
											<td>
												<display:security urlId="500052">
												<input type="button" class="btn"
														style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
														onclick="submitForm('EditForm', '${ctx}/terParameter/redirectAddkaBin.action', 'add')"
														value="添加卡BIN参数" />
												</display:security>
											</td>
											<td>
												<display:security urlId="500053">
												<input type="button" class="btn"
														style="width: 90px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
														onclick="submitForm('EditForm', '${ctx}/terParameter/redirectAddIcCard.action', 'add')"
														value="添加IC卡参数" />
												</display:security>
											</td>
											
											<td>								
															<display:security urlId="500054">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="submitForm('EditForm', '${ctx}/terParameter/loadPrm.action', 'edit', 'key')"
													value="编辑" />
											</display:security>
										</td>																
									</tr>
								</table>									
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>