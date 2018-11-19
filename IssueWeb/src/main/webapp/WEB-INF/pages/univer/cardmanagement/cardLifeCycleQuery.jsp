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
			var cardId = document.getElementById('cardNo').value;
			if(cardId == null || cardId == ""){
				errorDisplay("请输入卡号");
				return;
			} 
			var startDateString = document.getElementById('startDate').value;
			var endDateString = document.getElementById('endDate').value;
			if((startDateString != null || startDateString !="") && (endDateString != null || endDateString != "")){
				var startDate=new Date(startDateString.replace(/-/g, '/'));
				var endDate= new Date(endDateString.replace(/-/g, '/ '));
	
				if(startDate > endDate){
					errorDisplay("开始日期不能大于结束日期");
					return;
				}
			} 
		
			document.queryForm.submit();		
		}
		</script>
	
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>卡生命周期查询</span>
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
								action="queryCardLifeCycle/queryCardLifeCycle.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span style="color: red">*</span>卡号:
													</td>
													<td>
														<s:textfield name="cardLifeCycleQueryDTO.cardNo" id="cardNo"  size="23"
															maxLength="19" ></s:textfield>
														<s:fielderror>
															<s:param>cardLifeCycleQueryDTO.cardNo</s:param>
														</s:fielderror>
													</td>
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
								
									<tr height="35">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span>开始时间：</span>
													</td>
													<td>
														<input type="text" name="cardLifeCycleQueryDTO.startDate" id="startDate"
															onfocus="dateClick(this)" class="Wdate"
															value="${cardLifeCycleQueryDTO.startDate}"/>
														<s:fielderror>
															<s:param>
																cardLifeCycleQueryDTO.startDate
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span>结束时间：</span>
													</td>
													<td>
														<input type="text" name="cardLifeCycleQueryDTO.endDate" id="endDate"
															onfocus="dateClick(this)" class="Wdate"
															value="${cardLifeCycleQueryDTO.endDate}" />
														<s:fielderror>
															<s:param>
																cardLifeCycleQueryDTO.endDate
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
					
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
								<s:form id="EditForm" name="EditForm" action="queryCardLifeCycle/queryCardLifeCycle.action"
										method="post">
									<s:hidden name="cardLifeCycleQueryDTO.cardNo" />
									<s:hidden name="cardLifeCycleQueryDTO.startDate" />
									<s:hidden name="cardLifeCycleQueryDTO.endDate" />
									<ec:table items="pageDataDTO.data"  var="map"  width="100%" 
										tableId="cardCycleQueryTable"
										form="EditForm" 
										action="queryCardLifeCycle/queryCardLifeCycle.action"
										imagePath="${ctx}/images/extremecomponents/*.gif"  
										view="html"
										retrieveRowsCallback="limit" 
										autoIncludeParameters="false">
										<ec:row>
											<ec:column property="cardNo" title="卡号" width="20%"/>
											<ec:column property="accountNo" title="账号"  width="20%"/>
											<ec:column property="txnDate" title="日期"  width="20%" cell="date" format="yyyy-MM-dd HH:mm:ss"  />
											<ec:column property="txnType" title="交易类型" width="10%"/>
											<ec:column property="txnAmt" title="金额" width="15%" />
											<ec:column property="accBal" title="余额" width="15%" />										
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