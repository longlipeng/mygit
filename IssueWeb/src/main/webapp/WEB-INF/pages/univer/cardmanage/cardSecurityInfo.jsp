<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>安全信息设置管理</title>
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
			function add(){
             
             //add by yy, check null
             if (document.getElementById("password").value == null || document.getElementById("password").value == ''){
             	alert("密码必须输入");
             	return;
             }
             if (document.getElementById("withoutPinAmount").value == null || document.getElementById("withoutPinAmount").value == ''){
             	alert("无pin限额必须输入");
             	return;
             }
             if (document.getElementById("cvv2").value == null || document.getElementById("cvv2").value == ''){
             	alert("cvv2必须输入");
             	return;
             }
             if (document.getElementById("serviceFee").value == null || document.getElementById("serviceFee").value == ''){
             	alert("手续费必须输入");
             	return;
             }
             
             var checkbox=document.getElementsByName("accountId");
             var count=0;
             for(var i=0;i<checkbox.length;i++){
            
                   if(checkbox[i].checked==true){
                    count++;
                   var posSingleAmount=document.getElementById(checkbox[i].value).value;
                   var posDailyAmount=document.getElementById(checkbox[i].value+1).value;
            
                   if(null==posSingleAmount||''==posSingleAmount){
                   alert("POS单笔交易限额必须输入！");
                   return;
                   }
                    if(null==posDailyAmount||''==posDailyAmount){
                   alert("POS单日交易限额必须输入！");
                   return;
                   }
                  }
               }
                if(count<=0)
             {
             alert("必须选择一个账户!");
             return;
             }
					newForm.submit();
			}
			function a(){
				var n='${n}';
				if(n!=''&&n!=null){
					document.getElementById("cardNo").readOnly='true';
					document.getElementById("CVV2").readOnly='true';
					document.getElementById("password").readOnly='true';
				}
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
	newForm.action="${ctx}/cardManage/newCardRltAccount.action";
	
	newForm.submit();
	} 
	
	function changeAmount(){
	 
	 var totalBalance=document.getElementById("totalBalance").value;
	 var distributedAmount=document.getElementById("distributedAmount").value;
	 var undistributedAmount=document.getElementById("undistributedAmount");
	 undistributedAmount.value=totalBalance-distributedAmount;
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
	
	function dealOldCard(){
	 
     			 var type=document.getElementsByName("type");
		        var value = 0;
		        for(i = 0; i <type.length;i++){
		            if(type[i].checked) {
		                value = type[i].value;
		                break;
		            }
		        }
		        if(value==0){
		        	errorDisplay("请选择一种处理原卡方式！");
		        }else{
		        	document.newForm.action='cardManage/dealWithOldCard?cardManagementDTO.flag='+value;
		        	newForm.submit();
		        }
		        }
		        
		        function doTest_DeviceKTL512() 
			{
				cookie();
				if(p2==0){
					alert('请设置串口号');
					return;
				}
				var vData=DeviceKTL512.GetPinEx(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
			}
			function doTest_DeviceCVV512(){
			cookie();
				if(p2==0){
					alert('请设置串口号');
					return;
				}
				var vData=DeviceKTL512.GetPinEx(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("cvv2").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
			}
		</script>
		
	</head>
	<body onload="a()">
		<%@ include file="/commons/messages.jsp"%>
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
			CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<div class="TitleHref">
			<span>安全信息设置管理</span>
		</div>
		<s:form id="newForm" name="newForm"
								action="cardManage/setCardSecurityInfo.action" method="post">
								<s:hidden name="cardManagementDTO.agentrName" />
		<s:hidden name="cardManagementDTO.agentrCertType" />
		<s:hidden name="cardManagementDTO.agentrCertTypeNo" />
		<s:hidden name="cardManagementDTO.startDate" />
		<s:hidden name="cardManagementDTO.endDate" />
		 <s:hidden name="cardManagementDTO.owner" />	
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
									<span class="TableTop">安全信息设置</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>					
							<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" id="transferOutCard" readonly="true" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferOutCard
															</s:param>
														</s:fielderror>
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>密码：
														<br>

													</td>      
													<td>
													<div>
													<s:password  name="cardManagementDTO.password" size="23" id="password">
                                   
                                                   </s:password>
                                                   &nbsp;
                                                   <button class='bt' type="button"															
															onclick="doTest_DeviceKTL512()">
															读取密码
														</button>
													</div>													
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
														<span class="no-empty">*</span>无PIN限额（元）：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.withoutPinAmount" size="23" id="withoutPinAmount"></s:textfield>
														<s:fielderror>
															<s:param>
															cardManagementDTO.withoutPinAmount
															</s:param>
														</s:fielderror>
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>CVV2：
														<br>

													</td>
													<td><div>
													<s:textfield name="cardManagementDTO.cvv2" id="cvv2" size="23"></s:textfield>
														
													&nbsp;
                                                   <button class='bt' type="button"															
															onclick="doTest_DeviceCVV512()">
															读取CVV2
														</button></div>
														
														<s:fielderror>
															<s:param>
																cardManagementDTO.cvv2
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
														<span class="no-empty">*</span>手续费：
														<br>

													</td>
													<td>

														<s:textfield name="cardManagementDTO.serviceFee" size="23" id="serviceFee"></s:textfield>
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
			</td></tr></table>
			</div>
						<br/>
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
									<span class="TableTop">已设安全信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>				
							<table width="100%" style="table-layout: fixed;">
							<s:iterator value="cardManagementDTO.cardSeuriyInfos" var="map">
									<tr>    
									
									<td style="width: 130px; text-align: right;">账户类型：<s:label name="#map.serviceName"></s:label></td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 130px; text-align: right;">
														POS单笔交易限额（元）：
													</td>
													<td>
														<s:label name="#map.posSingleAmount"/>
														
													</td>
												</tr>
											</table>
										</td>




 <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 130px; text-align: right;">
														POS当日交易限额（元）：
													</td>
													<td>
														<s:label name="#map.posDailyAmount"/>
													
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>							
						
									</s:iterator>
													
										
									<tr>
									
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 130px; text-align: right;">
														无PIN限额（元）:
													</td>
													<td>
														<s:label name="cardManagementDTO.withoutPinAmountHis" />
														<s:hidden name="cardManagementDTO.withoutPinAmountHis" />
														</td>
														<td>
													</td>
												</tr>
											</table>
											</td>
									</tr>
								</table>
			</td></tr></table>
			<br/>
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
									<span class="TableTop">选择账户</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>				
							<table width="100%" style="table-layout: fixed;">
							<s:iterator value="cardManagementDTO.cardBalanceDTOs" var="balanceDto">
									<tr>
									<td style="width: 100px; text-align: right;"><s:checkbox  name="accountId" fieldValue="%{#balanceDto.accountId}"/><s:label name="#balanceDto.accountType"></s:label></td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 130px; text-align: right;">
														<span class="no-empty">*</span>POS单笔交易限额（元）：
														

													</td>
													<td>
														<s:textfield name="posSingleAmount" id="%{#balanceDto.accountId}"/>
														<s:fielderror>
															<s:param>
																posSingleAmount
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>




 <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 130px; text-align: right;">
														<span class="no-empty">*</span>POS当日交易限额（元）：
													</td>
													<td>
														<s:textfield name="posDailyAmount" id="%{#balanceDto.accountId+1}"></s:textfield>
														<s:fielderror>
															<s:param>
															posDailyAmount
															</s:param>
														</s:fielderror>
														</td>
														<td>
													</td>
												</tr>
											</table>
										</td>
													
										
										</tr>
									
									</s:iterator>
								</table>
			</td></tr></table>
			</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">

			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="add();">
				确认
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
