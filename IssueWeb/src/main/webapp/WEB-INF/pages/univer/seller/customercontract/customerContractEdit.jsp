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
<title>编辑客户合同</title>
<script type="text/javascript">

    function choiceCustomer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
            document.getElementById('contractBuyer').value = customerDTO['entityId'];
            document.getElementById('customerName').value = customerDTO['customerName'];
        }
    }
    
    function choiceSettleRule() {
        var contractBuyer = document.getElementById('contractBuyer').value;
        if (contractBuyer == null || contractBuyer == '') {
        	errorDisplay('请先选择客户！');
            return;
        }
        ruleResult = window.showModalDialog('${ctx}/customerContract/settleRuleChoice.action?nameSpace=${nameSpace}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if(ruleResult != null){
			var ruleArr = ruleResult.split(',');
			document.getElementById('ruleNo').value = ruleArr[0];
			document.getElementById('ruleName').value = ruleArr[1];
        }
    }

	function addProduct(){
		var sellerContractId = document.getElementById("sellerContractDTO.sellContractId").value;
		var returnValue = window.showModalDialog('${ctx}/customerContract/addProductService.action?sellerContractDTO.sellContractId='+sellerContractId+'&nameSpace=${nameSpace}', 
												 '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
		if(returnValue == 'success'){
			reload();
		}
	}

	function updateProduct(productId){
		var returnValue = window.showModalDialog('${ctx}/customerContract/editProduct.action?sellerProductContractDTO.id='+productId+'&nameSpace=${nameSpace}', 
												 '_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		if(returnValue == 'success'){
			reload();
		}
	}

	function updateAccount(accountId){
		var returnValue = window.showModalDialog('${ctx}/customerContract/editService.action?sellerAcctypeContractDTO.id='+accountId+'&nameSpace=${nameSpace}', 
												 '_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		if(returnValue == 'success'){
			reload();
		}
	}

	function reload(){
		document.productContractFrom.action="${ctx}/customerContract/edit.action";
		document.productContractFrom.submit();
	}
	function sub(){
        var fee=document.getElementById("fee").value;
        if(fee==''){
       	 errorDisplay('快递费不能为空！');
			 return;
         }else{
        	 var str=/^\d+(\.\d+)?$/; 
             if(!str.test(fee)) {
            	 alert("快递费输入格式错误，必须在0-99999999.99的数字");
				return;
             }
         }
        maskDocAll();
        document.getElementById("deliveryFee").value=fee*100;
    	document.contractForm.action = '${ctx}/customerContract/update.action';
    	document.contractForm.submit();
    	
    }
    
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>编辑客户合同信息</span>
    </div>
    <s:form id="contractForm" name="contractForm" action="sellerContract/insert.action" method="post">
    	<s:token></s:token>
    	<s:hidden id="sellerContractDTO.sellContractId" name="sellerContractDTO.sellContractId" />
    	<s:hidden name="nameSpace" />
        <div id="base" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="baseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">基本信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="baseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="98%" style="table-layout: fixed;">
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
                                    <td style="width: 140px; text-align: right;"><span style="color: red"></span>结算周期规则：</td>
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
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
										</s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.expiryDate</s:param>
                                        </s:fielderror>
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
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费：</td>
                                    <td><s:textfield id="fee" name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee && sellerContractDTO.deliveryFee != 0) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>
                                        <s:hidden name="sellerContractDTO.deliveryFee" id="deliveryFee"></s:hidden>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.deliveryFee</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
<%--        <div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">--%>
<%--            <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">--%>
<%--                <table width="100%" border="0" cellpadding="0" cellspacing="0">--%>
<%--                    <tr>--%>
<%--                        <td class="TableTitleFront">--%>
<%--                            <span class="TableTop">特殊信息</span>--%>
<%--                        </td>--%>
<%--                        <td class="TableTitleEnd">--%>
<%--                            &nbsp;--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </table>--%>
<%--            </div>--%>
<%--            <div id="specialtyTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">--%>
<%--                <table width="100%" style="table-layout: fixed;">--%>
<%--                    <tr>--%>
<%--                       --%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费：</td>--%>
<%--                                    <td><s:textfield name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee ) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>--%>
<%--                                        <s:hidden name="sellerContractDTO.deliveryFee" id="deliveryFee"></s:hidden>--%>
<%--                                        <s:fielderror>--%>
<%--                                            <s:param>sellerContractDTO.deliveryFee</s:param>--%>
<%--                                        </s:fielderror>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        --%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否支持网上支付：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webPayStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否短信通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.smsSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否邮件通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.emailSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否短信通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webSmsSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否邮件通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webEmailSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td colspan="2">--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否开通月报服务：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.monstmtSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </table>--%>
<%--            </div>--%>
<%--        </div>--%>

        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="98%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="sub()" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/customerContract/inquery.action';" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        
                
        <%--
        <div id="productInformation">
            <s:if test="sellerContractDTO.productId != null">
                <s:include value="productInput.jsp"></s:include>
            </s:if>
        </div>
        --%>
    </s:form>
    
    <!-- 营销合同产品明细 -->
     <%--<table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">明细信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
        </table>
	    --%>
	    
	    <s:if test="productContractDTOs != null">
	    	<s:form id="productContractFrom" name="productContractFrom" action="" method="post">
		    	<s:hidden id="sellerContractDTO.sellContractId" name="sellerContractDTO.sellContractId" />
		    	
		    	<s:iterator value="productContractDTOs" id="map" var="map">
		    		<%--<input type="hidden" id="sellerProductContractDTO.id" name="sellerProductContractDTO.id" value="${map.id}" />
		    		
		    		--%><!-- 服务明细 -->
<%--			        <s:include value="customerProductView.jsp" />--%>
			        <%@include file="customerProductView.jsp" %>
			        
		    	</s:iterator>
	    	
	    	</s:form>
	    </s:if>
	    
    	<div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="98%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="addProduct();" value="添加产品"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    <br/>
    
    
     <!-- 营销合同服务明细 -->
     <%-- 
     <div id="accountContractInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">服务明细信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
        </table>
     <s:if test="acctypeContractDTOs != null">
	    <s:form id="accountContractFrom" name="accountContractFrom" action="" method="post">
	    	<s:hidden id="sellerContractDTO.sellContractId" name="sellerContractDTO.sellContractId" />
	    	<s:iterator value="acctypeContractDTOs" id="map">
	    		
	    		
	    		<div id="buttonDiv" style="margin: 5px 8px 0px;">
			            <table border="0" cellpadding="0" cellspacing="0" width="100%">
			                <tr>
			                    <td align="right">
			                        <table border="0" cellpadding="0" cellspacing="0">
			                            <tr>
			                                <td>
			                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('productContractFrom', '${ctx}/customerContract/editProduct.action?sellerProductContractDTO.id='+${map.id})" value="编辑"/>
			                                </td>
			                            </tr>
			                        </table>
			                    </td>
			                </tr>
			            </table>
			        </div>
	    	</s:iterator>
	    	
	    </s:form>
    </s:if>
    </div>
     --%>
</body>
</html>