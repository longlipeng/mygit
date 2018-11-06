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
		function clickRow(serviceFee){
		   document.getElementById("prodAcctypeDTO.serviceFee").value=serviceFee;
		}
		function sub(){
			var bankId=document.getElementsByName('bankId');
			var id;
			var n=0;
			for(var i=0;i<bankId.length;i++){
				if(bankId[i].checked){
					n++;
					id=bankId[i].value;
				}
			}		
			
			if(n==0){
				errorDisplay('请选择一个银行');
				return;
			}
			document.EditForm.action='addBank.action?acqPayDTO.bank='+id;
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
	<base target="_self">
	</head>
	<body >
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>账户管理</span>
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
								action="serviceQuery.action" method="post">
								<s:hidden name="acqPayDTO.entityId"></s:hidden>
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/acqpay/chooseBank.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" >
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false">
											<input type="radio" name="bankId" value="${map.bankId}"/>
						
										</ec:column>
										<ec:column property="bankId" title="银行帐号" width="30%">
										</ec:column>

										<ec:column property="bankName" title="银行名称" width="40%" />
									</ec:row>
								</ec:table>
									<div id="serviceTable"
										style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; background-color: #FBFEFF;">
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td>
													<table style="text-align: left; width: 100%">

														<tr>
															<td style="width: 120px; text-align: right;">
																<span class="no-empty">*</span>商户号：
															</td>
															<td>
																<s:textfield name="acqPayDTO.mchantCode" id="mchantCode"></s:textfield>
															</td>
															<td>
																<s:fielderror>
																	<s:param>
																		acqPayDTO.mchantCode
																	</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>

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
														onclick="sub()">
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