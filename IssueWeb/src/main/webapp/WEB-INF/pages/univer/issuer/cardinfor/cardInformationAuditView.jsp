<%@page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<div class="TitleHref">
			<span>卡信息审核详细信息</span>
		</div>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		
		
		function confirm() {
			var cardholderComment = document.getElementById("cardholderComment").value;
			if(cardholderComment.length > 50){
				alert("备注不能大于50位"); 
 				return;
			}
			maskDocAll();
			document.EditForm.action="cardInformationAction!submit?applyAndBindCardDTO.cardholderComment="+cardholderComment;
			document.EditForm.submit();
		}
		function reject() {
			var cardholderComment = document.getElementById("cardholderComment").value;
// 			if (cardholderComment !== null || cardholderComment !== undefined || cardholderComment !== '') { 
// 				alert("请输入拒绝理由"); 
// 				return;
// 			} 
			if (cardholderComment.length == 0){
				alert("请输入拒绝理由"); 
 				return;
			} else if(cardholderComment.length > 50){
				alert("拒绝理由不能大于50位"); 
 				return;
			}
			maskDocAll();
			document.EditForm.action="cardInformationAction!sendBack?applyAndBindCardDTO.cardholderComment="+cardholderComment;
			document.EditForm.submit();
		}
		
		</script>
	</head>
		<body >
		<s:form id="newForm" name="newForm"
			action="" method="post">
			<s:hidden name="ApplyAndBindCardDTO.idType" />
			<s:hidden name="ApplyAndBindCardDTO.applyDateSeconds" />
			<s:hidden name="errorjsp" />
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
									<tr height="35">
										<td width="150" align=right nowrap="nowrap">
											持卡人名称：
										</td>
										<td width="160" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.firstName"/>
										</td>
										
										<td width="150" align=right nowrap="nowrap" id="deliveryFeeTd1">
											<span style="color: red;">*</span>证件类型：
										</td>
										<td width="160" nowrap="nowrap">
												<%-- <dl:dictList displayName="ApplyAndBindCardDTO.idType"
												dictType="205" dictValue="${ApplyAndBindCardDTO.idType}"
												tagType="1"  /> --%>
												<dl:dictList displayName="idType" dictType="401"
																dictValue="${ApplyAndBindCardDTO.idType}" tagType="1" />
										</td>
										
										<td width="150" align=right nowrap="nowrap">
											证件号：
										</td>
										<td width="160" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.idNo"/>
										</td>
									</tr>
									<tr height="35">
										<td  width="150" align=right nowrap="nowrap">
											持卡人生日：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderBirthday"/>
										</td>
										<td width="150" align=right nowrap="nowrap">
											持卡人性别：
										</td>
										<td width="120">
											<s:select list="#{'0':'女','1':'男'}"
															name="ApplyAndBindCardDTO.cardholderGender"
															id="ApplyAndBindCardDTO.cardholderGender" label="持卡人性别："
															disabled="true" />
										</td>
										<td width="150" align=right nowrap="nowrap">
											持卡人称谓：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderSalutation"/>
										</td>
									</tr>
									<tr height="35">
										<td width="150" align=right nowrap="nowrap">
											持卡人职位：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderFunction"/>
										</td>
										<td width="150" align=right nowrap="nowrap">
											渠道信息：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.source"/>
										</td>
										<td width="150" align=right nowrap="nowrap">
											持卡人电话：
										</td>
										<td width="120" nowrap="nowrap">
										<s:label name="ApplyAndBindCardDTO.cardholderMobile"/>
											
										</td>
									</tr>
									<tr height="35">
										<td width="150" align=right nowrap="nowrap">
											车架号：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.vId"/>
										</td>
										<td width="150" align=right nowrap="nowrap">
											车牌号：
										</td>
										<td width="120" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.plateNumber"/> 
											
										</td>
										<td width="150" align=right nowrap="nowrap">
											驾驶证号：
										</td>
										<td width="120" nowrap="nowrap">
										<s:label name="ApplyAndBindCardDTO.driverLicence"/>
											
										</td>
									</tr>
									<tr height="35">
										<td width="150" align=right nowrap="nowrap">
											是否存在客户信息：
										</td>
										<td width="120" nowrap="nowrap">
										<s:if test="ApplyAndBindCardDTO.isCustomerInfo==1">
										是
										</s:if>
										<s:if test="ApplyAndBindCardDTO.isCustomerInfo==0">
										否
										</s:if>	
										</td>
										<td width="150" align=right nowrap="nowrap">
											是否有过发卡记录：
										</td>
										<td width="120" nowrap="nowrap">
										<s:if test="ApplyAndBindCardDTO.isIssue==1">
										是
										</s:if>
										<s:if test="ApplyAndBindCardDTO.isIssue==0">
										否
										</s:if>
										<s:if test="ApplyAndBindCardDTO.isCustomerInfo==0">
										否
										</s:if>	
										</td>
										<td width="150" align=right nowrap="nowrap">
										</td>
										<td width="120" nowrap="nowrap">
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
		
		<div style="width: 100%" align=center>
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="98%" height="10" align="center" valign="middle"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayTableBody();"
										style="cursor: pointer;">
										<span class="TableTop">邮寄信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							
							<div id="serviceTable">
								<table width="100%">
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											收件人名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.recipient_name"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											收件人地址：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.recipient_addr"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											收件人电话：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.recipient_phone"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											邮编：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.post_code"/>
										</td>
									</tr>
								</table>
							</div>
			</td></tr></table></div>
			
			<br/>
		<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="left" valign="middle" bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm" action="cardInformationAction!operate">
								<s:hidden name="ApplyAndBindCardDTO.idNo" id="ID_NO"/>
								<s:hidden name="ApplyAndBindCardDTO.idType" id="ID_TYPE"/>
								<s:hidden name="ApplyAndBindCardDTO.cardholderComment" id="CARDHOLDER_COMMENT"/>
								<s:hidden name="ApplyAndBindCardDTO.productId" id="PRODUCT_ID"/>
								<s:hidden name="ApplyAndBindCardDTO.cardholderMobile" id="CARD_HOLDER_MOBILE"/>
								<s:hidden name="ApplyAndBindCardDTO.applyDateSeconds" id="APPLY_DATE_SECONDS"/>
								<s:hidden name="operation" id="operation" />
								<table style="width:403px; ">
									<tr >
										<td align="right" width="15%" nowrap="nowrap" style="width: 130px; ">
											备注：
										</td>
					
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="ApplyAndBindCardDTO.cardholderComment" id="cardholderComment" cols="70" rows="5"></s:textarea>
										</td>
									</tr>
								</table>
							

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn' style="float: right; margin: 7px"
												onclick="confirm()">
												审核
											</button>
											<button class='btn' style="float: right; margin: 7px"
												onclick="reject();">
												审核拒绝
											</button>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		<br>
</body>
</html>