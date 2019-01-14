<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>添加产品明细</title>
<script type="text/javascript">
  
    function choiceProduct() {

        var productId = window.showModalDialog('${ctx}/sellerConstract/productChoice.action?issuerFlag=1&sellerContractQueryDTO.sellContractId='+${layaltyContractDTO.loyaltyContractId}+'&sellerContractQueryDTO.contractBuyer='+${layaltyContractDTO.contractBuyer}, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (productId != null) {
        	document.productForm.action = '${ctx}/issuerProductView.action?productDTO.productId='+productId;
         	document.productForm.submit();
        }
    }
    
    function choiceRule(selectAcctypeNo,serviceId) {
    	document.getElementsByName('serviceIdList')[selectAcctypeNo].value = serviceId ;
		
    	var ruleNo = window.showModalDialog('${ctx}/sellerConstract/serviceRuleChoice.action','_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		if(ruleNo != null){
			document.getElementById('ruleNo_'+selectAcctypeNo).value = ruleNo;
			document.getElementsByName('ruleNoList')[selectAcctypeNo].value = ruleNo;
		}
    }

    function addIssuerProductService(){
         var productid=document.getElementById("productId").value;
         if(productid==''){
        	 errorDisplay('产品号不能为空！');
			 return;
          }
        
                
         var cardfee=document.getElementById("cardFee").value;
         if(cardfee==''){
        	 errorDisplay('卡费不能为空！');
			 return;
          }else{
       	      var str=/^\d+(\.\d+)?$/; 
              if(!str.test(cardfee)) {
        	  alert("卡费输入格式错误，必须是0-99999999.99的数字");
				return;
              }
          }
         var annualfee=document.getElementById("annualFee").value;
         if(annualfee==''){
        	 errorDisplay('卡年费不能为空！');
			 return;
          }else{
        	  var str=/^\d+(\.\d+)?$/; 
              if(!str.test(annualfee)) {
        	  alert("卡年费输入格式错误，必须是0-99999999.99的数字");
				return;
              }
          }  
          
         var serviceIdList=document.getElementsByName("serviceIdList");
		if(serviceIdList==null || serviceIdList.length==0){
			errorDisplay('账户不能为空！');
			return;
		}
         
         var ruleNoList=document.getElementsByName("ruleNoList");
		for(i=0;i<ruleNoList.length;i++){
			if(ruleNoList[i].value==''){
				errorDisplay('账户计算规则不能为空！');
				return;
			}
		}
     
        
        
    	document.productForm.action = '${ctx}/insertContractProductService.action';
    	document.productForm.submit();
    }
    
</script>
</head>
<%@ include file="/commons/messages.jsp"%>
<s:form id="productForm" name="productForm" method="post">
<div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
	
	<s:hidden id="layaltyContractDTO.loyaltyContractId" name="layaltyContractDTO.loyaltyContractId" />
	<s:hidden id="layaltyContractDTO.contractBuyer" name="layaltyContractDTO.contractBuyer"></s:hidden>
    <input type="hidden" id="productDTO.productName" value="${productDTO.productName}"></input>
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
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>产品号：</td>
                                    <td><s:textfield id="productId" name="loyaltyProdContractDTO.productId" cssClass="watch" readonly="true" onclick="choiceProduct()"/>
                                        <s:fielderror>
                                            <s:param>loyaltyProdContractDTO.productId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品名称：</td>
                                    <td><s:textfield id="productName" name="productDTO.productName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
            </tr>
            <tr>
                <td>
                    <table style="text-align: left; width: 100%">
                       <tr>
                            <td style="width: 140px; text-align: right;"><span style="color: red">*</span>卡费：</td>
                            <td>
                                <s:textfield name="loyaltyProdContractDTO.cardFee" id="cardFee"  maxlength="10" />
                                <s:fielderror>
                                    <s:param>loyaltyProdContractDTO.cardFee</s:param>
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
                            <td style="width: 140px; text-align: right;"><span style="color: red">*</span>卡年费：</td>
                            <td>
                                <s:textfield name="loyaltyProdContractDTO.annualFee" id="annualFee"  maxlength="10" />
                                <s:fielderror>
                                    <s:param>loyaltyProdContractDTO.annualFee</s:param>
                                </s:fielderror>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </div>
</div>
<div id="service" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
    <div id="serviceTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer" onclick="showOrHideDiv('serviceTable')">
                    <span class="TableTop">账户明细</span>
                </td>
                <td class="TableTitleEnd">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
 <!-- productServices: ${productServices }
   ruleNoList: ${ruleNoList }
     -->  
    <div id="serviceTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
       	<ec:table items="productServices" var="accTypeId" width="100%"
            imagePath="${ctx}/images/extremecomponents/*.gif"
            view="html"
            autoIncludeParameters="false"
            showPagination="false"
            tableId="product"
            sortable="false" form="contractForm">
            <ec:row>
               <ec:column property="serviceId" title="账户号" width="5%" >
               		<input type="hidden" name="serviceIdList" value="${productServices[ROWCOUNT-1].serviceId}"></input>
               		<span id="serviceId" name="serviceId" >${productServices[ROWCOUNT-1].serviceId}</span>
               </ec:column>
               <ec:column property="serviceName" title="账户名称" width="10%" />
               <ec:column property="defaultRate" title="默认账户费率(%)" width="10%" />
               <ec:column property="null" title="账户计算规则" width="15%" >
               		<input type="hidden" name="ruleNoList"></input>
               		<input type="text" id="ruleNo_${ROWCOUNT-1}" name="ruleNo_${ROWCOUNT-1}" class="watch" onclick="choiceRule(${ROWCOUNT-1},${productServices[ROWCOUNT-1].serviceId})" />
                </ec:column>
            </ec:row>
        </ec:table>
       
    </div>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="addIssuerProductService();" value="提 交"/>
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

</html>