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
 function sub(){
      var contractName=document.getElementById("contactNameId").value;
      if(contractName==''){
         alert("联系人不能为空！");
         return;
          } 
      document.customerContactForm.action="contactInsert.action";
      document.customerContactForm.submit(); 	 
	 }

</script>

<base target="_self"></base>
<title>添加联系人</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>添加联系人</span>
    </div>
    <s:form id="customerContactForm" name="customerContactForm" action="" method="post">
        <s:hidden name="contactDTO.entityId"></s:hidden>
        <div id="customerContact" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
            <div id="customerContactTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">联系人信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="customerContactTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;"><span class="no-empty">*</span>联系人姓名：</td>
                                    <td><s:textfield name="contactDTO.contactName" id="contactNameId"/>
                                        <s:fielderror>
                                            <s:param>contactDTO.contactName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">联系人性别：</td>
                                    <td>
                                        <edl:entityDictList displayName="contactDTO.contactGender" dictValue="${contactDTO.contactGender}" dictType="402" tagType="2" defaultOption="false" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">联系人职位：</td>
                                    <td><s:textfield name="contactDTO.contactFunction"/>
                                        <s:fielderror>
                                            <s:param>contactDTO.contactFunction</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">电子邮箱：</td>
                                    <td><s:textfield name="contactDTO.contactEmail"/>
                                        <s:fielderror>
                                            <s:param>contactDTO.contactEmail</s:param>
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
                                    <td style="width: 110px; text-align: right;">固定电话：</td>
                                    <td><s:textfield name="contactDTO.contactTelephone"/>
                                        <s:fielderror>
                                            <s:param>contactDTO.contactTelephone</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">手机号码：</td>
                                    <td><s:textfield name="contactDTO.contactMobilePhone"/>
                                        <s:fielderror>
                                            <s:param>contactDTO.contactMobilePhone</s:param>
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
                                    <td style="width: 110px; text-align: right;">联系人类型：</td>
                                    <td>
                                    <edl:entityDictList dictType="421" tagType="2" displayName="contactDTO.contactType" dictValue="${contactDTO.contactType}"></edl:entityDictList>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">设为默认联系人</td>
                                    <td>
                                   		 <s:radio list="#{'0':'否','1':'是'}" name="contactDTO.defaultFlag" value="1"></s:radio>
                                       <!-- 
                                        <s:hidden id="defaultFlag" name="contactDTO.defaultFlag" value="%{contactDTO.defaultFlag == null ? 0 : 1}"></s:hidden>
                                        <s:checkbox name="defaultFlag" value="contactDTO.defaultFlag" fieldValue="1" onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
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
                                    <td style="width: 110px; text-align: right;">有效状态：</td>
                                    <td><s:select list="#{'1':'有效', '0':'无效'}" name="contactDTO.validityFlag"></s:select></td>
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
                                    <input type="button" class="bt" style="margin: 5px" value="提 交" onclick="sub();"/>
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
    </s:form>
</body>
</html>