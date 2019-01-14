<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>换卡管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
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
              var accId=document.getElementById("accId");
              if(null==accId){
              alert("必须输入新卡相应的信息！");
              return;
              }
              else if(null==accId.value){
              alert("必须选择新卡账户！");
              return;
              }
               var onymusState=document.getElementById("onymusState").value;
			   var cardholderName=document.getElementById("incardholderName").value;
							
	            if(onymusState==3&&(null==cardholderName||''==cardholderName)){	
				      var returnValue=window.showModalDialog('${ctx}/cardManage/addCardHolder.action?cardManagementDTO.transferInCard=${cardManagementDTO.transferInCard}&cardManagementDTO.flag=1', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
				      if(returnValue!=null){				
			          document.newForm.action="cardManage/changeCard.action?cardManagementDTO.transferInAccount="+accId.value;
					  newForm.submit();
					 }
					  
		           }
		       else{
		           document.newForm.action="cardManage/changeCard.action?cardManagementDTO.transferInAccount="+accId.value;
				   newForm.submit();
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
	   
          var balance=document.getElementById("balance");
        
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
		</script>
		
	</head>
	<body onload="a()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>换卡管理</span>
		</div>
		<s:form id="newForm" name="newForm"
								action="" method="post">
								<s:hidden name="cardManagementDTO.agentrName" />
		<s:hidden name="cardManagementDTO.agentrCertType" />
		<s:hidden name="cardManagementDTO.agentrCertTypeNo" />
		<s:hidden name="cardManagementDTO.startDate" />
		<s:hidden name="cardManagementDTO.endDate" />
		 <s:hidden name="cardManagementDTO.owner" />
		 <s:hidden name="cardManagementDTO.onymusState" id="onymusState"/>   
		 	
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
									<span class="TableTop">换卡操作</span>
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
														<span class="no-empty">*</span>原卡号：
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
														<span class="no-empty">*</span>原卡账户：
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
														<s:textfield name="cardManagementDTO.balance" id="balance" readonly="true" size="23"></s:textfield>
														
														<s:fielderror>
															<s:param>
																cardManagementDTO.balance
															</s:param>
														</s:fielderror>
													</td>	
													</tr>
													</table>												
								
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>手续费：
														

													</td>
													<td>
														<s:textfield name="cardManagementDTO.serviceFee" size="23"/>元
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>新卡卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferInCard" id="transferInCard" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
															cardManagementDTO.transferInCard
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
														<span class="no-empty">*</span>新卡卡号确认：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferInCardConfirm" id="transferInCardConfirm" onchange="InCardAcc();" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
															cardManagementDTO.transferInCardConfirm
															</s:param>
														</s:fielderror>
														</td>
														<td>
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
														<span class="no-empty">*</span>新卡产品名称：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.prodName" readonly="true" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
															
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
														<span class="no-empty">*</span>新卡产品类型：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.prodType" readonly="true" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
															
															</s:param>
														</s:fielderror>
														</td>
														<td>
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
														<span class="no-empty">*</span>原卡密码：
														<br>

													</td>
													<td>

														<s:password name="cardManagementDTO.transferOutCardPassword" size="23"></s:password>
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
								</table>
								</br>
								</br>
								<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									style="cursor: pointer;">
									<span class="TableTop">持卡人信息</span>
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>持卡人姓名：

													</td>
													<td>
														<s:textfield name="cardManagementDTO.incardholderName" id="incardholderName"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>手机号码：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.mobile"></s:textfield>

													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
										
												<tr>
													<td style="width: 140px; text-align: right">
														<span class="no-empty">*</span>证件类型：
													</td>
													<td>
														<s:select list="#{'00':'身份证','01':'护照','10':'出生证','11':'工作证'}" name="cardManagementDTO.idType" id="idType" ></s:select>
													
													</td>
												</tr>
											</table>
										</td>
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>证件号码：
													</td>
													<td>														
														<s:textfield name="cardManagementDTO.idNo"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
			               </table>
			               </td></tr></table>
			               <table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="center" valign="middle">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">新卡账户明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>	 
							<ec:table tableId="acctype" items="cardManagementDTO.pageDataDTO.data"					
									var="map" width="100%" form="EditForm"
									action="cardManage/newCardRltAccount.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="acctypebox" title=" "
											width="10%" sortable="false">
											<input type="radio" name="acctypebox" id="accId"
												value="${map.accId}" />
			
										</ec:column>
										<ec:column property="accName" title="账户名称" width="20%" />
										<ec:column property="balace" title="金额" width="20%" />
									
									</ec:row>
								</ec:table>

				
					</td>
				</tr>
			</table>
			
			</td></tr></table>
			</div>
			</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			
			<input type="radio" name="type"
				value="1" ${cardManagementDTO.disableFlag}>
				原卡入库
			
			<input type="radio" name="type" value="2" ${cardManagementDTO.disableFlag}>
				原卡销毁
			
			
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="dealOldCard();" ${cardManagementDTO.disableFlag}>
				原卡操作确定
			</button>
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="add();">
				换卡
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
