<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>磁道信息重写管理</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>磁道信息重写管理</span>
		</div>
<div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
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
			<div id="queryTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="queryForm" name="queryForm"
					action="magentismInquery.action?op=2" method="post">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											卡号：
										</td>
										<td>
											<s:textfield name="cardManagementQueryDTO.cardNo" size="23"/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											开始时间：
										</td>
										<td>
											
											<input type="text" name="cardManagementQueryDTO.starDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='cardManagementQueryDTO.starDate' format='yyyy-MM-dd'/>"/>
											
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											结束时间：
										</td>
										<td>
											<input type="text" name="cardManagementQueryDTO.enDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='cardManagementQueryDTO.enDate' format='yyyy-MM-dd'/>"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="3">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryForm.submit();" value="查 询" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px; ">
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
				<s:form id="cardholderForm" name="cardholderForm"
					action="magentismInquery.action?op=2" method="post">
					<s:hidden name="cardManagementQueryDTO.cardNo"></s:hidden>
					<input type="hidden" name="cardManagementQueryDTO.starDate"
					value="<s:date name="cardManagementQueryDTO.starDate" format="yyyy-MM-dd"/>"/>
					<input type="hidden" name="cardManagementQueryDTO.enDate"
					value="<s:date name="cardManagementQueryDTO.enDate" format="yyyy-MM-dd"/>"/>
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="cardholderForm"
						action="${ctx}/magentismInquery.action?op=2"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="cardMagnetismList.xls" tooltip="导出Excel"/>
						<ec:row>
							
							<ec:column property="cardNo" title="卡号" width="15%"  escapeAutoFormat="true">

							</ec:column>
							<ec:column property="cardType" title="卡类型" width="15%">
								
							</ec:column>
							<ec:column property="cardholderName" title="持卡人" width="15%" />
							<ec:column property="serviceFee" title="手续费用" width="10%" cell="amt"/>
							<ec:column property="modifyTime" title="操作日期" width="15%"  format='yyyy-MM-dd' cell="date"/>
								
							<ec:column property="userName" title="操作人" width="15%" />
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 80px; height: 20px; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 2px 10px; text-align: right"
												onclick="if(cardholderForm['ec_eti']!=null){cardholderForm['ec_eti'].disabled=true;}cardholderForm.action='cardManagement!insertInit.action?op=2';cardholderForm.submit()"
												value="卡磁道重写" />
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</body>
</html>