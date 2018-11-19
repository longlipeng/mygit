<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>收单网关管理</title>
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
        var checkboxs = document.getElementsByName('idList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }
   
    function addAcq() {
        document.acqPayForm.action = '${ctx}/acqpay/initAdd.action';
        document.acqPayForm.submit();
    }

    function editAcq() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }

        document.acqPayForm.action = '${ctx}/acqpay/reEdit.action';
        document.acqPayForm.submit();
    }
    
    function deleteAcq() {
        var count = checkedCount();
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
		confirm("确定删除吗?",operation);
    }
    function operation(){
    	acqPayForm.action = '${ctx}/acqpay/delete.action';
    	acqPayForm.submit();
	}
    
    
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>收单网关管理</span>
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
							<s:form id="acqPayForm" name="acqPayForm"
								action="acqpay/inquery" method="post">
								<table>
									<tr height="35">
										<td width="85" align=right>
											<span>收单机构号：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.entityId"></s:textfield>
											<s:fielderror>
												<s:param>
                                            consumerQueryDTO.entityId
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="160" align=right>
											<span>收单机构名称：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.consumerName" />
											<s:fielderror>
												<s:param>
                                            consumerQueryDTO.consumerName
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right">
											<input type="submit" class="bt" style="margin: 5px" value="查 询" />
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
								form="acqpayForm"
								action="acqpay/inquery.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row>
									<ec:column property="null" alias="idList" title="选择"
										width="10%" sortable="false" headerCell="selectAll">
										<input type="checkbox" name="idList"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="entityId" title="收单机构号" width="10%"
										escapeAutoFormat="true">
										<a
											href="${ctx}/acqpay/view.action?acqPayDTO.entityId=${map.entityId}">${map.entityId}</a>
									</ec:column>
									<ec:column property="consumerName" title="收单机构名称" width="10%" />
									<ec:column property="modifyTime" title="操作日期" width="10%" />
								</ec:row>
							</ec:table>


							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
												<display:security urlId="30904">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="deleteAcq()">
													删除
												</div>
												</display:security>
										<display:security urlId="30903">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="editAcq()">
													编辑
												</div>
												</display:security>
											<display:security urlId="30902">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="addAcq()">
													添加
												</div>
												</display:security>												
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