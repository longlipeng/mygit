<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡邮寄审核</title>
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
			
			function queryOrder(){
				document.getElementById("queryForm").action='${actionName}!list';
				document.getElementById("queryForm").submit();
			}
			
			function submitForm(operation) {
				var count=0;
				var idNo;
				var idType;
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						orderId = document.getElementsByName("choose").item(i).value;
						var order_type = orderId.split(",");
						EditForm.ID_NO.value = order_type[0];
						EditForm.ID_TYPE.value = order_type[1];
						count++;
					}
				}
				if(count==1){
						document.EditForm.action="cardMailAction!listBuyOrder";
						document.EditForm.submit();
				}else{
					errorDisplay("请选择一条记录!");
				}
			}

			function cancelOperation(){
		        EditForm.submit();
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>卡邮寄管理-->卡邮寄审核</span>
		</div>
		
		<!-- 查询条件 -->
		<%-- <div id="queryBox">
			<%@ include file="../order/orderview/stockOrderQueryInput.jsp" %>
		</div> --%>
		
		<s:form id="confirmForm" name="confirmForm" action="" method="post">
			<s:hidden name="applyAndBindCardDTO.ID_NO" />
		<div id="ContainBox">
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
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
						
						<ec:table items="cardInfos" var="map" width="100%" form="confirmForm"
							action="${ctx}/cardMailQueryAction!list"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="cardInfo" autoIncludeParameters="false">
							<ec:row>
								<ec:column property="null" alias="choose" title="选择"
									width="5%" sortable="false" 
									viewsAllowed="html">
									<input type="radio" name="choose" value="${map.ID_NO},${map.ID_TYPE}" />
									<input type="hidden" name="ec_chooseIdType"
										value="${map.ID_TYPE}" />
								</ec:column>
								<ec:column property="ID_NO" title="证件号" width="6%" />
								<ec:column property="ID_TYPE" title="证件类型" width="10%" />
								<ec:column property="FIRST_NAME" title="持卡人名称" width="10%" />
								<ec:column property="RECIPIENT_NAME" title="收件人名称" width="10%" />
								<ec:column property="RECIPIENT_ADDR" title="收件人地址" width="10%" />
								<ec:column property="RECIPIENT_PHONE" title="收件人电话" width="5%" />
								<ec:column property="APPLY_STATUS" title="卡申请状态"  width="10%"  cell="dictInfo" alias="209">
								</ec:column>
							</ec:row>
						</ec:table>
						</div>
					</td>
				</tr>
			</table>
			
		</div>
		</s:form>
		<br/>
		<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm" action="cardInformationAction!operate">
								<s:hidden name="ApplyAndBindCardDTO.idNo" id="ID_NO"/>
								<s:hidden name="ApplyAndBindCardDTO.idType" id="ID_TYPE"/>
								<s:hidden name="operation" id="operation" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<display:security urlId="312032">
											<button class='btn' style="float: right; margin: 7px"
												onclick="submitForm('confirm');">
												审核
											</button>
											</display:security>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>