<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>		
		<div class="TitleHref">
			<span>卡作废单详细信息</span>
		</div>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
	<script type="text/javascript">	
		var isDisplayServiceTable = false;
		var isDisplayTableBody = false;
		function displayServiceTable(){
			if(isDisplayServiceTable){
				display("serviceTable");
				isDisplayServiceTable = false;
			}else{
				undisplay("serviceTable");
				isDisplayServiceTable = true;
			}
		}
		function displayTableBody(){
			if(isDisplayTableBody){
				display("TableBody");
				isDisplayTableBody = false;
			}else{
				undisplay("TableBody");
				isDisplayTableBody = true;
			}
		}
	</script>
	</head>
		<body >	 
		
		<s:form id="newForm" name="newForm"
			action="/cardManage/cardInvalidQuery!view" method="post">	
			<s:hidden name="sellOrderDTO.orderType" />
			<s:hidden name="errorjsp" />
			<s:hidden name="sellOrderDTO.perFlag" id="perFlag"></s:hidden>
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
										<span class="TableTop">作废单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%">
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											作废单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.orderId"/>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>作废日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="sellOrderDTO.createTime.substring(0,10)" />
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productId"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.productName"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
										 <span style="color: red;">*</span>作废张数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.realCardQuantity"/>
										</td>
										<td align="right" width="15%" >
											<span style="color: red;">*</span>作废操作员：
										</td>
										<td>
											<s:label name="sellOrderDTO.createUser"/>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>作废机构编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.firstEntityId"/>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>作废机构名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.firstEntityName"/>
										</td>
									</tr>
	
				
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											作废原因：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5" readonly="true"></s:textarea>
										</td>	
									</tr>
									
								</table>
							</div>
						</td>
					</tr>
				</table>

		</s:form>	
		
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
										<span class="TableTop">作废单明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
							
							<s:form id="viewForm" name="viewForm"
								action="/cardManage/cardInvalidQuery!view" method="post">	
								<s:hidden name="sellOrderQueryDTO.orderId" />
								<ec:table items="cardList" var="map" width="100%" form="viewForm"
									action="${ctx}/cardManage/cardInvalidQuery!view"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" tableId="cardListInfo" autoIncludeParameters="false">
									<ec:row ondblclick="javaScript:viewOrder('${map.orderId}');">
										<ec:column property="cardNo" title="作废卡号" width="5%"/>
										<ec:column property="productName" title="产品名称" width="6%" />
										<ec:column property="createUser" title="作废人姓名" width="6%" />
										<ec:column property="createId" title="作废人工号" width="6%" />
									</ec:row>
								</ec:table>
							</s:form>
						</div>
		
</body>
</html>