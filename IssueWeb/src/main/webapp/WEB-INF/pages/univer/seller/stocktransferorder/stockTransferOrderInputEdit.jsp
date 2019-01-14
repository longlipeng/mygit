<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>调拨订单录入编辑</title>
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
	//ajax获得产品
	function productChange() {
	    
	    var obj1 = document.getElementById('sellOrderDTO.firstEntityId');
        var obj2 = document.getElementById('sellOrderDTO.processEntityId');
        var first = obj1.selectedIndex;
        var process = obj2.selectedIndex;
        var firstId = obj1.options[first].value;
        var processId = obj2.options[process].value;
        if(firstId == null || processId == null || firstId == "" || processId == ""){
	        return;
	    }
		ajaxRemote('stockTransferOrderInputAction!getProducts.action',
	               {'sellOrderDTO.firstEntityId': firstId,'sellOrderDTO.processEntityId':processId},
	               successFn,
	               'json'
	    )
	    
	}
    function submitForm(){
        var obj1 = document.getElementById('sellOrderDTO.firstEntityId');
        var first = obj1.selectedIndex;
        var firstId = obj1.options[first].value;
	        
	    var obj3 = document.getElementById('sellOrderDTO.processEntityId');
        var process = obj3.selectedIndex;
        var processId = obj3.options[process].value;
	        
        var orderDate = document.getElementById('sellOrderDTO.orderDate').value;
        
        if(firstId == null || firstId == "" || processId == null || processId == ""){
	            errorDisplay("请选择调出机构和调入机构");
	            return;
	    }else if(orderDate == null || orderDate == ""){
	            errorDisplay("订单日期不能为空");
	            return;
	    }else if(firstId == processId){
            errorDisplay("调出机构和调入机构不能相同");
            return;
        }
        document.newForm.action = "stockTransferOrderInputAction!update";
        document.newForm.submit();
    }
 	//获得产品列表
    function successFn(productList){
        document.getElementById('sellOrderDTO.productId').options.length = 0; 
        document.getElementById('sellOrderDTO.productId').list = "productList";
        document.getElementById('sellOrderDTO.productId').listKey = "productId";
        document.getElementById('sellOrderDTO.productId').listValue = "productName";
        
       	for(var i = 0;i < productList.length;i++){
        document.getElementById('sellOrderDTO.productId').options[i] = 
        	new Option(productList[i].productName,productList[i].productId);
       }
    }
    //删除当前一条记录
    function deleteRecord(orderListId){
    	document.EditForm.action = "stockTransferOrderInputAction!deleteRecord.action?orderListId="+orderListId;
    	document.EditForm.submit();
    }
    //获得产品明细
    function getFirstEntityStock(){
			
			var obj1 = document.getElementById('sellOrderDTO.firstEntityId');
	        var first = obj1.selectedIndex;
	        var firstId = obj1.options[first].value;
	        
	        var obj2 = document.getElementById('sellOrderDTO.productId');
	        var product = obj2.selectedIndex;
	        if(product < 0){
	            errorDisplay("无有效产品");
	            return;
	        }
	        var productId = obj2.options[product].value;
	        
	        var orderId = document.getElementById('orderId').value;
	        
	        var obj3 = document.getElementById('sellOrderDTO.processEntityId');
	        var process = obj3.selectedIndex;
	        var processId = obj3.options[process].value;
	        
	        var orderDate = document.getElementById('sellOrderDTO.orderDate').value;
	        if(firstId == null || firstId == "" || processId == null || processId == ""){
	            errorDisplay("请选择调出机构和调入机构");
	            return;
	        }else if(orderDate == null || orderDate == ""){
	            errorDisplay("订单日期不能为空");
	            return;
	        }else if(firstId == processId){
            errorDisplay("调出机构和调入机构不能相同");
            return;
       		}else if(productId == null || productId == ""){
	            errorDisplay("请选择产品");
	            return;
	        }
	        
			var returnValue = window.showModalDialog('stockTransferOrderInputAction!getFirstEntityStock?sellOrderDTO.firstEntityId='+firstId
				+'&sellOrderDTO.productId='+productId
				+'&sellOrderDTO.orderId='+orderId
				+'&sellOrderDTO.processEntityId='+processId
				+'&sellOrderDTO.orderDate='+orderDate, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		
			if (returnValue == null || returnValue == undefined){
			return;	 
			}
			  	document.newForm.action = "stockTransferOrderInputAction!stockTransferOrderEdit?orderId="+returnValue;
			  	document.newForm.submit();
		}
    
	function init(){
	    //var obj1 = document.getElementById('sellOrderDTO.firstEntityId');
	       // var first = obj1.selectedIndex;
	       // var firstId = obj1.options[first].value;
	        
	       // firstId = "${map.firstEntityId}";
	        productChange();
	        
	        var initTable = document.getElementById("stockTransferOrderList_table");
	        if(initTable.rows.length > 2){
	            document.getElementById("sellOrderDTO.firstEntityId").disabled = true;
	            document.getElementById("sellOrderDTO.processEntityId").disabled = true;
	        }
	}
	
	//function init
	
</script>
	</head>
	<body onload="init()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨>编辑调拨订单</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="" method="post">
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
												cssClass="Wdate" >
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
												listValue="entityName" onchange="productChange(this);"
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
														 	id="sellOrderDTO.processEntityId" onchange="productChange(this.value);"
														 	headerKey="" headerValue="--请选择--">
										    </s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.processEntityId
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡片总数：
										</td>
										<td>
										<s:textfield name="sellOrderDTO.cardQuantity" readonly="true"
													id="sellOrderDTO.cardQuantity" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardQuantity
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select  id="sellOrderDTO.productId"  list="{}"
											listKey="" listValue="" headerKey="" headerValue="--请选择--">
										    </s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.processEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td>
										    <div id="addBtn" class="btn"
												style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 18px"
												onclick="getFirstEntityStock();">
												添加
											</div>
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
				onclick="submitForm();">
				保存
			</button>
			<div style="clear: both"></div>
		</div>
		
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
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<s:form id="EditForm" name="EditForm" action="stockTransferOrderInputAction!stockTransferOrderEdit"
									method="post">
									<s:hidden name="sellOrderDTO.orderId"></s:hidden>
									<s:hidden name="sellOrderDTO.firstEntityId"/>
								<ec:table items="stockTransferOrderList" var="map" width="100%" form="EditForm"
									action="stockTransferOrderInputAction!stockTransferOrderEdit?orderId=${sellOrderDTO.orderId}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html" showPagination="false"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="stockTransferOrderList">
									<ec:row>
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.orderListId}" />
										</ec:column>
										<ec:column property="productName" title="产品" width="15%" />
										<ec:column property="cardAmount" title="卡片数量" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="null" sortable="false" title="操作">
											 <a href="javascript:deleteRecord('${map.orderListId}');">
                                           		删除
                                        	</a>
										</ec:column>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
		
	</body>
</html>