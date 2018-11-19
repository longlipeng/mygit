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
			function doTest_DeviceKTL656H_Write() 
			{
				cookie();
				var vData="";
				vData=DeviceKTL656H._CommOpen(p1);
				vData=DeviceKTL656H._WriteCard(1,cardNo.substring(0,30));
				if(vData==-1){
					alert("写卡失败");
					b='a';
				}else{
					alert("写卡完毕");
				}
				vData=DeviceKTL656H._CommClose();
				
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
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656H"
			CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>

		<OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
			CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<%@ include file="/commons/messages.jsp"%>
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
								action="insertMagentism.action?op=2" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardNo" id="cardNo"
															size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.cardNo
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
														<span class="no-empty">*</span>CVV2：
														<br>

													</td>
													<td>
														<s:textfield name="cardManagementDTO.cvv2" id="CVV2" />
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
														<span class="no-empty">*</span>密码：


													</td>
													<td>
														<s:password name="cardManagementDTO.password"
															id="password"></s:password>
														<s:fielderror>
															<s:param>
																cardManagementDTO.password
															</s:param>
														</s:fielderror>
														<br>
														<button class='bt' type="button"
															style="float: left; margin: 5px 0px"
															onclick="doTest_DeviceKTL512()">
															读取密码
														</button>
														<button class='bt' type="button"
															style="float: left; margin: 5px 20px"
															onclick="newForm.action='selectMagentism.action?op=2';newForm.submit();">
															卡片查询
														</button>
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
														<s:textfield name="cardManagementDTO.serviceFee"
															id="serviceFee" />
														分
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
									<!-- <tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡面有效期：
														<br>

													</td>
													<td>
														
														<input type="text" name="cardManagementDTO.date"
															id="date" value="<s:date name="cardManagementDTO.date" format="yyyy-MM-dd"/>"/>
														<s:fielderror>
															<s:param>
																cardManagementDTO.date
															</s:param>
														</s:fielderror>
													</td>
													
												</tr>
											</table>	
										</td>
										<td>
										</td>
									</tr> -->
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														持卡人姓名：

													</td>
													<td>
														<s:label name="cardManagementDTO.cardholderName" />
														<s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
													</td>
												</tr>
											</table>
										</td>

										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														到期日期：

													</td>
													<td>
														<s:date name="cardManagementDTO.cardValidityPeriod"
															format="yyyy-MM-dd" />
														<input type="hidden"
															name="cardManagementDTO.cardValidityPeriod"
															id="cardValidityPeriod"
															value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>" />
													</td>
													<td>
														状态：
													</td>
													<td>
														<s:label name="cardManagementDTO.cardstate" />
														<s:hidden name="cardManagementDTO.cardstate"></s:hidden>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<s:iterator value="cardManagementDTO.cardBalanceDTOs"
										var="cardBalanceDTO">
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															账户类型：

														</td>
														<td>
															<s:label name="#cardBalanceDTO.accountType" />

														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															有效余额：

														</td>
														<td style="width: 110px;">
															<s:label name="#cardBalanceDTO.balance" />元

														</td>
														<td style="width: 110px; text-align: right;">
															冻结金额：
														</td>
														<td style="width: 110px;">
															<s:label name="#cardBalanceDTO.congeal" />元
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:iterator>

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
				onclick="newForm.action='magentismInquery.action?op=2';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="add();">
				卡磁道重写
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
