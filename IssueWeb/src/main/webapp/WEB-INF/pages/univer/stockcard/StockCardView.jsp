<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看结算周期规则</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"></base>
	    <script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
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
			function onLoad(){
				var flag=document.getElementById("flag").value;
				if(flag == 1){
					window.returnValue=1;
					window.close();
				}
			}
    
    function chan(){
    	 var ruleType=${settlePeriodRuleDTO.ruleType};
      if(ruleType==1){
    	    document.getElementById("box1").style.display="";
    	    document.getElementById("box11").style.display="";
		    document.getElementById("box2").style.display="none";
		    document.getElementById("box3").style.display="";
    	}else if(ruleType==0){
    		document.getElementById("box2").style.display="";
		    document.getElementById("box1").style.display="none";
		    document.getElementById("box11").style.display="none";
		    document.getElementById("box3").style.display="none";
    	}
    }
		</script>
	</head>
	<body onload="chan()">
		<%@ include file="/commons/messages.jsp"%>
		<s:hidden name="flag" id="flag"></s:hidden>
		<div class="TitleHref">
			<span>结算规则信息</span>
		</div>
		<s:form id="settleRuleForm" name="settleRuleForm" action="settlePeriodRule/insert.action" method="post">
          
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
									<span class="TableTop">规则信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														规则号：
													</td>
													<td>
														<s:textfield readonly="true" name="settlePeriodRuleDTO.ruleNo"
															id="settlePeriodRuleDTO.ruleNo"></s:textfield>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleNo
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
														<span class="no-empty">*</span>规则名称：
													</td>
													<td>
														<s:textfield id="settlePeriodRuleDTO.ruleName" name="settlePeriodRuleDTO.ruleName" readonly="true"/>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleName
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>规则类型：
													</td>
													<td>
														<s:select list="#{'1':'固定周期','0':'非固定周期'}"
															name="settlePeriodRuleDTO.ruleType"
															id="settlePeriodRuleDTO.ruleType" 
															 label="规则类型："
                                                             onchange="change();" readonly="true"/>
                                                        <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr id="box1">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>周期单位：
													</td>
													<td>
										               <s:select list="#{'D':'按天数','W':'按星期数','M':'按月数'}"
															name="settlePeriodRuleDTO.periodType" 
															id="settlePeriodRuleDTO.periodType"
															emptyOption="false"
															 headerKey=""
															 headerValue="--请选择--"
															 label="结算方式："
															 onchange="change2();" readonly="true"
                                                              />
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.periodType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td id="box4">
											
										</td>	
								      </tr>	
								      <tr id='box11'>
								      	<td>
								      		<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													  周期：
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.period"
															id="settlePeriodRuleDTO.period" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.period
															</s:param>
														</s:fielderror> 
													</td>
												
												</tr>
											</table>
								      	</td>
								      	<td></td>
								      </tr>
								      <tr id="box2">								       
										<td colspan="2"> 
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														可结金额：
													</td>
													<td>
										               <s:textfield name="settlePeriodRuleDTO.amountMin" id="settlePeriodRuleDTO.amountMin" readonly="true"/>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.amountMin
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
									 <tr id='box3'>								       
										<td> 
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														指定周期：
													</td>
													<td>
										               <s:textfield name="settlePeriodRuleDTO.appointPeriod" id="settlePeriodRuleDTO.appointPeriod" readonly="true"/>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.appointPeriod
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
														指定日：
													</td>
													<td>
										               <s:textfield name="settlePeriodRuleDTO.appointDay" id="settlePeriodRuleDTO.appointDay" readonly="true"/>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.appointDay
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
					  </div>
					</td>
				</tr>
			</table>
		</div>
		<div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/settleRule/query.action';" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
	</body>
</html>