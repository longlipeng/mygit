<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>编辑产品明细</title>
<script type="text/javascript">

    function choiceProduct() {

        var productId = window.showModalDialog('${ctx}/${nameSpace}/productChoice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (productId != null) {
        	document.productForm.action = '${ctx}/${nameSpace}/productView.action?productDTO.productId='+productId;
         	document.productForm.submit();
             
        }
    }
    
    function choiceRule(selectAcctypeNo,serviceId) {
    	document.getElementsByName('serviceIdList')[selectAcctypeNo].value = serviceId ;
		
    	var ruleNo = window.showModalDialog('${ctx}/${nameSpace}/serviceRuleChoice.action','_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		if(ruleNo != null){
			document.getElementById('ruleNo_'+selectAcctypeNo).value = ruleNo;
			document.getElementsByName('ruleNoList')[selectAcctypeNo].value = ruleNo;
		}
    }

    function sub(){
    	document.productForm.action = '${ctx}/${nameSpace}/updateProduct.action';
    	document.productForm.submit();
    	
    }
    
</script>
    <div class="TitleHref">
        <span>编辑营销合同产品信息</span>
    </div>
<s:form id="productForm" name="productForm" method="post">
<div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
	<s:hidden name="nameSpace" />
	<s:hidden id="sellerProductContractDTO.productId" name="sellerProductContractDTO.productId" />
	<s:hidden id="sellerProductContractDTO.id" name="sellerProductContractDTO.id"/>
    <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer" onclick="showOrHideDiv('specialtyTable')">
                    <span class="TableTop">产品信息</span>
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
                           	<td style="width: 140px; text-align: right;"><span style="color: red">*</span>卡费：</td>
		                            <td>
		                                <s:textfield name="sellerProductContractDTO.cardFee" id="cardFee" maxlength="10" />
		                                <s:fielderror>
		                                    <s:param>sellerProductContractDTO.cardFee</s:param>
		                                </s:fielderror>
		                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="text-align: left; width: 100%">
                        <tr>
                            <td style="width: 140px; text-align: right;"><span style="color: red">*</span>卡年费：</td>
		                            <td>
		                                <s:textfield name="sellerProductContractDTO.annualFee" id="annualFee" maxlength="10" />
		                                <s:fielderror>
		                                    <s:param>sellerProductContractDTO.annualFee</s:param>
		                                </s:fielderror>
		                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </div>
</div>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="sub();" value="提 交"/>
                                    </td>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="window.close();" value="返 回"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
</s:form>

