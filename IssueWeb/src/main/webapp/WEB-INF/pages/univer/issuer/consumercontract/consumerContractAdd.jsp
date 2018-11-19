<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增收单机构合同</title>
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

    function chooseConsumer() {
        var consumerDTO = window.showModalDialog('${ctx}/consumer/chooseConsumer', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (consumerDTO != null) {
        	var array = consumerDTO.split(',');
			document.getElementById('merchantId').value = array[0];
			document.getElementById('merchantName').value = array[1];
        }
    }
    
    function choosePeriodRule() {
       var ruleResult = window.showModalDialog('${ctx}/sellerConstract/settleRuleChoice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if(ruleResult != null){
			var ruleArr = ruleResult.split(',');
			document.getElementById('ruleNo').value = ruleArr[0];
			document.getElementById('ruleName').value = ruleArr[1];
        }

    }
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增收单机构合同信息</span>
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
									<span class="TableTop">合同信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="consumerContract/insert">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>收单机构号：
													</td>
													<td>
                                                        <s:textfield id="merchantId" name="consumerContractDTO.contractBuyer" cssClass="watch" onclick="chooseConsumer()" readonly="true"/>
														<s:fielderror>
															<s:param>
																consumerContractDTO.contractBuyer
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
										  </table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														收单机构名称：
													</td>
													<td>
														<s:textfield name="consumerContractDTO.merchantName" id="merchantName" readonly="true"/>
														
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
														<span class="no-empty">*</span>合同有效期开始日：
													</td>
													<td>
													    <s:textfield name="consumerContractDTO.contractStartDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
													      <s:param name="value"><s:date name="consumerContractDTO.contractStartDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield>
														<s:fielderror>
															<s:param>
																consumerContractDTO.contractStartDateDate
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<s:text name="xxl.merchant.agreeValEndDate"/>
													</td>
													<td>
													    <s:textfield name="consumerContractDTO.contractEndDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
													      <s:param name="value"><s:date name="consumerContractDTO.contractEndDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield> 
														<s:fielderror>
															<s:param>
																consumerContractDTO.contractEndDateDate
															</s:param>
														</s:fielderror> 
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
														<span class="no-empty">*</span>结算周期规则：
													</td>
													<td>
                                                        <s:textfield id="ruleNo" name="consumerContractDTO.ruleNo" cssClass="watch" onclick="choosePeriodRule()" readonly="true"/>
														<s:fielderror>
															<s:param>consumerContractDTO.ruleNo</s:param>
														</s:fielderror>  
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>结算周期名称：
													</td>
													<td>
                                                        <s:textfield id="ruleName" name="consumerContractDTO.ruleName" readonly="true"/>
														<s:fielderror>
															<s:param>consumerContractDTO.ruleName</s:param>
														</s:fielderror>  
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
														<span class="no-empty">*</span>结算类型：
													</td>
													<td>
													<s:select list="#{'1':'汇总结算'}"
															name="consumerContractDTO.clearTp"
															id="consumerContractDTO.clearTp" 
															 label="规则类型："/>
                                                       <!--  <s:textfield id="ruleNo" name="consumerContractDTO.clearTp" cssClass="watch" onclick="choosePeriodRule()" readonly="true"/>-->
														<s:fielderror>
															<s:param>consumerContractDTO.clearTp</s:param>
														</s:fielderror>  
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<s:hidden name="consumerContractDTO.contractSeller"></s:hidden>
								<s:hidden name="consumerContractDTO.contractType"></s:hidden>
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='consumerContract/inquery';newForm.submit();">
				返回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="newForm.submit();">
				下一步
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>