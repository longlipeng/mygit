﻿<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看商户</title>
		<%@ include file="/commons/meta.jsp"%>
 <style type="text/css">
	span#hint{
	color:red;
	}
	</style>
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
			
			function viewShopPos(shopId){
				 window.showModalDialog('${ctx}/merchantManagement/viewShopPos.action?shopDTO.shopId='+shopId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			}

		    function setPunishDiv(){
		    	document.getElementById('punishDiv').style.display='none';
		    	if (document.getElementById('isPunish').value==1) {
		        	document.getElementById('punishDiv').style.display='block';        
		      	}
		    }
		    
		    function setWebDiv() {
	     		document.getElementById('webDiv').style.display='none';
	      		if (document.getElementById('enableWebsite').checked) {
	        		document.getElementById('webDiv').style.display='block';        
	      		}
    		}
    		
            function loadSalesmanList() {
	        	var salesmanList = ${salesmanList};
	        	var store = new Ext.data.JsonStore({
	            data: salesmanList,
	            autoLoad: true,
	            fields: [{name: 'id', mapping: 'userId'}, {name: 'name', mapping: 'userName'}]
	        	});
	        
		        var combo = new Ext.form.ComboBox({
		            store: store,
		            displayField:'name',
		            hiddenName: 'merchantDTO.salesmanId',
		            valueField: 'id',
		            typeAhead: true,
		            mode: 'local',
		            triggerAction: 'all',
		            emptyText: '请选择销售代表',
		            selectOnFocus: true,
		            applyTo: 'salesmanId'
		        });
            }
    
            function checkGuaranteeValidDate(){
			    var strdate = document.getElementById("guaranteeValidDate").innerHTML;
			    var guaranteeValidDate = new Date(strdate.replace(/-/g,"/"));
			    var nowDate = new Date();
			    var time =  guaranteeValidDate.getTime()- nowDate.getTime();
			    var intervalDays = parseInt(time/(1000*60*60*24));
			    if(""!=strdate){
			        if(0 < intervalDays <= 30){
					    var spanHint = document.createElement("span");
					    spanHint.id = "hint";
					    var html = "担保有效期为" + intervalDays + "天";
					    spanHint.innerHTML = html;
					    var insert = document.getElementById("guaranteeValidDate");
					    insert.parentNode.appendChild(spanHint, insert.nextSibling); 
			    	}
			    }
            }
            
	        function setManageT() {
				var manageT = "${merchantDTO.merchantManageTime}";
				var startT = manageT.substr(0,manageT.indexOf('-',0));
				var endT = manageT.substr(manageT.indexOf('-',0)+1,manageT.length);
				document.getElementById("manageTimeStart").value=startT;
		 		document.getElementById("manageTimeEnd").value=endT;
		    }
		    
	        function InitPage() {
			    checkGuaranteeValidDate();
				setWebDiv();
	      		//loadSalesmanList();	  
				//display();
		 		setManageT();
		 		setPunishDiv();	
			}
		</script>
	</head>
	<body onload="InitPage();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref" align="left">
			<span>查看商户</span>
		</div>
		<s:form id="newForm" name="newForm">
			<s:hidden name="merchantDTO.isPunish" id="isPunish"></s:hidden>
			<div id="ContainBox">
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
									<span class="TableTop">商户信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
								<table width="100%" style="table-layout: fixed;">
									
									<tr>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 110px; text-align: right;">--%>
<%--														商户编号：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														<s:label name="merchantDTO.entityId"/>--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
										               <s:label name="merchantDTO.merchantCode" id="merchantDTO.merchantCode"/>
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
														<span class="no-empty">*</span>商户注册名称：
													</td>
													<td>
														<s:label name="merchantDTO.merchantName" id="merchantDTO.merchantName"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														商户实际店名：
													</td>
													<td>
														<s:label name="merchantDTO.merchantRealityName" id="merchantDTO.merchantRealityName"/>
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
														商户英文名称：
													</td>
													<td>
														<s:label name="merchantDTO.merchantEnglishName" id="merchantDTO.merchantEnglishName" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户级别：
													</td>
													<td>
													  <s:if test="sensitiveData">
										              <edl:entityDictList displayName="merchantDTO.merchantType" dictValue="${merchantDTO.merchantType}" dictType="181" tagType="1" defaultOption="false" props="disabled='true'"/>
										              </s:if>
										              <s:else>
										              	  <edl:entityDictList displayName="merchantDTO.merchantType" dictValue="${merchantDTO.merchantType}" dictType="181" tagType="2" defaultOption="false" props="disabled='true'"/>
										             </s:else>
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
														<span class="no-empty"></span>发票名称：
													</td>
													<td>
													   <s:label name="merchantDTO.invoiceName" id="merchantDTO.invoiceName"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户属性：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantAttribute" dictValue="${merchantDTO.merchantAttribute}" dictType="184" tagType="2" defaultOption="false" props="disabled='true'"/>
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
														老系统商户号：
													</td>
													<td>
														<s:label name="merchantDTO.legacyMerchantId" id="merchantDTO.legacyMerchantId"/>
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
														<s:label name="merchantDTO.externalId" id="merchantDTO.externalId"/>
														
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
														<span class="no-empty">*</span>市场代表：
													</td>
													<td>
										              <s:select id="salesmanId"
										              	  name="merchantDTO.salesmanId"  
													      list="merchantDTO.salesmanList"
														  listKey="userId"
														  listValue="userName"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>加盟日期：
													</td>
													<td>
										               <td>
										                 <s:label name="merchantDTO.joinDate" />
										                  
										                 
										               </td>
										            <td>													
                                                       <s:fielderror>
															<s:param>
																merchantDTO.joinDate
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										
									</tr> 
									<tr>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 110px; text-align: right;">
														担保方式：
													</td>
													<td>
<!--													<s:radio list="#{0:'保函',1:'押金'}"	name="merchantDTO.guaranteeType" ></s:radio>-->
														<s:select headerKey="" headerValue="" list="#{0:'保函',1:'押金'}" name="merchantDTO.guaranteeType" disabled="true"></s:select>
														<s:fielderror>
															<s:param>
																	merchantDTO.guaranteeType
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 110px; text-align: right;">
														担保有效期：
													</td>
													<td>
													&nbsp;
													<s:hidden name="merchantDTO.guaranteeValidDate" id="guaranteeValidDate1"></s:hidden>
										               <s:label name="merchantDTO.guaranteeValidDate" id="guaranteeValidDate"/>
										             
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
														POS机申请台数：
													</td>
													<td>
										               <s:label name="merchantDTO.postApplyNum" id="postApplyNum"/>
                                                       
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														 <span class="no-empty">*</span>是否布放移动POS：                                                     
													</td>
													<td>
														<s:select 
                                                         list="#{'0':'否','1':'是'}"  
                                                         name="merchantDTO.isMovePost" 
                                                         emptyOption="false" disabled="true"/>	
                                                      
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
														<span class="no-empty">*</span>商户行业：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantIdustry" dictValue="${merchantDTO.merchantIdustry}" dictType="185" tagType="2" defaultOption="false" props="disabled='true'"/>
                                                      
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
                                                         	  商户状态：
                                                       </td>
                                                       <td>
                                                            <s:select id="state"  name="merchantDTO.merchantState" list="#{'1':'已审核','0':'无效','2':'未审核','3':'审核未通过','4':'审核中'}" disabled="true"></s:select>
                                                       </td>
                                                   </tr>
                                               </table>
                                        </td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														开通网站管理：
													</td>													
													<td>
                                                      <input type="checkbox" <s:property value="merchantDTO.enableWebsite == 1 ? 'checked=checked' : ''"/> name="merchantDTO.enableWebsite" id="enableWebsite" value="1" disabled="disabled"/>
																										
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
														<span class="no-empty">*</span>商户交易类型：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantTransactionType" dictValue="${merchantDTO.merchantTransactionType}" dictType="186" tagType="2" defaultOption="false" props="disabled='true'"/>
                                                       
													</td>
												</tr>
											</table>
										</td> 
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													是否支持&nbsp;&nbsp;<br/>互联网交易：
													</td>
													<td>
													  <input type="checkbox"
															<s:property value="merchantDTO.ePayIn ==1 ? 'checked=checked' : ''"/>
															name="merchantDTO.ePayIn" id="ePayIn" value="1"
															/>
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
														商户后台返回URL：
													</td>
													<td>
										               <s:label name="merchantDTO.mchntUrl" id="merchantDTO.mchntUrl"/>
                                                       
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														证书DN信息：
													</td>
													<td>
														<s:label name="merchantDTO.dnInfo" id="merchantDTO.dnInfo"/>
														
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
														商户经营时间：
													</td>
													<td>
                                                     <input type="text" id="manageTimeStart" readonly="readonly"/>
													至 <input type="text" id="manageTimeEnd" readonly="readonly"/>
													
                                                     <s:hidden name="merchantDTO.merchantManageTime" id="merchantManageTime" />
                                                    	
													</td>
												</tr>
											</table>
										</td> 
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														交易查询次数：
													</td>
													<td>
										               <s:label name="merchantDTO.txnQryTimes" id="merchantDTO.txnQryTimes"/>
                                                       
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
														证书号：
													</td>
													<td>
                                                    	<s:textarea name="merchantDTO.certificateNo" cols="20" rows="5" readonly="true"></s:textarea>
                                                    	
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														注释信息：
													</td>
													<td>
                                                    	<s:textarea name="merchantDTO.annotation" cols="20" rows="5" readonly="true"></s:textarea>
                                                    	
													</td>
												</tr>
											</table>
										</td>
									</tr>
							     </table>
							     <div id = "webDiv">
								   <table width="100%" style="table-layout: fixed;">
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站登录名：
														</td>
														<td>
															<s:if test="merchantDTO.websiteUserName!=null &&　merchantDTO.websiteUserName!=''">
											              	 <s:label name="merchantDTO.websiteUserName" id="merchantDTO.websiteUserName" />
															</s:if>
															<s:else>
											               	<s:label name="merchantDTO.websiteUserName" id="merchantDTO.websiteUserName" />
											       			</s:else>
	                                                       
															<div id="message" style="color:red"/>
														</td>
													</tr>
												</table>
											</td>
											
											<td>
											<s:if test="merchantDTO.websitePassword!=null">
												<s:hidden name="merchantDTO.websitePassword"></s:hidden>
											</s:if>
											<s:else>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站登录密码：
														</td>
														<td>
															
														</td>
													</tr>
												</table>
												</s:else>
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
								<td class="TableTitleFront"
									onclick="displayTable('paraTable1');" style="cursor: pointer;">
									<span class="TableTop">联系方式</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="paraTable1">
							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													客户经理姓名：
												</td>
												<td>
													<s:label name="merchantDTO.customerManagerName"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													客户经理联系电话1：
												</td>
												<td>
													<s:label name="merchantDTO.customerManagerTelephone1"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													客户经理联系电话2：
												</td>
												<td>
													<s:label name="merchantDTO.customerManagerTelephone2"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户联系人：
												</td>
												<td>
													<s:label name="merchantDTO.merchantLinkman"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户联系电话1：
												</td>
												<td>
													<s:label name="merchantDTO.merchantTelephone"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户联系电话2：
												</td>
												<td>
													<s:label name="merchantDTO.merchantTelephone2"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户传真：
												</td>
												<td>
													<s:label name="merchantDTO.merchantFax"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户网址：
												</td>
												<td>
													<s:label name="merchantDTO.merchantWebsite"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户地址：
												</td>
												<td>
													<s:label name="merchantDTO.merchantAddress"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													英文地址 ：
												</td>
												<td>
													<s:label name="merchantDTO.merchantEnglishAddress"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户邮编：
												</td>
												<td>
													<s:label name="merchantDTO.merchantPostcode"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>


				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									onclick="displayTable('paraTable2');" style="cursor: pointer;">
									<span class="TableTop">结算信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="paraTable2">
							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户结算代理：
												</td>
												<td>
													<edl:entityDictList displayName="merchantDTO.settAgencyId"
														dictValue="${merchantDTO.settAgencyId}" dictType="422"
														tagType="2" defaultOption="false" props="disabled='true'"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户结算方式：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.merchantSettType"
														dictValue="${merchantDTO.merchantSettType}" dictType="187"
														tagType="2" defaultOption="false" props="disabled='true'"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<!--  
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户开户银行：
												</td>
												<td>
													<s:label name="merchantDTO.merchantBank"/>
													
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户账户：
												</td>
												<td>
													<s:label name="merchantDTO.merchantAccount"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户结算账户：
												</td>
												<td>
													<s:label name="merchantDTO.merchantSettAccount"/>
													
												</td>
											</tr>
										</table>
									</td>
								</tr>
								-->
							</table>
						</div>
					</td>
				</tr>





				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									onclick="displayTable('paraTable3');" style="cursor: pointer;">
									<span class="TableTop">证件信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="paraTable3">
							<table width="100%" style="table-layout: fixed;">
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													组织机构代码：
												</td>
												<td>
													<s:label name="merchantDTO.orgCode"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户法人：
												</td>
												<td>
													<s:label name="merchantDTO.merchantLegalPerson"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													营业执照号码：
												</td>
												<td>
													<s:label name="merchantDTO.businessLicenseNumber"/>
												</td>
											</tr>
										</table>
									</td> 
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													营业执照有效期From：
												</td>
												<td>
													<s:label name="merchantDTO.businessLicenseFrom"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													To：
												</td>
												<td>
													<s:label name="merchantDTO.businessLicenseTo"></s:label>
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													法人身份证号：
												</td>
												<td>
													<s:label name="merchantDTO.legalPersonIdno"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户开业时间：
												</td>
												<td>
													<s:label name="merchantDTO.merchantOpeningTime"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户分支机构数量：
												</td>
												<td>
													<s:label name="merchantDTO.merchantBranchNum"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户注册资本：
												</td>
												<td>
													<s:label name="merchantDTO.merchantRegisteredCapital"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户员工数：
												</td>
												<td>
													<s:label name="merchantDTO.merchantEmployeesNum"/>
													
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													营业用地形式：
												</td>
												<td>
													<edl:entityDictList displayName="merchantDTO.landType"
														dictValue="${merchantDTO.landType}" dictType="188"
														tagType="2" defaultOption="false" props="disabled='true'"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户经营地段：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.merchantSction"
														dictValue="${merchantDTO.merchantSction}" dictType="189"
														tagType="2" defaultOption="false" props="disabled='true'"/>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantSction
															</s:param>
													</s:fielderror>
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户知名度：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.merchantPopularity"
														dictValue="${merchantDTO.merchantPopularity}"
														dictType="190" tagType="2" defaultOption="false" props="disabled='true'"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													上年度POS消费总金额：
												</td>
												<td>
													<s:label name="merchantDTO.previousYearPos"/>
													
												</td>

											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>


				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront"
									onclick="displayTable('paraTable4');" style="cursor: pointer;">
									<span class="TableTop">风控信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
							<div id="paraTable4">
							<table width="100%" style="table-layout: fixed;">
								<tr>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														是否异地收单:
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isAllopatryAcquire"  disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														是否有内外卡收单经验：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isAcquireExp" disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
								<tr>
										<td>
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														申请资料是否含有照片：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isApplyMaterialPic"  disabled="true" emptyOption="false" />
													</td>
												</tr>
											</table>
										</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													征信系统是否已征信：
												</td>
												<td>
													<s:select name="merchantDTO.isCreditInvestigation"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>	
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													法人身份信息是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectLegalPerson"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													不良出票人系统是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectBadnessDrawer"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户照片信息是否已存档：
												</td>
												<td>
													<s:select name="merchantDTO.isPhotoOnFile"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户是否为其他收单机构拒绝：
												</td>
												<td>
													<s:select name="merchantDTO.isRejectedAcquirer"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isMerchantLawsuit"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户负责人（法人代表或高管）是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isPrincipalLawsuit"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户被执法部门处罚记录：
												</td>
												<td>
													<s:select name="merchantDTO.isPunish" id="isPunish"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>
											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户负责人（法人）的信用状况：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.principalCreditStatus"
														dictValue="${merchantDTO.principalCreditStatus}"
														dictType="191" tagType="2" defaultOption="false"  props="disabled='true'"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													五证齐全。营业执照、税务登记证、组织机构代码证、法人身份证、开户许可证齐全且未过期：
												</td>
												<td>
													<s:select name="merchantDTO.isFiveCertificateAll"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>

											</tr>
										</table>
									</td>
									<td>
										<table width="100%" style="table-layout: fixed;">
											<tr>
												<td style="width: 170px; text-align: right;">
													商户信息调查表填写完整。是否分支机构联系人有签名及盖公章：
												</td>
												<td>
													<s:select name="merchantDTO.isSignHave"
														list="#{2:'',1:'是',0:'否'}" disabled="true" ></s:select>
												</td>

											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<div id="punishDiv">
											<table width="100%" style="table-layout: fixed;">
												<tr>
													<td style="width: 170px; text-align: right;">
														商户被执法部门处罚记录内容：
													</td>
													<td>

														<s:textarea name="merchantDTO.punishContent" cols="20"
														   disabled="true" drows="5"></s:textarea>
													</td>
												</tr>
											</table>
									</td>
									</div>
								</tr>
							</table>
						</div>
					</td>
				</tr>

			    
			    
			    
			    
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('paraTableC');"
									style="cursor: pointer;">
									<span class="TableTop">参数设定</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="paraTableC">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>结算手续费修改标志：
													</td>
													<td>
														<s:property value="merchantDTO.commissionFee ==1?'是':'否'" />
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
														<s:property value="merchantDTO.reimburseWithoutCheck ==1?'是':'否'" />
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
                                                      <span class="no-empty">*</span>商户消费暂停标志：
													</td>
													<td>
													    <s:property value="merchantDTO.purchasePause ==1?'是':'否'" />
													   
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>商户结算暂停标志：
													</td>
													<td>
													    <s:property value="merchantDTO.reimbursePause ==1?'是':'否'"/>
													    
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
														<span class="no-empty">*</span>付款方式标志：
													</td>
													<td>
													 <s:if test="sensitiveData">
                                                     <edl:entityDictList displayName="merchantDTO.reimbursementType" dictValue="${merchantDTO.reimbursementType}" dictType="106" tagType="1" defaultOption="false"   props="disabled='true'"/>
                                                      </s:if>
                                                      <s:else>
                                                      	   <edl:entityDictList displayName="merchantDTO.reimbursementType" dictValue="${merchantDTO.reimbursementType}" dictType="106" tagType="2" defaultOption="false"   props="disabled='true'"/>
                                                     </s:else>
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
		<br/>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">		
					<s:form id="bankForm" name="bankForm" action="" method="post">
					<s:hidden name="entityId" />
					<s:hidden name="merchantDTO.entityId"></s:hidden>
			    	<s:hidden name="merchantDTO.fatherEntityId"></s:hidden>
					<div id="bankTitle"
						style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="TableTitleFront" style="cursor: pointer"
									onclick="showOrHideDiv('bankTable')">
									<span class="TableTop">银行信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div id="bankTable"
						style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
						<ec:table items="merchantDTO.bankList" var="map" width="100%"
							action="${ctx}/bankManagement"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							autoIncludeParameters="false" form ="bankForm"
							showPagination="false" sortable="false">
							<ec:row>
								<ec:column property="null" alias="chooseBankId" title="选择"
									width="10%" sortable="false" headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="chooseBankId"
										value="${map.bankId}" />
								</ec:column>
								<ec:column property="bankName" title="银行名称" width="30%"/>
								<ec:column property="bankAccount" title="银行账户" width="30%"/>
								<ec:column property="bankAccountName" title="银行账户名称" width="30%"/>
							</ec:row>
						</ec:table>
						
					</div>
					</s:form>
		</td>
		</tr>
		</table>
		
		<div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>

<!--  
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('contactTable');"
									style="cursor: pointer;">
									<span class="TableTop">联系人信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="contactTable">
					    <s:form id="contactForm" name="contactForm" action="" method="post">
                          <ec:table items="merchantDTO.contactList" var="contactDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="contact"
                              showPagination="false"
                              sortable="false">
              <ec:row>
                <ec:column property="null" alias="contactIdList" title="选择" width="10%" sortable="false" headerCell="selectAll" viewsAllowed="html">
                    <input type="checkbox" name="contactIdList" value="${contactDTO.contactId}" />
                </ec:column>
                <ec:column property="contactName" title="联系人姓名" width="20%" />
                <ec:column property="contactFunction" title="联系人职位" width="40%" />
                <ec:column property="contactMobilePhone" title="联系人电话" width="40%" />                
              </ec:row>
            </ec:table>
            
      </s:form>
      </div>
					</td>
				</tr>
			</table>
		</div>
        <div id="blank" style="height: 20px">
			&nbsp;&nbsp;
		</div>
-->

		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action= 'merchantManagement/inquiry';newForm.submit()">
				返 回
			</button>
		</div>
 
	</body>
		
</html>