<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>编辑账户明细</title>
<script type="text/javascript">
    
    function choiceRule(serviceId) {
		
    	var ruleNo = window.showModalDialog('${ctx}/sellerConstract/serviceRuleChoice.action','_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		if(ruleNo != null){
			document.getElementById("loyaltyAcctypeContractDTO.ruleNo").value = ruleNo;
		}
    }

    function sub(){
        if(document.getElementById("loyaltyAcctypeContractDTO.ruleNo").value==''){
        	errorDisplay("计算规则不能为空！");
           return;
         }
    	document.productForm.action = '${ctx}/updateIssuerService.action';
    	document.productForm.submit();
    }
    
</script>
    <div class="TitleHref">
        <span>编辑发行机构账户信息</span>
    </div>
<%@ include file="/commons/messages.jsp"%>
<s:form id="productForm" name="productForm" method="post">
<div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
	
	<s:hidden id="loyaltyAcctypeContractDTO.id" name="loyaltyAcctypeContractDTO.id"/>
    <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer" onclick="showOrHideDiv('specialtyTable')">
                    <span class="TableTop">账户信息</span>
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
                <!-- 用于发行机构合同 -->
                    <table style="text-align: left; width: 100%">
                        <tr>
                            <td style="width: 140px; text-align: right;"><span style="color: red">*</span>计算规则：</td>
                            <td>
								<s:textfield id="loyaltyAcctypeContractDTO.ruleNo" name="loyaltyAcctypeContractDTO.ruleNo" cssClass="watch" readonly="true" onclick="choiceRule()"/>
                                <s:fielderror>
                                    <s:param>loyaltyAcctypeContractDTO.ruleNo</s:param>
                                </s:fielderror>
                            </td>
                        </tr>
                    </table>
                </td>
                
<%--                <td>--%>
<%--                	<!-- 用于客户合同 -->--%>
<%--                    <td>--%>
<%--                    <table style="text-align: left; width: 100%">--%>
<%--                        <tr>--%>
<%--                            <td style="width: 140px; text-align: right;"><span style="color: red">*</span>费率：</td>--%>
<%--                            <td>--%>
<%--								<s:textfield id="" name=""  />--%>
<%--                                <s:fielderror>--%>
<%--                                    <s:param></s:param>--%>
<%--                                </s:fielderror>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
<%--                    </table>--%>
<%--                </td>--%>
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

