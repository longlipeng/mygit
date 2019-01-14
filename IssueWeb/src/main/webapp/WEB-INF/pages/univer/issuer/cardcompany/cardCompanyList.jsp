<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
	function query(){				
				document.queryForm.action='${ctx}/cardCompany/inquery.action';
				document.queryForm.submit();
			}
</script>
<title>制卡商管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>制卡商管理</span>
    </div>
    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
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
            <s:form id="queryForm" name="queryForm" action="cardCompany/inquery.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">制卡商号：</td>
                                    <td><s:textfield name="cardCompanyQueryDTO.cardCompanyId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">制卡商名称：</td>
                                    <td><s:textfield name="cardCompanyQueryDTO.cardCompanyName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'有效', '0':'无效'}" name="cardCompanyQueryDTO.cardCompanyState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="query();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
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
            <s:form id="cardCompanyForm" name="cardCompanyForm" action="cardCompany/inquery.action" method="post">
                <s:hidden name="cardCompanyQueryDTO.cardCompanyId"></s:hidden>
                <s:hidden name="cardCompanyQueryDTO.cardCompanyName"></s:hidden>
                <s:hidden name="cardCompanyQueryDTO.cardCompanyState"></s:hidden>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="cardCompanyForm"
                    action="${ctx}/cardCompany/inquery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">

                    <ec:row>
                        <ec:column property="null" alias="cardCompanyIds" title="选择" width="10%"   headerCell="selectAll">
                            <input type="checkbox" name="cardCompanyIds" value="${map.cardCompanyId}" />
                        </ec:column>
                        <ec:column property="cardCompanyId"  title="制卡商号" width="30%" >
                            <a href="cardCompany/view.action?cardCompanyDTO.cardCompanyId=${map.cardCompanyId}">
                                <s:property value="#attr['map'].cardCompanyId" />
                            </a>
                        </ec:column>
                        <ec:column property="cardCompanyName"  title="制卡商名称" width="40%" />
                        <ec:column property="cardCompanyState"   title="状态" width="20%" >                           
                        </ec:column>
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                        <td>
											<display:security urlId="313001">
                                            <input type="button" class="btn" style="width: 50px; height: 20px; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 2px 5px; text-align: right" onclick="submitForm('cardCompanyForm', '${ctx}/cardCompany/add.action', 'add')" value="添加"/>
                                            </display:security>
                                        </td>
                                        <td>
											<display:security urlId="313002">
                                            <input type="button" class="btn" style="width: 50px; height: 20px; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 2px 5px; text-align: right" onclick="submitForm('cardCompanyForm', '${ctx}/cardCompany/edit.action', 'edit', 'cardCompanyIds')" value="编辑"/>
	                                        </display:security>
                                        </td>
                                        <td>
											<display:security urlId="313003">
                                            <input type="button" class="btn" style="width: 70px; height: 20px; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 2px 5px; text-align: right" onclick="submitForm('cardCompanyForm', '${ctx}/cardCompany/modifyState.action', 'edit', 'cardCompanyIds')" value="修改状态"/>
	                                        </display:security>
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