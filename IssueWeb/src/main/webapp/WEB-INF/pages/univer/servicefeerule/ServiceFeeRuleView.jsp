<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>查看计算规则信息</title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看计算规则信息</span>
		</div>
		<s:form id="feeRuleForm" name="feeRuleForm"
			action="serviceFeeRule/update.action" method="post">
			<div id="feeRule"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="feeRuleTitle"
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
											<s:textfield name="caclInfQueryDTO.caclDspDTO.caclName" id="caclInfQueryDTO.caclDspDTO.caclName"
												readonly="true" />
										</td>
										<td>
											
											<s:fielderror>
												<s:param>caclInfQueryDTO.caclDspDTO.caclName</s:param>
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
											按照本金落在的分段统一计算
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
											<s:textarea name="caclInfQueryDTO.caclDspDTO.caclDsp" cols="50" rows="5" readonly="true"/>
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
									<span style="color: red">*</span>费率类型:
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
										<input type="text" name="amountStart"
											readonly="true" class="readonly"
											value="${amountStart}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="amountEnd"
											onchange="changeAmountStart(this);" readonly="true"
											value="${amountEnd}" maxlength="10"/>元
									</td>
									
										<td>
										<input type="text" name="feeMin" readonly="true"
											value="${feeMin}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="feeMax"  readonly="true"
											value="${feeMax}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="rateValue"  readonly="true"
											value="${rateValue}" maxlength="10"/>
									</td>
									<td>
										万分比
									</td>
								</tr>
							</table>
						</div>
					</s:iterator>
					</div>
					<div id="feeRuleAmountTable" style="width: 100%;">						
						<table id="amountTable" width="800px;">							
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
										<input type="text" name="serviceFeeRuleDTOFeeMin" readonly="true"
											value="${view.feeMin}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTOFeeMax" readonly="true"
											value="${view.feeMax}" maxlength="10"/>元
									</td>
									<td>
										<input type="text" name="serviceFeeRuleDTORateValue" readonly="true"
											value="${view.rateValue}" maxlength="10"/>
									</td>
									<td>
										万分比
									</td>
								</tr>
						</table>
					</div>
				</div>
				
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
								<td align="left" width="138 px">
									手续费计算数：
								</td>
								<td align="left">
									<span style="color: red">*</span>手续费类型:
								</td>
							</tr>
					</table>
					<div id="feeRuleAmountTable" style="width: 100%;">
						<table id="amountTable" width="800px;">
							
							<tr>
								<td width="138 px">
									<s:textfield name="caclInfQueryDTO.caclInfDTO.splitProfit"  id="splitProfit" maxlength="10" readonly="true"/>&nbsp;
									
								</td>
								<td>
								万分比
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
				
			</div>
		</s:form>
		<script type="text/javascript">
			document.getElementById("feeRuleAmountTable0").style.display='none'
		</script>
		<div id="btnDiv" style="text-align: right; width: 100%">
				<button class='bt' type="button" style="float: right; margin: 5px 10px"
					onclick="window.history.back();">
<!--						onclick="back();">-->
					返回
				</button>
				<div style="clear: both"></div>
			--</div>
	</body>
</html>