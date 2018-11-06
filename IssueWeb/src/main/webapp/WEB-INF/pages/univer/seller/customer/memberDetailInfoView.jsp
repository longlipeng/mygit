<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>会员信息</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			function back() {
			
							window.location="${ctx}/member/memberInfoInquery.action";
			}
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		
		<div class="TitleHref">
			<span>会员信息</span>
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
								<td class="TableTitleFront" 
									style="cursor: pointer;">
									<span class="TableTop">会员基本信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">	
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														昵称：
													</td>
													<td>
														<s:text name="memberQueryDTO.nickNm"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.nickNm
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
														姓名：
													</td>
													<td>

														<s:text name="memberQueryDTO.realNm"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.realNm
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														性别：
													</td>
													<td>
														<s:text name="memberQueryDTO.sexDiffer"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
														出生日期：
													</td>
													<td>
                                                   <s:text name="memberQueryDTO.birYear" />-<s:text name="memberQueryDTO.birMonth" />-<s:text name="memberQueryDTO.birDay" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														电子邮箱：
													</td>
													<td>
														<s:text name="memberQueryDTO.email"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.email
															</s:param>
														</s:fielderror>
													</td>

												</tr>
											</table>

										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
														证件类型：
													</td>
													<td>

														<s:text name="memberQueryDTO.certTp"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.certTp
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width:80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														证件号码：
													</td>
													<td>
														<s:text name="memberQueryDTO.certNo"/>

														<s:fielderror>
															<s:param>
																memberQueryDTO.certNo
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
													手机号码：

													</td>
													<td>
														<s:text name="memberQueryDTO.mobile"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.mobile
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														地址：
													</td>
													<td>
														<s:text name="memberQueryDTO.address"/>

														<s:fielderror>
															<s:param>
																memberQueryDTO.address
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
													邮编：

													</td>
													<td>
														<s:text name="memberQueryDTO.zipCd"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.zipCd
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 80%">
												<tr>
													<td style="width: 110px; text-align: left;">
														注册类型：
													</td>
													<td>
														<s:text name="memberQueryDTO.regCardTp"/>

														<s:fielderror>
															<s:param>
																memberQueryDTO.regCardTp
														</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 90%">
												<tr>
													<td style="width: 110px; text-align: left;">
													注册时间：

													</td>
													<td>
														<s:text name="memberQueryDTO.recCrtTs"/>
														<s:fielderror>
															<s:param>
																memberQueryDTO.recCrtTs
														</s:param>
														</s:fielderror>
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
						</br>
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
													<span class="TableTop">其它信息</span>
												</td>
												<td class="TableTitleEnd">
													&nbsp;
												</td>
											</tr>
										</table>
										<div id="serviceTable">
											<table width="100%" style="table-layout: fixed;">
											<tr>
													<td>
														<table style="text-align: left; width: 67%">
															<tr><td style="text-align:left;">
																	公司名称：
																</td>
																<td >
																<s:if test="memberQueryDTO.corpNm != null && memberQueryDTO.corpNm != ''">
																	<s:text name="memberQueryDTO.corpNm"/>
																</s:if>		
																<br></td>
																
																<td style="text-align:left;">
																	公司地址：
																</td>
																<td>
																<s:if test="memberQueryDTO.corpAddr != null && memberQueryDTO.corpAddr != ''">
																<s:text name="memberQueryDTO.corpAddr"/>	
                                                                </s:if>
																<br></td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<table style="text-align: left; width: 100%">
															<tr>
																<td style="text-align: left;">
																	邮编：
																</td>
																<td>
																<s:if test="memberQueryDTO.corpZipCd != null && memberQueryDTO.corpZipCd != ''">
																	<s:text name="memberQueryDTO.corpZipCd"/>
																</s:if>
																</td>
																<td style="text-align:left;">
																	公司电话：
																</td>
																<td><s:if test="memberQueryDTO.comPhoneSection != null && memberQueryDTO.comPhoneSection != ''">
																	<s:text
																		name="memberQueryDTO.comPhoneSection" 
																		/>
																		 </s:if>
																	&nbsp; -&nbsp;
																	<s:if test="memberQueryDTO.comPhoneNo != null && memberQueryDTO.comPhoneNo != ''">
																	<s:text name="memberQueryDTO.comPhoneNo"/>
																</s:if>
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
		</br>

	<div align=center>
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
										<span class="TableTop">持有卡片信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							
								<ec:table tableId="acctype" items="memberQueryDTO.memberQueryDTOs"
									var="map" width="100%" form="EditForm"
									action="${ctx}/issuer!inquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row >														
										<ec:column property="cardNo" title="卡号" width="10%" >
										</ec:column>
										<ec:column property="cardValidityPeriod" title="有效期" width="20%" />
										<ec:column property="productNm" title="产品名称" width="20%" />
										<ec:column property="totalBalance" title="总金额"width="20%" />
										<ec:column property="balance" title="可用金额" width="20%" />
										<ec:column property="congeal" title="冻结金额"width="20%" />
									</ec:row>
								</ec:table>
						</td>
					</tr>
				</table>
			</div>
			<div><button class='bt' style="float: right; margin: 5px 10px"
				onclick="back();">
				返 回
			</button></div>
	</body>
</html>