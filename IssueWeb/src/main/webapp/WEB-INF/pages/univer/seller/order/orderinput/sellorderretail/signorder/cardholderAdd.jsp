<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>订单管理</title>		
		<script type="text/javascript">
			function addSubmit(){
				var name=document.getElementById("firstName").value;
				var idNo=document.getElementById("idNo").value;
				var num=document.getElementById("num").value;
				if(name==null || name==""){
					errorDisplay("持卡人姓名必须填写!");
					return;
				}
				if(idNo==null || idNo==""){
					errorDisplay("证件号必须填写!");
					return;
				}
				if(num==null || num==""){
					errorDisplay("卡片张数必须填写!");
					return;
				}
				document.cardholderForm.action='${ctx}/sellOrderRetailSignAction!insertCardholder';
				cardholderForm.submit();
			}
		
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加记名订单明细信息</span>
		</div>
		<s:form id="cardholderForm" name="cardholderForm"
			action="cardholder/insert.action" method="post">
			<s:hidden name="cardholderDTO.fatherEntityId" id="fatherEntityId"></s:hidden>
			<s:hidden name="sellOrderDTO.orderId" ></s:hidden>	
			<s:hidden name="sellOrderDTO.firstEntityId" ></s:hidden>		
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
												cssClass="readonly" readonly="true" />
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
											<s:textfield id="department"
												name="cardholderDTO.departmentId"
												readonly="true" cssClass="readonly" />									
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
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											手机号码：
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
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											<span style="color: red">*</span>卡片张数：
										</td>
										<td>
											<s:textfield name="num" id="num" value="1" />
											<s:fielderror>
												<s:param>num</s:param>
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
<!--						<tr>-->
<!--							<td>-->
<!--								<table style="text-align: left; width: 100%">-->
<!--									<tr>-->
<!--										<td style="width: 90px; text-align: right;">-->
<!--											失效时间：-->
<!--										</td>-->
<!--										<td>-->
<!--											<s:textfield name="cardholderDTO.closeDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">-->
<!--												<s:param name="value">-->
<!--													<s:date name="cardholderDTO.closeDate" format="yyyy-MM-dd" />-->
<!--												</s:param>-->
<!--											</s:textfield>-->
<!--										</td>-->
<!--									</tr>-->
<!--								</table>-->
<!--							</td>-->
<!--							<td>-->
<!--								<table style="text-align: left; width: 100%">-->
<!--									<tr>-->
<!--										<td style="width: 90px; text-align: right;">-->
<!--											状态：-->
<!--										</td>-->
<!--										<td>-->
<!--											<s:select list="#{1:'有效',0:'无效'}" name="cardholderDTO.cardholderState" />-->
<!--										</td>-->
<!--									</tr>-->
<!--								</table>-->
<!--							</td>-->
<!--						</tr>-->
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
											onclick="addSubmit();"
											value="提 交" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.close()"
											value="关 闭" />
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