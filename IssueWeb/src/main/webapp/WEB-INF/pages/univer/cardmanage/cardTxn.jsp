<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片交易查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}	
			function change(){
				var flag=document.getElementById("selectFlag").value;
				if(flag==0){
					document.getElementById("hisStartTime").style.visibility="hidden";
					document.getElementById("hisStopTime").style.visibility="hidden";
				}else{
					document.getElementById("hisStartTime").style.visibility="";
					document.getElementById("hisStopTime").style.visibility="";
				
				}
			}
			
		</script>
	</head>
	<body onload="change()">
		
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片交易查询</span>
		</div>

		<div id="ContainBox">

			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">卡交易查询</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						
						<s:hidden name="cardManagementQueryDTO.starDate" id="cmqStartDate"></s:hidden>
						<s:hidden name="cardManagementQueryDTO.enDate" id="cmqEndDate"></s:hidden>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManage/cardTxnSelect.action" method="post">
								<s:hidden name="op" id="op"/>
							
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡号：
													</td>
													<td>
														<s:textfield name="cardManagementDTO.transferOutCard" id="cardNo"
															size="23"></s:textfield>
														<s:fielderror>
															<s:param>
																cardManagementDTO.transferOutCard
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
														<span class="no-empty"></span>交易类型：
													</td>
													<td>
														<s:select
															list="#{'':'请选择','S32':'充值','S22':'消费','S20':'预授权完成','M34':'转出','M33':'转入'}"
															name="cardManagementQueryDTO.txnType" value="cardManagementQueryDTO.txnType"></s:select>
													</td>													
												</tr>
													
											</table>
										</td>
										
									</tr>
									<tr>
									   <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														查询记录：
													</td>
													<td>
														<s:select list="#{0:'当天',1:'历史'}"
															name="cardManagementQueryDTO.cardFlag" id="selectFlag" onchange="change()" />
													</td>
													</tr>
											</table>
										</td>
										
									<td id="hisStartTime">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>开始时间：
													</td>
													<td>
														<input type="text" name="cardManagementQueryDTO.starDate" id="startDate"
															onclick="dateClick(this)" class="Wdate"
															value="${cardManagementQueryDTO.starDate}"/>
														<s:fielderror>
															<s:param>
																cardManagementQueryDTO.starDate
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td  id="hisStopTime">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>结束时间：
													</td>
													<td>
														<input type="text" name="cardManagementQueryDTO.enDate" id="endDate"
															onclick="dateClick(this)" class="Wdate"
															value="${cardManagementQueryDTO.enDate}" />
														<s:fielderror>
															<s:param>
																cardManagementQueryDTO.enDate
															</s:param>
														</s:fielderror>
													</td>
													
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
										<td>
														<img src="${ctx}/images/table/cx.GIF" width="50"
															height="19" class="btn" onclick="newForm.submit();">
													</td>
										</tr>
											</table>
										</td>
										
									</tr>

								</table>

							</s:form>

						</div>
				</tr>
			</table>

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
					style="background-color: #FBFEFF; padding: 5px; border-style: solid none no ne; border-width: 1px; border-color: #B9B9B9;">
					<s:form id="cardselectForm" name="cardselectForm"
						action="cardManage/cardTxnSelect.action?op=7" method="post">
						<s:hidden name="cardManagementDTO.transferOutCard" />
						<s:hidden name="cardManagementQueryDTO.txnType" />
						<s:hidden name="cardManagementQueryDTO.starDate" />
						<s:hidden name="cardManagementQueryDTO.enDate" />
						<s:hidden name="cardManagementQueryDTO.cardFlag" />
						<ec:table items="pageDataDTO.data" var="map" width="100%"
							form="cardselectForm"
							action="${ctx}/cardManage/cardTxnSelect.action?op=7"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" autoIncludeParameters="false">
							<ec:row>
								<ec:column property="sysSeqNum" title="交易系统参考号" width="10%">
								
								</ec:column>
								<ec:column property="merchantName" title="商户名称" />
								<ec:column property="shopName" title="门店名称" />
								<ec:column property="txnTypeString" title="交易类型" >
								</ec:column>
								<ec:column property="txnAmt" title="交易金额(元)" />
                                 <ec:column property="totalBalance" title="当前余额(元)"  />

								<ec:column property="balance" title="可用余额(元)" 
									/>
								<ec:column property="txnFee" title="手续费"  />

								<ec:column property="cardNo" escapeAutoFormat="true" title="卡号" />
							
								<ec:column property="rspCode" escapeAutoFormat="true" title="交易状态" />
								
								<ec:column property="txnDate" title="交易时间"/>
							</ec:row>
						</ec:table>

					</s:form>
					<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
				</div>
			</div>
		</div>
	</body>
</html>
