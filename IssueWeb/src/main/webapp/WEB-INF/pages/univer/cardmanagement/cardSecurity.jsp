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
				
				document.txnForm.action='cardManagement!loadAccount.action?id='+id;
				document.txnForm.submit();
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
			function a(){
				var n='${n}';
				if(n!=''&&n!=null){
					document.getElementById("cardNo").readOnly='true';
					document.getElementById("CVV2").readOnly='true';
					document.getElementById("password").readOnly='true';
				}
			}
			function doTest_DeviceKTL656H_Read() 
			{
				cookie();
				var vData="";
				if(p1==0){
					
					alert('请设置串口号');
					return;
				}else{
					
					vData=DeviceKTL656H._CommOpen(p1);
					vData=DeviceKTL656H._ReadCard(1);
				}
				if(vData!=-1){
					document.getElementById("cardNo").value=vData.substring(0,vData.indexOf('='))
					alert("读卡完毕");
				}else{
					alert('读卡失败');
				}
				vData=DeviceKTL656H._CommClose();
				
			}
			function doTest_DeviceKTL656() 
			{
				var vData=DeviceKTL656.GetCardNoEx(4);
				if(vData!=1){
					document.getElementById("cardNo").value=vData;
					alert("读卡完毕");
				}else{
					alert('读卡失败');
				}
				
			}
			function doTest_DevicePCSC() 
			{
				var vData="";
				vData=DevicePCSC.GetCardNo();
				if(vData!=-1){
					document.getElementById("cardNo").value=vData;
					alert("读卡完毕");
				}else{
					alert('读卡失败');
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
	<body onload="a()">
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
			CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656H"
			CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656"
			CLASSID="clsid:E8B803A1-41C1-4FF3-9832-5E3A17B2B5AF" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL"  name="DevicePCSC" CLASSID="clsid:416B15E8-CE9A-4BF8-8D0A-D79625298E25" 
			 HEIGHT=0 WIDTH=0 codebase="ACCOR_ATL.dll#version=1,0,0,1" viewastext"></OBJECT>
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
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="securityInquery.action?op=6" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardNo" id="cardNo" size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.cardNo
															</s:param>
														</s:fielderror>
													</td>
													<td>
														<select name="typeName" id="typeName">
															<option value="1">磁条卡机</option>
															<option value="2">PC/SC读写卡器</option>
															<option value="3">IC卡机</option>
														</select>
													</td>
													<td>
														<button class='bt' type="button"
															style="float: left; margin: 5px 10px" onclick="readCardNo();">
															读卡
														</button>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>CVV：
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
															onclick="newForm.action='selectSecurity.action?op=6';newForm.submit();">
															卡片查询
														</button>
													</td>

												</tr>
											</table>

										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													
												</tr>
											</table>
										</td>
									</tr>
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
														<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>
														<input type="hidden" name="cardManagementDTO.cardValidityPeriod"
															id="cardValidityPeriod" value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>"/>
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
														<td>
															<s:label name="#cardBalanceDTO.balance" />

														</td>
														<td>
															冻结金额：
														</td>
														<td>
															<s:label name="#cardBalanceDTO.congeal" />
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
					<ec:table items="cardManagementDTO.accountDTOs" var="map" width="100%"
						form="txnForm"
						action="securityInquery.action?op=6"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" showPagination="false"
										sortable="false">
						<ec:row>
							<ec:column property="null" alias="choose" title="选择"
												width="10%" sortable="false">
												<input type="radio" name="txnRadio" id="txnRadio"
													value='${map.accountNo}' />

											</ec:column>
							
							<ec:column property="accountType" title="账户类型" />
							<ec:column property="posSingleAmount" title="POS机单笔交易限额（元）">
								${map.posSingleAmount+.00}
							</ec:column>
							<ec:column property="posDailyAmount" title="POS机当日交易限额（元）">
								${map.posDailyAmount+.00}
							</ec:column>
									
							<ec:column property="webSingleAmount" title="网上单笔交易限额（元）">
								${map.webSingleAmount+.00}
							</ec:column>
							<ec:column property="webDailyAmount" title="网上当日交易限额（元）">
								${map.webDailyAmount+.00}
							</ec:column>
							<ec:column property="withoutPinAmount" title="无PIN交易限额（元）">
								${map.withoutPinAmount+.00}
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
