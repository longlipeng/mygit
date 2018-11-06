<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>账户信息添加</title>
		<%@ include file="/commons/meta.jsp"%>

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
			function igid(){
				var sel=document.getElementById("issuerGroupId");
				var selectvalue=sel.value;
				if(selectvalue!=''){
				
					var groupvalue=sel.options[0].value;
					document.getElementById("issuerGroup").value=groupvalue.substring(1);
					
					if(selectvalue==groupvalue){
						
						document.getElementById("issuer").value=0;
					}else{
						
						document.getElementById("issuer").value=selectvalue.substring(1);
					}
				}
			}
			function choiceLoyIssuer() {
       			var loyIssueInfo = window.showModalDialog('choice1.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        		if (loyIssueInfo != null) {
            	var s=loyIssueInfo.split(',');
            		document.getElementById("loyaltyIssueId").value=s[0];
            		document.getElementById("loyIssuerName").value=s[1];
        		}
    		}
    		function vip(){
    			var   sel   =   document.getElementById("isLoyalty");  
   				if(sel.options[sel.selectedIndex].value==0){
   					document.getElementById("loyId").style.display='none';
   				}else{
   					document.getElementById("loyId").style.display='';
   				}
   				
    		}
    		function sub(){
    			if(checkServiceName()){
	    			maskDocAll();
	   				newForm.submit();
    			}
    		}
    		
    		function checkServiceName(){
				$("#serviceName_msg").empty("");
				var serviceName = document.getElementById("serviceName").value;
				if(serviceName.length>80){
					$("#serviceName_msg").html('<ul class="errorMessage"><li><span>请填写80位内账户名称！</span></li></ul>');
					return false;
				}
				return true;
			}
    		
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增账户信息</span>
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
									<span class="TableTop">账户信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="insertAccount"
								method="post">
								<s:token></s:token>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														账户编号：
													</td>
													<td>
														<s:textfield name="serviceDTO.serviceId" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																serviceDTO.serviceId
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>账户名称：
													</td>
													<td>
														<s:textfield id="serviceName" name="serviceDTO.serviceName" onchange="checkServiceName()"></s:textfield>
														<div id="serviceName_msg">
															<s:fielderror>
																<s:param>
																	serviceDTO.serviceName
																</s:param>
															</s:fielderror>
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
														<span class="no-empty">*</span>账户短名称：
													</td>
													<td>
														<s:textfield name="serviceDTO.serviceShortName" maxLength="128"/>
														<s:fielderror>
															<s:param>
																serviceDTO.serviceShortName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;" maxLength="3">
														账户英文名称：
													</td>
													<td>
														<s:textfield name="serviceDTO.serviceEnglishName" />

														<s:fielderror>
															<s:param>
																serviceDTO.serviceEnglishName
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
														<span class="no-empty">*</span>缺省费率：
													</td>
													<td>
														<s:textfield name="serviceDTO.defaultRate" />
														%
														<s:fielderror>
															<s:param>
																serviceDTO.defaultRate
															</s:param>
														</s:fielderror>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														过期时间：
													</td>
													<td>
														<s:textfield name="serviceDTO.expiryDate"
															 onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" cssClass="Wdate" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																serviceDTO.expiryDate
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<!--  <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty"></span>发行机构：
													</td>
													<td>
														<s:select id="" list="entityList"
															name="serviceDTO.entityId" listKey="entityId"
															listValue="entityName"  >
														</s:select>
															<input type="hidden" id="entityId"
												            name="serviceDTO.entityId" />
													</td>
												</tr>
											</table>
										</td>-->
									</tr>
									
								</table>
							</s:form>
						</div>
			</table>



			<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' type="button"
					style="float: right; margin: 5px 10px"
					onclick="window.location='accountInquery'">
					返 回
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="sub();">
					提 交
				</button>
				<div style="clear: both"></div>
			</div>
	</body>
</html>
