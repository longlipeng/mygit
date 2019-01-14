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
		<title>与商户的结算单明细表</title>
		<script type="text/javascript">
	  function choiceSeller() {
    	var sellerDTO=window.showModalDialog('${ctx}/ireport/merchantChoose.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
         if (sellerDTO != null) {
        	 var string=sellerDTO.split(",");
            document.getElementById('merchantCode').value = string[0];
            document.getElementById('merchantName').value = string[1];
        }
    }
    
		function submitForm(){
    	var issuerId = document.getElementById('merchantCode').value;
		if(issuerId== null || issuerId==""){
			errorDisplay("商户必须选择");
			return;
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
	//	alert(currentDate);
		//if(!startDate< currentDate){
			//errorDisplay("开始日期必须早于当前日期");
		//}
		if(startDate > endDate){
			errorDisplay("开始日期不能大于结束日期");
			return;
		}
		document.queryForm.submit();
    }
	
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>与商户的结算单明细表</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="/ireport/queryCalculateWithMerchantDetail!viewReport.action" method="post">
				                <s:hidden name="calculateWithMerchantDetailDTO.reportName"></s:hidden>
								<s:hidden name="calculateWithMerchantDetailDTO.reportType"></s:hidden>
								<s:hidden name="calculateWithMerchantDetailDTO.issuerId"></s:hidden>
								<s:hidden name="calculateWithMerchantDetailDTO.issuerName"></s:hidden>
								<s:hidden name="calculateWithMerchantDetailDTO.reportFileName"></s:hidden>
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
					<table width="80%" style="table-layout: fixed; text-align:center">
						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span style="color: red">*</span>商户号：
										</td>
										<td align="left">
											<s:textfield 
												name="calculateWithMerchantDetailDTO.merchantCode" cssClass="watch" id="merchantCode"
												readonly="true" onclick="choiceSeller()" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											商户名称：
										</td>
										<td>
											<s:textfield
												name="calculateWithMerchantDetailDTO.merchantName" readonly="true" id="merchantName"
												cssClass="readonly" />
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
											开始时间：
										</td>
										<td align="left">
											<s:textfield name="calculateWithMerchantDetailDTO.startDate" id="startDate"
												 size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
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
											结束时间：
										</td>
										<td>
											<s:textfield name="calculateWithMerchantDetailDTO.endDate" id="endDate"
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
											 <input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="下 载 报 表"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>