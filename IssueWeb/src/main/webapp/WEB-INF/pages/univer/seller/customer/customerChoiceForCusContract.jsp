<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>客户信息</title>
<script type="text/javascript">
    function choice() {
        var entityId;
        var fatherEntityId;
        var customerId;
        var customerIdList = document.getElementsByName('customerIdList');
        for (var i=0; i<customerIdList.length; i++) {
            if (customerIdList[i].checked) {
                var idArr = customerIdList[i].value.split(",");
                customerId = idArr[0];
                fatherEntityId = idArr[1];
                break;
            }
        }
        if (customerId != null) {
            maskDocAll();
            Ext.Ajax.request({
                url: '${ctx}/customer/selectAjax.action',
                params: {
                    'customerDTO.entityId' : customerId,
                    'customerDTO.fatherEntityId' : fatherEntityId
                },
                success: function(response, options) {
                    var customerDTO = Ext.util.JSON.decode(response.responseText);
                    window.returnValue = customerDTO;
                    window.close();
                }
            });
        } else {
        	errorDisplay('请选择一个客户！');
        }
    }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>客户信息</span>
    </div>
    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">查询条件</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="queryTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="queryForm" name="queryForm" action="customer/choice.action?customerChoiceForContract=1" method="post">
                <s:hidden name="personFlag"></s:hidden>
               
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户号：</td>
                                    <td><s:textfield name="customerQueryDTO.entityId"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.entityId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:textfield name="customerQueryDTO.customerName"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.customerName</s:param>
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
                                    <td style="width: 90px; text-align: right;">外部系统代码：</td>
                                    <td><s:textfield name="customerQueryDTO.externalId"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.externalId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">父实体编号：</td>
                                    <td><s:textfield name="customerQueryDTO.fatherEntityId"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.fatherEntityId</s:param>
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
                                    <td style="width: 90px; text-align: right;">缺省支付节点：</td>
                                    <td>
                                        <dl:dictList displayName="customerQueryDTO.paymentTerm" dictValue="${customerQueryDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerQueryDTO.activitySector" dictValue="${customerQueryDTO.activitySector}" dictType="400" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                      	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">销售区域：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerQueryDTO.salesRegionId" dictValue="${customerQueryDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">有效状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '2':'未审核', '1':'已审核'}" name="customerQueryDTO.cusState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                       <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">创建时间：From</td>
                                    <td>
                                       <input type="text" name="customerQueryDTO.startTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.startTime" />"/>
                                     <s:fielderror>
                                            <s:param>customerQueryDTO.startTime</s:param>
                                        </s:fielderror>
                                    </td>
                                    
                                </tr>
                            </table>
                        </td>
                        
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">To：</td>
                                    <td>
                                       <input type="text" name="customerQueryDTO.stopTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.stopTime" />"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    	 <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">注销状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'未注销', '0':'已注销'}" name="customerQueryDTO.dataState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                             <table style="text-align: left; width: 100%">
                               <tr>
                                 <td style="width: 90px; text-align: right;">证件号码：</td>
                                 <td>
                                       <s:textfield name="customerQueryDTO.corpCredId"/>
                                 </td>
                               </tr>
                            </table>
                        </td>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
        <div id="listTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">记录列表</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="listTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="customerForm" name="customerForm" action="customer/choice.action?customerChoiceForContract=1" method="post">
                <s:hidden name="customerQueryDTO.entityId"></s:hidden>
                <s:hidden name="customerQueryDTO.customerName"></s:hidden>
                <s:hidden name="customerQueryDTO.externalId"></s:hidden>
                <s:hidden name="customerQueryDTO.paymentTerm"></s:hidden>
                <s:hidden name="customerQueryDTO.activitySector"></s:hidden>
                <s:hidden name="customerQueryDTO.salesRegionId"></s:hidden>
                <s:hidden name="customerQueryDTO.channelId"></s:hidden>
                <s:hidden name="customerQueryDTO.dataState"></s:hidden>
                
                <s:hidden name="personFlag"></s:hidden>
               	<input type="hidden" name="customerQueryDTO.startTime" value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.startTime" />"/>
               	<input type="hidden" name="customerQueryDTO.stopTime" value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.stopTime" />"/>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="customerForm"
                    action="${ctx}/customer/choice.action?customerChoiceForContract=1"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="customerIdList" title="选择" width="5%" sortable="false" headerCell="">
                        	<s:if test="#attr['map'].dataState == 1">
                            	<input type="radio" name="customerIdList" value="${map.entityId},${map.fatherEntityId}" />
                            </s:if>
                        </ec:column>
                        <ec:column property="entityId" title="客户号" width="10%">
                            <a href="${ctx}/customer/view.action?customerDTO.entityId=${map.entityId}&customerDTO.fatherEntityId=${map.fatherEntityId}&verifierViewFlag=3">
                            	${map.entityId}
                            </a>
                        </ec:column>
                        <ec:column property="customerName" title="客户名称" width="20%" />
                        <ec:column property="externalId" title="外部系统代码" width="10%" />
                        <ec:column property="salesRegionId" title="销售区域" width="10%">
                            <edl:entityDictList dictValue="${map.salesRegionId}" dictType="407" tagType="1"/>
                        </ec:column>
                 <%--        <ec:column property="customerState" title="有效状态" width="10%">
                            <s:property value="#attr['map'].cusState == 1 ? '已审核' : #attr['map'].cusState == 2 ? '未审核' : '审核中'"/>
                        </ec:column>  --%>
                        <ec:column property="cusState" title="客户状态" width="10%" cell="cusState"/>  
                        <ec:column property="dataState" title="注销状态" width="10%">
                            <s:property value="#attr['map'].dataState == 1 ? '未注销' : '已注销'"/>
                        </ec:column>
                         <ec:column property="createTime" title="创建日期" width="10%" />
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="choice();" value="提 交"/>
                                        </td>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="window.close();" value="关 闭"/>
                                        </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
</body>
</html>