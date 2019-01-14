<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>包装信息添加</title>
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
			
			function checkPackageName(){
				$("#packageName_msg").empty("");
				var packageName = document.getElementById("packageName").value;
				if(packageName.length>80){
					$("#packageName_msg").html('<ul class="errorMessage"><li><span>请填写80位内包装名称！</span></li></ul>');
					return false;
				}
				return true;
			}
			
			function addPackageSubmit(){
				if(checkPackageName()){
					maskDocAll();
					newForm.submit();
				}
			}
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增包装信息</span>
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
									<span class="TableTop">产品包装信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="insert"
								method="post">
								<s:token></s:token>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>包装号：
													</td>
													<td>
														<s:textfield name="packageDTO.packageId" disabled="true"></s:textfield>
														<s:fielderror>
															<s:param>
																packageDTO.packageId
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
														<span class="no-empty">*</span>包装名称：
													</td>
													<td>
														<s:textfield id="packageName" name="packageDTO.packageName" onchange="checkPackageName()"></s:textfield>
														<div id="packageName_msg">
															<s:fielderror>
																<s:param>
																	packageDTO.packageName
																</s:param>
															</s:fielderror>
														</div>
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
														<span class="no-empty">*</span>包装费：
													</td>
													<td>
														<s:textfield name="packageDTO.packageFee"></s:textfield>元	
														<s:fielderror>
															<s:param>
																packageDTO.packageFee
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
										
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
				onclick="window.location='packageInquery'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="addPackageSubmit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
