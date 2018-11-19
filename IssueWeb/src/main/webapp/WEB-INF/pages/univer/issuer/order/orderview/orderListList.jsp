<%@page contentType="text/html; charset=UTF-8"%>
		
<div style="width: 100%" align=center>
	<table width="98%" border="0" cellpadding="0" cellspacing="1"
		bgcolor="B5B8BF">
		<tr>
			<td width="98%" height="10" align="center" valign="middle"
				bgcolor="#FFFFFF">
				<table width="100%" height="20" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td class="TableTitleFront" onclick="displayTableBody('orderListTable');"
							style="cursor: pointer;">
							<span class="TableTop">订单明细信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
				<div id="orderListTable">
					<s:form id="listForm" name="listForm" action="" method="post">
						<s:hidden name="sellOrderDTO.orderId" />
						<ec:table items="orderListList" var="map" width="100%"
							form="listForm" action="${ctx}/${actionName}!${actionMethodName}"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" autoIncludeParameters="false"
							tableId="orderListList">
							<ec:row>
								<ec:column property="null" alias="orderListStr" title="选择"
									width="10%" sortable="false" headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="orderListStr"
										value="${map.orderListId}" />
								</ec:column>
								<ec:column property="cardLayoutName" title="卡面" width="10%" />
								<ec:column property="faceValue" title="面额值" width="10%" />
								<ec:column property="validityPeriod" title="有效期" width="10%" />
								<ec:column property="packageFee" title="包装费" width="10%" />
								
								<ec:column property="cardAmount" title="张数" />
								<ec:column property="realAmount" title="配送张数" />
								<ec:column property="realAmount" title="实际张数" />
							</ec:row>
						</ec:table>
					</s:form>
				</div>
			</td>
		</tr>
	</table>
</div>
<br />