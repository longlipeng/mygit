<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增信息</title>
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
			function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8 || key.keyCode== 45)
					return true;
				else
					return false;
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增信息</span>
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
								<td class="TableTitleFront" onclick="displayTable('serviceTable');" style="cursor: pointer;">
									<span class="TableTop">人员信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="">
								<s:hidden name="entityId" />
								<s:hidden name="merchantDTO.entityId"></s:hidden>
								<s:hidden name="contactDTO.entityId"></s:hidden>
								<s:hidden name="contactDTO.contactId" id="contactDTO.contactId" />
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>姓名：
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
														性别：
													</td>
													<td>
														
														<s:select list="#{'1':'男','0':'女'}"
															name="contactDTO.contactGender"></s:select>
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
														职位：
													</td>
													<td>
														<s:textfield name="contactDTO.contactFunction"
															id="contactDTO.contactFunction" />
														<s:fielderror>
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
														<s:textfield name="contactDTO.contactEmail"
															id="contactDTO.contactEmail" />
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
														<s:textfield name="contactDTO.contactTelephone"
															id="contactDTO.contactTelephone"
															onkeypress="return check(event);" />
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
														<s:textfield name="contactDTO.contactMobilePhone"
															id="contactDTO.contactMobilePhone" />
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
														类型：
													</td>
													<td>
														<edl:entityDictList displayName="contactDTO.contactType"
															dictValue="${contactDTO.contactType}" dictType="421"
															tagType="2" defaultOption="false" />
														<s:fielderror>
															<s:param>
																contactDTO.contactType
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
														<span class="no-empty">*</span>有效状态：
													</td>
													<td>
														<s:select list="#{'1':'有效','0':'无效'}"
															name="contactDTO.validityFlag" emptyOption="false"
															label="有效状态" />
														<s:fielderror>
															<s:param>
																contactDTO.validityFlag
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
														<span class="no-empty">*</span>证件类型：
													</td>
													<td>
														<edl:entityDictList displayName="contactDTO.papersType"
															dictValue="${contactDTO.papersType}" dictType="140"
															tagType="2" defaultOption="false" />
														<s:fielderror>
															<s:param>contactDTO.papersType</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>证件号码：
													</td>
													<td>
														<s:textfield name="contactDTO.papersNo" id="papersNo" />
														<s:fielderror>
															<s:param>contactDTO.papersNo</s:param>
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
														证件开始日期：
													</td>
													<td>
														<s:textfield name="contactDTO.starValidity"
															id="starValidity" size="20"
															onfocus="dateClick(this)" cssClass="Wdate">
														</s:textfield>
														<s:fielderror>
															<s:param>contactDTO.starValidity</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														证件截止日期：
													</td>
													<td>
														<s:textfield name="contactDTO.endValidity"
															id="endValidity" size="20"
															onfocus="dateClick(this)" cssClass="Wdate">
														</s:textfield>
														<s:fielderror>
															<s:param>contactDTO.endValidity</s:param>
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
														设为默认信息：
													</td>
													<td>
														<s:radio list="#{1: '是', 0: '否'}"
															name="contactDTO.defaultFlag" value="1"></s:radio>
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick=window.close();>
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="document.newForm.action='${ctx}/customer/insertContact.action';newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>