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
   function update(){
		document.deliveryPointForm.action = "${ctx}/${nameSpace}/updateDeliveryPoint.action";
		document.deliveryPointForm.submit();
   }

   function add(){
	   	document.recipientForm.action = "${ctx}/${nameSpace}/insertDeliveryContanct.action";
		document.recipientForm.submit();
   }
   

	function del(){
		var n=0;
		var checkbox=document.getElementsByName("chooseDeliveryContactId");
		for(i=0;i<checkbox.length;i++){
			if(checkbox[i].checked==true){
				n++;
			}
		}
		if(n==0){
			alert('请选择要删除的对象');
		}	
		if(n>0){
			confirm("确定删除吗？",operDel);
		}
	}

	function operDel(){
		document.deliveryPointForm.action = "${ctx}/${nameSpace}/delDeliveryContanct.action";
		document.deliveryPointForm.submit();
	}
	function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8 || key.keyCode==45)
					return true;
				else
					return false;
			}
	window.onbeforeunload = function(){
		window.returnValue = "success";
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
    <s:form id="deliveryPointForm" name="deliveryPointForm" action="" method="post">
    	<s:hidden name="entityId" />
    	<s:hidden name="nameSpace" />
        <s:hidden name="deliveryPointDTO.deliveryId" />
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
                                    <td><s:select list="#{'1':'有效', '0':'无效'}" name="deliveryPointDTO.deliveryState"></s:select>
                                        <s:fielderror>
                                            <s:param>deliveryPointDTO.deliveryState</s:param>
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
                                    <td style="width: 90px; text-align: right;">是否默认快递点</td>
                                    <td>
                                    	<s:radio list="#{1: '是', 0: '否'}" name="deliveryPointDTO.defaultFlag"></s:radio>
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
                                        <s:textarea name="deliveryPointDTO.deliveryComment" cols="50" rows="3"></s:textarea>
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
                                    <input type="submit" class="bt" style="margin: 5px" value="提 交" onclick="update();"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.close();window.returnValue = 'success'" value="返 回"/>
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
                    <ec:column property="null" alias="chooseDeliveryContactId" title="选择" width="20%" sortable="false" viewsAllowed="html">
                        <input type="checkbox" name="chooseDeliveryContactId" value="${deliveryRecipientDTO.deliveryContactId}"/>
                    </ec:column>
                    <ec:column property="deliveryContact" title="姓名" width="30%" />
                    <ec:column property="contactPhone" title="联系电话" width="30%" />
                </ec:row>
            </ec:table>
             <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" value="删除" onclick="del();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
   </s:form>
    <s:form id="recipientForm" name="recipientForm" action="" method="post">
    	<s:hidden name="entityId" />
       	<s:hidden name="nameSpace" />
        <s:hidden name="deliveryPointDTO.deliveryId" />
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
                                    <td><s:textfield name="deliveryRecipientDTO.contactPhone" id="recipientPhone"  onkeypress="return check(event);"/>
                                        <s:fielderror>
                                            <s:param>deliveryRecipientDTO.contactPhone</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
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