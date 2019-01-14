<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>修改持卡人关联客户</title>
<script type="text/javascript">
    function choiceCustomer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
        if (customerDTO != null) {
            maskDocAllWithMessage("Wait...");
            document.getElementById('entityId').value = customerDTO['entityId'];
            document.getElementById('customerName').value = customerDTO['customerName'];
            unmaskDocAll();
        }
    }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>修改持卡人关联客户</span>
    </div>
    <s:form id="cardholderForm" name="cardholderForm" action="" method="post">
        <s:hidden name="cardholderDTO.cardholderId"></s:hidden>
        <div id="modifyCustInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="modifyCustInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">关联客户</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="modifyCustInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red">*</span>客户号：</td>
                                    <td><s:textfield id="entityId" name="cardholderDTO.entityId" cssClass="watch" readonly="true" onclick="choiceCustomer()"/>
                                        <s:fielderror>
                                            <s:param>cardholderDTO.entityId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:textfield id="customerName" name="cardholderDTO.customerDTO.customerName" readonly="true" cssClass="readonly"/></td>
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
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardholder/updateCust.action')" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/cardholder/inqueryShow.action'" value="返 回"/>
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