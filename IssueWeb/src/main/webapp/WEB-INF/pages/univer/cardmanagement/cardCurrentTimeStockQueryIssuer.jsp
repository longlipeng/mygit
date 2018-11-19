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
		function submitForm(){
			var productName = document.getElementById('productName').value;
			if(productName == null || productName == ""){
				errorDisplay("请输入产品名称");
				return;
			}  
		
			document.queryForm.submit();		
		}
		</script>
	
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>卡实时库存查询</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="queryForm" name="queryForm"
								action="queryCardCurrentTimeStock/queryCardCurrentTimeStock.action?functionRoleId=2" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span style="color: red">*</span>产品名称:
													</td>
													<td>
														<s:textfield name="cardCurrentTimeStockQueryDTO.productName" id="productName"  size="23"
															maxLength="19" ></s:textfield>
														<s:fielderror>
															<s:param>cardCurrentTimeStockQueryDTO.productName</s:param>
														</s:fielderror>
													</td>
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
								
									<tr height="35">
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="submitForm();">
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"  style="cursor: pointer;">
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
							<div id="listTable"
								style="background-color: #FBFEFF; padding: 5px; border-style: solid none no ne; border-width: 1px; border-color: #B9B9B9;">
								<s:form id="EditForm" name="EditForm" action="queryCardCurrentTimeStock/queryCardCurrentTimeStock.action?functionRoleId=2"
										method="post">
									<s:hidden name="cardCurrentTimeStockQueryDTO.productName" />
									<ec:table items="pageDataDTO.data"  var="map"  width="100%" 
										tableId="cardCurrentTimeStockQueryTable"
										form="EditForm" 
										action="queryCardCurrentTimeStock/queryCardCurrentTimeStock.action?functionRoleId=2"
										imagePath="${ctx}/images/extremecomponents/*.gif"  
										view="html"
										retrieveRowsCallback="limit" 
										autoIncludeParameters="false">
										<ec:row>
											<ec:column property="productName" title="产品名称" width="20%"/>
											<ec:column property="onymousStat" title="署名类型"  width="20%"/>
											<ec:column property="productType" title="产品类别"  width="20%"/>
											<ec:column property="faceValueType" title="面额类型" width="10%"/>
											<ec:column property="faceValue" title="面额" width="15%" />
											<ec:column property="stockValue" title="实时库存量" width="15%" />										
										</ec:row>
									</ec:table>
								</s:form>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>