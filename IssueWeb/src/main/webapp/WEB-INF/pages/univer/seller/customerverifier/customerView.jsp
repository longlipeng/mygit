<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>审核企业客户信息</title>
<script type="text/javascript">
    function choiceCustomer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
            document.getElementById('groupId').value = customerDTO['customerId'];
        }
    }

    function changePaymentTerm() {
        
        var paymentTermSelectedId = document.getElementById('paymentTerm').value;

        // 选中无需实时支付时，可以设置延期天数
        if (paymentTermSelectedId == 4) {
            document.getElementById('paymentDelay').disabled = false;
        } else {
            document.getElementById('paymentDelay').value = '';
            document.getElementById('paymentDelay').disabled = true;
        }
    }

    var salesRegions ;
    function loadPage() {
        salesRegions = ${salesRegions};
        changeBusinessCity();
        changePaymentTerm();
    }

    function changeBusinessCity() {
        if ( null != salesRegions ) {
            var salesRegionSelectedId = document.getElementById('businessCity').value;
            var businessAreaData = salesRegions[salesRegionSelectedId];
            var businessAreaSelect = document.getElementById('businessArea');
            businessAreaSelect.innerHTML = '';
            for (var i = 0; i < businessAreaData.length; i++) {
                var businessArea = businessAreaData[i];
                var opt = document.createElement('option');
                opt.value = businessArea['dictCode'];
                opt.innerHTML = businessArea['dictShortName'];
                businessAreaSelect.appendChild(opt);
            }
            businessAreaSelect.disabled = false;
        }
    }
    
    function display(){
    	var selectValue=document.getElementById("channelId").value;
				if(selectValue==''){
					document.getElementById("time").style.visibility="hidden";
					
				}else{
					document.getElementById("time").style.visibility="";
				}
    }

    function reload(){
		document.customerForm.action="${ctx}/customer/edit.action";
		document.customerForm.submit();
	}

	function addEntity(actionUrl){
		var returnValue = window.showModalDialog(actionUrl, '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:600px;resizable:no');
		if(returnValue == 'success'){
			reload();
		}
	}

	var url = null;
	var formName = null;
	
	function delEntity(actionUrl,entityForm,chooseId){
		url = actionUrl;
		formName = entityForm;
		var n=0;
		var checkbox=document.getElementsByName(chooseId);
		for(i=0;i<checkbox.length;i++){
			if(checkbox[i].checked==true){
				n++;
			}
		}
		if(n==0){
			errorDisplay('请选择要删除的对象');
		}	
		if(n>0){
			confirm("确定删除吗？",operDel);
		}
	}

	function operDel(){
		document.forms[formName].action = url;
		document.forms[formName].submit();

		url = null;
		formName = null;
	}

	function updateEntity(entityForm,chooseId){

		var n=0;
		var deliveryId = null;
		var checkbox=document.getElementsByName(chooseId);
		for(i=0;i<checkbox.length;i++){
			if(checkbox[i].checked==true){
				n++;
				deliveryId = checkbox[i].value;
			}
		}
		if(n==0){
			errorDisplay('请选择要编辑的对象');
		}
		if(n>1){
			errorDisplay('请选择一条编辑的对象');
		}
		if(n==1){
			var returnValue = window.showModalDialog('${ctx}/${nameSpace}/editDeliveryPoint.action?deliveryPointDTO.deliveryId='+deliveryId+'&nameSpace=${nameSpace}&entityId=${entityId}',
					 '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:600px;resizable:no');
			if(returnValue == 'success'){
				reload();
			}
			//document.forms[entityForm].action = actionUrl;
			//document.forms[entityForm].submit();
		}
	}
	
	function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8 || key.keyCode==45)
					return true;
				else
					return false;
			}
	function sub(flag){
		maskDocAll();
		customerForm.action='${ctx}/customerVerifier/pass.action';
		document.getElementById("personFlag").value=flag;
		document.getElementById("operateType").value=flag;
		customerForm.submit();
	}
</script>
</head>
<body onload="loadPage()">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>审核企业客户信息</span>
    </div>
    <s:form id="customerForm" name="customerForm" action="" method="post">
    	<s:token></s:token>
        <s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.cusState" id="personFlag"></s:hidden>
    	<s:hidden name="operateType" id="operateType"></s:hidden>
        <s:hidden name="customerDTO.fatherEntityId"></s:hidden>
        <s:hidden name="customerDTO.createTime"></s:hidden>
   <div id="customerInfoTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>客户名称：
										</td>
										<td>
											<s:textfield name="customerDTO.customerName"  disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											英文名称：
										</td>
										<td>
											<s:textfield name="customerDTO.customerEnglishName" maxlength="64"  disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerEnglishName</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>客户地址：
										</td>
										<td>
											<s:textfield name="customerDTO.customerAddress" maxlength="100"  disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerAddress</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											英文地址：
										</td>
										<td>
											<s:textfield name="customerDTO.customerEnglishAddress" maxlength="100"  disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerEnglishAddress</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<%-- <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户代码：
										</td>
										<td>
											<s:textfield name="customerDTO.customerCode" maxlength="32"  disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerCode</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							 <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户传真：
										</td>
										<td>
											<s:textfield name="customerDTO.customerFax"
												onkeypress="return check(event);" maxlength="100" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerFax</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>

						</tr> --%>

						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户邮编：
										</td>
										<td>
											<s:textfield name="customerDTO.customerPostcode" maxlength="10" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerPostcode</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>客户电话：
										</td>
										<td>
											<s:textfield name="customerDTO.customerTelephone"
												onkeypress="return check(event);" maxlength="20" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerTelephone</s:param>
											</s:fielderror>
											<div id="telephone_msg"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户网站：
										</td>
										<td>
											<s:textfield name="customerDTO.customerWebsite" maxlength="100" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.customerWebsite</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>客户邮箱：
										</td>
										<td>
											<s:textfield name="customerDTO.email" maxlength="100" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.email</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>联系人固定电话：
										</td>
										<td>
											<s:textfield name="customerDTO.linkphone" maxlength="100" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.linkphone</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<%-- <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											外部系统代码：
										</td>
										<td>
											<s:textfield name="customerDTO.externalId" maxlength="50" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.externalId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											老系统客户号：
										</td>
										<td>
											<s:textfield name="customerDTO.legCusId" maxlength="50" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.legCusId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr> --%>
						<%--
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">销售渠道：</td>
                                    <td>
                                        <s:select list="customerDTO.channelList" listKey="channelId" listValue="channelName" name="customerDTO.channelId" headerKey="" headerValue="无" onchange="display();" id="channelId"></s:select>
                                        <s:fielderror>
                                            <s:param>customerDTO.channelId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td id="time">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">渠道开始时间：</td>
                                    <td>
                                    	<s:textfield name="customerDTO.chanBegDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                        	<s:param name="value">
                                        			<s:date name="customerDTO.chanBegDate" format="yyyy-MM-dd" />
                                        	</s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>customerDTO.chanBegDate</s:param>
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
										<td style="width: 100px; text-align: right;">
											卡片打印名称：
										</td>
										<td>
											<s:textfield name="customerDTO.printName"  maxlength="64" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.printName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											额外打印信息：
										</td>
										<td>
											<s:textfield name="customerDTO.extPrintInfo" maxlength="50" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.extPrintInfo</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						</tr>


						<tr>
							<!--    <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>失效时间：</td>
                                    <td>
                                    	<s:textfield name="customerDTO.closeDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                            <s:param name="value">
                                            	<s:date name="customerDTO.closeDateDate" format="yyyy-MM-dd" />
                                            </s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>customerDTO.closeDateDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td> -->
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											渠道信息：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.channel"
												dictValue="${customerDTO.channel}" dictType="144"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>customerDTO.channel</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 销售区域：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.salesRegionId"
												dictValue="${customerDTO.salesRegionId}" dictType="407"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>customerDTO.salesRegionId</s:param>
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
										<td style="width: 100px; text-align: right;">
											 缺省支付节点：
										</td>
										<td>
											<dl:dictList displayName="customerDTO.paymentTerm"
												dictValue="${customerDTO.paymentTerm}" dictType="207"
												tagType="2" defaultOption="false"
												props="disabled=\"true\"" />
											<s:fielderror>
												<s:param>customerDTO.paymentTerm</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											延期天数：
										</td>
										<td>
											<s:textfield name="customerDTO.paymentDelay"
												id="paymentDelay" disabled="true" maxlength="5"/>
											<s:fielderror>
												<s:param>customerDTO.paymentDelay</s:param>
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
										<td style="width: 100px; text-align: right;">
											 营业城市：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.businessCity"
												dictValue="${customerDTO.businessCity}" dictType="405"
												tagType="2" defaultOption="false"
												props="disabled=\"true\"" />
											<s:fielderror>
												<s:param>customerDTO.businessCity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 营业区域：
										</td>
										<td>
												<edl:entityDictList displayName="customerDTO.businessAreaId"
												dictValue="${customerDTO.businessAreaId}" dictType="408"
												tagType="2" defaultOption="false"
												props="disabled=\"true\"" />
											<s:fielderror>
												<s:param>customerDTO.businessAreaId</s:param>
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
										<td style="width: 100px; text-align: right;">
											 销售人员：
										</td>
										<td>
											<s:select disabled="true" list="salesmanList" name="customerDTO.salesmanId"
												listKey="userId" listValue="userName" id="currentUserId"></s:select>
											<s:fielderror>
												<s:param>customerDTO.salesmanId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 <span class="no-empty">*</span>客户行业：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.activitySector"
												dictValue="${customerDTO.activitySector}" dictType="400"
												tagType="2" defaultOption="false" props="disabled=\"true\"" />
											<s:fielderror>
												<s:param>customerDTO.activitySector</s:param>
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
										<td style="width: 100px; text-align: right;">
											客户状态：
										</td>
										<td>
											<s:select list="#{'2':'有效', '0':'无效'}"
												name="customerDTO.cusState" disabled="true"></s:select>
											<s:fielderror>
												<s:param>customerDTO.cusState</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 是否发送DM：
										</td>
										<td>
											<s:select disabled="true" list="#{'1':'是', '0':'否'}" name="customerDTO.hasDm"></s:select>
											<s:fielderror>
												<s:param>customerDTO.hasDm</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人姓名：
										</td>
										<td>
											<s:textfield name="customerDTO.corpName" maxlength="20" id="corpName" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.corpName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人联系电话：
										</td>
										<td>
											<s:textfield name="customerDTO.corpPhone" maxlength="20" id="corpPhone" disabled="true" />
											<s:fielderror>
												<s:param>customerDTO.corpPhone</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人性别：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.corpGender"
												dictValue="${customerDTO.corpGender}" dictType="10002" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人国籍：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.corpCountyr"
												dictValue="${customerDTO.corpCountyr}" dictType="450" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.corpCountyr</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人出生日期：
										</td>
										<td>
											<s:textfield name="customerDTO.corpBirthday"
												id="corpBirthday" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate" disabled="true">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.corpBirthday</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人住址：
										</td>
										<td>
											<s:textfield name="customerDTO.corpAddress" maxlength="100" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.corpAddress</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.corpCredType"
												dictValue="${customerDTO.corpCredType}" dictType="140"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>customerDTO.corpCredType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人证件号：
										</td>
										<td>
											<s:textfield  onblur="checksub()" disabled="true" maxlength="18" name="customerDTO.corpCredId" id="corpCredId" />
											<s:fielderror>
												<s:param>customerDTO.corpCredId</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人证件有效期开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.corpCredStaValidity"
												id="corpCredStaValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate" disabled="true">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.corpCredStaValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>法定代表人证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.corpCredEndValidity"
												id="corpCredEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate" disabled="true">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.corpCredEndValidity</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人姓名：
										</td>
										<td>
											<s:textfield maxlength="18" name="customerDTO.operatorName" id="operatorName" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.operatorName</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.operatorType"
												dictValue="${customerDTO.operatorType}" dictType="140" props="disabled=\"true\"" 
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.operatorType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人证件号：
										</td>
										<td>
											<s:textfield onblur="checksub()"  name="customerDTO.operatorId" disabled="true" maxlength="30" id="operatorId" />
											<s:fielderror>
												<s:param>customerDTO.operatorId</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人证件有效期开始时间:
										</td>
										<td>
											<s:textfield name="customerDTO.operatorStartValidity"
												id="operatorStartValidity" size="20" onfocus="dateClick(this)" disabled="true" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.operatorStartValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.operatorValidity"
												id="operatorValidity" size="20" onfocus="dateClick(this)" disabled="true" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.operatorValidity</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>经(代)办人联系电话：
										</td>
										<td>
											<s:textfield name="customerDTO.operatorTelephoneNumber" disabled="true" maxlength="30" id="operatorTelephoneNumber" />
											<s:fielderror>
												<s:param>customerDTO.operatorTelephoneNumber</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											经(代)办人地址：
										</td>
										<td>
											<s:textfield name="customerDTO.operatorAddress" disabled="true" maxlength="30" id="operatorAddress" />
											<s:fielderror>
												<s:param>customerDTO.operatorAddress</s:param>
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
										<td style="width: 100px; text-align: right;">
											公司会计：
										</td>
										<td>
											<s:textfield name="customerDTO.companyAccountant" maxlength="30" disabled="true" id="companyAccountant" />
											<s:fielderror>
												<s:param>customerDTO.companyAccountant</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>注册名称：
										</td>
										<td>
											<s:textfield name="customerDTO.regiName" maxlength="30" id="regiName" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.regiName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>注册资本：
										</td>
										<td>
											<s:textfield name="customerDTO.regiCapital" maxlength="10" id="regiCapital" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.regiCapital</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业注册国别：
										</td>
										<td>
												<edl:entityDictList displayName="customerDTO.companyCountyr"
												dictValue="${customerDTO.companyCountyr}" dictType="450" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.companyCountyr</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业注册地址：
										</td>
										<td>
											<s:textfield name="customerDTO.companyRegisteredAddress" disabled="true" maxlength="30" id="companyRegisteredAddress" />
											<s:fielderror>
												<s:param>customerDTO.companyRegisteredAddress</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业经营地址：
										</td>
										<td>
											<s:textfield name="customerDTO.companyRegisteredAddress" disabled="true" maxlength="30" id="companyRegisteredAddress" />
											<s:fielderror>
												<s:param>customerDTO.companyRegisteredAddress</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业规模：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.customerSize"
													dictValue="${customerDTO.customerSize}" dictType="194" props="disabled=\"true\""
													tagType="2" defaultOption="false" />
												<s:fielderror>
													<s:param>customerDTO.customerSize</s:param>
												</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
							
							
							</td>
						
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.companyIdType"
												dictValue="${customerDTO.companyIdType}" dictType="193" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.companyIdType</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业证件号码：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseId" maxlength="30" id="licenseId" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.licenseId</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人姓名：
										</td>
										<td>
											<s:textfield name="customerDTO.relName" maxlength="30" id="relName" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.relName</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件类型
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.citp"
												dictValue="${customerDTO.citp}" dictType="140" props="disabled=\"true\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.citp</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>证件类型说明：
										</td>
										<td>
											<s:textfield name="customerDTO.citpNt" maxlength="30" id="citpNt" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.citpNt</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件号:
										</td>
										<td>
											<s:textfield  onblur="checksub()" maxlength="18" name="customerDTO.ctid" id="ctid" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.ctid</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.ctidStartValidity"
												id="ctidStartValidity" size="20" onfocus="dateClick(this)" disabled="true" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.ctidStartValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.ctidEndValidity"
												id="ctidEndValidity" size="20" onfocus="dateClick(this)" disabled="true" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.ctidEndValidity</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人持股比例：
										</td>
										<td>
											<s:textfield name="customerDTO.holdPer" maxlength="30" id="holdPer" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.holdPer</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>控股股东或实际控制人持股金额：
										</td>
										<td>
											<s:textfield name="customerDTO.holdAmt" maxlength="30" id="holdAmt" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.holdAmt</s:param>
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
										<td style="width: 100px; text-align: right;">
											组织机构代码：
										</td>
										<td>
											<s:textfield name="customerDTO.organizCode" maxlength="30" id="organizCode" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.organizCode</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<%-- <td>
								<table style="text-align: left; width: 100%">
									<tr>
											<td style="width: 100px; text-align: right;">
												主体证件有效期：
											</td>
											<td>
												<s:textfield name="customerDTO.ctidEdt"
													id="ctidEdt" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
													cssClass="Wdate" disabled="true">
												</s:textfield>
												<s:fielderror>
													<s:param>customerDTO.ctidEdt</s:param>
												</s:fielderror>
											</td>
										</tr>
								</table>
							</td> --%>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业证件有效期开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseStaValidity"
												id="licenseStaValidity" size="20" onfocus="dateClick(this)" disabled="true" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.licenseStaValidity</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseEndValidity"
												id="licenseEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()" disabled="true"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.licenseEndValidity</s:param>
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
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>开户银行名称：
										</td>
										<td>
											<s:textfield name="customerDTO.bankPermit" maxlength="30" id="bankPermit" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.bankPermit</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>银行账号：
										</td>
										<td>
											<s:textfield name="customerDTO.bankAccount" maxlength="30" id="bankAccount" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.bankAccount</s:param>
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
										<td style="width: 100px; text-align: right;">
											员工人数：
										</td>
										<td>
											<s:textfield name="customerDTO.peopleNumber"
												id="peopleNumber" maxlength="10" disabled="true"/>
											<s:fielderror>
												<s:param>customerDTO.peopleNumber</s:param>
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
										<td style="width: 100px; text-align: right;">
											黑名单标识：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.isblacklist"
												dictValue="${customerDTO.isblacklist}" dictType="196"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>customerDTO.isblacklist</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											风险等级：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.riskGrade"
												dictValue="${customerDTO.riskGrade}" dictType="197"
												tagType="2" defaultOption="false" props="disabled=\"true\""/>
											<s:fielderror>
												<s:param>customerDTO.riskGrade</s:param>
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
										<td style="width: 100px; text-align: right;">
											注释信息：
										</td>
										<td>
											<s:textarea disabled="true" name="customerDTO.customerComment" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="15"
												rows="3"></s:textarea>
											<s:fielderror>
												<s:param>customerDTO.customerComment</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>公司描述(经营范围)：
										</td>
										<td>
											<s:textarea disabled="true" name="customerDTO.companyDescription" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="15"
												rows="3"></s:textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											上年度客户购卡总金额：
										</td>
										<td>
											<s:textfield name="customerDTO.lastYear" id="lastYear" value="0" disabled="true"></s:textfield>
										</td>
									</tr>
								</table>
							</td>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
        
			<%-- <div id="customerBase2"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="customerBaseTitle2"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront">
								<span class="TableTop">风控信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="customerBaseTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
												<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 客户知名度：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.awareness"
												dictValue="${customerDTO.awareness}" dictType="142"
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.awareness</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 五证是否齐全：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.fiveCertificate"></s:select>
											<s:fielderror>
												<s:param>customerDTO.fiveCertificate</s:param>
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
										<td style="width: 100px; text-align: right;">
											 是否分支机构联系人有签名及盖公章：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}" name="customerDTO.survey"></s:select>
											<s:fielderror>
												<s:param>customerDTO.survey</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 征信系统是否已征信：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.creditInformation"></s:select>
											<s:fielderror>
												<s:param>customerDTO.creditInformation</s:param>
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
										<td style="width: 100px; text-align: right;">
											 法定代表人身份信息是否已核查：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.identityInspect"></s:select>
											<s:fielderror>
												<s:param>customerDTO.identityInspect</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 不良出票人系统是否已核查：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.badnessInspect"></s:select>
											<s:fielderror>
												<s:param>customerDTO.badnessInspect</s:param>
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
										<td style="width: 100px; text-align: right;">
											 客户证件照片信息是否已存档：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.pictureSave"></s:select>
											<s:fielderror>
												<s:param>customerDTO.pictureSave</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 客户是否卷入法律诉讼：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.actionAtLaw"></s:select>
											<s:fielderror>
												<s:param>customerDTO.actionAtLaw</s:param>
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
										<td style="width: 100px; text-align: right;">
											 客户负责人（法定代表人代表或高管）是否卷入法律诉讼：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.corpActionAtLaw"></s:select>
											<s:fielderror>
												<s:param>customerDTO.corpActionAtLaw</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 客户负责人（法定代表人）的信用状况：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.creditStatus" props="id='creditStatus'"
												dictValue="${customerDTO.creditStatus}" dictType="143"
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.creditStatus</s:param>
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
										<td style="width: 100px; text-align: right;">
											 是否被执法部门处罚记录：
										</td>
										<td>
											<s:select list="#{2:'',1:'是',0:'否'}"
												name="customerDTO.punishRecordFlag"
												onchange="punishRecord(this);" id="punishRecordFlag"></s:select>
											<s:fielderror>
												<s:param>customerDTO.punishRecordFlag</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td id='punishRecordInfo' style="display: none;">
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 被执法部门处罚原因：
										</td>
										<td>
											<s:textarea name="customerDTO.punishRecordInfo" cols="15"
												rows="3" id="customerDTO.punishRecordInfo"></s:textarea>
											<label id="err" style="display: none">
												<font color="red">处罚原因不能为空</font>
											</label>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>	 
			</div>
     --%>
<div id="base" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="baseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">合同信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="baseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>客户号：</td>
                                    <td><s:textfield id="contractBuyer" name="sellerContractDTO.contractBuyer" readonly="true" />
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.contractBuyer</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">客户名称：</td>
                                    <td><s:textfield id="sellerName" name="sellerContractDTO.contractBuyerName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期规则：</td>
                                    <td><s:textfield id="ruleNo" name="sellerContractDTO.settlePeriodRule" cssClass="watch" readonly="true" onclick="choiceSettleRule()"/>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.settlePeriodRule</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期规则名称：</td>
                                    <td><s:textfield id="ruleName" name="sellerContractDTO.settlePeriodRuleName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>失效时间：</td>
                                    <td>
                                    	<s:textfield name="sellerContractDTO.expiryDate"
												id="cardValidityPeriod"  >
										</s:textfield>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>合同状态：</td>
                                    <td>
                                        <s:select name="sellerContractDTO.contractState" list="#{'1':'有效', '0':'无效'}" />
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.contractState</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
            <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">特殊信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="specialtyTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                       
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费：</td>
                                    <td><s:textfield name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee ) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>
                                        <s:hidden name="sellerContractDTO.deliveryFee" id="deliveryFee"></s:hidden>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.deliveryFee</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否支持网上支付：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webPayStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否短信通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.smsSvcStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否邮件通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.emailSvcStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否短信通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webSmsSvcStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否邮件通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webEmailSvcStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否开通月报服务：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.monstmtSvcStat"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

      
                
      
	    <br /><br />

    
	<s:iterator value="productContractDTOs" id="map" var="map">
	<div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
	<div id="baseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer">
                    <span class="TableTop">产品明细</span>
                </td>
                <td class="TableTitleEnd">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    <div id="" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
        <table width="95%" style="table-layout: fixed;">
        	<tr>
                        <td>
                            <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">合同产品明细号：</td>
                                    <td>
                                    ${map.id}
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <%--<td>
                            <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品名称：</td>
                                    <td>
									</td>
                                </tr>
                            </table>
                        </td>
            		--%>
            		<td>
            		 <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品号：</td>
                                    <td>
                                     ${map.productId}
									</td>
                                </tr>
                            </table>
                        </td>
            </tr>
            <tr>
                <td>
                    <table style="text-align: left; width: 90%">
                        <tr>
                            <td style="width: 140px; text-align: right;">卡费：</td>
                            <td>
								${map.cardFee/100}
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="text-align: left; width: 90%">
                        <tr>
                            <td style="width: 140px; text-align: right;">卡年费：</td>
                            <td>
								${map.annualFee / 100}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    
    <div id="service" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
    <div id="serviceTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer">
                    <span class="TableTop">账户明细</span>
                </td>
                <td class="TableTitleEnd">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    <div id="" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
    <s:hidden id="sellerAcctypeContractDTO.id" name="sellerAcctypeContractDTO.id"/>
       	<ec:table items="map.accDTOs" var="account" width="95%"
            imagePath="${ctx}/images/extremecomponents/*.gif"
            view="html"
            autoIncludeParameters="false"
            showPagination="false"
            sortable="false">
            <ec:row>
               <ec:column property="id" title="账户合同号" width="15%"  />
               <ec:column property="productId" title="产品编号" width="15%" />
               <ec:column property="acctypeId" title="账户编号" width="15%"  />
               <ec:column property="ruleNo" title="计算规则" width="15%" />
            </ec:row>
        </ec:table>
      
    </div>
</div>

</div>
</s:iterator>
<div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                	<!-- <input type="button" class="bt" style="margin: 5px" onclick="sub('5');" value="黑名单校验"/> -->
                                    <input type="button" class="bt" style="margin: 5px" onclick="sub('1');" value="通过"/>
                                    <input type="button" class="bt" style="margin: 5px" onclick="sub('3');" value="不通过"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        </s:form>
</body>
<!-- <script type="text/javascript">
    	if(document.getElementById("punishRecordFlag").value=='1'){
    		document.getElementById("punishRecordInfo").style.display='';
    	}
    </script> -->
</html>