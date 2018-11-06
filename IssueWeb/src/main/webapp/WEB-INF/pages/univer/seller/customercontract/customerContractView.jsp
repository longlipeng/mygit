<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
     function chooseConsumer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
			document.getElementById('customerId').value =customerDTO['entityId'];
			document.getElementById('customerName').value = customerDTO['customerName'];
        }
    }
 
</script>
<title>查看客户合同信息</title>
</head>
<body>
   <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看客户合同信息</span>
    </div>
    <s:form id="contractForm" name="contractForm" action="sellerContract/insert.action" method="post">
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
                <table width="100%" style="table-layout: fixed;">
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
                                    <s:textfield id="fee" name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee && sellerContractDTO.deliveryFee != 0) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
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
            </div>
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
                                    <td style="width: 140px; text-align: right;">产品合同号：</td>
                                    <td>
                                    ${map.sellContractId}
                                    </td>
                                </tr>
                            </table>
                        </td>
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
                    <td>
            		 <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品名称：</td>
                                    <td>
                                     ${map.productName}
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
    <div id="" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
    <s:hidden id="sellerAcctypeContractDTO.id" name="sellerAcctypeContractDTO.id"/>
       	<ec:table items="map.accDTOs" var="account" width="95%"
            imagePath="${ctx}/images/extremecomponents/*.gif"
            view="html"
            autoIncludeParameters="false"
            showPagination="false"
            sortable="false">
            <ec:row>
               
               <ec:column property="sellContractId" title="账户合同号" width="15%"  />
               <ec:column property="acctypeId" title="帐户号" width="15%" />
               <ec:column property="serviceName" title="账户名称" width="15%"  />
               <ec:column property="ruleNo" title="计算规则号" width="15%"  />
               <ec:column property="ruleName" title="计算规则名称" width="15%" />
               
            </ec:row>
        </ec:table>      
    </div>
</div>
</div>
</s:iterator>

	    
    	<div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="98%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                   <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/customerContract/inquery.action';" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    <br/>
</body>
</html>