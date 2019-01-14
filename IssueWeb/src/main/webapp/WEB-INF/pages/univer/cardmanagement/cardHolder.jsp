<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>持卡人卡信息</title>
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
			var id;
			var checkbox=document.getElementsByName('cardRadio');
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				alert('请选择要挂失的卡号');
			}
			if(n>1){
				alert('编辑对象只能是一个');
			}
			if(n==1){
				
				document.EditForm.action='cardManagement!insertInit.action?op=3&cardNo='+id;
				document.EditForm.submit();
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
			<span>持卡人卡信息</span>
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
								action="cardHolder.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>卡号：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.cardNo" size="23"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.cardNo</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>客户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.customerName"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.customerName</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>持卡人名：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.cardholderName"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.cardholderName</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr height="35">
										<td width="150" align=right>
											<span>持卡人证件号：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.idNo"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.idNo</s:param>
											</s:fielderror>
										</td>

										<td width="150" align=right>
											<span>手机号：</span>
										</td>
										<td width="160">
											<s:textfield name="cardManagementQueryDTO.mobile"></s:textfield>
											<s:fielderror>
												<s:param>cardManagementQueryDTO.mobile</s:param>
											</s:fielderror>
										</td>
										
										<td align="right" colspan="2">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="queryForm.submit();">
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
									<span class="TableTop">卡片列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
							<s:form action="cardHolder.action" name="EditForm" method="post">
								<div id="list"
									style="border: 1px solid #B9B9B9; margin-top: 5px;">
									<div id="listTitle"
										style="font-weight: bold; height: 19px; background-color: #DFE8F6;">

										<span>卡号信息</span>

									</div>
									
									<s:hidden name="cardManagementQueryDTO.cardNo"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.customerName"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.idNo"></s:hidden>
									<s:hidden name="cardManagementQueryDTO.cardholderName"></s:hidden>
									<ec:table items="pageDataDTO.data" var="map" width="100%"
										form="EditForm" action="cardHolder.action"
										
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										<ec:row onclick="">
											<ec:column property="null" alias="choose" title="选择"
												width="10%" sortable="false">
												<input type="radio" name="cardRadio" id="cardRadio"
													value='${map.cardNo}' />

											</ec:column>
											<ec:column property="cardNo" title="卡号"  width="20%"   escapeAutoFormat="true"/>
											<ec:column property="cardholderName" title="持卡人" width="10%" />
											<ec:column property="idNo" title="证件号码" width="20%"  escapeAutoFormat="true"/>
											<ec:column property="mobile" title="手机号码" width="10%" />
											<ec:column property="customerName" title="客户名" width="20%" />
										</ec:row>
									</ec:table>
								</div>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												
													<div id="btnDiv" style="text-align: right; width: 100%">
														<div id="addBtn" class="btn"
															style="width: 70px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
															onclick="edit();">
															挂失卡
														</div>
											
												<div style="clear: both"></div>
											</div>
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
	</body>
</html>