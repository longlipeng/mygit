<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>选择制卡商</title>
<script type="text/javascript">
    var cardCompanys = new Object();

    function loadPage() {
        var cardCompanyData = ${cardCompanyData};
        for (var i=0; i<cardCompanyData.length; i++) {
            cardCompanys[cardCompanyData[i]['cardCompanyId']] = cardCompanyData[i];
        }
        choiceCardCompany();
    }

    function choiceCardCompany() {
        var selectedCardCompanyId = document.getElementById('cardCompanyId').value;
        if (selectedCardCompanyId != '') {
            document.getElementById('cardCompanyAddress').innerHTML = cardCompanys[selectedCardCompanyId]['cardCompanyAddress'];
            document.getElementById('cardCompanyPostcode').innerHTML = cardCompanys[selectedCardCompanyId]['cardCompanyPostcode'];
            document.getElementById('cardCompanyContact').innerHTML = cardCompanys[selectedCardCompanyId]['cardCompanyContact'];
            document.getElementById('cardCompanyPhone').innerHTML = cardCompanys[selectedCardCompanyId]['cardCompanyPhone'];
            document.getElementById('isIcCard').innerHTML = cardCompanys[selectedCardCompanyId]['isIcCard'] == 1 ? '是' : '否';
            document.getElementById('isMsCard').innerHTML = cardCompanys[selectedCardCompanyId]['isMsCard'] == 1 ? '是' : '否';
            document.getElementById('cardCompanyInfo').style.display = '';
        } else {
            document.getElementById('cardCompanyInfo').style.display = 'none';
        }
    }
    
    function submitForm(){
    	var cardCompanyId=document.getElementById('cardCompanyId').value;
    	if(IsSpace(cardCompanyId)){
    		errorDisplay("制卡商 必须输入!");
			return;
    	}
    	makeCardForm.submit();
    }
</script>
</head>
<body onload="loadPage()">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>选择制卡商</span>
    </div>
    <s:form name="makeCardForm" id="makeCardForm" action="makeCardFile/choiceOrder.action">
        <div id="cardCompany" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="cardCompanyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">选择制卡商</span>
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
                                    <td style="width: 100px; text-align: right;">制卡商：</td>
                                    <td>
                                        <s:select list="orderMakeCardDTO.cardCompanyList" listKey="cardCompanyId" listValue="cardCompanyName" name="customerOrderQueryDTO.cardCompanyId" id="cardCompanyId" headerKey="" headerValue="--请选择--" onchange="choiceCardCompany()" value=""></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="cardCompanyInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px; display: none">
            <div id="cardCompanyInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">制卡商信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="cardCompanyInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">制卡商地址：</td>
                                    <td>
                                        <s:label name="cardCompanyAddress" id="cardCompanyAddress"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">制卡商邮编：</td>
                                    <td>
                                        <s:label name="cardCompanyPostcode" id="cardCompanyPostcode"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">联系人：</td>
                                    <td>
                                        <s:label name="cardCompanyContact" id="cardCompanyContact"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">联系电话：</td>
                                    <td>
                                        <s:label name="cardCompanyPhone" id="cardCompanyPhone"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">是否可制IC卡：</td>
                                    <td>
                                        <s:label name="isIcCard" id="isIcCard"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">是否可制磁条卡：</td>
                                    <td>
                                        <s:label name="isMsCard" id="isMsCard"></s:label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" onclick="submitForm()" class="bt" style="margin: 5px" value="选择订单"/>
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