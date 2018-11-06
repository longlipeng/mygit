<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script language="javaScript">
   function add(){
        var name =document.getElementById("recipient").value;
        var telephone=document.getElementById("recipientPhone").value;
        if(name==''){
           alert("用户名不能为空!");
           return;
            }
        if(telephone==''){
            alert("电话不能为空!");
            return;
             }
          document.recipientForm.action="deliveryPointInsertRecipient.action";
          document.recipientForm.submit();

	   }


</script>
<base target="_self"></base>
<title>编辑快递点</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>编辑快递点</span>
    </div>
    <s:form id="deliveryPointForm" name="deliveryPointForm" action="deliveryPointUpdate.action" method="post">
        <s:hidden name="deliveryPointDTO.deliveryPointId"></s:hidden>
       <s:hidden name="deliveryPointDTO.deliveryId"></s:hidden>
         <s:hidden name="entityId"></s:hidden>
        <div id="deliveryPoint" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
            <div id="deliveryPointTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">快递点信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="deliveryPointTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>快递点名称：</td>
                                    <td><s:textfield name="deliveryPointDTO.deliveryName"/>
                                        <s:fielderror>
                                            <s:param>deliveryPointDTO.deliveryName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>地址：</td>
                                    <td><s:textfield name="deliveryPointDTO.deliveryAddress"/>
                                        <s:fielderror>
                                            <s:param>deliveryPointDTO.deliveryAddress</s:param>
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
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>邮编：</td>
                                    <td><s:textfield name="deliveryPointDTO.deliveryPostcode"/>
                                        <s:fielderror>
                                            <s:param>deliveryPointDTO.deliveryPostcode</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>状态：</td>
                                    <td><s:select list="#{'1':'有效', '0':'无效'}" name="deliveryPointDTO.state"></s:select>
                                        <s:fielderror>
                                            <s:param>deliveryPointDTO.state</s:param>
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
                                    <td style="width: 90px; text-align: right;">设为默认快递点</td>
                                    <td>
                                       	<s:radio list="#{'0':'否','1':'是'}" name="deliveryPointDTO.defaultFlag"></s:radio>
                                        <!--  
                                        <s:hidden id="defaultFlag" name="deliveryPointDTO.defaultFlag" value="%{deliveryPointDTO.defaultFlag == null ? 0 : 1}"></s:hidden>
                                        <s:checkbox name="defaultFlag" value="deliveryPointDTO.defaultFlag" fieldValue="1" onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
                                    -->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right; vertical-align: top">描述：</td>
                                    <td>
                                        <s:textarea name="deliveryPointDTO.memo" cols="50" rows="3"></s:textarea>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <s:hidden name="deliveryPointDTO.customerId"></s:hidden>
            </div>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="submit" class="bt" style="margin: 5px" value="提 交" onclick="maskDocAll();"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.close()" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

    <div id="recipientList" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
        <div id="recipientListTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">收货人列表</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="recipientListTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <ec:table items="deliveryPointDTO.recipientList" var="deliveryRecipientDTO" width="100%"
                imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                autoIncludeParameters="false"
                tableId="deliveryPoint"
                showPagination="false"
                sortable="false">
                <ec:row>
                    <ec:column property="null" alias="recipientIdList" title="选择" width="20%" sortable="false" viewsAllowed="html">
                        <input type="radio" name="recipientIdList" value="${deliveryRecipientDTO.deliveryContactId}" onclick="document.getElementById('recipient').value = '${deliveryRecipientDTO.deliveryContactId}'; document.getElementById('recipientPhone').value = '${deliveryRecipientDTO.contactPhone}'"/>
                    </ec:column>
                    <ec:column property="deliveryContact" title="姓名" width="30%" />
                    <ec:column property="contactPhone" title="联系电话" width="30%" />
                    <ec:column property="null" title="删除" width="20%" >
                        <a href="deleteRecipient.action?deliveryRecipientDTO.deliveryContactId=${deliveryRecipientDTO.deliveryContactId}&deliveryRecipientDTO.deliveryPointId=${deliveryRecipientDTO.deliveryPointId}">删除</a>
                    </ec:column>
                </ec:row>
            </ec:table>
        </div>
    </div>
   </s:form>
    <s:form id="recipientForm" name="recipientForm" action="" method="post">
        <s:hidden name="deliveryRecipientDTO.deliveryPointId" value="%{deliveryPointDTO.deliveryId}"></s:hidden>
        <div id="recipientInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px">
            <div id="recipientInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">收货人信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="recipientInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9; background-color: #FBFEFF;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>姓名：</td>
                                    <td><s:textfield name="deliveryRecipientDTO.deliveryContact" id="recipient"/>
                                        <s:fielderror>
                                            <s:param>deliveryRecipientDTO.deliveryContact</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red;">*</span>联系电话：</td>
                                    <td><s:textfield name="deliveryRecipientDTO.contactPhone" id="recipientPhone"/>
                                        <s:fielderror>
                                            <s:param>deliveryRecipientDTO.contactPhone</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="deliveryRecipientDTO.deliveryPointId" value="<s:property value="deliveryPointDTO.deliveryPointId"/>"/>
            </div>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" value="添加" onclick="add();"/>
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