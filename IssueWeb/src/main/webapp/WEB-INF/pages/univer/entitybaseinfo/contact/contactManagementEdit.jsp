<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改联系人</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"></base>
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
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>修改联系人</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">联系人信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>联系人姓名：
													</td>
													<td>
														<s:textfield name="contactDTO.contactName"
															id="contactDTO.contactName"></s:textfield>
														<s:fielderror>
															<s:param>
																contactDTO.contactName
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
														联系人性别：
													</td>
													<td>
                                        <%-- <edl:entityDictList displayName="contactDTO.gender" dictValue="${contactDTO.gender}" dictType="402" tagType="2" defaultOption="false" />
											--%>			
										
                                                        <s:select 
                                                           list="#{'1':'男','0':'女'}" 
                                                           name="contactDTO.contactGender" 
                                                           emptyOption="false"
                                                           label="联系人性别"
                                                         /> 
											
												
											<s:fielderror>
															<s:param>
																contactDTO.contactGender
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
														联系人职位：
													</td>
													<td>
														<s:textfield name="contactDTO.contactFunction" id="contactDTO.contactFunction"/>														<s:fielderror>
															<s:param>
																contactDTO.contactFunction
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
														电子邮箱：
													</td>
													<td>
														<s:textfield name="contactDTO.contactEmail" id="contactDTO.contactEmail"/>
														<s:fielderror>
															<s:param>
																contactDTO.contactEmail
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
														固定电话：
													</td>
													<td>
														<s:textfield name="contactDTO.contactTelephone" id="contactDTO.contactTelephone"/>
														<s:fielderror>
															<s:param>
																contactDTO.contactTelephone
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
														<span class="no-empty">*</span>移动电话：
													</td>
													<td>
										               <s:textfield name="contactDTO.contactMobilePhone" id="contactDTO.contactMobilePhone"/>
                                                       <s:fielderror>
															<s:param>
																contactDTO.contactMobilePhone
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
														联系人类型：
													</td>
													<td>
                                                      <edl:entityDictList displayName="contactDTO.contactType" dictValue="${contactDTO.contactType}" dictType="421" tagType="2" defaultOption="false" />
														<s:fielderror>                                                       
															<s:param>
																contactDTO.contactType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<s:if test="contactDTO.is">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>有效状态：
													</td>
													<td>
                                                        <s:select 
                                                           list="#{'1':'有效','0':'无效'}" 
                                                           name="contactDTO.validFlag" 
                                                           emptyOption="false"
                                                           label="有效状态"
                                                         /> 
														<s:fielderror>
															<s:param>
																contactDTO.validFlag
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										</s:if>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														设为默认联系人：
													</td>
													<td>
                                                      <s:hidden id="defaultFlag" name="contactDTO.defaultFlag"></s:hidden>
                                                      <s:checkbox name="defaultFlag" value="contactDTO.defaultFlag" onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								    <s:hidden name="contactDTO.contactId" id="contactDTO.contactId"/>	
								    <s:hidden name="contactDTO.entityId" id="contactDTO.entityId"/>
									<s:hidden name="entityId" />							    
								</table>
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.close()">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="document.newForm.action='${ctx}/${actionName}';newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>