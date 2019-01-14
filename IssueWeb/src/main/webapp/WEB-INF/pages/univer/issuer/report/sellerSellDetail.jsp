<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>营销机构管理</title>
		<script type="text/javascript">
		
    function choiceSeller() {
   	  var sellerDTO = window.showModalDialog('${ctx}/issuerSeller/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
         if (sellerDTO != null) {
             document.getElementById('entityId').value = sellerDTO['entityId'];
             document.getElementById('sellerName').value = sellerDTO['sellerName'];
         }
    }
	var type=0;
	 function choiceOrderId() {
	 var id=document.getElementById('entityId').value;
		var sellerDTO = window.showModalDialog('${ctx}/ireport/orderChoose.action?sellerQueryDTO.entityId='+id,
				'_blank',
				'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		if (sellerDTO != null) {
		var order=sellerDTO.split(',');
			document.getElementById('orderId').value = order[0];
			type=order[1];
		}
	}
	function submitForm(){
		
		var tp_querytype=$('#queryType').val();
		if (tp_querytype=='sell'){
			var sell_id=$('#entityId').val();
			if(sell_id==null||sell_id==''){
				errorDisplay("请选择营销机构");	
				return false;
			}
		}
		
		var startDateString = document.getElementById('startDate').value;
		if(startDateString == null || startDateString ==""){
			errorDisplay("开始日期必须选择");
			return;
		} 
		var endDateString = document.getElementById('endDate').value;
		if(endDateString == null || endDateString == ""){
			errorDisplay("结束日期必须选择");
			return;
		} 
		var startDate=new Date(startDateString.replace(/-/g, '/'));
		var endDate= new Date(endDateString.replace(/-/g, '/ '));
		if(startDate > endDate){
			errorDisplay("开始日期不能大于结束日期");
			return;
		}
		document.queryForm.submit();
		document.getElementById('dowBt').value="下 载 中...";
        document.getElementById('dowBt').disabled=true;
		setTimeout(function (){
			document.getElementById('dowBt').disabled=false;
			document.getElementById('dowBt').value="下 载 报 表";
        },10000);
		
	}

	 function change(){
			var flag=document.getElementById("queryType").value;
			if(flag=='sell'){
				document.getElementById("sellerIssuerTr").style.visibility="";
				document.getElementById("sellerIssuerTr").style.height="30px";
			}else{
				document.getElementById("sellerIssuerTr").style.visibility="hidden";
				document.getElementById("sellerIssuerTr").style.height="0px";
		
			}
		}
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>营销运营明细表</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="/ireport/querySellerSellDetail!viewReport.action" method="post">
			<s:hidden name="sellerSellDetailDTO.reportName" id="name"></s:hidden>
			<s:hidden name="sellerSellDetailDTO.reportType"></s:hidden>
			<s:hidden name="sellerSellDetailDTO.issuerId"></s:hidden>
			<s:hidden name="sellerSellDetailDTO.issuerName"></s:hidden>
			<s:hidden name="sellerSellDetailDTO.reportFileName"></s:hidden>
			<div id="base"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="baseTitle"
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
				<div id="baseTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="80%" style="table-layout: fixed; text-align: center">
						<tr>
							<td>
								 <table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<!-- <span style="color: red">*</span>查询方式： -->
										</td>
										<td align="left">
											<%-- <s:select name="sellerSellDetailDTO.queryType" id="queryType"  onchange="change()"
												list="#{'sell':'营销机构','issue':'发行机构'}" >
											</s:select> --%>
											<s:hidden name="sellerSellDetailDTO.queryType" id="queryType" value="sell"></s:hidden>
											<s:hidden name="issuerStockSumDTO.productName" id="productName"></s:hidden>
										</td>
									</tr>
								</table> 
						
							</td>
						</tr>
						<tr id="sellerIssuerTr">
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											营销机构编号：
										</td>
										<td align="left">
											<s:textfield id="entityId" name="sellerSellDetailDTO.sellerId"
												cssClass="watch" readonly="true" onclick="choiceSeller()" />
											<s:fielderror>
												<s:param>sellerSellDetailDTO.sellerId</s:param>
											</s:fielderror>
											<!-- <input type="hidden" name="Id" value="entityId"/> -->
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											机构名称：
										</td>
										<td>
											<s:textfield id="sellerName" name="sellerSellDetailDTO.sellerName"
												readonly="true" cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span style="color: red">*</span>开始时间：
										</td>
										<td align="left">
											<s:textfield name="sellerSellDetailDTO.startDate" id="startDate"
												size="20" onfocus="dateClick(this)" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>dto.startDate</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span style="color: red">*</span>结束时间：
										</td>
										<td align="left">
											<s:textfield name="sellerSellDetailDTO.endDate" id="endDate"
												size="20" onfocus="dateClick(this)" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>dto.endDate</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>

					</table>
				</div>
			</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="90%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr height="35">

									<td align="right" colspan="3">
										<input type="button" class="bt" id="dowBt" style="margin: 5px"
											onclick="submitForm();" value="下 载 报 表" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>