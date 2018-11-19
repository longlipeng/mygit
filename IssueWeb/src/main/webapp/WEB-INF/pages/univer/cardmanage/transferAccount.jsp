<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片转账</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
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
          function getBalance(){
          
          var url='${ctx}/cardManage/accountRltBalance.action';
        ajaxRemote(url,{},successFn,'json');
          }
	function successFn(accountRltBalances){
	
	var transferOutAccount=document.getElementById("transferOutAccount");
	alert(transferOutAccount.value);
          var balance=document.getElementById("balance");
          alert(accountRltBalances);
          var index=transferOutAccount.value;
         balance.value=accountRltBalances[0][index];
	}
	function InCardAcc(){
	
	 var inCardNo=document.getElementById("transferInCard").value;
	  var inCardNoConfirm=document.getElementById("transferInCardConfirm").value;
	  var message=document.getElementById("message");
	 
	if(inCardNoConfirm!=inCardNo){
	 alert(inCardNo);
	message.innerHTML="<font color=\"red\">两次输入的转入卡号不一致</font>";
	return;
	}
	newForm.action='${ctx}/cardManage/inCardRltAccount';
	
	newForm.submit();
	} 
	function forward(){
	newForm.action='cardManage/transferAccount.action';newForm.submit();
	
	}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片转账</span>
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
									<span class="TableTop">卡片转账</span>
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
								action="cardManage/cardQuery.action" method="post">
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
														<span class="no-empty">*</span>转出卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" readonly="true" size="23"></s:textfield>
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
														<span class="no-empty">*</span>转出账户：
														<br>

													</td>
													<td>
													<s:select list="cardManagementDTO.accountList" name="cardManagementDTO.transferOutAccount" id="transferOutAccount"  onchange="getBalance();">
                                   
                                            </s:select>
																										
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
														<span class="no-empty">*</span>可用余额(元)：
														<br>

													</td>
													<td>
														<s:textfield name="cardManagementDTO.balance" id="balance" readonly="true"></s:textfield>
														
														<s:fielderror>
															<s:param>
																cardManagementDTO.balance
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
														<span class="no-empty">*</span>转入卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferInCard" size="23" id="transferInCard" />
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferInCard
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
														<span class="no-empty">*</span>转入卡号确认：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferInCardConfirm" size="23" onchange="InCardAcc();" id="transferInCardConfirm"/>
														
													</td>
													<td><div id="message"></div></td>
												</tr>
											</table>
										</td>
												
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>转入账户：


													</td>
													<td>
														<s:select list="cardManagementDTO.inCardAccounts" name="cardManagementDTO.transferInAccount" id="transferInAccount" />
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferInAccount
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
														<span class="no-empty">*</span>转出卡密码：
														<br>

													</td>
													<td>

														<s:password name="cardManagementDTO.transferOutCardPassword"></s:password>
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferOutCardPassword
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
														<span class="no-empty">*</span>转账金额(元)：
														<br>

													</td>
													<td>
														<s:textfield name="cardManagementDTO.transAmount" id="transAmount" ></s:textfield>
														
														<s:fielderror>
															<s:param>
																cardManagementDTO.transAmount
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
														<span class="no-empty">*</span>转账手续费：
														<br>

													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" id="serviceFee" ></s:textfield>元
														
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
									</s:form>
		</div>
		</td>
		</tr>
		</table>
		</div>
		
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
										<span class="TableTop">转入卡信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
								<ec:table tableId="acctype" items="cardManagementDTO.pageDataDTO.data"
									var="map" width="100%" form="newForm"
									action="cardManage/inCardRltAccount"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row>
										<ec:column property="productName" title="产品名称" width="10%" >										  
										</ec:column>
										<ec:column property="productType" title="产品类型" width="20%" />
										<ec:column property="firstName" title="持卡人名称" width="20%" />
										<ec:column property="idNo" title="持卡人证件号" width="20%" />
										<ec:column property="cardholderMobile" title="持卡人电话" width="20%" />
									</ec:row>
								</ec:table>
						</td>
					</tr>
				</table>
			</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
		
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="forward();">
				转账
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>

