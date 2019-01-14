<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<base target="_self"/>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>选择产品</title>
<script type="text/javascript">
    function choice(){
        var flag = true;
        var selectValue = "";
        for(i = 0; i < document.getElementsByName("pid").length; i++) {
            if (document.getElementsByName("pid").item(i).checked) {
                if(flag){
                    flag=false;
                    selectValue=document.getElementsByName("pid").item(i).value;
                }
            }
        }
        if (flag) {
        	errorDisplay("请选择一条记录操作！");
            return;
        } else {
            window.returnValue=selectValue;
            window.close();
        }
    }
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>选择产品</span>
    </div>
<!--    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
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
            <s:form id="queryForm" name="queryForm" action="customerContract/productChoice.action" method="post">
                <s:hidden name="productQueryDTO.issuerId"></s:hidden>
                <s:hidden name="productQueryDTO.issuerGroupId"></s:hidden>
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">产品号：</td>
                                    <td><s:textfield name="productQueryDTO.productId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">产品名称：</td>
                                    <td><s:textfield name="productQueryDTO.productName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">产品状态：</td>
                                    <td><s:select list="#{'':'-请选择-', 1:'可用', 0:'不可用'}" name="productQueryDTO.prodStat" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
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
    </div>-->
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
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
            <s:form id="customerForm" name="customerForm" action="" method="post">
                <s:hidden name="issuerFlag" id="issuerFlag"/>
            	<s:hidden id="sellerContractQueryDTO.sellContractId" name="sellerContractQueryDTO.sellContractId" />
            	<s:hidden id="sellerContractQueryDTO.contractBuyer" name="sellerContractQueryDTO.contractBuyer"></s:hidden>
                <ec:table items="pageDataDTO.data" var="map" width="100%" form="customerForm"
                    action="${ctx}/customerContract/productChoice.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                    retrieveRowsCallback="limit" autoIncludeParameters="false">
                    <ec:row onclick="">
                        <ec:column property="null" alias="choose" title="选择"
                            width="10%" sortable="false">
                            <input type="radio" name="pid" value="${map.productId}" />
                        </ec:column>
                        <ec:column property="productId" title="产品号" width="20%" />
                        <ec:column property="productName" title="产品名称" width="30%" />
                        <ec:column property="productType" title="产品类型" width="20%" />
                        <ec:column property="cardType" title="卡类型" width="20%" />
                        <ec:column property="prodStat" title="产品状态" width="20%" />
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="choice();" value="提 交"/>
                                    </td>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="window.close();" value="返 回"/>
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