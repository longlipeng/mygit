<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查看门店</title>
		<%@ include file="/commons/meta.jsp"%>

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
				 window.showModalDialog('${ctx}/merchantManagement/viewShopPos.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
			}
			
			 function sendSms(){
	        var mobile=/^((\(\d{3}\))|(\d{3}\-))?13\d{9}|15\d{9}|18\d{9}/;
	        var mobilePhone=document.getElementById("mobileId").value
	        if(mobilePhone==''){
	         document.getElementById("rexMobileId").innerHTML="手机号必须输入！";
	         document.getElementById("rexMobileId").focus();
	         return ;
	        }else{
	         if(mobilePhone.length>11){
	             document.getElementById("rexMobileId").innerHTML="手机号不能超过11个字符！";
	             document.getElementById("rexMobileId").focus();
	             return ;
	         }
	         if(!mobile.test(mobilePhone)){
	             document.getElementById("rexMobileId").innerHTML="请输入正确的手机号码！";
	             document.getElementById("rexMobileId").focus();
	             return ;
	         }
	        }
	        document.getElementById("rexMobileId").innerHTML="";
	      
	        var shopForm = Ext.get("newForm").dom;	        
	        
	        shopForm.action = '${ctx}/shopManagement/sendSms.action';
	        shopForm.submit();
	        //document.getElementById("mobileId").value="";
	    }
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看门店</span>
		</div>
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
									<span class="TableTop">门店信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="shopManagement/insert">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														商户号：
													</td>
													<td>
													  <s:label name="shopDTO.merchantCode"/>
													</td>
												</tr>
										  </table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														商户名称：
													</td>
													<td>
														<s:label name="shopDTO.merchantName"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
                                    
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店号：
													</td>
													<td>
														<s:label name="shopDTO.shopId"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店名称：
													</td>
													<td>
														<s:label name="shopDTO.shopName"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店英文名称：
													</td>
													<td>
														<s:label name="shopDTO.shopEnglishName"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店地址：
													</td>
													<td>
										               <s:label name="shopDTO.shopAddress"/> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店英文地址：
													</td>
													<td>
														<s:label name="shopDTO.shopEnglishAddress"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														邮编：
													</td>
													<td>
										               <s:label name="shopDTO.shopPostcode"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														电话：
													</td>
													<td>
														<s:label name="shopDTO.shopTelephone"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														传真：
													</td>
													<td>
														<s:label name="shopDTO.shopFax"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														城市：
													</td>
													<td>
                                                        <edl:entityDictList displayName="shopDTO.shopCity" dictValue="${shopDTO.shopCity}" dictType="405" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														区域：
													</td>
													<td>
                                                       <edl:entityDictList displayName="shopDTO.businessArea" dictValue="${shopDTO.businessArea}" dictType="408" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店类型：
													</td>
													<td>
                                                        <edl:entityDictList displayName="shopDTO.shopCategory" dictValue="${shopDTO.shopCategory}" dictType="107" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店子类：
													</td>
													<td>
                                                        <edl:entityDictList displayName="shopDTO.shopType" dictValue="${shopDTO.shopType}" dictType="108" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店等级：
													</td>
													<td>
                                                        <edl:entityDictList displayName="shopDTO.shopRank" dictValue="${shopDTO.shopRank}" dictType="109" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														支付类型：
													</td>
													<td>
                                                        <edl:entityDictList displayName="shopDTO.paymentType" dictValue="${shopDTO.paymentType}" dictType="110" tagType="1"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店经度：
													</td>
													<td>
														<s:label name="shopDTO.shopLongitude"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店纬度：
													</td>
													<td>
										               <s:label name="shopDTO.shopLatitude"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														缺省门店：
													</td>
													<td>
                                                        <s:property value="shopDTO.defaultFlag==1?'是':'否'"/>
													</td>
												</tr>
											</table>
										</td>

										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店状态：
													</td>
													<td>
														<s:property value="shopDTO.shopState==1?'有效':'无效'"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                                加盟日期：
                                                    </td>
                                                    <td>
                                                       <td>
                                                         <s:date name="shopDTO.joinDate" format="yyyy-MM-dd" />
                                                       </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                        网站：
                                                    </td>
                                                    <td>
                                                       <s:label name="shopDTO.shopWebsite"/> 
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
									<tr>
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店注释：
													</td>
													<td>
										               <s:label name="shopDTO.memo"/>												
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 190px; text-align: right;">
														门店介绍（用于网站同步显示）：
													</td>
													<td>
										               <s:label name="shopDTO.shopNotes"/>												
													</td>
												</tr>
											</table>
										</td>
									</tr>
                                    <tr>
                                        <td colspan="2">
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                                英文介绍（用于网站同步显示）：
                                                    </td>
                                                    <td>
                                                       <s:label name="shopDTO.shopEnglishNotes"/>                                               
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                                创建时间：
                                                    </td>
                                                    <td>
                                                       <s:date name="shopDTO.createTime" format="yyyy-MM-dd" />                                             
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                                创建人：
                                                    </td>
                                                    <td>
                                                       <s:label name="shopDTO.creator"/>                                               
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                    	<td>
                                    		<table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 190px; text-align: right;">
                                                            	短信内容：  
                                                    </td>
                                                    <td>
                                                    <s:textarea cols="80" rows="10" name="shopDTO.info" id="info"></s:textarea>
                                          	
                                            <s:fielderror>
                                              <s:param>
                                                shopDTO.info
                                              </s:param>
                                            </s:fielderror></td>
                                                </tr>
                                            </table>
                                    	</td>
                                    	
                                    </tr>
								</table>
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>
		<div id="ContainBox" style="margin-top:20px">
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
                          <ec:table items="shopDTO.contactList" var="contactDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="contact"
                              showPagination="false"
                              sortable="false">
              <ec:row>
                <ec:column property="contactName" title="联系人姓名" width="20%" />
                <ec:column property="contactFunction" title="联系人职位" width="40%" />
                <ec:column property="contactMobilePhone" title="联系人电话" width="40%" />                
              </ec:row>
            </ec:table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="right">
					&nbsp;
                  </td>
               </tr>
          </table>
      </s:form>
      </div>
					</td>
				</tr>
			</table>
		</div>
        <div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>

<%--		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('posTable');"
									style="cursor: pointer;">
									<span class="TableTop">Pos信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="posTable">
					    <s:form id="posForm" name="posForm" action="" method="post">
                          <ec:table items="shopDTO.posList" var="shopPosDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="pos"
                              showPagination="false"
                              sortable="false">
              <ec:row>
               <ec:column property="termId" title="Pos编号" width="20%" />     
               <ec:column property="termStat" title="Pos状态" width="20%" />           
               <ec:column property="createTime" title="Pos创建时间" width="20%" />     
              </ec:row>
            </ec:table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="right">
					&nbsp;
                  </td>
               </tr>
          </table>
      </s:form>
      </div>
					</td>
				</tr>
			</table>
		</div>--%>
		
	<%--	<div id="ContainBox">
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
									<span class="TableTop">授权卡信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="contactTable">
					    <s:form id="authorityCardForm" name="authorityCardForm" action="" method="post">
                          <ec:table items="shopDTO.authorityCardDTOList" var="authorityCardDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="contact"
                              showPagination="false"
                              sortable="false">
              <ec:row>
                <ec:column property="null" alias="authorityCardIdList" title="选择" width="10%" sortable="false" headerCell="selectAll" viewsAllowed="html">
                    <input type="checkbox" name="authorityCardIdList" value="${authorityCardDTO.id}" />
                </ec:column>
                <ec:column property="authorityCardNo" title="授权卡号" width="40%" />
                <ec:column property="shopId" title="门店号" width="40%" />
                              
              </ec:row>
            </ec:table>
            
      </s:form>
      </div>
					</td>
				</tr>
			</table>
		</div>
   --%>     <div id="btnDiv" style="text-align: right; width: 100%">
            <button class='bt' style="float: right; margin: 5px 10px"
                onclick="newForm.action='shopManagement/inquiry';newForm.submit();">
                返 回
            </button>
        </div>
	</body>
</html>