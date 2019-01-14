<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>商户信息审核管理</title>
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
        merchantForm.action = '${ctx}/merchantVerifier/inquiry.action';
        merchantForm.submit();
    }
  

    function editMerchant() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantVerifier/edit.action';
        merchantForm.submit();
    }

	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>商户信息审核管理</span>
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
											<s:textfield name="merchantQueryDTO.merchantCode"
												id='merchantCode'></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.entityId
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户注册名称：</span>
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
										<td width="85" align=right>
											<span>商户实际店名：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantRealityName"></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.merchantRealityName
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户属性：</span>
										</td>
										<td width="160">
											<edl:entityDictList displayName="merchantQueryDTO.merchantAttribute" dictValue="${merchantQueryDTO.merchantAttribute}" dictType="184" tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param> merchantQueryDTO.merchantAttribute </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="90" align=right>
											<span>注销状态：</span>
										</td>
										<td width="160">
											<s:select list="#{'1':'未注销','0':'已注销'}"
												name="merchantQueryDTO.dataState" emptyOption="false"
												label="注销状态" headerKey="" headerValue="--请选择--" />
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.dataState
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="102" align=right>
											<span>商户状态：</span>
										</td>
										<td width="120">
										<s:select list="#{'1':'已审核','3':'审核未通过','4':'审核中'}" 
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
								action="${ctx}/merchantVerifier/inquiry.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row ondblclick="view('merchantVerifier/view',{'merchantDTO.entityId':'${map.entityId}'},'');">
									<ec:column property="null" alias="entityIdList" title="选择"
										width="10%"  headerCell="selectAll">
										<input type="checkbox" name="entityIdList"
											value="${map.entityId}" />
										<input type="hidden" name="stateList" value="${map.merchantState}" />
										<input type="hidden" name="entityIdAll"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="10%" escapeAutoFormat="true" >
										<a href="merchantVerifier/view?verifierFlag=0&merchantDTO.entityId=${map.entityId}">${map.merchantCode}</a>
									</ec:column>
									<ec:column property="merchantName" title="商户注册名称" width="12%" />
									<ec:column property="merchantRealityName" title="商户实际店名" width="12%" />
									<ec:column property="englishName" title="商户英文名称" width="12%" />
									<ec:column property="merchantState" title="商户状态" width="8%" />
									<ec:column property="merchantAttribute" title="商户属性" width="10%" />
									<ec:column property="dataState" title="注销状态" width="8%" />
								</ec:row>
							</ec:table>

							 <table border="0" cellpadding="0" cellspacing="0" align="right">
									<tr>
										<td>
											<display:security urlId="5000291">
												<input id="modifyBtn" class="btn" type="button"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="editMerchant()"
													value="复核"  />
											</display:security>
										</td>
										<td>
											<display:security urlId="5000292">
											<input type="button" class="btn" type="button"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="submitForm('merchantForm', '${ctx}/merchantVerifier/view.action?verifierFlag=1', 'edit', 'entityIdList')"
												value="审核"  />
											</display:security>
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