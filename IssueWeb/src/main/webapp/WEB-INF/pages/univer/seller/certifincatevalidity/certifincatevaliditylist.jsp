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
		 		 queryForm.action='${ctx}/certifincatevalidity/inquery.action';
		 		 queryForm.submit();
			}

		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>证件有效期管理</span>
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
					action="certifincatevalidity/inquery.action" method="post">
				 <s:hidden name="type"></s:hidden>	
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											姓名：
										</td>
										<td>
											<s:textfield name="dto.name" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											证件号：
										</td>
										<td>
											<s:textfield name="dto.idNo" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
                        <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											到期时间：
										</td>
										<td>
											<input type="text" name="dto.validityDate"
												onclick="dateClick(this)" class="Wdate" value="${dto.validityDate }"/>
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
			  <s:form id="stockcardForm" name="stockcardForm" action="certifincatevalidity/inquery.action" method="post">
					<s:hidden name="dto.name"/>
					<s:hidden name="dto.idNo"/>
					<s:hidden name="dto.validityDate"/>
					
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="stockcardForm"
						action="certifincatevalidity/inquery.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls  fileName="certifincatevalidityList.xls" tooltip="导出Excel" />
						<ec:row> 
							<ec:column property="cusName" title="姓名" width="15%"/>
							<ec:column property="idNo" title="证件号" width="20%" escapeAutoFormat="true"/>
							<ec:column property="phoneNuber" title="手机号" width="15%" escapeAutoFormat="true"/>
							<ec:column property="cusType" title="客户类型" width="15%"/>
							<ec:column property="type" title="状态" width="5%"/>
							<ec:column property="validityDate" title="到期时间" width="15%" />
						</ec:row>
					</ec:table>
				</s:form>	
			</div>
		</div>
	</body>
</html>