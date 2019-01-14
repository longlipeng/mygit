<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
	<head>
		<title>修改商户</title>
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

    function chooseMerchantGroup() {
        var merchantGroupDTO = window.showModalDialog('${ctx}/merchantGroupManagement/chooseMchntGrp', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
        if (merchantGroupDTO != null) {
            maskDocAllWithMessage("Wait...");
            Ext.fly('merchantGroupId').dom.value = merchantGroupDTO['merchantGrpId'];
            Ext.fly('merchantGrpName').dom.value = merchantGroupDTO['merchantGrpName'];
            unmaskDocAll();
        }
    }

    function operateDetail(operater,list,url, inqueryUrl) {
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
			window.confirm("确认要删除吗?",  deleteback);
			return ;	
		
		}
        var entityId = document.getElementById('entityId').value;
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		window.location = inqueryUrl+'?entityId=' + entityId;
    }

	function deleteback(){
		document.bankForm.action=document.getElementById("url").value;
		document.bankForm.submit();
	}

	function operdel(){
		var url=document.getElementById("url").value;
		var inqueryUrl=document.getElementById("inqueryUrl").value;
		var entityId = document.getElementById('entityId').value;
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		window.location = inqueryUrl+'?entityId=' + entityId;
		return ;
	}
	
	function checksub(){
    	var idNo=document.getElementById("legalPersonIdno").value
    	   var aa=IdCardValidate(idNo)
    	   if(aa!=true){
    		   return;
    	   }
   	
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
   
    function check(){
		var strdate = document.getElementById("guaranteeValidDate").value;
		var guaranteeValidDate = new Date(strdate.replace(/-/g,"/"));
		var nowDate = new Date();
		var time =   guaranteeValidDate.getTime()-nowDate.getTime();
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
	
    //设置网站登录名和密码Div是否显示
    function setWebDiv() {
      document.getElementById('webDiv').style.display='none';
      if (document.getElementById('enableWebsite').checked) {
        document.getElementById('webDiv').style.display='block';        
      }
    }

    function setPunishDiv(){
    	document.getElementById('punishDiv').style.display='none';
    	if (document.getElementById('isPunish').value==1) {
        	document.getElementById('punishDiv').style.display='block';        
      	}
    }

 	 function setManageT() {
		var manageT = "${merchantDTO.merchantManageTime}";
		var startT = manageT.substr(0,manageT.indexOf('-',0));
		var endT = manageT.substr(manageT.indexOf('-',0)+1,manageT.length);
		document.getElementById("manageTimeStart").value=startT;
 		document.getElementById("manageTimeEnd").value=endT;
	}; 
 
	function InitPage() {
	check();
	  setWebDiv();
      loadSalesmanList();	  
		//display();
	 setManageT();
	 setPunishDiv();
	 
	 
	}
	
	function checkwebName(){
		var websiteUserName=document.getElementById("merchantDTO.websiteUserName").value;
		if(websiteUserName.search("[^\\s]")!=0){
			document.getElementById("message").innerHTML="登录名前后不能有空格";
			document.getElementById("merchantDTO.websiteUserName").value="";
			return;
		}
		ajaxRemote('merchantManagement/checkWebName.action',{'merchantDTO.websiteUserName':websiteUserName},back,'html');	
	
	}
	function back(data){
		if(data.indexOf("在")!=-1){
			document.getElementById("merchantDTO.websiteUserName").value="";
		}
		document.getElementById("message").innerHTML=data;
	}
	var state;
	function states(){
		
		state=document.getElementById("state");
		var n=0;
		for(var i=0;i<state.length;i++){
			if(state.options[i].selected==true){
				n=i;		
			}
		}
		
		if(state.options[n].value==0){
			window.confirm("确认要设置为无效吗?",operation);
			state.options[0].selected=true;
		}
	}
	 function operation(){
		state.options[1].selected=true;
	}
	
	function sub(){
		state=document.getElementById("state");
		var n=0;
		for(var i=0;i<state.length;i++){
			if(state.options[i].selected==true){
				n=i;		
			}
		}
		//if(state.options[n].value==0){
			//window.confirm("相关店面也会注销,确认要设置为无效吗?",operation1);
		//}else{
		//}
		var startT = document.getElementById("manageTimeStart").value;
 		var endT = document.getElementById("manageTimeEnd").value;
 		var manageT = startT+"-"+endT;
 		document.getElementById("merchantManageTime").value=manageT;
		newForm.submit();
 
	}
	function operation1(){
		 newForm.submit();
	}
	
	//function display(){
    //	var selectValue=document.getElementById("channelId").value;
	//			if(selectValue==''){
	//				document.getElementById("time").style.visibility="hidden";
					
	//			}else{
	//				document.getElementById("time").style.visibility="";
	//			}
    //}

	function addEntity(actionUrl) {
		var returnValue = window.showModalDialog(actionUrl, '_ blank',
				'center:yes;dialogHeight:600px;dialogWidth:600px;resizable:no');
		if (returnValue == 'success') {
			reload();
		}
	}
	
	var url = null;
	var formName = null;
	function delEntity(actionUrl, entityForm, chooseId) {
		url = actionUrl;
		formName = entityForm;
		var n = 0;
		var checkbox = document.getElementsByName(chooseId);
		for (i = 0; i < checkbox.length; i++) {
			if (checkbox[i].checked == true) {
				n++;
			}
		}
		if (n == 0) {
			errorDisplay('请选择要删除的对象');
		}
		if (n > 0) {
			confirm("确定删除吗？", operDel);
		}
	}
	

	function operDel() {
		document.forms[formName].action = url;
		document.forms[formName].submit();

		url = null;
		formName = null;
	}
 

    
 
</script>
	</head>
	<body onload="InitPage()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>修改商户</span>
		</div>
		<s:form id="newForm" name="newForm"
		  action="merchantManagement/update">
		  <s:hidden name="sensitiveData"/>
		  <s:hidden name="disabled"/>
		  <s:hidden name="url" id="url"/>
		  <s:hidden name="inqueryUrl" id="inqueryUrl"/>
		  <s:hidden name="merchantDTO.fatherEntityId"/>
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
									<s:hidden name="merchantDTO.entityId" id="entityId"/>
									<%--
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														商户编号：
													</td>
													<td>
														<s:textfield name="merchantDTO.entityId" id="entityId" readonly="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										 --%>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
										               <s:textfield name="merchantDTO.merchantCode" id="merchantDTO.merchantCode"  readonly="true"/>
                                                       
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
														<s:textfield name="merchantDTO.merchantName" id="merchantDTO.merchantName"/>
														<s:fielderror>
															<s:param>
																merchantDTO.merchantName
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
														商户实际店名：
													</td>
													<td>
														<s:textfield name="merchantDTO.merchantRealityName" id="merchantDTO.merchantRealityName"/>
														<s:fielderror>
															<s:param>
																merchantDTO.merchantRealityName
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
														商户英文名称：
													</td>
													<td>
														<s:textfield name="merchantDTO.merchantEnglishName" id="merchantDTO.merchantEnglishName"/>
														<s:fielderror>
															<s:param>
																merchantDTO.merchantEnglishName
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
														<span class="no-empty">*</span>商户级别：
													</td>
													<td>
													  <s:if test="sensitiveData">
										              <edl:entityDictList displayName="merchantDTO.merchantType" dictValue="${merchantDTO.merchantType}" dictType="181" tagType="1" defaultOption="false" />
										              </s:if>
										              <s:else>
										              	  <edl:entityDictList displayName="merchantDTO.merchantType" dictValue="${merchantDTO.merchantType}" dictType="181" tagType="2" defaultOption="false" />
										             </s:else>
                                                         <s:fielderror>
															<s:param>
																merchantDTO.merchantType
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
														<span class="no-empty"></span>发票名称：
													</td>
													<td>
													   <s:textfield name="merchantDTO.invoiceName" id="merchantDTO.invoiceName"/>
                                                       <s:fielderror>
															<s:param>
																merchantDTO.invoiceName
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
														<span class="no-empty">*</span>商户属性：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantAttribute" dictValue="${merchantDTO.merchantAttribute}" dictType="184" tagType="2" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																merchantDTO.merchantAttribute
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
														老系统商户号：
													</td>
													<td>
														<s:textfield name="merchantDTO.legacyMerchantId" id="merchantDTO.legacyMerchantId"/>
														<s:fielderror>
															<s:param>
																merchantDTO.legacyMerchantId
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
														<s:textfield name="merchantDTO.externalId" id="merchantDTO.externalId"/>
														<s:fielderror>
															<s:param>
																merchantDTO.externalId
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
														<span class="no-empty">*</span>市场代表：
													</td>
													<td>
										               <s:textfield name="merchantDTO.salesmanId" id="salesmanId"/>
                                                       <s:fielderror>
															<s:param>
																merchantDTO.salesmanId
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
										                 <s:textfield name="merchantDTO.joinDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
										                 </s:textfield>
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
													<s:select headerKey="" headerValue="" list="#{0:'保函',1:'押金'}" name="merchantDTO.guaranteeType"></s:select>
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
													<s:textfield name="merchantDTO.guaranteeValidDate" id="guaranteeValidDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
								                   </s:textfield>
														<s:fielderror>
															<s:param>
																merchantDTO.guaranteeValidDate
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
														POS机申请台数：
													</td>
													<td>
										               <s:textfield name="merchantDTO.postApplyNum" id="postApplyNum"/>
                                                       <s:fielderror>
															<s:param>
																merchantDTO.postApplyNum
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
														 <span class="no-empty">*</span>是否布放移动POS：                                                     
													</td>
													<td>
														<s:select 
                                                         list="#{'0':'否','1':'是'}"  
                                                         name="merchantDTO.isMovePost" 
                                                         emptyOption="false" />	
                                                       <s:fielderror>
															<s:param>
																merchantDTO.isMovePost
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
														<span class="no-empty">*</span>商户行业：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantIdustry" dictValue="${merchantDTO.merchantIdustry}" dictType="185" tagType="2" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																merchantDTO.merchantIdustry
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
                                                         	  商户状态：
                                                       </td>
                                                       <td>
                                                            <s:select id="state"  name="merchantDTO.merchantState" list="#{'1':'已审核','0':'无效','2':'未审核','3':'审核未通过','4':'审核中'}" onchange="states()" ></s:select>
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
                                                      <input type="checkbox" <s:property value="merchantDTO.enableWebsite == 1 ? 'checked=checked' : ''"/> name="merchantDTO.enableWebsite" id="enableWebsite" value="1" onclick="setWebDiv()"/>
														<s:fielderror>
															<s:param>
																merchantDTO.enableWebsite
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
														<span class="no-empty">*</span>商户交易类型：
													</td>
													<td>
													   <edl:entityDictList displayName="merchantDTO.merchantTransactionType" dictValue="${merchantDTO.merchantTransactionType}" dictType="186" tagType="2" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																merchantDTO.merchantTransactionType
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
													是否支持&nbsp;&nbsp;<br/>互联网交易：
													</td>
													<td>
													  <input type="checkbox"
															<s:property value="merchantDTO.ePayIn ==1 ? 'checked=checked' : ''"/>
															name="merchantDTO.ePayIn" id="ePayIn" value="1"
															onclick="setWebDiv()" />
														<s:fielderror>
															<s:param>
																merchantDTO.ePayIn
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
														商户后台返回URL：
													</td>
													<td>
										               <s:textfield name="merchantDTO.mchntUrl" id="merchantDTO.mchntUrl"/>
                                                       <s:fielderror>
															<s:param>
																merchantDTO.mchntUrl
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
														证书DN信息：
													</td>
													<td>
														<s:textfield name="merchantDTO.dnInfo" id="merchantDTO.dnInfo"/>
														<s:fielderror>
															<s:param>
																merchantDTO.dnInfo
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
														商户经营时间：
													</td>
													<td>
                                                    	<input type="text" id="manageTimeStart" class="Wdate"
															onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'manageTimeEnd\')}',dateFmt:'HH:mm'})"/>
														至
														<input type="text" id="manageTimeEnd" class="Wdate"
															onFocus="WdatePicker({minDate:'#F{$dp.$D(\'manageTimeStart\')}',dateFmt:'HH:mm'})"/>
                                                     <s:hidden name="merchantDTO.merchantManageTime" id="merchantManageTime" />
                                                    	<s:fielderror>
                                                    		<s:param>
                                                    			merchantDTO.merchantManageTime
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
														交易查询次数：
													</td>
													<td>
										               <s:textfield name="merchantDTO.txnQryTimes" id="merchantDTO.txnQryTimes"/>
                                                       <s:fielderror>
															<s:param>
																merchantDTO.txnQryTimes
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
														证书号：
													</td>
													<td>
                                                    	<s:textarea name="merchantDTO.certificateNo" cols="20" rows="5"></s:textarea>
                                                    	<s:fielderror>
                                                    		<s:param>
                                                    			merchantDTO.certificateNo
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
														注释信息：
													</td>
													<td>
                                                    	<s:textarea name="merchantDTO.annotation" cols="20" rows="5"></s:textarea>
                                                    	<s:fielderror>
                                                    		<s:param>
                                                    			merchantDTO.annotation
                                                    		</s:param>
                                                    	</s:fielderror>
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
											               	<s:textfield name="merchantDTO.websiteUserName" id="merchantDTO.websiteUserName" onblur="checkwebName();"/>
	                                                       <s:fielderror>
																<s:param>
																	merchantDTO.websiteUserName
																</s:param>
															</s:fielderror> 
															<div id="message" style="color:red"/>
														</td>
													</tr>
												</table>
											</td>
											
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															网站登录密码：
														</td>
														<td>
														
														<c:if test="${merchantDTO.websitePassword == null}">
															<c:set var="wspwd" value="b111111"></c:set>
														</c:if>
														<c:if test="${merchantDTO.websitePassword != null }">
															<c:if test="${fn:length(merchantDTO.websitePassword) == 32}">
																<c:set var="wspwd" value="******"></c:set>
															</c:if>
															<c:if test="${fn:length(merchantDTO.websitePassword) != 32}">
																<c:set var="wspwd" value="${merchantDTO.websitePassword}"></c:set>
															</c:if>
														</c:if>
														<input type ="text" name="merchantDTO.websitePassword" id="merchantDTO.websitePassword" value="${wspwd }"/>
	                                                       <s:fielderror>
																<s:param>
																	merchantDTO.websitePassword
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
													<s:textfield name="merchantDTO.customerManagerName"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.customerManagerName
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
													客户经理联系电话1：
												</td>
												<td>
													<s:textfield name="merchantDTO.customerManagerTelephone1"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.customerManagerTelephone1
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
												<td style="width: 170px; text-align: right;">
													客户经理联系电话2：
												</td>
												<td>
													<s:textfield name="merchantDTO.customerManagerTelephone2"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.customerManagerTelephone2
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
													<span class="no-empty"></span>商户联系人：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantLinkman"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantLinkman
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
												<td style="width: 170px; text-align: right;">
													<span class="no-empty"></span>商户联系电话1：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantTelephone"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantTelephone
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
													<span class="no-empty"></span>商户联系电话2：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantTelephone2"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantTelephone2
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
												<td style="width: 170px; text-align: right;">
													<span class="no-empty"></span>商户传真：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantFax"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantFax
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
													商户网址：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantWebsite"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantWebsite
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
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户地址：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantAddress"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantAddress
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
													英文地址 ：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantEnglishAddress"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantEnglishAddress
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
												<td style="width: 170px; text-align: right;">
													<span class="no-empty"></span>商户邮编：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantPostcode"></s:textfield>
													<s:fielderror>
														<s:param>
																 merchantDTO.merchantPostcode
														</s:param>
													</s:fielderror>
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
														tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>
																merchantDTO.settAgencyId
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
													<span class="no-empty">*</span>商户结算方式：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.merchantSettType"
														dictValue="${merchantDTO.merchantSettType}" dictType="187"
														tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>
																merchantDTO.merchantSettType
														</s:param>
													</s:fielderror>
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
													<s:textfield name="merchantDTO.merchantBank"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantBank
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
													<span class="no-empty">*</span>商户账户：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantAccount"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantAccount
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
												<td style="width: 170px; text-align: right;">
													<span class="no-empty">*</span>商户结算账户：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantSettAccount"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantSettAccount
														</s:param>
													</s:fielderror>
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
													<s:textfield name="merchantDTO.orgCode"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.orgCode
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
													商户法人：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantLegalPerson"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantLegalPerson
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
												<td style="width: 170px; text-align: right;">
													营业执照号码：
												</td>
												<td>
													<s:textfield name="merchantDTO.businessLicenseNumber"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.businessLicenseNumber
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
												<td style="width: 170px; text-align: right;">
													营业执照有效期From：
												</td>
												<td>
													<input name="merchantDTO.businessLicenseFrom"
														id="businessLicenseFrom" class="Wdate"
														cssStyle="cursor:pointer"
														onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'businessLicenseTo\')}'})"
														value="${merchantDTO.businessLicenseFrom}"/>
													<s:fielderror>
														<s:param>
																merchantDTO.businessLicenseFrom
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
													To：
												</td>
												<td>
													<input name="merchantDTO.businessLicenseTo"
														id="businessLicenseTo" class="Wdate"
														cssStyle="cursor:pointer"
														onFocus="WdatePicker({minDate:'#F{$dp.$D(\'businessLicenseFrom\')}'})"
														value="${merchantDTO.businessLicenseTo}"/>
													<s:fielderror>
														<s:param>
																merchantDTO.businessLicenseTo
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
												<td style="width: 170px; text-align: right;">
													法人身份证号：
												</td>
												<td>
													<s:textfield onblur="checksub()" id="legalPersonIdno" name="merchantDTO.legalPersonIdno"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.legalPersonIdno
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
													商户开业时间：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantOpeningTime"
														onclick="dateClick(this)" cssClass="Wdate"
														cssStyle="cursor:pointer"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantOpeningTime
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
												<td style="width: 170px; text-align: right;">
													商户分支机构数量：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantBranchNum"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantBranchNum
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
													商户注册资本：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantRegisteredCapital"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantRegisteredCapital
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
												<td style="width: 170px; text-align: right;">
													商户员工数：
												</td>
												<td>
													<s:textfield name="merchantDTO.merchantEmployeesNum"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.merchantEmployeesNum
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
													营业用地形式：
												</td>
												<td>
													<edl:entityDictList displayName="merchantDTO.landType"
														dictValue="${merchantDTO.landType}" dictType="188"
														tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>
																merchantDTO.landType
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
												<td style="width: 170px; text-align: right;">
													商户经营地段：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.merchantSction"
														dictValue="${merchantDTO.merchantSction}" dictType="189"
														tagType="2" defaultOption="false" />
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
														dictType="190" tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>
																merchantDTO.merchantPopularity
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
												<td style="width: 170px; text-align: right;">
													上年度POS消费总金额：
												</td>
												<td>
													<s:textfield name="merchantDTO.previousYearPos"></s:textfield>
													<s:fielderror>
														<s:param>
																merchantDTO.previousYearPos
															</s:param>
													</s:fielderror>
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
															name="merchantDTO.isAllopatryAcquire" emptyOption="false" />
														<s:fielderror>
															<s:param>
																merchantDTO.isAllopatryAcquire
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
														是否有内外卡收单经验：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isAcquireExp" emptyOption="false" />
														<s:fielderror>
															<s:param>
																merchantDTO.isAcquireExp
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
													<td style="width: 170px; text-align: right;">
														申请资料是否含有照片：
													</td>
													<td>
														<s:select list="#{2:'',1:'是',0:'否'}"
															name="merchantDTO.isApplyMaterialPic" emptyOption="false" />
														<s:fielderror>
															<s:param>
																merchantDTO.isApplyMaterialPic
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
													征信系统是否已征信：
												</td>
												<td>
													<s:select name="merchantDTO.isCreditInvestigation"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isCreditInvestigation
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
												<td style="width: 170px; text-align: right;">
													法人身份信息是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectLegalPerson"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isInspectLegalPerson
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
													不良出票人系统是否已核查：
												</td>
												<td>
													<s:select name="merchantDTO.isInspectBadnessDrawer"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isInspectBadnessDrawer
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
												<td style="width: 170px; text-align: right;">
													商户照片信息是否已存档：
												</td>
												<td>
													<s:select name="merchantDTO.isPhotoOnFile"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isPhotoOnFile
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
													商户是否为其他收单机构拒绝：
												</td>
												<td>
													<s:select name="merchantDTO.isRejectedAcquirer"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																merchantDTO.isRejectedAcquirer
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
												<td style="width: 170px; text-align: right;">
													商户是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isMerchantLawsuit"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isMerchantLawsuit
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
													商户负责人（法人代表或高管）是否卷入法律诉讼：
												</td>
												<td>
													<s:select name="merchantDTO.isPrincipalLawsuit"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isPrincipalLawsuit
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
												<td style="width: 170px; text-align: right;">
													商户被执法部门处罚记录：
												</td>
												<td>
													<s:select name="merchantDTO.isPunish" id="isPunish"
														list="#{2:'',1:'是',0:'否'}" onclick="setPunishDiv();"></s:select>
													<s:fielderror>
														<s:param>
																 merchantDTO.isPunish
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
													商户负责人（法人）的信用状况：
												</td>
												<td>
													<edl:entityDictList
														displayName="merchantDTO.principalCreditStatus"
														dictValue="${merchantDTO.principalCreditStatus}"
														dictType="191" tagType="2" defaultOption="false" />
													<s:fielderror>
														<s:param>
																 merchantDTO.principalCreditStatus
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
												<td style="width: 170px; text-align: right;">
													五证齐全。营业执照、税务登记证、组织机构代码证、法人身份证、开户许可证齐全且未过期：
												</td>
												<td>
													<s:select name="merchantDTO.isFiveCertificateAll"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																merchantDTO.isFiveCertificateAll
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
													商户信息调查表填写完整。是否分支机构联系人有签名及盖公章：
												</td>
												<td>
													<s:select name="merchantDTO.isSignHave"
														list="#{2:'',1:'是',0:'否'}"></s:select>
													<s:fielderror>
														<s:param>
																merchantDTO.isSignHave
															</s:param>
													</s:fielderror>
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
															rows="5"></s:textarea>
														<s:fielderror>
															<s:param>
																 merchantDTO.punishContent
														</s:param>
														</s:fielderror>
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
														<s:radio name="merchantDTO.commissionFee" id="merchantDTO.commissionFee" list="#{'1':'是','0':'否'}" disabled="sensitiveData"></s:radio> 
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
													    <s:radio name="merchantDTO.reimburseWithoutCheck" id="merchantDTO.reimburseWithoutCheck" list="#{'1':'是','0':'否'}" disabled="sensitiveData"></s:radio> 
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
													    <s:property value="merchantDTO.purchasePause ==1?'是':'否'"/>
													    <s:hidden name="merchantDTO.purchasePause"/>
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
													    <s:hidden name="merchantDTO.reimbursePause"/>
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
                                                     <edl:entityDictList displayName="merchantDTO.reimbursementType" dictValue="${merchantDTO.reimbursementType}" dictType="106" tagType="1" defaultOption="false"   />
                                                      </s:if>
                                                      <s:else>
                                                      	   <edl:entityDictList displayName="merchantDTO.reimbursementType" dictValue="${merchantDTO.reimbursementType}" dictType="106" tagType="2" defaultOption="false"   />
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

		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='merchantManagement/inquiry';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		
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
						<ec:table items="bankList" var="map" width="100%"
							action="${ctx}/bankManagement"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							autoIncludeParameters="false" form ="bankForm"
							showPagination="false" >
							<ec:row>
								<ec:column property="null" alias="chooseBankId" title="选择"
									width="10%"  headerCell="selectAll"
									viewsAllowed="html">
									<input type="checkbox" name="chooseBankId"
										value="${map.bankId}" />
								</ec:column>								
								<ec:column property="bankName" title="银行名称" width="30%">
									<s:property value="#attr['map'].bankName" />
									<s:property value="#attr['map'].accountFlag == 1 ? ' (默认)' : ''" />
								</ec:column><ec:column property="bankAccount" title="银行账户" width="30%"/>
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
													onclick="operateDetail('add','chooseBankId','${ctx}/merchantManagement/addBank.action?entityId=${merchantDTO.entityId}'+'&'+'actionName=merchantManagement/insertBanik', '${ctx}/merchantManagement/edit.action')"
													value="添加" />
											</td> 
											<td>
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
													onclick="operateDetail('edit','chooseBankId','${ctx}/merchantManagement/editBank.action?'+getParam('chooseBankId')+'&'+'merchantDTO.entityId=${merchantDTO.entityId}'+'&'+'actionName=merchantManagement/editBanik', '${ctx}/merchantManagement/edit.action')"
													value="编辑" />
											</td> 
											<td> 
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
													onclick="operateDetail('delete','chooseBankId','${ctx}/merchantManagement/deleteBank.action?'+getParam('chooseBankId')+'&'+'merchantDTO.entityId=${merchantDTO.entityId}', '${ctx}/merchantManagement/edit.action')"
													value="删除" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					</s:form>
		</td>
		</tr>
		</table>
		
		<div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>

<%--		<div id="ContainBox">--%>
<%--			<table width="98%" border="0" cellpadding="0" cellspacing="1"--%>
<%--				bgcolor="B5B8BF">--%>
<%--				<tr>--%>
<%--					<td width="100%" height="10" align="left" valign="top"--%>
<%--						bgcolor="#FFFFFF">--%>
<%--						<table width="100%" height="20" border="0" cellpadding="0"--%>
<%--							cellspacing="0">--%>
<%--							<tr>--%>
<%--								<td class="TableTitleFront" onclick="displayTable('contactTable');"--%>
<%--									style="cursor: pointer;">--%>
<%--									<span class="TableTop">联系人信息</span>--%>
<%--								</td>--%>
<%--								<td class="TableTitleEnd">--%>
<%--									&nbsp;--%>
<%--								</td>--%>
<%--							</tr>--%>
<%--						</table>--%>
<%--						<div id="contactTable">--%>
<%--					    <s:form id="contactForm" name="contactForm" action="" method="post">--%>
<%--                          <ec:table items="merchantDTO.contactList" var="contactDTO" width="100%"--%>
<%--                              action=""--%>
<%--                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"--%>
<%--                              autoIncludeParameters="false"--%>
<%--                              tableId="contact"--%>
<%--                              showPagination="false"--%>
<%--                              >--%>
<%--              <ec:row>--%>
<%--                <ec:column property="null" alias="contactIdList" title="选择" width="10%"  headerCell="selectAll" viewsAllowed="html">--%>
<%--                    <input type="checkbox" name="contactIdList" value="${contactDTO.contactId}" />--%>
<%--                </ec:column>--%>
<%--                <ec:column property="contactName" title="联系人姓名" width="20%" />--%>
<%--                <ec:column property="contactFunction" title="联系人职位" width="40%" />--%>
<%--                <ec:column property="contactMobilePhone" title="联系人电话" width="40%" />                --%>
<%--              </ec:row>--%>
<%--            </ec:table>--%>
<%--            <table border="0" cellpadding="0" cellspacing="0" width="100%">--%>
<%--              <tr>--%>
<%--                <td align="right">--%>
<%--                    <table border="0" cellpadding="0" cellspacing="0">--%>
<%--                        <tr>--%>
<%--							<td>--%>
<%--								<input type="button" class="btn"--%>
<%--									style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"--%>
<%--									onclick="operateDetail('add','contactIdList','merchantManagement/addContact?merchantDTO.entityId=${merchantDTO.entityId}'+'&'+'actionName=merchantManagement/insertContact', '${ctx}/merchantManagement/edit.action')"--%>
<%--									value="添加" />--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<input type="button" class="btn"--%>
<%--									style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"--%>
<%--									onclick="operateDetail('edit','contactIdList','merchantManagement/editContact?'+getParam('contactIdList')+'&'+'merchantDTO.entityId=${merchantDTO.entityId}'+'&'+'actionName=merchantManagement/updateContact', '${ctx}/merchantManagement/edit.action')"--%>
<%--									value="编辑" />--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<input type="button" class="btn"--%>
<%--									style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"--%>
<%--									onclick="operateDetail('delete','contactIdList','merchantManagement/deleteContact?'+getParam('contactIdList')+'&'+'merchantDTO.entityId=${merchantDTO.entityId}', '${ctx}/merchantManagement/edit.action')"--%>
<%--									value="删除" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--                    </table>--%>
<%--                  </td>--%>
<%--               </tr>--%>
<%--          </table>--%>
<%--      </s:form>--%>
<%--      </div>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--			</table>--%>
<%--		</div>--%>
        <div id="blank" style="height:20px">
          &nbsp;&nbsp;
        </div>
 
	</body>
</html>