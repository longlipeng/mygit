<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>营销机构信息管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>营销机构信息管理</span>
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
        <div id="queryTable" style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="queryForm" name="queryForm" action="seller/inquery.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">营销机构号：</td>
                                    <td><s:textfield name="sellerQueryDTO.entityId"/>
                                        <s:fielderror>
                                            <s:param>sellerQueryDTO.entityId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">营销机构名称：</td>
                                    <td><s:textfield name="sellerQueryDTO.sellerName"/>
                                        <s:fielderror>
                                            <s:param>sellerQueryDTO.sellerName</s:param>
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
                                    <td><s:textfield name="sellerQueryDTO.externalId"/>
                                        <s:fielderror>
                                            <s:param>sellerQueryDTO.externalId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">注销状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'未注销', '0':'已注销'}" name="sellerQueryDTO.dataState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 90px; text-align: right;">父实体编号：</td>--%>
<%--                                    <td><s:textfield name="sellerQueryDTO.fatherEntityId"/>--%>
<%--                                        <s:fielderror>--%>
<%--                                            <s:param>sellerQueryDTO.fatherEntityId</s:param>--%>
<%--                                        </s:fielderror>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">缺省支付节点：</td>
                                    <td>
                                        <dl:dictList displayName="sellerQueryDTO.paymentTerm" dictValue="${sellerQueryDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">营销机构行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerQueryDTO.activitySector" dictValue="${sellerQueryDTO.activitySector}" dictType="400" tagType="2" defaultOption="true" />
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
                                        <edl:entityDictList displayName="sellerQueryDTO.salesRegionId" dictValue="${sellerQueryDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">有效状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'有效', '0':'无效'}" name="sellerQueryDTO.sellerState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <!-- 
                    <tr>
                       <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">创建时间：From</td>
                                    <td>
                                       <input type="text" name="sellerQueryDTO.startTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="sellerQueryDTO.startTime" />"/>
                                     <s:fielderror>
                                            <s:param>sellerQueryDTO.startTime</s:param>
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
                                       <input type="text" name="sellerQueryDTO.stopTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="sellerQueryDTO.stopTime" />"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                     -->
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
        <div id="listTable" style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="customerForm" name="customerForm" action="seller/inquery.action" method="post">
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
                    action="${ctx}/seller/inquery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="sellerIds" title="选择" width="5%" sortable="false" headerCell="selectAll">
                        	<s:if test="#attr['map'].dataState == 1">
                            	<input type="checkbox" name="sellerIds" value="${map.entityId},${map.fatherEntityId}" onclick="document.getElementById('userDTO.userId').value=${map.userId};"/>
                            </s:if>
                        </ec:column>
                        <ec:column property="entityId" title="营销机构号" width="10%">
                            <a href="${ctx}/seller/view.action?sellerDTO.entityId=${map.entityId}&sellerDTO.fatherEntityId=${map.fatherEntityId}">
                            	${map.entityId}
                            </a>
                        </ec:column>
                        <ec:column property="sellerName" title="营销机构名称" width="20%" />
                        <ec:column property="externalId" title="外部系统代码" width="10%" />
                        <ec:column property="salesRegionId" title="销售区域" width="10%">
                            <edl:entityDictList dictValue="${map.salesRegionId}" dictType="407" tagType="1"/>
                        </ec:column>
                        <ec:column property="sellerState" title="有效状态" width="10%">
                            <s:property value="#attr['map'].sellerState == 1 ? '有效' : '无效'"/>
                        </ec:column>   
                        <ec:column property="dataState" title="注销状态" width="10%">
                            <s:property value="#attr['map'].dataState == 1 ? '未注销' : '已注销'"/>
                        </ec:column>
                         <ec:column property="createTime" title="创建日期" width="10%" />
                    </ec:row>
                </ec:table>
                
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0" >
                                <tr>
										<td>
											<display:security urlId="401101">
												<input type="button" class="btn"
													style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
													onclick="submitForm('updatePasswordForm', '${ctx}/seller/sellerModifyUserState.action', 'edit','sellerIds')"
													value="编辑机构用户" />
											</display:security>
										</td>
										<td>
											<display:security urlId="401102">
												<input type="button" class="btn"
													style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
													onclick="submitForm('updatePasswordForm', '${ctx}/seller/modifyPassword.action', 'edit','sellerIds')"
													value="修改用户密码" />
											</display:security>
										</td>
										<td>
											<display:security urlId="401103">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
													onclick="submitForm('customerForm', '${ctx}/seller/add.action', 'add')"
													value="添加" />
											</display:security>
										</td>
										<td>
											<display:security urlId="401104">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="submitForm('customerForm', '${ctx}/seller/edit.action', 'edit', 'sellerIds')"
													value="编辑" />
											</display:security>
										</td>
										<td>
											<display:security urlId="401105">
												<input type="button" class="btn"
													style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
													onclick="submitForm('customerForm', '${ctx}/seller/delete.action', 'delete', 'sellerIds')"
													value="注销" />
											</display:security>
										</td>
										<td>
											<display:security urlId="401106">
												<input type="button" class="btn"
													style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
													onclick="submitForm('customerForm', '${ctx}/seller/modifyState.action', 'edit', 'sellerIds')"
													value="修改状态" />
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
    <s:form name="updatePasswordForm">
       <s:hidden id="userDTO.userId" name="userDTO.userId"></s:hidden>
    </s:form>
</body>
</html>