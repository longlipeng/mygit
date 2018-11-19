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
<title>查看发行机构合同</title>
<script type="text/javascript">

    function chooseConsumer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
			document.getElementById('customerId').value =customerDTO['entityId'];
			document.getElementById('customerName').value = customerDTO['customerName'];
        }
    }
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
        	errorDisplay('请先选择发行机构！');
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
		var sellerContractId = document.getElementById("layaltyContractDTO.loyaltyContractId").value;
		var returnValue = window.showModalDialog('${ctx}/addProductService.action?layaltyContractDTO.loyaltyContractId='+sellerContractId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
		if(returnValue == 'success'){
			document.productContractFrom.action="${ctx}/issuerConstractView.action";
			document.productContractFrom.submit();
		}
	}

	function updateProduct(productId){
		var returnValue = window.showModalDialog('${ctx}/editIssuerProduct.action?loyaltyProdContractDTO.id='+productId, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');  
		if(returnValue == 'success'){
			document.productContractFrom.action="${ctx}/issuerConstractView.action";
			document.productContractFrom.submit();
		}
	}

	function updateAccount(accountId,ruleNo){
		var returnValue = window.showModalDialog('${ctx}/editIssuerService.action?loyaltyAcctypeContractDTO.id='+accountId+'&loyaltyAcctypeContractDTO.ruleNo='+ruleNo, '_blank', 'center:yes;dialogHeight:300px;dialogWidth:700px;resizable:no');
		if(returnValue == 'success'){
			document.productContractFrom.action="${ctx}/issuerConstractView.action";
			document.productContractFrom.submit();
		}
	}

    
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看发行机构合同信息</span>
    </div>
    <s:form id="contractForm" name="contractForm" action="sellerContract/insert.action" method="post">
    	<s:hidden id="layaltyContractDTO.loyaltyContractId" name="layaltyContractDTO.loyaltyContractId" />
    	
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
                                    <!--  <td><s:label name="layaltyContractDTO.ruleNo"/>
                                        <s:fielderror>
                                            <s:param>layaltyContractDTO.ruleNo</s:param>
                                        </s:fielderror>
                                    </td> -->
                                    <td><s:textfield id="ruleNo" name="layaltyContractDTO.ruleNo" cssClass="watch" onclick="chooseConsumer()" />
                                        <s:fielderror>
                                            <s:param>layaltyContractDTO.ruleNo</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期名称：</td>
                                    <!-- <td><s:label name="layaltyContractDTO.ruleName"/></td> -->
                                    <td><s:textfield id="ruleName" name="layaltyContractDTO.ruleName" readonly="true" cssClass="readonly"/></td>
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
                                        <s:textfield name="layaltyContractDTO.expiryDate"
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
										</s:textfield>
                                        <s:fielderror>
                                            <s:param>layaltyContractDTO.expiryDate</s:param>
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
                                       <!--  <s:property value="layaltyContractDTO.contractState == 1?'有效':'无效'"/>   -->
                                         <s:select name="layaltyContractDTO.contractState" list="#{'1':'有效', '0':'无效'}" />
                                        <s:fielderror>
                                            <s:param>layaltyContractDTO.contractState</s:param>
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
                                    <td><s:textfield name="deliveryFeeShow" value="%{(layaltyContractDTO != null && null != layaltyContractDTO.deliveryFee ) ? (layaltyContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive" ></s:textfield>
                                        <s:hidden name="layaltyContractDTO.deliveryFee" id="deliveryFee"></s:hidden>
                                        <s:fielderror>
                                            <s:param>layaltyContractDTO.deliveryFee</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 140px; text-align: right;">
											<span class="no-empty">*</span>结算类型：
										</td>
										<td>
											<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
											<s:select list="#{'1':'汇总结算'}"
												name="layaltyContractDTO.clearTp"
												id="layaltyContractDTO.clearTp" label="规则类型：" />
											</span>											
											<s:fielderror>
												<s:param>layaltyContractDTO.clearTp</s:param>
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
                            <span class="TableTop">产品明细</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            

        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="98%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                           
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/issuerContractInquery.action';" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        </s:form>
	    
	    <s:if test="productContractDTOs != null">
	    	<s:form id="productContractFrom" name="productContractFrom" action="" method="post">
		    	<s:hidden id="layaltyContractDTO.loyaltyContractId" name="layaltyContractDTO.loyaltyContractId" />
		    	
		    	<s:iterator value="loyaltyProductContractDTOs" id="map" var="map">
		    	     <!-- 服务明细 -->
			        <s:include value="issuerContractProductView.jsp" />
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
<%--                                    <input type="button" class="bt" style="margin: 5px" onclick="addProduct();" value="添加产品"/>--%>
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