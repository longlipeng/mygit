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
			function query(){
				 var queryForm = Ext.get("queryForm").dom;
				 //TODO 2014-12-11 修改 报错
		 		 //queryForm['ec_eti'].disabled=true;
		 		 queryForm.action='${ctx}/stockCard/query.action';
		 		 queryForm.submit();
			}
			 function viewOrder(productId,role){
	    			window.open("${ctx}/stockCard/cardListView.action?closeFlag=1&productId=" + productId+"&role="+role,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	    	 }
		</script>
		<title>库存卡片管理</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存卡片管理</span>
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
					action="stockCard/query.action" method="post">
				 <s:hidden name="stockCardQueryDTO.functionRoleId"/>
				 <s:hidden name="type"></s:hidden>	
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											产品号：
										</td>
										<td>
											<s:textfield name="stockCardQueryDTO.productId" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											产品名称：
										</td>
										<td>
											<s:textfield name="stockCardQueryDTO.productName" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
                        <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											面额类型 ：
										</td>
										<td>
											<s:select 
                                            list="#{'0':'固定','1':'非固定'}" 
                                            name="stockCardQueryDTO.faceValueType" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											面额值：
										</td>
										<td>
											 <s:textfield name="stockCardQueryDTO.faceValue" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						 <tr>
							<td colspan="1">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											署名类型：
										</td>
										<td>
											<s:select 
                                            list="#{'1':'记名（个性化卡）','3':'记名（库存卡）','2':'不记名'}" 
                                            name="stockCardQueryDTO.onymousStat" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											卡片有效期：
										</td>
										<td>
											<input type="text" name="stockCardQueryDTO.cardValidDate"
												onclick="dateClick(this)" class="Wdate" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="query();" value="查 询" />
										</td>
									</tr>
								</table>
							</td>
							<td >
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
			  <s:form id="stockcardForm" name="stockcardForm" action="stockCard/query.action" method="post">
			        <s:hidden name="stockCardQueryDTO.functionRoleId"/>
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="stockcardForm"
						action="stockCard/query.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
							<ec:exportXls fileName="stockCardList.xls" tooltip="导出Excel" />
						<ec:row> 
							<ec:column property="productId" title="产品号" width="10%"  >
							<a href="javascript:viewOrder('${map.productId}','${map.functionRoleId}');">
										${map.productId}</a>
							</ec:column>
							<ec:column property="productName" title="产品名称" width="12%" />
							<%-- <ec:column property="faceValueType" title="面额类型" width="7%" />
							<ec:column property="faceValue" title="面额值（单位：元）" width="11%" />
							<ec:column property="faceLayoutName" title="卡面" width="10%" /> --%>
							<ec:column property="onymousStat" title="署名类型" width="9%" />
							<%-- <ec:column property="cardValidDate" title="卡片有效期" width="9%" format="yyyy-mm-dd" /> --%>
							<ec:column property="stockNumber" title="库存数量" width="10%" />
							<ec:column property="totalAmount" title="总金额（单位：元）" width="12%" />
						</ec:row>
					</ec:table>
				</s:form>	
			</div>
		</div>
	</body>
</html>