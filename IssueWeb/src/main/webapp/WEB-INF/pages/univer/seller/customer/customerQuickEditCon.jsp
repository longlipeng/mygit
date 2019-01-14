<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript" src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
<title>编辑客户信息</title>
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
		document.customerForm.action="${ctx}/customer/quickEdit.action?personFlag=con";
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
	function punishRecord(flag){
   		if(flag.value==1){
   			document.getElementById("punishRecordInfo").style.display='';
   		}else{
   			document.getElementById("punishRecordInfo").style.display='none';
   		}
    }
    function sub(){
        /*
    	if("${banklistsize}"=="0"){
        	errorDisplay("请添加银行信息!");
			return ;
        }        
        if("${invoiceAddressListSize}"=="0"){
        	errorDisplay("请添加发票地址信息!");
			return ;
        }
        if("${invoiceCompanyListSize}"=="0"){
        	errorDisplay("请添加发票公司信息!");
			return ;
        }
        if("${deliveryPointListSize}"=="0"){
        	errorDisplay("请添加快递点信息!");
			return ;
        }
        if("${contractListSize}"=="0"){
        	errorDisplay("请添加联系人信息!");
			return ;
        }
        if("${departmentListSize}"=="0"){
        	errorDisplay("请添加部门信息!");
			return ;
        } */
   		if(document.getElementById("punishRecordFlag").value==1&&document.getElementById("customerDTO.punishRecordInfo").value==''){
   			document.getElementById("err").style.display='';
   			return;
   		}else{
   			document.getElementById("err").style.display='none';
   		}
   		submitForm('customerForm', '${ctx}/customer/quickUpdate.action');
   		var  returnVlaue = ${customerDTO.entityId};
   		window.returnValue=returnVlaue;
		window.close();
   }

    function editContact(entityForm,chooseId){

		var n=0;
		var contactId = null;
		var checkbox=document.getElementsByName(chooseId);
		for(i=0;i<checkbox.length;i++){
			if(checkbox[i].checked==true){
				n++;
				contactId = checkbox[i].value;
			}
		}
		if(n==0){
			errorDisplay('请选择要编辑的对象');
		}
		if(n>1){
			errorDisplay('请选择一条编辑的对象');
		}
		if(n==1){
			var returnValue = window.showModalDialog('${ctx}/${nameSpace}/editContact.action?contactDTO.contactId='+contactId+'&nameSpace=${nameSpace}&entityId=${customerDTO.entityId}',
					 '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:600px;resizable:no');
			if(returnValue == 'success'){
				reload();
			}
			//document.forms[entityForm].action = actionUrl;
			//document.forms[entityForm].submit();
		}
	}
	 function updateInfo(checkedId, url){
			var n=0;
			var checkbox=document.getElementsByName(checkedId);
			var id='';
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				var acctypeValue=window.showModalDialog(url+'?checkedId='+id+'&entityId='+document.getElementById('entityId').value, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(acctypeValue == 'success'){
					reload();
					//document.sellerForm.action = "${ctx}/seller/edit.action";
					//document.sellerForm.submit();	
					//document.getElementById("newForm").action="reLoad.action";
		 			//document.getElementById('newForm').submit();
		 		}
			}
			
		}
	
	function dateCheck(){
		if($("#corpCredEndValidity").val()!=''&&$("#corpCredStaValidity").val()!=''){
			if($("#corpCredEndValidity").val()<$("#corpCredStaValidity").val()){
				$("#corpCredEndValidity").val("");
				$("#corpCredStaValidity").val("");
				alert("法人证件有效期错误");
				return;
			}
		}
		if($("#licenseEndValidity").val()!=''&&$("#licenseStaValidity").val()!=''){
			if($("#licenseEndValidity").val()<$("#licenseStaValidity").val()){
				$("#licenseEndValidity").val("");
				$("#licenseStaValidity").val("");
				alert("营业执照有效期错误");
				return;
			}
		}
	}
</script>
</head>
<body onload="loadPage()">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>编辑客户信息</span>
    </div>
    
    <s:form id="customerForm" name="customerForm" action="" method="post">
        <s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
        <div id="customerBase" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="customerBaseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">客户信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="customerBaseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>客户名称：</td>
                                    <td>
                                    	<s:textfield name="customerDTO.customerName"/>
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
                                    <td style="width: 100px; text-align: right;">英文名称：</td>
                                    <td><s:textfield name="customerDTO.customerEnglishName"/>
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
                                    <td style="width: 100px; text-align: right;">客户地址：</td>
                                    <td><s:textfield name="customerDTO.customerAddress"/>
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
                                    <td style="width: 100px; text-align: right;">英文地址：</td>
                                    <td><s:textfield name="customerDTO.customerEnglishAddress"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.customerEnglishAddress</s:param>
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
                                    <td style="width: 100px; text-align: right;">客户代码：</td>
                                    <td>
                                    	<s:textfield name="customerDTO.customerCode"/>
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
                                    <td style="width: 100px; text-align: right;">客户传真：</td>
                                    <td>
                                    	<s:textfield name="customerDTO.customerFax" />
                                        <s:fielderror>
                                            <s:param>customerDTO.customerFax</s:param>
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
                                    <td style="width: 100px; text-align: right;"> 客户邮编：</td>
                                    <td><s:textfield name="customerDTO.customerPostcode"/>
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
                                    <td style="width: 100px; text-align: right;"> 客户电话：</td>
                                    <td><s:textfield name="customerDTO.customerTelephone"  onkeypress="return check(event);"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.customerTelephone</s:param>
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
                                    <td style="width: 100px; text-align: right;"> 客户规模：</td>
                                    <td><s:textfield name="customerDTO.customerSize"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.customerSize</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                  
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">客户网站：</td>
                                    <td><s:textfield name="customerDTO.customerWebsite"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.customerWebsite</s:param>
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
                                    <td style="width: 100px; text-align: right;">外部系统代码：</td>
                                    <td><s:textfield name="customerDTO.externalId"/>
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
                                    <td style="width: 100px; text-align: right;">老系统客户号：</td>
                                    <td><s:textfield name="customerDTO.legCusId"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.legCusId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty"></span>卡片打印名称：</td>
                                    <td><s:textfield name="customerDTO.printName"/>
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
                                    <td style="width: 100px; text-align: right;">额外打印信息：</td>
                                    <td><s:textfield name="customerDTO.extPrintInfo"/>
                                        <s:fielderror>
                                            <s:param>customerDTO.extPrintInfo</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    
                    <tr>
                    <!--   <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"> 失效时间：</td>
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
                        </td>-->
                        <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											 渠道信息：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.channel"
												dictValue="${customerDTO.channel}" dictType="144"
												tagType="2" defaultOption="false" />
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
                                    <td style="width: 100px; text-align: right;"> 销售区域：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerDTO.salesRegionId" dictValue="${customerDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="false" />
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
                                    <td style="width: 100px; text-align: right;"> 缺省支付节点：</td>
                                    <td>
                                        <dl:dictList displayName="customerDTO.paymentTerm" dictValue="${customerDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="false" props="id=\"paymentTerm\" onchange=\"changePaymentTerm()\""/>
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
                                    <td style="width: 100px; text-align: right;">延期天数：</td>
                                    <td><s:textfield name="customerDTO.paymentDelay" id="paymentDelay" disabled="true"/>
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
                                    <td style="width: 100px; text-align: right;"> 营业城市：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerDTO.businessCity" dictValue="${customerDTO.businessCity}" dictType="405" tagType="2" defaultOption="false" props="id=\"businessCity\" onchange=\"changeBusinessCity()\""/>
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
                                    <td style="width: 100px; text-align: right;"> 营业区域：</td>
                                    <td>
                                        <select name="customerDTO.businessAreaId"  id="businessArea"></select>
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
                                    <td style="width: 100px; text-align: right;"> 销售人员：</td>
                                    <td>
                                        <s:select list="salesmanList" name="customerDTO.salesmanId" listKey="userId" listValue="userName" headerKey="" headerValue="--请选择--"></s:select>
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
                                    <td style="width: 100px; text-align: right;"> 客户行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerDTO.activitySector" dictValue="${customerDTO.activitySector}" dictType="400" tagType="2" defaultOption="false"/>
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
                                    <td style="width: 100px; text-align: right;"> 是否发送DM：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="customerDTO.hasDm"></s:select>
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
											法人信息：
										</td>
										<td>
											<s:textfield name="customerDTO.corpName" id="corpName" />
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
											法人联系电话：
										</td>
										<td>
											<s:textfield name="customerDTO.corpPhone" id="corpPhone" />
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
											法人证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.corpCredType"
												dictValue="${customerDTO.corpCredType}" dictType="140"
												tagType="2" defaultOption="false" />
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
											法人证件号：
										</td>
										<td>
											<s:textfield name="customerDTO.corpCredId" id="corpCredId" />
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
											法人证件有效开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.corpCredStaValidity"
												id="corpCredStaValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
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
											法人证件有效结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.corpCredEndValidity"
												id="corpCredEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
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
											注册名称：
										</td>
										<td>
											<s:textfield name="customerDTO.regiName" id="regiName" />
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
											注册资本：
										</td>
										<td>
											<s:textfield name="customerDTO.regiCapital" id="regiCapital" />
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
											营业执行照号码：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseId" id="licenseId" />
											<s:fielderror>
												<s:param>customerDTO.licenseId</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											组织机构代码：
										</td>
										<td>
											<s:textfield name="customerDTO.organizCode" id="organizCode" />
											<s:fielderror>
												<s:param>customerDTO.organizCode</s:param>
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
											营业执照有效期开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseStaValidity"
												id="licenseStaValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
											营业执照有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.licenseEndValidity"
												id="licenseEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
											银行开户许可证号：
										</td>
										<td>
											<s:textfield name="customerDTO.bankPermit" id="bankPermit" />
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
											员工人数：
										</td>
										<td>
											<s:textfield name="customerDTO.peopleNumber"
												id="peopleNumber" />
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
                                    <td style="width: 100px; text-align: right;">注释信息：</td>
                                    <td>
                                    	<s:textarea name="customerDTO.customerComment" cols="15" rows="3"></s:textarea>
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
											上年度客户购卡总金额：
										</td>
										<td>
											<s:textfield name="customerDTO.lastYear" id="lastYear" value="0" disabled="true"></s:textfield>
										</td>
									</tr>
								</table>
							</td>
                    </tr>
                </table>
            </div>
        </div>
        
			<div id="customerBase2"
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
											 法人身份信息是否已核查：
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
											 客户负责人（法人代表或高管）是否卷入法律诉讼：
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
											 客户负责人（法人）的信用状况：
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
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="sub();" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.close();" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
    <script type="text/javascript">
    	if(document.getElementById("punishRecordFlag").value=='1'){
    		document.getElementById("punishRecordInfo").style.display='';
    	}
    	
    </script>
    <div id="entityDiv" style="">
    	<s:hidden id="entityId" name="entityId" />
    		 
		<s:form id="bankForm" name="bankForm" action="" method="post">
		<s:hidden name="entityId" />
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
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
			<ec:table items="bankList" var="map" width="100%"
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
					<ec:column property="bankName" title="银行名称" width="30%">
						<s:property value="#attr['map'].bankName" />
						<s:property value="#attr['map'].accountFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
					<ec:column property="bankAccount" title="银行账户" width="30%"/>
					<ec:column property="bankAccountName" title="银行账户名称" width="30%"/>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addBank.action?entityId=${customerDTO.entityId}')"
										value="添加" />
								</td>								
								 <td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseBankId','${ctx}/customer/editBank.action');"
										value="编辑" />
								</td> 
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delBankQuick.action?personFlag=con','bankForm','chooseBankId')"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		<s:form id="depForm" name="depForm" action="" method="post">
		<s:hidden name="entityId" />
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		<div id="departmentTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('departmentTable')">
						<span class="TableTop">部门信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div id="departmentTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="departmentList" var="map" width="100%"
				action="${ctx}/departmentManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" form ="depForm"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="chooseDepartmentId" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="chooseDepartmentId"
							value="${map.departmentId}" />
					</ec:column>
					<ec:column property="departmentId" title="部门号" width="40%" />
					<ec:column property="departmentName" title="部门名称" width="50%">
						<s:property value="#attr['map'].departmentName" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addDepartment.action?entityId=${customerDTO.entityId}')"
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseDepartmentId', '${ctx}/customer/editDepartment.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delDepartmentQuick.action?personFlag=con','depForm','chooseDepartmentId')"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		
	   <s:form id="contractForm" name="contractForm" action="" method="post">
		<s:hidden name="entityId" />
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		<div id="contactTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
							onclick="showOrHideDiv('contactTable')">
							<span class="TableTop">
					<s:textfield name="conName" id="conName" size="10" cssClass="phone" readonly="true" value="经办人信息"/>
					</span>
						</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div id="contactTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">		
			<ec:table items="contractList" var="map" width="100%"
				action=""
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="contact"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="chooseContactId" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="chooseContactId"
							value="${map.contactId}" />
					</ec:column>
					<ec:column property="contactName" title="姓名" width="30%">
						<s:property value="#attr['map'].contactName" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
					<ec:column property="contactFunction" title="职位" width="20%" />
					<ec:column property="contactTelephone" title="电话" width="20%" />
					<ec:column property="validFlag" title="有效状态" width="20%">
						<s:if test="#attr['map'].validityFlag ==1">有效
						</s:if>
						<s:else>无效</s:else>
					</ec:column>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addContact.action?entityId=${customerDTO.entityId}')"
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseContactId','${ctx}/consumer/editContact.action');"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delContactQuick.action?personFlag=con','contractForm','chooseContactId')"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</s:form>	
	
	  <s:form id="deliveryPointForm" name="deliveryPointForm" action="" method="post">
		<s:hidden name="entityId" />
		<s:hidden name="nameSpace"/> 
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		<div id="deliveryPointTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('deliveryPointTable')">
						<span class="TableTop">快递点信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>

		<div id="deliveryPointTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="deliveryPointList" var="map" width="100%"
				action="${ctx}/deliveryPointManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="deliveryPoint"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="choosePointId" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="choosePointId"
							value="${map.deliveryId}" />
					</ec:column>
					<ec:column property="deliveryPointName" title="快递点名称" width="40%">
						<s:property value="#attr['map'].deliveryName" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
					<ec:column property="deliveryAddress" title="地址" width="50%" />
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addDeliveryPoint.action?entityId=${customerDTO.entityId}')"
										value="添加" ${disabled}/>
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateEntity('deliveryPointForm','choosePointId');"
										value="编辑" ${disabled}/>
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delDeliveryPointQuick.action?personFlag=con','deliveryPointForm','choosePointId');"
										value="删除" ${disabled}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		
		<div id="invoiceAddressTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('invoiceAddressTable')">
						<span class="TableTop">发票地址信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	
		<s:form id="addressForm" name="addressForm" action="" method="post">
		<s:hidden id="addressId" name="addressId" />
		<s:hidden name="entityId" /> 
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		<div id="invoiceAddressTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="invoiceAddressList" var="map" width="100%"
				action="${ctx}/invoiceAddressManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="invoiceAddress"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="chooseAddressId" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="chooseAddressId"
							value="${map.invoiceAddressId}" />
					</ec:column>
					<ec:column property="invoiceAddress" title="发票地址" width="30%">
						<s:property value="#attr['map'].invoiceAddress" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
					<ec:column property="addressPostcode" title="邮编" width="20%" />
					<ec:column property="invoiceRecipient" title="收件人" width="20%" />
					<ec:column property="deliveryOption" title="订送方式" width="20%">
						<s:if test="#attr['map'].deliveryOption == 1">送货上门</s:if>
						<s:else>上门取货</s:else>
					</ec:column>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addAddress.action?entityId=${customerDTO.entityId}')" 
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseAddressId', '${ctx}/customer/editAddress.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delAddressQuick.action?personFlag=con','addressForm','chooseAddressId');"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="invoiceCompanyTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('invoiceCompanyTable')">
						<span class="TableTop">发票公司信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</s:form>
		
	<s:form id="companyForm" name="companyForm" action="" method="post">
		<s:hidden name="entityId" /> 
		<s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		<s:hidden id="invoiceCompanyId" name="invoiceCompanyId" />
		<div id="invoiceCompanyTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
             <ec:table tableId="acctype" items="invoiceCompanyList"
				var="map" width="100%" form="EditForm"
				action="${ctx}/issuer!inquery.action"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" showPagination="false"
				sortable="false">
				<ec:row>
				   <ec:column property="null" alias="chooseCompanyId" title="选择"
					   width="10%" sortable="false" headerCell="selectAll">
					    <input type="checkbox" name="chooseCompanyId" value="${map.invoiceCompanyId}" />
				   </ec:column>
					<ec:column property="invoiceCompanyName" title="发票公司" width="40%">
					    <s:property value="#attr['map'].invoiceCompanyName" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addEntity('${ctx}/customer/addCompany.action?entityId=${customerDTO.entityId}')" value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseCompanyId', '${ctx}/customer/editCompany.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="delEntity('${ctx}/customer/delCompanyQuick.action?personFlag=con','companyForm','chooseCompanyId')" value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form> 
    </div>
    <script type="text/javascript">
    	document.getElementById("conName").value="经办人信息";
    </script>
</body>
</html>