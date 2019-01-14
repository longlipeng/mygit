<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡作废</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayQueryBody = false;
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBody');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBody');
					isDisplayTableBody = true;
				}
			}
			
			function displayQueryBody() {
				if (isDisplayQueryBody) {
					display('QueryBody');
					isDisplayQueryBody = false;
				} else {
					undisplay('QueryBody');
					isDisplayQueryBody = true;
				}
			}
			function check(){
			    var startCardNo=document.getElementById("startCardNo").value;
				var endCardNo=document.getElementById("endCardNo").value;
				
			    if(startCardNo==null ||startCardNo==""){
					errorDisplay('起始卡号不能大于结束卡号！');
					return true;
				}
				return false;
			}
			function queryCard(){
				invalidForm.action='${ctx}/cardManage/batchCardManageAction!queryCard';
				
				invalidForm.submit();

			}
		</script>
	</head>
	<body><div align="left"> 
 
		<%@include file="/commons/messages.jsp"%> 
		</div><div class="TitleHref">
			<span>卡片管理-->卡作废</span>
		</div>
		
		<!-- 查询条件 -->
		<s:form id="invalidForm" name="invalidForm" action="" method="post">
		<div id="queryBox" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
			<tr>
				<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
				<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront" onclick="displayQueryBody();"
								style="cursor: pointer;">
								<span class="TableTop">查询条件</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
				</table>
				<table width="80%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr height="35">
								<td width="" align="right">
									<span>起始卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="batchCardDTO.startCardNo" id="startCardNo" size="30" maxlength="19"/>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											batchCardDTO.startCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td width="" align="right">
									<span>结束卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="batchCardDTO.endCardNo" id="endCardNo" size="30" maxlength="19"></s:textfield>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											batchCardDTO.endCardNo
										</s:param>
									</s:fielderror>
								</td>
								
								<td align="right">
									<input type="button" class="bt" style="margin: 5px" onclick="queryCard()" value="查 询" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	</body>
</html>