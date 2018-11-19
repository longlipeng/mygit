<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<base target="_self"></base>
<title>添加快递点</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>添加快递点</span>
    </div>
    <s:form id="deliveryPointForm" name="deliveryPointForm" action="customer/insertDeliveryPoint.action" method="post">
    	<s:hidden name="entityId" />
    	<s:hidden name="nameSpace" />
        <s:hidden name="deliveryPointDTO.entityId"></s:hidden>
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
                                    	<s:radio list="#{1: '是', 0: '否'}" name="deliveryPointDTO.defaultFlag" value="1"></s:radio>
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
                                    <input type="submit" class="bt" style="margin: 5px" value="提 交" onclick=""/>
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