<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>添加持卡人信息</title>
		<script type="text/javascript">
	function choiceCustomer() {
		var customerDTO = window.showModalDialog(
				'${ctx}/customer/choice.action', '_blank',
				'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
		setDepartmentSelect(customerDTO);
	}
	function setDepartmentSelect(customerDTO) {
		if (customerDTO != null) {
			maskDocAllWithMessage("Wait...");
			document.getElementById('entityId').value = customerDTO['entityId'];
			document.getElementById('customerName').value = customerDTO['customerName'];
			document.getElementById('fatherEntityId').value = customerDTO['fatherEntityId'];
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
	function sub(){
	var entityId=document.getElementById("entityId").value
	var firstName=document.getElementById("firstName").value
	var idNo=document.getElementById("idNo").value
	  if(null==entityId||''==entityId){
	  alert("请选择客户号！");
	  return;
	  }
	  if(null==firstName||''==firstName){
	  alert("持卡人姓名必须输入！"); 
	  return;
	  }
	  if(null==idNo||''==idNo){
	  alert("证件号码必须输入！");
	  return;
	  }
	cardholderForm.submit();
	window.returnValue="submit";
    window.close();
	}
</script>
	</head>
	<body onload="setCustomerDTO();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加持卡人信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm"
			action="cardManage/signCardRltCardHolder.action" method="post">
			<s:hidden name="cardholderDTO.fatherEntityId" id="fatherEntityId"></s:hidden>
			<s:hidden name="cardManagementDTO.transferOutCard" ></s:hidden>
			<s:hidden name="cardManagementDTO.creditAmont" ></s:hidden>
			<s:hidden name="cardManagementDTO.transferInCard" ></s:hidden>
			<s:hidden name="cardManagementDTO.flag" ></s:hidden>
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
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											外部系统代码：
										</td>
										<td>
											<s:textfield name="cardholderDTO.externalId" />
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
											<s:textfield name="cardholderDTO.firstName" id="firstName"/>
											<s:fielderror>
												<s:param>cardholderDTO.firstName</s:param>
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
											<span style="color: red">*</span>证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="cardholderDTO.idType"
												dictValue="${cardholderDTO.idType}" dictType="401"
												tagType="2" defaultOption="false" />
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
											<s:textfield name="cardholderDTO.idNo" id="idNo"/>
											<s:fielderror>
												<s:param>cardholderDTO.idNo</s:param>
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
										<span style="color: red">*</span>	手机号码：
										</td>
										<td>
											<s:textfield name="cardholderDTO.cardholderMobile" />
											<s:fielderror>
												<s:param>cardholderDTO.cardholderMobile</s:param>
											</s:fielderror>
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
											<s:textfield name="cardholderDTO.cardholderEmail" />
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
							<td colspan="2">
								<table style="text-align: left; width: 100%">
									<tr>
										<td
											style="width: 90px; text-align: right; vertical-align: top;">
											备注：
										</td>
										<td>
											<s:textarea name="cardholderDTO.cardholderComment" rows="5"
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
											onclick="sub();"
											value="提 交" />
									</td>
									<td>
										 <button class='bt' style="float: right; margin: 5px" onclick="window.close()">关 闭</button>
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