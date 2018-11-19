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
		<title>客户与持卡人批量录入</title>
		<script type="text/javascript">
	 
		function  submitForm(state){
			var startDateString = document.getElementById('path').value;
			if(startDateString == null || startDateString ==""){
				errorDisplay("请选择文件！");
				return;
			} 
			if(startDateString.substr(startDateString.length-4,startDateString.length)!='.xls'){
				errorDisplay("文件格式错误，请选择.xls格式文件！");
				
				return;
			}
			var countNumString = document.getElementById('countNum').value;
			if(countNumString == null || countNumString==""){
				errorDisplay("请输入要导入的记录数！");
				return;
			}
			if(state=="1"){
				$("#button1").removeAttr("value");
				$("#button1").attr("value","处理中,请稍后！");
				$("#button1").removeAttr("class");
				$("#button1").removeAttr("onclick");
				queryForm.action="customer/batchImportCardHolderAndCustomer.action";
				document.queryForm.submit();
				messageDisplay('提示','正在导入！请稍后……','clickspan','200');
			}else if(state=="2"){
				$("#button2").removeAttr("value");
				$("#button2").removeAttr("class");
				$("#button2").attr("value","处理中,请稍后！");
				$("#button2").removeAttr("onclick");
				queryForm.action="customer/batchImportIssue.action";
				document.queryForm.submit();
				messageDisplay('提示','正在导入！请稍后……','clickspan','200');
			}else if(state=="3"){
				$("#button3").removeAttr("value");
				$("#button3").removeAttr("class");
				$("#button3").attr("value","处理中,请稍后！");
				$("#button3").removeAttr("onclick");
				queryForm.action="customer/batchCardImportIssue.action";
				document.queryForm.submit();
				messageDisplay('提示','正在导入！请稍后……','clickspan','200');
			}
				
			}
		
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>客户与持卡人批量录入</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="customer/batchImportCardHolderAndCustomer.action" method="post" enctype="multipart/form-data">
								
			<div id="base"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="baseTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">选择文件</span>
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
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>记录数：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="batchCardHolderMessageDTO.countNum" id="countNum" maxlength="50"/>
										</td>
									</tr>
								
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>文件路径：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<%-- <s:file name="uploadCardFileDTO.filepath" id="path" size="20"
												maxLength="400"   onfocus="browseFolder('path')" /> --%>
												<s:file name="batchCardHolderMessageDTO.batchFile" id="path" size="20"
												maxLength="400"   />
											<s:fielderror>
												<s:param>
													batchCardHolderMessageDTO.batchFile
												</s:param>
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
											 <input type="button" id="button1"  class="bt" style="margin: 5px" onclick="submitForm('1');" value="客户持卡人批量导入"/></td>
										<td align="right" colspan="3">
											 <input type="button" id="button2" class="bt" style="margin: 5px" onclick="submitForm('2');" value="售卡自动完成"/></td>
										<td align="right" colspan="3">
											 <input type="button" id="button3" class="bt" style="margin: 5px" onclick="submitForm('3');" value="选择卡号开卡"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>