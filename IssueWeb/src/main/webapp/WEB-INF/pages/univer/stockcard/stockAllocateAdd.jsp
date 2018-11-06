<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加库存调拨信息</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			
	
	
	function sub(){
		var allocateDate=document.getElementById('allocateDate').value;
		var createDate=document.getElementById('createDate').value;
		var aIn=document.getElementById('aIn').value;
		var aOut=document.getElementById('aOut').value;
		if(allocateDate==null || allocateDate==""){
			alert("调拨日期不能为空!");
			return;
		}
		if(createDate==null || createDate==""){
			alert("创建日期不能为空!");
			return;
		}
		if(aIn==aOut){
			alert("调出、调入机构不能为同一机构！");
			return;
		}
		newForm.action='stockAllocateAction!toUpdateFlag.action';
		newForm.submit();
	}
	
	function load(){
	var flag=${updateFlag};
		if(flag==true){
			document.getElementById('sm').style.display="none";
			document.getElementById('allocateList').style.display="";
		}else{
			document.getElementById("sm").style.display="";
			document.getElementById("allocateList").style.display="none";
		}
	}
</script>
	</head>
	<body onload="load();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加库存调拨信息</span>
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
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">调拨信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>调拨号：
													</td>
													<td>
														<s:textfield name="stockAllocateDTO.allocateId" id="id" disabled="true"></s:textfield>
														<s:fielderror>
															<s:param>
																stockAllocateDTO.allocateId
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
														<span class="no-empty">*</span>调拨日期：
													</td>
													<td width="35%" align="left" nowrap="nowrap">
														<s:textfield name="stockAllocateDTO.allocateDate"
														 size="20" onfocus="dateClick(this)"
														cssClass="Wdate" id="allocateDate">
														</s:textfield>
														<s:fielderror>
															<s:param>
																stockAllocateDTO.allocateDate
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>调出机构：
													</td>
													<td>
													 <s:select id="aOut" 
												                   list="issuerList"
												                   name="stockAllocateDTO.allocateOut" 
												            listKey="entityId"
												            listValue="entityName"
											                ></s:select>
														<s:fielderror>
															<s:param>
																stockAllocateDTO.allocateOut
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
														<span class="no-empty">*</span>调入机构：
													</td>
													<td>
														 <s:select id="aIn" 
												                   list="issuerList"
												                   name="stockAllocateDTO.allocateIn" 
												            listKey="entityId"
												            listValue="entityName"
											                ></s:select>
														<s:fielderror>
															<s:param>
																stockAllocateDTO.allocateIn
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>调拨人员：
													</td>
													<td>
														<s:select id="userId" 
												                   list="saleUserList"
												                   name="stockAllocateDTO.allocateUser" 
												            listKey="userId"
												            listValue="userName"
											                ></s:select>
														<s:fielderror>
															<s:param>
																stockAllocateDTO.allocateUser
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
														<span class="no-empty">*</span>创建人：
													</td>
													<td>
														<s:select id="userId" 
												                   list="saleUserList"
												                   name="stockAllocateDTO.createUser" 
												            listKey="userId"
												            listValue="userName"
											                ></s:select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
									
									<td rowspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>创建时间：
													</td>
													<td>
														<td width="35%" align="left" nowrap="nowrap">
														<s:textfield name="stockAllocateDTO.createDate"
														id="createDate" size="20" onfocus="dateClick(this)"
														cssClass="Wdate">
														</s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>								
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div style="text-align: right; width: 100%" id="sm">
			<s:form action="stockAllocateAction!list.action" name="backForm"
				id="backForm">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="back();">
				返 回
			</button>
			</s:form>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		<div style="width: 100%" align=center id="allocateList">
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
										<span class="TableTop">明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="sellOrderInputDTO.orderList.data" var="map" width="100%" form="newForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderList">
								<ec:row ondblclick="">
									<ec:column property="null" alias="orderListStr" title="选择"
										width="10%" sortable="false" headerCell="selectAll"
										viewsAllowed="html">
									<input type="checkbox" name=orderListIdStr
										value="${map.productId}" />
								</ec:column>
								<ec:column property="productName" title="产品名称" width="15%" />
								<ec:column property="cardLayoutName" title="卡面" width="10%" />
								<ec:column property="faceValueType" title="面额类型"  width="10%" />
								<ec:column property="faceValue" title="面额值" width="10%" />
								<ec:column property="cardAmount" title="调出数量" width="7%" />
								</ec:row>
								</ec:table>

								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
<!--												<button class='bt' style="float: right; margin: 5px"													-->
<!--														onclick="delOrderList();">-->
<!--													删除-->
<!--												</button>			-->
												<button class='bt' style="float: right; margin: 5px"
														onclick="addOrderList();">
													添加
												</button>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
								<!-- div id=TableBody -->
						</td>
					</tr>
				</table>
			</div>
		<script type="text/javascript">
		 function addOrderList(){
			 var returnValue =window.showModalDialog('${ctx}/stockAllocateAction!toAddOrderList.action?stockAllocateDTO.allocateId=${stockAllocateDTO.allocateId}&stockAllocateDTO.allocateIn=${stockAllocateDTO.allocateIn}&stockAllocateDTO.allocateOut=${stockAllocateDTO.allocateOut}&stockAllocateDTO.allocateUser=${stockAllocateDTO.allocateUser}&stockAllocateDTO.allocateDate=${stockAllocateDTO.allocateDate}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no');
				if(returnValue==1){			 
				  	document.forms[0].action = "stockAllocateAction!toList?stockAllocateDTO.allocateId=${stockAllocateDTO.allocateId}";
				  }else{
				  	document.forms[0].action = "stockAllocateAction!delete.action?stockAllocateDTO.allocateId=${stockAllocateDTO.allocateId}";
				  }
				  
				document.forms[0].submit();	 
		 }
		 
		 function back(){
		 	backForm.action="stockAllocateAction!list";
		 	backForm.submit();
		 }
		</script>
		
	</body>
</html>
