<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单卡明细</title>
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
			

			function editCardState(orderListId){
				var returnValue = window.showModalDialog('${ctx}/stockOrderAcceptAction!editCardState?sellOrderCardListDTO.orderListId='+orderListId, 
							'_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(returnValue=='success'){
					reload();
				}
			}

			function reload(){
				EditForm.action='${ctx}/stockOrderAcceptAction!makeCardList';
				EditForm.submit();
			}
			 
			 function viewOrder(orderId){
    			window.open("${ctx}/orderMakeCardAction!view.action?closeFlag=1&orderId=" + orderId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
    	  	}
			
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>订单管理-->订单卡明细状态修改</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm" action="stockOrderAcceptAction!makeCardList">
			<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
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
							
								<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="85" align=right>
											<span>卡号：</span>
										</td>
										<td>
											<s:textfield name="sellOrderCardListQueryDTO.cardNo"
												id="cardNo" required="true"></s:textfield>
											&nbsp;
											<input type="submit" onclick="" value="查询"/>
										</td>
									</tr>
								</table>
						</div>
					</td>
				</tr>
			</table>
			<br/>
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
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
						<ec:table items="orderCardList" var="map" width="100%" form="queryForm"
							action="${ctx}/stockOrderAcceptAction!makeCardList"
							view="html"
							retrieveRowsCallback="limit" tableId="orderCardList" autoIncludeParameters="false">
							<ec:row>
								<ec:column property="orderCardListId" title="订单明细号" width="6%" />
								<ec:column property="cardNo" title="卡号" width="10%" />
								<ec:column property="faceValue" title="面额值" width="10%" />
								<ec:column property="cardState" title="制卡状态" width="10%" >
								 <s:property value="#attr['map'].cardState == 3 ? '有效' : '无效'"/>
								</ec:column>
								<ec:column sortable="false" property="null" title="操作" width="10%">
									<a href="javascript:editCardState('${map.orderCardListId}');">修改卡状态</a>
								</ec:column>
							</ec:row>
						</ec:table>
						</div>
					</td>
				</tr>
			</table>
			</s:form>
		</div>
		<br/>
		<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm" action="" method="post">
								<s:hidden name="sellOrderCardListDTO.orderListId" id="orderListId"/>
								<s:hidden name="sellOrderDTO.orderId" id="orderId"/>
								<s:hidden name="operation" id="operation" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn' style="float: right; margin: 40px 7px 2px"
												onclick="window.open('${ctx}/stockOrderAcceptAction!list', '_self')">
												返 回
											</button>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>