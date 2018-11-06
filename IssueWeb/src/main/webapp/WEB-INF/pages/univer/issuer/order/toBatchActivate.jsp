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
		<title>批量文件导入</title>
		<script type="text/javascript">
	 
		function  submitForm(){
			var flag = document.getElementById('batchActivateDTO.flag').value;
			if(flag == ''){
				errorDisplay("请选择操作类型！");
				return;
			}
			var startDateString = document.getElementById('path').value;
			if(startDateString == null || startDateString ==""){
				errorDisplay("请选择文件！");
				return;
			} 
			if(startDateString.substr(startDateString.length-4,startDateString.length)!='.csv'){
				errorDisplay("文件格式错误，请选择.csv格式文件！");
				return;
			}
			$("#button1").removeAttr("value");
			$("#button1").attr("value","处理中,请稍后！");
			$("#button1").removeAttr("onclick");
			$("#button1").removeAttr("class");
				queryForm.action="${ctx}/batchfile/batchActivate.action";
				document.queryForm.submit();
		}
		
		function backForm(){
			queryForm.action="${ctx}/batchfile/batchActivateListAction.action";
			document.queryForm.submit();
		}
		
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>批量文件导入</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="" method="post" enctype="multipart/form-data">
								
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
											<span style="color: red;">*</span>操作类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="batchActivateDTO.flag" id="batchActivateDTO.flag" list="#{'':'--请选择--','01':'首充','02':'激活','03':'再充值'}"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>文件路径：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:file name="batchFile" id="path" size="20"
												maxLength="400"   />
										</td>
										<td><span style="color: red;font: bold;">*导入文件的卡号第一位需要加N</span></td>
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
											 <input type="button" id="button1" class="bt" style="margin: 5px" onclick="submitForm();" value="批量导入"/></td>
											 </td>
											 <td align="right" colspan="3">
											 <input type="button"  class="bt" style="margin: 5px" onclick="backForm();" value="返回"/></td>
											
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>