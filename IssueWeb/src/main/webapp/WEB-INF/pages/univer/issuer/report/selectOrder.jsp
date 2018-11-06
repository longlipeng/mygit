
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
								action="issuerChoose" method="post">&nbsp; 
			 
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理</span>
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
						<s:hidden name="sellerQueryDTO.entityId"></s:hidden>
						<div id="TableBody">
								<ec:table items="pageDataDTO.data" var="map" width="100%" form="EditForm"
									action="${ctx}/ireport/orderChoose.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" 	view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="issuerList.xls" tooltip="导出Excel"/>
									<ec:row>
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="">
											<input type="radio" name="choose" value="${map.orderId},${map.orderType}" />
										</ec:column>
										<ec:column property="orderId" title="订单号" width="20%"/>
										<ec:column property="orderType" title="订单类型" width="20%" cell="dictInfo" alias="205" />
										<ec:column property="firstName" title="订单发起方" width="20%"/>
										<ec:column property="processName" title="订单处理方" width="20%"/>
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