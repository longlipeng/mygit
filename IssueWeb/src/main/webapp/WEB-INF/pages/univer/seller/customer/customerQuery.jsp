<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>客户查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
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
			
			function submit() {
				var flag = true;
				var selectValue = "";
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						if (flag) {
							flag = false;
							selectValue = document.getElementsByName("choose").item(i).value;
						} else {
							selectValue = "";
							flag = true;
							errorDisplay("请选择一条记录！");
							return;
						}
					}
				}
				if (flag) {
					errorDisplay("请选择一条记录操作！");
					return;
				} else {
					window.returnValue=selectValue;
					window.close();
				}
				
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>客户查询</span>
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
							<s:form id="queryForm" name="queryForm"
								action="customer/chooseCustomer">
								<s:hidden name="personFlag"></s:hidden>
								<table>
									<tr>
										<td width="65" align=right>
											客户号：
										</td>
										<td>
											<s:textfield name="customerQueryDTO.entityId" size="16" />
											
										</td>
										<td width="75" align=right>
											客户名称：
										</td>
										<td>
											<s:textfield name="customerQueryDTO.customerName" size="14" />
											&nbsp;
										</td>
	                                    
									</tr>
									<tr>
										<td width="75" align=right>证件号码：</td>
	                                    <td><s:textfield name="customerQueryDTO.corpCredId" size="19"/>
	                                    </td>
										<%--
										<td width="65" align=right>
											客户类型：
										</td>
										<td>
											<s:select name="customerQueryDTO.customerType" list="#{'0':'企业客户','1':'个人客户'}"></s:select>
											&nbsp;
										</td>
										--%>
										<td></td>
										<td align=right>
											<button class='bt' onclick="queryForm.submit();">
												查 询
											</button>
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

						<div id="TableBody">
							<s:form id="EditForm" name="EditForm"
								action="customerAction!list">
								<s:hidden name="customerQueryDTO.entityId"></s:hidden>
								<s:hidden name="customerQueryDTO.customerName"></s:hidden>
								<s:hidden name="personFlag" ></s:hidden>
								<s:hidden name="customerQueryDTO.customerType"></s:hidden>
								<ec:table items="pageDataDTO.data" var="map" width="100%" form="EditForm"
									action="${ctx}/customer/chooseCustomer"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row >
										<ec:column property="null" alias="choose" title="选择"
											width="5%" sortable="false" viewsAllowed="html">
											<s:if test="#attr['map'].dataState == 1">
				                            	<input type="radio" name="choose" value="${map.entityId}"
														checked="true" />
				                            </s:if>
										</ec:column>
										<ec:column property="entityId" title="客户号${map.dataState}" width="10%" />
										<ec:column property="customerName" title="客户名称" width="15%" />
									</ec:row>
								</ec:table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px"
				onclick="window.close()">
				关 闭
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>