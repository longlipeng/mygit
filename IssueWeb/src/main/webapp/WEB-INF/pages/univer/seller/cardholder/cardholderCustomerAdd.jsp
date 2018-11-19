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
		<title>添加企业持卡人信息</title>
		<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
  <script type="text/javascript">
	 function choiceCustomer() {
		var customerDTO = window.showModalDialog(
				'${ctx}/customer/choiceCus.action', '_blank',
				'center:yes;dialogHeight:600px;dialogWidth:850px;resizable:no');
		setDepartmentSelect(customerDTO);
	}
			 
	 function setDepartmentSelect(customerDTO) {
			if (customerDTO != null) {
				maskDocAllWithMessage("Wait...");
				document.getElementById('entityId').value = customerDTO['entityId']; //客户号
				document.getElementById('customerName').value = customerDTO['customerName'];//客户名称
			    document.getElementById('fatherEntityId').value = customerDTO['fatherEntityId'];//发行机构号						    			    			    			    
			    document.getElementById('linkphone').value = customerDTO['linkphone'];//企业固定电话
			    document.getElementById('companyName').value = customerDTO['customerName'];//公司名称
			    document.getElementById('companyEnglishname').value = customerDTO['customerEnglishName'];//公司英文名称
			    document.getElementById('companyRegisteredAddress').value = customerDTO['companyRegisteredAddress'];//企业注册地
			    document.getElementById('companyCountyr').value = customerDTO['companyCountyr'];//企业国别
			    document.getElementById('companyIdType').value = customerDTO['companyIdType'];//企业证件类型
			    document.getElementById('companyId').value = customerDTO['companyId'];//企业证件号
			    document.getElementById('ctidEdt').value = customerDTO['ctidEdt'];//企业证件有效期
			    document.getElementById('email').value = customerDTO['email'];//企业邮箱
			    document.getElementById('corpName').value = customerDTO['corpName'];//法定代表人姓名
			    document.getElementById('corpGender').value = customerDTO['corpGender'];//法定代表人性别
			    document.getElementById('corptype').value = customerDTO['corpCredType'];//法定代表人证件类型
			    document.getElementById('corpCredId').value = customerDTO['corpCredId'];//法定代表人证件号
			    document.getElementById('corpCredValidity').value = customerDTO['corpCredEndValidity'];//法定代表人证件有效期
			    document.getElementById('corpAddress').value = customerDTO['corpAddress'];//法人住址
			    document.getElementById('corpCountyr').value = customerDTO['corpCountyr'];//法人国籍
			    document.getElementById('corpTelephoneNumber').value = customerDTO['corpTelephoneNumber'];//法人电话
			    document.getElementById('companyAccountant').value = customerDTO['companyAccountant'];//公司会计
			    document.getElementById('registeredCapital').value = customerDTO['regiCapital'];//注册资本
			    document.getElementById('companySize').value = customerDTO['companySize'];//企业规模
			    document.getElementById('postcode').value = customerDTO['customerPostcode'];//邮编
			    document.getElementById('companyFax').value = customerDTO['activitySector'];//企业所属行业
			    document.getElementById('corpBirthday').value = customerDTO['corpBirthday'];//法人出生日期
			    document.getElementById('relName').value = customerDTO['relName'];//控股股东或实际控制人姓名
			    document.getElementById('controlcitp').value = customerDTO['citp'];//控股股东或实际控制人证件类型
			    document.getElementById('ctid').value = customerDTO['ctid'];//控股股东或实际控制人证件号
			    document.getElementById('citpNt').value = customerDTO['citpNt'];//证件类型说明
			  
			    document.getElementById('holdPer').value = customerDTO['holdPer'];//控股股东或实际控制人持股比例
			    document.getElementById('holdAmt').value = customerDTO['holdAmt'];//控股股东或实际控制人持股金额			    
			    document.getElementById('companyDescription').value = customerDTO['companyDescription'];//企业描述
			    document.getElementById('operatorType').value = customerDTO['operatorType'];//经(代)办人证件类型
			    document.getElementById('operatorId').value = customerDTO['operatorId'];//经(代)办人证件号
			    document.getElementById('operatorValidity').value = customerDTO['operatorValidity'];//经(代)办人证件有效期
			    document.getElementById('operatorName').value = customerDTO['operatorName'];//经(代)办人姓名
			    document.getElementById('operatorTelephoneNumber').value = customerDTO['operatorTelephoneNumber'];//经(代)办人联系电话
			    document.getElementById('operatorAddress').value = customerDTO['operatorAddress'];//经(代)办人住址
			    document.getElementById('bankAccount').value = customerDTO['bankAccount'];//银行账户号
			    document.getElementById('bankName').value = customerDTO['bankPermit'];//开户银行名称
			    document.getElementById('ctidStartValidity').value = customerDTO['ctidStartValidity'];//实际控制人证件有效期开始时间
			    document.getElementById('ctidEndValidity').value = customerDTO['ctidEndValidity'];//实际控制人证件有效期结束时间		    
			    var departmentSelect = document.getElementById('department');
				departmentSelect.innerHTML = '';
				for ( var i = 0; i < customerDTO['departmentList'].length; i++) {
					var departmentDTO = customerDTO['departmentList'][i];
					var opt = document.createElement('option');
					opt.value = departmentDTO['departmentId'];
					opt.innerHTML = departmentDTO['departmentName'];
					departmentSelect.appendChild(opt);
				} 
				departmentSelect.disabled = false;
				unmaskDocAll();
				exchangeIDtype();
			}
		}
	function exchangeIDtype(){
		 var companyIdTypeVal =document.getElementById('companyIdType').value //企业证件类型
		 document.getElementById("companyIdType1").value = companyIdTypeVal;
		 var corptypeVal =document.getElementById('corptype').value //法定代表人证件类型
		 document.getElementById("corptype1").value = corptypeVal;
		 var corpGenderVal =document.getElementById('corpGender').value //法定代表人性别
		 document.getElementById("corpGender1").value = corpGenderVal;
		 var companySizeVal =document.getElementById('companySize').value //企业规模
		 document.getElementById("companySize1").value = companySizeVal;
		 var companyFaxVal =document.getElementById('companyFax').value //所属行业
		 document.getElementById("companyFax1").value = companyFaxVal;
		 var controlcitpVal =document.getElementById('controlcitp').value //控股股东或实际控制人证件类型
		 document.getElementById("controlcitp1").value = controlcitpVal;
		 var operatorTypeVal =document.getElementById('operatorType').value //经办人证件类型
		 document.getElementById("operatorType1").value = operatorTypeVal;
		 
		 
	}
	function setCustomerDTO() {
		if (document.getElementById("entityId").value != null&&document.getElementById("entityId").value!="") {
			maskDocAll();
			Ext.Ajax.request( {
				url : '${ctx}/customer/selectAjax.action',
				params : {
					'customerDTO.entityId' : document.getElementById("entityId").value,
					'customerDTO.fatherEntityId' : document.getElementById('fatherEntityId').value
				},
				success : function(response, options) {
					var customerDTO = Ext.util.JSON
							.decode(response.responseText);
					setDepartmentSelect(customerDTO);
				}
			});
		}
	}
	
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
    		   $("#corpCredId_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>法定代表人身份证格式错误！</span></li></ul>');
    		   document.getElementById("ctid").value="";
    		   $("#ctid_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>控股股东或实际控制人身份证格式错误！</span></li></ul>');
    		   document.getElementById("operatorId").value="";
    		   $("#operatorId_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>经(代)办人身份证格式错误！</span></li></ul>');
    		   return retFlag;
    	   }
   		}else if(type==4){
   			var passport =/^[0-9a-zA-Z]*$/;
			if(passport.test(idNo)!=true){
				document.getElementById("corpCredId").value="";
				$("#msg")
				.html(
						'<ul id="cardholderForm_" class="errorMessage"><li><span>护照格式不正确！</span></li></ul>');
				return retFlag;
			}
   		}
 }
	
	//页面提交
	function sub2(){
		submitForm('cardholderForm', '${ctx}/cardholder/insertcustomer.action');
	}
	
	//页面提交
	function sub(){
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
		//检查企业证件类型是不是重复
		ajaxRemote('${ctx}/cardholder/checkLicense.action',{'companyInfoDTO.companyId':CompanyNo,'companyInfoDTO.companyIdType':type}, successFn,'json');
	}
	
	
	function successFn (res){		
		if (res == true) {
			document.getElementById("companyId").value="";
			alert("企业证件号已存在！");
			$("#msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>企业证件号已存在！</span></li></ul>');
		} else {
			this.disabled='disabled';  
			//maskDocAll();
			submitForm('cardholderForm', '${ctx}/cardholder/insertcustomer.action');
			var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');
		}
	}
	
  </script>
	</head>
	<body onload="setCustomerDTO();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加企业持卡人信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm"
			action="cardholder/insertcustomer.action" method="post">
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
												cssClass="watch" readonly="true" onclick="choiceCustomer()" /> 
											<s:fielderror>
												<s:param>companyInfoDTO.entityId</s:param>
											</s:fielderror>
											<div id ="entityId_msg"></div>
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
												readonly="true" cssClass="readonly" />
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
											<s:textfield name="companyInfoDTO.relationNo" readonly="true" cssClass="readonly" id="relationNo"/>						
											<s:fielderror>
												<s:param>companyInfoDTO.relationNo</s:param>
											</s:fielderror>
											<div id ="relationNo_msg"></div>
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
											<s:textfield name="companyInfoDTO.linkphone"  id="linkphone" maxlength="15" readonly="true" cssClass="readonly"/>						
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
											<s:textfield name="companyInfoDTO.companyName" id="companyName" maxlength="32" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.companyEnglishname" id="companyEnglishname" maxLength="32" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.companyRegisteredAddress" maxlength="20" id="companyRegisteredAddress" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.companyCountyr" id="companyCountyr"  readonly="true" cssClass="readonly"/> 
											<%-- <edl:entityDictList displayName="companyInfoDTO.companyCountyr"
												dictValue="${companyInfoDTO.companyCountyr}" dictType="450" props="id=\"companyCountyr\"disabled=\"true\""
												tagType="2" defaultOption="false" /> --%>
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
											<s:hidden name="companyInfoDTO.companyIdType" id="companyIdType" />
										    <edl:entityDictList displayName="companyInfoDTO.companyIdType"
												dictValue="${companyInfoDTO.companyIdType}" dictType="193" props="id=\"companyIdType1\"disabled=\"true\"" 
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
											<span class="no-empty">*</span>企业证件号：
										</td>
										<td>
											<s:textfield onblur="checkLicense()" name="companyInfoDTO.companyId" maxlength="20" id="companyId" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>企业证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidEdt" id="ctidEdt" cssClass="Wdate" readonly="true"/>
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
											<s:textfield name="companyInfoDTO.email" maxlength="32" id="email" maxLength="16" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.corpName" maxlength="20" id="corpName" readonly="true" cssClass="readonly"/>
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
											<s:hidden name="companyInfoDTO.corpGender" id="corpGender" />
										    <edl:entityDictList displayName="companyInfoDTO.corpGender"
												dictValue="${companyInfoDTO.corpGender}" dictType="10002" props="id=\"corpGender1\"disabled=\"true\"" 
												tagType="2" defaultOption="false"/>
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
											<s:hidden name="companyInfoDTO.corpCredType" id="corptype" />
										    <edl:entityDictList displayName="companyInfoDTO.corpCredType"
												dictValue="${companyInfoDTO.corpCredType}" dictType="140" props="id=\"corptype1\"disabled=\"true\"" 
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
											<span class="no-empty">*</span>法定代表人证件号：
										</td>
										<td>
										    <s:textfield  onblur="checksub('corptype','corpCredId')" maxlength="18" name="companyInfoDTO.corpCredId" id="corpCredId" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.corpCredValidity" id="corpCredValidity" readonly="true" cssClass="Wdate"/>
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
											<s:textfield name="companyInfoDTO.corpAddress" maxlength="64" id="corpAddress" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.corpCountyr" id="corpCountyr" readonly="true" cssClass="readonly"/>
											<%-- <edl:entityDictList displayName="companyInfoDTO.corpCountyr"
												dictValue="${companyInfoDTO.corpCountyr}" dictType="450" props="id=\"corpCountyr\"disabled=\"true\""
												tagType="2" defaultOption="false" /> --%>
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
											<s:textfield name="companyInfoDTO.corpTelephoneNumber" maxlength="11" id="corpTelephoneNumber" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.companyAccountant" maxlength="32" id="companyAccountant" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>注册资本：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.registeredCapital" maxlength="32" id="registeredCapital" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>企业规模：
										</td>
										<td>
											<s:hidden name="companyInfoDTO.companySize" id="companySize" />
											<edl:entityDictList displayName="companyInfoDTO.companySize"
												dictValue="${companyInfoDTO.companySize}" dictType="194" props="id=\"companySize1\"disabled=\"true\"" 
												tagType="2" defaultOption="false" />
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
											<s:textfield name="companyInfoDTO.postcode" maxlength="32" id="postcode" readonly="true" cssClass="readonly"/>
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
											<s:hidden name="companyInfoDTO.companyFax" id="companyFax" />
											<edl:entityDictList displayName="companyInfoDTO.companyFax"
												dictValue="${companyInfoDTO.companyFax}" dictType="400"
												tagType="2" defaultOption="false" props="id=\"companyFax1\"disabled=\"true\""/>
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
											<s:textfield name="companyInfoDTO.corpBirthday" id="corpBirthday" readonly="true" cssClass="Wdate"/>
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
												tagType="2" defaultOption="true" props="id=\"corpProfession\""/>
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
											<s:textfield name="companyInfoDTO.relName" maxlength="64" id="relName" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>控股股东或实际控制人证件类型:
										</td>
										<td>
											<s:hidden name="companyInfoDTO.citp" id="controlcitp" />
											<edl:entityDictList displayName="companyInfoDTO.citp"
												dictValue="${companyInfoDTO.citp}" dictType="140" props="id=\"controlcitp1\"disabled=\"true\"" 
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
											<span class="no-empty">*</span>控股股东或实际控制人证件号:
										</td>
										<td>
											<s:textfield  onblur="checksub('controlcitp','ctid')" maxlength="18" name="companyInfoDTO.ctid" id="ctid" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.citpNt" maxlength="15" id="citpNt" readonly="true" cssClass="readonly"/>
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
						<%--  <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.ctidEndValidity" maxlength="16" id="ctidEndValidity" readonly="true" cssClass="Wdate"/>
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
											<s:textfield name="companyInfoDTO.holdPer" maxlength="16" id="holdPer" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.holdAmt" maxlength="16" id="holdAmt" readonly="true" cssClass="readonly"/>
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
												cols="70" id="companyDescription" readonly="true" cssClass="readonly"></s:textarea>
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
											<span class="no-empty">*</span>经(代)办人证件类型：
										</td>
										<td>
											<s:hidden name="companyInfoDTO.operatorType" id="operatorType" />
											<edl:entityDictList displayName="companyInfoDTO.operatorType"
												dictValue="${companyInfoDTO.operatorType}" dictType="140" props="id=\"operatorType1\"disabled=\"true\"" 
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
											<span class="no-empty">*</span>经(代)办人证件号：
										</td>
										<td>
											<s:textfield onblur="checksub('operatorType','operatorId')"  maxlength="18" name="companyInfoDTO.operatorId" id="operatorId" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>经(代)办人证件有效期：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorValidity" id="operatorValidity" readonly="true" cssClass="Wdate"/>
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
											<span class="no-empty">*</span>经(代)办人姓名：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorName" maxlength="32" id="operatorName" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>经(代)办人联系电话：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.operatorTelephoneNumber" maxlength="11" id="operatorTelephoneNumber" readonly="true" cssClass="readonly"/>
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
											<s:textfield name="companyInfoDTO.operatorAddress" maxlength="64" id="operatorAddress" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>银行账户号：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.bankAccount" maxlength="30" id="bankAccount" readonly="true" cssClass="readonly"/>
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
											<span class="no-empty">*</span>开户银行名称：
										</td>
										<td>
											<s:textfield name="companyInfoDTO.bankName" maxlength="15" id="bankName" readonly="true" cssClass="readonly"/>
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
			
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="sub2();" value="提 交" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.location = '${ctx}/cardholder/inqueryShow.action'"
											value="返 回" />
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