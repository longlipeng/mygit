<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>门店管理</title>
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


    function queryShop() {
    	
        var shopForm = Ext.get("shopForm").dom;
        shopForm.submit();
    }
    </script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>门店信息管理</span>
		</div>
		<s:form id="shopForm" name="shopForm"
			action="shopManagement/inquerySms" method="post">
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
								<table>

									<tr height="35">
										<td width="85" align=right>
											<span>门店号：</span>
										</td>
										<td width="160">
											<s:textfield name="shopQueryDTO.shopId"></s:textfield>
											<s:fielderror>
												<s:param>
                                                shopQueryDTO.shopId
                                              </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>门店名称：</span>
										</td>
										<td width="160">
											<s:textfield name="shopQueryDTO.shopName" />
											<s:fielderror>
												<s:param>
                                                shopQueryDTO.shopName
                                              </s:param>
											</s:fielderror>
										</td>
									</tr>

									<tr height="35">
										<td width="85" align=right>
											<span>手机号码：</span>
										</td>
										<td width="160">
											<s:textfield name="shopQueryDTO.moblie" id="mobileId"></s:textfield>
											
											<s:fielderror>
												<s:param>
                                                shopQueryDTO.moblie
                                              </s:param>
											</s:fielderror>
										</td>
										<td width="160">
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryShop()" value="查 询" />
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
									form="shopForm" action="${ctx}/shopManagement/inquerySms"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										
										<ec:column property="shopId" title="门店号" width="10%">
											
										</ec:column>
										<ec:column property="shopName" title="门店名称" width="10%" />
										<ec:column property="mobile" title="手机号" width="15%" cell="city" />
										<ec:column property="sms" title="短信内容" width="30%"/>
										<ec:column property="time" title="时间" width="15%" format="date" />
										
									</ec:row>
								</ec:table>


							</div>
						</td>
					</tr>
				</table>
			</div>
			<br>
		</s:form>
	</body>
</html>