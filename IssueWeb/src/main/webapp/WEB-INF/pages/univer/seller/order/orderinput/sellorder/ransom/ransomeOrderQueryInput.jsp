<%@page contentType="text/html; charset=UTF-8"%>


	<s:form id="queryForm" name="queryForm" action="">
		<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
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
						<table width="80%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr height="35">
								<td align="right">
									<span>订单号：</span>
								</td>
								<td align="left">
									<s:textfield name="sellOrderOrigCardListDTO.orderId" id="orderId" />
								</td>
								<td align="right">
									<span>创建人名称：</span>
								</td>
								<td align="left">
									<s:textfield name="sellOrderOrigCardListDTO.createUser" id="sellOrderOrigCardListDTO.createUserName"></s:textfield>
								</td>
								
							</tr>
							<tr height="35">
								<td align="right">
									<span>订单发起者编号：</span>
								</td>
								<td align="left">
									<s:textfield name="sellOrderOrigCardListDTO.firstEntityId" id="firstEntityId" />
								</td>
								<td align="right">
									<span>订单发起者名称：</span>
								</td>
								<td align="left">
									<s:textfield name="sellOrderOrigCardListDTO.firstEntityName" id="firstEntityName" size="20" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td align="right">
									<input type="button" class="bt" style="margin: 5px"
										onclick="queryOrder();" value="查 询" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
<br />

