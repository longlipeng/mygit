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
		<script type="text/javascript" src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
		<title>添加企业客户信息</title>
		<script type="text/javascript">
    function choiceCustomer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
            document.getElementById('groupId').value = customerDTO['customerId'];
        }
    }

    function setCurrentUserId(){
    	document.getElementById('currentUserId').value=${userId};
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
        setCurrentUserId();
    }
    retFlag = false;
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
                if(businessArea==opt){
                	 businessAreaSelect.appendChild(' selected');
                }
            }
            businessAreaSelect.disabled = false;
        }
    }

    function testNubmer(){
    	reg =/^(((([1-9]{1}[0-9]{0,1})|([0]))([.]\d{1,2}))|([1-9]{1}[0-9]{0,1}|[0])|100)?$/;
    	var holdPer =document.getElementById('holdPer').value;
    	if(reg.test(holdPer)){
    		alert(true);
    	}
    }
    function setRegName(){
    	var cusName =document.getElementById('customerNamefield').value;
    	var regName =document.getElementById('regiName');
    	regName.value=cusName;
    }
    function setCustomerName(){
    	var cusName =document.getElementById('customerNamefield');
    	var regName =document.getElementById('regiName').value;
    	cusName.value=regName;
    }
    function changeSaleMan(){
    	var issuerId=document.getElementById("issuerId").value;
    }
    
    /* function display(){
    	var selectValue=document.getElementById("channelId").value;
				if(selectValue==''){
					document.getElementById("time").style.visibility="hidden";
					
				}else{
					document.getElementById("time").style.visibility="";
				}
    } */
    
    function check(key){
		if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8 || key.keyCode==45)
			return true;
		else
			return false;
		}
			
    function punishRecord(flag){
   		/* if(flag.value==1){
   			document.getElementById("punishRecordInfo").style.display='';
   		}else{
   			document.getElementById("punishRecordInfo").style.display='none';
   		} */
    }
    function sub(){
    	
    	var reg = /^[0-9]{0,11}$/;
    	var phoneNum=document.getElementById("corpPhone").value;
    	var flagPhone = reg.test(phoneNum); //true
    	if(flagPhone==false&&phoneNum!=''){
    		alert("法定代表人联系电话有误！");
    		return;
    	}
    	var operatorphoneNum=document.getElementById("operatorTelephoneNumber").value;
    	if(operatorphoneNum !=""||operatorphoneNum !=null){
    		var flagoperatorPhone = reg.test(operatorphoneNum); //true
        	if(flagoperatorPhone==false&&operatorphoneNum!=''){
        		alert("经办人联系电话有误！");
        		return;
        	}
    	}   	
    	var linkphoneNum=document.getElementById("linkphone").value;
    	var flaglinkPhone = reg.test(linkphoneNum); //true
    	if(flaglinkPhone==false&&linkphoneNum!=''){
    		alert("联系人固定电话有误！");
    		return;
    	}
    	/* if(document.getElementById("punishRecordFlag").value==1&&document.getElementById("customerDTO.punishRecordInfo").value==''){
        	document.getElementById("err").style.display='';
        	return;
        }else{
        	document.getElementById("err").style.display='none';
        } */
    	var reg =/^(((([1-9]{1}[0-9]{0,1})|([0]))([.]\d{1,2}))|([1-9]{1}[0-9]{0,1}|[0])|100)?$/;
        var holdPer =document.getElementById('holdPer').value;
        if(!reg.test(holdPer)){
        	alert('控股股东或实际控制人持股比例只能是0-100之间保留两位小数的数值')
        	return;
        }
    	//checkLicense();//此处存在问题！！！导致页面进行了刷新、、、请修改身份证校验部分。。。。
    	//var licenseState= $("#errorMsg").is(":hidden")

    	
    	//if(licenseState){
    		//maskDocAll();
    		submitForm('customerForm', '${ctx}/customer/insert.action');
    	//}
    }

	function dateCheck(){
		if($("#corpCredEndValidity").val()!=''&&$("#corpCredStaValidity").val()!=''){
			if($("#corpCredEndValidity").val()<$("#corpCredStaValidity").val()){
				$("#corpCredEndValidity").val("");
				$("#corpCredStaValidity").val("");
				alert("法定代表人证件有效期错误");
				return;
			}
		}
		if($("#licenseEndValidity").val()!=''&&$("#licenseStaValidity").val()!=''){
			if($("#licenseEndValidity").val()<$("#licenseStaValidity").val()){
				$("#licenseEndValidity").val("");
				$("#licenseStaValidity").val("");
				alert("企业证件有效期错误");
				return;
			}
		}
	}
	//检查企业证件类型是不是重复
	function checkLicense(){
		var CompanyNo=document.getElementById("licenseId").value
   		var type=document.getElementById("companyIdType").value
   		if(type==""){
   		 	$("#errorMsg").hide();
   			return;
   		}
		if(CompanyNo==""){
			 $("#errorMsg").hide();
   			return;
   		}
		
		ajaxRemote('${ctx}/customer/checkLicense.action',{'customerDTO.companyId':CompanyNo,'customerDTO.companyIdType':type}, successFn,'json');
		
	}
	function successFn (res){
		
		if (res == true){
			document.getElementById("licenseId").value="";
			$("#errorMsg").show();
		   } else {
			   $("#errorMsg").hide();
			   retFlag = true;
		   }/* else{
			   alert('页面信息有误！请仔细核对！'); 
		   } */
	}
	 function checksub(type,id){
	    	var idNo=document.getElementById(id).value
	   		var type=document.getElementById(type).value
	   		$("#msg").empty("");
	   		if (idNo == "") {
	   		    $("#msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入证件号！</span></li></ul>');
				return retFlag;
	   		}
	       if(type==1){
	    	   var aa=IdCardValidate(idNo);
	    	   
	    	   if(aa!=true){
	    		   document.getElementById(id).value="";
	    		   return retFlag;
	    	   }
	    	  // ajaxRemote('${ctx}/customer/checkIdNo.action',{'customerDTO.corpCredId':idNo,'customerDTO.corpCredType':type}, successFn,'json');
	    	   return true;
	   		}
	      /*  else if(type==4){
	   			var passport =/^[0-9a-zA-Z]*$/;
				if(passport.test(idNo)!=true){
					document.getElementById("corpCredId").value="";
					$("#msg")
					.html(
							'<ul id="customerForm_" class="errorMessage"><li><span>护照格式不正确！</span></li></ul>');
					return retFlag;
				}
				 ajaxRemote('${ctx}/customer/checkIdNo.action',{'customerDTO.corpCredId':idNo,'customerDTO.corpCredType':type}, successFn,'json');
				 return retFlag;
	   		}else{
	   			ajaxRemote('${ctx}/customer/checkIdNo.action',{'customerDTO.corpCredId':idNo,'customerDTO.corpCredType':type}, successFn,'json');
	   			return retFlag;
			} */
	       return true;
	 }
</script>
	</head>
	<body onload="loadPage()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加客户信息</span>
		</div>
		<s:form id="customerForm" name="customerForm"
			action="customerManagement/insert.action" method="post">
			<s:token></s:token>
			<s:hidden name="customerDTO.customerType" value="0"></s:hidden>
			<div id="customerInfo"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="customerInfoTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
											<s:textfield name="customerDTO.customerName" maxlength="20" id="customerNamefield" onblur="setRegName()"/>
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
											<s:textfield name="customerDTO.customerEnglishName" maxlength="32" />
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
											<s:textfield name="customerDTO.customerAddress" maxlength="75" />
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
											<s:textfield name="customerDTO.customerEnglishAddress" maxlength="32" />
											<s:fielderror>
												<s:param>customerDTO.customerEnglishAddress</s:param>
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
										<td style="width: 100px; text-align: right;">
											客户代码：
										</td>
										<td>
											<s:textfield name="customerDTO.customerCode" maxlength="20" />
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
												onkeypress="return check(event);" maxlength="100" />
											<s:fielderror>
												<s:param>customerDTO.customerFax</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td> 

						</tr>--%>

						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户邮编：
										</td>
										<td>
											<s:textfield name="customerDTO.customerPostcode" maxlength="6" />
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
												onkeypress="return check(event);" maxlength="11"/>
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
											<s:textfield name="customerDTO.customerWebsite" maxlength="75" />
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
											<s:textfield name="customerDTO.email" maxlength="32" />
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
											<s:textfield name="customerDTO.linkphone" maxlength="11" id="linkphone"/>
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
											<s:textfield name="customerDTO.externalId" maxlength="50" />
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
											<s:textfield name="customerDTO.legCusId" maxlength="40" />
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
											<s:textfield name="customerDTO.printName"  maxlength="40"/>
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
											<s:textfield name="customerDTO.extPrintInfo" maxlength="40"/>
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
										<td style="width: 100px; text-align: right;">
											 销售区域：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.salesRegionId"
												dictValue="${customerDTO.salesRegionId}" dictType="407"
												tagType="2" defaultOption="false" />
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
												props="id=\"paymentTerm\" onchange=\"changePaymentTerm()\"" />
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
												id="paymentDelay" disabled="true" maxlength="5" />
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
												props="id=\"businessCity\" onchange=\"changeBusinessCity()\"" />
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
											<select name="customerDTO.businessAreaId" id="businessArea"></select>
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
											<s:select list="salesmanList" name="customerDTO.salesmanId"
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
											<span class="no-empty">*</span> 客户行业：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.activitySector"
												dictValue="${customerDTO.activitySector}" dictType="400"
												tagType="2" defaultOption="false" />
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
												name="customerDTO.cusState"></s:select>
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
											<span class="no-empty">*</span>法定代表人姓名：
										</td>
										<td>
											<s:textfield name="customerDTO.corpName" maxlength="10" id="corpName" />
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
											<s:textfield name="customerDTO.corpPhone" maxlength="11" id="corpPhone" />
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
												dictValue="${customerDTO.corpGender}" dictType="10002" props="id=\"customerDTO.corpGender\"" 
												tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param>customerDTO.corpGender</s:param>
											</s:fielderror>
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
												dictValue="${customerDTO.corpCountyr}" dictType="450" props="id=\"type\""
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
												cssClass="Wdate">
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
											<s:textfield name="customerDTO.corpAddress" maxlength="40" />
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
												dictValue="${customerDTO.corpCredType}" dictType="140" props="id=\"corptype\"" 
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
											<span class="no-empty">*</span>法定代表人证件号：
										</td>
										<td>
											<s:textfield  onblur="checksub('corptype','corpCredId')" maxlength="18" name="customerDTO.corpCredId" id="corpCredId" />
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
											<span class="no-empty">*</span>法定代表人证件有效期结束时间：
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
											<span class="no-empty">*</span>经(代)办人姓名：
										</td>
										<td>
											<s:textfield maxlength="10" name="customerDTO.operatorName" id="operatorName" />
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
												dictValue="${customerDTO.operatorType}" dictType="140" props="id=\"operatorType\"" 
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
											<s:textfield onblur="checksub('operatorType','operatorId')"  name="customerDTO.operatorId" maxlength="18" id="operatorId" />
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
											<span class="no-empty">*</span>经(代)办人证件有效期开始时间：
										</td>
										<td>
											<s:textfield name="customerDTO.operatorStartValidity"
												id="operatorStartValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
												id="operatorValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
											<s:textfield name="customerDTO.operatorTelephoneNumber" maxlength="11" id="operatorTelephoneNumber" />
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
											<s:textfield name="customerDTO.operatorAddress" maxlength="64" id="operatorAddress" />
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
											<s:textfield name="customerDTO.companyAccountant" maxlength="20" id="companyAccountant" />
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
											<s:textfield name="customerDTO.regiName" maxlength="16" id="regiName" onblur="setCustomerName()"/>
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
											<s:textfield name="customerDTO.regiCapital" maxlength="12" id="regiCapital" />
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
										<td>
											<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>企业注册国别：
										</td>
										<td>
												<edl:entityDictList displayName="customerDTO.companyCountyr"
												dictValue="${customerDTO.companyCountyr}" dictType="450" props="id=\"type\""
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
											<span class="no-empty">*</span>企业注册地：
										</td>
										<td>
											<s:textfield name="customerDTO.companyRegisteredAddress" maxlength="20" id="companyRegisteredAddress" />
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
											<span class="no-empty">*</span>企业规模：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.customerSize"
													dictValue="${customerDTO.customerSize}" dictType="194" props="id=\"type\"" 
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
												dictValue="${customerDTO.companyIdType}" dictType="193" props="id=\"companyIdType\" onblur=\"checkLicense()\"" 
												tagType="2" defaultOption="true" />
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
											<s:textfield onblur="checkLicense()" name="customerDTO.licenseId" maxlength="20" id="licenseId" />
											<s:fielderror>
												<s:param>customerDTO.licenseId</s:param>
											</s:fielderror>
											<div id="errorMsg" style="display:none"><ul id="customerForm_" class="errorMessage"><li><span>该公司的证件号已存在！</span></li></ul></div>
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
											<s:textfield name="customerDTO.relName" maxlength="30" id="relName" />
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
												dictValue="${customerDTO.citp}" dictType="140" props="id=\"controlcitp\"" 
												tagType="2" defaultOption="true" />
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
										证件类型说明：
										</td>
										<td>
											<s:textfield name="customerDTO.citpNt" maxlength="10" id="citpNt" />
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
											<s:textfield  onblur="checksub('controlcitp','ctid')" maxlength="18" name="customerDTO.ctid" id="ctid" />
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
												id="ctidStartValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
												id="ctidEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
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
											<s:textfield name="customerDTO.holdPer" maxlength="30" id="holdPer"/>
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
											<s:textfield name="customerDTO.holdAmt" maxlength="30" id="holdAmt" />
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
											<s:textfield name="customerDTO.organizCode" maxlength="30" id="organizCode" />
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
													cssClass="Wdate">
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
											<span class="no-empty">*</span>企业证件有效期结束时间：
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
											<span class="no-empty">*</span>开户银行名称：
										</td>
										<td>
											<s:textfield name="customerDTO.bankPermit" maxlength="15" id="bankPermit" />
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
											<s:textfield name="customerDTO.bankAccount" maxlength="20" id="bankAccount" />
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
												id="peopleNumber" maxlength="10" />
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
											注释信息：
										</td>
										<td>
											<s:textarea name="customerDTO.customerComment" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="15" maxlength="200"
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
											<s:textarea name="customerDTO.companyDescription" onpropertychange="if(value.length>80) value=value.substr(0,80)" cols="15" maxlength="80"
												rows="3"></s:textarea>
											<s:fielderror>
												<s:param>customerDTO.companyDescription</s:param>
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
						<%-- <tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											受理区域：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.acceptArea"
												dictValue="${customerDTO.acceptArea}" dictType="192" tagType="2" defaultOption="false" props="id=\"customerDTO.acceptArea\""/>
										</td>
									</tr>
								</table>
							</td>
							<td></td> --%>
					</table>
				</div>
			</div>	
			<%-- <div id="customerBase2"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="customerBase2Title"
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
				<div id="customerBase2Table"
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
											<edl:entityDictList displayName="customerDTO.creditStatus"
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
												rows="3" id="customerDTO.punishRecordInfo"  maxlength="30"></s:textarea>
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
			</div> --%>
			
			
			
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="sub();" value="提 交" />
									</td>
									<td>
										<input type="button" class="bt" style="margin: 5px"
											onclick="window.location = '${ctx}/customer/inquery.action'"
											value="返 回" />
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