<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		
		<title>库存调拨管理</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存调拨管理</span>
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
					action="stockAllocateAction!list" method="post">
				 <s:hidden name="type"></s:hidden>	
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											调拨号：
										</td>
										<td>
											<s:textfield name="stockAllocateDTO.allocateId" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											调出机构：
										</td>
										<td>
											<s:textfield name="stockAllocateDTO.allocateOut" />
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
											调入机构 ：
										</td>
										<td>
											<s:textfield name="stockAllocateDTO.allocateIn" />
										</td>										
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											调拨人员：
										</td>
										<td>
											 <s:textfield name="stockAllocateDTO.allocateUserName" />
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
											开始日期：
										</td>
										<td>
												<s:textfield name="stockAllocateDTO.startDate"
														 size="20" onfocus="dateClick(this)"
														cssClass="Wdate">
														</s:textfield>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											结束日期：
										</td>
										<td>
											<s:textfield name="stockAllocateDTO.endDate"
														 size="20" onfocus="dateClick(this)"
														cssClass="Wdate">
														</s:textfield>
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
												onclick="queryForm.submit();" value="查 询" />
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
			  <s:form id="stockAllocateForm" name="stockAllocateForm" action="stockAllocateAction!list" method="post">
			         
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="stockAllocateForm"
						action="stockAllocateAction!list"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
							<ec:exportXls fileName="stockAllocateList.xls" tooltip="导出Excel" />
						<ec:row> 							
							<ec:column property="allocateId" title="调拨号" width="10%" >
								<a href="stockAllocateAction!view.action?stockAllocateDTO.allocateId=${map.allocateId}&stockAllocateDTO.allocateOut=${map.allocateOut}&stockAllocateDTO.allocateIn=${map.allocateIn}&stockAllocateDTO.allocateCount=${map.allocateCount}&stockAllocateDTO.allocateUserName=${map.allocateUserName}&stockAllocateDTO.allocateDate=${map.allocateTime}">
                                <s:property value="#attr['map'].allocateId" />
                            </a>
							</ec:column>
							<ec:column property="allocateOut" title="调出机构" width="10%" />
							<ec:column property="allocateIn" title="调入机构" width="10%" />
							<ec:column property="allocateCount" title="调拨数量（单位：张）" width="10%" />
							<ec:column property="allocateUserName" title="调拨人员" width="10%" />
							<ec:column property="allocateTime" title="调拨时间" width="9%" />
						</ec:row>
					</ec:table>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										
											<td>
<!--													<display:security urlId="60601">-->
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
													onclick="submitForm('stockAllocateForm','${ctx}/stockAllocateAction!toAdd.action','add','');"
													value="添加" />
<!--													</display:security>-->
											</td>										  
											<td>
<!--													<display:security urlId="60604">-->
<!--												<input type="button" class="btn"-->
<!--													style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"-->
<!--													onclick="submitForm('ruleForm', '${ctx}/settleRule/delete.action', 'delete', 'feeRuleIdList')"-->
<!--													value="删除" />-->
<!--													</display:security>-->
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