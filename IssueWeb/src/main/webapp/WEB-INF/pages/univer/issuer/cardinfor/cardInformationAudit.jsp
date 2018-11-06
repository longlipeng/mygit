<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>卡申请审核</title>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
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

	function queryOrder() {
		document.getElementById("queryForm").action = '${actionName}!list';
		document.getElementById("queryForm").submit();
	}

	function submitForm(operation) {
		var count = 0;
		var idNo;
		var idType;
		var applyDateSeconds;

		for (i = 0; i < document.getElementsByName("choose").length; i++) {
			if (document.getElementsByName("choose").item(i).checked) {
				orderId = document.getElementsByName("choose").item(i).value;
				var order_type = orderId.split(",");
				EditForm.ID_NO.value = order_type[0];
				EditForm.ID_TYPE.value = order_type[1];
				EditForm.APPLY_DATE_SECONDS.value = order_type[2];
				count++;
			}
		}
		if (count == 1) {
			document.EditForm.action = "cardInformationAction!listBuyOrder";
			document.EditForm.submit();
		} else {
			errorDisplay("请选择一条记录!");
		}
	}
	
	function sub() {
		var startDate = document.getElementById("startDate").value;
		var endDate =document.getElementById("endDate").value;

		if(startDate>endDate){
			alert("起始日期不能大于终止日期！");
			return;
		}
	
		document.queryForm.submit();
	}
	
	
	
	
	function cancelOperation() {
		EditForm.submit();
	}
</script>
</head>
<body>

	<%@ include file="/commons/messages.jsp"%>

	<div class="TitleHref">
		<span>卡申请管理-->卡申请审核</span>
	</div>

	<!-- 查询条件start -->
	<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 10px">
		<div id="queryTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">查询条件</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div id="queryTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:form id="queryForm" name="queryForm"
				action="cardInformationQueryAction!list	" method="post">
				<s:hidden name="applyAndBindCardDTO.entityId" />
				<s:hidden name="applyAndBindCardDTO.defaultEntityId" />
				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td width="90" align=right>开始时间：</td>
						<td width="160">
							<input type="text" id="startDate"
							name="applyAndBindCardDTO.startDate" onclick="dateClick(this);"
							class="Wdate" value="${applyAndBindCardDTO.startDate}" />
						</td>

						<td width="90" align=right>结束时间：</td>
						<td width="160">
							<input type="text" id="endDate"
							name="applyAndBindCardDTO.endDate" onclick="dateClick(this);"
							class="Wdate" value="${applyAndBindCardDTO.endDate}" />
						</td>
					</tr>
					<tr>
						<td style="width: 110px; text-align: right;">证件号：</td>
						<td>
							<s:textfield name="applyAndBindCardDTO.idNo" />
							<s:fielderror>
								<s:param>applyAndBindCardDTO.idNo</s:param>
							</s:fielderror></td>
						<!-- 
						<td style="width: 110px; text-align: right;">卡申请状态：</td>
						<td><s:select name="applyAndBindCardDTO.applyStatus"
								id="applyStatus"
								list="#{'':'--请选择--', '0':'审核中', '1':'已审核','2':'审核拒绝'}">
							</s:select>
						</td>
						 -->
					</tr>
					<tr>
						<td style="width: 110px; text-align: right;">持卡人：</td>
						<td><s:textfield name="applyAndBindCardDTO.firstName" />
							<s:fielderror>
								<s:param>applyAndBindCardDTO.firstName</s:param>
							</s:fielderror>
						</td>

						<td style="width: 110px; text-align: right;">收件人：</td>
						<td><s:textfield name="applyAndBindCardDTO.recipient_name" />
							<s:fielderror>
								<s:param>applyAndBindCardDTO.recipient_name</s:param>
							</s:fielderror>
						</td>

						<td align="right" style="text-align: right;" colspan="4">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt"
											style="float: right; margin-right: 80px;" onclick="sub()"
											value="查 询" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
								</tr>
							</table>
						</td>

					</tr>
				</table>
			</s:form>
		</div>

	</div>
	<!-- 查询条件end -->

	<s:form id="confirmForm" name="confirmForm" action="" method="post">
		<s:hidden name="applyAndBindCardDTO.ID_NO" />
		<s:hidden name="applyAndBindCardDTO.applyStatus" />
		<s:hidden name="applyAndBindCardDTO.entityId" />

		<s:hidden name="applyAndBindCardDTO.startDate" />
		<s:hidden name="applyAndBindCardDTO.endDate" />
		<s:hidden name="applyAndBindCardDTO.idNo" />
		<s:hidden name="applyAndBindCardDTO.firstName" />
		<s:hidden name="applyAndBindCardDTO.recipient_name" />

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
									style="cursor: pointer;"><span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">&nbsp;</td>
							</tr>
						</table>
						<div id="TableBody">
							<!-- cardInfos -->
							<ec:table items="cardInfos" var="map" width="100%"
								form="confirmForm"
								action="${ctx}/cardInformationQueryAction!list"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" tableId="cardInfo"
								autoIncludeParameters="false">
								<ec:row>
									<ec:column property="null" alias="choose" title="选择" width="5%"
										sortable="false" viewsAllowed="html">
										<input type="radio" name="choose"
											value="${map.ID_NO},${map.ID_TYPE},${map.APPLY_DATE_SECONDS}" />
										<input type="hidden" name="ec_chooseIdType"
											value="${map.ID_TYPE}" />
									</ec:column>
									<ec:column property="ID_NO" title="证件号" width="6%" />
									<%-- <ec:column property="ID_TYPE" title="证件类型" width="10%" /> --%>
									<ec:column property="ID_TYPE" title="证件类型" width="10%">
										<dl:dictList dictValue="${map.ID_TYPE}" dictType="401"
											tagType="1" />
									</ec:column>
									<ec:column property="FIRST_NAME" title="持卡人" width="10%" />
									<ec:column property="RECIPIENT_NAME" title="收件人" width="10%" />
									<ec:column property="RECIPIENT_ADDR" title="收件人地址" width="10%" />
									<ec:column property="RECIPIENT_PHONE" title="收件人电话" width="5%" />
									<ec:column property="APPLY_STATUS" title="卡申请状态" width="10%"
										cell="dictInfo" alias="209">
									</ec:column>
									<ec:column property="APPLY_DATE_SECONDS" title="申请时间"
										width="5%" />
								</ec:row>
							</ec:table>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</s:form>
	<br />
	<div style="width: 100%" align=center>

		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="B5B8BF">
			<tr>
				<td width="98%" height="10" align="center" valign="middle"
					bgcolor="#FFFFFF"><s:form id="EditForm" name="EditForm"
						action="cardInformationAction!operate">
						<s:hidden name="ApplyAndBindCardDTO.idNo" id="ID_NO" />
						<s:hidden name="ApplyAndBindCardDTO.idType" id="ID_TYPE" />
						<s:hidden name="operation" id="operation" />
						<s:hidden name="ApplyAndBindCardDTO.applyDateSeconds"
							id="APPLY_DATE_SECONDS" />
					</s:form>

					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<div id="buttonCRUD"
									style="text-align: right; width: 100%; height: 30px;">
									
										<button class='btn' style="float: right; margin: 7px"
											onclick="submitForm('confirm');">审核</button>
								</div>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
	<br>
</body>
</html>