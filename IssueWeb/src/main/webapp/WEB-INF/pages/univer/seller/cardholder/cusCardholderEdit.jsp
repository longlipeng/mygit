<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@ include file="/commons/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
    <script type="text/javascript">
    var exitIdNo = true;
	/* function verifyIdNo() {
		var flag = true;
		$("#corpCredId_msg").empty("");
		var idNo = document.getElementById("corpCredId").value;
		var type = document.getElementById("paymentType").value;
		var cardholderId = document.getElementById("sellOrderCardListQueryDTO.cardholderId").value;
		if (idNo == "") {
			$("#corpCredId_msg")
					.html(
							'<ul id="cardholderForm_" class="errorMessage"><li><span>请输入证件号！</span></li></ul>');
			return retFlag;
		}
		if (type == 1) {
			var aa = IdCardValidate(idNo);
			if (aa != true) {
				document.getElementById("corpCredId").value = "";
				return retFlag;
			}
			
		}else if(type == 2){
			var passport =/^[0-9a-zA-Z]*$/;
			if(passport.test(idNo)!=true){
				document.getElementById("corpCredId").value="";
				$("#corpCredId_msg")
				.html(
						'<ul id="cardholderForm_" class="errorMessage"><li><span>护照格式错误！</span></li></ul>');
				return retFlag;
			}
			
		}
		ajaxRemote('${ctx}/cardholder/checkOtherCusIdNo.action', {
			'companyInfoDTO.corpCredId' : idNo,
			'companyInfoDTO.corpCredType' : type,
			'companyInfoDTO.relationNo' : cardholderId
		}, successFn, 'json');
	}
	
	function successFn(res) {
		$("#corpCredId_msg").empty("");
		if (res == true) {
			alert("已有其他持卡人使用证件号！");
			$("#corpCredId_msg")
					.html(
							'<ul id="cardholderForm_" class="errorMessage"><li><span>已有其他持卡人使用该证件号！</span></li></ul>');
			exitIdNo = false;
		} else {
			exitIdNo = true;
		}
	} */

	function checksub(type,id){
    	var idNo=document.getElementById(id).value
   		var type=document.getElementById(type).value
   		$("#msg").empty("");
   		if (idNo == "") {
   		    $("#msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入证件号！</span></li></ul>');
			return retFlag;
   		}
       if(type==1){
    	   var aa=IdCardValidate(idNo);   	   
    	   if(aa!=true){
    		   document.getElementById("corpCredId").value="";    		   
    		   return retFlag;
    	   }
    	   return true;
   		}
 }
	
	
	//检查企业证件类型是不是重复
	function checkLicense(){
		var CompanyNo=document.getElementById("companyId").value
   		var type=document.getElementById("companyIdType").value
   		if(type==""){
   		 	$("#msg").hide();
   			return;
   		}
		if(CompanyNo==""){
			 $("#msg").hide();
   			return;
   		}		
		ajaxRemote('${ctx}/cardholder/checkLicense.action',{'companyInfoDTO.companyId':CompanyNo,'companyInfoDTO.companyIdType':type}, successFn,'json');		
	}
	
	function successFn (res){		
		if (res == true){
			document.getElementById("corpCredId").value="";
			retFlag = false;
			$("#msg").show();
		   } else {
			   $("#msg").hide();
			   retFlag = true;
		   }
	}
   
    function submit1(){
 		if(exitIdNo==false){
 			return ;
 		}
 		submitForm('cardholderForm', '${ctx}/cardholder/updateCus.action');
 	}
    </script>
    <title>编辑企业持卡人信息</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑企业持卡人信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm" action="cardholder/insertcustomer.action" method="post">
			<s:token></s:token>
			<s:hidden name="cardholderDTO.fatherEntityId" id="fatherEntityId"></s:hidden>
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
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>客户号：
										</td>
										<td>
											<s:textfield id="entityId" name="companyInfoDTO.entityId" 
											cssClass="readonly" readonly="true"/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											客户名称：
										</td>
										<td>
											<s:textfield id="customerName"
												name="companyInfoDTO.customerDTO.customerName"
												cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						  <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											部门：
										</td>
										<td>
											<s:select disabled="true" list="#{'':''}"
												name="cardholderDTO.departmentId" id="department"></s:select>
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
										<td style="width: 100px; text-align: right;">
											企业持卡人号：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.relationNo" 
											readonly="true" cssClass="readonly" />						
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业固定电话：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.linkphone"  id="linkphone" maxlength="15"/>						
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>公司名称：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.companyName" id="companyName" maxlength="32"/>
											<s:fielderror>
												<s:param>companyInfoDTO.companyName</s:param>
											</s:fielderror>
											<div id ="companyName_msg"></div>
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
											<s:textfield name="companyInfoDTO.companyEnglishname" maxlength="32" id="companyEnglishname"/>
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
											<span class="no-empty">*</span>企业注册地：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.companyRegisteredAddress" maxlength="20" id="companyRegisteredAddress"/>
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
											<span class="no-empty">*</span>企业国别：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.companyCountyr"
												dictValue="${companyInfoDTO.companyCountyr}" dictType="450" props="id=\"type\""
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
											<span class="no-empty">*</span>企业证件种类：
										</td>
										<td>
										    <edl:entityDictList displayName="companyInfoDTO.companyIdType"
												dictValue="${companyInfoDTO.companyIdType}" dictType="193" props="id=\"companyIdType\"" 
												tagType="2" defaultOption="true" />
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
											<span class="no-empty">*</span>企业证件号：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.companyId" maxlength="20" id="companyId"/>
											<s:fielderror>
												<s:param>companyInfoDTO.companyId</s:param>
											</s:fielderror>
											<div id ="companyId_msg"></div>
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
											<span class="no-empty">*</span>企业证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidEdt"
												id="ctidEdt" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>companyInfoDTO.ctidEdt</s:param>
											</s:fielderror>
											<div id ="ctidEdt_msg"></div>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业邮箱：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.email" maxlength="32" id="email"/>
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
											<span class="no-empty">*</span>法定代表人姓名：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpName" maxlength="18" id="corpName"/>
											<s:fielderror>
												<s:param>companyInfoDTO.corpName</s:param>
											</s:fielderror>
											<div id ="corpName_msg"></div>
										</td>
									</tr>
								</table>
							</td>
						    <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人性别：
										</td>
										<td>
										    <edl:entityDictList displayName="companyInfoDTO.corpGender"
												dictValue="${companyInfoDTO.corpGender}" dictType="10002" props="id=\"companyInfoDTO.corpGender\"" 
												tagType="2" defaultOption="true" />
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
											<span class="no-empty">*</span>法定代表人证件类型：
										</td>
										<td>
											<%-- <edl:entityDictList displayName="companyInfoDTO.corpCredType"
												dictValue="${companyInfoDTO.corpCredType}" dictType="140" props="disabled=\"true\""
												tagType="2" defaultOption="true" /> --%>
										    <edl:entityDictList displayName="companyInfoDTO.corpCredType"
												dictValue="${companyInfoDTO.corpCredType}" dictType="140" props="id=\"corptype\""
												tagType="2" defaultOption="true" />
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
											<span class="no-empty">*</span>法定代表人证件号：
										</td>
										<td>
											<%-- <s:textfield maxlength="18" name="companyInfoDTO.corpCredId"  disabled="true" /> --%>
										    <s:textfield  onblur="checksub('corptype','corpCredId')" maxlength="18" name="companyInfoDTO.corpCredId" id="corpCredId" />
											<s:fielderror>
												<s:param>companyInfoDTO.corpCredId</s:param>
											</s:fielderror>
											<div id ="corpCredId_msg"></div>
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
											<span class="no-empty">*</span>法定代表人证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpCredValidity"
												id="corpCredValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>companyInfoDTO.corpCredValidity</s:param>
											</s:fielderror>
											<div id ="corpCredValidity_msg"></div>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人住址：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpAddress" maxlength="64" id="corpAddress"/>
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
											<span class="no-empty">*</span>法定代表人国籍：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.corpCountyr"
												dictValue="${companyInfoDTO.corpCountyr}" dictType="450" props="id=\"type\""
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
											<span class="no-empty">*</span>法定代表人联系电话：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpTelephoneNumber" maxlength="11" id="corpTelephoneNumber" />
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
											<s:textfield name="companyInfoDTO.companyAccountant" maxlength="32" id="companyAccountant" />
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
											<span style="color: red">*</span>注册资本：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.registeredCapital" maxlength="12" id="registeredCapital"/>
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
											<span style="color: red">*</span>企业规模：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.companySize"
												dictValue="${companyInfoDTO.companySize}" dictType="194" props="id=\"companySize\"" 
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
											<s:textfield name="companyInfoDTO.postcode" maxlength="11" id="postcode" />
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
											<span class="no-empty">*</span>所属行业：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.companyFax"
												dictValue="${companyInfoDTO.companyFax}" dictType="400"
												tagType="2" defaultOption="true" props="id=\"companyFax\"" />
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
											<span class="no-empty">*</span>法定代表人出生日期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpBirthday" onclick="dateClick(this)" 
											    cssClass="Wdate" size="20" cssStyle="cursor:pointer">
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
											<span class="no-empty">*</span>法定代表人别名：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.corpAliasName" maxlength="32" id="corpAliasName"/>
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
											<span class="no-empty">*</span>法定代表人职业：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.corpProfession"
												dictValue="${companyInfoDTO.corpProfession}" dictType="145"
												tagType="2" defaultOption="true" />
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
											<span class="no-empty">*</span>控股股东或实际控制人姓名：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.relName" maxlength="32" id="relName" />
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
											<span class="no-empty">*</span>控股股东或控股股东或实际控制人证件类型
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.citp"
												dictValue="${companyInfoDTO.citp}" dictType="140" props="id=\"controlcitp\"" 
												tagType="2" defaultOption="true" />
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
											<span class="no-empty">*</span>控股股东或实际控制人证件号:
										</td>
										<td>
											<s:textfield  onblur="checksub('controlcitp','ctid')" maxlength="18" name="companyInfoDTO.ctid" id="ctid" />
											<s:fielderror>
												<s:param>companyInfoDTO.ctid</s:param>
											</s:fielderror>
											<div id="ctid_msg"></div>
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
											<s:textfield name="companyInfoDTO.citpNt" maxlength="10" id="citpNt" />
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
												cssClass="Wdate">
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
												cssClass="Wdate">
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
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidEndValidity" maxlength="16" id="corpCredStaValidity" 
												size="20" onfocus="dateClick(this)" onchange="dateCheck()" cssClass="Wdate" />
											<s:fielderror>
												<s:param>companyInfoDTO.ctidEndValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>		 --%>		
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人持股比例：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.holdPer" maxlength="32" id="holdPer" />
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
											<span class="no-empty">*</span>控股股东或实际控制人持股金额：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.holdAmt" maxlength="32" id="holdAmt" />
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
											<span class="no-empty">*</span>公司描述(经营范围)：
										</td>
										<td>
											<s:textarea name="companyInfoDTO.companyDescription" onpropertychange="if(value.length>200) value=value.substr(0,200)" rows="5"
												cols="70" id="companyDescription"></s:textarea>
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
											<span style="color: red">*</span>经(代)办人证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="companyInfoDTO.operatorType"
												dictValue="${companyInfoDTO.operatorType}" dictType="140" props="id=\"operatorType\"" 
												tagType="2" defaultOption="true" />
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
											<span style="color: red">*</span>经(代)办人证件号：
										</td>
										<td>
											<s:textfield onblur="checksub('operatorType','operatorId')" maxlength="18" name="companyInfoDTO.operatorId" id="operatorId" />
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
											<span style="color: red">*</span>经(代)办人证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorValidity"
												id="corpCredStaValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
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
											<span style="color: red">*</span>经(代)办人姓名：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorName" maxlength="18" id="operatorName" />
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
											<span style="color: red">*</span>经(代)办人联系电话：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorTelephoneNumber" maxlength="11" id="operatorTelephoneNumber"/>
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
											<s:textfield name="companyInfoDTO.operatorAddress" maxlength="64" id="operatorAddress"/>
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
											<span style="color: red">*</span>银行账户号：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.bankAccount" maxlength="30" id="bankAccount"/>
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
											<span style="color: red">*</span>开户银行名称：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.bankName" maxlength="15" id="bankName" />
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
			</div>			 --%>	
			
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
	                    autoIncludeParameters="false"  
	                    form="cardholderForm" action="${ctx}/cardholder/editCus.action">
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
        
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
	            <table border="0" cellpadding="0" cellspacing="0" width="100%">
	                <tr>
	                    <td align="right">
	                        <table border="0" cellpadding="0" cellspacing="0">
	                            <tr>
	                                <td>
	                                    <input type="button" class="bt" style="margin: 5px" onclick="submit1();" value="提 交"/>
	                                </td>
	                                <td>
	                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardholder/inqueryShow.action')" value="返 回"/>
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