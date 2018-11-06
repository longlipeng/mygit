<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>选择订单处理方</title>
<script type="text/javascript">
    var sellerContractDTOs = new Object();

    function loadPage() {
        var sellerContractDTOData = ${sellerContractData};
        for (var i=0; i<sellerContractDTOData.length; i++) {
            sellerContractDTOs[sellerContractDTOData[i]['contractSeller']] = sellerContractDTOData[i];
        }
        choiceSellContract();
    }

    function choiceSellContract() {
        var selectContractSeller = document.getElementById('selectContractSeller').value;
        if (selectContractSeller != '') {
        	document.getElementById('contractSeller').value=selectContractSeller;
        	document.getElementById('sellContractId').value=sellerContractDTOs[selectContractSeller]['sellContractId'];
            document.getElementById('deliveryFee').innerHTML = sellerContractDTOs[selectContractSeller]['deliveryFee']/100;
            document.getElementById('webPayStat').innerHTML = sellerContractDTOs[selectContractSeller]['webPayStat'] == 1 ? '是' : '否';
            document.getElementById('smsSvcStat').innerHTML = sellerContractDTOs[selectContractSeller]['smsSvcStat'] == 1 ? '是' : '否';
            document.getElementById('emailSvcStat').innerHTML = sellerContractDTOs[selectContractSeller]['emailSvcStat'] == 1 ? '是' : '否';
            document.getElementById('webSmsSvcStat').innerHTML = sellerContractDTOs[selectContractSeller]['webSmsSvcStat'] == 1 ? '是' : '否';
            document.getElementById('webEmailSvcStat').innerHTML = sellerContractDTOs[selectContractSeller]['webEmailSvcStat'] == 1 ? '是' : '否';
            document.getElementById('monstmtSvcStat').innerHTML = sellerContractDTOs[selectContractSeller]['monstmtSvcStat'] == 1 ? '是' : '否';
            
            document.getElementById('sellContractInfo').style.display = '';
        } else {
            document.getElementById('sellContractInfo').style.display = 'none';
        }
    }
    
    function submitForm(){
    	var contractSeller=document.getElementById('contractSeller').value;
    	if(IsSpace(contractSeller)){
    		errorDisplay("请选择订单处理方!");
			return;
    	}
    	//匿名订单(销售、采购)
		if('10000002'==${orderType}||'20000002'==${orderType}||'10000012'==${orderType}){
			var cardAmountList = document.getElementsByName("cardAmountList");
			for(i = 0; i < cardAmountList.length; i++) {
					var cardAmount = cardAmountList.item(i).value;
	<%--				if(IsSpace(cardAmount)){--%>
	<%--					errorDisplay("卡片数量必须输入！");--%>
	<%--					return;--%>
	<%--				}--%>
					if(!IsInteger(cardAmount)){
						errorDisplay("卡片数量必须是数字！");
						return;
					}else if(cardAmount<1){
						errorDisplay("卡片数量必须大于0！");
						return;
					}
			}
		}

    	document.buyOrderForm.action="${ctx}/orderReadyAction!insertBuyOrder";
    	document.buyOrderForm.submit();
    }
    function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
					return true;
				else
					return false;
			}
    
    
</script>
</head>
<body onload="loadPage();">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>选择采购订单处理方</span>
    </div>
    <s:form name="buyOrderForm" id="buyOrderForm" action="" method="post">
    	<!-- 订单处理方 -->
    	<s:hidden name="sellBuyOrderDTO.contractSeller" id="contractSeller"/>
    	<!-- 合同 -->
    	<s:hidden name="sellBuyOrderDTO.sellContractId" id="sellContractId"/>
    	<s:hidden name="sellBuyOrderDTO.orderIdArray" id="orderIdArray"/>
		<s:hidden name="sellBuyOrderDTO.productId"  id="productId"/>
		<s:hidden name="sellBuyOrderDTO.orderType" id="orderType"/>
		<s:hidden name="sellBuyOrderDTO.cardLayoutId" id="cardLayoutId" />
		<s:hidden name="sellBuyOrderDTO.faceValueType" id="faceValueType" />
		<s:hidden name="sellBuyOrderDTO.faceValue" id="faceValue" />
    	<s:hidden name="sellBuyOrderDTO.invoiceCompanyName" id="invoiceCompanyName" />
    	<s:hidden name="sellBuyOrderDTO.invoiceAddresses" id="invoiceAddresses" />
    	<s:hidden name="sellBuyOrderDTO.invoiceItemId" id="invoiceItemId" />
    	<s:hidden name="sellBuyOrderDTO.invoiceItem" id="invoiceItem" />
    	<s:hidden name="sellBuyOrderDTO.invoiceDate" id="invoiceDate" />
        <div id="cardCompany" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="cardCompanyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">订单处理方</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="cardCompanyTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">采购订单处理方：</td>
                                    <td>
                                        <s:select list="sellerContractDTOs" listKey="contractSeller" listValue="contractSellerName" name="selectContractSeller" id="selectContractSeller" headerKey="" headerValue="--请选择--" onchange="choiceSellContract()" value=""/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="sellContractInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
            <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">合同信息</span>
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
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费(元)：</td>
                                    <td>
                                        <s:label name="sellerContractDTO.deliveryFee" id="deliveryFee"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否支持网上支付：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.webPayStat" id="webPayStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否短信通知：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.smsSvcStat" id="smsSvcStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否邮件通知：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.emailSvcStat" id="emailSvcStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否短信通知：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.webSmsSvcStat" id="webSmsSvcStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否邮件通知：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.webEmailSvcStat" id="webEmailSvcStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否开通月报服务：</td>
                                    <td>
                                        <s:label  name="sellerContractDTO.monstmtSvcStat" id="monstmtSvcStat" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <br/>
       	<!-- 订单明细 -->
       	<c:if test="${sellOrderList_totalRows > 0}">
        <div id="orderList" name style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
        	<div id="orderListTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">订单明细信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
	       	<ec:table items="sellOrderListDTOs" var="map" width="100%"
	            view="html"
	            autoIncludeParameters="false"
	            showPagination="false"
	            tableId="sellOrderList"
	            sortable="false" form="buyOrderForm" >
	            
	            <ec:row>
	            	<ec:column property="cardLayoutId" title="卡面" width="10%" />
	            	<ec:column viewsAllowed="false" property="faceValueType" title="面额类型" width="10%" />
	            	<ec:column property="faceValue" title="面额值" width="10%" />
	            	<ec:column property="stockAmount" title="库存数量" width="10%" />
	            	<ec:column property="sumAmount" title="合计数量" width="10%" />
	            	<ec:column property="requireAmount" title="差值" width="10%" />
	            	<ec:column property="null" title="卡片数量" width="10%" >
	               		<input type="text" id="cardAmountList" name="cardAmountList" onkeypress="return check(event);"/>
	            	</ec:column>
	            	
	            <ec:column viewsAllowed="false" property="null" title="信息" width="10%" >
	            	<input type="hidden" name="cardLayoutIdList" value="${map.cardLayoutId}" />
		            <input type="hidden" name="faceValueTypeList" value="${map.faceValueType}" />
		            <input type="hidden" name="faceValueList" value="${map.faceValue}" />
	            </ec:column>
	            </ec:row>
	            
	        </ec:table>
    	</div>
    	</c:if>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" onclick="submitForm()" class="bt" style="margin: 5px" value="下一步"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
    
    
    
</body>
</html>