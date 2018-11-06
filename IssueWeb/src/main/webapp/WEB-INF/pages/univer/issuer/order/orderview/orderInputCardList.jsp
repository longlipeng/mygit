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
								<span class="TableTop">录入卡列表</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="inputCardListTable">
						<s:form id="cardListForm" name="cardListForm" action="stockOrderConfirmAction!downLoad" method="post">
							<s:hidden name="orderId" />
							<s:hidden name="operation" />
							<s:hidden name="sellOrderDTO.orderId" />
							<ec:table items="orderInputCardList" var="map" width="100%"
								form="cardListForm" action="${ctx}/${actionName}!${actionMethodName}"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								tableId="orderInputCardList">
								<ec:row>
									<ec:column property="orderId" title="订单号" width="50%" />
									<ec:column property="cardNo" title="卡号" width="50%" />
								</ec:row>
							</ec:table>
							<input type="submit"  style="float: right; margin: 40px 7px 2px" class='btn' value="导出Excel"/>
							<!-- <button class='btn' style="float: right; margin: 40px 7px 2px"
								onclick="downLoad()">
								导出Excel
							</button>  -->
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<br/>