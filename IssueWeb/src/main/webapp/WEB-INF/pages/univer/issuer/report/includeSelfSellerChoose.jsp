<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>营销运营管理</title>
<script type="text/javascript">
    function choice() {
        var entityId;
        var fatherEntityId;
        var sellerIdList = document.getElementsByName('sellerIdList');
        for (var i=0; i<sellerIdList.length; i++) {
            if (sellerIdList[i].checked) {
                var idArr = sellerIdList[i].value.split(",");
                sellerId = idArr[0];
                fatherEntityId = idArr[1];
                break;
            }
        }
        if (sellerId != null) {
            maskDocAll();
            Ext.Ajax.request({
                url: '${ctx}/seller/selectAjax.action',
                params: {
                    'sellerDTO.entityId' : sellerId,
                    'sellerDTO.fatherEntityId' : fatherEntityId
                },
                success: function(response, options) {
                    var sellerDTO = Ext.util.JSON.decode(response.responseText);
                    window.returnValue = sellerDTO;
                    window.close();
                }
            });
        } else {
        	errorDisplay('请选择一个营销机构！');
        }
    }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>营销机构信息</span>
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
            <s:form id="customerForm" name="customerForm" action="/ireport/querySeller.action" method="post">
                <s:hidden name="sellerQueryDTO.entityId"></s:hidden>
                <s:hidden name="sellerQueryDTO.sellerName"></s:hidden>
                <s:hidden name="sellerQueryDTO.externalId"></s:hidden>
                <s:hidden name="sellerQueryDTO.paymentTerm"></s:hidden>
                <s:hidden name="sellerQueryDTO.activitySector"></s:hidden>
                <s:hidden name="sellerQueryDTO.salesRegionId"></s:hidden>
                <s:hidden name="sellerQueryDTO.channelId"></s:hidden>
                <s:hidden name="sellerQueryDTO.dataState"></s:hidden>
                <s:hidden name="sellerQueryDTO.sellerState"></s:hidden>
               	<input type="hidden" name="sellerQueryDTO.startTime" value="<s:date format="yyyy-MM-dd" name="sellerQueryDTO.startTime" />"/>
               	<input type="hidden" name="sellerQueryDTO.stopTime" value="<s:date format="yyyy-MM-dd" name="sellerQueryDTO.stopTime" />"/>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="customerForm"
                    action="${ctx}/seller/choice.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="sellerIdList" title="选择" width="5%" sortable="false" headerCell="">
                        	<s:if test="#attr['map'].dataState == 1">
                            	<input type="radio" name="sellerIdList" value="${map.entityId},${map.fatherEntityId}" />
                            </s:if>
                        </ec:column>
                        <ec:column property="sellerId" title="营销机构号" width="10%">
                            <a href="${ctx}/seller/view.action?sellerDTO.entityId=${map.entityId}&sellerDTO.fatherEntityId=${map.fatherEntityId}">
                            	${map.entityId}
                            </a>
                        </ec:column>
                        <ec:column property="sellerName" title="营销机构名称" width="20%" />
                        <ec:column property="sellerCode" title="营销机构代码" width="10%" />
                        <ec:column property="sellerEnglishName" title="营销机构的英语名称" width="10%">
                            <edl:entityDictList dictValue="${map.salesRegionId}" dictType="407" tagType="1"/>
                        </ec:column>
                        
                      
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