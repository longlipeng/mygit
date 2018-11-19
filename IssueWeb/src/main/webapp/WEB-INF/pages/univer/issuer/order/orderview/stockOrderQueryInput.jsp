<%@page contentType="text/html; charset=UTF-8"%>


	<s:form id="queryForm" name="queryForm" action="">
	    <s:hidden name="companyId" id="companyId" />
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
								<td width="" align="right">
									<span>订单号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderQueryDTO.orderId" id="orderId" />
								</td>
								<td width="" align="right">
									<span>创建人：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderQueryDTO.createUserName" id="sellOrderQueryDTO.createUserName"></s:textfield>
								</td>
								
							</tr>
							<tr height="35">
								<td width="" align="right">
									<span>订单发起者编号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderQueryDTO.firstEntityId" id="firstEntityId" />
								</td>
								<td width="" align="right">
									<span>订单发起者：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="sellOrderQueryDTO.firstEntityName" id="firstEntityName" size="20" />
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

