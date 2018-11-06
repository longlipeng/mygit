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
		<title>零元购电子卡</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>零元购电子卡界面</span>
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
					action="queryVirtualCard.action" method="post">
					<s:hidden name="type"></s:hidden>
					<table width="100%" style="table-layout: fixed;">
						<tr height="35">
							<td width="85" align=right>
								<span>卡号：</span>
							</td>
							<td width="160">
								<s:textfield name="virtualCardQueryDto.cardNo" />
								<s:fielderror>
									<s:param>virtualCardQueryDto.cardNo</s:param>
								</s:fielderror>
							</td>
							<td width="85" align=right>
								<span>券号：</span>
							</td>
							<td width="160">
								<s:textfield name="virtualCardQueryDto.couponNo" />
								<s:fielderror>
									<s:param>virtualCardQueryDto.couponNo</s:param>
								</s:fielderror>
							</td>
							<td width="85" align=right>
								<span>卡状态：</span>
							</td>
							<td width="160">
								<s:select list="#{'2':'已使用','1':'内存中','0':'初始状态'}"
												name="virtualCardQueryDto.status" emptyOption="false"
												label="卡状态" headerKey="" headerValue="--请选择--" />
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
					action="queryVirtualCard.action" method="post">
					<s:hidden name="virtualCardQueryDto.cardNo"></s:hidden>
					<s:hidden name="virtualCardQueryDto.couponNo"></s:hidden>
					<s:hidden name="virtualCardQueryDto.status"></s:hidden>
					<ec:table items="virtualCardList" var="map" width="100%"
						tableId="virtual" form="orderInvoiceForm"
						action="queryVirtualCard.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:row>
								<ec:column property="productName" title="产品名称" width="10%" />
								<ec:column property="cardNo" title="卡号" width="20%"/>
								<ec:column property="couponNo" title="券号" width="20%"/>
								<ec:column property="availableBalance" title="余额(单位:分)" width="10%"/>
								<ec:column property="status" title="状态" width="10%">
								<c:if test="${map.status == '0'}">卡初始状态</c:if>
								<c:if test="${map.status == '1'}">内存中</c:if>
								<c:if test="${map.status == '2'}">已使用</c:if>
								</ec:column>
								<ec:column property="createdTime" title="生成时间" cell="date" format="yyyy-MM-dd hh:mm:ss" width="20%"/>			
						</ec:row>
					</ec:table>
					
				</s:form>
			</div>
		
		</div>
	</body>
</html>