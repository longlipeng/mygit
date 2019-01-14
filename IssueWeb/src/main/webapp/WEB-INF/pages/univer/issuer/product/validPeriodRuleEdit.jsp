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
	</head>
	<body onload="load();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑有效期规则</span>
		</div>
		<s:form id="validRuleForm" name="validRuleForm"
			action="/validPeriodRule/validPeriodRuleAction!update.action" method="post">
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
				<div id="feeRuleTable"
					style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>规则号:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.ruleDspId" readonly="true"
												cssClass="readonly" />
										</td>
										<td>
										</td>
									</tr>
								</table>
							</td> 
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>规则名称:
										</td>
										<td>
											<s:textfield name="validPeriodRuleDspDTO.ruleName" readonly="true"/>
										</td>
										<td>
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
											<span style="color: red">*</span>交易类型:
										</td>
										<td>
											<s:select list="#{1:'充值',2:'激活',3:'延期'}" name="validPeriodRuleDspDTO.transType" id="validPeriodRuleDspDTO.transType" onchange="change();"></s:select>
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
											<s:select list="#{1:'卡片有效期',2:'交易日期'}" name="validPeriodRuleDspDTO.branchMark"></s:select>
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
											<span style="color: red">*</span>截止日期:
										</td>
										<td>
											 <s:textfield name="validPeriodRuleDspDTO.deadLine"
												id="deadLine" size="20" onfocus="dateClick(this)"
												cssClass="Wdate" readonly="true">
										</s:textfield>
                                        <s:fielderror>
                                            <s:param>validPeriodRuleDspDTO.deadLine</s:param>
                                        </s:fielderror>
										</td>
									</tr>
								</table>
							</td> 
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则描述：
										</td>
										<td>
											<s:textarea name="validPeriodRuleDspDTO.ruleDsp" cols="50" rows="5" />
											 <s:fielderror>
                                            <s:param>validPeriodRuleDspDTO.ruleDsp</s:param>
                                        </s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div id="validRuleAmountTitle"
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
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;" id="valid1">
					<input type="hidden" value="${size}" id="listSize"/>
					<table width="800px;">
							<tr>
								<td align="left">
									<span style="color: red">*</span>计算起始金额：
								</td>
								<td align="left">
									<span style="color: red">*</span>计算截止金额：
								</td>
								<td align="left">
									<span style="color: red">*</span>延期月数：
								</td>
							</tr>
					</table>
					<s:iterator value="validPeriodRuleQueryDTO.list" status="status">
						<div id="validRuleAmountTable${status.index}" style="width: 100%;">
							<input type="hidden" value="${ruleStep}"
								name="ruleStep" />
								<input type="hidden" value="${ruleInfId}"
								name="ruleInfId" />
								<input type="hidden" value="${dataState}"
								name="dataState" />
						<table id="amountTable" width="800px;">
							<tr>
								<td>
									<input type="text" name="amountMin" readonly="readonly" class="readonly" value="${minAmount }"/>&nbsp;元
								</td>
								<td>
								<input type="text" name="amountMax"  onchange="changeAmountMin(this);" maxlength="10" value="${maxAmount }"/>&nbsp;元
								</td>
								<td>
								<input type="text" name="delayMonth" value="${delayMonth }"/>&nbsp;月
								</td>
							</tr>
						</table>
					</div>
					</s:iterator>
					<input type="button" class="bt" style="margin: 5px"
					onclick="clone()" value="增加" />
					
				<input type="button" class="bt" style="margin: 5px" onclick="del()"
					value="删除" />
				</div>
				<div
					style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;" id="valid2">
					<table width="800px;">
						<tr>
							<td align="left"><span style="color: red">*</span>延期月数:</td>							
						</tr>
						<tr>
							<td><s:textfield name="rechargeDelayMonth" id="rechargeDelayMonth"/>&nbsp;月</td>
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
							<td align="left"><s:textfield name="poundage" id="poundage"/>&nbsp;元</td>
							<td align="left"><s:textfield name="month" id="month"/>&nbsp;月</td>
						</tr>
					</table>
				</div>				
			</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
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
	function change(){
		var transType=document.getElementById("validPeriodRuleDspDTO.transType").value;
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
				
	function clone() {
		var feeRuleDiv;
		var size = parseInt(document.getElementById("listSize").value);
		var feeRuleDiv=feeRuleDiv =document.getElementById("validRuleAmountTable0");
		var div = document.createElement("DIV"); 
		div.innerHTML=feeRuleDiv.innerHTML;		
		document.getElementById("listSize").value = size+1;
			div.id="validRuleAmountTable"+size;
						feeRuleDiv.parentNode.appendChild(div);		
						var amountMaxs = document.getElementsByName("amountMax");
						var amountMins = document.getElementsByName("amountMin");
						var delayMonth = document.getElementsByName("delayMonth");
						var ruleSteps = document.getElementsByName("ruleStep");	
						for(var i = 0;i<ruleSteps.length;i++){
							ruleSteps[i].value = "0"+(i+1);
							if(i>0){
								var va = amountMaxs[i-1].value;
								if(va!=null && va!="")
									amountMins[i].value = parseFloat(amountMaxs[i-1].value)+0.01;
								else
									amountMins[i].value = "0.01";
							} 
						}
  							amountMaxs[i-1].value = "";
  							delayMonth[i-1].value="";
	}
	function del(){
		var a = parseInt(document.getElementById("listSize").value);
		if(a==1){
			return;
		}
		var feeRuleDiv =document.getElementById("validRuleAmountTable"+(a-1)); 
		if(feeRuleDiv){
			feeRuleDiv.parentNode.removeChild(feeRuleDiv);
			a--;
			document.getElementById("listSize").value = a;
		}
	}  
	function changeAmountMin(amountMax) {
		var amountMaxs = document.getElementsByName("amountMax");
		for ( var i = 0; i < amountMaxs.length; i++) {
			if (amountMax == amountMaxs[i]) {
				var amountMin = document.getElementsByName("amountMin")[i + 1];
				if (amountMin) {
					var tValue = "" + (parseFloat(amountMaxs[i].value) + 0.01);
					amountMin.value = tValue.substring(0,
							tValue.indexOf(".") + 3);
				}
			}
		}
	}

	function commit(){
						var amountPatrn= /^([0]|([1-9]{1}[0-9]{0,6})){1}([.][0-9]{1,2})?$/;
  					    var feePatrn=/^([0]|([1-9]{1}[0-9]{0,4}))$/;
  						var monthPatrn=/^([0]|([1-9]{1}[0-9]{0,2}))$/;
						var transType=document.getElementById("validPeriodRuleDspDTO.transType").value;
					if(transType==1){
  						var amountMins = document.getElementsByName("amountMin");
  						var amountMaxs = document.getElementsByName("amountMax");
  						var delayMonth = document.getElementsByName("delayMonth");
  						
						for(var i = 0;i<amountMins.length;i++){
							if(isNullOrEmpty(amountMaxs[i].value)){
  								errorDisplay("计算截至金额必须输入");
  								amountMaxs[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(amountMaxs[i].value))&&!amountPatrn.exec(amountMaxs[i].value)){
  								errorDisplay("计算截至金额整数部分不超过7位,保留两位小数");
  								amountMaxs[i].value = "";
  								amountMaxs[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(amountMins[i].value))&&(!isNullOrEmpty(amountMaxs[i].value))){
  								if(parseFloat(amountMins[i].value)>parseFloat(amountMaxs[i].value)){
  									errorDisplay("起始金额必须小于截至金额");
  									return;
  								}
  							}
  							if(isNullOrEmpty(delayMonth[i].value)){
  								errorDisplay("延期月数必须输入");
  								delayMonth[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(delayMonth[i].value))&&!monthPatrn.exec(delayMonth[i].value)){
  								errorDisplay("延期月数格式错误(1-3位正整数)");
  								delayMonth[i].value = "";
  								delayMonth[i].focus();
  								return;
  							}
  						}
				}else if(transType==2){
			var rechargeDelayMonth=document.getElementById("rechargeDelayMonth");
			if (isNullOrEmpty(rechargeDelayMonth.value)) {
					errorDisplay("延期月数必须输入");
					rechargeDelayMonth.focus();
					return;
			}
			if ((!isNullOrEmpty(rechargeDelayMonth.value))
						&& !monthPatrn.exec(rechargeDelayMonth.value)) {
					errorDisplay("延期月数格式错误(1-3位正整数)");
					rechargeDelayMonth.value = "";
					rechargeDelayMonth.focus();
					return;
				}
		}else{
			var poundage=document.getElementById("poundage");
			var month=document.getElementById("month");
			if (isNullOrEmpty(poundage.value)) {
					errorDisplay("延期手续费必须输入");
					poundage.focus();
					return;
				}
				if ((!isNullOrEmpty(poundage.value))
						&& !amountPatrn.exec(poundage.value)) {
					errorDisplay("延期手续费整数部分不超过7位,保留两位小数");
					amountMaxs[i].value = "";
					amountMaxs[i].focus();
					return;
				}
			
			if (isNullOrEmpty(month)) {
					errorDisplay("延期月数必须输入");
					month.focus();
					return;
			}
			if ((!isNullOrEmpty(month.value))
						&& !monthPatrn.exec(month.value)) {
					errorDisplay("延期月数格式错误(1-3位正整数)");
					month.value = "";
					month.focus();
					return;
				}
		}
  		document.validRuleForm.submit();
  	}

	function isNullOrEmpty(oValue) {
		return oValue == null || oValue == "";
	}
</script>
			</div>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' type="button" style="float: right; margin: 5px 10px"
					onclick="window.history.back();">
					返回
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="commit();">
					提交
				</button>
				<div style="clear: both"></div>
			</div>
	</body>
</html>