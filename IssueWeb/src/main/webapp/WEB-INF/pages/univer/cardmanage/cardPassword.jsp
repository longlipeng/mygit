<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡密重置</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			function a(){
				
				if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
					document.getElementById("a").style.display='none';
				}else{
					document.getElementById("a").style.display='';
				}
			}
			function submitCheck(){
				
				//var passwordParten=/^[0-9]{6}$/;
				var serviceFeeParten=/^([0]|([1-9]{1}[0-9]{0,8}))$/;
				//var password=document.getElementById("password").value;
				//var passwordVerify=document.getElementById("passwordVerify").value;
					
				var serviceFee=document.getElementById("serviceFee").value;
				var b=document.getElementById("balance").value;
				if(serviceFee == null || serviceFee ==""){
					errorDisplay("服务费必须输入(10位以内正整数)");
					return;
				}
			
				if(!serviceFeeParten.exec(serviceFee)){
					errorDisplay("请输入格式正确的服务费(10位以内正整数)");
					return;
				}
				if(parseFloat(serviceFee)>parseFloat(b)){
					errorDisplay("服务费大于卡内余额！请确认服务费！");
					return;
				}
				/* if(password == null || password ==""){
					errorDisplay("密码必须输入(6位数字)");
					return;
				}
				if(!passwordParten.exec(password)){
					errorDisplay("请输入格式正确的密码(6位数字)");
					return;
				}
				if(passwordVerify == null || passwordVerify ==""){
					errorDisplay("确认密码必须输入(6位数字)");
					return;
				}
				if(password != passwordVerify){
					errorDisplay("确认密码必须和密码一致");
					return;
				} */
			
					document.newForm.submit();
				}
			function readCardNo(){
				var n=document.getElementById("typeName").value;
				if(n==1){
					
					doTest_DeviceKTL656H_Read(); 
				}else if(n==2){
					doTest_DevicePCSC();
				}else{
					doTest_DeviceKTL656();
				}
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡密重置</span>
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
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">卡密重置</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManage/insertPassword.action" method="post">
								<s:token></s:token>	
								<s:hidden name="cardManagementDTO.agentrName" />
		<s:hidden name="cardManagementDTO.agentrCertType" />
		<s:hidden name="cardManagementDTO.agentrCertTypeNo" />
		<s:hidden name="cardManagementDTO.startDate" />
		<s:hidden name="cardManagementDTO.endDate" />	
		 <s:hidden name="cardManagementDTO.owner" />					
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" id="transferOutCard" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferOutCard
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>服务费用：


													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" id="serviceFee" />元
														<s:fielderror>
															<s:param>
																cardManagementDTO.serviceFee
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<!-- <td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>新密码设定： -->


													</td>
													<td>
														<%-- <s:password name="cardManagementDTO.newPassword"
															id="password" maxlength="6" size="24"/> --%>

														<%-- <s:fielderror>
															<s:param>
																cardManagementDTO.newPassword
															</s:param>
														</s:fielderror> --%>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<!-- <td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>确认新密码：
													</td> -->
													<td>
														<%-- <s:password name="passwordVerify" id="passwordVerify" maxlength="6" size="24"/>
														<label id="a" style="color: red; display: none;">
															两个密码输入不一致
														</label>
														<s:fielderror>
															<s:param>
																passwordVerify
															</s:param>
														</s:fielderror> --%>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
		<input type="hidden" id="balance" name="cardManagementDTO.balance" value="${cardManagementDTO.balance }" >
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="submitCheck();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>

