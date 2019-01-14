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
	   function invoiceMach(settleMentId){
		   window.location.href="${ctx}/settlement/toMachingInvoice.action?settleMentId="+settleMentId;
	   }
	   function openInvocieSettleView(settleMentId) {
			var returnValue = window.showModalDialog('${ctx}/settlement/settleInvoiceView.action?settleMentId='+settleMentId+'',
					'_blank',
					'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
			if(undefined == returnValue || "1" == returnValue) {	
				queryForm.submit();
			}
		}

		function sendAllToSOP(){
			var queryForm=Ext.get('queryForm').dom;
			queryForm.action='${ctx}/settlement/sendAllToSOP.action';
			queryForm.submit();
			queryForm.action='queryCouponSettlement.action';
		}
	   
		function checkedToSOP(){
			var checkboxs=document.getElementsByName('settlementId');
			var count=0;
			for(var i=0;i<checkboxs.length;i++){
				if(checkboxs[i].checked){
					count++;
				}
			}
			if(count<=0){
				alert('至少选择一条记录');
				return false;
			}
			var settlementForm=Ext.get('orderInvoiceForm').dom;
			settlementForm.action='${ctx}/settlement/sendCheckedToSOP.action';
			settlementForm.submit();
		}

		function invoiceAllToSOP(){
			var queryForm=Ext.get('queryForm').dom;
			queryForm.action='${ctx}/settlement/sendInvoiceAllToSOP.action';
			queryForm.submit();
			queryForm.action='queryCouponSettlement.action';
		}
		
		function checkedInvoiceToSOP(){
			var checkboxs=document.getElementsByName('settlementId');
			var count=0;
			for(var i=0;i<checkboxs.length;i++){
				if(checkboxs[i].checked){
					count++;
				}
			}
			if(count<=0){
				alert('至少选择一条记录');
				return false;
			}
			var settlementForm=Ext.get('orderInvoiceForm').dom;
			settlementForm.action='${ctx}/settlement/sendCheckedInvoiceToSOP.action';
			settlementForm.submit();
			settlementForm.action='queryCouponSettlement.action';
		}
		</script>
		<title>结算单页面</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算单页面</span>
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
					action="queryCouponSettlement.action" method="post">
					<s:hidden name="type"></s:hidden>
					<table width="100%" style="table-layout: fixed;">
						<tr height="35">
							<td width="85" align=right>
								<span>商户号：</span>
							</td>
							<td width="160">
								<s:textfield name="settlementQueryDto.mchtCode" />
								<s:fielderror>
									<s:param>settlementQueryDto.mchtCode</s:param>
								</s:fielderror>
							</td>
							<td width="85" align=right>
								<span>结算单发送状态：</span>
							</td>
							<td width="160">
								<s:select list="#{'0':'未发送','1':'已发送'}"
												name="settlementQueryDto.sendStatus" emptyOption="false"
												label="发送状态" headerKey="" headerValue="--请选择--" />
							</td>
							<td width="85" align=right>
								<span>开票发送状态：</span>
							</td>
							<td width="160">
								<s:select list="#{'0':'未发送','1':'已发送'}"
												name="settlementQueryDto.invoiceSendStatus" emptyOption="false"
												label="发送状态" headerKey="" headerValue="--请选择--" />
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
							
							<td align="center" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="sendAllToSOP();" value="发送查询结算单到SOP" />
										</td>
									</tr>
								</table>
							</td>
							
							<td align="center" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="invoiceAllToSOP();" value="发送结算单发票到SOP" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
							
					</table>
				</s:form>
			</div>
		</div>
		<s:form>
		
			
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="90%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr height="35">
									    <td align="right" colspan="3">
											 <input type="button" class="bt" style="margin: 5px" onclick="checkedToSOP();" value="发送选中结算单到开放平台"/></td>
											 <td><input type="button" class="bt" style="margin: 5px" onclick="checkedInvoiceToSOP();" value="发送选中开票到开放平台"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			
		</s:form>
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
					action="queryCouponSettlement.action" method="post">
					<s:hidden name="settlementQueryDto.mchtCode"></s:hidden>
					<ec:table items="settlementList" var="map" width="100%"
						tableId="settlement" form="orderInvoiceForm"
						action="queryCouponSettlement.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="couponSettlement.xls" tooltip="导出Excel" />
						<ec:row>
								<ec:column property="null" alias="settlementId" title="选择"
									width="3%" sortable="false" headerCell="selectAll">
									<input type="checkbox" name="settlementId"
								value="${map.id}" />
								</ec:column>
						        <input type="hidden" name="id" value="${map.id}" />
								<ec:column property="settleNo" title="结算单号" width="10%">
								<a href="javascript:openInvocieSettleView('${map.id}');">${map.settleNo}</a>
								</ec:column>
								<ec:column property="settleBatchId" title="结算批次" width="3%" />
								<ec:column property="mchtCode" title="商户号" width="10%">
									
								</ec:column>
								<ec:column property="mchtName" title="商户号称" width="10%">
					
								</ec:column>
								<ec:column property="totalAmount" title="金额(单位:分)" width="10%"/>
								<ec:column property="commissionAmount" title="手续费(单位:分)" width="5%"/>
								<ec:column property="receivedInvoinceAmount" title="已收票金额(单位:分)" width="5%"/>	
								<ec:column property="waitIvcAmount" title="待收票金额(单位:分)" width="5%"/>		
								<ec:column property="beginDate" title="开始日期" cell="date" format="yyyy-MM-dd" width="6%"/>	
								<ec:column property="endDate" title="结束日期" cell="date" format="yyyy-MM-dd" width="6%"/>	
								<ec:column property="createdTime" title="创建时间" cell="date" format="yyyy-MM-dd" width="6%"/>	
								<ec:column property="updatedTime" title="更新时间" cell="date" format="yyyy-MM-dd" width="6%"/>	
								<ec:column property="null" title="结算单发送"  width="5%">
									<c:if test="${map.sendStatus=='0'}">未发送</c:if>
									<c:if test="${map.sendStatus=='1'}">已发送</c:if>
								</ec:column>
								<ec:column property="null" title="开票发送"  width="3%">
									<c:if test="${map.invoiceSendStatus=='0'}">未发送</c:if>
									<c:if test="${map.invoiceSendStatus=='1'}">已发送</c:if>
								</ec:column>	
								<ec:column property="null" title="开票按钮" width="16%" sortable="false">
								<c:if test="${map.status =='0'}">
								<c:if test="${map.waitIvcAmount >='0'}">
								
								<input type="button" value="开票" class="btn"
											style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
								onclick="invoiceMach(${map.id})" />
								</c:if>
								</c:if>
								</ec:column>
						</ec:row>
					</ec:table>
					
				</s:form>
			</div>
		
		</div>
	</body>
</html>