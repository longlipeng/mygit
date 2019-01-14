<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>有效期规则</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
	function load(){
	var transType=${validPeriodRuleDspDTO.transType};
    	if(transType==1){
    	    document.getElementById("valid1").style.display="";
    	    document.getElementById("valid2").style.display="none";
    	    document.getElementById("valid3").style.display="none";
    	    document.getElementById("mark").style.display="";
    	}else if(transType==2){
    		document.getElementById("valid1").style.display="none";
    	    document.getElementById("valid2").style.display="";
    	    document.getElementById("valid3").style.display="none";
    	    document.getElementById("mark").style.display="";
    	}else{
    		document.getElementById("valid1").style.display="none";
    	    document.getElementById("valid2").style.display="none";
    	    document.getElementById("valid3").style.display="";
    	    document.getElementById("mark").style.display="none";
    	}
	}
	</script>
	</head>
	<body onload="load();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看有效期规则</span>
		</div>
			<div id="validRule"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
				<div id="validRuleTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">规则信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="validRuleTable"
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则号:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.ruleDspId" readonly="true"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则名称:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.ruleName" readonly="true" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
							<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											交易类型:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.transTypeName" readonly="true"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<div id="mark">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>延期基准:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.branchMarkName" readonly="true" />
										</td>
									</tr>
								</table>
								</div>
							</td>
						</tr>
							<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											截止日期:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.deadLine" readonly="true"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则状态:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.ruleState" readonly="true" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						</tr>
							<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											创建人:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.createUserName" readonly="true"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											创建时间:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.createTime" readonly="true" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则描述：
										</td>
										<td>
											<s:textarea name="validPeriodRuleDspDTO.ruleDsp" cols="50" rows="5" readonly="true"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>


				<div id="validRuleInfDiv"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">详细信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9;" id="valid1">
					<table width="800px;">
							<tr>
								<td align="left">
									计算起始金额：
								</td>
								<td align="left">
									计算截止金额：
								</td>
								<td align="left">
									延期月数:
								</td>
							</tr>
					</table>
					<s:iterator value="validPeriodRuleQueryDTO.list" status="status">
						<div id="validRuleAmountTable${status.index}" style="width: 100%;">
							<input type="hidden" value="${ruleStep}"
								name="ruleStep" />
							<table id="amountTable" width="800px;">
								<tr>
									<td>
										<input type="text" name="amountMin"
											readonly="readonly" class="readonly"
											value="${minAmount}" maxlength="10"/>&nbsp;元
									</td>
									<td>
										<input type="text" name="amountMax"
											readonly="readonly"
											value="${maxAmount}" maxlength="10"/>&nbsp;元
									</td>
									<td>
										<input type="text" name="delayMonth" readonly="readonly"
											value="${delayMonth}" maxlength="10"/>&nbsp;月
									</td>
								</tr>
							</table>
						</div>
					</s:iterator>
				</div>
				<div
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;" id="valid2">
					<table width="800px;">
						<tr>
							<td align="left"><span style="color: red">*</span>延期月数:</td>							
						</tr>
						<tr>
							<td><s:textfield name="rechargeDelayMonth" id="rechargeDelayMonth" readonly="true"/>&nbsp;月</td>
						</tr>
					</table>
				</div>
				<div
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;" id="valid3">
					<table width="800px;">
						<tr>
							<td align="left"><span style="color: red">*</span>延期手续费:</td>		
							<td align="left"><span style="color: red">*</span>延期月数:</td>							
						</tr>
						<tr>
							<td align="left"><s:textfield name="poundage" id="poundage" readonly="true"/>&nbsp;元</td>
							<td align="left"><s:textfield name="month" id="month" readonly="true"/>&nbsp;月</td>
						</tr>
					</table>
				</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' type="button" style="float: right;"
					onclick="window.history.back();">
					返回
				</button>
				<div style="clear: both"></div>
			</div>
	</body>
</html>