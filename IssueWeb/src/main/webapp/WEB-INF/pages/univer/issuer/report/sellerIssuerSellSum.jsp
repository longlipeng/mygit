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
		<title>营销机构报表管理</title>
		<script type="text/javascript">
	  function choiceIssuer() {
    	var sellerDTO=window.showModalDialog('${ctx}/ireport/queryChooseIssuerAction.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
         if (sellerDTO != null) {
        	 var string=sellerDTO.split(",");
            document.getElementById('issuerId').value = string[0];
            document.getElementById('issuerName').value = string[1];
        }
    }

function checkDate() {
		var end = "";
		var start = "";
		var enddate = document.getElementById('endDate').value;
		var enddates = enddate.split('-');
		for ( var i = 0; i < enddates.length; i++) {
			end += enddates[i];
		}
		var startdate = document.getElementById('startDate').value;
		var startdates = startdate.split('-');
		for ( var i = 0; i < startdates.length; i++) {
			start += startdates[i];
		}
		if (end < start) {
			alert("结束日期不能小于开始日期!");
			document.getElementById('endDate').value = startdate;
		}
	}
	

	
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发行运营汇总表</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="/ireport/report" method="post">
				                <s:hidden name="ireportDTO.reportName"></s:hidden>
								<s:hidden name="ireportDTO.reportType"></s:hidden>
								<s:hidden name="ireportDTO.issuerId"></s:hidden>
								<s:hidden name="ireportDTO.issuerName"></s:hidden>
								<s:hidden name="ireportDTO.reportFileName"></s:hidden>
			<div id="base"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="baseTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">查询条件</span>
							</td>
							<td class="TableTitleEnd">&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div id="baseTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="80%" style="table-layout: fixed; text-align:center">
						<tr>
							<td>
								<table style="text-align: right; width: 55%">
									<tr>
										<td style="width: 140px; text-align: right;">
											<span style="color: red">*</span>发行机构号：
										</td>
										<td>
											<s:textfield 
												name="ireportDTO.parValue" cssClass="watch" id="issuerId"
												readonly="true" onclick="choiceIssuer()" />
											<s:fielderror>
												<s:param>dto.sellerId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 45%">
									<tr>
										<td style="width: 140px; text-align: right;">
											发行机构名称：
										</td>
										<td>
											<s:textfield
												name="ireportDTO.parValue" readonly="true" id="issuerName"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td>
								<table style="text-align: right; width: 55%">
									<tr>
										<td style="width: 140px; text-align: right;">
											开始时间：
										</td>
										<td>
											<s:textfield name="ireportDTO.parValue" id="startDate"
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
								<table style="text-align: left; width: 45%">
									<tr>
										<td style="width: 140px; text-align: right;">
											结束时间：
										</td>
										<td>
											<s:textfield name="ireportDTO.parValue" id="endDate"
												 size="20" onfocus="dateClick(this)" onchange="checkDate();"
												cssClass="Wdate">
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
											 <input type="button"  class="bt" style="margin: 5px" onclick="queryForm.submit();;" value="下 载 报 表"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>