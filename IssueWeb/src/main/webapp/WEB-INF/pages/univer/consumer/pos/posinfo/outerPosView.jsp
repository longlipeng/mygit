<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端信息详情</title>
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
			
			 
			function addFaceValue(){
				var faceValue=window.showModalDialog('${ctx}/producte!faceValueList.action?id=${productDTO.productId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(faceValue!=null){
					document.getElementById('faceValue').value=faceValue;
					document.getElementById('newForm').action='faceValueAdd.action';
					document.getElementById('newForm').submit();
				}
			}
			
			
			
			
			function chooseMerchant(){
				var  merchants=window.showModalDialog('${ctx}/selectMerchant.action', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');				
				if(merchants!=null){
					var   merchantId=merchants.split(',');
			
					document.getElementById('posInfDTO.mchntId').value=merchantId[1];
				}
			}
			
			function chooseShopId(){
				var merchantId=document.getElementById('posInfDTO.mchntId').value;
				if(merchantId==""){
					alert("请先选择商户!");
					return ;
				}
				var  shopId=window.showModalDialog('${ctx}/inquiryShop.action?merchantId='+merchantId, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');				
				if(shopId!=null){
					document.getElementById('posInfDTO.shopId').value=shopId;
				}
			}
			
			function chooseTermBrand(){
				var  termBrandId=window.showModalDialog('${ctx}/choosePosBrandInf.action?', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(termBrandId!=null){
					document.getElementById('posInfDTO.termBrandId').value=termBrandId;		
				}	
			}
			function turnBack(){	
			
			document.newForm.action='inqueryOuterposOrPos.action';
			newForm.submit();
			}
			
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>终端信息详情</span>
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
									<span class="TableTop">终端信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="updateposInf.action"
								method="post">
<s:hidden name="termType" id="termType"></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>终端号：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.termId"
															></s:label>
														
														<s:fielderror>
															<s:param>
																outerPosInfoDTO.termId
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
														终端型号：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.termModel"/>																							
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
														<span class="no-empty">*</span>商户号：
													</td>
													<td><s:hidden id="mchntEntityId" name="mchntEntityId"></s:hidden>
														<s:label 
															name="outerPosInfoDTO.mchntId" />
														<s:fielderror>
															<s:param>
																outerPosInfoDTO.mchntId
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
														<span class="no-empty">*</span>商户名称：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.mchntName"
															/>
														<s:fielderror>
															<s:param>
																outerPosInfoDTO.mchntName
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
														<span class="no-empty">*</span>门店号：
													</td>
													<td>
														<s:label
															name="outerPosInfoDTO.shopId" />
														<s:fielderror>
															<s:param>
																outerPosInfoDTO.mchntId
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
														<span class="no-empty">*</span>门店名称：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.shopName"
															/>
														<s:fielderror>
															<s:param>
																
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
														终端厂商编号：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.termBrandId"
															 />
														<s:fielderror>
															<s:param>
																
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
														终端厂商名称：
													</td>
													<td>
														<s:label name="outerPosInfoDTO.termBrandName"
															/>
														<s:fielderror>
															<s:param>
																
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
														<span class="no-empty">*</span>终端状态：
													</td>
													<td>
														<s:select list="#{'1':'有效','0':'无效'}"
															name="outerPosInfoDTO.termStat" disabled="true"/>
													</td>
												</tr>
											</table>
										</td>
										<td> 
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>终端持有者：
													</td>
													<td>
													<edl:entityDictList displayName="outerPosInfoDTO.termOwner"  dictType="812" tagType="2"  />	
													<s:fielderror>
															<s:param>
																outerPosInfoDTO.termOwner
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
														传输标识：
													</td>
													<td>
														<s:select
															list="#{'A':'ADSL','C':'CDMA','D':'Dial up','G':'GPRS','T':'TCP/IP'}"
															name="outerPosInfoDTO.termTransFlag" disabled="true"/>
														<s:fielderror>
															<s:param>
																
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
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="turnBack();">
				返 回
			</button>			
	
			<div style="clear: both"></div>
		</div>
		
	</body>
</html>
