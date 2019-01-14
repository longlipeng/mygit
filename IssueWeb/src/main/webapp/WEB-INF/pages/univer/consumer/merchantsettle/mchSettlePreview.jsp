<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>商户结算单预览</title>
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

    function checkedCount() {
        var checkboxs = document.getElementsByName('entityIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }
    function queryMerchant() {
       	var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/inquiry.action';
        merchantForm.submit();
    }
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>商户结算单预览</span>
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
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="merchantForm" name="merchantForm"
								action="merchantManagement/inquiry" method="post">
								<table>
									<tr height="35">
										<td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.entityId"
												id='merchantEntityId'></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.entityId
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantName" />
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.merchantName
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr height="35">
										<td width="90" align=right>
											<span>外部系统代码：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.externalId" />
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.externalId
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="102" align=right>
											<span>商户状态：</span>
										</td>
										<td width="120">
											<s:select list="#{'1':'有效','0':'无效'}"
												name="merchantQueryDTO.merchantState" emptyOption="false"
												label="商户状态" headerKey="" headerValue="--请选择--" />
										</td>
										<td align="center">
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryMerchant()" value="查 询" />
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

							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="merchantForm"
								action="${ctx}/merchantManagement/inquiry.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row ondblclick="view('merchantManagement/view',{'merchantDTO.entityId':'${map.entityId}'},'');">
									<ec:column property="null" alias="entityIdList" title="选择"
										width="10%" sortable="false" headerCell="selectAll">
										<input type="checkbox" name="entityIdList"
											value="${map.entityId}" />
										<input type="hidden" name="stateList" value="${map.merchantState}" />
										<input type="hidden" name="entityIdAll"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="entityId" title="商户号" width="10%"
										escapeAutoFormat="true">
										<a
											href="merchantManagement/view?merchantDTO.entityId=${map.entityId}">${map.entityId}</a>
									</ec:column>
									<ec:column property="merchantName" title="商户名称" width="10%" />
									<ec:column property="englishName" title="商户英文名称" width="15%" />
									<ec:column property="externalId" title="外部系统代码" width="15%" />
									<ec:column property="merchantState" title="商户状态" width="10%" />
								</ec:row>
							</ec:table>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="deleteMerchant()">
													删除
												</div>
											
											
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="editMerchant()">
													编辑
												</div>
											
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="addMerchant()">
													添加
												</div>
												<button class='bt' style="float: right; margin: 5px"
													onclick="changePassword();">
													商户网站重置密码
												</button>
											
											<div style="clear: both"></div>
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