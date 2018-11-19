<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>添加计算规则</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	</head>
	<body onload="setValueEmpty();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加计算规则信息</span>
		</div>
		<s:form id="feeRuleForm" name="feeRuleForm"
			action="caclInf/AddCaclInf.action" method="post">
			<s:token></s:token>
			<div id="feeRule"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="feeRuleTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
							<s:hidden name="flag"></s:hidden>
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
										<td>
											
											<s:fielderror>
												<s:param>caclInfQueryDTO.CaclDspDTO.discCd</s:param>
											</s:fielderror>
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
												<s:param>caclInfQueryDTO.CaclDspDTO.caclName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
		<!-- 				<td colspan="2">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>分段计算方式:
										</td>
										<td>
											<s:select
												list="#{'1':'把本金按照分段规则拆分计算后汇总','0':'按照本金落在的分段统一计算'}"
												name="serviceFeeRuleDTO.calcType"
												emptyOption="false" label="分段计算方式" />
											<s:fielderror>
												<s:param>serviceFeeRuleDTO.calcType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>  -->
							<td colspan="2">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											计算规则方式:
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
					<div id="feeRuleAmountTable" style="width: 100%;display: none;">
						<s:hidden name="serviceFeeRuleDTORuleStep" value="02"/>
						<!--<input  name="serviceFeeRuleDTO.ruleStep" value="01"/>-->
						<table id="amountTable" width="800px;" >
							<!-- <tr>
								<td align="left">
									<span style="color: red">*</span><s:text name="xxl.rule.countStartSum"/>
								</td>
								<td align="left;">
									<span style="color: red">*</span><s:text name="xxl.rule.countEndSum"/>
								</td>
								<td align="left">
									<span style="color: red">*</span><s:text name="xxl.rule.rateCountSum"/>
								</td>
								<td align="left"><s:text name="xxl.rule.handChargeMin"/>
								</td>
								<td align="left"><s:text name="xxl.rule.handChargeMax"/>
								</td>
								<td align="left">
									<span style="color: red">*</span><s:text name="xxl.rule.rateType"/>
								</td>
							</tr> -->
							<tr>
								<td align="left">
									<s:textfield name="serviceFeeRuleDTOAmountStart" value="0" readonly="true" cssClass="readonly" maxlength="10"/>元
								</td>
								<td align="left">
									<s:textfield name="serviceFeeRuleDTOAmountEnd" onblur="changeAmountStart(this);" maxlength="10"/>元
								</td>
								
								<td align="left">
									<s:textfield name="serviceFeeRuleDTOFeeMin" maxlength="10"/>元
								</td>
								
								<td align="left">
									<s:textfield name="serviceFeeRuleDTOFeeMax" maxlength="10"/>元
								</td>
								<td align="left">
									<s:textfield name="serviceFeeRuleDTORateValue" maxlength="10"/>
								</td>
								<td align="left">万分比
									<s:hidden name="serviceFeeRuleDTORateType" value="10000"></s:hidden>
								</td>
							</tr>
						</table>
					</div>
					</div>
				
				<div id="" style="width: 100%;">						
						<table id="" width="800px;">							
							<tr>
								<td>
									<s:textfield name="serviceFeeRuleDTOAmountStart" value="0" readonly="true" cssClass="readonly" maxlength="10"/>元
								</td>
								<td>
									<s:textfield name="serviceFeeRuleDTOAmountEnd"  value="9999999999.99" readonly="true" cssClass="readonly" />元
								</td>
								
								<td>
									<s:textfield name="serviceFeeRuleDTOFeeMin" maxlength="7"/>元
								</td>
								
								<td>
									<s:textfield name="serviceFeeRuleDTOFeeMax" maxlength="7"/>元
								</td>
								<td>
									<s:textfield name="serviceFeeRuleDTORateValue" maxlength="7"/>
								</td>
								<td>万分比
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
					<div id="" style="width: 100%;">
						<table id="amountTable" width="800px;">
							
							<tr>
								<td width="145 px">
									<s:textfield name="splitProfit"  id="splitProfit" maxlength="7"/>&nbsp;
									
								</td>
								<td>万分比
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
                    var a=1;
					function clone(){
						var div = document.createElement("DIV"); 
						var feeRuleDiv =document.getElementById("feeRuleAmountTable"); 
						div.innerHTML=feeRuleDiv.innerHTML;
						a+=1;
						div.id="feeRuleAmountTable"+a;
						div.style.display="";
						feeRuleDiv.parentNode.appendChild(div);
						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
						var amountStarts = document.getElementsByName("serviceFeeRuleDTOAmountStart");
						var ruleSteps = document.getElementsByName("serviceFeeRuleDTORuleStep");
						for(var i = 2;i<ruleSteps.length;i++){
							ruleSteps[i].value = "0"+(i+1);
							if(parseFloat(amountEnds[i-1].value)=null){
								amountStarts[i].value="0";
							}else{
								amountStarts[i].value = parseFloat(amountEnds[i-1].value)+0.01;
							}
						}
						if(ruleSteps.length<3){
							amountStarts[i].value = "0";
						}
						var rateValues = document.getElementsByName("serviceFeeRuleDTORateValue"); 
  						var feeMaxs = document.getElementsByName("serviceFeeRuleDTOFeeMax"); 
  						var feeMins = document.getElementsByName("serviceFeeRuleDTOFeeMin"); 
  						//var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd"); 
  						//for(var i = 0;i<rateValues.length;i++){
  							rateValues[i-1].value="";
  							feeMaxs[i-1].value = "";
  							feeMins[i-1].value= "";
  							amountEnds[i-1].value = "";
  						//}
						
						
						
						
  					}
  					function del(){
  						var feeRuleDiv =document.getElementById("feeRuleAmountTable"+a); 
  						if(feeRuleDiv){
  							feeRuleDiv.parentNode.removeChild(feeRuleDiv);
  							a--;
  						}  					
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
  						var amountStart = document.getElementsByName("serviceFeeRuleDTOAmountStart")[amountEnds.length-1];
  						if(amountEnds.length>2){
  							if(amountStart){
  								var tValue = ""+(parseFloat(amountEnds[amountEnds.length-2].value) + 0.01);
  								amountStart.value = tValue.substring(0,tValue.indexOf(".")+3);
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
  									if(tValue.indexOf(".")!=-1){
  										amountStart.value = tValue.substring(0,tValue.indexOf(".")+3);
  									}else{
  										amountStart.value = tValue;
  									}
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
  							//amountEnds[i].value = "";
  						}
  					}
  					
  					function commit(){
  						var rateValues = document.getElementsByName("serviceFeeRuleDTORateValue"); 
  						var amountStarts = document.getElementsByName("serviceFeeRuleDTOAmountStart");
  						var amountEnds = document.getElementsByName("serviceFeeRuleDTOAmountEnd");
  						var feeMaxs = document.getElementsByName("serviceFeeRuleDTOFeeMax");
  						var feeMins = document.getElementsByName("serviceFeeRuleDTOFeeMin");
  						var intPatrn=/^\+?(([1-9]\d?)|([1-9]\d?\.[0-9]+))$/;
  					    var feePatrn=/^([0]|([1-9]{1}[0-9]{0,4}))$/;
  						var doublePatrn = /^[0-9]+(.[0-9]{1,2})?$/;
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
  								errorDisplay("费率计算数格式错误，必须是不大于5位正整数");
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
  								errorDisplay("手续费计算数格式错误，必须是不大于5位正整数");
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