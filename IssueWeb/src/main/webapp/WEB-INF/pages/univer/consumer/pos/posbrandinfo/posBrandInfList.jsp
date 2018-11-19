<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端厂商管理</title>
		<%@ include file="/commons/meta.jsp"%>

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
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				alert('请选择要编辑的对象');
			}
			if(n>1){
				alert('编辑对象只能是一个');
			}
			if(n==1){
				document.EditForm.action='loadPosBrandInf.action';
				document.EditForm.submit();
			}
		}
		
		function query() {
			var EditForm = Ext.get("EditForm").dom;
			EditForm.action = 'inqueryPosBrand.action';
			EditForm.submit();
		}
		
		function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
					return true;
				else
					return false;
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
			<span>终端厂商管理</span>
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
								action="inqueryPosBrand.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>终端厂商标识：</span>
										</td>
										<td width="160">
											<s:textfield name="posBrandInfQueryDTO.termBrandId" onkeypress="return check(event);"></s:textfield>
											<s:fielderror>
												<s:param>
													posBrandInfQueryDTO.termBrandId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>厂商名称：</span>
										</td>
										<td width="160">
											<s:textfield name="posBrandInfQueryDTO.brandName" />
											&nbsp;
										</td>
										</tr>
										<tr>
										<td>&nbsp;</td>		
										<td align="right">
											<input type="button" class="bt" style="margin: 5px"
												onclick="query();" value="查 询" />
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
									form="EditForm" action="${ctx}/inqueryPosBrand.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">	
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.termBrandId}" />											
										</ec:column>
										<ec:column property="termBrandId" title="终端厂商编号" width="25%"  >											
										</ec:column>
										<ec:column property="brandName" title="厂商名称" width="40%"  />
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												
												<div id="btnDiv" style="text-align: right; width: 100%">
											<display:security urlId="500070">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="EditForm.action='redirectPosBrandInf.action';EditForm.submit();">
														添加
													</div>
												</display:security>
												<display:security urlId="500071">
												
													<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit();">
													编辑
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