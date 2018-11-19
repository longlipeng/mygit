<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易路由添加</title>
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
		function sub() {
			var bin = document.getElementById('cardRouteDTO.binVal').value;
			if (bin.match(/\D+/g)) {
				alert("卡bin必须为数字!");
				document.getElementById('cardRouteDTO.binVal').value = "";
				return;
			}
	
			var length = document.getElementById('cardRouteDTO.length').value;
			if (length.match(/\D+/g)) {
				alert("卡号长度必须为数字!");
				document.getElementById('cardRouteDTO.length').value = "";
				return;
			}
			if (bin.length<4 || bin.length>10) {
				alert("卡bin长度为4-10位!");
				document.getElementById('cardRouteDTO.binVal').value = "";
				return;
			}
			if (length == null || length == "") {
				alert("卡号长度必须填写");
				return;
			}
			if (parseInt(length) < 13 || parseInt(length) > 19) {
				alert("卡号长度为13-19!");
				document.getElementById('cardRouteDTO.length').value = "";
				return;
			}
			maskDocAll();
			newForm.submit();
		}
	</script>
</head>
<body onload="load();">
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>新增交易路由</span>
	</div>
	<div id="ContainBox">
		<table width="98%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="B5B8BF" align="center">
			<tr>
				<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
					<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" onclick="displayServiceTable();"
								style="cursor: pointer;"><span class="TableTop">交易路由信息</span>
							</td>
							<td class="TableTitleEnd">&nbsp;</td>
						</tr>
					</table>
					<div id="serviceTable">
						<s:form id="newForm" name="newForm" action="addCardRoute" method="post">
							<s:token></s:token>
							<table width="100%">
								<tr>
									<td>
										<span style="width: 120px; text-align: right;">
											<span class="no-empty">*</span>卡bin：
										</span> 
										<span>
											<s:textfield id="cardRouteDTO.binVal" name="cardRouteDTO.binVal" maxlength="10"></s:textfield>
											<!--&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">卡bin长度为4-10位</span>-->
											<s:fielderror>
												<s:param>
													cardRouteDTO.binVal
												</s:param>
											</s:fielderror>
										</span>
									</td>
									<td>
										<span style="width: 120px; text-align: right;">
											<span class="no-empty">*</span>卡号长度：
										</span>
										<span>
											<s:textfield id="cardRouteDTO.length" name="cardRouteDTO.length" maxlength="2"></s:textfield>
											<!-- &nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">卡号长度在13-19之间</span> -->
											<s:fielderror>
												<s:param>
													cardRouteDTO.length
												</s:param>
											</s:fielderror>
										</span>
									</td>
									<td>
										<span style="width: 120px; text-align: right;">
											<span class="no-empty">*</span>发行机构：
										</span>
										<span>
											<s:select id="entityId" list="entityList" name="cardRouteDTO.issInsIdCd" listKey="entityId" listValue="entityName"></s:select>
											<s:fielderror>
												<s:param>
													cardRouteDTO.issInsIdCd
												</s:param>
											</s:fielderror>
										</span>
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
		<button class='bt' type="button" style="float: right; margin: 5px 10px" onclick="window.location='cardRouteList.action'">返 回</button>
		<button class='bt' style="float: right; margin: 5px" onclick="sub();"> 提 交</button>
		<div style="clear: both"></div>
	</div>
</body>
</html>
