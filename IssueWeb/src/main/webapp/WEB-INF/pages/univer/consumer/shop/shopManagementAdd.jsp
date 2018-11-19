<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增门店</title>
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

    function chooseMerchant() {
        var merchantDTO = window.showModalDialog('${ctx}/merchantManagement/chooseExternalMchnt', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
        if (merchantDTO != null) {
            maskDocAllWithMessage("Wait...");
            Ext.fly('entityId').dom.value = merchantDTO['entityId']; 
            Ext.fly('entityName').dom.value = merchantDTO['merchantName'];
            Ext.fly('shopName').dom.value = merchantDTO['merchantName'];
            Ext.fly('shopEnglishName').dom.value = merchantDTO['merchantEnglishName'];                     
            Ext.fly('shopAddress').dom.value = merchantDTO['merchantAddress'];
            Ext.fly('shopEnglishAddress').dom.value = merchantDTO['merchantEnglishAddress'];
            Ext.fly('shopPostcode').dom.value = merchantDTO['merchantPostcode'];            
            Ext.fly('shopTelephone').dom.value = merchantDTO['merchantTelephone'];
            Ext.fly('shopFax').dom.value = merchantDTO['merchantFax'];
            Ext.fly('shopWebsite').dom.value = merchantDTO['merchantWebsite'];
            Ext.fly('joinDate').dom.value = merchantDTO['joinDate'];     
            Ext.fly('merchantCode').dom.value = merchantDTO['merchantCode'];      
            unmaskDocAll();
        }
    }

    function loadPage() {
        city = ${city};
        category = ${category}; 
        changeCity(${shopDTO.businessArea});
        changeCategory(${shopDTO.shopType});
    }

    function changeCity(area) {
        var cityId = document.getElementById('city').value;
        var businessAreaData = city[cityId];
        var businessAreaSelect = document.getElementById('businessArea');
        businessAreaSelect.innerHTML = '';
        for (var i = 0; i < businessAreaData.length; i++) {
            var businessArea = businessAreaData[i];
            var opt = document.createElement('option');
            opt.value = businessArea['dictCode'];
            opt.innerHTML = businessArea['dictShortName'];
            if (area != null && area == opt.value) {
                opt.selected = "selected";
            }
            businessAreaSelect.appendChild(opt);
        }
        businessAreaSelect.disabled = false;
    }

    function changeCategory(shopType) {
        var categoryId = document.getElementById('category').value;
        var shopTypeData = category[categoryId];
        var shopTypeSelect = document.getElementById('shopType');
        shopTypeSelect.innerHTML = '';
        for (var i = 0; i < shopTypeData.length; i++) {
            var shopType = shopTypeData[i];
            var opt = document.createElement('option');
            opt.value = shopType['dictCode'];
            opt.innerHTML = shopType['dictShortName'];
            if (shopType != null && shopType == opt.value) {
                opt.selected = "selected";
            }
            shopTypeSelect.appendChild(opt);
        }
        shopTypeSelect.disabled = false;
    }
        var city;
        var category;
		</script>
	</head>
	<body onload="loadPage()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增门店</span>
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
							<s:form id="newForm" name="newForm" >
								<table width="100%" style="table-layout: fixed;">
								    <tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
                                                        <s:textfield id="merchantCode"  cssClass="watch" readonly="true" onclick="chooseMerchant()"/>
														<s:hidden id="entityId" name="shopDTO.entityId"></s:hidden>
														<s:fielderror>
															<s:param>
																shopDTO.entityId
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
														商户名称：
													</td>
													<td>
														<s:textfield name="shopDTO.entityName" id="entityName" readonly="true"/>
														<s:fielderror>
															<s:param>
																shopDTO.merchantName
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>门店号：
													</td>
													<td>
														<s:textfield name="shopDTO.shopCode" id="shopCode"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopCode
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
								    </tr>
								    --%>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>门店名称：
													</td>
													<td>
														<s:textfield name="shopDTO.shopName" id="shopName"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopName
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
														门店英文名称：
													</td>
													<td>
														<s:textfield name="shopDTO.shopEnglishName" id="shopEnglishName"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopEnglishName
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
														<span class="no-empty">*</span>门店地址：
													</td>
													<td>
										               <s:textfield name="shopDTO.shopAddress" id="shopAddress"/>
                                                       <s:fielderror>
															<s:param>
																shopDTO.shopAddress
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
														门店英文地址：
													</td>
													<td>
														<s:textfield name="shopDTO.shopEnglishAddress" id="shopEnglishAddress"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopEnglishAddress
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
														邮编：
													</td>
													<td>
										               <s:textfield name="shopDTO.shopPostcode" id="shopPostcode"/>
                                                       <s:fielderror>
															<s:param>
																shopDTO.shopPostcode
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
														<span class="no-empty">*</span>电话：
													</td>
													<td>
														<s:textfield name="shopDTO.shopTelephone" id="shopTelephone"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopTelephone
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
														<span class="no-empty">*</span>传真：
													</td>
													<td>
														<s:textfield name="shopDTO.shopFax" id="shopFax"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopFax
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
														网站：
													</td>
													<td>
										               <s:textfield name="shopDTO.shopWebsite" id="shopWebsite"/>
                                                       <s:fielderror>
															<s:param>
																shopDTO.shopWebsite
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
														<span class="no-empty">*</span>城市：
													</td>
													<td>
                                                         <edl:entityDictList displayName="shopDTO.shopCity" dictValue="${shopDTO.shopCity}" dictType="405" tagType="2" defaultOption="false" props="id='city' onchange='changeCity()'"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopCity
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
														<span class="no-empty">*</span>区域：
													</td>
													<td>
                                                      <select name="shopDTO.businessArea" id="businessArea" disabled="disabled"></select>
                                                       <s:fielderror>
															<s:param>
																shopDTO.businessArea
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
														<span class="no-empty">*</span>门店类型：
													</td>
													<td>
                                                       <edl:entityDictList displayName="shopDTO.shopCategory" dictValue="${shopDTO.shopCategory}" dictType="107" tagType="2" defaultOption="false" props="id='category' onchange='changeCategory()'"/>
											 			<s:fielderror>
															<s:param>
																shopDTO.shopCategory
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
														<span class="no-empty">*</span>门店子类：
													</td>
													<td>
										               <select name="shopDTO.shopType" id="shopType" disabled="disabled"></select>
                                                       <s:fielderror>
															<s:param>
																shopDTO.shopType
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
														<span class="no-empty">*</span>门店等级：
													</td>
													<td>
										               <edl:entityDictList displayName="shopDTO.shopRank" dictValue="${shopDTO.shopRank}" dictType="109" tagType="2" defaultOption="false" />
                                                         <s:fielderror>
															<s:param>
																shopDTO.shopRank
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
														<span class="no-empty">*</span>支付类型：
													</td>
													<td>
										                <edl:entityDictList displayName="shopDTO.paymentType" dictValue="${shopDTO.paymentType}" dictType="110" tagType="2" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																shopDTO.paymentType
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
														门店经度：
													</td>
													<td>
														<s:textfield name="shopDTO.shopLongitude" id="shopDTO.shopLongitude"/>
														<s:fielderror>
															<s:param>
																shopDTO.shopLongitude
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
														门店纬度：
													</td>
													<td>
										               <s:textfield name="shopDTO.shopLatitude" id="shopDTO.shopLatitude"/>
                                                       <s:fielderror>
															<s:param>
																shopDTO.shopLatitude
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
														缺省门店：
													</td>
													<td>
													    <s:select 
                                                         list="#{'1':'是','0':'否'}" 
                                                         name="shopDTO.defaultFlag" 
                                                         emptyOption="false"
                                                         label="defaultFlag"/> 											
                                                       <s:fielderror>
															<s:param>
                                                              shopDTO.defaultFlag
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
														<span class="no-empty">*</span>加盟日期：
													</td>
													<td>
										               <td>
										                 <s:textfield name="shopDTO.joinDate" id="joinDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
										                   <s:param name="value"><s:date name="shopDTO.joinDate" format="yyyy-MM-dd" /></s:param>
										                 </s:textfield>
										               </td>
										            <td>													
                                                       <s:fielderror>
															<s:param>
																shopDTO.joinDate
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
														<span class="no-empty">*</span>门店状态：
													</td>
													<td>
													    <s:select 
                                                         list="#{'1':'有效','2':'门店切换','3':'无效'}" 
                                                         name="shopDTO.shopState" 
                                                         emptyOption="false"
                                                         label="state" 
                                                       /> 											
                                                       <s:fielderror>
															<s:param>
                                                              shopDTO.shopState
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														门店介绍（用于网站同步显示）：
													</td>
													<td>
										               <s:textarea name="shopDTO.shopNotes" id="shopDTO.shopNotes" rows="5" cols="50"/>												
                                                       <s:fielderror>
															<s:param>
                                                              shopDTO.shopNotes
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														英文介绍（用于网站同步显示）：
													</td>
													<td>
										               <s:textarea name="shopDTO.englishNotes" id="shopDTO.englishNotes" rows="5" cols="50"/>												
                                                       <s:fielderror>
															<s:param>
                                                              shopDTO.englishNotes
															</s:param>
														</s:fielderror> 
													</td>
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


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='shopManagement/inquiry';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="newForm.action='shopManagement/insertShop';newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>