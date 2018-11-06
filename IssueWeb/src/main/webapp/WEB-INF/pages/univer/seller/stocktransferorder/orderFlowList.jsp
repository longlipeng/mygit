<%@page contentType="text/html; charset=UTF-8"%>


<c:if test="${orderFlow_totalRows>0}">
	<div style="width: 100%" align=center>
		<table width="98%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="B5B8BF">
			<tr>
				<td width="98%" height="10" align="center" valign="middle"
					bgcolor="#FFFFFF">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront"
								onclick="displayTableBody('orderflowListTableBody');"
								style="cursor: pointer;">
								<span class="TableTop">订单流程信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="orderflowListTableBody">
					<s:form id="flowForm" name="flowForm" action=""
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.orderType"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
									<s:hidden name="actionName"/>
									<s:hidden name="actionMethodName"/>
									<s:hidden name= "message"/>
									<s:hidden name= "operation"/>
									<s:hidden  name="buyOrderFlag" />
						<ec:table  items="orderflowList" var="map" width="100%"
							action="${ctx}/${actionName}!${actionMethodName}"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html" form="flowForm"
							retrieveRowsCallback="limit" tableId="orderFlow"  autoIncludeParameters="false" >
							<ec:row>
								<ec:column property="orderflowId" title="xxl.title.id" width="10%" sortable="false"/>
								<ec:column property="createUserName" title="xxl.title.operater" width="10%" sortable="false"/>
								<ec:column property="operateType" title="xxl.title.operateKind" width="10%" sortable="false">
									<dl:dictList dictType="312" dictValue="${map.operateType}"
										tagType="1" />
								</ec:column>
								<ec:column property="orderflowNode" title="xxl.title.flowPoint" width="10%" sortable="false">
									<dl:dictList dictType="310" dictValue="${map.orderflowNode}"
										tagType="1" />
								</ec:column>
								<ec:column property="createTime" title="xxl.title.operateTime" width="20%" sortable="false"/>
								<ec:column property="memo" title="xxl.title.annocation" sortable="false">
									<span id="clickspan" onclick="messageDisplay('注释','${map.memo}','clickspan');">注释</span>
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
</c:if>