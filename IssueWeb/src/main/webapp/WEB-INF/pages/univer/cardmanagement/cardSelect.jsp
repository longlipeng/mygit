<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片交易信息查询/title>
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
				
					newForm.submit();
				
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
					alert('设备连接有误,串口可能被占用！');
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
			function view(cardNo,merchantName,shopName,cardAccpTermId,txnType,txnAmt,txnState,txnDate,txnFee){
				document.getElementById("cardNo").value=cardNo;
				document.getElementById("merchantName").value=merchantName;
				document.getElementById("shopName").value=shopName;
				document.getElementById("acctId").value=cardAccpTermId;
				document.getElementById("txnType").value=txnType;
				document.getElementById("txnAmt").value=txnAmt;
				document.getElementById("txnState").value=txnState;
				document.getElementById("txnDate").value=txnDate;
				document.getElementById("serviceFee").value=txnFee;
				//cardselectForm.action='${ctx}/cardManage/cardQuery.action?cardManagementDTO.cardNo='+cardNO+'&cardManagementDTO.cardManagementDTO.merchantName'+merchantName+'&cardManagementDTO.shopDTO.shopName'+shopName+'&cardManagementDTO.acctId'+cardAccpTermId+'&cardManagementDTO.txnType'+txnType+'&cardManagementDTO.txnAmt'+txnAmt+'&cardManagementDTO.txnState'+txnState+'&cardManagementDTO.txnDate'+txnDate+'&cardManagementDTO.txnFee'+txnFee+;
				cardselectForm.action='${ctx}/cardManage/cardTxnSelectDetail.action';
				cardselectForm.submit();
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
		<OBJECT ID="ACCOR_ATL" name="DevicePCSC"
			CLASSID="clsid:416B15E8-CE9A-4BF8-8D0A-D79625298E25" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT> 
		
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片交易信息查询</span>
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
									<span class="TableTop">卡片信息</span>
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
								action="inqueryTxn.action?op=5" method="post">

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
													<td>
														<select name="typeName" id="typeName">
															<option value="1">
																磁条卡机
															</option>
															<option value="2">
																PC/SC读写卡器
															</option>
															<option value="3">
																IC卡机
															</option>
														</select>
													</td>
													<td>
														<button class='bt' type="button"
															style="float: left; margin: 5px 10px"
															onclick="readCardNo();">
															<!-- onclick="readCardNo(); -->
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
														<span class="no-empty">*</span>CVV2：


													</td>
													<td valign="top">
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

														<div id="btnDiv" style="text-align: left; width: 100%">
															<button class='bt' type="button"
																style="float: left; margin: 5px 0px"
																onclick="doTest_DeviceKTL512()">
																读取密码
															</button>
															<button class='bt' type="button"
																style="float: left; margin: 5px 20px"
																onclick="newForm.action='cardManage/cardQuery.action?op=5';newForm.submit();">
																卡片查询
															</button>
														</div>
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
													<td valign="top">
														<s:textfield name="cardManagementDTO.serviceFee" />
														分
														<s:fielderror>
															<s:param>
																cardManagementDTO.serviceFee
															</s:param>
														</s:fielderror>
													</td>
													<td>
														<s:radio list="#{0:'近期',1:'历史'}"
															name="cardManagementDTO.idNo" value="0"></s:radio>
														<s:fielderror>
															<s:param>
																	cardManagementDTO.idNo
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
									<tr>
										<td>
										<table style="text-align: left; width: 100%">
										<tr>
										<td style="width: 110px; text-align: right;">
											卡片产品名称：

										</td>
										<td>
											<s:label name="cardManagementDTO.prodName" />
											<s:hidden name="cardManagementDTO.prodName"></s:hidden>
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
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>开始日期：


													</td>
													<td>

														<input type="text" name="cardManagementDTO.startDate"
															onclick="dateClick(this)" class="Wdate"
															value="<s:date name='cardManagementDTO.startDate' format='yyyy-MM-dd'/>"
															readonly="readonly" />
														<input type="hidden" name="cardManagementDTO.startDate"
															value="<s:date name="cardManagementDTO.startDate" format="yyyy-MM-dd"/>" />
														<br />
														<s:fielderror>
															<s:param>
																cardManagementDTO.startDate
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
														<span class="no-empty">*</span>结束日期：


													</td>
													<td>

														<input type="text" name="cardManagementDTO.endDate"
															onclick="dateClick(this)" class="Wdate"
															value="<s:date name='cardManagementDTO.endDate' format='yyyy-MM-dd'/>"
															readonly="readonly" />
														<input type="hidden" name="cardManagementDTO.endDate"
															value="<s:date name="cardManagementDTO.endDate" format="yyyy-MM-dd"/>" />
														<br />
														<s:fielderror>
															<s:param>
																cardManagementDTO.endDate
															</s:param>
														</s:fielderror>
													</td>
													<td>
														<button class='bt' style="float: right; margin: 0px, 0px"
															onclick="add();">
															交易查询
														</button>
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
		<s:if test="pageDataDTO.data!=null">
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
										<span class="TableTop">记录列表</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="listTable"
								style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
								<s:form id="cardselectForm" name="cardselectForm"
									action="inqueryTxn.action?op=5" method="post">
									<s:hidden name="cardManagementDTO.cardNo"></s:hidden>
									<s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
									<s:hidden name="cardManagementDTO.idNo"></s:hidden>
									<input type="hidden" name="cardManagementDTO.startDate"
										value="<s:date name="cardManagementDTO.startDate" format="yyyy-MM-dd"/>" />
									<input type="hidden" name="cardManagementDTO.endDate"
										value="<s:date name="cardManagementDTO.endDate" format="yyyy-MM-dd"/>" />
									
									<s:hidden name="cardManagementDTO.cvv2" />
									<s:hidden name="cardManagementDTO.serviceFee" />
									<s:hidden name="cardManagementDTO.cardstate" />
									
									<s:hidden name="cardManagementDTO.serviceFee" />
									<s:hidden name="cardManagementDTO.cardstate" />
									<input type="hidden"
										name="cardManagementDTO.cardValidityPeriod"
										id="cardValidityPeriod"
										value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>" />
										
									<input type="hidden"
										name="cardManagementDTO.cardNo" id="cardNo"/>
										<input type="hidden"
										name="cardManagementDTO.cardManagementDTO.merchantName" id="merchantName"/>
										<input type="hidden"
										name="cardManagementDTO.shopDTO.shopName" id="shopName"/>
										<input type="hidden"
										name="cardManagementDTO.acctId" id="acctId"/>
										<input type="hidden"
										name="cardManagementDTO.txnType" id="txnType"/>
										<input type="hidden"
										name="cardManagementDTO.txnAmt" id="txnAmt"/>
										<input type="hidden"
										name="cardManagementDTO.txnState" id="txnState"/>
										<input type="hidden"
										name="cardManagementDTO.txnDate" id="txnDate"/>
										<input type="hidden"
										name="cardManagementDTO.serviceFee" id="serviceFee"/>

									<ec:table items="pageDataDTO.data" var="map" width="100%"
										form="cardselectForm" action="${ctx}/inqueryTxn.action?op=5"
										imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
										retrieveRowsCallback="limit" autoIncludeParameters="false">
										<ec:exportXls fileName="cardSelectTxnList.xls"
											tooltip="导出Excel" />
										<ec:row
											ondblclick="view(${map.cardNo},${map.merchantName },${map.shopName },${map.cardAccpTermId },${map.txnType },${map.txnAmt },${map.addtnlAmt },${respCode },${map.txnDate },${map.txnFee });">

											<ec:column property="sysSeqNum" title="交易系统参考号" width="8%"
												escapeAutoFormat="true">
												<!-- <a href="cardManagement!viewTxn.action?id=${map.ref}">${map.ref}</a> -->
											</ec:column>
										<!-- 	<ec:column property="acctypeName" title="服务名" width="10%">

											</ec:column>

											<ec:column property="cardholderName" title="持卡人" width="8%" />
											<ec:column property="costomerId" title="客户号" width="8%" />
											<ec:column property="customerName" title="客户名" width="8%">
											</ec:column> -->
											<ec:column property="merchantName" title="商户名" width="8%"></ec:column>
											<ec:column property="shopName" title="店名" width="8%"></ec:column>
											<ec:column property="cardAccpTermId" title="POS号" width="10%"></ec:column>
											<ec:column property="txnType" title="交易类型" width="8%" />
											<ec:column property="txnAmt" title="交易金额(元)" width="8%"
												cell="amt" />
											<ec:column property="addtnlAmt" title="余额(元)" width="8%"
												cell="amt" />
											<ec:column property="txnState" title="交易状态" width="8%"
												cell="cardSelect" />
											<ec:column property="txnDate" title="交易时间" width="10%"
												format="yyyy-MM-dd" cell="date" />
											<ec:column property="txnFee" title="手续费" width="8%"
												cell="amt" />
										</ec:row>
									</ec:table>

								</s:form>
							</div>

							<!-- div id=TableBody -->
						</td>
					</tr>
				</table>
			</div>
		</s:if>
	</body>
</html>

