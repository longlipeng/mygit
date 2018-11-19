<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/widgets/js/jquery.timers.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
</script>
<title>查看企业持卡人信息</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看企业持卡人信息</span>
    </div>
    <s:form id="cardholderForm" name="cardholderForm" action="cardholder/cusView.action" method="post">
       <div id="customerInfo"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="customerInfoTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">所属信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="customerInfoTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">客户号：</td>
									<td>
										<s:textfield id="entityId" name="companyInfoDTO.entityId" disabled="true" /> 
										<s:fielderror>
											<s:param>companyInfoDTO.entityId</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td>
                                    	<s:textfield name="companyInfoDTO.customerDTO.customerName" readonly="true" cssClass="readonly" /> 
										<s:fielderror>
											<s:param>companyInfoDTO.customerDTO.customerName</s:param>
										</s:fielderror>
									</td>
                                </tr>
                            </table>
                        </td>
					</tr>
				</table>
			</div>
		</div>
		<div id="companyInfo"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="customerInfoTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">企业信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="companyInfoTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
				    <tr>
				       <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">企业持卡人号：</td>
									<td>
										<s:textfield name="companyInfoDTO.relationNo" readonly="true" cssClass="readonly" id="relationNo"/>						
										<s:fielderror>
											<s:param>companyInfoDTO.relationNo</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">企业固定电话：</td>
									<td>
										<s:textfield name="companyInfoDTO.linkphone"  disabled="true"/>						
										<s:fielderror>
											<s:param>companyInfoDTO.linkphone</s:param>
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
									<td style="width: 100px; text-align: right;">公司名称：</td>
									<td>
										<s:textfield name="companyInfoDTO.companyName" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyName</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										公司英文名称：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.companyEnglishname" maxlength="64" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyEnglishname</s:param>
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
									<td style="width: 100px; text-align: right;">
										企业注册地址：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.companyRegisteredAddress" maxlength="64" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyRegisteredAddress</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										企业国别：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.companyCountyr"
												dictValue="${companyInfoDTO.companyCountyr}" dictType="450" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.companyCountyr</s:param>
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
									<td style="width: 100px; text-align: right;">
										企业经营地址：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.companyRegisteredAddress" maxlength="64" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyRegisteredAddress</s:param>
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
									<td style="width: 100px; text-align: right;">
										企业证件种类：
									</td>
									<td>
									    <edl:entityDictList displayName="companyInfoDTO.companyIdType"
											dictValue="${companyInfoDTO.companyIdType}" dictType="193" props="disabled=\"true\"" 
											tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.companyIdType</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										企业证件号：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.companyId" maxlength="64" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyId</s:param>
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
									<td style="width: 100px; text-align: right;">
										企业证件有效期：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.ctidEdt"
											id="ctidEdt" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
											disabled="true" cssClass="Wdate">
										</s:textfield>
										<s:fielderror>
											<s:param>companyInfoDTO.ctidEdt</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										企业邮箱：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.email" maxlength="100" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.email</s:param>
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
									<td style="width: 100px; text-align: right;">
										法定代表人姓名：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpName" maxlength="100" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.corpName</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人性别：
									</td>
									<td>
									    <edl:entityDictList displayName="companyInfoDTO.corpGender"
											dictValue="${companyInfoDTO.corpGender}" dictType="10002" props="disabled=\"true\"" 
											tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.corpGender</s:param>
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
									<td style="width: 100px; text-align: right;">
										法定代表人证件类型：
									</td>
									<td>
									    <edl:entityDictList displayName="companyInfoDTO.corpCredType"
											dictValue="${companyInfoDTO.corpCredType}" dictType="140" props="disabled=\"true\"" 
											tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.corpCredType</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人证件号：
									</td>
									<td>
									    <s:textfield  onblur="checksub()" maxlength="18" name="companyInfoDTO.corpCredId" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.corpCredId</s:param>
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
									<td style="width: 100px; text-align: right;">
										法定代表人证件有效期：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpCredValidity"
											id="corpCredValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
											disabled="true" cssClass="Wdate">
										</s:textfield>
										<s:fielderror>
											<s:param>companyInfoDTO.corpCredValidity</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人住址：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpAddress" maxlength="100" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.corpAddress</s:param>
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
									<td style="width: 100px; text-align: right;">
										法定代表人国籍：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.corpCountyr"
												dictValue="${companyInfoDTO.corpCountyr}" dictType="450" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.corpCountyr</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人联系电话：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpTelephoneNumber" maxlength="100" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.corpTelephoneNumber</s:param>
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
									<td style="width: 100px; text-align: right;">
										公司会计：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.companyAccountant" maxlength="64" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.companyAccountant</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										注册资本：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.registeredCapital" maxlength="10" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.registeredCapital</s:param>
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
									<td style="width: 100px; text-align: right;">
										企业规模：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.companySize"
												dictValue="${companyInfoDTO.companySize}" dictType="194" props="disabled=\"true\"" 
												tagType="2" defaultOption="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.companySize</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										邮编：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.postcode" maxlength="10" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.postcode</s:param>
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
									<td style="width: 100px; text-align: right;">
										所属行业：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.companyFax"
												dictValue="${companyInfoDTO.companyFax}" dictType="400"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
										<s:fielderror>
											<s:param>companyInfoDTO.companyFax</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">
										法定代表人出生日期：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpBirthday" onclick="dateClick(this)" 
										    disabled="true" cssClass="Wdate" size="20" cssStyle="cursor:pointer">
										</s:textfield>
										<s:fielderror>
											<s:param>companyInfoDTO.corpBirthday</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>						
					</tr>
					<%-- <tr>					   
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人别名：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.corpAliasName" maxlength="100" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.corpAliasName</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										法定代表人职业：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.corpProfession"
											dictValue="${companyInfoDTO.corpProfession}" dictType="145"
											tagType="2" defaultOption="false"  props="disabled=\"true\""/>
										<s:fielderror>
											<s:param>companyInfoDTO.corpProfession</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					</tr> --%>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人姓名：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.relName" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.relName</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人证件类型
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.citp"
											dictValue="${companyInfoDTO.citp}" dictType="140" props="disabled=\"true\"" 
											tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.citp</s:param>
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
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人证件号:
									</td>
									<td>
										<s:textfield  maxlength="18" name="companyInfoDTO.ctid" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.ctid</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										证件类型说明：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.citpNt" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.citpNt</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期开始时间：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidStartValidity"
												id="ctidStartValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												disabled="true" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>companyInfoDTO.ctidStartValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidEndValidity"
												id="ctidEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												disabled="true" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>companyInfoDTO.ctidEndValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr> 
					<%-- <tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人证件有效期：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.ctidEndValidity" maxlength="16" disabled="true" 
											size="20" onfocus="dateClick(this)" onchange="dateCheck()" cssClass="Wdate" />
										<s:fielderror>
											<s:param>companyInfoDTO.ctidEndValidity</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					</tr>	 --%>				
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人持股比例：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.holdPer" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.holdPer</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										控股股东或实际控制人持股金额：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.holdAmt" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.holdAmt</s:param>
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
										<td style="width: 100px; text-align: right;">
											黑名单标识：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.isblacklist"
												dictValue="${companyInfoDTO.isblacklist}" dictType="196"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>companyInfoDTO.isblacklist</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											风险等级：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.riskGrade"
												dictValue="${companyInfoDTO.riskGrade}" dictType="197"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>companyInfoDTO.riskGrade</s:param>
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
									<td
										style="width: 90px; text-align: right; vertical-align: top;">
										公司描述(经营范围)：
									</td>
									<td>
										<s:textarea name="companyInfoDTO.companyDescription" onpropertychange="if(value.length>200) value=value.substr(0,200)" rows="5"
											cols="70" disabled="true"></s:textarea>
										<s:fielderror>
											<s:param>companyInfoDTO.companyDescription</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					</tr>						
				</table>					
			</div>
		</div>	
		
		<div id="operatorInfo"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="operatorInfoTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">经(代)办人信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="operatorInfoTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
					<tr>
					   <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										经(代)办人证件类型：
									</td>
									<td>
										<edl:entityDictList displayName="companyInfoDTO.operatorType"
											dictValue="${companyInfoDTO.operatorType}" dictType="140" props="disabled=\"true\"" 
											tagType="2" defaultOption="false" />
										<s:fielderror>
											<s:param>companyInfoDTO.operatorType</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										经(代)办人证件号：
									</td>
									<td>
										<s:textfield  maxlength="18" name="companyInfoDTO.operatorId" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.operatorId</s:param>
										</s:fielderror>
										<div id="operatorId_msg"></div>
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
										经(代)办人证件有效期：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.operatorValidity"
											id="corpCredStaValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
											disabled="true" cssClass="Wdate">
										</s:textfield>
										<s:fielderror>
											<s:param>companyInfoDTO.operatorValidity</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										经(代)办人姓名：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.operatorName" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.operatorName</s:param>
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
									<td style="width: 100px; text-align: right;">
										经(代)办人联系电话：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.operatorTelephoneNumber" maxlength="30" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.operatorTelephoneNumber</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										经(代)办人住址：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.operatorAddress" maxlength="30" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.operatorAddress</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>	
		
		<div id="bankInfo"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="bankInfoTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">银行信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="backInfoTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
				   <tr>
					   <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										银行账户号：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.bankAccount" maxlength="30" disabled="true"/>
										<s:fielderror>
											<s:param>companyInfoDTO.bankAccount</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					    <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										开户银行名称：
									</td>
									<td>
										<s:textfield name="companyInfoDTO.bankName" maxlength="30" disabled="true" />
										<s:fielderror>
											<s:param>companyInfoDTO.bankName</s:param>
										</s:fielderror>
									</td>
								</tr>
							</table>
						</td>
					</tr>	
				</table>
			</div>
		</div>	
		
		<%-- <div id="customerBase2"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="customerBase2Title"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">风控信息</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="customerBase2Table"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										 法定代表人身份信息是否已核查：
									</td>
									<td>
										<s:select list="#{1:'是',0:'否'}"
											name="customerDTO.identityInspect" disabled="true"></s:select>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										 五证是否齐全：
									</td>
									<td>
										<s:select list="#{1:'是',0:'否'}"
											name="customerDTO.fiveCertificate" disabled="true"></s:select>
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
										 客户是否卷入法律诉讼：
									</td>
									<td>
										<s:select list="#{0:'否',1:'是'}"
											name="customerDTO.actionAtLaw" disabled="true"></s:select>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
										 客户负责人（法定代表人）的信用状况：
									</td>
									<td>
									    <s:select list="#{1:'好',0:'不好'}"
											name="customerDTO.creditStatus" disabled="true"></s:select>											
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
										 客户负责人（法定代表人代表或高管）是否卷入法律诉讼：
									</td>
									<td>
										<s:select list="#{0:'否',1:'是'}"
											name="customerDTO.corpActionAtLaw" disabled="true"></s:select>
									</td>
								</tr>
							</table>
						</td>	
					</tr>
				</table>
			</div>	 
		</div>	 --%>
		
        <div id="allCardInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="allCardInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">持有卡片信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
           	<input type="hidden" name="sellOrderCardListQueryDTO.cardholderId" id="sellOrderCardListQueryDTO.cardholderId" value="${companyInfoDTO.relationNo}"/>
            <div id="allCardInfoTable" style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <ec:table items="companyInfoDTO.cardholderCardList.data" var="map" width="100%"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                     retrieveRowsCallback="limit"
                    autoIncludeParameters="false"  form="cardholderForm" action="${ctx}/cardholder/cusView.action">
                    <ec:row>
                        <ec:column property="cardNo" title="卡号" width="15%" />
                        <ec:column property="productName" title="产品名称" width="15%" />
                        <ec:column property="validityPeriod" title="有效期" width="15%" />
                        <ec:column property="totalAmt" title="当前余额" width="15%" />  
                        <ec:column property="avaliableAmt" title="可用金额" width="15%" />
                        <ec:column property="freezeAmt" title="冻结金额" width="15%" />
                    </ec:row>
                </ec:table>
            </div>
        </div>
        
        <div id="buttonDiv" style="">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                <s:hidden name="cardManagementFlag"></s:hidden>
                                <s:hidden name="callcenter" ></s:hidden>
                                 <s:hidden name="cardManagementDTO.transferOutCard"></s:hidden>
                                <s:if test="#request.callcenter ==1">	
                                 <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardManageExt/queryByCard.action')" value="返 回"/>
                                </s:if>
                                <s:else>
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardholder/inqueryShow.action')" value="返 回"/>
                                </s:else>
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