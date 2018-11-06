<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>编辑计算规则信息</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑计算规则信息</span>
		</div>
		<s:form id="feeRuleForm" name="feeRuleForm"
			action="caclInf/update.action?state=0" method="post">
			<s:token></s:token>
			<s:hidden name="flag"></s:hidden>
			<div id="feeRule"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="feeRuleTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<s:hidden name="consumerContractDTO.consumerContractId"></s:hidden>
								<span class="TableTop">规则信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="feeRuleTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											规则号:
										</td>
										<td>
											<s:textfield name="caclInfQueryDTO.caclDspDTO.discCd" readonly="true"
												cssClass="readonly" />
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
											<s:textfield name="caclInfQueryDTO.caclDspDTO.caclName" id="caclName"
												 />
										</td>
										<td>
											
											<s:fielderror>
												<s:param>caclInfQueryDTO.caclInfDTO.caclName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>分段计算方式:
										</td>
										<td>
											分段比例										
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
											<s:textarea name="caclInfQueryDTO.caclDspDTO.caclDsp" cols="50" rows="5" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>


				<div id="feeRuleAmountTitle"
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
					style="width: 100%;">
					<input type="hidden" value="${size}" id="feeRuleDTOListSize"/>
					<table>
					
							<tr>
								<td align="left" width="145">
									<span style="color: red">*</span>计算起始金额：
								</td>
								<td align="left;" width="150">
									<span style="color: red">*</span>计算截止金额：
								</td>
								
								<td align="left" width="150"><span style="color: red">*</span>手续费下限：
								</td>
								<td align="left" width="150"><span style="color: red">*</span>手续费上限：
								</td>
								<td align="left" width="140">
									<span style="color: red">*</span>费率计算数：
								</td>
								<td align="left" width="140">
									费率类型:
								</td>
							</tr>
					</table>
					<div>
					<s:iterator value="caclInfQueryDTO.list" status="status">
					
						<div id="feeRuleAmountTable${status.index}" style="width: 100%;">
							<input type="hidden" value="${ruleStep}"
								name="serviceFeeRuleDTORuleStep" />
							<table id="amountTable" width="800px;">
								<tr>
									<td>
										<input type="text" name="serviceFeeRuleDTOAmountStart"
											readonly="true" class="readonly"
											value="${amountStart}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTOAmountEnd"
											onblur="changeAmountStart(this);"
											value="${amountEnd}" maxlength="10"/>元
									</td>
									
										<td>
										<input type="text" name="serviceFeeRuleDTOFeeMin"
											value="${feeMin}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTOFeeMax"
											value="${feeMax}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTORateValue"
											value="${rateValue}" maxlength="10"/>
									</td>
									<td>
										万分比
									<s:hidden name="serviceFeeRuleDTORateType" value="10000"></s:hidden>
									</td>
								</tr>
							</table>
						</div>
						
					</s:iterator>
				</div>
				<div  style="width: 100%;">
				<table id="" width="800px;">							
							<tr>
									<td>
										<input type="text" name="serviceFeeRuleDTOAmountStart"
											readonly="true" class="readonly"
											value="${view.amountStart}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTOAmountEnd"
											readonly="true" class="readonly"
											 value="9999999999.99" maxlength="10"/>元
									</td>
									
										<td>
										<input type="text" name="serviceFeeRuleDTOFeeMin"
											value="${view.feeMin}" maxlength="7"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTOFeeMax"
											value="${view.feeMax}" maxlength="7"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTORateValue"
											value="${view.rateValue}" maxlength="7"/>
									</td>
									<td>
										万分比
									<s:hidden name="serviceFeeRuleDTORateType" value="10000"></s:hidden>
									</td>
								</tr>
						</table>
						</div>
					</div>
				<input type="button" class="bt" style="margin: 5px"
					onclick="clone()" value="增加" />
					
				<input type="button" class="bt" style="margin: 5px" onclick="del()"
					value="<s:text name='删除'/>" />
			</div>
			<div id="feeRuleAmountTable" style="width: 100%;">						
						
					</div>
			<!-- 分润信息 -->
				<div id="splitTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">分润信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="800px;">
							<tr>
								<td align="left" width="145 px">
									手续费计算数：
								</td>
								<td align="left">
									手续费类型:
								</td>
							</tr>
					</table>
					<div id="feeRuleAmountTable" style="width: 100%;">
						<table id="amountTable" width="800px;">
							
							<tr>
								<td width="145 px">
									<s:textfield name="caclInfQueryDTO.caclInfDTO.splitProfit"  id="splitProfit" maxlength="7" />&nbsp;
									
								</td>
								<td>
								万分比
									<s:hidden name="serviceFeeRuleDTORateType" value="10000"></s:hidden>
										
								</td>
							</tr>
						</table>
					</div>
				</div>
			
			
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<!-- <table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="commit();"
											value="提 交" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.history.back();"
											value="返 回" />
									</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table> -->
				<script type="text/javascript">
					function clone(){
						var a = parseInt(document.getElementById("feeRuleDTOListSize").value);
						var div = document.createElement("DIV"); 
						var feeRuleDiv =document.getElementById("feeRuleAmountTable0"); 
						div.innerHTML=feeRuleDiv.innerHTML;
						document.getElementById("feeRuleDTOListSize").value = a + 1;
						div.id="feeRuleAmountTable"+a;
						div.style.display="";
						feeRuleDiv.parentNode.appendChild(div);
						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
						var amountStarts = document.getElementsByName("serviceFeeRuleDTOAmountStart");
						var ruleSteps = document.getElementsByName("serviceFeeRuleDTORuleStep");
						var rateValues = document.getElementsByName("serviceFeeRuleDTORateValue"); 
  						var feeMaxs = document.getElementsByName("serviceFeeRuleDTOFeeMax"); 
  						var feeMins = document.getElementsByName("serviceFeeRuleDTOFeeMin");
						rateValues[a].value="";
						feeMaxs[a].value = "";
						feeMins[a].value= "";
						amountEnds[a].value = "";
						var va = amountEnds[a-1].value;
						if(va!=null && va!="")
							amountStarts[a].value = parseFloat(va)+0.01;
// 							for(var i = 0;i<ruleSteps.length;i++){
//								ruleSteps[i].value = "0"+(i+1);
//							}
  						
						
  					}
  					function del(){
  						var a = parseInt(document.getElementById("feeRuleDTOListSize").value);
  						var feeRuleDiv =document.getElementById("feeRuleAmountTable"+(a-1));
  						if(feeRuleDiv&&a>1){
  							feeRuleDiv.parentNode.removeChild(feeRuleDiv);
  							a--;
  							document.getElementById("feeRuleDTOListSize").value = a;
  						}
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
  						var amountStart = document.getElementsByName("serviceFeeRuleDTOAmountStart")[amountEnds.length-1];
  						if(amountEnds.length>2){
  							if(amountStart){
  								var tValue = ""+(parseFloat(amountEnds[amountEnds.length-2].value) + 0.01);
  								if(tValue.indexOf(".")!=-1){
  										amountStart.value = tValue.substring(0,tValue.indexOf(".")+3);
  									}else{
  										amountStart.value = tValue;
  									}
  							}
  						}else{
  							amountStart.value ='0';
  						}
  					}  
  					function changeAmountStart(amountEnd){
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
  						for(var i = 0;i<amountEnds.length;i++){
  							if(amountEnd==amountEnds[i]){
  								var amountStart = document.getElementsByName("serviceFeeRuleDTOAmountStart")[i+1];
  								if(amountStart){
  									var tValue = ""+(parseFloat(amountEnds[i].value) + 0.01);
  									amountStart.value = tValue.substring(0,tValue.indexOf(".")+3);
  								}
  							}
  						}
  					}
  					
  					function setValueEmpty(){
  						var rateValues = document.getElementsByName("serviceFeeRuleDTORateValue"); 
  						var feeMaxs = document.getElementsByName("serviceFeeRuleDTOFeeMax"); 
  						var feeMins = document.getElementsByName("serviceFeeRuleDTOFeeMin"); 
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd"); 
  						for(var i = 0;i<rateValues.length;i++){
  							rateValues[i].value="";
  							feeMaxs[i].value = "";
  							feeMins[i].value= "";
  							amountEnds[i].value = "";
  						}
  					}
  					
  					function commit(){
  						var rateValues = document.getElementsByName("serviceFeeRuleDTORateValue"); 
  						var amountStarts = document.getElementsByName("serviceFeeRuleDTOAmountStart");
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
  						var feeMaxs = document.getElementsByName("serviceFeeRuleDTOFeeMax");
  						var feeMins = document.getElementsByName("serviceFeeRuleDTOFeeMin");
  						var intPatrn=/^\+?(([1-9]\d?)|([1-9]\d?\.[0-9]+))$/;
  						var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/;
  						var feePatrn=/^([0]|([1-9]{1}[0-9]{0,4}))$/;
  						var splitProfit=document.getElementById("splitProfit");
  						var ruleName = document.getElementById("caclName");
  						
  						if(isNullOrEmpty(ruleName.value)){
  							errorDisplay("规则名称必须输入");
							ruleName.focus();
							return;
  						}else{
				  							
  						for(var i = 1;i<rateValues.length;i++){
  							if(isNullOrEmpty(amountEnds[i].value)){
  								errorDisplay("计算截至金额必须输入");
  								amountEnds[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(amountEnds[i].value))&&!doublePatrn.exec(amountEnds[i].value)){
  								errorDisplay("计算截至金额输入格式错误，必须是0-99999999.99的数字");
  								amountEnds[i].value = "";
  								amountEnds[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(amountStarts[i].value))&&(!isNullOrEmpty(amountEnds[i].value))){
  								if(parseFloat(amountStarts[i].value)>parseFloat(amountEnds[i].value)){
  									errorDisplay("起始金额必须小于截至金额");
  									return;
  								}
  							}
  							
  							if(isNullOrEmpty(feeMins[i].value)){
  								errorDisplay("手续费下限金额必须输入");
  								feeMins[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(feeMins[i].value))&&!doublePatrn.exec(feeMins[i].value)){
  								errorDisplay("手续费下限金额输入格式错误，必须是0-99999999.99的数字");
  								feeMins[i].value = "";
  								feeMins[i].focus();
  								return;
  							}
  							if(isNullOrEmpty(feeMaxs[i].value)){
  								errorDisplay("手续费上限金额必须输入");
  								feeMaxs[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(feeMaxs[i].value))&&!doublePatrn.exec(feeMaxs[i].value)){
  								errorDisplay("手续费上限金额输入格式错误，必须是0-99999999.99的数字");
  								feeMaxs[i].value = "";
  								feeMaxs[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(feeMins[i].value))&&(!isNullOrEmpty(feeMaxs[i].value))){
  								if(parseFloat(feeMins[i].value)>parseFloat(feeMaxs[i].value)){
  									errorDisplay("手续费下限金额必须小于上限金额");
  									return;
  								}
  							}
  							
  							if(isNullOrEmpty(rateValues[i].value)){
  								errorDisplay("费率计算数必须输入");
  								rateValues[i].focus();
  								return;
  							}
							if (!feePatrn.exec(rateValues[i].value)){
  								errorDisplay("费率计算数格式错误，必须是不大于5位的正整数");
  								rateValues[i].value = "";
  								rateValues[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(feeMins[i].value))&&!doublePatrn.exec(feeMins[i].value)){
  								errorDisplay("手续费上限输入格式错误，必须是0-99999999.99的数字");
  								feeMins[i].value = "";
  								feeMins[i].focus();
  								return;
  							}
  							
  							if((!isNullOrEmpty(feeMaxs[i].value))&&!doublePatrn.exec(feeMaxs[i].value)){
  								errorDisplay("手续费下限输入格式错误，必须是0-99999999.99的数字");
  								feeMaxs[i].value = "";
  								feeMaxs[i].focus();
  								return;
  							}
  							if((!isNullOrEmpty(feeMins[i].value))&&(!isNullOrEmpty(feeMaxs[i].value))){
  								if(parseFloat(feeMins[i].value)>parseFloat(feeMaxs[i].value)){
  									errorDisplay("手续费上限必须大于手续费下限");
  									return;
  								}
  							}
//  							if(isNullOrEmpty(splitProfit.value)){
//  								errorDisplay("手续费计算数不能为空");
//  								splitProfit.focus();
//  								return;
//  							}
  							if((!isNullOrEmpty(splitProfit.value))&&(!feePatrn.exec(splitProfit.value))){
  								errorDisplay("手续费计算数格式错误，必须是不大于5位的正整数");
  								splitProfit.value = "";
  								splitProfit.focus();
  								return;
  							}
  						}
  						maskDocAll();
  						document.feeRuleForm.submit();
  						}
  					}
  					
  					function isNullOrEmpty(oValue){
  						return oValue==null||oValue=="";
  					}
  					document.getElementById("feeRuleAmountTable0").style.display='none'
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