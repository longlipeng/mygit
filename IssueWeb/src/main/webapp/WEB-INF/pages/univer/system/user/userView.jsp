<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>用户管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
.x-tree-node-icon {
	display: none;
}
</style>
	<script type="text/javascript" charset="UTF-8">
	var isDisplayTableBody = false;
	function displayServiceTable() {
		if (isDisplayTableBody) {
			display('serviceTable');
			isDisplayTableBody = false;
		} else {
			undisplay('serviceTable');
			isDisplayTableBody = true;
		}
	}
 	</script>

	</head>
	<body>
		<div class="TitleHref">
			<span>用户信息编辑</span>
		</div>


		<div id="ContainBox">
			<s:form id="newForm" name="newForm" action="editUser.action"
				method="post">
				<input type="hidden" id="id" name="id"/>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF" align="center">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();"
										style="cursor: pointer;">
										<span class="TableTop">用户信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">

<!-- i'm wangdulei~~ -->

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>用户编号：
													</td>
													<td>
														<s:label name="userDTO.userId" ></s:label>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>用户名称：
													</td>
													<td>
														<s:label name="userDTO.userName"></s:label>
														<s:fielderror>
															<s:param>
																userDTO.userName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>销售标识：
													</td>
													<td>
														<s:select list="#{1:'是',0:'否'}" name="userDTO.isSaleFlage" disabled="true"></s:select>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														用户状态：
													</td>
													<td>
														<s:select list="#{1:'有效',0:'无效'}" name="userDTO.userState" disabled="true"></s:select>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>部门名称：
													</td>
													<td>
														<edl:entityDictList dictType="901" tagType="1" defaultOption="true" displayName="userDTO.departmentId" dictValue="${userDTO.departmentId}"></edl:entityDictList>
													</td>
												</tr>
											</table>
										</td>
										
										<td>&nbsp;
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='user/listUser.action'">
				返 回
			</button>
			
			<div style="clear: both"></div>
		</div>
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
							<ec:table items="userDTO.roleDTOs" var="map" width="100%" form="EditForm"
									action="${ctx}/issuer!inquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false" sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="rolebox" value="${map.roleId}" />
										</ec:column>
										<ec:column property="roleId" title="角色号" width="10%" />
										<ec:column property="roleName" title="角色名称" width="20%" />
									</ec:row>
								</ec:table>
							<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
			</s:form>
		</div>
	</body>
</html>