<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片安全设置</title>
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
			function edit(){
			var n=0;
			var id;
			var checkbox=document.getElementsByName('txnRadio');
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				alert('请选择要修改的账户号');
			}
			if(n>1){
				alert('编辑对象只能是一个');
			}
			if(n==1){				
				document.txnForm.action='cardManage/editCardSecurity';
				document.txnForm.submit();
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
			<span>卡安全设置</span>
		</div>

		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">卡安全设置</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						
				   </td>
				</tr>
		     </table>
		</div>    	   		
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px; ">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">记录列表</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="listTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="txnForm" name="txnForm"
					action="securityInquery.action?op=6" method="post">
					<s:hidden name="cardManagementDTO.cardNo"></s:hidden>
					<s:hidden name="cardManagementDTO.cvv2"></s:hidden>
					<s:hidden name="cardManagementDTO.cardstate"/>
					<s:hidden name="cardManagementDTO.password"/>
					 <s:hidden name="cardManagementDTO.cardValidityPeriod" />
					<s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
					<s:hidden name="cardManagementDTO.cardBalanceDTOs"></s:hidden>
				  <s:hidden id="cardManagementDTO.accountNo" name="cardManagementDTO.accountNo"></s:hidden>
                  <s:hidden id="cardManagementDTO.accountType" name="cardManagementDTO.accountType"></s:hidden>
                  <s:hidden id="cardManagementDTO.posSingleAmount" name="cardManagementDTO.posSingleAmount"></s:hidden>
                  <s:hidden id="cardManagementDTO.posDailyAmount" name="cardManagementDTO.posDailyAmount"></s:hidden>
                  <s:hidden id="cardManagementDTO.webDailyAmount" name="cardManagementDTO.webDailyAmount"></s:hidden>
                  <s:hidden id="cardManagementDTO.webSingleAmount" name="cardManagementDTO.webSingleAmount"></s:hidden>
                  <s:hidden id="cardManagementDTO.withoutPinAmount" name="cardManagementDTO.withoutPinAmount"></s:hidden>
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="txnForm"
						action="cardManage/viewCardSecurity.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" showPagination="false"
										sortable="false">
						<ec:row>
							<ec:column property="null" alias="choose" title="选择"
												width="10%" sortable="false">
												<input type="radio" name="txnRadio" id="txnRadio"
													value='${map.accountNo}' onclick="
													document.getElementById("cardManagementDTO.accountNo").value=${map.serviceId};
													document.getElementById("cardManagementDTO.accountType").value=${map.accountType};
													document.getElementById("cardManagementDTO.posSingleAmount").value=${map.posSingleAmount};
													document.getElementById("cardManagementDTO.posDailyAmount").value=${map.posDailyAmount};
													document.getElementById("cardManagementDTO.webDailyAmount").value=${map.webDailyAmount};
													document.getElementById("cardManagementDTO.webSingleAmount").value=${map.webSingleAmount};
													document.getElementById("cardManagementDTO.withoutPinAmount").value=${map.withoutPinAmount};" />
							</ec:column>						
							<ec:column property="accountType" title="账户类型" />
							<ec:column property="posSingleAmount" title="POS机单笔交易限额（元）">
							</ec:column>
							<ec:column property="posDailyAmount" title="POS机当日交易限额（元）">
							</ec:column>
									
							<ec:column property="webSingleAmount" title="网上单笔交易限额（元）">
							</ec:column>
							<ec:column property="webDailyAmount" title="网上当日交易限额（元）">
							</ec:column>
							<ec:column property="withoutPinAmount" title="无PIN交易限额（元）">
							</ec:column>
						</ec:row>
					</ec:table>
					
				</s:form>
			</div>
                 
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px"
				onclick="edit();">
				修改
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
