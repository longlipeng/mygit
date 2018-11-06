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
		<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
		<title>添加个人客户信息</title>
		<script type="text/javascript">

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

    function setCurrentUserId(){
    	document.getElementById('currentUserId').value=${userId};
    }
    function loadPage() {
        salesRegions = ${salesRegions};
        changeBusinessCity();
        changePaymentTerm();
        setCurrentUserId();
    }
    
    
    
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
    var retFlag = false;
    
    
    function checksub(){
    	var idNo=document.getElementById("corpCredId").value
   		var type=document.getElementById("customerDTO.corpCredType").value
   		$("#msg").empty("");
   		if (idNo == "") {
   		    $("#msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入证件号！</span></li></ul>');
			return retFlag;
   		}
       if(type==1){
    	   var aa=IdCardValidate(idNo);
    	   
    	 if(aa!=true){
    		   document.getElementById("corpCredId").value="";
    		   return retFlag;
    	   }
    	   ajaxRemote('${ctx}/customer/checkIdNo.action',{'customerDTO.corpCredId':idNo,'customerDTO.corpCredType':type,'customerDTO.customerType':'1'}, successFn,'json');
    	 
    	 return retFlag;
   		}else{
   			ajaxRemote('${ctx}/customer/checkIdNo.action',{'customerDTO.corpCredId':idNo,'customerDTO.corpCredType':type,'customerDTO.customerType':'1'}, successFn,'json');
   			return retFlag;
		} 
       return true;
    }
	function successFn (res){
		
		if (res == true){
			document.getElementById("corpCredId").value="";
			retFlag = false;
			   $("#msg").html('<ul id="customerForm_" class="errorMessage"><li><span>该证件号已存在！</span></li></ul>');
		   } else {
			   retFlag = true;
		   }
	}
    function sub(){
    	
    	$("#name_msg").empty("");
    	$("#telephone_msg").empty("");
    	$("#address_msg").empty("");
    	$("#corpCredEndValidity_msg").empty("");
    	/* $("#city_msg").empty("");
    	$("#nationality_msg").empty(""); */
    	
    	var hasEmpty = false;
    	
   		/* if(document.getElementById("punishRecordFlag").value==1&&document.getElementById("customerDTO.punishRecordInfo").value==''){
   			document.getElementById("err").style.display='';
   			return;
   		}else{
   			document.getElementById("err").style.display='none';
   		} */
   	/* 	if (document.getElementById("customerForm_customerDTO_customerName").value=="") {
   			hasEmpty = true;
   		    $("#name_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入名称！</span></li></ul>');
   		}
   		if (document.getElementById("corpCredEndValidity").value=="") {
   			hasEmpty = true;
   		    $("#corpCredEndValidity_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入证件结束日期！</span></li></ul>');
   		} 
   		if (document.getElementById("customerForm_customerDTO_customerTelephone").value=="") {
   			hasEmpty = true;
   		    $("#telephone_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入联系电话！</span></li></ul>');
   		}
   		if (document.getElementById("customerForm_customerDTO_customerAddress").value=="") {
   			hasEmpty = true;
   		    $("#address_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入地址！</span></li></ul>');
   		} */
   		/* if (document.getElementById("nationality").value=="") {
   			hasEmpty = true;
   		    $("#nationality_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入国籍！</span></li></ul>');
   		}
   		if (document.getElementById("city").value=="") {
   			hasEmpty = true;
   		    $("#city_msg").html('<ul id="customerForm_" class="errorMessage"><li><span>请输入城市！</span></li></ul>');
   		} */
   		
   		
   	/* 	if(checksub()==false){
   			return;
   		}
   		
   		if(retFlag == false){
			return;
		}
   		if(hasEmpty){
   			return;
   		} */
   		var customerDTO_channel = $("select[name=customerDTO.channel] option:selected");
   		if (customerDTO_channel.val() == '1') {
   			alert("不能添加渠道为  网络 的客户信息！");
   			return;
   		}
   		/* if (retFlag){
	   		var customerComment=document.getElementById("customerComment").value;
	   		if(customerComment!=""){
	   			if(customerComment.length>300){
	   				alert("注释信息不能超过300！");
	   				return;
	   			}
	   		} */
   		//}
	   	submitForm('customerForm', '${ctx}/customer/insertPersion.action');
   }
   
	function dateCheck(){
		if($("#corpCredEndValidity").val()!=''&&$("#corpCredStaValidity").val()!=''){
			if($("#corpCredEndValidity").val()<$("#corpCredStaValidity").val()){
				$("#corpCredStaValidity").val("");
				$("#corpCredEndValidity").val("");
				alert("客户证件有效期错误");
			}
		}
	}
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>添加客户信息</span>
		</div>
		<s:form id="customerForm" name="customerForm"
			action="customerManagement/insert.action" method="post">
			<s:token></s:token>
			<s:hidden name="customerDTO.customerType" value="1"></s:hidden>
			<div id="customerBase"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="customerBaseTitle"
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
				<div id="customerBaseTable"
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
											<s:textfield name="customerDTO.customerName" maxlength="20"/>
											<s:fielderror>
												<s:param>customerDTO.customerName</s:param>
											</s:fielderror>
											<div id="name_msg"></div>
										</td>
									</tr>
								</table>
							</td>
							
							<%-- <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户代码：
										</td>
										<td>
											<s:textfield name="customerDTO.customerCode"  maxlength="32" />
											<s:fielderror>
												<s:param>customerDTO.customerCode</s:param>
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
											<span class="no-empty">*</span>客户证件类型：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.corpCredType"
												dictValue="${customerDTO.corpCredType}" dictType="140" props="id=\"customerDTO.corpCredType\" onblur=\"checksub()\"" 
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
											<span class="no-empty">*</span>客户证件号：
										</td>
										<td>
											<s:textfield onblur="checksub()" name="customerDTO.corpCredId" id="corpCredId" maxlength="18"/>
											<s:fielderror>
												<s:param>customerDTO.corpCredId</s:param>
											</s:fielderror>
											<div id="msg"></div>
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
											<span class="no-empty">*</span>客户证件有效期开始时间：
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
											 <span class="no-empty">*</span>客户证件有效期结束时间：
										</td>
										<td>
											<s:textfield name="customerDTO.validity"
												id="corpCredEndValidity" size="20" onfocus="dateClick(this)" onchange="dateCheck()"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>customerDTO.corpCredEndValidity</s:param>
											</s:fielderror>
											<div id="corpCredEndValidity_msg"></div>
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
											<span class="no-empty">*</span>客户性别：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.gender"
												dictValue="${customerDTO.gender}" dictType="10002" props="id=\"customerDTO.gender\"" 
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.gender</s:param>
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
											学历：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.education"
												dictValue="${customerDTO.education}" dictType="116" props="id=\"customerDTO.education\"" 
												tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param>customerDTO.education</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											婚姻状况：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.marriage"
												dictValue="${customerDTO.marriage}" dictType="117" props="id=\"customerDTO.marriage\"" 
												tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param>customerDTO.marriage</s:param>
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
											民族：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.nation"
												dictValue="${customerDTO.nation}" dictType="451" props="id=\"type\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.nation</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span class="no-empty">*</span>邮箱：
										</td>
										<td>
											<s:textfield name="customerDTO.email" id="nation" maxlength="32"/>
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
											<span class="no-empty">*</span>客户职业类别：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.awareness"
												dictValue="${customerDTO.awareness}" dictType="145"
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
											<span class="no-empty">*</span>联系电话：
										</td>
										<td>
											<s:textfield name="customerDTO.customerTelephone"
												onkeypress="return check(event);" maxlength="11" />
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
							<%-- <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户行业：
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
							</td> --%>
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
											上年度客户购卡总金额：
										</td>
										<td>
											<s:textfield name="customerDTO.lastYear" id="lastYear" value="0" disabled="true"></s:textfield>
										</td>
									</tr>
								</table>
							</td>
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
											<div id="address_msg"></div>
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
											<span class="no-empty">*</span>国籍：
										</td>
										<td>
											<edl:entityDictList displayName="customerDTO.nationality"
												dictValue="${customerDTO.nationality}" dictType="450" props="id=\"type\""
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.nationality</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
										城市：
										</td>
										<td>
											<s:textfield name="customerDTO.city" maxlength="10" id="city" ></s:textfield>
											<div id="city_msg"></div>
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
						</table>
						</div>
						</div>
　　　　　　　　　　　　<%-- 　<div id="customerBase2"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="riskControlTitle"
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
				<div id="riskControlTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="100%" style="table-layout: fixed;">
												
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											客户证件照片信息是否已存档：
										</td>
										<td>
											<s:select list="#{'2':'','0':'否','1':'是'}"
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
											客户负责人是否卷入法律诉讼：
										</td>
										<td>
											<s:select list="#{'2':'','0':'否','1':'是'}"
												name="customerDTO.corpActionAtLaw"></s:select>
											<s:fielderror>
												<s:param>customerDTO.corpActionAtLaw</s:param>
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
											客户负责人的信用状况：
										</td>
										<td>
										<edl:entityDictList displayName="customerDTO.creditStatus"
												dictValue="${customerDTO.creditStatus}" dictType="143" props="id=\"customerDTO.creditStatus\" " 
												tagType="2" defaultOption="false" />
											<s:fielderror>
												<s:param>customerDTO.creditStatus</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
							
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											是否被执法部门处罚记录：
										</td>
										<td>
											<s:select list="#{'2':'','0':'否','1':'是'}"
												name="customerDTO.punishRecordFlag"
												onchange="punishRecord(this);" id="punishRecordFlag"></s:select>
											<s:fielderror>
												<s:param>customerDTO.punishRecordFlag</s:param>
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
											<s:textarea id="customerComment"  maxlength="200" onpropertychange="if(value.length>200) value=value.substr(0,200)" name="customerDTO.customerComment" cols="15"
												rows="3"></s:textarea>
											<s:fielderror>
												<s:param>customerDTO.customerComment</s:param>
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