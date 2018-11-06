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
		<title>订单编辑中添加持卡人信息</title>
		<script type="text/javascript">
	function choiceCustomer() {
		var customerDTO = window.showModalDialog(
				'${ctx}/customer/choiceInOrder.action?customerDTO.entityId=${customerDTO.entityId}', '_blank',
				'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
		setDepartmentSelect(customerDTO);
	}
	function setDepartmentSelect(customerDTO) {
		if (customerDTO != null) {
			maskDocAllWithMessage("Wait...");
			document.getElementById('entityId').value = customerDTO['entityId'];
			alert(customerDTO['customerName']);
			document.getElementById('customerName').value = customerDTO['customerName'];
			document.getElementById('fatherEntityId').value = customerDTO['fatherEntityId'];
			document.getElementById('mailingAddress').value = customerDTO['customerAddress'];
			var departmentSelect = document.getElementById('department');
			departmentSelect.innerHTML = '';
			for ( var i = 0; i < customerDTO['departmentList'].length; i++) {
				var departmentDTO = customerDTO['departmentList'][i];
				var opt = document.createElement('option');
				opt.value = departmentDTO['departmentId'];
				opt.innerHTML = departmentDTO['departmentName'];
				departmentSelect.appendChild(opt);
			}
			//var defaultopt = document.createElement('option');
			//defaultopt.value = '';
			//defaultopt.innerHTML = '';
			//departmentSelect.appendChild(defaultopt);

			departmentSelect.disabled = false;
			unmaskDocAll();
		}
	}
	function setCustomerDTO() {
			alert()
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
	var exitIdNo = false;
	function verifyIdNo() {
		var flag = true;
		$("#idNo_msg").empty("");
		var idNo = document.getElementById("idNo").value;
		var type = document.getElementById("paymentType").value;
		if (document.getElementById("idNo").value == "") {
			flag = false;
			$("#idNo_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入身份证号！</span></li></ul>');
		}
		if (flag) {
			if (type == 1) {
				var idNoRegFlag = IdCardValidate(idNo);
				if (idNoRegFlag != true) {
					document.getElementById("idNo").value = "";
					return idNoRegFlag;
				}
				ajaxRemote('${ctx}/cardholder/checkIdNo.action', {'cardholderDTO.idNo' : idNo,'cardholderDTO.idType' : type}, successFn, 'json');
			}
		}
		return flag;
		
	}

	function successFn(res) {
		$("#idNo_msg").empty("");
		if (res == true) {
			exitIdNo = false;
			$("#idNo_msg")
					.html(
							'<ul id="cardholderForm_" class="errorMessage"><li><span>该身份证号已存在！</span></li></ul>');
		} else {
			exitIdNo = true;
		}
	}
	function checkMailingAddress()
	{
		var flag=true;
		var mailingAddress=document.getElementById("mailingAddress").value;
	/* 	if(mailingAddress=="")
			{
			errorDisplay("邮寄地址不能为空！");
			 flag=false;
			 return flag;
			} */
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
		$("#firstName_msg").empty("");
		$("#cardholderMobile_msg").empty("");
		$("#entityId_msg").empty("");
		$("#mailingAddress_msg").empty("");
		$("#validity_msg").empty("");
		alert(1);
		if (document.getElementById("firstName").value == "") {
			hasNull = true;
			$("#firstName_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入姓名！</span></li></ul>');
		}
		if (document.getElementById("cardholderMobile").value == "") {
			hasNull = true;
			$("#cardholderMobile_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入手机号码！</span></li></ul>');
		}
		if (document.getElementById("entityId").value == "") {
			hasNull = true;
			$("#entityId_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入客户号！</span></li></ul>');
		}
		if (document.getElementById("mailingAddress").value == "") {
			hasNull = true;
			$("#mailingAddress_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入邮寄地址！</span></li></ul>');
		}
		alert(2);
		if (document.getElementById("validity").value == "") {
			hasNull = true;
			$("#validity_msg").html('<ul id="cardholderForm_" class="errorMessage"><li><span>请输入证件失效日期</span></li></ul>');
		}
		alert(3);
		if(verifyIdNo()==false)
		{
			return ;
		}
		if(checkMailingAddress()==false)
		{
			return ;
		}
		if(hasNull){
			return ;
		}
		if(exitIdNo == false){
			return ;
		}
		document.getElementById("cardholderEntityId").value = ${customerDTO.entityId};
		document.cardholderForm.action = "${ctx}/cardholder/insertInOrder.action";
	  	document.cardholderForm.submit();
	}
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加持卡人信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm"
			action="cardholder/insertInOrder.action" method="post">
			<s:hidden name="cardholderDTO.fatherEntityId" id="fatherEntityId"></s:hidden>
			<s:hidden name="sellOrderDTO.firstEntityId" ></s:hidden>
			<s:hidden name="sellOrderDTO.orderId" ></s:hidden>
			<s:hidden name = "cardholderDTO.entityId"  id="cardholderEntityId"></s:hidden>
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
												<s:textfield id = "entityId" name = "customerDTO.entityId" readonly="true" cssClass = "readonly"></s:textfield>
											<s:fielderror>
												<s:param>cardholderDTO.entityId</s:param>
											</s:fielderror>
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
												name="customerDTO.customerName"
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
										<s:select id="departmentId" 
												                   list="departmentLists"
												                   name="cardholderDTO.departmentId" 
												            listKey="entityId"
												            listValue="entityName"></s:select>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											外部系统代码：
										</td>
										<td>
											<s:textfield name="cardholderDTO.externalId" maxlength="80" />
											<s:fielderror>
												<s:param>cardholderDTO.externalId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
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
											<s:textfield id="firstName" name="cardholderDTO.firstName" />
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
											<edl:entityDictList displayName="cardholderDTO.idType"
												dictValue="${cardholderDTO.idType}" dictType="401"
												tagType="2" defaultOption="false" props="id=\"paymentType\"" />
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
											<s:textfield name="cardholderDTO.idNo" id="idNo" onblur="verifyIdNo()" />
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
															<span style="color: red">*</span>证件失效日期：
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
															国家：
														</td>
														<td>
																<s:textfield name="cardholderDTO.country"  id="country">
																</s:textfield>
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
														<s:textfield name="cardholderDTO.city"  id="city">
																</s:textfield>
																<s:fielderror>
																		<s:param>cardholderDTO.city</s:param>
																</s:fielderror>
																<div id="city_msg"></div>
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
															行业：
													</td>
													<td>
														<edl:entityDictList displayName="cardholderDTO.industry"
												dictValue="${cardholderDTO.industry}" dictType="400"
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.industry</s:param>
											</s:fielderror>
													</td>
											</tr>
									</table>
							</td>
							<td>
									<table>
											<tr>
													<td style="width: 90px; text-align: right;">
															职业：
													</td>
													<td>
														<edl:entityDictList displayName="cardholderDTO.profession"
												dictValue="${cardholderDTO.profession}" dictType="145"
												tagType="2" defaultOption="false" />
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
											<s:textfield id="cardholderMobile" name="cardholderDTO.cardholderMobile" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderMobile</s:param>
											</s:fielderror>
											<div id="cardholderMobile_msg"></div>
										</td>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>邮寄地址：
										</td>
										<td>
											<s:textfield name="customerDTO.customerAddress"   id="mailingAddress"  onchange="checkMailingAddress();" />
											<s:fielderror>
												<s:param>customerDTO.customerAddress</s:param>
											</s:fielderror>
											<div id="mailingAddress_msg"></div>
											<%-- <input  name="cardholderDTO.mailingAddress" value="${customerDTO.customerAddress}" id="mailingAddress" onchange="checkMailingAddress();"/> --%>
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
											性别：
										</td>
										<td>
											<edl:entityDictList
												displayName="cardholderDTO.cardholderGender"
												dictValue="${cardholderDTO.cardholderGender}" dictType="402"
												tagType="2" defaultOption="false" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											出生日期：
										</td>
										<td>
											<s:textfield name="cardholderDTO.cardholderBirthday"
												onclick="dateClick(this)" cssClass="Wdate"
												cssStyle="cursor:pointer">
											</s:textfield>
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
											<s:textfield name="cardholderDTO.cardholderEmail" maxlength="30" />
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
											<dl:dictList displayName="cardholderDTO.cardholderSegment"
												dictValue="${cardholderDTO.cardholderSegment}"
												dictType="103" tagType="2" defaultOption="false" />
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
											<dl:dictList displayName="cardholderDTO.cardholderSalutation"
												dictValue="${cardholderDTO.cardholderSalutation}"
												dictType="104" tagType="2" defaultOption="false" />
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
											<dl:dictList displayName="cardholderDTO.cardholderFunction"
												dictValue="${cardholderDTO.cardholderFunction}"
												dictType="105" tagType="2" defaultOption="false" />
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
												<s:param name="value">
													<s:date name="cardholderDTO.closeDate" format="yyyy-MM-dd" />
												</s:param>
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
											<s:textfield name="cardholderDTO.v_Id" maxlength="100" />
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
											<s:textfield name="cardholderDTO.plateNumber" maxlength="80" />
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
											<s:textfield name="cardholderDTO.driverLicence" maxlength="100" />
											<s:fielderror>
												<s:param>cardholderDTO.driverLicence</s:param>
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
												cols="70"></s:textarea>
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
											onclick="window.close();"
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