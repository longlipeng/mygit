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
<title>查看收单机构</title>


<script type="text/javascript">
		
	var isDisplay = false;
	function displayTable(divShow) {
		if (isDisplay) {
			display(divShow);
			isDisplay = false;
		} else {
			undisplay(divShow);
			isDisplay = true;
		}
	}


	//设置网站登录名和密码Div是否显示
	function setWebDiv() {
		document.getElementById('websiteId').style.display = 'none';
		if (document.getElementById('enableWebsite').checked) {
			document.getElementById('websiteId').style.display = 'block';
		}
	}


	function checkwebName() {
		var websiteUserName = document
				.getElementById("merchantDTO.websiteUserName").value;
		if (websiteUserName.search("[^\\s]") != 0) {
			document.getElementById("message").innerHTML = "登录名前后不能有空格";
			document.getElementById("merchantDTO.websiteUserName").value = "";
			return;
		}
		ajaxRemote('merchantManagement/checkWebName.action', {
			'merchantDTO.websiteUserName' : websiteUserName
		}, back, 'html');

	}
	function back(data) {
		if (data.indexOf("在") != -1) {
			document.getElementById("merchantDTO.websiteUserName").value = "";
		}
		document.getElementById("message").innerHTML = data;
	}

	function display() {
		var selectValue = document.getElementById("channelId").value;
		if (selectValue == '') {
			document.getElementById("time").style.visibility = "hidden";

		} else {
			document.getElementById("time").style.visibility = "";
		}
	}
   </script>
   </head>
   
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看收单机构</span>
		</div>
		<s:form id="newForm" name="newForm"
		  action="consumer/edit">
		  <s:hidden name="consumerDTO.entityId"></s:hidden>
		  <div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">收单机构信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						  </table>
						<div id="serviceTable">
						    <table width="100%" style="table-layout: fixed;">
									<tr>									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														收单机构号：
													</td>
													<td>
                                                      <s:textfield name="consumerDTO.entityId" disabled="true"></s:textfield>
														<s:fielderror>
															<s:param>
                                                              consumerDTO.entityId
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
                                                      <span class="no-empty">*</span>收单机构名称：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerName"
															id="consumerName"  disabled="true"></s:textfield>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerName
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														收单机构代码：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerCode" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerCode
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														收单机构英文名称：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerEnglishName" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerEnglishName
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>收单机构地址：
													</td>
													<td>
										             	<s:textfield name="consumerDTO.consumerAddress" disabled="true"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerAddress
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														收单机构英文地址：
													</td>
													<td>
													   <s:textfield name="consumerDTO.consumerEnglishAddress" disabled="true"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerEnglishAddress
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>收单机构邮编：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerPostcode" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerPostcode
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>收单机构电话：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerTelephone" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerTelephone
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>收单机构传真：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerFax" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerFax
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>收单机构网站：
													</td>
													<td>
										               <s:textfield name="consumerDTO.consumerWebsite" disabled="true"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerWebsite
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>收单机构规模：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerSize" disabled="true" />
														<s:fielderror>
															<s:param>
																consumerDTO.consumerSize
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														外部系统代码：
													</td>
													<td>
														<s:textfield name="consumerDTO.externalId"  disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.externalId
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>旧系统商户标识：
													</td>
													<td>
														<s:textfield name="consumerDTO.legacyMerchantId" disabled="true"/>
														<s:fielderror>
															<s:param>
																consumerDTO.legacyMerchantId
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														发票名称：
													</td>
													<td>
										               <s:textfield name="consumerDTO.invoiceName" disabled="true"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.invoiceName
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>结算代理：
													</td>
													<td>
														<edl:entityDictList displayName="consumerDTO.settAgencyId" dictValue="${consumerDTO.settAgencyId}" dictType="422" tagType="1" defaultOption="false" />
														<s:fielderror>
															<s:param>
																consumerDTO.settAgencyId
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>销售代表：
													</td>
													<td>
													  <s:select list="salesmanList" name="consumerDTO.salesmanId" listKey="userId" listValue="userName" headerKey="" headerValue="--请选择--" disabled="true"></s:select> 
                                                       <s:fielderror>
															<s:param>
																consumerDTO.salesmanId
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>加盟日期：
													</td>
													<td>
										              <s:textfield name="consumerDTO.joinDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer" disabled="true">
										                <s:param name="value"><s:date name="consumerDTO.joinDateDate" format="yyyy-MM-dd" /></s:param>
										              </s:textfield>
										            </td>
										             <td>														
                                                       <s:fielderror>
															<s:param>
														consumerDTO.joinDateDate
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>收单机构状态：
													</td>
													<td>
													   <s:property value="consumerDTO.consumerState == 1?'有效':'无效'"/>								
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerState
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty"></span>收单机构类型：
													</td>
													<td>
										               <edl:entityDictList displayName="consumerDTO.consumerType" dictValue="${consumerDTO.consumerType}" dictType="181" tagType="1" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerType
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														开通网站管理：
													</td>
													<td>
													  <s:property value="consumerDTO.enableWebsite == 1?'是':'否'"/>
                 
														<s:fielderror>
															<s:param>
																consumerDTO.enableWebsite
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														证书号：
													</td>
													<td>
                                                    	<s:textarea name="consumerDTO.certificateNo" cols="20" rows="5" disabled="true"></s:textarea>
                                                    	<s:fielderror>
                                                    		<s:param>
                                                    			consumerDTO.certificateNo
                                                    		</s:param>
                                                    	</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
							     </table>
							     <div id="websiteId">
								     <table style="text-align: left; width: 100%">
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站登录名：
														</td>
														<td>
											               <s:textfield name="consumerDTO.websiteUserName" id="consumerDTO.websiteUserName"  onblur="checkwebName();" disabled="true"/>
	                                                       <s:fielderror>
																<s:param>
																	consumerDTO.websiteUserName
																</s:param>
															</s:fielderror> 
															<div id="message"  style="color:red"/>
														</td>
														
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站密码：
														</td>
														<td>
															<s:password name="consumerDTO.websitePassword" id="consumerDTO.websitePassword" disabled="true"/>
															<s:fielderror>
																<s:param>
																	consumerDTO.websitePassword
																</s:param>
															</s:fielderror> 
														</td>
													</tr>
												</table>
											</td>
										</tr>
								     </table>
								  </div>
							</div>
							</td>
							</tr>  
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('paraTable');"
									style="cursor: pointer;">
									<span class="TableTop">参数设定</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="paraTable">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>结算手续费修改标志：
													</td>
													<td>	
													    <s:property value="consumerDTO.commissionFee == 1?'是':'否'"/>										
													
														<s:fielderror>
														<s:param>
																consumerDTO.commissionFee
														</s:param>
														</s:fielderror>																																
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>直接结算而无需核对结算单：
													</td>
													<td>
													   <s:property value="consumerDTO.reimburseWithoutCheck == 1?'是':'否'"/>		
													  
													    <s:fielderror>
														<s:param>
																consumerDTO.reimburseWithoutCheck
														</s:param>
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
													<td style="width: 170px; text-align: right;">
                                                      <span class="no-empty">*</span>收单机构消费暂停标志：
													</td>
													<td>
													    <s:property value="consumerDTO.purchasePause == 1?'是':'否'"/>									
													  
													    <s:fielderror>
														<s:param>
																consumerDTO.purchasePause
														</s:param>
														</s:fielderror>	
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>收单机构结算暂停标志：
													</td>
													<td>
													    <s:property value="consumerDTO.reimbursePause == 1?'是':'否'"/>
													    <s:fielderror>
														<s:param>
																consumerDTO.reimbursePause
														</s:param>
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
													<td style="width: 170px; text-align: right;">
														<span class="no-empty"></span>付款方式标志：
													</td>
													<td>
                                                      <edl:entityDictList displayName="consumerDTO.reimbursementType" dictValue="${consumerDTO.reimbursementType}" dictType="106" tagType="1" defaultOption="false" />
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
							
								</table>
				             </div>
				          </td>
				      </tr>
				    </table>
       </div>
	    </s:form>

		<div id="btnDiv" style="text-align: right; width: 100%">
			<input type="button" class="bt" style="margin: 5px" 
				onclick="submitForm('newForm', '${ctx}/consumer/inquery.action')" value="返 回"/>
			
			<div style="clear: both"></div>
		</div>
	</body>
</html>