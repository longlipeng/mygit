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
<title>查看营销机构合同</title>
<script type="text/javascript">
    function choiceSeller() {
        var sellerDTO = window.showModalDialog('${ctx}/seller/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (sellerDTO != null) {
            document.getElementById('contractBuyer').value = sellerDTO['entityId'];
            document.getElementById('sellerName').value = sellerDTO['sellerName'];
        }
    }

    function choiceSettleRule() {
        var contractBuyer = document.getElementById('contractBuyer').value;
        if (contractBuyer == null || contractBuyer == '') {
        	errorDisplay('请先选择营销机构！');
            return;
        }
        var ruleResult = window.showModalDialog('${ctx}/sellerConstract/settleRuleChoice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if(ruleResult != null){
			var ruleArr = ruleResult.split(',');
			document.getElementById('ruleNo').value = ruleArr[0];
			document.getElementById('ruleName').value = ruleArr[1];
        }
    }

	function addProduct(){
		var sellerContractId = document.getElementById("sellerContractDTO.sellContractId").value;
		var returnValue = window.showModalDialog('${ctx}/sellerConstract/addProductService.action?sellerContractDTO.sellContractId='+sellerContractId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
		if(returnValue == 'true'){
			document.productContractFrom.action="${ctx}/issuerSellerConstract/reEditssuerSellerContract.action";
			document.productContractFrom.submit();
		}
	}

	function updateProduct(productId){
		var returnValue = window.showModalDialog('${ctx}/sellerConstract/editProduct.action?sellerProductContractDTO.id='+productId, '_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		if(returnValue == 'true'){
			document.productContractFrom.action="${ctx}/issuerSellerConstract/reEditssuerSellerContract.action";
			document.productContractFrom.submit();
		}
	}

	function updateAccount(accountId){
		var returnValue = window.showModalDialog('${ctx}/sellerConstract/editService.action?sellerAcctypeContractDTO.id='+accountId, '_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		if(returnValue == 'true'){
			document.productContractFrom.action="${ctx}/issuerSellerConstract/reEditssuerSellerContract.action";
			document.productContractFrom.submit();
		}
	}

    function returnList(){
    	
    	var queryType = document.getElementById('queryType').value;
    	if(queryType=="infoMng"){
    		window.location = '${ctx}/issuerSellerConstract/issuerSellerInquery.action';
    	}else if(queryType=="deadlineMng"){
    		window.location = '${ctx}/issuerSellerConstract/issuerSellerInqueryByDeadline.action';
    	}else{
    		window.location = '${ctx}/issuerSellerConstract/issuerSellerInquery.action';
    	}
    }
    
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看营销机构合同信息</span>
    </div>
        
    <div width="100% id="base" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
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
        	<s:form id="contractForm" name="contractForm" action="issuerSellerConstract/editssuerSellerContract.action" method="post">
    			<s:hidden id="sellerContractDTO.sellContractId" name="sellerContractDTO.sellContractId" />
    			<s:hidden name="queryType" />
                    <table width="98%" style="table-layout: fixed;">

	                <tr>
	                    <td>
	                        <table style="text-align: left; width: 100%">
	                            <tr>
	                                <td style="width: 140px; text-align: right;"><span style="color: red">*</span>结算周期号：</td>
	                                <td><s:textfield id="ruleNo" name="sellerContractDTO.settlePeriodRule" cssClass="watch" onclick="chooseConsumer()" />
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
	                                <td style="width: 140px; text-align: right;">结算周期名称：</td>
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
	                                <td>
	                                    <s:textfield name="sellerContractDTO.deliveryFee"   />
	                                 <!-- ${sellerContractDTO.deliveryFee} -->   
	                                </td>
	                            </tr>
	                        </table>
	                    </td>
	                    
	                    <td>
	                        <table style="text-align: left; width: 100% ">
	                            <tr>
	                                <td style="width: 140px; text-align: right;"><span style="color: red">*</span>结算类型：</td>
	                                <td>
	                                    <s:select name="sellerContractDTO.contractType" list="#{'1':'汇总结算'}" />
	                                    <!--<c:if test="${sellerContractDTO.clearTp eq 1}">汇总结算</c:if>-->
	                                </td>
	                            </tr>
	                        </table>
	                    </td>
	                </tr>
            	</table>
            </s:form>
        </div>
    </div>
    

    
    <div id="buttonDiv" style="margin: 5px 8px 0px;">
        <table border="0" cellpadding="0" cellspacing="0" width="98%">
            <tr>
                <td align="right">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                           
                            <td>
                                <%-- <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/issuerSellerConstract/issuerSellerInquery.action';" value="返 回"/> --%>
                                <input type="button" class="bt" style="margin: 5px" onclick="returnList()" value="返 回"/>
                                
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    
	    
	    <s:if test="productContractDTOs != null">
	    	<s:form id="productContractFrom" name="productContractFrom" action="" method="post">
		    	<s:hidden id="sellerContractDTO.sellContractId" name="sellerContractDTO.sellContractId" />
		    	
		    	<s:iterator value="productContractDTOs" id="map" var="map">
		    		<%--<input type="hidden" id="sellerProductContractDTO.id" name="sellerProductContractDTO.id" value="${map.id}" />
		    		
		    		--%><!-- 服务明细 -->
			        <s:include value="/WEB-INF/pages/univer/seller/sellercontract/sellerProductView.jsp" />
			        
		    	</s:iterator>
	    	
	    	</s:form>
	    </s:if>
</body>
</html>