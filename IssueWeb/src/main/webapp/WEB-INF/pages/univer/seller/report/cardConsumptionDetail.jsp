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
		<title>卡消费明细表</title>
		<script type="text/javascript">
			function submitForm(){
				var flag=document.getElementById("selectFlag").value;
				var dd = new Date();
		    	var y = dd.getFullYear();
		    	var m = dd.getMonth()+1;
		    	var d = dd.getDate();
		    	if(m < 10) m = "0" + m;
		    	if(d < 10) d = "0" + d;
		    	var today = y + "-" + m + "-" + d;
				if(flag==1){
					var startDateString = document.getElementById('startDate').value;
					if(startDateString == null || startDateString == ""){
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
					if(startDate >= new Date(y,m-1,d) || endDate >= new Date(y,m-1,d)) {
						errorDisplay("请选择历史时间段");
						return;
					}
					if(startDate > endDate){
						errorDisplay("开始日期不能大于结束日期");
						return;
					}
				}
				else {
					document.getElementById('startDate').value=today;
					document.getElementById('endDate').value=today;
				}
				document.queryForm.submit();
			}
			
			function change(){
				var flag=document.getElementById("selectFlag").value;
				if(flag==0){
					document.getElementById("hisStartTime").style.visibility="hidden";
					document.getElementById("hisStopTime").style.visibility="hidden";
				}else{
					document.getElementById('startDate').value="";
					document.getElementById('endDate').value="";
					document.getElementById("hisStartTime").style.visibility="";
					document.getElementById("hisStopTime").style.visibility="";
			
				}
			}
		</script>
	</head>
	<body onload="change()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡消费明细表</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="/ireport/queryCardConsumptionDetail!viewReport.action" method="post">
            <s:hidden name="cardConsumptionDetailDTO.reportName"></s:hidden>
			<s:hidden name="cardConsumptionDetailDTO.reportType"></s:hidden>
			<s:hidden name="cardConsumptionDetailDTO.issuerId"></s:hidden>
			<s:hidden name="cardConsumptionDetailDTO.issuerName"></s:hidden>
			<s:hidden name="cardConsumptionDetailDTO.reportFileName"></s:hidden>
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
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 150px; text-align: right;">
											查询当天/历史记录：
										</td>
										<td>
											<s:select list="#{0:'当天',1:'历史'}"
												name="cardConsumptionDetailDTO.cardFlag" id="selectFlag" onchange="change()" />
										</td>
										</tr>
								</table>
							</td>
							<td id="hisStartTime">
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 140px; text-align: right;">
											<span style="color: red">*</span>开始时间：
										</td>
										<td>
											<s:textfield name="cardConsumptionDetailDTO.startDate" id="startDate"
												 size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
										</td>
										</tr>
								</table>
							</td>
							
							<td  id="hisStopTime">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 140px; text-align: right;">
											<span style="color: red">*</span>结束时间：
										</td>
										<td>
											<s:textfield name="cardConsumptionDetailDTO.endDate" id="endDate"
												 size="20" onfocus="dateClick(this)" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>cardConsumptionDetailDTO.endDate</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span>商户编码：</span>
										</td>
										<td align="left">
											<s:textfield name="cardConsumptionDetailDTO.merchantNo" id="merchantNo"
												 size="20" >
											</s:textfield>
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