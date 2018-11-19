<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>持卡人账务调整</title>
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
			function sub(){
				
				var t;
				var c=document.getElementById("cardNo").value;
				var m=document.getElementById("serviceFee").value;
				var k=document.getElementById("adjustType").value;
				var a=document.getElementById("adjustAmount").value;
				var b=document.getElementById("txnAmt").value;
				var d=document.getElementById("misc").value;
				var patrn=/^(\d){1,10}$/;
				if(k=='7000'||k=='7001'){
					t=2;
				}else{
					t=1;
				}
				if(!patrn.test(m)){
					document.getElementById('serviceFeeErr').style.display='';
					return;
				}
				if(patrn.test(a)){
						if(a>b*100-d*100){
							document.getElementById('adjustAmountErr1').style.display='';
							return;
						}
						var s=t+","+a+","+c+","+m;
						window.returnValue=s;
						window.close();
				}else{
					document.getElementById('adjustAmountErr').style.display='';
				}
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		
		<div class="TitleHref">
			<span>交易明细</span>
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
									<span class="TableTop">持卡人信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManagement!insertDelay.action?op=8" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡号：
													</td>
													<td>
														<s:label name="cardManagementDTO.cardNo"></s:label>
														<s:hidden name="cardManagementDTO.cardNo" id="cardNo"></s:hidden>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡类型：
														

													</td>
													<td>
														<dl:dictList displayName="cardType" dictType="102"
																dictValue="${cardManagementDTO.cardType}" tagType="1" />
														
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
														特卡人姓名：


													</td>
													<td>
														<s:label name="cardManagementDTO.cardholderName"></s:label>
														
							
														
													</td>

												</tr>
											</table>

										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														持卡人工号：


													</td>
													<td>
														
															<s:label name="cardManagementDTO.externalId"/>
														
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
														证件类型：


													</td>
													<td>
														<edl:entityDictList displayName="idType" dictType="401"
																dictValue="${cardManagementDTO.idType}" tagType="1" />
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														证件号：

													</td>
													<td>
														<s:label name="cardManagementDTO.idNo" />

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
									<span class="TableTop">交易明细</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManagement!insertDelay.action?op=8" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														交易流水：
													</td>
													<td>
														<s:label name="cardManagementDTO.sysSeqNum"></s:label>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														交易类型：
														

													</td>
													<td>
														<s:label name="cardManagementDTO.txnType1"></s:label>
														
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
														交易金额：


													</td>
													<td>
														<s:label name="cardManagementDTO.txnAmt"></s:label>元
														<s:hidden name="cardManagementDTO.txnAmt" id="txnAmt"></s:hidden>
							 
														
													</td>

												</tr>
											</table>

										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														交易时间：


													</td>
													<td>
														<s:label name="cardManagementDTO.txnDate"></s:label>				
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
														调整类型：

													</td>
													<td>
														<s:select id="adjustType" name="cardManagementDTO.txnNum" list="#{'1105':'偿还','7000':'扣款','7001':'扣款'}" disabled="true"/>

													</td>
												</tr>
											</table>
										</td>

										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														调整金额：

													</td>
													<td>
														<s:textfield id="adjustAmount"></s:textfield>分
														<br/>
														<label style="color:red;display:none; " id="adjustAmountErr">请输入10位内数字</label>
														<label style="color:red;display:none; " id="adjustAmountErr1">调整金额不能大于交易金额与已调整金额的差值</label>
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
														已调整金额：


													</td>
													<td>
														<s:label name="cardManagementDTO.misc"></s:label>
														<s:hidden name="cardManagementDTO.misc" id="misc"></s:hidden>				
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														服务费：

													</td>
													<td>
														<s:textfield id="serviceFee"></s:textfield>分
														<br/>
														<label style="color:red;display:none; " id="serviceFeeErr">请正确填写服务费,为10位数内</label>
													</td>
												</tr>
												
											</table>
										</td>
										</tr>
										<tr>
										<td align="right">
														<button type="button" class='bt' style="float: right; margin: 5px 20px"
															onclick="window.close();">
															关闭
														</button>
														<button type="button" class='bt' style="float: right; margin: 5px 20px"
															onclick="sub();">
															确定
														</button>
														
										</td>
									</tr>

								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
