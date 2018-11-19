<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>审核商户</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}

	function sub(flag) {
		newForm.action = '${ctx}/merchantVerifier/pass.action';
		document.getElementById("stateFlag").value = flag;
		newForm.submit();
	}
    function setPunishDiv(){
    	document.getElementById('punishDiv').style.display='none';
    	if (document.getElementById('isPunish').value==1) {
        	document.getElementById('punishDiv').style.display='block';        
      	}
    }
	function InitPage() {
	      setPunishDiv();  
		}
</script>
	</head>
	<body onload="InitPage()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref" align="left">
			<span>审核商户</span>
		</div>
		<s:form id="newForm" name="newForm">
			<s:hidden name="merchantDTO.isPunish" id="isPunish"></s:hidden>
			<s:hidden name="stateFlag" id="stateFlag"></s:hidden>
      		<s:hidden name="entityId" value="%{merchantDTO.entityId}"></s:hidden>
      		<s:hidden name="fatherEntityId" value="%{merchantDTO.fatherEntityId}"></s:hidden>
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							 
<!-- 
							<div id="serviceTable">
								<table width="100%" style="table-layout: fixed;">

									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											 -->
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('serviceTable');"
														style="cursor: pointer;">
														<span class="TableTop">商户信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>
											<div id="serviceTable">
												<table width="100%" style="table-layout: fixed;">

													<tr>
													<%-- 
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		商户编号：
																	</td>
																	<td>
																		<s:label name="merchantDTO.entityId"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													--%>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		<span class="no-empty">*</span>商户号：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantCode"></s:label>
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
																		<span class="no-empty">*</span>商户注册名称：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantName"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		商户实际店名：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantRealityName"></s:label>
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
																		商户英文名称：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantEnglishName"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		<span class="no-empty">*</span>商户级别：
																	</td>
																	<td>
																			<edl:entityDictList
																				displayName="merchantDTO.merchantType"
																				dictValue="${merchantDTO.merchantType}"
																				dictType="181" tagType="1" defaultOption="false" />
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
																		<span class="no-empty">*</span>发票名称：
																	</td>
																	<td>
																		<s:label name="merchantDTO.invoiceName"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		<span class="no-empty">*</span>商户属性：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantAttribute"
																			dictValue="${merchantDTO.merchantAttribute}"
																			dictType="184" tagType="1" defaultOption="false" />
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
																		老系统商户号：
																	</td>
																	<td>
																		<s:label name="merchantDTO.legacyMerchantId"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		外部系统代码：
																	</td>
																	<td>
																		<s:label name="merchantDTO.externalId"></s:label>
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
																		<span class="no-empty">*</span>市场代表：
																	</td>
																	<td>
																		<s:label name="merchantDTO.salesmanId"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		<span class="no-empty">*</span>加盟日期：
																	</td>
																	<td>
																		<td>
																			<s:label name="merchantDTO.joinDate"></s:label>
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
																		POS机申请台数：
																	</td>
																	<td>
																		<s:label name="merchantDTO.postApplyNum"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		<span class="no-empty">*</span>是否布放移动POS：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.isMovePost == 1?'是':'否'" />
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
																		<span class="no-empty">*</span>商户行业：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantIdustry"
																			dictValue="${merchantDTO.merchantIdustry}"
																			dictType="185" tagType="1" defaultOption="false" />
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
																		商户状态：
																	</td>
																	<td>
																		<s:select id="state" name="merchantDTO.merchantState"
																			list="#{'1':'已审核','0':'无效','2':'未审核','3':'审核未通过','4':'审核中'}"
																			disabled="true"></s:select>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		开通网站管理：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.enableWebsite == 1?'是':'否'" />
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
																		<span class="no-empty">*</span>商户交易类型：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantTransactionType"
																			dictValue="${merchantDTO.merchantTransactionType}"
																			dictType="186" tagType="1" defaultOption="false" />
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		是否支持&nbsp;&nbsp;
																		<br />
																		互联网交易：
																	</td>
																	<td>
																		<s:property value="merchantDTO.ePayIn == 1?'是':'否'" />
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
																		商户后台返回URL：
																	</td>
																	<td>
																		<s:label name="merchantDTO.mchntUrl"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		证书DN信息：
																	</td>
																	<td>
																		<s:label name="merchantDTO.dnInfo"></s:label>
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
																		商户经营时间：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantManageTime"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		交易查询次数：
																	</td>
																	<td>
																		<s:label name="merchantDTO.txnQryTimes"></s:label>
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
																		证书号：
																	</td>
																	<td>
																		<s:textarea name="merchantDTO.certificateNo" cols="20"
																			rows="5" readonly="true"></s:textarea>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 110px; text-align: right;">
																		注释信息：
																	</td>
																	<td>
																		<s:textarea name="merchantDTO.annotation" cols="20"
																			rows="5" readonly="true"></s:textarea>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
												<div id="webDiv">
									<table width="100%" style="table-layout: fixed;">
										<tr>
											<td>
												<table width="100%" style="table-layout: fixed;">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站登录名：
														</td>
														<td>
															<s:label name="merchantDTO.websiteUserName"
																id="merchantDTO.websiteUserName" 
																onblur="checkwebName();" />
															<s:fielderror>
																<s:param>
																	merchantDTO.websiteUserName
																</s:param>
															</s:fielderror>
															<div id="message" style="color: red"/>
														</td>

													</tr>
												</table>
											</td>
											
										</tr>
									</table>
								</div>
											</div>
										</td>
									</tr>
									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('paraTable1');"
														style="cursor: pointer;">
														<span class="TableTop">联系方式</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>
											<div id="paraTable1">
												<table width="100%" style="table-layout: fixed;">
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		客户经理姓名：
																	</td>
																	<td>
																		<s:label name="merchantDTO.customerManagerName"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		客户经理联系电话1：
																	</td>
																	<td>
																		<s:label name="merchantDTO.customerManagerTelephone1"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		客户经理联系电话2：
																	</td>
																	<td>
																		<s:label name="merchantDTO.customerManagerTelephone2"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty"></span>商户联系人：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantLinkman"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty"></span>商户联系电话1：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantTelephone"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty"></span>商户联系电话2：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantTelephone2"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty"></span>商户传真：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantFax"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户网址：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantWebsite"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户地址：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantAddress"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		英文地址 ：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantEnglishAddress"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty"></span>商户邮编：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantPostcode"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>


									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('paraTable2');"
														style="cursor: pointer;">
														<span class="TableTop">结算信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>

											<div id="paraTable2">
												<table width="100%" style="table-layout: fixed;">
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户结算代理：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.settAgencyId"
																			dictValue="${merchantDTO.settAgencyId}"
																			dictType="422" tagType="1" defaultOption="false" />
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户结算方式：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantSettType"
																			dictValue="${merchantDTO.merchantSettType}"
																			dictType="187" tagType="1" defaultOption="false" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<!--  
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户开户银行：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantBank"></s:label>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户账户：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantAccount"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		<span class="no-empty">*</span>商户结算账户：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantSettAccount"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													-->
												</table>
											</div>
										</td>
									</tr>





									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('paraTable3');"
														style="cursor: pointer;">
														<span class="TableTop">证件信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>

											<div id="paraTable3">
												<table width="100%" style="table-layout: fixed;">
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		组织机构代码：
																	</td>
																	<td>
																		<s:label name="merchantDTO.orgCode"></s:label>
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户法人：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantLegalPerson"></s:label>
																	</td>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		营业执照号码：
																	</td>
																	<td>																		
																		<s:label name="merchantDTO.businessLicenseNumber"></s:label>
																	</td>
																</tr>
															</table>
														</td> 
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		营业执照有效期From：
																	</td>
																	<td>
																		<s:label name="merchantDTO.businessLicenseFrom"></s:label>
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		To：
																	</td>
																	<td>
																		<s:label name="merchantDTO.businessLicenseTo"></s:label>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		法人身份证号：
																	</td>
																	<td>
																		<s:label name="merchantDTO.legalPersonIdno"></s:label>
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户开业时间：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantOpeningTime"></s:label>
																	</td>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户分支机构数量：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantBranchNum"></s:label>
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户注册资本：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantRegisteredCapital"></s:label>
																	</td>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户员工数：
																	</td>
																	<td>
																		<s:label name="merchantDTO.merchantEmployeesNum"></s:label>
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		营业用地形式：
																	</td>
																	<td>
																		<edl:entityDictList displayName="merchantDTO.landType"
																			dictValue="${merchantDTO.landType}" dictType="188"
																			tagType="1" defaultOption="false" />
																	</td>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户经营地段：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantSction"
																			dictValue="${merchantDTO.merchantSction}"
																			dictType="189" tagType="1" defaultOption="false" />
																	</td>

																</tr>
															</table>
														</td>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户知名度：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.merchantPopularity"
																			dictValue="${merchantDTO.merchantPopularity}"
																			dictType="190" tagType="1" defaultOption="false" />
																	</td>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table width="100%" style="table-layout: fixed;">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		上年度POS消费总金额：
																	</td>
																	<td>
																		<s:label name="merchantDTO.previousYearPos"></s:label>
																	</td>

																</tr>
															</table>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>


									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('paraTable4');"
														style="cursor: pointer;">
														<span class="TableTop">风控信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>
											<div id="paraTable4">
							<table width="100%" style="table-layout: fixed;">
								<tr>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														是否异地收单:
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isAllopatryAcquire"  disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														是否有内外卡收单经验：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isAcquireExp" disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
								<tr>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														申请资料是否含有照片：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isApplyMaterialPic"  disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													征信系统是否已征信：
												</td>
												<td>
													<s:select name="merchantDTO.isCreditInvestigation"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>	
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													法人身份信息是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectLegalPerson"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													不良出票人系统是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectBadnessDrawer"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户照片信息是否已存档：
												</td>
												<td>
													<s:select name="merchantDTO.isPhotoOnFile"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户是否为其他收单机构拒绝：
												</td>
												<td>
													<s:select name="merchantDTO.isRejectedAcquirer"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isMerchantLawsuit"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户负责人（法人代表或高管）是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isPrincipalLawsuit"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户被执法部门处罚记录：
												</td>
												<td>
													<s:select name="merchantDTO.isPunish" id="isPunish"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户负责人（法人）的信用状况：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.principalCreditStatus"
														dictValue="${merchantDTO.principalCreditStatus}"
														dictType="191" tagType="2" defaultOption="false" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													五证齐全。营业执照、税务登记证、组织机构代码证、法人身份证、开户许可证齐全且未过期：
												</td>
												<td>
													<s:select name="merchantDTO.isFiveCertificateAll"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户信息调查表填写完整。是否分支机构联系人有签名及盖公章：
												</td>
												<td>
													<s:select name="merchantDTO.isSignHave"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<div id="punishDiv">
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														商户被执法部门处罚记录内容：
													</td>
													<td>

														<s:textarea name="merchantDTO.punishContent" cols="20"
														   disabled="true" drows="5"></s:textarea>
													</td>
												</tr>
											</table>
									</td>
									</div>
								</tr>
							</table>
						</div>
										</td>
									</tr>


									<tr>
										<td width="100%" height="10" align="left" valign="top"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td class="TableTitleFront"
														onclick="displayTable('paraTable');"
														style="cursor: pointer;">
														<span class="TableTop">参数设定</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>

											<div id="paraTable">
												<table width="100%" style="table-layout: fixed;">
													<tr>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		结算手续费修改标志：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.commissionFee == 1?'是':'否'" />
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		直接结算而无需核对结算单：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.reimburseWithoutCheck == 1?'是':'否'" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户消费暂停标志：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.purchasePause == 1?'是':'否'" />
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		商户结算暂停标志：
																	</td>
																	<td>
																		<s:property
																			value="merchantDTO.reimbursePause == 1?'是':'否'" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table style="text-align: left; width: 100%">
																<tr>
																	<td style="width: 170px; text-align: right;">
																		付款方式标志：
																	</td>
																	<td>
																		<edl:entityDictList
																			displayName="merchantDTO.reimbursementType"
																			dictValue="${merchantDTO.reimbursementType}"
																			dictType="106" tagType="1" defaultOption="false" />
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
							</s:form>
							<div id="blank" style="height: 20px">
								&nbsp;&nbsp;
							</div>
							
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
									<span class="TableTop">合同基本信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:iterator value="contractList"  status="contract">
						<div id="baseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">商户号：</td>
                                    <td><s:textfield id="merchantCode" name="contractList[%{#contract.index}].merchantCode" readonly="true" />
                                        
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">商户名称：</td>
                                    <td><s:textfield id="merchantName" name="contractList[%{#contract.index}].merchantName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算规则名称：</td>
                                    <td>
                                    <s:textfield id="ruleName" name="contractList[%{#contract.index}].ruleName" cssClass="readonly" readonly="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期类型：</td>
                                    <td><s:textfield id="clearTp" name="contractList[%{#contract.index}].clearTp" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">有效期开始时间：</td>
                                    <td>
                                    	<s:textfield name="contractList[%{#contract.index}].contractStartDate"
												id="contractStartDate"  readonly="true" >
										</s:textfield>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">有效期结束时间：</td>
                                    <td>
                                        <s:textfield name="contractList[%{#contract.index}].contractEndDate"  id="contractEndDate" readonly="true" />
                                       
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
						</s:iterator>
						<!-- 
						<div id="bankTable"
								style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
								<ec:table items="contractList" var="map" width="100%"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" form="bankForm"
									showPagination="false" sortable="false">
									<ec:row >
									<ec:column property="consumerContractId" title="合同号" width="10%">
										${map.consumerContractId}
									</ec:column>
									<ec:column property="merchantId" title="商户编号" width="15%" escapeAutoFormat="true">
									</ec:column>
									<ec:column property="merchantName" title="商户名称" width="10%" />
									<ec:column property="ruleNo" title="结算规则" width="10%"/>
									<ec:column property="ruleName" title="结算规则名称" width="10%"/>
									<ec:column property="clearTp" title="结算周期类型" width="10%"/>
									<ec:column property="contractStartDate" title="有效期开始时间" width="15%"/>
									<ec:column property="contractEndDate" title="有效期结束时间" width="15%"/>
								</ec:row>
								</ec:table>

							</div>
							 -->
					</td>
				</tr>
			</table>
		</div>
				<div id="blank" style="height: 20px">
			&nbsp;&nbsp;
		</div>
		
		<div id="ServiceContractBox" style="margin-top:10px">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									onclick= "displayTable('serviceContractTable')";
									style="cursor: pointer;">
									<span class="TableTop">账户明细信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceContractTable">
							<ec:table items="accTypeContractDTOList" var="feeDetDTO"
								width="100%" 
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								autoIncludeParameters="false" tableId="serviceContract"
								showPagination="false" sortable="false">
								<ec:row>

									<ec:column property="acctypeContractId" title="账户合同号"
										width="15%">
									</ec:column>
									<ec:column property="acctypeId" title="账户号" width="15%" />
									<ec:column property="ruleNo" title="费用计算规则编号" width="15%" />
									<ec:column property="ruleName" title="费用计算规则名称" width="15%" />
								</ec:row>
							</ec:table>
					</td>
				</tr>
			</table>
		</div>
		</div>
				<div id="blank" style="height: 20px">
			&nbsp;&nbsp;
		</div>
		
		<table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
										<s:form id="bankForm" name="bankForm" action="" method="post">
											<s:hidden name="entityId" />
											<s:hidden name="merchantDTO.entityId"></s:hidden>
											<s:hidden name="merchantDTO.fatherEntityId"></s:hidden>
											<div id="bankTitle"
												style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td class="TableTitleFront" style="cursor: pointer"
															onclick="showOrHideDiv('bankTable')">
															<span class="TableTop">银行信息</span>
														</td>
														<td class="TableTitleEnd">
															&nbsp;
														</td>
													</tr>
												</table>
											</div>
											<div id="bankTable"
												style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
												<ec:table items="bankList" var="map" width="100%"
													action="${ctx}/bankManagement"
													imagePath="${ctx}/images/extremecomponents/*.gif"
													view="html" autoIncludeParameters="false" form="bankForm"
													showPagination="false" sortable="false">
													<ec:row>
														<ec:column property="bankName" title="银行名称" width="30%" />
														<ec:column property="bankAccount" title="银行账户" width="30%" />
														<ec:column property="bankAccountName" title="银行账户名称"
															width="30%" />
													</ec:row>
												</ec:table>

											</div>
										</s:form>
									</td>
								</tr>
							</table>
							<br />
<%--							<div id="ContainBox">--%>
<%--								<table width="98%" border="0" cellpadding="0" cellspacing="1"--%>
<%--									bgcolor="B5B8BF">--%>
<%--									<tr>--%>
<%--										<td width="100%" height="10" align="left" valign="top"--%>
<%--											bgcolor="#FFFFFF">--%>
<%--											<table width="100%" height="20" border="0" cellpadding="0"--%>
<%--												cellspacing="0">--%>
<%--												<tr>--%>
<%--													<td class="TableTitleFront"--%>
<%--														onclick="displayTable('contactTable');"--%>
<%--														style="cursor: pointer;">--%>
<%--														<span class="TableTop">联系人信息</span>--%>
<%--													</td>--%>
<%--													<td class="TableTitleEnd">--%>
<%--														&nbsp;--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--											<div id="contactTable">--%>
<%--												<ec:table items="merchantDTO.contactList" var="contactDTO"--%>
<%--													width="100%" action=""--%>
<%--													imagePath="${ctx}/images/extremecomponents/*.gif"--%>
<%--													view="html" autoIncludeParameters="false" tableId="bank"--%>
<%--													showPagination="false" sortable="false">--%>
<%--													<ec:row>--%>
<%--														<ec:column property="contactName" title="联系人姓名"--%>
<%--															width="20%" />--%>
<%--														<ec:column property="contactFunction" title="联系人职位"--%>
<%--															width="40%" />--%>
<%--														<ec:column property="contactMobilePhone" title="联系人电话"--%>
<%--															width="40%" />--%>
<%--													</ec:row>--%>
<%--												</ec:table>--%>
<%--												<table border="0" cellpadding="0" cellspacing="0"--%>
<%--													width="100%">--%>
<%--													<tr>--%>
<%--														<td align="right">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--												</table>--%>
<%--											</div>--%>
<%--										</td>--%>
<%--									</tr>--%>
<%--								</table>--%>
<%--							</div>--%>
							<div id="blank" style="height: 20px">
								&nbsp;&nbsp;
							</div>


							<div id="buttonDiv" style="margin: 5px 8px 0px;">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td align="right">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input type="button" class="bt" style="margin: 5px"
															onclick="sub('1');" value="通过" />
														<input type="button" class="bt" style="margin: 5px"
															onclick="sub('3');" value="不通过" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
	</body>
</html>