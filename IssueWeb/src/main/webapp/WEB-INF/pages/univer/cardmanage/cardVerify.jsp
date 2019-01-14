<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片管理</title>
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
			function doTest_DeviceKTL512() 
			{
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("passwordVerify").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
				
			}
			function doTest_DeviceKTL511(){
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
			}
			function a(){
				
				if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
					
					document.getElementById("a").style.display='none';
					
				}else{
					
					document.getElementById("a").style.display='';
				}
			}
			function b(){
				//if(document.getElementById("cardValidityPeriod").value!=null&&document.getElementById("cardValidityPeriod").value!=''){
					if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
						document.getElementById("a").style.display='none';
						newForm.submit();
					}else{
						document.getElementById("a").style.display="";
					}
				//}
				
			}
			function doTest_DeviceKTL656H_Read() 
			{
				cookie();
				var vData="";
				if(p1==0){
					
					vData=DeviceKTL656H._CommOpen(1);
					vData=DeviceKTL656H._ReadCard(1);
				}else{
					
					vData=DeviceKTL656H._CommOpen(p1);
					vData=DeviceKTL656H._ReadCard(1);
				}
				if(vData!=-1){
					document.getElementById("cardNo").value=vData.substring(0,vData.indexOf('='))
					alert("读卡完毕");
				}else{
					alert('设备连接有误,串口可能被占用！');
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

			function check(key){
				if((key.keyCode>=48 && key.keyCode<=57)   ||key.keyCode==8)
					return true;
				else
					return false;
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
            CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
            codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>

        <OBJECT ID="ACCOR_ATL" name="DeviceKTL656"
            CLASSID="clsid:E8B803A1-41C1-4FF3-9832-5E3A17B2B5AF" HEIGHT=0 WIDTH=0
            codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>

        <OBJECT ID="ACCOR_ATL" name="DeviceKTL656H"
            CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" HEIGHT=0 WIDTH=0
            codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL"  name="DevicePCSC" CLASSID="clsid:416B15E8-CE9A-4BF8-8D0A-D79625298E25" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1" viewastext"></OBJECT>
		<div class="TitleHref">
			<span>卡片管理</span>
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
									<span class="TableTop">卡片校验</span>
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
								action="insertPassword.action?op=8" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 60px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cardNo" id="cardNo"  size="24" maxlength="19" onkeypress="return check(event);"></s:textfield>
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
												    <td style="width: 80px; text-align: right;">
														<span class="no-empty"></span>
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
										
										
									 </tr>
									<!--  <tr> 
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 60px; text-align: right;">
														<span class="no-empty">*</span>密码：
													</td>
													<td>
														<s:password name="cardManagementDTO.password"
															id="password"  size="24" maxlength="6"/>
														<s:fielderror>
															<s:param>
																cardManagementDTO.password
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 80px; text-align: right;">
														<span class="no-empty">*</span>CVV2：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.cvv2" id="cvv2"   size="24" maxlength="3"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.cvv2
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr> -->
									<tr>
									   <td></td>
									    <td></td>
										<td>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													</td>
													<td>
														<button class='bt' type="button"
															style="float: left; margin: 0px 0px"
															onclick="newForm.action='cardManage/cardQuery.action';newForm.submit();">
															卡片查询
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

	</body>
</html>

