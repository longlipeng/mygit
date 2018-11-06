<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>商户管理</title>
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
        merchantForm.action = '${ctx}/selectMerchant.action';
        merchantForm.submit();
    }
    function addMerchant() {
        var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/add.action';
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
        merchantForm.action = '${ctx}/merchantManagement/edit.action';
        merchantForm.submit();
    }

  
    
    function chooseMchnt() {
        var entityId;
        var entityIdList = document.getElementsByName('entityId');     
        for (var i=0; i<entityIdList.length; i++) {
            if (entityIdList[i].checked) {
                entityId = entityIdList[i].value;
                break;
            }
        }
        if (entityId == null) {
            alert("请选择商户！");
        }
        if (entityId != null) {
            window.returnValue=entityId;
			window.close();
        }  
    }
    
    
    
    
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>商户信息管理</span>
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
											<s:textfield name="merchantQueryDTO.merchantCode"></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.merchantCode
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
										<td align="center">
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryMerchant()" value="查 询" />
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
						<s:form id="merchantxForm" name="merchantxForm"
							action="selectMerchant" method="post">
							    <s:hidden name="merchantQueryDTO.entityId"/>
								<s:hidden name="merchantQueryDTO.merchantName"/>
								<s:hidden name="merchantQueryDTO.merchantState"/>
							<div id="TableBody">
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="merchantxForm"
									action="${ctx}/selectMerchant"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="null" alias="entityIdList" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="radio" name="entityId"
												value="${map.merchantName},${map.merchantCode},${map.entityId}" />
											<input type="hidden" name="stateList" value="${map.merchantState}" />
											<input type="hidden" name="entityIdAll"
												value="${map.entityId}" />
										</ec:column>
										<ec:column property="merchantCode" title="商户号" width="10%"
											escapeAutoFormat="true">
										</ec:column>											
										<ec:column property="merchantName" title="商户名称" width="10%" />
										<ec:column property="englishName" title="商户英文名称" width="15%" />
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
												<input type="button" class="bt" style="margin: 5px"
													onclick="chooseMchnt()" value="确定" />
												<input type="button" class="bt" style="margin: 5px"
													onclick="window.close()" value="返回" />
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</s:form>
					</td>
				</tr>
			</table>

		</div>

		<br>
	</body>
</html>