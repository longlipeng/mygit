<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>结算单查询</title>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
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
	var n = '';
	var v = '';
	function presentation() {
		var count = checkedCount();
		if (count < 1) {
			alert('请至少选择一条记录！');
			return;
		} else if (count > 1) {
			alert('请选择一条记录！');
			return;
		}
		var mchntSettleForm = Ext.get("mchntSettleForm").dom;
		if (mchntSettleForm['ec_eti'] != null) {
			mchntSettleForm['ec_eti'].disabled = true;
		}
		document.getElementById('__report').value = "/report/rpt_red_presentation_multi_service.rptdesign";
		document.getElementById('__format').value = "pdf";
		document.getElementById('settleId').value = n;
		queryForm.submit();
	}
	function presentationGroup() {
		var count = checkedCount();
		if (count < 1) {
			alert('请至少选择一条记录！');
			return;
		} else if (count > 1) {
			alert('请选择一条记录！');
			return;
		}
		var mchntSettleForm = Ext.get("mchntSettleForm").dom;
		if (mchntSettleForm['ec_eti'] != null) {
			mchntSettleForm['ec_eti'].disabled = true;
		}
		document.getElementById('__report').value = "/report/rpt_red_presentation_group_multi_service.rptdesign";
		document.getElementById('__format').value = "pdf";
		if (v == '') {
			alert('此商户没有商户集团');
			return;
		} else {
			document.getElementById('groupId').value = v;
			queryForm.submit();
		}
	}
	function presentationDetail() {
		var count = checkedCount();
		if (count < 1) {
			alert('请至少选择一条记录！');
			return;
		} else if (count > 1) {
			alert('请选择一条记录！');
			return;
		}
		var mchntSettleForm = Ext.get("settleForm").dom;
		if (mchntSettleForm['ec_eti'] != null) {
			mchntSettleForm['ec_eti'].disabled = true;
		}
		document.getElementById('__report').value = "/report/rpt_red_presentation_detail_multi_service.rptdesign";
		document.getElementById('__format').value = "pdf";
		document.getElementById('settleId').value = n;
		mchntSettleForm.action = '${ctx}/merchantSettle/querySettleDetail.action';
		mchntSettleForm.submit();
	}
	function querySettle() {
		var settleForm = Ext.get("settleForm").dom;
		if (settleForm['ec_eti'] != null) {
			settleForm['ec_eti'].disabled = true;
		}
		settleForm.action = '${ctx}/merchantSettle/query.action?state=0';
		settleForm.submit();
	}

	function checkedCount() {
		var listIds = document.getElementsByName('settleIdList');
		var checkboxs = document.getElementsByName('settleIdList');
		var count = 0;
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				n = checkboxs[i].value;
				v = listIds[i].value;
				count++;
			}
		}
		return count;
	}
</script>
	</head>
	<body>
		<form id="queryForm" name="queryForm" action="${ctx}/report/output"
			method="post" target="_blank">
			<input type="hidden" name="__report" id="__report" />
			<input type="hidden" name="__format" id="__format" />
			<input type="hidden" name="groupId" id="groupId" />
			<input type="hidden" name="printname"
				value="<s:property value="#attr.SPRING_SECURITY_LAST_USERNAME"/>" />
		</form>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算单查询</span>
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
								<td class="TableTitleFront" onclick="displayQueryBody();"  style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>

							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="settleForm" name="settleForm"
								action="merchantSettle/query.action?state=0" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="85" align=right>
											<span>结算单号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleId" id="settleId"></s:textfield>
											<s:fielderror>
												<s:param>
													settleQueryDTO.settleId
												</s:param>
											</s:fielderror>
										</td>

									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject2"></s:textfield>
											<s:fielderror>
												<s:param>
													settleQueryDTO.settltObject2
												</s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject2Name" />
											<s:fielderror>
												<s:param>
													settleQueryDTO.settltObject2Name
												</s:param>
											</s:fielderror>
										</td>
									</tr>

									<tr>

										<td width="85" align=right>
											开始时间：
										</td>
										<td width="160">
											<input type="text" name="settleQueryDTO.settleStartDate"
												onclick="dateClick(this);" class="Wdate"
												value="${settleQueryDTO.settleStartDate}" />

										</td>



										<td width="90" align=right>
											结束时间：
										</td>
										<td width="160">

											<input type="text" name="settleQueryDTO.settleEndDate"
												onclick="dateClick(this);" class="Wdate"
												value="${settleQueryDTO.settleEndDate}" />
										</td>
									</tr>

									<tr>
										<td align="right" colspan="4">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input type="button" class="bt" style="margin: 5px"
															onclick="querySettle();" value="查 询" />
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
		<br>
		<br>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTableBody()" style="cursor: pointer;">
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="TableBody">


							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="settleForm"
								action="${ctx}/merchantSettle/query.action?state=1"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="merchantSettleRow">
								<ec:exportXls fileName="mchntSettleList.xls" tooltip="导出Excel" />
								<ec:row onclick="">
									<ec:column property="null" alias="settleIdList" title="选择"
										width="5%"  headerCell="selectAll" viewsAllowed="html">
										<input type="checkbox" name="settleIdList"
											value="${map.settleId}" />
									</ec:column>
									<ec:column property="settleId" title="结算单号" width="7%"  escapeAutoFormat="true" />
										<!-- <a
											href="${ctx}/merchantSettle/querySettleDetail.action?settleQueryDTO.settleId=${map.settleId}">${map.settleId}</a>-->
									<ec:column property="merchantCode" title="商户号" width="8%"
										escapeAutoFormat="true" />
									<ec:column property="settleObject2Name" title="商户名称"
										width="12%" />
									<ec:column property="settleStartDate" title="结算起始日期"
										width="9%" />
									<ec:column property="settleEndDate" title="结算截止日期" width="9%" />
									<ec:column property="consumeAmt" title="消费总金额" width="9%" />
									<ec:column property="refundFee" title="手续费调整" width="9%" />
									<ec:column property="txnFee" title="手续费" width="9%" />
									<ec:column property="settleAmt" title="结算金额" width="9%" />
								</ec:row>
							</ec:table>
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<!-- <div id="modifyBtn" class="btn"
												style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="presentation();">
												结算单报表
											</div> -->
											<display:security urlId="501011">
											<div id="modifyBtn" class="btn"
												style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
												onclick="presentationDetail();">
												结算单明细
											</div>
											</display:security>

										</div>
									</td>
								</tr>
							</table>
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: center; width: 100%; height: 20px;"
											valign="middle">
											<span>消费总金额*费率=手续费    &nbsp;  &nbsp;   消费总金额-手续费调整-手续费=结算金额</span>

										</div>
									</td>
								</tr>
							</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>