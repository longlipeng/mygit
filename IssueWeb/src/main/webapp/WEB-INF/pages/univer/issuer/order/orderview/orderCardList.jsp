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
							<td class="TableTitleFront"
								onclick="displayTableBody('cardListTable');"
								style="cursor: pointer;">
								<span class="TableTop">订单卡明细</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="cardListTable">
						<s:form id="cardListForm" name="cardListForm" action="" method="post">
							<s:hidden name="orderId" />
							<s:hidden name="operation" />
							<s:hidden name="sellOrderDTO.orderId" />
							<ec:table items="orderCardList" var="map" width="100%"
								form="cardListForm" action="${ctx}/${actionName}!${actionMethodName}"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								tableId="orderCardList">
								<ec:row>
									<ec:column property="orderCardListId" title="明细号" width="10%" />
									<ec:column property="cardNo" title="卡号" width="10%" />
									<ec:column property="cardState" title="卡片状态"  width="5%"  cell="dictInfo" alias="309" />
									<ec:column property="cardholderName" title="持卡人" width="10%" />
								</ec:row>
							</ec:table>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<br/>