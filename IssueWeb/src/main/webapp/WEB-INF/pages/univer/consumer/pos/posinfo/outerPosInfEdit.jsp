<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>外部终端信息编辑</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			var choooseMerchantId="";
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			function chooseMerchant(){
				var  result=window.showModalDialog('${ctx}/selectMerchant.action', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');				
				
				if(result!=null){
				    var shopName=document.getElementById('shopName').value;
					var shopId=document.getElementById('shopId').value;
				     if(shopId!=''||shopName!=''){
				     document.getElementById('shopName').value='';
				     document.getElementById('shopId').value='';
				     }			
					var resultMap=result.split(",");
					var merchantName=resultMap[0];
					var merchantCode=resultMap[1];
					document.getElementById('mchntEntityId').value=resultMap[2];
					document.getElementById('outerPosInfoDTO.mchntId').value=merchantCode;
					document.getElementById('outerPosInfoDTO.mchntName').value=merchantName;
					choooseMerchantId=resultMap[0];
				}
			}
			function chooseTermBrand(){
				var  termBrandIds=window.showModalDialog('${ctx}/choosePosBrandInf.action?', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
				if(termBrandIds!=null){
				var termBrandId=termBrandIds.split(",")
					document.getElementById('outerPosInfoDTO.termBrandId').value=termBrandId[0];
					document.getElementById('outerPosInfoDTO.termBrandName').value=termBrandId[1];		
				}	
			}
				function chooseShopId(){
				var merchantId=document.getElementById('mchntEntityId').value;
				if(merchantId==""){
					alert("请先选择商户!");
					return ;
				}
				var  shopIds=window.showModalDialog('${ctx}/inquiryShop.action?merchantId='+merchantId, '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');				
				if(shopIds!=null){
				var  shopId=shopIds.split(',');
					
					document.getElementById('outerPosInfoDTO.shopId').value=shopId[1];
					document.getElementById('outerPosInfoDTO.shopName').value=shopId[2];
				}
			}	
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>外部终端信息编辑</span>
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
<s:hidden name="termType"/>
								<table width="100%" style="table-layout: fixed;">
									<tr>
									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>终端号：
													</td>
													<td>
														<s:textfield name="outerPosInfoDTO.termId" maxLength="8"
															id="outerPosInfoDTO.termId"></s:textfield>
														
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
														<s:textfield name="outerPosInfoDTO.termModel"/>																							
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
													<s:hidden  name="outerPosInfoDTO.oldMchntId"></s:hidden>
														<s:textfield id="outerPosInfoDTO.mchntId"
															name="outerPosInfoDTO.mchntId" cssClass="watch" readonly="true"
															onclick="chooseMerchant()" />
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
														<s:textfield name="outerPosInfoDTO.mchntName"
															id="outerPosInfoDTO.mchntName" readonly="true" />
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
														<s:textfield id="shopId"
															name="outerPosInfoDTO.shopId" cssClass="watch" readonly="true"
															onclick="chooseShopId()" />
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
														<s:textfield name="outerPosInfoDTO.shopName"
															 id="shopName" readonly="true" />
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
														<s:textfield name="outerPosInfoDTO.termBrandId"
															 cssClass="watch"
															readonly="true" onclick="chooseTermBrand()" />
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
														<s:textfield name="outerPosInfoDTO.termBrandName"
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
															name="outerPosInfoDTO.termStat" />
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
															name="outerPosInfoDTO.termTransFlag" />
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
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='inqueryTerinalManagement.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="this.disabled='disabled';newForm.submit();setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		
	</body>
</html>
