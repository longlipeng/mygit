<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择商户</title>
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
		/*var s='';
		var entityId=document.getElementById('entityId').value;
    	if(entityId!=''){
    		for(var i=0;i<15-entityId.length;i++){
    			s=s+'0';
    		}
    			document.getElementById('entityId').value=s+entityId;
    	}*/
    	document.queryForm.submit();
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
            ajaxRemote('${ctx}/merchantManagement/selectAjax.action',
                    'merchantDTO.entityId='+entityId,
                    function(merchantDTO) {
                        window.returnValue = merchantDTO;
                        window.close();
                    },
                    'json');
        }  
    }
    function selectentity(){
			var id='';
			var n=0;
			var checkbox=document.getElementsByName("merchantCode");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			} 
			if(n==0){
				errorDisplay('请选择一个商户！');
				return;
			}
			window.returnValue=id;
			window.close();  
		}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		
		<div class="TitleHref">
			<span>商户查询</span>
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
								action="ireport/merchantChoose.action" method="post">
			                <table>
									
									<tr height="35">
										<td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantCode" id='entityId'></s:textfield>
											&nbsp;
										</td>
										<td width="90" align=right>
											<span>商户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantName"/>
											&nbsp;
										</td>
									   <td align="right" colspan="4">
                                          <input type="button" class="bt" onclick="inquiry();" value="查 询"/>
									   </td>										
									</tr>
			                </table>
			                <s:hidden name="merchantQueryDTO.merchantState" value="1"/>
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
						<s:form id="merchantForm" name="EditForm"
								action="ireport/merchantChoose.action" method="post">
								<s:hidden name="merchantQueryDTO.merchantName"/>
								<s:hidden name="merchantQueryDTO.entityId"/>
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="EditForm"
								action="ireport/merchantChoose.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:row onclick="">
									<ec:column property="null" alias="choose" title="选择" width="10%" sortable="false">
										<input type="radio" name="merchantCode" value="${map.merchantCode},${map.merchantName},${map.entityId}" />
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="10%" />
									<ec:column property="merchantName" title="商户名称" width="10%" />
									<ec:column property="merchantState" title="商户状态" width="10%"/>
								</ec:row>
							</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<input type="button" class="bt" style="margin: 5px" onclick="selectentity()" value="确定"/>
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