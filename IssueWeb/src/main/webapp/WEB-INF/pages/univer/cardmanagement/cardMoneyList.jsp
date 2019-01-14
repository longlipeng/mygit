<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>持卡人账务调整</title>
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
			function update(retrivlRef,accountNo,txnNum,pan,sysSeqNum){
				
				var adjust=window.showModalDialog('${ctx}/cardManagement!moneyUpdate.action?txnNum='+txnNum+'&pan='+pan+'&sysSeqNum='+sysSeqNum, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(adjust!=null&&adjust!=''){
					var a=adjust.split(",");
					if(a[1]!=null&&a[1]!=''){
						document.getElementById("acctId1").value=accountNo;
						document.getElementById("retrivlRef").value=retrivlRef;
						document.getElementById("txnNum").value=txnNum;
						document.getElementById("cardNo").value=a[2];
						document.getElementById("serviceFee").value=a[3];
						newForm.action="insertBelTxn.action?op=7&adjust="+a;
						newForm.submit();
					}
				}
			}
			function doTest_DeviceKTL512() 
			{
				cookie();
				if(p2==0){
					alert('请设置串口号');
					return;
				}
				var vData=DeviceKTL512.GetPin(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('设备连接串口有误！');
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
				}else{
					alert('设备连接有误,串口可能被占用！');
				}
				alert("读卡完毕");
			}
			function doTest_DevicePCSC() 
			{
				var vData="";
				vData=DevicePCSC.GetCardNo();
				if(vData!=-1){
					document.getElementById("cardNo").value=vData;
				}else{
					alert('设备连接有误,串口可能被占用！');
				}
				alert("读卡完毕");
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
			<span>账务调整</span>
		</div>

		<div id="ContainBox">

			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">账务调整操作</span>
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
								action="cardMoneyInquery.action?op=7" method="post">
								<s:hidden name="cardManagementDTO.acctId1" id="acctId1"></s:hidden>
								<s:hidden name="cardManagementDTO.txnNum" id="txnNum"></s:hidden>
								<s:hidden name="cardManagementDTO.retrivlRef" id="retrivlRef"></s:hidden>
								<s:hidden name="cardManagementDTO.serviceFee" id="serviceFee"></s:hidden>
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
														<span class="no-empty"></span>调整查询类型：
													</td>
													<td>
														<s:select list="#{'':'请选择','7000':'充值','1105':'消费','7003':'内扣','1095':'预授权完成'}" name="cardManagementDTO.idNo"></s:select>
													</td>
													<td>
														<img src="${ctx}/images/table/cx.GIF" width="50"
															height="19" class="btn" onclick="newForm.submit();">
													</td>
												</tr>

											</table>
										</td>
									</tr>


								</table>

							</s:form>

						</div>
				</tr>
			</table>

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
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none no  ne; border-width: 1px; border-color: #B9B9B9;">
					<s:form id="cardselectForm" name="cardselectForm"
						action="cardMoneyInquery.action?op=7" method="post">
						<s:hidden name="cardManagementDTO.cardNo"/>

						<ec:table items="pageDataDTO.data" var="map" width="100%"
							form="cardselectForm"
							action="${ctx}/cardMoneyInquery.action?op=7"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" autoIncludeParameters="false">
							<!-- <ec:exportXls fileName="cardMoneyList.xls" tooltip="导出Excel" /> -->
							<ec:row>
								<ec:column property="sysSeqNum" title="交易系统参考号" width="10%">							
									<a href="javascript:update('${map.retrivlRef}','${map.accountNo}','${map.txnNum}','${map.pan}','${map.sysSeqNum}')">${map.sysSeqNum}</a>
								</ec:column>
								<ec:column property="acctypeName" title="服务"/>
								<ec:column property="txnType1" title="交易类型"/>
								<ec:column property="amtTrans" title="交易金额(元)" width="10%" cell="amt"/>
									
								<ec:column property="addtnlAmt" title="余额（元）" width="10%" cell="amt"/>
								
								<ec:column property="pan" title="卡号" width="20%" escapeAutoFormat="true"/>

								
								<ec:column property="txnDate" title ="交易时间"/>
							</ec:row>
						</ec:table>

					</s:form>
				</div>
			</div>
		</div>
	</body>
</html>
