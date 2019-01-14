<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>结算单手续费修改</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}
			function changeFee(){
			//手续费必须小于结算金额
				var settleAmt=document.getElementById("txnAmt").value;
				var afterFee=document.getElementById("afterFee").value;
				var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/;
				if(isNullOrEmpty(afterFee)){
					errorDisplay("手续费必须输入");
					return;
				}
				if(!doublePatrn.exec(afterFee)){
					errorDisplay("请输入格式正确的手续费(两位小数)");
					return;
				}
				if(parseFloat(settleAmt)==0 ){
					if(parseFloat(afterFee)>0){
					errorDisplay("手续费必须小于结算本金");
					return;
					}
				}
				else if(parseFloat(afterFee) >= parseFloat(settleAmt)){
					errorDisplay("手续费必须小于结算本金");
					return;
				}
				var memo=document.getElementById("memo").value;
				if(isNullOrEmpty(memo)){
					errorDisplay("修改原因必须输入");
					return;
				}
				newForm.action='${ctx}/merchantSettle/settleChangeFee.action';
				newForm.submit();
				
			}
			function isNullOrEmpty(oValue){
  				return oValue==null||oValue=="";
  			}
		</script>
	</head>
	<body>
	<s:fielderror>
	
	</s:fielderror>
	<s:actionerror></s:actionerror>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算单手续费修改</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">手续费修改</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="/merchantSettle/settleChangeFee">
								<table width="100%" style="table-layout: fixed;">
								<s:iterator value="pageDataDTO.data"  >
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 150px; text-align: right;">
															商户号：
														</td>
														<td>
																<s:hidden  name="merchantId"  id="merchantId"></s:hidden>
																<s:textfield name="merchantCode" readonly="true"></s:textfield>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 150px; text-align: right;">
															商户名称：
														</td>
														<td>
															<s:textfield name="merchantName"
																id="merchantName" readonly="true"></s:textfield>
													 	<s:hidden name="settleId"></s:hidden>
															
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 150px; text-align: right;">
															结算本金：
														</td>
														<td>
															<s:textfield name="txnAmt" id="txnAmt" readonly="true"/>
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
															结算金额：
														</td>
														<td>
															<s:textfield name="settleAmount" id="settleAmount" readonly="true"/>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 150px; text-align: right;">
															结算日期：
														</td>
														<td>
															<s:text name="settleEndDate" />
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 150px; text-align: right;">
															结算手续费：
														</td>
														<td>
															<s:text name="serviceFee" />
															<s:hidden name="serviceFee"></s:hidden>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:iterator>
							     
									<tr>
					                   <td valign="top">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>结算手续费修改为：
													</td>
													<td>
													    <s:textfield name="afterFee" maxLength="9" id="afterFee" value=""/>
													</td>
												</tr>
											</table>
										</td>
									
										<td  valign="top" colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td valign="top" style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>修改原因：
													</td>
													<td>
                                                       <s:textarea name="settleDTO.memo" id="memo" cols="30" rows="8"/>
													   <s:fielderror>
															<s:param>
																mchntSettleDTO.memo
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<!-- <input type="hidden" name="settleDTO.settleId" value="${settleDTO.settleId}"/> -->
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='${ctx}/merchantSettle/query.action?state=4';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="changeFee();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		
	<!-- 	<div id="TableBody">
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form=""
								action=""
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:exportXls fileName="mchntSettleModList.xls" tooltip="导出Excel" />
								<ec:row>
						            <ec:column property="mchntSettleId" title="结算单编号" width="10%"/>
									<ec:column property="oldTxnFee" title="修改前手续费" width="15%"/>
									<ec:column property="txnFee" title="修改后手续费" width="15%"/>
									<ec:column property="feeModDate" title="修改日期" width="10%"/>
									<ec:column property="modDesc" title="修改原因" width="40%"/>
									<ec:column property="userName" title="修改操作员" width="10%"/>
	              						
								</ec:row>
							</ec:table>
	</div>-->
	</body>
</html>