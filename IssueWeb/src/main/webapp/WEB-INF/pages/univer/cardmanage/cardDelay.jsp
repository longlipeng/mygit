<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片延期</title>
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
			function doTest_DeviceKTL512() 
			{
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("passwordVerify").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
				
			}
			function doTest_DeviceKTL511(){
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
			}
			function a(){
				
				if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
					
					document.getElementById("a").style.display='none';
					
				}else{
					
					document.getElementById("a").style.display='';
				}
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
			
			function check() {
				var b=document.getElementById("balance").value;
				var sf=document.getElementById("serviceFee").value;
				if(parseFloat(sf)>parseFloat(b)){
					errorDisplay("服务费大于卡内余额！请确认服务费！");
					return;
				}
				newForm.action='cardManage/cardDelay.action';
				newForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片延期</span>
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
									<span class="TableTop">卡片延期</span>
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
								action="cardManage/cardDelay.action" method="post">
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
														<s:textfield name="cardManagementDTO.transferOutCard" id="cardNo" size="23"></s:textfield>
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
														<span class="no-empty">*</span>卡面有效期：
														<br>

													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardValidityPeriod" id="cardValidityPeriod" readonly="true"></s:textfield>
														
														<s:fielderror>
															<s:param>
																cardManagementDTO.cardValidityPeriod
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>延期月数：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.extensionMonth"/>
														<s:fielderror>
															<s:param>
																cardManagementDTO.extensionMonth
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
														<span class="no-empty">*</span>延期费用：


													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" id='serviceFee' />元
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
								</table>
								<input type="hidden" id="balance" name="cardManagementDTO.balance" value="${cardManagementDTO.balance }" >
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
		
		
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="check();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>

