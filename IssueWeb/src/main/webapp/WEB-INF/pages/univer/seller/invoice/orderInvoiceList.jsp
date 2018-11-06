<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		var box;
		var userChoice="";
		function checkedCount() {
	        var checkboxs = document.getElementsByName('invoiceIdList');
	        var count = 0;
	        for (var i = 0; i < checkboxs.length; i++) {
	            if (checkboxs[i].checked) {               
	                count++;
	            }
	        }
	        return count;
	    }
	    
    	function deleteOrderInvoice(){
         	userChoice="delete";
            var count = checkedCount();
            	if (count < 1) {
                alert('请至少选择一条记录！');
                return;
            }else if (count > 1) {
	            alert('只能选择一条记录！');
	            return;
       	    }
           confirm("确定执行取消操作?",update);
         }
         
         function doInvoice() {
             var inputStr;
			 var checkboxs = document.getElementsByName('invoiceIdList');
	         var count = 0;
	         for (var i = 0; i < checkboxs.length; i++) {
	             if (checkboxs[i].checked) {  
	                 inputStr=checkboxs[i].value;             
	                 count++;
	             }
	         }
	         var orderInvoiceForm = Ext.get("orderInvoiceForm").dom;
	         if (count < 1) {
	             alert('请选择一条记录！');
	             return;
	         } else if (count > 1) {
	             alert('只能选择一条记录！');
	             return;
       	     }
       	    userChoice="invoice";
       	    var inputArr=inputStr.split(','); 
       	    if("1" == inputArr[1]) {
       	   	    alert("已开票");
       	        return;
       	    }
       	    if("2" == inputArr[1]) {
       	   	    alert("发票已取消");
       	        return;
       	    }
		    var returnValue = window.showModalDialog('${ctx}/orderInvoice/toAddOrderInvoiceConfirm?orderInvoiceInfoDTO.invoiceId=' 
		        + inputArr[0]+"&orderInvoiceInfoDTO.invoiceState=" + inputArr[1], 
				'_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		    if(undefined == returnValue || "1" == returnValue) {	  
		        queryForm.submit();
		        reload();
		    }	 
		 }
		
		function update(type){
	    	var orderInvoiceForm = Ext.get("orderInvoiceForm").dom;
	    	if (orderInvoiceForm['ec_eti'] != null) {
            orderInvoiceForm['ec_eti'].disabled=true;
        	}
			if(type=="edit"){
				orderInvoiceForm.action='${ctx}/orderInvoice/editInvoice.action?orderInvoiceInfoQueryDTO=orderInvoiceInfoQueryDTO';
			}else if(userChoice=='delete'){
				orderInvoiceForm.action='${ctx}/orderInvoice/cancelInvoice.action';
			}else if(userChoice=='invoice'){
				orderInvoiceForm.action='${ctx}/orderInvoice/doInvoice.action';
			}
			else{
				orderInvoiceForm.action='${ctx}/orderInvoice/list.action';
			}			
			orderInvoiceForm.submit();
	    }
		
	    function editInvoice() {
	        var count = checkedCount();
	        var orderInvoiceForm = Ext.get("orderInvoiceForm").dom;
	        if (orderInvoiceForm['ec_eti'] != null) {
            orderInvoiceForm['ec_eti'].disabled=true;
        	}
	        if (count < 1) {
	            alert('请选择一条记录！');
	            return;
	        } else if (count > 1) {
	            alert('只能编辑一条记录！');
	            return;
       	    }
	        orderInvoiceForm.action='${ctx}/orderInvoice/toEditInvoice.action';
        	orderInvoiceForm.submit();
	    }

       function viewOrder(orderId,orderType){
   			window.open("${ctx}/orderQueryAction!view.action?closeFlag=1&sellOrderDTO.orderId=" + orderId+"&sellOrderDTO.orderType="+orderType,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
   	  	}
   	  	
	   function viewInvoice(invoiceId){
   			window.open("${ctx}/orderInvoice/viewInvoice.action?closeFlag=1&orderInvoiceInfoDTO.invoiceId=" + invoiceId,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
   	  	}
	   	  	
		</script>
		<title>发票管理界面</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发票管理界面</span>
		</div>
		<div id="query"
			style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">查询条件</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="queryTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="queryForm" name="queryForm"
					action="orderInvoice/list.action" method="post">
					<s:hidden name="type"></s:hidden>
					<table width="100%" style="table-layout: fixed;">
						<tr height="35">
							<td width="85" align=right>
								<span>订单号：</span>
							</td>
							<td width="160">
								<s:textfield name="orderInvoiceInfoQueryDTO.orderId" />
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.orderId</s:param>
								</s:fielderror>
							</td>
							<!--					TODO					-->
							<td width="90" align=right>
								<span>销售人员：</span>
							</td>
							<td width="160">
								<s:select name="orderInvoiceInfoQueryDTO.saleManName" list="saleManList" 
									 listKey="userName" listValue="userName" headerKey="" headerValue="--请选择--" />
								<s:fielderror>
									<s:param>
										orderInvoiceInfoQueryDTO.saleManName
									</s:param>
								</s:fielderror>
							</td>
						</tr>

						<tr height="35">
							<td width="85" align=right>
								<span>订单发起者：</span>
							</td>
							<td width="160">
								<s:textfield name="orderInvoiceInfoQueryDTO.firstEntityName" />
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.firstEntityName</s:param>
								</s:fielderror>
							</td>
							<td width="90" align=right>
								<span>客户名称：</span>
							</td>

							<td width="160">
								<s:textfield name="orderInvoiceInfoQueryDTO.customerName" />
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.customerName</s:param>
								</s:fielderror>
							</td>
						</tr>

						<tr height="35">
							<td width="85" align=right>
								<span>发票类型：</span>
							</td>
							<td width="160">
								<dl:dictList dictType="182" tagType="2" defaultOption="true"
									displayName="orderInvoiceInfoQueryDTO.invoiceType"
									dictValue="${orderInvoiceInfoQueryDTO.invoiceType}"></dl:dictList>
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.invoiceType</s:param>
								</s:fielderror>
							</td>
							<!--					TODO					-->
							<td width="90" align=right>
								<span>开票项目：</span>
							</td>

							<td width="160">
								<dl:dictList displayName="orderInvoiceInfoQueryDTO.invoiceProj" defaultOption="true"
								dictType="120" tagType="2" dictValue="${orderInvoiceInfoQueryDTO.invoiceProj}"></dl:dictList>
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.invoiceProj</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr height="35">
							<td width="85" align=right>
								<span>开票主体：</span>
							</td>
							<td width="160">
								<s:textfield name="orderInvoiceInfoQueryDTO.billingSubject" />
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.billingSubject</s:param>
								</s:fielderror>
							</td>
							<td width="90" align=right>
								<span>发票状态：</span>
							</td>
							<td width="160">
								<dl:dictList dictType="101" tagType="2" defaultOption="true"
									displayName="orderInvoiceInfoQueryDTO.invoiceState"
									dictValue="${orderInvoiceInfoQueryDTO.invoiceState}"></dl:dictList>
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.invoiceState</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr>
							<td width="85" align=right>
								<span>销售日期：</span>
							</td>
							<td width="160">
								<s:textfield name="orderInvoiceInfoQueryDTO.saleDate"
									id="orderInvoiceInfoQueryDTO.saleDate" size="20" onfocus="dateClick(this)"
									cssClass="Wdate"/>
								<s:fielderror>
									<s:param>orderInvoiceInfoQueryDTO.saleDate</s:param>
								</s:fielderror>
							</td>
							<td align="center" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryForm.submit();" value="查 询" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">记录列表</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="listTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="orderInvoiceForm" name="orderInvoiceForm"
					action="orderInvoice/list.action" method="post">
					<ec:table items="invoiceInfos" var="map" width="100%"
						tableId="invoiceInfo" form="orderInvoiceForm"
						action="orderInvoice/list.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="orderInvoiceList.xls" tooltip="导出Excel" />
						<ec:row>
							<ec:column property="null" alias="invoiceIdList" title="选择"
								width="3%"  headerCell="selectAll">
								<input type="checkbox" name="invoiceIdList" value="${map.invoiceId},${map.invoiceState}" />
							</ec:column>
							<ec:column property="invoiceId" title="开票序号"  width="6%">
								<a href="javascript:viewInvoice('${map.invoiceId}');">${map.invoiceId}</a>
							</ec:column>
							<ec:column property="orderId" title="订单号"  width="5%">
								<a href="javascript:viewOrder('${map.orderId}','${map.orderType}');">${map.orderId}</a>
							</ec:column>
							<ec:column property="firstEntityName"  title="订单发起者" width="6%"/>
							<ec:column property="productName"  title="产品名称"  width="6%"/>
							<ec:column property="orderType"  title="订单类型" 
								cell="dictInfo" alias="205" width="6%" />
							<ec:column property="saleManName"  title="销售人" width="5%" />
							<ec:column property="customerName"  title="客户名称"  width="6%"/>

							<ec:column property="invoiceType"  title="发票类型" 
								cell="dictInfo" alias="182" width="6%" />
							<ec:column property="invoiceProj"  title="开票项目"   
								cell="dictInfo" alias="120" width="6%"/>

							<ec:column property="totalPrice"  title="订单总金额"  width="4%" />
							<ec:column property="invoiceAmount"  title="开票金额" width="4%" />
							<ec:column property="customerExpectedDate"  title="客户预计取票时间" 
								cell="date" format="yyyy-MM-dd" width="6%" />
							<ec:column property="createTime"  title="发票录入日期" cell="date"
								format="yyyy-MM-dd"  width="6%" />
							<ec:column property="billingSubject"  title="开票主体"  width="6%"/>
							<ec:column property="billingDate" title="开票日期"  cell="date"
								format="yyyy-MM-dd"  width="6%" />
							<ec:column property="saleDate" title="销售日期"  cell="date"
								format="yyyy-MM-dd"  width="6%" />
							<ec:column property="invoiceState" title="发票状态"     
								cell="dictInfo" alias="101" width="6%" />
								
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										 <td>
											<display:security urlId="60702">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="doInvoice();" value="开票" />
											</display:security>
										</td>
										<td>
											<display:security urlId="60702">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="editInvoice();" value="编辑" />
											</display:security>
										</td>
										<td>
											<display:security urlId="60704">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="deleteOrderInvoice();" value="取消" />
											</display:security>
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</body>
</html>