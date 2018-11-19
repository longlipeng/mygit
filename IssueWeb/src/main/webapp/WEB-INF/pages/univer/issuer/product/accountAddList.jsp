<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>账户管理</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
		window.onload=function(){
			
			var flag=document.getElementById('flag').value;
			if(flag=='true'){
					window.returnValue="1";
					window.close();
				}
		}		
			
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
		function clickRow(serviceFee){
		   document.getElementById("prodAcctypeDTO.serviceFee").value=serviceFee;
		}
		function sub(){
			var doublePatrn = /^[1-9]{1}[0-9]+$/;
			var TxnAmt=document.getElementById('TxnAmt').value;
			var DayTxnAmt=document.getElementById('DayTxnAmt').value;
			var webMaxDayTxnAmt = document.getElementById('webMaxDayTxnAmt').value;
			var maxBalance='${productDTO.maxBalance}';
			document.getElementById('webMaxTxnAmt').value=maxBalance;
			document.getElementById('webMaxDayTxnAmt').value=maxBalance;
			if(!doublePatrn.exec(DayTxnAmt)){
    			errorDisplay("当日交易额上限必须为正整数！(非零开头)");
				return ;
    		}
    		if(parseFloat(DayTxnAmt)>parseFloat(maxBalance)){
    				errorDisplay("当日交易额上限不能大于产品的最大余额");
    				return ;
    		}
    		if(!doublePatrn.exec(TxnAmt)){
    			errorDisplay("单笔交易额上限必须为正整数！(非零开头)");
				return ;
    		}
    		if(parseFloat(TxnAmt) > parseFloat(maxBalance)){
    		    errorDisplay("单笔交易额上限不能大于产品的最大余额");
    			return ;
    		}
    		if(parseFloat(TxnAmt) > parseFloat(DayTxnAmt)){
    		    errorDisplay("单笔交易额上限不能大于当日交易额上限");
    			return ;
    		}
    		if(parseFloat(webMaxDayTxnAmt)>parseFloat(maxBalance)){
    				errorDisplay("网上当日交易额上限不能大于产品的最大余额");
    				return ;
    		}
			
			var acctid=document.getElementsByName('serviceAccountId');
			var n=0;
			for(var i=0;i<acctid.length;i++){
				if(acctid[i].checked){
					n++;
				}
			}		
			
			if(n==0){
				errorDisplay('请选择一个服务');
				return;
			}
			
			document.EditForm.action='${ctx}/accountAdd.action';
			document.EditForm.submit();
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
	<base target="_self">
	</head>
	<body >
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>账户管理</span>
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
							<s:form id="queryForm" name="queryForm"
								action="serviceQuery.action" method="post">
								<s:hidden name="serviceQueryDTO.productId"></s:hidden>
							    <s:hidden name="serviceQueryDTO.serviceId"></s:hidden>
							 	<s:hidden name="productDTO.productId"></s:hidden>
                             	<s:hidden name="productDTO.maxBalance"></s:hidden>
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="100" align=right>
											<span>账户号：</span>
										</td>
										<td width="100">
											<s:textfield name="serviceQueryDTO.serviceId"></s:textfield>
											&nbsp;
										</td>
										<td width="100" align=right>
											<span>账户名称：</span>
										</td>
										<td width="100">
											<s:textfield name="serviceQueryDTO.serviceName" />
											&nbsp;
										</td>

										<td width="150" align=right>
											<span>发行机构：</span>
										</td>
										<td width="120">
										<s:select id="" 
												list="entityList"
												name="serviceQueryDTO.entityIdStr" 
												listKey="entityId"
												listValue="entityName"
											    headerKey="" headerValue="--请选择--" ></s:select>
										</td>
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="queryForm.submit();">
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
								action="serviceQuery.action" method="post">
								<s:hidden name="serviceQueryDTO.productId"></s:hidden>
								<s:hidden name="serviceQueryDTO.serviceId"/>
								<s:head name="serviceQueryDTO.serviceName"/>
								<s:hidden name="productDTO.productId"></s:hidden>
                             	<s:hidden name="productDTO.maxBalance"></s:hidden>
								<s:hidden name="flag" id="flag"></s:hidden>
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/productAccountInquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" >
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false">
											<input type="radio" name="serviceAccountId" value="${map.serviceId}" 
												onclick="clickRow('${map.serviceFee}');"/>
						
										</ec:column>
										<ec:column property="serviceId" title="账户号" width="30%">
										</ec:column>

										<ec:column property="serviceName" title="账户名称" width="40%" />
										<ec:column property="entityName" title="发行机构" width="30%" />
									</ec:row>
								</ec:table>

								<div id="service"
									style="border: 1px solid #B9B9B9; margin-top: 5px;">
									<div id="serviceTitle"
										style="font-weight: bold; height: 19px; background-color: #DFE8F6">
										
										<span>明细信息</span>
									</div>
									<div id="serviceTable"
										style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; background-color: #FBFEFF;">
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td>
													<table style="text-align: left; width: 100%">

														<tr>
															<td style="width: 120px; text-align: right;">
																<span class="no-empty">*</span>单笔交易额上限：
															</td>
															<td>
																<s:textfield name="prodAcctypeDTO.maxTxnAmt" id="TxnAmt"></s:textfield>
																元
															</td>
															<td>
																<s:fielderror>
																	<s:param>
																		prodAcctypeDTO.maxTxnAmt
																	</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
												<td>
													<table style="text-align: left; width: 100%">

														<tr>
															<td style="width: 120px; text-align: right;">
																<span class="no-empty">*</span>当日交易额上限：
															</td>
															<td>
																<s:textfield name="prodAcctypeDTO.maxDayTxnAmt" id="DayTxnAmt"></s:textfield>
																元					
															</td>
															<td>
																<s:fielderror>
																	<s:param>
																		prodAcctypeDTO.maxDayTxnAmt
																	</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<s:hidden name="prodAcctypeDTO.webMaxTxnAmt" id="webMaxTxnAmt"></s:hidden>
											<s:hidden name="prodAcctypeDTO.webMaxDayTxnAmt" id="webMaxDayTxnAmt"></s:hidden>
											<tr>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 120px; text-align: right;">
																<span class="no-empty">*</span>默认服务费（费率）：
															</td>
															<td>
																<s:textfield name="prodAcctypeDTO.serviceFee"></s:textfield>
																%
																<s:fielderror>
																	<s:param>
																		prodAcctypeDTO.serviceFee
																	</s:param>
																</s:fielderror>	
															</td>																																												
														</tr>

													</table>
												</td>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
                                                           <s:if test="prodAcctypeDTO.defaultFlag == 1">
                                                           	<td style="width: 120px; text-align: right;">
																 设为默认服务：
															</td>
															<td>
                                                             <s:hidden id="defaultFlag" name="prodAcctypeDTO.defaultFlag" value="%{prodAcctypeDTO.defaultFlag == null ? 0 : 1}"></s:hidden>
                                                             <input type="checkbox" <s:property value="prodAcctypeDTO.defaultFlag == 1 ? 'checked=checked disabled' : ''"/> name="rodAcctypeDTO.defaultFlag" id="defaultFlag"  onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
															</td>
															</s:if>
															<%--													   <s:else>无效</s:else>--%>
															<td>
																<s:fielderror>
																	<s:param>
																		prodAcctypeDTO.defaultFlag
																	</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
								</div>

								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">

												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="window.close();">
													关闭
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="sub()">
														确定
													</div>
													<div style="clear: both"></div>
												</div>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
		
		
	</body>
</html>