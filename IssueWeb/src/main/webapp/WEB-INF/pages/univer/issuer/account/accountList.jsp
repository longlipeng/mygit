<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>账户管理</title>
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
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				EditForm['ec_eti'].disabled=true;
				document.EditForm.action='accountLoad.action';
				document.EditForm.submit();
			}
		}
		function igid(){
			var sel=document.getElementById("issuerGroupId");
			var selectvalue=sel.value;
			if(selectvalue!=''){
				
				var groupvalue=sel.options[1].value;
				document.getElementById("issuerGroup").value=groupvalue.substring(1);
				
					if(selectvalue==groupvalue){
						
						document.getElementById("issuer").value=0;
					}else{
						
						document.getElementById("issuer").value=selectvalue.substring(1);
					}
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
				confirm("确定删除吗?",operation);
			}
		}
		function operation(){
			EditForm['ec_eti'].disabled=true;
			EditForm.action='deleteAccount.action';
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
			<span>账户管理</span>
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
								action="accountInquery" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>账户号：</span>
										</td>
										<td width="160">
											<s:textfield name="acctypeQueryDTO.serviceId"></s:textfield>
											<s:fielderror>
														<s:param>
																acctypeQueryDTO.serviceId
														</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>账户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="acctypeQueryDTO.serviceName" />
											
										</td>
<!-- 
										<td width="150" align=right>
											<span>发行机构：</span>
										</td>
										<td width="120">
										  <s:select id="issuerGroupId" 
												list="entityList"
												name="acctypeQueryDTO.entityIdStr" 
												listKey="entityId"
												listValue="entityName"
											    headerKey="" headerValue="--请选择--" ></s:select>
		
										</td>
										 -->
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="EditForm['ec_eti'].disabled=true;queryForm.submit();">
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
								action="accountInquery" method="post">	
								<s:token></s:token>																
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/accountInquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="issuerList.xls" tooltip="导出Excel"/>
									<ec:row>
										<ec:column property="null" alias="choose" title="选择" 
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.serviceId}" />
										</ec:column>
										<ec:column property="serviceId" title="账户编号" width="30%"  >
										     <a href="accountView.action?id=${map.serviceId}">
                                               <s:property value="#attr['map'].serviceId" />
                                            </a>
										</ec:column>

										<ec:column property="serviceName" title="账户名称" width="40%"  />
										<ec:column property="entityName" title="发行机构" width="30%"   />
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<display:security urlId="30304">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="del()">
													删除
												</div>
											</display:security>
												<display:security urlId="30303">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>
												<display:security urlId="30302">
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="EditForm['ec_eti'].disabled=true;EditForm.action='accountInsert.action';EditForm.submit();">
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
