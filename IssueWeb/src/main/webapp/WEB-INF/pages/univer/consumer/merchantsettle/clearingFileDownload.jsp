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
		<title>清结算文件下载</title>
		<script type="text/javascript">
	 
		function  submitForm(){
			var startDateString = document.getElementById('startDate').value;
			if(startDateString == null || startDateString ==""){
				errorDisplay("日期必须选择");
				return;
			} 
			document.queryForm.submit();
		}
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>清结算文件下载</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="clearingFileDownloaAction!downfile" method="post">
<%-- 				                <s:hidden name="fundSubsideSumDTO.reportName"></s:hidden> --%>
<%-- 								<s:hidden name="fundSubsideSumDTO.reportType"></s:hidden> --%>
<%-- 								<s:hidden name="fundSubsideSumDTO.issuerId"></s:hidden> --%>
<%-- 								<s:hidden name="fundSubsideSumDTO.issuerName"></s:hidden> --%>
<%-- 								<s:hidden name="fundSubsideSumDTO.reportFileName"></s:hidden> --%>
								
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
											清算时间：
										</td>
										<td>
											<s:textfield name="applyAndBindCardDTO.startDate" id="startDate"
												 size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>applyAndBindCardDTO.startDate</s:param>
											</s:fielderror>
										</td>
										<td> </td>
										<td> </td>
										<s:hidden name="applyAndBindCardDTO.isCheXiang" value="0"/>
										<%-- <td style="width: 140px; text-align: right;">
											车享文件：
										</td>
										<td>
											<s:select list="#{0:'否',1:'是' }"  name="applyAndBindCardDTO.isCheXiang"></s:select>
										</td> --%>
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