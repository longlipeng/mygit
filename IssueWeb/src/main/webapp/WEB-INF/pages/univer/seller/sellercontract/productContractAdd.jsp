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
<title>添加营销机构合同</title>
<script type="text/javascript">

    function choiceSeller() {
        var sellerDTO = window.showModalDialog('${ctx}/seller/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (sellerDTO != null) {
            document.getElementById('contractBuyer').value = sellerDTO['entityId'];
            document.getElementById('sellerName').value = sellerDTO['sellerName'];
        }
    }

    function choiceProduct() {
        var contractBuyer = document.getElementById('contractBuyer').value;
        if (contractBuyer == null || contractBuyer == '') {
        	errorDisplay('请先选择营销机构！');
            return;
        }
        var productId = window.showModalDialog('${ctx}/sellerConstract/productChoice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (productId != null) {
        	document.contractForm.action = '${ctx}/sellerConstract/productView.action?productDTO.productId='+productId;
        	document.contractForm.submit();
            <%--
            maskDocAll();
            ajaxRemote('${ctx}/sellerConstract/selectProductAjax.action',
                    	{'productDTO.productId': productId },
                    	successFn,
                    	'json'
            );
            --%>
        }
    }

    function choiceSettleRule() {
        var productId = document.getElementById('productId').value;
        if (productId == null || productId == '') {
        	errorDisplay('请先选择产品！');
            return;
        }
        var ruleResult = window.showModalDialog('${ctx}/sellerConstract/settleRuleChoice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if(ruleResult != null){
			var ruleArr = ruleResult.split(',');
			document.getElementById('ruleNo').value = ruleArr[0];
			document.getElementById('ruleName').value = ruleArr[1];
        }
    }
    
    function successFn (productDTO) {
        document.getElementById('productId').value = productDTO['productId'];
       	document.getElementById('productName').value = productDTO['productName'];
       	document.getElementById("productInformation").innerHTML = productDTO;
        unmaskDocAll();
    }

    //var serviceIdList = null;
    
    function choiceRule(selectAcctypeNo,serviceId) {
    	document.getElementsByName('serviceIdList')[selectAcctypeNo].value = serviceId ;
		
    	var ruleNo = window.showModalDialog('${ctx}/sellerConstract/serviceRuleChoice.action','_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		if(ruleNo != null){
			document.getElementById('ruleNo_'+selectAcctypeNo).value = ruleNo;
			document.getElementsByName('ruleNoList')[selectAcctypeNo].value = ruleNo;
		}
    }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>添加营销机构合同信息</span>
    </div>
    <s:form id="contractForm" name="contractForm" action="sellerContract/insert.action" method="post">
        <s:hidden name="productQueryDTO.issuerId" id="issuerId"></s:hidden>
        <s:hidden name="productQueryDTO.issuerGroupId" id="issuerGroupId"></s:hidden>
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
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>营销机构号：</td>
                                    <td><s:textfield id="contractBuyer" name="sellerContractDTO.contractBuyer" cssClass="watch" readonly="true" onclick="choiceSeller()"/>
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
                                    <td style="width: 140px; text-align: right;">营销机构名称：</td>
                                    <td><s:textfield id="sellerName" name="sellerContractDTO.sellerDTO.sellerName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>产品号：</td>
                                    <td><s:textfield id="productId" name="sellerContractDTO.productId" cssClass="watch" readonly="true" onclick="choiceProduct()"/>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.productId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品名称：</td>
                                    <td><s:textfield id="productName" name="sellerContractDTO.productDTO.productName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>结算周期规则：</td>
                                    <td><s:textfield id="ruleNo" name="sellerContractDTO.ruleNo" cssClass="watch" readonly="true" onclick="choiceSettleRule()"/>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.ruleNo</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期规则名称：</td>
                                    <td><s:textfield id="ruleName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>起始日期：</td>
                                    <td>
                                        <s:textfield name="sellerContractDTO.startDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                            <s:param name="value"><s:date name="sellerContractDTO.startDateDate" format="yyyy-MM-dd"/></s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.startDateDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">截止日期：</td>
                                    <td>
                                        <s:textfield name="sellerContractDTO.endDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                            <s:param name="value"><s:date name="sellerContractDTO.endDateDate" format="yyyy-MM-dd"/></s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.endDateDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="productInformation">
            <s:if test="sellerContractDTO.productId != null">
                <s:include value="productInput.jsp"></s:include>
            </s:if>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('contractForm', '${ctx}/sellerConstract/insert.action')" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/sellerConstract/inquery.action';" value="返 回"/>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
</body>
</html>