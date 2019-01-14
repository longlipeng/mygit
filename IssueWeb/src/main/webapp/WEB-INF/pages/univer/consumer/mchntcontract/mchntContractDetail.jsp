﻿<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看商户合同</title>
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
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看商户合同</span>
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
									<span class="TableTop">商户合同信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

											<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="mchntContractManagement/update">
								<table width="100%" style="table-layout: fixed;">
							      <tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														合同号:
													</td>
													<td>
														<s:label name="consumerContractDTO.consumerContractId"/>
													</td>
												</tr>
										  </table>
										</td>
										<td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														商户号:
													</td>
													<td>
														<s:label name="consumerContractDTO.merchantCode"/>
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
														商户名称
													</td>
													<td>
														<s:label name="consumerContractDTO.merchantName"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														合同有效期开始日:
													</td>
													<td>
													    <s:label name="consumerContractDTO.contractStartDate"/>
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
														合同有效期结束日:
													</td>
													<td>
													      <s:label name="consumerContractDTO.contractEndDate"/>
													</td>
												</tr>
											</table>
										</td>
										
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        结算周期规则编号：
                                                    </td>
                                                    <td>
                                                        <s:label name="consumerContractDTO.ruleNo"/>
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
														结算周期名称：
													</td>
													<td>
                                                        <s:label name="consumerContractDTO.ruleName"/>
														<s:fielderror>
															<s:param>consumerContractDTO.ruleName</s:param>
														</s:fielderror>  
													</td>
												</tr>
											</table>
										</td>
                                        <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														结算类型：
													</td>
													<td>
													<c:if test="${consumerContractDTO.clearTp eq 1}">汇总结算</c:if>
												<!-- <s:label name="consumerContractDTO.clearTp"></s:label> -->
													</td>
												</tr>
											</table>
										</td>
                                       
                                    </tr>

								</table>
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>

        <div id="ServiceContractBox" style="margin-top:10px">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('serviceContractTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">账户明细</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <div id="serviceContractTable">
                          <ec:table items="consumerContractDTO.accTypeContractDTOList" var="feeDetDTO" width="100%"
								
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="serviceContract"
                              showPagination="false"
                              sortable="false">
              <ec:row>
               
                <ec:column property="acctypeContractId" title="账户合同号" width="15%">                   
                </ec:column>
                  <ec:column property="acctypeId" title="账户号" width="15%" />
                <ec:column property="ruleNo" title="费用计算规则编号" width="15%" />
                <ec:column property="ruleName" title="费用计算规则名称" width="15%" /> 
              </ec:row>
            </ec:table>
            

                    </td>
                </tr>
            </table>
        </div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='mchntContractManagement/inquiry';newForm.submit();">
				返回
			</button>
		</div>
	</body>
</html>