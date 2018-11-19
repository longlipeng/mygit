<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>磁卡信息重写</title>
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
			var cardNo='';
			var b='';
			function add(){
				if(document.getElementById('cardNo').value==''){
					alert("请填写卡号");
				}else{
				ajaxRemote('${ctx}/cardManagement!getCardInfo.action',
                    'id='+document.getElementById('cardNo').value+','+document.getElementById('cardValidityPeriod').value,
                    function(cardId) {
                    	if(cardId=='ab'){
                    		return;
                    	}
                        cardNo=cardId;
                       	if(cardNo==null||document.getElementById("serviceFee").value==""){
                       		alert("请添写卡号或者服务费");
                       		return;
                       	}
                       	var patrn=/^(\d){1,10}$/;
                       	if(!patrn.test(document.getElementById("serviceFee").value)){
							alert("请正确填写服务费");
							return;
						}
                        doTest_DeviceKTL656H_Write();
                       	if(b=='a'){
                       		return;
                       	}
                        newForm.submit();
                    },
                    'html');
				
				
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
		<%@ include file="/commons/messages.jsp" %>
		<div class="TitleHref">
			<span>磁卡信息重写</span>
		</div>
		<s:fielderror>
			<s:param>
				cardManagementDTO.cardValidityPeriod
			</s:param>
		</s:fielderror>
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
									<span class="TableTop">磁卡重写操作</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManage/insertMagentism.action" method="post">
                                 <s:hidden name="cardManagementDTO.password"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cvv2"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cardValidityPeriod" />
							     <s:hidden name="cardManagementDTO.cardstate"></s:hidden>
							   	<!--      <s:hidden name="cardManagementDTO.cardBalanceDTOs"></s:hidden>	-->							
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardNo" id="cardNo" readonly="true" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.cardNo
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
														<s:textfield name="cardManagementDTO.serviceFee" />分
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


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button type="button" class='bt'
				style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="newForm.submit();">
				卡磁道重写
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
