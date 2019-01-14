<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>查看制卡商信息</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看制卡商信息</span>
    </div>
    <div id="cardCompanyInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
        <div id="cardCompanyInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">制卡商信息</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="cardCompanyInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <table width="100%" style="table-layout: fixed;">
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">制卡商号：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyId"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">制卡商名称：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyName"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">制卡商地址：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyAddress"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">制卡商邮编：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyPostcode"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">制卡商联系人：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyContact"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">联系电话：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyPhone"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">密钥索引：</td>
                                <td><s:label name="cardCompanyDTO.cardCompanyKey"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="makeCardInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
        <div id="makeCardInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">制卡信息</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="makeCardInfoTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <table width="100%" style="table-layout: fixed;">
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">IC卡</td>
                                <td><input type="checkbox" <s:property value="cardCompanyDTO.isIcCard == 1 ? 'checked=checked' : ''"/> name="cardCompanyDTO.isIcCard" value="1" onclick="setFileFormat(this.checked, 'icFileFormat')" disabled="disabled"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right; vertical-align: top">制卡文件格式：</td>
                                <td><s:textarea cols="70" tooltip="请输入256个字符" rows="3"  name="cardCompanyDTO.icFileFormat" id="icFileFormat" readonly="true" cssClass="readonly"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right;">磁条卡</td>
                                <td><input type="checkbox" <s:property value="cardCompanyDTO.isMsCard == 1 ? 'checked=checked' : ''"/> name="cardCompanyDTO.isMsCard" value="1" onclick="setFileFormat(this.checked, 'msFileFormat')" disabled="disabled"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="text-align: left; width: 100%">
                            <tr>
                                <td style="width: 90px; text-align: right; vertical-align: top">制卡文件格式：</td>
                                <td><s:textarea cols="70" tooltip="请输入256个字符" rows="3"  name="cardCompanyDTO.msFileFormat" id="msFileFormat" readonly="true" cssClass="readonly"/></td>
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
                                <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/cardCompany/inquery.action'" value="返 回"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>