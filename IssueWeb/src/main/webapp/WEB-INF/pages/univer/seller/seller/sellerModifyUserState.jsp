<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>修改用户状态</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>修改用户状态</span>
		</div>
		<div id="ContainBox">
			<s:form id="customerForm" name="customerForm" action="user/editUser.action" method="post">
				<input type="hidden" id="id" name="id"/>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayQueryBody();"
										style="cursor: pointer;">
										<span class="TableTop">用户信息</span>
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
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>用户编号：
													</td>
													<td>
														<s:textfield name="userDTO.userId" readonly="true"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>用户名称：
													</td>
													<td>
														<s:textfield name="userDTO.userName" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																userDTO.userName
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
														用户状态：
													</td>
													<td>
														<s:select list="#{1:'有效',0:'无效'}" name="userDTO.userState"></s:select>
													</td>
												</tr>
											</table>
										</td>
											
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty"></span>用户Email：
													</td>
													<td>
														<s:textfield name="userDTO.email" maxlength="128"></s:textfield>
														<s:fielderror>
															<s:param>
																userDTO.userEmail
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
														<span class="no-empty">*</span>销售标识：
													</td>
													<td>
														<s:select list="#{1:'是',0:'否'}" name="userDTO.isSaleFlage"></s:select>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>锁定状态：
													</td>
													<td>
													<s:if test="%{#request.login==userDTO.userId}"> 
														<s:select list="#{0:'正常',6:'锁定'}" name="userDTO.lockedState" disabled="true"></s:select>
													</s:if>
													<s:else>
													    <s:select list="#{0:'正常',6:'锁定'}" name="userDTO.lockedState" ></s:select>
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
				</s:form>
		</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="submitForm('customerForm', '${ctx}/seller/sellerUpdateUserState.action')"
											value="提 交" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.location = '${ctx}/seller/inquery.action'"
											value="返 回" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
	
	</body>
</html>