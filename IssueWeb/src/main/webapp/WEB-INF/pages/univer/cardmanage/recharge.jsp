<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>充值管理</title>
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
			function recharge(){
			
			var newForm = Ext.get("newForm").dom;            
			var checkbox=document.getElementsByName("accountId");
             var count=0;
             var sumCreditAmount;
             
             for(var i=0;i<checkbox.length;i++){
            
                   if(checkbox[i].checked==true){
                      count++;
                  
                  }
               }
                if(count<=0)
             {
             alert("必须选择一个账户!");
             return;
             }           
             var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/; 
             var creditAmont=document.getElementsByName("creditAmont");
                   
                     for(var j=0;j<creditAmont.length;j++){
                        if(null!=creditAmont[j].value && ''!=creditAmont[j].value){
	                        if(!doublePatrn.exec(creditAmont[j].value)){
	  								errorDisplay("充值金额输入格式错误，必须是0-99999999.99的数字");
	  								return;
	                         }else{
	                         	sumCreditAmount=creditAmont[j].value;
	                         }
                      }else{
	                      alert("充值金额必须输入！");
	                       return;
                      }
                  
                      if(sumCreditAmount==0){
                       alert("充值金额必须输入！");
                       return;
                        }
                      
                    var onymusState=document.getElementById("onymusState").value;
					var cardholderName=document.getElementById("cardholderName").value;				
	           		
	            if(onymusState==3&&(null==cardholderName||''==cardholderName)){	
				      var returnValue=window.showModalDialog('${ctx}/cardManage/addCardHolder.action?cardManagementDTO.transferOutCard=${cardManagementDTO.transferOutCard}&cardManagementDTO.creditAmont='+sumCreditAmount, '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
				      if(returnValue!=null){				
			          newForm.submit();
					     
					}
					  
		           }
		           else{
		            newForm.submit();
		           }
			}
			}
			function a(){
				var n='${n}';
				if(n!=''&&n!=null){
					document.getElementById("cardNo").readOnly='true';
					document.getElementById("CVV2").readOnly='true';
					document.getElementById("password").readOnly='true';
				}
			}
			
		</script>
		
	</head>
	<body onload="a()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>充值管理</span>
		</div>
		<div id="ContainBox">
		<s:form id="newForm" name="newForm"
								action="cardManage/recharge.action" method="post">	
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
									<span class="TableTop">充值操作</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						
						<div id="serviceTable">
							
								<s:hidden name="cardManagementDTO.agentrName" />
		                        <s:hidden name="cardManagementDTO.agentrCertType" />
		                        <s:hidden name="cardManagementDTO.agentrCertTypeNo" />
		                        <s:hidden name="cardManagementDTO.startDate" />
		                        <s:hidden name="cardManagementDTO.endDate" />   
		                         <s:hidden name="cardManagementDTO.owner" /> 
		                          <s:hidden name="cardManagementDTO.onymusState" id="onymusState"/>   
		                         <s:hidden name="cardManagementDTO.cardholderName" id="cardholderName"/>    					
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" readonly="true" size="23" id="cardManagementDTO.transferOutCard"></s:textfield>
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
														<span class="no-empty">*</span>服务费用：
														

													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee"/>元
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
							
						</div>
					</td>
				</tr>
			</table>
		
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
									<td style="width: 150px; text-align: right;">
									<s:checkbox  name="accountId" fieldValue="%{#balanceDto.accountId}"/><s:label name="#balanceDto.accountType"></s:label>
									</td>
													<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 200px; text-align: right;">
														<span class="no-empty">*</span>充值金额（元）：
													</td>
													<td>
														<s:textfield name="creditAmont"></s:textfield>
														<s:fielderror>
															<s:param>
															creditAmont
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
			</div>

		<div id="btnDiv" style="text-align: right; width: 100%">
		
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="this.disabled='disabled';recharge();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				充值
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
