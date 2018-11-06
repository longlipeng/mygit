<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <base target="_self"></base>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">

   function close1()
   {

		  	document.cardholderForm.action ="creditOrderAction!addOrderList?sellOrderDTO.productId=${sellOrderDTO.productId}&sellOrderDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}&sellOrderDTO.processEntityId=${sellOrderDTO.processEntityId}&maxbalance=${maxbalance}";
		  	document.cardholderForm.submit();	   
	    

   }
</script>
<title>查看持卡人信息</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看持卡人信息</span>
    </div>
    <s:form id="cardholderForm" name="cardholderForm" action="cardholder/view.action" method="post">
        <div id="customerInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
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
            <div id="customerInfoTable" style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red">*</span>客户号：</td>
                                    <td><s:label id="entityId" name="cardholderDTO.entityId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:label id="customerName" name="cardholderDTO.customerDTO.customerName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">部门：</td>
                                    <td>
                                        <s:select list="cardholderDTO.customerDTO.departmentList" listKey="departmentId" listValue="departmentName" name="cardholderDTO.departmentId" id="department" headerKey="" headerValue="" disabled="true"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">外部系统代码：</td>
                                    <td><s:label name="cardholderDTO.externalId"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="realNameInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="realNameInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">实名信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="realNameInfoTable" style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red">*</span>持卡人编号：</td>
                                    <td><s:label name="cardholderDTO.cardholderId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span style="color: red">*</span>姓名：</td>
                                    <td><s:label name="cardholderDTO.firstName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件类型：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.idType" dictValue="${cardholderDTO.idType}" dictType="401" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件号：</td>
                                    <td><s:label name="cardholderDTO.idNo"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td >
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">手机号码：</td>
                                    <td><s:label name="cardholderDTO.cardholderMobile"/></td>
                                </tr>
                            </table>
                        </td>
                          <td >
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">邮寄地址：</td>
                                    <td><s:label name="cardholderDTO.mailingAddress"/></td>
                                </tr>
                            </table>
                        </td> 
                    </tr>
                </table>
            </div>
        </div>
        <div id="personalInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="personalInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">个人信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="personalInfoTable" style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">性别：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderGender" dictValue="${cardholderDTO.cardholderGender}" dictType="402" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">出生日期：</td>
                                    <td><s:label name="cardholderDTO.cardholderBirthday" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">邮箱地址：</td>
                                    <td><s:label name="cardholderDTO.cardholderEmail"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人分类：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderSegment" dictValue="${cardholderDTO.cardholderSegment}" dictType="103" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">称谓：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderSalutation" dictValue="${cardholderDTO.cardholderSalutation}" dictType="104" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">职位：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderFunction" dictValue="${cardholderDTO.cardholderFunction}" dictType="105" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">失效时间：</td>
                                    <td><s:label name="cardholderDTO.closeDate" /></td>
                                </tr>
                            </table>
                        </td>
                          <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">状态：</td>
                                    <td>
                                       <s:property value="#attr[cardholderDTO.cardholderState]==1?'有效':'无效' "/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">车架号：</td>
                                    <td><s:label name="cardholderDTO.v_Id"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">车牌号：</td>
                                    <td><s:label name="cardholderDTO.plateNumber"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                   <td style="width: 90px; text-align: right;">驾驶证号：</td>
                                    <td><s:label name="cardholderDTO.driverLicence"/></td>
                                </tr>
                            </table>
                        </td>
                        
                    </tr> 
                    
                    <tr>
                        <td colspan="2">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right; vertical-align: top;">备注：</td>
                                    <td><s:textarea name="cardholderDTO.cardholderComment" rows="5" cols="70" readonly="true" cssClass="readonly"></s:textarea></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div id="allCardInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="allCardInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">持有卡片信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
           	<input type="hidden" name="sellOrderCardListQueryDTO.cardholderId" id="sellOrderCardListQueryDTO.cardholderId" value="${cardholderDTO.cardholderId}"/>
            <div id="allCardInfoTable" style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <ec:table items="cardholderDTO.cardholderCardList.data" var="map" width="100%"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    autoIncludeParameters="false" tableId="cardInfo" form="cardholderForm" action="${ctx}/cardholder/view.action">
                    <ec:row>
                        <ec:column property="cardNo" title="卡号" width="15%" />
                        <ec:column property="productName" title="产品名称" width="15%" />
                        <ec:column property="validityPeriod" title="有效期" width="15%" />
                        <ec:column property="totalAmt" title="当前余额" width="15%" />  
                        <ec:column property="avaliableAmt" title="可用金额" width="15%" />
                        <ec:column property="freezeAmt" title="冻结金额" width="15%" />
                    </ec:row>
                </ec:table>
            </div>
        </div>
        
        <div id="buttonDiv" style="">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                <s:hidden name="cardManagementFlag"></s:hidden>
                                <s:hidden name="callcenter" ></s:hidden>
                                 <s:hidden name="cardManagementDTO.transferOutCard"></s:hidden>
                               <input type="button" class="bt" style="margin: 5px"  value="返 回" onclick="close1();"> 
                             
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