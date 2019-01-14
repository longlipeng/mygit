<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单准备</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
			function cardReady(){
			    var count=0;
			    var orderListId="";
				for(i = 0; i < document.getElementsByName("orderListIdStr").length; i++) {
					if (document.getElementsByName("orderListIdStr").item(i).checked) {
						orderListId=document.getElementsByName("orderListIdStr").item(i).value;
						count++;
					}
				}
				if(count==0){
					errorDisplay("请选择一条订单明细信息!");
					return;
				}
				if(count!=1){
					errorDisplay("只能选择一条订单明细信息!");
					return;
				}
				var returnValue =window.showModalDialog('${ctx}/stockTransferOrderReadyAction!cardReady?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.orderListId='+orderListId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
				//避免returnValue在各个浏览器上的差异，不需要判断返回值，直接刷新页面;
				//经过后续测试,暂时不改
				if(returnValue!=null){			 
				  	document.getElementById("sellOrderDTO.orderId").value=returnValue;
				  	document.forms[0].action = "stockTransferOrderReadyAction!ready";
				  	document.forms[0].submit();
				}	  
			}
			function deleteRecord(orderCardListId){
				newForm.action="${ctx}/stockTransferOrderReadyAction!deleteCheckedRecord?orderReadyDTO.orderId=${sellOrderDTO.orderId}&orderReadyDTO.firstEntityId=${sellOrderDTO.firstEntityId}";
				newForm.orderCardListId.value=orderCardListId;
				newForm.submit();
				return ;
			}
			
			function operationDel(){		
				document.newForm.action="${ctx}/stockTransferOrderReadyAction!deleteAllRecord";
				document.newForm.submit();
			}

			function deleteAllCard(){
				confirm('确定要删除吗?',operationDel);
				return ;
			}
			
			function submitForm(){
			    document.newForm.action="${ctx}/stockTransferOrderReadyAction!submitOrderReady";
				document.newForm.submit();
			}
		</script>
		
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨>调拨订单准备</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="stockTransferOrderInputAction!update" method="post">
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%">
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.orderId"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.orderDate"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调出机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										    <s:label name="sellOrderDTO.firstEntityName"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调入机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.processEntityName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡片总数：
										</td>
										<td>
										<s:label name="sellOrderDTO.cardQuantity"/>
										</td>
									   </tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<s:hidden name="sellOrderDTO.orderId"  id="sellOrderDTO.orderId"/>
			<s:hidden name="sellOrderDTO.firstEntityId"/>
			<s:hidden name="orderReadyDTO.orderCardListId"  id="orderCardListId"/>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
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
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="orderlistListForm" name="orderlistListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
								<ec:table items="orderListDTOs" var="map" width="100%" form="orderlistListForm"
									action="${ctx}/stockTransferOrderReadyAction!ready"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderListRow">
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListIdStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListIdStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="productName" title="产品名称" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="10%" />
										<ec:column property="faceValue" title="面额值" width="15%" />
										<ec:column property="validityPeriod" title="有效期" width="10%" />
										<ec:column property="cardAmount" title="张数" />
										<ec:column property="realAmount" title="实际张数" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
			<!-- div id=TableBody -->
			
			
			
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
										<span class="TableTop">卡明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="orderCardListBody">
								<s:form id="orderCardListForm" name="orderCardListForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>								
								<ec:table items="orderCardListDTOs" var="map" width="100%" form="orderCardListForm"
									action="${ctx}/stockTransferOrderReadyAction!ready"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCardListRow" >
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderCardListId}" />
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="null" sortable="false" title="操作" width="15%" >
												  <a href="javascript:deleteRecord('${map.orderCardListId }');"> 删除 </a>
										</ec:column>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
			</td></tr></table></div>
	
		<div style="width: 100%" align=center>
		
			<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											
												<div id="deleteBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="deleteAllCard();">
														删除全部
												</div>	
												
												<div id="buttonCRUD" style="text-align: right; width: 100%">
													  <div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="cardReady();">
														准备
													</div>
													
													<!--
													<div id="addBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="orderListAllReady();">
														全部准备
													</div>
													-->
												</div>
												<div style="clear: both"></div>
										</div>
									</td>
								</tr>
								<tr>
								    <td>
										<div id="buttonCRUD" style="text-align: right; width: 100%">
													  <div id="addBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="submitForm();">
														提交订单
										</div>
								    </td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>