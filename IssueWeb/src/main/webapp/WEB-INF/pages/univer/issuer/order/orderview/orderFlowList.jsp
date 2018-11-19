<%@page contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
	function viewFlow(orderFlowId){
		window.showModalDialog('${ctx}/${actionName}!viewOrderFlow?sellOrderFlowDTO.orderFlowId='+orderFlowId, '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
	}
</script>

	<div style="width: 100%" align=center>
		<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
			<tr>
				<td width="98%" height="10" align="center" valign="middle"
					bgcolor="#FFFFFF">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront"
								onclick="displayTableBody('orderFlowTable');"
								style="cursor: pointer;">
								<span class="TableTop">订单流程信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="orderFlowTable">
						<s:form id="orderFlowForm" name="orderFlowForm" action="" method="post">
							<s:hidden name="orderId" />
							<s:hidden name="operation" />
							<s:hidden name="sellOrderDTO.orderId" />
							<ec:table items="sellOrderFlowList" var="map" width="100%"
								form="orderFlowForm" action="${ctx}/${actionName}!${actionMethodName}"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								tableId="sellOrderFlow">
								<ec:row>
									<ec:column property="orderflowId" title="序号" width="10%" />
									<ec:column property="createUserName" title="操作员" width="10%" />
									<ec:column property="operateType" title="操作种类" width="10%">
										<dl:dictList dictType="312" dictValue="${map.operateType}" tagType="1" />
									</ec:column>
									<ec:column property="orderflowNode" title="流程节点" width="10%">
										<dl:dictList dictType="310" dictValue="${map.orderflowNode}" tagType="1" />
									</ec:column>
									<ec:column property="createTime" title="xxl.title.operateTime" width="20%"/>
									<ec:column property="memo" title="注释">
										<span id="clickspan" onclick="viewFlow(${map.orderflowId});">查看详细</span>
									</ec:column>
								</ec:row>
							</ec:table>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<br/>