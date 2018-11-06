<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>导入持卡人信息</title>
<script type="text/javascript">

    function choiceCustomer() {
        var customerDTO = window.showModalDialog('${ctx}/customer/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (customerDTO != null) {
            maskDocAllWithMessage("Wait...");
            document.getElementById('entityId').value = customerDTO['entityId'];
            document.getElementById('customerName').value = customerDTO['customerName'];
           // var departmentSelect = document.getElementById('department');
           // departmentSelect.innerHTML = '';
            //var opt = document.createElement('option');
            //opt.value = '';
            //opt.innerHTML = '--请选择--';
            //departmentSelect.appendChild(opt);
           // for (var i = 0; i < customerDTO['departmentList'].length; i++) {
           //    var departmentDTO = customerDTO['departmentList'][i];
           //     var opt = document.createElement('option');
           //     opt.value = departmentDTO['departmentId'];
           //     opt.innerHTML = departmentDTO['departmentName'];
           //     departmentSelect.appendChild(opt);
           // }
            
           // departmentSelect.disabled = false;
            unmaskDocAll();
        }
    }
    
    function submitForm(){
    	document.cardholderForm.action = "creditOrderAction!checkImport";
		document.cardholderForm.submit();
    }
    
   // function submitAjaxForm(formName, action) {
   //     document.forms[formName].action = action;
   //     maskDocAll();
   //     Ext.Ajax.request({
   //         form: formName,
   //         success: function(response, options) {
   //             alert(response.responseText);
   //             unmaskDocAll();
  //          },
  //          failure: function() {
  //              alert('请求失败');
 //               unmaskDocAll();
  //          }
  //      });
 //   }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>导入持卡人信息</span>
    </div>
    <s:form id="cardholderForm" name="cardholderForm" action="cardholder/insert.action" method="post" enctype="multipart/form-data">
        <div id="customerInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="customerInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">所属信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="customerInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                	<s:hidden name="sellOrderDTO.orderId"></s:hidden>
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
        <div id="importFile" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="importFileTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">导入文件</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="importFileTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">选择文件：</td>
                                    <td><s:file name="cardholderFile"></s:file>
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
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="导 入"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px"  onclick="window.close()" value="返 回"/>
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