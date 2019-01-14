<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加库存调拨订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
   
	var isDisplay = false;
	function displayServiceTable() {
		if (isDisplay) {
			display('serviceTable');
			isDisplay = false;
		} else {
			undisplay('serviceTable');
			isDisplay = true;
		}
	}
	
    function orderSubmit(){ 	
		
		var obj1 = document.getElementById('sellOrderDTO.firstEntityId');
        var obj2 = document.getElementById('sellOrderDTO.processEntityId');
        var first = obj1.selectedIndex;
        var process = obj2.selectedIndex;
        var firstId = obj1.options[first].value;
        var processId = obj2.options[process].value;
        var orderDate = document.getElementById("sellOrderDTO.orderDate").value;
        
        if(firstId == null || processId ==null || firstId == "" || processId == ""){
            errorDisplay("调出机构和调入机构不能为空");
            return;
        }else if(orderDate == null || orderDate == ""){
            errorDisplay("订单日期不能为空");
            return;
        }else if(firstId == processId){
            errorDisplay("调出机构和调入机构不能相同");
            return;
        }
       
		
		newForm.action = "stockTransferOrderInputAction!insert";
		newForm.submit();
	}
	
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨>添加调拨订单基本信息</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post">
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
										<span class="TableTop">订单信息</span>
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
											订单编号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20" readonly="true" required="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderDate"
												id="sellOrderDTO.orderDate" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调出机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.firstEntityId" id="sellOrderDTO.firstEntityId"
												list="entityList" listKey="entityId"
												listValue="entityName" 
												headerKey="" headerValue="--请选择--">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>调入机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.processEntityId" list="entityList"
															listKey="entityId" listValue="entityName"
														 	id="sellOrderDTO.processEntityId" 
														 	headerKey="" headerValue="--请选择--">
										    </s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.processEntityId
												</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="stockTransferOrderInputAction!list" name="backForm"
				id="backForm">
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					返回
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
				保存
			</button>
			<div style="clear: both"></div>
		</div>
		
		
	</body>
</html>