
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>发行机构管理</title>
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
		function selectentity(){
			var id='';
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择一个发行机构！');
				return;
			}
			window.returnValue=id;
			window.close();  
		}
		
		
	</script>
<style type="text/css">
	body, table, td, p, font, select {
		font-size: 9pt;
	}
	
	.bt {
		background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0 0;
		border: 1px solid #7DE7FD;
		font-size: 9pt;
		height: 22px;
		cursor: pointer;
	}
	
	.bt:HOVER {
		background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll 0 0;
		border: 1px solid #C3A336;
	}
	
	.btn {
		cursor: pointer;
		border: #FFFFFF 1px solid;
	}
	
	.btn:HOVER {
		background-color: F0FFE1;
		border: #93B3CA 1px solid;
	}
</style>
	</head>
	<body>
	<s:form id="EditForm" name="EditForm"
								action="issuerChoose" method="post">
			
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>发行机构管理</span>
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
							
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>发行机构标识：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.entityId"></s:textfield>
											<s:fielderror>
												<s:param>
													issuerQueryDTO.entityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>发行机构名称：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.issuerName" />
											<s:fielderror>
												<s:param>
													issuerQueryDTO.issuerName
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr height="35">
									  <td width="150" align=right>
											<span>发行机构英文名称：</span>
										</td>
										<td width="160">
											<s:textfield name="issuerQueryDTO.issuerEnglishName"></s:textfield>
											<s:fielderror>
												<s:param>
													issuerQueryDTO.issuerEnglishName
												</s:param>
											</s:fielderror>
										</td>
										<td width="150" align=right>
											<span>发行机构状态：</span>
										</td>
										<td width="160">
									        <s:select list="#{'':'-请选择-',1:'有效',0:'无效'}" name="issuerQueryDTO.dataState"/>
										</td>	
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="EditForm['ec_eti'].disabled=true;EditForm.submit();">
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
								<ec:table items="pageDataDTO.data" var="map" width="100%" form="EditForm"
									action="${ctx}/ireport/queryIssuerChoose.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="issuerList.xls" tooltip="导出Excel"/>
									<ec:row>
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="">
											<input type="radio" name="choose" value="${map.entityId},${map.issuerName}" />
										</ec:column>
										<ec:column property="entityId" title="发行机构标识" width="20%"/>
										<ec:column property="issuerName" title="发行机构名称" width="20%"/>
										<ec:column property="issuerCode" title="发行机构编号" width="20%"/>
										<ec:column property="issuerEnglishName" title="发行机构英文名称" width="20%"/>
									</ec:row>
								</ec:table>


				<table border="0" cellpadding="0" cellspacing="0" width="100%">
                     <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="selectentity();" value="提 交"/>
                                        </td>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="window.close();" value="关 闭"/>
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
			</s:form>
		
	</body>
</html>