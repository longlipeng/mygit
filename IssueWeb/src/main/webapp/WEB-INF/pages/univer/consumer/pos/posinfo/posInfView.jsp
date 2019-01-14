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
					document.getElementById('shopDTO.shopId').value=shopId;
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
							<s:form id="newForm" name="newForm"
								action="updateposInf" method="post">
<s:hidden name="termType" id="termType"></s:hidden>
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
																posInfDTO.Id
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
														<span class="no-empty">*</span>终端号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termId"  maxLength="8"  readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																posInfDTO.termId
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
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
														<s:textfield id="posInfoDTO.mchntId" name="posInfoDTO.mchntId"  readonly="true"  />
														<s:fielderror>
															<s:param>
																posInfDTO.mchntId
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
														<s:textfield name="posInfoDTO.mchntName"  id="posInfDTO.mchntName"  readonly="true"/>
														<s:fielderror>
															<s:param>
																posInfDTO.mchntName
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
														<s:textfield name="shopDTO.shopId" id="shopDTO.shopId"  readonly="true" />
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
															name="posInfoDTO.termTransFlag" disabled="true"/>														
														<s:fielderror>
															<s:param>
																posInfDTO.termTransFlag
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
														<s:textfield name="posInfoDTO.termBrandId" id="posInfDTO.termBrandId" readonly="true" />											
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
													<s:label name="posInfoDTO.termModel"/>	
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
															name="posInfoDTO.consumePerFlag" disabled="true"/>																
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>充值允许标志：
													</td>
													<td>
													<s:select list="#{0:'不允许',1:'允许'}"
															name="posInfoDTO.reloadPerFlag" disabled="true"/>															
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
															name="posInfoDTO.reprintCtrlFlag" disabled="true"/>																
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>积分允许标志：
													</td>
													<td>
														<s:select list="#{0:'不允许',1:'允许'}"
															name="posInfoDTO.pointPerFlag" disabled="true"/>
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
														<span class="no-empty">*</span>最大交易笔数：
													</td>
													<td>
														<s:textfield name="posInfoDTO.maxTxnCnt" disabled="true"/>																												
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
														<s:select list="#{0:'无效',1:'有效'}"
															name="posInfoDTO.termStat" disabled="true"/>																											
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
													
														<edl:entityDictList displayName="posInfoDTO.termOwner"  dictType="812" tagType="1"  dictValue="${posInfDTO.termOwner}"/>																																			
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
														<span class="no-empty">*</span>公共参数版本号：
													</td>
													<td>
														<s:label name="posInfoDTO.prmVersion1"/>																																			
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>是否需要更新：
													</td>
													<td>
														<s:if  test="posInfoDTO.prmChangeFlag1==1">
														是
														</s:if>
														<s:else>
														否
														</s:else>																																		
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
														<span class="no-empty">*</span>卡BIN参数版本号：
													</td>
													<td>
														<s:label name="posInfoDTO.prmVersion2"/>																																		
													</td>
												</tr>
											</table>
										</td>										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>是否需要更新：
													</td>
													<td>
														<s:if  test="posInfoDTO.prmChangeFlag2==1">
														是
														</s:if>
														<s:else>
														否
														</s:else>																																
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
														IC卡参数版本号：
													</td>
													<td>
														<s:label name="posInfoDTO.prmVersion3"/>																																		
													</td>
												</tr>
											</table>
										</td>										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>是否需要更新：
													</td>
													<td>
														<s:if  test="posInfoDTO.prmChangeFlag3==1">
														是
														</s:if>
														<s:else>
														否
														</s:else>																																
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
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="turnBack();">
				返 回
			</button>			
	
			<div style="clear: both"></div>
		</div>
		</s:form>
	</body>
</html>
