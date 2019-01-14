<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡作废查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/jquery.jsp" %>
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
			
			 function viewOrder(orderId){
	    			window.open("${ctx}/cardManage/cardInvalidQuery!view?sellOrderQueryDTO.orderId="+orderId);
	    	  	}
			function query(){debugger;
				var starDate = document.getElementById("sellOrderQueryDTO.orderDateStart").value;
				var endDate = document.getElementById("sellOrderQueryDTO.orderDateEnd").value;
				if(starDate!=null && starDate!="" && endDate!=null && endDate!=""){
					if(starDate>endDate){
						errorDisplay("开始日期不能大于结束日期");
						return;
					}
				}
				document.queryForm.submit()
			}
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>		
		<div class="TitleHref">
			<span>卡片管理-->卡作废查询</span>
		</div>
		
		<div id="ContainBox">
		<s:form id="queryForm" name="queryForm"
								action="/cardManage/cardInvalidQuery!query">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
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
							
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														作废单号:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderId"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>	
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														作废机构:
													</td>
													<td>
														<s:select name="sellOrderQueryDTO.entityId" list="sellerQueryDTOs"  listKey="entityId" 
														listValue="sellerName"   headerKey="" headerValue="--请选择--">
														</s:select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														作废人:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.createUserName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
											</td>
												<td>
												<table width="100%">
													<tr>
														<td  width="45%" align="right">
															卡号:
														</td>
														<td>
															<s:textfield name="sellOrderQueryDTO.cardNo"
																id="cardNO" required="true"></s:textfield>
														</td>
													</tr>
												</table>
											</td>
									</tr>
									
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														订单日期From:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderDateStart"
															id="sellOrderQueryDTO.orderDateStart" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														To:
													</td>
													<td>
														<s:textfield name="sellOrderQueryDTO.orderDateEnd"
															id="sellOrderQueryDTO.orderDateEnd" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="center" colspan="2">
											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input type="button" class="bt" style="margin: 5px"
															onclick="query();" value="查 询" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
						</div>
					</td>
				</tr>
			</table>
			<br/>
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
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
						<ec:table items="sellOrders" var="map" width="100%" form="queryForm"
							action="${ctx}/cardManage/cardInvalidQuery!query"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="cardInvalidQueryInfo" autoIncludeParameters="false">
							<ec:row ondblclick="javaScript:viewOrder('${map.orderId}');">
								<ec:column property="orderId" title="作废单号" width="5%">
									<a href="javascript:viewOrder('${map.orderId}');">
										${map.orderId}</a>
								</ec:column>
								<ec:column property="realCardQuantity" title="作废张数" width="5%"/>
								
								<ec:column property="createTime" title="作废日期" width="7%"
									cell="date" format="yyyy-MM-dd" />
								<ec:column property="entityName" title="作废机构" width="8%" />
								<ec:column property="createUser" title="作废人" width="6%" />
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
						bgcolor="#FFFFFF">&nbsp;
							<s:form id="EditForm" name="EditForm"
								action="sellOrderAction!edit">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
							</s:form>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>