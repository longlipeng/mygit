<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>验卡信息查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
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
			
			function submit() {
				var flag = true;
				var selectValue = "";
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						if (flag) {
							flag = false;
							selectValue = document.getElementsByName("choose").item(i).value;
						} else {
							selectValue = "";
							flag = true;
							errorDisplay("请选择一条记录！");
							return;
						}
					}
				}
				if (flag) {
					errorDisplay("请选择一条记录操作！");
					return;
				} else {
					window.returnValue=selectValue;
					window.close();
				}
				
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡号查询</span>
		</div>
		<div id="ContainBox">
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
							<s:form id="queryForm" name="queryForm"
								action="ransomOrderAction/checkCardQueryList.action">
								<input type="hidden" name="sellOrderOrigCardListDTO.orderId" value="${sellOrderOrigCardListDTO.orderId}"/>
								<input type="hidden" name="sellOrderOrigCardListDTO.firstEntityId" value="${sellOrderOrigCardListDTO.firstEntityId}"/>
								<table>
									<tr>
										<td width="165" align=right>
											卡号:
										</td>
										<td>
											<s:textfield name="sellOrderOrigCardListDTO.cardNo"  cssStyle="width:300px" size="14" />
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class='bt' onclick="queryForm.submit();">
												查 询
											</button>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>

		</div>
		<br>
		<br>
		<div style="width: 100%" align=center>
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

							<s:form id="origCardListForm" name="origCardListForm" action="ransomOrderAction/checkCardQueryList.action"
									method="post">
								<input type="hidden" name="sellOrderOrigCardListDTO.orderId" value="${sellOrderOrigCardListDTO.orderId}"/>
								<input type="hidden" name="sellOrderOrigCardListDTO.callBack" />
								<ec:table items="pageDataDTO.data" var="map" width="100%" form="origCardListForm"
											action="${ctx}/ransomOrderAction!checkCardQueryList"
											imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
											retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="pageLists">
									<ec:row ondblclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="5%" sortable="false" viewsAllowed="html">
											<c:choose>
												<c:when test="${ROWCOUNT == 1}">
													<input type="radio" name="choose" 
													value="${map.cardNo},${sellOrderOrigCardListDTO.orderId},${map.productName},
														${map.amountStr},${map.firstName},${map.validityPeriodStr},${map.cardholderId},${map.productId},
														${map.cardSate},${map.cancelSate}"
														checked="true" />
												</c:when>
												<c:otherwise>
													<input type="radio" name="choose" value="${map.cardNo},${sellOrderOrigCardListDTO.orderId},${map.productName},
														${map.amountStr},${map.firstName},${map.validityPeriodStr},${map.cardholderId},${map.productId},
														${map.cardSate},${map.cancelSate}"/>
												</c:otherwise>
											</c:choose>
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="productName" title="产品名称" width="15%" />
										<ec:column property="amount" title="余额" width="15%" />
										<ec:column property="firstName" title="持卡人姓名" width="15%" />
										<ec:column property="validityPeriod" title="有效期" width="10%" cell="date" format="yyyy-MM-dd"  />
									</ec:row>
							</ec:table>
						</s:form>
							
							
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px"
				onclick="window.close()">
				关 闭
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>