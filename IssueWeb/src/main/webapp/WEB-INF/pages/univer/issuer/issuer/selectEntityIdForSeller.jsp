<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择实体</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"/>

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
	function inquiry(){
    	document.queryForm.submit();
	}
    function chooseEntity() {
        var entityId;
        var entityIdList = document.getElementsByName('entityId');
        for (var i=0; i<entityIdList.length; i++) {
            if (entityIdList[i].checked) {
                entityId = entityIdList[i].value;
                break;
            }
        }
        if (entityId == null) {
        	errorDisplay("请选择实体！");
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
			<span>实体查询</span>
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
								action="record.action" method="post">
			                <table>
									
									<tr height="35">
										<td width="85" align=right>
											<span>实体ID：</span>
										</td>
										<td width="160">
											<s:textfield name="sellerQueryDTO.queryEntityId" id='entityId'></s:textfield>
											&nbsp;
										</td>
										<td width="90" align=right>
											<span>实体名称：</span>
										</td>
										<td width="160">
											<s:textfield name="sellerQueryDTO.queryName"/>
											&nbsp;
										</td>
									</tr>
									<tr>
									    <td width="102" align=right>
											<span>实体机构：</span>
										</td>
										<td width="120">
											<s:select list="#{'1':'发卡机构','2':'收单机构'}"
												name="sellerQueryDTO.entityFlag" emptyOption="false"
												label="商户状态" headerKey="" headerValue="--请选择--" />
										</td>
									</tr>
									<tr>
									   <td align="right" colspan="4">
                                          <input type="button" class="bt" onclick="inquiry();" value="查 询"/>
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
								action="seller/record" method="post">
								<s:hidden name="sellerQueryDTO.queryName"/>
								<s:hidden name="sellerQueryDTO.queryEntityId"/>
								<s:hidden name="sellerQueryDTO.entityFlag"/>
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="EditForm"
								action="${ctx}/seller/record"
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:row onclick="">
									<ec:column property="null" alias="choose" title="选择" width="10%" sortable="false">
										<input type="radio" name="entityId" value="${map.queryId}" />
									</ec:column>
									<ec:column property="queryId" title="实体ID" width="10%" />
									<ec:column property="queryName" title="实体名称" width="15%"/>
								</ec:row>
							</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<input type="button" class="bt" style="margin: 5px" onclick="chooseEntity();" value="确定"/>
												<input type="button" class="bt" style="margin: 5px" onclick="window.close()" value="返回"/>
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