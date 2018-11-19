<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<div class="TitleHref">
			<span>卡邮寄审核详细信息</span>
		</div>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		
		
		function confirm() {
			document.EditForm.action="cardMailAction!submit";
			document.EditForm.submit();
		}
		/* function reject() {
			document.EditForm.action="cardMailAction!sendBack";
			document.EditForm.submit();
		} */
		
		</script>
	</head>
		<body >
		<s:form id="newForm" name="newForm"
			action="" method="post">
			<s:hidden name="ApplyAndBindCardDTO.idType" />
			<s:hidden name="errorjsp" />
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
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
								<table width="100%">
									
											
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											持卡人名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.firstName"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap"
											id="deliveryFeeTd1">
											证件类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<dl:dictList displayName="ApplyAndBindCardDTO.idType"
												dictType="205" dictValue="${ApplyAndBindCardDTO.idType}"
												tagType="1"  />
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											证件号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.idNo"/>
										</td>
										
										
										
										<td align="right" width="15%" nowrap="nowrap">
											持卡人生日：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderBirthday"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											持卡人性别：
											<s:select list="#{'0':'女','1':'男'}"
															name="ApplyAndBindCardDTO.cardholderGender"
															id="ApplyAndBindCardDTO.cardholderGender" label="持卡人性别："
															disabled="true" />
										</td>
										<%-- <td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderGender"/>
										</td> --%>
										
										<td align="right" width="15%" nowrap="nowrap">
											持卡人称谓：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderSalutation"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											持卡人职位：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.cardholderFunction"/>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											渠道信息：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.postCode"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
					
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="ApplyAndBindCardDTO.cardholderComment" cols="70" rows="5" readonly="true"></s:textarea>
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
										<td align="right" width="15%" nowrap="nowrap">
											邮编：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
												<s:label name="ApplyAndBindCardDTO.channel"/>
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
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm" action="cardInformationAction!operate">
								<s:hidden name="ApplyAndBindCardDTO.idNo" id="ID_NO"/>
								<s:hidden name="ApplyAndBindCardDTO.idType" id="ID_TYPE"/>
								<s:hidden name="operation" id="operation" />
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn' style="float: right; margin: 7px"
												onclick="confirm()">
												审核通过
											</button>
											<display:security urlId="312031">
											<button class='btn' style="float: right; margin: 7px"
												onclick="reject();">
												审核拒绝
											</button>
											</display:security>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		<br>
</body>
</html>