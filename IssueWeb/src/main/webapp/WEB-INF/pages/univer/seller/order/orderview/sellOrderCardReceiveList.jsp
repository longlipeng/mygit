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
								<span class="TableTop">接收卡片明细</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="cardListTable">
						<s:form id="cardReceiveListForm" name="cardReceiveListForm" action="" method="post">
							<s:hidden name="sellOrderDTO.orderId"></s:hidden>
							<s:hidden name="sellOrderDTO.orderType"></s:hidden>
							<s:hidden name="sellOrderDTO.firstEntityId"/>
							<s:hidden name="actionName"/>
							<s:hidden name="actionMethodName"/>
							<s:hidden name= "message"/>
							<s:hidden name= "operation"/>			
							<s:hidden  name="buyOrderFlag" />	
							<ec:table items="orderCardReceiveList" var="map" width="100%"
								form="cardReceiveListForm" action="${ctx}/${actionName}!${actionMethodName}"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								tableId="orderCardReceiveList">
								<ec:row>
									<ec:column property="cardNo" title="卡号" width="10%" />
									<ec:column property="productName" title="产品" width="10%" />
									<ec:column property="faceValue" title="面额"  width="5%"   />
									<ec:column property="faceValueType" title="面额类型" width="10%" cell="dictInfo" alias="309"/>
								</ec:row>
							</ec:table>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<br/>