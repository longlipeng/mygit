<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>修改用户密码</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>修改用户密码</span>
    </div>
    <s:form id="customerForm" name="customerForm" action="" method="post">
         
        <div id="customerState" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="customerStateTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">修改用户密码</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="cardCompanyStateTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>用户名称：
                                    </td>
                                    <td>
                                    	<s:textfield id="userName" name="userDTO.userName" maxlength="128" readonly="true"></s:textfield>
                                        <s:fielderror>
                                            <s:param>userDTO.userName</s:param>
                                        </s:fielderror>
                                    </td>
<%--                                    <td style="text-align: left;">--%>
<%--                                    	<input type="button" class="bt" style="" onclick="window.location = '${ctx}/seller/inquery.action'" value="检查用户名"/>--%>
<%--                                    </td>--%>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>用户邮箱：
                                    </td>
                                    <td>
                                       	<s:textfield id="userDTO.email" name="userDTO.email" readonly="true"/>
										<s:fielderror>
											<s:param>
												userDTO.email
											</s:param>
										</s:fielderror>		
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>用户密码：
                                    </td>
                                    <td>
                                    	<s:password name="password" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>password</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>确认密码：
                                    </td>
                                    <td>
                                       	<s:password id="repassword" name="repassword"/>
										<s:fielderror>
											<s:param>
												repassword
											</s:param>
										</s:fielderror>		
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <s:hidden name="userDTO.userId"></s:hidden>
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
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('customerForm', '${ctx}/seller/updatePassword.action')" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/seller/inquery.action'" value="返 回"/>
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