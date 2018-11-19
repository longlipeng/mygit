<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改门店</title>
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

    function getParam(name) {
        var str = '';
        var checkbox = document.getElementsByName(name);
        for (var i = 0; i < checkbox.length; i++) {
            if (checkbox[i].checked) {
                str += name + '=' + checkbox[i].value;
                str += '&';
            }
        }
        if (str != '') {
            str = str.substring(0, str.length - 1);
        }
        return str;
    }

    function chooseMerchant() {
        var merchantDTO = window.showModalDialog('${ctx}/merchantManagement/chooseMchnt', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
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

    function operateDetailppp(operater,list,url, inqueryUrl) {
        var count = checkedCount();
        if (operater != 'add') {
          var count = checkedCount(list);
          if (operater == 'delete') {
            if (count < 1) {
                alert('请至少选择一条记录！');
                return;
            }
          }
          if (operater == 'edit') {
            if (count != 1) {
                alert('请选择一条记录！');
                return;
            }
          }
        }
        if (operater == 'delete') {
       		document.getElementById("url").value=url;
			document.getElementById("inqueryUrl").value=inqueryUrl;
           	window.confirm("确认要删除吗?",deleteContact);
           	return ;
        }
        var shopId = document.getElementById('shopId').value;
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		window.location = inqueryUrl+'?shopId=' + shopId;
    }

	function deleteContact(){
		document.contactForm.action = document.getElementById("url").value +
		 		'&shopId=' + document.getElementById('shopId').value;
 		alert(document.contactForm.action )
		document.contactForm.submit();
	}
	
	function operdel(){
		 var url=document.getElementById("url").value;
		 var inqueryUrl=document.getElementById("inqueryUrl").value;
		 var shopId = document.getElementById('shopId').value;
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		window.location = inqueryUrl+'?shopId=' + shopId;
		return;
	}


    function checkedCount(list) {
        var checkboxs = document.getElementsByName(list);
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
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
            var shopType1 = shopTypeData[i];
            var opt = document.createElement('option');
            opt.value = shopType1['dictCode'];
            if (shopType == opt.value) {
                opt.selected = "selected";
            }
            opt.innerHTML = shopType1['dictShortName']; 
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
			<span>修改门店</span>
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
								action="shopManagement/update">
								<s:hidden name="url" id="url" />
								<s:hidden name="inqueryUrl" id="inqueryUrl" />
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
                                                <tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
                                                        <s:textfield id="merchantCode" name="shopDTO.merchantCode" cssClass="watch" readonly="true" onclick="chooseMerchant()"/>
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
														<s:textfield name="shopDTO.entityName" id="entityName"/>
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
												<tr>
												<%--
													<td>
														<table style="text-align: left; width: 100%">
															<tr>
																<td style="width: 110px; text-align: right;">
																	门店号：
																</td>
																<td>
																	<s:textfield name="shopDTO.shopCode"
																		id="shopDTO.shopCode" readonly="true" />
																		<s:hidden name="shopDTO.shopId" id="shopId"></s:hidden>
																	<s:fielderror>
																		<s:param>
																 shopDTO.shopCode
															</s:param>
																	</s:fielderror>
																</td>
															</tr>
														</table>
													</td>
													--%>
													<td>
														<table style="text-align: left; width: 100%">
															<tr>
																<td style="width: 110px; text-align: right;">
																	门店号：
																</td>
																<td>
																	<s:textfield name="shopDTO.shopId" id="shopId"
																		readonly="true" />
																	<s:fielderror>
																		<s:param>
																shopDTO.shopId
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
																	<span class="no-empty">*</span>门店名称：
																</td>
																<td>
																	<s:textfield name="shopDTO.shopName" id="shopName" />
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
																	<s:textfield name="shopDTO.shopEnglishName"
																		id="shopEnglishName" />
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
																	<s:textfield name="shopDTO.shopAddress"
																		id="shopAddress" />
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
																	<s:textfield name="shopDTO.shopEnglishAddress"
																		id="shopEnglishAddress" />
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
																	<s:textfield name="shopDTO.shopPostcode"
																		id="shopPostcode" />
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
																	<s:textfield name="shopDTO.shopTelephone"
																		id="shopTelephone" />
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
																	<s:textfield name="shopDTO.shopFax" id="shopFax" />
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
																	<s:textfield name="shopDTO.shopWebsite"
																		id="shopWebsite" />
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
																shopDTO.city
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
																	<select name="shopDTO.businessArea" id="businessArea"
																		disabled="disabled"></select>
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
																	<select name="shopDTO.shopType" id="shopType"
																		disabled="disabled">

																	</select>
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
																	<s:textfield name="shopDTO.shopLongitude"
																		id="shopDTO.shopLongitude" />
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
																	<s:textfield name="shopDTO.shopLatitude"
																		id="shopDTO.shopLatitude" />
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
																	<s:select list="#{'1':'是','0':'否'}"
																		name="shopDTO.defaultFlag" emptyOption="false"
																		label="defaultFlag" />
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
																	<s:textfield name="shopDTO.joinDate" id="joinDate"
																		onclick="dateClick(this)" cssClass="Wdate"
																		cssStyle="cursor:pointer">
																		
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
																	<s:select list="#{'1':'有效','2':'门店切换','3':'无效'}"
																		name="shopDTO.shopState" emptyOption="false"
																		label="state" />
																	<s:fielderror>
																		<s:param>
                                                              shopDTO.state
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
																	<s:textarea name="shopDTO.shopNotes"
																		id="shopDTO.shopNotes" rows="5" cols="50" />
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
																	<s:textarea name="shopDTO.englishNotes"
																		id="shopDTO.englishNotes" rows="5" cols="50" />
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
												<tr>
													<td>
														<table style="text-align: left; width: 100%">
															<tr>
																<td style="width: 110px; text-align: right;">
																	创建时间：
																</td>
																<td>

																	<s:label name="shopDTO.createTime" />
																</td>
															</tr>
														</table>
													</td>
													<td>
														<table style="text-align: left; width: 100%">
															<tr>
																<td style="width: 110px; text-align: right;">
																	创建人：
																</td>
																<td>
																	<s:label name="shopDTO.creator" />
																	<s:hidden name="shopDTO.createUser" />
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
				onclick="newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
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
                <ec:column property="null" alias="contactIdList" title="选择" width="10%" sortable="false" headerCell="selectAll" viewsAllowed="html">
                    <input type="checkbox" name="contactIdList" value="${contactDTO.contactId}" />
                </ec:column>
                <ec:column property="contactName" title="联系人姓名" width="20%" />
                <ec:column property="contactFunction" title="联系人职位" width="40%" />
                <ec:column property="contactMobilePhone" title="联系人电话" width="40%" />                
              </ec:row>
            </ec:table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="right">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="operateDetailppp('add','contactIdList','shopManagement/addContact?shopDTO.shopId=${shopDTO.shopId}'+'&'+'actionName=shopManagement/insertContact', '${ctx}/shopManagement/edit.action')" value="添加"/>
                            </td>
                            <td>
                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="operateDetailppp('edit','contactIdList','shopManagement/editContact?'+getParam('contactIdList')+'&'+'actionName=shopManagement/updateContact'+'&'+'contactDTO.entityId=${shopDTO.shopId}', '${ctx}/shopManagement/edit.action')" value="编辑"/>
                            </td>
                            <td>
                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right" onclick="operateDetailppp('delete','contactIdList','shopManagement/deleteContact?'+getParam('contactIdList')+'&'+'contactDTO.entityId=${shopDTO.shopId}', '${ctx}/shopManagement/edit.action')" value="删除"/>
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
	  <div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>

	
        <div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>
	</body>
</html>