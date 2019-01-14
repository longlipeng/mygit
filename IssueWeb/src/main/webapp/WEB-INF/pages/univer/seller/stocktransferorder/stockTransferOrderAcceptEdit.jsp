<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单准备</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		    function acceptCard(){
		    
		        var startCardNo = document.getElementById("start").value;
		        var endCardNo = document.getElementById("end").value;
		        
		        //var startCardNoArr = document.getElementsByName("startCardNo");
		        //var endCardNoArr = document.getElementsByName("endCardNo");
		        //alert(startCardNoArr.length);
		        startCardNo = startCardNo.replace(/\s+/g,"");
		        endCardNo = endCardNo.replace(/\s+/g,"");

		        if(startCardNo == null ||startCardNo == "" || endCardNo == null || endCardNo == "" || isNaN(startCardNo) || isNaN(endCardNo)){
		            errorDisplay("请输入有效卡号段");
		            return;
		        }else if(startCardNo.length > endCardNo.length){
		            errorDisplay("结束卡号不小于起始卡号");
		            return;
		        }
		        
		        var myTable = document.getElementById("cardPeriodListRow_table");
		        if(myTable.rows.length > 2){
		        for(var i = 2;i<myTable.rows.length;i++){
		            if((myTable.rows[i].cells[2].childNodes[1].value <= endCardNo && myTable.rows[i].cells[2].childNodes[1].value >= startCardNo) ||
		             (myTable.rows[i].cells[3].childNodes[1].value <= endCardNo && myTable.rows[i].cells[3].childNodes[1].value >= startCardNo) ||
		             (startCardNo >= myTable.rows[i].cells[2].childNodes[1].value && startCardNo <= myTable.rows[i].cells[3].childNodes[1].value) ||
		             (endCardNo >= myTable.rows[i].cells[2].childNodes[1].value && endCardNo <= myTable.rows[i].cells[3].childNodes[1].value)){
		                errorDisplay("卡号段不能重叠");
		                return;          
		            }
		        }
		        }
		        
		        document.getElementById("start").value = document.getElementById("start").value.replace(/\s+/g,"");
		        document.getElementById("end").value = document.getElementById("end").value.replace(/\s+/g,"");
				document.cardPeriodForm.action = "stockTransferOrderAcceptAction!acceptCard";
				document.cardPeriodForm.submit();
			}
			
			function deleteAllRecord(deleteId){
			    var myTable = document.getElementById("cardPeriodListRow_table");
			    
			    if(myTable.rows.length <= 2){
			        errorDisplay("没有需要删除的记录");
			        return;
			    }
			    
			    document.cardPeriodForm.action = "stockTransferOrderAcceptAction!accept";
				document.cardPeriodForm.submit();
			    
			}
			function deleteRecord(deleteId){
			    document.cardPeriodForm.action = "stockTransferOrderAcceptAction!deleteRecord?deleteId="+deleteId;
				document.cardPeriodForm.submit();
			    
			}
			
			function deleteCheckedRecord(){
			    for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
							document.cardPeriodForm.action = "stockTransferOrderAcceptAction!deleteCheckedRecord";
							document.cardPeriodForm.submit();
					}
				}
			}
			
			function submitAccept(){
			    var myTable = document.getElementById("cardPeriodListRow_table");
			    
			    if(myTable.rows.length <= 2){
			        errorDisplay("没有需要提交的记录");
			        return;
			    }
			    document.cardPeriodForm.action = "stockTransferOrderAcceptAction!submitAccept";
				document.cardPeriodForm.submit();
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨>调拨订单接收</span>
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
										<br></td>
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
										<span class="TableTop">出库明细信息</span>
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
									<s:hidden name="sellOrderDTO.orderType"/>
								<ec:table items="orderListDTOs" var="map" width="100%" form="orderlistListForm"
									action="${ctx}/stockTransferOrderAcceptAction!accept" showPagination="false"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html" sortable="false"
									retrieveRowsCallback="limit" autoIncludeParameters="true" tableId="orderListRow">
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
										<span class="TableTop">未接收卡片</span>
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
									<s:hidden name="cardPeriodIdStr"></s:hidden>
									<s:iterator value="cardPeriodList" var="map">
									    <s:iterator value="#map">
									        <input type="hidden" name="cardPeriodListKey" value="<s:property value="key"/>"/>
									        <input type="hidden" name="cardPeriodListValue" value="<s:property value="value"/>"/>
									    </s:iterator>
									</s:iterator>
								<ec:table items="orderCardListDTOs" var="map" width="100%" form="orderCardListForm"
									action="${ctx}/stockTransferOrderAcceptAction!accept"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCardListRow" >
									<ec:row ondblclick="">
										<ec:column property="productName" title="产品名称" width="15%" />
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
									</ec:row>
								</ec:table>
								</s:form>
							</div>
							
					</td>
				</tr>
			</table>
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
										<span class="TableTop">接收卡片明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="orderCardListBody">
								<s:form id="cardPeriodForm" name="cardPeriodForm" action=""
									method="post">
									<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="90" align=right>
											<span>起始卡号：</span>
										</td>
										<td width="160">
											<s:textfield name="startCardNoStr" 
												id="start"/>
											&nbsp;
										</td>
										<td width="90" align=right>
											<span>终止卡号：</span>
										</td>
										<td width="160">
											<s:textfield name="endCardNoStr"
												id="end"/>
											&nbsp;
										</td>
										<td width="90" align=right>
										<div id="addBtn" class="btn"
											style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
											onclick="acceptCard();">
												接收
										</div>
										</td>
										<td align="center">
											&nbsp;
										</td>
									</tr>
									
								</table>
								    <s:hidden name="cardPeriodIdStr"></s:hidden>
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="sellOrderDTO.orderType" />	
								<ec:table items="cardPeriodList" var="map" width="100%" showPagination="false"
								    action="${ctx}/stockTransferOrderAcceptAction!acceptCard" form="cardPeriodForm"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"  sortable="false"
									 autoIncludeParameters="false" tableId="cardPeriodListRow" >
									<ec:row ondblclick="">
									    <ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="choose"
												value="${map.cardPeriodId}" />
										</ec:column>
										<ec:column property="cardPeriodId" title="序号" width="15%">
										${map.cardPeriodId}
										<input type="hidden" name="cardPeriodId" value="${map.cardPeriodId}"/>
										</ec:column>
										<ec:column property="startCardNo" title="起始卡号" width="15%" >
										${map.startCardNo}
										<input type="hidden" name="startCardNo" value="${map.startCardNo}"/>
										</ec:column>
										<ec:column property="endCardNo" title="结束卡号" width="15%" >
										${map.endCardNo}
										<input type="hidden" name="endCardNo" value="${map.endCardNo}"/>
										</ec:column>
										<ec:column property="cardSum" title="张数" width="15%" >
										${map.cardSum}
										<input type="hidden" name="cardSum" value="${map.cardSum}"/>
										</ec:column>
										<ec:column property="null" sortable="false" title="操作" width="15%" >
												  <a href="javascript:deleteRecord('${map.cardPeriodId }');"> 删除 </a>
										</ec:column>
									</ec:row>
								</ec:table>
								
								<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<div id="buttonCRUD" style="text-align: right; width: 100%">
											    <div id="deleteBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="deleteAllRecord();">
														删除全部
												</div>
											    <!--<div id="deleteBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="deleteCheckedRecord();">
														删除
												</div>
												--><div id="buttonCRUD" style="text-align: right; width: 100%">
													  <div id="addBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="submitAccept();">
														提交
												</div>
												</div>
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
			
	</body>
</html>