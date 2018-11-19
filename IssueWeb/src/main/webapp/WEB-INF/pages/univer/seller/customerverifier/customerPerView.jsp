<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>审核个人客户信息</title>
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
		customerForm.action='${ctx}/customerVerifier/passPerson.action';
		document.getElementById("personFlag").value=flag;
		document.getElementById("operateType").value=flag;
		customerForm.submit();
	}
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>审核个人客户信息</span>
    </div>
    <s:form id="customerForm" name="customerForm" action="" method="post">
    	<s:token></s:token>
        <s:hidden name="customerDTO.entityId"></s:hidden>
    	<s:hidden name="customerDTO.cusState" id="personFlag"></s:hidden>
    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
    	<s:hidden name="operateType" id="operateType"></s:hidden>
        <div id="customerBaseTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<table width="100%" style="table-layout: fixed;">
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"><span
										class="no-empty">*</span>客户名称：</td>
									<td><s:textfield name="customerDTO.customerName"
											disabled="true" /></td>
								</tr>
							</table>
						</td>

					<%-- 	<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">客户代码：</td>
									<td><s:textfield name="customerDTO.customerCode"
											maxlength="32" disabled="true" /></td>
								</tr>
							</table>
						</td> --%>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"> <span class="no-empty">*</span>客户证件类型：</td>
									<td><s:hidden id="customerDTO.corpCredType"
											name="customerDTO.corpCredType"></s:hidden> <edl:entityDictList
											displayName="customerDTO.corpCredType"
											dictValue="${customerDTO.corpCredType}" dictType="140"
											tagType="2" defaultOption="false" props="disabled=\"true\"" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"> <span class="no-empty">*</span>客户证件号：</td>
									<td><s:textfield name="customerDTO.corpCredId"
											id="corpCredId" disabled="true" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"> <span class="no-empty">*</span>客户证件有效期开始时间：</td>
									<td><s:textfield name="customerDTO.corpCredStaValidity"
											id="corpCredStaValidity" size="20" onfocus="dateClick(this)"
											cssClass="Wdate" disabled="true">
										</s:textfield></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"> <span class="no-empty">*</span>客户证件有效期结束时间：</td>
									<td><s:textfield name="customerDTO.validity"
											id="corpCredEndValidity" size="20" onfocus="dateClick(this)"
											cssClass="Wdate" disabled="true">
										</s:textfield></td>
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
												dictValue="${customerDTO.gender}" dictType="10002"  props="disabled=\"true\""
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
												dictValue="${customerDTO.education}" dictType="116"  props="disabled=\"true\"" 
												tagType="2" defaultOption="true"/>
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
												dictValue="${customerDTO.marriage}" dictType="117" props="disabled=\"true\"" 
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
												dictValue="${customerDTO.nation}" dictType="451" props="disabled=\"true\"" 
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
											<s:textfield name="customerDTO.email" id="nation" disabled="true" />
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
									<td style="width: 100px; text-align: right;"><span class="no-empty">*</span>客户职业类别：</td>
									<td><edl:entityDictList
											displayName="customerDTO.awareness"
											dictValue="${customerDTO.awareness}" dictType="145"
											tagType="2" defaultOption="true" props="disabled=\"true\"" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;"><span class="no-empty">*</span>联系电话：</td>
									<td><s:textfield name="customerDTO.customerTelephone"
											disabled="true" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">渠道信息：</td>
									<td><edl:entityDictList displayName="customerDTO.channel"
											dictValue="${customerDTO.channel}" dictType="144" tagType="2"
											defaultOption="false" props="disabled=\"true\"" /></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">销售区域：</td>
									<td><edl:entityDictList
											displayName="customerDTO.salesRegionId"
											dictValue="${customerDTO.salesRegionId}" dictType="407"
											tagType="2" defaultOption="false" props="disabled=\"true\"" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">销售人员：</td>
									<td><s:select list="salesmanList"
											name="customerDTO.salesmanId" listKey="userId"
											listValue="userName" headerKey="" headerValue="--请选择--"
											disabled="true"></s:select></td>
								</tr>
							</table>
						</td>
						<%-- <td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">客户行业：</td>
									<td><edl:entityDictList
											displayName="customerDTO.activitySector"
											dictValue="${customerDTO.activitySector}" dictType="400"
											tagType="2" defaultOption="false" props="disabled=\"true\"" />
									</td>
								</tr>
							</table>
						</td> --%>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">客户状态：</td>
									<td><s:select list="#{'2':'有效', '0':'无效'}"
											name="customerDTO.cusState" disabled="true"></s:select></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">是否发送DM：</td>
									<td><s:select list="#{'1':'是', '0':'否'}"
											name="customerDTO.hasDm" disabled="true"></s:select></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">缺省支付节点：</td>
									<td><dl:dictList displayName="customerDTO.paymentTerm"
											dictValue="${customerDTO.paymentTerm}" dictType="207"
											tagType="2" defaultOption="false" props="disabled=\"true\"" />
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
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 100px; text-align: right;">
													上年度客户购卡总金额：</td>
												<td><s:textfield name="customerDTO.lastYear"
														id="lastYear" value="0" disabled="true"></s:textfield></td>
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
														dictValue="${customerDTO.nationality}" dictType="450" props="disabled=\"true\""
														tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>customerDTO.nationality</s:param>
													</s:fielderror>
										</td>
											</tr>
										</table>
									</td>

								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">

								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 100px; text-align: right;">延期天数：</td>
												<td><s:textfield name="customerDTO.paymentDelay"
														id="paymentDelay" disabled="true" /></td>
											</tr>
										</table>
									</td>

								</tr>
								<tr>
									<td>
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 100px; text-align: right;"><span class="no-empty">*</span>客户地址：</td>
												<td><s:textfield name="customerDTO.customerAddress"
														id="customerAddress" disabled="true"></s:textfield></td>
											</tr>
										</table>
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
										<table style="text-align: left; width: 100%">
											<tr>
												<td style="width: 100px; text-align: right;">城市：</td>
												<td><s:textfield name="customerDTO.city" id="city"
														disabled="true"></s:textfield></td>
											</tr>
										</table>
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
					<%-- <tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">
									受理区域：
									</td>
									<td>
										<edl:entityDictList displayName="customerDTO.acceptArea"
											dictValue="${customerDTO.acceptArea}" dictType="192" tagType="2" defaultOption="false" props="disabled=\"true\""/>
									</td>
								</tr>
							</table>
						</td>
						<td></td>
					</tr> --%>
					<!--  -->
					<tr>
						<td>

							<table style="text-align: left; width: 100%">
								<tr>
									<td
										style="width: 90px; text-align: right; vertical-align: top;">证件照（正面）：</td>
									<td id="imgfPath">
										<%--  <img src="${ctx }/images/img/${customerDTO.imgSrc}" alt="暂无照片" style="width:180px;height:240px"/>   --%>
										<img style="height: 180px; width: 300px" alt="暂无图片"
										src="${customerDTO.imgfPath }" />
									</td>
								</tr>

							</table>

						</td>

						<td>
							<table style="text-align: left; width: 100%">

								<tr>
									<td
										style="width: 90px; text-align: right; vertical-align: top;">证件照（背面）：</td>
									<td id="imgbPath">
										<%--  <img src="${ctx }/images/img/${customerDTO.imgSrc}" alt="暂无照片" style="width:180px;height:240px"/>   --%>
										<img style="height: 180px; width: 300px" alt="暂无图片"
										src="${customerDTO.imgbPath }" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
　　　　　　　　　　　<%-- 　　<div id="customerBase2"
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
											客户证件照片信息是否已存档：
										</td>
										<td>
											<s:select list="#{'2':'','0':'否','1':'是'}"
												name="customerDTO.pictureSave" disabled="true"></s:select>
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
												name="customerDTO.corpActionAtLaw"  disabled="true"></s:select>
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
												dictValue="${customerDTO.creditStatus}" dictType="143"
												tagType="1" defaultOption="false" />
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
												onchange="punishRecord(this);" id="punishRecordFlag"  disabled="true"></s:select>
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
											<s:textarea name="customerDTO.customerComment" cols="15"
												rows="3"  disabled="true"></s:textarea>
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
												rows="3" id="customerDTO.punishRecordInfo"  disabled="true"></s:textarea>
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
												id="cardValidityPeriod"  onfocus="dateClick(this)"
												cssClass="Wdate"  disabled="true" >
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
                                        <s:select name="sellerContractDTO.contractState" list="#{'1':'有效', '0':'无效'}"  disabled="true"  />
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
                                    <td><s:textfield name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee ) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"  disabled="true" ></s:textfield>
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
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webPayStat"  disabled="true"  ></s:select>
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
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.smsSvcStat"  disabled="true" ></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否邮件通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.emailSvcStat"  disabled="true" ></s:select>
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
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webSmsSvcStat"  disabled="true" ></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否邮件通知：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webEmailSvcStat"  disabled="true" ></s:select>
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
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.monstmtSvcStat"  disabled="true"  ></s:select>
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
<script type="text/javascript">
    	if(document.getElementById("punishRecordFlag").value=='1'){
    		document.getElementById("punishRecordInfo").style.display='';
    	}
    </script>
</body>
</html>