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
		<title>添加个人持卡人信息</title>
		<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
		<script type="text/javascript">
	function choiceCustomer() {
		var customerDTO = window.showModalDialog(
				'${ctx}/customer/choicePer.action', '_blank',
				'center:yes;dialogHeight:600px;dialogWidth:850px;resizable:no');
		setDepartmentSelect(customerDTO);
	}
	
	var exitIdNo = false;
	function verifyIdNo()
	{
	    var flag=true;
		$("#idNo_msg").empty("");
		if (document.getElementById("idNo").value == "") {
			flag = false;
   		    $("#idNo_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入证件号！</span></li></ul>');
			return flag;
   		}
		var idNoList=document.getElementsByName("cardholderDTO.idNo");
		var idNo=idNoList[0].value;
		var paymentTypeList=document.getElementsByName("cardholderDTO.idType");
		var paymentType=paymentTypeList[0].value;
		if(paymentType=="1"){
			if(idNo!=""){
				if(IdCardValidate(idNo)!=true){
					document.getElementById("idNo").value="";
					$("#idNo_msg")
					.html(
							'<ul id="cardholderForm_" class="errorMessage"><li><span>身份证格式错误！</span></li></ul>');
				    flag=false;
				}else{
					ajaxRemote('${ctx}/cardholder/checkIdNo.action', {'cardholderDTO.idNo' : idNo,'cardholderDTO.idType' : paymentType}, successFn, 'json');
					flag=true;
					return  flag;
				}
			}
		}else if(paymentType=="2"){
			var passport =/^[0-9a-zA-Z]*$/;
			if(passport.test(idNo)!=true){
				document.getElementById("idNo").value="";
				$("#idNo_msg")
				.html(
						'<ul id="cardholderForm_" class="errorMessage"><li><span>护照格式错误！</span></li></ul>');
				flag=false;
			}else{
				ajaxRemote('${ctx}/cardholder/checkIdNo.action', {'cardholderDTO.idNo' : idNo,'cardholderDTO.idType' : paymentType}, successFn, 'json');
				flag=true;
				return  flag;
			} 
			
		}else{
			ajaxRemote('${ctx}/cardholder/checkIdNo.action', {'cardholderDTO.idNo' : idNo,'cardholderDTO.idType' : paymentType}, successFn, 'json');
			flag=true;
			return  flag;
		}
	
	}
	
	function successFn(res) {
		if (res == true) {
			exitIdNo = false;
			document.getElementById("idNo").value="";
			alert("证件号已存在！");
			$("#idNo_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>证件号已存在！</span></li></ul>');
		} else {
			exitIdNo = true;
		}
	}
	
	function setDepartmentSelect(customerDTO) {
		if (customerDTO != null) {
			maskDocAllWithMessage("Wait...");
			document.getElementById('entityId').value = customerDTO['entityId'];
			document.getElementById('customerName').value = customerDTO['customerName'];
			document.getElementById('fatherEntityId').value = customerDTO['fatherEntityId'];
			document.getElementById('mailingAddress').value=customerDTO['customerAddress'];
			
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
		}
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
					var customerDTO = Ext.util.JSON.decode(response.responseText);
					setDepartmentSelect(customerDTO);
				}
			});
		}
	}
	function checkMailingAddress(){
		var flag=true;
		var mailingAddress=document.getElementById("mailingAddress").value;
		var byteLength=mailingAddress.replace(/[^x00-xFF]/g,'**').length;
		 if(byteLength>512)
			 {
			 errorDisplay("请填写邮寄地址(512位内)");
			     flag=false;
			     return  flag;
			 }		
	}
	
	
	function submit1(){	
		hasNull = false;
		if(document.getElementById('driverLicence').value !=null && document.getElementById('driverLicence').value !=""){
			var regx = /^[0-9a-zA-Z/-]+$/;
			var driverLicence =document.getElementById('driverLicence').value;
			var driverLicenceflag = regx.test(driverLicence);
			if(driverLicenceflag==false){
				alert("驾驶证号只能填写数字或字符！");
				return;
			}
		}
		if(document.getElementById('cardholderMobile').value !=null && document.getElementById('cardholderMobile').value !=""){
			var reg = /^[0-9]*$/;
			var phoneNum = document.getElementById("cardholderMobile").value;
			var flagPhone = reg.test(phoneNum); //true
	    	if(flagPhone==false){
	    		alert("手机号码只能输入数字！");
	    		return;
	    	}  
		}		
		
		//if(verifyIdNo()==false){
		//	return ;
		//}
		if(checkMailingAddress()==false){
			return ;
		}
		if(hasNull){
			return ;
		}
		//if(exitIdNo == false){
		//	return ;
		//}
		
		//this.disabled='disabled';
		submitForm('cardholderForm', '${ctx}/cardholder/insertcardholder.action');
		//var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');
	} 
</script>
	</head>
	<body onload="setCustomerDTO();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加个人持卡人信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm"
			action="cardholder/insert.action" method="post">
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
											<s:textfield id="entityId" name="cardholderDTO.entityId"
												cssClass="watch" readonly="true" onclick="choiceCustomer()" />
											<s:fielderror>
												<s:param>cardholderDTO.entityId</s:param>
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
												name="cardholderDTO.customerDTO.customerName"
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
							<%-- <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											外部系统代码：
										</td>
										<td>
											<s:textfield name="cardholderDTO.externalId" maxlength="50" />
											<s:fielderror>
												<s:param>cardholderDTO.externalId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td> --%>
						</tr>
					</table>
				</div>
			</div>
			<div id="realNameInfo"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="realNameInfoTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">实名信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="realNameInfoTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											持卡人编号：
										</td>
										<td>
											<s:textfield name="cardholderDTO.cardholderId"
												readonly="true" cssClass="readonly" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>姓名：
										</td>
										<td>
											<s:textfield id="firstName" name="cardholderDTO.firstName" maxlength="20" />
											<s:fielderror>
												<s:param>cardholderDTO.firstName</s:param>
											</s:fielderror>
											<div id="firstName_msg"></div>
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
											<span style="color: red">*</span>证件类型：
										</td>
										<td>
											<edl:entityDictList  displayName="cardholderDTO.idType" props="id=\"paymentType\""
												dictValue="${cardholderDTO.idType}" dictType="140"
												tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param>cardholderDTO.idType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>证件号：
										</td>
										<td>											
											<s:textfield id="idNo" onblur="verifyIdNo();" name="cardholderDTO.idNo" maxlength="18" />
											<s:fielderror>
												<s:param>cardholderDTO.idNo</s:param>
											</s:fielderror>
											<div id="idNo_msg"></div>
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
											 <span style="color: red">*</span> 证件失效日期：
										</td>
										<td>
											<s:textfield name="cardholderDTO.validity" id="validity"
												onclick="dateClick(this)" cssClass="Wdate"
												cssStyle="cursor:pointer">
											</s:textfield>
											<s:fielderror>
													<s:param>cardholderDTO.validity</s:param>
											</s:fielderror>
											<div id="validity_msg"></div>
										 </td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>国籍：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.country"
												dictValue="${cardholderDTO.country}" dictType="450" props="id=\"type\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>cardholderDTO.country</s:param>
											</s:fielderror>
											<div id="country_msg"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
										<td style="width: 90px; text-align: right;">
												城市：
										</td>
										<td>
											<s:textfield name="cardholderDTO.city"  id="city" maxlength="15">
											</s:textfield>
											<s:fielderror>
												<s:param>cardholderDTO.city</s:param>
											</s:fielderror>
											<div id="city_msg"></div>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											 <span style="color: red">*</span> 学历：
										</td>
										<td>
											<edl:entityDictList  displayName="cardholderDTO.cardholderEducation" props="id=\"paymentType\""
											dictValue="7" dictType="116" tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderEducation</s:param>
											</s:fielderror>
											<div id="cardholderEducation_msg"></div>
										 </td>
									</tr>
								</table>
							</td>
						</tr>						
						<tr>
							<td>
								<table>
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span> 民族：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.cardholderNation"
												dictValue="${cardholderDTO.cardholderNation}" dictType="451" props="id=\"type\""
												tagType="2" defaultOption="false" />										
											<s:fielderror>
												<s:param>cardholderDTO.cardholderNation</s:param>
											</s:fielderror>
											<div id="cardholderNation_msg"></div>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											 <span style="color: red">*</span> 婚姻状况：
										</td>
										<td>
											<edl:entityDictList  displayName="cardholderDTO.cardholderMarriage" props="id=\"paymentType\""
											dictValue="5" dictType="117" tagType="2" defaultOption="false" />
											<s:fielderror>
													<s:param>cardholderDTO.cardholderMarriage</s:param>
											</s:fielderror>
											<div id="cardholderMarriage_msg"></div>
										 </td>
									</tr>
								</table>
							</td>
						</tr>						
						<tr>
							<td>
								<table>
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>职业：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.profession"
										dictValue="${cardholderDTO.profession}" dictType="145"
										tagType="2" defaultOption="true" />
									<s:fielderror>
										<s:param>cardholderDTO.profession</s:param>
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
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>手机号码：
										</td>
										<td>
											<s:textfield id="cardholderMobile" name="cardholderDTO.cardholderMobile" maxlength="11" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderMobile</s:param>
											</s:fielderror>
											<div id="cardholderMobile_msg"></div>
										</td>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>邮寄地址：
										</td>
										<td>
											<s:textfield name="cardholderDTO.mailingAddress"  id="mailingAddress"  onchange="checkMailingAddress();" maxlength="75"/>
											<s:fielderror>
												<s:param>cardholderDTO.mailingAddress</s:param>
											</s:fielderror>
											<div id="mailingAddress_msg"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="personalInfo"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="personalInfoTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">个人信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="personalInfoTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>性别：
										</td>
										<td>
											<edl:entityDictList
												displayName="cardholderDTO.cardholderGender"
												dictValue="${cardholderDTO.cardholderGender}" dictType="10002"
												tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderGender</s:param>
											</s:fielderror>
										</td>										
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>出生日期：
										</td>
										<td>
											<s:textfield name="cardholderDTO.cardholderBirthday"
												onclick="dateClick(this)" cssClass="Wdate"
												cssStyle="cursor:pointer">
											</s:textfield>
											<s:fielderror>
												<s:param>cardholderDTO.cardholderBirthday</s:param>
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
										<td style="width: 90px; text-align: right;">
											邮箱地址：
										</td>
										<td>
											<s:textfield name="cardholderDTO.cardholderEmail" maxlength="32" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderEmail</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											持卡人分类：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.cardholderSegment"
												dictValue="${cardholderDTO.cardholderSegment}"
												dictType="103" tagType="2" defaultOption="true" />
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
											称谓：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.cardholderSalutation"
												dictValue="${cardholderDTO.cardholderSalutation}"
												dictType="104" tagType="2" defaultOption="true" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											职位：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.cardholderFunction"
												dictValue="${cardholderDTO.cardholderFunction}"
												dictType="105" tagType="2" defaultOption="true" />
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
											失效时间：
										</td>
										<td>
											<s:textfield name="cardholderDTO.closeDate"
												onclick="dateClick(this)" cssClass="Wdate"
												cssStyle="cursor:pointer">
											</s:textfield>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											状态：
										</td>
										<td>
											<s:select list="#{1:'有效',0:'无效'}"
												name="cardholderDTO.cardholderState" />
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
										车架号：
										</td>
										<td>
											<s:textfield name="cardholderDTO.v_Id" maxlength="15" />
											<s:fielderror>
												<s:param>cardholderDTO.v_Id</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											车牌号：
										</td>
										<td>
											<s:textfield name="cardholderDTO.plateNumber" maxlength="7" />
											<s:fielderror>
												<s:param>cardholderDTO.plateNumber</s:param>
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
										<td style="width: 90px; text-align: right;">
										驾驶证号：
										</td>
										<td>
											<s:textfield name="cardholderDTO.driverLicence" maxlength="18" id="driverLicence" />
											<s:fielderror>
												<s:param>cardholderDTO.driverLicence</s:param>
											</s:fielderror>
											<div id="driverLicence_msg"></div>
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
											<edl:entityDictList displayName="cardholderDTO.isblacklist"
												dictValue="${cardholderDTO.isblacklist}" dictType="196"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>cardholderDTO.isblacklist</s:param>
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
											<edl:entityDictList displayName="cardholderDTO.riskGrade"
												dictValue="${cardholderDTO.riskGrade}" dictType="197"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>cardholderDTO.riskGrade</s:param>
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
											备注：
										</td>
										<td>
											<s:textarea name="cardholderDTO.cardholderComment" onpropertychange="if(value.length>200) value=value.substr(0,200)" rows="5"
												cols="70" >
											</s:textarea>
											<s:fielderror>
												<s:param>cardholderDTO.cardholderComment</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="submit1();"
											value="提 交" />
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