<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>退货信息查看</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp" %>
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
		
		function verify(){
			var n=0;
			var roleId;
			var flag;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					var s=checkbox[i].value.split(',');
					roleId=s[0];
					flag=s[1];
				}
			}
			if(n==0){
				errorDisplay('请选择要审核的对象');
			}
			if(n>1){
				errorDisplay('审核对象只能是一个');
			}
			if(n==1){
				if(EditForm['ec_eti']!=null){
					EditForm['ec_eti'].disabled=true;
				}
				document.EditForm.action='refund/refundDetail.action?refundId='+roleId+'&flag='+flag;
				document.EditForm.submit();
			}
		}
	
		function query(){
			var n=0;
			var roleId;
			var flag;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					var s=checkbox[i].value.split(',');
					roleId=s[0];
					flag=s[1];
				}
			}
			if(n==0){
				errorDisplay('请选择要查询的对象');
			}
			if(n>1){
				errorDisplay('查询对象只能是一个');
			}
			if(n==1){
				if(EditForm['ec_eti']!=null){
					EditForm['ec_eti'].disabled=true;
				}
				document.EditForm.action='refund/refundTransLogQuery.action?refundId='+roleId+'&flag='+flag;
				document.EditForm.submit();
			}
		}
	</script>
		<style type="text/css">
body,table,td,p,font,select {
	font-size: 9pt;
}

.bt {
	background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0
		0;
	border: 1px solid #7DE7FD;
	font-size: 9pt;
	height: 22px;
	cursor: pointer;
}

.bt:HOVER {
	background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll
		0 0;
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
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>退货申请查看</span>
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
							<form id="EditForm" name="EditForm"
								action="${ctx }/refund/refundList.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>商户代码：</span>
										</td>
										<td width="120">
										 <s:if test="merchants!=null">
											<s:select id="merchantCode" name="merchantCode"
													list="merchants" listKey="merchantCode" listValue="merchantName"
													headerKey="" headerValue=""></s:select>
										 </s:if>			
										</td>
										<td width="150" align=right>
											<span>中心流水号：</span>
										</td>
										<td width="120">
											<s:textfield name="refundQueryDTO.orderId" />
										</td>
										<td width="150" align=right>
											<span>审批状态：</span>
										</td>
										<td width="120">
											<s:select id="transState" name="refundQueryDTO.authRlt"
													list="transStates" listKey="stateCode" listValue="stateName"
													headerKey="" headerValue=""></s:select>
										</td>																										
										</tr>
										<tr>
										<td width="150" align=right>
											<span>退货起始时间：</span>
										</td>
										<td width="120">
											<input type="text" name="refundQueryDTO.startDate"  	onclick="dateClick(this)"
											 class="Wdate"  value="${refundQueryDTO.startDate }"/>
										</td>
										<td width="150" align=right>
											<span>退货结束时间：</span>
										</td>
										<td width="120">
											<input type="text" name="refundQueryDTO.endDate"  	onclick="dateClick(this)"
											 class="Wdate"  value="${refundQueryDTO.endDate }"/>
										</td>
										<td></td><td></td>
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="if(EditForm['ec_eti']!=null){EditForm['ec_eti'].disabled=true;}EditForm.submit();">
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
									form="EditForm" action="refund/refundList.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									 retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="SystemLog.xls" tooltip="导出Excel"/>	
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.SEQID},${map.FLAG}" />
										</ec:column>
										<ec:column property="ORDERID" title="中心流水号" escapeAutoFormat="true" width="15%">						
										</ec:column>
										<ec:column property="SETTLEDT" title="清算日期" width="15%" />
										<ec:column property="ORGITRANSAT" title="订单金额(元)" width="10%" cell="amt">
										</ec:column>
										<ec:column property="TRANSAT" title="退货金额(元)" escapeAutoFormat="true" width="10%" cell="amt" >
										</ec:column>
										<ec:column property="REQDT" title="退货申请日期" escapeAutoFormat="true" width="10%" >
										</ec:column>
										<ec:column property="REQTM" title="退货申请时间" escapeAutoFormat="true" width="10%" >
										</ec:column>
										<ec:column property="AUTHRLT" title="审批状态" escapeAutoFormat="true" width="10%" >
										</ec:column>
										<ec:column property="RESPCODE" title="操作状态" escapeAutoFormat="true" width="10%" >
										</ec:column>
									</ec:row>
								</ec:table>		
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
													<div id="deleteBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="verify()">
														审核
													</div>
													<div id="modifyBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="query()">
														查询
													</div>
										</td>
									</tr>
								</table>		
							</form>
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>