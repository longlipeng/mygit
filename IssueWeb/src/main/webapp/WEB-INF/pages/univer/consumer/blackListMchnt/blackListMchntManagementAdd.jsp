<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增黑名单</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript"><!--
		
        function inserBlackListMchnt(){
			send();
        }
        function send(){
        newForm.action='blackListMchnt/insertBlackListMchnt.action';
        newForm.submit();
        }
		</script>
	</head>
	<body >
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增黑名单</span>
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
								<td class="TableTitleFront"
									onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">黑名单信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
														<s:textfield id="startNo" name="blackListMchntDTO.mchntNo" />
														<s:fielderror>
															<s:param>
																<!--
																shopDTO.entityId
															-->
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											&nbsp;
										</td>
									</tr>


									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>备注：
													</td>
													<td>
														<s:textfield name="blackListMchntDTO.meno" id="meno" cssStyle="size:10px; width:200px;height:60px"/>
														<s:fielderror>
															<s:param>
																<!--
																shopDTO.shopAddress
															-->
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='blackListMchnt/inquiry.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="inserBlackListMchnt()">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>