<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>修改合同信息</title>
        <%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx }/widgets/jquery/jquery-1.3.2.min.js"></script>
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

    function operateDetail(operater,list,url, inqueryUrl) {
        var count = checkedCount();
        if (operater != 'add') {
          var count = checkedCount(list);
          if (operater == 'delete') {
            if (count < 1) {
                alert("请至少选择一项");
                return;
            }
            document.getElementById("inqueryUrl").value=inqueryUrl;
            document.getElementById("url").value=url;
            confirm("你确定要删除吗？",oper);
            return;
          }
          if (operater == 'edit') {
            if (count != 1) {
                alert("请选择一项");
                return;
            }
          }
        }
       
        var merchantContractId = document.getElementById('consumerContractId').value;
        var merchantName = document.getElementById('merchantName').value; 
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');

        document.newForm.action=inqueryUrl;
        document.newForm.submit();
       
    }
    function oper(){
        var url=document.getElementById("url").value;
<%--       var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');--%> 

		//$.ajax({
		//	url:url
		//});
        document.newForm.action=url;
        document.newForm.submit();
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
        
     function addFinish() {
        confirm("你确定要完成合同添加吗？",operation);
    }
    function operation(){
    	document.newForm.action='${ctx}/consumerContract/inquiry.action';
        document.newForm.submit();
    }
    function selectPosAll(){
     var posTrading=document.getElementById("posTrading");
     if(posTrading.checked==true){
     
      document.getElementById("callBack").checked=true;
   document.getElementById("cardActive").checked=true;
   document.getElementById("balanceQuery").checked=true;
   document.getElementById("sale").checked=true;
   document.getElementById("preAuthorization").checked=true;
   document.getElementById("recharge").checked=true;
   }
   else{
     document.getElementById("callBack").checked=false;
   document.getElementById("cardActive").checked=false;
   document.getElementById("balanceQuery").checked=false;
   document.getElementById("sale").checked=false;
   document.getElementById("preAuthorization").checked=false;
   document.getElementById("recharge").checked=false;
   }
   selectRechargeAll();
   selectPreAuthorizationAll();
   selectSaleAll();
    }                                              
     
   function selectRechargeAll(){
   var recharge=document.getElementById("recharge");
     if(recharge.checked==true){
     document.getElementById("realRecharge").checked=true;
     document.getElementById("rechargeConfirm").checked=true;
     }
     else{
     document.getElementById("realRecharge").checked=false;
     document.getElementById("rechargeConfirm").checked=false;
     }
    }
  
   function selectPreAuthorizationAll(){
   var preAuthorization=document.getElementById("preAuthorization");
     if(preAuthorization.checked==true){
    document.getElementById("realPreAuthorization").checked=true;
   document.getElementById("preAuthorizationRvsl").checked=true;
   document.getElementById("preAuthorizationVoid").checked=true;
   document.getElementById("preAuthorization").checked=true;
   document.getElementById("preAuthorizationVoidRvsl").checked=true;
   document.getElementById("preAuthorizationComplete").checked=true;
   document.getElementById("preAuthorizationCompleteRvsl").checked=true;
   document.getElementById("preAuthorizationCompleteVoid").checked=true;
   document.getElementById("preAuthorizationCompleteVoidRvsl").checked=true;
   }
   else{
    document.getElementById("realPreAuthorization").checked=false;
   document.getElementById("preAuthorizationRvsl").checked=false;
   document.getElementById("preAuthorizationVoid").checked=false;
   document.getElementById("preAuthorization").checked=false;
   document.getElementById("preAuthorizationVoidRvsl").checked=false;
   document.getElementById("preAuthorizationComplete").checked=false;
   document.getElementById("preAuthorizationCompleteRvsl").checked=false;
   document.getElementById("preAuthorizationCompleteVoid").checked=false;
   document.getElementById("preAuthorizationCompleteVoidRvsl").checked=false;
    }
   }
   
   function selectSaleAll(){
    var sale=document.getElementById("sale");
     if(sale.checked==true){
   document.getElementById("trueSale").checked=true;
   document.getElementById("saleVoid").checked=true;
   document.getElementById("saleVoidRvsl").checked=true;
   document.getElementById("saleRvsl").checked=true;
     }
     else{
     document.getElementById("trueSale").checked=false;
   document.getElementById("saleVoid").checked=false;
   document.getElementById("saleVoidRvsl").checked=false;
   document.getElementById("saleRvsl").checked=false;
     }
   }
   
   
   function selectGateTradeAll(){
       
    var gatewayTrading=document.getElementById("gatewayTrading");
     if(gatewayTrading.checked==true){
   document.getElementById("gatewayCallback").checked=true;
   document.getElementById("gatewayRecharge").checked=true;
   document.getElementById("gatewayTxnQuery").checked=true;
   document.getElementById("gateSale").checked=true;
   document.getElementById("transfer").checked=true;
   document.getElementById("lineRecharge").checked=true;
   document.getElementById("conume").checked=true;
     }
     else{
     document.getElementById("gatewayCallback").checked=false;
   document.getElementById("gatewayRecharge").checked=false;
   document.getElementById("gatewayTxnQuery").checked=false;
   document.getElementById("gateSale").checked=false;
   document.getElementById("transfer").checked=false;
     document.getElementById("lineRecharge").checked=false;
     document.getElementById("conume").checked=false;
     }
     selecetGateSaleAll();
     selectTransferAll();
     selecConsume();
   }
      
    function selectTransferAll(){
    var transfer=document.getElementById("transfer");
     if(transfer.checked==true){
   document.getElementById("transferIn").checked=true;
   document.getElementById("transferOut").checked=true;
     }
     else{ 
     document.getElementById("transferIn").checked=false;
   document.getElementById("transferOut").checked=false;
     }
    }
     function selecConsume(){
    var transfer=document.getElementById("conume");
     if(transfer.checked==true){
   document.getElementById("singleConsume").checked=true;
   document.getElementById("singleConsumeBlunt").checked=true;
     }
     else{
     document.getElementById("singleConsumeBlunt").checked=false;
   document.getElementById("singleConsume").checked=false;
     }
    }
    
    
    function selecetGateSaleAll(){
    var gateSale=document.getElementById("gateSale");
     if(gateSale.checked==true){
   document.getElementById("ConsumerRegistration").checked=true;
   document.getElementById("saleConfirm").checked=true;
     }
     else{
     document.getElementById("ConsumerRegistration").checked=false;
   document.getElementById("saleConfirm").checked=false;
     }
    }
</script>
    </head>
    <body>
        <%@ include file="/commons/messages.jsp"%>
        <div class="TitleHref">
            <span>修改合同信息</span>
        </div>
        <s:form id="newForm" name="newForm" action="consumerContract/update">
        <div id="ContainBox0">
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
                                    <span class="TableTop">收单机构合同</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <div id="serviceTable">
                                <table width="100%" style="table-layout: fixed;">
                                  <tr>
                                      <td colspan="2">
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <s:text name="xxl.merchant.agreeID"/>
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.consumerContractId"
                                                            id="consumerContractId" readonly="true"></s:textfield>
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                    </tr>
                                    <tr>
                                      <td>
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                                收单机构号：
                                                    </td>
                                                    <td>
                                                        <s:textfield id="consumerId" name="consumerContractDTO.contractBuyer" readonly="true"/>
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractBuyer
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                              收单机构名称：
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.merchantName" id="merchantName" readonly="true" />
                                                        <s:fielderror>
                                                            <s:param>
                                                               consumerContractDTO.merchantName
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
                                                    <td style="width: 150px; text-align: right;">
                                                        <span class="no-empty">*</span>合同有效期开始日:
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.contractStartDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                                          <s:param name="value"><s:date name="consumerContractDTO.contractStartDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield>
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractStartDateDate
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                              合同有效期结束日：
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.contractEndDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                                          <s:param name="value"><s:date name="consumerContractDTO.contractEndDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield> 
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractEndDateDate
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
                                                    <td style="width: 150px; text-align: right;">
                                                     	结算周期规则：
                                                    </td>
                                                    <td>

                                                        <s:textfield id="ruleNo" name="consumerContractDTO.ruleNo" readonly="true"/>

                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.ruleNo
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                              <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														结算周期名称：
													</td>
													<td>
                                                        <s:textfield id="ruleName" name="consumerContractDTO.ruleName" readonly="true" cssClass="readonly"/>
														<s:fielderror>
															<s:param>consumerContractDTO.ruleName</s:param>
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>结算类型：
													</td>
													<td>
													<s:select list="#{'1':'汇总结算'}"
															name="consumerContractDTO.clearTp"
															id="consumerContractDTO.clearTp" 
															 label="规则类型："/>
                                                       <!--  <s:textfield id="ruleNo" name="consumerContractDTO.clearTp" cssClass="watch" onclick="choosePeriodRule()" readonly="true"/>-->
														<s:fielderror>
															<s:param>consumerContractDTO.clearTp</s:param>
														</s:fielderror>  
													</td>
												</tr>
											</table>
										</td>
									</tr>
                                      <s:hidden id="inqueryUrl"></s:hidden>            
                                       <s:hidden id="url"></s:hidden>
                                       <s:hidden id="consumerContractDTO.contractSeller" name="consumerContractDTO.contractSeller"></s:hidden>                    
                                </table>
                      </div>
                    </td>
                </tr>
            </table>
        </div> 
        <div>
             <table border="0" cellpadding="0" cellspacing="0" width="100%">
                 <tr>
                     <td style="text-align:right">
                         <input type="button" class="bt" style="margin: 5px;" onclick="newForm.submit();" value="提交合同"/>
                     </td>
                 </tr>
             </table>
        </div>


        <br/>

  
                <div id="tradePower">
            
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                  
                <tr>
                    <td width="100%" height="10" align="center" valign="top"
                        bgcolor="#FFFFFF">
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('tradePowerTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">交易权限</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <br/>
                        <div id="tradePowerTable">
                         <table width="100%" height="20" border="0" cellpadding="0" 
                            cellspacing="0">
                            <tr>
                                <td>
                                   <input name="posTrading" type="checkbox" id="posTrading" onclick="selectPosAll();"/> <span class="TableTop">终端交易</span>
                                </td>
                                
                            </tr>
                        </table>
                     <br/>  
                             
                   
          
          <br/>              
          <div id="ContainBox2">
            <table width="92%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="#FFFFFF">
                <tr>
                    <td>
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td>
                                    <input name="transIds" type="checkbox" id="balanceQuery" <s:iterator value="consumerContractDTO.transId" var="trandId"><s:if test="#trandId=='S00'">checked</s:if></s:iterator> value="S00"/>余额查询
                                </td>                              
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <br/>              
          <div id="ContainBox2">
            <table width="92%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="#FFFFFF">
                <tr>
                    <td>
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td>
                                    <input name="transIds" type="checkbox" id="callBack" <s:iterator value="consumerContractDTO.transId" var="trandId24"><s:if test="#trandId24=='S30'">checked</s:if></s:iterator> value="S30"/>退货申请
                                </td>                              
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <br/> 
         <div id="ContainBox5">
            <table width="92%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="#FFFFFF">
                <tr>
                    <td>
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td>
                                    <input name="transIds" type="checkbox" id="gatewayCallback" <s:iterator value="consumerContractDTO.transId" var="trandId16"><s:if test="#trandId16=='G30'">checked</s:if></s:iterator> value="G30"/>退货审核
                                </td>
                              
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>    
        <div style="display:none">
 <input name="transIds" type="checkbox" checked value="M33"/> 
 转账转入
 <input name="transIds" type="checkbox" checked value="M34"/> 
 转账转出
 <input name="transIds" type="checkbox" checked value="S55"/> 
贷记调整
 <input name="transIds" type="checkbox" checked value="S66"/> 
借记调整

 </div>      
        <br/>
          <div id="ContainBox">
            <table width="92%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('');"
                                    style="cursor: pointer;">
                                    <input name="sale" type="checkbox" id="sale" onclick="selectSaleAll();"/> <span class="TableTop">消费</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                                <table width="100%" style="table-layout: fixed;">
                                  <tr>
                                      <td >
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <input name="transIds"
                                                            type="checkbox" id="trueSale" <s:iterator value="consumerContractDTO.transId" var="trandId2"><s:if test="#trandId2=='S22'">checked</s:if></s:iterator> value="S22"/>                                                                                       
                                                    </td>
                                                    <td>&nbsp;
                                                    消费
                                                      
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                           <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="saleRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId3"><s:if test="#trandId3=='R22'">checked</s:if></s:iterator> value="R22"/> 
                                                   
                                                    </td>
                                                    <td>
                                                       消费冲正
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        
                                    </tr>
                                   
                                    <tr>
                                    <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                              <input name="transIds"
                                                            type="checkbox" id="saleVoid" <s:iterator value="consumerContractDTO.transId" var="trandId4"><s:if test="#trandId4=='V52'">checked</s:if></s:iterator> value="V52"/>  
                                                    </td>
                                                    <td>
                                                       消费撤销
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="saleVoidRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId5"><s:if test="#trandId5=='R52'">checked</s:if></s:iterator> value="R52"/> 
                                                    </td>
                                                    <td>
                                                       消费撤销冲正
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                       
                                    </tr>
                                </table>
                      
                    </td>
                </tr>
            </table>
        </div>

        <br/>             
          <div id="ContainBox3">
            <table width="92%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                         <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront"
                                    style="cursor: pointer;">
                                    <input name="preAuthorization" type="checkbox" id="preAuthorization" onclick="selectPreAuthorizationAll();"/> <span class="TableTop">预授权</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <div id="">
                                <table width="100%" style="table-layout: fixed;">
                                  <tr>
                                       <td >
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <input name="transIds"
                                                            type="checkbox" value="S10" <s:iterator value="consumerContractDTO.transId" var="trandId6"><s:if test="#trandId6=='S10'">checked</s:if></s:iterator> id="realPreAuthorization"/>                                                                                       
                                                    </td>
                                                    <td>&nbsp;
                                                   预授权
                                                      
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                           <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="preAuthorizationRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId7"><s:if test="#trandId7=='R10'">checked</s:if></s:iterator> value="R10"/> 
                                                    </td>
                                                    <td>
                                                       预授权冲正
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                       <td >
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <input name="transIds"
                                                            type="checkbox" id="preAuthorizationVoid" <s:iterator value="consumerContractDTO.transId" var="trandId8"><s:if test="#trandId8=='V40'">checked</s:if></s:iterator> value="V40"/>                                                                                       
                                                    </td>
                                                    <td>&nbsp;
                                                    预授权撤销
                                                      
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                           <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="preAuthorizationVoidRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId9"><s:if test="#trandId9=='R40'">checked</s:if></s:iterator> value="R40"/> 
                                                    </td>
                                                    <td>
                                                     预授权撤销冲正
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                         <td >
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <input name="transIds"
                                                            type="checkbox" id="preAuthorizationComplete" <s:iterator value="consumerContractDTO.transId" var="trandId10"><s:if test="#trandId10=='S20'">checked</s:if></s:iterator> value="S20"/>                                                                                       
                                                    </td>
                                                    <td>&nbsp;
                                                   预授权完成
                                                      
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                           <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="preAuthorizationCompleteRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId11"><s:if test="#trandId11=='R20'">checked</s:if></s:iterator> value="R20"/> 
                                                    </td>
                                                    <td>
                                                         预授权完成冲正
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>

                                    
                                    <tr>
                                         <td >
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <input name="transIds"
                                                            type="checkbox" id="preAuthorizationCompleteVoid" <s:iterator value="consumerContractDTO.transId" var="trandId12"><s:if test="#trandId12=='V50'">checked</s:if></s:iterator> value="V50"/>                                                                                       
                                                    </td>
                                                    <td>&nbsp;
                                                   预授权完成撤销
                                                      
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                           <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                         <input name="transIds"
                                                            type="checkbox" id="preAuthorizationCompleteVoidRvsl" <s:iterator value="consumerContractDTO.transId" var="trandId13"><s:if test="#trandId13=='R50'">checked</s:if></s:iterator> value="R50"/> 
                                                    </td>
                                                    <td>
                                                     预授权完成撤销冲正 
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
                  
         
        <br/>
                        
                  
     
         <br/>
         
          

           </div>             
          
                    </td>
                </tr>
                
            </table>
          
        </div>

        </s:form>
                 <br/>
          <br/>
                <div id="ServiceContractBox" style="margin-top:10px">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('serviceContractTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">账户明细</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <div id="serviceContractTable">

                          <ec:table items="consumerContractDTO.accTypeContractDTOList" var="accTypeContractDTO" width="100%" 
							
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="serviceContract"
                              showPagination="false"
                              sortable="false">
            	  <ec:row>
                <ec:column property="null" alias="accTypeContractIdList" title="选择" width="5%" sortable="false" headerCell="selectAll" viewsAllowed="html">
                    <input type="checkbox" name="accTypeContractIdList" value="${accTypeContractDTO.acctypeContractId},${accTypeContractDTO.consumerContractId}" />
                </ec:column>

                <ec:column property="acctypeContractId" title="账户合同号" width="15%">
                </ec:column>
                <ec:column property="acctypeId" title="账户号" width="15%" />
                <ec:column property="ruleNo" title="费用计算规则编号" width="15%" />
                <ec:column property="ruleName" title="费用计算规则名称" width="15%">
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
										onclick="operateDetail('add','accTypeContractIdList','${ctx}/consumerContract/addAcctypeContract?accTypeContractDTO.consumerContractId=${consumerContractDTO.consumerContractId}'+'&'+'actionName=consumerContract/insertAcctypeContract'+'&'+'accTypeContractDTO.contractSeller=${consumerContractDTO.contractSeller}'+'&'+'accTypeContractDTO.contractBuyer=${consumerContractDTO.contractBuyer}'+'&contractTypeStr=${consumerContractDTO.contractType}', '${ctx}/consumerContract/edit')"
										value="添加" />
							</td>
                            <td>
								<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="operateDetail('edit','accTypeContractIdList','${ctx}/consumerContract/editAcctypeContract?'+getParam('accTypeContractIdList')+'&'+'actionName=consumerContract/updateAcctypeContract'+'&'+'accTypeContractDTO.contractSeller=${consumerContractDTO.contractSeller}', '${ctx}/consumerContract/edit')"
										value="编辑" />
							</td>
                            <td>
								<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="operateDetail('delete','accTypeContractIdList','${ctx}/consumerContract/deleteAcctypeContract?'+getParam('accTypeContractIdList'),'${ctx}/consumerContract/edit')"
										value="删除" />
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
    </body>
</html>