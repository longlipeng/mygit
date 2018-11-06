<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>交易路由管理</title>
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
		
		
		function del(){
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要删除的对象');
			}
			//if(n>1){
				//errorDisplay("请选择一条记录！");
			//}
			else{
				confirm("确定删除吗?",operation)
			}
		}
		
		function add(){
			if (EditForm['ec_eti'] != null) {
            EditForm['ec_eti'].disabled=true;
        	}
			EditForm.action='reCardRouteAdd.action';
			EditForm.submit();
		}
		
		function operation(){
			if (EditForm['ec_eti'] != null) {
           	 EditForm['ec_eti'].disabled=true;
       		 }
			EditForm.action='delCardRoute.action';
			EditForm.submit();
		}
		
		
		function query(){
		if (EditForm['ec_eti'] != null) {
            EditForm['ec_eti'].disabled=true;
        }
		
		EditForm.submit();
		}
		
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>交易路由管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
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
							<s:form id="EditForm" name="EditForm"
								action="cardRouteList" method="post">
								<s:token></s:token>
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>卡BIN：</span>
										</td>
										<td width="160">
											<s:textfield name="cardRouteDTO.binVal"></s:textfield>
											<s:fielderror>
												<s:param>
													cardRouteDTO.binVal
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>机构号：</span>
										</td>
										<td width="160">
											<s:textfield name="cardRouteDTO.issInsIdCd" />
											&nbsp;
										</td>
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="query();">
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
								<ec:table items="pageDataDTO.data" var="map"
									width="100%" form="EditForm"
									action="cardRouteList.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="cardRouteList.xls" tooltip="导出Excel" />
									<ec:row>
										<ec:column property="null" alias="choose" title="选择"
											width="10%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.binVal},${map.length}" />
										</ec:column>
										<ec:column property="binVal"  title="卡BIN" width="10%">
										</ec:column>
										<ec:column property="length"  title="卡号长度" width="10%">
										</ec:column>
										<ec:column property="issInsIdCd"  title="机构号" width="10%" escapeAutoFormat="true">
										</ec:column>
										<ec:column property="insNm"  title="机构名称" width="15%" />
										<ec:column property="userName"  title="创建人" width="15%">
										</ec:column>
										<ec:column property="recCrtTs"  title="创建时间" width="10%"></ec:column>
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" >
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;">
											<display:security urlId="315002">
												<div id="delBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="del()">
														删除
												</div>
											</display:security>
											<display:security urlId="315001">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="add()">
														添加
													</div>
											</display:security>													
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