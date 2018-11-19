<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端信息编辑</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			var chooseMerchantId="";
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
				var  merchantId=window.showModalDialog('${ctx}/selectMerchant.action', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');				
				if(merchantId!=null){	
					var merchantCode=merchantId.split(",");		
					document.getElementById('posInfoDTO.mchntId').value=merchantCode[1];
					document.getElementById('mchntEntityId').value=merchantCode[2]
					chooseMerchantId=merchantCode[0];
				}
			}
			
			function chooseShopId(){
				var merchantId=document.getElementById('mchntEntityId').value;
				if(merchantId==""){
					alert("请先选择商户!");
					return ;
				}
				var  shopIds=window.showModalDialog('${ctx}/inquiryShop.action?merchantId='+merchantId, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');				
				if(shopIds!=null){
				var  shopId=shopIds.split(',');
					document.getElementById('posInfoDTO.shopId').value=shopId[1];
					document.getElementById('posInfoDTO.mchntName').value=shopId[2];
				}
			}
			
			
			function chooseTermBrand(){
				var  termBrandId=window.showModalDialog('${ctx}/choosePosBrandInf.action?', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
				if(termBrandId!=null){
					var  termBrandIdNew=termBrandId.split(',');
					document.getElementById('posInfoDTO.termBrandId').value=termBrandIdNew[0];
					document.getElementById('posInfoDTO.termBrandName').value=termBrandIdNew[1];			
				}	
			}
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>终端信息编辑</span>
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
							<s:form id="newForm" name="newForm"
								action="updateposInf" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>记录编号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.Id"  maxLength="8"  readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																posInfoDTO.Id
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
														<span class="no-empty">*</span>终端号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termId"  maxLength="8"  readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																posInfoDTO.termId
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
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
														<s:textfield id="posInfoDTO.mchntId" name="posInfoDTO.mchntId" cssClass="watch" readonly="true" onclick="chooseMerchant()" />
														<s:hidden id="mchntEntityId" name="posInfoDTO.mchntEntityId"></s:hidden>
														<s:fielderror>
															<s:param>
																posInfoDTO.mchntId
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
														<span class="no-empty">*</span>门店名称：														
													</td>
													<td>
														<s:textfield name="posInfoDTO.mchntName"  id="posInfoDTO.mchntName"  readonly="true"/>
														<s:fielderror>
															<s:param>
																posInfoDTO.mchntName
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
															<span class="no-empty">*</span>门店号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.shopId" id="posInfoDTO.shopId" cssClass="watch" readonly="true" onclick="chooseShopId()"/>
<!--														<s:hidden name="posInfoDTO.shopId" id="posInfoDTO.shopId"></s:hidden>-->
														<s:fielderror>
															<s:param>
																posInfoDTO.shopId
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
														<span class="no-empty">*</span>传输标识：
													</td>
													<td>
														<s:select list="#{'A':'ADSL','C':'CDMA','D':'Dial up','G':'GPRS','T':'TCP/IP'}"
															name="posInfoDTO.termTransFlag" />														
														<s:fielderror>
															<s:param>
																posInfoDTO.termTransFlag
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
														<span class="no-empty">*</span>TMK是否可下载：
													</td>
													<td>
														<s:select list="tmk" listKey="key" listValue="value" name="posInfoDTO.tmkDownTime"	/>				
														<s:fielderror>
															<s:param>
																posInfoDTO.tmkDownTime
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
														<span class="no-empty">*</span>终端厂商编号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termBrandId" id="posInfoDTO.termBrandId" cssClass="watch" readonly="true" onclick="chooseTermBrand()"/>											
													<s:fielderror>
															<s:param>
																posInfoDTO.termBrandId
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
														<span class="no-empty">*</span>终端型号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termModel"/>		
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
														终端厂商名称：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termBrandName" id="posInfoDTO.termBrandName" readonly="true" />											
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
														<span class="no-empty">*</span>消费允许标志：
													</td>
													<td>
													<s:select list="#{1:'允许',0:'不允许'}"
															name="posInfoDTO.consumePerFlag" />																
													</td>
												</tr>
											</table>
										</td>
										<td><table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>充值允许标志：
													</td>
													<td>
													<s:select list="#{0:'不允许',1:'允许'}"
															name="posInfoDTO.reloadPerFlag" />															
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
														<span class="no-empty">*</span>重打印控制标志：
													</td>
													<td>
													<s:select list="#{0:'无',1:'有'}"
															name="posInfoDTO.reprintCtrlFlag" />																
													</td>
												</tr>
											</table>
										</td>
										<td>&nbsp;
											<!-- 
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>积分允许标志：
													</td>
													<td>
														<s:select list="#{0:'不允许',1:'允许'}"
															name="posInfoDTO.pointPerFlag" />
													</td>
												</tr>
											</table>
											-->
										</td>						
									</tr>
									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>最大交易笔数：
													</td>
													<td>
														<s:textfield name="posInfoDTO.maxTxnCnt" />	
														<s:fielderror>
															<s:param>
																posInfoDTO.maxTxnCnt
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
														<span class="no-empty">*</span>终端状态：
													</td>
													<td>
														<s:select list="#{'0':'无效','1':'有效'}"
															name="posInfoDTO.termStat" />																											
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
														<span class="no-empty">*</span>终端持有者：
													</td>
													<td>
													<edl:entityDictList displayName="posInfoDTO.termOwner"  dictType="812" tagType="2" dictValue="${posInfoDTO.termOwner}"/>																																		
													</td>
												</tr>
											</table>
										</td>	
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>终端公共参数版本号：
													</td>
													<td>
														<s:select id="prmVersion1"
															name="posInfoDTO.prmVersion1"
															list="versionlist1"
															listKey="prmVersion"
															listValue="prmVersion"></s:select>																													
													</td>
												</tr>
											</table>
										</td>
											
									</tr>		
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>终端卡BIN参数版本号：
													</td>
													<td>
													<s:select id="prmVersion2"
														name="posInfoDTO.prmVersion2"
														list="versionlist2"
														listKey="prmVersion"
														listValue="prmVersion"></s:select>																																																																									
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														终端IC卡参数版本号：
													</td>
													<td>
													<s:select id="prmVersion3"
														name="posInfoDTO.prmVersion3"
														list="versionlist3"
														listKey="prmVersion"
														listValue="prmVersion"></s:select>																										
													</td>
												</tr>
											</table>
										</td>
									</tr>
																						
								</table>
							
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
		<s:hidden name="termType"/>
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="window.location='inqueryTerinalManagement.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		</s:form>
	</body>
</html>
